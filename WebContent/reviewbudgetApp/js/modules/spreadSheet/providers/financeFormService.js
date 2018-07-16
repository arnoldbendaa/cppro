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
        .service('FinanceFormService', FinanceFormService);

    /* @ngInject */
    function FinanceFormService($rootScope, $http, SpreadSheetService,
        ContextVariablesService, CellService, ProfileService) {

        var self = this;

        self.init = init;

        var sheet;
//        var numberFormatter = new GC.Spread.Sheets.GeneralFormatter("N", $.wijmo.wijspread.FormatMode.StandardNumericMode);
        var numberFormatter = new GC.Spread.Formatter.GeneralFormatter("General");

//        var style = new $.wijmo.wijspread.Style();
//        var style2 = new $.wijmo.wijspread.Style();
        var style = new GC.Spread.Sheets.Style();
        var style2 = new GC.Spread.Sheets.Style();

        var financeFormServiceData = null;
        var spreadSheet = null;
        var nrOfRow = 0;
        var rangeJSON = null;

        var currentLevel = -1;
        var groupNodes = [];
        var headerLetters = [];

        activate();

        function activate() {
            style = {
                backColor: "#e4f4fe",
                borderBottom: new GC.Spread.Sheets.LineBorder("#ffffff"),
                borderRight: new GC.Spread.Sheets.LineBorder("#ffffff"),
                font: "10pt Arial",
                vAlign: GC.Spread.Sheets.VerticalAlign.center,
                formatter: numberFormatter
            };

            style2 = {
                backColor: "#fffcad",
                borderBottom: style.borderBottom,
                borderRight: style.borderRight,
                font: style.font,
                vAlign: style.vAlign,
                formatter: numberFormatter
            };
        }

        function init() {
            resetFields();

            var dim0Id = contextVariablesService.dim0Id;
            var dim1Id = contextVariablesService.dim1Id;
            var dim2Id = contextVariablesService.dim2Id;
            var dataType = contextVariablesService.dataType;

            var profile = profileService.getSelectedProfile();
            var url = $BASE_PATH + 'Excel?topNodeId=' + TOP_NODE_ID + '&modelId=' + MODEL_ID + '&budgetCycleId=' + BUDGET_CYCLE_ID + '&dataEntryProfileId=' + profile.profileId + '&dim0=' + dim0Id + '&dim1=' + dim1Id + '&dim2=' + dim2Id + '&dataType=' + dataType;
            $http.get(url).success(function(response) {
                financeFormServiceData = response;

                $rootScope.selectedDimensions = contextVariablesService.selectedDimension = response.selectedDimension;
                $rootScope.selectedDataType = contextVariablesService.dataType = response.dataType;

                var test = {
                    worksheets: []
                };
                SpreadSheetService.setWorkbook(test);

                SpreadSheetService.setUserReadOnlyAccess(response.userReadOnlyAccess);
                contextVariablesService.setContextVariables(response.contextVariables);

                setUpFinanceFormView();
                $rootScope.$broadcast('veil:hide', {});
            });
        }


        /************************************************** PRIVATE MEMBERS *********************************************************************/


        var resetFields = function() {
            financeFormServiceData = null;
            spreadSheet = null;
            nrOfRow = 0;
            rangeJSON = {
                "itemsCount": 0,
                "itemsData": [],
                "direction": 0,
                "head": null,
                "tail": null
            };

            groupNodes = [];
            currentLevel = -1;
            headerLetters = [];
        };

        var setUpFinanceFormView = function() {
            spreadSheet = SpreadSheetService.spreadSheet;
            spreadSheet.clearSheets();
            spreadSheet.options.tabStripVisible = false;
            spreadSheet.options.newTabVisible = false;

            isFinanceFormCanBeRecalculated();

            drawFinanceForm();
            SpreadSheetService.blockExcelView();
            SpreadSheetService.toggleHeaders(false);
            addEventsForEditing();
        };

        var isFinanceFormCanBeRecalculated = function() {
            var isProtected = financeFormServiceData.userReadOnlyAccess;

            //if first dimension isn't leaf, we block to recalculate
            isProtected = isProtected || $rootScope.selectedDimensions[0].leaf === false;

            SpreadSheetService.setIsRecalculateDisabled(isProtected);
            return isProtected;
        };

        var drawFinanceForm = function() {
            spreadSheet.suspendPaint ();
            spreadSheet.suspendEvent();

            spreadSheet.addSheet(0, new GC.Spread.Sheets.Worksheet("FinanceForm"));

            sheet = spreadSheet.getActiveSheet();
            sheet.suspendPaint ();
            sheet.suspendCalcService();

            boundFinanceSheetArea();
            drawTitles();
            drawRows();
            drawGroups();

            sheet.resumeCalcService();
            sheet.resumePaint();
            spreadSheet.resumePaint();
            spreadSheet.resumeEvent();
            spreadSheet.repaint();
        };

        var drawRows = function() {
            var rows = financeFormServiceData.rows;
            for (var i = 0; i < rows.length; i++)
                drawRow(rows[i]);

            sheet.autoFitColumn(0);
        };

        var drawGroups = function() {
            rangeJSON.itemsCount = sheet.getRowCount();

            var rangeGroup = new GC.Spread.Sheets.Outlines.Outline(rangeJSON.itemsCount);
            rangeGroup.fromJSON(rangeJSON);
            sheet.rowRangeGroup = rangeGroup;
            rangeGroup.refresh();
        };

        var boundFinanceSheetArea = function() {
            var titles = financeFormServiceData.titles;

            sheet.setColumnCount(titles.length);
            sheet.setRowCount(financeFormServiceData.rows.length);
        };

        var drawTitles = function() {
            var titles = financeFormServiceData.titles;
            for (i = 0; i < titles.length; i++) {
                headerLetters.push(sheet.getValue(0, i, GC.Spread.Sheets.SheetArea.colHeader));
                sheet.setValue(0, i, titles[i], GC.Spread.Sheets.SheetArea.colHeader);
                sheet.autoFitColumn(i + 1);
            }
            sheet.setValue(0, 0, "", GC.Spread.Sheets.SheetArea.colHeader);
            spreadSheet.autoFitType(GC.Spread.Sheets.AutoFitType.CellWithHeader);
        };

        var drawRow = function(rowData) {
            prepareJSONtoRangeGroup(nrOfRow, rowData.depth, rowData.leaf);

            sheet.setValue(nrOfRow, 0, rowData.name);
            sheet.getCell(nrOfRow, 0).textIndent(3 * rowData.depth);
            sheet.getRow(nrOfRow).backColor("#d4e3fb").borderBottom(new GC.Spread.Sheets.LineBorder("#ffffff"));

            var cells = rowData.cells;
            for (var j = 0; j < cells.length; j++) {
                var currentCell = sheet.getCell(nrOfRow, j + 1);

                var canEdit = (rowData.leaf === true) && (isFinanceFormCanBeRecalculated() === false);
                if (canEdit === true) {
                    sheet.setStyle(nrOfRow, j + 1, style2);
                } else {
                    sheet.setStyle(nrOfRow, j + 1, style);
                }

                if (cells[j].value === 0)
                    currentCell.formatter("0.00");

                if (cells[j].formula !== null) {
                    currentCell.formula(cells[j].formula);
                } else {
                    currentCell.formula("");
                    sheet.setValue(nrOfRow, j + 1, cells[j].value);
                }

                currentCell.cellType(cellService.buildIOCellType(cells[j]));
                currentCell.locked(!canEdit);

                sheet.autoFitColumn(j + 1);
                currentCell = null;
            }
            nrOfRow++;
        };

        var prepareJSONtoRangeGroup = function(nrOfRow, depth, isLeaf) {
            var arr = rangeJSON.itemsData;
            var rangeNode = {
                "index": nrOfRow,
                "count": 1,
                "info": {
                    "level": depth - 1,
                    "collapsed": false
                }
            };

            if (depth > (groupNodes.length - 1)) {
                currentLevel = groupNodes.length;
                groupNodes.push(rangeNode);
            }

            if (depth !== currentLevel) {
                currentLevel = depth;
                groupNodes[currentLevel] = rangeNode;
                arr.push(rangeNode);
            } else {
                arr.push(rangeNode);
            }

            for (var i = 0; i < currentLevel; i++)
                groupNodes[i].count += 1;
        };

        /**
         * Adds events to cells
         */
        var addEventsForEditing = function() {
            SpreadSheetService.addEventsForEditing();
        };
    }
})();