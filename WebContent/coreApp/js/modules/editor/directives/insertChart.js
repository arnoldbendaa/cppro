/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 * 
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without
 * the prior written consent of IT Services Jacek Kurasiewicz. Contact The
 * Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok. 43, 00-673
 * Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 * 
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING
 * LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION,
 * EVEN IF IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING
 * DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES
 * JACEK KURASIEWICZ HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 * ENHANCEMENTS, OR MODIFICATIONS.
 ******************************************************************************/
(function() {
	'use strict';

	angular.module('coreApp.editor').directive('insertChart', insertChart);

	/* @ngInject */
	function insertChart(SpreadSheetService, $timeout, $rootScope,
			CoreCommonsService, ColorService, InsertService) {

		return {
			restrict : 'E',
			templateUrl : $BASE_PATH
					+ 'coreApp/js/modules/editor/views/insertChart.html',
			scope : {
				type : '=type',
				types : '=types',
				spread : '=spread',
				previousData : '=previousData',
				previousLocationRange : '=previousLocationRange',
				close : '&',
			},
			replace : true,
			link : function linkFunction($scope) {

				// parameters to resize modal
				var modalDialog = $('#insert-chart').closest('.modal-dialog');
				var elementToResize = null;
				var additionalElementsToCalcResize = [];

				$scope.cookieName = 'flatFormEditor_modal_insertChart';
				$scope.resizedColumn = resizedColumn;
				$scope.sortedColumn = sortedColumn;
				$scope.getSelections = getSelections;
				$scope.insert = insert;
				$scope.close = close;
				$scope.selectedSheet = 0;
				$scope.sheets = [];

				// update one field in modal during changing selection
				$scope.spread.bind(GC.Spread.Sheets.Events.SelectionChanging,
						function(e, info) {
							$timeout(function() {
								var sheet = $scope.spread.getActiveSheet();
								$scope[$scope.currentField] = InsertService
										.convertSelectionToRange(sheet);
							}, 0);
						});

				// show full modal after change selection
				$scope.spread.bind(GC.Spread.Sheets.Events.SelectionChanged,
						function(e, info) {
					var ctrlPressed = e.ctrlKey || (e.keyCode === 17);

						console.log(ctrlPressed);
							$timeout(function() {
								$scope.getSelections($scope.currentField);
							}, 0);
						});

				// bootstrap hint settings for form fields
				$scope.hint = {
					delay : 500,
					// atb: true,
					trigger : 'mouseenter',
					placement : 'bottom',
				};

				// default labels for form fields
				$scope.fieldLabels = {
					dataRange : 'Data Range',
					color : 'Color',
					color2 : 'Color',
				};
				// default descriptions for form fields used in every sparkline
				$scope.descriptions = {
					dataRange : 'A reference that represents a range of cells containing all values to be described, such as "A1:A10".',
					xRange : 'chart x axis data',
					yRange : 'chart Y axis data',
				};

				$scope.dataRange = '';
				$scope.locationRange = '';

				$scope.currentField = null;

				// default descriptions for other form fields used many times
				var commonDescriptions = {
					vertical : 'A boolean that represents whether to display the sparkline vertically.',
					showAverage : 'A boolean that represents whether to show the average.',
					labelRange : 'A reference that represents a cell range that contains all labels, such as "C1:C4". This setting is optional. The default value is empty.',
					hbarVbarValue : 'A number or reference that represents the length of the bar. The value should be between 100% and -100%. If the value is greater than 100% or smaller than -100%, an arrow is displayed.',
					dateAxisData : 'A range reference that represents sparkline date axis data. For example: D1:F3. This setting is optional.',
					color : 'Main sparkline color.',
					colorRange : 'A range reference that represents colors. For example: cells A1:D1 which contain values "#2861ab", "red", "yellow", "rgb(55, 233, 81)". It must be one column or one row.',
				};

				// common validation messages
				$scope.validationMessage1 = 'Invalid '
						+ $scope.fieldLabels.dataRange;
				$scope.validationMessage2 = 'Please set one column or one row';
				$scope.validationMessage3 = 'Invalid Location Range';
				$scope.validationMessage4 = 'Invalid Color Range';

				activate();

				/**
				 * ************************************************
				 * IMPLEMENTATION
				 * ************************************************************************
				 */

				function resetRangeParams() {
					$scope.dataStartCol = '';
					$scope.dataStartRow = '';
					$scope.dataEndCol = '';
					$scope.dataEndRow = '';
					$scope.locationStartCol = '';
					$scope.locationStartRow = '';
					$scope.locationEndCol = '';
					$scope.locationEndRow = '';
				}

				function resizedColumn(sender, args) {
					CoreCommonsService.resizedColumn(args, $scope.cookieName);
				}

				function sortedColumn(sender, args) {
					CoreCommonsService.sortedColumn(args, $scope.cookieName);
				}

				// minimize modal to select cell range
				// or maximize after select (or after restore button click)
				function getSelections(field) {
					if ($scope.currentField != field) {
						SpreadSheetService.unblockExcelView($scope.spread);
						$scope.currentField = field;
						$('.modal.softpro-modals').addClass('minimized');
						$('.modal-backdrop').hide();
						$('.fieldRange').hide();
						$('.fieldRange .spread-btn i').removeClass(
								'wijmo-wijribbon-filldown').addClass(
								'wijmo-wijribbon-fillup');
						$('.fieldRange-' + $scope.currentField).addClass(
								'fullWidth').show();
						$(
								'.fieldRange-' + $scope.currentField
										+ ' .spread-btn i').addClass(
								'wijmo-wijribbon-filldown').removeClass(
								'wijmo-wijribbon-fillup');
					} else {
						SpreadSheetService.blockExcelView($scope.spread);
						$('input[name="' + $scope.currentField + '"]').focus();
						$scope.currentField = null;
						$('.modal.softpro-modals').removeClass('minimized');
						$('.modal-backdrop').show();
						$('.fieldRange').removeClass('fullWidth').show();
						$('.fieldRange .spread-btn i').removeClass(
								'wijmo-wijribbon-filldown').addClass(
								'wijmo-wijribbon-fillup');
					}
				}

				// pre-validate data and insert formula into spreadsheet cell
				function insert() {
					// if (validateDataRange()) {
					var dataRangeOrientation = InsertService.checkOrientation(
							$scope.dataStartRow, $scope.dataEndRow,
							$scope.dataStartCol, $scope.dataEndCol);
					var locationRangeOrientation = InsertService
							.checkOrientation($scope.locationStartRow,
									$scope.locationEndRow,
									$scope.locationStartCol,
									$scope.locationEndCol);
					// validate data and location range
					if (($scope.type == 'Line' || $scope.type == 'Column' || $scope.type == 'Win/Loss')
							&& dataRangeOrientation === null) {
						// dataRange for those sparklines must be one column or
						// one row
						swal($scope.validationMessage1,
								$scope.validationMessage2, 'warning');
					} else if ($scope.type == 'Scatter'
							&& dataRangeOrientation !== null) {
						// dataRange for Scatter must be at least one column or
						// one row
						swal(
								$scope.validationMessage1,
								'Please set at least two columns and two rows.',
								'warning');
					} else if (($scope.type == 'Cascade' || $scope.type == 'Pareto')
							&& locationRangeOrientation === null) {
						// locationRange for those sparklines must be one column
						// or one row
						swal($scope.validationMessage3,
								$scope.validationMessage2, 'warning');
					} else {
						// can insert formula
						// data fields the same for all sparklines
						var data = {
							type : $scope.type,
							sheet:$scope.selectedSheet,
							dataRange : $scope.dataRange,
							dataRangeOrientation : dataRangeOrientation,
							locationStartCol : InsertService
									.getColumnIndexByName($scope.locationStartCol),
							locationStartRow : $scope.locationStartRow - 1,
							locationEndCol : InsertService
									.getColumnIndexByName($scope.locationEndCol),
							locationEndRow : $scope.locationEndRow - 1,
						};
						switch ($scope.type) {
						case $scope.types[0]: // Line
							data.dateAxisData = ($scope.dateAxisData !== undefined) ? $scope.dateAxisData
									: '';
							data.color = ($scope.color !== undefined) ? $scope.color
									: 'rgb(0, 0, 255)';
							break;
						case $scope.types[1]: // Column
							data.dateAxisData = ($scope.dateAxisData !== undefined) ? $scope.dateAxisData
									: '';
							data.color = ($scope.color !== undefined) ? $scope.color
									: 'rgb(0, 0, 255)';
							break;
						case $scope.types[2]: // Win/Loss
							data.dateAxisData = ($scope.dateAxisData !== undefined) ? $scope.dateAxisData
									: '';
							data.color = ($scope.color !== undefined) ? $scope.color
									: 'rgb(0, 0, 255)';
							break;
						case $scope.types[3]: // Pie
							// prepare color from colorRange (if is set) or just
							// take color
							// (Pie can have one color or string with many cells
							// coordinates)
							if ($scope.colorRange !== undefined
									&& $scope.colorRange !== '') {
								var colorRangeStartCol, colorRangeStartRow, colorRangeEndCol, colorRangeEndRow;
								data.colorRange = '';
								// validate colorRange
								var colorRangeStartValidate = InsertService
										.validateRange($scope.colorRange
												.split(':')[0],
												$scope.validationMessage4);
								if (colorRangeStartValidate.validationError === true) {
									return;
								} else {
									colorRangeStartCol = colorRangeStartValidate.col;
									colorRangeStartRow = colorRangeStartValidate.row;
								}
								var colorRangeEndValidate = InsertService
										.validateRange($scope.colorRange
												.split(':')[1],
												$scope.validationMessage4);
								if (colorRangeEndValidate.validationError === true) {
									return;
								} else {
									colorRangeEndCol = colorRangeEndValidate.col;
									colorRangeEndRow = colorRangeEndValidate.row;
								}
								var colorRangeOrientation = InsertService
										.checkOrientation(colorRangeStartRow,
												colorRangeEndRow,
												colorRangeStartCol,
												colorRangeEndCol);
								// try to prepare color from colorRange - for
								// Pie it's i.e. 'B3,B4,B5,B6' from range B3:B6
								if (colorRangeOrientation === null) {
									swal($scope.validationMessage4,
											$scope.validationMessage2,
											'warning');
									return;
								} else if (colorRangeOrientation === 0) { // vertical
									for (var rowIndex = colorRangeStartRow; rowIndex <= colorRangeEndRow; rowIndex++) {
										data.colorRange += colorRangeStartCol
												+ rowIndex;
										if (rowIndex < colorRangeEndRow) {
											data.colorRange += ',';
										}
									}
								} else if (colorRangeOrientation == 1) { // horizontal
									var colorRangeStartColIndex = InsertService
											.getColumnIndexByName(colorRangeStartCol);
									var colorRangeEndColIndex = InsertService
											.getColumnIndexByName(colorRangeEndCol);
									for (var colIndex = colorRangeStartColIndex; colIndex <= colorRangeEndColIndex; colIndex++) {
										data.colorRange += InsertService
												.getColumnNameByIndex(colIndex + 1)
												+ colorRangeStartRow;
										if (colIndex < colorRangeEndColIndex) {
											data.colorRange += ',';
										}
									}
								}
							} else if ($scope.color !== undefined) {
								data.colorRange = '"' + $scope.color + '"';
							} else {
								data.colorRange = '"rgb(200,100,255)"';
							}
							break;
						case $scope.types[4]: // Area
							data.minimumValue = ($scope.minimumValue !== undefined) ? $scope.minimumValue
									: '';
							data.maximumValue = ($scope.maximumValue !== undefined) ? $scope.maximumValue
									: '';
							data.line1 = ($scope.line1 !== undefined) ? $scope.line1
									: '';
							data.line2 = ($scope.line2 !== undefined) ? $scope.line2
									: '';
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							data.color2 = ($scope.color2 !== undefined) ? $scope.color2
									: '';
							break;
						case $scope.types[5]: // Scatter
							data.points2 = ($scope.points2 !== undefined) ? $scope.points2
									: '';
							data.minX = ($scope.minX !== undefined) ? $scope.minX
									: '';
							data.maxX = ($scope.maxX !== undefined) ? $scope.maxX
									: '';
							data.minY = ($scope.minY !== undefined) ? $scope.minY
									: '';
							data.maxY = ($scope.maxY !== undefined) ? $scope.maxY
									: '';
							data.hLine = ($scope.hLine !== undefined) ? $scope.hLine
									: '';
							data.vLine = ($scope.vLine !== undefined) ? $scope.vLine
									: '';
							data.xMinZone = ($scope.xMinZone !== undefined) ? $scope.xMinZone
									: '';
							data.xMaxZone = ($scope.xMaxZone !== undefined) ? $scope.xMaxZone
									: '';
							data.yMinZone = ($scope.yMinZone !== undefined) ? $scope.yMinZone
									: '';
							data.yMaxZone = ($scope.yMaxZone !== undefined) ? $scope.yMaxZone
									: '';
							data.tags = ($scope.tags !== undefined) ? $scope.tags
									: '';
							data.drawSymbol = ($scope.drawSymbol !== undefined) ? $scope.drawSymbol
									: '';
							data.drawLines = ($scope.drawLines !== undefined) ? $scope.drawLines
									: '';
							data.dash = ($scope.dash !== undefined) ? $scope.dash
									: '';
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							data.color2 = ($scope.color2 !== undefined) ? $scope.color2
									: '';
							break;
						case $scope.types[6]: // Spread
							data.scaleStart = ($scope.scaleStart !== undefined) ? $scope.scaleStart
									: '';
							data.scaleEnd = ($scope.scaleEnd !== undefined) ? $scope.scaleEnd
									: '';
							data.style = ($scope.style !== undefined) ? $scope.style.id
									: '';
							data.showAverage = ($scope.showAverage !== undefined) ? $scope.showAverage
									: false;
							data.vertical = ($scope.vertical !== undefined) ? $scope.vertical
									: false;
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							break;
						case $scope.types[7]: // Stacked
							data.maximumValue = ($scope.maximumValue !== undefined) ? $scope.maximumValue
									: '';
							data.labelRange = ($scope.labelRange !== undefined) ? $scope.labelRange
									: '';
							data.highlightPosition = ($scope.highlightPosition !== undefined) ? $scope.highlightPosition
									: '';
							data.textOrientation = ($scope.textOrientation !== undefined) ? $scope.textOrientation
									: '';
							data.textSize = ($scope.textSize !== undefined) ? $scope.textSize
									: '';
							data.targetRed = ($scope.targetRed !== undefined) ? $scope.targetRed
									: '';
							data.targetGreen = ($scope.targetGreen !== undefined) ? $scope.targetGreen
									: '';
							data.targetBlue = ($scope.targetBlue !== undefined) ? $scope.targetBlue
									: '';
							data.targetYellow = ($scope.targetYellow !== undefined) ? $scope.targetYellow
									: '';
							data.vertical = ($scope.vertical !== undefined) ? $scope.vertical
									: false;
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							data.colorRange = ($scope.colorRange !== undefined) ? $scope.colorRange
									: '';
							break;
						case $scope.types[8]: // BoxPlot
							data.scaleStart = ($scope.scaleStart !== undefined) ? $scope.scaleStart
									: '';
							data.scaleEnd = ($scope.scaleEnd !== undefined) ? $scope.scaleEnd
									: '';
							data.acceptableStart = ($scope.acceptableStart !== undefined) ? $scope.acceptableStart
									: '';
							data.acceptableEnd = ($scope.acceptableEnd !== undefined) ? $scope.acceptableEnd
									: '';
							data.boxPlotClass = ($scope.boxPlotClass !== undefined) ? '"'
									+ $scope.boxPlotClass + '"'
									: '';
							data.style = ($scope.style !== undefined) ? $scope.style.id
									: '';
							data.showAverage = ($scope.showAverage !== undefined) ? $scope.showAverage
									: false;
							data.vertical = ($scope.vertical !== undefined) ? $scope.vertical
									: false;
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							break;
						case $scope.types[9]: // Cascade
							data.labelRange = ($scope.labelRange !== undefined) ? $scope.labelRange
									: '';
							data.minimumValue = ($scope.minimumValue !== undefined) ? $scope.minimumValue
									: '';
							data.maximumValue = ($scope.maximumValue !== undefined) ? $scope.maximumValue
									: '';
							data.vertical = (locationRangeOrientation == 1) ? true
									: false;
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							data.color2 = ($scope.color2 !== undefined) ? $scope.color2
									: '';
							break;
						case $scope.types[10]: // Pareto
							data.target = ($scope.target !== undefined) ? $scope.target
									: '';
							data.target2 = ($scope.target2 !== undefined) ? $scope.target2
									: '';
							data.highlightPosition = ($scope.highlightPosition !== undefined) ? $scope.highlightPosition
									: '';
							data.label = ($scope.label !== undefined) ? $scope.label.id
									: '';
							data.vertical = (locationRangeOrientation == 1) ? true
									: false;
							data.colorRange = ($scope.colorRange !== undefined) ? $scope.colorRange
									: '';
							break;
						case $scope.types[11]: // Bullet
							data.target = ($scope.target !== undefined) ? $scope.target
									: '';
							data.maxi = ($scope.maxi !== undefined) ? $scope.maxi
									: '';
							data.good = ($scope.good !== undefined) ? $scope.good
									: '';
							data.bad = ($scope.bad !== undefined) ? $scope.bad
									: '';
							data.forecast = ($scope.forecast !== undefined) ? $scope.forecast
									: '';
							data.tickunitBullet = ($scope.tickunitBullet !== undefined) ? $scope.tickunitBullet
									: '';
							data.vertical = ($scope.vertical !== undefined) ? $scope.vertical
									: false;
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							break;
						case $scope.types[12]: // Hbar
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							break;
						case $scope.types[13]: // Vbar
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							break;
						case $scope.types[14]: // Variance
							data.reference = ($scope.reference !== undefined) ? $scope.reference
									: '';
							data.mini = ($scope.mini !== undefined) ? $scope.mini
									: '';
							data.maxi = ($scope.maxi !== undefined) ? $scope.maxi
									: '';
							data.mark = ($scope.mark !== undefined) ? $scope.mark
									: '';
							data.tickunit = ($scope.tickunit !== undefined) ? $scope.tickunit
									: '';
							data.legend = ($scope.legend !== undefined) ? $scope.legend
									: false;
							data.vertical = ($scope.vertical !== undefined) ? $scope.vertical
									: false;
							data.color = ($scope.color !== undefined) ? $scope.color
									: '';
							data.color2 = ($scope.color2 !== undefined) ? $scope.color2
									: '';
							break;
						}
						$rootScope.$broadcast('InsertChart:insertChart', data);
						$scope.close();
					}
					// }
				}

				function close() {
					$rootScope.$broadcast('InsertSparkline:close');
				}

				function validateDataRange() {
					resetRangeParams();
					var dataStartEnd = $scope.dataRange.split(':');
					var dataStart = dataStartEnd[0];
					var dataEnd = dataStartEnd[1];
					var locationStartEnd = $scope.locationRange.split(':');
					var locationStart = locationStartEnd[0];
					var locationEnd = locationStartEnd[1];
					// if (dataStart === undefined || dataEnd === undefined) {
					// swal($scope.validationMessage1, "", "warning");
					// return;
					// }

					var dataStartValidate = InsertService.validateRange(
							dataStart, $scope.validationMessage1);
					if (dataStartValidate.validationError === true) {
						return;
					} else {
						$scope.dataStartCol = dataStartValidate.col;
						$scope.dataStartRow = dataStartValidate.row;
					}

					var dataEndValidate = InsertService.validateRange(dataEnd,
							$scope.validationMessage1);
					if (dataEndValidate.validationError === true) {
						return;
					} else {
						$scope.dataEndCol = dataEndValidate.col;
						$scope.dataEndRow = dataEndValidate.row;
					}

					var locationStartValidate = InsertService.validateRange(
							locationStart, $scope.validationMessage3);
					if (locationStartValidate.validationError === true) {
						return;
					} else {
						$scope.locationStartCol = locationStartValidate.col;
						$scope.locationStartRow = locationStartValidate.row;
					}

					var locationEndValidate = InsertService.validateRange(
							locationEnd, $scope.validationMessage3);
					if (locationEndValidate.validationError === true) {
						return;
					} else {
						$scope.locationEndCol = locationEndValidate.col;
						$scope.locationEndRow = locationEndValidate.row;
					}

					if ($scope.dataStartCol === ''
							|| $scope.dataStartRow === ''
							|| $scope.dataEndCol === ''
							|| $scope.dataEndRow === '') {
						// swal($scope.validationMessage1, "", "warning");
						// return;
					}
					return true;
				}

				/**
				 * ************************************************ ACTIVATION
				 * ************************************************************************
				 */

				function activate() {
					var sheetLength = $scope.spread.sheets.length;
					for(var i = 0 ; i < sheetLength; i ++){
						$scope.sheets[i] = $scope.spread.sheets[i].name();
					}
					// try to resize and move modal based on the cookie
					CoreCommonsService.changeModalParams(modalDialog,
							$scope.cookieName);

					$timeout(function() { // timeout is necessary to pass
											// asynchro
						CoreCommonsService.allowResizingModal(modalDialog,
								elementToResize,
								additionalElementsToCalcResize, $scope.ctx);
					}, 0);

					SpreadSheetService.blockExcelView($scope.spread);
					resetRangeParams();
					// if create sparkline - take cell selection and use as
					// DataRange for new Sparkline
					// else (if edit sparkline)) - get parameters from formula
					// and set in modal fields
					if ($scope.previousData === undefined) {
						$scope.buttonName = 'Insert';
						var sheet = $scope.spread.getActiveSheet();
						$scope.dataRange = InsertService
								.convertSelectionToRange(sheet);
					} else {
						$scope.buttonName = 'Update';
						var color;
						$scope.dataRange = $scope.previousData[0];
						$scope.locationRange = $scope.previousLocationRange;
						switch ($scope.type) {
						case $scope.types[0]: // Line
							$scope.dateAxisData = $scope.previousData[2];
							color = InsertService
									.parseColor($scope.previousData[4]);
							if (color !== null) {
								$scope.color = color;
							}
							break;
						case $scope.types[1]: // Column
							$scope.dateAxisData = $scope.previousData[2];
							color = InsertService
									.parseColor($scope.previousData[4]);
							if (color !== null) {
								$scope.color = color;
							}
							break;
						case $scope.types[2]: // Win/Loss
							$scope.dateAxisData = $scope.previousData[2];
							color = InsertService
									.parseColor($scope.previousData[4]);
							if (color !== null) {
								$scope.color = color;
							}
							break;
						case $scope.types[3]: // Pie
							if ($scope.previousData[2] !== undefined) {
								// prepare range
								$scope.colorRange = $scope.previousData[1]
										+ ':'
										+ $scope.previousData.slice(-1)[0];
							} else if ($scope.previousData[1] !== undefined) {
								if ($scope.previousData[1].toLowerCase()
										.indexOf('rgb') > -1) {
									$scope.color = InsertService
											.stripQuotes($scope.previousData[1]);
								} else {
									$scope.colorRange = $scope.previousData[1]
											+ ':' + $scope.previousData[1];
								}
							}
							break;
						case $scope.types[4]: // Area
							$scope.minimumValue = $scope.previousData[1];
							$scope.maximumValue = $scope.previousData[2];
							$scope.line1 = $scope.previousData[3];
							$scope.line2 = $scope.previousData[4];
							$scope.color = InsertService
									.stripQuotes($scope.previousData[5]);
							$scope.color2 = InsertService
									.stripQuotes($scope.previousData[6]);
							break;
						case $scope.types[5]: // Scatter
							$scope.points2 = $scope.previousData[1];
							$scope.minX = $scope.previousData[2];
							$scope.maxX = $scope.previousData[3];
							$scope.minY = $scope.previousData[4];
							$scope.maxY = $scope.previousData[5];
							$scope.hLine = $scope.previousData[6];
							$scope.vLine = $scope.previousData[7];
							$scope.xMinZone = $scope.previousData[8];
							$scope.xMaxZone = $scope.previousData[9];
							$scope.yMinZone = $scope.previousData[10];
							$scope.yMaxZone = $scope.previousData[11];
							$scope.tags = InsertService
									.prepareBoolean($scope.previousData[12]);
							$scope.drawSymbol = InsertService
									.prepareBoolean($scope.previousData[13]);
							$scope.drawLines = InsertService
									.prepareBoolean($scope.previousData[14]);
							$scope.color = InsertService
									.stripQuotes($scope.previousData[15]);
							$scope.color2 = InsertService
									.stripQuotes($scope.previousData[16]);
							$scope.dash = InsertService
									.prepareBoolean($scope.previousData[17]);
							break;
						case $scope.types[6]: // Spread
							$scope.showAverage = InsertService
									.prepareBoolean($scope.previousData[1]);
							$scope.scaleStart = $scope.previousData[2];
							$scope.scaleEnd = $scope.previousData[3];
							$scope.style = $scope.previousData[4];
							$scope.color = InsertService
									.stripQuotes($scope.previousData[5]);
							$scope.vertical = InsertService
									.prepareBoolean($scope.previousData[6]);
							break;
						case $scope.types[7]: // Stacked
							$scope.colorRange = $scope.previousData[1];
							$scope.labelRange = $scope.previousData[2];
							$scope.maximumValue = $scope.previousData[3];
							$scope.targetRed = $scope.previousData[4];
							$scope.targetGreen = $scope.previousData[5];
							$scope.targetBlue = $scope.previousData[6];
							$scope.targetYellow = $scope.previousData[7];
							$scope.color = InsertService
									.stripQuotes($scope.previousData[8]);
							$scope.highlightPosition = $scope.previousData[9];
							$scope.vertical = InsertService
									.prepareBoolean($scope.previousData[10]);
							$scope.textOrientation = (parseInt($scope.previousData[11]) === 0 || parseInt($scope.previousData[11]) == 1) ? $scope.previousData[11]
									: 0;
							$scope.textSize = $scope.previousData[12];
							break;
						case $scope.types[8]: // BoxPlot
							var bpCl = InsertService
									.stripQuotes($scope.previousData[1]); // boxPlotClass
							$scope.boxPlotClass = (bpCl == '5ns'
									|| bpCl == '7ns' || bpCl == 'tukey'
									|| bpCl == 'bowley' || bpCl == 'sigma3') ? bpCl
									: '5ns';
							$scope.showAverage = InsertService
									.prepareBoolean($scope.previousData[2]);
							$scope.scaleStart = $scope.previousData[3];
							$scope.scaleEnd = $scope.previousData[4];
							$scope.acceptableStart = $scope.previousData[5];
							$scope.acceptableEnd = $scope.previousData[6];
							$scope.color = InsertService
									.stripQuotes($scope.previousData[7]);
							$scope.style = $scope.previousData[8];
							$scope.vertical = InsertService
									.prepareBoolean($scope.previousData[9]);
							break;
						case $scope.types[9]: // Cascade
							$scope.labelRange = $scope.previousData[2];
							$scope.minimumValue = $scope.previousData[3];
							$scope.maximumValue = $scope.previousData[4];
							$scope.color = InsertService
									.stripQuotes($scope.previousData[5]);
							$scope.color2 = InsertService
									.stripQuotes($scope.previousData[6]);
							$scope.vertical = $scope.previousData[7];
							break;
						case $scope.types[10]: // Pareto
							$scope.colorRange = $scope.previousData[2];
							$scope.target = $scope.previousData[3];
							$scope.target2 = $scope.previousData[4];
							$scope.highlightPosition = $scope.previousData[5];
							$scope.label = $scope.previousData[6];
							$scope.vertical = $scope.previousData[7];
							break;
						case $scope.types[11]: // Bullet
							$scope.target = $scope.previousData[1];
							$scope.maxi = $scope.previousData[2];
							$scope.good = $scope.previousData[3];
							$scope.bad = $scope.previousData[4];
							$scope.forecast = $scope.previousData[5];
							$scope.tickunitBullet = $scope.previousData[6];
							$scope.color = InsertService
									.stripQuotes($scope.previousData[7]);
							$scope.vertical = InsertService
									.prepareBoolean($scope.previousData[8]);
							break;
						case $scope.types[12]: // Hbar
							$scope.color = InsertService
									.stripQuotes($scope.previousData[1]);
							break;
						case $scope.types[13]: // Vbar
							$scope.color = InsertService
									.stripQuotes($scope.previousData[1]);
							break;
						case $scope.types[14]: // Variance
							$scope.reference = $scope.previousData[1];
							$scope.mini = $scope.previousData[2];
							$scope.maxi = $scope.previousData[3];
							$scope.mark = $scope.previousData[4];
							$scope.tickunit = $scope.previousData[5];
							$scope.legend = InsertService
									.prepareBoolean($scope.previousData[6]);
							$scope.color = InsertService
									.stripQuotes($scope.previousData[7]);
							$scope.color2 = InsertService
									.stripQuotes($scope.previousData[8]);
							$scope.vertical = InsertService
									.prepareBoolean($scope.previousData[9]);
							break;
						}
					}

					// set descriptions (hints) and default values for form
					// fields
					switch ($scope.type) {
					case $scope.types[0]: // Line
						$scope.descriptions.dateAxisData = commonDescriptions.dateAxisData;
						$scope.descriptions.color = commonDescriptions.color;
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(0, 0, 255)';
						break;
					case $scope.types[1]: // Column
						$scope.descriptions.dateAxisData = commonDescriptions.dateAxisData;
						$scope.descriptions.color = commonDescriptions.color;
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(0, 0, 255)';
						break;
					case $scope.types[2]: // Win/Loss
						$scope.descriptions.dateAxisData = commonDescriptions.dateAxisData;
						$scope.descriptions.color = commonDescriptions.color;
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(0, 0, 255)';
						break;
					case $scope.types[3]: // Pie
						$scope.descriptions.color = 'Main sparkline color. It is used if Color Range is empty.';
						$scope.descriptions.colorRange = commonDescriptions.colorRange;
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(200,100,255)';
						break;
					case $scope.types[4]: // Area
						$scope.fieldLabels.color = 'Color Positive';
						$scope.fieldLabels.color2 = 'Color Negative';
						$scope.descriptions.minimumValue = 'A number that represents the minimum value of the sparkline. This setting is optional.';
						$scope.descriptions.maximumValue = 'A number that represents the maximum value of the sparkline. This setting is optional.';
						$scope.descriptions.line1 = 'A number that represents a horizontal line\'s vertical position. This setting is optional.';
						$scope.descriptions.line2 = 'A number that represents a second horizontal line\'s vertical position. This setting is optional.';
						$scope.descriptions.color = 'A string that represents the area color of positive values.';
						$scope.descriptions.color2 = 'A string that represents the area color of negative values.';
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(0,128,0)';
						$scope.color2 = ($scope.color2 !== undefined) ? $scope.color2
								: 'rgb(255,0,0)';
						break;
					case $scope.types[5]: // Scatter
						$scope.fieldLabels.color = 'Color1';
						$scope.fieldLabels.color2 = 'Color2';
						$scope.fieldLabels.dataRange = 'Points1';
						// 'dataRange' means 'points1' here
						$scope.descriptions.dataRange = 'The first xy data series. If the row count is greater than or equal to the column count, use data from the first two columns. The first column contains x-values and the second column contains y-values. If the row count is less than the column count, use data from the first two rows. The first row contains x-values and the second row contains y-values.';
						$scope.descriptions.points2 = 'The second xy data series. If the row count is greater than or equal to the column count, use data from the first two columns. The first column contains x-values and the second column contains y-values. If the row count is less than the column count, use data from the first two rows. The first row contains x-values and the second row contains y-values.';
						$scope.descriptions.minX = 'The x-minimum limit of both series, each series has its own value if it is omitted.';
						$scope.descriptions.maxX = 'The x-maximum limit of both series, each series has its own value if it is omitted.';
						$scope.descriptions.minY = 'The y-minimum limit of both series, each series has its own value if it is omitted.';
						$scope.descriptions.maxY = 'The y-maximum limit of both series, each series has its own value if it is omitted.';
						$scope.descriptions.hLine = 'The horizontal axis position, there is no line if it is omitted.';
						$scope.descriptions.vLine = 'The vertical axis position, there is no line if it is omitted.';
						$scope.descriptions.xMinZone = 'The x-minimum value of the gray zone, there is no grey zone if any of these four parameters are omitted.';
						$scope.descriptions.xMaxZone = 'The x-maximum value of the gray zone, there is no grey zone if any of these four parameters are omitted.';
						$scope.descriptions.yMinZone = 'The y-minimum value of the gray zone, there is no grey zone if any of these four parameters are omitted.';
						$scope.descriptions.yMaxZone = 'The y-maximum value of the gray zone, there is no grey zone if any of these four parameters are omitted.';
						$scope.descriptions.tags = 'If this option is true, mark the point at which the y-value is the maximum of the first series as "blue", and mark the point at which the y-value is the minimum of the first series as "red".';
						$scope.descriptions.drawSymbol = 'If this option is true, draw each point as a symbol. The symbol of the first series is a circle, and the symbol of the second series is a square.';
						$scope.descriptions.drawLines = 'If this option is true, connect each point with a line by sequence in each series.';
						$scope.descriptions.dash = 'If this option is true, the line is a dashed line; otherwise, the line is a full line.';
						$scope.descriptions.color = 'The color string of the first point series.';
						$scope.descriptions.color2 = 'The color string of the second point series.';
						// default values
						$scope.tags = ($scope.tags !== undefined) ? $scope.tags
								: false;
						$scope.drawSymbol = ($scope.drawSymbol !== undefined) ? $scope.drawSymbol
								: true;
						$scope.drawLines = ($scope.drawLines !== undefined) ? $scope.drawLines
								: false;
						$scope.dash = ($scope.dash !== undefined) ? $scope.dash
								: false;
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(128, 128, 128)';
						$scope.color2 = ($scope.color2 !== undefined) ? $scope.color2
								: 'rgb(255,0,0)';
						break;
					case $scope.types[6]: // Spread
						$scope.styles = [
								{
									id : 1,
									name : '1 - Stacked - line from center to two sides',
								},
								{
									id : 2,
									name : '2 - Spread - dot from center to two sides',
								},
								{
									id : 3,
									name : '3 - Jitter - dot with a random location',
								},
								{
									id : 4,
									name : '4 - Poles - line from one side to another',
								},
								{
									id : 5,
									name : '5 - StackedDots - dot from one side to another',
								},
								{
									id : 6,
									name : '6 - Stripe - line with an equal length',
								} ];
						$scope.fieldLabels.color = 'Color Scheme';
						$scope.descriptions.color = commonDescriptions.color;
						$scope.descriptions.showAverage = commonDescriptions.showAverage;
						$scope.descriptions.scaleStart = 'A number that represents the minimum boundary of the sparkline. This setting is optional. The default value is the minimum of all values.';
						$scope.descriptions.scaleEnd = 'A number that represents the maximum boundary of the sparkline. This setting is optional. The default value is the maximum of all values.';
						$scope.descriptions.style = 'A number that references the style of the Spread sparkline. This setting is optional. The default value is 4 (poles).';
						$scope.descriptions.vertical = commonDescriptions.vertical;
						// default values
						$scope.style = ($scope.style !== undefined) ? $scope.styles[$scope.style - 1]
								: $scope.styles[3]; // $scope.styles[3] is
													// Poles;
													// $scope.styles[$scope.style-1]
													// because styles are 1-6
													// (not 0-5)
						$scope.vertical = ($scope.vertical !== undefined) ? $scope.vertical
								: false;
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(128, 128, 128)';
						break;
					case $scope.types[7]: // Stacked
						$scope.descriptions.color = 'Main sparkline color. It is used if Color Range is empty or invalid.';
						$scope.descriptions.colorRange = commonDescriptions.colorRange
								+ ' Can\'t contain empty cells. Data Range cell count must equals Color Range cell count.';
						$scope.descriptions.labelRange = commonDescriptions.labelRange;
						$scope.descriptions.maximumValue = 'A number that represents the maximum value of the sparkline. This setting is optional. The default value is the summary of all positive values.';
						$scope.descriptions.targetRed = 'A number that represents the location of the red line. This setting is optional. The default value is empty.';
						$scope.descriptions.targetGreen = 'A number that represents the location of the green line. This setting is optional. The default value is empty.';
						$scope.descriptions.targetBlue = 'A number that represents the location of the blue line. This setting is optional. The default value is empty.';
						$scope.descriptions.targetYellow = 'A number that represents the location of the yellow line. This setting is optional. The default value is empty.';
						$scope.descriptions.highlightPosition = 'A number that represents the index of the highlight area. This setting is optional. The default value is empty.';
						$scope.descriptions.vertical = commonDescriptions.vertical;
						$scope.descriptions.textOrientation = 'A number that represents the label text orientation.';
						$scope.descriptions.textSize = 'A number that represents the size of the label text. The default value is 10. The unit is px.';
						// default values
						$scope.textOrientation = ($scope.textOrientation !== undefined) ? $scope.textOrientation
								: 0;
						$scope.textSize = ($scope.textSize !== undefined) ? $scope.textSize
								: '10';
						$scope.vertical = ($scope.vertical !== undefined) ? $scope.vertical
								: false;
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(128, 128, 128)';
						break;
					case $scope.types[8]: // BoxPlot
						$scope.styles = [
								{
									id : 0,
									name : '0 - the whisker is a line and outlier is a circle.',
								},
								{
									id : 1,
									name : '1 - the whisker is a rectangle and outlier is a line.',
								} ];
						$scope.fieldLabels.color = 'Color Scheme';
						$scope.descriptions.color = 'A string that represents the color of the sparkline\'s box.';
						$scope.descriptions.showAverage = commonDescriptions.showAverage;
						$scope.descriptions.scaleStart = 'A number or reference that represents the minimum boundary of the sparkline, such as 1 or "A6". This setting is optional. The default value is the minimum of all values.';
						$scope.descriptions.scaleEnd = 'A number or reference that represents the maximum boundary of the sparkline, such as 8 or "A7". This setting is optional. The default value is the maximum of all values.';
						$scope.descriptions.acceptableStart = 'A number or reference that represents the start of the acceptable line, such as 3 or "A8". This setting is optional. The default value is None.';
						$scope.descriptions.acceptableEnd = 'A number or reference represents the end of the acceptable line, such as 5 or "A9". This setting is optional. The default value is None.';
						$scope.descriptions.boxPlotClass = 'Q1–>25% percentile, Q3–>75% percentile, IQR-->Q3-Q1.';
						$scope.descriptions.boxPlotClassOptions = {
							/* 5ns */
							fiveNS : 'whisker ends at minimum and maximum, median, no outliers.',
							/* 7ns */
							sevenNS : 'whisker ends at 2% percentile and 98% percentile, hatch marks at 9% percentile and 91% percentile, outliers beyond 2% percentile and 98% percentile.',
							tukey : 'whisker ends at a value (the minimum of the points between Q1 and Q1 - 1.5*IQR, use the point if it exists or use the minimum) and a value (the maximum of the points between Q3 and Q3 + 1.5 * IQR, use the point if it exists or use the maximum), outliers beyond Q1 - 1.5*IQR and Q3 + 1.5 * IQR, and extreme outliers beyond Q1 - 3 * IQR and Q3 + 3 * IQR.',
							bowley : 'whisker ends at minimum and maximum, hatch marks at 10% percentile and 90% percentile, no outliers.',
							sigma3 : 'whisker ends at a value (average - 2 * StDev > scaleStart ? average - 2 * StDev :  minimum) and a value (average + 2 * StDev < scaleEnd ? average = 2 * StDev : maximum), box at average +/- stdev, outliers beyond average - 2 * StDev and average + 2 * StDev, and extreme outliers beyond average - 3 * StDev and average + 3 * StDev.',
						};
						$scope.descriptions.style = 'A number or reference that represents the sparkline style. The default value is 0 (Classical).';
						$scope.descriptions.vertical = commonDescriptions.vertical;
						// default values
						$scope.boxPlotClass = ($scope.boxPlotClass !== undefined) ? $scope.boxPlotClass
								: '5ns';
						$scope.style = ($scope.style !== undefined) ? $scope.styles[$scope.style]
								: $scope.styles[0];
						$scope.showAverage = ($scope.showAverage !== undefined) ? $scope.showAverage
								: false;
						$scope.vertical = ($scope.vertical !== undefined) ? $scope.vertical
								: false;
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(128, 128, 128)';
						break;
					case $scope.types[9]: // Cascade
						$scope.fieldLabels.color = 'Color Positive';
						$scope.fieldLabels.color2 = 'Color Negative';
						$scope.descriptions.labelRange = commonDescriptions.labelRange;
						$scope.descriptions.minimumValue = 'A number or reference that represents the minimum values of the display area. This setting is optional. The default value is the minimum of the sum (the sum of the points\' value), such as -2000. The minimum you set must be less than the default minimum; otherwise, the default minimum is used.';
						$scope.descriptions.maximumValue = 'A number or reference that represents the maximum values of the display area. This setting is optional. The default value is the maximum of the sum (the sum of the points\' value), such as 6000. The maximum you set must be greater than the default maximum; otherwise, the default maximum is used.';
						$scope.descriptions.color = 'A string that represents the color of the first or last positive sparkline\'s box (this point\'s value is positive). If the first or last box represents a positive value, the box\'s color is set to colorPositive. The middle positive box is set to a lighter color than colorPositive.';
						$scope.descriptions.color2 = 'A string that represents the color of the first or last negative sparkline\'s box (this point\'s value is negative). If the first or last box represents the negative value, the box\'s color is set to colorNegative. The middle negative box is set to a lighter color than colorNegative.';
						$scope.descriptions.vertical = commonDescriptions.vertical;
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(0, 128, 0)';
						$scope.color2 = ($scope.color2 !== undefined) ? $scope.color2
								: 'rgb(255,0,0)';
						break;
					case $scope.types[10]: // Pareto
						$scope.labels = [ {
							id : 0,
							name : '0 - none',
						}, {
							id : 1,
							name : '1 - the cumulated percentage',
						}, {
							id : 2,
							name : '2 - the single percentage',
						} ];
						$scope.descriptions.colorRange = commonDescriptions.colorRange;
						$scope.descriptions.target = 'A number or reference that represents the "target" line position, such as 0.5. This setting is optional. The default value is none. The target line color is green if shown.';
						$scope.descriptions.target2 = 'A number or reference that represents the "target2" line position, such as 0.8. This setting is optional. The default value is none. The target2 line color is red if shown.';
						$scope.descriptions.highlightPosition = 'A number or reference that represents the rank of the segment to be colored in red, such as 3. This setting is optional. The default value is none. If you set the highlightPosition to a value such as 4, then the fourth segment box\'s color is set to red. If you do not set the highlightPosition, the segment box\'s color is set to the default color.';
						$scope.descriptions.label = 'A number that represents whether the segment\'s label is displayed. This setting is optional. The default value is 0.';
						// default values
						$scope.label = ($scope.label !== undefined) ? $scope.labels[$scope.label]
								: $scope.labels[0];
						break;
					case $scope.types[11]: // Bullet
						$scope.fieldLabels.color = 'Color Scheme';
						$scope.fieldLabels.dataRange = 'Measure';
						$scope.descriptions.color = 'A string that represents a color scheme for displaying the sparkline.';
						$scope.descriptions.dateAxisData = commonDescriptions.dateAxisData;
						$scope.descriptions.dataRange = 'A number or reference that represents the length of the measure bar, such as 5 or "A1".';
						$scope.descriptions.target = 'A number or reference that represents the location of the target line, such as 7 or "A2".';
						$scope.descriptions.maxi = 'A number or reference that represents the maximum value of the sparkline, such as 10 or "A3".';
						$scope.descriptions.good = 'A number or reference that represents the length of the good bar, such as 3 or "A4". This setting is optional. The default value is 0.';
						$scope.descriptions.bad = 'A number or reference that represents the length of the bad bar, such as 1 or "A5". This setting is optional. The default value is 0.';
						$scope.descriptions.forecast = 'A number or reference that represents the length of the forecast line, such as 8 or "A6". This setting is optional. The default value is 0.';
						$scope.descriptions.tickunitBullet = 'A number or reference that represents the tick unit, such as 1 or "A7". This setting is optional. The default value is 0.';
						$scope.descriptions.vertical = commonDescriptions.vertical;
						// default values
						$scope.vertical = ($scope.vertical !== undefined) ? $scope.vertical
								: false;
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(128, 128, 128)';
						break;
					case $scope.types[12]: // Hbar
						$scope.fieldLabels.color = 'Color Scheme';
						$scope.descriptions.color = 'A string that represents the color of the bar.';
						$scope.fieldLabels.dataRange = 'Value';
						$scope.descriptions.dataRange = commonDescriptions.hbarVbarValue
								+ ' The sparkline starts at the left of the cell for positive values and right of the cell for negative values.';
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(128, 128, 128)';
						break;
					case $scope.types[13]: // Vbar
						$scope.fieldLabels.color = 'Color Scheme';
						$scope.descriptions.color = 'A string that represents the color of the bar.';
						$scope.fieldLabels.dataRange = 'Value';
						$scope.descriptions.dataRange = commonDescriptions.hbarVbarValue
								+ ' The sparkline starts at the bottom of the cell for positive values and the top of the cell for negative values.';
						// default values
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(128, 128, 128)';
						break;
					case $scope.types[14]: // Variance
						$scope.fieldLabels.color = 'Color Positive';
						$scope.fieldLabels.color2 = 'Color Negative';
						$scope.fieldLabels.dataRange = 'Variance';
						$scope.descriptions.dataRange = 'A number or reference that represents the bar length, such as 2 or "A1".';
						$scope.descriptions.reference = 'A number or reference that represents the location of the reference line, such as 0 or "A2". This setting is optional. The default value is 0.';
						$scope.descriptions.mini = 'A number or reference that represents the minimum value of the sparkline, such as -5 or "A3". This setting is optional. The default value is -1.';
						$scope.descriptions.maxi = 'A number or reference that represents the maximum value of the sparkline, such as 5 or "A4". This setting is optional. The default value is 1.';
						$scope.descriptions.mark = 'A number or reference that represents the position of the mark line, such as 3 or "A5". This setting is optional.';
						$scope.descriptions.tickunit = 'A number or reference that represents a tick unit, such as 1 or "A6". This setting is optional. The default value is 0.';
						$scope.descriptions.legend = 'A boolean that represents whether to display the text.';
						$scope.descriptions.color = 'A string that represents the color sheme for variance and is larger than reference.';
						$scope.descriptions.color2 = 'A string that represents the color scheme for variance and is smaller than reference.';
						$scope.descriptions.vertical = commonDescriptions.vertical;
						// default values
						$scope.legend = ($scope.legend !== undefined) ? $scope.legend
								: false;
						$scope.vertical = ($scope.vertical !== undefined) ? $scope.vertical
								: false;
						$scope.color = ($scope.color !== undefined) ? $scope.color
								: 'rgb(0, 128, 0)';
						$scope.color2 = ($scope.color2 !== undefined) ? $scope.color2
								: 'rgb(255,0,0)';
						break;
					}
				}

			}
		};
	}
})();