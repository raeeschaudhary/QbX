var reportModule = angular.module('reportModule', ['dialogs', 'ui.bootstrap', 'ngResource']);

reportModule.factory('reportsService', [
    '$resource',
    function ($resource) {
        var baseUrl = angular.element('body').data('base-url').replace('report', 'testResult').replace('customer', 'testResult');

        return $resource(baseUrl + ':action/:id', {id: '@id'}, {
            sendReportEmail: {method: 'POST', params: {action: 'sendReportEmail'}}
        });
    }]);