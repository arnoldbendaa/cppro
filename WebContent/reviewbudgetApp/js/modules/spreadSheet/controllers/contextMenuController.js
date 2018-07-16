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
		.module('app.spreadSheet')
		.controller('ContextMenuController', ContextMenuController);

	/* @ngInject */
	function ContextMenuController($scope, $rootScope, SpreadSheetService) {


		$scope.showNotes = showNotes;
		$scope.showFinanceSystemCellModal = showFinanceSystemCellModal;
		$scope.showCellStatusModal = showCellStatusModal;

		var CellObject = {},
			Cell = {},
			$contextMenu = $("#context-menu");

		$rootScope.$on('showContextMenu', function(event, args) {
			CellObject = args.cellType;
			Cell = args.cell;

			if (args.cellType === undefined)
				return;

			$contextMenu.css({
				display: "block",
				left: args.x,
				top: args.y - 10
			});

			return false;
		});

		$contextMenu.on("click", "a", function() {
			$contextMenu.hide();
		});

		function showNotes() {
			var spreadSheet = SpreadSheetService.spreadSheet;
			var worksheetId = spreadSheet.getActiveSheetIndex();
			$rootScope.$broadcast('NotesController:OpenNotesManager', {
				cellObject: CellObject,
				Cell: Cell,
				workbook: SpreadSheetService.workbook,
				worksheetId: worksheetId
			});
		}

		function showFinanceSystemCellModal() {
			$rootScope.$broadcast('FinanceSystemCellController:openView');
		}

		function showCellStatusModal() {
			$rootScope.$broadcast('CellStatusController:openView');
		}
	}
})();