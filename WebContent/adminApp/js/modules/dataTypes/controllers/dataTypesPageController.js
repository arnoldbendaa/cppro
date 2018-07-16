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
		.module('adminApp.dataTypesPage')
		.controller('DataTypesPageController', DataTypesPageController);

	/* @ngInject */
	function DataTypesPageController($rootScope, $scope, $compile, $timeout, $modal, PageService, FilterService, Flash, DataTypesPageService, CoreCommonsService, ContextMenuService) {

		var dataTypes = {};
		$scope.selectedDataTypeId = -1;
		$scope.selectedDataTypeVisId = "";
		$scope.ctx = {};
		// parameters to save cookie for flex grid
		$scope.cookieName = "adminPanel_dataType";
		$scope.resizedColumn = resizedColumn;
		$scope.sortedColumn = sortedColumn;
		$scope.selectionChangedHandler = selectionChangedHandler;
		$scope.open = open;
		$scope.deleteDataType = deleteDataType;
		activate();

		function activate() {
			PageService.setCurrentPageService(DataTypesPageService);
			dataTypes = $scope.dataTypes = DataTypesPageService.getDataTypes();
			$scope.globalSettings = CoreCommonsService.globalSettings;
			$scope.subTypes = DataTypesPageService.getSubTypes();
			$scope.ctx = {
		            flex: null,
		            filter: '',
		            data: dataTypes
		        };
			$scope.measureClasses = DataTypesPageService.getMeasureClasses();
			ContextMenuService.initialize($('.grid'));
			// try to resize and sort colums based on the cookie
			$scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
			$scope.$parent.onFilterWordChange("");
		}

		function resizedColumn(sender, args) {
			CoreCommonsService.resizedColumn(args, $scope.cookieName);
		}

		function sortedColumn(sender, args) {
			CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
		}

		/**
		 * Set selected table row as current. If previous selected row id is different from current, change selectedDataTypeId. Otherwise reset selectedDataTypeId ('cause DataType was deselected).
		 *
		 * @param {Integer} dataTypeId
		 * @param {String} dataTypeNarrative to show this name of DataType in confirm and massages
		 */
		function selectionChangedHandler() {
			var flex = $scope.ctx.flex;
			var current = flex.collectionView ? flex.collectionView.currentItem : null;
			if (current !== null) {
				$scope.selectedDataTypeId = current.dataTypeId;
				$scope.selectedDataTypeVisId = current.dataTypeVisId;
			} else {
				$scope.selectedDataTypeId = -1;
				$scope.selectedDataTypeVisId = "";
			}
			manageActions();
		}

		/**
		 * Call "open modal" for update after click on button on the listing
		 */
		function open() {
			openModal($scope.selectedDataTypeId);
		}


		/**
		 * Call deleting Data Type
		 */
		function deleteDataType() {
			if ($scope.selectedDataTypeId == -1) {
				return;
			}
			swal({
				title: "Are you sure",
				text: "you want to delete DataType \"" + $scope.selectedDataTypeVisId + "\"?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
					DataTypesPageService.deleteDataType($scope.selectedDataTypeId);
				}
			});
		}

		/**
		 * Enable or disable top buttons due to selected row (enable if any is selected).
		 */
		var manageActions = function() {
			var actions = DataTypesPageService.getActions();
			DataTypesPageService.isOpenDisabled = $scope.selectedDataTypeId === -1;
			DataTypesPageService.isDeleteDisabled = $scope.selectedDataTypeId === -1;
			if ($scope.selectedDataTypeId !== -1) {
				PageService.enableActions(actions);
			} else {
				PageService.disableActions(actions);
			}
			ContextMenuService.updateActions();
			$rootScope.$broadcast('PageService:changeService', {});
		};

		/**
		 * Open modal (for insert or update). Insert when dataTypeId equals -1, update otherwise.
		 *
		 * @param {Integer} dataTypeId [description]
		 */
		var openModal = function(dataTypeId) {
			var modalInstance = $modal.open({
				template: '<details-data-type id="dataTypeId"></details-data-type >',
				windowClass: 'data-type-modals softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.dataTypeId = dataTypeId;
						$scope.$on('DataTypeDetails:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		};

		/** ****************************************************** FILTERS ******************************************************* */
		var filters = ['byMainWordFilter'];
		var filterFunction = FilterService.buildFilterFunction(filters);

		$rootScope.$watch(function() {
			return $rootScope.filter.byWord;
		}, function() {
			FilterService.doFilter($scope, filterFunction);
		});

		/** ****************************************************** CUSTOM CELLS ******************************************************* */
		function itemFormatter(panel, r, c, cell) {
			if (panel.cellType == wijmo.grid.CellType.Cell) {

				var col = panel.columns[c],
					html = cell.innerHTML;
				switch (col.name) {
					case 'index':
						html = "" + (r + 1);
						break;
					case 'buttons':
						html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open()">Open</button>' +
							' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteDataType()">Delete</button>';
						break;
				}

				if (html != cell.innerHTML) {
					cell.innerHTML = html;
					$compile(cell)($scope);
				}
			}
		}

		/** ****************************************************** WATCHERS ******************************************************* */
		$scope.$watch('ctx.flex', function() {
			var flex = $scope.ctx.flex;
			if (flex) {
				flex.isReadOnly = true;
				flex.selectionMode = "Row";
				flex.headersVisibility = "Column";
				flex.itemFormatter = itemFormatter;
				CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
			}
		});

		$scope.$on('SubMenuController:open', function() {
			$scope.open();
		});

		$scope.$on('SubMenuController:create', function() {
			openModal(-1);
		});

		$scope.$on('SubMenuController:deleteDataType', function() {
			$scope.deleteDataType();
		});

		$scope.$on("SubMenuController:refresh", function() {
			DataTypesPageService.getDataTypes(true);
		});

		/**
		 * Delete: On error after http->success() with error=true or http->error()
		 *
		 * @param {[type]} event [description]
		 * @param {Object} data [Response Message]
		 */
		$scope.$on("DataTypesPageService:dataTypeDetailsDeleteError", function(event, data) {
			$scope.isError = true;
			// error from method http->success() and from field "error"
			Flash.create('danger', data.message);
		});

		$scope.$on("DataTypesPageService:dataTypeDetailsDeleteSuccess", function() {
			var msg = "Data Type \"" + $scope.selectedDataTypeVisId + "\" is deleted.";
			$scope.selectedDataTypeId = -1;
			$scope.selectedDataTypeVisId = "";
			manageActions();
			Flash.create('success', msg);
		});
	}
})();