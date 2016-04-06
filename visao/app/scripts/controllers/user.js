'use strict';
app.controller('UserController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'DataService', 'NAV_DATA', 'AUTH_EVENTS',
function($window, $http, $scope, $route, $rootScope, $location, DataService, NAV_DATA, AUTH_EVENTS ) {

	setTimeout(function () {
        $scope.$apply(function () {
            $scope.user = JSON.parse(DataService.get(NAV_DATA.CURRENT_USER));
        });
    }, 100);
	
	
	$scope.logout = function logout() {
		$rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
    }	

}]);