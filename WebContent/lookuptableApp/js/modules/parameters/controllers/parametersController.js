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
	'use_strict';

	angular
		.module('lookupParametersApp.parameters')
		.controller('ParametersController', ParametersController);

	/* @ngInject */
	function ParametersController($scope, $timeout, $modal, ParametersService, SpreadSheetService, DictionaryService, DrawingService, CoreCommonsService, $http) {
		var spread, dictionaries, dimensions, delay;
		var editedRanges = [];
		var dimensionMapper = {};
		var url = $BASE_PATH;
		$scope.currentCompany = null;
		$scope.logout = logout;
		$scope.closeTab = closeTab;
		$scope.isImportBtnDisabled = true;
		$scope.isShowBtnDisabled = true;
		$scope.isValid = true;
		$scope.isRenderedDimensions = false;
		$scope.isShow = false;
		$scope.spreadLoader = SpreadSheetService.getSpreadLoader();
		$scope.parameterLoader = ParametersService.getLoader();
		$scope.show = show;
		$scope.add = add;
		//$scope.companyManager = companyManager
		$scope.importDimensions = importDimensions;

		activate();
		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			getDictionaries();
			getCompanies();

			onSpreadLoaded();

			onCompanyChange();
		}

		function onSpreadLoaded() {
			$scope.$watch('spreadLoader.isSpreadLoaded', function() {
				if ($scope.spreadLoader.isSpreadLoaded) {
					spread = SpreadSheetService.getSpread();

					addSpreadEvents();
					manageSpreadDefaults();
					manageDictionaryHeaders();
				}
			});
		}

		function manageSpreadDefaults() {
//			spread.tabStripVisible(false);
//			spread.showDragFillSmartTag(false);
			
			spread.options.tabStripVisible = false;
			spread.options.showDragFillSmartTag = false;
			//sheet.setIsProtected(false);

			// var defaultStyle = new $.wijmo.wijspread.Style();
			// defaultStyle.backColor = "LemonChiffon";
			// defaultStyle.foreColor = "Red";
			// defaultStyle.formatter = "0.00";
			// defaultStyle.hAlign = $.wijmo.wijspread.HorizontalAlign.center;
			// defaultStyle.vAlign = $.wijmo.wijspread.VerticalAlign.center;
			// defaultStyle.borderLeft = new $.wijmo.wijspread.LineBorder("Green");
			// defaultStyle.borderTop = new $.wijmo.wijspread.LineBorder("Green");
			// defaultStyle.borderRight = new $.wijmo.wijspread.LineBorder("Green");
			// defaultStyle.borderBottom = new $.wijmo.wijspread.LineBorder("Green");

			var sheet;
			for (var i = 0; i < spread.getSheetCount(); i++) {
				sheet = spread.getSheet(i);
				sheet.options.isProtected = (true);

				sheet.defaults.colWidth = 155;
				// sheet.defaults.rowHeight = 35;
				// sheet.setDefaultStyle(defaultStyle, $.wijmo.wijspread.SheetArea.viewport);
			}

			sheet = spread.getActiveSheet();
			sheet.setRowCount(0);

//			var row = sheet.getRow(0, GC.Spread.Sheets.SheetArea.colHeader);
			var row = sheet.getCell(0,-1);
			row.font("normal bold normal 13px/normal Arial");

			sheet.setColumnCount(3, GC.Spread.Sheets.SheetArea.rowHeader);

//			var column = sheet.getColumn(0, GC.Spread.Sheets.SheetArea.rowHeader);
			var rowCount = sheet.getRowCount();
			var column = sheet.getCell(-1,0);
//			column = sheet.getRange(0,0,rowCount,1);

			column.hAlign("left");
			column.font("normal bold normal 13px/normal Arial");
			column.width(215);

			column = sheet.getCell(-1,1);
//			column = sheet.getColumn(1, GC.Spread.Sheets.SheetArea.rowHeader);
			column.font("italic normal normal 10px/normal Arial");
			column.width(105);

			column = sheet.getCell(-1,2);
//			column = sheet.getColumn(2, GC.Spread.Sheets.SheetArea.rowHeader);
			column.font("italic normal normal 10px/normal Arial");
			column.width(80);
		}

		function onCompanyChange() {
			$scope.$watch('currentCompany', function() {

				toggleShowButton();
				toggleImportButton();
				toggleParameterLoader();

				$scope.currency = getCurrencyForCurrentCompany($scope.currentCompany);
				if ($scope.currentCompany) {
					getDimensions();
				}

			});

		}

		function getCurrencyForCurrentCompany(currentCompany) {
			var currency;
			if (currentCompany !== undefined && currentCompany !== null) {
				for (var i = 0; i < $scope.companies.length; i++) {
					if ($scope.companies[i].value === currentCompany) {
						currency = $scope.companies[i].dictionaryProperties.currency;
						break;
					}
				}
			}
			return currency;
		}

		function toggleParameterLoader() {
			var parameterLoader = ParametersService.getLoader();
			parameterLoader.isParametersLoaded = false;
		}

		function toggleShowButton() {
			$scope.isShowBtnDisabled = !$scope.currentCompany || $scope.parameterLoader.isParametersSaving;
		}

		function toggleImportButton() {
			$scope.isImportBtnDisabled = !$scope.currentCompany || $scope.parameterLoader.isParametersSaving;
		}

		function getDictionaries() {
			DictionaryService.getDictionaries("fields").then(function(data) {
				dictionaries = data;
				manageDictionaryHeaders();
			});
		}

		function manageDictionaryHeaders() {
			if (angular.isDefined(spread) && angular.isDefined(dictionaries)) {
				var sheet = spread.getActiveSheet();
				sheet.setColumnCount(dictionaries.length);
				for (var i = 0; i < dictionaries.length; i++) {
					sheet.setValue(0, i, dictionaries[i].value, GC.Spread.Sheets.SheetArea.colHeader);
				}
			}
		}

		function getDimensions() {
			ParametersService.getDimensions($scope.currentCompany).then(function(data) {
				dimensions = data;
				show();
			});
		}



		function manageDimensionHeaders() {
			dimensionMapper = {};

//			spread.isPaintSuspended(true);
			spread.suspendPaint();
//			spread.resumePaint();
			var tmpDimensions = [];

			for (var i = 0; i < dimensions.length; i++) {
				if (dimensions[i] == "") {
					dimensions.splice(i, 1);
					i--;
				}
			}
			tmpDimensions = dimensions.slice();

			var isNewRowBeforeSuspends = false;
			var isNewRowBeforeDeleted = false;
			if (angular.isDefined(spread) && angular.isDefined(dimensions)) {
				var sheet = spread.getActiveSheet();
				sheet.setRowCount(0);
				sheet.setColumnWidth(0,150,GC.Spread.Sheets.SheetArea.rowHeader);
				sheet.setColumnWidth(1,100,GC.Spread.Sheets.SheetArea.rowHeader);
				sheet.setColumnWidth(2,50,GC.Spread.Sheets.SheetArea.rowHeader);

				var emptyRow = "";
				var splicedTimes = 0;

				for (var i = 0; i < dimensions.length; i++) {
					if (dimensions[i].leaf === false && i > 0 || (dimensions[i].status === "SUSPENDED" && !isNewRowBeforeSuspends) || (dimensions[i].status === "DELETED" && !isNewRowBeforeDeleted)) {
						if (dimensions[i].status === "SUSPENDED") {
							isNewRowBeforeSuspends = true;
						}
						if (dimensions[i].status === "DELETED") {
							isNewRowBeforeDeleted = true;
						}
						sheet.addRows(sheet.getRowCount(), 1);
						sheet.setValue(sheet.getRowCount() - 1, 1, "", GC.Spread.Sheets.SheetArea.rowHeader);
						sheet.setValue(sheet.getRowCount() - 1, 2, "", GC.Spread.Sheets.SheetArea.rowHeader);
						tmpDimensions.splice(i + (splicedTimes++), 0, emptyRow);

					}
					sheet.addRows(sheet.getRowCount(), 1);
//					sheet.getRow(sheet.getRowCount() - 1).locked(false);
					sheet.getCell(sheet.getRowCount()-1,-1).locked(false);
					if (dimensions[i].leaf === false) {
						if (dimensions[i].costCentre == dimensions[i].company + "/1-cc-" + dimensions[i].group) {
							sheet.setValue(sheet.getRowCount() - 1, 0, "" + dimensions[i].costCentre, GC.Spread.Sheets.SheetArea.rowHeader);
							sheet.getCell(sheet.getRowCount() - 1, 0, GC.Spread.Sheets.SheetArea.rowHeader).foreColor("blue");
							console.log(sheet.getCell(sheet.getRowCount() - 1, 0, GC.Spread.Sheets.SheetArea.rowHeader));
						} else {
							sheet.setValue(sheet.getRowCount() - 1, 0, "    " + dimensions[i].costCentre, GC.Spread.Sheets.SheetArea.rowHeader);
						}
					} else {
						sheet.setValue(sheet.getRowCount() - 1, 0, "        " + dimensions[i].costCentre, GC.Spread.Sheets.SheetArea.rowHeader);
					}

					sheet.setValue(sheet.getRowCount() - 1, 1, dimensions[i].status, GC.Spread.Sheets.SheetArea.rowHeader);
					sheet.setValue(sheet.getRowCount() - 1, 2, $scope.currency, GC.Spread.Sheets.SheetArea.rowHeader);

					if (!angular.isDefined(dimensionMapper[dimensions[i].costCentre])) {
						dimensionMapper[dimensions[i].costCentre] = [sheet.getRowCount() - 1];
					} else {
						dimensionMapper[dimensions[i].costCentre].push((sheet.getRowCount() - 1));
					}
				}
			}
			dimensions = tmpDimensions.slice();
//			spread.isPaintSuspended(false);
			spread.resumePaint();
		}

		function getCompanies() {
			DictionaryService.getDictionaries("company").then(function(companies) {
				$scope.companies = companies;
			});
		}

		function show() {
			//DrawingService.redraw();
			$scope.isValid = true;
			editedRanges.length = 0;
			manageDimensionHeaders();
			ParametersService.getParameters($scope.currentCompany).then(function(data) {
				$scope.parameters = data;
				manageParametersCells();
				DrawingService.redraw();
			});
		}


		function importDimensions() {
			$("#importData").attr('class', 'fa fa-refresh fa-spin');
			$scope.isImportingDimension = true;

			if ($scope.currentCompany !== null) {
				ParametersService.importDimensions($scope.currentCompany).then(function(data) {
					$scope.isImportingDimension = false;

					dimensions = data;
					manageDimensionHeaders();
					$("#importData").attr('class', 'fa fa-download');
					show();
				});
			}
		}

		function manageParametersCells() {
//			spread.isPaintSuspended(true);
			spread.suspendPaint();
			var sheet = spread.getActiveSheet();
			sheet.options.isProtected = (false);

			spread.options.highlightInvalidData = (true);
			// var dv = $.wijmo.wijspread.DefaultDataValidator.createTextLengthValidator($.wijmo.wijspread.ComparisonOperator.Between, -1, 128);
			// var dvb = $.wijmo.wijspread.DefaultDataValidator.createNumberValidator($.wijmo.wijspread.ComparisonOperator.Between, 0.00, 100000000000000.00);

			// dv.showInputMessage = false;
			// dv.inputMessage = "Enter less than  128 characters.";
			// dv.errorMessage = "Enter less than  128 characters.";
			// dv.inputTitle = "Tip";
			var sheet = spread.getActiveSheet();
//			var style = new $.wijmo.wijspread.Style();
			var style = new GC.Spread.Sheets.Style();
			var border = new GC.Spread.Sheets.LineBorder("#9eb6ce", GC.Spread.Sheets.LineStyle.thin);
			style.backColor = "#EDEFF4";
			style.borderLeft = border;
			style.borderTop = border;
			style.borderRight = border;
			style.borderBottom = border

			if (dimensions !== undefined) {
				for (var j = 0; j < dictionaries.length; j++) {
					var dictionary = dictionaries[j];

					for (var i = 0; i < dimensions.length; i++) {
						var dimension = dimensions[i];

						var cell = sheet.getCell(i, j);
						cell.text(null);

						//detect cells from epmty and blocked row
						if (cell.locked()) {
							//cell.backColor("#EDEFF4");
							sheet.setStyle(i, j, style, GC.Spread.Sheets.SheetArea.viewport);
						}

						for (var k = 0; k < $scope.parameters.length; k++) {
							var parameter = $scope.parameters[k];

							if (parameter.fieldName === dictionary.value && parameter.costCentre === dimension.costCentre) {
								if (parameter.fieldValue !== null) {
									if (parameter.fieldValue.toString().charAt(0) === "=") {
										cell.formula(parameter.fieldValue);
									} else if (!isNaN(parameter.fieldValue)) {
										cell.value(parseFloat(parameter.fieldValue));
									} else {
										cell.value(parameter.fieldValue);
									}
								} else {
									cell.value(parameter.fieldValue);
								}
							}
						}
					}
					//sheet.getRow(j).dataValidator(dv);
					//sheet.getRow(j).dataValidator(dvb);
				}
			}
			sheet.options.isProtected = (true);
//			spread.isPaintSuspended(false);
			spread.resumePaint();
		}

		function update(data) {
//			spread.isPaintSuspended(true);
			spread.suspendPaint();

			if (data.editedDictionaries.length > 0) {
				ParametersService.updateFieldName(data.editedDictionaries);
				for (var i = 0; i < $scope.parameters.length; i++) {
					var parameter = $scope.parameters[i];
					for (var j = 0; j < data.editedDictionaries.length; j++) {
						var editedParameter = data.editedDictionaries[j];
						if (parameter.fieldName === editedParameter.oldValue.toUpperCase()) {
							if (editedParameter.newValue !== "null") {
								parameter.fieldName = editedParameter.newValue.toUpperCase();
								j = data.editedDictionaries.length;
							} else if (editedParameter.newValue === "null") {
								$scope.parameters.splice(i, 1);
								j = data.editedDictionaries.length;
								i--;
							}
						}
					}
				}
			}

			dictionaries.sort(function(dict1, dict2) {
				return dict1.rowIndex - dict2.rowIndex
			});
			manageDictionaryHeaders();
			manageParametersCells();
			toggleParameterLoader();
			//show();
//			spread.isPaintSuspended(false);
			spread.resumePaint();
		}

		/**
		 * [propagationDuplicateValue funkction auto changes values for cells in dimensions with the same name]
		 * @param  {[object with dynamic fields]} mapper [object with indexes of dimensions with the same name (same dimensions in different hierarchy)]
		 * @param  {[type]} info   [object from event]
		 * @return {[type]}        [description]
		 */
		function propagationDuplicateValue(info) {
			if (info.cancel) {
				return;
			}
//			spread.isPaintSuspended(true);
			spread.suspendPaint();
			var dim = dimensions[info.row].costCentre;
			var indexes = dimensionMapper[dim];
			var editedCellText = info.editingText;
			if (angular.isDefined(indexes)) {
				for (var i = 0; i < indexes.length; i++) {
					var cell = spread.getActiveSheet().getCell(indexes[i], info.col);

					if (editedCellText !== null) {
						if (editedCellText.toString().charAt(0) === "=") {
							cell.formula(editedCellText);
						} else if (!isNaN(editedCellText)) {
							cell.formula(null);
							cell.value(parseFloat(editedCellText));

						} else {
							cell.formula(null);
							cell.value(editedCellText);
						}
					} else {
						cell.value(editedCellText);
					}


				}
			}
//			spread.isPaintSuspended(false);
			spread.resumePaint();
		}


		function propagationDuplicateValue2(info, isClipboardPasted) {
//			spread.isPaintSuspended(true);
			spread.suspendPaint();
			var rowCount = spread.getActiveSheet().getRowCount();
			var colCount = spread.getActiveSheet().getColumnCount();
			var i;
			if (isClipboardPasted || isClipboardPasted === null) {
				if (isClipboardPasted) {
					i = info.cellRange.row;
				} else {
					i = info.toRow;
				}
			} else {
				i = 0;
			}

			if (info.toRow < info.fromRow || angular.isDefined(info.cellRange)) {
				for (i; i < rowCount; i++) {
					copyCells(i, colCount, info);
				}
			} else {
				for (i; i > 0; i--) {
					copyCells(i, colCount, info);
				}
			}
//			spread.isPaintSuspended(false);
			spread.resumePaint();
		}

		function copyCells(i, colCount, info) {
			for (var j = 0; j < colCount; j++) {
				var dim = dimensions[i].costCentre;
				var indexes = dimensionMapper[dim];
				var cell = spread.getActiveSheet().getCell(i, j)
				var editedCellText;
				if (cell.formula() !== null && !isClipboardPasted) {
					editedCellText = "=" + cell.value();
				} else {
					editedCellText = cell.value();
				}
				if (angular.isDefined(indexes)) {
					for (var l = 0; l < indexes.length; l++) {
						var cell = spread.getActiveSheet().getCell(indexes[l], j); // col w ktory mamy wstawiac
						if (indexes[l] !== info.toRow || info.toRow === undefined) {
							settingTypeCell(editedCellText, cell);
						}
					}
				}
			}
		}

		function settingTypeCell(inputCell, cell) {
			if (inputCell !== null) {
				if (inputCell.toString().charAt(0) === "=") {
					cell.formula(inputCell);
				} else if (!isNaN(inputCell)) {
					cell.formula(null);
					cell.value(parseFloat(inputCell));
				} else {
					cell.formula(null);
					cell.value(inputCell);
					//cell.text(editedCellText);
				}
			} else {
				cell.value(inputCell);
			}
		}

		function addSpreadEvents() {
			spread.bind(GC.Spread.Sheets.Events.EditEnd, function(e, info) {
				handleValueChanged(info);
				propagationDuplicateValue(info);


			});

			spread.bind(GC.Spread.Sheets.Events.ValueChanged, function(e, info) {
				handleValueChanged2(info);
				propagationDuplicateValue(info);

			});

			angular.element(window).on('keydown', function(event) {
				if (event.keyCode === 46) { // Del
					handleDelKeyDown();
				}
			});

			spread.bind(GC.Spread.Sheets.Events.DragFillBlockCompleted, function(e, info) {
				handleDragFillBlockCompleted(info);
				propagationDuplicateValue2(info, false);
			});

			spread.bind(GC.Spread.Sheets.Events.DragDropBlockCompleted, function(e, info) {
				handleDragDropBlockCompleted(info);
				propagationDuplicateValue2(info, null);
			});

			spread.bind(GC.Spread.Sheets.Events.ClipboardPasted, function(e, info) {
				handleClipboardPasted(info);
				propagationDuplicateValue2(info, true);
			});

			spread.bind(GC.Spread.Sheets.Events.ValidationError, function(event, data) {
				handleValidationError(data);
			});
		}

		function handleValidationError(data) {
			var dv = data.validator;
			if (dv) {
				// TIP z errorem
				console.log(dv.errorMessage);

				// default
				data.validationResult = GC.Spread.Sheets.DataValidation.DataValidationResult.forceApply;
				// // finish editing, and restor the original value
				// data.validationResult = $.wijmo.wijspread.DataValidationResult.Discard;
				// // keep editing until enter a valid value
				// data.validationResult = $.wijmo.wijspread.DataValidationResult.Retry;
			}
		}

		function isValueValid(value) {
			if (value === null) {
				return true;
			}
			var test = value.toString();
			//var pattern = /^[+-]?\d+(\.\d+)?$/gi;
			//var res = pattern.test(value);
			// if (res === false) {
			// 	return false;
			// }
			// var temp = parseFloat(value);
			//value.toString();
			return (test.length <= 128);
		}

		function manageEditedRanges(range) {
			manageSavingLabel();
			//sheet.setIsProtected(false);
			editedRanges.push(range);
			$timeout.cancel(delay);
			delay = $timeout(function() {
				manageEditedParameters(editedRanges);
			}, 3000);
			//sheet.setIsProtected(true);
		}

		function manageSavingLabel() {
			$scope.isValid = true;
			$scope.parameterLoader.isParametersSaved = false;
			$scope.parameterLoader.isParametersSaving = true;
			$scope.$apply('parameterLoader');
		}

		function manageEditedParameters(ranges) {
			console.log($scope.currentCompany);

			var sheet = spread.getActiveSheet();
			var isValid = true;

			var editedParameters = [];
			for (var i = 0; i < ranges.length; i++) {
				var range = ranges[i];

				for (var j = range.row; j < range.row + range.rowCount; j++) {
					for (var k = range.col; k < range.col + range.colCount; k++) {
						var cell = sheet.getCell(j, k);
						var value = cell.value();
						var content = cell.text();
						var formula = cell.formula();
						if (cell.formula() !== null) {
							value = "=" + cell.formula();
						}

						if (isValueValid(value) === false) {
							isValid = false;
							break;
						}

						var dictionary = dictionaries[k];
						var dimension = dimensions[j];

						var editedParameter = null;
						var parameter = null;
						for (var m = 0; m < $scope.parameters.length; m++) {
							parameter = $scope.parameters[m];
							if (parameter.fieldName === dictionary.value && parameter.costCentre === dimension.costCentre && parameter.company === parseInt($scope.currentCompany)) {
								editedParameter = parameter;
								break;
							}
						}

						if (editedParameter) {
							editedParameter.fieldValue = value;
						} else {
							editedParameter = {
								parameterUUID: null,
								costCentre: dimension.costCentre,
								company: $scope.currentCompany,
								fieldName: dictionary.value,
								fieldValue: value
							};
						}

						var ifInEditedParameters = false;
						for (m = 0; m < editedParameters.length; m++) {
							parameter = editedParameters[m];
							if (parameter.fieldName === dictionary.value && parameter.costCentre === dimension.costCentre) {
								ifInEditedParameters = true;
								break;
							}
						}
						if (!ifInEditedParameters && (editedParameter.parameterUUID !== null || editedParameter.fieldValue !== null)) {
							editedParameters.push(editedParameter);
						}
					}
				}
			}
			if (isValid) {
				editedRanges.length = 0;
				ParametersService.saveParameters(editedParameters).then(function(data) {
					$scope.parameterLoader.isParametersSaving = false;

					$timeout(function() {
						$scope.parameterLoader.isParametersSaved = false;
					}, 1500);
				});
			} else {
				$scope.parameterLoader.isParametersSaving = false;
				$scope.isValid = false;
			}
		}

		function handleDragDropBlockCompleted(info) {
			var toRange = new GC.Spread.Sheets.Range(info.toRow, info.toCol, info.rowCount, info.colCount);
			var fromRange = new GC.Spread.Sheets.Range(info.fromRow, info.fromCol, info.rowCount, info.colCount);
			manageEditedRanges(toRange);
			manageEditedRanges(fromRange);
		}

		function handleDragFillBlockCompleted(info) {
			manageEditedRanges(info.fillRange);
		}

		function handleClipboardPasted(info) {
			manageEditedRanges(info.cellRange);
		}

		function handleDelKeyDown() {

			var sheet = spread.getActiveSheet();
			var selectedRanges = sheet.getSelections();
			for (var i = 0; i < selectedRanges.length; i++) {
				var range = selectedRanges[i];
				manageEditedRanges(range);
			}
		}

		function handleValueChanged(info) {
			var range = new GC.Spread.Sheets.Range(info.row, info.col, 1, 1);
			manageEditedRanges(range);
		}

		function resetView() {
			var sheet = spread.getActiveSheet();
			for (var j = 0; j < $scope.parameters.length; j++) {
				for (var k = 0; k < dictionaries.length; k++) {
					sheet.getCell(k, j).value(null);
				}
			}
			sheet.options.isProtected = (true);
		}
		//fields manager
		function add() {
			var modalInstance = $modal.open({
				template: '<dictionary-editor modal="modal" type="type" enable-insert="enableInsert" enable-actions="enableActions" enable-order="enableOrder" enable-description="enableDescription"></dictionary-editor>',
				windowClass: 'dictionary-editor-modals softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.modal = $modalInstance;
						$scope.type = "fields";
						///////////////////// Properties to manage dictionary ///////////
						$scope.enableInsert = true;
						$scope.enableActions = true; // enable/disable buttons: 'edit', 'delete' and 'save'
						$scope.enableOrder = true;
						$scope.enableDescription = true;
						$scope.close = function() {
							$modalInstance.close();
						};
					}
				]
			});

			modalInstance.result.then(function(data) {
				//resetView();
				update(data);
				//show();
			}, function() {

			});
		}

		function logout() {
			window.location = url + "/logout.do";
		}

		function closeTab() {
			CoreCommonsService.closeTab($timeout);
		}

	}

})();