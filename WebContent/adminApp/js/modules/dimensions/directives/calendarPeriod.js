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
        .module('adminApp.dimension')
        .directive('calendarPeriod', calendarPeriod);

    /* @ngInject */
    function calendarPeriod($timeout, ModelService, CalendarService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'dimensions/views/calendarPeriod.html',
            scope: {
                modelId: '=',
                periodId: '=',
                periodVisId: '=',
                maxPeriodId: '=',
                minPeriodId: '=',
                onPeriodChange: '&'
            },
            transclude: true,
            replace: true,
            link: function linkFunction($scope, element) {
                $scope.editMode = false;
                $scope.isUpdating = false;

                var modelVisId = ModelService.manageModelName($scope.modelId);

                if (angular.isDefined($scope.periodId)) {
                    $scope.selectedPeriodId = $scope.periodId;
                }
                if (angular.isDefined($scope.periodToVisId)) {
                    $scope.selectedPeriodVisId = $scope.periodToVisId;
                }

                $scope.startEditing = startEditing;
                $scope.filterCalendars = filterCalendars;

                /**
                 * Changes mode of directive. Switch to edit mode, where we could edit period
                 */
                function startEditing() {
                    if (modelVisId == "") {
                        return;
                    }
                    $scope.isUpdating = false;
                    $scope.editMode = true;

                    $scope.filteredCalendars = [];
                    $scope.calendars = CalendarService.getCalendarsForModelVisId(modelVisId);
                    if ($scope.calendars.length > 0) {
                        $scope.filterCalendars($scope.calendars);
                    }
                };

                /**
                 * We divide calendars which we get from database. When directive is 'from' we take only 'dates' lower than period to (maxPeriodId),
                 * when directive is 'to' we take only 'dates' higer than period from (minPeriodId),
                 */
                function filterCalendars(calendars) {
                    if ($scope.filteredCalendars.length != 0) {
                        return;
                    }
                    if (angular.isDefined($scope.maxPeriodId)) {
                        var weFoundMaxPeriod = false;
                        angular.forEach($scope.calendars, function(calendar) {
                            if (weFoundMaxPeriod == false) {
                                $scope.filteredCalendars.push(calendar);
                            }
                            if (calendar.id == $scope.maxPeriodId) {
                                weFoundMaxPeriod = true;
                            }
                        });
                    } else if (angular.isDefined($scope.minPeriodId)) {
                        var weFoundMinPeriod = false;
                        angular.forEach($scope.calendars, function(calendar) {
                            if (calendar.id == $scope.minPeriodId) {
                                weFoundMinPeriod = true;
                            }
                            if (weFoundMinPeriod == true) {
                                $scope.filteredCalendars.push(calendar);
                            }
                        });
                    }
                };

                /**
                 * Watches and updates our selected period id and vis id, and pass them to function onPeriodChange.
                 */
                $scope.$watch("selectedPeriodId", function(newPeriodId) {
                    if (angular.isDefined(newPeriodId) && $scope.editMode) {
                        $scope.editMode = false;
                        $scope.isUpdating = true;
                        $scope.selectedPeriodVisId = CalendarService.managePeriodName(modelVisId, $scope.selectedPeriodId);
                        $scope.onPeriodChange({
                            id: $scope.selectedPeriodId,
                            visId: $scope.selectedPeriodVisId
                        });
                    }
                });

                /**
                 * Watch if any data which is related to model changed. ModelId is demanded to request for calendars (they are depended on modelVisId)
                 */
                $scope.$watch("modelId", function(newId, oldId) {
                    if (angular.isDefined(newId) && newId != oldId) {
                        modelVisId = ModelService.manageModelName($scope.modelId);
                        $scope.selectedPeriodId = $scope.periodId;
                        $scope.selectedPeriodVisId = $scope.periodToVisId;
                    }
                });

                /**
                 * Watch if calendars object (array) is changed. For example when data comes from database
                 */
                $scope.$watch("calendars", function(newCalendars, oldCalendars) {
                    if (angular.isDefined(newCalendars) && newCalendars.length > 0) {
                        $scope.filterCalendars(newCalendars);
                    }
                }, true);

                /**
                 * Watch if period id has been changed. Hides loader in directive
                 */
                $scope.$watch("periodId", function(newPeriodId) {
                    if (angular.isDefined(newPeriodId)) {
                        $scope.isUpdating = false;
                    }
                });
            }
        };
    }
})();