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
		.module('adminApp.report')
		.service('InternalDestinationService', InternalDestinationService);

	/* @ngInject */
	function InternalDestinationService($rootScope, $http, UsersPageService, CoreCommonsService) {

		var self = this;
		var areReportsLoaded = false;
		var areReportsLoading = false;
		var areMessageTypesLoaded = false;
		var areMessageTypesLoading = false;
		var areAllUsersLoaded = false;
		var url = $BASE_PATH + 'adminPanel/report/internaldestinations';
		var reports = new wijmo.collections.CollectionView();
		var messageTypes = [];
		var allUsers = [];
		var reportDetails = {};
		var actions = [{
			name: "Delete",
			action: "deleteInternalDestination",
			disabled: true
		}];
		self.isCreateDisabled = false;
		self.isOpenDisabled = true;
		self.isPrintDisabled = true;
		self.isAskDisabled = false;
		self.getReports = getReports;
		self.getReportDetails = getReportDetails;
		self.createEmptyReport = createEmptyReport;
		self.getMessageTypes = getMessageTypes;
		self.getAllUsers = getAllUsers;
		self.save = save;
		self.deleteInternalDestination = deleteInternalDestination;
		self.getActions = getActions;

		var getReportsFromDatabase = function() {
			areReportsLoaded = false;
			areReportsLoading = true;
			$http.get(url).success(function(data) {
				areReportsLoaded = true;
				areReportsLoading = false;
				if (data && data.length >= 0) {
					reports.sourceCollection = data;
				}
			});
		};

		/**
		 * Return reports if were taken, otherwise call getting Reports from database.
		 */
		function getReports(hardReload) {
			if ((!areReportsLoaded && !areReportsLoading) || hardReload) {
				getReportsFromDatabase();
			}
			return reports;
		}

		/**
		 * Get details of one Report (get always from database to have the newest version).
		 * @param  {Integer} reportId [description]
		 */
		function getReportDetails(reportId) {
			reportDetails = {};
			if (reportId != -1) {
				$http.get(url + "/" + reportId).success(function(response) {
					angular.copy(response, reportDetails);
				});
			}
			return reportDetails;
		}

		/**
		 * Create new Dmension Report object to return to directive and have ready object for scope and template. Now "id" is always -1.
		 * @param  {Integer} id
		 */
		function createEmptyReport(id) {
			var report = {
				reportId: id, // now id=-1
				reportVisId: "",
				reportDescription: "",
				versionNum: -1,
				messageType: {
					index: 0,
					name: "System Message"
				},
				users: [],
				availableUsers: [],
			};
			angular.copy(allUsers.sourceCollection, report.availableUsers);
			return report;
		}

		/**
		 * Get MessageTypes from backend service.
		 */
		var getMessageTypesByAjax = function() {
			areMessageTypesLoaded = false;
			areMessageTypesLoading = true;
			$http.get(url + "/messageTypes").success(function(data) {
				areMessageTypesLoaded = true;
				areMessageTypesLoading = false;
				if (data && data.length >= 0) {
					messageTypes.length = 0;
					angular.forEach(data, function(technique) {
						messageTypes.push(technique);
					});
				}
			});
		};

		/**
		 * Return MessageTypes if were taken, otherwise call getting MessageTypes from database.
		 */
		function getMessageTypes() {
			if (!areMessageTypesLoaded && !areMessageTypesLoading) {
				getMessageTypesByAjax();
			}
			return messageTypes;
		}

		/**
		 * Return AllUsers if were taken, otherwise call getting AllUsers from other service.
		 * AllUsers are used to populate availableUsers select field on create Report.
		 */
		function getAllUsers() {
			if (!areAllUsersLoaded) {
				allUsers = UsersPageService.getUsers();
				areAllUsersLoaded = true;
			}
			return allUsers;
		}

		/**
		 * Save report details. It's "insert" (when report.id is -1) or "update" otherwise.
		 * @param  {Object} report [report details]
		 */
		function save(report) {
		    var insert;
			if (report === null) {
				return;
			}
			var method = "";
			if (report.reportId != -1) {
				// edit Report
				method = "PUT";
				insert = false;
			} else {
				// create Report
				method = "POST";
				insert = true;
			}
			// Send request as PUT (for insert) or POST (for update)
			$http({
				method: method,
				url: url + "/",
				data: report
			}).success(function(response) {
				if (response.success) {
					reports.length = 0;
					// refresh listing
					if (insert){
					    getReportsFromDatabase();
	                } else {
	                var reportChange = CoreCommonsService.findElementByKey(reports.sourceCollection, report.reportId, 'reportId');
	                reportChange.reportVisId = report.reportVisId;
	                reportChange.reportDescription = report.reportDescription;
	                reports.refresh();
	                }
					var dataToReturn = response;
					// to response object add information if it was PUT or POST action
					dataToReturn.method = method;
					$rootScope.$broadcast('InternalDestinationDetails:close', dataToReturn);
				} else if (response.error) {
					$rootScope.$broadcast('InternalDestinationService:reportDetailsSaveError', response);
				}
			});
		}

		/**
		 * Delete Report
		 * @param  {Integer} reportId
		 */
		function deleteInternalDestination(reportId) {
			$http({
				method: 'DELETE',
				url: url + "/" + reportId
			}).success(function(response) {
				if (response.success) {
					reports.length = 0;
					// refresh listing
					getReportsFromDatabase();
					$rootScope.$broadcast('InternalDestinationService:reportDetailsDeleteSuccess');
				} else if (response.error) {
					$rootScope.$broadcast('InternalDestinationService:reportDetailsDeleteError', response);
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