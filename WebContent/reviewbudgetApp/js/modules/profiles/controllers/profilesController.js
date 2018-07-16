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
		.module('app.profiles')
		.controller('ProfilesController', ProfilesController);

	/* @ngInject */
	function ProfilesController($rootScope, $scope, $http, $location, $modal,
		$log, ProfileService, FormListService, SpreadSheetService, $timeout) {

		$scope.profiles = [];
		$scope.defaultProfile = null;
		$scope.selectedProfile = null;
		$scope.manageProfileView = manageProfileView;
		$scope.changeProfile = changeProfile;
		$scope.changeProfileConfirmation = changeProfileConfirmation;

		activate();

		function activate() {
			$rootScope.$broadcast('veil:show', {});
			ProfileService.getProfilesFromDatabase();
		}

		/************************************************** IMPLEMENTATION *************************************************************************/


		/**
		 * This function is checking if default profile is in json data form service
		 * if default profile is not set then function call trigger with select profile window
		 * if profile list is empty then function is calling trigger with error window
		 *
		 * @return void
		 */
		function manageProfileView() {
			var profiles = $scope.profiles;

			$rootScope.$broadcast('veil:hideInformation', {});

			if (profiles === null || profiles === undefined || profiles.length === 0) {
				$rootScope.profilesErrorNotFoundVisible = true;
				//$rootScope.$broadcast('veil:hideInformation', {});
			} else if (ProfileService.getSelectedProfile() === null || ProfileService.getSelectedProfile() === undefined) {
				//$rootScope.$broadcast('veil:hideInformation', {});
				//$rootScope.profilesDefaultProfileNotFoundVisible = true;
				//$rootScope.profilesShowGroupsVisible = true;
				$rootScope.$broadcast("profilesController:profilesVerified");
			} else {
				$rootScope.title = MODEL_NAME + " : " + CYCLE_NAME + " : " + $scope.selectedProfile.name;
				$rootScope.$broadcast("spreadSheetController:loadSpreadSheet", {});
			}
		}

		function manageProfilesLoadingError() {
			$rootScope.$broadcast('exceptionViewer:foundError', {});
		}

		/**
		 * This function is changing the profile and send reload action.
		 *
		 * @return void
		 */
		function changeProfile() {
			if (SpreadSheetService.isSpreadWasChanged) {
				$scope.changeProfileConfirmation($scope.selectedProfile);
			} else {
				ProfileService.setSelectedProfile($scope.selectedProfile);

				$rootScope.$broadcast('veil:show', {});
				$rootScope.title = MODEL_NAME + " : " + CYCLE_NAME + " : " + $scope.selectedProfile.name;
				$rootScope.$broadcast("spreadSheetController:loadSpreadSheet", {});
			}
		}

		function changeProfileConfirmation(selectedProfile) {
			var parentScope = $scope;

			var modalInstance = $modal.open({
				templateUrl: $BASE_TEMPLATE_PATH + 'profiles/views/profileManagerConfirmationModal.html',
				windowClass: 'profielModal',
				backdrop: 'static',
				controller: ['$scope', '$modalInstance', 'ProfileService',
					function($scope, $modalInstance, profileService) {

						$scope.title = 'Confirmation';
						$scope.message = 'Are you sure you want to change this profile? You have unsaved changes!';

						$scope.cancel = function() {
							parentScope.selectedProfile = profileService.getSelectedProfile();
							$modalInstance.close();
						};

						$scope.ok = function() {
							profileService.setSelectedProfile(parentScope.selectedProfile);

							$modalInstance.close();
							$rootScope.$broadcast('veil:show', {});
							$rootScope.title = MODEL_NAME + " : " + CYCLE_NAME + " : " + $scope.selectedProfile.name;
							$rootScope.$broadcast("spreadSheetController:loadSpreadSheet", {});
						};
					}
				]
			});
		}


		/************************************************** EVENTS *********************************************************************/



		$scope.$on('ProfileService:profilesUpdated', function(event, args) {
			$scope.profiles = ProfileService.getProfiles();
		});

		$scope.$on('ProfileService:selectedProfileUpdated', function(event, args) {
			$scope.selectedProfile = ProfileService.getSelectedProfile();
		});

		$scope.$on('ProfileService:handleProfilesFromDatabase', function(event, args) {
			$scope.profiles = ProfileService.getProfiles();
			$scope.selectedProfile = ProfileService.getSelectedProfile();
			$scope.manageProfileView();
		});
		$scope.$on('ProfileService:handleErrorWhileLoadingProfiles', function(event) {
			manageProfilesLoadingError();
		});

		/**
		 * Opening profile manager, in profile manager you can create, edit, remove profiles
		 * @return void
		 */
		$rootScope.$on('ProfilesController:openManager', function() {
			formListService.getFormListFromDatabase();

			var modalInstance = $modal.open({
				templateUrl: $BASE_TEMPLATE_PATH + 'profiles/views/profileManagerModal.html',
				windowClass: 'profielModal',
				backdrop: 'static',
				controller: ['$scope', '$modalInstance', 'ProfileService',
					function($scope, $modalInstance, profileService) {
						$scope.profiles = profileService.getProfiles();
						$scope.$on('ProfileService:profilesUpdated', function(event, args) {
							$scope.profiles = profileService.getProfiles();
						});

						$scope.close = function() {
							$modalInstance.close();
						};

						$scope.edit = function(profile) {
							var originProfile = profile || {};
							var editedProfile = {};
							angular.copy(originProfile, editedProfile);

							$rootScope.$broadcast('ProfilesController:openProfileEditor', {
								profile: editedProfile,
								mode: 'edit'
							});
						};

						$scope.createNew = function() {
							var profile = {
								defaultProfile: false,
								description: null,
								formId: null,
								name: null,
								profileId: null,
								structureId0: null,
								structureId1: null,
								structureElementId0: null,
								structureElementId1: null,
								elementLabel0: null,
								elementLabel1: null,
								dataType: null
							};

							$rootScope.$broadcast('ProfilesController:openProfileEditor', {
								profile: profile,
								mode: 'create'
							});
						};

						$scope.copyProfile = function(profile) {
							var copiedProfile = {};
							var originProfile = profile || {};

							angular.copy(profile, copiedProfile);
							copiedProfile.name = null;

							$rootScope.$broadcast('ProfilesController:openProfileEditor', {
								profile: copiedProfile,
								mode: 'copy'
							});
						};

						$scope.deleteProfile = function(profileId) {
							$rootScope.$broadcast('ProfilesController:openManagerDelete', {
								profileId: profileId
							});
						};
					}
				]
			});
		});

		/**
		 * Opens Modal with profile editor
		 * @param  {[type]} event [description]
		 * @param  {[type]} args  args.profile - profile to edit/copy, args.mode - which mode myst be loaded (create/edit/copy)
		 * @return {[type]}       [description]
		 */
		$rootScope.$on('ProfilesController:openProfileEditor', function(event, args) {
			var modalInstance = $modal.open({
				template: '<profile-editor profile="profile" mode="mode"></profile-editor>',
				windowClass: 'profielModal',
				backdrop: 'static',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.profile = args.profile;
						$scope.mode = args.mode;

						$scope.$on('ProfileEditor:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		});

		/**
		 * Profile Manager delete Action, this function is creating modal
		 * @return void
		 */
		$rootScope.$on('ProfilesController:openManagerDelete', function(event, args) {
			var modalInstance = $modal.open({
				templateUrl: $BASE_TEMPLATE_PATH + 'profiles/views/profileManagerConfirmationModal.html',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.title = 'Confirmation';
						$scope.message = 'Are you sure you want to delete this profile?';

						$scope.ok = function() {
							ProfileService.delete(args.profileId).success(function(data) {
								$rootScope.profiles = data;
								ProfileService.setProfiles(data);
								$modalInstance.close();
							});
						};

						$scope.cancel = function() {
							$modalInstance.dismiss('cancel');
						};
					}
				]
			});
		});
	}
})();