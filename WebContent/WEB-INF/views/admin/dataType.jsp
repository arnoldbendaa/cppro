    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:include page="adminHeader.jsp"></jsp:include>
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
                    <button type="button" class="btn btn-default btn-md" onclick="edit()" ng-disabled="isOpenDisabled">
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
                <h1 class="text-center">Data Types</h1>
                <table id="example" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">#</th>
		                     <th>Data Type ID</th>
		                     <th>Type</th>
		                     <th>Measure Class</th>
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
			<h3><i class="fa fa-cogs"></i> 
				Data Type
			</h3>
		</div>
	    <div class="modal-body">
				<form class="data-type-details-form" name="form" role="form" novalidate>
					<div class="inner" ng-show="isMainDataLoaded">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<span class="text-danger" id="errIdentifier"></span>
									<label for="">Identifier:</label>
									<input name="name" id="dataTypeVisId" type="text" class="form-control">
								</div>
							</div>	
							<div class="col-md-8">
								<div class="form-group">
									<span class="text-danger" id="errDesc"></span>
									<label for="">Description:</label>
									<input name="name" id="dataTypeDes" type="text" class="form-control">
								</div>				
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<fieldset>
									<legend>Type</legend>
									<span class="text-danger" id="errSubType"></span>
									<div class="form-group">
										<select class="form-control" id="subTypes">
											<option value="0">Financial Value</option>
											<option value="1">Budget Transfer Temporary</option>
											<option value="2">Budget Transfer Permanent</option>
											<option value="3">Virtual</option>
											<option value="4">Measure</option>
										</select>
									</div>
								</fieldset>
							</div>	
							<div class="col-md-4">
								<fieldset>
									<legend>Import/Export Settings</legend>
									<div class="form-group">
										<div class="checkbox">
											<label class="checkbox">
												<input type="checkbox" id="importSetting">
												Available for Import
											</label>
										</div>
										<span class="text-danger" id=errImport></span>
									</div>
									<div class="form-group">
										<div class="checkbox">
											<label class="checkbox">
												<input type="checkbox" id="exportSetting">
												Available for Export
											</label>
										</div>
										<span class="text-danger" id="errExport"></span>
									</div>	
								</fieldset>
							</div>
							<div class="col-md-4">
								<fieldset>
									<legend>Update Settings</legend>
									<div class="form-group">
										<div class="checkbox">
											<label class="checkbox">
												<input type="checkbox" id="readOnly">
												Read Only
											</label>
										</div>
										<span class="text-danger" class="help-block" id="errUpdateSetting"></span>
									</div>		
								</fieldset>
							</div>
						</div>		
						<div style="display:none">
							<br/>
							<div class="loader align-center">
								<i class="fa fa-refresh fa-2x fa-spin"></i>
							</div>
						</div>
					</div>
				</form>
		      </div>
		      <div class="modal-footer">
				<button class="btn btn-success" onclick="save()" id="submit" data-save="save" type="reset">Save</button>
		      </div>
    </div>
  </div>
</div>

<div id="confirmModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <h3>Sure Delete?</h3>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-danger" onclick="deleteDataType();">OK</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
      </div>
    </div>
  </div>
</div>
<jsp:include page="adminFooter.jsp"></jsp:include>
<script src="./scripts/dataTypes.js"></script>

</body>
</html>