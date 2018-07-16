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
		.service('ProfileService', ProfileService);

	/* @ngInject */
	function ProfileService($rootScope, $http) {
		//DATA_ENTRY_PROFILE_ID = "25755";

		/**
		 * Profile Service Object
		 * @type Object
		 */
		var vm = this;

		vm.profiles = null;
		vm.defaultProfile = null;
		vm.selectedProfile = null;


		vm.getProfilesFromDatabase = getProfilesFromDatabase;
		vm.findProfileById = findProfileById;
		vm.findDefaultProfile = findDefaultProfile;
		vm.getProfiles = getProfiles;
		vm.setProfiles = setProfiles;
		vm.getDefaultProfile = getDefaultProfile;
		vm.setDefaultProfile = setDefaultProfile;
		vm.getSelectedProfile = getSelectedProfile;
		vm.setSelectedProfile = setSelectedProfile;
		vm.create = create;
		vm.update = update;
		vm.copy = copy;
		vm.deleteProfile = deleteProfile;
		vm.getProfilesNames = getProfilesNames;

		/**
		 * URL Bulider for profileservice
		 *
		 * @param  String path path to service action
		 * @return String URL for action with params and base path
		 */
		var url = function url(path) {
			return $BASE_PATH + path + 'model/' + MODEL_ID + '/budgetCycle/' + BUDGET_CYCLE_ID + '/topNode/' + TOP_NODE_ID;
		};

		/**
		 * Get list of profiles from database
		 * @return {[type]} [description]
		 */
		function getProfilesFromDatabase() {
			$http.get(url('reviewBudget/profiles/'))
				.success(function(data) {
					vm.setProfiles(data);

					var findedDefaultProfile = vm.findDefaultProfile();
					vm.setDefaultProfile(findedDefaultProfile);

					if (DATA_ENTRY_PROFILE_ID !== "") {
						var selectedProfile = vm.findProfileById(DATA_ENTRY_PROFILE_ID);
						vm.setSelectedProfile(selectedProfile);
					} else {
						vm.setSelectedProfile(findedDefaultProfile);
					}
					$rootScope.$broadcast('ProfileService:handleProfilesFromDatabase');
				})
				.error(function(data, status, headers, config) {
					$rootScope.$broadcast('ProfileService:handleErrorWhileLoadingProfiles');
				});
		}

		/**
		 * Finds profile with passed id
		 * @param  {[type]} profileId [description]
		 * @return {[type]}           [description]
		 */
		function findProfileById(profileId) {
			profileId = parseInt(profileId);
			var findedProfile = null;
			angular.forEach(vm.profiles, function(profile) {
				if (profile.profileId == profileId) {
					findedProfile = profile;
					return;
				}
			});
			return findedProfile;
		}

		/**
		 * Finds default profile in list of profiles
		 * @return {[type]} [description]
		 */
		function findDefaultProfile() {
			var defaultProfile = null;
			angular.forEach(vm.profiles, function(profile) {
				if (profile.defaultProfile === true) {
					defaultProfile = profile;
					return;
				}
			});
			return defaultProfile;
		}

		/**
		 * Gets list of profiles
		 * @return {[type]} [description]
		 */
		function getProfiles() {
			return vm.profiles;
		}

		/**
		 * Sets list of profiles
		 * @param {[type]} profiles [description]
		 */
		function setProfiles(profiles) {
			$rootScope.profiles = profiles;
			vm.profiles = profiles;

			var selectedProfile = vm.getSelectedProfile();
			if (selectedProfile)
				vm.setSelectedProfile(vm.findProfileById(selectedProfile.profileId));

			$rootScope.$broadcast('ProfileService:profilesUpdated');
		}

		/**
		 * Returns default profile
		 * @return {[type]} [description]
		 */
		function getDefaultProfile() {
			return vm.defaultProfile;
		}

		/**
		 * Sets passed profile as default profile
		 * @param {[type]} profile [description]
		 */
		function setDefaultProfile(profile) {
			$rootScope.defaultProfile = profile; //TODO do wywalenia

			vm.defaultProfile = profile;
			$rootScope.$broadcast('ProfileService:defaultProfileUpdated');
		}

		/**
		 * Gets selected profile (profile which is already choosed/shown in profile selection)
		 * @return {[type]} [description]
		 */
		function getSelectedProfile() {
			return vm.selectedProfile;
		}

		/**
		 * Sets passed profile as selected profile
		 * @param {[type]} profile [description]
		 */
		function setSelectedProfile(profile) {
			$rootScope.selectedProfile = profile; //TODO do wywalenia

			vm.selectedProfile = profile;
			$rootScope.$broadcast('ProfileService:selectedProfileUpdated');
		}

		/**
		 * Function is sending profile object to create method in profileservice
		 *
		 * @param ProfileDTO profileObject profile object to create
		 * @return $http Object with in response ProfileDTO
		 */
		function create(profileObject) {
			return $http.post(url('reviewBudget/profile/'), profileObject);
		}

		/**
		 * Function is sending profile object to update method in profileservice
		 *
		 * @param ProfileDTO profileObject profile object to update
		 * @return $http Object with in response ProfileDTO
		 */
		function update(profileObject) {
			return $http.put(url('reviewBudget/profile/'), profileObject);
		}

		/**
		 * Function is sending profile object to copy method in profileservice
		 *
		 * @param ProfileDTO profileObject profile object to copy
		 * @return $http Object with in response ProfileDTO
		 */
		function copy(profileObject) {
			return $http.post(url('reviewBudget/profile/'), profileObject);
		}

		/**
		 * Function is sending profile object to copy method in profileservice
		 *
		 * @param int profileId profile id object to delete
		 * @return $http Object with in response ProfileDTO
		 */
		function deleteProfile(profileId) {
			return $http.delete(url('reviewBudget/profile/' + profileId + '/'));
		}

		/**
		 * Return list of profile names
		 * @return {[type]} [description]
		 */
		function getProfilesNames() {
			var profilesNames = [];
			angular.forEach($rootScope.profiles, function(profile) {
				profilesNames.push(profile.name);
			});
			return profilesNames;
		}
	}
})();