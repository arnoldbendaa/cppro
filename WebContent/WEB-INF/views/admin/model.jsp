    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:include page="adminHeader.jsp"></jsp:include>
<link rel="stylesheet" href="./css/adminModel.css"/>
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
                    <button type="button" class="btn btn-default btn-md" data-toggle="modal" data-target="#openDialog">
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
                <h1 class="text-center">Model</h1>
                <table id="example" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">#</th>
		                     <th>Model ID</th>
		                     <th>Description</th>
		                     <th>Golobal</th>
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
		<h3><i class="fa fa-list"></i>
			Model
		</h3>
	</div>
	<div class="modal-body">
		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#details">Details</a></li>
			<li><a data-toggle="tab" href="#budget">Budget Transfer Control</a></li>
			<li><a data-toggle="tab" href="#properties">Properties</a></li>
		</ul>
		<div class="tab-content">
			<div id="details" class="tab-pane fade in active">
				<tab heading="Details">
					<!-- <h4 class="groupTitle">Details:</h4> -->
					<div class="inner" ng-show="isDataLoaded">
						<fieldset>
							<legend>Details</legend>
							<div class="row">
							  <div class="col-md-6 form-group">
							  	<label for="">Model ID:</label>
							  	<span class="text-danger" ng-show="validation.identifier != ''" class="help-block">{{validation.identifier}}</span>
								<input name="name" ng-model="model.modelVisId" type="text" class="form-control">
							  </div>
							  <div class="col-md-6 form-group">
							  	<label for="">Name:</label>
							  	<span class="text-danger" ng-show="validation.description != ''" class="help-block">{{validation.description}}</span>
								<input name="name" ng-model="model.modelDescription" type="text" class="form-control">
							  </div>
						  	</div>
						  	
						  	<div class="row">
							  <div class="alert alert-danger" role="alert" ng-show="validation.references != ''">{{validation.references}}</div>
							  <div class="col-md-6 form-group">
							  	<label for="">Account Dimension:</label>
								<select class="form-control"
									ng-options="account.dimensionVisId for account in accountList"
									ng-change="changeAccountDimension(choosedAccountDimension)"
									ng-model="choosedAccountDimension">
								</select>
							  </div>
							  <div class="col-md-6 form-group">
							  	<label for="">Calendar Dimension:</label>
							  	<select class="form-control"
									ng-options="calendar.dimensionVisId for calendar in calendarList"
									ng-change="changeCalendarDimension(choosedCalendarDimension)"
									ng-model="choosedCalendarDimension">
								</select>
							  </div>						  
						  	</div>
						  	
						  	<div class="row">
							  <div class="col-md-6 form-group">
							  	<label for="">Business Dimension:</label>
							  	<select class="form-control"
									ng-options="business.dimensionVisId for business in businessList"
									ng-change="changeBusinessDimension(choosedBusinessDimension)"
									ng-model="choosedBusinessDimension">
								</select>
							  </div>
							  <div class="col-md-6 form-group">
							  	<label for="">Budget Hierarchy:</label>
								<select class="form-control"
									ng-options="hierarchy.hierarchyVisId for hierarchy in hierarchyList"
									ng-change="changeHierarchy(choosedHierarchy)"
									ng-model="choosedHierarchy">
								</select>
							  </div>
						  	</div>
							
						</fieldset>

						<fieldset>
							<legend>Status</legend>
						  	<!-- <h4 class="groupTitle">Status:</h4> -->
					  		<div class="checkbox">
								<label class="checkbox">
									<input type="checkbox" ng-model="model.locked" disabled="disabled">
									Locked
								</label>
							</div>
						</fieldset>						
					  	
				  	</div>
			  	</tab>
			</div>
			<div id="budget" class="tab-pane fade">
			  	<tab heading="Budget Transfer Controls">
			  		<fieldset>
			  			<legend>Budget Transfer Entry</legend>
			  			<!-- <h4 class="groupTitle">Budget Transfer Entry:</h4> -->
				  		<div class="checkbox">
							<label class="checkbox">
								<input type="checkbox" ng-model="model.virementEntryEnabled">
								Enabled
							</label>
						</div>
			  		</fieldset>
			  	</tab>
			</div>
			<div id="properties" class="tab-pane fade">
						  	<tab heading="Properties">
			  		<h4 class="groupTitle floated getToCalc1">Model Properties:</h4>
			  		<button class="btn btn-success add-remove-property-button" ng-click="createProperty(selectedProperty.name, selectedProperty.value)" type="reset">Insert</button>
			  		<br class="clearBoth" />
			  		<span class="text-danger" ng-show="validation.properties != ''" class="help-block">{{validation.properties}}</span>
			  		<div class="properties-table">
				  		<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th class="clickable" ng-click="orderBy = ''; reverse=false">#</th>
									<th class="clickable limited" ng-class="{sorted: orderBy == 'name', desc: reverse}" ng-click="orderBy = 'name'; reverse=!reverse">Name</th>
									<th class="clickable limited" ng-class="{sorted: orderBy == 'value', desc: reverse}" ng-click="orderBy = 'value'; reverse=!reverse">Value</th>
									<th class="action-column" style="width: 75px">Actions</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-class="{selected: property === selectedProperty}" 
									ng-repeat="property in model.properties | orderBy:orderBy:reverse" 
									ng-click="changeSelectedProperty(property)">
									<td>{{$index+1}}</td>
									<td class="limited"><div>{{property.name}}</div></td>
									<td class="limited"><div>{{property.value}}</div></td>
									<td class="action-column">
										<button type="button" class="btn btn-danger btn-xs" ng-click="deleteProperty($index); $event.stopPropagation();">Delete</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<br class="clearBoth" />
					<fieldset class="getToCalc2">
						<legend>Model Property details</legend>
						<div class="row">
							<div class="col-md-6 form-group">
								<label for="">Name:</label>
								<input name="name" ng-model="selectedProperty.name" type="text" class="form-control">
							</div>
							<div class="col-md-6 form-group">
								<label for="">Value:</label>
								<textarea rows="2" ng-model="selectedProperty.value" class="form-control">
								</textarea>
							</div>
						</div>
					</fieldset>
		  		</tab>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-success" ng-click="save()" type="reset">Save</button>
			<button class="btn btn-primary" ng-click="close()" type="reset">Close</button>
		</div>
    </div>
  </div>
</div>
<jsp:include page="adminFooter.jsp"></jsp:include>
<script src="./scripts/adminModel.js"></script>
</body>
</html>