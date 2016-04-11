'use strict';

app.service('DadosService', ['$window', '$http', 'NAV_DATA', function($window, $http, NAV_DATA) {

	var services = {};

	services.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
     
        $http.post(uploadUrl, fd, {
           transformRequest: angular.identity,
           headers: {'Content-Type': undefined}
        })
     
        .success(function(){
        })
     
        .error(function(){
        });
     }
    
	return services;
}]);