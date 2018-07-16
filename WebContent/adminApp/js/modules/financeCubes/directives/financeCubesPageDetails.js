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
        .module('adminApp.financeCubesPage')
        .directive('financeCubeDetails', financeCubeDetails);

    /* @ngInject */
    function financeCubeDetails($rootScope, Flash, FinanceCubesPageService, DataTypesPageService, $timeout, CoreCommonsService, $modal) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'financeCubes/views/financeCubeDetails.html',
            scope: {
                financeCubeId: '=financeCube',
                modelId: '=model'
            },
            replace: true,
            controller: ['$scope',
                function($scope) {

                    $timeout(function() {
                        // parameters to resize modal
                        var modalDialog = $(".finance-cube-details").closest(".modal-dialog");
                        var elementToResize = $(".fc-panel");
                        var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $("ul.nav"), $(".getToCalc3"), 30];
                        $scope.cookieName = "adminPanel_modal_financeCube";
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
                    
                    $scope.isError = false;
                    $scope.messageError = "";
                    $scope.financeCube = null;
                    $scope.selectedDataTypeId = null;
                    $scope.dataTypes = {};
                    $scope.separate = [];
                    var choosedDataType;
                    $scope.validation = {
                        change: '',
                        identifier: '',
                        description: '',
                        dataTypesSize: '',
                        dataTypesBudgetTransfer: '',
                        dataTypesMapped: ''
                    };
                    $scope.addFinanceCubeDataType = addFinanceCubeDataType;
                    self.deleteSelectDataType = deleteSelectDataType;
                    $scope.deleteFinanceCubeDataType = deleteFinanceCubeDataType;
                    self.separateDuplicatedDataTypes = separateDuplicatedDataTypes;
                    $scope.openDataTypeForFinanceCubeChooser = openDataTypeForFinanceCubeChooser;
                    $scope.close = close;
                    $scope.save = save;
                     
                    if ($scope.financeCubeId != -1) {
                        $scope.isDataLoaded = false;
                        $scope.isModelChoosed = true;
                        //FinanceCubesPageService.getFinanceCubeDetailsFromDatabase();
                        $scope.financeCube = FinanceCubesPageService.getFinanceCubeDetailsFromDatabase($scope.modelId, $scope.financeCubeId);
                        $scope.operator = "save";
                    } else {
                        // create DataType
                        // create new empty Data type object
                        $scope.isModelChoosed = false;
                        $scope.financeCube = FinanceCubesPageService.createEmptyFinanceCube();
                        $scope.operator = "create";
                    }

                    /**
                     * Prepare lockedByTaskId to display
                     */
                    var manageLockedByTaskId = function(lockedByTaskId) {
                        if (lockedByTaskId !== -1) {
                            return lockedByTaskId;
                        }
                        return "";
                    }

                    /**
                     * Try to add choosedDataType to table "Data Types" and if chosedDataType allows configurable roll up add to table "Rolls Up Rules"
                     */
                    function addFinanceCubeDataType(choosedDataTypes) {
                        if (choosedDataTypes.length > 0) {
                            for(var i = 0; i < choosedDataTypes.length; i++) {
                                deleteSelectDataType(choosedDataTypes[i].dataTypeId);
                                var dataTypes = $scope.financeCube.dataTypes;
                                dataTypes.push(choosedDataTypes[i]);
                                if (choosedDataTypes[i].subType == 4 && choosedDataTypes[i].measureClass != null && choosedDataTypes[i].measureClass == 1) {
                                    var addedRollUpRuleLine = {};
                                    addedRollUpRuleLine.dataType = choosedDataTypes[i];
                                    addedRollUpRuleLine.dataType.cubeLastUpdated = undefined;
                                    addedRollUpRuleLine.rollUpRules = [3];
                                    addedRollUpRuleLine.rollUpRules[0] = {};
                                    addedRollUpRuleLine.rollUpRules[1] = {};
                                    addedRollUpRuleLine.rollUpRules[2] = {};
                                    addedRollUpRuleLine.rollUpRules[0].dimension = $scope.financeCube.dimensions[0];
                                    addedRollUpRuleLine.rollUpRules[1].dimension = $scope.financeCube.dimensions[1];
                                    addedRollUpRuleLine.rollUpRules[2].dimension = $scope.financeCube.dimensions[2];
                                    addedRollUpRuleLine.rollUpRules[0].rollUp = false;
                                    addedRollUpRuleLine.rollUpRules[1].rollUp = false;
                                    addedRollUpRuleLine.rollUpRules[2].rollUp = false;
                                    $scope.financeCube.rollUpRuleLines.push(addedRollUpRuleLine);
                                }
                            }
                        }
                    }

                    /**
                     * Delete select DataType from the selectDataTypes used by form-control
                     */
                    function deleteSelectDataType(dataTypeId) {
                        var selectDataTypes = $scope.separate;

                        for (var i = 0; i < selectDataTypes.length; i++) {
                            if (selectDataTypes[i].dataTypeId === dataTypeId) {
                                selectDataTypes.splice(i, 1);
                            }
                        }
                    }

                    /**
                     * Check if DataType can by deleted
                     */
                    var isPossibleDeleteDataType = function(dataType) {
                        if (dataType.subType == 2 || dataType.subType == 1) {
                            $scope.isError = true;
                            $scope.validation.dataTypesBudgetTransfer = "Budget transfer data types may not be removed";
                        }
                        var mappedDataTypes = $scope.financeCube.mappedDataTypes;
                        for (var i = mappedDataTypes.length - 1; i >= 0; i--) {
                            if (dataType.dataTypeVisId == mappedDataTypes[i].dataTypeVisId) {
                                $scope.isError = true;
                                $scope.validation.dataTypesMapped = "Data Type " + dataType.dataTypeVisId + " is used in a mapped model.";
                            }
                        }
                        if ($scope.isError == true) {
                            Flash.create('danger', "Error during removing data type operation");
                            $scope.isError = false;
                            return false;
                        }
                        return true;
                    }

                    /**
                     * Remove line from "Roll Up Rules" table
                     */
                    var removeFromRollUpRuleLines = function(dataTypeId) {
                        var rollUpRuleLinesDataTypes = $scope.financeCube.rollUpRuleLines;

                        for (var i = 0; i < rollUpRuleLinesDataTypes.length; i++) {
                            if (rollUpRuleLinesDataTypes[i].dataType.dataTypeId === dataTypeId) {
                                rollUpRuleLinesDataTypes.splice(i, 1);
                            }
                        }
                        $scope.financeCube.rollUpRuleLines = rollUpRuleLinesDataTypes;
                    }

                    /**
                     * Delete DataType from FinanceCube
                     */
                    function deleteFinanceCubeDataType(dataTypeId) {
                        var selectDataTypes = $scope.separate;
                        var financeCubeDataTypes = $scope.financeCube.dataTypes;

                        for (var i = 0; i < financeCubeDataTypes.length; i++) {
                            if (financeCubeDataTypes[i].dataTypeId === dataTypeId) {
                                if (isPossibleDeleteDataType(financeCubeDataTypes[i])) {
                                    if (financeCubeDataTypes[i].subType == 4 && financeCubeDataTypes[i].measureClass != null && financeCubeDataTypes[i].measureClass == 1) {
                                        removeFromRollUpRuleLines(financeCubeDataTypes[i].dataTypeId);
                                    }
                                    selectDataTypes.push(financeCubeDataTypes[i]);
                                    financeCubeDataTypes.splice(i, 1);
                                }
                            }
                        }
                    }

                    /**
                     * Rewrite Data Types what is not has by FinanceCube to selectDataTypes used by form-control
                     */
                    function separateDuplicatedDataTypes() {
                        var selectDataTypes = $scope.dataTypes.sourceCollection;
                        var i,j;
                        var financeCubeDataTypes = $scope.financeCube.dataTypes;
                        var ifHavedByFinanceCube = false;
                        var lenght = 0;
                        for (i = 0; i < selectDataTypes.length; i++) {
                            for (j = 0; j < financeCubeDataTypes.length; j++) {

                                if (financeCubeDataTypes[j].dataTypeId === selectDataTypes[i].dataTypeId) {
                                    ifHavedByFinanceCube = true;
                                }

                            }
                            if (ifHavedByFinanceCube == false) {
                                $scope.separate[lenght] = selectDataTypes[i];
                                lenght++;
                            }
                            ifHavedByFinanceCube = false;
                        }
                    }
                    
                    function openDataTypeForFinanceCubeChooser() {
                        var separate = $scope.separate;
                        var modalInstance = $modal.open({
                            //templateUrl: $BASE_TEMPLATE_PATH + 'financeCubes/dataTypeChooserModal.html',
                            template: '<chooser-data-type-modal available="availableDataTypes" modal="modal"></chooser-data-type-modal>',
                            windowClass: 'finance-cube-modals softpro-modals',
                            backdrop: 'static',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.availableDataTypes = separate;
                                    $scope.modal = $modalInstance;
                                }
                            ]
                        });
                        modalInstance.result.then(function(selectedDataTypes) {
                            if (selectedDataTypes !== undefined) {
                                $scope.addFinanceCubeDataType(selectedDataTypes);
                            }
                        }, function() {

                        });
                    }

                    /**
                     * Check if Finance Cube update not broke conditions
                     */
                    var validateFinanceCube = function() {
                        $scope.isError = false;
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                        if (!$scope.financeCube.insideChangeManagement && $scope.financeCube.changeManagementOutstanding) {
                            $scope.isError = true;
                            $scope.validation.change = "Change managemt requests are pending for this model.";
                        } else if ($scope.financeCube.dataTypes.length == 0) {
                            $scope.isError = true;
                            $scope.validation.dataTypesSize = "FinaceCube must contain at least one datatype";
                        } else if ($scope.financeCube.financeCubeVisId.length > 100) {
                            $scope.isError = true;
                            $scope.validation.identifier = "Length (" + $scope.financeCube.financeCubeVisId.length + ") of VisId must not exceed 100 on a FinanceCube";
                        } else if ($scope.financeCube.financeCubeVisId.length == 0) {
                            $scope.isError = true;
                            $scope.validation.identifier = "Length (" + $scope.financeCube.financeCubeVisId.length + ") of VisId can't be 0";
                        } else if ($scope.financeCube.financeCubeDescription.length > 128) {
                            $scope.isError = true;
                            $scope.validation.description = "Length (" + $scope.financeCube.financeCubeDescription.length + ") of Description must not exceed 128 on a FinanceCube";
                        }

                        if ($scope.isError == true) {
                            Flash.create('danger', "Error during " + $scope.operator + " operation");
                        } else {
                            openSubmitingConfirmBox();
                        }
                    }

                    function close() {
                        $rootScope.$broadcast('FinanceCubeDetails:close');
                    }

                    /**
                     * Try to update changed Finance Cube
                     */
                    function save() {
                        validateFinanceCube();
                    }

                    $scope.$on("FinanceCubesPageService:dimensionsForFinanceCube", function(event, data) {
                        $scope.financeCube.dimensions = data;
                    });

                    /**
                     * Update: On error after http->success() with error=true or http->error()
                     * @param  {[type]} event
                     * @param  {Object} data  [ResponseMessage]
                     */
                    $scope.$on("FinanceCubePageService:financeCubesUpdatedError", function(event, data) {
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

                    $scope.$on("FinanceCubeDetails:close", function(event, data) {
                        if (typeof data != "undefined") {
                            if (data.success) {
                                if (data.method == "POST") {
                                    var operation = "created";
                                } else if (data.method == "PUT") {
                                    var operation = "updated";
                                }
                                Flash.create('success', "Finance Cube (" + $scope.financeCube.financeCubeVisId + ") is " + operation + ".");
                            }
                        }
                    });

                    // cancel dragging and resizing for ModelChooser
                    $scope.$watch("isModelChoosed", function() {
                        var modal = $(".modal.finance-cube-modals .modal-dialog");
                        if (!$scope.isModelChoosed) {
                            modal.addClass("modalReset");
                        } else {
                            modal.removeClass("modalReset");
                        }
                    });

                    /**
                     * This function watch if modelId is choosed. If modelId is choosed start create new Finance Cube.
                     */
                    $scope.$watch("financeCube.model.modelId", function(newModelId) {
                        if (angular.isDefined(newModelId)) {
                            $scope.isDataLoaded = true;
                            $scope.isModelChoosed = $scope.financeCube.model.modelId && $scope.financeCube.model.modelId !== -1;
                            $scope.financeCube.lockedByTaskId = manageLockedByTaskId($scope.financeCube.lockedByTaskId);
                            //$scope.dataTypes = DataTypesPageService.getDataTypes();
                            if (newModelId != -1) {
                                FinanceCubesPageService.getDimensionsForNewFinanceCubeFromDatabase(newModelId);
                            }
                        }
                    });

                    /**
                     * This function watch if modelId is choosed. If modelId is choosed start create new Finance Cube.
                     */
                    $scope.$watch("financeCube.dimensions", function(dimensions) {
                        if (angular.isDefined(dimensions)) {
                            $scope.financeCube.dimensions = dimensions;
                        }
                    });

                    $scope.$watch(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.dataTypes.sourceCollection
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if newValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                                separateDuplicatedDataTypes();
                            }
                        }
                    );

                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.financeCube
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            if (newValue !== oldValue) {
                                $scope.financeCube.lockedByTaskId = manageLockedByTaskId($scope.financeCube.lockedByTaskId);
                                $scope.dataTypes = DataTypesPageService.getDataTypes();
                                $scope.isDataLoaded = true;
                            }
                        }
                    );

                    /**
                     * Display warning message.
                     */
                    var openSubmitingConfirmBox = function() {
                        swal({
                            title: "Changes will be saved.",
                            text: "Do you wish to submit the change management request immediately?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#d9534f",
                            confirmButtonText: "Yes",
                            cancelButtonText: "No",
                        }, function(isConfirm) {
                            $rootScope.$broadcast("modal:blockAllOperations");
                            if (isConfirm) {
                                $scope.financeCube.submitChangeManagement = true;
                                FinanceCubesPageService.save($scope.financeCube);
                            } else {
                                $scope.financeCube.submitChangeManagement = false;
                                FinanceCubesPageService.save($scope.financeCube);
                            }
                        });
                    };
                }
            ]
        };
    }
})();