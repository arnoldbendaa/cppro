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
        .module('adminApp.profilesPage')
        .directive('profilesUnDeployment', profilesUnDeployment);

    /* @ngInject */
    function profilesUnDeployment($rootScope, $compile, Flash, ProfilesPageService, CoreCommonsService, $timeout) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'profiles/views/profilesUnDeployment.html',
			scope: {
				selectedFlatFormId: "=",
				selectedFlatFormVisId: "=",
				isMobile: "="
			},
			replace: true,
			controller: ['$scope',
				function($scope) {

					$timeout(function() {
						// parameters to resize modal
						var modalDialog = $(".flat-forms-un-deploy").closest(".modal-dialog");
						var elementToResize = $(".fc-panel");
						var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $("ul.nav"), $(".getToCalc3"), 30];
						$scope.cookieName = "adminPanel_modal_flatForm_unployed";
						// try to resize and move modal based on the cookie
						CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
						$scope.resizedColumn = function(sender, args) {
						    CoreCommonsService.resizedColumn(args, $scope.cookieName);
						};
						$scope.sortedColumn = function(sender, args) {
						    CoreCommonsService.sortedColumn(args, $scope.cookieName);
						};
						$timeout(function() { // timeout is necessary to pass asynchro
						    CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
						}, 0);
					}, 0);

					$scope.message = {
						subject: "Profile Deletion",
						content: "Your profiles for form: " + $scope.selectedFlatFormVisId + "  have been deleted by the administrator"
					}

					$scope.undeploy = function() {
						var flatForm = {
							flatFormId: $scope.selectedFlatFormId,
							subject: $scope.message.subject,
							messageText: $scope.message.content,
							mobile: $scope.isMobile
						}
						ProfilesPageService.undeployFlatForm(flatForm, $scope.selectedFlatFormVisId);
					}

					$scope.close = function() {
						$rootScope.$broadcast('profilesUnDeployment:close');
					};
				}
			]
		};
	}
})();