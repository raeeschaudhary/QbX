testModule.controller('TestCtrl', [
    '$scope', '$location', '$timeout',
    function ($scope, $location, $timeout) {
        $scope.cptTestData = {};
        var cptTestObject = CPTTest;
        $scope.showCanvas = false;
        $scope.showVideoCanvas = false;
        $scope.isComputerCorrect = false;
        $scope.isDataSent = false;
        $scope.commission = false;
        $scope.omission = false;
        $scope.testTime = false;
        $scope.testSubjectId = angular.element('body').data('test-subject');

        $scope.checkComputer = function () {
            $location.path('/computerCheck');
            $scope.showCanvas = true;
            $scope.showVideoCanvas = true;
            $scope.isCameraAllowed = true;
            initCptTest();
        };

        $scope.restartCheckComputer = function () {
           // cptTestObject.stopTest();
           // cptTestObject.headTrckrStop();
          //  cptTestObject.stop();
            $scope.checkComputer();
        };

        $scope.goToPracticeTest = function () {
            $scope.showCanvas = false;
            $scope.showVideoCanvas = false;
            $location.path('/performPracticeTest');
        };

        $scope.startPracticeTest = function () {
            $scope.showVideoCanvas = false;
            cptTestObject.runPractiseTest($scope.testSubjectId);
            $scope.showCanvas = true;
        };

        $scope.startTest = function () {
            $scope.showVideoCanvas = false;
            cptTestObject.startTest();
            $scope.showCanvas = true;
        };

        $scope.reviewTestInstructions = function () {
            $scope.showVideoCanvas = false;
            $scope.showCanvas = false;
            $scope.isDataSent = false;
            $location.path('/introduction');

        };

        $scope.doPracticeTestAgain = function () {
            $scope.showVideoCanvas = false;
            $scope.showCanvas = false;
            $scope.cptTestData.practiseTestResult = '';
            $location.path('/performPracticeTest');
        };

        $scope.startRealTestAnyway = function () {
            $scope.showCanvas = true;
            cptTestObject.startTest();
        };


        function initCptTest() {
            $timeout(function () {
                cptTestObject.init($scope.testSubjectId);
            }, 1000);
        }

        var fpsThreshold = angular.element('body').data('fps-threshold');
        $scope.$watchCollection('[cptTestData.isFaceTracked, cptTestData.isFaceDetected, cptTestData.fps]', function (newValues) {
            console.log(newValues);
            if (newValues[0] && newValues[1] && newValues[2]) {
                if (newValues[2] >= fpsThreshold) {
                    $scope.isComputerCorrect = true;
                    $scope.showFpsError = false;
                } else {
                    $scope.showFpsError = true;
                    //code here
                    $scope.showVideoCanvas = false;
                    $scope.showCanvas = false;
                    $scope.isDataSent = false;
                    cptTestObject.removeDomElements();
                }
            }
        });

        $scope.$watchCollection('[cptTestData.practiseTestResult, cptTestData.commission, cptTestData.omission, cptTestData.testTime]', function (values) {
            console.log(values);
            if (values[0] == 'ok') {
                if (values[3] == "false"){
                    $scope.omission = false;
                    $scope.commission = false;
                    $scope.testTime = true;
                    $scope.showCanvas = false;
                    $location.path('/failPracticeTest');
                }
                else{
                $scope.showCanvas = false;
                $location.path('/startTest');
                }
            } else if (values[0] == 'error') {
                if (values[3] == "false"){
                    $scope.omission = false;
                    $scope.commission = false;
                    $scope.testTime = true;
                    $scope.showCanvas = false;
                    $location.path('/failPracticeTest');
                }
                else if (values[1] == "true"){
                    $scope.commission = true;
                    $scope.omission = false;
                    $scope.testTime = false;
                    $scope.showCanvas = false;
                    $location.path('/failPracticeTest');
                }
                else if (values[2] == "true"){
                    $scope.omission = true;
                    $scope.commission = false;
                    $scope.testTime = false;
                    $scope.showCanvas = false;
                    $location.path('/failPracticeTest');
                }
           }
        });

        $scope.$watch('isDataSent', function (val) {
            if (val) {
                $scope.showCanvas = false;
            }
        })
    }]);