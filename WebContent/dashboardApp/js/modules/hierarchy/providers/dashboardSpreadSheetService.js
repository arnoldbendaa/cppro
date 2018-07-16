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
		.module('dashboardFormApp.dashboardForm')
		.service('dashboardSpreadSheetService', dashboardSpreadSheetService);

	/* @ngInject */
	function dashboardSpreadSheetService() {
		var self = this;
		self.consts = {
			rows: "rows",
			cols: "columns",
			vals: "values",
			none: "none"
		};

		self.loadSpreadDocument = loadSpreadDocument;
		self.setRows = setRows;
		self.setColumns = setColumns;
		self.setValues = setValues;
		self.setUpCells = setUpCells;
		self.refreshSpreadValues = refreshSpreadValues;
		// self.setRanges = setRanges;
		// self.getRanges = getRanges;
		// self.setSpread = setSpread;

		// var showValidationError = showValidationError;
		self.blockSpreadEdition = blockSpreadEdition;
		self.getSelectedCellsAddresses = getSelectedCellsAddresses;
		self.addSelection = addSelection;
		self.setSelectionColor = setSelectionColor;

		self.getColumnNameByIndex = getColumnNameByIndex;
		// var setUpCell = setUpCell;
		// var getSelectedRanges = getSelectedRanges;
		// var formatValues = formatValues;
		// var getValuesFrom = getValuesFrom;
		// var getLabelsForRows = getLabelsForRows;
		// var getLabelsForCols = getLabelsForCols;

		// $scope.showOnlySpread = true;
		// $scope.setRowsFlag
		// $scope.setColumnsFlag
		// $scope.setValuesFlag
		// $scope.rowsRange[$scope.tabID]
		// $scope.valuesRange[$scope.tabID]
		// $scope.colsRange[$scope.tabID]
		// spread.grayAreaBackColor("#FCFDFD");

		function loadSpreadDocument(jsonForm, spread, range, tab) {
			if (jsonForm == null || jsonForm.trim() === "" || spread == null) {
				return;
			}
			//  else if (spreadIt != null) {
			// 	spread = spreadIt;
			// }

			spread.fromJSON(JSON.parse(jsonForm).spread);

			// block spread edition
			blockSpreadEdition();

			// bind spread selection
			for (var i = 0; i < spread.getSheetCount(); i++) {
				var sheet = spread.getSheet(i);
				sheet.bind(GC.Spread.Sheets.Events.SelectionChanged, function(sender, args) {
					var selection = getSelectedCellsAddresses();
					if (selection == null) {
						showValidationError('Chart will not be draw:', "Can't process multiselection.");
					} else if (range.selected === self.consts.rows) {
						if (selection.selectedRanges.rowsCount > 1) {
							showValidationError('Chart will not be draw:', 'Rows can not be multidimensional');
						} else {
							range.rows = selection;
							//$scope.$apply();
						}
					} else if (range.selected === self.consts.cols) {
						if (selection.selectedRanges.colsCount > 1) {
							showValidationError('Chart will not be draw:', 'Columns can not be multidimensional');
						} else {
							range.columns = selection;
							//$scope.$apply();
						}
					} else if (range.selected === self.consts.vals) {
						range.values = selection;
						//$scope.$apply();
					} else if (range.selected === self.consts.none) {
						range.selected = self.consts.none;
						setSelectionColor("rgba(0,0,0, 0.3)");
					}

				});
			}
		}

		function setRows(range, spread) {
			setSelectionColor("rgba(238,0,0, 0.3)");
			range.selected = self.consts.rows;
			//$scope.showOnlySpread = true;

			//if ($scope.rowsRange[tabName] != null) {
			dashboardSpreadSheetService.addSelection(range.rows, spread);
			//}
		}

		function setColumns(range, spread) {
			setSelectionColor("rgba(0,238,0, 0.3)");
			range.selected = self.consts.cols;
			//$scope.showOnlySpread = true;
			//if ($scope.colsRange[tabName] != null) {
			dashboardSpreadSheetService.addSelection(range.columns, spread);
			//}
		}

		function setValues(range, spread) {
			setSelectionColor("rgba(0,0,238, 0.3)");
			range.selected = self.consts.vals;

			//$scope.showOnlySpread = true;
			//if ($scope.valuesRange[tabName] != null) {
			dashboardSpreadSheetService.addSelection(range.values, spread);
			//}
		}

		function addSelection(range, spread) {
			if (range.firstNumber != null && range.lastNumber != null) {
				// var firstNumber = range.firstNumber;
				// var lastNumber = range.lastNumber;
				// var topLeftmostCell = firstNumber.split(":");
				// var bottomRightmostCell = lastNumber.split(":");
				// var row = parseInt(topLeftmostCell[1]);
				// var column = parseInt(topLeftmostCell[0]);
				// var rowCount = parseInt(bottomRightmostCell[1]) - parseInt(topLeftmostCell[1]);
				// var columnCount = parseInt(bottomRightmostCell[0]) - parseInt(topLeftmostCell[0]) + 1;
				var selectedRanges = getSelectedRanges(range);
				var sheetName = range.sheetName;
				var sheet = spread.getSheetFromName(sheetName);
				var activeSheet = spread.getActiveSheet();
				var sheets = spread.sheets;
				if (sheet !== activeSheet) {
					for (var index in sheets) {
						if (sheets[index] === sheet) {
							spread.setActiveSheetIndex(parseInt(index));
							break;
						}
					}
				}
				sheet.addSelection(selectedRanges.row, selectedRanges.col, selectedRanges.rowCount, selectedRanges.colCount);
				sheet.showCell(selectedRanges.row, selectedRanges.col, $.wijmo.wijspread.VerticalPosition.center, $.wijmo.wijspread.HorizontalPosition.center);
			}
		}

		function showValidationError(validationTitle, validationError) {
			swal({
				title: validationTitle,
				text: validationError,
				type: 'warning'
			});
		}

		/**
		 * Blocks all functions related to editing
		 */
		function blockSpreadEdition(spread) {
			// block adding new worksheet
			spread.newTabVisible(false);

			// block capability of modifying cells
			spread.canUserDragDrop(false);
			spread.canUserDragFill(false);

			// block editing cell in form
			for (var i = 0; i < spread.getSheetCount(); i++) {
				var sheet = spread.getSheet(i);
				sheet.allowCellOverflow(true);
				sheet.isProtected = (true);
			}
		}

		function getSelectedCellsAddresses(spread) {
			var sheet = spread.getActiveSheet();
			var selectedRanges = sheet.getSelections();
			if (selectedRanges.length == 1) { // allow only one selected range
				var selectedOneRange = selectedRanges[0];
				var cellFirst = {
					row: selectedOneRange.row,
					col: selectedOneRange.col
				};
				var cellLast = {
					row: selectedOneRange.row + selectedOneRange.rowCount,
					col: selectedOneRange.col + selectedOneRange.colCount - 1
				};
				var headerCellFirst = getColumnNameByIndex(cellFirst.col + 1) + (cellFirst.row + 1);
				var headerCellLast = getColumnNameByIndex(cellLast.col + 1) + (cellLast.row);

				return {
					sheetName: sheet.getName(),
					firstHeader: headerCellFirst,
					lastHeader: headerCellLast,
					firstNumber: '' + cellFirst.col + ':' + cellFirst.row,
					lastNumber: '' + cellLast.col + ':' + cellLast.row,
					selectedRanges: {
						row: selectedOneRange.row,
						col: selectedOneRange.col,
						rowCount: selectedOneRange.rowCount,
						colCount: selectedOneRange.colCount
					}
				};
			} else if (selectedRanges.length > 1) {
				return {
					isMultiselect: true
				};
			}
		}

		var alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

		function getColumnNameByIndex(n) {
			var letter;
			if (n > alphabet.length) {
				var x1 = n % alphabet.length;
				var x2 = ((n - x1) / alphabet.length);
				if (x1 === 0) {
					letter = alphabet.substr(x2 - 2, 1) + alphabet.substr(alphabet.length - 1, 1);
				} else {
					letter = alphabet.substr(x2 - 1, 1) + alphabet.substr(x1 - 1, 1);
				}
			} else {
				letter = alphabet.substr(n - 1, 1);
			}
			return letter;
		}



		function setSelectionColor(backColor, spread) {
			spread.isPaintSuspended(true);
			spread.suspendEvent();

			for (var i = 0; i < spread.getSheetCount(); i++) {
				var sheet = spread.getSheet(i);
				sheet.clearSelection();
				if (backColor) {
					sheet.selectionBackColor(backColor);
				}
			}

			spread.isPaintSuspended(false);
			spread.resumeEvent();
			spread.repaint();
		}

		function setUpCells(xmlForm, spread) {
			spread.suspendCalcService(false);
			//if (isSetUpCell) {
			spread.isPaintSuspended(true);
			var worksheets = xmlForm.worksheets;
			for (var i = 0; i < worksheets.length; i++) {
				var sheet = spread.getSheetFromName(worksheets[i].name);
				if (sheet) {
					var cells = worksheets[i].cells;
					for (var c = 0; c < cells.length; c++) {
						var workbookCell = angular.copy(cells[c]);
						var spreadCell = sheet.getCell(workbookCell.row, workbookCell.column);
						setUpCell(spreadCell, workbookCell);
					}
				}
			}
			// }
			spread.resumeCalcService(true);
			spread.isPaintSuspended(false);

			// spread.repaint();
			// updatePositionBox();
			// removeEvents();
			// redraw();
			// $rootScope.$broadcast('veil:hide', {});
			// $scope.isDocumentCompleted = true;
		}

		function setUpCell(spreadCell, workbookCell) {
			// var tag = spreadCell.tag();
			// if (tag === null || tag === undefined) {
			// 	return;
			// }

			// tag.valueExcel = spreadCell.value();
			// tag.formulaExcel = spreadCell.formula();
			spreadCell.formula("");
			if (workbookCell.date !== null && workbookCell.date !== undefined) {
				var dateStr = workbookCell.date;
				var arr = dateStr.split('/');
				if (spreadCell.formatter() === undefined || spreadCell.formatter() === "General") {
					spreadCell.formatter("d-mmm-yy;;");
				}
				spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, new Date(arr[2], parseInt(arr[1]) - 1, arr[0]));
			} else if (workbookCell.text !== null) {
				spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, workbookCell.text);
			} else if (workbookCell.formula !== null) {
				try {
					spreadCell.sheet.setFormula(spreadCell.row, spreadCell.col, workbookCell.formula);
				} catch (exception) {
					spreadCell.sheet.setFormula(spreadCell.row, spreadCell.col, "");
					console.log("invalid formula : " + cell.formula);
				}
			} else if (workbookCell.value !== null) {
				if (spreadCell.formatter() === "@" || spreadCell.formatter() === "d-mmm-yy;;") {
					// text value
					spreadCell.sheet.setText(spreadCell.row, spreadCell.col, workbookCell.value + "");
				} else {
					// numeric value
					spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, workbookCell.value);
				}
			} else {
				if (spreadCell.formatter() === "@") {
					spreadCell.sheet.setText(spreadCell.row, spreadCell.col, "");
				} else {
					spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, 0);
				}
			}

		}

		// function refreshSpreadValues() {
		// 	var activeTab;
		// 	for (var i = 0; i < $scope.tabs.length; i++) {
		// 		if ($scope.tabs[i] != null && $scope.tabs[i].active === true) {
		// 			activeTab = $scope.tabs[i].id;
		// 			break;
		// 		}
		// 	}
		// 	if (activeTab == null) {
		// 		return;
		// 	}

		// 	var ranges = {
		// 		rows: $scope.rowsRange[activeTab],
		// 		columns: $scope.colsRange[activeTab],
		// 		values: $scope.valuesRange[activeTab]
		// 	}

		// 	setRanges(ranges);
		// 	dashboardSpreadSheetService.refreshSpreadValues($scope.hideValues[activeTab]);
		// 		drawChart4Tab(activeTab);
		// }

		function refreshSpreadValues(spread, selection) {
			var selectedRanges;
			var values = {
				rows: null,
				columns: null,
				values: null
			};

			if (selection.values != null) {
				selectedRanges = selection.values.selectedRanges;
				if (selectedRanges != null) {
					var vals = getValuesFrom(spread.getSheetFromName(selection.values.sheetName), [selectedRanges]);
					values.values = formatValues(vals);
				}
			}
			if (selection.rows != null) {
				selectedRanges = selection.rows.selectedRanges;
				if (selectedRanges != null) {
					var labels = getLabelsForRows(spread.getSheetFromName(selection.rows.sheetName), [selectedRanges], values.values, selection.readHiddenCells);
					values.rows = labels;
				}
			}
			if (selection.columns != null) {
				selectedRanges = selection.columns.selectedRanges;
				if (selectedRanges != null) {
					var labels = getLabelsForCols(spread.getSheetFromName(selection.columns.sheetName), [selectedRanges], values.values, selection.readHiddenCells);
					values.columns = labels;
				}
			}
			return values;
		}

		function getSelectedRanges(range) {
			var firstNumber = range.firstNumber;
			var lastNumber = range.lastNumber;
			var topLeftmostCell = firstNumber.split(":");
			var bottomRightmostCell = lastNumber.split(":");
			// var row = topLeftmostCell[1];
			// var column = topLeftmostCell[0];
			// var rowCount = bottomRightmostCell[1] - topLeftmostCell[1];
			// var columnCount = bottomRightmostCell[0] - topLeftmostCell[0] + 1;
			return {
				row: parseInt(topLeftmostCell[1]),
				col: parseInt(topLeftmostCell[0]),
				rowCount: parseInt(bottomRightmostCell[1]) - parseInt(topLeftmostCell[1]),
				colCount: parseInt(bottomRightmostCell[0]) - parseInt(topLeftmostCell[0]) + 1
			};
		}
		//returns String[][]
		function formatValues(values) {
			for (var i in values) {
				for (var j in values[i]) {
					var value = values[i][j];
					value = parseFloat(value);
					if (value !== value) {
						value = parseFloat("0").toFixed(2);
					} else {
						value = value.toFixed(2); //toFixed() returns a string not a number
					}
					values[i][j] = value;
				}
			}
			return values;
		}

		function getValuesFrom(sheet, selectedRanges) {
			var values = [];

			if (selectedRanges.length == 1) { // allow only one selected range
				var selectedOneRange = selectedRanges[0];
				var columnStartIndex = selectedOneRange.col;
				var columnEndIndex = selectedOneRange.col + selectedOneRange.colCount;
				var rowStartIndex = selectedOneRange.row;
				var rowEndIndex = selectedOneRange.row + selectedOneRange.rowCount;

				for (var i = rowStartIndex; i < rowEndIndex; i++) {
					var oneRowValues = [];
					for (var j = columnStartIndex; j < columnEndIndex; j++) {
						oneRowValues.push(sheet.getValue(i, j));
					}
					values.push(oneRowValues);
				}
			}
			return values;
		}

		function getLabelsForRows(sheet, selectedRanges, values, readFromHidenCells) {
			var labels = [];
			var i;
			if (selectedRanges.length == 1) { // allow only one selected range
				var selectedOneRange = selectedRanges[0];
				var rowStartIndex = selectedOneRange.row;
				var rowEndIndex = selectedOneRange.row + selectedOneRange.rowCount;
				var column = selectedOneRange.col;

				for (i = rowStartIndex; i < rowEndIndex; i++) {
					if (!readFromHidenCells) {
						if (sheet.getRow(i).visible() === false) {
							values[i - rowStartIndex] = null;
							continue;
						}
					}
					var value = sheet.getValue(i, column);
					if (sheet.getValue(i, column) != null && typeof value === "string" && sheet.getValue(i, column).trim() !== "") {
						labels.push(sheet.getValue(i, column));
					} else {
						//values.splice(i - rowStartIndex, 1);
						values[i - rowStartIndex] = null;
					}
				}
				var temp = values;
				for (i = values.length - 1; i > -1; i--) {
					if (temp[i] == null) {
						temp.splice(i, 1);
					}
				}
				//}
			}
			return labels;
		}

		function getLabelsForCols(sheet, selectedRanges, values, readFromHidenCells) {
			var labels = [];
			var i;
			if (selectedRanges.length == 1) { // allow only one selected range
				var selectedOneRange = selectedRanges[0];
				// if (selectedOneRange.rowCount == 1) {
				var columnStartIndex = selectedOneRange.col;
				var columnEndIndex = selectedOneRange.col + selectedOneRange.colCount;
				var row = selectedOneRange.row;

				for (i = columnEndIndex - 1; i >= columnStartIndex; i--) {
					if (!readFromHidenCells) {
						if (sheet.getColumn(i).visible() === false) {
							for (var j = 0; j < values.length; j++) {
								values[j].splice(i - columnStartIndex, 1);
							}
							continue;
						}
					}
					var value = sheet.getValue(row, i);
					if (value != null && typeof value === "string" && sheet.getValue(row, i).trim() !== "") {
						labels.unshift(sheet.getValue(row, i));
					} else {
						for (var j = 0; j < values.length; j++) {
							values[j].splice(i - columnStartIndex, 1);
							//values[j][i - columnStartIndex] = null;
						}
					}
				}
			}
			return labels;
		}

		// var spread;

		// var selected = consts.none;

		//ranges : {
		//	rows:Range,
		//	columns:Range,
		//	values:Range,
		//	selected:consts || consts.none
		//}

		//Range: {
		//	sheetName: string,
		//	firstHeader: int,
		//	lastHeader: int,
		// 	firstNumber: string,
		// 	lastNumber: string,
		// 	selectedRanges: {
		// 		row: int,
		// 		col: int,
		// 		rowCount: int,
		// 		colCount: int
		//	}
		//}

		// function setRanges(ranges) {
		// 	selection = angular.copy(ranges);
		// }

		// function getRanges() {
		// 	return angular.copy(selection);
		// }

		// function setSpread(spreadIt) {
		// 	spread = spreadIt;
		// }
	}
})();