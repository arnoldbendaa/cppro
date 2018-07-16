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
        .module('adminApp.budgetCycle')
        .controller('ChangeManagementController', ChangeManagementController);

    /* @ngInject */
    function ChangeManagementController($rootScope, $scope, $compile, $filter, PageService, FilterService, Flash, ChangeManagementService, CoreCommonsService, ContextMenuService) {

		var changeMgmts = {};
		$scope.selectedChangeMgmtId = -1;
		$scope.selectedChangeMgmtCreatedTime = null;
		$scope.ctx = {};
		// parameters to save cookie for flex grid
		$scope.cookieName = "adminPanel_changeManagement";
		$scope.resizedColumn = resizedColumn;
		$scope.sortedColumn = sortedColumn;
		$scope.selectionChangedHandler = selectionChangedHandler;
		$scope.submit = submit;
		activate();
		
		function activate(){
		    PageService.setCurrentPageService(ChangeManagementService);
		    changeMgmts = $scope.changeMgmts = ChangeManagementService.getChangeMgmts();
	        $scope.globalSettings = CoreCommonsService.globalSettings;
	        $scope.ctx = {
	                flex: null,
	                filter: '',
	                data: changeMgmts
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
		 * If previous selected row id is different from current, change selectedChangeMgmtId.
		 * Otherwise reset selectedChangeMgmtId (because changeMgmt was deselected).
		 */
		function selectionChangedHandler() {
			var flex = $scope.ctx.flex;
			var current = flex.collectionView ? flex.collectionView.currentItem : null;
			if (current != null) {
				$scope.selectedChangeMgmtId = current.changeMgmtId;
				$scope.selectedChangeMgmtCreatedTime = formatDate(current.created);
			} else {
				$scope.selectedChangeMgmtId = -1;
				$scope.selectedChangeMgmtCreatedTime = null;
			}
			manageActions();
		}

		/**
		 * Call submiting ChangeMgmt
		 */
		function submit() {
			if ($scope.selectedChangeMgmtId == -1) {
				return;
			}
			ChangeManagementService.submit($scope.selectedChangeMgmtId);
		}

		var formatDate = function(timestamp) {
			return $filter('date')(timestamp, 'MMM dd. yyyy hh:mm:ss a');
		};

		/**
		 * Enable or disable top buttons due to selected row (enable if any is selected).
		 */
		var manageActions = function() {
			var actions = ChangeManagementService.getActions();
			// ChangeManagementService.isOpenDisabled = $scope.selectedChangeMgmtId === -1;
			if ($scope.selectedChangeMgmtId !== -1) {
			    PageService.enableActions(actions);
			} else {
			    PageService.disableActions(actions);
			}
			ContextMenuService.updateActions();
			$rootScope.$broadcast('PageService:changeService', {});
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
					html = cell.innerHTML,
					taskId = panel.rows[r].dataItem['taskId'];
				switch (col.name) {
					case 'index':
						html = "" + (r + 1);
						break;
					case 'created':
						html = formatDate(panel.rows[r].dataItem['created']);
						break;
					case 'taskId':
						if (taskId == 0) {
							html = "";
						} else {
							html = "" + taskId;
						}
						break;
					case 'buttons':
						var disabled = null;
						if (taskId > 0) {
							disabled = "disabled";
						}
						html = '<button type="button" class="btn btn-primary btn-xs ' + disabled + '" ng-click="submit()">Submit</button>';
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

		$scope.$on('SubMenuController:submit', function() {
			$scope.submit();
		});

		$scope.$on("SubMenuController:refresh", function() {
		    ChangeManagementService.getChangeMgmts();
		});

		/**
		 * Submit: On error after http->success() with error=true or http->error()
		 * @param  {[type]} event [description]
		 * @param  {Object} data  [Response Message]
		 */
		$scope.$on("ChangeManagementService:ChangeManagementSubmitError", function(event, data) {
			$scope.isError = true;
			// error from method http->success() and from field "error"
			Flash.create('danger', data.message);
		});

		$scope.$on("ChangeManagementService:ChangeManagementSubmitSuccess", function() {
			var msg = "Change Management (created " + $scope.selectedChangeMgmtCreatedTime + ") was submited.";
			$scope.selectedChangeMgmtId = -1;
			$scope.selectedChangeMgmtCreatedTime = null;
			manageActions();
			Flash.create('success', msg);
		});

	}
})();