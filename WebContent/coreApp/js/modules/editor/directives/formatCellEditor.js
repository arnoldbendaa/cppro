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
		.directive('formatCellEditor', formatCellEditor);

	/* @ngInject */
	function formatCellEditor(ColorService, FormatService, $timeout) {
		var directive = {
			restrict: 'E',
			templateUrl: $BASE_PATH + 'coreApp/js/modules/editor/views/formatCellEditor.html',
			scope: {
				activetab: "=activetab",
				spread: "=",
				close: "&",
			},
			replace: true,
			controller: 'coreApp.formatCellEditorController',
			link: formatCellEditorLinkFunction
		};

		return directive;



		function formatCellEditorLinkFunction(scope, element, attrs) {

			var sheet = null;

			scope.previewSpread = null;
			scope.changeBorder = changeBorder;
			scope.redraw = redraw;
			scope.onTabSelect = onTabSelect;


			activate();

			function activate() {
				var spreadElement = angular.element(document.querySelector('#format-cell-editor-preview'));
//				scope.previewSpread = spreadElement.wijspread("spread");
				scope.previewSpread = new GC.Spread.Sheets.Workbook(document.getElementById('format-cell-editor-preview'));
				organizePreviewProperties(scope.previewSpread);
				sheet = scope.previewSpread.getActiveSheet();
				scope.changeBorder();
				scope.redraw();
			}

			function changeBorder() {
				scope.isBorderEdited = true;
//				scope.previewSpread.isPaintSuspended(true);
				scope.previewSpread.suspendPaint();
				var cell = sheet.getCell(1, 6, GC.Spread.Sheets.SheetArea.viewport);
				var borderTop = new GC.Spread.Sheets.LineBorder();
				borderTop.color = scope.borderTop.color;
				borderTop.style = scope.borderTop.style;
				cell.borderTop(borderTop);

				var borderRight = new GC.Spread.Sheets.LineBorder();
				borderRight.color = scope.borderRight.color;
				borderRight.style = scope.borderRight.style;
				cell.borderRight(borderRight);

				var borderBottom = new GC.Spread.Sheets.LineBorder();
				borderBottom.color = scope.borderBottom.color;
				borderBottom.style = scope.borderBottom.style;
				cell.borderBottom(borderBottom);

				var borderLeft = new GC.Spread.Sheets.LineBorder();
				borderLeft.color = scope.borderLeft.color;
				borderLeft.style = scope.borderLeft.style;
//				borderLeft.style = GC.Spread.Sheets.LineStyle.dashDot;
				cell.borderLeft(borderLeft);

//				scope.previewSpread.isPaintSuspended(false);
				scope.previewSpread.resumePaint();
			}

			function redraw() {
				var delay;
				$timeout.cancel(delay);
				delay = $timeout(function() {
					$("#format-cell-editor-preview").height(150);
//					$("#format-cell-editor-preview").wijspread("refresh");
					var spread = GC.Spread.Sheets.findControl(document.getElementById('format-cell-editor-preview'));
					if(spread==undefined||spread==null)
						spread = new GC.Spread.Sheets.Workbook(document.getElementById("format-cell-editor-preview"));

					spread.refresh();

				}, 5);
			}

			function onTabSelect(param) {
				if (param == 'Border') {
					$("#preview").show();
				} else {
					$("#preview").hide();
				}
			}


			function organizePreviewProperties(spread) {
//				spread.isPaintSuspended(true);
				spread.suspendPaint();
				spread.useWijmoTheme = true;
				for (var i = 0; i < spread.getSheetCount(); i++) {
					spread.removeSheet(i);
				}
				spread.addSheet(0, new GC.Spread.Sheets.Worksheet("PREVIEW"));
				spread.setActiveSheetIndex(0);
				spread.options.newTabVisible = false;
				spread.options.tabStripVisible = false;
				spread.options.showHorizontalScrollbar = false;
				spread.options.showVerticalScrollbar = false;
				spread.isPaintSuspended = false;
				spread.options.canUserDragDrop = false;
				spread.options.canUserDragFill = false;

				var sheet = spread.getActiveSheet();
				sheet.isProtected = (true);
//				sheet.setGridlineOptions({
//					showVerticalGridline: false,
//					showHorizontalGridline: false
//				});
				sheet.options.gridline = {showHorizontalGridline:false,showVerticalGridline:false};
				sheet.setRowHeight(1, 90, GC.Spread.Sheets.SheetArea.viewport);
				sheet.setColumnWidth(6, 90, GC.Spread.Sheets.SheetArea.viewport);
				sheet.setRowCount(3);
				sheet.setColumnCount(13);
				sheet.clearSelection();

				var cell = sheet.getCell(1, 6, GC.Spread.Sheets.SheetArea.viewport);
				cell.value("Preview Cell");
				cell.hAlign(GC.Spread.Sheets.HorizontalAlign.center);
				cell.vAlign(GC.Spread.Sheets.VerticalAlign.center);
				spread.resumePaint();
				

//				spread.isPaintSuspended(false);
			}
		}
	}

})();