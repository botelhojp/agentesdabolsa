'use strict';
app.controller('LogController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'DataService', 'NAV_DATA', 'AUTH_EVENTS', 'ENV',
function($window, $http, $scope, $route, $rootScope, $location, DataService, NAV_DATA, AUTH_EVENTS, ENV ) {
	
	
    var mySocket = new WebSocket(ENV.wsEndpoint);
    mySocket.onopen = function(evt) {
    	 //console.log("Abre conexão…");
    };
    
    mySocket.onmessage = function(evt) {
    	$window.document.getElementById("messageArea").value += evt.data + "\n";
    };
    mySocket.onclose = function(evt) {
    	 console.log("Conexão fechada…");
    };
    mySocket.onerror = function(evt) {
    	 console.log("Erro…");
    };
}]);


	