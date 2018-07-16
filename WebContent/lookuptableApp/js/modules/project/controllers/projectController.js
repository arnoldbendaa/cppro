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
    'use_strict';

    angular
        .module('lookupProjectApp.project')
        .controller('ProjectController', ProjectController);

    /* @ngInject */
    function ProjectController($scope, $timeout, Flash, ImportService, TaskStatusService, ProjectService, CoreCommonsService) {
        //var spread, dictionaries;
        var url = $BASE_PATH;
        var currentSort = {};
        var currentFilter = {};
        $scope.inputMapping;
        $scope.checked = false;
        var pagingIncrement = 50;
        var scrollbarWidth = kendo.support.scrollbar();
        var dataBindingFlag = true;

        var gridElement = $("#kendoGrid");

        $scope.searchByInputMapping = searchByInputMapping;
        $scope.importData = importData;
        $scope.infoAlert = infoAlert;
        $scope.logout = logout;
        $scope.closeTab = closeTab;
        

        var dataSource = {
            schema: {
                data: "data",
                total: "total",
                model: {
                    fields: {
                        company: {
                            type: "number"
                        },
                        costcentre: {
                            type: "string"
                        },
                        project: {
                            type: "string"
                        },
                        expensecode: {
                            type: "string"
                        },
                        yearno: {
                            type: "number"
                        },
                        period: {
                            type: "number"
                        },
                        baseVal: {
                            type: "number"
                        },
                        qty: {
                            type: "number"
                        },
                        cumBaseVal: {
                            type: "number"
                        }
                    }
                }
            },
            transport: {
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
                read: function(e) {
                    console.log("read: ", e);
                    if (isFilterChanged(e)) {
                        ProjectService.setIsLastPage(false);
                    }
                    ProjectService.getAll(e.data).then(function(data) {
                        e.success(data);
                        saveKendoOptions();
                    })

                },
                update: function(e) {
                    console.log("update: ", e);
                }
            },
            serverPaging: true,
            serverFiltering: true,
            serverSorting: true,
            pageSize: 100
        }

        $("#kendoGrid").kendoGrid({
            //height: "81%",
            resizable: true,
            sortable: true,
            reorderable: true,
            columnReorder: function(e) {
                saveKendoOptions()
            },
            columnResize: function(e) {
                saveKendoOptions()
            },
            columnHide: function(e) {
                saveKendoOptions()
            },
            columnShow: function(e) {
                saveKendoOptions()
            },
            filterable: {
                mode: "row",
                extra: false
            },
            scrollable: true,
            dataBound: function() {
                dataBindingFlag = true;
            },
            columnMenu: {
                filterable: false,
            },
            columns: [{
                field: "company",
                title: "Company",
                filterable: {
                    cell: {
                        template: function(args) {
                            args.element.kendoNumericTextBox({
                                format: '#',
                                decimals: 0
                            });
                        }
                    }
                },
                width: 200
            }, {
                field: "costcentre",
                title: "Cost Centre",
                width: 200,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "project",
                title: "Project",
                width: 200,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "expensecode",
                title: "Expense Code",
                width: 200,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "yearno",
                title: "Year",
                filterable: {
                    cell: {
                        template: function(args) {
                            args.element.kendoNumericTextBox({
                                format: '#',
                                decimals: 0,
                                max: 9999
                            });
                        }
                    }
                },
                width: 200
            }, {
                field: "period",
                title: "Period",
                filterable: {
                    cell: {
                        template: function(args) {
                            args.element.kendoNumericTextBox({
                                format: '#',
                                decimals: 0,
                                max: 99
                            });
                        }
                    }
                },
                width: 200
            }, {
                field: "baseVal",
                title: "Base Val",
                width: 200
            }, {
                field: "qty",
                title: "QTY",
                width: 150
            }, {
                field: "cumBaseVal",
                title: "Cum Base Val",
                width: 200
            }, {
                field: "cumQty",
                title: "Cum QTY",
                width: 200
            }],
        });

        var kendoGrid = $("#kendoGrid").data("kendoGrid");

        activate();
        /************************************************** IMPLEMENTATION *************************************************************************/
        /**
         * Activate Kendo UI options. If is first run on browser set default. If second or later load last time set. Set dataSource in options.
         */
        function activate() {
            var storagedOptions = localStorage["project-lookup-grid"];
            if (storagedOptions) {
                var options = JSON.parse(storagedOptions);
            } else {
            var options = kendoGrid.getOptions();
            }
            options.dataSource = dataSource;
            kendoGrid.setOptions(options);
            gridResize();
        }

        function searchByInputMapping() {
            $filter = new Array();
            if ($scope.inputMapping !== undefined && $scope.inputMapping !== null && $scope.inputMapping.length > 0) {
                var im = $scope.inputMapping.replace(/.*\(|\)/gi, '');
                var tableIM = im.split(",");
                for (var i = 0; i < tableIM.length; i++) {
                    var tableDIM = tableIM[i].split("=");
                    if (tableDIM.length === 2) {
                        if (tableDIM[0] == "dim0") {
                            $filter.push({
                                field: "costcentre",
                                operator: "eq",
                                value: tableDIM[1].replace(/['"]+/g, '')
                            });
                        } else if (tableDIM[0] == "dim1") {
                            $filter.push({
                                field: "expensecode",
                                operator: "eq",
                                value: tableDIM[1].replace(/['"]+/g, '')
                            });
                        } else if (tableDIM[0] == "dim2") {
                            var date = dim2ToDate(tableDIM[1].replace(/['"]+/g, ''));
                            if (date) {
                                if (date[0]) {
                                    $filter.push({
                                        field: "yearno",
                                        operator: "eq",
                                        value: date[0]
                                    });
                                }
                                if (date[1]) {
                                    $filter.push({
                                        field: "period",
                                        operator: "eq",
                                        value: date[1]
                                    });
                                }

                            }

                        } else if (tableDIM[0] == "dim3") {
                            $filter.push({
                                field: "project",
                                operator: "eq",
                                value: tableDIM[1].replace(/['"]+/g, '')
                            });
                        }
                    }
                }
            }
            kendoGrid.dataSource.filter($filter)
        }

        function dim2ToDate(dim2) {
            var date = []; // date[0] - year, dane[1] - month
            var tmp;
            tmp = dim2.replace(/^\//, '');
            date = tmp.split("/");
            return date;
        }

        function logout() {
            window.location = url + "/logout.do";
        }

        function closeTab() {
            CoreCommonsService.closeTab($timeout);
        }

        function saveKendoOptions() {
            $timeout(function() {
                localStorage["project-lookup-grid"] = kendo.stringify(kendoGrid.getOptions());
            }, 0);
        }

        $(window).resize(gridResize());

        function gridResize() {
            // var gridElement = $("#kendoGrid"),
            newHeight = gridElement.innerHeight(),
            otherElements = gridElement.children().not(".k-grid-content"),
            otherElementsHeight = 0;

            otherElements.each(function() {
                otherElementsHeight += $(this).outerHeight(true);
            });

            gridElement.children(".k-grid-content").height(newHeight - otherElementsHeight);
        };


        var gridDataSource = gridElement.data("kendoGrid").dataSource;
        gridElement.children(".k-grid-content")
            .on("scroll", function(e) {
                if (dataBindingFlag) {
                    var dataDiv = e.target;
                    var currentPageSize = gridDataSource.pageSize();
                    if (dataDiv.scrollTop >= dataDiv.scrollHeight - dataDiv.offsetHeight - scrollbarWidth && !ProjectService.getIsLastPage()) {
                        dataBindingFlag = false;
                        gridDataSource.pageSize(currentPageSize + pagingIncrement);
                    }
                }
            });



        function isFilterChanged(e) {
            if (e.data.filter) {
                if (arraysNotEqual(currentFilter, e.data.filter.filters)) {
                    currentFilter = e.data.filter.filters;
                    return true;
                }
            }
        };

        function arraysNotEqual(a1, a2) {
            return !(JSON.stringify(a1) == JSON.stringify(a2));
        };
        
        function importData(){
            $("#importData").attr('class', 'fa fa-refresh fa-spin');
            ImportService.submitImport(1, "OA_PCTRANS", "OpenAccounts");
            $scope.checked = true;
        }
        
        function infoAlert(message) {
            Flash.create('success', message, 'custom-class');
        }
        
        function failAlert(message) {
            Flash.create('danger', message, 'custom-class');
        }
        
        $scope.$on('ImportService:runCheckTaskStatus', function(event, args) {
            TaskStatusService.checkTaskStatus(args);
        });
        
        $scope.$on('TaskStatusService:statusIsChanged', function(event, args) {
            if (args=="Failed"){
                failAlert("Task status is changed to - " + args);
            } else{
                infoAlert("Task status is changed to - " + args);
            }
            if (args==="Complete" || args==="Failed" || args==="Complete (exceptions)"){
                $scope.checked = false; //enable import button
            }
        });

    }
})();