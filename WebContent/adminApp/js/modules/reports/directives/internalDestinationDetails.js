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
        .directive('internalDestinationDetails', internalDestinationDetails);

    /* @ngInject */
    function internalDestinationDetails($timeout, $compile, $modal, $rootScope, Flash, InternalDestinationService, CoreCommonsService, ReportCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'reports/views/internalDestinationDetails.html',
			scope: {
				id: '='
			},
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".report-details").closest(".modal-dialog");
					var elementToResize = $(".report-users-grid");
					var additionalElementsToCalcResize = [$(".report-main-data"), $(".getToCalc1"), $(".refresh-button"), $(".add-selected-element-to-table"), 20];
					$scope.cookieName = "adminPanel_modal_internalDestination";
					// try to resize and move modal based on the cookie
					CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
					$scope.resizedColumn = function(sender, args) {
					    CoreCommonsService.resizedColumn(args, $scope.cookieName);
					};
					$scope.sortedColumn = function(sender, args) {
					    CoreCommonsService.sortedColumn(args, $scope.cookieName);
					};

					$scope.globalSettings = CoreCommonsService.globalSettings;
					$scope.isMainDataLoaded = false;
					$scope.messageTypes = InternalDestinationService.getMessageTypes();
					$scope.selectedReportElementId = -1;
					$scope.selectedReportElement = {};
					$scope.report = {};

					$scope.currentMessageType = {};

					$scope.isError = false;
					// validation messages: messageError for top message bar, validation fields for each form fields
					// If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
					$scope.messageError = "";
					$scope.validation = {
						reportVisId: "",
						reportDescription: "",
						messageType: "",
						users: "" // information on the top of the table (at least one user must be added)
					};

					$scope.changeMessageType = function() {
						if ($scope.currentMessageType != null) {
							$scope.report.messageType = $scope.currentMessageType;
						}
					};

					if ($scope.id != -1) {
						// edit Report 
						// get Report details
						$scope.report = InternalDestinationService.getReportDetails($scope.id);
						$scope.submitButtonName = "Save";
					} else {
						// create Report
						// create new empty Report object
						var emptyReport = InternalDestinationService.createEmptyReport($scope.id);
						$scope.report = emptyReport;
						$scope.submitButtonName = "Create";
						$scope.isMainDataLoaded = true;
					}

					//----------------------------- wijmo -----------------------------

					var reportElementsWijmoCollection = new wijmo.collections.CollectionView();

					// create main ctx object
					$scope.ctx = {
						flex: null,
						filter: ''
					};

					$scope.$watch('ctx.flex', function() {
						var flex = $scope.ctx.flex;
						flexInitialize();
					});

					// format cells on every refresh
					var itemFormatter = function(panel, r, c, cell) {
						if (panel.cellType == wijmo.grid.CellType.Cell) {
							var col = panel.columns[c],
								html = cell.innerHTML,
								reportElementId = panel.rows[r].dataItem['reportElementId'];
							switch (col.name) {
								case 'index':
									html = "" + (r + 1);
									break;
								case 'buttons':
									html = '<button type="button" class="btn btn-danger btn-xs" ng-click="deleteReportElement(' + panel.rows[r].dataItem['userId'] + ')">Remove</button>';
									break;
							}

							// set new HTML inside cell
							if (html != cell.innerHTML) {
								cell.innerHTML = html;
								$compile(cell)($scope);
							}

						}
					};

					var flexInitialize = function() {
						var flex = $scope.ctx.flex;
						var data = $scope.ctx.data;

						if (flex && data && data.sourceCollection && data.sourceCollection.length >= 0) {
							flex.isReadOnly = true;
							flex.selectionMode = "Row";
							flex.headersVisibility = "Column";
							flex.itemFormatter = itemFormatter;
							$timeout(function() { // timeout is necessary to pass asynchro
							    CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
							}, 0);
						}
					};

					$scope.$watch('report.users', function() {
						if ($scope.report.users != undefined) {
							reportElementsWijmoCollection.sourceCollection = $scope.report.users;
							$scope.ctx.data = reportElementsWijmoCollection;
							flexInitialize();
						}
					});

					$scope.$watch('selectedReportElement', function() {
						var flex = $scope.ctx.flex;
						if (flex) {
							flex.refresh(false);
						}
					}, true);

					//Set selected table row as current.
					$scope.selectionChangedHandler = function() {
						var flex = $scope.ctx.flex;
						var current = flex.collectionView ? flex.collectionView.currentItem : null;
						if (current != null) {
							$scope.selectedReportElement = current;
							$scope.selectedReportElementId = current.reportElementId;
						} else {
							$scope.selectedReportElement = {};
							$scope.selectedReportElementId = -1;
						}
					};

					$scope.refreshData = function() {
						$scope.ctx.data.refresh();
					};
					//----------------------------- end of wijmo -----------------------------

					/**
					 * After cancel editing/creating Report.
					 */
					$scope.close = function() {
						$rootScope.$broadcast("InternalDestinationDetails:close");
					};

					//----------------------------- add/remove user -----------------------------
					//
					$scope.chosenReportElement = null;

					// add chosenReportElement (=user) to Users and remove from availableUsers
					$scope.addReportElement = function() {
						if ($scope.chosenReportElement) {
							var existsInUsers = CoreCommonsService.findElementByKey($scope.report.users, $scope.chosenReportElement.userId, "userId");
							// to not duplicate user in users
							if (existsInUsers == null) {
								// add to users
								$scope.report.users.push($scope.chosenReportElement);
								// delete from availableUsers
								var currentUser = CoreCommonsService.findElementByKey($scope.report.availableUsers, $scope.chosenReportElement.userId, "userId");
								var index = $scope.report.availableUsers.indexOf(currentUser);
								$scope.report.availableUsers.splice(index, 1);
								$scope.refreshData();
							}
							$scope.chosenReportElement = null;
						}
					};

					var addManyReportElements = function(selectedUsers) {
						angular.forEach(selectedUsers, function(user) {
							$scope.chosenReportElement = user;
							$scope.addReportElement();
						});
					};

					$scope.selectUsers = function(reportId) {
						var users = angular.copy($scope.report.availableUsers);
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
										addManyReportElements(selectedUsers);
										$modalInstance.close();
									});									
								}
							]
						});
					};

					// delete chosenReportElement (=user) from Users and add it to availableUsers
					$scope.deleteReportElement = function(userId) {
						// find user in users
						var user = CoreCommonsService.findElementByKey($scope.report.users, userId, "userId");
						// add to availableUsers
						$scope.report.availableUsers.push(user);
						// delete from users
						var index = $scope.report.users.indexOf(user);
						$scope.report.users.splice(index, 1);
						$scope.refreshData();
					};

					//----------------------------- end of add/remove user -----------------------------

					/**
					 * Try to save Report after click on button - save means update or insert.
					 */
					$scope.save = function() {
						$rootScope.$broadcast("modal:blockAllOperations");
						InternalDestinationService.save($scope.report);
					};

					var gettingDetailsFromRequest = true;

					/**
					 * After getting Report details from service. (Details are used to populate form fields).
					 * Watcher: https://docs.angularjs.org/api/ng/type/$rootScope.Scope
					 * @param  {[type]} event [description]
					 * @param  {Object} data  [Report details]
					 */
					$scope.$watchCollection(
						// "This function returns the value being watched. It is called for each turn of the $digest loop"
						function() {
							return $scope.report;
						},
						// "This is the change listener, called when the value returned from the above function changes"
						function(newValue, oldValue) {
							// This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
							// To protect against this we check if newValue isn't empty.
							if (newValue !== oldValue && Object.keys(newValue).length != 0) {
								$scope.report = newValue;
								// Set select fields only if details comes from request. In other situation those fields are setting in other functions.
								if (gettingDetailsFromRequest) {
									$scope.currentMessageType = CoreCommonsService.findElementByKey($scope.messageTypes, $scope.report.messageType.index, "index");
									$scope.changeMessageType();
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
					$scope.$on("InternalDestinationService:reportDetailsSaveError", function(event, data) {
						$rootScope.$broadcast("modal:unblockAllOperations");
						$scope.isError = true;
						if (typeof data.title == "undefined") {
							// error from method http->success() and from field "error"
							Flash.create('danger', data.message);
						} else {
							// error from method http->error()
							var messageToReturn = data.title + " " + data.message.split("\n")[0];
							Flash.create('danger', messageToReturn);
						}
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
						$scope.refreshData();
					});

					/**
					 * On modal close. If is set any response with "success" field, show info success message.
					 * @param  {[type]} event [description]
					 * @param  {Object} data  [Response Message]
					 */
					$scope.$on("InternalDestinationDetails:close", function(event, data) {
						$scope.isError = false;
						if (typeof data != "undefined") {
							if (data.success) {
								var operation = "";
								if (data.method == "POST") {
									operation = "created";
								} else if (data.method == "PUT") {
									operation = "updated";
								}
								Flash.create('success', "Report (" + $scope.report.reportVisId + ") is " + operation + ".");
							}
						}
					});

				}
			]
		};
	}
})();