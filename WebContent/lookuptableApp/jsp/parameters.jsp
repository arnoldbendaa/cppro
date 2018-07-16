<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html ng-app="lookupParametersApp" class="lookupParametersApp">
    <head>
        <title>Parameters - Lookup Table</title>
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
    <body class="bonhamsLookupParametersApp">
        <div class="timer">
            Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a>
        </div>

        <div class="container-full" ng-controller="ParametersController">
            <div id="container-navbar" role="navigation">
                <div class="pull-left">
                    <button class="btn btn-default btn-sm pull-right" ng-click="add()" ng-disabled="parameterLoader.isParametersSaving">Fields</button>
                </div>

                <div class="options-navbar pull-right" >
                    <button class="btn btn-default btn-sm" ng-click="importDimensions()" ng-disabled="!currentCompany"><i id="importData" class="fa fa-download"></i> Import data</button>
                </div>

                <div class="options-navbar form-inline pull-right">
                    <div class="form-group">
                        <label for="company">Company: </label>
                        <select class="form-control" ng-model="currentCompany" ng-options="company.value as company.value for company in companies" ng-disabled="parameterLoader.isParametersSaving || isImportingDimension"></select>
                    </div>
                </div>

                <div class="btn btn-default btn-sm pull-right info ng-hide" ng-show="parameterLoader.isParametersSaving && isValid"><i class="fa fa-refresh fa-spin"></i> Saving</div> 
                <div class="btn btn-danger btn-sm pull-right info ng-hide" ng-hide="isValid"><i class="fa fa-exclamation-triangle"></i> Error: Number of characters must be less or equal than 128</div>
                <div class="btn btn-success btn-sm pull-right info ng-hide" ng-show="parameterLoader.isParametersSaved">Saved</div>

                <div class="clear-both"></div>
            </div>

            <div id="container-main" ng-controller="SpreadSheetController" class="bonhamsflatFormApp" ng-show="spreadLoader.isSpreadLoaded">
                <div id="spreadsheet"></div>
            </div>  
        </div>
    </body> 

    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/controllers/contextMenuController.js"></script>
    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuActionService.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/veilController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js"></script>    
    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/capitalize.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/dictionary.js"></script>
    <script type="text/javascript" src="<%=path%>/coreApp/js/modules/dictionary/controllers/dictionaryEditorController.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/providers/dictionaryService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/directives/dictionaryEditor.js"></script>

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/lookupTableApp.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/lookupParametersApp.js"></script>

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/parameters/parameters.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/parameters/controllers/parametersController.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/parameters/providers/parametersService.js"></script>

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/spreadSheet.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/controllers/spreadSheetController.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/drawingService.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/spreadSheetService.js"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/spreadSheetMainMenuService.js"></script>
</html>
