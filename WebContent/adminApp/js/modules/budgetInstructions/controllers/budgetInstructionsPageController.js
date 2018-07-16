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
		.module('adminApp.budgetInstructionsPage')
		.controller('BudgetInstructionsPageController', BudgetInstructionsPageController);

	/* @ngInject */
	function BudgetInstructionsPageController($rootScope, $scope, $compile, $modal, PageService, Flash, FilterService, BudgetInstructionsPageService, CoreCommonsService, ContextMenuService) {

		var budgetInstructions = {};
		$scope.budgetInstructionId = -1;
		$scope.selectedBudgetInstructionId = -1;
		$scope.ctx = {};
		// parameters to save cookie for flex grid
		$scope.cookieName = "adminPanel_budgetInstructions";
		$scope.resizedColumn = resizedColumn;
		$scope.sortedColumn = sortedColumn;
		$scope.selectionChangedHandler = selectionChangedHandler;
		$scope.onFileSelect = onFileSelect;
		$scope.open = open;
		$scope.deleteBudget = deleteBudget;
		activate();

		function activate() {
			PageService.setCurrentPageService(BudgetInstructionsPageService);
			$scope.globalSettings = CoreCommonsService.globalSettings;
			budgetInstructions = BudgetInstructionsPageService.getBudgetInstructions();
			$scope.ctx = {
		            flex: null,
		            filter: '',
		            data: budgetInstructions
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
				$scope.selectedBudgetInstructionId = current.budgetInstructionId;
			} else {
				$scope.selectedBudgetInstructionId = -1;

			}
			manageActions();
		}

		function onFileSelect($files) {
			$scope.file = $files;

		}
		/**
		 * Call "open modal" for update after click on button on the listing
		 * @param  {Integer} budgetInstructionsId [description]
		 */

		function open(budgetInstructionId) {
			openModal($scope.selectedBudgetInstructionId);

		}
		/**
		 * Call deleting budgetInstruction
		 * @param  {Integer} budgetInstructionId
		 */

		function deleteBudget(budgetInstructionId) {

			if ($scope.selectedBudgetInstructionId == -1) {
				return;
			} else {
				openDeletingConfirmBox();

			}

		}

		/******************************************************** FILTERS ********************************************************/
		var filters = ['byMainWordFilter'];
		var filterFunction = FilterService.buildFilterFunction(filters);

		$rootScope.$watch(function() {
			return $rootScope.filter.byWord
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
							'<button type="button" class="btn btn-danger btn-xs" ng-click="deleteBudget();">Delete</button>';
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
				flex.headersVisibility = "Column"
				flex.itemFormatter = itemFormatter;
				CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
			}
		});

		var openDeletingConfirmBox = function() {
			swal({
				title: "Are you sure",
				text: "you want to delete budget instruction?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
					BudgetInstructionsPageService.deleteBudget($scope.selectedBudgetInstructionId);
				}
			});
		};

		var openModal = function(budgetInstructionId) {
			var modalInstance = $modal.open({
				template: '<budget-instruction-details id="budgetInstructionId" close="close()"></budget-instruction-details>',
				windowClass: 'budget-instruction-modals softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.budgetInstructionId = budgetInstructionId;
						$scope.onFileSelect = function($files) {
							$scope.file = $files;
						};
						$scope.$on('BudgetInstructionDetails:close', function(event, args) {
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
			var actions = BudgetInstructionsPageService.getActions();
			BudgetInstructionsPageService.isOpenDisabled = $scope.selectedBudgetInstructionId === -1;
			BudgetInstructionsPageService.isDeleteDisabled = $scope.selectedBudgetInstructionId === -1;
			if ($scope.selectedBudgetInstructionId !== -1) {
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
			openModal(-1);
		});

		$scope.$on('SubMenuController:deleteBudget', function() {
			openDeletingConfirmBox($scope.selectedBudgetInstructionId);
		});

		$scope.$on("SubMenuController:refresh", function() {
			BudgetInstructionsPageService.getBudgetInstructions();
		});

		$scope.$on("BudgetInstructionsPageService:budgetInstructionDetailsDeleteError", function(event, data) {
			$scope.isError = true;

			Flash.create('danger', data.message);
		});

		$scope.$on("BudgetInstructionsPageService:budgetInstructionDetailsDeleteSuccess", function() {
			var msg = "Budget Instruction " + " is deleted.";
			$scope.selectedBudgetInstructionId = -1;
			manageActions();
			Flash.create('success', msg);
		});
		$scope.$on('BudgetInstructionDetails:close', function(event, data) {
			if (data != null) {
				if (data.budgetInstructionId != -1) {
					var msg = "Budget Instruction was changed";
					Flash.create('success', msg);
				} else if (data.budgetInstructionId == -1) {
					var msgg = "Budget Instruction is created";
					Flash.create('success', msgg);
				}
			}
		});

		// $scope.$on("SubMenuController:doFilter", function(event, filter) {
		// 	$scope.globalFilter = filter;
		// });
	}
})();