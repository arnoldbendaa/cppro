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
		.module('dashboardAuction')
		.controller('AuctionSelectController', AuctionSelectController);

	/* @ngInject */
	function AuctionSelectController($scope, $rootScope, CoreCommonsService, $timeout, $http, auctionSelectService) {

		$scope.close = close;
		$scope.loadAuction = loadAuction;

		$scope.logout = logout;
		$scope.closeTab = closeTab;

		$scope.selectCurrentModel = selectCurrentModel;

		$scope.from = null;
		$scope.to = null;

		$scope.setFilter = setFilter;

		//$scope.kendoGrid;
		$scope.gridDataSource;
		$scope.handleChange = handleChange;
		$scope.disableSelectAuction = false;

		var currentFilter = null;
		var myFilter = null;

		var url = $BASE_PATH;
		var currentSelection;
		var dataBindingFlag = true;
		//var kendoGrid;
		var storagedOptions;
		var gridElement;

		var dataSource;

		activate();
		activateKendoGrid();

		function activate() {
			if (localStorage["dashboard-auction-daterange"]) {
				myFilter = JSON.parse(localStorage["dashboard-auction-daterange"]);
				for (var f in myFilter) {
					if (myFilter[f].operator === "gte") {
						$scope.from = myFilter[f].value;
					}
					if (myFilter[f].operator === "lte") {
						$scope.to = myFilter[f].value;
					}
				}
			}
		}

		function setUpModal() {
			// parameters to resize modal
			var modalDialog = $(".dashboard-grid").closest(".modal-dialog");
			var additionalElementsToCalcResize = [modalDialog.find(".getElementToCalcResize1"), modalDialog.find("#kendoGrid .k-grid-header"), 3];
			var elementToResize = modalDialog.find("#kendoGrid .k-grid-content");
			$scope.cookieName = "dashboard-auctions-grid";
			// try to resize and move modal based on the cookie
			CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
			$timeout(function() { // timeout is necessary to pass asynchro
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
			}, 1000);

			//getData();
		}

		function close() {
			$scope.$broadcast('dashboardModalDisplay:close');
		}

		function setFilter() {
			myFilter = [];
			if ($scope.from != null) {
				myFilter.push({
					field: "daenddate",
					operator: "gte",
					value: $scope.from
				});
			}
			if ($scope.to != null) {
				myFilter.push({
					field: "daenddate",
					operator: "lte",
					value: $scope.to
				});
			}
			auctionSelectService.setIsLastPage(false);
			$scope.gridDataSource.read();
		}

		function kendoRead(e) {
			var filter = null;
			if (currentFilter == null && e.data.filter == null) {
				if (myFilter != null) {
					filter = myFilter;
				}
			} else if (currentFilter != null && e.data.filter == null) {
				if (myFilter != null) {
					currentFilter = null;
					filter = myFilter;
				} else {
					currentFilter = null;
					filter = null;
				}
			} else if (currentFilter == null && e.data.filter != null) {
				for (var a in e.data.filter.filters) {
					if (e.data.filter.filters[a].field === "daenddate") {
						myFilter = null;
						$scope.from = null;
						$scope.to = null;

						//compy filter, remove daenddate, 
						break;
					}
				}
				filter = e.data.filter.filters;
				if (myFilter != null) {
					angular.forEach(myFilter, function(value, key) {
						filter.push(value);
					});
				}
				currentFilter = e.data.filter.filters;
			} else if (currentFilter != null && e.data.filter != null) {
				filter = angular.copy(e.data.filter.filters);
				if (JSON.stringify(currentFilter) === JSON.stringify(e.data.filter.filters)) {
					if (myFilter != null) {
						for (var a in filter) {
							if (filter[a].field === "daenddate") {
								filter.splice(a, 1);
								//kendoGrid.dataSource.filter(filter);
								$scope.gridDataSource.filter(angular.copy(filter));
								currentFilter = angular.copy(filter);
								break;
							}
						}
						angular.forEach(myFilter, function(value, key) {
							filter.push(value);
						});
					}
				} else {
					currentFilter = e.data.filter.filters;
					for (var a in e.data.filter.filters) {
						if (e.data.filter.filters[a].field === "daenddate") {
							myFilter = null;
							$scope.from = null;
							$scope.to = null;
							break;
						}
					}
					if (myFilter != null) {
						angular.forEach(myFilter, function(value, key) {
							filter.push(value);
						});
					}
				}
			}
			localStorage["dashboard-auction-daterange"] = JSON.stringify(myFilter);
			auctionSelectService.setIsLastPage(false);
			auctionSelectService.getAll(e.data, filter, $scope.dashboardModel.model).then(function(data) {
				e.success(data);
				saveKendoOptions();
			});
		}

		function selectCurrentModel(currentModel) {
			$scope.dashboardModel.model = currentModel.model;
			localStorage["dashboard-model-selected"] = currentModel.model;

			$scope.gridDataSource.filter(null);
			$scope.from = null;
			$scope.to = null;
			myFilter = null;
			currentFilter = null;

			$scope.gridDataSource.read();

		}

		function handleChange(dataItem) {
			if (dataItem != null) {
				currentSelection = dataItem.isaleno;
				$scope.disableSelectAuction = dataItem.ssalestatus != "Cancel" ? false : true;
			}
		}

		function loadAuction() {
			if ($scope.disableSelectAuction === false) {
				$rootScope.$broadcast('dashboardController:showAuction', currentSelection);
				close();
			}
		}

		function activateKendoGrid() {
			$scope.gridOptions = {
				resizable: true,
				sortable: true,
				reorderable: true,
				filterable: {
					mode: "row",
					extra: false
				},
				scrollable: true,
				selectable: "row",
				columnReorder: function(e) {
					saveKendoOptions();
				},
				columnResize: function(e) {
					saveKendoOptions();
				},
				columnHide: function(e) {
					saveKendoOptions();
				},
				columnShow: function(e) {
					saveKendoOptions();
				},
				dataBound: function() {
					dataBindingFlag = true;
				}
			};
			var kendoSchema = {
				data: "data",
				total: "total",
				model: {
					fields: {
						isaleno: {
							type: "number"
						},
						ssalename: {
							type: "string"
						},
						daenddate: {
							type: "date",
						},
						ssalestatus: {
							type: "string"
						}
					}
				}
			};
			var kendoTransport = {
				create: function(e) {
					console.log("create: ", e);
				},
				destroy: function(e) {
					console.log("destroy: ", e);
				},
				parameterMap: function(options, type) {
					console.log("parameterMap: ", options);
				},
				push: function(e) {
					console.log("push: ", e);
				},
				read: kendoRead,
				update: function(e) {
					console.log("update: ", e);
				},
				change: function(e) {
					currentSelection = e !== null ? e.sender.dataItem(e.sender.select()).isaleno : null;
				},
				dataBound: function() {
					dataBindingFlag = true;
				}
			};
			dataSource = {
				schema: kendoSchema,
				transport: kendoTransport,
				scrollable: true,
				serverPaging: true,
				serverFiltering: true,
				serverSorting: true,
				pageSize: 100
			};
			$scope.gridDataSource = new kendo.data.DataSource(dataSource);

			$scope.gridColumns = [{
				field: "isaleno",
				title: "Auction No",
				width: 200,
				filterable: {
					cell: {
						template: function(args) {
							args.element.kendoNumericTextBox({
								format: '#',
								decimals: 0
							});
						}
					}
				}
			}, {
				field: "ssalename",
				title: "Description",
				filterable: {
					cell: {
						dataSource: {}
					}
				}
			}, {
				field: "daenddate",
				title: "End date",
				width: 200,
				format: "{0:yyyy-MM-dd}",
				filterable: {
					cell: {
						dataSource: {},
					}
				}
			}, {
				field: "ssalestatus",
				title: "Status",
				width: 100,
				filterable: false
			}];
		}

		function logout() {
			window.location = url + "/logout.do";
		}

		function closeTab() {
			CoreCommonsService.closeTab($timeout);
		}

		function saveKendoOptions() {
			$timeout(function() {
				var options = kendo.stringify($scope.myKendo.getOptions());
				localStorage["dashboard-auctions-grid"] = kendo.stringify($scope.myKendo.getOptions());
			}, 0);
		}

		$scope.$on("kendoWidgetCreated", function(event, widget) {
			if ($scope.myKendo === widget) {
				var storagedOptions = localStorage["dashboard-auctions-grid"];
				if (storagedOptions) {
					var kendoOptions = $scope.myKendo.getOptions();
					var options = JSON.parse(storagedOptions);
					angular.forEach(options, function(value, key) {
						kendoOptions[key] = value;
					});
					$scope.myKendo.setOptions(kendoOptions);
					$scope.gridDataSource = $scope.myKendo.dataSource;
				}

				widget.element.find(".k-grid-content").on('dblclick', function(e) {
					loadAuction();
				});
				$scope.myKendo.content.on('scroll', function(event, widget) {
					if (dataBindingFlag) {
						var pagingIncrement = 50;
						var scrollbarWidth = kendo.support.scrollbar();
						var dataDiv = event.target;
						var currentPageSize = $scope.gridDataSource.pageSize();
						if (dataDiv.scrollTop >= dataDiv.scrollHeight - dataDiv.offsetHeight - scrollbarWidth && !auctionSelectService.getIsLastPage()) {
							dataBindingFlag = false;
							$scope.gridDataSource.pageSize(currentPageSize + pagingIncrement);
						}
					}

				});
				widget.element.find(".k-picker-wrap .k-input").kendoDatePicker({
					format: "yyyy-MM-dd"
				});
				setUpModal();
			}


		});
		$scope.$on("kendoRendered", function(e) {
			console.log("All Kendo UI Widgets are rendered.");
		});
		/*------------ dashboard auction: select auction: date picker ------*/
		$scope.openDatePicker = function(calendar) {
			for (var option in $scope.datePickerOptions.opened) {
				if (calendar !== option) {
					$scope.datePickerOptions.opened[option] = false;
				} else {
					$scope.datePickerOptions.opened[option] = true;
				}
			}
		};
		$scope.datePickerOptions = {
			opened: {
				from: false,
				to: false
			}
		};
	}
})();