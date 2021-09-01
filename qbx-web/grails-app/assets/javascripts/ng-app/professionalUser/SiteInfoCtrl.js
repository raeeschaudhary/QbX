professionalUserModule.controller('siteInfoCtrl', [
    '$scope', '$dialogs',  'siteInfoService',
    function ($scope, $dialogs, siteInfoService) {
        $scope.siteInfo = siteInfoService.getSiteInfo(function () {
            $scope.siteInfo.payment = $scope.siteInfo.paymentMethod.value;
        });

        siteInfoService.getLast4(function(result){
            $scope.siteInfo.last4 = result.last4
        });

        $scope.select2Options = {
            dropdownCssClass: "form_select_dropdown"
        };

        $scope.save = function () {
            siteInfoService.save($scope.siteInfo, function (response) {
                $scope.siteInfo.errors = null;
            }, function (error) {
                var errors = {};
                error.data.errors.forEach(function (entry) {
                    errors[entry.field] = entry.error;
                });

                $scope.siteInfo.errors = errors;
            });
        };

        $scope.registerNewCard = function () {
            var baseUrl = angular.element('body').data('base-url');
            $dialogs
                    .create(baseUrl + 'changeCard', 'EditCardCtrl', {
                        siteInfo: $scope.siteInfo
                    }, {
                        keyboard: false,
                        backdrop: true
                    }).result.then(function (item) {
                        if (item.last4) {
                            $scope.siteInfo.last4 = item.last4;
                        }
                    });
        };
    }]);

professionalUserModule.factory('siteInfoService', [
    '$resource',
    function ($resource) {
        var baseUrl = angular.element('body').data('base-url');

        return $resource(baseUrl + ':action/:id', {id: '@id'}, {
            getSiteInfo: {method: 'GET', params: {action: 'siteInfo'}},
            getLast4: {method: 'GET', params: {action: 'last4'}},
            save: {method: 'POST', params: {action: 'saveSiteInfo'}}
        });
    }]);
