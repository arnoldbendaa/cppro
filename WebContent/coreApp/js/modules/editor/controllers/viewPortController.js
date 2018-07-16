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
		.controller('ViewPortController', ViewPortController);

	/* @ngInject */
	function ViewPortController($scope, CoreCommonsService, ViewPortService) {
		$scope.freeze = freeze;
		$scope.unfreeze = unfreeze;

		activate();
		/////////////////////////////////////////////////////////////

		function activate() {
			onSpreadSheetsLengthChanged();
		}

		function freeze() {
			CoreCommonsService.askIfReload = true;
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			sheet.frozenRowCount(sheet.getActiveRowIndex());
			sheet.frozenColumnCount(sheet.getActiveColumnIndex());
			sheet.resumePaint();
		}

		function unfreeze() {
			CoreCommonsService.askIfReload = true;
			var sheet = $scope.spread.getActiveSheet();
			sheet.suspendPaint ();
			sheet.frozenRowCount(0);
			sheet.frozenColumnCount(0);
			sheet.frozenTrailingRowCount(0);
			sheet.frozenTrailingColumnCount(0);
			sheet.resumePaint();
		}

		function onSpreadSheetsLengthChanged() {
			$scope.$watch("spread.sheets.length", function(newLength, oldLength) {
				if ($scope.spread && newLength !== oldLength) {
					ViewPortService.manageFrozenLines($scope.spread);
				}
			});
		}

	}

})();