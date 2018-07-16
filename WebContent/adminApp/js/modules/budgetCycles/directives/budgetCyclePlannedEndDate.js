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
        .directive('budgetCyclePlannedEndDate', budgetCyclePlannedEndDate);
        
    /* @ngInject */
    function budgetCyclePlannedEndDate($modal) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'budgetCycles/views/budgetCyclePlannedEndDate.html',
            scope: {
                modelId: '=',
                plannedEndDate: '=',
                levelDates: '='
            },
            replace: true,
            link: function linkFunction($scope, element, attr, ctrl) {
                var oldEndDate;
                $scope.format = 'dd/MMM/yy';
                $scope.isPlannedEndDateChanged = false;
                $scope.openPlannedEndDate = openPlannedEndDate;
                $scope.openLevels = openLevels;

                /**
                 * Blocks propagation of events
                 */
                var stopPropagation = function(event) {
                    event.preventDefault();
                    event.stopPropagation();
                };

                /**
                 * Opens date picker modal
                 */
                function openPlannedEndDate($event) {
                    stopPropagation($event);
                    $scope.openedPlannedEndDate = true;
                }

                /**
                 * Open structure level end dates modal
                 */
                function openLevels($event) {
                    var levelDates = $scope.levelDates;
                    var isPlannedEndDateChanged = $scope.isPlannedEndDateChanged;
                    var plannedEndDate = $scope.plannedEndDate;
                    var modelId = $scope.modelId;

                    var modalInstance = $modal.open({
                        template: '<budget-cycle-structure-level-end-dates model-id="modelId" level-dates="levelDates" planned-end-date="plannedEndDate" is-planned-end-date-changed="isPlannedEndDateChanged" close="close()"></budget-cycle-structure-level-end-dates>',
                        windowClass: 'budget-cycle-structure-modals softpro-modals',
                        backdrop: 'static',
                        size: 'lg',
                        controller: ['$scope', '$modalInstance',
                            function($scope, $modalInstance) {
                                $scope.modelId = modelId;
                                $scope.levelDates = levelDates;
                                $scope.plannedEndDate = plannedEndDate;
                                $scope.isPlannedEndDateChanged = isPlannedEndDateChanged;

                                $scope.close = function() {
                                    $modalInstance.close();
                                };
                            }
                        ]
                    });
                }

                $scope.$watch('plannedEndDate', function() {
                    if ($scope.plannedEndDate != null && angular.isDefined($scope.plannedEndDate)) {
                        if (angular.isUndefined(oldEndDate)) {
                            oldEndDate = $scope.plannedEndDate;
                        }
                        if (oldEndDate !== $scope.plannedEndDate) {
                            $scope.isPlannedEndDateChanged = true;
                        } else {
                            $scope.isPlannedEndDateChanged = false;
                        }
                    }
                })
            }
        };
    }
    
    angular
    .module('adminApp.budgetCycle')
    .directive('dateFormat', dateFormat);
    
/* @ngInject */
    function dateFormat(dateFilter) {
        
        return {
            require: 'ngModel',
            link: function(scope, element, attrs, ngModel) {
                ngModel.$parsers.push(function(viewValue) {
                    return dateFilter(viewValue, 'yyyy-MM-dd');
                    //return viewValue.getTime(); // timestamp
                    //ngModel.$setValidity('plannedEndDate', false); //set validity if date is correct
                });
            }
        };
    }
})();