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
        .module('flatFormTemplateApp.spreadSheet')
        .service('SpreadSheetService', SpreadSheetService);

    /* @ngInject */
    function SpreadSheetService($rootScope, $http, $timeout) {
        var self = this;
        var loader = {
            isSpreadLoaded: false,
            isJsonLoaded: false
        };
        var emptyJsonForm = null;
        var formulaBox;

        self.getSpreadLoader = getSpreadLoader;
        self.initializeFormulaBox = initializeFormulaBox;
        self.updatePositionBox = updatePositionBox;

        self.blockExcelView = blockExcelView;
        self.unblockExcelView = unblockExcelView;
        self.getActualCellRange = getActualCellRange;
        self.clearSelection = clearSelection;

        self.setSpread = setSpread;
        self.getSpread = getSpread;
        self.setJsonForm = setJsonForm;
        self.getJsonForm = getJsonForm;
        self.startEdit = startEdit;
        self.setEmptyJsonForm = setEmptyJsonForm;
        self.getEmptyJsonForm = getEmptyJsonForm;

        /************************************************** IMPLEMENTATION *************************************************************************/
        function getSpreadLoader() {
            return loader;
        }

        function initializeFormulaBox() {
            //formulaBox = new GC.Spread.Sheets.FormulaTextBox(document.getElementById('formula-box'));
			formulaBox = new GC.Spread.Sheets.FormulaTextBox.FormulaTextBox(document.getElementById("formula-box"));

//            formulaBox.spread(self.spread);
			formulaBox.workbook(self.spread);
        }

        function updateFormulaBox() {
            if (formulaBox) {
                var sheet = self.spread.getActiveSheet();
                var text = sheet.getText(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                formulaBox.text(text);
            }
        }

        function updatePositionBox() {
            var sheet = self.spread.getActiveSheet();
            var position = sheet.getText(0, sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.colHeader) + sheet.getText(sheet.getActiveRowIndex(), 0, GC.Spread.Sheets.SheetArea.rowHeader);
            $("#position-box").val(position);
        }

        function blockExcelView(spread) {
            if (!spread) {
                return;
            }
            spread.options.newTabVisible = false;
            spread.options.canUserDragDrop = false;
            spread.options.canUserDragFill = false;
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                sheet.options.isProtected = (true);
            }
        }

        function setFirstCellSelected() {
            if (self.spread) {
                var activeSheet = self.spread.getActiveSheet();
                if (activeSheet.getSelections().length === 0) {
                    activeSheet.setSelection(0, 0, 1, 1);
                }
            }
        }

        function unblockExcelView(spread) {
            if (!spread) {
                return;
            }
            spread.options.newTabVisible = true;
            spread.options.canUserDragDrop = true;
            spread.options.canUserDragFill = true;
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                sheet.options.isProtected = (false);
            }
        }

        function getActualCellRange(cellRange, rowCount, columnCount) {
            if (cellRange.row == -1 && cellRange.col == -1) {
                return new GC.Spread.Sheets.Range(0, 0, rowCount, columnCount);
            } else if (cellRange.row == -1) {
                return new GC.Spread.Sheets.Range(0, cellRange.col, rowCount, cellRange.colCount);
            } else if (cellRange.col == -1) {
                return new GC.Spread.Sheets.Range(cellRange.row, 0, cellRange.rowCount, columnCount);
            }
            return cellRange;
        }

        function setSpread(spread) {
            self.spread = spread;
            loader.isSpreadLoaded = true;
        }

        function getSpread() {
            return self.spread;
        }

        function setJsonForm(jsonForm) {
            manageSpread(jsonForm);
        }

        function getJsonForm() {
            if (self.spread) {
                clearSelection();
                var data = {
                    spread: self.spread.toJSON()
                };
                return JSON.stringify(data);
            }
        }

        function startEdit() {
            if (self.spread) {
                var sheet = self.spread.getActiveSheet();
                sheet.startEdit(true);
            }
        }

        function setEmptyJsonForm() {
            if (self.spread && emptyJsonForm === null) { // prepare emptyJsonForm only once
                setJsonForm(null);
                emptyJsonForm = getJsonForm();
            }
        }

        function getEmptyJsonForm() {
            return emptyJsonForm;
        }

        function clearSelection() {
            if (self.spread) {
                var spread = self.spread;
                for (var i = 0; i < spread.getSheetCount(); i++) {
                    var sheet = spread.getSheet(i);
                    sheet.clearSelection();
                }
            }
        }

        function manageSpread(jsonForm) {
            if (self.spread) {
                self.spread.suspendCalcService(false);
                self.spread.suspendPaint ();

                if (jsonForm) {
                    var data = JSON.parse(jsonForm);
                    self.spread.fromJSON(data.spread);
                } else {
                    self.spread.clearSheets();
//                    var sheet = new GC.Spread.Sheets.Sheet("Sheet1");
                    var sheet = new GC.Spread.Sheets.Worksheet('Sheet1');

                    sheet.options.frozenlineColor="rgba(255, 255, 255, 0)"; // hide frozen line
                    self.spread.addSheet(0, sheet);
                }

                setFirstCellSelected();
                updatePositionBox();
                updateFormulaBox();

                self.spread.resumeCalcService(true);
                self.spread.resumePaint();

                loader.isJsonLoaded = true;
            }
        }

    }
})();