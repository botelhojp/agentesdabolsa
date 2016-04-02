'use strict';
app.controller('UserController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'DataService', 'NAV_DATA',
function($window, $http, $scope, $route, $rootScope, $location, DataService, NAV_DATA  ) {
	
	$scope.test = "dfdsf";
	
	$scope.foto = DataService.get(NAV_DATA.CURRENT_USER_FOTO);
	
	console.log($scope.foto);
	
	 
	
}]);