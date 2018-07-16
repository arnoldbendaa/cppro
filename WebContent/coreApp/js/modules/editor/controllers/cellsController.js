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
		.module('coreApp.editor')
		.controller('CellsController', CellsController);

	/* @ngInject */
	function CellsController($scope, $modal) {
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
		}

		function insertRow(rowsCount) {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			if (rowsCount === undefined) {
				rowsCount = 1;
			}
			sheet.addRows(sheet.getActiveRowIndex(), rowsCount);
			sheet.resumePaint();
		}

		function deleteRow() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			sheet.deleteRows(sheet.getActiveRowIndex(), 1);
			sheet.resumePaint();
		}

		function insertColumn(columnsCount) {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			if (columnsCount === undefined) {
				columnsCount = 1;
			}
			sheet.addColumns(sheet.getActiveColumnIndex(), columnsCount);
			sheet.resumePaint();
		}

		function deleteColumn() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			sheet.deleteColumns(sheet.getActiveColumnIndex(), 1);
			sheet.resumePaint();
		}

		function hideRows() {
			toggleVisibility("row", false);
		}

		function showRows() {
			toggleVisibility("row", true);
		}

		function hideColumns() {
			toggleVisibility("col", false);
		}

		function showColumns() {
			toggleVisibility("col", true);
		}

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