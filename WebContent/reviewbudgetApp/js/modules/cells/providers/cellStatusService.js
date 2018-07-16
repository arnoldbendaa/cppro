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
        .service('CellStatusService', CellStatusService);

    /* @ngInject */
    function CellStatusService($rootScope, $http, SpreadSheetService) {
        var self = this;
        self.isNotesAdded = isNotesAdded;
        self.getDataFromDatabase = getDataFromDatabase;
        self.getData = getData;
        self.resetData = resetData;
        self.getCurrentValue = getCurrentValue;
        self.getOriginalValue = getOriginalValue;

        /************************************************** IMPLEMENTATION *************************************************************************/

        /**
         * Get data from database
         * @return {[type]} [description]
         */
        function getDataFromDatabase() {
            var position = SpreadSheetService.getCellPosition(),
                worksheet = SpreadSheetService.getActiveSheet(),
                mapping = worksheet.getCell(position.row, position.col).cellType().getMapping(),
                dim0 = $rootScope.selectedDimensions[0].name,
                dim1 = $rootScope.selectedDimensions[1].name,
                dim2 = $rootScope.selectedDimensions[2].name,
                contextDataType = $rootScope.selectedDataType,
                index = SpreadSheetService.getSpreadSheet().getActiveSheetIndex(),
                workbook = SpreadSheetService.workbook,
                sheetModel = workbook.worksheets[index].properties.MODEL_ID;

            $http.get(
                $BASE_PATH + 'reviewBudget/fetchDimensionDetails' +
                '?modelId=' + MODEL_ID +
                '&sheetModel=' + sheetModel +
                '&mapping=' + mapping +
                '&contextDim0=' + dim0 +
                '&contextDim1=' + dim1 +
                '&contextDim2=' + dim2 +
                '&contextDataType=' + contextDataType
            ).success(function(response) {
                if (response.warningMessage !== null) {
                    self.warningMessage = response.warningMessage;
                }
                if (response.dimensions !== null && response.dimensions.length !== 0) {
                    self.originalValue = self.getOriginalValue(position);
                    self.currentValue = self.getCurrentValue(position, worksheet);
                    self.hasNote = self.isNotesAdded();

                    self.dim0 = response.dimensions[0];
                    self.dim1 = response.dimensions[1];
                    self.dim2 = response.dimensions[2];
                    self.dataType = response.dimensions[3];
                }
                self.isDataLoaded = true;
            });
        }

        /**
         * [getData description]
         * @return {[type]} [description]
         */
        function getData() {
            return self;
        }

        /**
         * [resetData description]
         * @return {[type]} [description]
         */
        function resetData() {
            self.isDataLoaded = false;
            self.originalValue = null;
            self.currentValue = null;
            self.hasNote = false;
            self.dataType = null;
            self.dim0 = null;
            self.dim1 = null;
            self.dim2 = null;

            self.warningMessage = null;
        }

        /**
         * [getCurrentValue description]
         * @param  {[type]} position  [description]
         * @param  {[type]} worksheet [description]
         * @return {[type]}           [description]
         */
        function getCurrentValue(position, worksheet) {
            if (position === undefined)
                position = SpreadSheetService.getCellPosition();

            if (worksheet === undefined)
                worksheet = SpreadSheetService.getActiveSheet();

            var invertedValue = worksheet.getCell(position.row, position.col).cellType().getInvertedValue();
            if (invertedValue)
                return -1 * worksheet.getValue(position.row, position.col);
            return worksheet.getValue(position.row, position.col);
        }

        /**
         * [getOriginalValue description]
         * @param  {[type]} position [description]
         * @return {[type]}          [description]
         */
        function getOriginalValue(position) {
            if (position === undefined)
                position = SpreadSheetService.getCellPosition();

            var index = SpreadSheetService.getSpreadSheet().getActiveSheetIndex(),
                workbook = SpreadSheetService.getWorkbook(),
                currentWorkbookWorksheet = workbook.worksheets[index],
                cells = currentWorkbookWorksheet.cells;

            for (var i = 0; i < cells.length; i++) {
                if (cells[i].row == position.row && cells[i].column == position.col) {
                    if (cells[i].value !== null) {
                        if (cells[i].invertedValue)
                            return -1 * cells[i].value;
                        else
                            return cells[i].value;
                    }
                    if (cells[i].text !== null)
                        return cells[i].text;
                    if (cells[i].formula !== null)
                        return cells[i].formula;
                    break;
                }
            }
            return null;
        }

        /**
         * [isNotesAdded description]
         * @param  {[type]}  position  [description]
         * @param  {[type]}  worksheet [description]
         * @return {Boolean}           [description]
         */
        function isNotesAdded(position, worksheet) {
            if (position === undefined)
                position = SpreadSheetService.getCellPosition();

            if (worksheet === undefined)
                worksheet = SpreadSheetService.getActiveSheet();
            return worksheet.getCell(position.row, position.col).cellType().getNotes() !== null;
        }
    }
})();