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
        .module('adminApp.systemPropertiesPage')
        .directive('systemPropertyDetails', systemPropertyDetails);

    /* @ngInject */
    function systemPropertyDetails($rootScope, SystemPropertiesPageService, $timeout, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'systemProperties/views/systemPropertyDetails.html',
            scope: {
                systemPropertyId: '=systemProperty'
            },
            replace: true,
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".system-property-details").closest(".modal-dialog");
                    var elementToResize = null; // $(".");
                    var additionalElementsToCalcResize = [];
                    $scope.cookieName = "adminPanel_modal_systemProperty";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                    }, 0);
                    $scope.isError = false;
                    $scope.messageError = "";
                    $scope.systemProperty = null;
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.close = close;
                    $scope.save = save;

                    if ($scope.systemProperty === null) {
                        $scope.isDataLoaded = false;
                        $scope.systemProperty = SystemPropertiesPageService.getSystemPropertyDetailsFromDatabase($scope.systemPropertyId);
                    } else {
                        $scope.isDataLoaded = true;
                    }

                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }

                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }

                    /**
                     * Close System Property details window
                     */
                    function close() {
                        $rootScope.$broadcast('SystemPropertyDetails:close');
                    }

                    /**
                     * Try to update changed Property
                     */
                    function save() {
                        $rootScope.$broadcast("modal:blockAllOperations");
                        SystemPropertiesPageService.updateSystemPropertyInDatabase($scope.systemProperty);
                    }

                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.systemProperty
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if newValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                                $scope.systemProperty = newValue;
                                $scope.isDataLoaded = true;
                            }
                        }
                    );

                    /**
                     * Update: On error after http->success() with error=true or http->error()
                     * @param  {[type]} event
                     * @param  {Object} data  [ResponseMessage]
                     */
                    $scope.$on("SystemPropertiesPageService:systemPropertiesUpdatedError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        $scope.isError = true;
                        $scope.messageError = data.message;
                    });
                }
            ]
        };
    }
})();