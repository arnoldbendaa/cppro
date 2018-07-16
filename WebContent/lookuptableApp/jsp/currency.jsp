<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html ng-app="lookupCurrencyApp" class="lookupCurrencyApp">
    <head>
        <title>Currency - Lookup Table</title>
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
        <link rel="stylesheet" href="<%= path %>/lookuptableApp/css/main.css?">

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
	<body class="bonhamsLookupCurrencyApp">
        <div class="container-full" ng-controller="CurrencyController">
        <div class="timer">
            Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a>
        </div>
            <div id="container-navbar" role="navigation">
                <span class="btn btn-default btn-sm file-input btn-file">
                    <i class="fa fa-file-excel-o"></i> Import from Excel
                    <input type="file" style="height:34px;" ngf-select ngf-change="upload($files)" accept=".xls,.xlsx" />
                </span>

                <div class="options-navbar btn-group">
                    <button class="btn btn-default btn-sm " ng-click="addCurrency()" ng-disabled="currencyLoader.isCurrenciesSaving">Currencies</button>
                    <button class="btn btn-default btn-sm pull-right" ng-click="manageCompany()" ng-disabled="parameterLoader.isParametersSaving">Companies</button>
                </div>

                <div class="options-navbar form-inline pull-right">
                    <div class="form-group">
                        <label for="company">Company: </label>
                        <select class="form-control" ng-model="currentCompany" ng-change="onCompanyChange()" ng-options="company as company.value for company in companies" ng-disabled="currencyLoader.isCurrenciesSaving"></select>
                    </div>
                </div>

                <div class="options-navbar pull-right">
                    <div class="label-navbar">
                        <label for="currency">Base value: </label>
                        <span id="currency" ng-bind="currencyBase"></span>
                    </div>
                </div>

				<div class="options-navbar pull-right">
				    <div class="pull-right" ng-class="{true: 'has-error has-feedback',false: 'is-required'}[!isValidYear]">
                        <div class="chooser">
		                    <input id="year" class="form-control" type="text" ng-model="currentYear" ng-pattern="onlyNumbers" ng-disabled="currencyLoader.isCurrenciesSaving"/>
		                    <div class="arrows">
		                      	<div class="arrow arrow-up" ng-click="increaseYear($event)">
		                           	<i class="fa fa-caret-up"></i>
		                        </div>
		                        <div class="arrow arrow-down" ng-click="decreaseYear($event)">
		                          	<i class="fa fa-caret-down"></i>
		                        </div>
		                    </div>
		                </div>
	                </div>
                    <div class="label-navbar pull-right">
                        <label for="year">Year:</label>
                    </div>
	            </div>

                <div class="options-navbar pull-right">
                        <div class="chooser pull-right">
                            <input id="precision" class="form-control" type="text" ng-model="currentPrecision" ng-pattern="onlyNumbers" ng-disabled="currencyLoader.isCurrenciesSaving"/>
                            <div class="arrows">
                                <div class="arrow arrow-up" ng-click="increasePrecision($event)">
                                    <i class="fa fa-caret-up"></i>
                                </div>
                                <div class="arrow arrow-down" ng-click="decreasePrecision($event)">
                                    <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                        </div>
                        <div class="label-navbar pull-right">
                            <label for="precision">Precision:</label>
                        </div>
                </div>

                <div class="btn btn-default btn-sm pull-right info ng-hide" ng-show="currencyLoader.isCurrenciesSaving && isValid"><i class="fa fa-refresh fa-spin"></i> Saving</div>
                <div class="btn btn-danger btn-sm pull-right info ng-hide" ng-hide="isValid"><i class="fa fa-exclamation-triangle"></i> Error</div>
                <div class="btn btn-success btn-sm pull-right info ng-hide" ng-show="currencyLoader.isCurrenciesSaved">Saved</div>

                <div class="clear-both"></div>                   
            </div> <!-- end container-navbar -->

            <div id="container-main" ng-controller="SpreadSheetController" class="bonhamsflatFormApp" ng-show="spreadLoader.isSpreadLoaded" >
                <div id="spreadsheet"></div>
            </div> <!-- end container-main -->
        </div> <!-- end container-full -->
    </body> 

    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuActionService.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/veilController.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js?"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/capitalize.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js?"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/dictionary.js?"></script>
    <script type="text/javascript" src="<%=path%>/coreApp/js/modules/dictionary/controllers/dictionaryEditorController.js?"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/providers/dictionaryService.js?"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/dictionary/directives/dictionaryEditor.js?"></script>

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/lookupTableApp.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/lookupCurrencyApp.js?"></script>

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/currency/currency.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/currency/controllers/currencyController.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/currency/providers/currencyService.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/currency/providers/periodService.js?"></script>

    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/spreadSheet.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/controllers/spreadSheetController.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/drawingService.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/spreadSheetService.js?"></script>
    <script type="text/javascript" src="<%= path %>/lookuptableApp/js/modules/spreadSheet/providers/spreadSheetMainMenuService.js?"></script>
</html>
