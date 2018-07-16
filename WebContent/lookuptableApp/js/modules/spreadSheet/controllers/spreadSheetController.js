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
		.module('lookupTableApp.spreadSheet')
		.controller('SpreadSheetController', SpreadSheetController);

	/* @ngInject */
	function SpreadSheetController($rootScope, $scope, $timeout, SpreadSheetService, DrawingService, ContextMenuService, CoreCommonsService) {
		var spreadElement;
		var spread;

		$scope.templatePath = $BASE_TEMPLATE_PATH;
		$scope.isDocumentCompleted = false; // to: allow change askIfReload by binded events, show tab navigation
		$scope.spreadLoader = SpreadSheetService.getSpreadLoader();

		$scope.onShowHandler = onShowHandler;

		activate();
		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			onDomLoaded();
			onSpreadLoaded();
			onJSONLoaded();
		}

		function onDomLoaded() {
			angular.element(document).ready(function() {
				spreadElement = angular.element(document.querySelector('#spreadsheet'));
				$scope.isTranscluded = spreadElement[0].innerHTML;
			});
		}

		function onJSONLoaded() {
			$scope.$watch("spreadLoader.isJsonLoaded", function() {
				if ($scope.spreadLoader.isJsonLoaded) {
					//updateZoom();
				}
			});
		}

		function onSpreadLoaded() {
			$scope.$watch("isTranscluded", function() {
				if (spreadElement) {
//					spread = $scope.spread = spreadElement.wijspread("spread");
					spread = $scope.spread = new GC.Spread.Sheets.Workbook(document.getElementById('spreadsheet'));
					spread.useWijmoTheme = true;
					SpreadSheetService.setSpread(spread);
					SpreadSheetService.setEmptyJsonForm();

					SpreadSheetService.initializeFormulaBox();
					initializeContextMenu();

					addEvents();
					DrawingService.redraw();
					$scope.isDocumentCompleted = true;
				}
			});
		}

		function initializeContextMenu() {
			ContextMenuService.initialize(spreadElement);
		}

		function handleSelectionChanging(e, info) {
			SpreadSheetService.updatePositionBox();
			$rootScope.$broadcast('SpreadSheetController:SelectionChanging', info);
		}

		function handleSelectionChanged(e, info) {
			$rootScope.$broadcast('SpreadSheetController:SelectionChanged', info);
		}

		function handleUserZooming(e, info) {
			$rootScope.$broadcast('SpreadSheetController:UserZooming', info);
		}

		function handleSheetTabClick(e, info) {
			$timeout(function() {}, 0);
			$rootScope.$broadcast('SpreadSheetController:SheetTabClick', info);
		}

		function handleSheetNameChanging(e, info) {
			var worksheet = CoreCommonsService.findElementByKey($scope.template.workbook.worksheets, info.oldValue, "name");
			if (worksheet) {
				worksheet.name = info.newValue;
			}
		}

		function addEvents() {
			$scope.spread.bind(GC.Spread.Sheets.Events.SelectionChanged, function(e, info) {
				handleSelectionChanged(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.SelectionChanging, function(e, info) {
				handleSelectionChanging(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.SheetTabClick, function(e, info) {
				handleSheetTabClick(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.UserZooming, function(e, info) {
				handleUserZooming(e, info);
			});

			$scope.spread.bind(GC.Spread.Sheets.Events.SheetNameChanging, function(e, info) {
				handleSheetNameChanging(e, info);
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
						// console.log(eventName);
						CoreCommonsService.askIfReload = true;
					}
				});
			});
		}

		function onShowHandler(e) {
			if ($(e.target).attr("id") !== "spreadsheetvp_vp") {
				return false;
			}
			var offset = spreadElement.offset();
			var x = e.pageX - offset.left;
			var y = e.pageY - offset.top;

			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
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
			return true;
		}

	}

})();