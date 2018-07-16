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
        .module('adminApp.profilesPage')
        .directive('profilesDeployment', profilesDeployment);

    /* @ngInject */
    function profilesDeployment($rootScope, $modal, $timeout, Flash, CoreCommonsService, ModelService, BudgetCycleService, ProfilesPageService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'profiles/views/profilesDeployment.html',
            scope: {
                modal: "=",
                flatForm: "=",
                isMobile: "=",
                profileId: "="
            },
            replace: true,
            controller: ['$scope',
                function($scope) {
                    initResizing();
                    var ccTree = [];
                    var expTree = [];
                    var callTree = [];
                    var dataType;
                    var flatForm = $scope.flatForm;
                    var financeCubeModels = ModelService.getFinanceCubeModels();
                    var financeCubeModel;

                    $scope.isMainDataLoaded = false;
                    $scope.visId = "";
                    $scope.description = "";
                    $scope.validation = {
                        visId: "",
                        description: "",
                        budgetCycle: "",
                        businessDimension: "",
                        messageType: "",
                        messageText: ""
                    };
                    $scope.messageTypes = [{
                        id: 0,
                        label: "System Message"
                    }, {
                        id: 1,
                        label: "Email"
                    }, {
                        id: 2,
                        label: "Email and System Message"
                    }];
                    $scope.selectedMessageType = $scope.messageTypes[0];
                    $scope.messageText = "";
                    $scope.change = change;
                    $scope.finish = finish;
                    $scope.close = close;
                    $scope.showMessage = true;
                    
                    if (financeCubeModels && flatForm) {
                        var profileVisId = $scope.flatForm.flatFormVisId.match(/-\s(.*)/);
                        if (profileVisId) {
                            $scope.visId = $scope.flatForm.flatFormVisId.match(/-\s(.*)/)[1];
                        } else {
                            $scope.visId =  $scope.flatForm.flatFormVisId;
                        }
                        $scope.description = $scope.flatForm.flatFormDescription;

                        financeCubeModel = CoreCommonsService.findElementByKey(financeCubeModels.sourceCollection, flatForm.financeCube.financeCubeId, 'financeCubeId');
                        if (financeCubeModel) {
                            BudgetCycleService.getBudgetCyclesForModel(financeCubeModel.modelId).success(function(data) {
                                $scope.isMainDataLoaded = true;
                                $scope.budgetCycles = data;
                                $scope.selectedBudgetCycle = $scope.budgetCycles[0];
                            });
                        }
                        
                    
                        if ($scope.profileId !== undefined && $scope.profileId > 0) {
                            $scope.isMainDataLoaded = false;
                            $scope.showMessage = false;
                            
                            ProfilesPageService.getDataForDeployment(financeCubeModel.modelId, $scope.profileId).then(function(data) {
                                $scope.isMainDataLoaded = true;
                                $scope.budgetCycles = data.res1;
   
                                dataType = data.res1.dataType;
                                $scope.description = data.res2.profileDescription;
                                $scope.visId = data.res2.profileVisId;
                                $scope.selectedBudgetCycle = CoreCommonsService.findElementByKey(data.res1, data.res2.budgetCycleId, 'budgetCycleId');
                                $scope.ccTreeString = data.res2.selectedElements[0].structureElementVisId;
                                $scope.expTreeString = data.res2.selectedElements[1].structureElementVisId;
                                $scope.callTreeString = data.res2.selectedElements[2].structureElementVisId;
                                $scope.dataTypeString = data.res2.dataType;
                                var account = {
                                    structureElementId: data.res2.selectedElements[0].structureElementId,
                                    structureElementVisId: data.res2.selectedElements[0].structureElementVisId,
                                    structureElementDescription: data.res2.selectedElements[0].structureElementDescription,
                                    structureId: data.res2.selectedElements[0].structureId
                                }
                                ccTree.push(account);
                                var business = {
                                    structureElementId: data.res2.selectedElements[1].structureElementId,
                                    structureElementVisId: data.res2.selectedElements[1].structureElementVisId,
                                    structureElementDescription: data.res2.selectedElements[1].structureElementDescription,
                                    structureId: data.res2.selectedElements[1].structureId
                                }
                                expTree.push(business);
            
                                var calendar = {
                                    structureElementId: data.res2.selectedElements[2].structureElementId,
                                    structureElementVisId: data.res2.selectedElements[2].structureElementVisId,
                                    structureElementDescription: data.res2.selectedElements[2].structureElementDescription,
                                    structureId: data.res2.selectedElements[2].structureId
                                }
                                callTree.push(calendar);    
                            });
                        }
                    }


                    function change(type) {
                        var modelId = financeCubeModel.modelId;
                        var financeCubeId = financeCubeModel.financeCubeId;
                        var selectedCcTree, selectedExpTree, selectedCallTree, selectedDataType;
                        var treesVisibility, modalTitle, multiple, checkboxState;
                        switch (type) {
                            case "business":
                                selectedCcTree = [];
                                treesVisibility = [true, false, false, false];
                                modalTitle = "Choose Business Dimension";
                                multiple = false;
                                // checkboxState = "false";
                                break;
                            case "account":
                                selectedExpTree = [];
                                treesVisibility = [false, true, false, false];
                                modalTitle = "Choose Account Dimension";
                                multiple = false;
                                break;
                            case "calendar":
                                selectedCallTree = [];
                                treesVisibility = [false, false, true, false];
                                modalTitle = "Choose Calendar Dimension";
                                multiple = false;
                                break;
                            case "dataType":
                                selectedDataType = "";
                                treesVisibility = [false, false, false, true];
                                modalTitle = "Choose Data Type";
                                multiple = false;
                                break;
                        }

                        var modalInstance = $modal.open({
                            template: '<trees-chooser modal-title="modalTitle" selected-cc-tree="selectedCcTree" selected-exp-tree="selectedExpTree" selected-call-tree="selectedCallTree" selected-data-type="selectedDataType" model-id="modelId"  finance-cube-id="financeCubeId" multiple="multiple" ok="ok()" trees-visibility="treesVisibility" close="close()" checkbox-state="checkboxState"></trees-chooser>',
                            windowClass: 'sub-system-modals softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.modalTitle = modalTitle;
                                    $scope.selectedCcTree = selectedCcTree;
                                    $scope.selectedExpTree = selectedExpTree;
                                    $scope.selectedCallTree = selectedCallTree;
                                    $scope.selectedDataType = selectedDataType;
                                    $scope.modelId = modelId;
                                    $scope.financeCubeId = financeCubeId;
                                    $scope.multiple = multiple;
                                    $scope.treesVisibility = treesVisibility;
                                    $scope.checkboxState = checkboxState;

                                    $scope.ok = function() {
                                        var dimensions;
                                        switch (type) {
                                            case "business":
                                                dimensions = $scope.selectedCcTree;
                                                break;
                                            case "account":
                                                dimensions = $scope.selectedExpTree;
                                                break;
                                            case "calendar":
                                                dimensions = $scope.selectedCallTree;
                                                break;
                                            case "dataType":
                                                if ($scope.selectedDataType.length > 0) {
                                                    dimensions = $scope.selectedDataType[0].structureElementVisId;
                                                } else {
                                                    dimensions = "";
                                                }
                                                break;
                                        }
                                        updateDimensions(dimensions, type);
                                        $scope.close();
                                    };

                                    $scope.close = function() {
                                        $modalInstance.dismiss('cancel');
                                    };
                                }
                            ]
                        });
                    }

                    function finish() {
                        if (isValid()) {
                            var data = {
                                flatFormId: flatForm.flatFormId,
                                identifier: $scope.visId,
                                dataType: dataType,
                                description: $scope.description,
                                financeCubeId: financeCubeModel.financeCubeId,
                                modelId: financeCubeModel.modelId,
                                budgetCycleId: $scope.selectedBudgetCycle.budgetCycleId,
                                structureElements: manageStructureElements(),
                                mailType: $scope.selectedMessageType.id,
                                mailContent: $scope.messageText,
                                mobile: $scope.isMobile
                            };
                            if ($scope.profileId > 0) {
                                data.profileId = $scope.profileId;
                                ProfilesPageService.editProfile(data).then(function(data) {
                                    if (data.success) {
                                        Flash.create('success', 'Profile ' + $scope.profileId + ' was updated');
                                        $scope.modal.dismiss('cancel');
                                    }
                                });
                            } else {
                                ProfilesPageService.deployFlatForm(data).then(function(data) {
                                    if (data.success) {
                                        Flash.create('success', 'Flat Forms were deployed with task id = ' + data.message + '.');
                                        $scope.modal.dismiss('cancel');
                                    } else if (data.error) {
                                        clearValidation();
                                        angular.forEach(data.fieldErrors, function(error) {
                                            if (error.fieldName in $scope.validation) {
                                                $scope.validation[error.fieldName] = error.fieldMessage;
                                            }
                                        });
                                    }

                                });
                            }
                        }
                    }

                    function close() {
                        $scope.modal.dismiss('cancel');
                    }

                    function updateDimensions(dimensions, type) {
                        if (!dimensions && dimensions != "") {
                            return;
                        }
                        switch (type) {
                            case "business":
                                ccTree = dimensions;
                                break;
                            case "account":
                                expTree = dimensions;
                                break;
                            case "calendar":
                                callTree = dimensions;
                                break;
                            case "dataType":
                                dataType = dimensions;
                                break;
                        }
                        manageDimensionString(dimensions, type);
                    }

                    function manageDimensionString(dimensions, type) {
                        var str = "";
                        for (var i = 0; i < dimensions.length; i++) {
                            var dimension = dimensions[i];
                            if (type === "calendar") {
                                if (dimension.parentVisId) {
                                    str += " /" + dimension.parentVisId + "/" + dimension.structureElementVisId;
                                } else {
                                    str += " /" + dimension.structureElementVisId;
                                }
                            } else {
                                str += " " + dimension.structureElementVisId;
                            }
                            if (i !== dimensions.length - 1) {
                                str += ",";
                            }
                        }
                        switch (type) {
                            case "business":
                                $scope.ccTreeString = str;
                                break;
                            case "account":
                                $scope.expTreeString = str;
                                break;
                            case "calendar":
                                $scope.callTreeString = str;
                                break;
                            case "dataType":
                                $scope.dataTypeString = dimensions;
                                break;
                        }
                    }

                    function clearValidation() {
                        // clear previous validation messages
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                    }

                    function isValid() {
                        var isValid = true;
                        isValid = isValidName() && isValid;
                        isValid = isValidDescription() && isValid;
                        isValid = isValidBydgetCycle() && isValid;
                        isValid = isValidBusinessDimensions() && isValid;
                        isValid = isValidMailType() && isValid;
                        isValid = isValidMailContent() && isValid;
                        return isValid;
                    }

                    function isValidName() {
                        var isValid = $scope.visId !== null && $scope.visId !== undefined && $scope.visId !== "";
                        if (!isValid) {
                            $scope.validation.visId = "Please supply an identifier.";
                        }
                        return isValid;
                    }

                    function isValidDescription() {
                        var isValid = $scope.description !== null && $scope.description !== undefined && $scope.description !== "";
                        if (!isValid) {
                            $scope.validation.description = "Please supply a description.";
                        }
                        return isValid;
                    }

                    function isValidBusinessDimensions() {
                        var isValid = ccTree.length > 0;
                        if (!isValid && $scope.profileId < 0) {
                            $scope.validation.businessDimension = "Please select business location deployment.";
                        }
                        if ($scope.profileId > 0) {
                            isValid = true;
                        }
                        return isValid;
                    }

                    function isValidAccountDimensions() {
                        var isValid = ccTree.length > 0;
                        if (!isValid) {
                            $scope.validation.accountDimension = "Please select account location deployment.";
                        }
                        return isValid;
                    }

                    function isValidCalendarDimensions() {
                        var isValid = ccTree.length > 0;
                        if (!isValid) {
                            $scope.validation.calendarDimension = "Please select calendar location deployment.";
                        }
                        return isValid;
                    }

                    function isValidDataTypeDimensions() {
                        var isValid = ccTree.length > 0;
                        if (!isValid) {
                            $scope.validation.dataType = "Please select Data Type.";
                        }
                        return isValid;
                    }

                    function isValidBydgetCycle() {
                        var isValid = $scope.selectedBudgetCycle !== null && $scope.selectedBudgetCycle !== undefined;
                        if (!isValid) {
                            $scope.validation.budgetCycle = "Please select budget cycle.";
                        }
                        return isValid;
                    }

                    function isValidMailType() {
                        var isValid = $scope.selectedMessageType !== null && $scope.selectedMessageType !== undefined;
                        if (!isValid) {
                            $scope.validation.messageType = "Please select message type.";
                        }
                        return isValid;
                    }

                    function isValidMailContent() {
                        var isValid = $scope.messageText !== null && $scope.messageText !== undefined && $scope.messageText !== "";
                        if (!isValid && $scope.profileId < 1) {
                            $scope.validation.messageText = "Please supply a message text.";
                        }
                        if ($scope.profileId > 0) {
                            isValid = true;
                        }
                        return isValid;
                    }

                    function manageStructureElements() {
                        var structureElements = [];
                        for (var i = 0; i < ccTree.length; i++) {
                            var ccTreeData = {
                                structureId: ccTree[i].structureId,
                                structureElementId: ccTree[i].structureElementId,
                                structureElementVisId: ccTree[i].structureElementVisId,
                                structureElementDescription: ccTree[i].structureElementDescription
                            };
                            structureElements.push(ccTreeData);
                        }
                        if (expTree.length === 0) {
                            structureElements.push(null);
                        } else {
                            var expTreeData = {
                                structureId: expTree[0].structureId,
                                structureElementId: expTree[0].structureElementId,
                                structureElementVisId: expTree[0].structureElementVisId,
                                structureElementDescription: expTree[0].structureElementDescription
                            };
                            structureElements.push(expTreeData);
                        }
                        if (callTree.length === 0) {
                            structureElements.push(null);
                        } else {
                            var callTreeData = {
                                structureId: callTree[0].structureId,
                                structureElementId: callTree[0].structureElementId,
                                structureElementDescription: callTree[0].structureElementDescription,
                                structureElementVisId: (function() {
                                    if (callTree[0].parentVisId) {
                                        return callTree[0].parentVisId + "/" + callTree[0].structureElementVisId;
                                    } else {
                                        return callTree[0].structureElementVisId;
                                    }
                                })()
                            };
                            structureElements.push(callTreeData);
                        }
                        return structureElements;
                    }

                    function initResizing() {
                        $scope.cookieName = "adminPanel_modal_profilesDeployment";
                        // parameters to resize modal
                        var modalDialog = $(".profiles-deployment").closest(".modal-dialog");
                        var elementToResize = null;
                        var additionalElementsToCalcResize = [];
                        // try to resize and move modal based on the cookie
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                        $timeout(function() { // timeout is necessary to pass asynchro
                            CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize);
                        }, 0);
                    }


                    $scope.$watch('selectedMessageType', function() {
                        $scope.validation.messageType = "";
                    });

                    $scope.$watch('selectedBudgetCycle', function() {
                        $scope.validation.budgetCycle = "";
                    });

                    $scope.$watch('ccTreeString', function() {
                        $scope.validation.businessDimension = "";
                    });

                    $scope.$watch('expTreeString', function() {
                        $scope.validation.accountDimension = "";
                    });

                    $scope.$watch('callTreeString', function() {
                        $scope.validation.calendarDimension = "";
                    });

                    $scope.$watch('dataTypeString', function() {
                        $scope.validation.dataType = "";
                    });
                }
            ]
        };
    }
})();