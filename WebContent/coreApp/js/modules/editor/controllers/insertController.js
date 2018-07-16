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
		.controller('InsertController', InsertController);

	/* @ngInject */
	function InsertController($scope, $modal, SpreadSheetService, InsertService) {

		$scope.types = ['Line', 'Column', 'Win/Loss', 'Pie', 'Area', 'Scatter', 'Spread', 'Stacked', 'BoxPlot', 'Cascade', 'Pareto', 'Bullet', 'Hbar', 'Vbar', 'Variance'];

		$scope.insertSparkline = insertSparkline;


		/************************************************** IMPLEMENTATION *************************************************************************/


		function insertSparkline(type, previousData, previousLocationRange) {
			var spread = $scope.spread;
			var types = $scope.types;
			var modalInstance = $modal.open({
				template: '<insert-sparkline id="insert-sparkline" type="type" types="types" spread="spread" previous-data="previousData" previous-location-range="previousLocationRange"></insert-sparkline>',
				windowClass: 'softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.type = type;
						$scope.types = types;
						$scope.spread = spread;
						$scope.previousData = previousData;
						$scope.previousLocationRange = previousLocationRange;
						$scope.$on('InsertSparkline:close', function(event, args) {
							unbindUnblock();
							$modalInstance.close();
						});
					}
				]
			});
			// on ESC key press
			modalInstance.result.then(function() {}, function() {
				unbindUnblock();
			});
		}

		// unbind Selection Events
		function unbindUnblock() {
			$scope.spread.unbind(GC.Spread.Sheets.Events.SelectionChanging);
			$scope.spread.unbind(GC.Spread.Sheets.Events.SelectionChanged);
			SpreadSheetService.unblockExcelView($scope.spread);
		}

		// Cascade and Pareto are inserted in more than one cell
		function insertCascadeOrParetoSparkline(data, type, sheet) {
			var iterator = 1;
			var formula = '';
			if (data.vertical) {
				var colIndex = data.locationStartCol;
				while (colIndex <= data.locationEndCol) {
					formula = prepareFormula(type, data, iterator);
					sheet.setFormula(data.locationStartRow, colIndex, formula);
					iterator++;
					colIndex++;
				}
			} else {
				var rowIndex = data.locationStartRow;
				while (rowIndex <= data.locationEndRow) {
					formula = prepareFormula(type, data, iterator);
					sheet.setFormula(rowIndex, data.locationStartCol, formula);
					iterator++;
					rowIndex++;
				}
			}
		}

		// prepare formula which is used many times (i.e. in loop)
		function prepareFormula(type, data, iterator) {
			switch (type) {
				case $scope.types[9]:
					return '=CASCADESPARKLINE(' + data.dataRange + ',' + iterator + ',' + data.labelRange + ',' + data.minimumValue + ',' + data.maximumValue + ',"' + data.color + '","' + data.color2 + '",' + data.vertical + ')';
				case $scope.types[10]:
					return '=PARETOSPARKLINE(' + data.dataRange + ',' + iterator + ',' + data.colorRange + ',' + data.target + ',' + data.target2 + ',' + data.highlightPosition + ',' + data.label + ',' + data.vertical + ')';
			}
			return null;
		}

		// Before edit Cascade or Pareto: find all cells with other parts of the same sparkline and prepare Location Range.
		function prepareCascadeParetoLocationRange(data, selection, sparklineName, sheet) {
			// parameteres of selected cell - one of Cascade or Pareto sparkline.
			var dataRange = data[0];
			var pointIndex = parseInt(data[1]);
			var isVertical = InsertService.prepareBoolean(data[7]);
			var currentCol = selection.col;
			var currentRow = selection.row;
			// find first cell to start searching other cells from this one
			if (pointIndex == 1) {
				// it's first cell
			} else {
				// find first
				if (isVertical) {
					currentCol = currentCol - pointIndex + 1;
				} else {
					currentRow = currentRow - pointIndex + 1;
				}
				pointIndex = 1;
			}
			// set first cell parameteres
			var startLocationColName = InsertService.getColumnNameByIndex(currentCol + 1);
			var startLocationRowIndex = currentRow + 1;
			var startLocationRange = startLocationColName + startLocationRowIndex;
			// variables for last cell parameteres
			var endLocationColName = '';
			var endLocationRowIndex = '';
			var endLocationRange = '';
			// check next cells
			var expectedPointIndex = pointIndex + 1;
			var searchNext = true;
			while (searchNext) {
				// check next cells in the same row (for horizontal) or in the same column (for vertical)
				if (isVertical) {
					currentCol++;
				} else {
					currentRow++;
				}
				var currentCellValue = sheet.getValue(currentRow, currentCol);
				// if cell value is Sparkline Expression
				if (currentCellValue !== null && currentCellValue.constructor !== undefined && currentCellValue.constructor.name !== undefined && currentCellValue.constructor.name == 'SparklineExValue') {
					var currentFormula = sheet.getFormula(currentRow, currentCol);
					var currentSparklineName = currentCellValue.name;
					var currentDataString = currentFormula.replace(currentSparklineName + '(', '').slice(0, -1);
					var currentData = InsertService.parseSparklineFormula(currentDataString);
					var currentDataRange = currentData[0];
					var currentPointIndex = parseInt(currentData[1]);
					var isCurrentVertical = InsertService.prepareBoolean(data[7]);
					// Cell is next part of the same Cascade/Pareto Sparkline if has formula with: the same sparkline name, the same data range, next point index, the same orientation
					if (currentSparklineName == sparklineName && currentDataRange == dataRange && currentPointIndex == expectedPointIndex && isCurrentVertical == isVertical) {
						// set endLocation (can be override in next loop rotation)
						endLocationColName = InsertService.getColumnNameByIndex(currentCol + 1);
						endLocationRowIndex = currentRow + 1;
						endLocationRange = endLocationColName + endLocationRowIndex;
					} else {
						searchNext = false;
					}
				} else {
					searchNext = false;
				}
				expectedPointIndex++;
			}
			var locationRange = startLocationRange + ':' + endLocationRange;
			return locationRange;
		}



		/************************************************** EVENTS *********************************************************************/



		$scope.$on('InsertSparkline:insertSparkline', function(e, data) {
			var sheet = $scope.spread.getActiveSheet();
			var type = data.type;
			var formula = '';
			var dataRange = data.dataRange;
			var dataRangeOrientation = data.dataRangeOrientation; // 0 is vertical, 1 is horizontal
			var dateAxisOrientation = data.dataRangeOrientation; // 0 is vertical, 1 is horizontal
			// prepare formula (string)
			switch (type) {
				case $scope.types[0]:
					formula = '=LINESPARKLINE(' + dataRange + ',' + dataRangeOrientation + ',' + data.dateAxisData + ',' + dateAxisOrientation + ',"{seriesColor:' + data.color + ',displayHidden:true}")';
					break;
				case $scope.types[1]:
					formula = '=COLUMNSPARKLINE(' + dataRange + ',' + dataRangeOrientation + ',' + data.dateAxisData + ',' + dateAxisOrientation + ',"{seriesColor:' + data.color + ',displayHidden:true}")';
					break;
				case $scope.types[2]:
					formula = '=WINLOSSSPARKLINE(' + dataRange + ',' + dataRangeOrientation + ',' + data.dateAxisData + ',' + dateAxisOrientation + ',"{seriesColor:' + data.color + ',displayXAxis:true,displayHidden:true}")';
					break;
				case $scope.types[3]:
					formula = '=PIESPARKLINE(' + dataRange + ',' + data.colorRange + ')';
					break;
				case $scope.types[4]:
					formula = '=AREASPARKLINE(' + dataRange + ',' + data.minimumValue + ',' + data.maximumValue + ',' + data.line1 + ',' + data.line2 + ',"' + data.color + '","' + data.color2 + '")';
					break;
				case $scope.types[5]:
					formula = '=SCATTERSPARKLINE(' + dataRange + ',' + data.points2 + ',' + data.minX + ',' + data.maxX + ',' + data.minY +
						',' + data.maxY + ',' + data.hLine + ',' + data.vLine + ',' + data.xMinZone + ',' + data.xMaxZone + ',' + data.yMinZone + ',' + data.yMaxZone +
						',' + data.tags + ',' + data.drawSymbol + ',' + data.drawLines + ',"' + data.color + '","' + data.color2 + '",' + data.dash + ')';
					break;
				case $scope.types[6]:
					formula = '=SPREADSPARKLINE(' + dataRange + ',' + data.showAverage + ',' + data.scaleStart + ',' + data.scaleEnd + ',' + data.style + ',"' + data.color + '", ' + data.vertical + ')';
					break;
				case $scope.types[7]:
					formula = '=STACKEDSPARKLINE(' + dataRange + ',' + data.colorRange + ',' + data.labelRange + ',' + data.maximumValue + ',' + data.targetRed + ',' + data.targetGreen + ',' + data.targetBlue + ',' + data.targetYellow + ',"' + data.color + '",' + data.highlightPosition + ',' + data.vertical + ',' + data.textOrientation + ',' + data.textSize + ')';
					break;
				case $scope.types[8]:
					formula = '=BOXPLOTSPARKLINE(' + dataRange + ',' + data.boxPlotClass + ',' + data.showAverage + ',' + data.scaleStart + ',' + data.scaleEnd + ',' + data.acceptableStart + ',' + data.acceptableEnd + ',"' + data.color + '",' + data.style + ',' + data.vertical + ')';
					break;
				case $scope.types[9]:
					insertCascadeOrParetoSparkline(data, $scope.types[9], sheet);
					break;
				case $scope.types[10]:
					insertCascadeOrParetoSparkline(data, $scope.types[10], sheet);
					break;
				case $scope.types[11]:
					formula = '=BULLETSPARKLINE(' + dataRange + ',' + data.target + ',' + data.maxi + ',' + data.good + ',' + data.bad + ',' + data.forecast + ',' + data.tickunitBullet + ',"' + data.color + '",' + data.vertical + ')';
					break;
				case $scope.types[12]:
					formula = '=HBARSPARKLINE(' + dataRange + ',"' + data.color + '")';
					break;
				case $scope.types[13]:
					formula = '=VBARSPARKLINE(' + dataRange + ',"' + data.color + '")';
					break;
				case $scope.types[14]:
					formula = '=VARISPARKLINE(' + dataRange + ',' + data.reference + ',' + data.mini + ',' + data.maxi + ',' + data.mark + ',' + data.tickunit + ',' + data.legend + ',"' + data.color + '","' + data.color2 + '",' + data.vertical + ')';
					break;
			}
			if (formula !== '') {
				sheet.setFormula(data.locationStartRow, data.locationStartCol, formula);
			}
			unbindUnblock();
		});
		$scope.$on('InsertChart:insertChart', function(e, data) {
			var sheet = $scope.spread.getActiveSheet();
			var sheetIndex = data.sheet;
			var sheettoIndex = $scope.spread.sheets[sheetIndex]
			var type = data.type;
			var formula = '';
			var dataRange = data.dataRange;
			var dataRangeOrientation = data.dataRangeOrientation; // 0 is vertical, 1 is horizontal
			var dateAxisOrientation = data.dataRangeOrientation; // 0 is vertical, 1 is horizontal
			var chartType=null;
			switch (type) {
				case 'column3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.column3D;
					break;
				case 'columnClustered':
					chartType=GC.Spread.Sheets.Charts.ChartType.columnClustered;
					break;
				case 'columnClustered3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.columnClustered3D;
					break;
				case 'columnStacked':
					chartType=GC.Spread.Sheets.Charts.ChartType.columnStacked;
					break;
				case 'columnStacked3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.columnStacked3D;
					break;
				case 'columnStacked100':
					chartType=GC.Spread.Sheets.Charts.ChartType.columnStacked100;
					break;
				case 'columnStacked1003D':
					chartType=GC.Spread.Sheets.Charts.ChartType.columnStacked1003D;
					break;					
				case 'line':
					chartType=GC.Spread.Sheets.Charts.ChartType.line;
					break;					
				case 'line3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.line3D;
					break;					
				case 'lineMarkers':
					chartType=GC.Spread.Sheets.Charts.ChartType.lineMarkers;
					break;					
				case 'lineMarkersStacked':
					chartType=GC.Spread.Sheets.Charts.ChartType.lineMarkersStacked;
					break;					
				case 'lineMarkersStacked100':
					chartType=GC.Spread.Sheets.Charts.ChartType.lineMarkersStacked100;
					break;					
				case 'lineStacked':
					chartType=GC.Spread.Sheets.Charts.ChartType.lineStacked;
					break;					
				case 'lineStacked100':
					chartType=GC.Spread.Sheets.Charts.ChartType.lineStacked100;
					break;					
				case 'pie':
					chartType=GC.Spread.Sheets.Charts.ChartType.pie;
					break;					
				case 'pie3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.pie3D;
					break;					
				case 'pieExploded':
					chartType=GC.Spread.Sheets.Charts.ChartType.pieExploded;
					break;					
				case 'pieExploded3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.pieExploded3D;
					break;					
				case 'pieOfPie':
					chartType=GC.Spread.Sheets.Charts.ChartType.pieOfPie;
					break;					
				case 'area':
					chartType=GC.Spread.Sheets.Charts.ChartType.area;
					break;					
				case 'area3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.area3D;
					break;					
				case 'areaStacked':
					chartType=GC.Spread.Sheets.Charts.ChartType.areaStacked;
					break;					
				case 'areaStacked3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.areaStacked3D;
					break;					
				case 'areaStacked100':
					chartType=GC.Spread.Sheets.Charts.ChartType.areaStacked100;
					break;					
				case 'areaStacked1003D':
					chartType=GC.Spread.Sheets.Charts.ChartType.areaStacked1003D;
					break;					
				case 'barClustered':
					chartType=GC.Spread.Sheets.Charts.ChartType.barClustered;
					break;					
				case 'barClustered3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.barClustered3D;
					break;					
				case 'barOfPie':
					chartType=GC.Spread.Sheets.Charts.ChartType.barOfPie;
					break;					
				case 'barStacked':
					chartType=GC.Spread.Sheets.Charts.ChartType.barStacked;
					break;					
				case 'barStacked3D':
					chartType=GC.Spread.Sheets.Charts.ChartType.barStacked3D;
					break;					
				case 'barStacked100':
					chartType=GC.Spread.Sheets.Charts.ChartType.barStacked100;
					break;					
				case 'barStacked1003D':
					chartType=GC.Spread.Sheets.Charts.ChartType.barStacked1003D;
					break;					
				case 'xyScatter':
					chartType=GC.Spread.Sheets.Charts.ChartType.xyScatter;
					break;					
				case 'xyScatterLines':
					chartType=GC.Spread.Sheets.Charts.ChartType.xyScatterLines;
					break;					
				case 'xyScatterLinesNoMarkers':
					chartType=GC.Spread.Sheets.Charts.ChartType.xyScatterLinesNoMarkers;
					break;					
				case 'xyScatterSmooth':
					chartType=GC.Spread.Sheets.Charts.ChartType.xyScatterSmooth;
					break;					
				case 'xyScatterSmoothNoMarkers':
					chartType=GC.Spread.Sheets.Charts.ChartType.xyScatterSmoothNoMarkers;
					break;					
				case 'stockHLC':
					chartType=GC.Spread.Sheets.Charts.ChartType.stockHLC;
					break;					
				case 'stockOHLC':
					chartType=GC.Spread.Sheets.Charts.ChartType.stockOHLC;
					break;					
				case 'stockVHLC':
					chartType=GC.Spread.Sheets.Charts.ChartType.stockVHLC;
					break;					
				case 'stockVOHLC':
					chartType=GC.Spread.Sheets.Charts.ChartType.stockVOHLC;
					break;					
				case 'combo':
					chartType=GC.Spread.Sheets.Charts.ChartType.combo;
					break;					
				default:
					chartType=GC.Spread.Sheets.Charts.ChartType.area;
				break;						
				
			}
			var chartArray = sheet.charts.all();
			
			
			try{
//				var instance = new GC.Spread.Sheets.Charts.Chart(sheet, 'Chart '+type+chartArray.length, chartType,  0, 100, 400, 300, dataRange);
//				var returnValue; // Type: any
//				returnValue = instance.axes();
//				console.log(returnValue);
				var sheet = $scope.spread.getActiveSheet();
				var dataSheetName = $scope.spread.getActiveSheet().name();
				var insertSheet = $scope.spread.sheets[data.sheet];
//					sheet.charts.add('Chart '+type+chartArray.length, chartType, 0, 100, 400, 300, dataRange);
				var arrDataRange = dataRange.split(':');
				var insertDataRange = dataSheetName+'!'+getRangeString(arrDataRange[0])+':'+getRangeString(arrDataRange[1]);
				insertSheet.charts.add('Chart '+type+chartArray.length, chartType, 0, 100, 400, 300, dataSheetName+'!'+dataRange);
				var chart = insertSheet.charts.all()[0]; 
				var axes = chart.axes();
				console.log(axes);
				
				
				
            }catch (e){
                swal(e.message)
                return;
            }
			unbindUnblock();
		});
		function getRangeString(inputRange){
			var column = inputRange.replace( /^\D+/g, ''); 
			var row = inputRange.replace(column,'');
			var returnValue = '$'+row+'$'+column;
			return returnValue;
		}

		$scope.$on('InsertController:editSparkline', function(event, info) {
			// var formula = 'LINESPARKLINE(D11:D19,0,E11:E19,0,"{seriesColor:#aa80c1,displayHidden:true}")';//var formula = '=LINESPARKLINE(D11:D19,0,E11:E19,0,"{seriesColor:rgb(231, 106, 71),displayHidden:true}")';
			// var formula = 'COLUMNSPARKLINE(D11:D19,0,E11:E19,0,"{seriesColor:rgb(178, 31, 131),displayHidden:true}")';
			// var formula = 'WINLOSSSPARKLINE(D11:D19,0,E11:E19,0,"{seriesColor:rgb(43, 178, 77),displayXAxis:true,displayHidden:true}")';
			// var formula = 'PIESPARKLINE(D11:D19,F11,F12,F13,F14,F15,F16,F17,F18,F19)';
			// var formula = 'AREASPARKLINE(D11:D19,H11,L12,D13,D19,"rgb(240, 122, 58)","rgb(35, 35, 154)")';
			// var formula = 'SCATTERSPARKLINE(D11:E19,G11:L13,D17,D11,H13,D15,D17,D12,D17,D12,D14,D11,TRUE,FALSE,TRUE,"rgb(0, 128, 0)","rgb(103, 103, 238)",TRUE)';
			// var formula = 'SPREADSPARKLINE(D11:D19,TRUE,D17,D12,5,"rgb(223, 143, 120)",TRUE)';
			// var formula = 'STACKEDSPARKLINE(D11:D19,F11:F19,C11:C19,H12,D17,D12,D19,J11,"rgb(63, 191, 73)",4,TRUE,1,13)';
			// var formula = 'BOXPLOTSPARKLINE(G4:R4,"bowley",TRUE,G5,G6,G7,G8,"rgb(240, 122, 58)",1,TRUE)';
			// var formula = 'CASCADESPARKLINE(D11:D19,1,C11:C19,D17,D11,"rgb(223, 143, 120)","rgb(180, 33, 117)",FALSE)';
			// var formula = 'PARETOSPARKLINE(D11:D19,1,F11:F19,0.5,0.8,3,2,FALSE)';
			// var formula = 'BULLETSPARKLINE(D10,D5,D6,D7,D8,C10,2,"rgb(167, 118, 63)",TRUE)';
			// var formula = 'HBARSPARKLINE(N11,"rgb(219, 106, 216)")';
			// var formula = 'VBARSPARKLINE(G15,"rgb(63, 191, 73)")';
			// var formula = 'VARISPARKLINE(N11,H11,G15,I15,E19,2,TRUE,"rgb(255, 255, 0)","rgb(35, 35, 154)",TRUE)';
			$scope.type = null;
			var sheet = $scope.spread.getActiveSheet();
			var selection = null;
			var locationColName = null;
			var locationRowIndex = null;
			var locationRange = '';
			// get first cell from first  sellection
			selection = sheet.getSelections()[0];
			locationColName = InsertService.getColumnNameByIndex(selection.col + 1);
			locationRowIndex = selection.row + 1;
			locationRange = locationColName + locationRowIndex;

			var cellValue = sheet.getValue(selection.row, selection.col);

			var name = '';
			var formula = '';
			// check if cell contains Sparkline formula
			if (cellValue !== null && cellValue.constructor !== undefined && cellValue.constructor.name !== undefined && cellValue.constructor.name == 'SparklineExValue') {
				formula = sheet.getFormula(selection.row, selection.col);
				name = cellValue.name;
			}
			// set type based on Sparkline name
			switch (name) {
				case 'LINESPARKLINE':
					$scope.type = $scope.types[0];
					break;
				case 'COLUMNSPARKLINE':
					$scope.type = $scope.types[1];
					break;
				case 'WINLOSSSPARKLINE':
					$scope.type = $scope.types[2];
					break;
				case 'PIESPARKLINE':
					$scope.type = $scope.types[3];
					break;
				case 'AREASPARKLINE':
					$scope.type = $scope.types[4];
					break;
				case 'SCATTERSPARKLINE':
					$scope.type = $scope.types[5];
					break;
				case 'SPREADSPARKLINE':
					$scope.type = $scope.types[6];
					break;
				case 'STACKEDSPARKLINE':
					$scope.type = $scope.types[7];
					break;
				case 'BOXPLOTSPARKLINE':
					$scope.type = $scope.types[8];
					break;
				case 'CASCADESPARKLINE':
					$scope.type = $scope.types[9];
					break;
				case 'PARETOSPARKLINE':
					$scope.type = $scope.types[10];
					break;
				case 'BULLETSPARKLINE':
					$scope.type = $scope.types[11];
					break;
				case 'HBARSPARKLINE':
					$scope.type = $scope.types[12];
					break;
				case 'VBARSPARKLINE':
					$scope.type = $scope.types[13];
					break;
				case 'VARISPARKLINE':
					$scope.type = $scope.types[14];
					break;
			}

			if ($scope.type !== null) {
				var dataString = formula.replace(name + '(', '').slice(0, -1);
				var data = InsertService.parseSparklineFormula(dataString);
				// prepare and change locationRange for Cascade or Pareto
				if ($scope.type == $scope.types[9] || $scope.type == $scope.types[10]) {
					locationRange = prepareCascadeParetoLocationRange(data, selection, name, sheet);
				}
				$scope.insertSparkline($scope.type, data, locationRange);
			}
		});

	}
})();