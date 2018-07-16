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
        .module('adminApp.model')
        .directive('modelDetails', modelDetails);

    /* @ngInject */
    function modelDetails($rootScope, Flash, ModelService, $timeout, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'models/views/modelDetails.html',
            scope: {
                modelId: '=model'
            },
            replace: true,
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".model-details").closest(".modal-dialog");
                    var elementToResize = $(".properties-table");
                    var additionalElementsToCalcResize = [$("ul.nav"), $(".getToCalc1"), $(".getToCalc2"), 30];
                    $scope.cookieName = "adminPanel_modal_model";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    $scope.isError = false;
                    $scope.messageError = "";
                    $scope.model = null;
                    $scope.availableReferences = true;
                    $scope.availableReferencesProblemMessage = "";
                    $scope.hierarchyList = [];
                    $scope.choosedAccountDimension = null;
                    $scope.choosedCalendarDimension = null;
                    $scope.choosedHierarchy = null;
                    $scope.choosedBusinessDimension = null;
                    $scope.selectedProperty = {};
                    $scope.firstCharge = true;
                    $scope.validation = {
                        identifier: '',
                        description: '',
                        properties: '',
                        references: ''
                    };
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.changeAccountDimension = changeAccountDimension;
                    $scope.changeCalendarDimension = changeCalendarDimension;
                    $scope.changeHierarchy = changeHierarchy;
                    $scope.changeBusinessDimension = changeBusinessDimension;
                    $scope.changeSelectedProperty = changeSelectedProperty;
                    $scope.createProperty = createProperty;
                    $scope.deleteProperty = deleteProperty;
                    $scope.close = close;
                    $scope.save = save;

                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                    }, 0);

                    if ($scope.modelId != -1) {
                        $scope.isDataLoaded = false;
                        $scope.model = ModelService.getModelDetailsFromDatabase($scope.modelId);
                        $scope.operator = "save";
                    } else {
                        // create Model
                        // create new empty Model object
                        $scope.model = ModelService.getModelDetailsFromDatabase(-1);
                        $scope.operator = "create";
                    }

                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }

                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }

                    function changeAccountDimension(accountDimension) {
                        $scope.choosedAccountDimension = accountDimension;
                        $scope.model.account = accountDimension;
                    }

                    function changeCalendarDimension(calendarDimension) {
                        $scope.choosedCalendarDimension = calendarDimension;
                        $scope.model.calendar = calendarDimension;
                    }

                    function changeHierarchy(hierarchy) {
                        $scope.choosedHierarchy = hierarchy;
                        $scope.model.budgetHierarchy = hierarchy;
                    }

                    function changeBusinessDimension(businessDimension) {
                        $scope.choosedBusinessDimension = businessDimension;
                        $scope.model.business = [businessDimension];
                    }

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
                        $scope.model.properties.push(newElement);
                        $scope.selectedProperty = {};
                    }

                    function deleteProperty(indeksDeleteElement) {
                        $scope.selectedProperty = {};
                        $scope.model.properties.splice(indeksDeleteElement, 1);
                    }

                    function close() {
                        $rootScope.$broadcast('ModelDetails:close');
                    }

                    /**
                     * Try to update changed Model
                     */
                    function save() {
                        $rootScope.$broadcast("modal:blockAllOperations");
                        ModelService.saveModel($scope.model);
                    }

                    /**
                     * Condition: visible Hierarchies schoold have diemensionId choosed Dimension's.
                     * Firstly choosed Hierarchies fit to selected dimension and push that to hierarchyList. HierarchyList is content to select "Budget Hierarchy".
                     * Secondly choosed Hierarchy, what is focus.
                     */
                    var visibleHierarchyForDimension = function() {
                        $scope.hierarchyList = [];
                        for (var i = 0; i < $scope.model.availableBudgetHierarchy.length; i++) {
                            if ($scope.model.availableBudgetHierarchy[i].dimensionId == $scope.model.business[0].dimensionId) {
                                $scope.hierarchyList.push($scope.model.availableBudgetHierarchy[i])
                            }
                        }
                        if ($scope.model.budgetHierarchy != null) {
                            $scope.choosedHierarchy = CoreCommonsService.findElementByKey($scope.hierarchyList, $scope.model.budgetHierarchy.hierarchyId, "hierarchyId");
                        }
                    }

                    /**
                     * Update: On error after http->success() with error=true or http->error()
                     * @param  {[type]} event
                     * @param  {Object} data  [ResponseMessage]
                     */
                    $scope.$on("ModelDetails:modelsSaveError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        // error from method http->success() and from field "error"
                        // clear previous validation messages
                        Flash.create('danger', data.message);
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                        // set new messages
                        angular.forEach(data.fieldErrors, function(error) {
                            if (error.fieldName in $scope.validation) {
                                $scope.validation[error.fieldName] = error.fieldMessage;
                            }
                        });
                    });

                    $scope.$on("ModelDetails:close", function(event, data) {
                        if (typeof data != "undefined") {
                            if (data.success) {
                                if (data.method == "POST") {
                                    var operation = "created";
                                } else if (data.method == "PUT") {
                                    var operation = "updated";
                                }
                                Flash.create('success', "Model (" + $scope.model.modelVisId + ") is " + operation + ".");
                            }
                        }
                    });

                    /**
                     * When choosedBusinessDimension is changed then change too range of hierarchies.
                     */
                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.choosedBusinessDimension
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if newValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                                if ($scope.choosedBusinessDimension != null) {
                                    visibleHierarchyForDimension();
                                }
                            }
                        }
                    );

                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.model
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if newValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                                $scope.model = newValue;
                                $scope.accountList = $scope.model.availableAccounts || [];
                                $scope.calendarList = $scope.model.availableCalendars || [];
                                if ($scope.firstCharge == true) {
                                    //first charge data
                                    if ($scope.model.business != null) {
                                        $scope.accountList.push($scope.model.account);
                                        $scope.calendarList.push($scope.model.calendar);
                                        $scope.businessList = $scope.model.business.concat($scope.model.availableBusiness);
                                        $scope.model.budgetHierarchy.dimensionId = $scope.model.business[0].dimensionId;
                                    } else {
                                        $scope.businessList = $scope.model.availableBusiness;
                                    }
                                    $scope.firstCharge = false;
                                }
                                if ($scope.operator != "create") {
                                    //prepare data for object from database
                                    $scope.choosedAccountDimension = $scope.model.account;
                                    $scope.choosedCalendarDimension = $scope.model.calendar;
                                    if ($scope.model.business.length != 0) {
                                        $scope.choosedBusinessDimension = $scope.model.business[0];
                                    } else {
                                        $scope.choosedBusinessDimension = {};
                                    }
                                } else {
                                    //prepare data for object created
                                    $scope.model.modelId = -1;
                                    //if created is imposiable
                                    if ($scope.model.availableAccounts.length == 0) {
                                        $scope.availableReferences = false;
                                        $scope.availableReferencesProblemMessage = "No available account dimension.";
                                    } else if ($scope.model.availableCalendars.length == 0) {
                                        $scope.availableReferences = false;
                                        $scope.availableReferencesProblemMessage = "No available calendar dimension.";
                                    } else if ($scope.model.availableBusiness.length == 0) {
                                        $scope.availableReferences = false;
                                        $scope.availableReferencesProblemMessage = "No available business dimension.";
                                    } else if ($scope.model.availableBudgetHierarchy.length == 0) {
                                        $scope.availableReferences = false;
                                        $scope.availableReferencesProblemMessage = "No available hierarchy dimension.";
                                    }
                                }
                                $scope.isDataLoaded = true;
                            }
                        }
                    );
                }
            ]
        };
    }
})();