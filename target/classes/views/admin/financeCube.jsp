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
                <div class="timer text-right">Session timeout: 119 min | <a class="logout color-white "  href="${base_url}logout">logout</a> | <a class="logout color-white" href="${base_url}home">To Home</a></div>
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
                <h1 class="text-center">Finance Cubes</h1>
                <table id="example" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">#</th>
		                     <th>Model ID</th>
		                     <th>Finance Cube ID</th>
		                     <th>Description</th>
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
			<h3><i class="fa fa-cubes"></i>
				Finance Cube
			</h3>
		</div>
		<div class="modal-body useInModalHeightCalc">
			<div class="inner" ng-show="isDataLoaded">
				<div class="row getToCalc1">
					<div class="col-md-3 form-group" ng-class="{true: 'has-error'}[validation.identifier != '']">
						<label for="">Identifier:</label>
						<input name="name" ng-model="financeCube.financeCubeVisId" type="text" class="form-control">
					</div>
		
					<div class="col-md-5 form-group" ng-class="{true: 'has-error'}[validation.description != '']">
						<label for="">Description:</label>
						<input name="name" ng-model="financeCube.financeCubeDescription" type="text" class="form-control">
					</div>
					<div class="col-md-2 form-group">
						<label for="">Model:</label>
						<input name="name" ng-model="financeCube.model.modelVisId" type="text" readonly="readonly" class="form-control">
					</div>
		
					<div class="col-md-2 form-group">
						<label for="" class="nowrap">Locked by TaskID:</label>
						<input name="name" ng-model="financeCube.lockedByTaskId" type="text" readonly="readonly" class="form-control">
					</div>
				</div>
					
				<div class="row getToCalc2">
					<div class="col-md-6 form-group">
						<label for="">Internal Key:</label>
						<input name="name" ng-model="financeCube.internalKey" type="text" readonly="readonly" class="form-control">
					</div>
					<div class="col-md-2">
						<div class="form-group">
						<label for=""></label>
							<div class="checkbox">
								<label class="checkbox">
									<input type="checkbox" ng-model="financeCube.audited">
									Audited
								</label>
							</div>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label for=""></label>
							<div class="checkbox">
								<label class="checkbox">
									<input type="checkbox" ng-model="financeCube.hasData" disabled="disabled">
									Has Data
								</label>
							</div>
						</div>		
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label for=""></label>
							<div class="checkbox">
								<label class="checkbox">
									<input type="checkbox" ng-model="financeCube.cubeFormulaEnabled">
									Cube formula enabled
								</label>
							</div>
						</div>		
					</div>
				</div>
			</div>
			
			<tabset justified="true">
			
				<tab heading="Data Types">
					<div class="fc-panel fc-data-types">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th class="clickable" ng-click="orderBy = ''; reverse=false">#</th>
									<th class="clickable" ng-class="{sorted: orderBy == 'dataTypeVisId', desc: reverse}" ng-click="orderBy = 'dataTypeVisId'; reverse=!reverse">Data Type</th>
									<th class="clickable" ng-class="{sorted: orderBy == 'dataTypeDescription', desc: reverse}" ng-click="orderBy = 'dataTypeDescription'; reverse=!reverse">Description</th>
									<th class="clickable" ng-class="{sorted: orderBy == 'subTypeName', desc: reverse}" ng-click="orderBy = 'subTypeName'; reverse=!reverse">Type</th>
									<th class="clickable" ng-class="{sorted: orderBy == 'cubeLastUpdated', desc: reverse}" ng-click="orderBy = 'cubeLastUpdated'; reverse=!reverse">Cube Last Updated</th>
									<th class="action-column" style="width: 75px">Actions</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="dataType in financeCube.dataTypes | orderBy:orderBy:reverse">
									<td>{{$index+1}}</td>
									<td title="Data Type">{{dataType.dataTypeVisId}}</td>
									<td title="Description">{{dataType.dataTypeDescription}}</td>
									<td title="Type">{{dataType.subTypeName}}</td>
									<td title="Cube Last Updated">{{dataType.cubeLastUpdated | date:'short'}}</td>
									<td class="action-column">
										<button type="button" class="btn btn-danger btn-xs" ng-click="deleteFinanceCubeDataType(dataType.dataTypeId); $event.stopPropagation();">Remove</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="add-selected-element-to-table getToCalc3" ng-class="{true: 'has-error has-feedback',false: 'is-required'}[form.name.$dirty && form.name.$invalid]">
						<label for="">Add Data Type:</label>
						<button type="button" class="btn btn-success" ng-click="openDataTypeForFinanceCubeChooser(separate); $event.stopPropagation();">Add</button>
						<br class="clearBoth" />
					</div>
					<br class="clearBoth" />
				</tab>
			
				<tab heading="Roll up Rules">
					<div class="fc-panel fc-roll-up-rules">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th class="clickable">#</th>
									<th class="clickable">Data Type</th>
									<th ng-repeat="dimension in financeCube.dimensions">
										{{dimension.dimensionVisId}}
									</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="rollUpRuleLine in financeCube.rollUpRuleLines">
									<td>{{$index+1}}</td>
									<td>{{rollUpRuleLine.dataType.dataTypeVisId}}</td>
									<td ng-repeat="rollUpRule in rollUpRuleLine.rollUpRules">
										<input type="checkbox" ng-model="rollUpRule.rollUp">
									</td>
								</tr>
							</tbody>
						</table>
					</div>	
				</tab>
			
			</tabset>
	
		</div>
		<div class="modal-footer">
			<button class="btn btn-success" ng-click="save()" type="reset">Save</button>
			<button class="btn btn-primary" ng-click="close()" type="reset">Close</button>
		</div>
	</div>
  </div>
</div>
<jsp:include page="adminFooter.jsp"></jsp:include>
<script src="./scripts/financeCube.js"></script>
</body>
</html>