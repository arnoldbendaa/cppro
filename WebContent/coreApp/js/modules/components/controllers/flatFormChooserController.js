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
		.controller('FlatFormChooserController', FlatFormChooserController);

	/* @ngInject */
	function FlatFormChooserController($rootScope, $scope, $compile, CoreCommonsService) {
		var collectionView = new wijmo.collections.CollectionView();
		var toFilter; // apply filter (applied on a 500 ms timeOut)

		$scope.isFinanceCubeColumnVisible = $scope.isFinanceCubeColumn === true;
		$scope.isLastUpdatedColumnVisible = angular.isDefined($scope.isLastUpdatedColumn) ? $scope.isLastUpdatedColumn : true;
		$scope.chooserInfo = angular.isDefined($scope.info) ? $scope.info : "";
		$scope.mainCheckboxSelected = false;
		$scope.ctx = {
			//flex: null,
			filter: '',
			data: collectionView
		};
		if (angular.isDefined($scope.modalContext)) {
			$scope.modalContext = $scope.ctx;
		}

		$scope.isSelectedFlatForm = isSelectedFlatForm;
		$scope.selectFlatForm = selectFlatForm;
		$scope.selectAllFlatForms = selectAllFlatForms;

		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/



		function activate() {
			onAvailableFinanceCubesChanged();
			onFlexLoaded();
			onFilterLoaded();

			$scope.$on("MainMenu:refreshCopyTemplateTo", function() {
				var flex = $scope.ctx.flex;
				if (flex) {
					flex.refresh();
				}
			});
		}

		function isSelectedFlatForm(flatFormVisId) {
			if (angular.isUndefined($scope.selectedFlatForms)) {
				return false;
			}
			var flatForm = CoreCommonsService.findElementByKey($scope.availableFlatForms, flatFormVisId, 'flatFormVisId');
			var index = $scope.selectedFlatForms.indexOf(flatForm);
			if (index == -1) { // not selected
				return false;
			} else { // selected
				return true;
			}
		}

		function selectFlatForm(flatFormVisId) {
			if (angular.isUndefined($scope.selectedFlatForms)) {
				return false;
			}
			var flatForm = CoreCommonsService.findElementByKey($scope.availableFlatForms, flatFormVisId, 'flatFormVisId');
			var index = $scope.selectedFlatForms.indexOf(flatForm);
			if (index === -1) {
				if ($scope.isProfileModule != undefined){
				    $scope.selectedFlatForms = [];
				    $scope.selectForm.isFlatFormChooserChanged = false;
				    onFlexLoaded();
				}
			    $scope.selectedFlatForms.push(flatForm);
			    
			} else {
				$scope.selectedFlatForms.splice(index, 1);
				if ($scope.isProfileModule != undefined){
				    $scope.selectForm.isFlatFormChooserChanged = true;
				}
			}
		}

		function selectAllFlatForms() {
			$scope.selectedFlatForms.length = 0;
			if ($scope.mainCheckboxSelected) {
				angular.forEach($scope.availableFlatForms, function(flatForm) {
					$scope.selectFlatForm(flatForm.flatFormVisId);
				});
			}
		}


		/************************************************** PRIVATE MEMBERS *********************************************************************/



		function onAvailableFinanceCubesChanged() {
			$scope.$watch('availableFlatForms.length', function() {
				if (angular.isDefined($scope.availableFlatForms) && $scope.availableFlatForms.length > 0) {
					collectionView.sourceCollection = $scope.availableFlatForms;
				}
			});
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
			$scope.$watch('ctx.filter', function() {
				if (toFilter) {
					clearTimeout(toFilter);
				}
				toFilter = setTimeout(function() {
					toFilter = null;
					if ($scope.ctx.flex) {
						$scope.selectedFlatForms.length = 0;

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
	                if ($scope.isProfileModule === undefined){
	                    cell.innerHTML = '<input type="checkbox"> ' + cell.innerHTML;
    					var cb = cell.firstChild;
    					cb.checked = $scope.mainCheckboxSelected; //cnt > 0;
    
    					// apply checkbox value to cells
    					cb.addEventListener('click', function(e) {
    						flex.beginUpdate();
    						$scope.mainCheckboxSelected = !$scope.mainCheckboxSelected;
    						$scope.selectAllFlatForms();
    						flex.endUpdate();
    					});
                    }
				}
			} else if (panel.cellType == wijmo.grid.CellType.Cell) {
				col = panel.columns[c];
				var html = cell.innerHTML;
				if (col.name == "checked") {
					var flatFormVisId = flex.getCellData(r, 1, true);
					var checked = ($scope.isSelectedFlatForm(flatFormVisId)) ? "checked" : "";
					html = '<input type="checkbox"  ' + checked + ' ng-click="selectFlatForm(\'' + flatFormVisId + '\'); $event.stopPropagation();" />';
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