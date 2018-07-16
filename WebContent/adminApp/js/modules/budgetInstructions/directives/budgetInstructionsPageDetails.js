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
		.module('adminApp.budgetInstructionsPage')
		.directive('budgetInstructionDetails', budgetInstructionDetails);

	/* @ngInject */
	function budgetInstructionDetails($rootScope, Flash, BudgetInstructionsPageService, $timeout, CoreCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'budgetInstructions/views/budgetInstructionDetails.html',
			scope: {
				id: '='
			},
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".budget-instruction-details").closest(".modal-dialog");
					var elementToResize = $(".elementToResize");
					var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3"), 60];
					$scope.cookieName = "adminPanel_modal_budgetInstruction";
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.onFileSelect = onFileSelect;
					$scope.save = save;
					$scope.readyTree = readyTree;
					$scope.selectNode = selectNode;
					$scope.deselectNode = deselectNode;
					$scope.close = close;
					activate();

					function activate() {
						// try to resize and move modal based on the cookie
						CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
					}

					function resizedColumn(sender, args) {
						CoreCommonsService.resizedColumn(args, $scope.cookieName);
					}

					function sortedColumn(sender, args) {
						CoreCommonsService.sortedColumn(args, $scope.cookieName);
					}
					$timeout(function() { // timeout is necessary to pass asynchro
						CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
					}, 0);

					$scope.isMainDataLoaded = false;
					$scope.isError = false;
					$scope.budgetInstruction = {};
					$scope.treeAjax = '/cppro/adminPanel/budgetInstructions/' + $scope.id + '/assignments'


					if ($scope.id == -1) {
						$scope.budgetInstruction = BudgetInstructionsPageService.getBudgetInstructionDetails($scope.id);
						// $scope.budgetInstruction.budgetInstructionDocument = BudgetInstructionsPageService.upload($scope.id);
					} else {
						$scope.budgetInstruction = BudgetInstructionsPageService.getBudgetInstructionDetails($scope.id);
					}
					$scope.budgetInstruction.assignments = [];

					/**
					 * validation messages: messageError for top message bar, validation fields for each form fields
					 * If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
					 *@type {Boolean}
					 */
					$scope.validation = {
						budgetInstructionVisId: "",
						budgetInstructionDocumentRef: "",
						budgetInstructionAssignment: []
					};

					function onFileSelect($files) {
						$scope.budgetInstruction.budgetInstructionDocument = $files;
					};
					$scope.budgetInstruction.budgetInstructionId = -1;


					function save() {
						$rootScope.$broadcast("modal:blockAllOperations");
						BudgetInstructionsPageService.upload($scope.budgetInstruction);
					};

					function readyTree(e, data) {}

					/**
					 * select node checkboxes.
					 */
					function selectNode(e, data) {
						console.log("data", data);
						manageSelectedAssignment(data, data.instance);
					}

					/**
					 * deselect node checkboxes.
					 */
					function deselectNode(e, data) {
						manageDeselectedAssignament(data, data.instance);
					}

					/**
					 * Add to selected list , actually selecting item
					 * [manageSelectedAssignment description]
					 * @param  {[type]} assignment [description]
					 * @param  {[type]} jsTree     [description]
					 * @return {[type]}            [description]
					 */
					var manageSelectedAssignment = function(assignment, jsTree) {
						var selectList = assignment.selected;
						var selectedAssignment = {};
						var model = assignment.node.parents[assignment.node.parents.length - 2];
						var modelId = parseInt(model.split("/")[1]);
						selectedAssignment.modelId = modelId;
						assignment.node.state.selected = true;
						var parent = assignment.node.parent.split("/")[0];
						if (parent == "budgetCycles") { //budget Cycles Folder
							var budgetCycleId = assignment.node.id;
							selectedAssignment.budgetCycleId = budgetCycleId;
							selectedAssignment.structureId = -1;
							selectedAssignment.structureElementId = -1;
							selectedAssignment.narrative = "";
							assignment.node.state.selected = true;
							$scope.budgetInstruction.assignments.push(selectedAssignment);
						} else { //ResponsibilityAreas
							for (var i = 0; i < selectList.length; i++) {
								var selectedSecurityID = assignment.selected[i];
								var actualyConvertIdSelectedSecurity = parseInt(selectedSecurityID.split("/")[1]);
								selectedAssignment.budgetCycleId = -1;
								var id = assignment.node.id;
								var select = parseInt(id.split("/")[1]);
								if (select == actualyConvertIdSelectedSecurity) {
									assignment.node.state.selected = true;
									var structureIdAndStructureElementId = assignment.node.id;
									selectedAssignment.structureId = parseInt(structureIdAndStructureElementId.split("/")[1]);
									selectedAssignment.structureElementId = parseInt(structureIdAndStructureElementId.split("/")[3]);
									var nameResponsibility = assignment.node.text;
									selectedAssignment.narrative = nameResponsibility;
									assignment.node.state.selected = true;
									$scope.budgetInstruction.assignments.push(selectedAssignment);
									break;

								}
							}
						}
					};

					/**
					 * Remove item from selected list
					 * [manageSelectedAssignment description]
					 * @param  {[type]} assignment [description]
					 * @param  {[type]} jsTree     [description]
					 * @return {[type]}            [description]
					 */
					var manageDeselectedAssignament = function(assignment, jsTree) {
						var selectList = assignment.selected;
						var selectedAssignment = {};
						var model = assignment.node.parents[assignment.node.parents.length - 2];
						var modelId = parseInt(model.split("/")[1]);
						selectedAssignment.modelId = modelId;
						var parent = assignment.node.parent.split("/")[0];
						if (parent == "budgetCycles") {
							//budget Cycles Folder 
							var budgetCycleId = assignment.node.id;
							for (var i = 0; i < $scope.budgetInstruction.assignments.length; i++) {
								if (budgetCycleId == $scope.budgetInstruction.assignments[i].budgetCycleId) {
									$scope.budgetInstruction.assignments.splice(i, 1);
									break;
								}
							}
						} else {
							for (var i = 0; i <= selectList.length; i++) {
								var id = assignment.node.id;
								var actuallyDselectedStructureElement = parseInt(id.split("/")[3]);
								if (selectList.length == 0) { // if list is empty remove last selected item
									for (var k = 0; $scope.budgetInstruction.assignments.length; k++) {
										var structureElementId = $scope.budgetInstruction.assignments[k].structureElementId;
										if (actuallyDselectedStructureElement == structureElementId) {
											$scope.budgetInstruction.assignments.splice(k, 1);
											break;
										}
									}
								} else {
									//ResponsibilityAreas
									var selectedSecurityID = assignment.selected[i];
									var actualyConvertIdSelectedSecurity = parseInt(selectedSecurityID.split("/")[1]);
									var select = parseInt(id.split("/")[1]);
									assignment.node.state.selected = false;
									if (select == actualyConvertIdSelectedSecurity) {
										assignment.node.state.selected = false;
										var structureIdAndStructureElementId = assignment.node.id;
										selectedAssignment.structureId = parseInt(structureIdAndStructureElementId.split("/")[1]);
										selectedAssignment.structureElementId = parseInt(structureIdAndStructureElementId.split("/")[3]);
										var nameSecurity = assignment.node.text;
										selectedAssignment.narrative = nameSecurity;
										// var assignmentId = $scope.budgetInstruction.assignments[i].assignmentId;
										selectedAssignment.assignmentId = -1;
										for (var j = 0; j < $scope.budgetInstruction.assignments.length; j++) {
											if ($scope.budgetInstruction.assignments[j].structureElementId == selectedAssignment.structureElementId)

												$scope.budgetInstruction.assignments.splice(j, 1);
										}
									}
								}
							}
						}
					};

					$scope.$on("BudgetInstructionsPageService:budgetInstructionDetailsSaveError", function(event, data) {
						$rootScope.$broadcast("modal:unblockAllOperations");
						$scope.isError = true;
						// error from method http->success() from "error" field
						Flash.create('danger', data.message);
						// clear previous validation messages
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

					function close() {
						$rootScope.$broadcast("BudgetInstructionDetails:close");
					};

					$scope.$watchCollection(
						function() {

							return $scope.budgetInstruction
						},

						function(newValue, oldValue) {
							if (newValue !== oldValue && Object.keys(oldValue).length != 0) {
								$scope.budgetInstruction = newValue;
								$scope.isMainDataLoaded = true;
							}
						}
					);
				}
			]
		}
	}
})();