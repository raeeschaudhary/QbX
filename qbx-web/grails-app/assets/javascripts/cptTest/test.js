/*
 qbX test main script
 Qbtech AB, Sweden
 */

// --- WEB CAMERA SECTION ---
var CPTTest = (function () {
    var videoInput;
    var canvasInput; // not displayed
    var canvasOverlay;

    var version = "1.4";
    var stimuli_canvas;
    var stimuli_context;
    var videoSet = false;
    var blockTest = false;
    var test_started = false;

    var lastTimeFPS = Date.now();
    var timeStartForm = null;
    var lastTime = null;
    var startTime = null;
    var test_time = null;
    var fov;
    var timeout_Stimuli;
    var timeout_Timer;
    var timeout_BeforeFinish;

    var o_StimuliGenerator = null;

    var testType = 1; // 1 - child, 6 - adult

    var drawMiddlePoint = false;
    var ScaleFactor = 1;
    var i_smoothing = "";
    var imageArray;
    var practise_test = false;
    var test_time_out = false;
    var is_full_screen = true;
    var last_stimuli_time = null;
    var framerate = 0;
    var stimuli_duration;

    var JsonData = null;
    var frameData = [];

    var year;
    var month;
    var day;
    var session_id;

    var videoWidth;
    var videoHeight;

    var htracker;
    var xmlhttp;

// --- WEB CAMERA SECTION ---

    function log(message) {
//        console.log(message);
    }

    function initDomElements() {
        videoInput = document.getElementById('inputVideo');
        canvasInput = document.createElement('canvas'); // not displayed
        canvasOverlay = document.getElementById('overlay');

        videoWidth = videoInput.offsetWidth;
        videoHeight = videoInput.offsetHeight;

        canvasInput.width = videoWidth;
        canvasInput.height = videoHeight;

        setCanvasOverlaySize(videoWidth, videoHeight);

        window.onresize = function () {
            videoWidth = videoInput.offsetWidth;
            videoHeight = videoInput.offsetHeight;
            canvasInput.width = videoWidth;
            canvasInput.height = videoHeight;

            setCanvasOverlaySize(videoWidth, videoHeight);
        };
    }

    function setCanvasOverlaySize(width, height) {
        if (canvasOverlay) {
            canvasOverlay.width = width;
            canvasOverlay.height = height;
        }
    }

    function init(testSubjectId) {
        var arrow = document.getElementById('arrow');
        initDomElements();
        addAllEventListeners();
        initHeadTrackerObject();
        initHeadTracker(arrow);
        updateComputerChecksCounter(testSubjectId)
    }

    function updateComputerChecksCounter(testSubjectId){
        $.ajax(
            {
                type: "POST",
                dataType: "JSON",
                url: updateComputerChecksUrl,
                data:{
                    subject: testSubjectId
                },
                success: function(data){
                    console.log("testSubjectId " + data);

                }
            }
        )
    }

    function updatePracticeTestsCounter(testSubjectId){
        $.ajax(
            {
                type: "POST",
                dataType: "JSON",
                url: updatePracticeTestsUrl,
                data:{
                    subject: testSubjectId
                },
                success: function(data){
                    console.log("testSubjectId " + data);

                }
            }
        )
    }

    function initHeadTrackerObject() {
        createHeadTrackerObject({
                calcAngles: true,
                ui: false,
                detectionInterval: 2,
                headPosition: true, // whether to calculate the head position
                rectangleFilter: true,
                smoothing: true}
        );
    }

    var statusMessages = {
        'whitebalance': 'Checking for stability of camera whitebalance...',
        'detecting': 'Detecting face...',
        'hints': 'Hmmm... Detecting the face is taking a long time.',
        'redetecting': 'Lost track of face, redetecting...',
        'lost': 'Lost track of face :^{.',
        'found': 'Tracking face!'
    };

    var supportMessages = {
        'no getUserMedia': 'getUserMedia is not supported by your browser',
        'no camera': 'No camera found. Using fallback video for facedetection.'
    };

// with smoothing!
    function createHeadTrackerObject(params) {
        try {
            htracker = new headtrackr.Tracker(params);
        }
        catch (err) {
            alert("Error " + err.message);
        }
    }


// -------------------- HEADTRACKER EVENTS FUNCTIONS ----------------------------
    function handleHeadtrackrStatusEvent(event) {
        if (event.status in supportMessages) {
            log(supportMessages[event.status]);
        } else if (event.status in statusMessages) {
            log(statusMessages[event.status]);

            if (!videoSet) {
                videoSet = true;
                timeStartForm = Date.now();
            }

            if (event.status == 'detecting') {
                TestUtils.setValue('isFaceDetected', true);
            } else if (event.status == 'found') {
                TestUtils.setValue('isFaceTracked', true);
            }

            // if camera loses face we still need to show the stimuli, record camera and register buttons
            if (test_started && event.status != 'found') {
                var l_time = Date.now();
                var l_interval = l_time - lastTime;
                var time;

                // show and save stimuli
                invokeHandleStimuliAfterTimeGap(l_time);

                // save camera data
                if (JsonData.camera_length() == 0) {
                    time = l_interval;
                } else {
                    time = JsonData.get_camera_time(JsonData.camera_length() - 1) + l_interval;
                }
                JsonData.add_camera(0, 0, time, 0);
            }
        }
    }

    function invokeHandleStimuliAfterTimeGap(l_time) {
        var timeGap = 2000; //2 seconds
        if ((l_time - last_stimuli_time) >= timeGap) {
            handl_stimuli(l_time);
        }
    }

    function handleFaceTrackingEvent(event) {
        try {
            var l_time = Date.now();
            var lb_changePosition = true;

            // delete rectangle
            var overlayContext = canvasOverlay.getContext('2d');
            overlayContext.clearRect(0, 0, videoWidth, videoHeight);

            var mi_X = Math.round(event.x);
            var mi_Y = Math.round(event.y);

            // once we have stable tracking, draw rectangle
            if (event.detection == 'CS') {

                overlayContext.translate(mi_X, mi_Y);
                overlayContext.rotate(event.angle - (Math.PI / 2));
                overlayContext.strokeStyle = '#00CC00';
                overlayContext.strokeRect((-(event.width / 2)) >> 0,
                    (-(event.height / 2)) >> 0, event.width, event.height);
                overlayContext.rotate((Math.PI / 2) - event.angle);
                overlayContext.translate(-mi_X, -mi_Y);

                // draw middle point
                if (drawMiddlePoint) {
                    DotFContext.beginPath();
                    DotFContext.arc(mi_X, mi_Y, 2, 0, 2 * Math.PI);
                    DotFContext.strokeStyle = '#00CC00';
                    DotFContext.fillStyle = '#00CC00';
                    DotFContext.fill();
                    DotFContext.stroke();
                }

                if (test_started) {

                    // --- RECORDING CAMERA DATA
                    var l_interval = l_time - lastTime;
                    var l_new_value;
                    var l_previous_val;
                    var x;
                    var y;
                    var time;

                    if (JsonData.camera_length() > 0 && i_smoothing != "") {

                        // X smoothing
                        l_previous_val = JsonData.get_camera_x(JsonData.camera_length() - 1);
                        l_new_value = TestUtils.smoothedValue(mi_X, l_interval, i_smoothing, l_previous_val);

                        x = Math.round(l_new_value);

                        // Y smoothing
                        l_previous_val = JsonData.get_camera_y(JsonData.camera_length() - 1);
                        l_new_value = TestUtils.smoothedValue(mi_Y, l_interval, i_smoothing, l_previous_val);
                        y = Math.round(l_new_value);
                    } else {
                        x = mi_X;
                        y = mi_Y;
                    }

                    if (JsonData.camera_length() == 0) {
                        time = l_interval;
                    } else {
                        time = JsonData.get_camera_time(JsonData.camera_length() - 1) + l_interval;
                    }

                    // save data
                    JsonData.add_camera(x, y, time, 1);

                    lastTime = l_time;

                    // show stimuli each 2 seconds
                    invokeHandleStimuliAfterTimeGap(l_time);
                }
            }

            // SHOW number frames per second (if test is not running)
            framerate++;
            if ((l_time - lastTimeFPS) >= 1000) {
                frameData.push(Math.round(framerate / ((l_time - lastTimeFPS) / 1000)));

                if (!test_started) {
                    log(frameData[frameData.length - 1] + " fps");
                }

                framerate = 0;  //and reset values
                lastTimeFPS = l_time;
            }

            // check fps after 5 sec we open the page
            if (timeStartForm != null && !blockTest) {
                //check fps after 5 seconds
                if ((l_time - timeStartForm) / 1000 > 5) {
                    TestUtils.setValue('fpsValue', frameData[frameData.length - 1]);

                    if (frameData[frameData.length - 1] < 20) {
                        log("Unfortunately your computer is not fast enough for this test, you must try this on another computer instead");
                    }

                    blockTest = true;
                }
            }

        } catch (err) {
            throw Error("Error in handleFaceTrackingEvent : " + err.message);
        }
    }

// requires headPosition : true in Tracker constructor
    function handleHeadTrackingEvent(e) {
        fov = Math.round(e.fov * (180 / Math.PI));

        // and Main scale Factor!
        ScaleFactor = ( (videoWidth / 2) / (Math.tan(e.fov / 2) * 600) ) * 27;
        ScaleFactor = Math.round(ScaleFactor * 100) / 100;

        // show stimuli each 2 seconds even if we loose the face
        if (test_started) {
            invokeHandleStimuliAfterTimeGap(Date.now());
        }
    }


// -------------------- HEADTRACKER EVENTS FUNCTIONS ----------------------------

// -------------------- HEADTRACKER EVENTS --------------------------------------

    function addTrackingEventListeners() {
        document.addEventListener("facetrackingEvent", handleFaceTrackingEvent);
        // requires headPosition : true in Tracker constructor
        document.addEventListener('headtrackingEvent', handleHeadTrackingEvent);
        document.addEventListener('headtrackrStatus', handleHeadtrackrStatusEvent, true);
    }

    function addFullScreenEventListeners() {
        document.addEventListener("fullscreenchange", handler_changeFullScreen, false);
        document.addEventListener("webkitfullscreenchange", handler_changeFullScreen, false);
        document.addEventListener("mozfullscreenchange", handler_changeFullScreen, false);
        document.addEventListener("msfullscreenchange", handler_changeFullScreen, false);
    }

    function addKeysEventListeners() {
        document.addEventListener("keydown", handler_KeyPress, false);
    }

    function addAllEventListeners() {
        addTrackingEventListeners();
        addFullScreenEventListeners();
        addKeysEventListeners();
    }

// -------------------- HEADTRACKER EVENTS --------------------------------------

// --- RUN HEADTRACKER -----
// --- web browser requires camera conformation!

    function initHeadTracker(arrow) {
        try {
            htracker.init(videoInput, canvasInput, arrow);
            htracker.start();
            htracker.stop();
        } catch (error) {
            alert("Error " + error.message);
        }
    }

// ---------------------------- STIMULI, CAMERA ---------------------------------

    function handl_stimuli(currTime) {

        // show stimuli
        try {

            // save current time
            last_stimuli_time = currTime;
            var li_interval = currTime - startTime;

            var stimuliRand = o_StimuliGenerator.get_stimuli();
            var target = stimuliRand < 0 ? 0 : stimuliRand;

            // save data
            JsonData.add_stimuli(target, li_interval);

            // show stimuli on the screen
            var stimuliLength = JsonData.stimuli_length(),
                stimuliId = JsonData.get_stimuli(stimuliLength - 1);

            show_stimuli(stimuliId);

            var stimuli = document.getElementById("stimuli");
            // wait N ms and hide stimuli image
            timeout_Stimuli = setTimeout(function () {
                stimuli.style.display = 'none';
            }, stimuli_duration);

            // test finished ?
            if ((testType == 1 && JsonData.stimuli_length() == 450) ||
                (testType != 1 && JsonData.stimuli_length() == 600) ||
                (practise_test && JsonData.stimuli_length() == 20)
                )
                timeout_BeforeFinish = setTimeout(function () {
                    handler_TimerComplete();
                }, 1000); // wait 1 sec and terminate test!
        } catch (err) {
            throw Error("Error in handler_Stimuli :" + err.message);
        }
    }

    function handler_TimerComplete() {
        try {
            // exit full screen
            TestUtils.getOutFullScreen();
            test_time_out = true;

            if (stop_test()) {
                if (!practise_test) {
                    // next page
//                    var sessionid = TestUtils.get_sessionid();

                    //we havent't this method
//                    reportPage(sessionid, test_time);
                }
            }

        }
        catch (err) {
            throw Error("Error in handler_TimerComplete: " + err.message);
        }
    }

    function preloadImages() {
        var img;
        imageArray = new Array(4);

        if (testType == 1) {
            img = new Image();
            img.src = '../image/target.png';
            imageArray[0] = img;
            img = new Image();
            img.src = '../image/nontarget.png';
            imageArray[1] = img;
        } else {
            img = new Image();
            img.src = '../image/stimuli1.png';
            imageArray[0] = img;
            img = new Image();
            img.src = '../image/stimuli2.png';
            imageArray[1] = img;
            img = new Image();
            img.src = '../image/stimuli3.png';
            imageArray[2] = img;
            img = new Image();
            img.src = '../image/stimuli4.png';
            imageArray[3] = img;
        }

        stimuli_canvas = document.getElementById("stimuli");
        var fullScreenElement = document.getElementById("full_screen");

        if (is_full_screen) {
            TestUtils.getInFullScreen(fullScreenElement);
        }

        stimuli_context = stimuli_canvas.getContext('2d');

        // stimuli_canvas.style.top = "50%";
        //my code
        //stimuli_context.fillStyle = "blue";
        //stimuli_context.font = "bold 16px Arial";
        //stimuli_context.fillText("Zibri", 100, 400);

        stimuli_canvas.style.top = screen.height / 2 - stimuli_canvas.height / 2 + "px";
        //stimuli_canvas.style.left = "50%";
        stimuli_canvas.style.left = screen.width / 2 - stimuli_canvas.width / 2 + "px";
    }

    function show_stimuli(id) {
        var index = -1;

        if (testType == 1) {
            switch (id) {
                case 0:
                    index = 1;
                    break;
                case 128:
                    index = 0;
                    break;
            }
        } else {
            switch (id) {
                case 128:
                    index = 0;
                    break;
                case 0:
                    index = 0;
                    break;
                case 129:
                    index = 1;
                    break;
                case 1:
                    index = 1;
                    break;
                case 130:
                    index = 2;
                    break;
                case 2:
                    index = 2;
                    break;
                case 131:
                    index = 3;
                    break;
                case 3:
                    index = 3;
                    break;
            }
        }

        stimuli_canvas.style.display = 'block';
        stimuli_context.drawImage(imageArray[index], 0, 0);
    }

    function start_test() {
        try {
            JsonData = new JSONObject();

            // fin out the test type
            var age = TestUtils.getAge();

            if (age > 12) {
                testType = 6;
                stimuli_duration = 200;
            } else {
                testType = 1;
                stimuli_duration = 100;
            }

            // init random generator (target/nontarget)
            o_StimuliGenerator = new StimuliGenerator();

            // load images to the buffer
            preloadImages();

            if (!o_StimuliGenerator.init(testType, practise_test)) {
                TestUtils.getOutFullScreen();
                return;
            }

            test_time = null;

            lastTime = Date.now();
            startTime = lastTime;

            last_stimuli_time = startTime;
            test_time_out = false;
            frameData = [];

            // Wait 4 seconds...
            timeout_Timer = setTimeout(function () {
                test_started = true;
            }, 4000);

        } catch (err) {
            throw Error("Error start_test :" + err.message);
        }
    }

    var stop_test = function () {
        // stop headtracking

        //console.log('stop test');
        try {
            test_started = false; // should be before htracker.stop();!

            htracker.stop();

            test_time = Math.round((Date.now() - startTime) / 1000);

            lastTime = null;
            startTime = lastTime;
            last_stimuli_time = null;
            clearTimeout(timeout_Stimuli);
            clearTimeout(timeout_Timer);
            clearTimeout(timeout_BeforeFinish);

            var ret;

            // send data to the server
            if (!practise_test)
                ret = send_data_server();
            else {

                var obj = check_practise_result(JsonData);

                ret = obj.return_val;
                var resultToNGModel = ret ? 'ok' : 'error';
                //console.log("val " + ret)
                //console.log("omission " + obj.omision)
                //console.log("commissio " + obj.comision)
                var omitted = true;
                var committed = true;
                if (obj.omision >= 50 || obj.comision >= 50){
                    if (obj.omision >= 50){
                        if (obj.comision >= 50){
                        omitted = true;
                        committed = true;
                        }
                        else{
                            omitted = true;
                            committed = false;
                        }
                    }
                    else if (obj.comision >= 50){
                        if (obj.omision >= 50){
                            omitted = true;
                            committed = true;
                        }
                        else{
                            omitted = false;
                            committed = true;
                        }
                    }
                    resultToNGModel = "error"
                }
                else{
                    if (isNaN(obj.omision) || isNaN(obj.comision)){
                        omitted = true;
                        committed = true;
                        resultToNGModel = "error"
                    }
                    else{
                        resultToNGModel = "ok"
                        omitted = false;
                        committed = false;
                    }
                }
                var completed = false;
                if (test_time >= 42){
                    completed = true;
                }

                //console.log("omission " + obj.omision);
                //console.log("comission " + obj.comision);

                TestUtils.setValue('omission', omitted);
                TestUtils.setValue('commission', committed);
                TestUtils.setValue('testTime', completed);
                TestUtils.setValue('practiseTestResult', resultToNGModel);

                if (!ret) {
                    log("Your practise test is awful! I think you do not understand anything! You need to watch video and read instruction again!");
                }

                log("Omission fel: " + Math.round(obj.omision) + "%");
                log("Comission fel: " + Math.round(obj.comision) + "%");
                log("Test time = " + test_time + " sec");
            }

            // clean data

            htracker.start();
            return ret;
        } catch (err) {
            throw Error("Error in stop_btn :" + err.message);
        }
    };

    function send_data_server() {
        try {
            // get JSON
            var fps = TestUtils.calc_average_fps(frameData);
            var country = 46;
            var age = TestUtils.getAge();

            //json in string
            var sendData = TestUtils.prepareJSONObjectToSend(JsonData, test_time, fov, ScaleFactor, fps, country, testType, age);

//            xmlhttp.send("data=" + sendata + "&sessionid=" + session_id + "&version=" + version);
            TestUtils.setValue('testResults', sendData);
            $("#testResultsForm").submit();
        } catch (err) {
            throw Error(err.message);
        }
    }

    function handler_KeyPress(e) {
        try {
            if (e.keyCode == 32) {
                // space
                var lo_Date = Date.now();
                var li_interval = lo_Date - startTime;

                // save data
                JsonData.add_button(li_interval);
            }
        } catch (err) {
            alert(err.message);
        }
    }

    function handler_changeFullScreen() {
        try {
            var isInFullScreen = TestUtils.isInFullScreen();

            if (!isInFullScreen && !test_time_out) {
                if (stop_test()) {
                    // next page
                    if (!practise_test) {
//                        var sessionid = TestUtils.get_sessionid();
//                        reportPage(sessionid, test_time);
                    }
                }
            }
        } catch (err) {
            throw Error(err.message);
        }
    }


    // code here
    function removeDomElements(){
        htracker.stop();
        document.removeEventListener("facetrackingEvent", handleFaceTrackingEvent);
        // requires headPosition : true in Tracker constructor
        document.removeEventListener('headtrackingEvent', handleHeadTrackingEvent);
        document.removeEventListener('headtrackrStatus', handleHeadtrackrStatusEvent, true);
    }

    return {
        init: function (testSubjectId) {
            init(testSubjectId);
        },

        resetBlockTest: function () {
            blockTest = false;
        },

        runPractiseTest: function (testSubjectId) {
            practise_test = true;
            updatePracticeTestsCounter(testSubjectId)
            start_test();
        },

        stopTest: function () {
            stop_test();
        },

        reInitDom: function () {
            initDomElements();
        },

        removeDomElements: function(){
            removeDomElements();
        },

        headTrackrInit: function () {
            htracker.init(videoInput, canvasInput);
        },

        headTrckrStop: function () {
            htracker.stop();
        },

        headTrckrStart: function () {
            htracker.start();
        },

        startTest: function () {
            practise_test = false;
            start_test();
        },

        headTrackrTrack: function () {
            htracker.track();
        }
    }
})();

var TestUtils = {
    isInFullScreen: function () {
        return document.webkitIsFullScreen || document.mozFullScreen;
    },

    smoothedValue: function (newValue, elapsedTime, smoothing, smoothed) {
        var a = ( newValue - smoothed );
        var b = smoothed;
        var ret = smoothed + (elapsedTime * a / smoothing);

        return Math.round(ret);
    },

    getInFullScreen: function (element) {
        if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen();
        } else {
            alert("no full screen regime available!");
        }
    },

    getOutFullScreen: function () {
        if (document.exitFullscreen) {
            console.log('document.exitFullscreen');
            document.exitFullscreen();
        } else if (document.webkitExitFullscreen) {
            console.log('document.webkitExitFullscreen');
            document.webkitExitFullscreen();
        } else if (document.mozCancelFullScreen()) {
            console.log('document.mozCancelFullScreen');
            document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
            console.log('document.msExitFullscreen');
            document.msExitFullscreen();
        } else {
            console.log('error in getOutFullScreen');
        }
    },

    getAge: function () {
        var age = parseInt(document.getElementById('age').value);
        return age;
    },

    calc_average_fps: function (frameData) {
        var frame_sum = 0;
        for (var i = 0; i < frameData.length; i++) {
            frame_sum += frameData[i];
        }
        if (frameData.length > 0) {
            return Math.round(frame_sum / frameData.length);
        } else {
            return 0;
        }
    },

    setValue: function (elemId, val) {
        var element = document.getElementById(elemId);
        element.value = val;
        jQuery(element).trigger('input');
    },

    prepareJSONObjectToSend: function (JsonData, test_time, fov, scale, fps, country, testType, age) {
        var test_date = new Date();

        JsonData.testDate = test_date.getFullYear() + "-" + (test_date.getMonth() + 1) + "-" + test_date.getDate();
        JsonData.testDuration = test_time;

        JsonData.fov = fov;
        JsonData.scaleFactor = scale;
        JsonData.fps = fps;
        JsonData.country = country;
        JsonData.product = testType;
        JsonData.age = age;
        JsonData.testSubjectId = $('body').data('test-subject');

        return JSON.stringify(JsonData);
    }

};
