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
        .module('flatFormEditorApp.settings')
        .controller('SettingsController', SettingsController);

    /* @ngInject */
    function SettingsController($rootScope,$scope, $modal, DataService, CoreCommonsService, SpreadSheetService) {

		DataService.getModels();
		DataService.getDataTypes();
		$scope.openWorkbookProperties = openWorkbookProperties;
		$scope.openWorksheetProperties = openWorksheetProperties;
		$scope.manageUsers = manageUsers;
		$scope.toggleLock = toggleLock;
		SpreadSheetService.getLockFlag(FLAT_FORM_ID,function(data){
			var flag = data.data.flag=='Y';
			$scope.lockState = flag;
		});

		/************************************************** IMPLEMENTATION *************************************************************************/
		
		function openWorkbookProperties() {
			var modalInstance = $modal.open({
				template: '<workbook-properties id="workbook-properties"></workbook-properties>',
				windowClass: 'softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.$on('WorkbookProperties:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		}

		function openWorksheetProperties() {
			var currentSpreadSheet = CoreCommonsService.findElementByKey($scope.spread.sheets, $scope.spread.getActiveSheet().getName(), "_name");
			var modalInstance = $modal.open({
				template: '<worksheet-properties id="worksheet-properties"></worksheet-properties>',
				windowClass: 'softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.currentSpreadSheet = currentSpreadSheet;
						$scope.$on('WorksheetProperties:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		}
		function manageUsers() {
			var modalInstance = $modal.open({
				template: '<manage-users id="manage-users"></manage-users>',
				windowClass: 'softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.$on('ManageUsers:close', function(event, args) {
							$modalInstance.close();
						})
					}
				]
			});
		}
		function toggleLock(){
			$scope.lockState = $scope.lockState==undefined?false:$scope.lockState;
			$scope.lockState = !$scope.lockState;
			SpreadSheetService.toggleLockFlag(FLAT_FORM_ID,$scope.lockState,function(response){
				 swal("Success!", "Lock Flags Saved", "success");
			});
		}
	}
})()