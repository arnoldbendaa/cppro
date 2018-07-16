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
		.module('adminApp.authentication')
		.service('AuthenticationService', AuthenticationService);

	function AuthenticationService($rootScope, $http, CoreCommonsService) {

		var self = this;
		var areAuthenticationsLoaded = false;
		var areAuthenticationsLoading = false;
		var areAuthenticationTechniquesLoaded = false;
		var areAuthenticationTechniquesLoading = false;
		var areSecurityLogsLoaded = false;
		var areSecurityLogsLoading = false;
		var areAdminUsersLoaded = false;
		var areAdminUsersLoading = false;
		var url = $BASE_PATH + 'adminPanel/authentications';
		var authentications = new wijmo.collections.CollectionView();
		var authenticationTechniques = [];
		var securityLogs = [];
		var adminUsers = [];
		var authenticationDetails = {};
		var actions = [{
			name: "Delete",
			action: "deleteAuthentication",
			disabled: true
		}, {
			name: "Activate",
			action: "activate",
			disabled: true
		}];
		self.isCreateDisabled = false;
		self.isOpenDisabled = true;
		self.isPrintDisabled = true;
		self.isAskDisabled = false;
		self.getAuthentications = getAuthentications;
		self.getAuthenticationDetails = getAuthenticationDetails;
		self.createEmptyAuthentication = createEmptyAuthentication;
		self.getAuthenticationTechniques = getAuthenticationTechniques;
		self.getSecurityLogs = getSecurityLogs;
		self.getSecurityAdministrators = getSecurityAdministrators;
		self.save = save;
		self.activateAuthentication = activateAuthentication;
		self.deleteAuthentication = deleteAuthentication;
		self.getActions = getActions;

		var getAuthenticationsFromDatabase = function() {
			areAuthenticationsLoaded = false;
			areAuthenticationsLoading = true;
			$http.get(url).success(function(data) {
				areAuthenticationsLoaded = true;
				areAuthenticationsLoading = false;
				if (data && data.length >= 0) {
					authentications.sourceCollection = data;
				}
			});
		};

		/**
		 * Return authentications if were taken, otherwise call getting Authentications from database.
		 */
		function getAuthentications(hardReload) {
			if ((!areAuthenticationsLoaded && !areAuthenticationsLoading) || hardReload) {
				getAuthenticationsFromDatabase();
			}
			return authentications;
		}

		/**
		 * Get details of one Authentication (get always from database to have the newest version).
		 * @param  {Integer} authenticationId [description]
		 */
		function getAuthenticationDetails(authenticationId) {
			authenticationDetails = {};
			if (authenticationId != -1) {
				$http.get(url + "/" + authenticationId).success(function(response) {
					angular.copy(response, authenticationDetails);
				});
			}
			return authenticationDetails;
		}

		/**
		 * Create new Authentication object to return to directive and have ready object for scope and template. Now "id" is always -1.
		 * @param  {Integer} id
		 */
		function createEmptyAuthentication(authenticationId) {
			var authentication = {
				authenticationId: authenticationId, // now authenticationId=-1
				authenticationVisId: "",
				authenticationDescription: "",
				authenticationTechnique: {
					index: 1,
					name: "Internal"
				},
				active: false,
				versionNum: -1,
				securityAdministrator: null,
				securityLog: {
					index: 1,
					name: "NONE"
				},
				internal: {
					minimumPasswordLength: 0,
					minimumAlphas: 0,
					minimumDigits: 0,
					maximumRepetition: 0,
					minimumChanges: 0,
					passwordUseridDiffer: false,
					passwordMask: null,
					passwordReuseDelta: 0,
					maximumLogonAttempts: 0,
					passwordExpiry: 0
				},
				jaasEntryName: null,
				cosignConfigurationFile: null,
				ntlm: {
					ntlmDomain: null,
					ntlmDomainController: null,
					ntlmLogLevel: null,
					ntlmNetbiosWins: null
				}
			};
			return authentication;
		}

		/**
		 * Get Authentication Techniques from backend service.
		 */
		var getAuthenticationTechniquesByAjax = function() {
			areAuthenticationTechniquesLoaded = false;
			areAuthenticationTechniquesLoading = true;
			$http.get(url + "/authenticationTechniques").success(function(data) {
				areAuthenticationTechniquesLoaded = true;
				areAuthenticationTechniquesLoading = false;
				if (data && data.length >= 0) {
					authenticationTechniques.length = 0;
					angular.forEach(data, function(technique) {
						authenticationTechniques.push(technique);
					});
				}
			});
		};

		/**
		 * Return AuthenticationTechniques if were taken, otherwise call getting AuthenticationTechniques from database.
		 */
		function getAuthenticationTechniques() {
			if (!areAuthenticationTechniquesLoaded && !areAuthenticationTechniquesLoading) {
				getAuthenticationTechniquesByAjax();
			}
			return authenticationTechniques;
		}

		/**
		 * Return SecurityLogs if were taken, otherwise call getting SecurityLogs from database.
		 */
		function getSecurityLogs() {
			if (!areSecurityLogsLoaded && !areSecurityLogsLoading) {
				getSecurityLogsByAjax();
			}
			return securityLogs;
		}

		/**
		 * Get SecurityLogs from backend service.
		 */
		var getSecurityLogsByAjax = function() {
			areSecurityLogsLoaded = false;
			areSecurityLogsLoading = true;
			$http.get(url + "/securityLogs").success(function(data) {
				areSecurityLogsLoaded = true;
				areSecurityLogsLoading = false;
				if (data && data.length >= 0) {
					securityLogs.length = 0;
					angular.forEach(data, function(log) {
						securityLogs.push(log);
					});
				}
			});
		};

		/**
		 * Get SecurityAdministrator from backend service.
		 */
		var getSecurityAdministratorsByAjax = function() {
			areAdminUsersLoaded = false;
			areAdminUsersLoading = true;
			$http.get(url + "/adminUsers").success(function(data) {
				areAdminUsersLoaded = true;
				areAdminUsersLoading = false;
				if (data && data.length >= 0) {
					adminUsers.length = 0;
					angular.forEach(data, function(user) {
						adminUsers.push(user);
					});
				}
			});
		};

		/**
		 * Return AdminUsers if were taken, otherwise call getting AdminUsers from database.
		 */
		function getSecurityAdministrators() {
			if (!areAdminUsersLoaded && !areAdminUsersLoading) {
				getSecurityAdministratorsByAjax();
			}
			return adminUsers;
		}

		/**
		 * Save authentication details. It's "insert" (when authentication.id is -1) or "update" otherwise.
		 * @param  {Object} authentication [authentication details]
		 */
		function save(authentication) {
		    var insert;
			if (authentication === null) {
				return;
			}
			var method = "";
			if (authentication.authenticationId != -1) {
				// edit Authentication
				method = "PUT";
				insert = false;
			} else {
				// create Authentication
				method = "POST";
				insert = true;
			}
			// Send request as PUT (for insert) or POST (for update)
			$http({
				method: method,
				url: url + "/",
				data: authentication
			}).success(function(response) {
				if (response.success) {
					authentications.length = 0;
					// refresh listing
					if (insert){
					    getAuthenticationsFromDatabase();
                    } else {
                    var authenticationChange = CoreCommonsService.findElementByKey(authentications.sourceCollection, authentication.authenticationId, 'authenticationId');
                    authenticationChange.active = authentication.active;
                    authenticationChange.authenticationDescription = authentication.authenticationDescription;
                    authenticationChange.authenticationTechnique = authentication.authenticationTechnique;
                    authenticationChange.authenticationVisId = authentication.authenticationVisId;
                    authentications.refresh();
                    }
					var dataToReturn = response;
					// to response object add information if it was PUT or POST action
					dataToReturn.method = method;
					$rootScope.$broadcast('AuthenticationDetails:close', dataToReturn);
				} else if (response.error) {
					$rootScope.$broadcast('AuthenticationService:authenticationDetailsSaveError', response);
				}
			});
		}

		/**
		 * Activate Authentication
		 * @param  {Integer} authenticationId
		 */
		function activateAuthentication(newActiveAuthenticationId) {
			var oldActiveAuthentication = CoreCommonsService.findElementByKey(authentications.sourceCollection, true, "active");
			var newActiveAuthentication = CoreCommonsService.findElementByKey(authentications.sourceCollection, newActiveAuthenticationId, "authenticationId");
			var data = []; // IDs for request
			var authenticationNames = {}; // names for success message
			if (oldActiveAuthentication == null) {
				data[0] = null;
				authenticationNames.oldAuthenticationName = null;
			} else {
				data[0] = oldActiveAuthentication.authenticationId;
				authenticationNames.oldAuthenticationName = oldActiveAuthentication.authenticationVisId;
			}
			data[1] = newActiveAuthenticationId;
			authenticationNames.newAuthenticationName = newActiveAuthentication.authenticationVisId;
			$http({
				method: 'PUT',
				url: url + "/activate",
				data: data
			}).success(function(response) {
				if (response.success) {
					authentications.length = 0;
					// refresh listing
					getAuthenticationsFromDatabase();
					$rootScope.$broadcast('AuthenticationService:authenticationDetailsActivateSuccess', authenticationNames);
				} else if (response.error) {
					$rootScope.$broadcast('AuthenticationService:authenticationDetailsActivateError', response);
				}
			});
		}

		/**
		 * Delete Authentication
		 * @param  {Integer} authenticationId
		 */
		function deleteAuthentication(authenticationId) {
			$http({
				method: 'DELETE',
				url: url + "/" + authenticationId
			}).success(function(response) {
				if (response.success) {
					authentications.length = 0;
					// refresh listing
					getAuthenticationsFromDatabase();
					$rootScope.$broadcast('AuthenticationService:authenticationDetailsDeleteSuccess');
				} else if (response.error) {
					$rootScope.$broadcast('AuthenticationService:authenticationDetailsDeleteError', response);
				}
			});
		}

		/**
		 * For top buttons
		 */
		function getActions() {
			return actions;
		}
	}
})();