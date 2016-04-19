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
	
	
	return services;
}]);