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
        .service('SpreadSheetService', SpreadSheetService);

    /* @ngInject */
    function SpreadSheetService($rootScope, $http, ContextVariablesService, DateService) {

        var self = this;
        var ExcelDatas;

        // data from database
        self.workbook = null;
        self.userReadOnlyAccess = true;

        // data from frontend
        self.spreadSheet = null;

        self.isUndoDisabled = true;
        self.isRedoDisabled = true;
        self.isRecalculateDisabled = true;
        self.isManageNotesDisabled = true;
        self.userReadOnlyAccess = true;
        self.cellPosition = {};

        self.selectedDimension = [];
        self.dataType = null;
        self.isSpreadWasChanged = false;

        self.blockExcelView = blockExcelView;
        self.toggleHeaders = toggleHeaders;
        self.isWorkbookCanBeRecalculated = isWorkbookCanBeRecalculated;
        self.saveWorkbook = saveWorkbook;
        self.getWorkbook = getWorkbook;
        self.setWorkbook = setWorkbook;
        self.getUserReadOnlyAccess = getUserReadOnlyAccess;
        self.setUserReadOnlyAccess = setUserReadOnlyAccess;
        self.getSpreadSheet = getSpreadSheet;
        self.setSpreadSheet = setSpreadSheet;
        self.setIsRecalculateDisabled = setIsRecalculateDisabled;
        self.setIsManageNotesDisable = setIsManageNotesDisable;
        self.setSpreadWasChanged = setSpreadWasChanged;
        self.getSpreadWasChanged = getSpreadWasChanged;
        self.getActiveSheet = getActiveSheet;
        self.setCellPosition = setCellPosition;
        self.getCellPosition = getCellPosition;
        self.addEventsForEditing = addEventsForEditing;
        self.undo = undo;
        self.redo = redo;

        var getWorksheetsWithChangedCells = getWorksheetsWithChangedCells;
        var nextSheet = nextSheet;
        var prevSheet = prevSheet;

        /************************************************** IMPLEMENTATION *************************************************************************/

        /**
         * Blocks all functions related to editing
         */
        function blockExcelView() {
            // block adding new worksheet
            self.spreadSheet.options.newTabVisible = (false);

            // block capability of modifying cells
            self.spreadSheet.options.canUserDragDrop = (false);
            self.spreadSheet.options.canUserDragFill=(false);

            // block editing cell in form
            for (var i = 0; i < self.spreadSheet.getSheetCount(); i++) {
                var sheet = self.spreadSheet.getSheet(i);
                sheet.options.allowCellOverflow = (true);
                sheet.options.isProtected = (true);
            }
        }

        function toggleHeaders(areHeadingsVisible) {
            for (var i = 0; i < self.spreadSheet.getSheetCount(); i++) {
                var sheet = self.spreadSheet.getSheet(i);
//                sheet.setRowHeaderVisible(areHeadingsVisible);
//                sheet.setColumnHeaderVisible(areHeadingsVisible);
                sheet.options.rowHeadervisible = areHeadingsVisible;
                sheet.options.colHeaderVisible = areHeadingsVisible;
            }
        }

        /**
         * Checks if workbook could be recalculated (locked, non-editable)
         */
        function isWorkbookCanBeRecalculated() {
            var workbook = self.workbook;
            var isProtected = self.userReadOnlyAccess;

            //if in workbook properties we have properties which blocks spreadsheet
            isProtected = isProtected || (workbook !== undefined && workbook.properties !== undefined && workbook.properties.PROTECTED === 'true');

            //if first dimension isn't leaf, we block to recalculate
            isProtected = isProtected || $rootScope.selectedDimensions[0].leaf === false;

            self.setIsRecalculateDisabled(isProtected);
            return isProtected;
        }

        /**
         * Save any changes in cells with outputmapping in workbook and other cells related to them
         * @return {[type]} [description]
         */
        function saveWorkbook() {
            var url = $BASE_PATH + 'reviewBudget/updateWorkbookData/model/' + MODEL_ID + '/topNode/' + TOP_NODE_ID;
            var workbookToUpdate = {
                budgetCycleId: BUDGET_CYCLE_ID,
                selectedDimension: ContextVariablesService.selectedDimension,
                dataType: ContextVariablesService.dataType,
                properties: self.workbook.properties,
                contextVariables: ContextVariablesService.contextVariables,
                worksheets: getWorksheetsWithChangedCells()
            };
            $http.put(url, workbookToUpdate).success(function(response) {
                $rootScope.$broadcast('veil:hide');
                self.setSpreadWasChanged(false);

                // Set last information
                angular.element('#veil-informations').find('h1').text('Loading data, please wait...');

                //Send reload message
                $rootScope.$broadcast('veil:show');
                $rootScope.$broadcast('spreadSheetController:loadSpreadSheet', {});
            }).error(function() {
                $rootScope.$broadcast('veil:hide');
            });
        }

        function getWorkbook() {
            return self.workbook;
        }

        function setWorkbook(workbook) {
            $rootScope.workbook = workbook; //TODO do wywalenia

            self.workbook = workbook;
            $rootScope.$broadcast('SpreadSheetService:dataUpdated');
        }

        function getUserReadOnlyAccess() {
            return self.userReadOnlyAccess;
        }

        function setUserReadOnlyAccess(userReadOnlyAccess) {
            $rootScope.userReadOnlyAccess = userReadOnlyAccess; //TODO do wywalenia

            self.userReadOnlyAccess = userReadOnlyAccess;
            $rootScope.$broadcast('SpreadSheetService:dataUpdated');
        }

        function getSpreadSheet() {
            return self.spreadSheet;
        }

        function setSpreadSheet(spreadSheet) {
            self.spreadSheet = spreadSheet;
            spreadSheet.options.allowContextMenu = false;

            $rootScope.$broadcast('SpreadSheetService:dataUpdated');
        }

        function setIsRecalculateDisabled(state) {
            self.isRecalculateDisabled = state;
            //$rootScope.$broadcast('SpreadSheetService:IsRecalculateDisabled', state);
            $rootScope.$broadcast('SpreadSheetService:dataUpdated');
        }

        function setIsManageNotesDisable(state) {
            self.isManageNotesDisabled = state;
            $rootScope.$broadcast('SpreadSheetService:IsManageNotesDisabled', state);
        }

        function setSpreadWasChanged(state) {
            if (self.isRecalculateDisabled === true) {
                return;
            }

            self.isUndoDisabled = !state; //prev false
            self.isRedoDisabled = true;
            self.isSpreadWasChanged = state;
            $rootScope.$broadcast('SpreadSheetService:isSpreadWasChanged', state);

            function confirmExit() {
                return "";
            }

            if (state) {
                window.onbeforeunload = function() {
                    return "Do you really want to close the window? You have unsaved changes!";
                };
            } else {
                window.onbeforeunload = function() {
                    return null;
                };
            }
        }

        function getSpreadWasChanged() {
            return self.isSpreadWasChanged;
        }

        function getActiveSheet() {
            return self.spreadSheet.getActiveSheet();
        }

        function setCellPosition(row, col) {
            self.cellPosition = {
                row: row,
                col: col
            };
        }

        function getCellPosition() {
            return self.cellPosition;
        }

        function addEventsForEditing() {
            self.spreadSheet.bind(GC.Spread.Sheets.Events.CellChanged, function(e, info) {
                var cell = self.spreadSheet.getActiveSheet().getCell(info.row, info.col);
                self.setSpreadWasChanged(true);
            });

            self.spreadSheet.bind(GC.Spread.Sheets.Events.ActiveSheetChanged, function(sender, args) {
                //Hide all notes
                angular.element('#cell-note').hide();
            });
            //by arnold 
            self.spreadSheet.bind(GC.Spread.Sheets.Events.SelectionChanged, function (e, info) {  
            	 //alert("Name (" + info.sheetName + ")");
                var json = self.spreadSheet.getActiveSheet().toJSON();

                var selection = self.spreadSheet.getActiveSheet().getSelections();
                drawSelection(json,selection);

            });

            // Chrome moving through tabs workaround
            angular.element(window).on('keyup', function(event) {
                if (event.altKey && event.keyCode === 33) {
                    nextSheet(self.spreadSheet.getActiveSheetIndex());
                    event.preventDefault();
                }
                if (event.altKey && event.keyCode === 34) {
                    prevSheet(self.spreadSheet.getActiveSheetIndex());
                    event.preventDefault();
                }
            });
        }

        /**
         * Performs a redo of the most recently undone edit or action.
         */
        function undo() {
            var activeSheet = self.spreadSheet.getActiveSheet();
            var undoManager = activeSheet.undoManager();
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
            self.isUndoDisabled = undoManager.canUndo() ? false : true;
            self.isRedoDisabled = undoManager.canRedo() ? false : true;
        }
        /**
         * Performs an undo of the most recent edit or action.
         */
        function redo() {
            var activeSheet = self.spreadSheet.getActiveSheet();
            var undoManager = activeSheet.undoManager();
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
            self.isUndoDisabled = undoManager.canUndo() ? false : true;
            self.isRedoDisabled = undoManager.canRedo() ? false : true;
        }

        /************************************************** PRIVATE MEMBERS *************************************************************************/

        function getWorksheetsWithChangedCells() {
            var worksheetsToUpdate = [];
            var spreadSheet = self.spreadSheet;

            angular.forEach(self.workbook.worksheets, function(workbookWorksheet, index) {
                var worksheetToUpdate = {};

                // copy properties
                worksheetToUpdate.properties = workbookWorksheet.properties;
                worksheetToUpdate.name = workbookWorksheet.name;

                // set changed cells in Excel view
                var changedCells = [];
                var worksheet = self.spreadSheet.getSheet(index);

                angular.forEach(workbookWorksheet.cells, function(cell) {
                    var value = worksheet.getCell(cell.row, cell.column).value();

                    if (cell.outputMapping !== null) {
                        var changedCell = false;

                        if (value instanceof Date) { // parse object to string with formatting (yyyy/mm/dd)
                            value = DateService.parseDate(value);
                        }

                        if (cell.text !== null) {
                            if (cell.text != value) {
                                if (!value) {
                                    value = "";
                                }
                                changedCell = true;
                            }
                        } else if (cell.value !== null) {
                            if (cell.value != value) {
                                if (!value) {
                                    value = 0;
                                }
                                changedCell = true;
                            }
                        } else if (cell.date !== null) {
                            if (cell.date != value) {
                                if (!value) {
                                    value = 0;
                                }
                                changedCell = true;
                            }
                        } else if (value !== null) {
                            if (worksheet.getCell(cell.row, cell.column).formatter() == "@") {
                                if (!value) {
                                    value = "";
                                }
                            } else {
                                if (!value) {
                                    value = 0;
                                }
                            }
                            changedCell = true;
                        }
                        if (changedCell) {
                            var cellChanged = {
                                row: cell.row,
                                column: worksheet.getValue(0, cell.column, GC.Spread.Sheets.SheetArea.colHeader),
                                value: value,
                                outputMapping: cell.outputMapping
                            };
                            changedCells.push(cellChanged);
                        }
                    }
                });

                worksheetToUpdate.cells = changedCells;
                worksheetsToUpdate.push(worksheetToUpdate);
            });
            console.log();
            return worksheetsToUpdate;
        };

        function nextSheet(currentIndex) {
            if (currentIndex < self.spreadSheet.getSheetCount() - 1) {
                var nextIndex = currentIndex + 1;
                if (self.spreadSheet.getSheet(nextIndex).visible()) {
                    self.spreadSheet.setActiveSheetIndex(nextIndex);
                } else {
                    nextSheet(nextIndex);
                }
            }
        };

        function prevSheet(currentIndex) {
            if (currentIndex > 0) {
                var prevIndex = currentIndex - 1;
                if (self.spreadSheet.getSheet(prevIndex).visible()) {
                    self.spreadSheet.setActiveSheetIndex(prevIndex);
                } else {
                    prevSheet(prevIndex);
                }
            }
        };
        function drawSelection(json,selection) {
            var sheet = json;
            if(sheet.name!="Evaluation Version"){
                ExcelDatas = sheet.data.dataTable;
                var colStart = selection[0].col;
                var rowStart = selection[0].row;
                var selectionColLen = selection[0].colCount;
                var selectionRowLen = selection[0].rowCount;
                var datas = [];
                for(var j=0;j<selectionColLen;j++){
                    var temp={data:[]};
                    datas.push(temp)
                }
                for(var i = rowStart ; i<rowStart+selectionRowLen;i++){
                    for(var j=colStart;j<colStart+selectionColLen;j++){
                        if(ExcelDatas[i]!=undefined&&ExcelDatas[i]!=""){
                            if(ExcelDatas[i][j]!=undefined&&ExcelDatas[i][j]!=""){
                                if(ExcelDatas[i][j].value!=undefined&&ExcelDatas[i][j].value!="") {
                                    datas[j-colStart].data.push(ExcelDatas[i][j].value);
                                }
                            }
                        }
                    }
                }
                drawChart(datas);
            }
        }
        
        function drawChart(datas){
        	var type = $("#chartType").val();
//        	type="bar";
        	var dimention = $("#dimention").val();
//        	dimention = "2d";
        	if(dimention=="2d"){
            	var chart = 
                    Highcharts.chart('container', {
                        title: {
                            text: 'High Chart Graph',
                            x: -20 //center
                        },
                        chart:{
                        	type:type//line,column,area,spline
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'middle',
                            borderWidth: 0
                        },
                        series: datas,
                        plotOptions: {
                            series: {
                                animation: false
                            }
                        }
                    });
        	}else{
            	var chart = new
                    Highcharts.chart({
                        title: {
                            text: 'High Chart Graph',
                            x: -20 //center
                        },
                        chart:{
                        	renderTo:"container",
                        	type:type,//line,column,area,spline
                            options3d: {
                                enabled: true,
                                alpha: 15,
                                beta: 15,
                                depth: 50,
                                viewDistance: 25
                            }
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'middle',
                            borderWidth: 0
                        },
                        series: datas,
                        plotOptions: {
                            column:{
                            	depth:25
                            }
                        }
                    });
        	}
        }
    }
})();