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
        .module('adminApp.budgetCycle')
        .directive('budgetCycleStructureLevelEndDates', budgetCycleStructureLevelEndDates);

    /* @ngInject */
    function budgetCycleStructureLevelEndDates($timeout, CoreCommonsService, BudgetCycleService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'budgetCycles/views/budgetCycleStructureLevelEndDates.html',
            scope: {
                modelId: '=',
                plannedEndDate: '=',
                isPlannedEndDateChanged: '=',
                levelDates: '=',
                close: '&'
            },
            replace: true,
            link: function linkFunction($scope, element, attr, ctrl) {
                $scope.editedLevelDates = angular.copy($scope.levelDates);
                $scope.validation = "";
                $scope.editedDate = new Date();
                $scope.currentIndex = -1;
                $scope.save = save;
                $scope.changeDepth = changeDepth;
                $scope.resizedColumn = resizedColumn;
                $scope.sortedColumn = sortedColumn;
                
                if ($scope.levelDates.length !== 0 && $scope.isPlannedEndDateChanged == false) {
                    $scope.isStructureLevelDatesLoaded = true;
                } else {
                    $scope.isStructureLevelDatesLoaded = false;
                    var data = {
                        modelId: $scope.modelId,
                        plannedEndDate: $scope.plannedEndDate
                    }
                    BudgetCycleService.getStructureLevelDatesForModel(data).success(function(data) {
                        $scope.isStructureLevelDatesLoaded = true;
                        angular.copy(data, $scope.editedLevelDates);
                    })
                }
               
                /**
                 * Check if edited level dates are listed in reverse chronology
                 */
                function isValid() {
                    var lastDate = $scope.editedLevelDates[0];
                    for (var i = 1; i < $scope.editedLevelDates.length; i++) {
                        if (lastDate >= $scope.editedLevelDates[i]) {
                            lastDate = $scope.editedLevelDates[i];
                        } else {
                            return false;
                        }
                    }
                    return true;
                }

                /**
                 * Saves structure level end dates
                 */
                function save() {
                    if (isValid()) {
                        $scope.levelDates.length = 0;
                        angular.forEach($scope.editedLevelDates, function(levelDate, index) {
                            $scope.levelDates.push(levelDate);
                        });
                        $scope.close();
                    } else {
                        $scope.validation = "Dates you have set are not valid. Date should be listed in reverse chronology.";
                    }
                }

                /**
                 * Handle changing depth in editor
                 */
                function changeDepth(index, param) {
                    if (index === 0) {
                        $scope.currentIndex = -1;
                        $scope.editedDate = new Date($scope.editedLevelDates[0]);
                        return;
                    }
                    $scope.currentIndex = index;
                    $scope.editedDate = new Date(param);
                }

                $scope.$watch('editedDate', function() {
                    $scope.validation = "";
                    if ($scope.currentIndex !== -1) {
                        $scope.editedLevelDates[$scope.currentIndex] = $scope.editedDate.getTime();
                    }
                });

                // parameters to resize modal
                var modalDialog = $(".budget-cycle-structure-level-end-dates").closest(".modal-dialog");
                var elementToResize = null; // $(".");
                var additionalElementsToCalcResize = [];
                $scope.cookieName = "adminPanel_modal_budgetCycle_levelDates";
                // try to resize and move modal based on the cookie
                CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);

                function resizedColumn(sender, args) {
                    CoreCommonsService.resizedColumn(args, $scope.cookieName);
                }

                function sortedColumn(sender, args) {
                    CoreCommonsService.sortedColumn(args, $scope.cookieName);
                }
                $timeout(function() { // timeout is necessary to pass asynchro
                    CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                }, 0);
            }
        };
    }
})();