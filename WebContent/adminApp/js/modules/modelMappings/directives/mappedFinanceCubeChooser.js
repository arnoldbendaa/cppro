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
        .module('adminApp.modelMappings')
        .directive('mappedFinanceCubeChooser', mappedFinanceCubeChooser);

    /* @ngInject */
    function mappedFinanceCubeChooser(CoreCommonsService, ModelMappingsService, $timeout) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'modelMappings/views/mappedFinanceCubeChooser.html',
			scope: {
				global: '=',
				years: '=',
				financeCube: "=",
				financeCubeCollectionView: "=collectionView",
				cancel: '&'
			},
			replace: true,
			controller: ['$scope',
				function($scope) {
					var cancelModal = $scope.cancel;
					var actions = [{
                        id: 0,
                        visId: 'IMPORT ONLY'
                    }, {
                        id: 1,
                        visId: 'EXPORT ONLY'
                    }, {
                        id: 2,
                        visId: 'IMPORT/EXPORT'
                    }];
                    var usedDataTypes = [];
                    var currentNode, currentDataType = null;
                    var yearStart = $scope.years[0].name;
                    var yearEnd = $scope.years[$scope.years.length - 1].name;
                    $scope.financeCubeId = $scope.financeCube.financeCubeId;
                    $scope.financeCubeVisId = $scope.financeCube.financeCubeVisId;
                    $scope.financeCubeDescription = $scope.financeCube.financeCubeDescription;
                    var dataTypeView = new wijmo.collections.CollectionView();
                    $scope.ctx = {
                        flex: null,
                        filter: '',
                        data: dataTypeView
                    };
                    $scope.dataTypes = ModelMappingsService.getDataTypesDetails();
                    $scope.exportImportMap = new wijmo.grid.DataMap(actions, 'id', 'visId');
                    $scope.yearsMap = new wijmo.grid.DataMap($scope.years, 'name', 'name');
                    $scope.isAddDataTypeDisabled = true;
                    $scope.isDeleteDataTypeDisabled = true;
				
					if ($scope.global) {
						$scope.treeAjaxDataType = '/cppro/adminPanel/modelMappings/externalDataTypesGlobal/' + $scope.years[0].name;
					} else {
						$scope.treeAjaxDataType = '/cppro/adminPanel/modelMappings/externalDataTypes/' + $scope.years[0].name;
					}

					var flexGrid = $('.mapped-finance-cube-chooser .flex-view');
					flexGrid.bind('clickoutside', function(event) {
						if ($scope.ctx.flex) {
							$scope.ctx.flex.finishEditing();
						}
					});

					$scope.contextMenu = {
						"Add": {
							"label": "Add",
							"action": function(data) {
								var inst = $.jstree.reference(data.reference),
									node = inst.get_node(data.reference);
								addDataTypeToFlex(currentNode);
								$scope.$apply("isOkBtnDisabled");
							}
						}
					};

					$scope.selectDataType = selectDataType;
                    $scope.addDataType = addDataType;
                    $scope.deleteDataType = deleteDataType;
                    $scope.cancel = cancel;
                    $scope.ok = ok;
					
					/**
					 * Manage data type map which will be used in drop dawn in flex grid
					 */
					function manageDataTypeMap() {
						$scope.dataTypeMap = new wijmo.grid.DataMap($scope.dataTypes, 'dataTypeId', 'dataTypeVisId');
					}

					/**
					 * Manage saved datatypes in database to rows which are used flex grid view
					 */
					function manageDataTypesRows(mappedDataTypes) {
						var flex = $scope.ctx.flex;
						var sourceCollection = dataTypeView.sourceCollection;
						sourceCollection.length = 0;
						usedDataTypes.length = 0;
						for (var i = 0; i < mappedDataTypes.length; i++) {
							var mappedDataTypeRow = mappedDataTypes[i];
							var dataType = CoreCommonsService.findElementByKey($scope.dataTypes, mappedDataTypeRow.dataTypeId, "dataTypeId");
							if (dataType) {
								var dataTypeRow = {
									mappedDateTypeId: mappedDataTypeRow.mappedDateTypeId,
									dataTypeId: dataType.dataTypeId,
									dataTypeVisId: dataType.dataTypeVisId,
									impExp: mappedDataTypeRow.impExpStatus,
									valueType: mappedDataTypeRow.valueType,
									currency: mappedDataTypeRow.currency,
									balanceType: mappedDataTypeRow.balType,
									impFromDate: manageYear($scope.years, mappedDataTypeRow.impStartYearOffset),
									impToDate: manageYear($scope.years, mappedDataTypeRow.impEndYearOffset),
									expFromDate: manageYear($scope.years, mappedDataTypeRow.expStartYearOffset),
									expToDate: manageYear($scope.years, mappedDataTypeRow.expEndYearOffset)
								};
								usedDataTypes.push(dataType);
								sourceCollection.push(dataTypeRow);
							} else {
								console.log("to mamy jakieś błedy");
							}
						}
						updateUI();
						$timeout(function() { // timeout is necessary to pass asynchro
							flex.refresh(true);
						}, 100);
					}

					/**
					 * Manage correct year string (which is used in flex grid view) related to offset and available years (first parameter) in mapped calendar
					 */
					function manageYear(years, offset) {
						if (offset === null || offset === undefined || !years || years.length <= 0) {
							return null;
						}
						var tmpOffset = 0;
						var year = null;
						do {
							var yearObj = years[years.length - 1 + tmpOffset];
							if (yearObj) {
								year = yearObj.name;
							} else {
								return year;
							}
							tmpOffset--;
						}
						while (tmpOffset >= offset);
						return year;
					}

					/**
					 * Function which is used in checking if years are stored chronologically
					 */
					function isOrdered(num1, num2) {
						if (!num1 || !num2) {
							return false;
						}
						num1 = parseInt(num1);
						num2 = parseInt(num2);
						if (isNaN(num1) || isNaN(num2)) {
							return false;
						}
						return num1 <= num2;
					}

					/**
					 * Manage correct dates for passed row depends on impExp type. If type doesn't 'accept' specific dates, dates are set as null.
					 */
					function manageImportExportDate(row, type) {
						switch (type) {
							case 0:
								row.impFromDate = (row.impFromDate) ? row.impFromDate : yearStart;
								row.impToDate = (row.impToDate) ? row.impToDate : yearEnd;
								row.expFromDate = null;
								row.expToDate = null;
								break;
							case 1:
								row.impFromDate = null;
								row.impToDate = null;
								row.expFromDate = (row.expFromDate) ? row.expFromDate : yearStart;
								row.expToDate = (row.expToDate) ? row.expToDate : yearEnd;
								break;
							case 2:
								row.impFromDate = (row.impFromDate) ? row.impFromDate : yearStart;
								row.impToDate = (row.impToDate) ? row.impToDate : yearEnd;
								row.expFromDate = (row.expFromDate) ? row.expFromDate : yearStart;
								row.expToDate = (row.expToDate) ? row.expToDate : yearEnd;
								break;
						}
					}

					/**
					 * Invoked when we end editing data type column (first column)
					 */
					function onDataTypeChange(row, newVal) {
						var usedDataType = CoreCommonsService.findElementByKey(usedDataTypes, newVal, "dataTypeVisId");
						if (usedDataType !== null) {
							console.log("datatype juz został wybrany");
							return true;
						} else {
							usedDataType = CoreCommonsService.findElementByKey(usedDataTypes, row.dataTypeId, "dataTypeId");
							var index = usedDataTypes.indexOf(usedDataType);
							usedDataTypes.splice(index, 1);

							var dataType = CoreCommonsService.findElementByKey($scope.dataTypes, newVal, "dataTypeVisId");
							usedDataTypes.push(dataType);
							if (dataType.availableForImport && dataType.availableForExport) {
								row.impExp = 2;
							} else if (dataType.availableForExport) {
								row.impExp = 1;
							} else if (dataType.availableForImport) {
								row.impExp = 0;
							}
							manageImportExportDate(row, row.impExp);
							return false;
						}
					}

					/**
					 * Invoked when we end editing imp/exp column (second column)
					 */
					function onImpExpChange(row, newVal) {
						var dataType = CoreCommonsService.findElementByKey($scope.dataTypes, row.dataTypeId, "dataTypeId");
						if (dataType.availableForImport === false && dataType.availableForExport === false) {
							return true;
						}
						if (newVal === 'IMPORT ONLY' && dataType.availableForImport) {
							newVal = 0;
						} else if (newVal === 'EXPORT ONLY' && dataType.availableForExport) {
							newVal = 1;
						} else if (newVal === 'IMPORT/EXPORT' && dataType.availableForImport && dataType.availableForExport) {
							newVal = 2;
						} else {
							return true;
						}
						manageImportExportDate(row, newVal);
						return false;
					}

					/**
					 * Invoked when we end editing one row of data type
					 */
					function onCellEditEnding(sender, e) {
						var flex = $scope.ctx.flex;
						var newVal = flex.activeEditor.value;
						var editedRow = $scope.ctx.data.currentEditItem;
						switch (sender.col) {
							case 0:
								sender.cancel = onDataTypeChange(editedRow, newVal);
								break;
							case 1:
								sender.cancel = onImpExpChange(editedRow, newVal);
								break;
							case 5:
								sender.cancel = !isOrdered(newVal, editedRow.impToDate);
								break;
							case 6:
								sender.cancel = !isOrdered(editedRow.impFromDate, newVal);
								break;
							case 7:
								sender.cancel = !isOrdered(newVal, editedRow.expToDate);
								break;
							case 8:
								sender.cancel = !isOrdered(editedRow.expFromDate, newVal);
								break;
						}
					}

					/**
					 * Add datatype (from tree view) stored in treeNode to flex grid collection
					 */
					function addDataTypeToFlex(treeNode) {
						if (treeNode.state.disabled) {
							swal({
								title: "Can't add " + treeNode.text,
								text: "I will close in 2 seconds.",
								type: "warning",
								timer: 2000
							});
						} else {
							var dataType = null;
							for (var i = 0; i < $scope.dataTypes.length; i++) {
								var availableDataType = $scope.dataTypes[i];
								var usedDataType = CoreCommonsService.findElementByKey(usedDataTypes, availableDataType.dataTypeId, "dataTypeId");
								if (usedDataType === null) {
									dataType = availableDataType;
									break;
								}
							}

							if (dataType) {
								var dataTypeRow = dataTypeView.addNew();
								dataTypeRow.mappedDateTypeId = -1;
								dataTypeRow.dataTypeId = dataType.dataTypeId;
								dataTypeRow.dataTypeVisId = dataType.dataTypeVisId;
								dataTypeRow.impExp = 0;
								dataTypeRow.valueType = treeNode.original.valueType;
								dataTypeRow.currency = treeNode.original.currency;
								dataTypeRow.balanceType = treeNode.original.balanceTyp;

								if (dataType.availableForImport && dataType.availableForExport) {
									dataTypeRow.impExp = 2;
									dataTypeRow.impFromDate = yearStart;
									dataTypeRow.impToDate = yearEnd;
									dataTypeRow.expFromDate = yearStart;
									dataTypeRow.expToDate = yearEnd;
								} else if (dataType.availableForExport) {
									dataTypeRow.impExp = 1;
									dataTypeRow.expFromDate = yearStart;
									dataTypeRow.expToDate = yearEnd;
								} else if (dataType.availableForImport) {
									dataTypeRow.impExp = 0;
									dataTypeRow.impFromDate = yearStart;
									dataTypeRow.impToDate = yearEnd;
								}
								dataTypeView.commitNew();
								usedDataTypes.push(dataType);
							} else {
								swal({
									title: "There in no available data types.",
									text: "I will close in 3 seconds.",
									type: "warning",
									timer: 3000
								});
							}
						}
					}

					/**
					 * Selects/Updates current (selected) node
					 */ 
					function selectDataType(e, data) {
						currentNode = data.node;
						updateUI();
					};

					/**
					 * Invoked when user click click green arrow on view. If currentNode is null, data type is not added.
					 */
					function addDataType() {
						if (currentNode === null) {
							return;
						}
						addDataTypeToFlex(currentNode);
					};

					/**
					 * Invoked when user wants to delete data type from flex grid collection. If none of rows are selected, currentDataType is null and data type is not deleted
					 */
					function deleteDataType() {
						if (currentDataType) {
							var nrOfRow = dataTypeView.sourceCollection.indexOf(currentDataType);
							var rowToDelete = dataTypeView.sourceCollection[nrOfRow];

							var usedDataType = CoreCommonsService.findElementByKey(usedDataTypes, rowToDelete.dataTypeId, "dataTypeId");
							var index = usedDataTypes.indexOf(usedDataType);
							usedDataTypes.splice(index, 1);

							dataTypeView.sourceCollection.splice(nrOfRow, 1);
							updateUI();
							onSelectionChanged();
						}
					};

					/**
					 * Manage proper offset (in data type row) for passed year
					 */
					function manageMappedOffset(year) {
						if (!year) {
							return null;
						}
						year = parseInt(year);
						yearEnd = parseInt(yearEnd);
						return year - yearEnd;
					}

					/**
					 * Manage proper data in data types which match to our DTO and sort collection by dataTypeId
					 */
					function manageMappedDataTypes(mappedDataTypes) {
						mappedDataTypes.length = 0;
						angular.forEach(dataTypeView.sourceCollection, function(row) {
							var mappedRow = {
								mappedDateTypeId: row.mappedDateTypeId,
								dataTypeId: row.dataTypeId,
								dataTypeVisId: row.dataTypeVisId,
								impExpStatus: row.impExp,
								valueType: row.valueType,
								balType: row.balanceType,
								currency: row.currency,
								impStartYearOffset: (row.impExp === 0 || row.impExp === 2) ? manageMappedOffset(row.impFromDate) : null,
								impEndYearOffset: (row.impExp === 0 || row.impExp === 2) ? manageMappedOffset(row.impToDate) : null,
								expStartYearOffset: (row.impExp === 1 || row.impExp === 2) ? manageMappedOffset(row.expFromDate) : null,
								expEndYearOffset: (row.impExp === 1 || row.impExp === 2) ? manageMappedOffset(row.expToDate) : null
							};
							mappedDataTypes.push(mappedRow);
						});
						mappedDataTypes.sort(function(mappedDataType1, mappedDataType2) {
							if (parseInt(mappedDataType1.dataTypeId) < parseInt(mappedDataType2.dataTypeId)) {
								return -1;
							} else if (parseInt(mappedDataType1.dataTypeId) > parseInt(mappedDataType2.dataTypeId)) {
								return 1;
							} else {
								return 0;
							}
						});
					}

					/**
					 * Close modal without any changes
					 */
					function cancel() {
						flexGrid.unbind('clickoutside');
						$scope.ctx.data.cancelEdit();
						cancelModal();
					};

					/**
					 * Save and close modal with data types with any changes
					 */
					function ok() {
						$scope.ctx.flex.finishEditing();
						$scope.financeCube.financeCubeVisId = $scope.financeCubeVisId;
						$scope.financeCube.financeCubeDescription = $scope.financeCubeDescription;
						manageMappedDataTypes($scope.financeCube.mappedDataTypes);

						if ($scope.financeCubeCollectionView.sourceCollection.length === 0) {
							$scope.financeCubeCollectionView.sourceCollection.push($scope.financeCube);
						}
						$scope.financeCubeCollectionView.refresh();
						cancelModal();
					};

					/**
					 * Updates UI (flex grid collection and all important buttons)
					 */
					function updateUI() {
						$scope.isOkBtnDisabled = dataTypeView.sourceCollection.length <= 0;
						$scope.isDeleteDataTypeDisabled = currentDataType === null;
						$scope.isAddDataTypeDisabled = currentNode === null || currentNode === undefined;
					}

					/**
					 * Invoked when user selects data type on flex grid
					 */
					function onSelectionChanged() {
						var flex = $scope.ctx.flex;
						currentDataType = flex.collectionView ? flex.collectionView.currentItem : null;
						updateUI();
					}

					$scope.$watch('ctx.flex', function() {
						var flex = $scope.ctx.flex;
						if (flex) {
							flex.selectionMode = "Row";
							flex.headersVisibility = "Column";
							flex.allowSorting = false;
							flex.autoClipboard = false;
							flex.onSelectionChanged = onSelectionChanged;
							flex.onCellEditEnding = onCellEditEnding;
							//CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
						}
					});

					$scope.$watch('dataTypes', function() {
						if ($scope.dataTypes.length > 0) {
							manageDataTypeMap();
							manageDataTypesRows($scope.financeCube.mappedDataTypes);
						}
					}, true);
				}
			]
		};
	}
})();