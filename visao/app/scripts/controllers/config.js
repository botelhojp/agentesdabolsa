'use strict';
app.controller('ConfigController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'ConfigService', 'DataService', 'NAV_DATA', 'GameService',

function($window, $http, $scope, $route, $rootScope, $location, ConfigService, DataService, NAV_DATA, GameService ) {
	
	$scope.config = {}; 

    $scope.load();


	
	$scope.load = function () {
        ConfigService.get().then(
            function (data) {
                $scope.config = data;
            },
            function (error) {
                console.log(error);                 
            }
        );
    };  
	

    $scope.save = function () {                 
        AgenteService.save($scope.config).then(
            function (data) {
                $scope.new();
                $scope.refreshGrid();
            },
            function (error) {
                console.log(error);                 
            }
        );
    };  


	
	
}]);