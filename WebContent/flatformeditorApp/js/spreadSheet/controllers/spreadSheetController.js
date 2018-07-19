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
		.module('flatFormEditorApp.spreadSheet')
		.controller('SpreadSheetController', SpreadSheetController);

	/* @ngInject */
	function SpreadSheetController($rootScope, $scope, $modal, $timeout, CoreCommonsService, ContextMenuService, ContextMenuActionService,
		ContextMenuActionsSetupService, SpreadSheetService, ViewModeService, SessionService, DataService, ZoomService, validationService) {

		//TODO think about optimalization  - onTabSelect - store in variable which tab is selected
		//and code organization for example - switchMainMenu to DetailsController

		var url = $BASE_PATH;
		var spreadElement;
		var spread;
		var sheet;
		var isJsonRendered = false;
		var isWorkbookDownloaded = false;
		var isCurrentItemChoosed = false;
		var delay;
		$scope.isDocumentCompleted = false; // to: allow change askIfReload by binded events, show tab navigation
		$scope.mainMenuVisibility = {};
		$scope.saveAsParams = {};
		$scope.switchMainMenu = switchMainMenu;
		$scope.tryRunWorkbookTest = tryRunWorkbookTest;
		$scope.upload = upload;
		$scope.save = save;
		$scope.saveAs = saveAs;
		$scope.newFlatForm = newFlatForm;
		$scope.copyTemplateTo = copyTemplateTo;
		$scope.exit = exit;
		$scope.hideMenu = hideMenu;
		$scope.showHideHeadings = showHideHeadings;
		$scope.showHideGridLines = showHideGridLines;
		$scope.logout = logout;
		$scope.onShowHandler = onShowHandler;
		$scope.menuLeft = -200;
		$scope.menuRight = -300;
		$scope.types = ['Line', 'Column', 'Win/Loss', 'Pie', 'Area', 'Scatter', 'Spread', 'Stacked', 'BoxPlot', 'Cascade', 'Pareto', 'Bullet', 'Hbar', 'Vbar', 'Variance'];
		var showValue = false;
		var showSeriesName = false;
		var showCategoryName = false;
		var axesTY;

		var chartTypeDict = {
			    0: {
			        chartType: "combo",
			            chartGroup: "ComboGroup"
			    },
			    1: {
			        chartType: "xyScatter",
			            chartGroup: "ScatterGroup"
			    },
			    2: {
			        chartType: "xyScatter",
			            chartGroup: "ScatterGroup"
			    },
			    3: {
			        chartType: "doughnut",
			            chartGroup: "PieGroup"
			    },
			    8: {
			        chartType: "area",
			            chartGroup: "AreaGroup"
			    },
			    9: {
			        chartType: "line",
			            chartGroup: "LineGroup"
			    },
			    10: {
			        chartType: "pie",
			            chartGroup: "PieGroup"
			    },
			    11: {
			        chartType: "bubble",
			            chartGroup: "ScatterGroup"
			    },
			    12: {
			        chartType: "columnClustered",
			            chartGroup: "ColumnGroup"
			    },
			    13: {
			        chartType: "columnStacked",
			            chartGroup: "ColumnGroup"
			    },
			    14: {
			        chartType: "columnStacked100",
			            chartGroup: "ColumnGroup"
			    },
			    18: {
			        chartType: "barClustered",
			            chartGroup: "BarGroup"
			    },
			    19: {
			        chartType: "barStacked",
			            chartGroup: "BarGroup"
			    },
			    20: {
			        chartType: "barStacked100",
			            chartGroup: "BarGroup"
			    },
			    24: {
			        chartType: "lineStacked",
			            chartGroup: "LineGroup"
			    },
			    25: {
			        chartType: "lineStacked100",
			            chartGroup: "LineGroup"
			    },
			    26: {
			        chartType: "lineMarkers",
			            chartGroup: "LineGroup"
			    },
			    27: {
			        chartType: "lineMarkersStacked",
			            chartGroup: "LineGroup"
			    },
			    28: {
			        chartType: "lineMarkersStacked100",
			            chartGroup: "LineGroup"
			    },
			    33: {
			        chartType: "xyScatterSmooth",
			            chartGroup: "ScatterGroup"
			    },
			    34: {
			        chartType: "xyScatterSmoothNoMarkers",
			            chartGroup: "ScatterGroup"
			    },
			    35: {
			        chartType: "xyScatterLines",
			            chartGroup: "ScatterGroup"
			    },
			    36: {
			        chartType: "xyScatterLinesNoMarkers",
			            chartGroup: "ScatterGroup"
			    },
			    37: {
			        chartType: "areaStacked",
			            chartGroup: "AreaGroup"
			    },
			    38: {
			        chartType: "areaStacked100",
			            chartGroup: "AreaGroup"
			    },
			    49: {
			        chartType: "stockHLC",
			            chartGroup: "StockGroup"
			    },
			    50: {
			        chartType: "stockOHLC",
			            chartGroup: "StockGroup"
			    },
			    51: {
			        chartType: "stockVHLC",
			            chartGroup: "StockGroup"
			    },
			    52: {
			        chartType: "stockVOHLC",
			            chartGroup: "StockGroup"
			    }
			}
		var dataLabelPosition = GC.Spread.Sheets.Charts.DataLabelPosition;

		var chartGroupItemObj = {
			    ColumnGroup:
			        [
			            {desc:uiResource.chartDataLabels.center,key:dataLabelPosition.center},
			            {desc:uiResource.chartDataLabels.insideEnd,key:dataLabelPosition.insideEnd},
			            {desc:uiResource.chartDataLabels.outsideEnd,key:dataLabelPosition.outsideEnd}
			        ],
			    LineGroup:
			        [
			            {desc:uiResource.chartDataLabels.center,key:dataLabelPosition.center},
			            {desc:uiResource.chartDataLabels.above,key:dataLabelPosition.above},
			            {desc:uiResource.chartDataLabels.below,key:dataLabelPosition.below}
			        ],
			    PieGroup:
			        [
			            {desc:uiResource.chartDataLabels.center,key:dataLabelPosition.center},
			            {desc:uiResource.chartDataLabels.insideEnd,key:dataLabelPosition.insideEnd},
			            {desc:uiResource.chartDataLabels.bestFit,key:dataLabelPosition.bestFit},
			            {desc:uiResource.chartDataLabels.outsideEnd,key:dataLabelPosition.outsideEnd}
			        ],
			    BarGroup:
			        [
			            {desc:uiResource.chartDataLabels.center,key:dataLabelPosition.center},
			            {desc:uiResource.chartDataLabels.insideEnd,key:dataLabelPosition.insideEnd},
			            {desc:uiResource.chartDataLabels.outsideEnd,key:dataLabelPosition.outsideEnd}
			        ],
			    AreaGroup:[
			    ],
			    ScatterGroup:
			        [
			            {desc:uiResource.chartDataLabels.center,key:dataLabelPosition.center},
			            {desc:uiResource.chartDataLabels.above,key:dataLabelPosition.above},
			            {desc:uiResource.chartDataLabels.below,key:dataLabelPosition.below}
			        ],
			    StockGroup:[
			    ],
			    ComboGroup: {}
			};

		activate();

		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			$scope.viewMode = ViewModeService.getViewMode();
			$scope.flatForm = SpreadSheetService.getFlatForm();
			$scope.loader = SpreadSheetService.getLoader();
			$scope.mainMenuVisibility = {
				newFlatForm: false,
				save: true,
				saveAs: false,
				copyTemplateTo: false
			};

			$scope.saveAsParams = {
				visId: "",
				description: "",
			};
		}

		function switchMainMenu(activeElem) {
			angular.forEach($scope.mainMenuVisibility, function(value, key) {
				$scope.mainMenuVisibility[key] = false;
			});
			$scope.mainMenuVisibility[activeElem] = true;
			switch (activeElem) {
				case 'saveAs':
					$scope.saveAsParams.visId = $scope.flatForm.visId;
					$scope.saveAsParams.description = $scope.flatForm.description;
					break;
			}
		}

		/**
		 * Renders spread from json data. Invoked during changing jsonForm
		 */
		function setUpSpreadSheetView() {
			if (angular.isUndefined($scope.flatForm.jsonForm) || angular.isUndefined($scope.viewMode.currentItem)) {
				return;
			}
//			spread.suspendPaint ();
			//spread.suspendCalcService();
//			spread.grayAreaBackColor("#FCFDFD");
			spread.options.grayAreaBackColor = ("#FCFDFD");
//			spread.suspendPaint ();
//			spread.isPaintSuspended(true);
			spread.clearSheets();
			

			var jsonForm = JSON.parse($scope.flatForm.jsonForm);
			spread.fromJSON(jsonForm.spread);
			isJsonRendered = true;

			SpreadSheetService.blockExcelView(spread);

			$scope.areHeadingsVisible = SpreadSheetService.getHeadingsVisible(spread);
			$scope.areGridLinesVisible = SpreadSheetService.getGridLinesVisible(spread);
			var sheet = $scope.spread.getActiveSheet();
			var zoom = ZoomService.getZoomData();
			zoom.status = Math.floor(sheet.zoom() * 100);
			// BEFORE REFACTOR $scope.$broadcast("ZoomController:zoomTo", Math.floor(sheet.zoom() * 100));
			spread.options.allowContextMenu = false;
			buildCells();
//			spread.resumePaint ();
		}

		function buildCells() {
			if (isJsonRendered === false || isWorkbookDownloaded === false) {
				return;
			}
			//spread.suspendCalcService();
			spread.suspendPaint ();
//			spread.isPaintSuspended(true);
			var worksheets = $scope.flatForm.xmlForm.worksheets;
			for (var i = 0; i < worksheets.length; i++) {
				sheet = spread.getSheet(i);
				if (sheet) {
					//BEFORE REFACTOR sheet.frozenlineColor("rgba(255, 255, 255, 0)"); // hide frozen line		
					var cells = worksheets[i].cells;
					for (var c = 0; c < cells.length; c++) {
						var workbookCell = angular.copy(cells[c]);
						var spreadCell = sheet.getCell(workbookCell.row, workbookCell.column);
						spreadCell.tag(workbookCell);
					}
				}
			}
			ViewModeService.setCurrentViewMode("VALUE");
			//setUpCells(true);
			spread.resumePaint ();
		}

		function setUpCells(isSetUpCell, xmlForm) {
			if (isJsonRendered === false || isWorkbookDownloaded === false || isCurrentItemChoosed === false) {
				return;
			}
			if (xmlForm === undefined) {
				xmlForm = $scope.flatForm.xmlForm;
			}
			spread.suspendCalcService(false);
			if (isSetUpCell) {
				spread.suspendPaint ();
//				spread.isPaintSuspended(true);
				var worksheets = xmlForm.worksheets;
				for (var i = 0; i < worksheets.length; i++) {
					sheet = spread.getSheetFromName(worksheets[i].name);
					if (sheet) {
						var cells = worksheets[i].cells;
						for (var c = 0; c < cells.length; c++) {
							var workbookCell = angular.copy(cells[c]);
							var spreadCell = sheet.getCell(workbookCell.row, workbookCell.column);
							try {
								setUpCell(spreadCell, workbookCell);
							} catch (exception) {
								console.log(worksheets[i]);
								console.log(cells[c]);
								throw exception;
							}
						}
					}
				}
			}
			spread.resumeCalcService(true);
			spread.resumePaint();
//			spread.isPaintSuspended(false);

			spread.repaint();
			updatePositionBox();
			removeEvents();
			redraw();
			$rootScope.$broadcast('veil:hide', {});
			$scope.isDocumentCompleted = true;
		}

		function setUpCell(spreadCell, workbookCell) {
			var tag = spreadCell.tag();
			if (tag === null || tag === undefined) {
				return;
			}
			switch ($scope.viewMode.currentItem.name) {
				case "TEST":
					tag.valueExcel = spreadCell.value();
					tag.formulaExcel = spreadCell.formula();

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
					break;
				case "VALUE":
				case "FORMULA":
				case "INPUT":
				case "OUTPUT":
				case "LOADING":
					spreadCell.formula("");
					if (tag.formulaExcel) {
						try {
							spreadCell.sheet.setFormula(spreadCell.row, spreadCell.col, tag.formulaExcel);
						} catch (exception) {
							spreadCell.sheet.setFormula(spreadCell.row, spreadCell.col, "");
							console.log("invalid formula : " + cell.formula);
						}
					} else if (tag.valueExcel) {
						if (spreadCell.formatter() === "@" || spreadCell.formatter() === "d-mmm-yy;;") {
							spreadCell.sheet.setText(spreadCell.row, spreadCell.col, tag.valueExcel);
						} else {
							spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, tag.valueExcel);
						}
					} else {
						if (spreadCell.formatter() === "@") {
							spreadCell.sheet.setText(spreadCell.row, spreadCell.col, "");
						} else {
							spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, 0);
						}
					}
					if (tag.formulaExcel) {
						delete tag.formulaExcel;
					}
					if (tag.valueExcel) {
						delete tag.valueExcel;
					}
					break;
			}
		}

		$scope.onTabSelect = function(param) {
			$scope.menuLeft = -200;
			switch (param) {
				case 'Settings':
					SpreadSheetService.blockExcelView($scope.spread);
					ViewModeService.setCurrentViewMode("VALUE");
					break;
				case 'Home':
				case 'View':
				case 'Insert':
					SpreadSheetService.unblockExcelView($scope.spread);
					ViewModeService.setCurrentViewMode("VALUE");
					break;
				case 'Mappings':
					SpreadSheetService.unblockExcelView($scope.spread);
					ViewModeService.setCurrentViewMode("VALUE");
					break;
				case 'Test':
					isWorkbookDownloaded = false;
					SpreadSheetService.blockExcelView($scope.spread);
					ViewModeService.setCurrentViewMode("TEST");
					$scope.xmlFormTest = SpreadSheetService.getFlatFormTest();
					break;
				case 'Chart':
					if($scope.menuLeft==0)
						$scope.menuLeft = -200;
					else 
						$scope.menuLeft = 0;
					SpreadSheetService.unblockExcelView($scope.spread);
					ViewModeService.setCurrentViewMode("VALUE");
					break;

					
			}
			//gather context menu actions
			var actions = ContextMenuActionsSetupService.setActions(param);

			//set context menu actions
			ContextMenuService.updateActions(actions);
			redraw();
		};
		$scope.insertChart = function(type, previousData, previousLocationRange){
			$scope.menuLeft = -200;
			var spread = $scope.spread;
			var types = $scope.types;
			var modalInstance = $modal.open({
				template: '<insert-chart id="insert-chart" type="type" types="types" spread="spread" previous-data="previousData" previous-location-range="previousLocationRange"></insert-chart>',
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
							SpreadSheetService.unblockExcelView($scope.spread);
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
		$scope.DeleteChart = function(){
			$scope.menuLeft = -200;
            var activeSheet = spread.getActiveSheet();
            var activeChart = getActiveChart(activeSheet);
            if (activeChart) {
                activeSheet.charts.remove(activeChart.name());
            }
		}

		var updateVisibility = function(inputMappingVisibility, outputMappingVisibility, formulaMappingVisibility) {
			var actions = angular.copy(ContextMenuService.getCtx().actions);
			angular.forEach(actions, function(action) {
				if (action.visibilityDependsOn === "in") {
					action.isVisible = inputMappingVisibility;
				} else if (action.visibilityDependsOn === "out") {
					action.isVisible = outputMappingVisibility;
				} else if (action.visibilityDependsOn === "formula") {
					action.isVisible = formulaMappingVisibility;
				} else if (action.visibilityDependsOn === "in out") {
					action.isVisible = inputMappingVisibility || outputMappingVisibility;
				} else if (action.visibilityDependsOn === "in out formula") {
					action.isVisible = inputMappingVisibility || outputMappingVisibility || formulaMappingVisibility;
				} else {
					action.isVisible = true;
				}
			});

			// Hide dividers next to each other
			var visibleElements = [];
			angular.forEach(actions, function(action) {
				if (action.isDivider && visibleElements.length === 0) {
					action.isVisible = false;
				} else if (action.isDivider) {
					visibleElements = [];
				}
				if (!action.isDivider && action.isVisible) {
					visibleElements.push(true);
				}
			});

			// Hide divider on the end of the list
			if (actions.length > 0 && actions[actions.length - 1].isDivider) {
				actions[actions.length - 1].isVisible = false;
			}

			ContextMenuService.updateActions(actions);
		};

		function tryRunWorkbookTest() {
			var props = $scope.flatForm.xmlForm.properties;
			var modelVisId = props.MODEL_VISID;
			if (typeof modelVisId === undefined || modelVisId === "" || modelVisId === null) {
				// if model is not set - don't allow run test
				swal({
					title: "Model is not set.",
					text: "Please select Model in Workbook Properties window.",
					type: "warning",
					confirmButtonColor: "#5cb85c",
				}, function(isConfirm) {});
			} else if (props.DIMENSION_0_VISID === undefined || props.DIMENSION_0_VISID === "" || props.DIMENSION_0_VISID === null ||
				props.DIMENSION_1_VISID === undefined || props.DIMENSION_1_VISID === "" || props.DIMENSION_1_VISID === null ||
				props.DIMENSION_2_VISID === undefined || props.DIMENSION_2_VISID === "" || props.DIMENSION_2_VISID === null ||
				props.DATA_TYPE === undefined || props.DATA_TYPE === "" || props.DATA_TYPE === null
			) {
				// if data type or any of dimension hierarchies is not set - ask if run test
				swal({
					title: "Some test parameters \n are not set",
					text: "Are you sure you want to run test?",
					type: "warning",
					showCancelButton: true,
					confirmButtonColor: "#5cb85c",
					confirmButtonText: "Run"
				}, function(isConfirm) {
					if (isConfirm) {
						$scope.onTabSelect('Test');
					}
				});
			} else {
				// if all parameters are set - run test
				$scope.onTabSelect('Test');
			}
		}

		function upload(files) {
//			$scope.hideMenu();
//			isJsonRendered = false;
//			isWorkbookDownloaded = false;
//			if (files.length > 0) {
//				CoreCommonsService.askIfReload = true;
//
//				$scope.flatForm.jsonForm = undefined;
//				$scope.flatForm.xmlForm = undefined;
//
//				$rootScope.$broadcast('veil:show', {});
//				ViewModeService.setCurrentViewMode("LOADING");
//				var file = files[0];
//				SpreadSheetService.upload(file);
//			}
			var excelIO = new GC.Spread.Excel.IO();
	        var excelFile = files[0];
	        excelIO.open(excelFile, function (json) {
	           spread.fromJSON(json);
	        }, function (e) {
	            console.log(e);
	        });

		}

		function save() {
			// validate some data
			if ($scope.flatForm.visId === undefined || $scope.flatForm.visId === "" || $scope.flatForm.visId === null || $scope.flatForm.financeCubeId <= 0) {
				swal("Error", "Please supply \n Flat Form name and Finance Cube.", "warning");
				return;
			}
			swal({
				title: "Are you sure",
				text: "to save flat form (" + $scope.flatForm.visId + ") ?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#5cb85c",
				confirmButtonText: "Save"
			}, function(isConfirm) {
				if (isConfirm) {
					$scope.loader.isSaving = true;

					var json = {
						spread: $scope.spread.toJSON()
					};

					$scope.flatForm.jsonForm = JSON.stringify(json);
					//console.log(JSON.stringify(json).replace(/\"/g, "\\\""));

					$timeout.cancel(delay);
					delay = $timeout(function() {
						SpreadSheetService.save($scope.spread);
					}, 300);
				}
			});
		}

		function saveAs() {
			var flatform = $scope.flatForm;
			var name = $scope.flatForm.visId;

			var modalInstance = $modal.open({
				template: '<save-flat-form-as name="name" spread="spread" flatform="flatform" id="save-flat-form-as"></save-flat-form-as>',
				windowClass: 'softpro-modals',
				backdrop: 'static',
				size: 'lg',
				scope: {
					id: '='
				},
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.name = name;
						$scope.spread = spread;
						$scope.flatform = flatform;
						$scope.$on('SaveFlatFormAs:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		}

		// BEFORE REFACTOR
		// $scope.zoomTo = function(value) {
		// 	$scope.$broadcast("ZoomController:zoomTo", value);
		// };

		function newFlatForm() {
			SpreadSheetService.newFlatForm();
		}

		function copyTemplateTo() {
			var sourceid = $scope.flatForm.flatFormId;
			$timeout(function() {
				var modalInstance = $modal.open({
					template: '<copy-template-to id="copy-template-to" sourceid="sourceid"></copy-template-to>',
					windowClass: 'softpro-modals copy-template-to-dialog',
					backdrop: 'static',
					size: 'lg',
					controller: ['$scope', '$modalInstance',
						function($scope, $modalInstance) {
							$scope.sourceid = sourceid;
							$scope.$on('CopyTemplateTo:close', function(event, args) {
								$modalInstance.close();
							});
						}
					]
				});
			}, 100);
		}

		function exit() {
			$scope.hideMenu();
			CoreCommonsService.closeTab($timeout);
		}

		var mainMenu = $("#mainMenu");
		$scope.showMenu = function() {
			mainMenu.fadeIn(100, function() {
				mainMenu.bind('clickoutside', function(event) {
					var target = $(event.target);
					if (target.closest(".sweet-overlay").length > 0 || target.closest(".sweet-alert").length > 0) {
						// don't hide if click on sweet alert
					} else {
						$scope.hideMenu();
					}
				});
			});
			mainMenu.resizable({
				minWidth: 821,
				containment: "parent",
				handles: "e",
				resize: function() {
					$rootScope.$broadcast("MainMenu:refreshCopyTemplateTo");
				}
			});
		};



		function hideMenu() {
			mainMenu.fadeOut(250);
			mainMenu.unbind('clickoutside');
		}

		$scope.$on("MainMenu:show", function() {
			$scope.showMenu();
		});

		$scope.$on("MainMenu:hide", function() {
			$scope.hideMenu();
		});

		$scope.$on("DocumentCompleted:false", function() {
			$scope.isDocumentCompleted = false;
		});

		// row/header headings
		function showHideHeadings(type) {
			if (type == "row" || type == "column") {
				SpreadSheetService.toggleHeaders(spread, type);
			}
		}

		// gridlines
		$scope.areGridLinesVisible = SpreadSheetService.areGridLinesVisible;

		function showHideGridLines(type) {
			if (type == "row" || type == "column") {
				SpreadSheetService.toggleGridlines(spread, type);
			}
		}

		function logout() {
			window.location = url + "/logout.do";
		}

		/****************************************************************************************************************/
		$rootScope.$broadcast('veil:show', {});
		angular.element(document).ready(function() {
			spreadElement = angular.element(document.querySelector('#spreadsheet'));
			$scope.isTranscluded = spreadElement[0].innerHTML;
		});

		$scope.$watch("isTranscluded", function() {
			if (spreadElement) {
//				spread = $scope.spread = spreadElement.wijspread("spread");
				spread = $scope.spread = new GC.Spread.Sheets.Workbook(document.getElementById('spreadsheet'));
				spread.useWijmoTheme = true;

				redraw();
				initializeFormulaBox();
				initializeContextMenu();
				addEvents();
			}
		});

		function initializeFormulaBox() {
//			var formulaBox = new $.wijmo.wijspread.FormulaTextBox(document.getElementById('formula-box'));
			var formulaBox = new GC.Spread.Sheets.FormulaTextBox.FormulaTextBox(document.getElementById("formula-box"));
			formulaBox.workbook($scope.spread);
		}

		function initializeContextMenu() {
			ContextMenuService.initialize(spreadElement);
		}

		function onShowHandler(e) {
			if ($(e.target).attr("id") == "spreadsheet_tabStrip") {
				return false;
			}
			var offset = spreadElement.offset();
			var x = e.pageX - offset.left;
			var y = e.pageY - offset.top;

			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
//			spread.isPaintSuspended(true);
			var target = sheet.hitTest(x, y); // current selected cell (could be cell in headers viewport)

			var inRowRange = false;
			var inColumnRange = false;

			var selections = sheet.getSelections();
			for (var i = 0; i < selections.length; i++) {
				var selection = selections[i];

				inRowRange = (target.row >= selection.row && target.row < selection.row + selection.rowCount);
				inColumnRange = (target.col >= selection.col && target.col < selection.col + selection.colCount);
			}

			if (inRowRange === false || inColumnRange === false) {
				sheet.setActiveCell(target.row, target.col);
			}
			sheet.resumePaint();
//			spread.isPaintSuspended(false);
			return true;
		}

		function updatePositionBox() {
			var sheet = $scope.spread.getActiveSheet();
			var position = sheet.getText(0, sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.colHeader) + sheet.getText(sheet.getActiveRowIndex(), 0, GC.Spread.Sheets.SheetArea.rowHeader);
			$("#position-box").val(position);
		}

		function handleSelectionChanging(e, info) {
			updatePositionBox();
			$rootScope.$broadcast('SpreadSheetController:SelectionChanging', info);
		}

		function handleSheetTabClick(e, info) {
			$timeout(function() {}, 0);
			$rootScope.$broadcast('SpreadSheetController:SheetTabClick', info);
		}

		function handleSheetNameChanging(e, info) {
			var worksheet = CoreCommonsService.findElementByKey($scope.flatForm.xmlForm.worksheets, info.oldValue, "name");
			worksheet.name = info.newValue;
		}

		function handleSelectionChanged(e, info) {
			$rootScope.$broadcast('SpreadSheetController:SelectionChanged', info);
		}

		function handleLeaveCell(e, info) {
			$rootScope.$broadcast('SpreadSheetController:LeaveCell', info);
		}

		function handleDragFillBlock(e, info) {
			$rootScope.$broadcast('SpreadSheetController:DragFillBlock', info);
		}

		function handleUserZooming(e, info) {
			$rootScope.$broadcast('SpreadSheetController:UserZooming', info);
		}

		function removeEvents() {
//			GC.Spread.Sheets.SpreadActions.none = function() {};
			var sheet = $scope.spread.getActiveSheet();
			//arnold
//			sheet.addKeyMap(GC.Spread.Sheets.Key.del, false, false, false, false, GC.Spread.Sheets.SpreadActions.none);
			spread.commandManager().setShortcutKey(undefined, GC.Spread.Commands.Key.del, false, false, false, false);

		}

		function addEvents() {

			$scope.spread.bind(GC.Spread.Sheets.Events.LeaveCell, function(e, info) {
				handleLeaveCell(e, info);
			});
	        spread.bind(GC.Spread.Sheets.Events.ChartClicked, function (event, args) {
	            var sheet = args.sheet, chart = args.chart;
	            showChartPanel(chart);
	        });
	        spread.bind(GC.Spread.Sheets.Events.CellClick, function (event, args) {
	        	$scope.menuLeft = -200;
	            var sheet = args.sheet, chart = args.chart;
	            showChartPanel(chart);
	        });
			$scope.spread.bind(GC.Spread.Sheets.Events.SelectionChanged, function(e, info) {
	            var sheet = info.sheet, chart = info.chart;
	            showChartPanel(chart);

				var activeSheet = spread.getActiveSheet();
				//pobranie zaznaczonych komorek
				var selectedCells = activeSheet.getSelections();
				var numCols = selectedCells[0].colCount;
				var numRows = selectedCells[0].rowCount;
				var startCol = selectedCells[0].col;
				var startRow = selectedCells[0].row;
				var sum = 0;
				var inputMappingContextMenuVisibility = false;
				var outputMappingContextMenuVisibility = false;
				var formulaMappingContextMenuVisibility = false;
				for (var c = startCol; c < startCol + numCols; c++) {
					if (activeSheet.getColumnVisible(c)) {
						for (var d = startRow; d < startRow + numRows; d++) {
							if (activeSheet.getRowVisible(d)) {
//								var cellTag = activeSheet.getCell(d, c).tag();
								var cellTag = activeSheet.getTag(d,c,GC.Spread.Sheets.SheetArea.viewport);
								var cellValue = activeSheet.getCell(d, c).value();
								if (cellTag !== null&&cellTag!=undefined) {
									formulaMappingContextMenuVisibility = formulaMappingContextMenuVisibility || cellTag.text !== null;
									inputMappingContextMenuVisibility = inputMappingContextMenuVisibility || cellTag.inputMapping !== null;
									outputMappingContextMenuVisibility = outputMappingContextMenuVisibility || cellTag.outputMapping !== null;
								}

								if (!isNaN(cellValue)) {
									sum += cellValue;
								}
							}
						}
					}
				}
				$scope.sum = Math.round(sum).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
				updateVisibility(inputMappingContextMenuVisibility, outputMappingContextMenuVisibility, formulaMappingContextMenuVisibility);
				handleSelectionChanged(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.SelectionChanging, function(e, info) {
				// var sheet = $scope.spread.getActiveSheet();
				// var cell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), $.wijmo.wijspread.SheetArea.viewport, true);
				// console.log(cell.cellType())
				handleSelectionChanging(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.SheetTabClick, function(e, info) {
				console.log("start");
				for(var i = 0; i < $scope.flatForm.xmlForm.worksheets.length;i++){
					console.log($scope.flatForm.xmlForm.worksheets[i].name);
				}
				handleSheetTabClick(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.SheetNameChanging, function(e, info) {
				handleSheetNameChanging(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.ActiveSheetChanged, function(e, info) {});

			$scope.spread.bind(GC.Spread.Sheets.Events.UserZooming, function(e, info) {
				handleUserZooming(e, info);

				// BEFORE REFACTOR
				//$scope.$broadcast("ZoomController:zoomTo", Math.round(info.newZoomFactor * 100));
			});

			// Catch events to set spread as "edited". After any of those events web browser will ask if user really wants to close web browser tab.
			var eventsToSetSpreadAsEdited = [
				"SheetNameChanging",
				"RangeGroupStateChanging",
				"CellChanged",
				"ClipboardChanging",
				"ClipboardPasting",
				"ColumnChanged",
				"ColumnWidthChanged",
				"DragDropBlockCompleted",
				"DragFillBlockCompleted",
				"EditStarting",
				"RowChanged",
				"RowHeightChanged",
				"ValueChanged"
			];

			angular.forEach(eventsToSetSpreadAsEdited, function(eventName) {
				$scope.spread.bind(GC.Spread.Sheets.Events[eventName], function(e, info) {
					if ($scope.isDocumentCompleted) {
						//console.log(eventName);
						CoreCommonsService.askIfReload = true;
					}
				});
			});

			angular.element(window).on('keydown', function(event) {
				if (event.keyCode === 113) { // F2
					var sheet = $scope.spread.getActiveSheet();
//					sheet.startEdit(true);
					sheet.setActiveCell(5,5);
					sheet.startEdit(true, "Test");

				}
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.DragFillBlock, function(e, info) {
				handleDragFillBlock(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.CellChanged, function(e, info) {
				if ($scope.isDocumentCompleted) {
					//console.log(e, info);
				}
			});
		}

		/************************************* WATCHERS *********************************************/
		$scope.$watch('viewMode.currentItem', function(newCurrentItem, oldCurrentItem) {
			if (angular.isUndefined($scope.viewMode.currentItem)) {
				return;
			}
			$rootScope.$broadcast('veil:show', {});
			$timeout.cancel(delay);
			delay = $timeout(function() {
				isCurrentItemChoosed = true;
				if (oldCurrentItem.name == "TEST" || oldCurrentItem.name == "LOADING") {
					isWorkbookDownloaded = true;
					setUpCells(true);
				} else {
					setUpCells();
				}
			}, 200);
		});

		$scope.$watch('flatForm.jsonForm', function() {
			if (angular.isUndefined($scope.flatForm.jsonForm) || $scope.loader.isSaving === true) {
				return;
			}
			setUpSpreadSheetView();
//			// TODO do it the other way
//			// Set Home tab as active after page loading.
			$timeout(function() {
				$("#container-navbar li[heading='Home'] a").click();
				//$("#container-navbar li[heading='Data'] a").click();
			}, 0);
		});
		$scope.$watch('flatForm.xmlForm', function() {
			if (angular.isUndefined($scope.flatForm.xmlForm) || $scope.loader.isSaving === true) {
				return;
			}
			isWorkbookDownloaded = true;
			buildCells();
		});

		$scope.$watch('xmlFormTest.xmlForm', function() {
			if ($scope.xmlFormTest && $scope.xmlFormTest.xmlForm) {
				isWorkbookDownloaded = true;
				if ($scope.xmlFormTest.xmlForm.valid === false) {
					var errmsg = [];
					errmsg = validationService.getValidationErrors($scope.xmlFormTest.xmlForm);
					if (errmsg.length > 0) {
						validationService.showErrors(errmsg);
					}
				}
				// 	var sheets = $scope.xmlFormTest.xmlForm.worksheets;
				// 	var errmsg = [];
				// 	if (sheets !== null && sheets !== undefined) {
				// 		angular.forEach(sheets, function(sheet) {
				// 			var cells = sheet.cells;
				// 			var cellmsg = [];
				// 			angular.forEach(cells, function(cell) {
				// 				var errors = cell.validationMessages;
				// 				if (errors !== null && errors !== undefined) {
				// 					if (errors[0] !== null && errors[0] !== undefined) {
				// 						cellmsg.push(errors[0]);
				// 					}
				// 					if (errors[1] !== null && errors[1] !== undefined) {
				// 						cellmsg.push(errors[1]);
				// 					}
				// 				}
				// 			});
				// 			if (cellmsg.length > 0) {
				// 				errmsg.push({
				// 					sheet: "Sheet \"" + sheet.name + "\" has incorect mappings:",
				// 					cell: cellmsg
				// 				});
				// 			}
				// 		});
				// 	}
				// 	if (errmsg.length > 0) {
				// 		validationViewer.show(errmsg);
				// 	}
				// }
				setUpCells(true, $scope.xmlFormTest.xmlForm);
			}
		});
		// $scope.$watch('xmlFormTest.xmlForm', function() {
		// 	if ($scope.xmlFormTest && $scope.xmlFormTest.xmlForm) {
		// 		isWorkbookDownloaded = true;
		// 		if ($scope.xmlFormTest.xmlForm.isValid == false) {
		// 			$timeout(function() {
		// 				swal({
		// 					title: "Validation Problem.",
		// 					text: $scope.xmlFormTest.xmlForm.validationMessage,
		// 					type: "warning",
		// 					showCancelButton: false,
		// 					confirmButtonColor: "#d9534f",
		// 					confirmButtonText: "Ehh...",
		// 				}, function(isConfirm) {});
		// 			}, 500)
		// 			$rootScope.$broadcast('veil:hide', {});
		// 		} else {
		// 			setUpCells(true, $scope.xmlFormTest.xmlForm);
		// 		}
		// 	}
		// });

		$scope.$watch("flatForm.visId", function(newValue, oldValue) {
			if (oldValue !== undefined) {
				CoreCommonsService.askIfReload = true;
			}
		});

		$scope.$watch("flatForm.description", function(newValue, oldValue) {
			if (oldValue !== undefined) {
				CoreCommonsService.askIfReload = true;
			}
		});

		/************************ RESIZE SPREADSHEET AREA *****************************/
		var fullHeightFlag = $(window).height();
		var redrawDelay;

		function redraw() {
			$timeout.cancel(redrawDelay);
			redrawDelay = $timeout(function() {
				var fullHeight = $(window).height();

				var containerNavbarHeight = $("#container-navbar").outerHeight(true);
				var formulaBarHeight = 0;
				if ($("#formula-bar").css("display") !== "none") {
					formulaBarHeight = $("#formula-bar").outerHeight(true);
				}
				var statusBarHeight = $("#status-bar").outerHeight(true);

				var height = fullHeight - containerNavbarHeight - formulaBarHeight - statusBarHeight;
				$("#spreadsheet").height(height);
//				$("#spreadsheet").wijspread("refresh");
//				spread = GC.Spread.Sheets.findControl(document.getElementById('spreadsheet'));
				spread.refresh();
			}, 5);
		}

		$(document).ready(function() {
			redraw();
			$(window).resize(function() {
				var actualFullHeight = $(window).height();
				if (fullHeightFlag != actualFullHeight) {
					fullHeightFlag = actualFullHeight;
					redraw();
				}
			});
	        $('html').keyup(function(e){
	            if(e.keyCode == 46) {
	            	$scope.DeleteChart();
	            }
	        });

		});
        function getActiveChart(sheet) {
            var activeChart = null;
            sheet.charts.all().forEach(function(chart) {
                if (chart.isSelected()) {
                    activeChart = chart;
                }
            });
            return activeChart;
        }
//        function getActiveChart() {
//            var activeSheet = spread.getActiveSheet();
//            var chart = getActiveChart(activeSheet);
//
//            return chart;
//        }
        function showChartPanel(chart) {
            if (chart && chart.isSelected()) {
            	$scope.menuRight = 0;
            	
                updateChartOption(chart);
            }else
            	$scope.menuRight = -300;
            
        	$scope.$apply();

        }
        function updateChartOption(chart) {
            updateChartAreaSetting(chart);
            updateChartTitleSetting(chart);
//            updateChartSeriesSetting(chart, 0);
            updateChartLegendSetting(chart);
            updateChartDataLabelsSetting(chart);
            updateChartAxesSetting(chart);
        }
        function updateChartTitleSetting(chart) {
            if(chart){
                var title = chart.title();
                setTextValue('chartTitletext',title.text);
                $scope.chartTitleColor = title.color;
                
                setDropDownText("chartTitleFontSize", parseInt(title.fontSize));
                setDropDownText("chartTitleFontFamily", title.fontFamily);
            }
        }
        function setTextValue(name, value) {
            $("div.insp-text[data-name='" + name + "'] input.editor").val(value);
        }
        function setColorValue(name, value) {
            //$("div.insp-color-picker[data-name='" + name + "'] div.color-view").css("background-color", value || "");
        	name = value;
        }
        function setDropDownText(container, value) {
        	$("#"+container).val(value);
        }
        function getDropDownText(container){
        	return $("#"+container).val();
        }
        function getTextValue(name) {
            return $("div.insp-text[data-name='" + name + "'] input.editor").val();
        }
        function getColorByThemeColor(themeColor) {
            var sheet = spread.getActiveSheet();
            var theme = sheet.currentTheme();
            return theme.getColor(themeColor);
        	return 0;
        }
        $scope.applyChartTitle = function(){
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);
            if(chart){
                var fontSize = parseInt(getDropDownText('chartTitleFontSize'));
                var fontFamily = getDropDownText("chartTitleFontFamily")
                var text = getTextValue('chartTitletext');
                var color = getColorByThemeColor($scope.chartTitleColor);
                var title = chart.title();
                title.text = text;
                title.color  = color ;
                title.fontFamily = fontFamily;
                title.fontSize = fontSize;
                chart.title(title);
            }

        }
        function updateChartAreaSetting(chart) {
            if(chart){
                var chartArea = chart.chartArea();
                $scope.chartAreaBackColor = getColorByThemeColor(chartArea.backColor);
                $scope.chartAreaColor = getColorByThemeColor(chartArea.color);
                setDropDownText("chartAreaFontFamily", chartArea.fontFamily);
                setDropDownText("chartAreaFontSize", parseInt(chartArea.fontSize));
            }
        }
        $scope.applyChartAreaSetting = function() {
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);
            if(chart){
                var fontSize = parseInt(getDropDownText("chartAreaFontSize"));
                var fontFamily = getDropDownText("chartAreaFontFamily")
//                var backColor = getBackgroundColor("chartAreaBackColor");
//                var color = getBackgroundColor("chartAreaColor");
                var chartArea = chart.chartArea();
                chartArea.fontSize = fontSize;
                
                chartArea.backColor  = $scope.chartAreaBackColor ;
                chartArea.color = $scope.chartAreaColor;
                chartArea.fontFamily = fontFamily;
                chart.chartArea(chartArea);
            }
        }
        function updateChartLegendSetting(chart) {
            var chartGroupString = getChartGroupString(chart.chartType());
            // there is no legend for stock chart, need to control whether to show legend group in panel.
            if (chartGroupString === "StockGroup") {
                $('#chartLegendGroup').hide();
                return;
            }
            $('#chartLegendGroup').show();
            var legend = chart.legend();
            setCheckValue("showChartLegend", legend.visible);
            var position = legend.position.toString();
            setDropDownText("chartLegendPositionList", position);

        }
        function getChartGroupString (typeValue) {
            var chartTypeInfo = chartTypeDict[typeValue];
            if (chartTypeInfo && chartTypeInfo.chartGroup) {
                return chartTypeInfo.chartGroup;
            }
        }

        function setCheckValue(id,value,options){
        	var $taget = $("#"+id);
        	$taget.prop("checked",value);
            if (options) {
                $target.data(options);
            }
        }
        function getCheckValue(id){
        	return $('#' + id).is(":checked");
        }
        $scope.applyChartLegendSetting = function() {
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);

            var chartGroupString = getChartGroupString(chart.chartType());
            if(chart && chartGroupString !== "StockGroup"){
                var legend = chart.legend();
                var isShowLegend = getCheckValue("showChartLegend");
                legend.visible = isShowLegend;
                var currentPosition = getDropDownText("chartLegendPositionList");
                legend.position = currentPosition;
                chart.legend(legend);
            }
        }
        function updateChartDataLabelsSetting(chart) {
            var chartGroupString = getChartGroupString(chart.chartType());
            if(chartGroupString === "StockGroup"){
                // there is no data labels for stock chart, hide this dom in panel.
                $("#chartDataLabelsGroup").hide();
                return;
            }
            $("#chartDataLabelsGroup").show();
            var dataLabels = chart.dataLabels();
            showValue = dataLabels.showValue;
            showSeriesName = dataLabels.showSeriesName;
            showCategoryName = dataLabels.showCategoryName;

            var isShow = judjeDataLabelsIsShow();
            updateDataLabelsPositionDropDown(isShow);
            setCheckValue("showDataLabelsValue",dataLabels.showValue);
            setCheckValue("showDataLabelsSeriesName",dataLabels.showSeriesName);
            setCheckValue("showDataLabelsCategoryName",dataLabels.showCategoryName);
            setColorValue("dataLabelsColor",getColorByThemeColor(dataLabels.color));

        }
        function updateDataLabelsPositionDropDown(isShow){
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);

            if(chart){
                var obj = getChartDataLabelsDescAndKey(chart);
                var dataLabelsKeyArray = obj.key;
                var dataLabelsDescArray = obj.desc;
                var dataLabels = chart.dataLabels();
                if(isShow){
                    var position = dataLabels.position;
                    //get dropDownIndex
                    var index = 0;
                    for(var i=0;i<dataLabelsKeyArray.length;i++){
                        if(position === dataLabelsKeyArray[i]){
                            index = i;
                            break;
                        }
                    }
                    $('#dataLabelsColorCon').show();
                    //create dropDownList
                    if(dataLabelsDescArray.length>0){
                        $('#chartDataLabelPositionDropDown').show();
                        var $host = $('#chartDataLabelList');
                        $host.html('');
                        createSeriesListMenu($host,dataLabelsDescArray);
//                        setDropDownValue("chartDataLabelPosition", index);
                    }else{
                        //hide dropDown
                        $('#chartDataLabelPositionDropDown').hide();
                    }
                }else{
                    //hide
                    $('#chartDataLabelPositionDropDown').hide();
                    $('#dataLabelsColorCon').hide();
                }
            }
        }
        
        function judjeDataLabelsIsShow(isShowObj){
            var isShow;
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);

            if(isShowObj !== undefined && isShowObj !== null){
                var itemString = isShowObj.item;
                switch (itemString){
                    case "showDataLabelsValue":
                        showValue = isShowObj.isShow;
                        break;
                    case "showDataLabelsSeriesName":
                        showSeriesName = isShowObj.isShow;
                        break;
                    case "showDataLabelsCategoryName":
                        showCategoryName = isShowObj.isShow;
                        break;
                    default:
                        isShow = false;
                        break;
                }
            }
            isShow = showCategoryName || showValue|| showSeriesName;
            return isShow;
        }
        function getChartTypeString (typeValue) {
            var chartTypeInfo = chartTypeDict[typeValue];
            if (chartTypeInfo && chartTypeInfo.chartType) {
                return chartTypeInfo.chartType;
            }
        }


        function getChartDataLabelsDescAndKey(chart) {
            var chartGroupString = getChartGroupString(chart.chartType());
            var chartTypeString = getChartTypeString(chart.chartType());
            var dataLabelsDescArray = [];
            var dataLabelsKeyArray = [];
            if(chartTypeString === 'doughnut'){
                dataLabelsDescArray = [];
                dataLabelsKeyArray = [];
            }else if(chartGroupItemObj[chartGroupString]){
                var array = chartGroupItemObj[chartGroupString];
                for(var i=0;i<array.length;i++){
                    dataLabelsDescArray.push(array[i].desc);
                    dataLabelsKeyArray.push(array[i].key);
                }
            }
            return {desc:dataLabelsDescArray,key:dataLabelsKeyArray};
        }
        $scope.applyChartDataLabelsSetting = function(){
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);
            if(chart){
                var dataLabels = chart.dataLabels();
                var showValue = getCheckValue("showDataLabelsValue");
                var showSeriesName = getCheckValue("showDataLabelsSeriesName");
                var showCategoryName = getCheckValue("showDataLabelsCategoryName");
                dataLabels.showValue = showValue;
                dataLabels.showSeriesName = showSeriesName;
                dataLabels.showCategoryName = showCategoryName;
                chart.dataLabels(dataLabels);
            }
        }
        function updateChartAxesSetting(chart) {
            //var axes = chart.axes();
            setDropDownText("chartAxieType", 0);
            var chartGroupString = getChartGroupString(chart.chartType());
            if(chartGroupString === 'PieGroup'){
                $('#chartAxesGroup').hide();
            }else{
                $('#chartAxesGroup').show();
                changeAxieTypeIndex('0');
            }
        }
        
        $scope.applyChartAxesSetting = function() {
//            var chart = getActiveChart();
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);

            var spreadCH = GC.Spread.Sheets.Charts;
            if (chart) {
                var axes = chart.axes();
                var axesType = getDropDownText("chartAxieType");
                var text = getTextValue("chartAixsTitletext");
                var showMajorGridline = getCheckValue("showMajorGridline");
                var showMinorGridline = getCheckValue("showMinorGridline");
                var showAxis = getCheckValue("showAxis");
                var aixsTitleColor = $scope.chartAixsTitleColor;
                var aixsTitleFontFamily = getDropDownText("chartAxesTitleFontFamily");
                var aixsTitleFontSize = getDropDownText("chartAxesTitleFontSize");
                var aixsColor = $scope.chartAixsColor;
                var aixsFontFamily = getDropDownText("chartAxesFontFamily");
                var aixsFontSize = getDropDownText("chartAxesFontSize");
                var aixsLineColor = $scope.chartAixsLineColor;
                var aixsLineWidth = parseInt(getTextValue("chartAixsLineWidth"));
                var aixsMajorUnit = parseInt(getTextValue("chartAixsMajorUnit"));
                var aixsMinorUnit = parseInt(getTextValue("chartAixsMinorUnit"));
                var aixsMajorGridlineWidth = parseInt(getTextValue("chartAixsMajorGridlineWidth"));
                var aixsMajorGridlineColor = $scope.chartAixsMajorGridlineColor;
                var aixsMinorGridlineWidth = parseInt(getTextValue("chartAixsMinorMinorGridlineWidth"));
                var aixsMinorGridlineColor = $scope.chartAixsMinorGridlineColor;
                var aixsTickLabelPosition;
                switch (getDropDownText("chartTickLabelPosition")) {
                    case '3':
                        aixsTickLabelPosition = spreadCH.TickLabelPosition.none;
                        break;
                    case '2':
                        aixsTickLabelPosition = spreadCH.TickLabelPosition.nextToAxis;
                        break;
                }
                var aixsMajorTickPosition;
                var aixsMinorTickPosition;
                switch (getDropDownText("chartMajorTickPosition")) {
                    case '0':
                        aixsMajorTickPosition = spreadCH.TickMark.cross;
                        break;
                    case '1':
                        aixsMajorTickPosition = spreadCH.TickMark.inside;
                        break;
                    case '2':
                        aixsMajorTickPosition = spreadCH.TickMark.none;
                        break;
                    case '3':
                        aixsMajorTickPosition = spreadCH.TickMark.outside;
                        break;
                }

                switch (getDropDownText("chartMinorTickPosition")) {
                    case '0':
                        aixsMinorTickPosition = spreadCH.TickMark.cross;
                        break;
                    case '1':
                        aixsMinorTickPosition = spreadCH.TickMark.inside;
                        break;
                    case '2':
                        aixsMinorTickPosition = spreadCH.TickMark.none;
                        break;
                    case '3':
                        aixsMinorTickPosition = spreadCH.TickMark.outside;
                        break;
                }

                var axesTY;
                switch (axesType) {
                    case '0':
                        axesTY = axes.primaryCategory;
                        break;
                    case '1':
                        axesTY = axes.primaryValue;
                        break;
                    case '2':
                        axesTY = axes.secondaryCategory;
                        break;
                    case '3':
                        axesTY = axes.secondaryValue;
                        break;

                }
                axesTY.style.color = aixsColor;
                axesTY.style.fontFamily = aixsFontFamily;
                axesTY.style.fontSize = aixsFontSize;
                axesTY.title.text = text;
                axesTY.title.color = aixsTitleColor;
                axesTY.title.fontFamily = aixsTitleFontFamily;
                if (aixsTitleFontSize) {
                    axesTY.title.fontSize = aixsTitleFontSize;
                }
                axesTY.majorGridLine.visible = showMajorGridline;
                axesTY.minorGridLine.visible = showMinorGridline;
                axesTY.minorGridLine.visible = showMinorGridline;
                axesTY.lineStyle.color = aixsLineColor;
                axesTY.lineStyle.width = aixsLineWidth;
                axesTY.majorTickPosition = aixsMajorTickPosition;
                axesTY.minorTickPosition = aixsMinorTickPosition;
                axesTY.visible = showAxis;
                axesTY.majorUnit = aixsMajorUnit;
                axesTY.minorUnit = aixsMinorUnit;
                axesTY.majorGridLine.width = aixsMajorGridlineWidth;
                axesTY.majorGridLine.color = aixsMajorGridlineColor;
                axesTY.minorGridLine.width = aixsMinorGridlineWidth;
                axesTY.minorGridLine.color = aixsMinorGridlineColor;
                axesTY.tickLabelPosition = aixsTickLabelPosition;

                chart.axes(axes);

                changeAxieTypeIndex(axesType);
            }
        }
        
        function changeAxieTypeIndex(nameValue) {
            var activeSheet = spread.getActiveSheet();
            var chart = getActiveChart(activeSheet);
            var axes = chart.axes();
            switch(nameValue){
                case '0':
                    axesTY = axes.primaryCategory;
                    break;
                case '1':
                    axesTY = axes.primaryValue;
                    break;
                case '2':
                    axesTY = axes.secondaryCategory;
                    break;
                case '3':
                    axesTY = axes.secondaryValue;
                    break;
            }
            
            var chartType = chart.chartType();
            if(chartType !== 10 && chartType !== 3){
                var text = axesTY.title.text;
                var aixsLineWidth = axesTY.lineStyle.width;
                var aixsMajorUnit = axesTY.majorUnit || 'Auto';
                var aixsMinorUnit = axesTY.minorUnit || 'Auto';
                var aixsMajorGridlineWidth = axesTY.majorGridLine.width;
                var aixsMinorGridlineWidth = axesTY.minorGridLine.width;

                var aixsFontFamily = axesTY.style.fontFamily;
                var aixsTitleFontFamily = axesTY.title.fontFamily || '';
                var aixsTitleFontSize = axesTY.title.fontSize || '';
                var aixsFontSize = axesTY.style.fontSize;

                var showMajorGridline = axesTY.majorGridLine.visible;
                var showMinorGridline = axesTY.minorGridLine.visible;
                var showAxis = axesTY.visible;

                var aixsTitleColor = axesTY.title.color || 'rgb(153,153,153)';
                var aixsColor = axesTY.style.color || 'rgb(153,153,153)';
                var aixsLineColor = axesTY.lineStyle.color || 'rgb(153,153,153)';
                var aixsMajorGridlineColor = axesTY.majorGridLine.color || 'rgb(153,153,153)';
                var aixsMinorGridlineColor = axesTY.minorGridLine.color || 'rgb(153,153,153)';

                var aixsTickLabelPosition = axesTY.tickLabelPosition.toString();
                var aixsMajorTickPosition = axesTY.majorTickPosition.toString();
                var aixsMinorTickPosition = axesTY.minorTickPosition.toString();

                setTextValue('chartAixsTitletext', text);
                setTextValue("chartAixsLineWidth", aixsLineWidth);
                setTextValue("chartAixsMajorUnit", aixsMajorUnit);
                setTextValue("chartAixsMinorUnit", aixsMinorUnit);
                setTextValue("chartAixsMajorGridlineWidth", aixsMajorGridlineWidth);
                setTextValue("chartAixsMinorMinorGridlineWidth", aixsMinorGridlineWidth);


                setDropDownText("chartAxesFontFamily", aixsFontFamily);
                setDropDownText("chartAxesFontSize", aixsFontSize);

                setDropDownText("chartAxesTitleFontFamily", aixsTitleFontFamily);
                setDropDownText("chartAxesTitleFontSize", aixsTitleFontSize);

                setCheckValue("showMajorGridline", showMajorGridline);
                setCheckValue("showMinorGridline", showMinorGridline);
                setCheckValue("showAxis", showAxis);

                $scope.chartAixsTitleColor = getColorByThemeColor(aixsTitleColor);
                $scope.chartAixsColor = getColorByThemeColor(aixsColor);
                $scope.chartAixsLineColor = getColorByThemeColor(aixsLineColor);
                $scope.chartAixsMajorGridlineColor = getColorByThemeColor(aixsMajorGridlineColor);
                $scope.chartAixsMinorGridlineColor=getColorByThemeColor(aixsMinorGridlineColor);

                setDropDownText("chartTickLabelPosition", aixsTickLabelPosition);
                setDropDownText("chartMajorTickPosition", aixsMajorTickPosition);
                setDropDownText("chartMinorTickPosition", aixsMinorTickPosition);
            }
        }
        

	}
})();