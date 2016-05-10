'use strict';

app.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);

app.directive('uiLinhabar', ['$rootScope', '$anchorScroll', function($rootScope, $anchorScroll) {
    return {
        restrict: 'AC',
        template: '<span class="bar"></span>',
        link: function(scope, el, attrs) {
            el.addClass('linhabar hide');

            scope.$on('$routeChangeStart', function(e) {
                $anchorScroll();
                el.removeClass('hide').addClass('active');
            });

            scope.$on('$routeChangeSuccess', function(event, toState, toParams, fromState) {
                event.targetScope.$watch('$viewContentLoaded', function() {
                    el.addClass('hide').removeClass('active');
                })
            });

            scope.$on("loading-started", function(e) {
                el.removeClass('hide').addClass('active');
            });

            scope.$on("loading-complete", function(e) {
                el.addClass('hide').removeClass('active');
            });
        }
    }
}]);

app.directive('backButton', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function() {
                history.back();
                scope.$apply();
            });
        }
    };
});

app.directive('alerts', function() {
    return {
        restrict: 'E',
        templateUrl: 'partials/alerts.html'
    };
});

app.directive("autofill", function() {
    return {
        require: "ngModel",
        link: function(scope, element, attrs, ngModel) {
            scope.$on("autofill:update", function() {
                ngModel.$setViewValue(element.val());
            });
        }
    };
});

app.directive("appVersion", ["version", function(version) {
    return function(scope, elm, attrs) {
        elm.text(version);
    };
}]);

app.directive("hasRoles", ["AuthService", function(AuthService) {
    return {
        restrict: "A",
        link: function(scope, element, attributes) {

            var paramRoles = attributes.hasRoles.split(",");

            if (!AuthService.isAuthorized(paramRoles)) {
                element.remove();
            }
        }
    };
}]);

app.directive("isLogged", ["AuthService", function(AuthService) {
    return {
        restrict: "A",
        link: function(scope, element, attributes) {
            if(!AuthService.isAuthenticated()){
                element.remove();
            }
        }
    };
}]);

app.directive("confirmButton", function($timeout) {
    return {
        restrict: 'A',
        scope: {
            actionOK: '&confirmAction',
            actionCancel: '&cancelAction'
        },
        link: function(scope, element, attrs) {
            var buttonId, html, message, nope, title, yep;
            buttonId = Math.floor(Math.random() * 10000000000);
            attrs.buttonId = buttonId;
            message = attrs.message || "Tem certeza?";
            yep = attrs.yes || "Sim";
            nope = attrs.no || "Não";
            title = attrs.title || "Confirmação";

            element.bind('click', function(e) {

                var box = bootbox.dialog({
                    message: message,
                    title: title,
                    buttons: {
                        success: {
                            label: yep,
                            className: "btn-success",
                            callback: function() {
                                $timeout(function() {
                                    scope.$apply(scope.actionOK);
                                });
                            }
                        },
                        danger: {
                            label: nope,
                            className: "btn-danger",
                            callback: function() {
                                scope.$apply(scope.actionCancel);
                            }
                        }
                    }
                });

            });
        }
    };
});

app.directive('validationMsg', ['ValidationService', function(ValidationService) {
    return {
        restrict: 'E',
        scope: {
            propriedade: '@'
        },
        template: "<div class='error text-danger' ng-show='msg'><small class='error' >{{msg}}</small></div>",
        controller: function($scope) {
            $scope.$watch(function() {
                return ValidationService.validation[$scope.propriedade];
            },
                    function(msg) {
                        $scope.msg = msg;
                    }
            );
        }
    };
}]);

app.directive("maxLength", ['$compile', 'AlertService', function($compile, AlertService) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elem, attrs, ctrl) {
            attrs.$set("ngTrim", "false");
            var maxlength = parseInt(attrs.maxLength, 10);
            ctrl.$parsers.push(function(value) {
                if (value !== undefined && value.length !== undefined) {
                    if (value.length > maxlength) {
                        AlertService.addWithTimeout('warning', 'O valor máximo de caracteres (' + maxlength + ') para esse campo já foi alcançado');
                        value = value.substr(0, maxlength);
                        ctrl.$setViewValue(value);
                        ctrl.$render();
                    }
                }
                return value;
            });
        }
    };
}]);

app.directive("hasRolesDisable", ["AuthService", function(AuthService) {
    return {
        restrict: "A",
        link: function(scope, element, attributes) {

            var paramRoles = attributes.hasRolesDisable.split(",");

            if (!AuthService.isAuthorized(paramRoles)) {
                angular.forEach(element.find('input, select, textarea, button, a'), function(node){
                    var ele = angular.element(node);
                    ele.attr("disabled", "true");
                });
            }
        }
    };
}]);

app.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});


app.directive('modal', function(){
        return {
            template: '<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"><div class="modal-dialog modal-sm"><div class="modal-content" ng-transclude><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button><h4 class="modal-title" id="myModalLabel">Modal title</h4></div></div></div></div>',
            restrict: 'E',
            transclude: true,
            replace:true,
            scope:{visible:'=', onShown:'&', onHide:'&'},
            link:function postLink(scope, element, attrs){

                $(element).modal({
                    show: false,
                    keyboard: attrs.keyboard,
                    backdrop: attrs.backdrop
                });

                scope.$watch(function(){return scope.visible;}, function(value){
                    if(value == true){

                        $(element).modal('show');
                    }else{
                        $(element).modal('hide');
                    }
                });

                $(element).on('shown.bs.modal', function(){
                  scope.$apply(function(){
                    scope.$parent[attrs.visible] = true;
                  });
                });

                $(element).on('shown.bs.modal', function(){
                  scope.$apply(function(){
                      scope.onShown({});
                  });
                });

                $(element).on('hidden.bs.modal', function(){
                  scope.$apply(function(){
                    scope.$parent[attrs.visible] = false;
                  });
                });

                $(element).on('hidden.bs.modal', function(){
                  scope.$apply(function(){
                      scope.onHide({});
                  });
                });
            }
        };
    }
);

app.directive('modalHeader', function(){
    return {
        template:'<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button><h4 class="modal-title">{{title}}</h4></div>',
        replace:true,
        restrict: 'E',
        scope: {title:'@'}
    };
});

app.directive('modalBody', function(){
    return {
        template:'<div class="modal-body" ng-transclude></div>',
        replace:true,
        restrict: 'E',
        transclude: true
    };
});

app.directive('modalFooter', function(){
    return {
        template:'<div class="modal-footer" ng-transclude></div>',
        replace:true,
        restrict: 'E',
        transclude: true
    };
});

function ModalController($scope){

    $scope.showModal = false;

    $scope.modalHide = function(){
        $scope.showModal = false;
    }

    $scope.modalShow = function(){
        $scope.showModal = true;
    }

    $scope.modalOneShown = function(){
     //   console.log('model one shown');
    }

    $scope.modalOneHide = function(){
     //   console.log('model one hidden');
    }
}
