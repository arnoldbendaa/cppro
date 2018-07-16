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
		.module('coreApp.components')
		.directive('selectUsers', selectUsers);

	/* @ngInject */
	function selectUsers($rootScope, $modal, $timeout, CoreCommonsService, $http) {
		return {
			restrict: 'E',
			templateUrl: $BASE_PATH + 'coreApp/js/modules/components/views/selectUsers.html',
			scope: {
				users: '=',
				ok: '&',
				close: '&'
			},
			replace: true,
			link: linkFunction
		};

		function linkFunction($scope) {
			// parameters to resize modal
			var modalDialog = $(".select-users").closest(".modal-dialog");
			var elementToResize = $("#select-users-grid");
			var additionalElementsToCalcResize = []; //[$(".getToCalc1")];
			$scope.cookieName = "adminPanel_modal_selectUsers";
			// try to resize and move modal based on the cookie
			CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
			$scope.resizedColumn = function(sender, args) {
				CoreCommonsService.resizedColumn(args, $scope.cookieName);
			};
			$scope.sortedColumn = function(sender, args) {
				CoreCommonsService.sortedColumn(args, $scope.cookieName);
			};
			$timeout(function() { // timeout is necessary to pass asynchro
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
			}, 0);

			var reportElementsWijmoCollection = new wijmo.collections.CollectionView();

			// add field to every User
			angular.forEach($scope.users, function(user) {
				user.checked = false;
			});

			reportElementsWijmoCollection.sourceCollection = $scope.users;

			$scope.ctx = {
				filter: '',
				data: reportElementsWijmoCollection
			};

			$scope.mainCheckboxSelected = false;

			$scope.changeSelection = changeSelection;
			$scope.refreshData = refreshData;
			$scope.ok = ok;
			$scope.close = close;
			$scope.resizedColumn = resizedColumn;
			$scope.sortedColumn = sortedColumn;

			
			$scope.$watch('ctx.flex', function() {
				var flex = $scope.ctx.flex;
				if (flex) {
					flex.selectionMode = "Row";
					flex.headersVisibility = "Column";
					flex.itemFormatter = itemFormatter;
				}
			});
			/************************************************** IMPLEMENTATION *************************************************************************/



			function resizedColumn(sender, args) {
				CoreCommonsService.resizedColumn(args, $scope.cookieName);
			}

			function sortedColumn(sender, args) {
				CoreCommonsService.sortedColumn(args, $scope.cookieName);
			}

			// formatter to add checkboxes to boolean columns http://wijmo.com/topic/adding-a-new-checkbox-in-header-column-name-of-the-flex-grid/
			function itemFormatter(panel, r, c, cell) {
				var flex = panel.grid;
				if (panel.cellType == wijmo.grid.CellType.ColumnHeader) {
					var col = flex.columns[c];

					if (col.name == "checked") {
						// prevent sorting on click
						col.allowSorting = false;

						// create and initialize checkbox
						cell.innerHTML = '<input type="checkbox">';
						var cb = cell.firstChild;
						cb.checked = $scope.mainCheckboxSelected;

						// apply checkbox value to cells
						cb.addEventListener('click', function(e) {
							flex.beginUpdate();
							$scope.mainCheckboxSelected = !$scope.mainCheckboxSelected;
							var whatToDo = ($scope.mainCheckboxSelected) ? "all" : "none";
							$scope.changeSelection(whatToDo);
							flex.endUpdate();
						});
					}
				}
			}

			function changeSelection(whatToDo) {
				angular.forEach(reportElementsWijmoCollection.sourceCollection, function(user) {
					switch (whatToDo) {
						case "all":
							user.checked = true;
							break;
						case "none":
							user.checked = false;
							break;
						case "invert":
							user.checked = !user.checked;
							break;
					}
				});
				$scope.refreshData();
			}

			function refreshData() {
				$scope.ctx.data.refresh();
			}

			function ok() {
				var selectedUsers = [];
				angular.forEach($scope.users, function(user) {
					if (user.checked === true) {
						delete user.checked;
						selectedUsers.push(user);
					} else {
						delete user.checked;
					}
				});
				$rootScope.$broadcast("SelectUsers:ok", selectedUsers);
			}

			function close() {
				$rootScope.$broadcast("SelectUsers:close");
			}

		}
	}

})();