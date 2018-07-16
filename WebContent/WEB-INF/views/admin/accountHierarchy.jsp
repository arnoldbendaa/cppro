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
                <div class="timer text-right  bg-primary">Session timeout: 119 min | <a class="logout color-white "  href="${base_url}logout">logout</a> | <a class="logout color-white" href="${base_url}home">To Home</a></div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-md" ng-click="refresh()" ng-disabled="isRefreshDisabled" title="Refresh Table">
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-md" onclick="create()">
                        <i class="fa fa-file-o"></i> Create
                    </button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default btn-md" onclick="editHierarchy();">
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
                <h1 class="text-center">Hierarchies Account</h1>
                <table id="tblHierarchyDimension" class="display cell-border compact" cellspacing="0" width="100%">
                    <thead>
	                    <tr>
		                     <th  style="border-left:1px solid #e0e0e0;">#</th>
		                     <th>Model ID</th>
		                     <th>Dimension ID</th>
		                     <th>Hierarchy ID</th>
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
		<h3><i class="fa fa-user"></i>
		Account Hierarchy
		</h3>
	</div>
	<div class="modal-body" >
		<div class="modalDivider getToCalc3"></div>
		<div class="dimension-main-data">
			<div class="row">
				<div class="col-md-4">
					<label for="">Identifier:</label>
					<input name="name" maxlength=100 id="hierarchyVisId" type="text" class="form-control">
				</div>
				<div class="col-md-4">
					<label for="">Description:</label>
					<input name="name" maxlength=128 id="hierarchyDescription" type="text" class="form-control">
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label for="">Dimension:</label>
						<select class="form-control" id="dimensionVisId" ></select>
					</div>
				</div>
			</div>
		</div>
		<div class="modalDivider getToCalc1"></div>
		<div class="hierarchy-elements">
			<div class="crud-buttons">
				<button class="btn btn-danger" onclick="deleteTreeElements()" id="btnDelete" disabled>Delete</button>
				<button class="btn btn-success" onclick="createTreeElement('before')" id="btnInsertBefore" disabled>Insert Before</button>
				<button class="btn btn-success" onclick="createTreeElement('after')" id="btnInsertAfter" disabled>Insert After</button>
				<button class="btn btn-success" onclick="createTreeElement()" id="btnInsertDependent" disabled>Insert Dependent</button>
				<button class="btn btn-warning" onclick="moveTreeElement('before')" id="btnmoveUp" disabled>Move Up</button>
				<button class="btn btn-warning" onclick="moveTreeElement('after')" id="btnmoveDown" disabled>Move Down</button>
			</div>
			<div class="col-md-12 panel elementToResize" ng-if="id == -1">
				<js-tree tree-data="scope" tree-model="createdNode" tree-plugins="contextmenu,unique,dnd" tree-contextmenu="contextMenu" tree-events="select_node:selectNode;create_node:createNode;delete_node:deleteNode;move_node:dnd;refresh:refreshNode;ready:readyTree;activate_node:activeNode"></js-tree>
			</div>
			<div class="col-md-12 panel elementToResize" ng-if="id != -1">
				<div id="tree"></div>
			</div>
		</div>
		
		<div class="modalDivider getToCalc2"></div>

		<div class="edit-hier-elem" ng-if="type === true">
			<div class="coverEdition" ng-hide="select.state.selected">
			</div>		
			<div class="row">
				<div class="form-group col-md-6">
					<label for="">Identifier:</label>
					<input class="form-control" 
					name="name" id="treeIdentifier"
					maxlength=64
					>
				</div>
				<div class="form-group col-md-6">
					<label for="">Description:</label>
					<input
					name="name" 
					maxlength=64 id="treeDescription"
					ng-model="select.original.description"
					ng-disabled="!select.original.augentElement || hierarchy.readOnly"
					ng-change="changeValeuInNode()"
					type="text"
					class="form-control">
				</div>
			</div>
			<div class="row checkbox-radio">
				<div class="col-md-4">
					<fieldset>
						<legend class="no-margin">Credit/Debit:</legend>
						<div class="form-group">
							<div class="radio">
								<label class="radio">
									<input
									type="radio"
									name="creditDebit"
									data-ng-model="select.original.creditDebit"
									ng-disabled="!select.original.augentElement || !hierarchy.augentMode "
									ng-change="changeValeuInNode()"
									value="1"> Credit
								</label>
							</div>
							<div class="radio">
								<label class="radio">
									<input
									type="radio"
									name="creditDebit"
									data-ng-model="select.original.creditDebit"
									ng-disabled="!select.original.augentElement || !hierarchy.augentMode"
									ng-change="changeValeuInNode()"
									value="2"> Debit
								</label>
							</div>
						</div>
					</fieldset>
				</div>
				<div class="col-md-4"  ng-hide="id == -1">
					<fieldset>
						<legend class="no-margin">Cr/Dr Override:</legend>
						<div class="form-group">
							<div class="radio">
								<label class="radio">
									<input
									type="radio"
									name="augCreditDebit"
									data-ng-model="select.original.augCreditDebit"
									ng-disabled="!((!hierarchy.readOnly && !select.original.feedImpl) && hierarchy.augentMode && !select.original.augentElement)"
									ng-change="changeValeuInNode()"
									value="1"> Credit
								</label>
								
							</div>
							<div class="radio">
								<label class="radio">
									<input
									type="radio"
									name="augCreditDebit"
									data-ng-model="select.original.augCreditDebit"
									ng-disabled="!(!hierarchy.readOnly && !select.original.feedImpl && hierarchy.augentMode && !select.original.augentElement)"
									ng-change="changeValeuInNode()"
									value="2"> Debit
								</label>
							</div>
							<div class="radio">
								<label class="radio">
									<input
									type="radio"
									name="augCreditDebit"
									data-ng-model="select.original.augCreditDebit"
									ng-disabled="!(!hierarchy.readOnly && !select.original.feedImpl && hierarchy.augentMode && !select.original.augentElement)"
									ng-change="changeValeuInNode()"
									value="0"> No override
								</label>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
			<br class="clearBoth" />
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn btn-success" onclick="save()" type="button">Save</button>
		<button class="btn btn-primary" type="button" data-dismiss="modal">Close</button>
	</div>
	</div>
  </div>
</div>
<div id="chooseDimension" class="modal fade" role="dialog">
  <div class="modal-dialog">
      <div class="modal-content">
	<div class="modal-header">
	<h3> <i class="fa fa-cubes" ></i> 
			Choose Dimension
		</h3>
	</div>
	<div class="modal-body">
		<div class="dimension-chooser list clearfix">
			<table id="dimensionTable" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Id</th>
						<th>Dimension</th>
					</tr >
				</thead>
				<tbody>
				</tbody>
			</table >
		</div>
	</div >
	<div class ="modal-footer" >
		<button class = "btn btn-success" ng-disabled ="selectedDimension == null" onclick ="choose()"> Choose </button>
		<button class="btn btn-primary" ng-click="close()" type="reset" data-dismiss="modal">Close</button >
	</div>
	</div>
	</div>
</div>
<jsp:include page="adminFooter.jsp"></jsp:include>
<script src="./scripts/accountHierarchy.js"></script>
</body>
</html>