'use strict';

app.service('AppService', ['$window', '$rootScope', '$cookies', 'ENV', function ($window, $rootScope, $cookies, ENV) {

        var tokenKey = "gp-token";
        var tokenGuia = ENV.ssoCookie;
        

        var service = {};
        
        var tryUrl = "/";

        service.getToken = function () {
        	if (service.guiaIsOn()){
        		var name = ENV.ssoCookie + "=";
        		var ca = document.cookie.split(';');        		
        		for (var i = 0; i < ca.length; i++) {
        			
        			var c = ca[i];
        			while (c.charAt(0) == ' ')
        				c = c.substring(1);
        			if (c.indexOf(name) == 0)
        				return c.substring(name.length, c.length);
        		}
        		return;
        	}else{
        		var token = $window.localStorage.getItem(tokenKey);
                if (token && token !== undefined && token !== null && token !== "null") {
                    return token;
                } else {
                    $window.localStorage.removeItem(tokenKey);
                    $rootScope.currentUser = null;
                }
        	}            
            return null;
        };

        //Token
        service.setToken = function (token) {
            $window.localStorage.setItem(tokenKey, token);
        };

        service.removeToken = function () {
            $window.localStorage.removeItem(tokenKey);
            
            //limpar cookies do guia
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
        
        service.guiaIsOn = function(){
        	return ($window.localStorage.getItem("guia") === "on");
        };

        return service;
    }]);

app.service('Session', function () {

    this.create = function (userId, userRole) {
        this.userId = userId;
        this.userRole = userRole;
    };

    this.destroy = function () {
        this.userId = null;
        this.userRole = null;
    };

    return this;
});