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
        .module('flatFormTemplateApp.editor')
        .service('HeadingGridlineService', HeadingGridlineService);

    /* @ngInject */
    function HeadingGridlineService() {
        var self = this;

        var areHeadingsVisible = {
            row: true,
            column: true
        };
        var areGridLinesVisible = {
            row: true,
            column: true
        };

        self.toggleHeaders = toggleHeaders;
        self.toggleGridlines = toggleGridlines;
        self.manageHeadingsVisibility = manageHeadingsVisibility;
        self.manageGridLinesVisibility = manageGridLinesVisibility;
        self.manageHeaders = manageHeaders;
        self.manageGridLines = manageGridLines;

        self.getHeadingsVisible = getHeadingsVisible;
        self.getGridLinesVisible = getGridLinesVisible;

        function toggleHeaders(spread, type) {
            areHeadingsVisible[type] = !areHeadingsVisible[type];
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                if (type == "row") {
//                    sheet.setRowHeaderVisible(areHeadingsVisible[type]);
                	sheet.options.rowHeadervisible = areHeadingsVisible[type];
                } else if (type == "column") {
//                    sheet.setColumnHeaderVisible(areHeadingsVisible[type]);
                	sheet.options.colHeaderVisible = areHeadingsVisible[type];
                }
            }
        }

        function toggleGridlines(spread, type) {
            areGridLinesVisible[type] = !areGridLinesVisible[type];
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                if (type == "row") {
//                    sheet.setGridlineOptions({
//                        showHorizontalGridline: areGridLinesVisible[type]
//                    });
//                    sheet.options.gridline = {showHorizontalGridline:areGridLinesVisible[type]};
                    sheet.options.gridline.showHorizontalGridline = areGridLinesVisible[type];
                } else if (type == "column") {
//                    sheet.setGridlineOptions({
//                        showVerticalGridline: areGridLinesVisible[type]
//                    });
//                	sheet.options.gridline = {showVerticalGridline:areGridLinesVisible[type]};
                	sheet.options.gridline.showVerticalGridline = areGridLinesVisible[type];
                }
            }
        }

        function manageHeadingsVisibility(spread) {
            if (spread !== null && spread.getActiveSheet()) {
                var activeSheet = spread.getActiveSheet();
//                areHeadingsVisible.row = activeSheet.getRowHeaderVisible();
//                areHeadingsVisible.column = activeSheet.getColumnHeaderVisible();
            	areHeadingsVisible.row = activeSheet.options.rowHeaderVisible;
            	areHeadingsVisible.column = activeSheet.options.columnHeaderVisible;

                manageHeaders(spread);
            }
        }

        function manageGridLinesVisibility(spread) {
            if (spread !== null && spread.getActiveSheet()) {
                var activeSheet = spread.getActiveSheet();
//                areGridLinesVisible.row = activeSheet.getGridlineOptions().showHorizontalGridline;
//                areGridLinesVisible.column = activeSheet.getGridlineOptions().showVerticalGridline;
                areGridLinesVisible.row = activeSheet.options.gridline.showHorizontalGridline;
                areGridLinesVisible.column = activeSheet.options.gridline.showVerticalGridline;
                manageGridLines(spread);
            }
        }

        function manageHeaders(spread) {
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                sheet.options.rowHeadervisible = areHeadingsVisible.row;
                sheet.options.colHeaderVisible = areHeadingsVisible.column;
//                sheet.setRowHeaderVisible(areHeadingsVisible.row);
//                sheet.setColumnHeaderVisible(areHeadingsVisible.column);
            }
        }

        function manageGridLines(spread) {
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
//                sheet.setGridlineOptions({
//                    showHorizontalGridline: areGridLinesVisible.row
//                });
//                sheet.setGridlineOptions({
//                    showVerticalGridline: areGridLinesVisible.column
//                });
                sheet.options.gridline = {showHorizontalGridline:areGridLinesVisible.row,showVerticalGridline:areGridLinesVisible.column};
            }
        }

        function getHeadingsVisible(spread) {
            return areHeadingsVisible;
        }

        function getGridLinesVisible(spread) {
            return areGridLinesVisible;
        }
    }

})();