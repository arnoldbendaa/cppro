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
		.controller('ColorController', ColorController);

	/* @ngInject */
	function ColorController($scope, SpreadSheetService, ColorService) {
		$scope.colors = ColorService.getColors();
		$scope.changeFontColor = changeFontColor;
		$scope.changeBackgroundColor = changeBackgroundColor;



		function changeFontColor(color) {
			var selectedColor = (color !== null) ? color.toHexString() : null;
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			var sels = sheet.getSelections();
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());

				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						var cell = sheet.getCell(i, j);
						var isLocked = cell.locked();
						cell.locked(false);

						var style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
						style.foreColor = selectedColor;

						cell.locked(isLocked);
					}
				}
			}
			sheet.resumePaint();
		}

		function changeBackgroundColor(color) {
			var selectedColor = (color !== null) ? color.toHexString() : null;
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			var sels = sheet.getSelections();
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());

				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						var cell = sheet.getCell(i, j);
						var isLocked = cell.locked();
						cell.locked(false);

						var style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
						style.backColor = selectedColor;

						cell.locked(isLocked);
					}
				}
			}
			sheet.resumePaint();
		}

		$scope.$on("SpreadSheetController:SelectionChanging", function() {
			var sheet = $scope.spread.getActiveSheet();

			$scope.fontColor = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true).foreColor();
			$scope.backgroundColor = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true).backColor();

			$scope.$apply();
		});
	}


})();