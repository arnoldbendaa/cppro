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
        .module('app.cellStatus')
        .service('CellService', CellService);

    /* @ngInject */
    function CellService($rootScope, $http, SpreadSheetService) {
        var self = this;
        self.buildIOCellType = buildIOCellType;
        self.ifCellTyleIsIOCellType = ifCellTyleIsIOCellType;
        self.ifCellCanHaveNote = ifCellCanHaveNote;
        self.ifCellHasInputMapping = ifCellHasInputMapping;
        self.ifRangeHasOutputMapping = ifRangeHasOutputMapping;
        self.ifCellHasOutputMapping = ifCellHasOutputMapping;
        self.getSelectedCell = getSelectedCell;

        activate();

        function activate() {
            /*** IOCellType Object ***/

            /**
             * Set prototype of IOCellType as TextCellType
             * @return {[type]} [description]
             */
//            IOCellType.prototype = new $.wijmo.wijspread.TextCellType();
            IOCellType.prototype = new GC.Spread.Sheets.CellTypes.Text();
            /**
             * Getter for Input Mapping
             * @return Object InputMapping object
             */
            IOCellType.prototype.getInputMapping = function() {
                return this.inputMapping;
            };

            /**
             * Getter for Output Mapping
             * @return Object OutputMapping object
             */
            IOCellType.prototype.getOutputMapping = function() {
                return this.outputMapping;
            };

            IOCellType.prototype.getMapping = function() {
                if (this.inputMapping !== null) {
                    return this.inputMapping;
                } else if (this.outputMapping !== null) {
                    return this.outputmapping;
                }
            };

            /**
             * Getter for Inverted Value
             * @return Object InvertedValue object
             */
            IOCellType.prototype.getInvertedValue = function() {
                return this.invertedValue;
            };

            /**
             * Getter for Note
             * @return Object Notes object
             */
            IOCellType.prototype.getNotes = function() {
                return this.notes;
            };

            /**
             * This function is only the wijmo api hook
             */
            IOCellType.prototype.getHitInfo = function(x, y, row, col, cellStyle, cellRect, sheetArea) {
                //return $.wijmo.wijspread.TextCellType.prototype.getHitInfo.apply(this, arguments);
                return {
                    x: x,
                    y: y,
                    row: row,
                    col: col,
                    cellStyle: cellStyle,
                    cellRect: cellRect,
                    sheetArea: sheetArea
                };
            };

            // Override paint function, drag a red triangle at top-right corner of the cell
            IOCellType.prototype.paint = function(ctx, value, x, y, w, h, style, options) {
//                $.wijmo.wijspread.TextCellType.prototype.paint.apply(this, arguments);
                //console.log('painting');
                GC.Spread.Sheets.CellTypes.Text.prototype.paint.apply(this, arguments);
                if (this.notes === null)
                    return;
                if (typeof this.notes.notes === 'undefined')
                    return;

                ctx.fillStyle = "red";
                ctx.beginPath();
                ctx.moveTo(x + w - 10, y);
                ctx.lineTo(x + w, y);
                ctx.lineTo(x + w, y + 10);
                ctx.fill();
            };
        }

        /************************************************** IMPLEMENTATION *************************************************************************/


        function buildIOCellType(cell) {
            return new IOCellType(cell);
        }

        function ifCellTyleIsIOCellType(cell) {
            if (cell === null)
                return false;

            if (cell.cellType() === undefined || !cell.cellType() instanceof IOCellType)
                return false;

            return true;
        }

        /**
         * Checks if cell can have note
         */
        function ifCellCanHaveNote(cell) {
            if (cell === null)
                return false;

            if (cell.cellType() === undefined || !cell.cellType() instanceof IOCellType || cell.cellType().getNotes() === null)
                return false;

            return true;
        }

        /**
         * Checks if cell have input mapping
         */
        function ifCellHasInputMapping(cell) {
            if (cell === null)
                return false;

            if (cell.cellType() === undefined || !cell.cellType() instanceof IOCellType || cell.cellType().getInputMapping() === null)
                return false;

            //cell.backColor("pink");
            return true;
        }

        /**
         * Checks if range has at least one cell with outputmapping
         * @param  {[type]} range [description]
         * @return {[type]}       [description]
         */
        function ifRangeHasOutputMapping(range) {
            var spread = SpreadSheetService.getSpreadSheet();
            var sheet = SpreadSheetService.getActiveSheet();
            var rangeHasOutputMapping = false;

            for (var i = range.column; i < range.column + range.columnCount; i++) {
                for (var j = range.row; j < range.row + range.rowCount; j++) {
                    var cell = sheet.getCell(j, i);
                    if (self.ifCellHasOutputMapping(cell)) {
                        rangeHasOutputMapping = true;
                        break;
                    }
                }
            }
            return rangeHasOutputMapping;
        }

        /**
         * Checks if cell can be edited, have outputmapping
         */
        function ifCellHasOutputMapping(cell) {
            if (cell !== null && cell.cellType() !== undefined && cell.cellType() instanceof IOCellType && cell.cellType().getOutputMapping() !== null) {
                return true;
            }
            return false;
        }

        /**
         * Returns selected cell
         * @return {[type]} [description]
         */
        function getSelectedCell() {
            var spread = SpreadSheetService.getSpreadSheet();
            var sheet = SpreadSheetService.getActiveSheet();

            var selections = sheet.getSelections();
            if (selections.activeSelectedRangeIndex != -1) {
                var data = selections["0"];
                return sheet.getCell(data.row, data.col);
            }
            return null;
        }

        /**
         * IOCellType Constructor
         * @param Object data data object with I/O Mapping
         */
        function IOCellType(data) {
            this.inputMapping = (data !== undefined && data.inputMapping !== null) ? data.inputMapping : null;
            this.outputMapping = (data !== undefined && data.outputMapping !== null) ? data.outputMapping : null;
            this.notes = (data !== undefined && data.notes !== null && data.notes !== "") ? data.notes : null;
            this.invertedValue = (data !== undefined && data.invertedValue !== null) ? data.invertedValue : false;

            /*            if (this.notes != null)
                console.log("mam notatkę: " + this.notes);*/
        }
    }
})();