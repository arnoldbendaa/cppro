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
        .directive('manageUsers', manageUsers);

    /* @ngInject */
    function manageUsers($rootScope, $modal, SpreadSheetService, $timeout, CoreCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'settings/views/manageUsers.html',
			// scope: {
			// 	id: '='
			// },
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".manage-users").closest(".modal-dialog");
					var elementToResize = $(".elementToResize");
					var additionalElementsToCalcResize = [$(".getToCalc1")];
					$scope.cookieName = "flatFormEditor_modal_manageUsers";
					// try to resize and move modal based on the cookie
					CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
					
					$timeout(function() { // timeout is necessary to pass asynchro
						CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
					}, 0);	
					$scope.flatForm = SpreadSheetService.getFlatForm();
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.addAvailableUser = addAvailableUser;
					$scope.selectUsers = selectUsers;
					$scope.deleteAvailableUser = deleteAvailableUser;
					$scope.save = save;
					$scope.close = close;
					
					/************************************************** IMPLEMENTATION *************************************************************************/					
					
					function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    };
                    
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    };
					
					$scope.$watch('flatForm.users', function(newValue, oldValue) {
						if (angular.isDefined($scope.flatForm) && angular.isDefined($scope.flatForm.users)) {
							$scope.users = angular.copy($scope.flatForm.users);
						}
					}, true);
					$scope.$watch('flatForm.availableUsers', function(newValue, oldValue) {
						if (angular.isDefined($scope.flatForm) && angular.isDefined($scope.flatForm.availableUsers)) {
							$scope.availableUsers = angular.copy($scope.flatForm.availableUsers);
						}
					}, true);

					//----------------------------- add/remove user -----------------------------

					$scope.selectedAvailableUser = null;

					// add selectedAvailableUser (=user) to Users and remove from availableUsers
					function addAvailableUser() {
						if ($scope.selectedAvailableUser) {
							var existsInUsers = CoreCommonsService.findElementByKey($scope.users, $scope.selectedAvailableUser.userId, "userId");
							// to not duplicate user in users
							if (existsInUsers == null) {
								// add to users
								$scope.users.push($scope.selectedAvailableUser);
								// delete from availableUsers
								var currentUser = CoreCommonsService.findElementByKey($scope.availableUsers, $scope.selectedAvailableUser.userId, "userId");
								var index = $scope.availableUsers.indexOf(currentUser);
								$scope.availableUsers.splice(index, 1);
							}
							$scope.selectedAvailableUser = null;
						}
					}

					var addManyAvailableUsers = function(selectedUsers) {
						angular.forEach(selectedUsers, function(user) {
							$scope.selectedAvailableUser = user;
							$scope.addAvailableUser();
						});
					};

					function selectUsers(reportId) {
						var users = angular.copy($scope.availableUsers);
						var modalInstance = $modal.open({
							template: '<select-users users="users"></select-users>',
							windowClass: 'softpro-modals',
							backdrop: 'static',
							size: 'lg',
							controller: ['$scope', '$modalInstance',
								function($scope, $modalInstance) {
									$scope.users = users;
									$scope.$on('SelectUsers:close', function(event, args) {
										$modalInstance.close();
									});
									$scope.$on('SelectUsers:ok', function(event, selectedUsers) {
										addManyAvailableUsers(selectedUsers);
										$modalInstance.close();
									});									
								}
							]
						});
					}

					// delete selectedAvailableUser (=user) from Users and add it to availableUsers
					function deleteAvailableUser(userId) {
						// find user in users
						var user = CoreCommonsService.findElementByKey($scope.users, userId, "userId");
						// add to availableUsers
						$scope.availableUsers.push(user);
						// delete from users
						var index = $scope.users.indexOf(user);
						$scope.users.splice(index, 1);
					};

					//----------------------------- end of add/remove user -----------------------------

					function save() {
						CoreCommonsService.askIfReload = true;
						angular.copy($scope.users, $scope.flatForm.users);
						angular.copy($scope.availableUsers, $scope.flatForm.availableUsers);
						$rootScope.$broadcast("ManageUsers:close");
					}

					/**
					 * After cancel editing/creating Report.
					 */
					function close() {
						$rootScope.$broadcast("ManageUsers:close");
					}

				}
			]
		};
	}
})();