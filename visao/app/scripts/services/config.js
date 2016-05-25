'use strict';

app.service('ConfigService', ['$http', '$q', '$window', 'DataService', 'NAV_DATA', function($http, $q, $window, DataService, NAV_DATA) {

	var services = {};

    
	services.save = function(config) {
		var deferred = $q.defer();
		var id = config.id;
		config.id = null;
		$http({
			url : id ? '/api/config/' + id : '/api/config',
			method : id ? "PUT" : "POST",
			data : agente,
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
	
	services.get = function() {
		var deferred = $q.defer();
		$http({
			url : '/api/config/',
			method : "GET",
		}).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};

	return services;
}]);