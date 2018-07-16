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
        .controller('DataEditorController', DataEditorController);

    /* @ngInject */
    function DataEditorController($scope, $compile, $timeout, PageService, CoreCommonsService, DataEditorService, FinanceCubesPageService, DataTypesPageService) {
     
        
        $scope.financeCubes = [];
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_dataEditor";
        // try to resize and sort colums based on the cookie
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.dataTypes = [];
        var selectedFinanceCubesIds = [];
        var selectedModelIds = [];
        var modelIdForEditedElement;
        var rows = {};
        var originalRow;
        var delay;
        $scope.selectedFinanceCubes = [];
        $scope.selectedCostCenters = [];
        $scope.selectedExpenseCodes = [];
        $scope.selectedDataTypes = [];
        $scope.periodFrom = new Date();
        $scope.periodTo = new Date();
        $scope.isAnyRowsEdited = false;
        $scope.isEditDisabled = true;
        $scope.ctx = {};
        $scope.validation = {};
        $scope.onChange = onChange;
        $scope.display = display;
        $scope.exportXls = exportXls;
        activate();
        
        /************************************************** IMPLEMENTATION *************************************************************************/
     
        function activate(){
            
            PageService.setCurrentPageService(DataEditorService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            $scope.originalFinanceCubes = FinanceCubesPageService.getFinanceCubes();
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.originalDataTypes = DataTypesPageService.getDataTypes();
            resetData();
            $scope.costCenters = DataEditorService.getCostCenters();
            $scope.expenseCodes = DataEditorService.getExpenseCodes();
            $scope.loadingManager = DataEditorService.getLoadingManager();
            rows = DataEditorService.getRows();
            $scope.ctx = {
                    flex: null,
                    data: rows
                };
            $scope.validation = {
                    financeCubes: "",
                    costCenters: "",
                    expenseCodes: "",
                    dataTypes: "",
                    calendarPeriods: ""
            };
            clearValidation();
            $scope.$parent.onFilterWordChange("");
        }
        
        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        };
        
        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        };
        
        /**
         * Reset data in angular service to initial values
         */
        function resetData() {
            DataEditorService.resetRows();
            DataEditorService.resetFilters();
            DataEditorService.resetLoadingManager();
        }

        /**
         * Handles any changes in selected finance cube and manage dimensions (cost centers, expense codes) for selected finance cubes.
         */
        function onChange() {
            $scope.loadingManager.isRowsLoaded = false;
            $scope.loadingManager.isCostCenterLoaded = false;
            $scope.loadingManager.isExpenseCodeLoaded = false;

            $scope.$apply("loadingManager");
            // $scope.selectedCostCenters = [];
            // $scope.selectedExpenseCodes = [];

            manageSelectedIds();
            $timeout.cancel(delay);
            delay = $timeout(function() {
                DataEditorService.getCostsCentersAndExpenseCodes(selectedModelIds);
            }, 750);
        };

        /**
         * Manage list of finance cube visIds which will be shown in multiple selector
         */
        function manageFinanceCubes() {
            angular.forEach($scope.originalFinanceCubes.sourceCollection, function(financeCube) {
                $scope.financeCubes.push(financeCube.financeCubeVisId);
            });
        }
        
        /**
         * Manage list of data type visIds which will be shown in multiple selector
         */
        function manageDataTypes() {
            angular.forEach($scope.originalDataTypes.sourceCollection, function(dataType) {
                $scope.dataTypes.push(dataType.dataTypeVisId);
            });
        }
        
        /**
         * Manage list of finance cube visIds which will be shown in multiple selector
         */
        function manageFinanceCubes() {
            angular.forEach($scope.originalFinanceCubes.sourceCollection, function(financeCube) {
                $scope.financeCubes.push(financeCube.financeCubeVisId);
            });
        }
        
        /**
         * Manage selected model ids and finance cubes ids. Selected finance cubes are necessary to retrieve data rows for selected dimensions.
         * Selected model ids are necessary to retrieves dimensions (cost centers, expense codes) during uploading data rows from file.
         */
        function manageSelectedIds() {
            selectedModelIds.length = 0;
            selectedFinanceCubesIds.length = 0;
            for (var i = 0; i < $scope.selectedFinanceCubes.length; i++) {
                var financeCube = CoreCommonsService.findElementByKey($scope.originalFinanceCubes.sourceCollection, $scope.selectedFinanceCubes[i], 'financeCubeVisId');
                selectedFinanceCubesIds.push(financeCube.financeCubeId);
                selectedModelIds.push(financeCube.model.modelId);
            }
        }

        /**
         * Clear all validation for any filters
         */
        function clearValidation() {
            angular.forEach($scope.validation, function(message, field) {
                $scope.validation[field] = "";
            });
        }

        /**
         * Check if all filters (which are used to display data rows) are selected in proper way.
         */
        function isValidToDisplay(data) {
            var isValid = true;

            if (data.financeCubeIds.length === 0) {
                isValid = false;
                $scope.validation.financeCubes = "You haven't selected any Finance Cubes.";
            }

            if (data.costCenters.length === 0) {
                isValid = false;
                $scope.validation.costCenters = "You haven't selected any Cost Centers.";
            } else if (data.costCenters.length >= 1000) {
                isValid = false;
                $scope.validation.costCenters = "You can't selected more than 1000 Cost Centers. All option is available.";
            }

            if (data.expenseCodes.length === 0) {
                isValid = false;
                $scope.validation.expenseCodes = "You haven't selected any Expense Codes.";
            } else if (data.expenseCodes.length >= 1000) {
                isValid = false;
                $scope.validation.expenseCodes = "You can't selected more than 1000 Expense Codes. All option is available.";
            }

            if (data.dataTypes.length === 0) {
                isValid = false;
                $scope.validation.dataTypes = "You haven't selected any Data Types.";
            }

            // if (data.calendarPeriods.length == 0) {
            // }
            return isValid;
        }

        /**
         * Displays data rows for filtered data (all selected data in our filters)
         */
        function display() {
           var data = getData(data);
            $scope.isEditDisabled = false;
            $scope.isAnyRowsEdited = false;

            if (isValidToDisplay(data)) {
                $scope.loadingManager.isRowsLoading = true;
                $scope.loadingManager.isRowsLoaded = false;

                $timeout.cancel(delay);
                delay = $timeout(function() {
                    DataEditorService.display(data);
                }, 750);
            }
        };
        function exportXls(){
        	var data = getData(data);
        	var dataTypeLength = data.dataTypes.length;
        	if(dataTypeLength>1||dataTypeLength<1){
        		swal("Sorry", "Please select only 1 data type.", "warning");
        		return;
        	}
            $scope.isEditDisabled = false;
            $scope.isAnyRowsEdited = false;
            if (isValidToDisplay(data)) {
                $scope.loadingManager.isRowsLoading = true;
                $scope.loadingManager.isRowsLoaded = false;

                $timeout.cancel(delay);
                delay = $timeout(function() {
                    DataEditorService.exportXls(data);
                }, 750);
            }

        }
        function getData(data){
            var data = {
                    financeCubeIds: selectedFinanceCubesIds,
                    costCenters: null,
                    expenseCodes: null,
                    dataTypes: null,
                    fromYear: $scope.periodFrom.getFullYear(),
                    fromPeriod: $scope.periodFrom.getMonth() + 1,
                    toYear: $scope.periodTo.getFullYear(),
                    toPeriod: $scope.periodTo.getMonth() + 1
                };

            if ($scope.selectedDataTypes.length == 0 || $scope.selectedDataTypes.indexOf("All") != -1) {
                data.dataTypes = $scope.dataTypes;
            } else if ($scope.selectedDataTypes.length > 0) {
                data.dataTypes = $scope.selectedDataTypes
            }

            if ($scope.selectedCostCenters.length == 0 || $scope.selectedCostCenters.indexOf("All") != -1) {
                data.costCenters = ["All"];
            } else if ($scope.selectedCostCenters.length > 0) {
                data.costCenters = $scope.selectedCostCenters;
            }

            if ($scope.selectedExpenseCodes.length == 0 || $scope.selectedExpenseCodes.indexOf("All") != -1) {
                data.expenseCodes = ["All"];
            } else if ($scope.selectedExpenseCodes.length > 0) {
                data.expenseCodes = $scope.selectedExpenseCodes;
            }
            return data;
        }
        /**
         * Handles event when selected row in flex grid is changed
         */
        function onSelectionChanged(event) {
            var flex = $scope.ctx.flex;
            var selectedRow = flex.collectionView ? flex.collectionView.currentItem : null;
            originalRow = angular.copy(selectedRow);
        }

        /**
         * Handles event when user has finished editing.
         */
        function onRowEditEnding(event) {
            var editedRow = $scope.ctx.data.currentEditItem;
            var isValid = isRowValid(editedRow);

            if (!isValid) {
                $scope.ctx.data.cancelEdit();
                return;
            } else {
                $scope.isAnyRowsEdited = true;
                editedRow.modelId = modelIdForEditedElement;
                //editedRow.dataEditorRowId = "financeCubeId=" + editedRow.financeCubeId + ",dim0=" + editedRow.dim0 + ",dim1=" + editedRow.dim1 + ",year=" + editedRow.year + ",period=" + editedRow.period + ",dataType=" + editedRow.dataType;
                editedRow.dataEditorRowId = "" + editedRow.financeCubeId + "," + editedRow.dim0 + "," + editedRow.dim1 + "," + editedRow.year + "," + editedRow.period + "," + editedRow.dataType;
                $scope.$apply('isAnyRowsEdited');
            }
        }

        /**
         * Validates edited rows. Check if proper dimensions/data type/period/row are filled in.
         */
        function isRowValid(editedRow) {
            var collection = $scope.originalFinanceCubes.sourceCollection;
            var financeCubeForEditedElement = CoreCommonsService.findElementByKey(collection, editedRow.financeCubeId, 'financeCubeId');
            modelIdForEditedElement = financeCubeForEditedElement.model.modelId;

            var property = whichPropertyChanged(originalRow, editedRow);
            var isValid = true;
            switch (property) {
                case "costCenter":
                    isValid = isValidCostCenter(editedRow, modelIdForEditedElement);
                    break;
                case "dim0":
                    isValid = isValidDim0(editedRow, modelIdForEditedElement);
                    break;
                case "expenseCode":
                    isValid = isValidExpenseCode(editedRow, modelIdForEditedElement);
                    break;
                case "dim1":
                    isValid = isValidDim1(editedRow, modelIdForEditedElement);
                    break;
                case "period":
                    isValid = isValidPeriod(editedRow);
                    break;
                case "year":
                    isValid = isValidYear(editedRow);
                    break;
                case "dataType":
                    isValid = isValidDataType(editedRow);
                    break;
            }
            return isValid;
        }

        /**
         * Check which property was changed and returns name of key.
         */
        function whichPropertyChanged(orginalRow, editedRow) {
            var property = "";
            angular.forEach(orginalRow, function(value, key) {
                if (value !== editedRow[key]) {
                    property = key;
                    return;
                }
            });
            return property;
        }

        /**
         * Check if valid cost center (vis id).
         */
        function isValidCostCenter(item, modelId) {
            var modelData = DataEditorService.getAllModelDataForModel(modelId);
            if (modelData !== null) {
                for (var i = 0; i < modelData.costCenters.length; i++) {
                    var costCenter = modelData.costCenters[i];
                    if (costCenter.dimensionElementVisId == item.costCenter) {
                        item.dim0 = costCenter.dimensionElementId;
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Check if valid dim0 (cost center id).
         */
        function isValidDim0(item, modelId) {
            var modelData = DataEditorService.getAllModelDataForModel(modelId);
            if (modelData !== null) {
                for (var i = 0; i < modelData.costCenters.length; i++) {
                    var costCenter = modelData.costCenters[i];
                    if (costCenter.dimensionElementId == item.dim0) {
                        item.costCenter = costCenter.dimensionElementVisId;
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Check if valid expense code (vis id).
         */
        function isValidExpenseCode(item, modelId) {
            var modelData = DataEditorService.getAllModelDataForModel(modelId);
            if (modelData !== null) {
                for (var i = 0; i < modelData.expenseCodes.length; i++) {
                    var expenseCode = modelData.expenseCodes[i];
                    if (expenseCode.dimensionElementVisId == item.expenseCode) {
                        item.dim1 = expenseCode.dimensionElementId;
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Check if valid dim0 (expense code id).
         */
        function isValidDim1(item, modelId) {
            var modelData = DataEditorService.getAllModelDataForModel(modelId);
            if (modelData !== null) {
                for (var i = 0; i < modelData.expenseCodes.length; i++) {
                    var expenseCode = modelData.expenseCodes[i];
                    if (expenseCode.dimensionElementId == item.dim1) {
                        item.expenseCode = expenseCode.dimensionElementVisId;
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Check if valid data type (check if is stored in list of data types)
         */
        function isValidDataType(item) {
            return $scope.dataTypes.indexOf(item.dataType) != -1;
        }

        /**
         * Check if valid period (month from 1 to 12. 0 means no month selected)
         */
        function isValidPeriod(item) {
            var periods = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
            return periods.indexOf(item.period) != -1;
        }

        /**
         * Check if valid year
         */
        function isValidYear(item) {
            return item.year > 1970;
        }

        /**
         * Save all data stored in rows.
         */
        $scope.save = function() {
            var flex = $scope.ctx.flex;
            flex.isReadOnly = true;
            $scope.isEditDisabled = false;
            $scope.isAnyRowsEdited = false;
            DataEditorService.save();
        };

        /**
         * Handles click in edit button. Enable edition of data rows.
         */
        $scope.edit = function() {
            var flex = $scope.ctx.flex;
            flex.isReadOnly = false;
            $scope.isEditDisabled = true;
        };

        /**
         * Uploads data from uploaded file
         */
        $scope.upload = function(files) {
            if (files.length > 0) {
                var file = files[0];
                manageSelectedIds();
                DataEditorService.upload(file, selectedModelIds);
                $scope.isEditDisabled = false;
                $scope.isAnyRowsEdited = true;
            }
        };

        /******************************************************** CUSTOM CELLS ********************************************************/
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {

                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'index':
                        html = "" + (r + 1);
                        break;
                }

                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }

        /******************************************************** WATCHERS ********************************************************/
        $scope.$watch('ctx.flex', function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                flex.selectionMode = "Row";
                flex.headersVisibility = "Column";
                flex.isReadOnly = true;
                flex.onSelectionChanged = onSelectionChanged;
                flex.onRowEditEnding = onRowEditEnding;
                flex.itemFormatter = itemFormatter;
                var additionalElementsToCalcResize = [angular.element(".data-editor-filter"), angular.element(".data-editor-buttons")];
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie, additionalElementsToCalcResize);
                // CoreCommonsService.initializeResizeFlexGrid(flex, additionalElementsToCalcResize);
            }
        });

        $scope.$watch("originalDataTypes.sourceCollection", function() {
            if ($scope.originalDataTypes.sourceCollection.length > 0) {
                manageDataTypes();
            }
        });
        
        $scope.$watch("originalFinanceCubes.sourceCollection", function() {
            if ($scope.originalFinanceCubes.sourceCollection.length > 0) {
                manageFinanceCubes();
            }
        });
        
        $scope.$watch("originalFinanceCubes.sourceCollection", function() {
            if ($scope.originalFinanceCubes.sourceCollection.length > 0) {
                manageFinanceCubes();
            }
        });
        
        $scope.$watch('selectedFinanceCubes', function() {
            $scope.validation.financeCubes = "";
        }, true);
        $scope.$watch('selectedCostCenters', function() {
            $scope.validation.costCenters = "";
        }, true);
        $scope.$watch('selectedExpenseCodes', function() {
            $scope.validation.expenseCodes = "";
        }, true);
        $scope.$watch('selectedDataTypes', function() {
            $scope.validation.dataTypes = "";
        }, true);
    }
})();