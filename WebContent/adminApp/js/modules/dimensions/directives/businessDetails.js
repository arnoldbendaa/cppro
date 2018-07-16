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
        .directive('dimensionsBusinessDetails', dimensionsBusinessDetails);

    /* @ngInject */
    function dimensionsBusinessDetails($timeout, $compile, $rootScope, Flash, BusinessService, CoreCommonsService, DimensionCommonsService) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'dimensions/views/businessDetails.html',
			scope: {
				id: '='
			},
			replace: true,
			controller: ['$scope',
				function($scope) {

					// parameters to resize modal
					var modalDialog = $(".dimensions-business-details").closest(".modal-dialog");
					var elementToResize = $(".dim-elems-grid");
					var additionalElementsToCalcResize = [$(".inUseLabel"), $(".dimension-main-data"), $(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3"), $(".groupTitle"), $(".edit-dim-elem")];
					$scope.cookieName = "adminPanel_modal_dimBusiness";
					// try to resize and move modal based on the cookie
					CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
					$scope.globalSettings = CoreCommonsService.globalSettings;
					$scope.isMainDataLoaded = false;
					$scope.isAnyNullElement = false;
					$scope.selectedDimensionElementId = -1;
					$scope.selectedDimensionElement = {};
					$scope.business = {};
					$scope.isError = false;
					// validation messages: messageError for top message bar, validation fields for each form fields
					// If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
					$scope.messageError = "";
					$scope.validation = {
						dimensionVisId: "",
						dimensionDescription: "",
						dimensionElements: {}
					};					
					$scope.resizedColumn = resizedColumn;
					$scope.sortedColumn = sortedColumn;
					$scope.selectionChangedHandler = selectionChangedHandler;
					$scope.refreshData = refreshData;
					$scope.close = close;
					$scope.save = save;
					$scope.markDimensionElementAsUpdated = markDimensionElementAsUpdated;
					$scope.setAnyNullElement = setAnyNullElement;
					$scope.createDimensionElement = createDimensionElement;
					$scope.deleteDimensionElement = deleteDimensionElement;
					
					function resizedColumn(sender, args) {
					    CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    };
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    };
					if ($scope.id != -1) {
						// edit Dimension
						// get Dimension details
						$scope.business = BusinessService.getBusinessDetails($scope.id);
						$scope.submitButtonName = "Save";
					} else {
						// create Dimension
						// create new empty Dimension object
						var emptyBusiness = BusinessService.createEmptyBusiness($scope.id);
						$scope.business = emptyBusiness;
						$scope.submitButtonName = "Create";
						$scope.isMainDataLoaded = true;
					}

					// ----------------------------- wijmo -----------------------------

					var dimensionElementsWijmoCollection = new wijmo.collections.CollectionView();

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
								dimensionElementId = panel.rows[r].dataItem['dimensionElementId'];
							switch (col.name) {
								case 'index':
									html = "" + (r + 1);
									break;
								case 'dimensionElementVisId':
								    DimensionCommonsService.formatValidationInfo(dimensionElementId, 'dimensionElementVisId', $scope.validation.dimensionElements[dimensionElementId], cell);
									break;
								case 'dimensionElementDescription':
								    DimensionCommonsService.formatValidationInfo(dimensionElementId, 'dimensionElementDescription', $scope.validation.dimensionElements[dimensionElementId], cell);
									break;
								case 'nullElement':
									// don't allow set any element as null if found one null now (but only if it's not element to remove ater submit tasks)
									if (panel.rows[r].dataItem['nullElement'] && panel.rows[r].dataItem['operation'] != "remove") {
										$scope.setAnyNullElement(true);
									}
									break;
								case 'creditDebit':
									html = DimensionCommonsService.formatCreditDebit(parseInt(panel.rows[r].dataItem['creditDebit']));
									cell.style.textAlign = "left";
									break;
								case 'augCreditDebit':
									html = DimensionCommonsService.formatAugCreditDebit(parseInt(panel.rows[r].dataItem['augCreditDebit']));
									cell.style.textAlign = "left";
									break;
								case 'buttons':
									html = '<button type="button" class="btn btn-danger btn-xs" ng-click="deleteDimensionElement(' + panel.rows[r].dataItem['dimensionElementId'] + ')" ng-class="{disabled: business.readOnly || business.augentMode}">Delete</button>';
									break;
							}

							// set new HTML inside cell
							if (html != cell.innerHTML) {
								cell.innerHTML = html;
								$compile(cell)($scope);
							}

							// set small opacity for deleted rows (only old rows which exists in database, because new rows during delete operation are permanently removed from collection)
							if (panel.rows[r].dataItem['operation'] == "remove") {
								cell.style.opacity = 0.3;
							} else {
								cell.style.opacity = 1;
							}
						} else if (panel.cellType == wijmo.grid.CellType.ColumnHeader && panel.columns[c].name != "index") {
							// Center almost every column header text (default centering for numeric columns is right).
							cell.style.textAlign = "center";
						}
					};

					// parameters to resize modal
					var modalDialog = $(".dimensions-business-details").closest(".modal-dialog");
					var elementToResize = $(".dim-elems-grid");
					var additionalElementsToCalcResize = [$(".inUseLabel"), $(".dimension-main-data"), $(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3"), $(".groupTitle"), $(".edit-dim-elem")];

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

					$scope.$watch('business.dimensionElements', function() {
						if ($scope.business.dimensionElements != undefined) {
							dimensionElementsWijmoCollection.sourceCollection = $scope.business.dimensionElements;
							$scope.ctx.data = dimensionElementsWijmoCollection;
							flexInitialize();
						}
					});

					$scope.$watch('selectedDimensionElement', function() {
						var flex = $scope.ctx.flex;
						if (flex) {
							flex.refresh(false);
							$timeout(function() {
							    CoreCommonsService.resizeModalElement(modalDialog, elementToResize, additionalElementsToCalcResize);
							}, 0);
						}
					}, true);

					// Set selected table row as current.
					function selectionChangedHandler() {
						var flex = $scope.ctx.flex;
						var current = flex.collectionView ? flex.collectionView.currentItem : null;
						if (current != null) {
							$scope.selectedDimensionElement = current;
							$scope.selectedDimensionElementId = current.dimensionElementId;
						} else {
							$scope.selectedDimensionElement = {};
							$scope.selectedDimensionElementId = -1;
						}
					};

					function refreshData() {
						$scope.ctx.data.refresh();
					};
					// ----------------------------- end of wijmo -----------------------------

					/**
                     * After cancel editing/creating Dimension.
                     */
					function close() {
						$rootScope.$broadcast("BusinessDetails:close");
					};

					/**
                     * Try to save Dimension after click on button - save means update or insert.
                     */
					function save() {
						// Check if anything changed with dimensionElements and if Dimension has Model ($scope.business.model.modelId > 0).
						// If yes - show alert with "submit immediately" question.
						// If we save Dimension without Model, task for its Elements is not created, so additional question is unnecessary.
						var operationCount = 0;
						angular.forEach($scope.business.dimensionElements, function(element) {
							if (element.operation == "update" || element.operation == "insert" || element.operation == "remove") {
								operationCount++;
							}
						});
						if (operationCount > 0 && $scope.business.model.modelId > 0) {
							swal({
								title: "Changes will be saved.",
								text: "Do you wish to submit the change management request immediately?",
								type: "warning",
								showCancelButton: true,
								confirmButtonColor: "#d9534f",
								confirmButtonText: "Yes",
								cancelButtonText: "No",
							}, function(isConfirm) {
								if (isConfirm) {
									$scope.business.submitChangeManagementRequest = true;
									$rootScope.$broadcast("modal:blockAllOperations");
									BusinessService.save($scope.business);
								} else {
									$scope.business.submitChangeManagementRequest = false;
									$rootScope.$broadcast("modal:blockAllOperations");
									BusinessService.save($scope.business);
								}
							});
						} else {
							$rootScope.$broadcast("modal:blockAllOperations");
							BusinessService.save($scope.business);
						}
					};

					// change "operation" to "update" (if it isn't new element), i.e. function is called on change in filed with D.E. identifier
					function markDimensionElementAsUpdated() {
						if ($scope.selectedDimensionElementId > -1) { // it's not new element (operation of new elem. is always "insert")
							$scope.selectedDimensionElement.operation = "update";
						}
					};

					// don't allow set other element as null if now you set Dimension Element as null
					function setAnyNullElement(isAnyNullElement) {
						if (isAnyNullElement) {
							$scope.isAnyNullElement = true;
						}
					};

					// new element has to have any id, so we set negative value "-2" and reduce it for every next new element
					$scope.newDimensionElementTempId = -2;
					function createDimensionElement() {
						var newElement = {
							dimensionElementId: $scope.newDimensionElementTempId,
							dimensionElementVisId: "",
							dimensionElementDescription: "",
							creditDebit: 2,
							augCreditDebit: 0,
							disabled: false,
							leaf: true,
							notPlannable: false,
							nullElement: false,
							operation: "insert"
						};
						$scope.business.dimensionElements.push(newElement);
						$scope.newDimensionElementTempId--;
						$scope.refreshData();
					};


					function deleteDimensionElement(dimensionElementId) {
						var itemToDelete = CoreCommonsService.findElementByKey($scope.business.dimensionElements, dimensionElementId, "dimensionElementId");
						// allow set other element as null if now you remove Dimension Element which was null
						if (itemToDelete.nullElement) {
							itemToDelete.nullElement = false;
							$scope.isAnyNullElement = false;
						}
						var index = $scope.business.dimensionElements.indexOf(itemToDelete);
						if (itemToDelete.dimensionElementId > -1) {
							// It was element existed in database, so we have to send it to backend (to remove function).
							itemToDelete.operation = "remove";
						} else {
							// It was new element, so we don't have to send it via request. We only delete it from model.
							$scope.business.dimensionElements.splice(index, 1);
						}
						$scope.selectedDimensionElementId = -1;
						$scope.selectedDimensionElement = {};
						$scope.refreshData();
					};

					/**
                     * After getting Dimension details from service. (Details are used to populate form fields). Watcher: https://docs.angularjs.org/api/ng/type/$rootScope.Scope
                     * 
                     * @param {[type]} event [description]
                     * @param {Object} data [Dimension details]
                     */
					$scope.$watchCollection(
						// "This function returns the value being watched. It is called for each turn of the $digest loop"
						function() {
							return $scope.business;
						},
						// "This is the change listener, called when the value returned from the above function changes"
						function(newValue, oldValue) {
							// This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
							// To protect against this we check if newValue isn't empty.
							if (newValue !== oldValue && Object.keys(newValue).length != 0) {
								$scope.business = newValue;
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
					$scope.$on("BusinessService:businessDetailsSaveError", function(event, data) {
						$rootScope.$broadcast("modal:unblockAllOperations");
						$scope.isError = true;
						if (typeof data.title == "undefined" || data.title == null) {
							// error from method http->success() and from field "error"
							Flash.create('danger', data.message);
						} else {
							// error from method http->error()
							var messageToReturn = data.title + " " + data.message.split("\n")[0];
							Flash.create('danger', messageToReturn);
						}
						// clear previous validation messages
						angular.forEach($scope.validation, function(message, field) {
							if (field == "dimensionElements") {
								$scope.validation[field] = {};
							} else {
								$scope.validation[field] = "";
							}
						});
						// set new messages
						angular.forEach(data.fieldErrors, function(error) {
							if (error.fieldName in $scope.validation) {
								$scope.validation[error.fieldName] = error.fieldMessage;
							} else {
								var fieldArray = (error.fieldName).split(".");
								// if it's dimensionElement validation error
								if (fieldArray[0] == "dimensionElements") {
									var dimensionElementId = fieldArray[1];
									var fieldName = fieldArray[2];
									if ($scope.validation.dimensionElements[dimensionElementId] != null) {
										$scope.validation.dimensionElements[dimensionElementId][fieldName] = error.fieldMessage;
									} else {
										$scope.validation.dimensionElements[dimensionElementId] = {};
										$scope.validation.dimensionElements[dimensionElementId][fieldName] = error.fieldMessage;
									}
								}
							}
						});
						$scope.refreshData();
					});

					/**
                     * On modal close. If is set any response with "success" field, show info success message.
                     * 
                     * @param {[type]} event [description]
                     * @param {Object} data [Response Message]
                     */
					$scope.$on("BusinessDetails:close", function(event, data) {
						$scope.isError = false;
						if (typeof data != "undefined") {
							if (data.success) {
								var operation = "";
								if (data.method == "POST") {
									operation = "created";
								} else if (data.method == "PUT") {
									operation = "updated";
								}
								Flash.create('success', "Business (" + $scope.business.dimensionVisId + ") is " + operation + ".");
							}
						}
					});

				}
			]
		};
	}
})();