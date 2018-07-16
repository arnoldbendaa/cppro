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
		.module('coreApp.dictionary')
		.controller('DictionaryEditorController', DictionaryEditorController);

	/* @ngInject */
	function DictionaryEditorController($scope, $timeout, DictionaryService, CoreCommonsService) {
		var editedRow, originalValue, originalDescription;

		$scope.isEditedMode = false;
		$scope.isNewRowAdded = false;

		$scope.insertRow = insertRow;
		$scope.deleteRow = deleteRow;
		$scope.editRow = editRow;
		$scope.saveRow = saveRow;
		$scope.moveRow = moveRow;

		$scope.close = close;
		$scope.ok = ok;
		$scope.importCompanies = importCompanies;
		$scope.editedDictionaries = [];

		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			resetValidation();
			resetFields();
			initResize();

			getDictionaries();
		}

		function getDictionaries() {
			DictionaryService.getDictionaries($scope.type).then(function(data) {
				$scope.dictionaries = angular.copy(data);
			});
		}

		function resetValidation() {
			$scope.validation = "";
		}

		function resetFields() {
			$scope.editedRowIndex = -1;

			editedRow = null;
			originalValue = null;
			originalDescription = null;
		}

		function initResize() {
			var modalDialog = $(".dictionary-editor").closest(".modal-dialog");
			var elementToResize = $(".elementToResize");
			var additionalElementsToCalcResize = [$(".getToCalc1"), 10];
			CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
			$timeout(function() {
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
			}, 0);
		}

		function isValid(item) {
			if (item.value === "" || item.value === null || item.value === undefined) {
				$scope.validation = "Value of item is required.";
				return false;
			}
			if (item.value.length > 50) {
				$scope.validation = "Length (" + item.value.length + ") of value must not exceed 50 characters.";
				return false;
			}
			if (item.description && item.description.length > 120) {
				$scope.validation = "Length (" + item.description.length + ") of description must not exceed 120 characters.";
				return false;
			}
			var index = $scope.dictionaries.indexOf(item);
			var restDictionaries = angular.copy($scope.dictionaries);
			restDictionaries.splice(index, 1);
			if (CoreCommonsService.findElementByKey(restDictionaries, item.value.toUpperCase(), 'value') !== null) {
				$scope.validation = "Duplicates of value (" + item.value + ") are not allowed.";
				return false;
			}
			return true;
		}

		function backChanges() {
			if ($scope.isNewRowAdded) {
				$scope.isNewRowAdded = false;
				$scope.dictionaries.splice($scope.dictionaries.length - 1, 1);
			}
			if (editedRow !== null) {
				editedRow.value = originalValue;
				editedRow.description = originalDescription;
			}
			resetFields();
		}

		function insertRow() {
			resetValidation();
			backChanges();

			editedRow = {
				key: null,
				type: null,
				value: null,
				description: null
			};
			$scope.editedRowIndex = $scope.dictionaries.length;
			$scope.dictionaries.push(editedRow);

			$scope.isNewRowAdded = true;

			$scope.isEditedMode = true;
		}

		function deleteRow(index) {
			resetValidation();
			backChanges();
			$scope.editedDictionaries.push(deletedToChanges($scope.dictionaries[index]));
			$scope.dictionaries.splice(index, 1);
		}

		function editRow(index) {
			if ($scope.editedRowIndex !== index) {
				backChanges();
			}
			resetValidation();

			$scope.editedRowIndex = index;
			editedRow = $scope.dictionaries[index];

			originalValue = editedRow.value;
			originalDescription = editedRow.description;
		}

		function saveRow(index) {
			var editedRow = $scope.dictionaries[index];

			if (isValid(editedRow) === false) {
				if ($scope.isNewRowAdded) {
					$scope.isNewRowAdded = false;
					//if (editedRow.value === null) {
					$scope.dictionaries.splice($scope.dictionaries.length - 1, 1);
					//}
				} else {
					editedRow.value = originalValue;
					editedRow.description = originalDescription;
				}
			} else {
				if ($scope.isNewRowAdded && index === $scope.dictionaries.length - 1) {
					$scope.isNewRowAdded = false;
				}
				resetValidation();
			}
			resetFields();
		}

		function moveRow(index, direction) {
			resetValidation();
			backChanges();
			var dictionary = $scope.dictionaries[index];
			$scope.dictionaries.splice(index, 1);
			$scope.dictionaries.splice(index + direction * 1, 0, dictionary);
		}

		function saveDictionaries() {
			var newKey;
			angular.forEach($scope.dictionaries, function(dictionary, i) {
				newKey = angular.lowercase(dictionary.type + "|" + dictionary.value);
				var copy = {
					oldValue: "",
					newValue: ""
				};
				if (dictionary.key !== newKey && dictionary.key !== null) {
					copy.oldValue = dictionary.key;
					copy.newValue = newKey;
					$scope.editedDictionaries.push(copy);
				}
				dictionary.rowIndex = i + 1;
			});
			DictionaryService.saveDictionaries($scope.dictionaries, $scope.type).then(function(data) {
				data.editedDictionaries = $scope.editedDictionaries;
				$scope.modal.close(data);
			});
		}

		function deletedToChanges(deletedDictionary) {
			var change = {
				oldValue: "",
				newValue: ""
			};
			change.oldValue = deletedDictionary.key;
			change.newValue = 'null';
			return change;
		}

		function importCompanies(){
		    DictionaryService.importDictionariesFromDatabase("company").then(function(dictionary){
		        if (dictionary != null){
		              getDictionaries();
		              swal("Success!", "Dictionaries has been updated", "success");
		        } else{
		            swal("Error!", "Cannot import dictionaries", "warning");
		        }
		        
		    });
		}

		function close() {
			$scope.modal.dismiss('cancel');
		}

		function ok() {
			if (editedRow !== null && isValid(editedRow) === false) {
				if ($scope.isNewRowAdded) {
					$scope.isNewRowAdded = false;
					$scope.dictionaries.splice($scope.dictionaries.length - 1, 1);
				}
			} else if (editedRow === null || isValid(editedRow)) {
				resetValidation();
				saveDictionaries();
			}
		}
	}
})();