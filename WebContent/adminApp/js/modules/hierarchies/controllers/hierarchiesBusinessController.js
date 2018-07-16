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
        .controller('HierarchiesBusinessController', HierarchiesBusinessController);

    /* @ngInject */
    function HierarchiesBusinessController($rootScope, $scope, $compile, $modal, PageService, Flash, FilterService, HierarchiesService, CoreCommonsService, ContextMenuService) {

        var hierarchiesBusiness = {};
		$scope.hierarchyBusinessId = -1;
		$scope.selectedHierarchiesBusinessId = -1;
		$scope.selectedDimensionsBusinessId = -1;
		$scope.selectedModelId = -1;
		$scope.ctx = {};
		// parameters to save cookie for flex grid
		$scope.cookieName = "adminPanel_hierarchiesBusiness";
		$scope.resizedColumn = resizedColumn;
		$scope.sortedColumn = sortedColumn;
		$scope.selectionChangedHandler = selectionChangedHandler;
		$scope.open = open;
		$scope.deleteHierarchy = deleteHierarchy;
		activate();
		
		function activate(){
		    PageService.setCurrentPageService(HierarchiesService);
	        $scope.globalSettings = CoreCommonsService.globalSettings;
	        hierarchiesBusiness = HierarchiesService.getHierarchies(false);
	        $scope.ctx = {
	                flex: null,
	                filter: '',
	                data: hierarchiesBusiness
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
		 * Otherwise reset selectedUsersId ('cause budgetInstructionsId was deselected).
		 */
		function selectionChangedHandler() {
			var flex = $scope.ctx.flex;
			var current = flex.collectionView ? flex.collectionView.currentItem : null;
			if (current != null) {
				$scope.selectedHierarchiesBusinessId = current.hierarchyId;
				$scope.selectedDimensionsBusinessId = current.dimension.dimensionId;
				$scope.selectedModelId = current.model.modelId;
			} else {
				$scope.selectedHierarchiesBusinessId = -1;

			}
			manageActions();
		}

		/**
		 * Call "open modal" for update after click on button on the listing
		 * @param  {Integer} hierarchyBusinessId [description]
		 */

		function open() {
		    openModal($scope.selectedDimensionsBusinessId, $scope.selectedHierarchiesBusinessId, $scope.selectedModelId);

		};
		/**
		 * Call deleting hierarchyBusiness
		 * @param  {Integer} hierarchyBusinessId
		 */
		function deleteHierarchy(hierarchyBusinessId) {

			if ($scope.selectedHierarchiesBusinessId == -1) {
				return;
			} else {
				openDeletingConfirmBox();

			}

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
				text: "you want to delete Hierarchy Business?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
				    HierarchiesService.deleteHierarchy(false, $scope.selectedDimensionsBusinessId, $scope.selectedHierarchiesBusinessId);
				}
			});
		};
		/**
		 * Open modal (for insert or update). Insert when hierarchyId equals -1, update otherwise.
		 */
		var openModal = function(dimensionId, hierarchyId, modelId) {
			var modalInstance = $modal.open({
				template: '<hierarchy-details id="dimensionId" hierarchy-id="hierarchyId" model-id="modelId" type="type"></hierarchy-details>',
				windowClass: 'hierarchy-modals softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.dimensionId = dimensionId;
						$scope.hierarchyId = hierarchyId;
						$scope.modelId = modelId;
                        $scope.type = false;

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
			HierarchiesService.isOpenDisabled = $scope.selectedHierarchiesBusinessId === -1;
			HierarchiesService.isDeleteDisabled = $scope.selectedHierarchiesBusinessId === -1;
			if ($scope.selectedHierarchiesBusinessId !== -1) {
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
			openDeletingConfirmBox($scope.selectedHierarchiesBusinessId);
		});

		$scope.$on("SubMenuController:refresh", function() {
		    HierarchiesService.getHierarchies(false);
		});


		$scope.$on("HierarchiesService:budgetInstructionDetailsDeleteError", function(event, data) {
			$scope.isError = true;

			Flash.create('danger', data.message, {
				timeout: 3000
			});
		});

		$scope.$on("HierarchiesService:budgetInstructionDetailsDeleteSuccess", function() {
			var msg = "Hierarchies Business " + " is deleted.";
			$scope.selectedHierarchiesBusinessId = -1;
			manageActions();
			Flash.create('success', msg, {
				timeout: 3000
			});
		});
/*		$scope.$on('BudgetInstructionDetails:close', function(event, data) {
			if (data != null) {
				if (data.budgetInstructionId != -1) {
					var msg = "Budget Instruction was changed";
					Flash.create('success', msg, {
						timeout: 3000
					});
				} else if (data.budgetInstructionId == -1) {
					var msgg = "Budget Instruction is created";
					Flash.create('success', msgg, {
						timeout: 3000
					});
				}
			}
		});*/
		$scope.$on('HierarchyBusinessDetails:close', function(event, data) {
			if (data != null) {
				if (data.hierarchyId != -1) {
					var msg = "Hierarchy Business was changed";
					Flash.create('success', msg);
				} else if (data.hierarchyId == -1) {
					var msg = "Hierarchy Business is created";
					Flash.create('success', msg);
				}
			}
		});
	}
})();