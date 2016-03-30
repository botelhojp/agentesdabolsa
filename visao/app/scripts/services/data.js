'use strict';

app.service('DataService', ['$window', 'NAV_DATA', function($window, NAV_DATA) {

	var services = {};

	services.get = function (key) {
        return $window.localStorage.getItem(key);
    };

    services.set = function (key, value) {
        $window.localStorage.setItem(key, value);
    };
    
    
    services.clear = function () {
    	angular.forEach(NAV_DATA, function(value, key) {    	
    		$window.localStorage.removeItem(value);
    	});
    };
    
	return services;
}]);