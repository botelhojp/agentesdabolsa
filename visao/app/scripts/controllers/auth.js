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
	
	
	$scope.guiaOn = function guiaOn() {
		AuthService.guiaOn();		
		$scope.guiaEnabled = AuthService.guiaEnabled();	
    } 
	
	$scope.guiaOff = function guiaOff() {
		AuthService.guiaOff();
		$scope.guiaEnabled = AuthService.guiaEnabled();
    } 	
	
	if (AuthService.guiaEnabled() === null) {		
		AuthService.guiaOn();
	}
	$scope.guiaEnabled = AuthService.guiaEnabled();
}]);