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
		.module('coreApp.components')
		.directive('treesChooser', treesChooser);

	/* @ngInject */
	function treesChooser($rootScope, $modal, $timeout, CoreCommonsService, $http) {
		return {
			restrict: 'E',
			templateUrl: $BASE_PATH + 'coreApp/js/modules/components/views/treesChooser.html',
			scope: {
				modalTitle: '=',
				selectedCcTree: '=',
				selectedExpTree: '=',
				selectedCallTree: '=',
				selectedDataType: '=',
				modelId: '=',
				financeCubeId: '=',
				multiple: '=',
				treesVisibility: '=',
				checkboxState: '=',
				tabIndex: '=',
				ok: '&',
				close: '&'
			},
			replace: true,
			link: linkFunction
		};

		function linkFunction($scope) {
			// parameters to resize modal
			var modalDialog = $(".trees-chooser").closest(".modal-dialog");
			var navTabs = modalDialog.find("ul.nav");
			var additional = 30;
			var elementToResize1 = $(".elementToResize1");
			var additionalElementsToCalcResize1 = [navTabs, $(".label1"), additional];
			var elementToResize2 = $(".elementToResize2");
			var additionalElementsToCalcResize2 = [navTabs, $(".label2"), additional];
			var elementToResize3 = $(".elementToResize3");
			var additionalElementsToCalcResize3 = [navTabs, $(".label3"), additional];
			var elementToResize4 = $(".elementToResize4");
			var additionalElementsToCalcResize4 = [navTabs, $(".label4"), additional];

			$scope.cookieName = "adminPanel_modal_treesChooser";


			$scope.resizedColumn = resizedColumn;
			$scope.sortedColumn = sortedColumn;
			self.getDimensionsForModel = getDimensionsForModel; //????
			$scope.beforeOpen = beforeOpen;
			$scope.dimensionForModel = getDimensionsForModel($scope.modelId);
			$scope.listNodes = [];


			activate();

			function activate() {
				// try to resize and move modal based on the cookie
				CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);

				if ($scope.multiple) {
					$scope.treeAjaxCC = '/cppro/adminPanel/financeCubeFormula/' + $scope.modelId + '/hierarchyCc';
					$scope.treeAjaxEXP = '/cppro/adminPanel/financeCubeFormula/' + $scope.modelId + '/hierarchyExp';
					$scope.treeAjaxCalendars = '/cppro/adminPanel/financeCubeFormula/calendar/' + $scope.modelId + '/calendars';
					$scope.treeAjaxDataTypes = '/cppro/adminPanel/financeCubeFormula/dataTypes/' + $scope.modelId;
					/**
					 * Call if multiple selection is set to true
					 * @param  {[type]} e    [description]
					 * @param  {[type]} data [description]
					 * @return {[type]}      [description]
					 */
					$scope.selectNode = function(e, data) {
						manageSelectedNode(e, data.instance._model.data);
					};
					$scope.deselectNode = function(e, data) {
						manageSelectedNode(e, data.instance._model.data);
					};
				} else {
					$scope.treeAjaxCC = '/cppro/adminPanel/financeCubeFormula/' + $scope.modelId + '/hierarchyCc?disableLevel=1';
					$scope.treeAjaxEXP = '/cppro/adminPanel/financeCubeFormula/' + $scope.modelId + '/hierarchyExp?disableLevel=1';
					$scope.treeAjaxCalendars = '/cppro/adminPanel/financeCubeFormula/calendar/' + $scope.modelId + '/calendars?disableLevel=2';
					$scope.treeAjaxDataTypes = '/cppro/adminPanel/financeCubeFormula/dataTypes/' + $scope.modelId + '?disableLevel=1';
					/**
					 * Managing Node when we can select one item
					 * @param  {[type]} e    [description]
					 * @param  {[type]} data [description]
					 * @return {[type]}      [description]
					 */
					$scope.activeNode = function(e, data) {
						var treeId = $("#" + e.currentTarget.id);
						var tree = $(treeId).jstree();
						tree.settings.core.multiple = false;
						manageSelectedNodeNotMultiple(e, data, tree);
					};

				}

				$timeout(function() { // timeout is necessary to pass asynchro
					var allElementsToResize = [elementToResize1, elementToResize2, elementToResize3, elementToResize4];
					var allAdditionalElementsToCalcResize = [additionalElementsToCalcResize1, additionalElementsToCalcResize2, additionalElementsToCalcResize3, additionalElementsToCalcResize4];
					CoreCommonsService.allowResizingModal(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx);
				}, 0);
				// Find current tab and show it.
				$timeout(function() {
					$($(".trees-chooser ul.nav li")[$scope.tabIndex]).find("a").click();
				}, 0);

			}


			/************************************************** IMPLEMENTATION *************************************************************************/



			function resizedColumn(sender, args) {
				CoreCommonsService.resizedColumn(args, $scope.cookieName);
			}

			function sortedColumn(sender, args) {
				CoreCommonsService.sortedColumn(args, $scope.cookieName);
			}

			function beforeOpen(e, data) {
				var listOpenedNodes = data.instance._model.data;
				switch (e.currentTarget.id) {
					case "1": // CC
						selectOneTreePreviousElements($scope.selectedCcTree, listOpenedNodes, e);
						break;
					case "2": // Exp
						selectOneTreePreviousElements($scope.selectedExpTree, listOpenedNodes, e);
						break;
					case "3": // Calendar
						angular.forEach(listOpenedNodes, function(nodeObject) {
							if (nodeObject.parent !== null && nodeObject.parent != "#") {
								angular.forEach($scope.selectedCallTree, function(selectedObject) {
									var yearToCompare = "";
									if (selectedObject.parentVisId !== undefined) { //  year/month
										yearToCompare = selectedObject.parentVisId;
										var monthToCompare = selectedObject.structureElementVisId;
										// find parent
										var parentName = nodeObject.parent;
										var parent = listOpenedNodes[parentName];
										var nodeObjectYear = parent.original.textNode;
										if (nodeObject.original.textNode == monthToCompare && nodeObjectYear == yearToCompare) {
											nodeObject.state.selected = true;
										}
									} else { // year
										yearToCompare = selectedObject.structureElementVisId;
										if (nodeObject.original.textNode == yearToCompare) {
											nodeObject.state.selected = true;
										}
									}
								});
							}
						});
						break;
					case "4": // DataType
						angular.forEach(listOpenedNodes, function(nodeObject) {
							if (nodeObject.parent !== null && nodeObject.parent !== "#") {
								angular.forEach($scope.selectedDataType, function(selectedObject) {
									var toCompare = selectedObject.structureElementVisId;
									var nodeObjectName = nodeObject.original.textNode;
									if (nodeObjectName == toCompare) {
										nodeObject.state.selected = true;
									}
								});
							}
						});
						break;
				}
				var treeId = $("#" + e.currentTarget.id);
				var tree = $(treeId).jstree();
				$timeout(function() {
					tree.redraw(true);
				}, 0);
			}


			/**
			 * Get dimension and hierarchies for selected modelId
			 * @type {Array}
			 */
			function getDimensionsForModel(modelId) {
				var currentModelDimensions = [];
				var modelDimensionsWithHierarchies = [];
				if (modelDimensionsWithHierarchies[modelId]) {
					angular.copy(modelDimensionsWithHierarchies[modelId], currentModelDimensions);
				} else {
					$http.get($BASE_PATH + 'adminPanel/financeCubeFormula/modelDimensionsWithHierarchies/' + modelId).success(function(data) {
						if (data && data.length > 0) {
							modelDimensionsWithHierarchies[modelId] = data;
							angular.copy(modelDimensionsWithHierarchies[modelId], currentModelDimensions);
						}
					});
				}
				return currentModelDimensions;
			}


			/************************************************** PRIVATE MEMBERS *********************************************************************/



			var selectOneTreePreviousElements = function(allSelected, listOpenedNodes, e) {
				angular.forEach(listOpenedNodes, function(nodeObject) {
					if (nodeObject.parent !== null && nodeObject.parent !== "#") {
						angular.forEach(allSelected, function(selectedObject) {
							var toCompare = selectedObject.structureElementVisId;
							var nodeObjectName = "";
							if (nodeObject.original.textNode !== undefined && nodeObject.original.textNode !== null) {
								nodeObjectName = nodeObject.original.textNode;
							} else {
								nodeObjectName = nodeObject.original.text;
							}
							if (nodeObjectName == toCompare) {
								nodeObject.state.selected = true;
							}
						});
					}
				});
			};



			/**
			 * Managing node
			 * @param  {[type]} event [description]
			 * @param  {[type]} data  [description]
			 * @return {[type]}       [description]
			 */
			var manageSelectedNodeNotMultiple = function(event, data, inst) {
				clearProperDimensions(event.currentTarget.id);
				var temporaryList = [];

				var arraySelectedNode = inst.get_selected(true);
				if (arraySelectedNode.length === 0) {
					return;
				} else {
					inst.deselect_all();
					inst.select_node(data.node);
					temporaryList.push(data.node);
				}
				var visId = null;
				if (event.currentTarget.id === "1") {
					$scope.selectedCcTree = convertListElementsToRequiredObject(temporaryList, 2);
				}
				if (event.currentTarget.id === "2") {
					$scope.selectedExpTree = convertListElementsToRequiredObject(temporaryList, 1);
				}
				if (event.currentTarget.id === "3") {
					if (data.node.state.leaf) {
						visId = findParentVisId(data.node.parent, data.instance._model.data);
					}
					$scope.selectedCallTree = convertListElementsToRequiredObject(temporaryList, 3);
					if (visId !== null) {
						$scope.selectedCallTree[0].parentVisId = visId;
					}
				}
				if (event.currentTarget.id === "4") {
					$scope.selectedDataType = convertListElementsToRequiredObject(temporaryList);
				}
			};

			/**
			 * Managing Node when we can select multiple elements
			 * @param  {[type]} event           [description]
			 * @param  {[type]} listOpenedNodes [description]
			 * @return {[type]}                 [description]
			 */
			var manageSelectedNode = function(event, listOpenedNodes) {
				if ($scope.checkboxState === "false") {
					manageOnlySelectedNodes(event, listOpenedNodes);
				} else {
					manageTopSelectedNodes(event, listOpenedNodes);
				}
			};

			function manageOnlySelectedNodes(event, listOpenedNodes) {
				var temporaryList = [];
				angular.forEach(listOpenedNodes, function(nodeObject) {
					if (nodeObject.state.selected === true && nodeObject.parent !== null && nodeObject.parent !== "#") {
						temporaryList.push(nodeObject);
					}
				});
				manageSelectedDimensionsToRequiredObject(temporaryList, event.currentTarget.id);
			}

			function manageTopSelectedNodes(event, listOpenedNodes) {
				clearProperDimensions(event.currentTarget.id);

				var temporaryList = [];
				angular.forEach(listOpenedNodes, function(nodeObject) {
					if (nodeObject.state.selected === true && nodeObject.parent !== null && nodeObject.parent !== "#") {
						temporaryList.push(nodeObject);
					}
				});

				var temporaryListToDelete = [];
				for (var i = 0; i < temporaryList.length; i++) {
					var findedChildren = findChildrenInList(temporaryList[i], temporaryList);
					temporaryListToDelete = temporaryListToDelete.concat(findedChildren);
				}
				var topSelected = temporaryList.diff(temporaryListToDelete);

				manageSelectedDimensionsToRequiredObject(topSelected, event.currentTarget.id);
			}

			function manageSelectedDimensionsToRequiredObject(selected, type) {
				switch (type) {
					case "1":
						$scope.selectedCcTree = convertListElementsToRequiredObject(selected, 2);
						break;
					case "2":
						$scope.selectedExpTree = convertListElementsToRequiredObject(selected, 1);
						break;
					case "3":
						$scope.selectedCallTree = convertListElementsToRequiredObject(selected, 3);
						break;
					case "4":
						$scope.selectedDataType = convertListElementsToRequiredObject(selected);
						break;
				}
			}

			function clearProperDimensions(type) {
				switch (type) {
					case "1":
						$scope.selectedCcTree = [];
						break;
					case "2":
						$scope.selectedExpTree = [];
						break;
					case "3":
						$scope.selectedCallTree = [];
						break;
					case "4":
						$scope.selectedDataType = [];
						break;
				}
			}

			/**
			 * Create required node object
			 * @param  {[type]} list [description]
			 * @param  {[type]} type [description]
			 * @return {[type]}      [description]
			 */
			function convertListElementsToRequiredObject(list, type) {
				var readyList = [];
				var object;
				for (var i = 0; i < list.length; i++) {
					var id = parseInt(list[i].id.split("/")[1]);
					var structureElementVisId = list[i].original.textNode;
					var structureElementDescription = list[i].original.description;
					if (structureElementVisId === null) {
						structureElementVisId = list[i].text;
					}
					if (type === 3 && list[i].state.leaf === true) {
						var jsTree = $("#3");
						var tree = $(jsTree).jstree();
						var parent = tree.get_node(list[i].parent);
						object = {
							structureElementId: id,
							structureElementVisId: structureElementVisId,
							structureElementDescription: structureElementDescription,
							dimension: createDimensionObject($scope.dimensionForModel, type),
							structureId: list[i].original.structureId,
							parentVisId: parent.original.textNode
						};
					} else {
						object = {
							structureElementId: id,
							structureElementVisId: structureElementVisId,
							structureElementDescription: structureElementDescription,
							dimension: createDimensionObject($scope.dimensionForModel, type),
							structureId: list[i].original.structureId
						};
					}
					readyList.push(object);
				}
				//console.log("READY LIST", readyList);
				return readyList;
			}

			var findParentVisId = function(parentId, availableTreeNodes) {
				var parentObject = null;
				angular.forEach(availableTreeNodes, function(treeNode) {
					if (treeNode.parent !== null) {
						if (parentId === treeNode.id) {
							parentObject = treeNode;
							return false;
						}
					}
				});
				return parentObject.original.textNode;
			};

			/**
			 * Create required dimension object
			 * @param  {[type]} dimensionForModel [description]
			 * @param  {[type]} type              [description]
			 * @return {[type]}                   [description]
			 */
			var createDimensionObject = function(dimensionForModel, type) {
				var list = [];
				var dimensionObject;
				for (var i = 0; i < dimensionForModel.length; i++) {
					if (dimensionForModel[i].type == type) {
						dimensionObject = {
							dimensionDescription: dimensionForModel[i].dimensionDescription,
							dimensionId: dimensionForModel[i].dimensionId,
							dimensionVisId: dimensionForModel[i].dimensionVisId,
							type: type,
						};

					}

				}
				return dimensionObject;

			};

			/**
			 * Find node children
			 * @param  {[type]} element [description]
			 * @param  {[type]} list    [description]
			 * @return {[type]}         [description]
			 */

			function findChildrenInList(element, list) {
				var children = [];
				for (var i = 0; i < list.length; i++) {
					if (element.id == list[i].parent) {
						children.push(list[i]);
					}
				}
				return children;
			}
			/**
			 * This method is called when we have to return the difference between the two arrays
			 * @param  {[type]} a [description]
			 * @return {[type]}   [description]
			 */
			Array.prototype.diff = function(a) {
				return this.filter(function(i) {
					return a.indexOf(i) < 0;
				});
			};
		}
	}

})();