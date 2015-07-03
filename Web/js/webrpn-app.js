'use strict';

var webRpnApp = angular.module('webRpnApp', [
    'webRpnControllers',
    'webRpnServices'
]);

angular.module('webRpnControllers', []).controller('WebRpnCtrl', ['$scope', 'Calculator',
    function($scope, Calculator) {
        var self = this;

        var history = Calculator.history();
        if (history.length > 0) {
            $scope.result = history.pop();
        }
        $scope.history = history;

        $scope.calculate = function(expression) {
            Calculator.calculate(expression).$promise.then(self._updateResult);
        };
        
        self._updateResult = function (result) {
            var oldResult = $scope.result;
            if (oldResult) {
                $scope.history.unshift(oldResult);
            }
            $scope.result = result;
        };
        
        
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