<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html data-ng-app="app">
    <head>
        <title ng-bind-template="{{title}}">${submitModelName} : ${submitCycleName}</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

        <!-- jQuery -->
        <script type="text/javascript" src="<%=path %>/libs/jquery/1.11.1/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/jquery-ui/jquery-ui.min.js"></script>
        <link rel="stylesheet" href="<%=path %>/libs/jquery-ui/jquery-ui.css">

        <!-- File download -->
        <script type="text/javascript" src="<%= path %>/libs/jquery/jquery.fileDownload.min.js"></script>

        <!-- Angular -->
        <script type="text/javascript" src="<%= path %>/libs/ng-file-upload/ng-file-upload-shim.js"></script>
       <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular.min.js"></script>
       <!-- <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.js"> -->
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-route.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-animate.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-sanitize.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-cookies.min.js"></script>
        
        <script type="text/javascript" src="/cppro/libs/sweetalert/sweet-alert.min.js"></script>
        <link href="/cppro/libs/sweetalert/sweet-alert.css" type="text/css" rel="stylesheet" >

        <script type="text/javascript" src="<%= path %>/libs/angularjs/ui-bootstrap/ui-bootstrap-tpls-0.11.2.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/angular-flash.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/ng-file-upload/ng-file-upload.js"></script>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%= path %>/libs/font-awesome/4.2.0/css/font-awesome.min.css">

        <!-- Wijmo -->
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/styles/wijmo.min.css" />
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.grid.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/interop/angular/wijmo.angular.min.js" type="text/javascript"></script>

		<!-- SpreadJS -->
       	<!-- <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/gcspread.sheets.8.40.20151.4.css">
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/sterling/jquery-wijmo.css">
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/8.40.20151.4/js/gcspread.sheets.all.8.40.20151.4.v1.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/8.40.20151.4/js/interop/angular.gcspread.sheets.8.40.20151.4.min.js"></script>-->

        <link rel="stylesheet" type="text/css" href="<%=path %>/libs/wijmo/SpreadJS/11.0.0/css/gc.spread.sheets.excel2013white.11.0.0.css">
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/sterling/jquery-wijmo.css">
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/gc.spread.sheets.all.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/interop/angularjs/gc.spread.sheets.angularjs.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/plugins/gc.spread.sheets.charts.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/interop/gc.spread.excelio.11.0.0.min.js"></script>
       
    <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2014-11-29/FileSaver.min.js"></script>

        <!-- SoftproIdeas style -->
        <link rel="stylesheet" href="<%= path %>/css/commons.css?">
        <link rel="stylesheet" href="<%= path %>/reviewbudgetApp/css/main.css?">

        <!-- jsTree -->
        <link rel="stylesheet" href="<%= path %>/libs/jsTree/style.min.css" />
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jstree.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jsTree.directive.js"></script>

        <!-- Project basePath, global variable form java to js -->
        <script type="text/javascript">
            var $BASE_PATH = '<%=basePath%>';
            var $BASE_TEMPLATE_PATH = '<%=basePath%>' + 'reviewbudgetApp/js/modules/';
            var showGraph = false;
        </script>
        <style>
        .btn-toolbar>.btn, .btn-toolbar>.btn-group, .btn-toolbar>.input-group{
        	padding:0;
        }
        </style>
		        

    </head>
    <body>
        <div class="timer">
            Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a>
        </div>

        <div flash-message="3000"></div>
        <!-- commonsModule.js -->
        <veil></veil>
        <!-- profileModule.js -->
        <profiles-default-profile-not-found ng-show="profilesDefaultProfileNotFoundVisible"></profiles-default-profile-not-found>
        <profiles-error-not-found ng-show="profilesErrorNotFoundVisible"></profiles-error-not-found>
        <profiles-show-groups ng-show="profilesShowGroupsVisible"></profiles-show-groups>

        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <script type="text/javascript">
            var TOP_NODE_ID = "${topNodeId}",
                MODEL_ID = "${modelId}",
                BUDGET_CYCLE_ID = "${budgetCycleId}",
                USER_ID = "${userId}",
                USER_NAME = "${userName}",
                IS_ADMIN = "${isAdmin}",
                DATA_ENTRY_PROFILE_ID = "${dataEntryProfileId}",
                MODEL_NAME = "${submitModelName}",
                CYCLE_NAME = "${submitCycleName}";
            
            var fullHeightFlag = $(window).height();
            function adjustScreen() {
                var fullHeight = $(window).height();
                var topHeight = $("#container-navbar").height() + 25; // height + padding top + padding bottom
                var breadcrumbHeight = $(".breadcrumb").height() + 16; // height + padding top + padding bottom
        
                //$("#spreadSheet").height((fullHeight - topHeight - breadcrumbHeight - 5)/2);
                if(showGraph)
                	$("#spreadSheet").height((fullHeight - topHeight - breadcrumbHeight - 5)/2);
                else 
                	$("#spreadSheet").height((fullHeight - topHeight - breadcrumbHeight - 5));
                //$("#spreadSheet").height((fullHeight - topHeight - breadcrumbHeight - 5));
               // $("#spreadSheet").wijspread("refresh");
                var spread = GC.Spread.Sheets.findControl(document.getElementById('spreadSheet'));
                if(spread != undefined && spread != null)
                	spread.refresh();
                
            }
        
            $(document).ready(function () {
                adjustScreen();
                $(window).resize(function() {
                    var actualFullHeight = $(window).height();
                    if (fullHeightFlag != actualFullHeight) {
                        fullHeightFlag = actualFullHeight;
                        adjustScreen();
                    }
                });
            });
            function showGraphHandler(cb){
                //console.log(cb.checked);
                showGraph = cb.checked;
                adjustScreen();
            }
        </script>
        
        
        <!-- Static navbar -->
        <div id="container-full" ng-controller="MainMenuController">
            <div id="container-navbar" role="navigation">
                <div class="row">
                    <div class="col-md-12 btn-toolbar" role="toolbar">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default btn-sm" ng-click="openNoteManager()" ng-disabled="isManageNotesDisabled">
                               <i class="fa fa-comments-o"></i> Manage Notes
                            </button>
                            <c:if test="${areButtonsVisible == true}">
                                <button type="button" class="btn btn-default btn-sm" ng-click="openRespAreaManager()">
                                   <i class="fa fa-users"></i> Responsibility Areas
                                </button>
                            </c:if>    
                            <button type="button" class="btn btn-default btn-sm" ng-click="exportFile('xls')" ng-disabled="!(isRecalculateDisabled || !isSpreadWasChanged) || isExportDisabled">
                                <i id="excelExport" class="fa fa-file-excel-o"></i> Export to Excel
                            </button>
                           	<button type="button" class="btn btn-default btn-sm" ng-click="exportPptFile()" ng-disabled="!(isRecalculateDisabled || !isSpreadWasChanged) || isExportDisabled">
								<i id="pptExport" class="fa fa-upload"></i> Export to PPt
							</button>
                            <button type="button" class="btn btn-default btn-sm" ng-click="exportPdfFile()" ng-disabled="!(isRecalculateDisabled || !isSpreadWasChanged) || isExportDisabled">
								<i id="pdfExport" class="fa fa-upload"></i> Export to PDF
							</button>
                            <button type="button" class="btn btn-default btn-sm" ng-click="exportDocFile()" ng-disabled="!(isRecalculateDisabled || !isSpreadWasChanged) || isExportDisabled">
								<i id="docExport" class="fa fa-upload"></i> Export to Doc
							</button>
                            <button type="button" class="btn btn-default btn-sm" ng-click="search()" ng-disabled="!(isRecalculateDisabled || !isSpreadWasChanged) || isExportDisabled">
								<i id="docExport" class="fa fa-search"></i> search
							</button>
                        </div>
                        <div class="btn-group"><input type="text" ng-model="searchKey" name="search" style="color:black;width:100px;" class="btn btn-sm"/> </div>
                        <div class="btn-group"> 
                            <button type="button" class="btn btn-default btn-sm" ng-click="showHideHeadings()">
                                <i class="fa fa-table"></i> {{headingsStatus}}
                            </button>
                        </div>
                        <c:if test="${areButtonsVisible == true}">
                            <div class="btn-group pull-right">
                                 <button type="button" class="btn btn-default btn-sm" ng-click="openProfileManager()">
                                    <i class="fa fa-list-ul"></i> Manage Profiles
                                 </button>
                            </div>
                        </c:if>
                        	<div class="btn-group">
								<select class="btn btn-default btn-sm" id="chartType">
									<option value="line">Line Chart</option>
									<option value="column">Column Chart</option>
									<option value="area">Area Chart</option>
									<option value="spline">Spline Chart</option>
									<option value="pie">Pie Chart</option>
								</select>
							</div>
							<div class="btn-group">
								<select class="btn btn-default btn-sm" id="dimention">
									<option value="2d">2D Graph</option>
									<option value="3d">3D Graph</option>
								</select>
							</div>
							<div class="btn-group">
								<lable class="checkbox" style="margin-top:5px;margin-left:27px">
									<input type="checkbox" onclick="showGraphHandler(this)" /> Show Graph
								</lable>
							</div>
                        
                        <div class="dropdown pull-right" ng-controller="ProfilesController">
                            <select class="form-control" id="activeProfieSelectBox" 
                                ng-change="changeProfile()"
                                ng-model="selectedProfile"
                                ng-options="(profile.name + '  -  ' + profile.description) for profile in profiles">
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        
            <div ng-controller="SpreadSheetController" id="container-main">
                <div class="row">
                    <div class="col-md-12" ng-controller="DimensionController">
                        <ul class="breadcrumb" style="margin-bottom: 5px;">
                            <li>{{selectedProfile.description}}</li>
                            <li ng-repeat="dimension in selectedDimensions track by $index" ng-click="showDimension($index)">
                                <a href="#" ng-if="dimension.description == ''">
                                    {{dimension.name}}
                                </a>
                                <a href="#" ng-if="dimension.description != ''">
                                    {{dimension.name}} - {{dimension.description}}
                                </a>
                            </li>
                            <li ng-click="showDataType()"><a href="#">{{selectedDataType}}</a></li>
                            <li style ="float: right" >Sum: {{sum}}</li>
                        </ul>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div id="spreadSheet" gcuielement="gcSpread" class="gc-host-none-user-select"></div>
                        
                        <!-- context menu -->
                        <context-menu></context-menu>
        
                        <!-- cell note-->
                        <cell-note></cell-note>
                        <div ng-controller="NotesController"></div>
        
                        <!-- finance cell system data -->
                        <div ng-controller="FinanceSystemCellController"></div>
        
                        <!-- cell status -->
                        <div ng-controller="CellStatusController"></div>
        
                        <!-- responsibility assignments -->
                        <div ng-controller="ResponsibilityAssignmentsController"></div>
        
                        <!-- export file -->
                        <div ng-controller="ExportFileController"></div>
                    </div>
                    <div class="col-md-12">				
                    	<div id="container"	style="min-width: 310px; height: 50%; margin: 0 auto"></div>
                    </div>
                </div>
            </div>
        </div>

    </body>
    <!-- PRODUCTION 
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/app.20140917.min.js"></script>
    <!-- -->

    <!-- DEVELOPER -->
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/app.js?"></script>
    
    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/veilController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js"></script>
    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validation.app.js?"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationDisplayDirective.js?"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationService.js?"></script>    

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/cells/cells.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/cells/controllers/cellStatusController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/cells/controllers/openViewController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/cells/controllers/showCellStatusController.js?"></script>           
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/cells/providers/cellStatusService.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/cells/providers/cellService.js?"></script>

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/commons/commons.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/commons/providers/dateService.js?"></script>

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/dimensions.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/controllers/dimensionController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/controllers/accountTreeViewModalController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/controllers/bussinessTreeViewModalController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/controllers/calendarTreeViewModalController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/controllers/dataTypeTreeViewModalController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/directives/dimension-tree-view.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/dimensions/providers/dimensionFactory.js?"></script>

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/export/export.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/export/controllers/exportFileController.js?"></script>   
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/export/providers/exportFileService.js?"></script>

    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/financeSystems/financeSystems.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/financeSystems/controllers/financeSystemCellController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/financeSystems/controllers/showFinanceSystemCellViewController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/financeSystems/providers/financeSystemCellService.js?"></script>

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/menus/menus.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/menus/controllers/mainMenuController.js?"></script>

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/notes.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/controllers/notesController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/controllers/cellNotesController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/controllers/createNoteController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/controllers/deleteNoteController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/controllers/editNoteController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/controllers/showNotesController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/directives/cell-note.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/notes/providers/notesService.js?"></script>

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/profiles.js?"></script>   
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/controllers/profilesController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/controllers/profileEditorController.js?"></script>   
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/directives/ng-uniq.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/directives/profile-editor.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/directives/profiles-error-not-found.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/directives/profiles-default-profile-not-found.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/directives/profiles-show-groups.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/providers/formListService.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/profiles/providers/profileService.js?"></script>

    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/responsibilityAssignments/responsibilityAssignments.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/responsibilityAssignments/controllers/responsibilityAssignmentsController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/responsibilityAssignments/providers/responsibilityAssignmentsService.js?"></script>
 
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/spreadSheet.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/controllers/spreadSheetController.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/controllers/contextMenuController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/directives/context-menu.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/providers/spreadsheetService.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/providers/financeFormService.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/providers/excelFormService.js?"></script>
    <script type="text/javascript" src="<%= path %>/reviewbudgetApp/js/modules/spreadSheet/providers/contextVariablesService.js?"></script>
    		<script src="../scripts/highcharts.js"></script>
		<script src="../scripts/highcharts-3d.js"></script>
    <script type="text/javascript" src="../scripts/html2canvas.js"></script>
	<script type="text/javascript">
		GC.Spread.Sheets.LicenseKey = "localhost,438998912661945#A03V2csFmZ0IiczRmI1pjIs9WQisnOiQkIsISP3c6SLdWcwYmNBJnV0hFVwdjQa3ENttyRxNWR4RFdLlmY4F7NtxWcxIkQtZnSvRlWXBlM6F7aNJjUr9WMBdUMCFVOrRDVwR7QFdzS5MlUHZVV5dkQwd6TiojITJCL8YzM4cTN6gTO0IicfJye#4Xfd5nIzImNnJiOiMkIsISMx8idgMlSgQWYlJHcTJiOi8kI1tlOiQmcQJCLikDM4QzMwASNyYDM8EDMyIiOiQncDJCLiQ7cvhGbhN6bsJiOiMXbEJCLiQGdMByM9cTMgMXbhhmbvJkI0ISYONkIsISN4kTM6YjMxkDO9kDOzQjI0ICZcJjL";
    </script>
	
    <!-- -->
</html>