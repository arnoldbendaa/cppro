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
		.controller('coreApp.formatCellEditorController', FormatCellEditorController);

	/* @ngInject */
	function FormatCellEditorController($scope, ColorService, FormatService, $timeout) {


		$scope.changeLocked = changeLocked;
		$scope.selectFormat = selectFormat;
		$scope.saveCellFormats = saveCellFormats;
		$scope.closeCellFormats = closeCellFormats;

		$scope.colors = ColorService.getColors();
		$scope.categories = FormatService.getCategories();
		$scope.formats = FormatService.getFormats();
		$scope.lineStyles = null;

		var spread = $scope.spread;
		var sheet = spread.getActiveSheet();
		var cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport);

		$scope.isBorderEdited = false;
		$scope.isFormatEdited = false;
		$scope.cellLocked = cell.locked();

		$scope.currentFormat = {
			data: undefined
		};
		var format = cell.formatter();
		$scope.currentCategory = null;

		$scope.borderTop = manageBorder(spread, "borderTop");
		$scope.borderRight = manageBorder(spread, "borderRight");
		$scope.borderBottom = manageBorder(spread, "borderBottom");
		$scope.borderLeft = manageBorder(spread, "borderLeft");


		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/


		function activate() {
			if ($scope.activetab !== undefined || $scope.activetab == "formatCell") {
				// TODO do it the other way
				// Set Format tab as active after page loading.
				$timeout(function() {
					$("#format-cell-editor .nav-tabs li[heading='Format'] a").click();
				}, 0);
			}

			if (format) {
				format = format.replace(/\\/g, '');
				$scope.currentFormat.data = format;
			}

			$scope.currentCategory = FormatService.getCategoryForFormat(format);
			$scope.lineStyles = manageLineStyles();
		}

		function changeLocked() {
			$scope.cellLocked = !$scope.cellLocked;
		}

		function selectFormat(format) {
			$scope.isFormatEdited = true;
			$scope.currentFormat.data = format;
			$scope.saveCellFormats();
		}

		function saveCellFormats() {
			var spread = $scope.spread;
			spread.suspendPaint ();

			var sheet = spread.getActiveSheet();
			var sels = sheet.getSelections();
			var selection = sels[0];

			var cell, isLocked, style, i, j;
			// set locked
			sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport).locked($scope.cellLocked);

			if ($scope.isBorderEdited) {
				// borderTop
				var borderTop = new GC.Spread.Sheets.LineBorder();
				borderTop.color = $scope.borderTop.color;
				borderTop.style = $scope.borderTop.style;
				for (i = selection.col; i < selection.col + selection.colCount; i++) {
					cell = sheet.getCell(selection.row, i);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(selection.row, i, GC.Spread.Sheets.SheetArea.viewport, true);
					style.borderTop = borderTop;
					cell.locked(isLocked);

					cell = sheet.getCell(selection.row - 1, i);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(selection.row - 1, i, GC.Spread.Sheets.SheetArea.viewport, true);
					if (style !== null) {
						style.borderBottom = borderTop;
					}
					cell.locked(isLocked);
				}

				// borderBottom
				var borderBottom = new GC.Spread.Sheets.LineBorder();
				borderBottom.color = $scope.borderBottom.color;
				borderBottom.style = $scope.borderBottom.style;
				for (i = selection.col; i < selection.col + selection.colCount; i++) {
					cell = sheet.getCell(selection.row + selection.rowCount - 1, i);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(selection.row + selection.rowCount - 1, i, GC.Spread.Sheets.SheetArea.viewport, true);
					style.borderBottom = borderBottom;
					cell.locked(isLocked);

					cell = sheet.getCell(selection.row + selection.rowCount, i);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(selection.row + selection.rowCount, i, GC.Spread.Sheets.SheetArea.viewport, true);
					if (style !== null) {
						style.borderTop = borderBottom;
					}
					cell.locked(isLocked);
				}

				// borderLeft
				var borderLeft = new GC.Spread.Sheets.LineBorder();
				borderLeft.color = $scope.borderLeft.color;
				borderLeft.style = $scope.borderLeft.style;
				for (i = selection.row; i < selection.row + selection.rowCount; i++) {
					// sheet.getCell(i, selection.col).borderLeft(borderLeft);
					// sheet.getCell(i, selection.col - 1).borderRight(borderLeft);
					cell = sheet.getCell(i, selection.col);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(i, selection.col, GC.Spread.Sheets.SheetArea.viewport, true);
					style.borderLeft = borderLeft;
					cell.locked(isLocked);

					cell = sheet.getCell(i, selection.col - 1);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(i, selection.col - 1, GC.Spread.Sheets.SheetArea.viewport, true);
					if (style !== null) {
						style.borderRight = borderLeft;
					}
					cell.locked(isLocked);
				}

				// borderRight
				var borderRight = new GC.Spread.Sheets.LineBorder();
				borderRight.color = $scope.borderRight.color;
				borderRight.style = $scope.borderRight.style;
				for (i = selection.row; i < selection.row + selection.rowCount; i++) {
					cell = sheet.getCell(i, selection.col + selection.colCount - 1);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(i, selection.col + selection.colCount - 1, GC.Spread.Sheets.SheetArea.viewport, true);
					style.borderRight = borderRight;
					cell.locked(isLocked);

					cell = sheet.getCell(i, selection.col + selection.colCount);
					isLocked = cell.locked();
					cell.locked(false);
					style = sheet.getStyle(i, selection.col + selection.colCount, GC.Spread.Sheets.SheetArea.viewport, true);
					if (style !== null) {
						style.borderLeft = borderRight;
					}
					cell.locked(isLocked);
				}
			}
			if ($scope.isFormatEdited || $scope.currentFormat.data !== cell.formatter()) {
				for (i = selection.row; i < selection.row + selection.rowCount; i++) {
					for (j = selection.col; j < selection.col + selection.colCount; j++) {
						cell = sheet.getCell(i, j);
						isLocked = cell.locked();
						cell.locked(false);
						style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
						style.formatter = $scope.currentFormat.data;
						cell.locked(isLocked);
					}
				}
			}
			spread.resumePaint();
			$scope.close();
		}

		function closeCellFormats() {
			$scope.close();
		}

		function manageBorder(spread, type) {
			var sheet = spread.getActiveSheet();
			var sels = sheet.getSelections();

			var borderStyle = 0;
			var borderColor;

			if (sels.length === 0) {
				borderColor = null;
				return {
					style: borderStyle,
					color: borderColor
				};
			}
			var selection = sels[0];
			var isBorderStyleEqual = true;
			var isBorderColorEqual = true;

			var border = manageCellBorderByType(spread, type, selection);
			if (border[type]) {
				borderStyle = border[type].style;
				borderColor = border[type].color;
			}

			var indexes = manageIndexesByType(type, selection);
			for (var i = indexes.startIndex; i < indexes.endIndex; i++) {
				var borderTmp = manageCellBordersByType(spread, type, selection, i);
				if (borderTmp[type]) {
					if (borderTmp[type].style !== borderStyle || borderTmp[type].style === undefined) {
						borderStyle = 0;
						isBorderStyleEqual = false;
					}
					if (borderTmp[type].color !== borderColor || borderTmp[type].color === undefined) {
						borderColor = null;
						isBorderColorEqual = false;
					}
				} else {
					borderStyle = 0;
					isBorderStyleEqual = false;
					borderColor = null;
					isBorderColorEqual = false;
				}
				if (isBorderColorEqual === false && isBorderStyleEqual === false) {
					break;
				}
			}
			return {
				style: borderStyle,
				color: borderColor
			};
		}

		function manageLineStyles() {
			var lineStyles = [];
			angular.forEach(GC.Spread.Sheets.LineStyle, function(key, value) {
				if (isNaN(key) === false) {
					lineStyles.push({
						id: parseInt(key),
						name: value
					});
				}
			});
			return lineStyles;
		}



		/************************************************** PRIVATE MEMBERS *********************************************************************/



		function manageCellBorderByType(spread, type, selection) {
			var border;
			switch (type) {
				case "borderTop":
				case "borderLeft":
					border = manageCellBorder(spread, selection.row, selection.col);
					break;
				case "borderBottom":
					border = manageCellBorder(spread, selection.row + selection.rowCount - 1, selection.col);
					break;
				case "borderRight":
					border = manageCellBorder(spread, selection.row, selection.col + selection.colCount - 1);
					break;
			}
			return border;
		}

		function manageCellBordersByType(spread, type, selection, i) {
			var borderTmp;
			switch (type) {
				case "borderTop":
					borderTmp = manageCellBorder(spread, selection.row, i);
					break;
				case "borderRight":
					borderTmp = manageCellBorder(spread, i, selection.col + selection.colCount - 1);
					break;
				case "borderBottom":
					borderTmp = manageCellBorder(spread, selection.row + selection.rowCount - 1, i);
					break;
				case "borderLeft":
					borderTmp = manageCellBorder(spread, i, selection.col);
					break;
			}
			return borderTmp;
		}

		function manageCellBorder(spread, row, col) {
			var data = {
				borderLeft: undefined,
				borderTop: undefined,
				borderRight: undefined,
				borderBottom: undefined
			};

			var sheet = spread.getActiveSheet();
			var cellTop = sheet.getCell(row - 1, col, GC.Spread.Sheets.SheetArea.viewport);
			if (cellTop) {
				data.borderTop = (cellTop.borderBottom() !== undefined) ? cellTop.borderBottom() : data.borderTop;
			}

			var cellBottom = sheet.getCell(row + 1, col, GC.Spread.Sheets.SheetArea.viewport);
			if (cellBottom) {
				data.borderBottom = (cellBottom.borderTop() !== undefined) ? cellBottom.borderTop() : data.borderBottom;
			}

			var cellLeft = sheet.getCell(row, col - 1, GC.Spread.Sheets.SheetArea.viewport);
			if (cellLeft) {
				data.borderLeft = (cellLeft.borderRight() !== undefined) ? cellLeft.borderRight() : data.borderLeft;
			}

			var cellRight = sheet.getCell(row, col + 1, GC.Spread.Sheets.SheetArea.viewport);
			if (cellRight) {
				data.borderRight = (cellRight.borderLeft() !== undefined) ? cellRight.borderLeft() : data.borderRight;
			}

			var cellCurrent = sheet.getCell(row, col, GC.Spread.Sheets.SheetArea.viewport);
			data.borderTop = (cellCurrent.borderTop() !== undefined) ? cellCurrent.borderTop() : data.borderTop;
			data.borderBottom = (cellCurrent.borderBottom() !== undefined) ? cellCurrent.borderBottom() : data.borderBottom;
			data.borderRight = (cellCurrent.borderRight() !== undefined) ? cellCurrent.borderRight() : data.borderRight;
			data.borderLeft = (cellCurrent.borderLeft() !== undefined) ? cellCurrent.borderLeft() : data.borderLeft;
			return data;
		}

		function manageIndexesByType(type, selection) {
			var startIndex = 0;
			var endIndex = 0;

			switch (type) {
				case "borderTop":
				case "borderBottom":
					startIndex = selection.col + 1;
					endIndex = selection.col + selection.colCount;
					break;
				case "borderRight":
				case "borderLeft":
					startIndex = selection.row + 1;
					endIndex = selection.row + selection.rowCount;
					break;
			}

			return {
				startIndex: startIndex,
				endIndex: endIndex
			};
		}
	}
})();