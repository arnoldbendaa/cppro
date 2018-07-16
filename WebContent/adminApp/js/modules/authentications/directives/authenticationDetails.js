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
		.module('adminApp.admin')
		.directive('detailsAuthentication', detailsAuthentication);

	/* @ngInject */
	function detailsAuthentication($rootScope, Flash, AuthenticationService, $timeout, CoreCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'authentications/views/authenticationDetails.html',
			scope: {
				id: '='
			},
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".authentication-details").closest(".modal-dialog");
					var elementToResize = null; // $(".");
					var additionalElementsToCalcResize;
					$scope.isMainDataLoaded = false;
					$scope.currentAuthenticationTechnique = {};
					$scope.currentSecurityLog = {};
					$scope.currentSecurityAdministrator = null;
					$scope.currentNtlmLogLevel = null;
					$scope.isError = false;
					// validation messages: messageError for top message bar, validation fields for each form fields
					// If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
					$scope.messageError = "";
					$scope.validation = {};
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.changeAuthenticationTechnique = changeAuthenticationTechnique;
					$scope.changeSecurityLog = changeSecurityLog;
					$scope.changeSecurityAdministrator = changeSecurityAdministrator;
					$scope.changeNtlmLogLevel = changeNtlmLogLevel;
					$scope.close = close;
					$scope.save = save;
					activate();

					function activate() {
						$scope.cookieName = "adminPanel_modal_authentication";
						// try to resize and move modal based on the cookie
						CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);

						$timeout(function() { // timeout is necessary to pass asynchro
							CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
						}, 0);

						$scope.authenticationTechniques = AuthenticationService.getAuthenticationTechniques();
						$scope.securityLogs = AuthenticationService.getSecurityLogs();
						$scope.securityAdministrators = AuthenticationService.getSecurityAdministrators();
						$scope.ntlmLogLevels = [{
							index: 1,
							name: 1
						}, {
							index: 2,
							name: 2
						}, {
							index: 3,
							name: 3
						}];
						$scope.validation = {
							authenticationId: "",
							authenticationVisId: "",
							authenticationDescription: "",
							authenticationTechnique: "",
							active: "",
							versionNum: "",
							securityAdministrator: "",
							securityLog: "",
							minimumPasswordLength: "",
							minimumAlphas: "",
							minimumDigits: "",
							maximumRepetition: "",
							minimumChanges: "",
							passwordUseridDiffer: "",
							passwordMask: "",
							passwordReuseDelta: "",
							maximumLogonAttempts: "",
							passwordExpiry: "",
							jaasEntryName: "",
							cosignConfigurationFile: "",
							ntlmDomain: "",
							ntlmDomainController: "",
							ntlmLogLevel: "",
							ntlmNetbiosWins: ""
						};
					}

					function resizedColumn(sender, args) {
						CoreCommonsService.resizedColumn(args, $scope.cookieName);
					}

					function sortedColumn(sender, args) {
						CoreCommonsService.sortedColumn(args, $scope.cookieName);
					}

					var resetDetailsFieldsGroupVisibility = function() {
						// Authentication techniques are indexed from 1 to 5, so we don't use detailsFieldsGroupVisibility[0].
						// We don't need detailsFieldsGroupVisibility[5] too, 'cause it doesn't have additional form fields.
						$scope.detailsFieldsGroupVisibility = [null, false, false, false, false];
					};

					// initialize visibility array
					resetDetailsFieldsGroupVisibility();

					function changeAuthenticationTechnique() {
						if ($scope.currentAuthenticationTechnique != null) {
							$scope.authentication.authenticationTechnique = $scope.currentAuthenticationTechnique;
							// switch Details field groups
							resetDetailsFieldsGroupVisibility();
							$scope.detailsFieldsGroupVisibility[$scope.authentication.authenticationTechnique.index] = true;
						}
					}

					function changeSecurityLog() {
						if ($scope.currentSecurityLog != null) {
							$scope.authentication.securityLog = $scope.currentSecurityLog;
						}
					}

					function changeSecurityAdministrator() {
						if ($scope.currentSecurityAdministrator != null) {
							$scope.authentication.securityAdministrator = $scope.currentSecurityAdministrator.index;
						}
					}

					function changeNtlmLogLevel() {
						if ($scope.currentNtlmLogLevel != null) {
							$scope.authentication.ntlm.ntlmLogLevel = $scope.currentNtlmLogLevel.index;
						}
					}

					if ($scope.id != -1) {
						// edit - get details
						$scope.authentication = AuthenticationService.getAuthenticationDetails($scope.id);
						$scope.submitButtonName = "Save";
					} else {
						// create new empty object
						var emptyAuthentication = AuthenticationService.createEmptyAuthentication($scope.id);
						$scope.authentication = emptyAuthentication;
						$scope.submitButtonName = "Create";
						$scope.isMainDataLoaded = true;
					}

					/**
					 * After cancel editing/creating authentication.
					 */
					function close() {
						$scope.currentAuthenticationTechnique = {};
						$scope.currentSecurityLog = {};
						$scope.currentSecurityAdministrator = null;
						$scope.currentNtlmLogLevel = null;
						$rootScope.$broadcast("AuthenticationDetails:close");
					}

					/**
					 * Try to save authentication after click on button - save means update or insert.
					 */
					function save() {
						$rootScope.$broadcast("modal:blockAllOperations");
						AuthenticationService.save($scope.authentication);
					}

					var gettingDetailsFromRequest = true;

					/**
					 * After getting authentication details from service. (Details are used to populate form fields).
					 * Watcher: https://docs.angularjs.org/api/ng/type/$rootScope.Scope
					 * @param  {[type]} event [description]
					 * @param  {Object} data  [authentication details]
					 */
					$scope.$watchCollection(
						// "This function returns the value being watched. It is called for each turn of the $digest loop"
						function() {
							return $scope.authentication;
						},
						// "This is the change listener, called when the value returned from the above function changes"
						function(newValue, oldValue) {
							// This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
							// To protect against this we check if newValue isn't empty.
							if (newValue !== oldValue) {
								$scope.authentication = newValue;
								// Set select fields only if details comes from request. In other situation those fields are setting in other functions.
								if (gettingDetailsFromRequest) {
									$scope.currentAuthenticationTechnique = CoreCommonsService.findElementByKey($scope.authenticationTechniques, $scope.authentication.authenticationTechnique.index, "index");
									$scope.changeAuthenticationTechnique();
									$scope.currentSecurityLog = CoreCommonsService.findElementByKey($scope.securityLogs, $scope.authentication.securityLog.index, "index");
									$scope.changeSecurityLog();
									$scope.currentSecurityAdministrator = CoreCommonsService.findElementByKey($scope.securityAdministrators, $scope.authentication.securityAdministrator, "index", true);
									$scope.changeSecurityAdministrator();
									$scope.currentNtlmLogLevel = CoreCommonsService.findElementByKey($scope.ntlmLogLevels, $scope.authentication.ntlm.ntlmLogLevel, "index", true);
									$scope.changeNtlmLogLevel();
									gettingDetailsFromRequest = false;
								}
								$scope.isMainDataLoaded = true;
							}
						}
					);

					/**
					 * Insert/update: On error after http->success() with error=true or http->error()
					 * @param  {[type]} event [description]
					 * @param  {Object} data  [Response Message]
					 */
					$scope.$on("AuthenticationService:authenticationDetailsSaveError", function(event, data) {
						$rootScope.$broadcast("modal:unblockAllOperations");
						$scope.isError = true;
						// error from method http->success() from "error" field
						Flash.create('danger', data.message);
						// clear previous validation messages
						angular.forEach($scope.validation, function(message, field) {
							$scope.validation[field] = "";
						});
						// set new messages
						angular.forEach(data.fieldErrors, function(error) {
							if (error.fieldName in $scope.validation) {
								$scope.validation[error.fieldName] = error.fieldMessage;
							}
						});
					});

					/**
					 * On modal close. If is set any response with "success" field, show info success message.
					 * @param  {[type]} event [description]
					 * @param  {Object} data  [Response Message]
					 */
					$scope.$on("AuthenticationDetails:close", function(event, data) {
						$scope.isError = false;
						if (typeof data != "undefined") {
							if (data.success) {
								var operation = "";
								if (data.method == "POST") {
									operation = "created";
								} else if (data.method == "PUT") {
									operation = "updated";
								}
								Flash.create('success', "Authentication (" + $scope.authentication.authenticationVisId + ") is " + operation + ".");
							}
						}
					});

				}
			]
		};
	}
})();