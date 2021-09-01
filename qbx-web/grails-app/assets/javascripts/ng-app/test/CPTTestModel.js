var testModule = angular.module('test', ['ngRoute']).config([
    '$routeProvider',
    function ($routeProvider) {
        var $body = angular.element('body'),
            testSubjectId = $body.data('test-subject');

        $routeProvider
            .when('/introduction', {
                templateUrl: 'introduction?id=' + testSubjectId
            })
            .when('/startTest', {
                templateUrl: 'startTest'
            })
            .when('/computerCheck', {
                templateUrl: 'computerCheck'
            })
            .when('/performPracticeTest', {
                templateUrl: 'performPracticeTest?id=' + testSubjectId
            })
            .when('/failPracticeTest', {
                templateUrl: 'failPracticeTest'
            })
            .when('/testScreen', {
                templateUrl: 'testScreen'
            })
            .otherwise({
                redirectTo: '/introduction'
            })
    }]);