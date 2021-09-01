/*
 JSON object to transfer test data from the client to the server
 Qbtech AB, Sweden
 */

function JSONObject() {
    this.patkey = "";
    this.test_version = "1.4";
    this.data_version = "1.3";
    this.patientid = "";
    this.test_date = "";
    this.test_duration = 0;
    this.gender = "";
    this.dob = "";
    this.fov = 0;
    this.fps = 0;
    this.scale_factor = 0;
    this.country = 0;
    this.testType;
    this.age;
    this.camera_data = new CameraObject();
    this.stimuli_data = new StimuliObject();
    this.button_data = [];

    // -- methods
    this.add_camera = function (x, y, time, quality) {
        try {
            this.camera_data.x.push(x);
            this.camera_data.y.push(y);
            this.camera_data.t.push(time);
            this.camera_data.q.push(quality);
        }
        catch (err) {
            throw Error(err.message);
        }
    }

    this.add_stimuli = function (target, time) {
        try {
            this.stimuli_data.tg.push(target);
            this.stimuli_data.t.push(time);
        }
        catch (err) {
            throw Error(err.message);
        }
    }

    this.add_button = function (time) {
        this.button_data.push(time);
    }

    this.get_camera_x = function (index) {
        return this.camera_data.x[index];
    }

    this.get_camera_y = function (index) {
        return this.camera_data.y[index];
    }

    this.get_camera_time = function (index) {
        return this.camera_data.t[index];
    }

    this.camera_length = function () {
        return this.camera_data.x.length;
    }

    this.get_stimuli = function (index) {
        return this.stimuli_data.tg[index];
    }

    this.get_stimuli_time = function (index) {
        return this.stimuli_data.t[index];
    }

    this.stimuli_length = function () {
        return this.stimuli_data.tg.length;
    }

    this.button_length = function () {
        return this.button_data.length;
    }
    this.get_button = function (index) {
        return this.button_data[index];
    }
}

function CameraObject() {
    this.x = [];
    this.y = [];
    this.t = []; // time
    this.q = []; // quality
}

function StimuliObject() {
    this.tg = []; // target
    this.t = []; // time
}
