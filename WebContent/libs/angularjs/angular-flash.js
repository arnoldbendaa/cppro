(function() {
    'use strict';
    var app = angular.module('flash', []);

    app.run(function($rootScope) {
        // initialize variables
        $rootScope.flash = {};
        $rootScope.flash.text = '';
        $rootScope.flash.type = '';
        $rootScope.flash.timeout = 5000;
        $rootScope.hasFlash = false;
    });

    // Directive for compiling dynamic html
    app.directive('dynamic', function($compile) {
        return {
            restrict: 'A',
            replace: true,
            link: function(scope, ele, attrs) {
                scope.$watch(attrs.dynamic, function(html) {
                    ele.html(html);
                    $compile(ele.contents())(scope);
                });
            }
        };
    });

    // Directive for closing the flash message
    app.directive('closeFlash', function($compile, Flash) {
        return {
            link: function(scope, ele, attrs) {
                ele.on('click', function() {
                    Flash.dismiss();
                });
            }
        };
    });

    // Create flashMessage directive
    app.directive('flashMessage', function($compile, $rootScope) {
        return {
            restrict: 'A',
            template: '<div role="alert" ng-show="hasFlash" class="alert {{flash.addClass}} alert-{{flash.type}} alert-dismissible ng-hide alertIn alertOut "> <span dynamic="flash.text"></span> <button type="button" class="close" close-flash><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button> </div>',
            link: function(scope, ele, attrs) {
                // get timeout value from directive attribute and set to flash timeout
                $rootScope.flash.timeout = parseInt(attrs.flashMessage, 10);
            }
        };
    });

    app.factory('Flash', ['$rootScope', '$timeout',
        function($rootScope, $timeout) {

            var dataFactory = {},
                num = 0,
                timeOut,
                canceled = false;


            // Create flash message
            dataFactory.create = function(type, text, addClass) {
                $rootScope.flash.type = type;
                $rootScope.flash.text = text;
                $rootScope.flash.addClass = addClass;
                canceled = false;
                $timeout(function() {
                    $rootScope.hasFlash = true;
                }, 100);
                num++;
                timeOut = $timeout(function() {
                    if (canceled === false) {
                        if (num == 1) {
                            $timeout(function() {
                                $rootScope.hasFlash = false;
                            });
                        }
                        num--;
                    }
                }, $rootScope.flash.timeout);
            };

            // Cancel flashmessage timeout function
            dataFactory.pause = function() {
                num = 0;
                $timeout.cancel(timeOut);
                canceled = true;
            };

            // Dismiss flash message
            dataFactory.dismiss = function() {
                $timeout.cancel(timeOut);
                canceled = true;
                num = 0;
                $timeout(function() {
                    $rootScope.hasFlash = false;
                });
            };
            return dataFactory;
        }
    ]);
}());