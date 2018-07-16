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
        .module('lookupAuctionApp.auction')
        .controller('AuctionController', AuctionController);

    /* @ngInject */
    function AuctionController($scope, $timeout, Flash, AuctionService, CoreCommonsService, ImportService, TaskStatusService) {
        //var spread, dictionaries;
        var url = $BASE_PATH;

        var options, storagedOptions;
        var pagingIncrement = 50;
        var scrollbarWidth = kendo.support.scrollbar();
        var dataBindingFlag = true;

        var gridElement = $("#kendoGrid");

        $scope.logout = logout;
        $scope.closeTab = closeTab;
        $scope.importData = importData;
        $scope.infoAlert = infoAlert;
        $scope.checked = false;
        
        var dataSource = {

            schema: {
                data: "data",
                total: "total",
                model: {
                    fields: {
                        unsoldPassPrice: {
                            type: "number"
                        },
                        unsoldLE: {
                            type: "number"
                        },
                        sold: {
                            type: "number"
                        },
                        unsold: {
                            type: "number"
                        },
                        auctionNo: {
                            type: "number"
                        },
                        lotNo: {
                            type: "number"
                        },
                        sfx: {
                            type: "number"
                        },
                        sellerNo: {
                            type: "number"
                        },
                        contractNo: {
                            type: "number"
                        },
                        contractLineNo: {
                            type: "number"
                        },
                        status: {
                            type: "string"
                        },
                        buyer: {
                            type: "number"
                        },
                        paddleNo: {
                            type: "number"
                        },
                        hammerPrice: {
                            type: "number"
                        },
                        premium: {
                            type: "number"
                        },
                        buyerNett: {
                            type: "number"
                        },
                        commAmt: {
                            type: "number"
                        },
                        insAmt: {
                            type: "number"
                        },
                        bIFee: {
                            type: "number"
                        },
                        wDFee: {
                            type: "number"
                        },
                        illustFee: {
                            type: "number"
                        },
                        chgAmt: {
                            type: "number"
                        },
                        passPrice: {
                            type: "number"
                        },
                        iCAmount: {
                            type: "number"
                        },
                        lotDescription: {
                            type: "string"
                        },
                        bidType: {
                            type: "string"
                        },
                        iCRepUserID: {
                            type: "string"
                        },
                        valuationUserID: {
                            type: "string"
                        },
                        estimateLow: {
                            type: "number"
                        },
                        estimateHigh: {
                            type: "number"
                        },
                        reservePrice: {
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
                        AuctionService.setIsLastPage(false);
                    }
                    AuctionService.getAll(e.data).then(function(data) {
                        e.success(data);
                        saveKendoOptions();
                    });
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
            //height: "95%",
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
            // pageable: true,
            columnMenu: {
                filterable: false,
            },
            columns: [{
                field: "unsoldPassPrice",
                title: "Unsold Pass Price",
                width: 200
            }, {
                field: "unsoldLE",
                title: "Unsold LE",
                width: 200
            }, {
                field: "sold",
                title: "Sold",
                width: 200
            }, {
                field: "unsold",
                title: "Unsold",
                width: 200
            }, {
                field: "auctionNo",
                title: "Auction No",
                width: 200
            }, {
                field: "lotNo",
                title: "Lot No",
                width: 200
            }, {
                field: "sfx",
                title: "Sfx",
                width: 200
            }, {
                field: "sellerNo",
                title: "Seller No",
                width: 200
            }, {
                field: "contractNo",
                title: "Contract No",
                width: 200
            }, {
                field: "contractLineNo",
                title: "Contract Line No",
                width: 200
            }, {
                field: "status",
                title: "Status",
                width: 200,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "buyer",
                title: "Buyer",
                width: 200
            }, {
                field: "paddleNo",
                title: "Paddle No",
                width: 200
            }, {
                field: "hammerPrice",
                title: "Hammer Price",
                width: 200
            }, {
                field: "premium",
                title: "Premium",
                width: 200
            }, {
                field: "buyerNett",
                title: "Buyer Nett",
                width: 200
            }, {
                field: "commAmt",
                title: "Comm Amt",
                width: 200
            }, {
                field: "insAmt",
                title: "Ins Amt",
                width: 200
            }, {
                field: "bIFee",
                title: "BI Fee",
                width: 200
            }, {
                field: "wDFee",
                title: "WD Fee",
                width: 200
            }, {
                field: "illustFee",
                title: "Illust Fee",
                width: 200
            }, {
                field: "chgAmt",
                title: "Chg Amt",
                width: 200
            }, {
                field: "passPrice",
                title: "Pass Price",
                width: 200
            }, {
                field: "iCAmount",
                title: "IC Amount",
                width: 200
            }, {
                field: "lotDescription",
                title: "Lot Description",
                width: 250,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "bidType",
                title: "Bid Type",
                width: 250,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "iCRepUserID",
                title: "IC Rep User ID",
                width: 250,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "valuationUserID",
                title: "Valuation User ID",
                width: 200,
                filterable: {
                    cell: {
                        dataSource: {}
                    }
                }
            }, {
                field: "estimateLow",
                title: "Estimate Low",
                width: 200
            }, {
                field: "estimateHigh",
                title: "Estimate High",
                width: 200
            }, {
                field: "reservePrice",
                title: "Reserve Price",
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
            var storagedOptions = localStorage["auction-lookup-grid"];
            if (storagedOptions) {
                var options = JSON.parse(storagedOptions);
            } else {
                var options = kendoGrid.getOptions();
            }
            options.dataSource = dataSource;
            kendoGrid.setOptions(options);
        }

        function logout() {
            window.location = url + "/logout.do";
        }

        function closeTab() {
            CoreCommonsService.closeTab($timeout);
        }

        function saveKendoOptions() {
            $timeout(function() {
                localStorage["auction-lookup-grid"] = kendo.stringify(kendoGrid.getOptions());
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
                    if (dataDiv.scrollTop >= dataDiv.scrollHeight - dataDiv.offsetHeight - scrollbarWidth && !AuctionService.getIsLastPage()) {
                        dataBindingFlag = false;
                        gridDataSource.pageSize(currentPageSize + pagingIncrement);
                    }
                }
            });



        function isFilterChanged(e) {
            if (e.data.filter) {
                if (arraysNotEqual($scope.currentFilter, e.data.filter.filters)) {
                    $scope.currentFilter = e.data.filter.filters;
                    return true;
                }
            }
        }

        function arraysNotEqual(a1, a2) {
            return !(JSON.stringify(a1) == JSON.stringify(a2));
        };
        
        function importData(){
            $("#importData").attr('class', 'fa fa-refresh fa-spin');
            ImportService.submitImport(2, "a3", "A3");
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