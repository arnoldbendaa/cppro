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
        .module('app.menus')
        .controller('MainMenuController', MainMenuController);

    /* @ngInject */
    function MainMenuController($rootScope, $scope, $timeout,$modal, SpreadSheetService, SessionService, CoreCommonsService) {

        $scope.closeTab = closeTab;
        $scope.showHideHeadings = showHideHeadings;
        $scope.logout = logout;
        $scope.undo = undo;
        $scope.recalculate = recalculate;
        $scope.exportFile = exportFile;
        $scope.openRespAreaManager = openRespAreaManager;
        $scope.openNoteManager = openNoteManager;
        $scope.openProfileManager = openProfileManager;
        $scope.headingsStatus = 'Show headings';
        $scope.redo = redo;
        $scope.exportPptFile = exportPptFile;
        $scope.exportPdfFile = exportPdfFile;
        $scope.exportDocFile = exportDocFile;
        $scope.search = search;
        //block button for note manager
        $scope.isManageNotesDisabled = SpreadSheetService.isManageNotesDisabled;

        // block button for recalculate
        $scope.isRecalculateDisabled = true;

        // block button for export
        $scope.isExportDisabled = false;

        // block button for undo
        $scope.isUndoDisabled = true;

        // block button for redo
        $scope.isRedoDisabled = true;

        //block save button
        $scope.isSpreadWasChanged = SpreadSheetService.isSpreadWasChanged;
        $scope.searchKey="";

        var url = $BASE_PATH;
        var areHeadingsVisible = false;



        /************************************************** IMPLEMENTATION *************************************************************************/

        function openProfileManager() {
            $rootScope.$broadcast('ProfilesController:openManager', {});
        }

        function openNoteManager() {
            $rootScope.$broadcast('spreadSheetController:openNotesManager', {});
        }

        function openRespAreaManager() {
            $rootScope.$broadcast('ResponsibilityAssignmentsController:openView', {});
        }

        function exportFile(param) {
            $scope.isExportDisabled = true;
            $rootScope.$broadcast('ExportFileController:export', param);
        }

        /**
         * Recalculates form. Invoke function from spreadsheetService to recalculate (in spreadModule.js)
         * @return void
         */
        function recalculate() {
            if ($scope.isRecalculateDisabled) {
                return;
            }

            angular.element('#veil-informations').find('h1').html('Saving data, please wait...');
            $rootScope.$broadcast('veil:show');
            SpreadSheetService.saveWorkbook();
        }

        /**
         * Undo action
         */
        function undo() {
            SpreadSheetService.undo();
            updateUndoRedo();
        }

        /**
         * Redo action
         */
        function redo() {
            SpreadSheetService.redo();
            updateUndoRedo();
        }

        /**
         * Logout from applcation
         */
        function logout() {
            window.location = url + "/logout.do";
        }

        function closeTab() {
            CoreCommonsService.closeTab($timeout);
        }

        /**
         * [showHideHeadings description]
         * @return {[type]} [description]
         */
        function showHideHeadings() {
            areHeadingsVisible = !areHeadingsVisible;
            if (areHeadingsVisible) {
                $scope.headingsStatus = "Hide headings";
            } else {
                $scope.headingsStatus = "Show headings";
            }
            SpreadSheetService.toggleHeaders(areHeadingsVisible);
        }


        /************************************************** PRIVATE MEMBERS *********************************************************************/


        function setSpreadWasChanged() {
            $scope.isSpreadWasChanged = SpreadSheetService.isSpreadWasChanged;
            updateUndoRedo();
        }
        /**
         * Update undo and redo buttons
         */
        function updateUndoRedo() {
            $scope.isUndoDisabled = SpreadSheetService.isUndoDisabled;
            $scope.isRedoDisabled = SpreadSheetService.isRedoDisabled;
        }
        function exportPptFile(param){
//        	html2canvas(document.body).then(function(canvas) {
//                angular.element('#veil-informations').find('h1').html('Saving data, please wait...');
//                $rootScope.$broadcast('veil:show');
//
//        		var img    = canvas.toDataURL("image/png");
//        		var fileName = $scope.selectedProfile.profileId;
//        		var test = $scope.selectedProfile.profileId;
//        		$.ajax({
//                	url:localStorage["cp_base_url"]+"saveSlideImage",
//                	type:"POST",
//                	data:{
//                		img:img,
//                		fileName:fileName
//                	},
//                	success:function(data){
//                		$rootScope.$broadcast('veil:hide', {});
//                		var jsonData = JSON.parse(data);
//                		if(jsonData.errCode=="0"){
//                            var a = document.createElement("a");
//                            a.href = jsonData.pptUrl;
//                            document.body.appendChild(a);
//                            a.click();
//
//                		}
//                			
//                	}
//                })
//
//        	});
        	$scope.isExportDisabled = true;
        	$rootScope.$broadcast('ExportFileController:exportPpt', param);
        	
        }
        function exportPdfFile(param){
            $scope.isExportDisabled = true;
            $rootScope.$broadcast('ExportFileController:exportPdf', param);

        }
        function exportDocFile(param){
        	$scope.isExportDisabled = true;
        	$rootScope.$broadcast('ExportFileController:exportDoc', param);
        }
        function search(){
        	var searchCondition = new GC.Spread.Sheets.Search.SearchCondition();
        	searchCondition.searchString = $scope.searchKey;
        	searchCondition.searchOrder = GC.Spread.Sheets.Search.SearchOrder.nOrder;

        	searchCondition.searchTarget =  GC.Spread.Sheets.Search.SearchFoundFlags.cellText;
        	searchCondition.searchFlags = GC.Spread.Sheets.Search.SearchFlags.ignoreCase| GC.Spread.Sheets.Search.SearchFlags.useWildCards;
        	var searchresult= SpreadSheetService.spreadSheet.search(searchCondition);
        	var str ="[searchFoundFlag:"+ searchresult.searchFoundFlag+",\r\n foundSheetIndex:"+searchresult.foundSheetIndex+",foundRowIndex:" +
        	searchresult.foundRowIndex+", foundColumnIndex:"+searchresult.foundColumnIndex+", foundString:"+searchresult.foundSheetIndex+"]";
//        	alert(str);]
        	if(searchresult.foundRowIndex>-1 && searchresult.foundColumnIndex>-1)
        		SpreadSheetService.spreadSheet.getActiveSheet().setActiveCell(searchresult.foundRowIndex,searchresult.foundColumnIndex);
        	else 
        		swal("Not Found");
        	
        }
        /************************************************** EVENTS *************************************************************************/

        /**
         * @return {[type]} [description]
         */
        $scope.$on('SpreadSheetService:isSpreadWasChanged', function() {
            setTimeout(function() {
                $scope.$apply(setSpreadWasChanged());
            }, 1);
        });
        /**
         * [description]
         * @return {[type]} [description]
         */
        $scope.$on('SpreadSheetService:IsManageNotesDisabled', function() {
            $scope.isManageNotesDisabled = SpreadSheetService.isManageNotesDisabled;
            $scope.$apply();
        });
        /**
         * Handles if isRecalulated property has changed
         * @return void
         */
        $scope.$on('SpreadSheetService:dataUpdated', function() {
            $scope.isRecalculateDisabled = SpreadSheetService.isRecalculateDisabled;
        });
        /**
         * Enables export button after action
         */
        $scope.$on('mainMenuController:exportFinished', function() {
            $scope.isExportDisabled = false;
            $scope.$apply();
        });
    }
})();