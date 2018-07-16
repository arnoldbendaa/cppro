<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html ng-app="flatFormTemplateApp" class="flatFormTemplateApp">
    <head>
        <title>Flat Forms Template Manager</title>
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
    <link rel="stylesheet" type="text/css" href="<%=path %>/libs/wijmo/SpreadJS/11.0.0/css/gc.spread.sheets.excel2013white.11.0.0.css">
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/sterling/jquery-wijmo.css">
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/gc.spread.sheets.all.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/interop/angularjs/gc.spread.sheets.angularjs.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/plugins/gc.spread.sheets.charts.11.0.0.min.js"></script>

        <!-- Spectrum -->
        <link rel="stylesheet" href="<%= path %>/libs/spectrum/spectrum-bootstrap.css">
        <script type="text/javascript" src="<%= path %>/libs/spectrum/spectrum.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/angular-spectrum-colorpicker-master/angular-spectrum-colorpicker.js"></script>

        <!-- SoftproIdeas style -->
        <link rel="stylesheet" href="<%= path %>/css/commons.css">
        <link rel="stylesheet" href="<%= path %>/css/spreadsheet.css?">
        <link rel="stylesheet" href="<%= path %>/css/spreadsheetMainMenu.css">
        <link rel="stylesheet" href="<%= path %>/flatformtemplateApp/css/main.css?">

         <!-- jsTree -->
        <link rel="stylesheet" href="<%= path %>/libs/jsTree/style.min.css" />
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jstree.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jsTree.directive.js"></script>
        
        <!-- select2  -->
        <link rel="stylesheet" href="<%= path %>/libs/select2/select.min.css" />
        <script type="text/javascript" src="<%= path %>/libs/select2/select.min.js"></script>
        
        <!-- Angular Loading Bar -->
        <link rel='stylesheet' href='<%= path %>/libs/loadingBar/loading-bar.css' type='text/css' media='all' />
		<script type='text/javascript' src='<%= path %>/libs/loadingBar/loading-bar.js'></script>

        <!-- Project basePath, global variable form java to js -->
        <script type="text/javascript">
            var $BASE_PATH = '<%=basePath%>';
            var $BASE_TEMPLATE_PATH = '<%=basePath%>' + 'flatformtemplateApp/js/';
        </script>
    </head>
    <body class="bonhamsflatFormTemplateApp">
        <veil></veil>
        <nav class="navbar" ng-controller="MenuController">
            <div class="frontend-cover menuCover">
            </div>                
        	<div flash-message="3000"></div>
            <div class="logo">
                <a href="#templates/"><img src="<%= path %>/images/bonhams.png" alt="logo"/></a>
            </div>
            <ul>
                <li>
                    <a class="item-label" href="#templates/" active-link="active"><span class="fa fa-file"></span> Templates</a>
                </li>
                <li>
                    <a class="item-label" href="#configurations/" active-link="active"><span class="fa fa-cog"></span> Configurations</a>
                </li>           
                <li>
                    <a class="item-label" href="#/" active-link="active"><span class="fa fa-play"></span> Generate</a>
                </li>
            </ul>
            <div class="timer">Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a></div>
        </nav>

        <div id="wrapper" class="container-fluid gripped">
            <div class="page" ng-view></div>
        </div>
    </body>

    <!-- DEVELOPER -->
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/flatFormTemplateApp.js?"></script>

     <!-- Core -->
    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/controllers/contextMenuController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/directives/contextMenu.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuActionService.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/veilController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/financeCubeChooserController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js?"></script>    
    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/financeCubeChooser.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/treesChooser.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/selectUsers.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/editor.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/undoRedoController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/clipboardController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/formatController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/formatCellEditorController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/outlineController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/viewPortController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/colorController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/fontController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/sheetEditorController.js?"></script>     
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/tableBuilderController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/alignmentController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/cellsController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/insertController.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/sheetEditor.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/insertRowsColumns.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/formatCellEditor.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/insertSparkline.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/formatService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/colorService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/viewPortService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/insertService.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/spreadSheet.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/controllers/zoomController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/controllers/statusBarController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/providers/zoomService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/directives/zoomStatus.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/main/main.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/editor/editor.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/editor/controllers/sheetEditorController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/editor/controllers/headingGridlineController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/editor/providers/headingGridlineService.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/commons/commons.js?"></script>
<!--     <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/commons/providers/contextMenuActionService.js?"></script> -->
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/commons/providers/contextMenuActionsSetupService.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/commons/providers/flatFormTemplateCommonsService.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/commons/providers/dataService.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/menu/menu.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/menu/controllers/menuController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/menu/animations/menuAnimation.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/menu/directives/activeLinkDirective.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/spreadSheet/spreadSheet.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/spreadSheet/controllers/spreadSheetController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/spreadSheet/controllers/tabController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/spreadSheet/controllers/spreadSheetMenuController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/spreadSheet/providers/drawingService.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/spreadSheet/providers/spreadSheetService.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/spreadSheet/providers/spreadSheetMainMenuService.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/settings/settings.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/settings/controllers/settingsController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/settings/directives/manageUsers.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/settings/directives/workbookProperties.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/settings/directives/worksheetProperties.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/templates/templates.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/templates/controllers/templatesController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/templates/directives/saveTemplate.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/templates/providers/templatesService.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/configurations/configurations.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/configurations/controllers/configurationsController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/configurations/providers/configurationsService.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/configurations/directives/configurationsTree.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/configurations/directives/configurationsExcludeDimensions.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/configurations/directives/configurationsAddTotal.js?"></script>

    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/generate/generate.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/generate/controllers/generateController.js?"></script>
    <script type="text/javascript" src="<%= path %>/flatformtemplateApp/js/modules/generate/providers/generateService.js?"></script>    
    <script type="text/javascript">
		GC.Spread.Sheets.LicenseKey = "localhost,438998912661945#A03V2csFmZ0IiczRmI1pjIs9WQisnOiQkIsISP3c6SLdWcwYmNBJnV0hFVwdjQa3ENttyRxNWR4RFdLlmY4F7NtxWcxIkQtZnSvRlWXBlM6F7aNJjUr9WMBdUMCFVOrRDVwR7QFdzS5MlUHZVV5dkQwd6TiojITJCL8YzM4cTN6gTO0IicfJye#4Xfd5nIzImNnJiOiMkIsISMx8idgMlSgQWYlJHcTJiOi8kI1tlOiQmcQJCLikDM4QzMwASNyYDM8EDMyIiOiQncDJCLiQ7cvhGbhN6bsJiOiMXbEJCLiQGdMByM9cTMgMXbhhmbvJkI0ISYONkIsISN4kTM6YjMxkDO9kDOzQjI0ICZcJjL";
    </script>
	
    
</html>
