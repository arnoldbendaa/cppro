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
		.controller('AlignmentController', AlignmentController);

	/* @ngInject */
	function AlignmentController($scope, SpreadSheetService) {
		$scope.hAlign = "";
		$scope.vAlign = "";

		$scope.manageHorizontalAlignment = manageHorizontalAlignment;
		$scope.manageVerticalAlignment = manageVerticalAlignment;
		$scope.manageWrapText = manageWrapText;
		$scope.manageIndentation = manageIndentation;
		$scope.clearIndentation = clearIndentation;
		$scope.mergeCells = mergeCells;

		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/


		function activate() {
			onSelectionChanging();
			onMergeCells();
		}

		function manageHorizontalAlignment(param) {
			if ($scope.hAlign == param) {
				return;
			}
			$scope.hAlign = param;

			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var hAlign = GC.Spread.Sheets.HorizontalAlign.left;
			if (param == "center") hAlign = GC.Spread.Sheets.HorizontalAlign.center;
			if (param == "right") hAlign = GC.Spread.Sheets.HorizontalAlign.right;
			if (param == "general") hAlign = GC.Spread.Sheets.HorizontalAlign.general;

			var sels = sheet.getSelections();
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());

				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						var cell = sheet.getCell(i, j);
						var isLocked = cell.locked();
						cell.locked(false);

						var style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
						style.hAlign = hAlign;

						cell.locked(isLocked);
					}
				}
			}
			sheet.resumePaint ();
		}

		function manageVerticalAlignment(param) {
			if ($scope.vAlign == param) {
				return;
			}
			$scope.vAlign = param;

			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var vAlign = GC.Spread.Sheets.VerticalAlign.top;
			if (param == "center") vAlign = GC.Spread.Sheets.VerticalAlign.center;
			if (param == "bottom") vAlign = GC.Spread.Sheets.VerticalAlign.bottom;

			var sels = sheet.getSelections();
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());

				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						var cell = sheet.getCell(i, j);
						var isLocked = cell.locked();
						cell.locked(false);

						var style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
						style.vAlign = vAlign;

						cell.locked(isLocked);
					}
				}
			}
			sheet.resumePaint ();
		}

		function manageWrapText() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var sels = sheet.getSelections();
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());

				var wordWrap = sheet.getCell(sel.row, sel.col, GC.Spread.Sheets.SheetArea.viewport).wordWrap();
				if (wordWrap === true) {
					wordWrap = false;
				} else {
					wordWrap = true;
				}

				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						var cell = sheet.getCell(i, j);
						var isLocked = cell.locked();
						cell.locked(false);

						var style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
						style.wordWrap = wordWrap;

						cell.locked(isLocked);
					}
				}
			}

			sheet.resumePaint ();
		}

		function manageIndentation(param) {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var sels = sheet.getSelections();
			var offset = 1;
			if (param == "decrease") offset = -1;

			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
				for (var i = 0; i < sel.rowCount; i++) {
					for (var j = 0; j < sel.colCount; j++) {
						var indent = sheet.getCell(i + sel.row, j + sel.col, GC.Spread.Sheets.SheetArea.viewport).textIndent();
						if (isNaN(indent)) indent = 0;
						sheet.getCell(i + sel.row, j + sel.col, GC.Spread.Sheets.SheetArea.viewport).textIndent(indent + offset);
					}
				}
			}
			sheet.resumePaint ();
		}

		function clearIndentation() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var sels = sheet.getSelections();
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
				for (var i = 0; i < sel.rowCount; i++) {
					for (var j = 0; j < sel.colCount; j++) {
						sheet.getCell(i + sel.row, j + sel.col, GC.Spread.Sheets.SheetArea.viewport).textIndent(0);
					}
				}
			}
			sheet.resumePaint ();
		}

		function mergeCells() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var sels = sheet.getSelections();
			var hasSpan = false;
			var sel;

			for (var n = 0; n < sels.length; n++) {
				sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
				if (sheet.getSpans(sel, GC.Spread.Sheets.SheetArea.viewport).length > 0) {
					for (var i = 0; i < sel.rowCount; i++) {
						for (var j = 0; j < sel.colCount; j++) {
							sheet.removeSpan(i + sel.row, j + sel.col);
						}
					}
					$scope.manageHorizontalAlignment('general');
					hasSpan = true;
				}
			}
			if (!hasSpan) {
				for (n = 0; n < sels.length; n++) {
					sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
					sheet.addSpan(sel.row, sel.col, sel.rowCount, sel.colCount);
				}
				$scope.manageHorizontalAlignment('center');
			}

			sheet.resumePaint ();
		}

		function onSelectionChanging() {
			$scope.$on("SpreadSheetController:SelectionChanging", function() {
				var sheet = $scope.spread.getActiveSheet();

				var hAlign = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true).hAlign();
				var vAlign = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true).vAlign();

				switch (hAlign) {
					case GC.Spread.Sheets.HorizontalAlign.left:
						$scope.hAlign = "left";
						break;
					case GC.Spread.Sheets.HorizontalAlign.center:
						$scope.hAlign = "center";
						break;
					case GC.Spread.Sheets.HorizontalAlign.right:
						$scope.hAlign = "right";
						break;
					case GC.Spread.Sheets.HorizontalAlign.general:
						$scope.hAlign = "";
						break;
				}

				switch (vAlign) {
					case GC.Spread.Sheets.VerticalAlign.top:
						$scope.vAlign = "top";
						break;
					case GC.Spread.Sheets.VerticalAlign.center:
						$scope.vAlign = "center";
						break;
					case GC.Spread.Sheets.VerticalAlign.bottom:
						$scope.vAlign = "bottom";
						break;
				}

				$scope.$apply();
			});
		}

		function onMergeCells() {
			$scope.$on("AlignmentController:mergeCells", function() {
				mergeCells();
			});
		}
	}

})();