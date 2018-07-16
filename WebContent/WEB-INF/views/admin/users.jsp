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
		                     <th>User</th>
		                     <th>Description</th>
		                     <th>Disabled</th>
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
			<li><a data-toggle="tab" href="#role">Role</a></li>
			<li><a data-toggle="tab" href="#security">Security</a></li>
			<li><a data-toggle="tab" href="#flatForm">Flat Forms</a></li>
			<li><a data-toggle="tab" href="#mobileProfiles"></a></li>
		</ul>
		<div class="tab-content">
			<div id="details" class="tab-pane fade in active">
				details
			</div>
			<div id="role" class="tab-pane fade">
			</div>
			<div id="security" class="tab-pane fade">
			</div>
			<div id="flatForm" class="tab-pane fade">
			</div>
			<div id="mobileProfiles" class="tab-pane fade">
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
<script src="./scripts/adminusers.js"></script>
</body>
</html>