<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html ng-app="lookupProjectApp" class="lookupProjectApp">
    <head>
        <title>Project - Lookup Table</title>
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

        <!-- Wijmo -->
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/styles/wijmo.min.css" />
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.grid.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/interop/angular/wijmo.angular.min.js" type="text/javascript"></script>

        <!-- SpreadJS -->
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/gcspread.sheets.8.40.20151.4.css">
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/sterling/jquery-wijmo.css">
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/10.1.0/scripts/gc.spread.sheets.all.10.1.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/10.1.0/scripts/interop/angular.gc.spread.sheets.10.1.0.min.js"></script>

        <!-- kendo ui -->
        <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.common-material.min.css" />
	    <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.material.min.css" />
	    <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.dataviz.min.css" />
	    <link rel="stylesheet" href="<%= path %>/libs/kendo/css/kendo.dataviz.material.min.css" />
        <!-- kendo ui -->
        <script src="<%= path %>/libs/kendo/js/kendo.all.min.js"></script>
        
        <!-- SoftproIdeas style -->
        <link rel="stylesheet" href="<%= path %>/css/commons.css">
        <link rel="stylesheet" href="<%= path %>/lookuptableApp/css/main.css">

         <!-- jsTree -->
        <link rel="stylesheet" href="<%= path %>/libs/jsTree/style.min.css" />
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jstree.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jsTree.directive.js"></script>

        <!-- Project basePath, global variable form java to js -->
        <script type="text/javascript">
            var $BASE_PATH = '<%=basePath%>';
            var $BASE_APP_PATH = '<%=path%>' + '/lookupTable';
            var $BASE_TEMPLATE_PATH = '<%=basePath%>' + 'lookuptableApp/js/';
        </script>
    </head>
	<body class="bonhamsLookupProjectApp" ng-controller="ProjectController">
        <div class="timer">
            Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a>
        </div>
        <div flash-message="5000" ></div> 

        <div id="container-full">
            <div id="container-navbar" role="navigation">
                <h2 class="pageTitle pull-left" >Project Lookup</h2>
                <div class="pull-right">
                <form class="form-inline pull-left" ng-submit="searchByInputMapping()">
    			  <div class="form-group">
    			    <label for="inputMapping">Show data by IM:</label>
    			    <input type="text" class="form-control" id="inputMapping" ng-model="inputMapping">
    			  </div>
    			  <button type="submit" class="btn btn-default btn-sm" >Display</button>
    			</form>
    			
    			<button class="btn btn-default pull-right btn-sm import-btn" ng-click="importData()" ng-disabled="checked"><i id="importData" class="fa fa-download"></i> Import data</button>
    			</div>
    			<div class="clearfix"></div>
            </div> <!-- end container-navbar -->
        </div> <!-- end container-full -->

		<div id="kendoGrid"></div>
    </body> 

    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuActionService.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/capitalize.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/taskStatusService.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/dictionary.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/providers/dictionaryService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/directives/dictionaryEditor.js"></script>

	<script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/import.js"></script>
    <script type="text/javascript" src="<%= path %>/adminApp/js/modules/importtasks/providers/importService.js"></script>
    
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/lookupTableApp.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/lookupProjectApp.js"></script>

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/project/project.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/project/controllers/projectController.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/project/providers/projectService.js"></script>
    

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/spreadSheet.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/controllers/spreadSheetController.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/drawingService.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/spreadSheetService.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/spreadSheetMainMenuService.js"></script>
</html>