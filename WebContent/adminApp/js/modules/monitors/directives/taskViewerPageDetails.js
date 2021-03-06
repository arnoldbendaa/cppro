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
        .module('adminApp.taskViewerPage')
        .directive('taskViewerDetails', taskViewerDetails);

    /* @ngInject */
    function taskViewerDetails($rootScope, TaskViewerPageService, $timeout, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'monitors/views/taskViewerDetails.html',
            scope: {
                taskId: '=id',
                userName: '=user'
            },
            replace: true,
            controller: ['$scope',
                function($scope) {

                    $timeout(function() {
                        // parameters to resize modal
                        var modalDialog = $(".task-view-details").closest(".modal-dialog");
                        var elementToResize = $(".log-panel");
                        var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3")];
                        $scope.cookieName = "adminPanel_modal_taskViewer";
                        // try to resize and move modal based on the cookie
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                        $scope.resizedColumn = function(sender, args) {
                            CoreCommonsService.resizedColumn(args, $scope.cookieName);
                        };
                        $scope.sortedColumn = function(sender, args) {
                            CoreCommonsService.sortedColumn(args, $scope.cookieName);
                        };
                        $timeout(function() { // timeout is necessary to pass asynchro
                            CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                        }, 0);
                    }, 0);

                    $scope.task = null;
                    $scope.userName = $scope.userName;

                    if ($scope.task === null) {
                        $scope.isDataLoaded = false;
                        $scope.task = TaskViewerPageService.getTaskDetailsFromDatabase($scope.taskId);
                    } else {
                        $scope.isDataLoaded = true;
                    }

                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.task;
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if oldValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                                $scope.task = newValue;
                                $scope.isDataLoaded = true;
                            }
                        }
                    );

                    /**
                     * Close Task details window
                     */
                    $scope.close = function() {
                        $rootScope.$broadcast('TaskViewerDetails:close');
                    };
                }
            ]
        };
    }
})();