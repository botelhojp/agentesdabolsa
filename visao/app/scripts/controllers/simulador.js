'use strict';
app.controller('SimuladorController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'GameService', 'ConfigService', 'NAV_DATA', 'AUTH_EVENTS', 'ENV',
function($window, $http, $scope, $route, $rootScope, $location, GameService, ConfigService, NAV_DATA, AUTH_EVENTS, ENV ) {
	
	$scope.trust = "";
	$scope.refresh = true;

    ConfigService.get().then(
        function (data) {
            //console.log(data);
            $scope.rounds = data.iterationTotal;
        },
        function (error) {
            console.log(error);                 
        }
    );    

	$scope.startGame = function () {
		$scope.result();
		$window.document.getElementById("messageArea").value = "";		
		GameService.simulate($scope.rounds, $scope.trust, $scope.metric).then(
			function (data) {
				//console.log(data);		
			},
			function (error) {
				console.log(error);					
			}
		);
	};

	$scope.stop = function () {
		$scope.refresh = false;
		$window.document.getElementById("messageArea").value = "";			
		GameService.stop().then(
			function (data) {
				//console.log(data);

				location.reload();	

			},
			function (error) {
				console.log(error);
			}
		);
	};

	function getFormattedDate() {
    	var date = new Date();
    	var str = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "_" +  date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds();
	    return str;
	}

	$scope.csv = function () {		
		GameService.csv().then(
			function (data) {
				//console.log(data.result);
				var blob = new Blob([data.result], { type:"application/csv;charset=utf-8;" });			
				var downloadLink = angular.element('<a></a>');
				downloadLink.attr('href',window.URL.createObjectURL(blob));
		        downloadLink.attr('download', 'result_'+ getFormattedDate()  + '.csv');
				downloadLink[0].click();
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
	    x: { }
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
					if ($scope.refresh == true){
						$scope.result()	;
					}
				}, 10000); 
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
	    	$window.document.getElementById("messageArea").value += evt.data;
			
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