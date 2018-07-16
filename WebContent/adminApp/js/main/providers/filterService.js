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
adminApp.service('FilterService', ['$rootScope', '$filter',
    function($rootScope, $filter) {
        var self = this;

        var toFilter;
        self.doFilter = function(scope, filterFunction) {
            if (checkIfAllDataIsStored(scope, filterFunction) == false) {
                return;
            }
            if (toFilter) {
                clearTimeout(toFilter);
            }
            toFilter = setTimeout(function() {
                toFilter = null;
                if (scope.ctx.flex) {
                    var cv = scope.ctx.flex.collectionView;
                    if (cv) {
                        if (cv.filter != filterFunction) {
                            cv.filter = filterFunction;
                        } else {
                            cv.refresh();
                        }
                        scope.$apply('ctx.flex.collectionView');
                        scope.selectionChangedHandler();
                    }
                }
            }, 500);
        }

        self.buildFilterFunction = function(filters) {
            if (filters == undefined || filters.length == 0) {
                return null;
            }
            for (var i = 0; i < filters.length; i++) {
                try {
                    $filter(filters[i]);
                } catch (ex) {
                    var name = filters[i];
                    filters.splice(i, 1);
                }
            }
            return function(item) {
                var condition = true;
                for (var i = 0; i < filters.length; i++) {
                    condition = condition && $filter(filters[i])(item);
                }
                return condition;
            }
        }

        function checkIfAllDataIsStored(scope, filterFunction) {
            if (scope == null || scope.ctx == undefined || filterFunction == null) {
                return false;
            }
            return true;
        }
    }
]);