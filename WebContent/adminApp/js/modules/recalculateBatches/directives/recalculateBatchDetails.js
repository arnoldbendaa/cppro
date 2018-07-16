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
        .module('adminApp.recalculateBatch')
        .directive('recalculateBatchDetails', recalculateBatchDetails);

    /* @ngInject */
    function recalculateBatchDetails($timeout, $rootScope, UserModelSecurityService, CoreCommonsService, RecalculateBatchService, ModelService, BudgetCycleService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'recalculateBatches/views/recalculateBatchDetails.html',
            scope: {
                id: '=',
                close: '&'
            },
            replace: true,
            link: function linkFunction($scope) {

                // var recalculateBatchDB = {};
                // parameters to resize modal
                var modalDialog = $(".recalculate-batch-details").closest(".modal-dialog");
                var elementToResize = $(".elementToResize");
                var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3"), 40];
                $scope.cookieName = "adminPanel_modal_recalculateBatch";
                // try to resize and move modal based on the cookie
                CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                $timeout(function() { // timeout is necessary to pass asynchro
                    CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                }, 0);

                // TODO walidacja dla wybranego modelu i budget cycle

                $scope.resizedColumn = resizedColumn;
                $scope.sortedColumn = sortedColumn;
                $scope.manageModel = manageModel;
                $scope.changeModel = changeModel;
                $scope.manageBudgetCycles = manageBudgetCycles;
                $scope.manageProfiles = manageProfiles;
                $scope.changeBudgetCycle = changeBudgetCycle;
                $scope.save = save;
                $scope.selectNode = selectNode;
                $scope.deselectNode = deselectNode;
                $scope.toggleProfile = toggleProfile;
                $scope.isValid = isValid;
                $scope.treeProcess = {};

                function resizedColumn(sender, args) {
                    CoreCommonsService.resizedColumn(args, $scope.cookieName);
                }

                function sortedColumn(sender, args) {
                    CoreCommonsService.sortedColumn(args, $scope.cookieName);
                }

                /**
                 * Manage proper modelId. After setting proper modelId request for budget cycles is sended.
                 */
                function manageModel() {
                    var areModelLoaded = $scope.models.sourceCollection.length > 0;
                    if (areModelLoaded && $scope.isMainDataLoaded === true && $scope.isModelDataLoaded === false) {
                        if ($scope.recalculateBatch.model.modelId !== -1) {
                            $scope.isModelDataLoaded = true;
                            $scope.isBudgetCycleDataLoaded = false;
                            $scope.treeProcess = UserModelSecurityService.getTree($scope.recalculateBatch.model.modelId);
                            $scope.manageBudgetCycles($scope.recalculateBatch.model.modelId);
                        } else if ($scope.recalculateBatch.model.modelId == -1) {
                            $scope.recalculateBatch.model.modelId = $scope.models.sourceCollection[0].modelId;
                        }
                    }
                }

                /**
                 * Handle event when model is changed. Ask database for data depends on newly choosed model
                 */
                function changeModel() {
                    $scope.isBudgetCycleDataLoaded = false;
                    $scope.treeProcess = UserModelSecurityService.getTree($scope.recalculateBatch.model.modelId);
                    $scope.recalculateBatch.profiles = []; // clear profiles for recalculate batch //look for recalculateBatchDB maybe will useful in future
                    $scope.validation.profiles = '';
                    $scope.recalculateBatch.responsibilityAreas = []; // clear responsibilityAreas for recalculate batch
                    $scope.validation.responsibilityAreas = '';
                    $scope.manageBudgetCycles($scope.recalculateBatch.model.modelId);
                }

                /**
                 * Manage budget cycles for model. Get list of budget cycles. If we opened specific (concrete, with id) recalculate batch
                 * in budgetCycleId we have id from database. After changing model this id is clear.
                 */
                function manageBudgetCycles(modelId) {
                    if (modelId == -1) {
                        return;
                    }
                    if ($scope.isModelDataLoaded && $scope.isBudgetCycleDataLoaded === false) {
                        $scope.isProfilesDataLoaded = false;
                        BudgetCycleService.getBudgetCyclesForModel(modelId).success(function(response) {
                            $scope.isBudgetCycleDataLoaded = true;
                            $scope.budgetCycles = response;
                            var budgetCycleId = $scope.recalculateBatch.budgetCycleId;
                            var budgetCycles = $scope.budgetCycles;
                            if (budgetCycleId !== -1) {
                                if (budgetCycles.length > 0 && CoreCommonsService.findElementByKey(budgetCycles, budgetCycleId, 'budgetCycleId') === null) {
                                    $scope.recalculateBatch.budgetCycleId = budgetCycles[0].budgetCycleId;
                                }
                            } else if ($scope.budgetCycles.length > 0) {
                                $scope.recalculateBatch.budgetCycleId = budgetCycles[0].budgetCycleId;
                            }

                            $scope.isProfilesDataLoaded = false;
                            $scope.manageProfiles(modelId, $scope.recalculateBatch.budgetCycleId);
                        });
                    }
                }

                /**
                 * Manage profiles for selecte model and budget cycle
                 * @param  {[type]} modelId       [current selected model]
                 * @param  {[type]} budgetCycleId [current selected budget cycle]
                 */
                function manageProfiles(modelId, budgetCycleId) {
                    if ($scope.isBudgetCycleDataLoaded === true && $scope.isProfilesDataLoaded === false) {
                        RecalculateBatchService.getProfilesFromDatabase($scope.id, modelId, budgetCycleId).success(function(response) {
                            $scope.isProfilesDataLoaded = true;
                            $scope.recalculateBatch.profiles = response;
                        });
                    }
                }

                /**
                 * Handle event when budget cycle is changed. Ask database for data depends on newly choosed budget cycle
                 */
                function changeBudgetCycle() {
                    $scope.isProfilesDataLoaded = false;
                    $scope.manageProfiles($scope.recalculateBatch.model.modelId, $scope.recalculateBatch.budgetCycleId);
                }

                /**
                 * Save (update or insert) recalculate batch
                 */
                function save() {
                    
                    for (var i=0;i<$scope.models.sourceCollection.length;i++){
                        if ($scope.models.sourceCollection[i].modelId == $scope.recalculateBatch.model.modelId){
                            $scope.recalculateBatch.model.modelVisId = $scope.models.sourceCollection[i].modelVisId;
                            $scope.recalculateBatch.model.modelDescription = $scope.models.sourceCollection[i].modelDescription;
                            break;
                        }
                    }
                       
                    if ($scope.isValid($scope.recalculateBatch)) {
                        $rootScope.$broadcast("modal:blockAllOperations");
                        RecalculateBatchService.save($scope.recalculateBatch);
                    }
                }

                /**
                 * Handle if node in jsTree is selected.
                 * @param  {[type]} e    [event object]
                 * @param  {[type]} data [data with jsTree object]
                 */
                function selectNode(e, data) {
                    manageSelectedResponsibilityArea(data.instance);
                }

                /**
                 * Handle if node in jsTree is deselected.
                 * @param  {[type]} e    [event object]
                 * @param  {[type]} data [data with jsTree object]
                 */
                function deselectNode(e, data) {
                    manageSelectedResponsibilityArea(data.instance);
                }

                /**
                 * Manage array of selected responsibility areas from jsTree library and pass it to current batch reacalculate (which is already in scope)
                 * @param  {[type]} responsibilityAreas [all selected nodes from jsTree]
                 * @param  {[type]} jsTree              [data with jsTree object]
                 */
                var manageSelectedResponsibilityArea = function(jsTree) {
                    var selectedResponsibilityAreas = []; // $scope.responsibilityArea;
                    var listOfSelectedNodes = jsTree.get_selected(true);
                    for (var i = 0; i < listOfSelectedNodes.length; i++) {
                        var structureElementId = listOfSelectedNodes[i].original.structureElementId;
                        structureElementId = parseInt(structureElementId);
                        var isNodeLeaf = jsTree.is_leaf(listOfSelectedNodes[i]);
                        if (selectedResponsibilityAreas.indexOf(structureElementId) == -1 && isNodeLeaf)
                            selectedResponsibilityAreas.push(structureElementId);
                    }
                    $scope.recalculateBatch.responsibilityAreas = selectedResponsibilityAreas;
                    if (selectedResponsibilityAreas.length > 0) {
                        $scope.validation.responsibilityAreas = '';
                    }
                };

                $scope.allProfileSelected = false;
                $scope.isMainDataLoaded = false;
                $scope.isModelDataLoaded = false;
                $scope.isBudgetCycleDataLoaded = false;
                $scope.isProfilesDataLoaded = false;

                // $scope.id = -1;
                // ############################## DETAILS ######################################

                if ($scope.id == -1) {
                    $scope.isMainDataLoaded = true;
                    $scope.recalculateBatch = RecalculateBatchService.createEmptyRecalculateBatch();
                } else {
                    $scope.recalculateBatch = RecalculateBatchService.getRecalculateBatch($scope.id);
                    // var c = RecalculateBatchService.getRecalculateBatch($scope.id);
                    // var r = c.responsibilityAreas;
                    console.log();
                
                }

                $scope.$watch("recalculateBatch", function(newRecalculateBatch, oldRecalculateBatch) {
                    if (angular.isDefined(newRecalculateBatch) &&
                        angular.isDefined(newRecalculateBatch.model) &&
                        angular.isDefined(newRecalculateBatch.model.modelId)) {
                        $scope.isMainDataLoaded = true;
                        $scope.recalculateBatch.model.modelVisId = newRecalculateBatch.model.modelVisId;
                        $scope.recalculateBatch.model.modelDescription = newRecalculateBatch.model.modelDescription;
                        // recalculateBatchDB.modelId = newRecalculateBatch.model.modelId;
                        // recalculateBatchDB.modelVisId = newRecalculateBatch.model.modelVisId;
                        // recalculateBatchDB.budgetCycleId = newRecalculateBatch.budgetCycleId;
                        $scope.manageModel();
                    }
                }, true);

                function checkInTree(tree, i){
                            if (tree.children[i] != null){
                                    tree = tree.children[i];
                                    checkInTree(tree);
                            } else {
                               for (var l=0;l<tree.children.length;l++){
                                   for (var k=0;k<$scope.recalculateBatch.responsibilityAreas.length;k++){
                                       if ($scope.recalculateBatch.responsibilityAreas[k]===tree.children[l].structureElementId){
                                           tree.children[l].state.selected = true;
                                       }
                                   }
                               }
                           }
                    }  
                
                
                $scope.$watchCollection("treeProcess", function(newTreeProcess, oldTreeProcess) {
                    if (angular.isDefined(newTreeProcess)){
                        if (newTreeProcess.id != undefined){
                            for (var i=0;i<newTreeProcess.children.length;i++){
                                checkInTree(newTreeProcess, i);
                            }
                        }
                    }
                });
                
                $scope.$watch("allProfileSelected", function() {
                    if (angular.isDefined($scope.recalculateBatch.profiles)) {
                        angular.forEach($scope.recalculateBatch.profiles, function(profile) {
                            profile.selected = $scope.allProfileSelected;
                        });
                    }
                });

                // ############################## MODELS ##########################################
                // we declared $scope.models to simplify watching whole collection of models
                // if we haven't this declaration, we use global (from MainController) models
                // but how we can watch this collection in current controller and after changing this collection how we can invoke $scope.manageModels()
                $scope.models = ModelService.getModels();
                $scope.$watch("models.sourceCollection.length", function(newLength) {
                    if (newLength <= 0)
                        return;
                    $scope.manageModel();
                });
                // 
                // ############################## VALIDATION ######################################
                /**
                 * Validate if any profile from recalculate batch profile is selected
                 * @param  {[type]}  profiles [selected profiles]
                 * @return {Boolean}          [true if at least one profile is selected]
                 */
                var isValidProfiles = function(profiles) {
                    var isValid = true;
                    if (profiles.length > 0) {
                        var isSelected = false;
                        angular.forEach(profiles, function(profile) {
                            if (profile.selected) {
                                isSelected = true;
                                return;
                            }
                        });

                        if (isSelected) {
                            $scope.validation.profiles = '';
                        } else {
                            isValid = false;
                            $scope.validation.profiles = 'You must choose at least one XML Form.';
                        }
                    } else {
                        isValid = false;
                        $scope.validation.profiles = 'There is no profiles for budget cycle.';
                    }
                    return isValid;
                };

                /**
                 * Validate if any responsibility area from recalculate batch responsibility areas is choosed]
                 * @param  {[type]}  responsibilityAreas [description]
                 * @return {Boolean}                     [description]
                 */
                var isValidResponsibilityArea = function(responsibilityAreas) {
                    var isValid = true;
                    if (responsibilityAreas.length <= 0) {
                        isValid = false;
                        $scope.validation.responsibilityAreas = 'Recalculate batch task requires at least one assignment.';
                    } else {
                        $scope.validation.responsibilityAreas = '';
                    }
                    return isValid;
                };

                /**
                 * Handle if user select or deselect profile model (from profile list)
                 * @return {[type]} [description]
                 */
                function toggleProfile() {
                    $scope.validation.profiles = '';
                }

                /**
                 * Validates if we have correct data in form. Other validation rules are in anuglar template.
                 * Function is invoked after click save in recalucate batch form.
                 * @param  {[type]}  recalculateBatch [description]
                 * @return {Boolean}                  [description]
                 */
                function isValid(recalculateBatch) {
                    var isValid = true;
                    isValid = isValidProfiles(recalculateBatch.profiles);
                    isValid = isValidResponsibilityArea(recalculateBatch.responsibilityAreas);
                    return isValid;
                }

                $scope.validation = {
                    recalculateBatchVisId: "",
                    recalculateBatchDescription: "",
                    profiles: "",
                    responsibilityAreas: ""
                };

                $scope.$on("RecalculateBatchService:recalculateBatchSaveError", function(event, data) {
                    $rootScope.$broadcast("modal:unblockAllOperations");
                    clearValidation();
                    // set new messages
                    angular.forEach(data.fieldErrors, function(error) {
                        if (error.fieldName in $scope.validation) {
                            $scope.validation[error.fieldName] = error.fieldMessage;
                        }
                    });
                });

                /**
                 * Clears whole validation object. All errors in templates hide.
                 * @return {[type]} [description]
                 */
                var clearValidation = function() {
                    // clear previous validation messages
                    angular.forEach($scope.validation, function(message, field) {
                        $scope.validation[field] = "";
                    });
                };

                $scope.$on("RecalculateBatchService:recalculateBatchSaved", function(event, data) {
                    if (data.success) {
                        $scope.close();
                    }
                });
            }
        };
    }
})();