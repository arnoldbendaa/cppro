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
		.controller('ExternalDestinationController', ExternalDestinationController);

	/* @ngInject */
	function ExternalDestinationController($rootScope, $scope, $compile, $modal, PageService, FilterService, ExternalDestinationService, Flash, CoreCommonsService, ContextMenuService) {

		var reports = {}
		$scope.selectedReportId = -1;
		$scope.selectedReportVisId = "";
		$scope.ctx = {};
		// parameters to save cookie for flex grid
		$scope.cookieName = "adminPanel_externalDestination";
		$scope.resizedColumn = resizedColumn;
		$scope.sortedColumn = sortedColumn;
		$scope.selectionChangedHandler = selectionChangedHandler;
		$scope.open = open;
		$scope.deleteExternalDestination = deleteExternalDestination;
		activate();

		function activate() {
			PageService.setCurrentPageService(ExternalDestinationService);
			$scope.globalSettings = CoreCommonsService.globalSettings;
			reports = ExternalDestinationService.getReports();
			$scope.ctx = {
				flex: null,
				filter: '',
				data: reports
			};
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
		 * Set selected table row as current.
		 * If previous selected row id is different from current, change selectedReportId.
		 * Otherwise reset selectedReportId (because Report was deselected).
		 */
		function selectionChangedHandler() {
			var flex = $scope.ctx.flex;
			var current = flex.collectionView ? flex.collectionView.currentItem : null;
			if (current != null) {
				$scope.selectedReportId = current.reportId;
				$scope.selectedReportVisId = current.reportVisId;
			} else {
				$scope.selectedReportId = -1;
				$scope.selectedReportVisId = "";
			}
			manageActions();
		}

		/**
		 * Call "open modal" for update after click on button on the listing
		 */
		function open() {
			openModal($scope.selectedReportId);
		}

		/**
		 * Show alert before delete and then call deleting method.
		 */
		function deleteExternalDestination() {
			if ($scope.selectedReportId == -1) {
				return;
			}
			swal({
				title: "Are you sure",
				text: "you want to delete External Users Destination \"" + $scope.selectedReportVisId + "\"?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
					ExternalDestinationService.deleteExternalDestination($scope.selectedReportId);
				}
			});
		}

		/**
		 * Enable or disable top buttons due to selected row (enable if any is selected).
		 */
		var manageActions = function() {
			var actions = ExternalDestinationService.getActions();
			ExternalDestinationService.isOpenDisabled = $scope.selectedReportId === -1;
			ExternalDestinationService.isDeleteDisabled = $scope.selectedReportId === -1;
			if ($scope.selectedReportId !== -1) {
				PageService.enableActions(actions);
			} else {
				PageService.disableActions(actions);
			}
			ContextMenuService.updateActions();
			$rootScope.$broadcast('PageService:changeService', {});
		};

		/**
		 * Open modal (for insert or update). Insert when reportId equals -1, update otherwise.
		 * @param  {Integer} reportId
		 */
		var openModal = function(reportId) {
			var modalInstance = $modal.open({
				template: '<external-destination-details id="reportId"></report-details>',
				windowClass: 'softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.reportId = reportId;
						$scope.$on('ExternalDestinationDetails:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		};

		/******************************************************** FILTERS ********************************************************/
		var filters = ['byMainWordFilter', 'byModelFilter'];
		var filterFunction = FilterService.buildFilterFunction(filters);

		$rootScope.$watch(function() {
			return $rootScope.filter.byWord;
		}, function() {
			FilterService.doFilter($scope, filterFunction);
		});

		$rootScope.$watch(function() {
			return $rootScope.currentModel;
		}, function() {
			FilterService.doFilter($scope, filterFunction);
		});

		/******************************************************** CUSTOM CELLS ********************************************************/
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
							' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteExternalDestination()">Delete</button>';
						break;
				}

				if (html != cell.innerHTML) {
					cell.innerHTML = html;
					$compile(cell)($scope);
				}
			}
		}
		/******************************************************** WATCHERS ********************************************************/
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

		$scope.$on("SubMenuController:open", function() {
			$scope.open();
		});

		$scope.$on("SubMenuController:create", function() {
			openModal(-1);
		});

		$scope.$on("SubMenuController:deleteExternalDestination", function() {
			$scope.deleteExternalDestination();
		});

		$scope.$on("SubMenuController:refresh", function() {
			ExternalDestinationService.getReports(true);
		});

		/**
		 * Delete: On error after http->success() with error=true or http->error()
		 * @param  {[type]} event [description]
		 * @param  {Object} data  [Response Message]
		 */
		$scope.$on("ExternalDestinationService:reportDetailsDeleteError", function(event, data) {
			$scope.isError = true;
			// error from method http->success() and from field "error"
			Flash.create('danger', data.message);
		});

		$scope.$on("ExternalDestinationService:reportDetailsDeleteSuccess", function() {
			var msg = "Report \"" + $scope.selectedReportVisId + "\" is deleted.";
			$scope.selectedReportId = -1;
			$scope.selectedReportVisId = "";
			manageActions();
			Flash.create('success', msg);
		});

	}
})();