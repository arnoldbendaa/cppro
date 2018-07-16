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
        .service('ExcelFormService', ExcelFormService);

    /* @ngInject */
    function ExcelFormService($rootScope, $http, $window, SpreadSheetService,
        ContextVariablesService, CellService, ProfileService, validationService) {

        var self = this;

        self.init = init;
        self.spreadSheet = null;

        function init() {
            $rootScope.$broadcast('veil:show');
            // Set last information
            var dim0Id = ContextVariablesService.dim0Id;
            var dim1Id = ContextVariablesService.dim1Id;
            var dim2Id = ContextVariablesService.dim2Id;
            var dataType = ContextVariablesService.dataType;

            var profile = ProfileService.getSelectedProfile();
            var url = $BASE_PATH + 'reviewBudget/fetchReviewBudget?topNodeId=' + TOP_NODE_ID + '&modelId=' + MODEL_ID + '&budgetCycleId=' + BUDGET_CYCLE_ID + '&dataEntryProfileId=' + profile.profileId + '&dim0=' + dim0Id + '&dim1=' + dim1Id + '&dim2=' + dim2Id + '&dataType=' + dataType;
            //var url = '/cppro/reviewBudget/fetchReviewBudget?topNodeId=186010&modelId=56181&budgetCycleId=79400&dataEntryProfileId=104345&dim0=186010&dim1=85830&dim2=85857&dataType=AQ';

            $http.post(url).success(function(response) {

                var spreadJSON = JSON.parse(response.jsonForm);
                self.spreadSheet = SpreadSheetService.spreadSheet;
                self.spreadSheet.fromJSON(spreadJSON.spread);

                $rootScope.selectedDimensions = ContextVariablesService.selectedDimension = response.selectedDimension;
                $rootScope.selectedDataType = ContextVariablesService.dataType = response.dataType;

                var workbook = response.workbook;
                SpreadSheetService.setUserReadOnlyAccess(response.userReadOnlyAccess);
                SpreadSheetService.setWorkbook(workbook);
                ContextVariablesService.setContextVariables(response.contextVariables);

                setUpExcelView();
                $rootScope.$broadcast('veil:hide', {});

                if (workbook.valid === false) {
                    var errmsg = [];
                    errmsg = validationService.getValidationErrors(workbook);
                    if (errmsg.length > 0) {
                        validationService.showErrors(errmsg);
                    }
                }
            });

        }

        $rootScope.$on('SpreadSheetService:dataUpdated', function(event, args) {});


        /************************************************** PRIVATE MEMBERS *********************************************************************/


        /**
         * Sets up excel view using data from DB
         */
        function setUpExcelView() {
            self.spreadSheet.options.newTabVisible = (false);
            self.spreadSheet.options.tabStripVisible = (true);
            self.spreadSheet.options.grayAreaBackColor = ("#FCFDFD");

            SpreadSheetService.blockExcelView();
            SpreadSheetService.toggleHeaders(false);
            SpreadSheetService.isWorkbookCanBeRecalculated();

            setUpCells();
            addEventsForEditing();
        };

        /**
         * Sets up cell values from JSON workbook.
         */
        function setUpCells() {
            var worksheets = SpreadSheetService.workbook.worksheets;
            var sheet = {};

            self.spreadSheet.suspendPaint ();
            self.spreadSheet.suspendEvent();
            for (var i = 0; i < worksheets.length; i++) {
                sheet = self.spreadSheet.getSheetFromName(worksheets[i].name);
                if (sheet) {
                    sheet.suspendPaint ();
                    sheet.suspendCalcService();

                    var cells = worksheets[i].cells;
                    for (var c = 0; c < cells.length; c++) {
                        var cell = cells[c];
                        //sheet.getColumn(cell.column).visible(true); // for debug

                        // clear formula to see new text value
                        var currentCell = sheet.getCell(cell.row, cell.column);
                        currentCell.formula("");
                        currentCell.cellType(CellService.buildIOCellType(cell));
                        // Adding tag to spread cell if not exist
                        if (cell.tags !== undefined && cell.tags !== null) {
                            currentCell.tag().tags = cell.tags;
                        }

                        // if (CellService.ifCellHasInputMapping(currentCell)) {
                        //     currentCell.backColor("pink");
                        // }
                        // if (CellService.ifCellHasOutputMapping(currentCell)) {
                        //     currentCell.backColor("lightgreen");
                        // }
                        
                        //console.log(cell);
                        
                        var canEdit = (CellService.ifCellHasOutputMapping(currentCell) === true) && (SpreadSheetService.isRecalculateDisabled === false);
                        if (canEdit) {
                            currentCell.locked(false);
                        }

                        if (cell.date !== null) {
                            var dateStr = cell.date;
                            var arr = dateStr.split('/');
                            if (sheet.getCell(cell.row, cell.column).formatter() === undefined || sheet.getCell(cell.row, cell.column).formatter() == "General") {
                                sheet.getCell(cell.row, cell.column).formatter("d-mmm-yy;;");
                            }
                            sheet.setValue(cell.row, cell.column, new Date(arr[2], parseInt(arr[1]) - 1, arr[0]));
                        } else if (cell.text !== null) {
                            sheet.setValue(cell.row, cell.column, cell.text);
                        } else if (cell.formula !== null) {
                            try {
                                sheet.setFormula(cell.row, cell.column, cell.formula);
                            } catch (exception) {
                                sheet.setFormula(cell.row, cell.column, "");
                                console.log("invalid formula : " + cell.formula);
                            }
                        } else if (cell.value !== null) {
                            //"d\-mmm\-yy;;",  bad or unnecessary escaping???
                            if (sheet.getCell(cell.row, cell.column).formatter() == "@" || sheet.getCell(cell.row, cell.column).formatter() == "d\-mmm\-yy;;") {
                                // text value
                                sheet.setText(cell.row, cell.column, cell.value + "");
                            } else {
                                // numeric value
                                sheet.setValue(cell.row, cell.column, cell.value);
                            }
                        } else {
                            if (sheet.getCell(cell.row, cell.column).formatter() == "@") {
                                // text value
                                sheet.setText(cell.row, cell.column, "");
                            } else {
                                // numeric value
                                sheet.setValue(cell.row, cell.column, 0);
                            }
                        }
                    }
                }
            }

            for (var j = 0; j < worksheets.length; j++) {
                var sheet = self.spreadSheet.getSheet(j);
                if (sheet) {
                    sheet.options.frozenlineColor = ("rgba(255, 255, 255, 0)"); // hide frozen line
//                    sheet.setGridlineOptions({
//                        showHorizontalGridline: false
//                    }); // hide horizontal grid lines
//                    sheet.setGridlineOptions({
//                        showVerticalGridline: false
//                    }); // hide vertical grid lines
                    sheet.options.gridline = {showHorizontalGridline:false,showVerticalGridline:false};
                    sheet.resumeCalcService();
                    sheet.resumePaint();
                }
            }

            self.spreadSheet.resumePaint();
            self.spreadSheet.resumeEvent();
            self.spreadSheet.repaint();
        };

        /**
         * Adds events to cells
         */
        function addEventsForEditing() {
            SpreadSheetService.addEventsForEditing();

            self.spreadSheet.bind(GC.Spread.Sheets.Events.RangeChanged, function(e, info) {
                if (CellService.ifRangeHasOutputMapping(info) && SpreadSheetService.isRecalculateDisabled === false) {
                    SpreadSheetService.setSpreadWasChanged(true);
                }
            });

            // Chrome moving through tabs workaround
            angular.element(window).on('keyup', function(event) {
                var cell = CellService.getSelectedCell();
                if (cell && (SpreadSheetService.isRecalculateDisabled === false) && (CellService.ifCellHasOutputMapping(cell) === true)) {
                    if (event.keyCode === 113) { // F2
                        var spread = SpreadSheetService.getSpreadSheet();
                        var sheet = spread.getActiveSheet();
                        sheet.startEdit(true);
                    } else if (event.keyCode === 8) { // Backspace
                        SpreadSheetService.setSpreadWasChanged(true);
                    } else if (event.keyCode === 46) { // Delete
                        SpreadSheetService.setSpreadWasChanged(true);
                    }
                }
            });


        };
    }
})();