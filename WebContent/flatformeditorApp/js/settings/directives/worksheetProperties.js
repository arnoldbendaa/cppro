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
        .directive('worksheetProperties', worksheetProperties);

    /* @ngInject */
    function worksheetProperties($rootScope, DataService, SpreadSheetService, $timeout, CoreCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'settings/views/worksheetProperties.html',
			// scope: {
			// 	id: '='
			// },
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".worksheet-properties").closest(".modal-dialog");
					var elementToResize = $(".elementToResize");
					var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), 40];
					$scope.cookieName = "flatFormEditor_modal_worksheetProperties";
					// try to resize and move modal based on the cookie
					CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
					$timeout(function() { // timeout is necessary to pass asynchro
						CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
					}, 0);
					$scope.currentModelDimensions = {};
					$scope.currentFinanceCube = {};
					$scope.financeCubes = {};
					var currentSpreadSheetName = $scope.currentSpreadSheet.name();
					var firstLoading = true;
					// to disable FinanceCube select filed and show loader during getting informations about dimensions for current model
					$scope.changingFinanceCubeDisabled = false;
					$scope.flatForm = SpreadSheetService.getFlatForm();
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.changeFinanceCube = changeFinanceCube;
					$scope.changeHierarchy = changeHierarchy;
					$scope.save = save;
					$scope.close = close;
					
					/************************************************** IMPLEMENTATION *************************************************************************/
					
					function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    };
                    
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    };
                    
					function changeFinanceCube() {
						$scope.changingFinanceCubeDisabled = true;
						$scope.currentModelDimensions = DataService.getDimensionsForModel($scope.currentFinanceCube.model.modelId);
						// update Worksheet
						$scope.currentFlatFormWorksheet.properties.FINANCE_CUBE_VISID = $scope.currentFinanceCube.financeCubeVisId;
						$scope.currentFlatFormWorksheet.properties.FINANCE_CUBE_ID = "FinanceCubeCK|ModelPK|" + $scope.currentFinanceCube.model.modelId + "|FinanceCubePK|" + $scope.currentFinanceCube.financeCubeId;
						$scope.currentFlatFormWorksheet.properties.MODEL_ID = "ModelPK|" + $scope.currentFinanceCube.model.modelId;
						$scope.currentFlatFormWorksheet.properties.MODEL_VISID = $scope.currentFinanceCube.model.modelVisId;
					};

					function changeHierarchy(index) {
						// update Worksheet
						$scope.currentFlatFormWorksheet.properties["DIMENSION_" + index + "_HIERARCHY_VISID"] = $scope["currentHierarchy" + index].hierarchyVisId;
					}

					function save() {
						CoreCommonsService.askIfReload = true;
						// copy Worksheet to flatForm.xmlForm.worksheets (in service)
						angular.copy($scope.currentFlatFormWorksheet, CoreCommonsService.findElementByKey($scope.flatForm.xmlForm.worksheets, $scope.currentSpreadSheet.name(), "name"));
						$rootScope.$broadcast("WorksheetProperties:close");
					};

					function close() {
						$rootScope.$broadcast("WorksheetProperties:close");
					}

					$scope.$watch('flatForm.xmlForm.worksheets.length', function(newValue, oldValue) {
						// only if we initialize properties or save ones
						if (firstLoading || newValue != oldValue) {
							firstLoading = false;
							$scope.originalFlatFormWorksheet = CoreCommonsService.findElementByKey($scope.flatForm.xmlForm.worksheets, $scope.currentSpreadSheet.name(), "name");
							$scope.currentFlatFormWorksheet = angular.copy($scope.originalFlatFormWorksheet);
							if ($scope.currentFlatFormWorksheet !== null) {
								$scope.financeCubes = DataService.getFinanceCubes();
								$scope.currentFinanceCube = CoreCommonsService.findElementByKey($scope.financeCubes, $scope.currentFlatFormWorksheet.properties.FINANCE_CUBE_VISID, "financeCubeVisId");
								if ($scope.currentFinanceCube != null) {
									$scope.currentModelDimensions = DataService.getDimensionsForModel($scope.currentFinanceCube.model.modelId);
								}
							}
						}
					}, true);

					$scope.$watch('currentModelDimensions', function(newValue, oldValue) {
						if (newValue.length > 0 && newValue != oldValue) {
							// on first loading information about worksheet
							// update Worksheet
							$scope.currentFlatFormWorksheet.properties.DIMENSION_0_VISID = newValue[0].dimensionVisId;
							$scope.currentFlatFormWorksheet.properties.DIMENSION_1_VISID = newValue[1].dimensionVisId;
							$scope.currentFlatFormWorksheet.properties.DIMENSION_2_VISID = newValue[2].dimensionVisId;
							// set values in hierarchy select fields:
							// if model has hierarchies - catch them
							var tempHier0 = CoreCommonsService.findElementByKey(newValue[0].hierarchies, $scope.currentFlatFormWorksheet.properties.DIMENSION_0_HIERARCHY_VISID, "hierarchyVisId");
							var tempHier1 = CoreCommonsService.findElementByKey(newValue[1].hierarchies, $scope.currentFlatFormWorksheet.properties.DIMENSION_1_HIERARCHY_VISID, "hierarchyVisId");
							var tempHier2 = CoreCommonsService.findElementByKey(newValue[2].hierarchies, $scope.currentFlatFormWorksheet.properties.DIMENSION_2_HIERARCHY_VISID, "hierarchyVisId");
							// if model doesn't have hierarchies - catch first hierarchy for every dimension
							if (tempHier0 === null || tempHier1 === null || tempHier2 === null) {
								tempHier0 = newValue[0].hierarchies[0];
								tempHier1 = newValue[1].hierarchies[0];
								tempHier2 = newValue[2].hierarchies[0];
							}
							// set values in hierarchy select fields
							$scope.currentHierarchy0 = tempHier0;
							$scope.currentHierarchy1 = tempHier1;
							$scope.currentHierarchy2 = tempHier2;
						} else if (newValue == oldValue && oldValue.length > 0) {
							// after opening worksheet properties again
							// set values in hierarchy select fields
							$scope.currentHierarchy0 = CoreCommonsService.findElementByKey(newValue[0].hierarchies, $scope.currentFlatFormWorksheet.properties.DIMENSION_0_HIERARCHY_VISID, "hierarchyVisId");
							$scope.currentHierarchy1 = CoreCommonsService.findElementByKey(newValue[1].hierarchies, $scope.currentFlatFormWorksheet.properties.DIMENSION_1_HIERARCHY_VISID, "hierarchyVisId");
							$scope.currentHierarchy2 = CoreCommonsService.findElementByKey(newValue[2].hierarchies, $scope.currentFlatFormWorksheet.properties.DIMENSION_2_HIERARCHY_VISID, "hierarchyVisId");

						}
						$timeout(function() {
							$scope.changingFinanceCubeDisabled = false;
						}, 500);
					}, true);

					$scope.$watch('currentHierarchy0', function(newValue, oldValue) {
						if (newValue != oldValue) {
							$scope.currentFlatFormWorksheet.properties.DIMENSION_0_HIERARCHY_VISID = newValue.hierarchyVisId;
						}
					}, true);

					$scope.$watch('currentHierarchy1', function(newValue, oldValue) {
						if (newValue != oldValue) {
							$scope.currentFlatFormWorksheet.properties.DIMENSION_1_HIERARCHY_VISID = newValue.hierarchyVisId;
						}
					}, true);

					$scope.$watch('currentHierarchy2', function(newValue, oldValue) {
						if (newValue != oldValue) {
							$scope.currentFlatFormWorksheet.properties.DIMENSION_2_HIERARCHY_VISID = newValue.hierarchyVisId;
						}
					}, true);

				}
			]
		};
	}
})();