siteSignUpModule.controller('siteSignUpCtrl', [
    '$scope', '$dialogs', 'siteSignUpService',
    function ($scope, $dialogs, siteSignUpService) {
        $scope.item = {
            payment: 'Credit'
        };

        $scope.editAddress = function (type) {
            var baseUrl = angular.element('body').data('base-url');
            $dialogs
                .create(baseUrl + 'changeAddress?type=' + type, 'ChangeAddressCtrl', {
                    address: $scope.item.address,
                    billingAddress: $scope.item.billingAddress
                }, {
                    keyboard: false,
                    backdrop: true
                }).result.then(function (item) {
                    if (item.address) {
                        $scope.item.address = item.address;
                    }
                    if (item.billingAddress) {
                        $scope.item.billingAddress = item.billingAddress;
                    }
                });
        };

        $scope.saveSite = function () {
            siteSignUpService.saveSite($scope.item).then(function (response) {
                var validationErrors = response.errors;
                if (validationErrors && validationErrors.length) {
                    var errors = {};
                    validationErrors.forEach(function (entry) {
                        errors[entry.field] = entry.error;
                    });

                    $scope.item.errors = errors;
                } else if (response.status === "ok") {
                    $scope.item.errors = null;
                    //@todo: implement it in other way
                    angular.element('#afterSiteSubmittingForm').submit();
                }
            });
        }
    }]);

siteSignUpModule.factory('siteSignUpService', [
    '$http',
    function ($http) {
        var baseUrl = angular.element('body').data('base-url');
        return {
            saveSite: function (params) {
                return $http.post(baseUrl + 'registration', params).then(
                    function (response) {
                        return response.data;
                    }, function (error) {
                        return error.data;
                    }
                );
            }
        }
    }]);

siteSignUpModule.controller('ChangeAddressCtrl', [
    '$scope', '$modalInstance', 'data',
    function ($scope, $modalInstance, data) {
        $scope.item = {
            address: data.address,
            billingAddress: data.billingAddress
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.setAddress = function (item) {
            $modalInstance.close(item);
        };
    }]);