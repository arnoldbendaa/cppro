<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String view = (String)request.getAttribute("view");
String uuid = (String)request.getAttribute("uuid");
%>
<!DOCTYPE html>
<html ng-app="dashboardViewApp" class="dashboardApp">
    <head>
        <title>Dashboard</title>
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
        <script type="text/javascript" src="<%= path %>/libs/sweetalert/sweetalert.min.js"></script>
        <link href="<%= path %>/libs/sweetalert/sweetalert.css" type="text/css" rel="stylesheet" >
        
        <!-- Bootstrap -->
        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/3.2.0/css/bootstrap-submenu.css">
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/font-awesome/4.2.0/css/font-awesome.min.css">
        <script type="text/javascript" src="<%= path %>/libs/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/bootstrap-slider/css/slider.css">
        <script type="text/javascript" src="<%= path %>/libs/bootstrap/bootstrap-slider/js/bootstrap-slider.js"></script>

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
        
        
        <!-- Wijmo-Angular interop -->
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/styles/wijmo.min.css" />
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/interop/angular/wijmo.angular.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.grid.min.js" type="text/javascript"></script>
        
        <!-- SpreadJS -->
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/gcspread.sheets.8.40.20151.4.css">
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/sterling/jquery-wijmo.css">
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/8.40.20151.4/js/gcspread.sheets.all.8.40.20151.4.v1.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/8.40.20151.4/js/interop/angular.gcspread.sheets.8.40.20151.4.min.js"></script>
        
        <!-- Project basePath, global variable form java to js -->
        <script type="text/javascript">
            var $BASE_PATH = '<%=basePath%>';
            var VIEW = '<%=view%>';
            var UUID = '<%=uuid%>';
        </script>
    </head>
	<body class="bonhamsDashboardApp">
        <div class="container-full" ng-controller="DashboardViewController">
        <div class="timer">
            Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a>
        </div>
            <div id="container-navbar" role="navigation">
                <h2 class="pageTitle pull-left dashboardName">{{dashboardName}}</h2>
                <div class="clearfix"></div>
            </div> <!-- end container-navbar -->
        
            <div id="container-main">

                <div class="row <%=view.equals("hierarchy") ? "" : "ng-hide"%>">
                    <div class="col-md-12">
                        <ul class="breadcrumb" style="margin-bottom: 2px;">
                            <li>
                                <a href="#" ng-bind="ccTreeString" ng-class="{'notSet': ccTreeString == contextDataPlaceholder.business}" ng-click="setContextData('business')"></a>
                            </li>
                            <li>
                                <span ng-bind="expTreeString" ng-class="fixedDimension"></span>
                            </li>
                            <li>
                                <a href="#" ng-bind="callFromTreeString" ng-class="{'notSet': callFromTreeString == contextDataPlaceholder.calendarfrom}" ng-click="setContextData('calendarfrom')"></a> - 
                                <a href="#" ng-bind="callToTreeString" ng-class="{'notSet': callToTreeString == contextDataPlaceholder.calendarto}" ng-click="setContextData('calendarto')"></a>
                            </li>
                            <li>
                                <span ng-bind="dataTypeString" ng-class="fixedDimension"></span>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="clearfix"></div>

                <tabset justified="false" class="generalTabs mainTabs">
               		<tab ng-repeat="tab in tabs" ng-if="tab !== undefined" ng-click="repaint(tab.id)">
                        <tab-heading>
                            {{tab.title}}
                        </tab-heading>   
                        <div id="myChart-{{tab.id}}"></div>
                        <div id="myGrid-{{tab.id}}">
                            <div ng-show="tabs[tab.id].chartType !== 'pie'">
                            	<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
        							<thead>
        								<tr>
        									<th></th>
        									<th ng-repeat="col in gridCols[tab.id] track by $index">{{col}}</th>
        								</tr>
        							</thead>
        							<tbody>
        								<tr ng-repeat="row in gridRows[tab.id] track by $index">
        									<th>{{row}}</th>
        									<td ng-repeat="value in gridValues[tab.id][$index] track by $index">{{value}}</td>
        								</tr>
        							</tbody>
        						</table>
                                </div>
                                <div ng-show="tabs[tab.id].chartType === 'pie'">
                                <table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
        							<thead>
        								<tr>
        									<th></th>
        									<th>Values</th>
        								</tr>
        							</thead>
        							<tbody>
    								<tr ng-repeat="row in gridRows[tab.id] track by $index">
    									<th>{{row}}</th>
                                        <td ng-show="gridValues[tab.id][0].length === 1">{{gridValues[tab.id][$index][0]}}</td>
                                        <td ng-show="gridValues[tab.id][0].length !== 1">{{gridValues[tab.id][0][$index]}}</td>
    								</tr>
        							</tbody>
        						</table>
                                </div>
                        	<!--<table class="table table-striped table-hover auctionTable" ng-show="!tableHidden">
    							<thead>
    								<tr>
    									<th></th>
    									<th ng-repeat="col in gridCols[tab.id] track by $index">{{col}}</th>
    								</tr>
    							</thead>
    							<tbody>
    								<tr ng-repeat="row in gridRows[tab.id] track by $index">
    									<th>{{row}}</th>
                                        <td ng-show="gridValues[tab.id][0].length === 1">{{gridValues[tab.id][$index][0]}}</td>
                                        <td ng-show="gridValues[tab.id][0].length !== 1">{{gridValues[tab.id][0][$index]}}</td>
    									<td ng-repeat="value in gridValues[tab.id][$index] track by $index">{{value}}</td>
    								</tr>
    							</tbody>
    						</table>-->
                        </div>             
        			</tab>
        		</tabset>
    		</div> <!-- end container-main -->
		    
            <div id="spreadContainer" style="width: 1200px; height: 800px; border: 1px solid gray" ng-show="false"></div>

        </div> <!-- end container-full -->
    </body> 

    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/veilController.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/treesChooser.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/businessChooser.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/directives/numbersOnly.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/taskStatusService.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/import.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/providers/importService.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/dashboardApp/js/dashboardViewApp.js?version=<spring:message code="buildNumber"/>"></script>
    
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/dashboardForm.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/providers/dashboardFormService.js?version=<spring:message code="buildNumber"/>"></script>
    
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/dashboardView.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/providers/dashboardViewService.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/controllers/dashboardViewController.js?version=<spring:message code="buildNumber"/>"></script>
</html>