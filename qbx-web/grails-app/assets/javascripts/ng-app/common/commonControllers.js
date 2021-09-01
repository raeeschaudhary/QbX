angular.module('commonControllersService', [])
    .controller('ModalInstanceCtrl', [
        '$scope', '$modalInstance',
        function ($scope, $modalInstance) {
            $scope.ok = function () {
                $modalInstance.close(true);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }])
    .controller('ChangePasswordCtrl', [
        '$scope', 'data', '$modalInstance', '$http', 'Grails',
        function ($scope, data, $modalInstance, $http, Grails) {
            Grails.get({id: data.id}, function (item) {
                $scope.item = item;
            });

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

            $scope.setPassword = function (item) {
                var baseUrl = angular.element('body').data('base-url');
                $http.post(baseUrl + 'setPassword', item).success(function (result) {
                    if (result.status === 'ok') {
                        $modalInstance.close();
                    } else {
                        $scope.alerts = [
                            { type: 'error', msg: result.content }
                        ];
                    }
                });
            };
        }])
    .controller('ChangeAddressCtrl', [
        '$scope', 'data', '$modalInstance', '$http',
        function ($scope, data, $modalInstance, $http) {
            $scope.item = data.item;

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

            $scope.setAddress = function (item) {
                if (item.id) {
                    var baseUrl = angular.element('body').data('base-url');
                    $http.post(baseUrl + 'editAddress', item).success(function (result) {
                        var item = result.item;
                        item.editMode = $scope.item.editMode;
                        $modalInstance.close(item);
                    }).error(function (result, status) {
                        $scope.alerts = [
                            { type: 'error', msg: result.errors }
                        ];
                    });
                } else {
                    $modalInstance.close(item);
                }
            };
        }])
    .controller('EditCardCtrl', [
        '$scope', '$modalInstance', 'data', '$http',
        function ($scope, $modalInstance, data, $http) {
            $scope.item = data.siteInfo;
            $scope.card = {};
            $scope.card.isValidCard = false;

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

            $scope.setCard = function (item) {
                if ($scope.card.isValidCard) {
                    var baseUrl = angular.element('body').data('base-url');
                    $http.post(baseUrl + 'updateCard', item).success(function (result) {
                        var item = result.item;
                        $modalInstance.close(item);
                    }).error(function (result, status) {
                                $scope.alerts = [
                                    { type: 'error', msg: result.errors }
                                ];
                            });
                }
            };
        }]);
