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
		.module('dashboardFormApp.dashboardForm')
		.controller('DashboardSelectFlatFormController', DashboardSelectFlatFormController);

	/* @ngInject */
	function DashboardSelectFlatFormController($scope, $rootScope, CoreCommonsService, $timeout, $http, DashboardSelectFlatFormService) {
		$scope.close = close;
		$scope.loadForm = loadForm;

		$scope.logout = logout;
		$scope.closeTab = closeTab;

        $scope.selectCurrentFinanceCube = selectCurrentFinanceCube;

		$scope.gridDataSource;
		$scope.handleChange = handleChange;
		
		if(localStorage["dashboard-finance-cube-id-selected"]) {
		    $scope.financeCubeId = parseInt(localStorage["dashboard-finance-cube-id-selected"]);
		} else {
		    $scope.financeCubeId = 0;
		}
		$scope.financeCubeModels = {};
        $scope.financeCubeModels.listFinanceCubeModels = [];
        $scope.financeCubeModels = DashboardSelectFlatFormService.getFinanceCubeModels();

		var url = $BASE_PATH;
		var currentSelection;
		var dataBindingFlag = true;
		var storagedOptions;
		
		var dataSource;

        activateKendoGrid();


		function setUpModal() {
			// parameters to resize modal
			var modalDialog = $(".dashboard-select-flat-form").closest(".modal-dialog");
			var additionalElementsToCalcResize = [modalDialog.find("#kendoGrid .k-grid-header"), modalDialog.find(".getElementToCalcResize1"), 3];
			var elementToResize = modalDialog.find("#kendoGrid .k-grid-content");
			$scope.cookieName = "dashboard-select-flat-form";
			// try to resize and move modal based on the cookie
			CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
			$timeout(function() { // timeout is necessary to pass asynchro
				CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
			}, 1000);
		}

		function close() {
			$scope.$broadcast('dashboardSelectFlatForm:close');
		}

		function kendoRead(e) {
			DashboardSelectFlatFormService.setIsLastPage(false);
			DashboardSelectFlatFormService.getAll(e.data, $scope.financeCubeId).then(function(data) {
				e.success(data);
				saveKendoOptions();
			});
		}

        function selectCurrentFinanceCube() {
            localStorage["dashboard-finance-cube-id-selected"] = $scope.financeCubeId;
            
            $scope.gridDataSource.filter(null);
            $scope.gridDataSource.read();
        }

		function handleChange(dataItem) {
			//if (dataItem != null) {
			currentSelection = {
				xml_form_id: dataItem.xml_form_id || -1,
				vis_id: dataItem.vis_id || ""
				//description: dataItem.description,
				//type: dataItem.type,
				//updateTime: dataItem.updateTime,
				// financeCube: {
				// 	financeCubeId: dataItem.financeCube.financeCubeId,
				// 	financeCubeVisId: dataItem.financeCube.financeCubeVisId,
				// 	financeCubeDescription: dataItem.financeCube.financeCubeDescription,
				// }
			};
			//}
		}

		function loadForm() {
			if (currentSelection != null) {
				$rootScope.$broadcast('dashboardSelectFlatForm:formSelected', currentSelection);
			}
			close();
		}

		function activateKendoGrid() {
			$scope.gridOptions = {
				//height: "95%",
				//dataSource: $scope.gridDataSource,
				resizable: true,
				sortable: true,
				reorderable: true,
				filterable: {
					mode: "row",
					extra: false,
					operators: { // redefine the string operators
						string: {
							contains: "Contains",
							startswith: "Starts with",
							endswith: "Ends with",
							eq: "Is equal to",
							gte: "Is after or equal to"
							// gt: "Is after",
							// lte: "Is before or equal to"
							// lt: "Is before"
						}
					}
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
				// change: function(e) {
				// 	currentSelection = e !== null ? e.sender.dataItem(e.sender.select()).isaleno : null;
				// }
			};
			var kendoSchema = {
				data: "data",
				total: "total",
				model: {
					fields: {
						xml_form_id: {
							type: "number"
						},
						vis_id: {
							type: "string"
						},
						description: {
							type: "string"
						},
						updated_time: {
							type: "date"
							//editable: false,
							//disable: true
							//format: "yyyy/MM/dd"
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
					// currentSelection = e !== null ? e.sender.dataItem(e.sender.select()).isaleno : null;
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
			//$scope.observable = new kendo.data.ObservableObject(dataSource);

			$scope.gridColumns = [{
				field: "xml_form_id",
				title: "Flat Form ID",
				width: 200,
				filterable: {
					cell: {
						dataSource: {}
					}
				}
			}, {
				field: "vis_id",
				title: "Flat Form VisID",
				//width: 300,
				filterable: {
					cell: {
						dataSource: {}
					}
				}
			}, {
				field: "description",
				title: "Description",
				//width: 300,
				filterable: {
					cell: {
						dataSource: {}
					}
				}
			}, {
				field: "updated_time",
				title: "Update Time",
				width: 200,
				format: "{0:yyyy-MM-dd}",
				filterable: {
					cell: {
						dataSource: {},
						// template: function(args) {
						// 	// args.element.kendoNumericTextBox({
						// 	//     format: '####-##-##',
						// 	//     decimals: 0
						// 	// });
						// 	args.element.kendoDatePicker({});
						// 	args.element.attr('disabled', 'disabled');
						// }
					}
					// extra: true,
					// operators: { // redefine the string operators
					// 	//date: {
					// 	//eq: "Is equal to",
					// 	gte: "Is after or equal to",
					// 	//gt: "Is after",
					// 	lte: "Is before or equal to"
					// 	//lt: "Is before"
					// 	//}
					// }
				}
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
				// localStorage["dahsboard-auctions-grid"] = kendo.stringify($scope.myKendo.getOptions());
			}, 0);
		}

		$scope.$on("kendoWidgetCreated", function(event, widget) {
			if ($scope.myKendo === widget) {
				// var storagedOptions = localStorage["dahsboard-auctions-grid"];
				// if (storagedOptions) {
				// 	var kendoOptions = $scope.myKendo.getOptions();
				// 	var options = JSON.parse(storagedOptions);
				// 	angular.forEach(options, function(value, key) {
				// 		kendoOptions[key] = value;
				// 	});
				// 	$scope.myKendo.setOptions(kendoOptions);
				// 	$scope.gridDataSource = $scope.myKendo.dataSource;
				// }

				widget.element.find(".k-grid-content").on('dblclick', function(e) {
					loadForm();
				});
				$scope.myKendo.content.on('scroll', function(event, widget) {
					if (dataBindingFlag) {
						var pagingIncrement = 100;
						var scrollbarWidth = kendo.support.scrollbar();
						var dataDiv = event.target;
						var currentPageSize = $scope.gridDataSource.pageSize();
						if (dataDiv.scrollTop >= dataDiv.scrollHeight - dataDiv.offsetHeight - scrollbarWidth && !DashboardSelectFlatFormService.getIsLastPage()) {
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

        $scope.$watchCollection(
            // "This function returns the value being watched. It is called for each turn of the $digest loop"
            function() {
                return $scope.financeCubeModels.listFinanceCubeModels
            },
            // "This is the change listener, called when the value returned from the above function changes"
            function(newValue, oldValue) {
                // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                // To protect against this we check if newValue isn't empty.
                if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                    if(newValue.length > 0) {
                        if($scope.financeCubeId === 0) {
                            $scope.financeCubeId = newValue[0].financeCubeId;
                        }
                        $scope.gridDataSource.read();
                    }
                }
            }
        );
	}
})();