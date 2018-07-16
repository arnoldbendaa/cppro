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
        .directive('externalSystemDetails', externalSystemDetails);

    /* @ngInject */
    function externalSystemDetails($rootScope, $modal, ExternalSystemService, $timeout, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'externalSystems/views/externalSystemDetails.html',
            replace: true,
            scope: {
                id: '=',
                close: '&'
            },
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".external-system-details").closest(".modal-dialog");
                    var elementToResize = $(".properties-table");
                    var additionalElementsToCalcResize = [$("ul.nav"), $(".getToCalc1"), $(".getToCalc2"), 30];
                    $scope.cookieName = "adminPanel_modal_externalSystem";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                    }, 0);
                    $scope.dataLoaded = false;
                    $scope.selectedProperty = {};
                    $scope.externalSystem = ExternalSystemService.getExternalSystemDetails($scope.id);
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.save = save;
                    $scope.editSubSystem = editSubSystem;
                    $scope.changeSelectedProperty = changeSelectedProperty;
                    $scope.createProperty = createProperty;
                    $scope.deleteProperty = deleteProperty;

                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }

                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }

                    function save() {
                        $rootScope.$broadcast("modal:blockAllOperations");
                        ExternalSystemService.save($scope.externalSystem);
                    }

                    /**
                     * Invokes during clicking in directive with calendar periods.
                     * Function was created to control if user click in period from or period to
                     */
                    function editSubSystem(subSystem) {
                        var modalInstance = $modal.open({
                            template: '<sub-system-configuration sub-system="subSystem" ok="ok()" close="close()"></sub-system-configuration>',
                            windowClass: 'sub-system-modals softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.subSystem = {};
                                    angular.copy(subSystem, $scope.subSystem);

                                    $scope.ok = function() {
                                        angular.copy($scope.subSystem, subSystem);
                                        $modalInstance.close($scope.subSystem);
                                    };

                                    $scope.close = function() {
                                        $modalInstance.dismiss('cancel');
                                    };
                                }
                            ]
                        });

                        modalInstance.result.then(function(subSystem) {
                            console.log("subSystemConfiguration: " + subSystem);
                        }, function() {
                            console.log('Modal dismissed at: ' + new Date());
                        });
                    }

                    /**
                     * Watch if receive data from backend.
                     */
                    $scope.$watchCollection('externalSystem', function(externalSystem) {
                        if (angular.isDefined(externalSystem) && angular.isDefined(externalSystem.versionNum)) {
                            $scope.dataLoaded = true;
                        }
                    }, true);

                    /****************************************** PROPERTIES **************************************************************/
                    function changeSelectedProperty(property) {
                        if ($scope.selectedProperty != property) {
                            $scope.selectedProperty = property;
                        } else {
                            $scope.selectedProperty = {};
                        }
                    }

                    function createProperty(newName, newValue) {
                        var newElement = {
                            name: newName,
                            value: newValue
                        };
                        $scope.externalSystem.properties.push(newElement);
                        $scope.selectedProperty = {};
                    }

                    function deleteProperty(indexDeleteElement) {
                        $scope.selectedProperty = {};
                        $scope.externalSystem.properties.splice(indexDeleteElement, 1);
                    }

                    /****************************************** VALIDATION **************************************************************/
                    $scope.validation = {
                        externalSystemVisId: "",
                        externalSystemDescription: "",
                        location: "",
                        connectorClass: ""
                    };

                    /**
                     * Clear whole validation object which is sended to template. All validation messages are disappeared
                     */
                    var clearValidation = function() {
                        // clear previous validation messages
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                    };

                    /****************************************** RESPONSES **************************************************************/
                    $scope.$on("BudgetCycleService:externalSystemSaveError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        clearValidation();
                        // set new messages
                        angular.forEach(data.fieldErrors, function(error) {
                            if (error.fieldName in $scope.validation) {
                                $scope.validation[error.fieldName] = error.fieldMessage;
                            }
                        });
                    });

                    $scope.$on("ExternalSystemService:externalSystemSaved", function(event, data) {
                        if (data.success) {
                            $scope.close();
                        }
                    });
                }
            ]
        };
    }
})();