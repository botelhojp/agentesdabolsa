'use strict';
app.controller('DadosController', [ '$window', '$timeout', '$http', '$scope', '$route', '$rootScope', '$location', 'ENV', 'DadosService',
function($window, $timeout, $http, $scope, $route, $rootScope, $location, ENV, DadosService ) {
	
	$scope.urlAction = ENV.apiEndpoint;
	
    $scope.uploadFile = function(){
       var file = $scope.myFile;
       console.log('file is ' );
       console.dir(file);
       DadosService.uploadFileToUrl(file, ENV.apiEndpoint + '/api/serie/upload');
    };
    
    var poll = function() {
        $timeout(function() {
            DadosService.getImportStatus().then(
    				function (data) {
    					$scope.status = data;
    					console.log($scope.status.value);
    				},
    				function (error) {
    					console.log(error);			
    				}
    			);            
            poll();
        }, 10000);
    };     
	
    poll();

	
}]);