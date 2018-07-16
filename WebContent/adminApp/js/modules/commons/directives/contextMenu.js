/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
(function() {
    'use strict';

    angular
        .module('adminApp.commons')
        .directive('contextMenu', contextMenu);

    /* @ngInject */
    function contextMenu() {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'commons/views/contextMenu.html',
            scope: {
                onShow: "&"
            },
            controller: ['$scope', '$rootScope', '$document', 'ContextMenuService',
                function($scope, $rootScope, $document, contextMenuService) {

                    $scope.position = {
                        x: 0,
                        y: 0
                    };

                    $scope.ctx = contextMenuService.getCtx();
                    var $contextMenu = $("#context-menu");

                    function addEvents() {
                        $document.bind('click.test', function(event) {
                            hide();
                            $scope.$apply();
                            removeEvents();
                        });
                    }

                    function removeEvents() {
                        $document.unbind('click.test');
                    }

                    function show() {
                        var windowHeight = window.innerHeight;
                        var height = $contextMenu.height();

                        if ($scope.position.y + height > windowHeight) {
                            $contextMenu.css({
                                display: "block",
                                left: $scope.position.x,
                                top: $scope.position.y - height
                            });
                        } else {
                            $contextMenu.css({
                                display: "block",
                                left: $scope.position.x,
                                top: $scope.position.y
                            });
                        }
                        addEvents();
                    }

                    function hide() {
                        removeEvents();
                        $contextMenu.css({
                            display: "none"
                        });
                    }

                    $scope.doAction = function(action) {
                        $rootScope.$broadcast("SubMenuController:" + action);
                    };

                    $scope.$watch("ctx.element", function() {
                        if ($scope.ctx.element == null) {
                            return;
                        }
                        var element = $scope.ctx.element;
                        element.bind('contextmenu', function(e) {
                            e.preventDefault();
                            $scope.position.x = e.pageX;
                            $scope.position.y = e.pageY;
                            $scope.$apply();

                            var ifShow = $scope.onShow({
                                event: e
                            });
                            if (ifShow) {
                                show();
                            }
                        });
                    });
                }
            ]
        };
    }
})();