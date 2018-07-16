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
        .service('SpreadSheetService', SpreadSheetService);

    /* @ngInject */
    function SpreadSheetService($rootScope, $http, $timeout, Upload, CoreCommonsService) {

        var self = this;
        var url = $BASE_PATH + 'flatFormEditor/';
        var flatForm = {};
        var isFlatFormLoaded = false;
        var isFlatFormLoading = false;
        var areAllFlatFormsLoaded = false;
        var areAllFlatFormsLoading = false;
        var allFlatForms = [];
        var loader = {
            isSaving: false
        };
        self.getLoader = getLoader;
        self.getFlatFormTest = getFlatFormTest;
        self.getFlatForm = getFlatForm;
        self.getAllFlatForms = getAllFlatForms;
        self.upload = upload;
        self.save = save;
        self.saveAs = saveAs;
        self.newFlatForm = newFlatForm;
        self.copyTemplate = copyTemplate;
        self.getActualCellRange = getActualCellRange;
        self.blockExcelView = blockExcelView;
        self.unblockExcelView = unblockExcelView;
        self.getHeadingsVisible = getHeadingsVisible;
        self.toggleHeaders = toggleHeaders;
        self.getGridLinesVisible = getGridLinesVisible;
        self.toggleGridlines = toggleGridlines;
        self.setSpreadParams = setSpreadParams;
        self.clearCells = clearCells;
        self.toggleLockFlag = toggleLockFlag;
        self.getLockFlag = getLockFlag;

        /************************************************** IMPLEMENTATION *************************************************************************/

        function getLoader() {
            return loader;
        }

        var getFlatFormFromDatabase = function() {
            isFlatFormLoaded = false;
            isFlatFormLoading = true;

            $http.get(url + 'flatForm/' + FLAT_FORM_ID, {ignoreLoadingBar: true}).success(function(data) {
                isFlatFormLoading = false;
                isFlatFormLoaded = true;
                angular.copy(data, flatForm);
            });
        };

        function getFlatFormTest() {
            var workbookTest = {
        	xmlForm: null
            };
            $http({
                method: "PUT",
                url: $BASE_PATH + "flatFormEditor/workbookTest",
                data: flatForm.xmlForm
            }).success(function(response) {
        	workbookTest.xmlForm = response;
            });
            return workbookTest;
        }

        function getFlatForm() {
            if (isFlatFormLoaded === false && isFlatFormLoading === false) {
                getFlatFormFromDatabase();
            }
            return flatForm;
        }

        // Return flat forms if cached, otherwise call getAllFlatFormsFromDatabase() method
        function getAllFlatForms(hardReload) {
            if ((!areAllFlatFormsLoaded && !areAllFlatFormsLoading) || hardReload) {
                getAllFlatFormsFromDatabase();
            }
            return allFlatForms;
        }

        // Load all flat forms from database
        var getAllFlatFormsFromDatabase = function() {
            areAllFlatFormsLoaded = false;
            areAllFlatFormsLoading = true;
            $http.get(url + "/flatForms").success(function(data) {
                areAllFlatFormsLoaded = true;
                areAllFlatFormsLoading = false;
                if (data && data.length > 0) {
                    angular.copy(data, allFlatForms);
                }
            });
        };

        function upload(file) {
            var promise = Upload.upload({
                url: url + "upload",
                method: 'POST',
                file: file,
                ignoreLoadingBar: true
            }).success(function(response) {
                flatForm.jsonForm = response.jsonForm;
                flatForm.xmlForm = response.xmlForm;
            }).error(function(response) {
                $rootScope.$broadcast('veil:hide', {});
            });
        }

        function save(spread) {
            loader.isSaving = true;
            $http.put(url + 'flatForm/', flatForm, {coverAllView: true, statement: "Saving data, please wait..."}).success(function(response) {
                var flatFormVisId = flatForm.visId;
                if (response.success) {
                    loader.isSaving = false;
                    CoreCommonsService.askIfReload = false;
                    var flatFormId = response.message;
                    if (flatFormId !== undefined && flatFormId !== null) {
                        // if it was new Flat Form saved for the first time - reload page to change flatFormId in jsp.
                        reloadPage(flatFormId, flatFormVisId, "new");
                    } else {
                        // if old FlatForm seved - unblockExcelView
                        //self.unblockExcelView(spread);
                        $rootScope.$broadcast("MainMenu:hide");
                    }
                } else {
//                    swal("Error", "Something went wrong with saving Flat Form \n (" + flatFormVisId + ").", "error");
                	 swal(response.title, response.message, "error");
                }
            });
        }

        function saveAs(newFlatForm) {
            $http.put(url + 'flatForm/saveAs/', newFlatForm, {coverAllView: true, statement: "Saving data, please wait..."}).success(function(response) {
                var flatFormVisId = newFlatForm.visId;
                if (response.success) {
                    $rootScope.$broadcast("SaveFlatFormAs:close");
                    var flatFormId = response.message;
                    reloadPage(flatFormId, flatFormVisId, "saveAs");
                } else if (response.error && response.message !== undefined) {
                    swal({
                        title: "Error",
                        text: response.message,
                        type: "error"
                    });
                } else {
//                    swal("Error", "Something went wrong with saving Flat Form \n (" + flatFormVisId + ").", "error");
                	swal(response.title, response.message, "error");
                }
            });
        }

        function newFlatForm() {
            var flatFormId = -1;
            // reload page with new flat form data
            var uri = $BASE_PATH + 'flatFormEditor/';
            var form = angular.element(
                '<form id="flatFormEditorPopUp" action="' + uri + '" method="post">' +
                '<input id="flatFormId" name="flatFormId" type="hidden" value="' + flatFormId + '">' +
                '</form>'
            );
            angular.element(document.body).append(form);
            form[0].submit();
            form.remove();
//            angular.element('#veil-informations').find('h1').text('Loading data, please wait...');
        }

        var reloadPage = function(flatFormId, flatFormVisId, type) {
            var confirmButtonText = "OK, Open it";
            if (type == "new") {
                confirmButtonText = "OK";
            }
            swal({
                title: "Success",
                text: "Flat Form (" + flatFormVisId + ") is saved.",
                type: "info",
                confirmButtonText: confirmButtonText
            }, function(isConfirm) {
                if (isConfirm) {
                    $rootScope.$broadcast("DocumentCompleted:false");
                    //$rootScope.$broadcast('veil:show', {});
                    // reload page with new flat form data
                    CoreCommonsService.askIfReload = false;
                    var uri = $BASE_PATH + 'flatFormEditor/';
                    var form = angular.element(
                        '<form id="flatFormEditorPopUp" action="' + uri + '" method="post">' +
                        '<input id="flatFormId" name="flatFormId" type="hidden" value="' + flatFormId + '">' +
                        '</form>'
                    );
                    angular.element(document.body).append(form);
                    form[0].submit();
                    form.remove();
                }
            });
        };

        function copyTemplate(flatFormIds) {
            $http.put(url + 'flatForm/copytemplate/', flatFormIds).success(function(response) {
                $rootScope.$broadcast("CopyTemplateTo:toggleLoader", false);
                if (response.success) {
                    swal({
                        title: "Success",
                        text: "Flat Form was copied.",
                        type: "info"
                    }, function(isConfirm) {
                        if (isConfirm) {
                            $rootScope.$broadcast("MainMenu:hide");
                            $rootScope.$broadcast("CopyTemplateTo:close");
                            $rootScope.$broadcast("CopyTemplateTo:resetData");
                        }
                    });
                } else {
                    swal("Error", "Something went wrong with copying Flat Form. \n" + response.message, "error");
                }
//                angular.element('#veil-informations').find('h1').text('Loading data, please wait...');
            });
        }

        function getActualCellRange(cellRange, rowCount, columnCount) {
            if (cellRange.row == -1 && cellRange.col == -1) {
                return new  GC.Spread.Sheets.Range(0, 0, rowCount, columnCount);
            } else if (cellRange.row == -1) {
                return new GC.Spread.Sheets.Range(0, cellRange.col, rowCount, cellRange.colCount);
            } else if (cellRange.col == -1) {
                return new GC.Spread.Sheets.Range(cellRange.row, 0, cellRange.rowCount, columnCount);
            }
            return cellRange;
        }

        function blockExcelView(spread) {
            if (!spread) {
                return;
            }
            spread.options.newTabVisible = false;
            spread.options.allowUserDragDrop = false;
            spread.options.allowUserDragFill = false;
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                sheet.options.isProtected = (true);
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
                sheet.options.isProtected = false;
            }
        }

        self.areHeadingsVisible = {};

        function getHeadingsVisible(spread) {
            if (Object.keys(self.areHeadingsVisible).length > 0) {
                return self.areHeadingsVisible;
            } else {
//                self.areHeadingsVisible.row = spread.getSheet(0).getRowHeaderVisible();
//                self.areHeadingsVisible.column = spread.getSheet(0).getColumnHeaderVisible();
            	self.areHeadingsVisible.row = spread.getSheet(0).options.rowHeaderVisible;
            	self.areHeadingsVisible.column = spread.getSheet(0).options.columnHeaderVisible;
            }
            return self.areHeadingsVisible;
        }

        function toggleHeaders(spread, type) {
            self.areHeadingsVisible[type] = !self.areHeadingsVisible[type];
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                if (type == "row") {
                    sheet.options.rowHeaderVisible = (self.areHeadingsVisible[type]);
                } else if (type == "column") {
                    sheet.options.colHeaderVisible = (self.areHeadingsVisible[type]);
                }
                spread.invalidateLayout();
                spread.repaint();
            }
        }

        self.areGridLinesVisible = {};

        function getGridLinesVisible(spread) {
            if (Object.keys(self.areGridLinesVisible).length > 0) {
                return self.areGridLinesVisible;
            } else {
                // get grid lines visibility for active sheet
//                self.areGridLinesVisible.row = spread.getActiveSheet().getGridlineOptions().showHorizontalGridline;
            	self.areGridLinesVisible.row = spread.getActiveSheet().options.gridline.showHorizontalGridline
//                self.areGridLinesVisible.column = spread.getActiveSheet().getGridlineOptions().showVerticalGridline;
            	self.areGridLinesVisible.column = spread.getActiveSheet().options.gridline.showVerticalGridline;
                // set this visibility to other sheets (it's important after first open FlatForm in new AdminApp)
                self.setSpreadParams(spread);
            }
            return self.areGridLinesVisible;
        }

        function toggleGridlines(spread, type) {
            self.areGridLinesVisible[type] = !self.areGridLinesVisible[type];
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
//                sheet.suspendPaint();
                if (type == "row") {
                	sheet.options.gridline.showHorizontalGridline = self.areGridLinesVisible[type];
                } else if (type == "column") {
                	sheet.options.gridline.showVerticalGridline = self.areGridLinesVisible[type];
                }
                spread.invalidateLayout();
                spread.repaint();
            }
        }

        function setSpreadParams(spread) {
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                sheet.options.rowHeaderVisible = (self.areHeadingsVisible.row);
                sheet.options.columnHeaderVisible = (self.areHeadingsVisible.column);
//                sheet.setGridlineOptions({
//                    showHorizontalGridline: self.areGridLinesVisible.row
//                });
//                sheet.setGridlineOptions({
//                    showVerticalGridline: self.areGridLinesVisible.column
//                });
                sheet.options.gridline = {showHorizontalGridline:self.areGridLinesVisible.row,showVerticalGridline:self.areGridLinesVisible.column};
            }
        }

        function clearCells(spread) {
            var sheet = spread.getActiveSheet();
            var sels = sheet.getSelections();
            sheet.suspendPaint ();
 //           sheet.isPaintSuspended(true);
            for (var n = 0; n < sels.length; n++) {
                var sel = self.getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                clearCellsInRange(spread, sel);
            }
//            sheet.isPaintSuspended(false);
            sheet.resumePaint();
        }

        function clearCellsInRange(spread, range) {
            var sheet = spread.getActiveSheet();
            sheet.clear(range.row, range.col, range.rowCount, range.colCount, GC.Spread.Sheets.SheetArea.viewport,  GC.Spread.Sheets.StorageType.Data);
            sheet.clear(range.row, range.col, range.rowCount, range.colCount, GC.Spread.Sheets.SheetArea.viewport,  GC.Spread.Sheets.StorageType.Style);
        }
        function toggleLockFlag(xml_form_id,enable,callback){
            $http.get(url + "flatForm/toggleLockFlag/"+xml_form_id+"/"+enable).success(function(data) {
            	callback(data);
            });
        }
        function getLockFlag(xml_form_id,callback){
        	$http.get(url+"flatForm/getLockFlag/"+xml_form_id).success(function(data){
        		callback(data);
        	})
        }
    }
})();