'use strict';

app.service('AppService', ['$window', '$rootScope', '$cookies', 'ENV', 'NAV_DATA', function ($window, $rootScope, $cookies, ENV, NAV_DATA) {

        var tokenKey = "agdb_oauth2";

        var service = {};
        
        var tryUrl = "/";

        service.getToken = function () {
    		var token = $window.localStorage.getItem(tokenKey);
            if (token && token !== undefined && token !== null && token !== "null") {
                return token;
            } else {
                $window.localStorage.removeItem(tokenKey);
                $rootScope.currentUser = null;
            }
            return null;
        };

        //Token
        service.setToken = function (token) {
            $window.localStorage.setItem(tokenKey, token);
        };

        service.clear = function () {
            $window.localStorage.removeItem(tokenKey);
            $window.localStorage.removeItem(NAV_DATA.CURRENT_USER);
            
            var cookies = document.cookie.split(";");
            for (var i = 0; i < cookies.length; i++) {
            	var cookie = cookies[i];
            	var eqPos = cookie.indexOf("=");
            	var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
            	$cookies.remove(name);            	
            }
        };
        
        //tentativa de acessa url
        service.getTryUrl = function () {
        	return tryUrl;
        };
        
        service.setTryUrl = function (value) {
        	tryUrl = value;
        };
        return service;
    }]);

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        