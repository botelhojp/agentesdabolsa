'use strict';

app.service('AgenteService', ['$http', '$q', '$window', 'DataService', 'NAV_DATA', function($http, $q, $window, DataService, NAV_DATA) {

	var services = {};

    
	services.save = function(agente) {
		var deferred = $q.defer();
		var id = agente.id;
		agente.id = null;
		$http({
			url : id ? '/api/agentes/' + id : '/api/agentes',
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
	return services;
}]);