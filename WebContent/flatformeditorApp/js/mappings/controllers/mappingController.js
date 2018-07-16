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
		.module('flatFormEditorApp.mappings')
		.controller('MappingController', MappingController);

	/* @ngInject */
	function MappingController($scope, $modal, $timeout, CoreCommonsService, SpreadSheetService, MappingService, ViewModeService) {

		//TODO Bug przy autouzupełnianiu. Range w odwrotną stronę
		// 1 2
		// 2
		// 3 - autouzupełnić

		$scope.viewMode = ViewModeService.getViewMode();
		$scope.currentMapping = MappingService.getCurrentMapping();
		$scope.validation = MappingService.getValidation();
		//var sheet;
		var isMappingEditing = false;
		// not delete current cell position. it is necessary in saving mapping when event 'Leave Cell' occur
		var currentCellPosition = {
			row: 0,
			column: 0
		};
		$scope.setEditedType = setEditedType;
		$scope.onMappingEditEnded = onMappingEditEnded;
		$scope.saveMappingByCellEditing = saveMappingByCellEditing;

		/************************************************** IMPLEMENTATION *************************************************************************/

		/**
		 * Set flag if mapping is edited in inputs
		 */
		function setEditedType(type) {
			isMappingEditing = true;
			$scope.validation[type + 'Mapping'] = true;
		}

		/**
		 * Invoked when we focus out inputs with mapping - after editing mapping in inputs
		 */
		function onMappingEditEnded() {
			saveMappingByInputEditing();
		}

		/**
		 * Create empty tag or copy existing tag in cell for current position. Current position is necessary
		 */
		function manageTag() {
			var sheet = $scope.spread.getActiveSheet();
			var spreadCell = sheet.getCell(currentCellPosition.row, currentCellPosition.column);
			var tag = spreadCell.tag();
			if (tag == null) {
				tag = MappingService.createTag();
				tag.row = sheet.getActiveRowIndex();
				tag.column = sheet.getActiveColumnIndex();
			} else {
				tag = angular.copy(tag);
			}
			return tag;
		}

		/**
		 * Save mapping (in fact set tag) for current cell
		 */
		function saveMapping(tag) {
			var sheet = $scope.spread.getActiveSheet();
			var spreadCell = sheet.getCell(tag.row, tag.column);
			MappingService.override = true;
			sheet.setTag(spreadCell.row, spreadCell.col, tag, GC.Spread.Sheets.SheetArea.viewport, true);
			MappingService.override = false;
			$scope.spread.repaint();
		}

		/**
		 * Invoked when we focus out inputs with mapping - after editing mapping in inputs
		 */
		function saveMappingByInputEditing() {
			if (isMappingEditing === false) {
				return;
			}
			isMappingEditing = false;

			var tag = manageTag();
			tag.inputMapping = ($scope.currentMapping.inputMapping !== null && $scope.currentMapping.inputMapping !== "") ? $scope.currentMapping.inputMapping : null;
			tag.outputMapping = ($scope.currentMapping.outputMapping !== null && $scope.currentMapping.outputMapping !== "") ? $scope.currentMapping.outputMapping : null;
			tag.text = ($scope.currentMapping.formulaMapping !== null && $scope.currentMapping.formulaMapping !== "") ? $scope.currentMapping.formulaMapping : null;
			MappingService.overrideFormula = true;
			saveMapping(tag);
			MappingService.overrideFormula = false;
		}

		/**
		 * Invoked when we edit mapping in cell in edit mode in spreadsheet. Look at file 'spreadJsOverrides.js' in function '_endEditImp'
		 */
		function saveMappingByCellEditing(spreadCell, value) {
			var cellMode = ViewModeService.getCurrentViewMode().name;
			if (cellMode !== "INPUT" && cellMode !== "OUTPUT" && cellMode !== "FORMULA" && value === null) {
				return value;
			}
			var tag = manageTag();

			var ifInputMapping = MappingService.checkIfMappingIsCorrect('input', value);
			var ifOutputMapping = MappingService.checkIfMappingIsCorrect('output', value);
			var ifFormulaMapping = MappingService.checkIfMappingIsCorrect('formula', value);

			if (ifInputMapping) {
				tag.inputMapping = value;
			}
			if (ifOutputMapping) {
				tag.outputMapping = value;
			}
			if (ifFormulaMapping) {
				tag.text = value;
			}

			if (ifInputMapping === false && ifOutputMapping === false && ifFormulaMapping === false) {
				return value;
			} else {
				saveMapping(tag);
				if (spreadCell.formula()) {
					return "=" + spreadCell.formula();
				}
				return spreadCell.value();
			}
		}

		angular.element(window).on('keydown', function(event) {
			if (event.keyCode === 46) { // "del" pressed
				clearPropertiesInCells();
				CoreCommonsService.askIfReload = true;
			}
		});

		/**
		 * Clear properties for selected range. Properties for clearing depends on view mode
		 */
		function clearPropertiesInCells() {
			var sheet = $scope.spread.getActiveSheet();
			var selections = sheet.getSelections();

			var cellMode = ViewModeService.getCurrentViewMode().name;
			if (cellMode === "TEST" || isMappingEditing === true) {
				return;
			}
			var cell;
			for (var i = 0; i < selections.length; i++) {
				var range = selections[i];
				for (var j = 0; j < range.rowCount; j++) {
					for (var k = 0; k < range.colCount; k++) {
						cell = sheet.getCell(range.row + j, range.col + k);
						var tag = cell.tag();
						if (cellMode == "VALUE") {
							cell.value(null);
						} else if (cellMode == "INPUT" && tag) {
							tag.inputMapping = null;
							cell.tag(tag);
						} else if (cellMode == "OUTPUT" && tag) {
							tag.outMapping = null;
							cell.tag(tag);
						} else if (cellMode == "FORMULA" && tag) {
							tag.text = null;
							cell.tag(tag);
						}
					}
				}
			}
			cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true);
			MappingService.refreshCurrentMapping(cell);
			$scope.$apply('currentMapping');

			$scope.spread.repaint();
		}

		/**
		 * Invoked when user leave cell in spreadsheet
		 */
		function handleLeaveCell(info) {
			saveMappingByInputEditing();

			// this 'apply' functions couldn't be in 'saveMappingByInputEditing' because it could be invoked from angular context (funciton 'onMappingEditEnded')
			$scope.$apply('currentMapping');
			$scope.$apply('validation');
		}

		$scope.$on("SpreadSheetController:LeaveCell", function(e, info) {
			handleLeaveCell(info);
		});

		/**
		 * Invoked when user change selected cell in spreadsheet
		 */
		function handleSelectionChanged() {
			var sheet = $scope.spread.getActiveSheet();
			var cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true);

			currentCellPosition.row = sheet.getActiveRowIndex();
			currentCellPosition.column = sheet.getActiveColumnIndex();

			MappingService.refreshValidation();
			$scope.$apply('validation');
			MappingService.refreshCurrentMapping(cell);
			$scope.$apply('currentMapping');

			// console.log('tag', cell.tag());
			// console.log('cellType', cell.cellType(), "VALUE  = " + cell.value(), "FORMULA = " + cell.formula())
			// var worksheet = CoreCommonsService.findElementByKey($scope.flatForm.xmlForm.worksheets, sheet.getName(), "name");
			// //console.log(worksheet);
			// $scope.testWorkbookCell = MappingService.findCellInWorkbook(cell);
			// $scope.$apply('testWorkbookCell');
			// console.log(worksheet.cells.length, worksheet)
		}

		$scope.$on("SpreadSheetController:SelectionChanged", function(e, info) {
			handleSelectionChanged();
		});

		$scope.$on("SpreadSheetController:SheetTabClick", function(e, info) {
			$scope.spread.setActiveSheet(info.sheetName);
			var sheet = $scope.spread.getActiveSheet();
			var cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true);
			MappingService.refreshCurrentMapping(cell);
			handleSelectionChanged();
		});

		/************************************ COPY MAPPINGS ************************************************/
		/**
		 * Copy mappings from specified type
		 */
		function handleCopyMappings(type) {
			var sheet = $scope.spread.getActiveSheet();

			var sels = sheet.getSelections();
			var cell;
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						cell = sheet.getCell(i, j);
						var tag = cell.tag();
						if (tag != null) {
							if (type === "input") {
								var mapping = tag.inputMapping;
								if (mapping == null) {
									return;
								}
								if (mapping.indexOf("cedar.cp.getCell") > -1) {
									tag.outputMapping = mapping.replace("cedar.cp.getCell", "cedar.cp.putCell");
								} else if (mapping.indexOf("cedar.cp.cell") > -1) {
									tag.outputMapping = mapping.replace("cedar.cp.cell", "cedar.cp.post");
								}
							} else if (type === "output") {
								var mapping = tag.outputMapping;
								if (mapping == null) {
									return;
								}
								if (mapping.indexOf("cedar.cp.putCell") > -1) {
									tag.inputMapping = mapping.replace("cedar.cp.putCell", "cedar.cp.getCell");
								} else if (mapping.indexOf("cedar.cp.post") > -1) {
									tag.inputMapping = mapping.replace("cedar.cp.post", "cedar.cp.cell");
								}
							}
							cell.tag(tag);
							saveMapping(tag);
						}
					}
				}
			}

			cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true);
			MappingService.refreshCurrentMapping(cell);
		}

		function handleYearToDate(turnOn) {
			var sheet = $scope.spread.getActiveSheet();

			var sels = sheet.getSelections();
			var cell;

			// Suspend painting for optimalization
			sheet.suspendPaint ();
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						cell = sheet.getCell(i, j);
						var tag = cell.tag();
						if (tag) {
							if (tag.tags == null) {
								tag.tags = [];
							}
							if (turnOn && tag.tags.indexOf('ytd') == -1) {
								tag.tags.push('ytd');
							} else if (!turnOn && tag.tags.indexOf('ytd') != -1) {
								var index = tag.tags.indexOf('ytd');
								tag.tags.splice(index, 1);
							}

							saveMapping(tag);
						}
					}
				}
			}
			sheet.resumePaint();
			cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true);
			MappingService.refreshCurrentMapping(cell);
		}

		$scope.$on("MappingController:copyInputToOutputMapping", function(e, info) {
			handleCopyMappings('input');
		});

		$scope.$on("MappingController:copyOutputToInputMapping", function(e, info) {
			handleCopyMappings('output');
		});

		$scope.$on("MappingController:unSetYearToDate", function(e, info) {
			handleYearToDate(false);
		});

		$scope.$on("MappingController:setYearToDate", function(e, info) {
			handleYearToDate(true);
		});
		/************************************ FILL DIMENSION ************************************************/
		/**
		 * Invoked when we want to autofill mappings in our spreadsheet. Autofillings depends on view mode. If view mode is standard ('VALUE') we don't override drag fill function.
		 */
		function handleDragFillBlock(info) {
			if ($scope.viewMode.currentItem.name === "INPUT" || $scope.viewMode.currentItem.name === "OUTPUT") {
				if (info.autoFillType == 1) { // 'Fill Series'
					var sheet = $scope.spread.getActiveSheet();
					var rangeToFill = new GC.Spread.Sheets.Range(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), 1, 1);
					rangeToFill = rangeToFill.union(info.fillRange);
					openFillByDimensionModal(rangeToFill);
				}
			}
		}

		/**
		 * Open modal which helps autofill our mapping. Modal contains dimensions which we want to autofill. If mapping hasn't got dimension, dimension couldn't be autofilled.
		 */
		function openFillByDimensionModal(rangeToFill) {
			var cellMode = ViewModeService.getCurrentViewMode().name;
			if (cellMode == "VALUE") {
				return false;
			}
			var field = (cellMode.toLowerCase() == "formula") ? "text" : cellMode.toLowerCase() + "Mapping";
			var sheet = $scope.spread.getActiveSheet();
			var cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true);
			var activeCellTag = cell.tag();
			if (!activeCellTag) {
				return;
			}

			var offset = rangeToFill.rowCount - 1 + rangeToFill.colCount - 1;
			var modelId = 1;

			var modalInstance = $modal.open({
				templateUrl: $BASE_TEMPLATE_PATH + 'mappings/fillByDimension.html',
				windowClass: 'softpro-modals no-resize',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						var mapping = activeCellTag[field];

						$scope.currentDim = -1;
						$scope.isDataLoading = false;
						for (var i = 0; i < 3; i++) {
							$scope["dim" + i] = "";
						}

						if (mapping) {
							//var dimensions = mapping.match(/dim[012]=\".*?\"/g);
							var dimensions = mapping.match(/dim[012]=.[^,\)]+/g);
							if (dimensions !== null) {
								for (i = 0; i < dimensions.length; i++) {
									for (var j = 0; j < 3; j++) {
										if ($scope["dim" + j] === "" && dimensions[i].indexOf("dim" + j) !== -1) {
											$scope["dim" + j] = dimensions[i].replace("dim" + j + "=", "").replace(/\"/g, "");
										}
									}
								}
							}
						}

						/**
						 * Invoked when we change dimension which is used to autofill cells
						 */
						$scope.changeCurrentDim = function(dim) {
							$scope.currentDim = dim;
						};

						/**
						 * Save (manage) dimensions which are used to autofill cells. Dimensions could be taken from backend (dim1 && dim0). Calendar dimensions are managed in frontend
						 */
						$scope.save = function() {
							var dimensions;
							if ($scope.currentDim === 0 || $scope.currentDim === 1) {
								$scope.isDataLoading = true;
								MappingService.fillByDimension(modelId, $scope.currentDim, $scope["dim" + $scope.currentDim], offset).success(function(response) {
									dimensions = manageDatabaseDimensions(response);
									fillByDimensions(dimensions);
								});
							} else if ($scope.currentDim === 2) {
								dimensions = manageCalendarDimensions(offset);
								fillByDimensions(dimensions);
							}
						};

						$scope.close = function() {
							$modalInstance.close();
						};


						/**
						 * Manage dimensions from database in array of structure element visIds
						 */
						function manageDatabaseDimensions(data) {
							var dimensions = [];
							angular.forEach(data, function(dimension) {
								dimensions.push(dimension.structureElementVisId);
							});
							return dimensions;
						}

						/**
						 * Manage calendar dimensions in array of specific strings
						 */
						function manageCalendarDimensions(offset) {
							var dimensions = [];
							var date = $scope.dim2.split("/");
							if (date.length > 0) {
								var year = -1,
									month = -1;

								angular.forEach(date, function(value, index) {
									if (index == 1) {
										if (value !== "?") {
											year = parseInt(value);
										} else {
											year = value;
										}
									}
									if (index == 2) {
										if (value !== "?") {
											month = parseInt(value);
										} else {
											month = value;
										}
									}
								});

								if ((isNaN(year) && year !== "?") || (isNaN(month) && month !== "?")) {
									return dimensions;
								}

								var tempDim = "";
								var i;
								if (year === "?") {
									for (i = 0; i < offset; i++) {
										if (month == 12) {
											month = 1;
											tempDim = "\/?\/" + month;
										} else if ((month > -1) && (month < 12)) {
											month++;
											tempDim = "\/?\/" + month;
										}
										dimensions.push(tempDim);
									}
								} else {
									for (i = 0; i < offset; i++) {
										if (month == 12) {
											year++;
											month = 1;
											tempDim = "\/" + year + "\/" + month;
										} else if ((month > -1) && (month < 12)) {
											month++;
											tempDim = "\/" + year + "\/" + month;
										} else if (month === "?") {
											year++;
											tempDim = "\/" + year + "\/" + month;
										} else {
											year++;
											tempDim = "\/" + year;
										}
										dimensions.push(tempDim);
									}
								}
							}
							return dimensions;
						}

						/**
						 * Manage mappings (in fact setting tags) for cells which are stored in rangeFill object
						 */
						function fillByDimensions(dimensions) {
							if (dimensions.length > 0) {
								//var re = new RegExp("dim" + $scope.currentDim + "=\".*?\"");
								var re = new RegExp("dim" + $scope.currentDim + "=.[^,)]+");
								var rowIndex = -1;
								var colIndex = 0;
								for (var i = rangeToFill.row; i < rangeToFill.row + rangeToFill.rowCount; i++) {
									for (var j = rangeToFill.col; j < rangeToFill.col + rangeToFill.colCount; j++) {
										if (i === rangeToFill.row && j === rangeToFill.col) { // skip selected active cell
											colIndex++;
											continue;
										}
										var spreadCell = sheet.getCell(i, j);
										var tag = spreadCell.tag();
										if (tag) {
											tag = angular.copy(tag);
										} else {
											tag = angular.copy(activeCellTag);
										}

										var mapping = tag[field];
										if (angular.isDefined(dimensions[rowIndex + colIndex])) {
											var dim = dimensions[rowIndex + colIndex];
											if (mapping) {
												tag[field] = mapping.replace(re, "dim" + $scope.currentDim + "=\"" + dim + "\"");
											} else {
												mapping = activeCellTag[field];
												tag[field] = mapping.replace(re, "dim" + $scope.currentDim + "=\"" + dim + "\"");
											}
										}

										spreadCell.tag(tag);
										colIndex++;
									}
									rowIndex++;
									colIndex = 0;
								}
							}
							$scope.isDataLoading = false;
							$modalInstance.close();
						}
					}
				]
			});
		}

		$scope.$on("SpreadSheetController:DragFillBlock", function(e, info) {
			handleDragFillBlock(info);
		});

		/************************************ RESET CELLS ************************************************/
		/**
		 * Reset mapping for specified type. If type is equals to 'all', all mappings are deleted.
		 */
		function resetMapping(type) {
			MappingService.override = true;
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			var sels = sheet.getSelections();
			var cell;
			for (var n = 0; n < sels.length; n++) {
				var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
				for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
					for (var j = sel.col; j < sel.col + sel.colCount; j++) {
						cell = sheet.getCell(i, j);
						var tag = cell.tag();
						if (tag) {
							tag = angular.copy(tag);

							if (type == 'input') {
								tag.inputMapping = null;
							} else if (type == 'output') {
								tag.outputMapping = null;
							} else if (type == 'formula') {
								tag.text = null;
							} else if (type == 'all') {
								tag.inputMapping = null;
								tag.outputMapping = null;
								tag.text = null;
							}
							cell.tag(tag);
						}
					}
				}
			}
			sheet.resumePaint();
			$scope.spread.repaint();
			MappingService.override = false;

			cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true);
			MappingService.refreshCurrentMapping(cell);
		}

		$scope.$on("MappingController:resetInputMapping", function(e, info) {
			resetMapping('input');
		});

		$scope.$on("MappingController:resetOutputMapping", function(e, info) {
			resetMapping('output');
		});

		$scope.$on("MappingController:resetFormulaMapping", function(e, info) {
			resetMapping('formula');
		});

		$scope.$on("MappingController:resetAllMappings", function(e, info) {
			resetMapping('all');
		});

		$scope.$on("MappingController:resetWholeCell", function(e, info) {
			resetMapping('all');
			SpreadSheetService.clearCells($scope.spread);
		});

		/************************************ CELL PICKER ************************************************/
		/**
		 * Open modal (for insert or update). Insert when financeCubeId equals -1, update otherwise.
		 * @param  {Integer} financeCubeId
		 */
		var openModal = function(modelId, financeCubeId) {
			var currentSpreadSheet = CoreCommonsService.findElementByKey($scope.spread.sheets, $scope.spread.getActiveSheet().getName(), "_name");
			var modalInstance = $modal.open({
				template: '<cell-picker id="cell-picker"></cell-picker>',
				windowClass: 'cell-picker-modals softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.financeCubeId = financeCubeId;
						$scope.currentSpreadSheet = currentSpreadSheet;
						$scope.$on('CellPicker:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		};

		$scope.$on("MappingController:cellPicker", function(e, info) {
			openModal();
		});
	}
})();