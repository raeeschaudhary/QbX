//=require_tree ng-app/common

/**
 * A service for storing one-time messages to be displayed after redirecting to
 * another view.
 */
angular.module('flashService', []).factory('Flash', function () {
    var flash = {};

    flash.getMessage = function () {
        var value = this.message;
        this.message = undefined;
        return value;
    };

    flash.error = function (text) {
        this.message = {level: 'error', text: text};
    };
    flash.success = function (text) {
        this.message = {level: 'success', text: text};
    };
    flash.info = function (text) {
        this.message = {level: 'info', text: text};
    };

    return flash;
});

/**
 * The main scaffolding module.
 */
var scaffoldingModule = angular.module('scaffolding', ['grailsService', 'commonControllersService', 'commonDirectivesService', 'flashService', 'ngRoute', 'infinite-scroll', 'ngSanitize', 'ui.bootstrap', 'dialogs', 'ui.select2']);

/**
 * Route definitions connecting URL fragments to views and controllers.
 */
scaffoldingModule.config([
    '$routeProvider',
    function ($routeProvider) {
        var baseUrl = angular.element('body').data('base-url');
        $routeProvider.
            when('/list', {templateUrl: baseUrl + 'totalList', controller: 'ListCtrl'}).
            otherwise({redirectTo: '/list'});
    }
]);

function toArray(element) {
    return Array.prototype.slice.call(element);
}

Function.prototype.curry = function () {
    if (arguments.length < 1) {
        return this; //nothing to curry with - return function
    }
    var __method = this;
    var args = toArray(arguments);
    return function () {
        return __method.apply(this, args.concat(toArray(arguments)));
    }
};

/**
 * Generic $resource error handler used by all controllers.
 */
function errorHandler($scope, $location, Flash, item, response) {
    if (response && response.status) {
        switch (response.status) {
            case 404: // resource not found - return to the list and display message returned by the controller
                Flash.error(response.data.message);
                break;
            case 409: // optimistic locking failure - display error message on the page
                $scope.alerts = [
                    {type: 'error', msg: response.data.message}
                ];
                break;
            case 422: // validation error - display errors alongside form fields
                $scope.errorMessages = {};
                $scope.errorMessages[item.id] = {};
                response.data.errors.forEach(function (entry) {
                    $scope.errorMessages[item.id][entry.field] = entry.error;
                });
                break;
            default: // TODO: general error handling
        }
    }
}

scaffoldingModule.controller('ListCtrl', [
    '$scope', '$routeParams', '$location', '$filter', 'Grails', 'Flash', '$http', '$dialogs', '$timeout', '$modal', '$log',
    function ($scope, $routeParams, $location, $filter, Grails, Flash, $http, $dialogs, $timeout, $modal, $log) {
        $scope.list = [];
        $scope.offset = 0;
        $scope.newItem = new Grails;
        $scope.table = {};
        $scope.busy = false;
        $scope.voucher = {
            tooltip: 'Please, select site',
            usedStatusTooltip: 'Used Voucher cannot be updated'
        };

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

        $scope.$watch('selectedSite.id', function (val) {
            $scope.voucher.tooltip = val ? '' : 'Please, select site';
        });

        $scope.search = function () {
            $scope.list = [];

            if ($scope.query) {
                Grails.search({
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
                Grails.list({
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
                    var itemToAdd = new Grails;
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
            $scope.newItem = new Grails;
        };

        $scope.setDeletedItem = function (item) {
            $scope.itemToRemove = item;
        };

        $scope.confirmDelete = function () {
            var item = $scope.itemToRemove,
                index = $scope.list.indexOf(item);

            Grails.delete(item, function (response) {
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
                newItem = new Grails,
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

        $scope.createVoucher = function (amount) {
            for (var i = 0; i < amount; i++) {
                var item = new Grails;
                item.site = $scope.selectedSite;

                item
                    .$save(function (response) {
                        Flash.success(response.message);
                    }, errorHandler.curry($scope, $location, Flash))

                    .then(function (response) {
                        $scope.list.push(response.item);
                    })
            }
        };

        $scope.editVoucher = function (elem) {
            if (elem.status.name == 'NOT_USED') {
                $scope.edit(elem);
            }
        };

        $scope.deleteVoucher = function (elem) {
            if (elem.status.name == 'NOT_USED') {
                $scope.setDeletedItem(elem);
            }
        };

        $scope.loadMoreRows = function () {
            if ($scope.busy || $scope.offset > $scope.total || $scope.total <= 20) return;

            $scope.busy = true;
            $scope.offset += 20;

            Grails.list({
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

        $scope.updatePassword = function (id) {
            var baseUrl = angular.element('body').data('base-url');
            $dialogs.create(baseUrl + 'changePassword', 'ChangePasswordCtrl', {id: id},
                {keyboard: false, backdrop: true});
        };

        $scope.editAddress = function (item, type) {
            var index = $scope.list.indexOf(item);
            var baseUrl = angular.element('body').data('base-url');
            $dialogs.create(baseUrl + 'changeAddress?type=' + type, 'ChangeAddressCtrl', {item: item},
                {keyboard: false, backdrop: true}).result.then(function (item) {
                    if (index < 0) {
                        $scope.newItem[type] = item[type];
                    } else {
                        $scope.list[index][type] = item[type];
                    }
                });
        };
    }]);