    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:include page="adminHeader.jsp"></jsp:include>
<link rel="stylesheet" href="./css/accountDimension.css"/>
<div id="page-container" class="sidebar-l sidebar-o side-scroll header-navbar-fixed">
<jsp:include page="adminSidebar.jsp"></jsp:include>
    <header id="header-navbar" class="content-mini content-mini-full bg-primary">
        <div id="submenu" class="row" ng-controller="SubMenuController">
            <div class="col-md-12">
                <div class="timer text-right">Session timeout: 119 min | <a class="logout color-white " href="${base_url}logout">logout</a> | <a class="logout color-white" href="${base_url}home">To Home</a></div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-md" ng-click="refresh()" ng-disabled="isRefreshDisabled" title="Refresh Table">
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-md" data-toggle="modal" onclick="create()">
                        <i class="fa fa-file-o"></i> Create
                    </button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-md" onclick="editDimension();">
                        <i class="fa fa-folder-open-o"></i> Open
                    </button>
                </div>
                <div class="btn-group" ng-show="actions.length > 0">
                    <button type="button" class="btn btn-default btn-md" ng-disabled="isActionDisabled">Actions</button>
                    <button type="button" class="btn btn-default btn-md dropdown-toggle" ng-disabled="isActionDisabled" data-toggle="dropdown">
                        <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li role="presentation" ng-repeat="action in actions" ng-class="{divider: action.isDivider == true, disabled: action.disabled}">
                            <a role="menuitem" tabindex="-1" ng-click="action.disabled != true && doAction($index)" title="{{action.name}}">{{action.name}}</a>
                        </li>
                    </ul>
                </div>
                <div class="filter-by-model pull-right">
                    <select class="form-control" id="activeModel"
                            ng-change="selectCurrentModel(currentModel)"
                            ng-model="currentModel"
                            ng-options="(model.modelVisId + ' &mdash; ' + model.financeCubeVisId + ' &mdash; ' + model.description) for model in models.sourceCollection">
                        <option value="">Model &mdash;&mdash; Finance Cube &mdash;&mdash; Description</option>
                    </select>
                </div>
                <div class="input-group filter-by-word pull-right" style="width:20%;margin-right:7px;">
                    <input type="text" class="form-control" placeholder="filter by word..." ng-model="filter.byWord" ng-change="onFilterWordChange(filter.byWord)"/>
                    <span class="input-group-addon clear-filter" ng-click="filter.byWord='';onFilterWordChange(filter.byWord)"><span class="glyphicon glyphicon-remove"></span></span>
                </div>

            </div>
        </div>
    </header>
    <main id="main-container">
        <div class="content bg-gray-lighter" style="background-color:#c7c7c7;padding:50px; ">
            <div class="row items-push" style="padding:20px;background-color:#f0f0f0;">
                <h1 class="text-center">Budget Cycles</h1>
                <table id="example" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">#</th>
		                     <th>Model ID</th>
		                     <th>Budget Cycle</th>
		                     <th>Description</th>
		                     <th>Status</th>
		                     <th>Period From</th>
		                     <th>Period To</th>
		                     <th>Category</th>
		                     <th>Actions</th>		                     
	                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
<div id="openDialog" class="modal fade" role="dialog">
  <div class="modal-dialog" style="width:800px">
    <!-- Modal content-->
    <div class="modal-content">
		<div class="modal-header">
			<h3><i class="fa fa-refresh"></i> 
				Budget Cycle
			</h3>
		</div>
		<div class="modal-body useInModalHeightCalc">
			<div class="loader align-center" ng-hide="isMainDataLoaded">
				<i class="fa fa-refresh fa-2x fa-spin"></i>
			</div>
			<div class="inner" ng-show="isMainDataLoaded">

				<fieldset class="getToCalc2">
					<legend>Details</legend>
					<div class="row">
						<div class="col-md-2">	
							<div class="form-group">
								<label class="control-label" for="modelId">Model:</label>
								<input disabled="true" id="modelVisId" type="text" class="form-control">
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group" ng-class="{'has-error': validation.budgetCycleVisId != '' || (form.budgetCycleVisId.$invalid && form.budgetCycleVisId.$dirty)}">
								<label class="control-label" for="budgetCycleVisId">Identifier:</label>
								<input name="budgetCycleVisId" id="budgetCycleVisId" type="text" class="form-control" ng-maxlength="20" required/>
							</div>
						</div>
						<div class="col-md-5">
							<div class="form-group" ng-class="{'has-error': validation.budgetCycleDescription != '' || (form.budgetCycleDescription.$invalid && form.budgetCycleDescription.$dirty)}">
								<label class="control-label" for="budgetCycleDescription">Description:</label>
								<input name="budgetCycleDescription" id="budgetCycleDescription" type="text" class="form-control" ng-maxlength="128"/>
							</div>
						</div>
						<div class="col-md-2">
							<label class="control-label" for="category">Category:</label>
							<select class="form-control"
								ng-disabled="disableBudgetStatus(budgetCycle.status)"
								onchange="onBudgetStatusChange()"
								id="category"
								ng-options="category.key as category.name for category in categories">
								<option value=""></option>
								<option value="M">Management Accounts</option>
								<option value="F">Forecast</option>
								<option value="B">Budget</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-2">
							<div class="form-group">
								<label class="control-label" for="modelId">Status:</label>
								<select class="form-control"
									ng-disabled="disableBudgetStatus(budgetCycle.status)"
									onchange="onBudgetStatusChange()"
									id="status"
									ng-options="status.statusId as status.label for status in statuses">
									
								</select>
							</div>
						</div>				
						<div class="col-md-3">
							<div class="form-group" ng-class="{'has-error': validation.periodFrom != '' || validation.periods != '' }">
								<label class="control-label" for="periodFrom">Period (From):</label>
								<calendar-period
										model-id="budgetCycle.model.modelId"
										period-id="budgetCycle.periodFromId"
										period-vis-id="budgetCycle.periodFromVisId"
										max-period-id="budgetCycle.periodToId"
										on-period-change="onPeriodChange(id, visId)">
										<div class="form-group" ng-click="changePeriod('from');">
											<input class="form-control" value="{{budgetCycle.periodFromVisId}}" type="text">
										</div>
								</calendar-period>
							</div>
						</div>	
						<div class="col-md-3">	
							<div class="form-group" ng-class="{'has-error': validation.periodTo != '' || validation.periods != '' }">
								<label class="control-label" for="modelId">Period (To):</label>
								<calendar-period
										model-id="budgetCycle.model.modelId"
										period-id="budgetCycle.periodToId"
										period-vis-id="budgetCycle.periodToVisId"
										min-period-id="budgetCycle.periodFromId"
										on-period-change="onPeriodChange(id, visId)">
										<div class="form-group" ng-click="changePeriod('to');">
											<input class="form-control" value="{{budgetCycle.periodToVisId}}" type="text">
										</div>
								</calendar-period>
							</div>
						</div>	
						<div class="col-md-4">
							<div class="form-group datepicker" ng-class="{'has-error': validation.plannedEndDate != ''}">
								<label class="control-label" for="plannedEndDate">Planned:</label>
								<budget-cycle-planned-end-date model-id="budgetCycle.model.modelId" planned-end-date="budgetCycle.plannedEndDate" level-dates="budgetCycle.levelDates"></budget-cycle-planned-end-date>
							</div>
							<button ng-hide="true" class="btn btn-info btn-xs pull-right" ng-click="">Levels</button>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend class="getToCalc3">XML Forms</legend>
					<div ng-class="{'has-error': validation.xmlForms != ''}">
						<budget-cycle-xml-forms 
							model-id="budgetCycle.model.modelId"
							xml-forms="budgetCycle.xmlForms" 
							default-xml-form-id="budgetCycle.defaultXmlFormId"
							default-xml-form-data-type="budgetCycle.defaultXmlFormDataType">
						</budget-cycle-xml-forms>
					</div>
				</fieldset>

				<fieldset class="getToCalc4">
					<legend>Actuals</legend>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group datepicker">
								<label class="control-label" for="name">Start Date:</label>
								<input class="form-control" type="text" value="{{budgetCycle.startDate | date:'dd/MMM/yy'}}" disabled="true" />
							</div>
						</div>	
						<div class="col-md-6">
							<div class="form-group datepicker">
								<label class="control-label" for="name">End Date:</label>
								<input class="form-control" type="text" value="{{budgetCycle.endDate | date:'dd/MMM/yy'}}" disabled="true" />
							</div>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-success" ng-disabled="form.$invalid" ng-click="save()">Save</button>
			<button class="btn btn-primary" ng-click="close()" type="reset">Close</button>
		</div>
	</div>
  </div>
</div>
<jsp:include page="adminFooter.jsp"></jsp:include>
<script src="./scripts/budgetCycle.js"></script>
</body>
</html>