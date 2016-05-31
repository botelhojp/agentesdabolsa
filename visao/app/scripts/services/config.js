'use strict';

app.service('ConfigService', ['$http', '$q', '$window', 'DataService', 'NAV_DATA', function($http, $q, $window, DataService, NAV_DATA) {

	var services = {};

    
	services.save = function(obj) {
		var deferred = $q.defer();
		var id = obj.id;
		obj.id = null;

		$http({
			url : id ? '/api/configuracoes/' + id : '/api/configuracoes',
			method : id ? "PUT" : "POST",
			data : obj,
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
			url : '/api/configuracoes/',
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