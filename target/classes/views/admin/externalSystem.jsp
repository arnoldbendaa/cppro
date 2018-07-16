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
                <h1 class="text-center">External System</h1>
                <table id="example" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">External System Id</th>
		                     <th>Name</th>		                     
		                     <th>System Type</th>
		                     <th>Description</th>
		                     <th>Location</th>
		                     <th>Enabled</th>
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
				External System
			</h3>
		</div>
		
		<div class="modal-body">
		<form class="external-system-details-form" name="form" novalidate>
			<div class="inner" ng-show="dataLoaded">

				<tabset justified="true">
					<tab heading="Details">
						<div class="row">
							<div class="col-md-4 form-group" ng-class="{'has-error': validation.externalSystemVisId != '' || (form.externalSystemVisId.$invalid && form.externalSystemVisId.$dirty)}">
								<label for="externalSystemVisId">Name:</label>
								<input name="externalSystemVisId" id="externalSystemVisId" ng-model="externalSystem.externalSystemVisId" type="text" class="form-control" ng-maxlength="29" required>
							</div>

							<div class="col-md-8 form-group" ng-class="{'has-error': validation.externalSystemDescription != '' || (form.externalSystemDescription.$invalid && form.externalSystemDescription.$dirty)}">
								<label for="externalSystemDescription">Description:</label>
								<input name="externalSystemDescription" id="externalSystemDescription" ng-model="externalSystem.externalSystemDescription" type="text" class="form-control" ng-maxlength="128" required>
							</div>
						</div>	
						<div class="row">
							<div class="col-md-4 form-group">
								<label>System Type</label>
								<select class="form-control" ng-disabled="true">
									<option>{{externalSystem.systemType}}</option>
								</select>
							</div>

							<div class="col-md-8 form-group" ng-class="{'has-error': validation.connectorClass != '' || (form.connectorClass.$invalid && form.connectorClass.$dirty)}">
								<label for="connectorClass">Connector Class:</label>
								<input name="connectorClass" id="connectorClass" ng-model="externalSystem.connectorClass" type="text" class="form-control" ng-maxlength="512">
							</div>
						</div>	
						<div class="row">	
							<div class="col-md-12 form-group" ng-class="{'has-error': validation.location != '' || (form.location.$invalid && form.location.$dirty)}">
								<label for="location">Location:</label>
								<input name="location" id="location" ng-model="externalSystem.location" type="text" class="form-control" ng-disabled="externalSystem.externalSystemId > -1" ng-maxlength="128" required>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<label class="radio-inline"><input type="radio" name="optradio" id="radioDisabled" value="0">Disabled</label>
								<label class="radio-inline"><input type="radio" name="optradio" id="radioEnabled" value="1" checked="checked">Enabled</label>
							</div>
						</div>	
					</tab>
				</tabset>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button class="btn btn-success" onclick="save()" id="submit">Save</button>
		<button class="btn btn-primary" data-dismiss="modal">Close</button>
	</div>
	</div>
  </div>
</div>
<jsp:include page="adminFooter.jsp"></jsp:include>
<script src="./scripts/externalSystem.js"></script>
</body>
</html>