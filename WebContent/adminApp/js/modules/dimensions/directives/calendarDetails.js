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
        .module('adminApp.dimension')
        .directive('dimensionsCalendarDetails', dimensionsCalendarDetails);

    /* @ngInject */
    function dimensionsCalendarDetails($timeout, $rootScope, Flash, CalendarService, CoreCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'dimensions/views/calendarDetails.html',
			scope: {
				id: '=id',
				hierarchyId: '=hierarchyId',
				close: '&'
			},
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".dimensions-calendar-details").closest(".modal-dialog");
					var elementToResize = null; // $(".");
					var additionalElementsToCalcResize = [];
					$scope.cookieName = "adminPanel_modal_dimensionsCalendar";
					// try to resize and move modal based on the cookie
					CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
					$timeout(function() { // timeout is necessary to pass asynchro
					    CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
					}, 0);
					$scope.isMainDataLoaded = false;
					$scope.isAnyNullElement = false;
					$scope.selectedDimensionElementId = -1;
					$scope.selectedDimensionElement = {};
					$scope.calendar = {};
					$scope.deletedYears = [];
					$scope.calendarView = new wijmo.collections.CollectionView();
					$scope.isError = false;
					// validation messages: messageError for top message bar, validation fields for each form fields
					// If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
					$scope.messageError = "";
					$scope.validation = {
						dimensionVisId: "",
						dimensionDescription: "",
						dimensionElements: {},
						years: ""
					};
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.save = save;
					$scope.addYear = addYear;
					$scope.deleteYear = deleteYear;
					
					function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    };
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    };
					
					if ($scope.id != -1) {
						// edit Dimension 
						// get Dimension details
						$scope.calendar = CalendarService.getCalendarDetails($scope.id, $scope.hierarchyId);
						$scope.submitButtonName = "Save";
					} else {
						// create Dimension
						// create new empty Dimension object
						var emptyCalendar = CalendarService.createEmptyCalendar($scope.id);
						$scope.calendar = emptyCalendar;
						$scope.submitButtonName = "Create";
						$scope.isMainDataLoaded = true;
					}

					/**
					 * Clear whole validation object which is sended to template. All validation messages are disappeared
					 */
					function clearValidation() {
						// clear previous validation messages
						angular.forEach($scope.validation, function(message, field) {
							$scope.validation[field] = "";
						});
					}

					/**
					 * Try to save Dimension after click on button - save means update or insert.
					 */
					function save() {
						var data = angular.copy($scope.calendar);

						// Check if anything changed with dimensionElements and if Dimension has Model ($scope.calendar.model.modelId > 0).
						// If yes - show alert with "submit immediately" question.
						// If we save Dimension without Model, task for its Elements is not created, so additional question is unnecessary.
						var newYears = prepareYears(flexGridColumns);
						var oldYears = $scope.calendar.years;

						var isYearsChanged = false;
						var i;
						if (newYears.length === oldYears.length) {
							for (i = 0; i < newYears.length; i++) {
								if (newYears[i].year !== oldYears[i].length) {
									isYearsChanged = true;
									break;
								}
							}
						} else {
							isYearsChanged = true;
						}
						for (i = 0; i < newYears.length; i++) {
							var oldYear = CoreCommonsService.findElementByKey(oldYears, newYears[i].year, 'year');
							if (oldYear) {
								newYears[i].yearId = oldYear.yearId;
							}
						}
						data.years = newYears;
						if (isYearsChanged > 0 && $scope.calendar.model.modelId > 0) {
							var answer = confirm("Changes will be saved. \nDo you wish to submit the change management request immediately? Press \"OK\" to submit immediately or \"Cancel\" to do it later.");
							if (answer) {
								data.submitChangeManagementRequest = true;
							} else {
								data.submitChangeManagementRequest = false;
							}
						}
						$rootScope.$broadcast("modal:blockAllOperations");
						CalendarService.save(data);
					};
					/*********************************************** FLEX GRID *******************************************************/
					var ourCalendarLevels = ["Year", "Month", "Opening Balance"];
					var calendarLevels = ["Year", "Half Year", "Quarter", "Month", "Week", "Day", "Opening Balance", "Adjustment Period", "Additional Period 1", "Additional Period 2"];
					var flexGrid;
					var flexGridRows = [];
					var flexGridColumns = [{
						header: 'Level',
						binding: 'name',
						width: '2*',
						isReadOnly: true
					}];

					if ($scope.id === -1) {
						var date = new Date();
						var actualYear = date.getFullYear();
						var years = [{
							yearId: -1,
							year: actualYear,
							spec: [true, false, false, true, false, false, false, false, false, false],
							operation: null
						}];
						manageRows();
						manageYearSelections(years);
						createCols(years);
						createCalendarGrid();
					}

					function manageRows() {
						flexGridRows.length = 0;
						for (var i = 0; i < ourCalendarLevels.length; i++) {
							var row = {
								year: false,
								name: ourCalendarLevels[i]
							};
							flexGridRows.push(row);
						}
					}

					function manageYearSelections(years) {
						if (!years || years.length <= 0) {
							return;
						}
						for (var i = 0; i < years.length; i++) {
							var spec = years[i].spec;
							for (var j = 0; j < ourCalendarLevels.length; j++) {
								var name = flexGridRows[j].name;
								var index = calendarLevels.indexOf(name);
								if (index !== -1) {
									flexGridRows[j].year = spec[index];
								} else {
									flexGridRows[j].year = false;
								}
							}
							break;
						}
					}

					function createCols(years) {
						for (var i = 0; i < years.length; i++) {
							var year = years[i].year;
							var column = {
								header: '' + year,
								binding: 'year',
								name: '' + year,
								width: '*'
							};
							flexGridColumns.push(column);
						}
					}

					function createCalendarGrid() {
						flexGrid = new wijmo.grid.FlexGrid('#calendarTable');
						flexGrid.isReadOnly = true;
						flexGrid.selectionMode = "Row";
						flexGrid.headersVisibility = "Column";
						flexGrid.autoGenerateColumns = false; // before setting itemsSource
						flexGrid.itemsSource = $scope.calendarView;
						$scope.calendarView.sourceCollection = flexGridRows;

						for (var i = 0; i < flexGridColumns.length; i++) {
							var col = new wijmo.grid.Column();
							wijmo.copy(col, flexGridColumns[i]);
							flexGrid.columns.push(col);
						}
						$timeout(function() { // timeout is necessary to pass asynchro
							flexGrid.refresh(true);
							flexGrid.isReadOnly = false;
						}, 500);

					}

					function prepareYears(flexGridColumns) {
						var years = [];
						var newYearTempId = -1;
						var year;
						var sourceCollection = $scope.calendarView.sourceCollection;

						// we omit first column which are labels;
						for (var i = 1; i < flexGridColumns.length; i++) {
							year = {
								yearId: newYearTempId,
								year: parseInt(flexGridColumns[i].header),
								spec: [sourceCollection[0].year, false, false, sourceCollection[1].year, false, false, sourceCollection[2].year, false, false, false],
								operation: null
							};
							newYearTempId--;
							years.push(year);
						}
						return years;
					}

					function addYear(param) {
						var col = new wijmo.grid.Column();
						var column;

						if (param === 'right') {
							var pushLast = parseInt(flexGridColumns[flexGridColumns.length - 1].header) + 1;
							column = {
								header: '' + pushLast,
								binding: 'year',
								name: '' + pushLast,
								width: '*'
							};
							flexGridColumns.push(column);
							wijmo.copy(col, column);
							flexGrid.columns.push(col);
						}
						if (param === 'left') {
							var left = parseInt(flexGridColumns[1].header) - 1;
							column = {
								header: '' + left,
								binding: 'year',
								name: '' + left,
								width: '*'
							};
							flexGridColumns.splice(1, 0, column);
							wijmo.copy(col, column);
							flexGrid.columns.splice(1, 0, col);
						}
					};

					function deleteYear(param) {
						if (flexGridColumns.length == 2) {
							return;
						}
						if (param === 'right') {
							flexGridColumns.pop();
							flexGrid.columns.pop();
						}
						if (param === 'left') {
							flexGridColumns.splice(1, 1);
							flexGrid.columns.splice(1, 1);
						}
					};

					/****************************************** WATCHERS **************************************************************/
					/**
					 * After getting Dimension details from service. (Details are used to populate form fields).
					 * Watcher: https://docs.angularjs.org/api/ng/type/$rootScope.Scope
					 * @param  {[type]} event [description]
					 * @param  {Object} data  [Dimension details]
					 */
					$scope.$watchCollection(
						// "This function returns the value being watched. It is called for each turn of the $digest loop"
						function() {
							return $scope.calendar;
						},
						// "This is the change listener, called when the value returned from the above function changes"
						function(newValue, oldValue) {
							// This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
							// To protect against this we check if newValue isn't empty.
							if (newValue !== oldValue && newValue.years.length > 0) {
								$scope.calendar = newValue;
								manageRows();
								manageYearSelections($scope.calendar.years);
								createCols($scope.calendar.years);
								createCalendarGrid();
								$scope.isMainDataLoaded = true;
							}
						}
					);

					/****************************************** RESPONSES **************************************************************/
					$scope.$on("CalendarService:calendarSaveError", function(event, data) {
						$rootScope.$broadcast("modal:unblockAllOperations");
						clearValidation();
						// set new messages
						angular.forEach(data.fieldErrors, function(error) { 
							if (error.fieldName in $scope.validation) {
								$scope.validation[error.fieldName] = error.fieldMessage;
							}
						});
					});

					$scope.$on("CalendarService:calendarSaved", function(event, data) {
						if (data.success) {
							$scope.close();
						}
					});
				}
			]
		};
	}
})();