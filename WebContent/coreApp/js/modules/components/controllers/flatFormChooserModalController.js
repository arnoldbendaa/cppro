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
		.module('coreApp.components')
		.controller('FlatFormChooserModalController', FlatFormChooserModalController);

	/* @ngInject */
	function FlatFormChooserModalController($scope, $rootScope, $timeout, CoreCommonsService) {
		$scope.isFinanceCubeColumnVisible = angular.isDefined($scope.isFinanceCubeColumn) ? $scope.isFinanceCubeColumn : false;
		$scope.isLastUpdatedColumnVisible = angular.isDefined($scope.isLastUpdatedColumn) ? $scope.isLastUpdatedColumn : true;
		$scope.modalTitle = angular.isDefined($scope.title) ? $scope.title : "Choose Flat Form";
		$scope.modalInfo = angular.isDefined($scope.info) ? $scope.info : "";
		$scope.okBtnModalLabel = angular.isDefined($scope.okBtnLabel) ? $scope.okBtnLabel : "OK";
		$scope.cookieName = angular.isDefined($scope.cookieName) ? $scope.cookieName : "coreApp_modal_chooserModal";
		$scope.modalContext = null;

		$scope.isFlatFormChooserLoaded = false;
		$scope.selectedFlatForms = [];
		$scope.selectForm = {
		        isFlatFormChooserChanged : true
		};
		$scope.ok = ok;
		$scope.close = close;

		activate();


		/************************************************** IMPLEMENTATION *************************************************************************/


		function activate() {
			onModalContextChanged();
			if ($scope.isProfileModule === undefined){
			    $scope.selectForm.isFlatFormChooserChanged = false
			}
		}

		function onModalContextChanged() {
			$scope.$watch('modalContext', function() {
				if ($scope.modalContext) {
					var flex = $scope.modalContext.flex;
					if (flex) {
						//flex.onResizedColumn = resizedColumn;
						flex.onSortedColumn = sortedColumn;
					}
					initResize();
				}
			});
		}

		function ok() {
			$scope.modal.close($scope.selectedFlatForms);
			$rootScope.$broadcast("FlatFormChooserModal:close", $scope.selectedFlatForms);
		}

		function close() {
			$scope.modal.dismiss('cancel');
		}

		function resizedColumn(sender, args) {
			CoreCommonsService.resizedColumn(sender, $scope.cookieName);
		}

		function sortedColumn(sender, args) {
			CoreCommonsService.sortedColumn(sender, $scope.cookieName);
		}

		function initResize() {
			// parameters to resize modal
			var modalDialog = $(".flat-form-chooser-modal").closest(".modal-dialog");
			var elementToResize = $(".flat-form-chooser .flat-form-grid");
			var additionalElementsToCalcResize = [$(".flat-form-filter"), 10];
			// try to resize and move modal based on the cookie
			CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
			$timeout(function() { // timeout is necessary to pass asynchro
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.modalContext);
			}, 0);
		}

	}

})();