<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html ng-app="dashboardAuction" class="dashboardApp">
    <head>
        <title>Auction Dashboard</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

        <!-- jQuery -->
        <script type="text/javascript" src="<%=path %>/libs/jquery/1.11.1/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/jquery-ui/jquery-ui.min.js"></script>
        <link rel="stylesheet" href="<%=path %>/libs/jquery-ui/jquery-ui.css">
        <script type="text/javascript" src="<%=path %>/libs/jquery/jquery.ba-outside-events.min.js"></script>

        <!-- File download -->
        <script type="text/javascript" src="<%= path %>/libs/jquery/jquery.fileDownload.min.js"></script>
        
        <!-- Angular -->
        <script type="text/javascript" src="<%= path %>/libs/ng-file-upload/ng-file-upload-shim.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-route.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-animate.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-sanitize.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-cookies.min.js"></script>

        <script type="text/javascript" src="<%= path %>/libs/angularjs/ui-bootstrap/ui-bootstrap-tpls-0.11.2.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/message-center.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/angular-flash.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/ng-file-upload/ng-file-upload.min.js"></script>

        <!-- Sweet Alert -->
        <script type="text/javascript" src="<%= path %>/libs/sweetalert/sweet-alert.min.js"></script>
        <link href="<%= path %>/libs/sweetalert/sweet-alert.css" type="text/css" rel="stylesheet" >
        
        <!-- Bootstrap -->
        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/3.2.0/css/bootstrap-submenu.css">
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/font-awesome/4.2.0/css/font-awesome.min.css">
        <script type="text/javascript" src="<%= path %>/libs/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/bootstrap-slider/css/slider.css">
        <script type="text/javascript" src="<%= path %>/libs/bootstrap/bootstrap-slider/js/bootstrap-slider.js"></script>


        <!-- Wijmo-Angular interop -->
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/styles/wijmo.min.css" />
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/interop/angular/wijmo.angular.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.grid.min.js" type="text/javascript"></script>
        
        <!-- kendo ui -->
        <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.common-material.min.css" />
	    <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.material.min.css" />
	    <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.dataviz.min.css" />
	    <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.dataviz.material.min.css" />

        <!-- SoftproIdeas style -->
        <link rel="stylesheet" href="<%= path %>/css/commons.css">
        <link rel="stylesheet" href="<%= path %>/dashboardApp/css/main.css?version=<spring:message code="buildNumber"/>">

         <!-- jsTree -->
        <link rel="stylesheet" href="<%= path %>/libs/jsTree/style.min.css" />
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jstree.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jsTree.directive.js"></script>
        
        <!-- Angular Loading Bar -->
        <link rel='stylesheet' href='<%= path %>/libs/loadingBar/loading-bar.css' type='text/css' media='all' />
		<script type='text/javascript' src='<%= path %>/libs/loadingBar/loading-bar.js'></script>

        <!-- kendo ui -->
        <script src="<%= path %>/libs/kendo/js/kendo.all.min.js"></script>

        <!-- Project basePath, global variable form java to js -->
        <script type="text/javascript">
            var $BASE_PATH = '<%=basePath%>';
            var $BASE_TEMPLATE_PATH = '<%=basePath%>' + 'dashboardApp/js/';
            var $AUCTION_NO = String(${auctionNo});
        </script>
    </head>
	<body class="bonhamsDashboardApp">
        <div id="container-full" ng-controller="DashboardController">
        <div class="timer">
        	Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a>
        </div>
        <div flash-message="5000" ></div>

        	<div id="container-navbar" role="navigation">
	            <h2 class="pageTitle pull-left">{{mainLabel}}</h2>
				
				<form class="pull-right form-inline form-right" ng-submit="exportToExcel()">
					<div class="btn-group">
						<button class="btn btn-default btn-sm" ng-click="importData()" ng-disabled="checked"><i id="importData" class="fa fa-download"></i> Import data</button>
						<button type="submit" class="btn btn-default btn-sm" ng-disabled="!auctionNumber"><i id="excelExport" class="fa fa-file-excel-o"></i> Export to Excel</button>
						</div>
				</form>

				<form class="pull-right form-inline" ng-submit="showModal(dashboardModel)">
					<div class="form-group">
						<select class="form-control" id="activeModel" ng-change="selectCurrentModel(dashboardModel)" ng-model="dashboardModel.model" ng-options="(model) for model in dashboardModel.models"></select>
					</div>
					<div class="form-group">
			 			<button type="submit" class="btn btn-default btn-sm btn-select-auction"><i class="fa fa-list"></i> Select auction</button>
			 		</div>
				</form>

				<div class="clearfix"></div>
			</div> <!-- end container-navbar -->

        	<div id="container-main">

				<tabset justified="false" class="generalTabs mainTabs">
					<tab heading='Sale result' ng-click="tabSelected('saleResult')">
						<div id="saleResult"></div>
						<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
							<thead>
								<tr>
									<th></th>
									<th>Actual</th>
									<th>Budget</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="col-md-4">Hammer</td>
									<td class="col-md-4">{{hammerForTable}}</td>
									<td class="col-md-4"><input type="number" class="form-control" ng-model="budget.data[0]" ng-change="saleResultTotalUpdateAndChartRefresh()"></td>
								</tr>
								<tr>
									<td class="col-md-4">Buyers premium</td>
									<td class="col-md-4">{{buyersPremiumForTable}}</td>
									<td class="col-md-4"><input type="number" class="form-control" ng-model="budget.data[1]" ng-change="saleResultTotalUpdateAndChartRefresh()"></td>
								</tr>
								<tr>
									<td class="col-md-4">Commission</td>
									<td class="col-md-4">{{commissionForTable}}</td>
									<td class="col-md-4"><input type="number" class="form-control" ng-model="budget.data[2]" ng-change="saleResultTotalUpdateAndChartRefresh()"></td>
								</tr>
								<tr>
									<td class="col-md-4">Insurance</td>
									<td class="col-md-4">{{insuranceForTable}}</td>
									<td class="col-md-4"><input type="number" class="form-control" ng-model="budget.data[3]" ng-change="saleResultTotalUpdateAndChartRefresh()"></td>
								</tr>
								<tr>
									<td class="col-md-4">Total</td>
									<td class="col-md-4">{{actualTotal}}</td>
									<td class="col-md-4"><div class="paddingLikeInput">{{budgetTotal}}</div></td>
								</tr>
							</tbody>
						</table>
					</tab>
		        	<tab heading='Value of lots sold' ng-click="tabSelected('valueOfLotsSold')">
						<div id="valueOfLotsSold"></div>
						<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
							<thead>
								<tr>
									<th></th>
									<th>Unsold</th>
									<th>Sold</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="element in valueOfLotsSoldTable">
									<td ng-bind-html="element.category"></td>
									<td>{{element.unsold}}</td>
									<td>{{element.sold}}</td>
								</tr>
								<tr>
									<td>Total</td>
									<td>{{totalUnsold_ValueOfLotsSold}}</td>
									<td>{{totalSold_ValueOfLotsSold}}</td>
								</tr>
							</tbody>
						</table>
					</tab>
					<tab heading='Number of lots sold' ng-click="tabSelected('numberOfLotsSold')">
						<div id="numberOfLotsSold"></div>
						<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
							<thead>
								<tr>
									<th></th>
									<th>Unsold</th>
									<th>Sold</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="element in numberOfLotsSoldTable">
									<td ng-bind-html="element.category"></td>
									<td>{{element.unsold}}</td>
									<td>{{element.sold}}</td>
								</tr>
								<tr>
									<td>Total</td>
									<td>{{totalUnsold_NumberOfLotsSold}}</td>
									<td>{{totalSold_NumberOfLotsSold}}</td>
								</tr>
							</tbody>
						</table>
					</tab>
					<tab heading='Bid types' ng-click="tabSelected('bidTypes')">
						<div id="bidTypes"></div>
						<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
							<thead>
								<tr>
						           <th></th>
						           <th ng-repeat="bidType in bidTypesArray">{{bidType.name}}</th>
								</tr>
							</thead>
							<tbody>
						       <tr ng-repeat="index in rowCount">
						           <td>{{categoriesScopesMoney[index]}}</td>
						           <td ng-repeat="bidType in bidTypesArray">{{bidType.data[index]}}</td>
						       </tr>
							</tbody>
						</table>
					</tab>
					<tab heading='Number of lots v income' ng-click="tabSelected('incomeVsNumberOfLots')">
						<div id="incomeVsNumberOfLots"></div>
						<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
							<thead>
								<tr>
									<th></th>
									<th>Income</th>
									<th>No lots</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="element in incomeVsNumberOfLotsTable">
									<td ng-bind-html="element.category"></td>
									<td>{{element.income}}</td>
									<td>{{element.noLots}}</td>
								</tr>
							</tbody>
						</table>
					</tab>
					<tab heading='Vendors commission' ng-click="tabSelected('vendorsCommission')">
						<div id="vendorsCommission"></div>
						<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
							<thead>
								<tr>
									<th></th>
									<th>% Vendors commission</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="element in vendorsCommissionTable">
									<td ng-bind-html="element.category"></td>
									<td>{{element.vendorsCommission}}</td>
								</tr>
							</tbody>
						</table>
					</tab>
				</tabset>
			</div> <!-- end container-main -->
		</div> <!-- end container-full -->
    </body> 

    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/veilController.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/directives/numbersOnly.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/taskStatusService.js?version=<spring:message code="buildNumber"/>"></script>

	<script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/import.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/providers/importService.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/dashboardApp/js/dashboardAuctionApp.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/auction/providers/dashboardService.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/auction/providers/auctionSelectService.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/auction/directives/auctionSelect.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/auction/controllers/auctionSelectController.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/auction/controllers/dashboardController.js?version=<spring:message code="buildNumber"/>"></script>
</html>