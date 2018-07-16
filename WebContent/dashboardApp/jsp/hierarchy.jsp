<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String view = (String)request.getAttribute("view");
//String view= "hierarchy";
String isEdit = (String)request.getAttribute("isEdit");
String uuid = (String)request.getAttribute("uuid");
%>
<!DOCTYPE html>
<html ng-app="dashboardFormApp" class="dashboardApp">
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

        <!-- SoftproIdeas style -->
        <link rel="stylesheet" href="<%= path %>/css/commons.css">
        <link rel="stylesheet" href="<%= path %>/dashboardApp/css/main.css?version=<spring:message code="buildNumber"/>

        <!-- Project basePath, global variable form java to js -->
        <script type="text/javascript">
            var $BASE_PATH = '<%=basePath%>';
            var VIEW = '<%=view%>';
            var ISEDIT = '<%=isEdit%>';
            var $UUID = '<%=uuid%>';
        </script>
    </head>
	<body class="bonhamsDashboardApp">
        <div class="container-full" ng-controller="DashboardFormController as formCtrl">
        <div class="timer">
            Session timeout: {{sessionTime}} min | <a class="logout" ng-click="formCtrl.logout()">logout</a> | <a class="logout" ng-click="formCtrl.closeTab()">close tab</a>
        </div>

            <div id="container-navbar" role="navigation" ng-hide="formCtrl.editMode === formCtrl.views.open">
                <h2 class="pageTitle pull-left dashboardName">
                    <input type="text" name="text1" id="text1" ng-model="formCtrl.dashboardName" ng-change="formCtrl.toggleAskIfReload()" class="form-control edit" placeholder="Dashboard name..." />
                </h2>

                <form class="form-inline pull-right form-right" ng-hide="formCtrl.showOnlySpread">
                    <button ng-click="formCtrl.saveDashboard()" type="submit" class="btn btn-default btn-sm btn-save" ng-disabled="false"><i id="excelExport" class="fa fa-save"></i> Save </button>
                </form>

                <div id="selectForm" class="input-group pull-right">
                    <input type="text" class="form-control" ng-model="formCtrl.selectedFlatForm.vis_id" disabled>
                    <span class="input-group-btn">
                       <button class="btn btn-default btn-sm" ng-click="formCtrl.showModal()" title ="Select Flat Form"> <i class="fa fa-list"></i> Select form</button>
                    </span>
                </div>

                <div class="clearfix"></div>
            </div> <!-- end container-navbar -->
            
            <div id="container-navbar" role="navigation" ng-show="formCtrl.editMode === formCtrl.views.open">
                <h2 class="pageTitle pull-left dashboardName">{{formCtrl.dashboardName}}</h2>
                <div class="clearfix"></div>
            </div>
        
            <div id="container-main">
                <div class="row <%=view.equals("hierarchy") ? "" : "ng-hide"%>">
                    <div class="col-md-12">
                        <ul class="breadcrumb" style="margin-bottom: 2px;">
                            <li>
                                <a href="#" ng-bind="formCtrl.contextData.ccTreeString" ng-class="{'notSet': ccTreeString == contextDataPlaceholder.business}" ng-click="formCtrl.setContextData('business')"></a>
                            </li>
                            <li>
                                <a href="#" ng-bind="formCtrl.contextData.expTreeString" ng-class="{'notSet': expTreeString == contextDataPlaceholder.account}" ng-click="formCtrl.setContextData('account')" ng-show="formCtrl.editMode !== formCtrl.views.open"></a>
                                <span ng-bind="formCtrl.contextData.expTreeString" ng-class="fixedDimension" ng-show="formCtrl.editMode === formCtrl.views.open"></span>
                            </li>
                            <li>
                                <a href="#" ng-bind="formCtrl.contextData.callFromTreeString" ng-class="{'notSet': callFromTreeString == contextDataPlaceholder.calendarfrom}" ng-click="formCtrl.setContextData('calendarfrom')"></a> - 
                                <a href="#" ng-bind="formCtrl.contextData.callToTreeString" ng-class="{'notSet': callToTreeString == contextDataPlaceholder.calendarto}" ng-click="formCtrl.setContextData('calendarto')"></a>
                            </li>
                            <li>
                                <a href="#" ng-bind="formCtrl.contextData.dataTypeString" ng-class="{'notSet': dataTypeString == contextDataPlaceholder.dataType}" ng-click="formCtrl.setContextData('dataType')" ng-show="formCtrl.editMode !== formCtrl.views.open"></a>
                                <span ng-bind="formCtrl.contextData.dataTypeString" ng-class="fixedDimension" ng-show="formCtrl.editMode === formCtrl.views.open"></span>
                            </li>
                            <li id="test" ng-show="formCtrl.editMode !== formCtrl.views.open">
                                <button ng-click="formCtrl.testDashboard()" type="submit" class="btn btn-default btn-sm saveAll" ng-disabled="false"><i id="excelExport" class="fa fa-play"></i> Test </button>
                            </li>
                        </ul>
                    </div>
                </div>
                
                <div class="clearfix"></div>

                <div ng-hide="formCtrl.showOnlySpread">
                    <tabset justified="false" class="mainTabs generalTabs">
                        <!--active="tab.tab.active"-->
                   		<tab ng-repeat="tab in formCtrl.tabs" ng-if="tab !== undefined" active="tab.tab.active" ng-click="formCtrl.onTabSelect(tab.tab.id)">
                            <tab-heading>
                                {{tab.tab.title}} 
                                <span class="manageTab" ng-hide="formCtrl.editMode === formCtrl.views.open">
                                    <i class="deleteTab fa fa-remove" ng-click="formCtrl.deleteTab(tab.tab.id); $event.stopPropagation();" title="delete tab"></i>
                                    <i class="renameTab fa fa-edit" ng-click="formCtrl.renameTab(tab.tab.id); $event.stopPropagation();" title="rename tab"></i>
                                </span>
                            </tab-heading>  
                            
        					<div class="manageCharts" ng-show="formCtrl.controllButtonsEnable && formCtrl.editMode !== formCtrl.views.open">                      
                                <div class="setting">
                                    <button type="button" ng-click="formCtrl.selectData(tab.tab.id)" class="btn btn-success">Select Data</button>
                                </div>
                                <div class="setting" ng-show="formCtrl.showCharts && tab.chartType.value !== 'pie'">
                                    <div class="info">Chart type:</div>
        				            <div class="dataDropdown">
        				                <select class="form-control"
        				                	ng-options="chartAvailableTypeNoPie.label as chartAvailableTypeNoPie.label for chartAvailableTypeNoPie in formCtrl.chartAvailableTypesNoPie"
                                            ng-model="formCtrl.tabs[formCtrl.tabID].chartType.label"
                                            ng-change="formCtrl.changeChartType()">
        				                    <!--ng-model="tab.chartType"-->
        				                    <!--ng-change="formCtrl.drawChart4Tab(tab.tab.id);formCtrl.toggleAskIfReload()">-->
        				                </select>
        				            </div>
        				        </div>
                                <br class="clearBoth">
                           </div>                               
                             
                            <div id="myChart-{{tab.tab.id}}" ng-show="formCtrl.showCharts"></div>
                            <div id="myGrid-{{tab.tab.id}}" ng-show="formCtrl.showCharts">
                                <div ng-show="formCtrl.tabs[formCtrl.tabID].chartType.value !== 'pie'">
                            	<table class="table table-striped table-hover auctionTable">
        							<thead>
        								<tr>
        									<th></th>
        									<th ng-repeat="col in tab.values.columns track by $index">{{col}}</th>
        								</tr>
        							</thead>
        							<tbody>
        								<tr ng-repeat="row in tab.values.rows track by $index">
        									<th>{{row}}</th>
        									<td ng-repeat="value in tab.values.values[$index] track by $index">{{value}}</td>
        								</tr>
        							</tbody>
        						</table>
                                </div>
                                <div ng-show="formCtrl.tabs[formCtrl.tabID].chartType.value === 'pie'">
                                <table class="table table-striped table-hover auctionTable">
        							<thead>
        								<tr>
        									<th></th>
        									<th>Values</th>
        								</tr>
        							</thead>
        							<tbody>
                                        <tr ng-repeat="row in tab.values.rows track by $index">
        									<th>{{row}}</th>
                                            <td ng-show="formCtrl.tabs[formCtrl.tabID].values.values[0].length === 1">{{tab.values.values[$index][0]}}</td>
                                            <td ng-show="formCtrl.tabs[formCtrl.tabID].values.values[0].length !== 1">{{tab.values.values[0][$index]}}</td>
        								</tr>
        							</tbody>
        						</table>
                                </div>
                            </div>             
            			</tab>
            			
                         <tab ng-click="formCtrl.addTab()" ng-hide="formCtrl.editMode === formCtrl.views.open">
                            <tab-heading>
                                <i class="fa fa-plus-circle"></i>
                            </tab-heading>  
                        </tab>

            		</tabset>
                </div>
                <div class="manageCharts" ng-show="formCtrl.showOnlySpread">
                    <div class="setting">
                        <div class="info">Rows:</div>
                        <div class="data dataNoMargin"><!--ng-show="rowsRange[tabID]"-->
                            <input type="text" class="form-control" value="'{{formCtrl.tabs[formCtrl.tabID].ranges.rows.sheetName}}'{{formCtrl.tabs[formCtrl.tabID].ranges.rows.firstHeader}}:{{formCtrl.tabs[formCtrl.tabID].ranges.rows.lastHeader}}" disabled/>
                        </div>
                        <!--<button type="button" ng-click="formCtrl.setRows()" class="btn btn-selection-rows btnRange" ng-disabled="setRowsFlag"><i class="fa fa-th"></i></button>-->
                        <button type="button" ng-click="formCtrl.setRows()" class="btn btn-selection-rows btnRange" ng-disabled="formCtrl.isSelected(formCtrl.consts.rows)"><i class="fa fa-th"></i></button>
                    </div>
                    <div class="setting" ng-hide="formCtrl.tabs[formCtrl.tabID].chartType.value==='pie'">
                        <div class="info">Columns:</div>
                        <div class="data dataNoMargin"><!--ng-show="colsRange[tabID]"-->
                            <input type="text" class="form-control" value="'{{formCtrl.tabs[formCtrl.tabID].ranges.columns.sheetName}}'{{formCtrl.tabs[formCtrl.tabID].ranges.columns.firstHeader}}:{{formCtrl.tabs[formCtrl.tabID].ranges.columns.lastHeader}}" disabled />
                        </div>
                        <button type="button" ng-click="formCtrl.setColumns()" class="btn btn-selection-columns btnRange" ng-disabled="formCtrl.isSelected(formCtrl.consts.cols)"><i class="fa fa-th"></i></button>
                    </div>
                    <div class="setting">
                        <div class="info">Values:</div>
                        <div class="data dataNoMargin"><!--ng-show="valuesRange[tabID]"-->
                            <input type="text" class="form-control" value="'{{formCtrl.tabs[formCtrl.tabID].ranges.values.sheetName}}'{{formCtrl.tabs[formCtrl.tabID].ranges.values.firstHeader}}:{{formCtrl.tabs[formCtrl.tabID].ranges.values.lastHeader}}" disabled />
                        </div>
                        <button type="button" ng-click="formCtrl.setValues()" class="btn btn-selection-values btnRange" ng-disabled="formCtrl.isSelected(formCtrl.consts.vals)"><i class="fa fa-th"></i></button>
                    </div>
                    <div class="setting pull-right">
                        <button type="button" ng-click="formCtrl.confirmDataSelect(false)" class="btn btn-success">OK</button>
                        <!--formCtrl.spreadVisible = false;formCtrl.showOnlySpread = false;-->
                        <button type="button" ng-click="formCtrl.cancelDataSelect()" class="btn btn-default btnCancel">Cancel</button>
                    </div>
                    <div class="checkbox setting pull-right" >
						<label class="checkbox">
							<input type="checkbox" ng-model="formCtrl.readFromHiddenCells" class="ng-pristine ng-valid"> Read from hidden cells
						</label>
					</div>
                    <div class="setting pull-right">
                        <div class="info">Chart type:</div>
                        <div class="dataDropdown">
                            <select class="form-control"
                                ng-options="chartAvailableType.label as chartAvailableType.label for chartAvailableType in formCtrl.chartAvailableTypes"
                                ng-model="formCtrl.tabs[formCtrl.tabID].chartType.label"
                                ng-change="formCtrl.changeChartType()">
                            </select>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
    		</div> <!-- end container-main --> 
      
    		<div id="spreadContainer" style="width: 1200px; height: 800px; border: 1px solid gray" ng-show="formCtrl.showOnlySpread"></div>

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

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validation.app.js?version=<spring:message code="buildNumber"/>"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationController.js?version=<spring:message code="buildNumber"/>"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationDisplayDirective.js?version=<spring:message code="buildNumber"/>"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationService.js?version=<spring:message code="buildNumber"/>"></script>    
    
    <script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/import.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/providers/importService.js?version=<spring:message code="buildNumber"/>"></script>

    <script type="text/javascript" src="<%= path %>/dashboardApp/js/dashboardFormApp.js?version=<spring:message code="buildNumber"/>"></script>
    
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/dashboardForm.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/providers/dashboardFormService.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/providers/dashboardSpreadSheetService.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/controllers/dashboardFormController.js?version=<spring:message code="buildNumber"/>"></script>
    
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/directives/dashboardSelectFlatFormDirective.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/controllers/dashboardSelectFlatFormController.js?version=<spring:message code="buildNumber"/>"></script>
    <script type="text/javascript" src="<%= path %>/dashboardApp/js/modules/hierarchy/providers/dashboardSelectFlatFormService.js?version=<spring:message code="buildNumber"/>"></script>
</html>