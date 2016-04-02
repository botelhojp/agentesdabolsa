'use strict';

app.service('UserService', ['$http', '$window', 'DataService', 'NAV_DATA', function($http, $window, DataService, NAV_DATA) {

	var services = {};
	services.load = function (token) {
        console.log("load.. " + token);
        $http.get('https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=' + token).then(function(response, status) {
            DataService.set(NAV_DATA.CURRENT_USER_NAME, response.data.name);
            DataService.set(NAV_DATA.CURRENT_USER_EMAIL, response.data.email);
            DataService.set(NAV_DATA.CURRENT_USER_FOTO, response.data.picture);
        });
    };
	return services;
}]);