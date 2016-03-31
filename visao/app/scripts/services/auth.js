'use strict';

app.service('AuthService', ['$http', '$q', 'AppService', '$rootScope', '$window',
    function ($http, $q, AppService, $rootScope, $window) {
	
	var services = {};
	
	services.login = function(login) {
		var deferred = $q.defer();
		$http({
			url : 'api/auth/login',
			method : "POST",
			data : login,
			headers : {
				'Content-Type' : 'application/json;charset=utf8'
			}
		}).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {			
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
	
	
	//Implementação provisório para convivencia do GUIA e auteticacao local
	
	services.guiaEnabled = function () {
        var guia = $window.localStorage.getItem("guia");
        return  (guia && guia == "on")
    };
    
	services.guiaOn = function() {
		$window.localStorage.setItem("guia", "on");
	};
	
	services.guiaOff = function() {
		$window.localStorage.setItem("guia", "off");
	};
	
	return services;

}]);

