'use strict';

app.service('GameService', ['$http', '$window', '$q', function($http, $window, $q) {

	var services = {};
	
	services.start = function(user) {
		var deferred = $q.defer();
		$http.get('/api/game/start?user=' + user).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
    
	services.play = function(game) {
		var deferred = $q.defer();
		$http.get('/api/game/play?game=' + game.user).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
	
	
	services.buy = function(game) {
		var deferred = $q.defer();
		$http.get('/api/game/buy?game=' + game.user).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
	
	services.sell = function(game) {
		var deferred = $q.defer();
		$http.get('/api/game/sell?game=' + game.user).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
	
	
	return services;
}]);