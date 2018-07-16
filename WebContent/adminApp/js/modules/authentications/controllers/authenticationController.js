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
		.module('adminApp.authentication')
		.controller('AuthenticationController', AuthenticationController);

	/* @ngInject */
	function AuthenticationController($rootScope, $scope, $compile, $modal, PageService, FilterService, Flash, AuthenticationService, CoreCommonsService, ContextMenuService) {

		$scope.selectedAuthenticationId = -1;
		$scope.selectedAuthenticationVisId = "";
		$scope.ctx = {};
		$scope.resizedColumn = resizedColumn;
		$scope.sortedColumn = sortedColumn;
		$scope.selectionChangedHandler = selectionChangedHandler;
		$scope.open = open;
		$scope.activateAuthentication = activateAuthentication;
		$scope.deleteAuthentication = deleteAuthentication;
		activate();

		function activate() {
			PageService.setCurrentPageService(AuthenticationService);
			$scope.globalSettings = CoreCommonsService.globalSettings;
			var authentications = $scope.authentications = AuthenticationService.getAuthentications();
			// get some data from backend - for use in Details windows
			AuthenticationService.getAuthenticationTechniques();
			AuthenticationService.getSecurityLogs();
			AuthenticationService.getSecurityAdministrators();
			$scope.ctx = {
				flex: null,
				filter: '',
				data: authentications
			};
			ContextMenuService.initialize($('.grid'));

			// parameters to save cookie for flex grid
			$scope.cookieName = "adminPanel_authentication";
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
		 * If previous selected row id is different from current, change selectedAuthenticationId.
		 * Otherwise reset selectedAuthenticationId ('cause Authentication was deselected).
		 * @param  {Integer} authenticationId
		 * @param  {String} authenticationNarrative 	to show this name of Authentication in confirm and massages
		 */
		function selectionChangedHandler() {
			var flex = $scope.ctx.flex;
			var current = flex.collectionView ? flex.collectionView.currentItem : null;
			if (current != null) {
				$scope.selectedAuthenticationId = current.authenticationId;
				$scope.selectedAuthenticationVisId = current.authenticationVisId;
			} else {
				$scope.selectedAuthenticationId = -1;
				$scope.selectedAuthenticationVisId = "";
			}
			ContextMenuService.updateActions();
			manageActions();
		}

		/**
		 * Call "open modal" for update after click on button on the listing
		 */
		function open() {
			openModal($scope.selectedAuthenticationId);
		}

		/**
		 * Call activation of Authentication
		 */
		function activateAuthentication() {
			AuthenticationService.activateAuthentication($scope.selectedAuthenticationId);
		}

		/**
		 * Call deleting Authentication
		 */
		function deleteAuthentication() {
			if ($scope.selectedAuthenticationId == -1) {
				return;
			}
			swal({
				title: "Are you sure",
				text: "you want to delete Authentication \"" + $scope.selectedAuthenticationVisId + "\"?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
					AuthenticationService.deleteAuthentication($scope.selectedAuthenticationId);
				}
			});
		}

		/**
		 * Enable or disable top buttons due to selected row (enable if any is selected).
		 */
		var manageActions = function() {
			var actions = AuthenticationService.getActions();
			AuthenticationService.isOpenDisabled = $scope.selectedAuthenticationId === -1;
			AuthenticationService.isDeleteDisabled = $scope.selectedAuthenticationId === -1;
			if ($scope.selectedAuthenticationId !== -1) {
				PageService.enableActions(actions);
			} else {
				PageService.disableActions(actions);
			}
			$rootScope.$broadcast('PageService:changeService', {});
		};

		/**
		 * Open modal (for insert or update). Insert when authenticationId equals -1, update otherwise.
		 * @param  {Integer} authenticationId [description]
		 */
		var openModal = function(authenticationId) {
			var modalInstance = $modal.open({
				template: '<details-authentication id="authenticationId"></details-authentication>',
				windowClass: 'authentication-modals softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.authenticationId = authenticationId;
						$scope.$on('AuthenticationDetails:close', function(event, args) {
							$modalInstance.close();
						});
					}
				]
			});
		};

		/******************************************************** FILTERS ********************************************************/
		var filters = ['byMainWordFilter'];
		var filterFunction = FilterService.buildFilterFunction(filters);

		$rootScope.$watch(function() {
			return $rootScope.filter.byWord;
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
						html = '<button type="button" class="btn btn-primary btn-xs" ng-click="activateAuthentication()">Activate</button>' +
							' <button type="button" class="btn btn-warning btn-xs" ng-click="open()">Open</button>' +
							' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteAuthentication()">Delete</button>';
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

		$scope.$on('SubMenuController:open', function() {
			$scope.open();
		});

		$scope.$on('SubMenuController:create', function() {
			openModal(-1);
		});

		$scope.$on('SubMenuController:delete', function() {
			$scope.deleteAuthentication();
		});

		$scope.$on('SubMenuController:activate', function() {
			$scope.activateAuthentication();
		});

		$scope.$on("SubMenuController:refresh", function() {
			AuthenticationService.getAuthentications(true);
		});

		/**
		 * Activate: On error after http->success() with error=true or http->error()
		 * @param  {[type]} event [description]
		 * @param  {Object} data  [Response Message]
		 */
		$scope.$on("AuthenticationService:authenticationDetailsActivateError", function(event, data) {
			$scope.isError = true;
			// error from method http->success() and from field "error"
			Flash.create('danger', data.message);
		});

		$scope.$on("AuthenticationService:authenticationDetailsActivateSuccess", function(event, authenticationNames) {
			var msg = "You need to restart the server for this policy to become active. \"" + authenticationNames.newAuthenticationName + "\" will be active.";
			if (authenticationNames.oldAuthenticationName != null) {
				msg += " \"" + authenticationNames.oldAuthenticationName + "\" will be inactive.";
			}
			Flash.create('success', msg);
		});

		/**
		 * Delete: On error after http->success() with error=true or http->error()
		 * @param  {[type]} event [description]
		 * @param  {Object} data  [Response Message]
		 */
		$scope.$on("AuthenticationService:authenticationDetailsDeleteError", function(event, data) {
			$scope.isError = true;
			// error from method http->success() and from field "error"
			Flash.create('danger', data.message);
		});

		$scope.$on("AuthenticationService:authenticationDetailsDeleteSuccess", function() {
			var msg = "Authentication \"" + $scope.selectedAuthenticationVisId + "\" is deleted.";
			$scope.selectedAuthenticationId = -1;
			$scope.selectedAuthenticationVisId = "";
			manageActions();
			Flash.create('success', msg);
		});

	}
})();