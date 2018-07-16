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
    .controller('DashboardController', DashboardController);

  /* @ngInject */
  function DashboardController($rootScope, $modal, $scope, DashboardService, $timeout, Flash, ImportService, TaskStatusService, CoreCommonsService, $http) {
    var url = $BASE_PATH;
    var valueOfLotsSold;
    var saleResult;
    var numberOfLotsSold;
    var bidType;
    var incomeVsNumberOfLots;
    var vendorsCommission;
    var chartData;
    var seriesDefault = {
      type: "column",
      labels: {
        visible: true,
        background: "transparent"
      }
    };
    $scope.categoriesScopesMoney = [];
    $scope.tableHidden = true;
    $scope.logout = logout;
    $scope.closeTab = closeTab;
    $scope.auctionNumber = undefined;
    $scope.checked = false;
    $scope.importData = importData;
    $scope.infoAlert = infoAlert;
    $scope.getData = getData;
    $scope.exportToExcel = exportToExcel;
    $scope.generateScopesMoney = generateScopesMoney;
    $scope.drawChart = drawChart;
    $scope.tabSelected = tabSelected;
    $scope.saleResultTotalUpdateAndChartRefresh = saleResultTotalUpdateAndChartRefresh;
    $scope.createChartValueOfLotsSold = createChartValueOfLotsSold;
    $scope.createChartSaleResult = createChartSaleResult;
    $scope.createChartNumberOfLotsSold = createChartNumberOfLotsSold;
    $scope.createChartBidType = createChartBidType;
    $scope.createChartIncomeVsNumberOfLots = createChartIncomeVsNumberOfLots;
    $scope.createChartVendorsCommission = createChartVendorsCommission;
    $scope.budget = {
      name: "Budget",
      data: [0, 0, 0, 0, 0]
    };
    $scope.budgetTotal = 0;

    $scope.dashboardName = "Enter name here";

    $scope.dashboardModel = {
      model: undefined,
      models: []
    };

    $scope.ch = function(type) {
      console.log("type", type);
    };

    function logout() {
      window.location = url + "/logout.do";
    }

    function closeTab() {
      CoreCommonsService.closeTab($timeout);
    }

    $(window).resize(function() {
      var chosenChart = $("#" + selecteTab).data("kendoChart");
      if (chosenChart !== undefined && chosenChart !== null) {
        chosenChart.refresh();
      }
    });


    //my garbage
    $scope.models = [];
    var temp;
    $scope.currentModel = {
      model: temp
    };

    $scope.showModal = showModal;

    $scope.mainLabel = "Dashboard";



    init();

    function init() {
      $scope.auctionNumber = $AUCTION_NO;
      loadModelsForUser();
    }


    function showModal(dashboardModel) {
      //var temp = $scope.currentModel;
      //var temp2 = $scope.models;
      var modalInstance = $modal.open({
        backdrop: 'static',
        windowClass: 'softpro-modals',
        size: 'lg',
        template: '<dashboard-modal-display></dashboard-modal-display>',
        controller: ['$scope', '$modalInstance',
          function($scope, $modalInstance) {
            $scope.dashboardModel = dashboardModel;
            //$scope.currentModel = temp;
            // $scope.models = temp2;

            function close() {
              $modalInstance.dismiss('cancel');
              $scope.apply();
            }

            $scope.$on('dashboardModalDisplay:close', function(event, args) {
              $modalInstance.close();
            });
          }
        ]
      });
    }

    $scope.selectCurrentModel = selectCurrentModel;

    function selectCurrentModel(currentModel) {
      $scope.dashboardModel.model = currentModel.model;
      //$scope.currentModel.model = currentModel.model;
      localStorage["dashboard-model-selected"] = currentModel.model;
    }

    function loadModelsForUser() {
      var url = $BASE_PATH + 'dashboard/auction/models';
      return $http.get(url)
        .then(function(response) {
          angular.copy(response.data, $scope.dashboardModel.models);
          if ($scope.dashboardModel.models) {
            $scope.dashboardModel.model = localStorage["dashboard-model-selected"] || $scope.dashboardModel.models[0];
          } else {
            $scope.dashboardModel.model = localStorage["dashboard-model-selected"] || "";
          }
          if ($scope.auctionNumber == undefined || $scope.auctionNumber == "") {
            showModal($scope.dashboardModel)
          } else {
            getData($scope.auctionNumber);
          }
        });
    }

    $rootScope.$on('dashboardController:showAuction', function(event, args) {
      getData(args);
    });


    function getData(auctionNumber) {
      if (auctionNumber !== null && auctionNumber !== undefined && auctionNumber !== "") {
        DashboardService.getAll(auctionNumber)
          .then(function(response) {
            chartData = response;
            var rawCategories = Object.keys(response.data.valueOfLotsSold.sold);
            generateScopesMoney(rawCategories);
            drawChart();
            $scope.tableHidden = false;
            $scope.mainLabel = "Auction No. " + auctionNumber;

            $scope.auctionNumber = auctionNumber;
          });
      }
    }
    //end of my garbage
    //
    function importData() {
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

    function exportToExcel() {
      if ($scope.auctionNumber !== null && $scope.auctionNumber !== undefined && $scope.auctionNumber !== "") {
        var url = $BASE_PATH + 'dashboard/auction/excel/' + $scope.auctionNumber + '?budgetHammer=' + $scope.budget.data[0] + '&budgetBuyersPremium=' + $scope.budget.data[1] + '&budgetCommision=' + $scope.budget.data[2] + '&budgetInsurance=' + $scope.budget.data[3];

        // set animated icon
        $("#excelExport").attr('class', 'fa fa-refresh fa-spin');

        // start downloading the excel file
        $.fileDownload(url)
          .done(function() {
            // restore icon
            $("#excelExport").attr('class', 'fa fa-upload');
          })
          .fail(function() {
            alert('File download failed!');
          });
      }
    }

    function generateScopesMoney(categories) {
      if ($scope.categoriesScopesMoney.length === 0) {
        var categoryElement = "&pound;" + categories[0] + "-" + (categories[1] / 1000) + "k";
        $scope.categoriesScopesMoney.push(categoryElement);
        for (var i = 1; i < categories.length - 1; i++) {
          categoryElement = "&pound;" + (categories[i] / 1000) + "-" + (categories[i + 1] / 1000) + "k";
          $scope.categoriesScopesMoney.push(categoryElement);
        }
        categoryElement = "Over &pound;" + (categories[categories.length - 1] / 1000) + "k";
        $scope.categoriesScopesMoney.push(categoryElement);
      }
    }

    var selecteTab = "saleResult";

    function drawChart() {
      if (chartData !== null && chartData !== undefined) {
        if (selecteTab === "saleResult") {
          saleResult = chartData.data.saleResult;
          $(document).ready(createChartSaleResult(saleResult));
          $(document).bind("kendo:skinChange", createChartSaleResult);
        } else if (selecteTab === "valueOfLotsSold") {
          valueOfLotsSold = chartData.data.valueOfLotsSold;
          $(document).ready(createChartValueOfLotsSold(valueOfLotsSold));
          $(document).bind("kendo:skinChange", createChartValueOfLotsSold);
        } else if (selecteTab === "numberOfLotsSold") {
          numberOfLotsSold = chartData.data.numberOfLotsSold;
          $(document).ready(createChartNumberOfLotsSold(numberOfLotsSold));
          $(document).bind("kendo:skinChange", createChartNumberOfLotsSold);
        } else if (selecteTab === "bidTypes") {
          bidType = chartData.data.auctionBidType;
          $(document).ready(createChartBidType(bidType));
          $(document).bind("kendo:skinChange", createChartBidType);
        } else if (selecteTab === "incomeVsNumberOfLots") {
          incomeVsNumberOfLots = chartData.data.incomeVsNumberOfLots;
          $(document).ready(createChartIncomeVsNumberOfLots(incomeVsNumberOfLots));
          $(document).bind("kendo:skinChange", createChartIncomeVsNumberOfLots);
        } else if (selecteTab === "vendorsCommission") {
          vendorsCommission = chartData.data.vendorsCommission;
          $(document).ready(createChartVendorsCommission(vendorsCommission));
          $(document).bind("kendo:skinChange", createChartVendorsCommission);
        }
      }
    }

    function tabSelected(stringOfTab) {
      selecteTab = stringOfTab;
      drawChart();
    }

    function createChartSaleResult(saleResult) {
      var categories = Object.keys(saleResult.actual);
      $scope.buyersPremiumForTable = saleResult.actual.premium;
      $scope.commissionForTable = saleResult.actual.commission;
      $scope.hammerForTable = saleResult.actual.hammer;
      $scope.insuranceForTable = saleResult.actual.insurance;
      $scope.actualTotal = parseInt($scope.buyersPremiumForTable) + parseInt($scope.commissionForTable) + parseInt($scope.insuranceForTable);

      $("#saleResult").kendoChart({
        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
        title: {
          text: "Sale result"
        },
        legend: {
          position: "top"
        },
        seriesDefaults: seriesDefault,
        series: [{
            name: "Actual",
            data: [$scope.hammerForTable, $scope.buyersPremiumForTable, $scope.commissionForTable, $scope.insuranceForTable, $scope.actualTotal]
          },
          $scope.budget
        ],
        valueAxis: {
          labels: {
            format: "{0}k"
          },
          line: {
            visible: false
          },
          axisCrossingValue: 0
        },
        categoryAxis: {
          categories: ["Hammer", "Buyers premium", "Commission", "Insurance", "Total"],
          line: {
            visible: false
          }
        },
        tooltip: {
          visible: true,
          format: "{0}k",
          template: "#= series.name #: #= value #"
        }
      });
    }

    function saleResultTotalUpdateAndChartRefresh() {
      $scope.budget.data[4] = $scope.budget.data[1] + $scope.budget.data[2] + $scope.budget.data[3];
      $scope.budgetTotal = $scope.budget.data[4];
      $("#saleResult").data("kendoChart").refresh();
    }

    function createChartValueOfLotsSold(valueOfLotsSold) {
      $scope.valueOfLotsSoldTable = [];
      var sold = [];
      var unsold = [];
      $scope.totalSold_ValueOfLotsSold = 0;
      $scope.totalUnsold_ValueOfLotsSold = 0;
      var categories = Object.keys(valueOfLotsSold.sold);
      for (var i = 0; i < categories.length; i++) {
        sold.push(valueOfLotsSold.sold[categories[i]]);
        unsold.push(valueOfLotsSold.unsold[categories[i]]);

        $scope.totalSold_ValueOfLotsSold += parseInt(valueOfLotsSold.sold[categories[i]]);
        $scope.totalUnsold_ValueOfLotsSold += parseInt(valueOfLotsSold.unsold[categories[i]]);
        var valueOfLotsSoldTableElement = {
          sold: valueOfLotsSold.sold[categories[i]],
          unsold: valueOfLotsSold.unsold[categories[i]],
          category: $scope.categoriesScopesMoney[i]
        };
        $scope.valueOfLotsSoldTable.push(valueOfLotsSoldTableElement);
      }

      sold.push($scope.totalSold_ValueOfLotsSold);
      unsold.push($scope.totalUnsold_ValueOfLotsSold);

      $("#valueOfLotsSold").kendoChart({
        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
        title: {
          text: "Value of lots sold"
        },
        legend: {
          position: "top"
        },
        seriesDefaults: seriesDefault,
        series: [{
          name: "Unsold",
          data: unsold
        }, {
          name: "Sold",
          data: sold
        }],
        valueAxis: {
          labels: {
            format: "{0}k"
          },
          line: {
            visible: false
          },
          axisCrossingValue: 0
        },
        categoryAxis: {
          categories: $scope.categoriesScopesMoney.concat(["Total"]),
          line: {
            visible: false
          }
        },
        tooltip: {
          visible: true,
          format: "{0}k",
          template: "#= series.name #: #= value #"
        }
      });
    }

    function createChartNumberOfLotsSold(numberOfLotsSold) {
      $scope.numberOfLotsSoldTable = [];
      var sold = [];
      var unsold = [];
      $scope.totalSold_NumberOfLotsSold = 0;
      $scope.totalUnsold_NumberOfLotsSold = 0;
      var categories = Object.keys(numberOfLotsSold.sold);
      for (var i = 0; i < categories.length; i++) {
        sold.push(numberOfLotsSold.sold[categories[i]]);
        unsold.push(numberOfLotsSold.unsold[categories[i]]);

        $scope.totalSold_NumberOfLotsSold += parseInt(numberOfLotsSold.sold[categories[i]]);
        $scope.totalUnsold_NumberOfLotsSold += parseInt(numberOfLotsSold.unsold[categories[i]]);
        var numberOfLotsSoldTableElement = {
          sold: numberOfLotsSold.sold[categories[i]],
          unsold: numberOfLotsSold.unsold[categories[i]],
          category: $scope.categoriesScopesMoney[i]
        };
        $scope.numberOfLotsSoldTable.push(numberOfLotsSoldTableElement);
      }

      sold.push($scope.totalSold_NumberOfLotsSold);
      unsold.push($scope.totalUnsold_NumberOfLotsSold);

      $("#numberOfLotsSold").kendoChart({
        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
        title: {
          text: "Number of lots sold"
        },
        legend: {
          position: "top"
        },
        seriesDefaults: seriesDefault,
        series: [{
          name: "Unsold",
          data: unsold
        }, {
          name: "Sold",
          data: sold
        }],
        valueAxis: {
          labels: {
            format: "{0}"
          },
          line: {
            visible: false
          },
          axisCrossingValue: 0
        },
        categoryAxis: {
          categories: $scope.categoriesScopesMoney.concat(["Total"]),
          line: {
            visible: false
          }
        },
        tooltip: {
          visible: true,
          format: "{0}",
          template: "#= series.name #: #= value #"
        }
      });
    }

    function createChartBidType(bidTypes) {
      var absentBid = {};
      var afterAuctionBid = {};
      var auctionRoomBid = {};
      var internetBid = {};
      var liveBid = {};
      var telephoneBid = {};

      absentBid.data = [];
      afterAuctionBid.data = [];
      auctionRoomBid.data = [];
      internetBid.data = [];
      liveBid.data = [];
      telephoneBid.data = [];

      absentBid.sum = 0;
      afterAuctionBid.sum = 0;
      auctionRoomBid.sum = 0;
      internetBid.sum = 0;
      liveBid.sum = 0;
      telephoneBid.sum = 0;

      absentBid.name = "Absent bid";
      afterAuctionBid.name = "After auction bid";
      auctionRoomBid.name = "Auction room bid";
      internetBid.name = "Internet bid";
      liveBid.name = "Live bid";
      telephoneBid.name = "Telephone bid";

      var categories = Object.keys(bidTypes.absentBid);
      for (var i = 0; i < categories.length; i++) {
        absentBid.data.push(bidTypes.absentBid[categories[i]]);
        afterAuctionBid.data.push(bidTypes.afterAuctionBid[categories[i]]);
        auctionRoomBid.data.push(bidTypes.auctionRoomBid[categories[i]]);
        internetBid.data.push(bidTypes.internetBid[categories[i]]);
        liveBid.data.push(bidTypes.liveBid[categories[i]]);
        telephoneBid.data.push(bidTypes.telephoneBid[categories[i]]);

        absentBid.sum += parseInt(bidTypes.absentBid[categories[i]]);
        afterAuctionBid.sum += parseInt(bidTypes.afterAuctionBid[categories[i]]);
        auctionRoomBid.sum += parseInt(bidTypes.auctionRoomBid[categories[i]]);
        internetBid.sum += parseInt(bidTypes.internetBid[categories[i]]);
        liveBid.sum += parseInt(bidTypes.liveBid[categories[i]]);
        telephoneBid.sum += parseInt(bidTypes.telephoneBid[categories[i]]);
      }
      $scope.bidTypesArray = [];
      $scope.bidTypesArray.push(absentBid);
      $scope.bidTypesArray.push(afterAuctionBid);
      $scope.bidTypesArray.push(auctionRoomBid);
      $scope.bidTypesArray.push(internetBid);
      $scope.bidTypesArray.push(liveBid);
      $scope.bidTypesArray.push(telephoneBid);

      $scope.bidTypesArray.sort(function(a, b) {
        return a.sum - b.sum;
      });

      $scope.rowCount = [];
      for (var i = 0; i < $scope.categoriesScopesMoney.length; i++) {
        $scope.rowCount.push(i);
      }

      $("#bidTypes").kendoChart({
        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
        title: {
          text: "Bid type"
        },
        legend: {
          position: "top"
        },
        seriesDefaults: seriesDefault,
        series: $scope.bidTypesArray,
        valueAxis: {
          labels: {
            format: "{0}"
          },
          line: {
            visible: false
          },
          axisCrossingValue: 0
        },
        categoryAxis: {
          categories: $scope.categoriesScopesMoney,
          line: {
            visible: false
          }
        },
        tooltip: {
          visible: true,
          format: "{0}",
          template: "#= series.name #: #= value #"
        }
      });
    }

    function createChartIncomeVsNumberOfLots(incomeVsNumberOfLots) {
      $scope.incomeVsNumberOfLotsTable = [];
      var income = [];
      var noLots = [];
      var categories = Object.keys(incomeVsNumberOfLots.income);
      for (var i = 0; i < categories.length; i++) {
        income.push(incomeVsNumberOfLots.income[categories[i]]);
        noLots.push(incomeVsNumberOfLots.noLots[categories[i]]);
        var incomeVsNumberOfLotsTableElement = {
          income: incomeVsNumberOfLots.income[categories[i]],
          noLots: incomeVsNumberOfLots.noLots[categories[i]],
          category: $scope.categoriesScopesMoney[i]
        };
        $scope.incomeVsNumberOfLotsTable.push(incomeVsNumberOfLotsTableElement);
      }
      $("#incomeVsNumberOfLots").kendoChart({
        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
        title: {
          text: "Number of lots v Income"
        },
        legend: {
          position: "top"
        },
        seriesDefaults: seriesDefault,
        series: [{
          name: "Income",
          data: income
        }, {
          name: "No lots",
          data: noLots
        }],
        valueAxis: {
          labels: {
            format: "{0}k"
          },
          line: {
            visible: false
          },
          axisCrossingValue: 0
        },
        categoryAxis: {
          categories: $scope.categoriesScopesMoney,
          line: {
            visible: false
          }
        },
        tooltip: {
          visible: true,
          format: "{0}k",
          template: "#= series.name #: #= value #"
        }
      });
    }

    function createChartVendorsCommission(vendorsCommission) {
      $scope.vendorsCommissionTable = [];
      var vc = [];
      var categories = Object.keys(vendorsCommission.vc);
      for (var i = 0; i < categories.length; i++) {
        vc.push(vendorsCommission.vc[categories[i]]);
        var vendorsCommissionTableElement = {
          vendorsCommission: vendorsCommission.vc[categories[i]],
          category: $scope.categoriesScopesMoney[i]
        };
        $scope.vendorsCommissionTable.push(vendorsCommissionTableElement);
      }
      $("#vendorsCommission").kendoChart({
        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
        title: {
          text: "% Vendors commission"
        },
        legend: {
          position: "top"
        },
        seriesDefaults: seriesDefault,
        series: [{
          name: "VC",
          data: vc
        }],
        valueAxis: {
          labels: {
            format: "{0}%"
          },
          line: {
            visible: false
          },
          axisCrossingValue: 0
        },
        categoryAxis: {
          categories: $scope.categoriesScopesMoney,
          line: {
            visible: false
          }
        },
        tooltip: {
          visible: true,
          format: "{0}",
          template: "#= series.name #: #= value #"
        }
      });
    }

    $scope.$on('ImportService:runCheckTaskStatus', function(event, args) {
      TaskStatusService.checkTaskStatus(args);
    });

    $scope.$on('TaskStatusService:statusIsChanged', function(event, args) {
      if (args == "Failed") {
        failAlert("Task status is changed to - " + args);
      } else {
        infoAlert("Task status is changed to - " + args);
      }
      if (args === "Complete" || args === "Failed" || args === "Complete (exceptions)") {
        $scope.checked = false;
      }
    });
  }
})();