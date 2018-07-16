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
        .controller('SpreadSheetController', SpreadSheetController);

    /* @ngInject */
    function SpreadSheetController($rootScope, $scope, SpreadSheetService,
        FinanceFormService, ExcelFormService, CellService, ProfileService, ContextVariablesService) {

        var spreadSheetElement = null;
        var spreadSheet = null;

        $scope.openNotesManager = openNotesManager;
        $scope.loadData = loadData;
        $scope.addEvents = addEvents;

        activate();
        
        
        $rootScope.$on('ExportFileController:export', function(event, args) {

            var profile = ProfileService.selectedProfile;
            // set animated icon
            $("#excelExport").attr('class', 'fa fa-refresh fa-spin');
           
            var fileName = profile.name+".xlsx";
            var json = JSON.stringify(spreadSheet.toJSON());
            var excelIO = new GC.Spread.Excel.IO();
            setTimeout(function(){
                excelIO.save(json,function(blob){
                	saveAs(blob,fileName);
                	$("#excelExport").attr('class', 'fa fa-upload');
                	$rootScope.$broadcast('mainMenuController:exportFinished');
                },function(e){
                	console.log(e);
                })
            },200);
            
        });

//        $rootScope.$on("ExportFileController:exportPdf",function(event,arg){
//        	var profile = ProfileService.selectedProfile;
//        	var url = $BASE_PATH + 'reviewBudget/exportToPdf?profileName=' + profile.name;
//        	 $("#pdfExport").attr('class', 'fa fa-refresh fa-spin');
//            var fileName = profile.name+".pdf";
//            var json = JSON.stringify(spreadSheet.toJSON());
//            var excelIO = new GC.Spread.Excel.IO();
//            excelIO.save(json,function(blob){
//                var form = new FormData();
//                var fileOfBlob = new File([blob], 'aFileName.xlsx');
//                form.append("upload", fileOfBlob);
//            	$.ajax({
//            	    type: 'POST',
//            	    url: url,
//            	    data: form,
//            	    processData: false,
//            	    contentType: false
//            	}).done(function(data) {
//            		var jsonData = JSON.parse(data);
//            		$("#pdfExport").attr('class', 'fa fa-upload');
//                    var link = document.createElement("a");
//                    link.href = jsonData.url;
//                    link.download = jsonData.fileName;
//                    link.target="_blank";
//                    document.body.appendChild(link);
//                    link.click();
//                    document.body.removeChild(link);
//                    $rootScope.$broadcast('mainMenuController:exportFinished');
//            	});
//
//            },function(e){
//            	console.log(e);
//            })
//
//        })
//        $rootScope.$on("ExportFileController:exportPpt",function(event,arg){
//        	var profile = ProfileService.selectedProfile;
//
//            var url = $BASE_PATH + 'reviewBudget/exportToPpt?profileName=' + profile.name;
//
//            // set animated icon
//            $("#pptExport").attr('class', 'fa fa-refresh fa-spin');
//            // start downloading the excel file
//            
//            
//            var fileName = profile.name+".pdf";
//            var json = JSON.stringify(spreadSheet.toJSON());
//            var excelIO = new GC.Spread.Excel.IO();
//            excelIO.save(json,function(blob){
//                var form = new FormData();
//                var fileOfBlob = new File([blob], 'aFileName.xlsx');
//                form.append("upload", fileOfBlob);
//
//            	$.ajax({
//            	    type: 'POST',
//            	    url: url,
//            	    data: form,
//            	    processData: false,
//            	    contentType: false
//            	}).done(function(data) {
//            		var jsonData = JSON.parse(data);
//                    $("#pptExport").attr('class', 'fa fa-upload');
//                        var link = document.createElement("a");
//                        link.href = jsonData.url;
//                        link.download = jsonData.fileName;
//                        link.target="_blank";
//                        document.body.appendChild(link);
//                        link.click();
//                        document.body.removeChild(link);
//                        $rootScope.$broadcast('mainMenuController:exportFinished');
//            	});
//
//            },function(e){
//            	console.log(e);
//            })
//        })
//        $rootScope.$on("ExportFileController:exportDoc",function(event,arg){
//            var profile = ProfileService.selectedProfile;
//			
//			var url = $BASE_PATH + 'reviewBudget/exportToDoc?profileName=' + profile.name;
//			
//			
//			
//			
//            // set animated icon
//            $("#docExport").attr('class', 'fa fa-refresh fa-spin');
//            // start downloading the excel file
//            
//            
//            var fileName = profile.name+".docx";
//            var json = JSON.stringify(spreadSheet.toJSON());
//            var excelIO = new GC.Spread.Excel.IO();
//            excelIO.save(json,function(blob){
//                var form = new FormData();
//                var fileOfBlob = new File([blob], 'aFileName.xlsx');
//                form.append("upload", fileOfBlob);
//
//            	$.ajax({
//            	    type: 'POST',
//            	    url: url,
//            	    data: form,
//            	    processData: false,
//            	    contentType: false
//            	}).done(function(data) {
//            		var jsonData = JSON.parse(data);
//                    $("#docExport").attr('class', 'fa fa-upload');
//                        var link = document.createElement("a");
//                        link.href = jsonData.url;
//                        link.download = jsonData.fileName;
//                        link.target="_blank";
//                        document.body.appendChild(link);
//                        link.click();
//                        document.body.removeChild(link);
//                        $rootScope.$broadcast('mainMenuController:exportFinished');
//            	});
//
//            },function(e){
//            	console.log(e);
//            })
//
            
            
			
//			// set animated icon
//			$("#docExport").attr('class', 'fa fa-refresh fa-spin');
//			// start downloading the excel file
//			
//			var test = $scope.selectedProfile.profileId;
//			$.ajax({
//				url:url,
//				type:"POST",
//				success:function(data){
//					var jsonData = JSON.parse(data);
//			        $("#docExport").attr('class', 'fa fa-upload');
//			            var link = document.createElement("a");
//			            link.href = jsonData.url;
//			            link.download = jsonData.fileName;
//			            link.target="_blank";
//			            document.body.appendChild(link);
//			            link.click();
//			            document.body.removeChild(link);
//	                    $rootScope.$broadcast('mainMenuController:exportFinished');
//
//						
//				}
//			})
//        })

        function activate() {
            /**
             * Document ready function, creating spread.js element.
             * Set style of spreasSheet
             */
            angular.element(document).ready(function() {
                spreadSheetElement = angular.element(document.querySelector('#spreadSheet'));
//                spreadSheet = spreadSheetElement.wijspread("spread");
                spreadSheet = new GC.Spread.Sheets.Workbook(document.getElementById("spreadSheet"));
                spreadSheet.useWijmoTheme = true;
                spreadSheet.repaint();
                spreadSheet.options.allowContextMenu = false;

                SpreadSheetService.setSpreadSheet(spreadSheet);

                $scope.$apply();
                $scope.addEvents();
                spreadSheet.options.allowContextMenu = false;

            });
        }

        /************************************************** IMPLEMENTATION *************************************************************************/


        /**
         * Open notes manager for active cell
         * @return {[type]} [description]
         */
        function openNotesManager() {
            var worksheetId = spreadSheet.getActiveSheetIndex(),
                activeSheet = spreadSheet.getActiveSheet(),
                target = SpreadSheetService.getCellPosition(),
                cell = activeSheet.getCell(target.row, target.col);

            $rootScope.$broadcast('NotesController:OpenNotesManager', {
                cellObject: cell.cellType(),
                Cell: {
                    row: target.row,
                    col: target.col
                },
                workbook: SpreadSheetService.workbook,
                worksheetId: worksheetId
            });
        }

        /**
         * Loading profile by specified data. Used:
         * - at the start application
         * - when profile was changed
         * - when contextual data was change
         */
        function loadData() {
            var profile = ProfileService.getSelectedProfile();
            //console.log("selected profile", profile);
            if (profile === null || profile === undefined) {
                //throw exception
                console.log("No profile selected");
                var error = "error";
                return error;
            }
            var formType = profile.formType;
            if (formType === "FINANCEFORM") {
                FinanceFormService.init();
            } else { //"XCELLFORM"
                ExcelFormService.init();
            }
        }

        function addEvents() {

            /**
             * Right click action listener. This funtion is creating the context menu.
             */
            spreadSheetElement.bind('contextmenu', function(e) {
                e.preventDefault();

                //Acquire cell index from mouse-clicked point of regular cells which are neither fixed rows/columns nor row/column headers.
                var offset = spreadSheetElement.offset();
                var x = e.pageX - offset.left;
                var y = e.pageY - offset.top;

                var sheet = SpreadSheetService.getActiveSheet();
                var target = sheet.hitTest(x, y);

                if (target &&
                    (target.rowViewportIndex === 0 || target.rowViewportIndex === 1) &&
                    (target.colViewportIndex === 0 || target.colViewportIndex === 1)) {

                    sheet.setActiveCell(target.row, target.col);
                    SpreadSheetService.setCellPosition(target.row, target.col);

                    $rootScope.$broadcast('showContextMenu', {
                        x: x,
                        y: y,
                        cell: {
                            row: target.row,
                            col: target.col
                        },
                        cellType: sheet.getCell(target.row, target.col).cellType()
                    });
                }
            });

            /**
             * This event is creating the note if cell has one.
             */
            spreadSheetElement.bind('click', function(e) {
                angular.element('#cell-note').hide();
                angular.element("#context-menu").hide();

                var offset = spreadSheetElement.offset(),
                    x = e.pageX - offset.left,
                    y = e.pageY - offset.top,
//                    target = spreadSheetElement.wijspread("spread").getActiveSheet().hitTest(x, y),
                    target = GC.Spread.Sheets.findControl(document.getElementById('spreadSheet')).getActiveSheet().hitTest(x,y),

                    activeSheet = spreadSheet.getActiveSheet(),
                    cell = activeSheet.getCell(target.row, target.col),
                    cellRect = activeSheet.getCellRect(target.row, target.col);

                getSumOfSelectedCellValues(activeSheet);

                SpreadSheetService.setCellPosition(target.row, target.col);
                if (CellService.ifCellTyleIsIOCellType(cell)) {

                    //Note Manager Button Enabled
                    SpreadSheetService.setIsManageNotesDisable(false);

                    if (CellService.ifCellCanHaveNote(cell)) {
                        //Left Top Corner Listener
                        if ((x > ((cellRect.x + cellRect.width) - 10) && x < cellRect.x + cellRect.width) && (y > cellRect.y && y < cellRect.y + 10)) {
                            if (CellService.ifCellCanHaveNote(cell)) {
                                $rootScope.$broadcast('NotesModule:openNote', {
                                    note: cell.cellType().notes.notes[0],
                                    x: cellRect.x + cellRect.width + 10,
                                    y: cellRect.y - 7
                                });
                            }
                        }
                    }
                } else {
                    SpreadSheetService.setIsManageNotesDisable(true);
                }
            });
        }

        function getSumOfSelectedCellValues(activeSheet) {
            //pobranie zaznaczonych komorek
            var selectedCells = activeSheet.getSelections();
            var numCols = selectedCells[0].colCount;
            var numRows = selectedCells[0].rowCount;
            var startCol = selectedCells[0].col;
            var startRow = selectedCells[0].row;
            var sum = 0;
            for (var c = startCol; c < startCol + numCols; c++) {
                if (activeSheet.getColumnVisible(c)) {
                    for (var d = startRow; d < startRow + numRows; d++) {
                        if (activeSheet.getRowVisible(d)) {
                            var cellValue = activeSheet.getCell(d, c).value();
                            if (!isNaN(cellValue)) {
                                sum += cellValue;
                            }
                        }
                    }
                }
            }
            $scope.sum = Math.round(sum).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }


        /**
         * Trigger function, this function is listener function,
         * Waiting for default profile id.
         */
        $scope.$on("spreadSheetController:loadSpreadSheet", function(event, args) {
            spreadSheet.unbind(GC.Spread.Sheets.Events.CellChanged);

            SpreadSheetService.setSpreadWasChanged(false);
            ContextVariablesService.setDimensions(args);
            spreadSheet.options.allowContextMenu = false;
            $scope.loadData();

        });

        $scope.$on("spreadSheetController:openNotesManager", function() {
            $scope.openNotesManager();
        });
    }
})();