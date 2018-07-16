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
    'use_strict';

    angular
        .module('lookupCurrencyApp.currency')
        .controller('CurrencyController', CurrencyController);

    /* @ngInject */
    function CurrencyController($scope, $timeout, $modal, CurrencyService, PeriodService, SpreadSheetService, DictionaryService, DrawingService, CoreCommonsService) {
        var spread, dictionaries, delay;
        var periods = PeriodService.getPeriods();
        var editedRanges = [];
        var isUpload = false;
        var url = $BASE_PATH;
        
        $scope.logout = logout;
        $scope.closeTab = closeTab;
        $scope.currentCompany = {};
        $scope.onlyNumbers = /^\d+$/;
        $scope.currencyBase = "?";
        $scope.currentYear = new Date().getFullYear();
        $scope.isValid = true;
        $scope.isValidYear = true;
        $scope.spreadLoader = SpreadSheetService.getSpreadLoader();
        $scope.currencyLoader = CurrencyService.getLoader();
        $scope.increaseYear = increaseYear;
        $scope.decreaseYear = decreaseYear;
        $scope.increasePrecision = increasePrecision;
        $scope.decreasePrecision = decreasePrecision;
        $scope.show = show;
        $scope.addCurrency = addCurrency;
        $scope.upload = upload;
        $scope.manageCompany = manageCompany;
        $scope.onCompanyChange = onCompanyChange;

        activate();
        /************************************************** IMPLEMENTATION *************************************************************************/

        function activate() {
            getDictionaries();
            getCompanies();

            onSpreadLoaded();

            onYearChange();
        }

        function onSpreadLoaded() {
            $scope.$watch('spreadLoader.isSpreadLoaded', function() {
                if ($scope.spreadLoader.isSpreadLoaded) {
                    spread = SpreadSheetService.getSpread();

                    addSpreadEvents();
                    manageSpreadDefaults();
                    manageDictionaryHeaders();
                    managePeriodHeaders();
                    onPrecisionChange();
                }
            });
        }

        function manageSpreadDefaults() {
//            spread.tabStripVisible(false);
//            spread.showDragFillSmartTag(false);
//            spread.showHorizontalScrollbar(true);
        	spread.options.tabStripVisible = false;
        	spread.options.showDragFillSmartTag = false;
        	spread.options.showHorizontalScrollbar = true;

            // var defaultStyle = new $.wijmo.wijspread.Style();
            // defaultStyle.backColor = "LemonChiffon";
            // defaultStyle.foreColor = "Red";
            // defaultStyle.formatter = "0.00";
            // defaultStyle.hAlign = $.wijmo.wijspread.HorizontalAlign.center;
            // defaultStyle.vAlign = $.wijmo.wijspread.VerticalAlign.center;
            // defaultStyle.borderLeft = new $.wijmo.wijspread.LineBorder("Green");
            // defaultStyle.borderTop = new $.wijmo.wijspread.LineBorder("Green");
            // defaultStyle.borderRight = new $.wijmo.wijspread.LineBorder("Green");
            // defaultStyle.borderBottom = new $.wijmo.wijspread.LineBorder("Green");

            var sheet;
            for (var i = 0; i < spread.getSheetCount(); i++) {
                sheet = spread.getSheet(i);
                sheet.options.isProtected = (true);
                sheet.defaults.colWidth = 65;
            }

            sheet = spread.getActiveSheet();
            var sheetAreaCol = GC.Spread.Sheets.SheetArea.colHeader;
            var sheetAreaRow = GC.Spread.Sheets.SheetArea.rowHeader;

//            var row = sheet.getRow(0, sheetAreaCol);
            var rowCount = sheet.getRowCount();
            var columnCount = sheet.getColumnCount();
            
            //var row = sheet.getRange(0,0,1,columnCount,sheetAreaCol);
            var row = sheet.getCell(0,-1);
            row.font("normal bold normal 13px/normal Arial");
          //  var column = sheet.getColumn(0, sheetAreaRow);
//            var column = sheet.getRange(0,0,rowCount,1,sheetAreaRow);
            var column = sheet.getCell(-1,0);
            column.font("normal bold normal 13px/normal Arial");
            column.width(65);

            // Set columns width
            sheet.setColumnWidth(0, 120, sheetAreaCol);
            sheet.setColumnWidth(13, 120, sheetAreaCol)
            sheet.setColumnWidth(14, 90, sheetAreaCol)
            sheet.setColumnWidth(15, 90, sheetAreaCol)
        }

        function onCompanyChange() {
            toggleCurrencyLoader();
            $scope.currencyBase = getCurrencyForCurrentCompany($scope.currentCompany.value);
            $scope.currentPrecision = getPrecisionForCurrentCompany($scope.currentCompany.value);
            show();
        }

        function getCurrencyForCurrentCompany(currentCompany) {
            var currency;
            if (currentCompany !== undefined && currentCompany !== null) {
                for (var i = 0; i < $scope.companies.length; i++) {
                    if ($scope.companies[i].value === currentCompany) {
                        currency = $scope.companies[i].dictionaryProperties.currency;
                        break;
                    }
                }
            }
            return currency;
        }

        function getPrecisionForCurrentCompany(currentCompany) {
            var currency;
            if (currentCompany !== undefined && currentCompany !== null) {
                for (var i = 0; i < $scope.companies.length; i++) {
                    if ($scope.companies[i].value === currentCompany) {
                        precision = $scope.companies[i].dictionaryProperties.precision;
                        break;
                    }
                }
            }
            return precision;
        }

        function onYearChange() {
            $scope.$watch('currentYear', function() {
                if (isUpload === false) {
                    toggleCurrencyLoader();
                    if (typeof $scope.currentYear == "number") {
                        if ($scope.currentYear < 1970) {
                            $scope.isValidYear = false;
                        } else if ($scope.currentYear > 3000) {
                            $scope.isValidYear = false;
                        } else {
                            $scope.isValidYear = true;
                        }
                    } else {
                        var year = parseInt($scope.currentYear);
                        if (!isNaN(year)) {
                            if ($scope.currentYear < 1970) {
                                $scope.isValidYear = false;
                            } else if ($scope.currentYear > 3000) {
                                $scope.isValidYear = false;
                            } else {
                                $scope.isValidYear = true;
                            }
                        } else {
                            $scope.isValidYear = false;
                        }
                    }
                    show();
                } else {
                    isUpload = false;
                }
            });
        }

        function onPrecisionChange() {
            $scope.$watch('currentPrecision', function() {
                managePrecision();
                //Dictionary lService.savePrecision($scope.currentPrecision);
            });
        }

        function managePrecision() {
//            spread.isPaintSuspended(true);
        	spread.suspendPaint();
            var sheet = spread.getActiveSheet();
            var rows = sheet.getRowCount();
            var cols = sheet.getColumnCount();
            var formatting = "##############.";

            for (var i = 1; i < $scope.currentPrecision; i++) {
                formatting += "#";
            }
            if ($scope.currentPrecision !== 0) {
                formatting += "0";
            }
            for (var i = 0; i < rows; i++) {
                for (var j = 0; j < cols; j++) {
                    sheet.setFormatter(i, j, formatting);
                }
            }
//            spread.isPaintSuspended(false);
            spread.resumePaint();
        }

        function toggleCurrencyLoader() {
            var currencyLoader = CurrencyService.getLoader();
            currencyLoader.isCurrenciesLoaded = false;
        }

        function getDictionaries() {
            DictionaryService.getDictionaries("currency").then(function(data) {
                dictionaries = data;
                manageDictionaryHeaders();
            });
        }

        function manageDictionaryHeaders() {
            if (angular.isDefined(spread) && angular.isDefined(dictionaries)) {
                //dictionaries.sort(function(dict1, dict2) {return dict1.rowIndex - dict2.rowIndex});
                var sheet = spread.getActiveSheet();
                sheet.setRowCount(dictionaries.length);
                for (var i = 0; i < dictionaries.length; i++) {
                    sheet.setValue(i, 0, dictionaries[i].value, GC.Spread.Sheets.SheetArea.rowHeader);
                }
            }
        }

        function managePeriodHeaders() {
            if (angular.isDefined(spread)) {
                var sheet = spread.getActiveSheet();
                sheet.setColumnCount(periods.length);
                for (var i = 0; i < periods.length; i++) {
                    sheet.setValue(0, i, periods[i].name, GC.Spread.Sheets.SheetArea.colHeader);
                }
            }
        }

        function getCompanies() {
            DictionaryService.getDictionaries("company").then(function(companies) {
                $scope.companies = angular.copy(companies)
            });
        }

        function increaseYear($event) {
            if ($scope.currencyLoader.isCurrenciesSaving) {
                return;
            }
            $event.stopPropagation();
            $scope.currentYear++;
        }

        function decreaseYear($event) {
            if ($scope.currencyLoader.isCurrenciesSaving) {
                return;
            }
            $event.stopPropagation();
            $scope.currentYear--;
        }


        function increasePrecision($event) {
            if ($scope.currencyLoader.isCurrenciesSaving) {
                return;
            }
            $event.stopPropagation();
            $scope.currentPrecision++;
            updatePrecision($scope.currentPrecision);
        }

        function decreasePrecision($event) {
            if ($scope.currencyLoader.isCurrenciesSaving) {
                return;
            }
            $event.stopPropagation();
            if ($scope.currentPrecision > 1) {
                $scope.currentPrecision--;
            }
            updatePrecision($scope.currentPrecision);
        }

        function updatePrecision(precision) {
            if (angular.isDefined($scope.currentCompany.dictionaryProperties)) {
                $scope.currentCompany.dictionaryProperties.precision = precision;

                var copy = angular.copy($scope.companies);
                DictionaryService.saveDictionaries(copy, "company").then(function(data) {});
            }
        }


        function show() {
            $scope.isValid = true;
            editedRanges.length = 0;

            if (angular.isDefined($scope.currentCompany.value) && $scope.isValidYear === true) {
                CurrencyService.getCurrencies($scope.currentCompany.value, $scope.currentYear).then(function(currencies) {
                    $scope.currencies = currencies;
                    manageCurrencyCells();
                    DrawingService.redraw();
                });
            }
        }

        function manageCurrencyCells() {
//            spread.isPaintSuspended(true);
        	spread.suspendPaint();
            var sheet = spread.getActiveSheet();
            sheet.options.isProtected = (false);

            spread.options.highlightInvalidData = true;
//            var dv = GC.Spread.Sheets.DataValidation.createNumberValidator($.wijmo.wijspread.ComparisonOperator.Between, 0.0000, 100000000000000.0000);
            var dv = GC.Spread.Sheets.DataValidation.createNumberValidator(GC.Spread.Sheets.ConditionalFormatting.ComparisonOperators.Between, 0.0000, 100000000000000.0000);
            
            dv.showInputMessage = true;
            dv.inputMessage = "How many of this currency = 1 of base currency?";
            dv.errorMessage = "Enter a number between '0.0000' and '100000000000000.0000'.";
            //dv.inputTitle = "Tip";

            for (var j = 0; j < dictionaries.length; j++) {
                var dictionary = dictionaries[j];

                for (var i = 0; i < periods.length; i++) {
                    var period = periods[i];

                    var cell = sheet.getCell(j, i);
                    cell.value(null);
                    cell.formula(null);
                    cell.text(null);
                    for (var k = 0; k < $scope.currencies.length; k++) {
                        var currency = $scope.currencies[k];

                        if (currency.currency == dictionary.value && currency.period == period.id) {
                            if (currency.fieldValue !== null) {
                                if (currency.fieldValue.toString().charAt(0) === "=") {
                                    cell.formula(currency.fieldValue);
                                } else {
                                    cell.value(parseFloat(currency.fieldValue));
                                }
                            } else {

                                cell.value(currency.fieldValue);

                            }
                        }
                    }
                }
//                sheet.getRow(j).dataValidator(dv);
                sheet.getCell(j,-1).validator(dv);
            }
//            spread.isPaintSuspended(false);
            spread.resumePaint();
        }

        function updateCurrencyRow(data) {
//            spread.isPaintSuspended(true);
        	spread.suspendPaint();

            if (data.editedDictionaries.length > 0) {
                CurrencyService.updateCurrencyName(data.editedDictionaries);
                for (var i = 0; i < $scope.currencies.length; i++) {
                    var currency = $scope.currencies[i];
                    for (var j = 0; j < data.editedDictionaries.length; j++) {
                        var editedCurrency = data.editedDictionaries[j];
                        if (currency.currency === editedCurrency.oldValue.toUpperCase()) {
                            if (editedCurrency.newValue !== "null") {
                                currency.currency = editedCurrency.newValue.toUpperCase();
                                j = data.editedDictionaries.length;
                            } else if (editedCurrency.newValue === "null") {
                                $scope.currencies.splice(i, 1);
                                j = data.editedDictionaries.length;
                                i--;
                            }
                        }
                    }
                }
            }

            dictionaries.sort(function(dict1, dict2) {
                return dict1.rowIndex - dict2.rowIndex
            });
            manageDictionaryHeaders();
            manageCurrencyCells();
            toggleCurrencyLoader();

//            spread.isPaintSuspended(false);
            spread.resumePaint();
        }

        function addSpreadEvents() {
            //          spread.bind($.wijmo.wijspread.Events.ValueChanged, function(e, info) {
            //              handleValueChanged(info);
            //          });

            spread.bind(GC.Spread.Sheets.Events.EditEnd, function(e, info) {
                handleValueChanged(info);
            });

            angular.element(window).on('keydown', function(event) {
                if (event.keyCode === 46) { // Del
                    handleDelKeyDown();
                }
            });

            spread.bind(GC.Spread.Sheets.Events.DragFillBlockCompleted, function(e, info) {
                handleDragFillBlockCompleted(info);
            });

            spread.bind(GC.Spread.Sheets.Events.DragDropBlockCompleted, function(e, info) {
                handleDragDropBlockCompleted(info);
            });

            spread.bind(GC.Spread.Sheets.Events.ClipboardPasted, function(e, info) {
                handleClipboardPasted(info);
            });

            spread.bind(GC.Spread.Sheets.Events.ValidationError, function(event, data) {
                handleValidationError(data);
            });
        }

        function handleValidationError(data) {
            var dv = data.validator;
            if (dv) {
                // default
                data.validationResult = GC.Spread.Sheets.DataValidation.DataValidationResult.forceApply;
                // // finish editing, and restor the original value
                // data.validationResult = $.wijmo.wijspread.DataValidationResult.Discard;
                // // keep editing until enter a valid value
                // data.validationResult = $.wijmo.wijspread.DataValidationResult.Retry;
            }
        }

        function isValueValid(value) {
            if (value === null || value === "") {
                return true;
            }
            var pattern = /^[+-]?\d+(\.\d+)?$/gi;
            var res = pattern.test(value);
            if (res === false) {
                return false;
            }
            var temp = parseFloat(value);
            return (temp > 0 && temp < 100000000000000);
        }

        function manageEditedRanges(range) {
            manageSavingLabel();

            editedRanges.push(range);
            $timeout.cancel(delay);
            delay = $timeout(function() {
                manageEditedCurrencies(editedRanges);
            }, 3000);
        }

        function manageSavingLabel() {
            $scope.isValid = true;
            $scope.currencyLoader.isCurrenciesSaved = false;
            $scope.currencyLoader.isCurrenciesSaving = true;
            if (!$scope.$$phase) {
                $scope.$apply('currencyLoader');
            }
        }

        function manageEditedCurrencies(ranges) {
            var sheet = spread.getActiveSheet();
            var isValid = true;

            var editedCurrencies = [];
            for (var i = 0; i < ranges.length; i++) {
                var range = ranges[i];

                for (var j = range.row; j < range.row + range.rowCount; j++) {
                    for (var k = range.col; k < range.col + range.colCount; k++) {
                        var cell = sheet.getCell(j, k);
                        var value = cell.value();
                        if (value === "") {
                            value = null;
                        }
                        var text = cell.text();
                        var formula = cell.formula();
                        if (cell.formula() !== null) {
                            value = "=" + cell.formula();
                        }
                        if (isValueValid(cell.value()) === false) {
                            isValid = false;
                            break;
                        }

                        var dictionary = dictionaries[j];
                        var period = periods[k];

                        var editedCurrency = null;
                        var currency = null;
                        for (var m = 0; m < $scope.currencies.length; m++) {
                            currency = $scope.currencies[m];
                            if (currency.currency === dictionary.value && currency.period === period.id && currency.year === parseInt($scope.currentYear)) {
                                editedCurrency = currency;
                                break;
                            }
                        }

                        if (editedCurrency) {
                            editedCurrency.fieldValue = value;
                        } else {
                            editedCurrency = {
                                currencyUUID: null,
                                year: $scope.currentYear,
                                period: period.id,
                                company: $scope.currentCompany.value,
                                currency: dictionary.value,
                                fieldValue: value
                            };
                        }

                        var ifInEditedCurrencies = false;
                        for (m = 0; m < editedCurrencies.length; m++) {
                            currency = editedCurrencies[m];
                            if (currency.currency === dictionary.value && currency.period === period.id) {
                                ifInEditedCurrencies = true;
                                break;
                            }
                        }
                        if (!ifInEditedCurrencies && (editedCurrency.currencyUUID !== null || editedCurrency.fieldValue !== null)) {
                            editedCurrencies.push(editedCurrency);
                        }
                    }
                }
            }
            if (isValid) {
                editedRanges.length = 0;
                if (editedCurrencies.length !== 0) {
                    CurrencyService.saveCurrencies(editedCurrencies).then(function(data) {
                        $scope.currencyLoader.isCurrenciesSaving = false;

                        $timeout(function() {
                            $scope.currencyLoader.isCurrenciesSaved = false;
                        }, 1500);
                    });
                } else {
                    $scope.currencyLoader.isCurrenciesSaving = false;
                }
            } else {
                editedRanges.length = 0;
                $scope.currencyLoader.isCurrenciesSaving = false;
                $scope.isValid = false;
            }
        }

        function handleDragDropBlockCompleted(info) {
            var toRange = new GC.Spread.Sheets.Range(info.toRow, info.toCol, info.rowCount, info.colCount);
            var fromRange = new GC.Spread.Sheets.Range(info.fromRow, info.fromCol, info.rowCount, info.colCount);
            manageEditedRanges(toRange);
            manageEditedRanges(fromRange);
        }

        function handleDragFillBlockCompleted(info) {
            manageEditedRanges(info.fillRange);
        }

        function handleClipboardPasted(info) {
            manageEditedRanges(info.cellRange);
        }

        function handleDelKeyDown() {
            var sheet = spread.getActiveSheet();
            var selectedRanges = sheet.getSelections();
            for (var i = 0; i < selectedRanges.length; i++) {
                var range = selectedRanges[i];
                manageEditedRanges(range);
            }
        }

        function handleValueChanged(info) {
            var range = new GC.Spread.Sheets.Range(info.row, info.col, 1, 1);
            manageEditedRanges(range);
        }

        function resetView() {
            var sheet = spread.getActiveSheet();
            for (var j = 0; j < periods.length; j++) {
                for (var k = 0; k < dictionaries.length; k++) {
                    sheet.getCell(k, j).value(null);
                }
            }
            sheet.options.isProtected = (true);
        }

        function addCurrency() {
            var modalInstance = $modal.open({
                template: '<dictionary-editor modal="modal" type="type" enable-insert="enableInsert" enable-actions="enableActions" enable-order="enableOrder" enable-description="enableDescription"> </dictionary-editor>',
                windowClass: 'dictionary-editor-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modal = $modalInstance;
                        $scope.type = "currency";
                        ///////////////////// Properties to manage dictionary ///////////
                        $scope.enableInsert = true;
                        $scope.enableActions = true; // enable/disable buttons: 'edit', 'delete' and 'save'
                        $scope.enableOrder = true;
                        $scope.enableDescription = true;
                        $scope.close = function() {
                            $modalInstance.close();
                        };
                    }
                ]
            });

            modalInstance.result.then(function(data) {
                updateCurrencyRow(data);
            }, function() {

            });
        }

        function isValidCompany(company) {
            for (var i = 0; i < $scope.companies.length; i++) {
                if (parseInt($scope.companies[i].value) === company) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Uploads data from uploaded file
         */
        function upload(files) {
            if (files.length > 0) {
                var file = files[0];
                CurrencyService.upload(file).then(function(data) {
                    if (data.year === 0 || data.company === 0 || isValidCompany(data.company) === false) {
                        $scope.isValid = false;
                        console.log("imported file hasn't got enough informations");
                    }
                    if ($scope.isValid) {
                        isUpload = true;
                        var company = CoreCommonsService.findElementByKey($scope.companies, data.company.toString(), "value");
                        $scope.currentCompany = company;
                        $scope.currentYear = data.year;
                        $scope.isValid = true;
                        editedRanges.length = 0;
                        $scope.currencies = data.currencies;
                        manageCurrencyCells();
                        DrawingService.redraw();
                        var sheet = spread.getActiveSheet();
                        var range = new GC.Spread.Sheets.Range(0, 0, sheet.getRowCount(), sheet.getColumnCount());
                        manageEditedRanges(range);
                    }
                });
            }
        }

        function manageCompany() {
            var modalInstance = $modal.open({
                template: '<dictionary-editor modal="modal" type="type" enable-insert="enableInsert"  enable-actions="enableActions" enable-order="enableOrder" enable-description="enableDescription" enable-properties="enableProperties"></dictionary-editor>',
                windowClass: 'dictionary-editor-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modal = $modalInstance;
                        $scope.type = "company";
                        ///////////////////// Properties to manage dictionary ///////////
                        $scope.enableInsert = false;
                        $scope.enableActions = false; // enable/disable buttons: 'edit', 'delete' and 'save'
                        $scope.enableOrder = true;
                        $scope.enableDescription = true;
                        $scope.enableProperties = true;
                        $scope.close = function() {
                            $modalInstance.close();
                            //manageDictionaryHeaders();
                        };
                    }
                ]
            });

            modalInstance.result.then(function(data) {
                //update(data);
            }, function() {

            });
        }
        function logout() {
            window.location = url + "/logout.do";
        }
        
        function closeTab(){
            CoreCommonsService.closeTab($timeout);
        }
    }

})();