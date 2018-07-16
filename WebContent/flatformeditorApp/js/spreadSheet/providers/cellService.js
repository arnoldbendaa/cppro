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
        .module('flatFormEditorApp.spreadSheet')
        .service('CellService', CellService);

    /* @ngInject */
    function CellService(ViewModeService) {

        var self = this;
        self.ifCellTyleIsIOCellType = ifCellTyleIsIOCellType;
        self.setUpCellType = setUpCellType;
        self.buildIOCellType = buildIOCellType;
        self.buildIOCellTypeInRange = buildIOCellTypeInRange;

        /************************************************** IMPLEMENTATION *************************************************************************/

        function ifCellTyleIsIOCellType(cell) {
            if (cell === null)
                return false;

            if (cell.cellType() === undefined)
                return false;

            return cell.cellType() instanceof IOCellType;
        }

        function setUpCellType(cell, tag) {
            if (tag && (tag.inputMapping !== null || tag.outputMapping !== null || tag.text !== null)) {
                cell.cellType(self.buildIOCellType());
            } else {
                cell.cellType(undefined);
            }
        }

        function buildIOCellType(cell) {
            return new IOCellType(cell);
        }

        function buildIOCellTypeInRange(sheet, range) {
            for (var i = 0; i < range.rowCount; i++) {
                for (var j = 0; j < range.colCount; j++) {
                    var cell = sheet.getCell(range.row + i, range.col + j);
                    if (self.ifCellTyleIsIOCellType(cell) === false) {
                        cell.cellType(self.buildIOCellType(cell));
                    }
                }
            }
        }

        /*** IOCellType Object ***/
        function IOCellType() {}

//        IOCellType.prototype = new $.wijmo.wijspread.TextCellType();
        IOCellType.prototype = new GC.Spread.Sheets.CellTypes.Text();

        IOCellType.prototype.paint = function(ctx, value, x, y, w, h, style, context) {
            var cellMode = ViewModeService.getCurrentViewMode().name;
            var sheet = context.sheet;
            var tag = sheet.getCell(context.row, context.col, GC.Spread.Sheets.SheetArea.viewport, true).tag();

            if (tag) {
                if (cellMode === "INPUT" && tag.inputMapping !== null) {
                    value = tag.inputMapping;
                }

                if (cellMode === "OUTPUT" && tag.outputMapping !== null) {
                    value = tag.outputMapping;
                }

                if (cellMode === "FORMULA" && tag.text !== null) {
                    value = tag.text;
                }
            }

//            $.wijmo.wijspread.TextCellType.prototype.paint.apply(this, [ctx, value, x, y, w, h, style, context]);
            GC.Spread.Sheets.CellTypes.Text.prototype.paint.apply(this, [ctx, value, x, y, w, h, style, context]);

            if (tag && cellMode !== "TEST") {
                if (tag.inputMapping) {
                    ctx.fillStyle = "red";
                    ctx.beginPath();
                    ctx.moveTo(x + w - 12, y);
                    ctx.lineTo(x + w, y);
                    ctx.lineTo(x + w, y + 12);
                    ctx.closePath();
                    ctx.fill();
                }

                if (tag.outputMapping) {
                    ctx.fillStyle = "green";
                    ctx.beginPath();
                    ctx.moveTo(x, y + h - 12);
                    ctx.lineTo(x, y + h);
                    ctx.lineTo(x + 12, y + h);
                    ctx.closePath();
                    ctx.fill();
                }

                if (tag.text) {
                    ctx.fillStyle = "blue";
                    ctx.beginPath();
                    ctx.moveTo(x, y + h - 10);
                    ctx.lineTo(x, y + h);
                    ctx.lineTo(x + 10, y + h);
                    ctx.closePath();
                    ctx.fill();

                    ctx.beginPath();
                    ctx.moveTo(x + w - 10, y);
                    ctx.lineTo(x + w, y);
                    ctx.lineTo(x + w, y + 10);
                    ctx.closePath();
                    ctx.fill();
                }
            }
        };
    }
})();