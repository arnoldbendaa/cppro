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
		.module('adminApp.commons')
		.directive('detailsDataType', detailsDataType);

	/* @ngInject */
	function detailsDataType($rootScope, Flash, DataTypesPageService, $timeout, CoreCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'dataTypes/views/dataTypeDetails.html',
			scope: {
				id: '='
			},
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".data-type-details").closest(".modal-dialog");
					var elementToResize = null; // $(".");
					var additionalElementsToCalcResize = [];
					$scope.cookieName = "adminPanel_modal_dataType";
					$scope.isMainDataLoaded = false;
					$scope.areMeasureDetailsDisplayed = false;	
					// disable Mesaure Details fields
					$scope.measureLengthDisabled = false;
					$scope.measureScaleDisabled = false;
					$scope.measureValidationDisabled = false;
					$scope.isError = false;
					// validation messages: messageError for top message bar, validation fields for each form fields
					// If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
					$scope.messageError = "";
					$scope.validation = {};
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.changeSubType = changeSubType;
					$scope.changeMeasureClass = changeMeasureClass;
					$scope.close = close;
					$scope.save = save;
					activate();

					function activate() {
						// try to resize and move modal based on the cookie
						CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
						$timeout(function() { // timeout is necessary to pass asynchro
	                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
	                    }, 0);
						$scope.validation = {
		                        dataTypeVisId: "",
		                        dataTypeDescription: "",
		                        subType: "",
		                        availableForImport: "",
		                        availableForExport: "",
		                        readOnlyFlag: "",
		                        measureClass: "",
		                        measureLength: "",
		                        measureScale: "",
		                        measureValidation: "",
		                        formulaExpr: ""
		                    };
						$scope.subTypes = DataTypesPageService.getSubTypes();
	                    $scope.measureClasses = DataTypesPageService.getMeasureClasses();
						$scope.subTypeMeasureNumber = DataTypesPageService.getSubTypeNumber("Measure"); // should return 4
						$scope.subTypeVirtualNumber = DataTypesPageService.getSubTypeNumber("Virtual"); // should return 3
					}

					function resizedColumn(sender, args) {
						CoreCommonsService.resizedColumn(args, $scope.cookieName);
					}

					function sortedColumn(sender, args) {
						CoreCommonsService.sortedColumn(args, $scope.cookieName);
					}

					/**
					 * Show / hide some boxes after changing subType
					 *
					 * @param {String} "set" for populate DataType and "change" otherwise.
					 */
					function changeSubType(displayMethod) {
						// values in fields ar set (on Data Type details load) or changed (after change in SubType select or MeasureClass select)
						var method = "";
						if (typeof displayMethod == "undefined") {
							method = "change";
						} else {
							method = displayMethod;
						}
						$scope.dataType.subType = $scope.currentSubType.index;
						$scope.dataType.subTypeName = $scope.currentSubType.name;
						switch ($scope.currentSubType.index) {
							case $scope.subTypeMeasureNumber:
								$scope.areMeasureDetailsDisplayed = true;
								$scope.areVirtualDetailsDisplayed = false;
								if (method == "change") {
									// update Data Type details and current Measure Class - set to default value ("String")
									$scope.currentMeasureClass = {};
									$scope.dataType.measureClass = $scope.currentMeasureClass.index = 0;
									$scope.dataType.measureClassName = $scope.currentMeasureClass.name = "String";
									$scope.changeMeasureClass();
									// clear Formula field (for Virtual subType)
									$scope.dataType.formulaExpr = "";
								}
								break;
							case $scope.subTypeVirtualNumber:
								$scope.areMeasureDetailsDisplayed = false;
								$scope.areVirtualDetailsDisplayed = true;
								if (method == "change") {
									// remove Measure Class in Data Type details and in current Measure Class
									$scope.currentMeasureClass = {};
									$scope.dataType.measureClass = $scope.currentMeasureClass.index = null;
									$scope.dataType.measureClassName = $scope.currentMeasureClass.name = "";
								}
								break;
							default:
								$scope.areMeasureDetailsDisplayed = false;
								$scope.areVirtualDetailsDisplayed = false;
								if (method == "change") {
									// remove Measure Class in Data Type details and in current Measure Class
									$scope.currentMeasureClass = {};
									$scope.dataType.measureClass = $scope.currentMeasureClass.index = null;
									$scope.dataType.measureClassName = $scope.currentMeasureClass.name = "";
									// clear Formula field (for Virtual subType)
									$scope.dataType.formulaExpr = "";
								}
								break;
						}
					}

					/**
					 * Set Measure Class type ("String", "Numeric", "Time" etc.). Disable some fields.
					 *
					 * @param {String} "set" for populate DataType and "change" otherwise.
					 */
					function changeMeasureClass(displayMethod) {
						// values in fields ar set (on Data Type details load) or changed (after change in SubType select or MeasureClass select)
						var method = "";
						if (typeof displayMethod == "undefined") {
							method = "change";
						} else {
							method = displayMethod;
						}
						$scope.measureLengthDisabled = true;
						$scope.measureScaleDisabled = true;
						$scope.measureValidationDisabled = true;
						// reset default measure parameters
						if (method == "change") {
							$scope.dataType.measureLength = null;
							$scope.dataType.measureScale = null;
							$scope.dataType.measureValidation = null;
						}
						if ($scope.currentMeasureClass !== null) {
							$scope.dataType.measureClass = $scope.currentMeasureClass.index;
							$scope.dataType.measureClassName = $scope.currentMeasureClass.name;
							switch ($scope.currentMeasureClass.name) {
								case "String":
									$scope.measureLengthDisabled = false;
									$scope.measureValidationDisabled = false;
									if (method == "change") {
										$scope.dataType.measureLength = 20;
									}
									break;
								case "Numeric":
									$scope.measureScaleDisabled = false;
									if (method == "change") {
										$scope.dataType.measureLength = 18;
										$scope.dataType.measureScale = 2;
									}
									break;
								case "Time":
									break;
								case "Date":
									break;
								case "Date & Time":
									break;
								case "Boolean":
									break;
								case "":
									break;
								default:
									break;
							}
						}
					}

					if ($scope.id != -1) {
						// edit DataType
						// get Data Type details
						$scope.dataType = DataTypesPageService.getDataTypeDetails($scope.id);
						$scope.submitButtonName = "Save";
					} else {
						// create DataType
						// create new empty Data type object
						var emptyDataType = DataTypesPageService.createEmptyDataType($scope.id);
						$scope.dataType = emptyDataType;
						// currentSubType will be "Finanacial Value"
						$scope.currentSubType = CoreCommonsService.findElementByKey($scope.subTypes, $scope.dataType.subType, "index");
						// currentMeasureClass will be null
						$scope.currentMeasureClass = CoreCommonsService.findElementByKey($scope.measureClasses, $scope.dataType.measureClass, "index");
						// Set values in SubType dropdown and MeasureClass dropdown - based on currentSubType and currentMeasureClass
						$scope.changeSubType("set");
						$scope.changeMeasureClass("set");
						$scope.submitButtonName = "Create";
						$scope.isMainDataLoaded = true;
					}

					/**
					 * After cancel editing/creating DataType.
					 */
					function close() {
						$scope.currentSubType = null;
						$scope.currentMeasureClass = null;
						$rootScope.$broadcast("DataTypeDetails:close");
					}

					/**
					 * Try to save DataType after click on button - save means update or insert.
					 */
					function save() {
						$rootScope.$broadcast("modal:blockAllOperations");
						DataTypesPageService.save($scope.dataType);
					}

					/**
					 * After getting DataType details from service. (Details are used to populate form fields). Watcher: https://docs.angularjs.org/api/ng/type/$rootScope.Scope
					 *
					 * @param {[type]} event [description]
					 * @param {Object} data [DataType details]
					 */
					$scope.$watchCollection(
						// "This function returns the value being watched. It is called for each turn of the $digest loop"
						function() {
							return $scope.dataType;
						},
						// "This is the change listener, called when the value returned from the above function changes"
						function(newValue, oldValue) {
							// This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
							// To protect against this we check if newValue isn't empty.
							if (newValue !== oldValue && Object.keys(newValue).length != 0) {
								$scope.dataType = newValue;
								$scope.currentSubType = CoreCommonsService.findElementByKey($scope.subTypes, $scope.dataType.subType, "index");
								$scope.currentMeasureClass = CoreCommonsService.findElementByKey($scope.measureClasses, $scope.dataType.measureClass, "index");
								// Set dropdowns to appropriate values
								$scope.changeSubType("set");
								$scope.changeMeasureClass("set");
								$scope.isMainDataLoaded = true;
							}
						}
					);

					/**
					 * Insert/update: On error after http->success() with error=true or http->error()
					 *
					 * @param {[type]} event [description]
					 * @param {Object} data [Response Message]
					 */
					$scope.$on("DataTypesPageService:dataTypeDetailsSaveError", function(event, data) {
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
					 *
					 * @param {[type]} event [description]
					 * @param {Object} data [Response Message]
					 */
					$scope.$on("DataTypeDetails:close", function(event, data) {
						$scope.isError = false;
						if (typeof data != "undefined") {
							if (data.success) {
								var operation = "";
								if (data.method == "POST") {
									operation = "created";
								} else if (data.method == "PUT") {
									operation = "updated";
								}
								Flash.create('success', "Data Type (" + $scope.dataType.dataTypeVisId + ") is " + operation + ".");
							}
						}
					});

				}
			]
		};
	}
})();