'use strict';
app.controller('ConfigController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'ConfigService', 'DataService', 'NAV_DATA', 'GameService',

function($window, $http, $scope, $route, $rootScope, $location, ConfigService, DataService, NAV_DATA, GameService ) {
	
	$scope.cfg = {}; 


	$scope.load = function () {
        ConfigService.get().then(
            function (data) {
                console.log(data);
                $scope.cfg = data;
            },
            function (error) {
                console.log(error);                 
            }
        );
    };  

    $scope.cleanDB = function () {
        ConfigService.cleanDB().then(
            function (data) {
                console.log(data);
                $scope.cfg = data;
            },
            function (error) {
                console.log(error);                 
            }
        );
    }; 
	

    $scope.save = function () {                 
        ConfigService.save($scope.cfg).then(
            function (data) {
                $scope.cfg = data;
            },
            function (error) {
                console.log(error);                 
            }
        );
    };  


$scope.load();
	
	
}]);