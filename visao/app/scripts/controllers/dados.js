'use strict';
app.controller('DadosController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'ENV', 'DadosService',
function($window, $http, $scope, $route, $rootScope, $location, ENV, DadosService ) {
	
	$scope.urlAction = ENV.apiEndpoint;
	
    $scope.uploadFile = function(){
       var file = $scope.myFile;
       console.log('file is ' );
       console.dir(file);
       DadosService.uploadFileToUrl(file, ENV.apiEndpoint + '/api/serie/upload');
    };
	
}]);