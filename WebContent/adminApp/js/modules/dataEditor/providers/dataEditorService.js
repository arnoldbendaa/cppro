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
        .module('adminApp.dataEditor')
        .service('DataEditorService', DataEditorService);
    
    /* @ngInject */
    function DataEditorService($http, Upload, Flash) {

		var self = this;
		var url = $BASE_PATH + 'adminPanel/dataEditor/';
		var actions = [];
		var allModelData = {};
		var costCenters = [];
		var expenseCodes = [];
		var originalRows = [];
		var workingRows = [];
		var sortByFinanceCubeVisId = new wijmo.collections.SortDescription('financeCubeVisId', true);
		var rows = new wijmo.collections.CollectionView();
		rows.sourceCollection = workingRows;
		rows.trackChanges = true;
		self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.isRefreshDisabled = true;
		//rows.sortDescriptions.push(sortByFinanceCubeVisId);
		self.getCostsCentersAndExpenseCodes = getCostsCentersAndExpenseCodes;
		self.display = display;
		self.exportXls = exportXls;
		self.upload = upload;
		self.save = save;
		self.resetRows = resetRows;
		self.resetFilters = resetFilters;
		self.resetLoadingManager = resetLoadingManager;
		self.getRows = getRows;
		self.getLoadingManager = getLoadingManager;
		self.getExpenseCodes = getExpenseCodes;
		self.getCostCenters = getCostCenters;
		self.getAllModelDataForModel = getAllModelDataForModel;
		self.getAllModelData = getAllModelData;
		self.getActions = getActions;
		
		var loadingManager = {
			isCostCenterLoaded: true,
			isExpenseCodeLoaded: true,
			isRowsLoading: false,
			isRowsLoaded: false,
			isRowsSaving: false
		};

		/**
		 * Get cost centers and expense codes for models
		 * @param  modelIds - list of model ids
		 */
		function getCostsCentersAndExpenseCodes(modelIds) {
			var modelIdsWithNoData = getModelsWithNoData(modelIds);
			if (modelIdsWithNoData.length > 0) {
				$http({
					url: url + "dimensionElements",
					method: "GET",
					params: {
						modelIds: modelIdsWithNoData
					}
				}).success(function(data) {
					for (var i = 0; i < modelIdsWithNoData.length; i++) {
						if (angular.isDefined(data[modelIdsWithNoData[i]])) {
							allModelData[modelIdsWithNoData[i]] = data[modelIdsWithNoData[i]];
						}
					}
					manageCostCentersAndExpenseCodes(modelIds);
				});
			} else {
				manageCostCentersAndExpenseCodes(modelIds);
			}
		};

		/**
		 * Get models which have no data stored in service
		 */
		function getModelsWithNoData(modelIds) {
			var ids = [];
			for (var i = 0; i < modelIds.length; i++) {
				if (angular.isDefined(allModelData[modelIds[i]]) === false) {
					ids.push(modelIds[i]);
				}
			}
			return ids;
		}

		/**
		 * Organize cost centeres and expense code in hash map which key is model id.
		 */
		function manageCostCentersAndExpenseCodes(modelIds) {
			costCenters.length = 0;
			expenseCodes.length = 0;

			var costCentersChanged = false;
			var expenseCodeChanged = false;

			for (var j = 0; j < modelIds.length; j++) {
				var modelId = modelIds[j];
				if (angular.isDefined(allModelData[modelId]) === true) {
					var costCentersForModel = allModelData[modelId].costCenters;
					var expenseCodesForModel = allModelData[modelId].expenseCodes;

					for (var i = 0; i < costCentersForModel.length; i++) {
						if (costCenters.indexOf(costCentersForModel[i].dimensionElementVisId) == -1) {
							costCenters.push(costCentersForModel[i].dimensionElementVisId);
							costCentersChanged = true;
						}
					}

					for (i = 0; i < expenseCodesForModel.length; i++) {
						if (expenseCodes.indexOf(expenseCodesForModel[i].dimensionElementVisId) == -1) {
							expenseCodes.push(expenseCodesForModel[i].dimensionElementVisId);
							expenseCodeChanged = true;
						}
					}
				}
			}

			loadingManager.isCostCenterLoaded = true;
			loadingManager.isExpenseCodeLoaded = true;

			if (costCentersChanged) {
				costCenters.sort();
			}
			if (expenseCodeChanged) {
				expenseCodes.sort();
			}
		}

		/**
		 * Retrieve data of rows for filtered data (selected finance cubes, cost centeres, expense codes, period and data type)
		 */
		function display(filteredData) {
			$http.post(url + "display", filteredData).success(function(response) {
				originalRows = angular.copy(response);
				angular.copy(originalRows, workingRows);
				rows.refresh();

				loadingManager.isRowsLoading = false;
				loadingManager.isRowsLoaded = true;
			});
		};
		function exportXls(filteredData) {
			$http.post(url + "exportXls", filteredData).success(function(response) {
				loadingManager.isRowsLoading = false;
				loadingManager.isRowsLoaded = true;

				if(response.url!="No result"){
	                var link = document.createElement("a");
	                link.href = response.url;
	                link.download = response.fileName;
	                link.target="_blank";
	                document.body.appendChild(link);
	                link.click();
	                document.body.removeChild(link);
				}else{
					swal("No Result", "There is no result!", "info");
				}

			});
		};

		/**
		 * Find row in collection and return its index
		 */
		function findRowIndexInCollection(collection, row) {
			for (var j = 0; j < collection.length; j++) {
				var r = collection[j];
				if (ifRowsAreEqual(r, row)) {
					return j;
				}
			}
			return -1;
		}

		/**
		 * Check if row exists in collection. It depends on ifRowsAreEqual function.
		 */
		function ifRowExistsInCollection(collection, row) {
			for (var j = 0; j < collection.length; j++) {
				var r = collection[j];
				if (ifRowsAreEqual(r, row)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Check if data row are equal. DataEditorRowId (merged string) contains information about dimensions and data type.
		 */
		function ifRowsAreEqual(row1, row2) {
			return row1.dataEditorRowId == row2.dataEditorRowId;
		}

		/**
		 * Update working rows with data from new rows
		 */
		function updateWorkingRows(newRows) {
			// update working rows
			for (var i = 0; i < newRows.length; i++) {
				var newRow = newRows[i];
				var index = findRowIndexInCollection(workingRows, newRow);
				if (index != -1) {
					workingRows[index].value = newRow.value;
				}
			}
		}

		/**
		 * Insert new rows in working rows from specified index
		 */
		function insertRowsToWorkingRows(newRows, fromIndex) {
			var currentIndex = fromIndex;
			for (var i = 0; i < newRows.length; i++) {
				var newRow = newRows[i];
				var index = findRowIndexInCollection(workingRows, newRow);
				if (index == -1) {
					workingRows.splice(currentIndex, 0, newRow);
					currentIndex++;
				}
			}
		}

		/**
		 * Update/Insert rows from uploaded file
		 */
		function upload(file, modelIds) {
			loadingManager.isRowsLoading = true;
			loadingManager.isRowsLoaded = false;
			loadingManager.isCostCenterLoaded = false;
			loadingManager.isExpenseCodeLoaded = false;

			Upload.upload({
				url: url + "upload",
				method: 'POST',
				file: file
			}).success(function(response) {
				if (!angular.isDefined(response.rows)) {
					self.resetLoadingManager();
					loadingManager.isRowsLoaded = true;
					return;
				}
				loadingManager.isRowsLoaded = true;
				var fromIndex = rows.currentPosition + 1;

				updateWorkingRows(response.rows);
				insertRowsToWorkingRows(response.rows, fromIndex);

				rows.moveCurrentToPosition(fromIndex - 1);
				rows.refresh();

				for (var modelId in response.map) {
					modelIds.push(modelId);
				}
				var data = response.map;
				var isAnyNewDimensions = false;
				for (var i = 0; i < modelIds.length; i++) {
					if (!angular.isDefined(allModelData[modelIds[i]])) {
						isAnyNewDimensions = true;
						allModelData[modelIds[i]] = data[modelIds[i]];
					}
				}
				manageCostCentersAndExpenseCodes(modelIds);
				if (isAnyNewDimensions === false) {
					loadingManager.isCostCenterLoaded = true;
					loadingManager.isExpenseCodeLoaded = true;
				}
				loadingManager.isRowsLoading = false;
				loadingManager.isRowsLoaded = true;
			});
		};

		/**
		 * Save data which are change in database
		 */
		function save() {
			var editedRows = manageEditedRows();

			if (editedRows.length > 0) {
				loadingManager.isRowsSaving = true;

				$http.post(url + "save", editedRows).success(function(response) {
					loadingManager.isRowsSaving = false;
					var messageToReturn;
					if (response.success) {
						originalRows = angular.copy(workingRows);

						messageToReturn = 'Rows have been updated.';
						Flash.create('success', messageToReturn);
						//$rootScope.$broadcast('BudgetCycleService:budgetCycleSaved', response);
					} else if (response.error) {
						messageToReturn = response.message.split("\n")[0];
						Flash.create('danger', messageToReturn);
						//$rootScope.$broadcast('BudgetCycleService:budgetCycleSaveError', response);
					}
				});
			}
		};

		/**
		 * Retrieves row which were edited. Working rows are compared to original rows.
		 */
		function manageEditedRows() {
			var editedRows = [];

			var tmp = [];
			// collect original rows which must have reseted value
			for (var i = 0; i < originalRows.length; i++) {
				var orginalRow = originalRows[i];
				if (!ifRowExistsInCollection(workingRows, orginalRow)) {
					orginalRow.value = null;
					editedRows.push(orginalRow);
				} else {
					tmp.push(orginalRow);
				}
			}

			// in tmp we have original rows which dimension not changed. only values could be changed.
			for (i = 0; i < workingRows.length; i++) {
				var workingRow = workingRows[i];

				var index = findRowIndexInCollection(tmp, workingRow);
				if (index != -1) {
					var tmpRow = tmp[index];
					if (tmpRow.value != workingRow.value) {
						editedRows.push(workingRow);
					}
				} else {
					editedRows.push(workingRow);
				}
			}
			return editedRows;
		}

		/**
		 * Reset collection of working and original rows. Reference is not deleted.
		 */
		function resetRows() {
			workingRows.length = 0;
			originalRows.length = 0;
		};

		/**
		 * Reset filters of cost centeres and expense codes
		 */
		function resetFilters() {
			costCenters.length = 0;
			expenseCodes.length = 0;
		};

		/**
		 * Reset loading manager to initial state
		 */
		function resetLoadingManager() {
			loadingManager.isCostCenterLoaded = true;
			loadingManager.isExpenseCodeLoaded = true;
			loadingManager.isRowsLoading = false;
			loadingManager.isRowsLoaded = false;
			loadingManager.isRowsSaving = false;
		};

		/**
		 * Retrieves data of rows
		 */
		function getRows() {
			return rows;
		};

		/**
		 * Retrieves loading manager in actual state
		 */
		function getLoadingManager() {
			return loadingManager;
		};

		/**
		 * Retrieves list of expense codes (list of strings)
		 */
		function getExpenseCodes() {
			return expenseCodes;
		};

		/**
		 * Retrieves list of cost centers (list of strings)
		 */
		function getCostCenters() {
			return costCenters;
		};

		/**
		 * Retrieves expense codes and cost centers (list of object which constains id and name of dimension) for one model
		 */
		function getAllModelDataForModel(modelId) {
			if (angular.isDefined(allModelData[modelId])) {
				return allModelData[modelId];
			}
			return null;
		};

		/**
		 * Retrieves all expense codes and cost centeres (list of object which constains id and name of dimension) segregated by model id and stored in hash map
		 */
		function getAllModelData() {
			return allModelData;
		};

		/**
		 * Retrieves actions available for data editor
		 */
		function getActions() {
			return actions;
		};
	}
})();