'use strict';
app.controller('AuthController', [ '$window', '$scope', '$route', '$rootScope', '$location', 'AUTH_EVENTS', 'AuthService',
function($window, $scope, $route, $rootScope, $location, AUTH_EVENTS, AuthService) {
	
	$scope.user = {username : "mk_00000000000", password: "123456789"};
	
	
	$scope.login = function login() {
		if ($scope.form.$valid) {	
			AuthService.login($scope.user).then(
				function (data) {
					 $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
				},
				function (error) {
					console.log(error);
				}
			);	
		}
    }    
	
	
	$scope.logout = function logout() {
		$rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
    }	
	
}]);