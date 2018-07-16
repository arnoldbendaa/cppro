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
        .module('flatFormEditorApp.settings')
        .directive('workbookProperties', workbookProperties);

    /* @ngInject */
    function workbookProperties($rootScope, DataService, SpreadSheetService, $timeout, $modal, CoreCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'settings/views/workbookProperties.html',
			// scope: {
			// 	id: '='
			// },
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".workbook-properties").closest(".modal-dialog");
					var elementToResize = $(".elementToResize");
					var additionalElementsToCalcResize = [$(".getToCalc"), 10];
					$scope.cookieName = "flatFormEditor_modal_workbookProperties";
					// try to resize and move modal based on the cookie
					CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
					$timeout(function() { // timeout is necessary to pass asynchro
						CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
					}, 0);
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.save = save;
					$scope.close = close;
					$scope.changeModel = changeModel;
					$scope.choose = choose;
					
	                /************************************************** IMPLEMENTATION *************************************************************************/                   
					
					function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    };
                    
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    };
					
					function save() {
						CoreCommonsService.askIfReload = true;
						// translate booleans to strings - to make object compatibile with map from backend
						angular.forEach($scope.workbookProperties, function(value, key) {
							switch (value) {
								case true:
									$scope.originalWorkbookProperties[key] = "true";
									break;
								case false:
									$scope.originalWorkbookProperties[key] = "false";
									break;
								default:
									$scope.originalWorkbookProperties[key] = value;
									break;
							}
						});
						$rootScope.$broadcast("WorkbookProperties:close");
					};

					function close() {
						$rootScope.$broadcast("WorkbookProperties:close");
					}

					function changeModel() {
						$scope.workbookProperties.MODEL_VISID = $scope.currentModel.modelVisId;
					}

					function choose(tabIndex, jsTreeDataType) {
						var modelId = $scope.currentModel.modelId;
						var financeCubeId = $scope.financeCubeId;
						var selectedCcTree = [];
						var selectedExpTree = [];
						var selectedCallTree = [];
						var selectedDataType = [];
						var treesVisibility = [true, true, true, false];
						if (tabIndex == 3) { // if EXCLUDE_DATA_TYPES or DATA_TYPE
							treesVisibility = [false, false, false, true];
							if (jsTreeDataType == "ExcludeDataTypes") {
								if ($scope.workbookProperties.EXCLUDE_DATA_TYPES !== undefined) {
									var selectedDataTypes = $scope.workbookProperties.EXCLUDE_DATA_TYPES.split(",");
									angular.forEach(selectedDataTypes, function(elem) {
										selectedDataType.push({
											structureElementVisId: elem
										});
									});
								}
							} else {
								if ($scope.workbookProperties.DATA_TYPE !== undefined) {
									selectedDataType.push({
										structureElementVisId: $scope.workbookProperties.DATA_TYPE
									});
								}
							}
						} else { // CC, Exp, Cal
							if ($scope.workbookProperties.DIMENSION_0_VISID !== undefined && $scope.workbookProperties.DIMENSION_0_VISID != "") {
								selectedCcTree.push({
									structureElementVisId: $scope.workbookProperties.DIMENSION_0_VISID
								});
							}
							if ($scope.workbookProperties.DIMENSION_1_VISID !== undefined && $scope.workbookProperties.DIMENSION_1_VISID != "") {
								selectedExpTree.push({
									structureElementVisId: $scope.workbookProperties.DIMENSION_1_VISID
								});
							}
							if ($scope.workbookProperties.DIMENSION_2_VISID !== undefined && $scope.workbookProperties.DIMENSION_2_VISID != "") {
								var callTreeObject = {};
								var selectedObjectSplitted = $scope.workbookProperties.DIMENSION_2_VISID.split("/");
								if (selectedObjectSplitted[2] !== undefined) { //  year/month
									callTreeObject.structureElementVisId = selectedObjectSplitted[2];
									callTreeObject.parentVisId = selectedObjectSplitted[1];
								} else if (selectedObjectSplitted[1] !== undefined) { // year
									callTreeObject.structureElementVisId = selectedObjectSplitted[1];
								}
								selectedCallTree.push(callTreeObject);
							}
						}
						var modalTitle = "Workbook Properties Tree";
						var modalInstance = $modal.open({
							template: '<trees-chooser modal-title="modalTitle" selected-cc-tree="selectedCcTree" selected-exp-tree="selectedExpTree" selected-call-tree="selectedCallTree" selected-data-type="selectedDataType" model-id="modelId"  finance-cube-id="financeCubeId" multiple="multiple" ok="ok()"  trees-visibility="treesVisibility" tab-index="tabIndex" close="close()"></trees-chooser>',
							windowClass: 'sub-system-modals softpro-modals trees-chooser-dialog',
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
									$scope.multiple = false;
									if (tabIndex == 3) {
										if (jsTreeDataType == "ExcludeDataTypes") {
											$scope.multiple = true;
										}
									}
									$scope.treesVisibility = treesVisibility;
									$scope.tabIndex = tabIndex;

									$scope.ok = function() {
										var obj = {
											selectedCcTree: $scope.selectedCcTree,
											selectedCallTree: $scope.selectedCallTree,
											selectedExpTree: $scope.selectedExpTree,
											selectedDataType: $scope.selectedDataType,
										};

										$modalInstance.close(obj);
									};
									$scope.close = function() {
										$modalInstance.dismiss('cancel');
									};
								}
							]
						});
						modalInstance.result.then(function(obj) {
							if (tabIndex == 3) { // if EXCLUDE_DATA_TYPES or DATA_TYPE
								// update DataType or ExcludeDataTypes
								if (jsTreeDataType == "DataType") {
									if (obj.selectedDataType[0] !== undefined) {
										$scope.workbookProperties.DATA_TYPE = obj.selectedDataType[0].structureElementVisId;
									} else {
										$scope.workbookProperties.DATA_TYPE = "";
									}
								} else if (jsTreeDataType == "ExcludeDataTypes") {
									if (obj.selectedDataType[0] !== undefined) {
										var excludeDataTypes = "";
										angular.forEach(obj.selectedDataType, function(dataType) {
											excludeDataTypes += dataType.structureElementVisId + ",";
										});
										$scope.workbookProperties.EXCLUDE_DATA_TYPES = excludeDataTypes.substring(0, excludeDataTypes.length - 1);
									} else {
										$scope.workbookProperties.EXCLUDE_DATA_TYPES = "";
									}
								}
							} else { // CC, Exp, Cal
								// update CC
								if (obj.selectedCcTree[0] !== undefined) {
									$scope.workbookProperties.DIMENSION_0_VISID = obj.selectedCcTree[0].structureElementVisId;
								} else {
									$scope.workbookProperties.DIMENSION_0_VISID = "";
								}
								// update Exp
								if (obj.selectedExpTree[0] !== undefined) {
									$scope.workbookProperties.DIMENSION_1_VISID = obj.selectedExpTree[0].structureElementVisId;
								} else {
									$scope.workbookProperties.DIMENSION_1_VISID = "";
								}
								// update Cal
								if (obj.selectedCallTree[0] !== undefined) {
									if (obj.selectedCallTree[0].parentVisId !== undefined) {
										$scope.workbookProperties.DIMENSION_2_VISID = "/" + obj.selectedCallTree[0].parentVisId + "/" + obj.selectedCallTree[0].structureElementVisId;
									} else {
										$scope.workbookProperties.DIMENSION_2_VISID = "/" + obj.selectedCallTree[0].structureElementVisId;
									}
								} else {
									$scope.workbookProperties.DIMENSION_2_VISID = "";
								}
							}
						}, function() {

						});
					};

					var firstLoading = true;
					$scope.flatForm = SpreadSheetService.getFlatForm();
					$scope.$watch('flatForm.xmlForm.properties', function(newValue, oldValue) {
						if (angular.isDefined($scope.flatForm.xmlForm) && angular.isDefined($scope.flatForm.xmlForm.properties)) {
							// only if we initialize properties or save ones
							if (firstLoading || newValue != oldValue) {
								firstLoading = false;
								$scope.originalWorkbookProperties = newValue;
								$scope.workbookProperties = angular.copy($scope.originalWorkbookProperties);
								// translate some strings to booleans - now checkbox will work
								angular.forEach($scope.workbookProperties, function(value, key) {
									switch (value) {
										case "true":
											$scope.workbookProperties[key] = true;
											break;
										case "false":
											$scope.workbookProperties[key] = false;
											break;
									}
								});

								$scope.models = DataService.getModels(true);
								// get currentModel based on $scope.workbookProperties.MODEL_VISID or set it as first (=empty) element
								if (typeof $scope.workbookProperties.MODEL_VISID != "undefined") {
									$scope.currentModel = CoreCommonsService.findElementByKey($scope.models, $scope.workbookProperties.MODEL_VISID, "modelVisId");
								} else {
									$scope.currentModel = $scope.models[0];
								}

								$scope.dataTypes = DataService.getDataTypes(true);

							}
						}
					}, true);

				}
			]
		};
	}
})();