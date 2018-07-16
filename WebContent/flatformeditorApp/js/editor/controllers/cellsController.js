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
		.module('flatFormEditorApp.editor')
		.controller('CellsController', CellsController);

	/* @ngInject */
	function CellsController($scope, $modal, CoreCommonsService) {

		$scope.insertRowsColumns = insertRowsColumns;
		$scope.insertRow = insertRow;
		$scope.deleteRow = deleteRow;
		$scope.insertColumn = insertColumn;
		$scope.deleteColumn = deleteColumn;
		$scope.hideRows = hideRows;
		$scope.showRows = showRows;
		$scope.hideColumns = hideColumns;
		$scope.showColumns = showColumns;

		/************************************************** IMPLEMENTATION *************************************************************************/

		function updateWorkbook(action, index, howMany) {
			var flatForm = $scope.flatForm;
			if (!flatForm || !flatForm.xmlForm || !flatForm.xmlForm.worksheets) {
				return;
			}
			var sheet = $scope.spread.getActiveSheet();
			var workbookSheet = CoreCommonsService.findElementByKey(flatForm.xmlForm.worksheets, sheet.name(), "name");
			if (!workbookSheet) {
				return;
			}
			var cells = workbookSheet.cells;
			for (var i = 0; i < cells.length; i++) {
				switch (action) {
					case "insertRow":
						if (cells[i].row >= index) {
							cells[i].row = cells[i].row + howMany;
						}
						break;
					case "deleteRow":
						if (cells[i].row == index) {
							var param = cells.indexOf(cells[i]);
							cells.splice(param, 1);
						} else if (cells[i].row > index) {
							cells[i].row--
						}
						break;
					case "insertColumn":
						if (cells[i].column >= index) {
							cells[i].column = cells[i].column + howMany;
						}
						break;
					case "deleteColumn":
						if (cells[i].column == index) {
							var param = cells.indexOf(cells[i]);
							cells.splice(param, 1);
						} else if (cells[i].column > index) {
							cells[i].column--
						}
						break;
				}
			}
		}

		function insertRowsColumns() {
			var modalInstance = $modal.open({
				template: '<insert-rows-columns id="insert-rows-columns"></insert-rows-columns>',
				windowClass: 'softpro-modals no-resize',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.$on('InsertRowsColumns:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		};

		function insertRow(rowsCount) {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendCalcService();
			sheet.suspendPaint ();
			if (rowsCount === undefined) {
				rowsCount = 1
			}
			sheet.addRows(sheet.getActiveRowIndex(), rowsCount);
			sheet.resumePaint();
			sheet.resumeCalcService();

			updateWorkbook("insertRow", sheet.getActiveRowIndex(), rowsCount);
		};

		function deleteRow() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			sheet.deleteRows(sheet.getActiveRowIndex(), 1);
			sheet.resumePaint();

			updateWorkbook("deleteRow", sheet.getActiveRowIndex());
		};

		function insertColumn(columnsCount) {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendCalcService();
			sheet.suspendPaint ();
			if (columnsCount === undefined) {
				columnsCount = 1;
			}
			sheet.addColumns(sheet.getActiveColumnIndex(), columnsCount);
			sheet.resumePaint();
			sheet.resumeCalcService();

			updateWorkbook("insertColumn", sheet.getActiveColumnIndex(), columnsCount);
		};

		function deleteColumn() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			sheet.deleteColumns(sheet.getActiveColumnIndex(), 1);
			sheet.resumePaint();

			updateWorkbook("deleteColumn", sheet.getActiveColumnIndex());
		};

		function hideRows() {
			toggleVisibility("row", false);
		};

		function showRows() {
			toggleVisibility("row", true);
		};

		function hideColumns() {
			toggleVisibility("col", false);
		};

		function showColumns() {
			toggleVisibility("col", true);
		};

		function toggleVisibility(what, visibility) {
			var sheet = $scope.spread.getActiveSheet();
			var sels = sheet.getSelections();
			if (sels.length === 0) {
				return;
			}
			var sel = sels[0];
			sheet.suspendPaint ();
			for (var i = sel[what]; i < sel[what] + sel[what + "Count"]; i++) {
				if (what == "col") {
					sheet.getColumn(i).visible(visibility);
				} else {
					sheet.getRow(i).visible(visibility);
				}
			}
			sheet.resumePaint();
		}



		/************************************************** EVENTS *********************************************************************/



		$scope.$on("InsertRowsColumns:insertRowsColumns", function(e, data) {
			$scope.insertRow(data.rows);
			$scope.insertColumn(data.columns);
		});

		$scope.$on("CellsController:insertRow", function() {
			$scope.insertRow();
		});
		$scope.$on("CellsController:deleteRow", function() {
			$scope.deleteRow();
		});
		$scope.$on("CellsController:insertColumn", function() {
			$scope.insertColumn();
		});
		$scope.$on("CellsController:deleteColumn", function() {
			$scope.deleteColumn();
		});
		$scope.$on("CellsController:hideRows", function() {
			$scope.hideRows();
		});
		$scope.$on("CellsController:showRows", function() {
			$scope.showRows();
		});
		$scope.$on("CellsController:hideColumns", function() {
			$scope.hideColumns();
		});
		$scope.$on("CellsController:showColumns", function() {
			$scope.showColumns();
		});
	}
})();