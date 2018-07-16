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
        .module('app.financeSystemCell')
        .service('FinanceSystemCellService', FinanceSystemCellService);

    /* @ngInject */
    function FinanceSystemCellService($rootScope, $http, SpreadSheetService) {
        var self = this;

        self.getDataFromDatabase = getDataFromDatabase;
        self.resetData = resetData;
        self.getData = getData;
        self.getWholeValue = getWholeValue;
        self.getWholeQuantity = getWholeQuantity;
        self.getRows = getRows;
        self.getColumnNames = getColumnNames;


        /************************************************** IMPLEMENTATION *************************************************************************/



        /**
         * Get all finance data from database
         * @return {[type]} [description]
         */
        function getDataFromDatabase() {
            var index = SpreadSheetService.getSpreadSheet().getActiveSheetIndex();
            var position = SpreadSheetService.getCellPosition();

            var worksheet = SpreadSheetService.getActiveSheet();
            var cell = worksheet.getCell(position.row, position.col);
            var cellType = cell.cellType();
            var cpValue = worksheet.getValue(position.row, position.col);

            var mapping = cellType.getMapping();
            var ytd = false;
            if(mapping!=undefined){
                if (mapping.indexOf("type=B") > -1) {
                    ytd = true;
                }
            }

            var dim0 = $rootScope.selectedDimensions[0].name;
            var dim1 = $rootScope.selectedDimensions[1].name;
            var dim2 = $rootScope.selectedDimensions[2].name;
            var contextDataType = $rootScope.selectedDataType;
            var currentFinanceCube = SpreadSheetService.workbook.worksheets[index].properties.FINANCE_CUBE_ID;
            var sheetModel = SpreadSheetService.workbook.worksheets[index].properties.MODEL_ID;

            $http.get(
                $BASE_PATH + 'reviewBudget/browseInvoices' +
                '?modelId=' + MODEL_ID +
                '&sheetModel=' + sheetModel +
                '&mapping=' + mapping +
                '&contextDim0=' + dim0 +
                '&contextDim1=' + dim1 +
                '&contextDim2=' + dim2 +
                '&contextDataType=' + contextDataType +
                '&financeCube=' + currentFinanceCube
            ).success(function(response) {
                self.isDataLoaded = true;
                if (response.warningMessage !== null) {
                    self.warningMessage = response.warningMessage;
                } else if (response.validationMessage !== null) {
                    self.validationMessage = response.validationMessage;
                } else if (response.rows !== null) {
                    self.rows = response.rows;
                    self.timePeriod = response.selectionInfo[0];
                    self.startPeriod = ytd ? {
                        name: "Start Period",
                        value: "1"
                    } : response.selectionInfo[1];
                    self.endPeriod = response.selectionInfo[2];
                    self.dataType = response.selectionInfo[3];
                    self.dimensionInfo = response.dimensionInfo;
                    self.cpValue = cpValue;
                    if (cellType.getInvertedValue()) {
                        for (var i = 0; i < self.rows.length; i++) {
                            self.rows[i][5] *= -1;
                        }
                    }
                    //by arnold
                    self.columnNames = response.columnNames;
                } else {
                    self.cpValue = cpValue;
                    self.warningMessage = null;
                    $rootScope.$broadcast('FinanceSystemCellService:DataUpdated');
                }
            });
        }

        /**
         * Resets all properties in service
         * @return {[type]} [description]
         */
        function resetData() {
            self.isDataLoaded = false;
            self.cpValue = 0;

            self.rows = null;
            self.timePeriod = null;
            self.startPeriod = null;
            self.endPeriod = null;
            self.dataType = null;

            self.dimensionInfo = null;
            self.warningMessage = null;
        }

        /**
         * Get whole data from service
         * @return {[type]} [description]
         */
        function getData() {
            return self;
        }

        /**
         * Get whole value (sum of finance invoices) in specific time (time period from startPeriod to endPeriod)
         * @return {[type]} [description]
         */
        function getWholeValue() {
            var value = 0;
            angular.forEach(self.rows, function(row) {
                if (row[3] == self.timePeriod.value && row[4] >= self.startPeriod.value && row[4] <= self.endPeriod.value)
                    value += row[5];
            });
            return value;
        }

        /**
         * Get whole quantity (sum of finance invoices) in specific time (time period from startPeriod to endPeriod)
         * @return {[type]} [description]
         */
        function getWholeQuantity() {
            var quantity = 0;
            angular.forEach(self.rows, function(row) {
                if (row[3] == self.timePeriod.value && row[4] >= self.startPeriod.value && row[4] <= self.endPeriod.value)
                    quantity += row[2];
            });
            return quantity;
        }

        /**
         * Get filtered row in specific time (time period from startPeriod to endPeriod)
         * @return {[type]} [description]
         */
        function getRows() {
            var rows = [];

            angular.forEach(self.rows, function(row) {
                if (row[3] == self.timePeriod.value && row[4] >= self.startPeriod.value && row[4] <= self.endPeriod.value) {
                    rows.push(row);
                }
            });

            return rows;
        }
        function getColumnNames(){
        	var columns = [];
        	for(var i = 0; i < self.columnNames.length;i++){
        		if(self.columnNames[i]!='Company')
        			columns.push(self.columnNames[i]);
        	}
            return columns;
        }
    }
})();