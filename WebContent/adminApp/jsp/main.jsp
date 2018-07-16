<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html data-ng-app="adminApp">
<head>
<title>Admin Panel</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<link rel="stylesheet"
	href="<%=path%>/adminApp/css/reset.css"">

<!-- jQuery -->
<script type="text/javascript"
	src="<%=path%>/libs/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/jquery/jquery.fileDownload.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/jquery-ui/jquery-ui.min.js"></script>
<link rel="stylesheet" href="<%=path%>/libs/jquery-ui/jquery-ui.css">
<script type="text/javascript"
	src="<%=path%>/libs/jquery/jquery.ba-outside-events.min.js"></script>

<!-- Angular -->
<script type="text/javascript"
	src="<%=path%>/libs/ng-file-upload/ng-file-upload-shim.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/angularjs/1.2.25/angular.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/angularjs/1.2.25/angular-route.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/angularjs/1.2.25/angular-animate.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/angularjs/1.2.25/angular-sanitize.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/angularjs/1.2.25/angular-cookies.min.js"></script>

<script type="text/javascript"
	src="<%=path%>/libs/angularjs/ui-bootstrap/ui-bootstrap-tpls-0.11.2.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/angularjs/angular-flash.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/ng-file-upload/ng-file-upload.min.js"></script>

<!-- Sweet Alert -->
<script type="text/javascript"
	src="<%=path%>/libs/sweetalert/sweet-alert.min.js"></script>
<link href="<%=path%>/libs/sweetalert/sweet-alert.css" type="text/css"
	rel="stylesheet">

<!-- Bootstrap -->
<link rel="stylesheet"
	href="<%=path%>/libs/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=path%>/libs/font-awesome/4.2.0/css/font-awesome.min.css">
<script type="text/javascript"
	src="<%=path%>/libs/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<!-- TweenMax -->
<script type="text/javascript"
	src="<%=path%>/libs/greensock/src/minified/TweenMax.min.js"></script>

<!-- jsTree -->
<link rel="stylesheet" href="<%=path%>/libs/jsTree/style.min.css" />
<script type="text/javascript" src="<%=path%>/libs/jsTree/jstree.js"></script>
<script type="text/javascript"
	src="<%=path%>/libs/jsTree/jsTree.directive.js"></script>

<!-- Wijmo -->
<script
	src="<%=path%>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.min.js"
	type="text/javascript"></script>
<script
	src="<%=path%>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.input.min.js"
	type="text/javascript"></script>
<script
	src="<%=path%>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.grid.min.js"
	type="text/javascript"></script>
<script
	src="<%=path%>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.chart.min.js"
	type="text/javascript"></script>
<link
	href="<%=path%>/libs/wijmo/Wijmo5/5.20143.32/styles/wijmo.min.css"
	rel="stylesheet" />


<!-- Wijmo-Angular interop -->
<script
	src="<%=path%>/libs/wijmo/Wijmo5/5.20143.32/interop/angular/wijmo.angular.min.js"
	type="text/javascript"></script>

<!-- Angular Loading Bar -->
<link rel='stylesheet'
	href='<%=path%>/libs/loadingBar/loading-bar.css' type='text/css'
	media='all' />
<script type='text/javascript'
	src='<%=path%>/libs/loadingBar/loading-bar.js'></script>

<!-- SoftproIdeas style -->
<link rel="stylesheet"
	href="<%=path%>/css/commons.css"">
<link rel="stylesheet"
	href="<%=path%>/adminApp/css/main.css"">
<!-- <link rel="stylesheet" href="<%=path%>/adminApp/css/animation.css""> -->


<!-- Project basePath, global variable form java to js -->
<script type="text/javascript">
        	var $USER_ID = '${userId}';
            var $BASE_PATH = '<%=basePath%>';
            var $BASE_APP_PATH = '<%=path%>' + '/adminPanel';
            var $BASE_TEMPLATE_PATH = '<%=basePath%>' + 'adminApp/js/modules/';
            var $ROLES = [<c:forEach items="${roles}" var="role">"${role}",</c:forEach>""];
        </script>

</head>
<body class="bonhamsAdminApp">
	<div class="mc-messages">
		<div mc-messages></div>
	</div>
	<div flash-message="3000"></div>

	<div id="wrapper" class="container-fluid gripped"
		ng-controller="MainController">
		<%@include file="templates/navigation.jsp"%>
		<%@include file="templates/submenu.jsp"%>

		<div id="main-content" class="row">
			<context-menu on-show="onShowHandler(event)"></context-menu>
			<div class="page" ng-view></div>
		</div>
	</div>

</body>

<!-- DEVELOPER -->
<script type="text/javascript"
	src="<%=path%>/adminApp/js/app.js"></script>

<!-- Core -->
<script type="text/javascript"
	src="<%=path%>/coreApp/js/app.js"></script>

<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/commons/commons.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/commons/providers/sessionService.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/commons/providers/coreCommonsService.js"></script>

<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/components.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/config.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/run.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/providers/taskStatusService.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/controllers/flatFormChooserModalController.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/controllers/flatFormChooserController.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/providers/exceptionViewer.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/directives/treesChooser.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/directives/selectUsers.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/directives/flatFormChooser.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/components/directives/flatFormChooserModal.js"></script>

<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/dictionary/dictionary.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/dictionary/controllers/dictionaryEditorController.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/dictionary/providers/dictionaryService.js"></script>
<script type="text/javascript"
	src="<%=path%>/coreApp/js/modules/dictionary/directives/dictionaryEditor.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/main/main.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/main/controllers/mainController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/main/filters/mainFilters.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/main/providers/pageService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/main/providers/filterService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/main/animations/pageAnimation.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/commons/commons.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/commons/providers/contextMenuService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/commons/directives/monthChooser.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/commons/directives/multipleChooser.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/commons/directives/contextMenu.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/menu/menu.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/menu/controllers/menuController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/menu/controllers/submenuController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/menu/animations/menuAnimation.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/menu/directives/activeLinkDirective.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/home/homePage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/home/controllers/homePageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/home/providers/homePageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/models/model.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/models/controllers/modelController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/models/providers/modelService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/models/directives/modelChooser.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/models/directives/modelDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/models/directives/modelImportData.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetCycles/budgetCycle.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetCycles/controllers/budgetCyclesController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetCycles/providers/budgetCycleService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetCycles/directives/budgetCycleDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetCycles/directives/budgetCycleXmlForms.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetCycles/directives/budgetCyclePlannedEndDate.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetCycles/directives/budgetCycleStructureLevelEndDates.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dataEditor/dataEditor.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dataEditor/controllers/dataEditorController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dataEditor/providers/dataEditorService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/dimension.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/providers/dimensionCommonsService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/controllers/accountController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/providers/accountService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/directives/accountDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/controllers/businessController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/providers/businessService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/directives/businessDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/providers/calendarService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/directives/calendarPeriod.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/controllers/calendarController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dimensions/directives/calendarDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/report.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/providers/reportCommonsService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/controllers/internalDestinationController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/providers/internalDestinationService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/directives/internalDestinationDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/controllers/externalDestinationController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/providers/externalDestinationService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/reports/directives/externalDestinationDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/admin/admin.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/admin/controllers/databaseController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/admin/providers/databaseService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/admin/directives/taskDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/admin/controllers/taskGroupController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/admin/providers/taskGroupService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/recalculateBatches/recalculateBatch.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/recalculateBatches/controllers/recalculateBatchController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/recalculateBatches/providers/recalculateBatchService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/recalculateBatches/directives/recalculateBatchDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/notes/notesPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/notes/controllers/notesPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/notes/providers/notesPageService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/notes/directives/notesViewerPage.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/systemProperties/systemPropertiesPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/systemProperties/controllers/systemPropertiesPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/systemProperties/directives/systemPropertiesPageDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/systemProperties/providers/systemPropertiesPageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dataTypes/dataTypesPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dataTypes/controllers/dataTypesPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dataTypes/providers/dataTypesPageService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dataTypes/directives/dataTypesDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/monitors/taskViewerPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/monitors/controllers/taskViewerPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/monitors/directives/taskViewerPageDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/monitors/providers/taskViewerPageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubes/financeCubesPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubes/controllers/financeCubesPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubes/directives/financeCubesPageDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubes/directives/chooserDataTypeModal.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubes/directives/chooserDataType.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubes/providers/financeCubesPageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubeFormula/financeCubeFormulaPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubeFormula/controllers/financeCubeFormulaPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubeFormula/directives/financeCubeFormulaPageDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubeFormula/providers/financeCubeFormulaPageService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/financeCubeFormula/directives/financeCubeChooser.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/roles/rolesPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/roles/controllers/rolesPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/roles/directives/rolesPageDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/roles/providers/rolesPageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/users/usersPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/users/controllers/usersPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/users/directives/usersPageDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/users/directives/userRoleChooser.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/users/directives/profilesForUsers.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/users/providers/usersPageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/loggedUsers/loggedUsersPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/loggedUsers/controllers/loggedUsersPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/loggedUsers/providers/loggedUsersPageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/loggedHistory/loggedHistoryPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/loggedHistory/controllers/loggedHistoryPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/loggedHistory/providers/loggedHistoryPageService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/flatforms/flatFormsPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/flatforms/controllers/flatFormsPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/flatforms/providers/flatFormsPageService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/flatforms/directives/flatFormsExportPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/flatforms/directives/flatFormsImportPage.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/profiles/profilesPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/profiles/controllers/mobileProfilesPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/profiles/controllers/webProfilesPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/profiles/providers/profilesPageService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/profiles/directives/profilesDeployment.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/profiles/directives/profilesUnDeployment.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetInstructions/budgetInstructionsPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetInstructions/controllers/budgetInstructionsPageController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetInstructions/providers/budgetInstructionsPageService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/budgetInstructions/directives/budgetInstructionsPageDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/externalSystems/externalSystem.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/externalSystems/controllers/externalSystemController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/externalSystems/directives/externalSystemDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/externalSystems/directives/subSystemConfiguration.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/externalSystems/providers/externalSystemService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelMappings/modelMappings.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelMappings/controllers/modelMappingsController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelMappings/providers/modelMappingsService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelMappings/directives/modelMappingsDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelMappings/directives/mappedFinanceCubeChooser.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/authentications/authentication.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/authentications/controllers/authenticationController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/authentications/providers/authenticationService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/authentications/directives/authenticationDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/hierarchies/hierarchiesPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/hierarchies/controllers/hierarchiesAccountController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/hierarchies/controllers/hierarchiesBusinessController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/hierarchies/providers/hierarchiesService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/hierarchies/directives/hierarchiesDetails.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/hierarchies/directives/dimensionChooser.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/importtasks/import.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/importtasks/controllers/importController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/importtasks/providers/importService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/changeManagement/changeManagement.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/changeManagement/controllers/changeManagementController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/changeManagement/providers/changeManagementService.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelUserSecurity/modelUserSecurity.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelUserSecurity/controllers/modelUserSecurityController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelUserSecurity/providers/modelUserSecurityService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/modelUserSecurity/directives/modelUserSecurityDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/userModelSecurity/userModelSecurity.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/userModelSecurity/controllers/userModelSecurityController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/userModelSecurity/providers/userModelSecurityService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/userModelSecurity/directives/userModelSecurityDetails.js"></script>

<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dashboard/dashboardPage.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dashboard/controllers/hierarchyFormDashboardController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dashboard/controllers/freeFormDashboardController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dashboard/providers/freeFormDashboardService.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/dashboard/providers/hierarchyFormDashboardService.js"></script>
	
		<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/slideShow/slideShow.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/slideShow/controllers/slideShowController.js"></script>
<script type="text/javascript"
	src="<%=path%>/adminApp/js/modules/slideShow/providers/slideShowService.js"></script>
	
</html>