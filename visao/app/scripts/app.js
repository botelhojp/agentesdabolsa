'use strict';

var app = angular.module('gpapp', ['ngAnimate', 'ngCookies', 'ngResource',
    'ngRoute', 'ngSanitize', 'ngAnimate', 'ngTouch', 'ui.bootstrap',
    'ngGrid', 'swaggerUi', 'notification', 'config', 'angular-loading-bar'
]);
app.config(['$routeProvider', '$httpProvider', 'USER_ROLES', 'cfpLoadingBarProvider',
    function($routeProvider, $httpProvider, USER_ROLES, cfpLoadingBarProvider) {

        cfpLoadingBarProvider.spinnerTemplate = '<div></div>';

        $routeProvider.when('/agente', {
            templateUrl: 'views/agente.html',
            data: {
                requires: [USER_ROLES.GUEST]
            },
        }).when('/bolsa', {
			templateUrl : 'views/bolsa.html',
			data : {
				requires : [ USER_ROLES.GUEST ]
			},
		}).when('/usuario', {
			templateUrl : 'views/usuario.html',
			data : {
				requires : [ USER_ROLES.GUEST ]
			},
		}).otherwise({
            redirectTo: '/',
            data: {
                requires: [USER_ROLES.GUEST]
            },
        });
    }
]);

app.config([
    '$httpProvider',
    function($httpProvider) {

        $httpProvider.interceptors.push([
            '$q',
            '$location',
            '$rootScope',
            'AppService',
            'ENV',
            function($q, $location, $rootScope, AppService, ENV) {
                return {
                    'request': function(config) {
                        var token = AppService.getToken();
                        if (token) {
                            config.headers.Authorization = 'Token ' + token;
                        }
                        if (config.url.indexOf("api") !== -1) {
                            config.url = ENV.apiEndpoint + config.url;
                        }
                        return config || $q.when(config);
                    },

                    'response': function(response) {
                        var token = response.headers('Set-Token');
                        if (token) {
                            AppService.setToken(token);
                        }
                        return response;

                    }
                };
            }
        ]);

        $httpProvider.interceptors.push(['$injector', function($injector) {
            return $injector.get('AuthInterceptor');
        }]);

    }
]);

app
    .run([
        '$rootScope',
        '$location',
        '$window',
        'AUTH_EVENTS',
        'USER_ROLES',
        'AppService',
        'DataService',
        'ENV',
        function($rootScope, $location, $window, AUTH_EVENTS,
            USER_ROLES, AppService, DataService, ENV) {
            $rootScope
                .$on(
                    '$routeChangeStart',
                    function(event, next) {

                       // console.log($location.path());

                        if (next.redirectTo !== '/') {

                            var requires = next.data.requires;

                            if (requires.indexOf(USER_ROLES.GUEST) == 0) {

                            } else {
                                if (!AppService.getToken()) {
                                    AppService.setTryUrl($location.path());
                                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                                }
                            }
                        }
                    });

            $rootScope.$on(AUTH_EVENTS.notAuthenticated, function() {
                $rootScope.currentUser = null;
                AppService.removeToken();
                DataService.clear();
                $location.path("/login");
            });

            $rootScope.$on(AUTH_EVENTS.loginFailed, function() {
                AppService.removeToken();
                $location.path("/login");
            });

            $rootScope.$on(AUTH_EVENTS.logoutSuccess, function() {
                AppService.removeToken();
                location.reload();
            });

            $rootScope.$on(AUTH_EVENTS.loginSuccess, function() {
                $location.path(AppService.getTryUrl());
            });

        }
    ]);

app.constant('USER_ROLES', {
    ADMIN: 'ADMIN',
    GUEST: 'GUEST'
});


app.constant('NAV_DATA', {
    ID_CESSIONARIO: 'nav_data_id_ces',
    ID_RESPONSAVEL: 'nav_data_id_res',
    TAB_CESSIONARIO: 'nav_data_tab_cesssionnario'
});

app.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
});

app.constant('APP_EVENTS', {
    offline: 'app-events-offline'
});


app.factory('AuthInterceptor', [ '$rootScope', '$q', 'AUTH_EVENTS',
'APP_EVENTS', function($rootScope, $q, AUTH_EVENTS, APP_EVENTS) {
	return {
		responseError : function(response) {
			$rootScope.$broadcast({
				'-1' : APP_EVENTS.offline,
				0 : APP_EVENTS.offline,
				404 : APP_EVENTS.offline,
				503 : APP_EVENTS.offline,
				401 : AUTH_EVENTS.notAuthenticated,
				403 : AUTH_EVENTS.notAuthorized,
				419 : AUTH_EVENTS.sessionTimeout,
				440 : AUTH_EVENTS.sessionTimeout
			}[response.status], response);
			return $q.reject(response);
		}
	};

} ]);