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
		.module('adminApp.dashboardPage')
		.controller('HierarchyFormDashboardController', HierarchyFormDashboardController);

	/* @ngInject */
	function HierarchyFormDashboardController($rootScope, $scope, $compile, $modal, $http, PageService, FilterService, FlatFormsPageService, CoreCommonsService, ContextMenuService, HierarchyFormDashboardService) {

		$scope.cookieName = "adminPanel_dashboard_hierarchy";
		$scope.resizedColumn = resizedColumn;
		//$scope.sortedColumn = sortedColumn;

		$scope.open = open;
		$scope.edit = edit;
		$scope.deleteHierarchy = deleteHierarchy;
		$scope.selectionChangedHandler = selectionChangedHandler;
		var currentSelectedItemUUID;
		var isAllowOpen, isAllowEdit;

		var formHierarchies = new wijmo.collections.CollectionView();
		$scope.ctx = {
			flex: null,
			filter: '',
			data: formHierarchies
		};

		activate();

		function activate() {
			PageService.setCurrentPageService(HierarchyFormDashboardService);
			HierarchyFormDashboardService.getFormHierarchies()
				.then(function(data) {
					formHierarchies.sourceCollection = data;
				});
			isAllowOpen = HierarchyFormDashboardService.getHierarchyPrivilegeOpen();
			isAllowEdit = HierarchyFormDashboardService.getHierarchyPrivilegeEdit();
			$scope.$parent.onFilterWordChange("");
		}

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
						if (isAllowOpen == true) {
							html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open();" > Open</button> ';
						}
						if (isAllowEdit == true) {
							html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open();" > Open</button> ' +
								'<button type="button" class="btn btn-primary btn-xs" ng-click="edit();" > Edit</button> ' +
								'<button type="button" class="btn btn-danger btn-xs" ng-click="deleteHierarchy();" >Delete</button>';
						}
						break;
				}
				if (html != cell.innerHTML) {
					cell.innerHTML = html;
					$compile(cell)($scope);
				}
			}
		}

		function resizedColumn(sender, args) {
			CoreCommonsService.resizedColumn(args, $scope.cookieName);
		}

		function resizedColumn(sender, args) {
			CoreCommonsService.resizedColumn(args, $scope.cookieName);
		}

		function open() {
			window.open($BASE_PATH + 'dashboard/form/hierarchy/' + currentSelectedItemUUID, '_blank');
		}

		function edit() {
			window.open($BASE_PATH + 'dashboard/form/hierarchy/edit/' + currentSelectedItemUUID, '_blank');
		}

		function deleteHierarchy() {
			swal({
				title: "Are you sure",
				text: "to delete hierarchy (ID = \"" + currentSelectedItemUUID + "\") ?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
					$http.delete("/cppro/dashboard/form/delete/" + currentSelectedItemUUID).success(function(data) {
						console.log("deleted", currentSelectedItemUUID);
						activate();
					});
				}
			});
		}

		function create() {
			window.open($BASE_PATH + 'dashboard/form/hierarchy', '_blank');
		}

		function selectionChangedHandler() {
			var flex = $scope.ctx.flex;
			var current = flex.collectionView ? flex.collectionView.currentItem : null;
			if (current == null) {
				return;
			}
			currentSelectedItemUUID = current.dashboardUUID;
		}

		//////////////////////////////////// WATCHERS ////////////////////////////////////////////////////////////////

		$scope.$watch('ctx.flex', function() {
			var flex = $scope.ctx.flex;
			if (flex) {
				flex.isReadOnly = true;
				flex.selectionMode = "Row";
				flex.headersVisibility = "Column";
				//flex.onScrollPositionChanged = onScrollPositionChanged;
				flex.itemFormatter = itemFormatter;
				CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
			}
		});


		//////////////////////  broadcast handle ////

		$scope.$on("SubMenuController:create", function() {
			create();
		});

		$scope.$on("SubMenuController:open", function() {
			open();
		});

		$scope.$on("SubMenuController:refresh", function() {
			activate();
		});
	}
})();