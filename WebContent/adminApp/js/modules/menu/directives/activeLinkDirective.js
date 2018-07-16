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
        .module('adminApp.menu')
        .directive('activeLink', activeLink);

    /* @ngInject */
    function activeLink($location) {

        return {
            restrict: 'A',
            link: function(scope, element, attrs, controller) {
                var clazz = attrs.activeLink;
                var path = attrs.href;
                path = "/" + path.substring(1); //hack because path does bot return including hashbang
                scope.location = $location;
                scope.$watch('location.path()', function(newPath) {
                    if (path === newPath) {
                        element.addClass(clazz);
                        // i.e. get ["security","responsibility-areas","users-models"] from /security/responsibility-areas/users-models/
                        var splitedNewPath = newPath.slice(1, -1).split("/");
                        if (splitedNewPath.length == 2) {
                            // i.e. /dimensions/business/ - expand Dimensions (set scope.dimensionsExpand = true)
                            scope[camelCase(splitedNewPath[0]) + "Expand"] = true;
                        } else if (splitedNewPath.length == 3) {
                            // i.e. /security/responsibility-areas/models-users/ - expand Security and Responsibility Areas
                            // (set scope.securityExpand = true and scope.responsibilityAreasExpand = true)
                            scope[camelCase(splitedNewPath[0]) + "Expand"] = true;
                            scope[camelCase(splitedNewPath[1]) + "Expand"] = true;
                        } else if (splitedNewPath.length == 4) {
                            // i.e. /first/second/third/fourth/ - to use in the future, beacuse now we don't have that menu
                            scope[camelCase(splitedNewPath[0]) + "Expand"] = true;
                            scope[camelCase(splitedNewPath[1]) + "Expand"] = true;
                            scope[camelCase(splitedNewPath[2]) + "Expand"] = true;
                        }
                    } else {
                        element.removeClass(clazz);
                    }
                });

                //http://stackoverflow.com/questions/10425287/convert-string-to-camelcase-with-regular-expression
                function camelCase(input) {
                    return input.toLowerCase().replace(/-(.)/g, function(match, group1) {
                        return group1.toUpperCase();
                    });
                }
            }

        };

    }
})();