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
                <h1 class="text-center">Model Mappings</h1>
                <table id="example" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">#</th>
		                     <th>Name</th>		        
		                     <th>Description</th>
		                     <th>External System</th>
		                     <th>Companies</th>
		                     <th>Ledger</th>
		                     <th>Global</th>
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
			<h3><i class="fa fa-exchange"></i> 
				Mapped Model
			</h3>
		</div>
		<div class="modal-body">
			<div id="exTab1" class="container">	
				<ul  class="nav nav-pills">
					<li class="active"><a href="#1a" data-toggle="tab"></a></li>
					<li><a href="#2a" data-toggle="tab"></a></li>
					<li><a href="#3a" data-toggle="tab"></a></li>
		  			<li><a href="#4a" data-toggle="tab"></a></li>
		  			<li><a href="#5a" data-toggle="tab"></a></li>
		  			<li><a href="#6a" data-toggle="tab"></a></li>
		  			<li><a href="#7a" data-toggle="tab"></a></li>
		  			<li><a href="#8a" data-toggle="tab"></a></li>
				</ul>
				<div class="tab-content clearfix">
					<div class="tab-pane active" id="1a">
						<h4>External System</h4>
						<p class="bg-info getToCalc1-2">Choose the external system you want to interface with Collaborative Planning.</p>
		          		<table id="externalTable" class="display cell-border compact" cellspacing="0" width="100%">
		          			<thead>
		          				<tr>
		          					<th style="border-left:1px solid #e0e0e0;">Name</th>
		          					<th>Description</th>
		          					<th>Location</th>
		          					<th>Enabled</th>
		          					<th>Selection</th>
		          				</tr>
		          			</thead>
		          			<tbody></tbody>
		          		</table>
					</div>
					<div class="tab-pane" id="2a">
						<h4>Company</h4>
						<p class="bg-info getToCalc1-2">Select a company to base the mapping on.</p>
		          		<table id="externalTable" class="display cell-border compact" cellspacing="0" width="100%">
		          			<thead>
		          				<tr>
		          					<th style="border-left:1px solid #e0e0e0;">Name</th>
		          					<th>Selection</th>
		          				</tr>
		          			</thead>
		          			<tbody></tbody>
		          		</table>
					</div>
			        <div class="tab-pane" id="3a">
						<h4>Ledger</h4>
						<p class="bg-info getToCalc1-2">Select a ledger to be used in the mapping.</p>
		          		<table id="externalTable" class="display cell-border compact" cellspacing="0" width="100%">
		          			<thead>
		          				<tr>
		          					<th style="border-left:1px solid #e0e0e0;">Name</th>
		          					<th>Companies</th>
		          					<th>Selection</th>
		          				</tr>
		          			</thead>
		          			<tbody></tbody>
		          		</table>
					</div>
			        <div class="tab-pane" id="4a">
						<h4>Model details</h4>
						<p class="bg-info getToCalc1-2">Input a model name and model description.</p>
						<div class="inner" ng-show="mappedModelLoader.externalModelSuggestedAndDimensionsLoaded">
		                    <div class="row">
		                        <div class="col-md-12 form-group">
		                            <label for="mappedModelVisId">Model:</label>
		                            <input name="mappedModelVisId" id="mappedModelVisId" ng-disabled="mappedModel.mappedModelId !== -1" ng-model="suggestedModel.modelVisId" ng-class="{'has-error': form.mappedModelVisId.$invalid && form.mappedModelVisId.$dirty}" type="text" class="form-control" ng-maxlength="50" required>
		                        </div>
		                    </div>
		                    <div class="row">
		                        <div class="col-md-12 form-group">
		                            <label for="mappedModelDescription">Description:</label>
		                            <input name="mappedModelDescription" id="mappedModelDescription" ng-disabled="mappedModel.mappedModelId !== -1" ng-model="suggestedModel.modelDescription" ng-class="{'has-error': form.mappedModelDescription.$invalid && form.mappedModelDescription.$dirty}" type="text" class="form-control" ng-maxlength="128" required>
		                        </div>
		                    </div>
		                </div>
					</div>
			        <div class="tab-pane" id="5a">
						<h4>Dimensions and Hierarchies</h4>
						<p class="bg-info getToCalc1-2">Select the dimensions and hierarchies to include in the mapping.</p>
					</div>					
				</div>
			</div>
	    </div>
    	<div class="modal-footer">
    		<input type="button" class="btn btn-default" id="btnPrevious" value="Previous" style = "display:none"/>
			<input type="button" class="btn btn-default" id="btnNext" value="Next" />
			<button class="btn btn-success" ng-disabled="form.$invalid" ng-click="save()">Finished</button>
			<button class="btn btn-primary" ng-click="close()" type="reset">Close</button>
		</div>
	</div>
  </div>
</div>
<jsp:include page="adminFooter.jsp"></jsp:include>
<script src="./scripts/modelMapping.js"></script>
</body>
</html>