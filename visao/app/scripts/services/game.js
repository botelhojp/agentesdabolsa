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
    
	services.play = function(game, iteration) {
		var deferred = $q.defer();
		$http.get('/api/game/play?game=' + game.user + '&iteration=' + iteration).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};
	
	
	services.simulate = function(rounds, trust, metric) {
		var deferred = $q.defer();
		$http.get('/api/game/simulate_start?rounds=' + rounds + '&trust=' + trust + '&metric=' + metric).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};

	services.stop = function(rounds, trust) {
		var deferred = $q.defer();
		$http.get('/api/game/stop').success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};

	services.result = function() {
		var deferred = $q.defer();
		$http.get('/api/game/result/json').success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};

	services.csv = function() {
		var deferred = $q.defer();
		$http.get('/api/game/result/csv').success(function(data) {
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