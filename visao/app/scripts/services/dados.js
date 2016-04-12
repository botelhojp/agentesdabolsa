'use strict';

app.service('DadosService', ['$window', '$q','$http', 'NAV_DATA', function($window, $q, $http, NAV_DATA) {

	var services = {};
	
	services.getImportStatus = function() {
		var deferred = $q.defer();
		$http({
			url : '/api/serie/status',
			method : "GET",
			headers : {
				'Content-Type' : 'application/json;charset=utf8'
			}
		}).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status) {
			deferred.reject([ data, status ]);
		});
		return deferred.promise;
	};

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