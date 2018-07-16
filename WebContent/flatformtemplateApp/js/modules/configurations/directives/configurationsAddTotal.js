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
		.directive('configurationsAddTotal', configurationsAddTotal);

	/* @ngInject */
	function configurationsAddTotal(ConfigurationsService, CoreCommonsService) {
		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'modules/configurations/views/configurationsAddTotal.html',
			scope: {
				totals: '=',
				createdSheets: '=',
				dimensions: '=',
				indexTotal: '=indexTotal',
				configurationEdited: '='
			},
			replace: true,
			controller: configurationsAddTotalController
		};

		function configurationsAddTotalController($rootScope, $scope, $timeout, $compile) {
			// parameters to resize modal
			var modalDialog = $(".configurations-add-total").closest(".modal-dialog");
			var elementToResize = $(".data-dimensions");
			var additionalElementsToCalcResize = [$(".getToCalc1"), 55];
			$scope.cookieName = "flatFormTemplate_modal_configurationsAddTotal";
			$timeout(function() { // timeout is necessary to pass asynchro
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
			}, 0);
			$scope.dimensionsCollection = new wijmo.collections.CollectionView();
			$scope.ctx = {};
			var sortBy = new wijmo.collections.SortDescription("modelVisId", true);
			$scope.total = {};
			$scope.isDim = false;
			$scope.resizedColumn = resizedColumn;
			$scope.sortedColumn = sortedColumn;
			$scope.isSelectedDim = isSelectedDim;
			$scope.selectDim = selectDim;
			$scope.selectAllDims = selectAllDims;
			self.findElementByTwoKeys = findElementByTwoKeys;
			$scope.close = close;
			$scope.saveTotalToTable = saveTotalToTable;
			$scope.validationName = validationName;
			activate();

			/************************************************** IMPLEMENTATION *************************************************************************/

			function activate() {
				// try to resize and move modal based on the cookie
				CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
				if ($scope.dimensions === null) {
					$scope.dimensions = [];
				}
				$scope.dimensionsCollection.sourceCollection = $scope.dimensions;
				$scope.ctx = {
					flex: null,
					filter: '',
					data: $scope.dimensionsCollection
				};
				$scope.ctx.data.sortDescriptions.push(sortBy);

				$scope.total = {
					totalElements: []
				};
			}

			function resizedColumn(sender, args) {
				CoreCommonsService.resizedColumn(args, $scope.cookieName);
			};

			function sortedColumn(sender, args) {
				CoreCommonsService.sortedColumn(args, $scope.cookieName);
			};

			function isSelectedDim(dimensionVisId, modelVisId) {
				var dim = self.findElementByTwoKeys($scope.dimensions, dimensionVisId, 'dimensionVisId', modelVisId, 'modelVisId');
				if ($scope.total.totalElements === null) {
					$scope.total.totalElements = [];
				}
				var index = -1;
				for (var i = 0; i < $scope.total.totalElements.length; i++) {
					if (dim.dimensionVisId == $scope.total.totalElements[i].dimensionVisId && dim.modelVisId == $scope.total.totalElements[i].modelVisId) {
						index = 50000;
					}
				}
				if (index == -1) { // not selected
					return false;
				} else { // selected
					return true;
				}
			};

			function selectDim(dimensionVisId, modelVisId) {
				var dim = self.findElementByTwoKeys($scope.dimensions, dimensionVisId, 'dimensionVisId', modelVisId, 'modelVisId');
				var index = -1;
				for (var i = 0; i < $scope.total.totalElements.length; i++) {
					if (dim.dimensionVisId == $scope.total.totalElements[i].dimensionVisId && dim.modelVisId == $scope.total.totalElements[i].modelVisId) {
						index = i;
					}
				}
				if (index === -1) {
					$scope.total.totalElements.push(dim);
				} else {
					$scope.total.totalElements.splice(index, 1);
				}
			};

			function selectAllDims() {
				$scope.total.totalElements.length = 0;
				if ($scope.mainCheckboxSelected) {
					// select all
					angular.forEach($scope.dimensions, function(dim) {
						$scope.selectDim(dim.dimensionVisId, dim.modelVisId);
					});
				}
			};

			/**
			 * Returns element from collection which has the same keys as our current element.
			 * Current element must be reference (not copy) to one of the objects from collection.
			 * It's important, 'cause if it will be copy, Angular won't link current object with object in collection (and i.e. in dropdown list).
			 * @param  {Object} elements [list of elements which builds collection (i.e. dropdown list)]
			 * @param  {Integer} keyValue1 [first key of current element]
			 * @param  {String} keyName1 [first name of field in object - "id", "index", "name", "PK" or sth else]
			 * @param  {Integer} keyValue2 [second key of current element]
			 * @param  {String} keyName2 [second name of field in object - "id", "index", "name", "PK" or sth else]
			 * @return {Object}          [description]
			 */
			function findElementByTwoKeys(elements, keyValue1, keyName1, keyValue2, keyName2, returnFirstIfNothingMatches) {
				var findedElement = null;
				if (typeof keyValue1 === "undefined" || typeof keyValue2 === "undefined") {
					return findedElement;
				}
				angular.forEach(elements, function(element) {
					if (element[keyName1] == keyValue1 && element[keyName2] == keyValue2) {
						findedElement = element;
						return;
					}
				});
				if (findedElement === null && elements.length > 0 && returnFirstIfNothingMatches) {
					findedElement = elements[0];
				}
				return findedElement;
			};


			// formatter to add checkboxes to boolean columns http://wijmo.com/topic/adding-a-new-checkbox-in-header-column-name-of-the-flex-grid/
			function itemFormatter(panel, r, c, cell) {
				var flex = panel.grid;
				if (panel.cellType == wijmo.grid.CellType.ColumnHeader) {
					var col = flex.columns[c];

					if (col.name == "checked") {
						// prevent sorting on click
						col.allowSorting = false;
						cell.innerHTML = '<input type="checkbox"> ' + cell.innerHTML;
						var cb = cell.firstChild;
						cb.checked = $scope.mainCheckboxSelected;
						// apply checkbox value to cells
						cb.addEventListener('click', function(e) {
							flex.beginUpdate();
							$scope.mainCheckboxSelected = !$scope.mainCheckboxSelected;
							$scope.selectAllDims();
							flex.endUpdate();
						});
					}
				} else if (panel.cellType == wijmo.grid.CellType.Cell) {
					var col = panel.columns[c],
						html = cell.innerHTML;
					if (col.name == "checked") {
						var dimensionVisId = flex.getCellData(r, 0, true);
						var modelVisId = flex.getCellData(r, 1, true);
						var checked = ($scope.isSelectedDim(dimensionVisId, modelVisId)) ? "checked" : "";
						html = '<input type="checkbox" name="externalSystemCheckbox" ' + checked + ' ng-click="selectDim(\'' + dimensionVisId + '\', \'' + modelVisId + '\'); $event.stopPropagation();" />';
						if (html != cell.innerHTML) {
							cell.innerHTML = html;
							$compile(cell)($scope);
						}
					}
				}
			};

			if ($scope.indexTotal === undefined) {
				$scope.headTitle = 'Add Total';
			} else if ($scope.createdSheets.sourceCollection[$scope.indexTotal].grandTotal === true) {
				$scope.isDim = true;
				$scope.headTitle = 'Edit Grand Total';
				$scope.inputName = $scope.createdSheets.sourceCollection[($scope.indexTotal)].sheetName;
			} else if (angular.isUndefined($scope.createdSheets.sourceCollection[$scope.indexTotal].totalUUID)) {
				$scope.isDim = true;
				$scope.headTitle = 'Edit Dimension';
				$scope.inputName = $scope.createdSheets.sourceCollection[($scope.indexTotal)].sheetName;
				$scope.dimensionVisId = $scope.createdSheets.sourceCollection[($scope.indexTotal)].dimensionVisId;
			} else {
				$scope.headTitle = 'Edit Total';
				$scope.inputName = $scope.createdSheets.sourceCollection[($scope.indexTotal)].sheetName;
				$scope.dimensionVisId = $scope.createdSheets.sourceCollection[($scope.indexTotal)].dimensionVisId;
				$scope.total.totalElements = angular.copy($scope.createdSheets.sourceCollection[($scope.indexTotal)].dimensionList);
			}


			function close() {
				$rootScope.$broadcast("ConfigurationsAddTotal:close");
			};

			function saveTotalToTable(name) {
				if ($scope.isDim) {
					$scope.createdSheets.sourceCollection[$scope.indexTotal].sheetName = name;
					$scope.createdSheets.refresh();
					$scope.isDim = false;

				} else {
					//Insert new total
					if ($scope.indexTotal === undefined) {
						var sheet = {
							dimensionList: $scope.total.totalElements,
							index: 1,
							totalUUID: null,
							hidden: false,
							sheetName: name
						};
						$scope.createdSheets.sourceCollection.unshift(sheet);
						$scope.totals.unshift(sheet);
						$scope.createdSheets.refresh();
					}
					//Update total
					else {
						var oldName = $scope.createdSheets.sourceCollection[$scope.indexTotal].sheetName;
						if (angular.isDefined($scope.createdSheets.sourceCollection[$scope.indexTotal].grandTotal)) {
							var grandTotal = $scope.createdSheets.sourceCollection[$scope.indexTotal].grandTotal;
						} else {
							var grandTotal = null;
						}
						var sheet = $scope.createdSheets.sourceCollection[$scope.indexTotal];
						sheet.sheetName = name;
						sheet.dimensionList = $scope.total.totalElements;
						$scope.createdSheets.refresh();
					}
				}
				CoreCommonsService.askIfReload = true;
			};

			function validationName(name) {
				$scope.isWrongName = false;
				$scope.isForbiddenChar = false;
				// Excel - sheet name forbidden characters : \ / ? * [ or ]
				if (name !== undefined && name.search(/[:\\/?*\[\]]/g) !== -1) {
					$scope.isForbiddenChar = true;
				} else {
					$scope.isForbiddenChar = false;
				}
				if (name !== undefined) {
					$scope.myForm.inputName.$invalid = false;
				}
				for (var i = 0; i < $scope.createdSheets.sourceCollection.length; i++) {
					if ($scope.createdSheets.sourceCollection[i].sheetName === name) {
						$scope.isWrongName = true;
						break;
					}
				}
			};

			/** ******************************************************* WATCHERS ******************************************************* */

			$scope.$watch('ctx.flex', function() {
				var flex = $scope.ctx.flex;
				if (flex) {
					flex.isReadOnly = true;
					flex.selectionMode = "Row";
					flex.headersVisibility = "Column";
					flex.itemFormatter = itemFormatter;
				}
			});
		}
	}
})();