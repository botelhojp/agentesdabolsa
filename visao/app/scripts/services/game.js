'use strict';

app.service('GameService', ['$http', '$window', '$q', function($http, $window, $q) {

	var services = {};
    
	services.getAtivoRandom = function(acao, init, end) {
		var deferred = $q.defer();
		$http.get('/api/acoes?search=random').success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
	
	
	services.buy = function(acao, dia) {
		var deferred = $q.defer();
		$http.get('/api/game/buy?acao=random&dia=dia').success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
	
	
	return services;
}]);