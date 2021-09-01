var professionalUserModule = angular.module('professionalUserModule', ['commonDirectivesService', 'commonControllersService', 'ngRoute', 'ngResource', 'infinite-scroll', 'flashService', 'ui.bootstrap', 'dialogs', 'ui.select2']);
professionalUserModule.config([
    '$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when('/transactions', {
                templateUrl: 'transactionsTpl',
                controller: 'transactionsCtrl'
            })
            .when('/testSubjects', {
                templateUrl: 'testSubjectsTpl',
                controller: 'testSubjectsCtrl'
            })
            .when('/siteInfo', {
                templateUrl: 'siteInfoTpl',
                controller: 'siteInfoCtrl'
            })
            .when('/billingInfo', {
                templateUrl: 'billingInfoTpl',
                controller: 'billingInfoCtrl'
            });
    }]);