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
		.controller('FormatController', FormatController);

	/* @ngInject */
	function FormatController($rootScope, $scope, FormatService) {
		$scope.categories = FormatService.getCategories();
		$scope.formats = FormatService.getFormats();

		$scope.currentFormat = {
			data: undefined
		};

		$scope.changeFormatTo = changeFormatTo;
		$scope.formatCells = formatCells;
		$scope.changeFormat = changeFormat;

		activate();

		function activate() {
			onSelectionChanging();
		}


		/************************************************** IMPLEMENTATION *************************************************************************/



		function onSelectionChanging() {
			// Get category and format for active cell.
			$scope.$on("SpreadSheetController:SelectionChanging", function() {
				var sheet = $scope.spread.getActiveSheet();
				var cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport);

				$scope.currentFormat.data = undefined;
				var format = cell.formatter();
				if (format) {
					format = format.replace(/\\/g, '');
					$scope.currentFormat.data = format;
				}
				$scope.currentCategory = FormatService.getCategoryForFormat(format);
				$scope.$apply();
			});
		}

		// Set predefined styles like: coma style, percent style
		function changeFormatTo(format) {
			switch (format) {
				case "comaStyle":
					$scope.currentCategory = "Number";
					$scope.changeFormat("changeCategoryAndFormat", 11);
					break;
				case "percent":
					$scope.currentCategory = "Percent";
					$scope.changeFormat("changeCategoryAndFormat", 0);
					break;
			}
		}

		// Main function. Go to every selected cell and change its format (set predefined format, increase or decrease decimal).
		function changeFormat(whatToDo, parameter) {
			$scope.spread.suspendPaint ();
			var sheet = $scope.spread.getActiveSheet();
			var sels = sheet.getSelections();
			var cell, isLocked, style, s;
			for (s = 0; s < sels.length; s++) {
				var selection = sels[s];
				var i = (selection.row > -1) ? selection.row : 0;
				for (i; i < selection.row + selection.rowCount; i++) {
					var j = (selection.col > -1) ? selection.col : 0;
					for (j; j < selection.col + selection.colCount; j++) {
						cell = sheet.getCell(i, j);
						isLocked = cell.locked();
						cell.locked(false);
						style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
						switch (whatToDo) {
							case "changeCategoryAndFormat":
								changeCategoryAndFormat(parameter);
								break;
							case "increaseDecimal":
								increaseDecimal(cell);
								break;
							case "decreaseDecimal":
								decreaseDecimal(cell);
								break;
						}
						style.formatter = $scope.currentFormat.data;
						cell.locked(isLocked);
					}
				}
			}
			$scope.spread.resumePaint();
		}

		// Change cell category and format. 
		function changeCategoryAndFormat(formatIndex) {
			if (formatIndex !== undefined) {
				// Set selected format for current category (i.e. format from predefined formats).
				$scope.currentFormat.data = $scope.formats[$scope.currentCategory][formatIndex];
			} else {
				// Set first available format for current category.
				$scope.currentFormat.data = $scope.formats[$scope.currentCategory][0];
			}
		}

		// Prepare cell format to increase decimal.
		function increaseDecimal(cell) {
			var format2;
			var format = cell.formatter();
			if (format) {
				format = format.replace(/\\/g, '');
				// Try to replace 0.0... to 0.00... (Add one 0 after dot).
				format2 = format.replace(/0\.0/g, "0.00");
				var newFormat = "";
				// If there wasn't decimal part of format, change 0 to 0.0
				if (format == format2) {
					newFormat = format.replace(/0/g, "0.0");
				} else {
					newFormat = format2;
				}
				$scope.currentFormat.data = newFormat;
			}
		}

		// Prepare cell format to increase decimal.
		function decreaseDecimal(cell) {
			var format2;
			var format = cell.formatter();
			if (format) {
				format = format.replace(/\\/g, '');
				// Try to replace 0.00... to 0.0... (Remove one 0 after dot).
				format2 = format.replace(/0\.00/g, "0.0");
				var newFormat = "";
				// If there is only one 0 after dot, change 0.0 to 0
				if (format == format2) {
					newFormat = format.replace(/0\.0/g, "0");
				} else {
					newFormat = format2;
				}
				$scope.currentFormat.data = newFormat;
			}
		}

		function formatCells() {
			$rootScope.$broadcast("TableBuilderController:formatCells", "formatCells");
		}
	}

})();