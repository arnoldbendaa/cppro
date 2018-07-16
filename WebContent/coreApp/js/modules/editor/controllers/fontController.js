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
        .module('coreApp.editor')
        .controller('FontController', FontController);

    /* @ngInject */
    function FontController($scope, SpreadSheetService) {
        var jqueryElement = $('#fontSample');
        var jsElement = document.getElementById('fontSample');


        $scope.manageBold = manageBold;
        $scope.manageItalic = manageItalic;
        $scope.manageFont = manageFont;
        $scope.increaseFontSize = increaseFontSize;
        $scope.decreaseFontSize = decreaseFontSize;
        $scope.manageTextDecoration = manageTextDecoration;

        $scope.fontFamilies = [
            'Arial', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana', 'Wingdings'
        ];

        $scope.fontSizes = [
            '10px', '11px', '12px', '13px', '14px', '15px', '16px', '19px', '20px', '24px', '36px'
        ];


        activate();

        function activate() {

            if (!window.getComputedStyle) {
                window.getComputedStyle = function(el) {
                    this.el = el;
                    this.getPropertyValue = function(prop) {
                        var re = /(\-([a-z]){1})/g;
                        if (prop == 'float') prop = 'styleFloat';
                        if (re.test(prop)) {
                            prop = prop.replace(re, function() {
                                return arguments[2].toUpperCase();
                            });
                        }
                        return el.currentStyle[prop] ? el.currentStyle[prop] : null;
                    };
                    return this;
                };
            }
        }


        /************************************************** IMPLEMENTATION *************************************************************************/



        function css(element, property) {
            return window.getComputedStyle(element, null).getPropertyValue(property);
        }

        function setFontForCells(font) {
            var sheet = $scope.spread.getActiveSheet();
            sheet.suspendPaint ();

            var sels = sheet.getSelections();
            for (var n = 0; n < sels.length; n++) {
                var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());

                for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
                    for (var j = sel.col; j < sel.col + sel.colCount; j++) {
                        var cell = sheet.getCell(i, j);
                        var isLocked = cell.locked();
                        cell.locked(false);
                        var style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
                        style.font = font;
                        cell.locked(isLocked);
                    }
                }
            }
            sheet.resumePaint();
        }

        function manageBold() {
            $scope.isBold = !$scope.isBold;

            var weight = ($scope.isBold) ? 'bold' : 'normal';
            jqueryElement.css('font-weight', weight);
            var font = jqueryElement.css('font');
            setFontForCells(font);
        }

        function manageItalic() {
            $scope.isItalic = !$scope.isItalic;

            var italic = ($scope.isItalic) ? 'italic' : 'normal';
            jqueryElement.css('font-style', italic);
            var font = jqueryElement.css('font');
            setFontForCells(font);
        }

        function manageFont() {
            jqueryElement.css('font-family', $scope.selectedFontFamily);
            jqueryElement.css('font-size', $scope.selectedFontSize);
            var font = jqueryElement.css('font');
            setFontForCells(font);
        }

        function increaseFontSize() {
            var currentIndex = $scope.fontSizes.indexOf($scope.selectedFontSize);
            var newIndex = currentIndex + 1;
            if (newIndex < $scope.fontSizes.length) {
                $scope.selectedFontSize = $scope.fontSizes[newIndex];
                $scope.manageFont();
            }
        }

        function decreaseFontSize() {
            var currentIndex = $scope.fontSizes.indexOf($scope.selectedFontSize);
            var newIndex = currentIndex - 1;
            if (newIndex >= 0) {
                $scope.selectedFontSize = $scope.fontSizes[newIndex];
                $scope.manageFont();
            }
        }

        function manageTextDecoration(param) {
            if (param === 'underline') {
                $scope.isUnderline = !$scope.isUnderline;
            }
            if (param === 'overline') {
                $scope.isOverline = !$scope.isOverline;
            }
            if (param === 'line-through') {
                $scope.isStrikethrough = !$scope.isStrikethrough;
            }
            var textDecoration = '';
            textDecoration += ($scope.isUnderline) ? 'underline ' : '';
            textDecoration += ($scope.isOverline) ? 'overline ' : '';
            textDecoration += ($scope.isStrikethrough) ? 'line-through ' : '';
            textDecoration.trim();

            if (textDecoration.length === 0) {
                textDecoration = 'none';
            }
            jqueryElement.css('text-decoration', textDecoration);
            var condition = $scope.isUnderline + 2 * $scope.isStrikethrough + 4 * $scope.isOverline;
            var sheet = $scope.spread.getActiveSheet();
            sheet.suspendPaint ();

            var sels = sheet.getSelections();
            for (var n = 0; n < sels.length; n++) {
                var sel = SpreadSheetService.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());

                for (var i = sel.row; i < sel.row + sel.rowCount; i++) {
                    for (var j = sel.col; j < sel.col + sel.colCount; j++) {
                        var cell = sheet.getCell(i, j);
                        var isLocked = cell.locked();
                        cell.locked(false);

                        var style = sheet.getStyle(i, j, GC.Spread.Sheets.SheetArea.viewport, true);
                        style.textDecoration = condition;

                        cell.locked(isLocked);
                    }
                }
            }
            sheet.resumePaint();
        }


        /************************************************** EVENTS *********************************************************************/



        $scope.$on('SpreadSheetController:SelectionChanging', function() {
            var sheet = $scope.spread.getActiveSheet();

            var font = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true).font();
            var textDecoration = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.viewport, true).textDecoration();

            switch (textDecoration) {
                case GC.Spread.Sheets.TextDecorationType.None:
                    textDecoration = 'none';
                    break;
                case GC.Spread.Sheets.TextDecorationType.Underline:
                    textDecoration = 'underline';
                    break;
                case GC.Spread.Sheets.TextDecorationType.Overline:
                    textDecoration = 'overline';
                    break;
                case GC.Spread.Sheets.TextDecorationType.Underline | GC.Spread.Sheets.TextDecorationType.Overline:
                    textDecoration = 'underline overline';
                    break;
                case GC.Spread.Sheets.TextDecorationType.LineThrough:
                    textDecoration = 'line-through';
                    break;
                case GC.Spread.Sheets.TextDecorationType.Underline | GC.Spread.Sheets.TextDecorationType.LineThrough:
                    textDecoration = 'underline line-through';
                    break;
                case GC.Spread.Sheets.TextDecorationType.Overline | GC.Spread.Sheets.TextDecorationType.LineThrough:
                    textDecoration = 'overline line-through';
                    break;
                case GC.Spread.Sheets.TextDecorationType.Underline | GC.Spread.Sheets.TextDecorationType.Overline | GC.Spread.Sheets.TextDecorationType.LineThrough:
                    textDecoration = 'underline overline line-through';
                    break;
                default:
                    textDecoration = 'none';
                    break;
            }
            jqueryElement.css('text-decoration', textDecoration);
            jqueryElement.css('font', font);

            $scope.isBold = css(jsElement, 'font-weight') == 'bold';
            $scope.isItalic = css(jsElement, 'font-style') == 'italic';
            $scope.isUnderline = css(jsElement, 'text-decoration').indexOf('underline') != -1;
            $scope.isStrikethrough = css(jsElement, 'text-decoration').indexOf('line-through') != -1;
            $scope.isOverline = css(jsElement, 'text-decoration').indexOf('overline') != -1;

            var index, fontSize, fontFamily;
            if (font) {
                fontFamily = css(jsElement, 'font-family');
                fontFamily = fontFamily.replace(/\'/g, '');

                fontSize = css(jsElement, 'font-size');
                fontSize = '' + Math.round(parseFloat(fontSize)) + 'px';
            } else {
                fontFamily = 'Arial';
                fontSize = '14px';
            }
            index = $scope.fontFamilies.indexOf(fontFamily);
            $scope.selectedFontFamily = $scope.fontFamilies[index];

            index = $scope.fontSizes.indexOf(fontSize);
            $scope.selectedFontSize = $scope.fontSizes[index];

            $scope.$apply();
        });
    }

})();