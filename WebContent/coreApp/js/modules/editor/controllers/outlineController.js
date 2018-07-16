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
		.controller('OutlineController', OutlineController);

	/* @ngInject */
	function OutlineController($scope, CoreCommonsService) {

		$scope.groupSelection = groupSelection;
		$scope.ungroupSelection = ungroupSelection;
		$scope.showDetailGroup = showDetailGroup;
		$scope.hideDetailGroup = hideDetailGroup;


		/************************************************** IMPLEMENTATION *************************************************************************/


		function groupSelection() {
			
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var sels = sheet.getSelections();
			var sel = sels[0];

			var groupExtent;
			var action;

			if (sel.col == -1) // row selection
			{
				CoreCommonsService.askIfReload = true;
				sheet.rowOutlines.group(sel.row, sel.rowCount); 
	            sheet.invalidateLayout(); 
	            sheet.repaint(); 
			} else if (sel.row == -1) // column selection
			{
				CoreCommonsService.askIfReload = true;
				sheet.columnOutlines.group(sel.col, sel.colCount); 
	            sheet.invalidateLayout(); 
	            sheet.repaint(); 
			} else {
				swal("Error", "Please select a range of rows or columns.", "warning");
			}
			sheet.resumePaint();
		}

		function ungroupSelection() {
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();

			var sels = sheet.getSelections();
			var sel = sels[0];
			var groupExtent;
			var action;

			if (sel.col == -1 && sel.row == -1) // sheet selection
			{
				CoreCommonsService.askIfReload = true;
				sheet.columnOutlines.ungroup(0, sheet.getRowCount());
				sheet.columnOutlines.ungroup(0, sheet.getColumnCount()); 

			} else if (sel.col == -1) // row selection
			{
				CoreCommonsService.askIfReload = true;
				sheet.columnOutlines.ungroup(sel.col, sel.colCount); 
	            sheet.invalidateLayout(); 
	            sheet.repaint(); 

			} else if (sel.row == -1) // column selection
			{
				sheet.columnOutlines.ungroup(sel.row, sel.rowCount); 
	            sheet.invalidateLayout(); 
	            sheet.repaint(); 
			} else // cell range selection
			{
				swal("Error", "Please select a range of rows or columns.", "warning");
			}
			sheet.resumePaint();
		}

		function toggleDetailGroup(isShown) {
			var sheet = $scope.spread.getActiveSheet();
//			sheet.isPaintSuspended(true);
			sheet.suspendPaint();

			var sels = sheet.getSelections();
			var sel = sels[0];
			var rgi;
			var i = 0;

			if (sel.col == -1 && sel.row == -1) // sheet selection
			{} else if (sel.col == -1) // row selection
			{
				for (i = 0; i < sel.rowCount; i++) {
					rgi = sheet.rowOutlines.find(sel.row + i, 0);
					if (rgi) {
						sheet.rowOutlines.expand(rgi.level, isShown);
					}
				}
			} else if (sel.row == -1) // column selection
			{
				for (i = 0; i < sel.colCount; i++) {
					rgi = sheet.columnOutlines.find(sel.col + i, 0);
					if (rgi) {
						sheet.columnOutlines.expand(rgi.level, isShown);
					}
				}
			} else // cell range selection
			{}

//			sheet.isPaintSuspended(false);
			sheet.resumePaint();
		}

		function showDetailGroup() {
			toggleDetailGroup(true);
		}

		function hideDetailGroup() {
			toggleDetailGroup(false);
		}
	}

})();