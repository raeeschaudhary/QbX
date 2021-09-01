reportModule.controller('reportCtrl', [
    '$scope', '$dialogs', 'reportsService',
    function ($scope, $dialogs, reportsService) {
        var testResultsId = angular.element('.container.report_page').data('test-res');
        $scope.mail = {
            id: testResultsId
        };
        $scope.setEmail = function () {
            var baseUrl = angular.element('body').data('base-url').replace('report', 'testResult').replace('customer', 'testResult');
            $dialogs
                .create(baseUrl + 'renderEmailTpl', 'sendEmailCtrl', {id: $scope.mail.id}, {
                    keyboard: false,
                    backdrop: true
                }).result.then(function (mail) {
                    $scope.mail = mail;
                    reportsService.sendReportEmail(mail, function (response) {
                        alert(response.message);
                    }, function (error) {
                        alert(error.data.errors);
                    });
                });
        };
    }]);

reportModule.controller('sendEmailCtrl', [
    '$scope', '$modalInstance', 'data',
    function ($scope, $modalInstance, data) {
        $scope.mail = {
            id: data.id
        }
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.setEmailInstance = function (mail) {
            $modalInstance.close(mail);
        };
    }]);