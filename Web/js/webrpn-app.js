'use strict';

angular.module('webRpnApp', [
    'webRpnControllers',
    'webRpnServices'
]);

angular.module('webRpnControllers', []).controller('WebRpnCtrl', ['$scope', 'Calculator',
    function ($scope, Calculator) {
        var self = this;
        
        $scope.calculate = function (expression) {
            $scope.expression = null;
            Calculator.calculate(expression).$promise.then(self._updateResult);
        };
        
        $scope.getClass = function (result) {
            if (!result) {
                return "";
            }
            if (result.isError) {
                return "bg-danger";
            }
            return "bg-success";
        };
        
        $scope.reenter = function (result) {
            $scope.expression = result.expression;
        };

        self._restoreFromHistory = function (history) {
            if (history.length > 0) {
                $scope.result = history.shift();
            }
            $scope.history = history;
        };

        self._updateResult = function (result) {
            var oldResult = $scope.result;
            if (oldResult) {
                $scope.history.unshift(oldResult);
            }
            $scope.result = result;
        };

        Calculator.history().$promise.then(self._restoreFromHistory);

    }]);

angular.module('webRpnServices', ['ngResource']).factory('Calculator', ['$resource',
    function($resource) {
        var calculateResource = $resource('calculator/calculate', {}, {
            calculate: {method:'POST'}
        });
        var historyResource = $resource('calculator/history', {}, {
            history: {method:'GET', isArray:true}
        });
        var calculator = {};
        calculator.calculate = function(expression) {
            return calculateResource.calculate(null, {expression:expression});
        };
        calculator.history = function() {
            return historyResource.history();
        };
        return calculator;
    }]);