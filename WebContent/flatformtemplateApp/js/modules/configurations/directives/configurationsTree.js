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
		.module('flatFormTemplateApp.configurations')
		.directive('configurationsTree', configurationsTree);

	/* @ngInject */
	function configurationsTree(ConfigurationsService, CoreCommonsService) {
		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'modules/configurations/views/configurationsTree.html',
			scope: {
				createdSheets: '=',
				dimensions: '='
			},
			replace: true,
			controller: configurationsTreeController
		};

		function configurationsTreeController($rootScope, $scope, $timeout) {
			// parameters to resize modal
			var modalDialog = $(".configurations-tree").closest(".modal-dialog");
			var elementToResize = $(".elementToResize");
			var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), -40];
			var stateRoot = {};
			var tmpDimensions = [];
			$scope.cookieName = "flatFormTemplate_modal_configurationsTree";
			$timeout(function() { // timeout is necessary to pass asynchro
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
			}, 0);
			$scope.choosedModel = {};
			$scope.treeProcess = {};
			$scope.isChoosedModel = false;
			$scope.resizedColumn = resizedColumn;
			$scope.sortedColumn = sortedColumn;
			$scope.selectNode = selectNode;
			$scope.deselectNode = deselectNode;
			$scope.parseSheetName = parseSheetName;
			$scope.saveDimensions = saveDimensions;
			$scope.onChange = onChange;
			$scope.close = close;
			activate();

			/************************************************** IMPLEMENTATION *************************************************************************/

			stateRoot = {
				opened: false,
				disabled: false,
				selected: false,
				leaf: true,
			};

			function activate() {
				// try to resize and move modal based on the cookie
				CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
				if ($scope.dimensions.length !== 0) {

					angular.forEach($scope.dimensions, function(sheet) {
						var tmpDimension = {};
						angular.copy(sheet, tmpDimension);
						tmpDimension.oldDimension = true;
						tmpDimension.toSave = true;
						tmpDimensions.push(tmpDimension);
					});
				}
				$scope.treeProcess = {
					id: '1',
					text: 'Node root',
					state: stateRoot,
					children: null,
				};
				$scope.sourceModels = ConfigurationsService.getModels();
			}

			function resizedColumn(sender, args) {
				CoreCommonsService.resizedColumn(args, $scope.cookieName);
			};

			function sortedColumn(sender, args) {
				CoreCommonsService.sortedColumn(args, $scope.cookieName);
			};

			/**
			 * select treeNode checkboxes.
			 */
			function selectNode(e, data) {
				manageSelectedProcess(data.node);
			};


			/**
			 * deselect treeNode checkboxes.
			 */
			function deselectNode(e, data) {
				manageDeselectedProcess(data.node);
			};
			/**
			 * Management for selected process and children.
			 *
			 * @param {[type]} node
			 */
			function parseSheetName(sheetName) {
				var resultSheetName = sheetName.replace(/:/g, " ").replace(/\//g, "-");
				return resultSheetName;
			};
			var manageSelectedProcess = function(node) {
				var tmpSheetName = node.id;
				for (var i = 0; i < tmpDimensions.length; i++) {
					if (tmpDimensions[i].sheetName === node.id) {
						tmpSheetName = node.id + " " + "(" + $scope.choosedModel.modelVisId + ")";
						break;
					} else {
						tmpSheetName = node.id;
					}
				}
				
				var excludedDimensions;
				if(node.state.leaf === true) {
				    excludedDimensions = null;
				} else {
				    excludedDimensions = [];
				}

				//var tmpSheetName = $scope.choosedModel.modelVisId + '-' + node.id;
				var dimension = {
					dimensionId: node.original.structureElementId,
					dimensionVisId: node.id,
					sheetName: $scope.parseSheetName(tmpSheetName),
					modelVisId: $scope.choosedModel.modelVisId,
					index: 0,
					hidden: false,
					oldDimension: false,
					excludedDimensions: excludedDimensions
				};
				tmpDimensions.push(dimension);
				node.state.selected = true;
				for (var i = 0; i < tmpDimensions.length; i++) {
					if (node.id === tmpDimensions[i].dimensionVisId && $scope.choosedModel.modelVisId === tmpDimensions[i].modelVisId) {
						if (tmpDimensions[i].oldDimension === true && tmpDimensions[i].toSave === false) {
							tmpDimensions[i].toSave = true;
						}
					}
				}
			};
			/**
			 * Management deselected for tree process and children
			 *
			 * @param {[type]} node
			 */
			var manageDeselectedProcess = function(node) {
				if (node.original.oldDimension === true && node.original.toSave === true) {
					node.original.toSave = false;
				} else {
					for (var i = 0; i < tmpDimensions.length; i++) {
						if (node.id === tmpDimensions[i].dimensionVisId && $scope.choosedModel.modelVisId === tmpDimensions[i].modelVisId) {
							tmpDimensions[i].toSave = false;
						}
					}
				}
			};

			function saveDimensions() {
				var sheets = $scope.createdSheets.sourceCollection;
				var dimensionsToSave = tmpDimensions;
				var dim = $scope.dimensions;

				for (var i = 0; i < dimensionsToSave.length; i++) {
					if (dimensionsToSave[i].oldDimension === false && dimensionsToSave[i].toSave === undefined) {
						var simpleDim = dimensionsToSave[i];
						delete simpleDim.dimensionId;
						delete simpleDim.oldDimension;
						delete simpleDim.toSave;

						sheets.push(simpleDim);
						$scope.dimensions.push(simpleDim);
					} else if ((dimensionsToSave[i].oldDimension !== true && dimensionsToSave[i].toSave !== true) || (dimensionsToSave[i].oldDimension === true && dimensionsToSave[i].toSave === false)) {
						for (var j = 0; j < sheets.length; j++) {
							if (dimensionsToSave[i].dimensionVisId === sheets[j].dimensionVisId && dimensionsToSave[i].modelVisId === sheets[j].modelVisId) {
								sheets.splice(j, 1);
							}
						}
						for (var j = 0; j < dim.length; j++) {
							if (dimensionsToSave[i].dimensionVisId === dim[j].dimensionVisId && dimensionsToSave[i].modelVisId === dim[j].modelVisId) {

								var tmpDim = dim[j];
								dim.splice(j, 1);

								for (var m = 0; m < $scope.createdSheets.sourceCollection.length; m++) {
									if ((angular.isDefined($scope.createdSheets.sourceCollection[m].totalUUID) && $scope.createdSheets.sourceCollection[m].dimensionList !== null)) {
										for (var n = 0; n < $scope.createdSheets.sourceCollection[m].dimensionList.length; n++) {
											var a = $scope.createdSheets.sourceCollection[m].dimensionList[n].modelVisId;
											var b = $scope.createdSheets.sourceCollection[m].dimensionList[n].dimensionVisId;
											if (a == tmpDim.modelVisId && b == tmpDim.dimensionVisId) {
												$scope.createdSheets.sourceCollection[m].dimensionList.splice(n, 1);
											}
										}
									}
								}
							}
						}
					}
				}
				for (var i = 0; i < sheets.length; i++) {
					sheets[i].index = i + 1;
				}
				$scope.createdSheets.refresh();
				CoreCommonsService.askIfReload = true;
			};

			/**
			 * Find and select node's checkbox and that children by selectNodeAndChildren.
			 */
			var findAndSelect = function(currentNode, lookingNodeVisId) {
				if (currentNode.id == lookingNodeVisId) {
					currentNode.state.selected = true;
					return true;
				} else {
					if (currentNode.children !== undefined && currentNode.children !== null && currentNode.children.length > 0) {
						for (var i = 0; i < currentNode.children.length; i++) {
							if (findAndSelect(currentNode.children[i], lookingNodeVisId) === true) {
								return true;
							}
						}
					}
					return false;
				}
			};

			var loader = $(".configurationsTreeBusinessDimensionChooser");

			function onChange() {
				loader.show();
				$scope.treeProcess = ConfigurationsService.getTree($scope.choosedModel.modelId);
				$scope.isChoosedModel = true;
			}

			function close() {
				$rootScope.$broadcast("ConfigurationsTree:close");
			};


			/** ******************************************************* WATCHERS ******************************************************* */

			/**
			 * When choosedBusinessDimension is changed then change too range of hierarchies.
			 */
			$scope.$watchCollection(
				// "This function returns the value being watched. It is called for each turn of the $digest loop"
				function() {
					return $scope.treeProcess
				},
				// "This is the change listener, called when the value returned from the above function changes"
				function(newValue, oldValue) {
					// This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
					// To protect against this we check if newValue isn't empty.
					if (newValue !== oldValue && Object.keys(newValue).length !== 0) {
						var elements = tmpDimensions;
						for (var j = 0; j < elements.length; j++) {
							if (elements[j].modelVisId == $scope.choosedModel.modelVisId) {
								if ($scope.treeProcess.text == elements[j].dimensionVisId) {
									$scope.treeProcess.state.selected = true;
								} else {
									for (var i = 0; i < $scope.treeProcess.children.length; i++) {
										findAndSelect($scope.treeProcess.children[i], elements[j].dimensionVisId);
									}
								}
							}
						}
					}
				}
			);

			$scope.$watch('treeProcess', function(newValue, oldValue) {
				// hide loader when new tree content is loaded
				if (Object.keys(newValue).length !== 0 && Object.keys(oldValue).length === 0) {
					loader.hide();
				}
			}, true);

			$scope.$watch(
				// "This function returns the value being watched. It is called for each turn of the $digest loop"
				function() {
					return $scope.sourceModels.sourceCollection.length;
				},
				// "This is the change listener, called when the value returned from the above function changes"
				function() {
					// This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
					// To protect against this we check if newValue isn't empty.
					if ($scope.sourceModels.sourceCollection.length > 0) {
						$scope.models = $scope.sourceModels.sourceCollection;
					}
				}
			);
		}
	}
})();