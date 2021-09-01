angular.module('commonDirectivesService', [])
    .directive('datepick', function () {
        return {
            restrict: 'A',
            link: function postLink(scope, iElement) {
                iElement.datepicker({
                    format: 'dd-mm-yyyy',
                    autoclose: true,
                    orientation: "auto right",
                    startView: 2, weekStart: 1,
                    startDate: '-90y', endDate: "-3y"
                });
            }
        };
    })
    .directive('validDate', function () {
        var isValid = function (date) {
            return /^(\d{2})[-\/](\d{2})[-\/](\d{4})$/.test(date);
        };

        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ngModelCtrl) {

                ngModelCtrl.$parsers.unshift(function (viewValue) {
                    ngModelCtrl.$setValidity('validDate', isValid(viewValue));
                    return viewValue;
                });

                ngModelCtrl.$formatters.unshift(function (modelValue) {
                    ngModelCtrl.$setValidity('validDate', isValid(modelValue));
                    return modelValue;
                });
            }
        };
    })
    .directive('deleteDialog', function () {
        return {
            restrict: 'A',
            link: function ($scope, $element, $attrs) {
                var $deleteMessage = angular.element('#del_message');

                $element.find('.delete').find('i').bind('click', function () {
                    var position = $element.position();
                    $deleteMessage
                        .show()
                        .css('top', position.top);
                });
            }
        }
    })
    .directive('deleteVoucherDialog', function () {
        return {
            restrict: 'A',
            scope: {
                item: '=deleteVoucherDialog'
            },
            link: function ($scope, $element, $attrs) {
                if ($scope.item.status.name == 'NOT_USED') {
                    var $deleteMessage = angular.element('#del_message');

                    $element.find('.delete').find('i').bind('click', function () {
                        var position = $element.position();
                        $deleteMessage
                            .show()
                            .css('top', position.top);
                    });
                }
            }
        }
    });

