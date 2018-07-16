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
		.controller('ClipboardController', ClipboardController);

	/* @ngInject */
	function ClipboardController($scope) {
		$scope.cut = cut;
		$scope.copy = copy;
		$scope.paste = paste;

		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/



		function activate() {
			onCut();
			onCopy();
			onPaste();
		}

		function cut() {
//			var sheet = $scope.spread.getActiveSheet();
//			sheet.isPaintSuspended(true);
//			$.wijmo.wijspread.SpreadActions.cut.call(sheet);
//			sheet.isPaintSuspended(false);
			var spread = $scope.spread;
			var sheet = $scope.spread.getActiveSheet();
			var commandManager = spread.commandManager();
			commandManager.execute({cmd:"paste",sheetName:sheet.name()})

			
		}

		function copy() {
//			var sheet = $scope.spread.getActiveSheet();
//			sheet.isPaintSuspended(true);
////			$.wijmo.wijspread.SpreadActions.copy.call(sheet);
////			GC.Spread.Sheets.Commands.copy.execute(sheet);
//			sheet.options.clipBoardOptions = GC.Spread.Sheets.ClipboardPasteOptions.values;
//			sheet.isPaintSuspended(false);
			var spread = $scope.spread;
			var sheet = $scope.spread.getActiveSheet();
			sheet.options.clipBoardOptions = GC.Spread.Sheets.ClipboardPasteOptions.all;
			var commandManager = spread.commandManager();
			commandManager.execute({cmd:"copy",sheetName:sheet.name()})
//			commandManager.copy;
//			commandManager.execute(spread,{cmd:"copy",sheetname},sheet.name());
		}

		function paste() {
//			var sheet = $scope.spread.getActiveSheet();
//			sheet.isPaintSuspended(true);
//			$.wijmo.wijspread.SpreadActions.paste.call(sheet);
//			sheet.isPaintSuspended(false);
			var spread = $scope.spread;
			var sheet = $scope.spread.getActiveSheet();
			var commandManager = spread.commandManager();
			commandManager.execute({cmd:"paste",sheetName:sheet.name()})

//			sheet.isPaintSuspended(true);
//
//			var commandManager = spread.commandManager();
//			commandManager.paste;
//			
//			sheet.isPaintSuspended(false);


		}

		function onCut() {
			$scope.$on("ClipboardController:cut", function() {
				$scope.cut();
			});
		}

		function onCopy() {
			$scope.$on("ClipboardController:copy", function() {
				$scope.copy();
			});

		}

		function onPaste() {
			$scope.$on("ClipboardController:paste", function() {
				$scope.paste();
			});
		}

	}

})();