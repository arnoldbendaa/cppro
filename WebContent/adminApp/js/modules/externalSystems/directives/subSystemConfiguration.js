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
        .module('adminApp.externalSystem')
        .directive('subSystemConfiguration', subSystemConfiguration);

    /* @ngInject */
    function subSystemConfiguration($rootScope, $timeout, ExternalSystemService, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'externalSystems/views/subSystemConfiguration.html',
            replace: true,
            scope: {
                subSystem: '=',
                ok: '&',
                close: '&'
            },
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".sub-system-configuration").closest(".modal-dialog");
                    var elementToResize = null;
                    var additionalElementsToCalcResize = [];
                    $scope.cookieName = "adminPanel_modal_subSystemConfiguration";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);

                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                    }, 0);
                    $scope.itemAvailable = {};
                    $scope.itemSelected = {};
                    $scope.dataLoaded = false;
                    if ($scope.subSystem.subSystemConfiguration === null) {
                        $scope.subSystem.subSystemConfiguration = ExternalSystemService.getSubSystemConfiguration($scope.subSystem.subSystemId);
                    }
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.add = add;
                    $scope.remove = remove;
                    $scope.up = up;
                    $scope.down = down;

                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }

                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }

                    function add() {
                        $scope.subSystem.subSystemConfiguration.selectedColumns.push($scope.itemAvailable);
                        $scope.subSystem.subSystemConfiguration.availableColumns.splice($scope.subSystem.subSystemConfiguration.availableColumns.indexOf($scope.itemAvailable), 1);
                    }

                    function remove() {
                        $scope.subSystem.subSystemConfiguration.availableColumns.push($scope.itemSelected);
                        $scope.subSystem.subSystemConfiguration.selectedColumns.splice($scope.subSystem.subSystemConfiguration.selectedColumns.indexOf($scope.itemSelected), 1);
                    }

                    function up() {
                        var itemSelectedIndex = $scope.subSystem.subSystemConfiguration.selectedColumns.indexOf($scope.itemSelected);
                        swap(itemSelectedIndex, itemSelectedIndex - 1);
                    }

                    function down() {
                        var itemSelectedIndex = $scope.subSystem.subSystemConfiguration.selectedColumns.indexOf($scope.itemSelected);
                        swap(itemSelectedIndex, itemSelectedIndex + 1);
                    }

                    /**
                     * Watch if opened tidy task changed. If additional data (sql's, commands) comes from database
                     */
                    $scope.$watch('subSystem', function(subSystem) {
                        if (angular.isDefined(subSystem.subSystemConfiguration) && angular.isDefined(subSystem.subSystemConfiguration.availableColumns) && angular.isDefined(subSystem.subSystemConfiguration.selectedColumns)) {
                            $scope.dataLoaded = true;
                            $scope.itemAvailable = subSystem.subSystemConfiguration.availableColumns.length > 0 ? subSystem.subSystemConfiguration.availableColumns[0] : null;
                            $scope.itemSelected = subSystem.subSystemConfiguration.selectedColumns.length > 0 ? subSystem.subSystemConfiguration.selectedColumns[0] : null;
                        }
                    }, true);

                    var swap = function(index1, index2) {
                        if (index1 >= 0 && index2 >= 0 && index1 < $scope.subSystem.subSystemConfiguration.selectedColumns.length && index2 < $scope.subSystem.subSystemConfiguration.selectedColumns.length) {
                            var temp = $scope.subSystem.subSystemConfiguration.selectedColumns[index2];
                            $scope.subSystem.subSystemConfiguration.selectedColumns[index2] = $scope.subSystem.subSystemConfiguration.selectedColumns[index1];
                            $scope.subSystem.subSystemConfiguration.selectedColumns[index1] = temp;
                        }
                    };
                }
            ]
        };
    }
})();