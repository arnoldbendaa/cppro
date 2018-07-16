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
		.service('InsertService', InsertService);

	/* @ngInject */
	function InsertService() {

		var self = this;
		// http://tech-chec.blogspot.com/2009/03/convert-excel-column-letter-to-number.html (modified)
		var alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
		self.getColumnIndexByName = getColumnIndexByName;
		self.getColumnNameByIndex = getColumnNameByIndex;
		self.convertSelectionToRange = convertSelectionToRange;
		self.checkOrientation = checkOrientation;
		self.validateRange = validateRange;
		self.parseSparklineFormula = parseSparklineFormula;
		self.prepareBoolean = prepareBoolean;
		self.parseColor = parseColor;
		self.stripQuotes = stripQuotes;
		self.stripCurlyBracket = stripCurlyBracket;

		/************************************************** IMPLEMENTATION *************************************************************************/

		function getColumnIndexByName(c) {
			var num, ltr;
			if (c.length == 1) {
				num = alphabet.indexOf(c) + 1;
			} else {
				for (var i = 0; i < c.length; i++) {
					ltr = c.substr(i, 1);
					if (i === 0) {
						num = (alphabet.length * (alphabet.indexOf(ltr) + 1));
					} else {
						num += alphabet.indexOf(ltr) + 1;
					}
				}
			}
			return num - 1;
		}

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

		function convertSelectionToRange(sheet) {
			var selection = sheet.getSelections()[sheet.getSelections().length - 1]; // last selection
			var startColIndex = selection.col + 1;
			var startColName = self.getColumnNameByIndex(startColIndex);
			var startRowIndex = selection.row + 1;
			var endColumnIndex = selection.col + selection.colCount;
			var endColumnName = self.getColumnNameByIndex(endColumnIndex);
			var endRowIndex = selection.row + selection.rowCount;
			if (startColName == endColumnName && startRowIndex == endRowIndex) {
				return startColName + startRowIndex;
			} else {
				return startColName + startRowIndex + ':' + endColumnName + endRowIndex;
			}
		}

		// get start and end cell coordinates, then check if selection is one row, one cell or more rows/cells
		function checkOrientation(startRow, endRow, startCol, endCol) {
			if (startRow == endRow) {
				return 1; // horizontal
			} else if (startCol == endCol) {
				return 0; // vertical
			} else {
				return null; // other
			}
		}

		// validate cell coordinates (like B5) and return it (if it's ok) or return validation error info
		function validateRange(expr, validationMessage) {
			var toReturn = {
				row: '',
				col: '',
			};
			// Expression must start with letter, because we check column (rowsColumnsGet = "columns").
			// Since the first instance of number in expression we expect only numbers because we check rows (rowsColumnsGet = "rows").
			var rowsColumnsGet = 'columns';
			if (expr !== undefined) {
				var i;
				for (i = 0; i < expr.length; i++) {
					var currentChar = expr.charAt(i);
					if (isNaN(parseInt(currentChar))) {
						if (rowsColumnsGet == 'columns') {
							toReturn.col += currentChar;
						} else {
							swal(validationMessage, '', 'warning');
							return {
								validationError: true
							};
						}
					} else {
						// the first (and others) instance of number
						rowsColumnsGet = 'rows';
						if (rowsColumnsGet == 'rows') {
							toReturn.row += currentChar;
						} else {
							swal(validationMessage, '', 'warning');
							return {
								validationError: true
							};
						}
					}
				}
			}
			return toReturn;
		}

		function parseSparklineFormula(dataString) {
			var data = [];
			// Stack is used to decide if any comma separates sparkline formula parameters or is a part of other formula inside sparkline.
			// For example look at first and second comma here: HBARSPARKLINE(N11,"rgb(219, 106, 216)")
			// We put on stack special chars: " ' ( )
			// If meet one of them in expression, check top of the stack. If contains a pair for current char, pop from stack. If not - push to stack.
			// If meet comma in expression, check if stack is empty. If it is - explode formula here. If it's not - just go to next char, because we are inside other formula.
			var stack = [];
			var dataTemp = '';
			for (var i = 0; i < dataString.length; i++) {
				var currentChar = dataString.charAt(i);
				var lastOnStack = stack.slice(-1)[0];
				switch (currentChar) {
					case '"':
						if (lastOnStack != '"') {
							stack.push(currentChar);
						} else {
							stack.pop();
						}
						dataTemp += currentChar;
						break;
					case '\'':
						if (lastOnStack != '\'') {
							stack.push(currentChar);
						} else {
							stack.pop();
						}
						dataTemp += currentChar;
						break;
					case ')':
						if (lastOnStack != '(') {
							stack.push(currentChar);
						} else {
							stack.pop();
						}
						dataTemp += currentChar;
						break;
					case '(':
						stack.push(currentChar);
						dataTemp += currentChar;
						break;
					case ',':
						if (stack.length === 0) {
							data.push(dataTemp);
							dataTemp = '';
						} else {
							dataTemp += currentChar;
						}
						break;
					default:
						dataTemp += currentChar;
				}
			}
			if (dataTemp !== '') {
				data.push(dataTemp);
			}
			return data;
		}

		// translate booleans from string to boolean
		function prepareBoolean(value) {
			if (value == 'TRUE' || value == 'true' || value === true) {
				return true;
			} else if (value == 'FALSE' || value == 'false' || value === false) {
				return false;
			} else {
				return '';
			}
		}

		// get color from option string for Columns, Line or Win/Loss
		// i.e. "{seriesColor:rgb(0, 0, 255),displayHidden:true}"
		function parseColor(value) {
			var settings = self.stripCurlyBracket(self.stripQuotes(value));
			var settingsParsed = self.parseSparklineFormula(settings);
			for (var i = 0; i < settingsParsed.length; i++) {
				if (settingsParsed[i].indexOf('seriesColor') > -1) {
					var color = settingsParsed[i].split(':')[1];
					return color;
				}
			}
			return null;
		}

		// remove quotes from the begining and from the end of expression
		function stripQuotes(value) {
			if (value.charAt(0) === '"' && value.charAt(value.length - 1) === '"') {
				return value.substr(1, value.length - 2);
			}
			return value;
		}

		// remove curly brackets from the begining and from the end of expression
		function stripCurlyBracket(value) {
			if (value.charAt(0) === '{' && value.charAt(value.length - 1) === '}') {
				return value.substr(1, value.length - 2);
			}
			return value;
		}

	}
})();