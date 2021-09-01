professionalUserModule.controller('transactionsCtrl', [
    '$scope', '$timeout', 'transactionsService',
    function ($scope, $timeout, transactionsService) {
        var params = {
            offset: 0,
            max: 20,
            total: 0
        };

        function load() {
            transactionsService.getTransactions({offset: 0, max: 20}).then(
                function (response) {
                    var transactions = response.data;
                    var list = [];
                    transactions.forEach(function (entry) {
                        list.push(entry);
                    });

                    $scope.list = list;
                    params.total = parseInt(response.headers('X-Pagination-Total'));
                }
            );
        }

        $scope.loadMoreRows = function () {
            if ($scope.busy || params.offset > params.total || params.total <= 20) return;

            params.offset += 20;
            $scope.busy = true;

            transactionsService.getTransactions({offset: params.offset, max: params.max}).then(
                function (response) {
                    var transactions = response.data;

                    transactions.forEach(function (entry) {
                        $scope.list.push(entry);
                    });
                    $scope.busy = false;
                }
            );
        }

        var tempFilterText,
            filterTextTimeout;

        $scope.$watch('query', function (val) {
            if (filterTextTimeout) {
                $timeout.cancel(filterTextTimeout);
            }

            if (val) {
                tempFilterText = val;
                filterTextTimeout = $timeout(function () {
                    transactionsService.search(tempFilterText).then(
                        function (foundTransactions) {
                            $scope.list = [];
                            var newList = [];
                            foundTransactions.forEach(function (entry) {
                                newList.push(entry);
                            });

                            $scope.list = newList;
                        }
                    );
                }, 250); //250 ms
            } else {
                load();
            }
        });

    }]);

professionalUserModule.factory('transactionsService', [
    '$http',
    function ($http) {
        var baseUrl = angular.element('body').data('base-url');
        return {
            getTransactions: function (params) {
                return $http.get(baseUrl + 'transactions', {params: params}).then(
                    function (response) {
                        return response;
                    }, function (error) {
                        return error.data;
                    }
                );
            },

            search: function (params) {
                console.log(params);
                return $http.post(baseUrl + 'searchTransactions', {query: params}).then(
                    function (response) {
                        return response.data;
                    }, function (error) {
                        return error.data;
                    }
                );
            }
        }
    }]);