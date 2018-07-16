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
                <h1 class="text-center">Dimensions Business</h1>
                <table id="tblbusinessDimension" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">#</th>
		                     <th>Model ID</th>
		                     <th>Dimension ID</th>
		                     <th>Description</th>
		                     <th>Hierarchies</th>
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
			<h3><i class="fa fa-tachometer"></i> 
				Dimension Business
			</h3>
		</div>
	    <div class="modal-body">
	    	<form class="dimension-details-form dimensions-account-details-form" name="form" role="form" novalidate>
	    	<div class="loader align-center" style="display:none">
				<i class="fa fa-refresh fa-2x fa-spin"></i>
			</div>
			<div class="inner" ng-show="isMainDataLoaded">
				<div class="modalDivider getToCalc1"></div>
				<div class="dimension-main-data">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group" ng-class="{true: 'has-error'}[validation.dimensionVisId != '']">
								<label for="">Identifier:</label>
								<span class="text-danger" ng-show="validation.dimensionVisId != ''" class="help-block"></span>		
								<input name="name" id="dimensionVisId" ng-model="account.dimensionVisId" ng-disabled="account.readOnly" type="text" class="form-control">
							</div>							
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="">Description:</label>
								<span class="text-danger" ng-show="validation.dimensionDescription != ''" class="help-block"></span>
								<input name="name"  id="dimensionDesc"  ng-model="account.dimensionDescription" ng-disabled="account.readOnly" type="text" class="form-control">
							</div>							
						</div>
						<div class="col-md-2">
							<div class="form-group">
								<label for="">Model:</label>
								<input name="name" id="modelVisId" ng-model="account.model.modelVisId" type="text" class="form-control" value="Unassigned" disabled="disabled">
							</div>							
						</div>
						<div class="col-md-2">
							<div class="form-group">
								<label for="">Source System:</label>
								<input name="name" ng-model="account.externalSystemRefName" type="text" class="form-control" value="N/A" disabled="disabled">
							</div>						
						</div>
					</div>
				</div>
				<div class="modalDivider getToCalc2"></div>
				<div class="dim-elements">
					<h4 class="groupTitle" style="display:inline-block">Dimension Elements:</h4>
					<button 
						type="button" 
						class="btn btn-success btn-md add-remove-dimension-element"
						onClick="insertElement();">Insert</button>
					<br class="clearBoth" />
					<table id="elementTable" class="display cell-border compact" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th style="border-left:1px solid #e0e0e0;">#</th>
								<th>Identifier</th>
								<th>Description</th>
								<th>Not Plannable</th>
								<th>Disabled</th>
								<th>Cr/Dr</th>
								<th>Null</th>
								<th>Actions</th>		
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>				
				</div>
				<div class="modalDivider getToCalc3"></div>
				<div class="edit-dim-elem">
					<div class="coverEdition" ng-hide="selectedDimensionElementId != -1 && selectedDimensionElement.operation != 'remove'">
					</div>
					<div>
					<div class="row">
							<div class="form-group col-md-6">
								<label for="">Identifier:</label>				
								<input 
									name="name" id="elementVisId"
									maxlength=60
									ng-model="selectedDimensionElement.dimensionElementVisId" 
									ng-disabled="account.readOnly || account.augentMode"
									ng-change="markDimensionElementAsUpdated()"
									type="text" 
									class="form-control"
									>
							</div>				
							<div class="form-group col-md-6">
								<label for="">Description:</label>
								<input 
									name="name"  id="elementDescription"
									maxlength=64
									ng-model="selectedDimensionElement.dimensionElementDescription" 
									ng-disabled="account.readOnly || account.augentMode"
									ng-change="markDimensionElementAsUpdated()"
									type="text" 
									class="form-control">
							</div>					
						</div>
						<div class="row checkbox-radio">
							<div class="col-md-3">
								<fieldset>
									<legend>Access:</legend>
									<div class="form-group">
										<div class="checkbox">
											<label class="checkbox">
												<input 
													type="checkbox" id="notPlannable"
													ng-model="selectedDimensionElement.notPlannable"
													ng-disabled="account.readOnly || account.augentMode"
													ng-change="markDimensionElementAsUpdated()"
													>
												Not Plannable
											</label>
										</div>
										<div class="checkbox">
											<label class="checkbox">
												<input 
													type="checkbox"  id="disabled"
													ng-model="selectedDimensionElement.disabled"
													ng-disabled="account.readOnly || account.augentMode"
													ng-change="markDimensionElementAsUpdated()"
													>
												Disabled
											</label>
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-md-3">
								<fieldset>
									<legend>Credit/Debit:</legend>
									<div class="form-group">
									    <div class="radio">
									        <label class="radio">
									            <input 
									            	type="radio"
									            	name="creditDebit" 
									            	data-ng-model="selectedDimensionElement.creditDebit" 
									            	ng-disabled="account.readOnly || account.augentMode"
									            	ng-change="markDimensionElementAsUpdated()"
									            	value="1"> Credit
									        </label>
									    </div>    
									    <div class="radio">    
									        <label class="radio">
									            <input 
									            	type="radio"  checked="checked"
									            	name="creditDebit" 
									            	data-ng-model="selectedDimensionElement.creditDebit" 
													ng-disabled="account.readOnly || account.augentMode"
													ng-change="markDimensionElementAsUpdated()"
									            	value="2"> Debit
									        </label>				        
									    </div>					    
								    </div>
								</fieldset>
							</div>
							<div class="col-md-3" style="display:none">	
								<fieldset>
									<legend>Cr/Dr Override:</legend>
									<div class="form-group">
									    <div class="radio">
									        <label class="radio">
									            <input 
									            	type="radio" 
									            	name="augCreditDebit" 
									            	data-ng-model="selectedDimensionElement.augCreditDebit" 
													ng-disabled="account.readOnly || !account.augentMode"
													ng-change="markDimensionElementAsUpdated()"
									            	value="1"> Credit
									        </label>
									    </div>    
									    <div class="radio">    
									        <label class="radio">
									            <input 
									            	type="radio"
									            	name="augCreditDebit" 
									            	data-ng-model="selectedDimensionElement.augCreditDebit" 
													ng-disabled="account.readOnly || !account.augentMode"
													ng-change="markDimensionElementAsUpdated()"
									            	value="2"> Debit
									        </label>				        
									    </div>
									    <div class="radio">    
									        <label class="radio">
									            <input 
									            	type="radio" 
									            	name="augCreditDebit" 
									            	data-ng-model="selectedDimensionElement.augCreditDebit" 
													ng-disabled="account.readOnly || !account.augentMode"
													ng-change="markDimensionElementAsUpdated()"
									            	value="0"> No override
									        </label>				        
									    </div>					    
								    </div>
								</fieldset>	
							</div>	
							<div class="col-md-3">	
								<fieldset>
									<legend>Null Element:</legend>
									<div class="form-group">
										<div class="checkbox">
											<label class="checkbox">
												<input 
													type="checkbox" id="nullElement"
													ng-model="selectedDimensionElement.nullElement"
													ng-disabled="isAnyNullElement || selectedDimensionElement.operation != 'insert'"
													ng-change="markDimensionElementAsUpdated(); setAnyNullElement(true)"
													>
												Null
											</label>
										</div>
									</div>
								</fieldset>
							</div>	
						</div>
					</div>
					<br class="clearBoth" />
				</div>
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
<script src="./scripts/businessDimension.js"></script>
</body>
</html>