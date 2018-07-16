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
        .module('app.exportFile')
        .controller('ExportFileController', ExportFileController);

    /* @ngInject */
    function ExportFileController($scope, $rootScope, $modal, ExportFileService, SpreadSheetService,
        ContextVariablesService, ProfileService) {

        $rootScope.$on("ExportFileController:exportPdf",function(event,arg){
            var profile = ProfileService.selectedProfile;
				var dim0Id = ContextVariablesService.dim0Id;
				var dim1Id = ContextVariablesService.dim1Id;
				var dim2Id = ContextVariablesService.dim2Id;
				var dataType = ContextVariablesService.dataType;
				
				var url = $BASE_PATH + 'reviewBudget/exportToPdf?topNodeId=' + TOP_NODE_ID + '&modelId=' + MODEL_ID + '&budgetCycleId=' + BUDGET_CYCLE_ID + '&dataEntryProfileId=' + profile.profileId + '&dim0=' + dim0Id + '&dim1=' + dim1Id + '&dim2=' + dim2Id + '&dataType=' + dataType + '&profileName=' + profile.name;
				
				// set animated icon
				$("#pdfExport").attr('class', 'fa fa-refresh fa-spin');
				// start downloading the excel file
				
				var test = $scope.selectedProfile.profileId;
				$.ajax({
					url:url,
					type:"POST",
					success:function(data){
						var jsonData = JSON.parse(data);
				        $("#pdfExport").attr('class', 'fa fa-upload');
				            var link = document.createElement("a");
				            link.href = jsonData.url;
				            link.download = jsonData.fileName;
				            link.target="_blank";
				            document.body.appendChild(link);
				            link.click();
				            document.body.removeChild(link);
				            $rootScope.$broadcast('mainMenuController:exportFinished');
					}
				})
			})
	        $rootScope.$on("ExportFileController:exportPpt",function(event,arg){
	            var profile = ProfileService.selectedProfile;
					var dim0Id = ContextVariablesService.dim0Id;
					var dim1Id = ContextVariablesService.dim1Id;
					var dim2Id = ContextVariablesService.dim2Id;
					var dataType = ContextVariablesService.dataType;
					
					var url = $BASE_PATH + 'reviewBudget/exportToPpt?topNodeId=' + TOP_NODE_ID + '&modelId=' + MODEL_ID + '&budgetCycleId=' + BUDGET_CYCLE_ID + '&dataEntryProfileId=' + profile.profileId + '&dim0=' + dim0Id + '&dim1=' + dim1Id + '&dim2=' + dim2Id + '&dataType=' + dataType + '&profileName=' + profile.name;
					
					// set animated icon
					$("#pptExport").attr('class', 'fa fa-refresh fa-spin');
					// start downloading the excel file
					
					var test = $scope.selectedProfile.profileId;
					$.ajax({
						url:url,
						type:"POST",
						success:function(data){
							var jsonData = JSON.parse(data);
					        $("#pptExport").attr('class', 'fa fa-upload');
					            var link = document.createElement("a");
					            link.href = jsonData.url;
					            link.download = jsonData.fileName;
					            link.target="_blank";
					            document.body.appendChild(link);
					            link.click();
					            document.body.removeChild(link);
					            $rootScope.$broadcast('mainMenuController:exportFinished');
						}
					})
			})
			$rootScope.$on("ExportFileController:exportDoc",function(event,arg){
				var profile = ProfileService.selectedProfile;
				var dim0Id = ContextVariablesService.dim0Id;
				var dim1Id = ContextVariablesService.dim1Id;
				var dim2Id = ContextVariablesService.dim2Id;
				var dataType = ContextVariablesService.dataType;
				
				var url = $BASE_PATH + 'reviewBudget/exportToDoc?topNodeId=' + TOP_NODE_ID + '&modelId=' + MODEL_ID + '&budgetCycleId=' + BUDGET_CYCLE_ID + '&dataEntryProfileId=' + profile.profileId + '&dim0=' + dim0Id + '&dim1=' + dim1Id + '&dim2=' + dim2Id + '&dataType=' + dataType + '&profileName=' + profile.name;
				
				// set animated icon
				$("#docExport").attr('class', 'fa fa-refresh fa-spin');
				// start downloading the excel file
				
				var test = $scope.selectedProfile.profileId;
				$.ajax({
					url:url,
					type:"POST",
					success:function(data){
						var jsonData = JSON.parse(data);
				        $("#docExport").attr('class', 'fa fa-upload');
				            var link = document.createElement("a");
				            link.href = jsonData.url;
				            link.download = jsonData.fileName;
				            link.target="_blank";
				            document.body.appendChild(link);
				            link.click();
				            document.body.removeChild(link);
				            $rootScope.$broadcast('mainMenuController:exportFinished');
					}
				})
			})
    }
})();