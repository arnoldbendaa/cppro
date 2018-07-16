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
		.controller('FinanceCubeChooserController', FinanceCubeChooserController);


	/* @ngInject */
	function FinanceCubeChooserController($rootScope, $scope, $compile, CoreCommonsService) {
		var collectionView = new wijmo.collections.CollectionView();
		var toFilter; // apply filter (applied on a 500 ms timeOut)

		$scope.mainCheckboxSelected = false;
		$scope.chooserInfo = angular.isDefined($scope.info) ? $scope.info : "";

		$scope.ctx = {
			//flex: null,
			filter: '',
			data: collectionView
		};

		$scope.isSelectedFinanceCube = isSelectedFinanceCube;
		$scope.selectFinanceCube = selectFinanceCube;
		$scope.selectAllFinanceCubes = selectAllFinanceCubes;

		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			onAvailableFinanceCubesChanged();
			onFlexLoaded();
			onFilterLoaded();
		}

		function onFlexLoaded() {
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

		function onFilterLoaded() {
			if (!$scope.isFilter) {
				return;
			}
			$scope.$watch('ctx.filter', function() {
				if (toFilter) {
					clearTimeout(toFilter);
				}
				toFilter = setTimeout(function() {
					toFilter = null;
					if ($scope.ctx.flex) {
						$scope.selectedFinanceCubes.length = 0;

						var cv = $scope.ctx.flex.collectionView;
						if (cv) {
							if (cv.filter != filterFunction) {
								cv.filter = filterFunction;
							} else {
								cv.refresh();
							}
							$scope.$apply('ctx.flex.collectionView');
						}
					}
				}, 500);
			});
		}

		function onAvailableFinanceCubesChanged() {
			$scope.$watch('availableFinanceCubes.length', function() {
				if (angular.isDefined($scope.availableFinanceCubes) && $scope.availableFinanceCubes.length > 0) {
					collectionView.sourceCollection = $scope.availableFinanceCubes;
				}
			});
		}

		function isSelectedFinanceCube(financeCubeVisId) {
			if (angular.isUndefined($scope.selectedFinanceCubes)) {
				return false;
			}
			var financeCube = CoreCommonsService.findElementByKey($scope.availableFinanceCubes, financeCubeVisId, 'financeCubeVisId');
			var index = $scope.selectedFinanceCubes.indexOf(financeCube);
			if (index == -1) { // not selected
				return false;
			} else { // selected
				return true;
			}
		}

		function selectFinanceCube(financeCubeVisId) {
			if (angular.isUndefined($scope.selectedFinanceCubes)) {
				return false;
			}
			var financeCube = CoreCommonsService.findElementByKey($scope.availableFinanceCubes, financeCubeVisId, 'financeCubeVisId');
			var index = $scope.selectedFinanceCubes.indexOf(financeCube);
			if (index === -1) {
				$scope.selectedFinanceCubes.push(financeCube);
			} else {
				$scope.selectedFinanceCubes.splice(index, 1);
			}
		}

		function selectAllFinanceCubes() {
			$scope.selectedFinanceCubes.length = 0;
			if ($scope.mainCheckboxSelected) {
				angular.forEach($scope.availableFinanceCubes, function(financeCube) {
					selectFinanceCube(financeCube.financeCubeVisId);
				});
			}
			$scope.$apply('selectedFinanceCubes');
		}

		// formatter to add checkboxes to boolean columns http://wijmo.com/topic/adding-a-new-checkbox-in-header-column-name-of-the-flex-grid/
		function itemFormatter(panel, r, c, cell) {
			var flex = panel.grid;
			var col;
			if (panel.cellType == wijmo.grid.CellType.ColumnHeader) {
				col = flex.columns[c];

				if (col.name == "checked") {
					// prevent sorting on click
					col.allowSorting = false;
					// create and initialize checkbox
					cell.innerHTML = '<input type="checkbox"> ' + cell.innerHTML;
					var cb = cell.firstChild;
					cb.checked = $scope.mainCheckboxSelected; //cnt > 0;

					// apply checkbox value to cells
					cb.addEventListener('click', function(e) {
						flex.beginUpdate();
						$scope.mainCheckboxSelected = !$scope.mainCheckboxSelected;
						$scope.selectAllFinanceCubes();
						flex.endUpdate();
					});
				}
			} else if (panel.cellType == wijmo.grid.CellType.Cell) {
				col = panel.columns[c];
				var html = cell.innerHTML;
				if (col.name == "checked") {
					var financeCubeVisId = flex.getCellData(r, 0, true);
					var checked = ($scope.isSelectedFinanceCube(financeCubeVisId)) ? "checked" : "";
					html = '<input type="checkbox"  ' + checked + ' ng-click="selectFinanceCube(\'' + financeCubeVisId + '\'); $event.stopPropagation();" />';
					if (html != cell.innerHTML) {
						cell.innerHTML = html;
						$compile(cell)($scope);
					}
				}
			}
		}

		function filterFunction(item) {
			var f = $scope.ctx.filter;
			if (f && item) {

				// split string into terms to enable multi-field searches such as 'us gadget red'
				var terms = f.toUpperCase().split(' ');

				// look for any term in any string field
				for (var i = 0; i < terms.length; i++) {
					var termFound = false;
					for (var key in item) {
						var value = item[key];
						if (angular.isString(value) && value.toUpperCase().indexOf(terms[i]) > -1) {
							termFound = true;
							break;
						}
					}

					// fail if any of the terms is not found
					if (!termFound) {
						return false;
					}
				}
			}

			// include item in view
			return true;
		}
	}

})();