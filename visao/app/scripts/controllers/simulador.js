'use strict';
app.controller('SimuladorController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'GameService', 'NAV_DATA', 'AUTH_EVENTS', 'ENV',
function($window, $http, $scope, $route, $rootScope, $location, GameService, NAV_DATA, AUTH_EVENTS, ENV ) {
	
	$scope.rounds = 1;	

	$scope.trust = "";

	$scope.startGame = function () {

		$window.document.getElementById("messageArea").value = "";		
		
		GameService.simulate($scope.rounds, $scope.trust).then(
			function (data) {
				console.log(data);		
			},
			function (error) {
				console.log(error);					
			}
		);
	};	


	$scope.clean = function () {
		$window.document.getElementById("messageArea").value = "";			
		GameService.clean().then(
			function (data) {
				console.log(data);		
			},
			function (error) {
				console.log(error);					
			}
		);
	};	

	var chart = c3.generate({
	  data: {
	    json: []
	  },
	  axis: {
	    x: {
	    }
	  }
	});

	if ($rootScope.mySocket === undefined){
		$rootScope.mySocket = new WebSocket(ENV.wsEndpoint);
	}

	$scope.result = function(){
		GameService.result().then(
			function (data) {
				var keys =  data.keys;
				setTimeout(function () {        
					chart.load({
					  json: data.values,
					  keys: {
					    value: keys
					  }
					});	

				$scope.result()	;
				}, 1000); 
			},
			function (error) {
				console.log(error);					
			}
		);

         	


	}
	
   
    $rootScope.mySocket.onopen = function(evt) {
    	 //console.log("Abre conexão…");
    };
    
    $rootScope.mySocket.onmessage = function(evt) {
    	$window.document.getElementById("messageArea").value += evt.data + "\n";
		
		//rola para a ultima linha
		var obj = $window.document.getElementById("messageArea");
		var currentScrollHeight = obj.scrollHeight;
		obj.scrollTop = (obj.scrollTop + 100000); 
		
    };
    $rootScope.mySocket.onclose = function(evt) {
    	 console.log("Conexão fechada…");
    };
    $rootScope.mySocket.onerror = function(evt) {
    	 console.log("Erro…");
    };


	$scope.getSeries = function() {
		var deferred = $q.defer();
		$http({
			url : '/api/game/result_keys',
			method : "GET",
		}).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};


}]);


	