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
		.controller('coreApp.sheetEditorController', SheetEditorController);

	/* @ngInject */
	function SheetEditorController($scope, $timeout, CoreCommonsService) {
		var sheets = $scope.sheets;
		var editedSheet, editedName;

		$scope.onAllSheetsShow = onAllSheetsShow;
		$scope.insertRow = insertRow;
		$scope.deleteRow = deleteRow;
		$scope.moveRow = moveRow;
		$scope.editRow = editRow;
		$scope.saveRow = saveRow;
		$scope.close = close;
		$scope.ok = ok;

		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/


		function activate() {
			resetValidation();
			resetFields();
			updateIsAllSheetsShownProperty();
			initResize();
			onSheetChanging();
		}

		function resetValidation() {
			$scope.validation = "";
		}

		function resetFields() {
			$scope.editedRowIndex = -1;
			editedSheet = null;
			editedName = null;
		}

		function initResize() {
			var modalDialog = $(".sheet-editor").closest(".modal-dialog");
			var elementToResize = $(".elementToResize");
			var additionalElementsToCalcResize = [$(".getToCalc1"), 10];
			CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
			$timeout(function() {
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
			}, 0);
		}

		function manageNewSheetName() {
			var availableNumber = sheets.length;
			var sheetName;
			do {
				availableNumber++;
				sheetName = "Sheet" + availableNumber;
			} while (CoreCommonsService.findElementByKey(sheets, sheetName, 'name') !== null);
			return sheetName;
		}

		function isValid(sheet) {
			var index = sheets.indexOf(sheet);
			var restSheets = angular.copy(sheets);
			restSheets.splice(index, 1);
			return CoreCommonsService.findElementByKey(restSheets, sheet.name, 'name') === null;
		}

		function backChanges() {
			if (editedSheet !== null) {
				editedSheet.name = editedName;
			}
			resetFields();
		}

		function updateIsAllSheetsShownProperty() {
			$scope.isAllSheetsShown = true;
			for (var i = 0; i < sheets.length; i++) {
				if (!sheets[i].isVisible) {
					$scope.isAllSheetsShown = false;
					break;
				}
			}
		}

		function onAllSheetsShow() {
			$scope.isAllSheetsShown = !$scope.isAllSheetsShown;
			for (var i = 0; i < sheets.length; i++) {
				sheets[i].isVisible = $scope.isAllSheetsShown;
			}
		}

		function insertRow() {
			resetValidation();
			backChanges();
			var newName = manageNewSheetName();
			var sheet = {
				name: newName,
				originalName: null,
				isVisible: false
			};
			sheets.push(sheet);
		}

		function deleteRow(index) {
			resetValidation();
			backChanges();
			sheets.splice(index, 1);
		}

		function moveRow(index, direction) {
			resetValidation();
			backChanges();
			var sheet = sheets[index];
			sheets.splice(index, 1);
			sheets.splice(index + direction * 1, 0, sheet);
		}

		function editRow(index) {
			if ($scope.editedRowIndex !== index) {
				backChanges();
			}
			resetValidation();
			$scope.editedRowIndex = index;
			var sheet = sheets[index];
			editedName = sheet.name;
			editedSheet = sheet;
		}

		function saveRow(index) {
			var sheet = sheets[index];
			if (isValid(sheet) === false) {
				sheet.name = editedName;
				$scope.validation = "Duplicates of sheet name are not allowed.";
			} else {
				resetValidation();
			}
			resetFields();
		}

		function close() {
			$scope.modal.dismiss('cancel');
		}

		function ok() {
			$scope.modal.close(sheets);
		}

		function onSheetChanging() {
			$scope.$watch("sheets", function() {
				updateIsAllSheetsShownProperty();
			}, true);
		}
	}

})();