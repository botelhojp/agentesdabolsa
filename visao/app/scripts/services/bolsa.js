'use strict';

app.service('BolsaService', ['$window', '$q','$http', 'NAV_DATA', function($window, $q, $http, NAV_DATA) {

	var services = {};
	
	services.loadCotacoes = function(acao, init, end) {
		var deferred = $q.defer();
		$http({
			url : '/api/cotacoes?acao='+ acao,
			method : "GET",
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

	
	//nao usado
	services.loadAcoes = function() {
		var deferred = $q.defer();
		$http({
			url : '/api/acoes',
			method : "GET",
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