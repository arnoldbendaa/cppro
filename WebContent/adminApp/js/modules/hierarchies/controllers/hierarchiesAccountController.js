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
        .module('adminApp.hierarchiesPage')
        .controller('HierarchiesAccountController', HierarchiesAccountController);

    /* @ngInject */
    function HierarchiesAccountController($rootScope, $scope, $compile, $modal, PageService, Flash, FilterService, HierarchiesService, CoreCommonsService, ContextMenuService) {
       
		var hierarchiesAccounts = HierarchiesService.getHierarchies(true);
		$scope.hierarchyAccountId = -1;
		$scope.selectedHierarchiesAccountId = -1;
		$scope.selectedDimensionsAccountId = -1;		
		$scope.ctx = {};
		// parameters to save cookie for flex grid
		$scope.cookieName = "adminPanel_hierarchiesAccount";
		// try to resize and sort colums based on the cookie
		$scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
		$scope.resizedColumn = resizedColumn;
		$scope.sortedColumn = sortedColumn;
		$scope.selectionChangedHandler = selectionChangedHandler;
		$scope.open = open;
		$scope.deleteHierarchy = deleteHierarchy;
		activate();
		
		function activate(){
		    PageService.setCurrentPageService(HierarchiesService);
		    hierarchiesAccounts = HierarchiesService.getHierarchies(true);
	        $scope.globalSettings = CoreCommonsService.globalSettings;  
	        $scope.ctx = {
	                flex: null,
	                filter: '',
	                data: hierarchiesAccounts
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
		 * If previous selected row id is different from current, change selectedUsersId.
		 * Otherwise reset selectedUsersId ('cause hierarchyAccountId was deselected).
		 */
		function selectionChangedHandler() {
			var flex = $scope.ctx.flex;

			var current = flex.collectionView ? flex.collectionView.currentItem : null;

			if (current != null) {
				$scope.selectedHierarchiesAccountId = current.hierarchyId;
				$scope.selectedDimensionsAccountId = current.dimension.dimensionId;
			} else {
				$scope.selectedHierarchiesAccountId = -1;

			}
			manageActions();
		}

		/**
		 * Call "open modal" for update after click on button on the listing
		 * @param  {Integer} hierarchyAccountId [description]
		 */

		function open() {
			openModal($scope.selectedDimensionsAccountId, $scope.selectedHierarchiesAccountId);
		}
		/**
		 * Call deleting hierarchies
		 * @param  {Integer} hierarchyAccountId
		 */
		function deleteHierarchy(hierarchyAccountId) {

			if ($scope.selectedHierarchiesAccountId == -1) {
				return;
			} else {
				openDeletingConfirmBox();

			}

		}

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
						html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open();">Open</button> ' +
							'<button type="button" class="btn btn-danger btn-xs" ng-click="deleteHierarchy();">Delete</button>';
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

		var openDeletingConfirmBox = function() {
			swal({
				title: "Are you sure",
				text: "you want to delete Hierarchy Account?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
				    HierarchiesService.deleteHierarchy(true, $scope.selectedDimensionsAccountId, $scope.selectedHierarchiesAccountId);
				}
			});
		};
		/**
		 * Open modal (for insert or update). Insert when hierarchyId equals -1, update otherwise.
		 */
		var openModal = function(dimensionId, hierarchyId) {
			var modalInstance = $modal.open({
				template: '<hierarchy-details id="dimensionId" hierarchy-id="hierarchyId" type="type"></hierarchy-details>',
				windowClass: 'hierarchy-modals softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.dimensionId = dimensionId;
						$scope.hierarchyId = hierarchyId;
						$scope.type = true;

						$scope.$on('HierarchyDetails:close', function(event, args) {
						    HierarchiesService.dimension.dimensionId = null;
						    HierarchiesService.dimension.dimensionVisId = null;
						    HierarchiesService.dimension.description = null;
							$modalInstance.close();
						});
					}
				]
			});
		};

		/**
		 * Enable or disable top buttons due to selected row (enable if any is selected).
		 */
		var manageActions = function() {
			var actions = HierarchiesService.getActions();
			HierarchiesService.isOpenDisabled = $scope.selectedHierarchiesAccountId === -1;
			HierarchiesService.isDeleteDisabled = $scope.selectedHierarchiesAccountId === -1;
			if ($scope.selectedHierarchiesAccountId !== -1) {
			    PageService.enableActions(actions);
			} else {
			    PageService.disableActions(actions);
			}
			ContextMenuService.updateActions();
			$rootScope.$broadcast('PageService:changeService', {});
		};

		$scope.$on('SubMenuController:open', function() {
			$scope.open();
		});

		$scope.$on('SubMenuController:create', function() {
			openModal(-1, -1);
		});

		$scope.$on('SubMenuController:deleteHierarchy', function() {
			openDeletingConfirmBox($scope.selectedHierarchiesAccountId);
		});

		$scope.$on("SubMenuController:refresh", function() {
		    HierarchiesService.getHierarchies(true);
		});


		$scope.$on("HierarchiesService:budgetInstructionDetailsDeleteError", function(event, data) {
			$scope.isError = true;

			Flash.create('danger', data.message);
		});

		$scope.$on("HierarchiesService:budgetInstructionDetailsDeleteSuccess", function() {
			var msg = "Hierarchies Account " + " is deleted.";
			$scope.selectedHierarchiesAccountId = -1;
			manageActions();
			Flash.create('success', msg);
		});
		$scope.$on('HierarchyDetails:close', function(event, data) {
			if (data != null) {
				if (data.hierarchyId != -1) {
					var msg = "Hierarchy Account was changed";
					Flash.create('success', msg);
				} else if (data.hierarchyId == -1) {
					var msg = "Hierarchy Account is created";
					Flash.create('success', msg);
				}
			}
		});

	}
})();