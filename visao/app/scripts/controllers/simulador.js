'use strict';
app.controller('SimuladorController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'GameService', 'NAV_DATA', 'AUTH_EVENTS', 'ENV',
function($window, $http, $scope, $route, $rootScope, $location, GameService, NAV_DATA, AUTH_EVENTS, ENV ) {
	
	$scope.rounds = 1;
	
	$scope.startGame = function () {
		$window.document.getElementById("messageArea").value = "";		
		
		GameService.simulate($scope.rounds).then(
				function (data) {
					console.log(data);		
				},
				function (error) {
					console.log(error);					
				}
			);
	};	
	
	
    var mySocket = new WebSocket(ENV.wsEndpoint);
    mySocket.onopen = function(evt) {
    	 //console.log("Abre conexão…");
    };
    
    mySocket.onmessage = function(evt) {
    	$window.document.getElementById("messageArea").value += evt.data + "\n";
		
		//rola para a ultima linha
		var obj = $window.document.getElementById("messageArea");
		var currentScrollHeight = obj.scrollHeight;
		obj.scrollTop = (obj.scrollTop + 100000); 
		
    };
    mySocket.onclose = function(evt) {
    	 console.log("Conexão fechada…");
    };
    mySocket.onerror = function(evt) {
    	 console.log("Erro…");
    };
}]);


	