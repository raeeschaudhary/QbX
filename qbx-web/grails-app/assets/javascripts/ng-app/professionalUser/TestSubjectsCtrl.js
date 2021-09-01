professionalUserModule.factory('TestSubjectsService', [
    '$resource',
    function ($resource) {
        var baseUrl = angular.element('body').data('base-url');

        return $resource(baseUrl + ':action/:id', {id: '@id'}, {
            list: {method: 'GET', params: {action: 'testSubjects'}, isArray: true},
            get: {method: 'GET', params: {action: 'get'}},
            search: {method: 'GET', params: {action: 'searchTestSubjects'}, isArray: true},
            save: {method: 'POST', params: {action: 'saveTestSubject'}},
            update: {method: 'POST', params: {action: 'updateTestSubject'}},
            delete: {method: 'POST', params: {action: 'deleteTestSubject'}}
        });
    }]);

professionalUserModule.controller('testSubjectsCtrl', [
    '$scope', '$routeParams', '$location', '$filter', 'TestSubjectsService', 'Flash', '$http', '$dialogs', '$timeout', '$modal', '$log',
    function ($scope, $routeParams, $location, $filter, TestSubjectsService, Flash, $http, $dialogs, $timeout, $modal, $log) {
        $scope.list = [];
        $scope.offset = 0;
        $scope.newItem = new TestSubjectsService;
        $scope.table = {};
        $scope.busy = false;

        $scope.select2Options = {
            dropdownCssClass: "select_type2_dropdown"
        };

        var tempFilterText = '',
            filterTextTimeout;

        $scope.$watch('query', function (val) {
            if (filterTextTimeout) {
                $timeout.cancel(filterTextTimeout);
            }

            tempFilterText = val;
            filterTextTimeout = $timeout(function () {
                $scope.search();
            }, 250); //250 ms
        });

        $scope.search = function () {
            $scope.list = [];

            if ($scope.query) {
                TestSubjectsService.search({
                    query: $scope.query
                }, function (list) {
                    var baseUrl = angular.element('body').data('i18n-messages-url');
                    list.forEach(function (entry) {
                        if (entry.language) {
                            $http.get(baseUrl + '?code=professionalUser.language.' + entry.language).success(function (data) {
                                entry.languageName = data.message;
                            });
                        }
                        $scope.list.push(entry);
                    });

                }, errorHandler.curry($scope, $location, Flash));
            } else {
                TestSubjectsService.list({
                    max: parseInt($routeParams.max) || 20,
                    offset: parseInt($scope.offset) || 0
                }, function (list, headers) {
                    list.forEach(function (entry) {
                        if (entry.language) {
                            var baseUrl = angular.element('body').data('i18n-messages-url');
                            $http.get(baseUrl + '?code=professionalUser.language.' + entry.language).success(function (data) {
                                entry.languageName = data.message;
                            });
                        }
                        $scope.list.push(entry);
                    });

                    $scope.total = parseInt(headers('X-Pagination-Total'));
                    $scope.message = Flash.getMessage();
                }, errorHandler.curry($scope, $location, Flash));
            }
        };

        $scope.save = function (newItem) {

            newItem
                .$save(function (response) {
                    Flash.success(response.message);
                }, errorHandler.curry($scope, $location, Flash, newItem))

                .then(function (response) {
                    $scope.errorMessages = {};
                    var item = response.item;
                    var itemToAdd = new TestSubjectsService;
                    angular.extend(itemToAdd, item);

                    if (itemToAdd && itemToAdd.language) {
                        var baseUrl = angular.element('body').data('i18n-messages-url');
                        $http.get(baseUrl + '?code=professionalUser.language.' + itemToAdd.language).success(function (data) {
                            itemToAdd.languageName = data.message;
                        });
                    }

                    $scope.list.push(itemToAdd);
                });
        };

        $scope.clear = function () {
            $scope.newItem = new TestSubjectsService;
        };

        $scope.setDeletedItem = function (item) {
            $scope.itemToRemove = item;
        };

        $scope.confirmDelete = function () {
            var item = $scope.itemToRemove,
                index = $scope.list.indexOf(item);

            TestSubjectsService.delete(item, function (response) {
                Flash.success(response.message);

                $scope.list.splice(index, 1);
                $scope.table.editMode = false;

                hideDeleteDialog();
                $log.info("Item with index : " + item.id + ' deleted');
            }, errorHandler.curry($scope, $location, Flash));
        };

        $scope.cancelDelete = function () {
            hideDeleteDialog();
        };

        function hideDeleteDialog() {
            var delMessage = angular.element('#del_message');
            delMessage
                .hide()
                .css('top', 0);
        }

        $scope.edit = function (item) {
            item.dateOfBirth = $filter('date')(item.dateOfBirth, "dd-MM-yyyy");
            item.editMode = true;
            $scope.table.editMode = true;
        };

        $scope.update = function (item) {
            var index = $scope.list.indexOf(item),
                newItem = new TestSubjectsService,
                itemToAdd;

            angular.extend(newItem, item);

            newItem
                .$update(function (response) {
                    Flash.success(response.message);
                    itemToAdd = response.item;
                    if (response.item && response.item.site) {
                        itemToAdd.site = response.item.site;
                    }
                }, errorHandler.curry($scope, $location, Flash))

                .then(function () {
                    itemToAdd.editMode = false;
                    $scope.table.editMode = false;
                    if (itemToAdd.language) {
                        var baseUrl = angular.element('body').data('i18n-messages-url');
                        $http.get(baseUrl + '?code=professionalUser.language.' + itemToAdd.language).success(function (data) {
                            itemToAdd.languageName = data.message;
                        });
                    }

                    $scope.list[index] = itemToAdd;
                }, errorHandler.curry($scope, $location, Flash, item))

        };

        $scope.loadMoreRows = function () {
            if ($scope.busy || $scope.offset > $scope.total || $scope.total <= 20) return;

            $scope.busy = true;
            $scope.offset += 20;

            TestSubjectsService.list({
                    max: parseInt($routeParams.max) || 20,
                    offset: parseInt($scope.offset) || 0
                },

                function (list, headers) {
                    list.forEach(function (entry) {
                        $scope.list.push(entry);
                    });

                    $scope.message = Flash.getMessage();
                    $scope.busy = false;
                }, errorHandler.curry($scope, $location, Flash));
        };

        $scope.createNewTest = function (item) {
            angular.element('#testSubjectId').val(item.id);
            angular.element('#createTestForm').submit();
        };
    }]);