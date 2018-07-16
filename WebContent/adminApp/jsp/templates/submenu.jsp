<div id="submenu" class="row" ng-controller="SubMenuController">
    <div class="col-md-12">

        <div class="btn-group">
            <button type="button" class="btn btn-default btn-md" ng-click="refresh()" ng-disabled="isRefreshDisabled" title="Refresh Table">
                <i class="fa fa-refresh"></i>
            </button>
        </div> 

        <div class="btn-group">
            <button type="button" class="btn btn-default btn-md" ng-click="create()" ng-disabled="isCreateDisabled">
                <i class="fa fa-file-o"></i> Create
            </button>
        </div>

        <div class="btn-group">
            <button type="button" class="btn btn-default btn-md" ng-click="open()" ng-disabled="isOpenDisabled">
                <i class="fa fa-folder-open-o"></i> Open
            </button>
        </div>

        <%
/*        <div class="btn-group">
            <button type="button" class="btn btn-default btn-md" ng-click="clearCookies()">
                Clear cookies
            </button>
        </div>*/
        %>

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
        
        <div class="timer">Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="closeTab()">close tab</a></div>

        <div class="filter-by-model pull-right">
            <% /*NOTE: declaration of models is in Main Controller
            <select class="form-control" id="activeModel"
                ng-change="selectCurrentModel(currentModel)"
                ng-model="currentModel"
                ng-options="(model.modelVisId + '  --  [' + model.financeCubeVisId + ']  --  [' + model.description+']') for model in models.sourceCollection">
                <option value="">-- Model -- Finance Cube -- Description --</option>
            </select>*/ %>
            <select class="form-control" id="activeModel"
                ng-change="selectCurrentModel(currentModel)"
                ng-model="currentModel"
                ng-options="(model.modelVisId + ' &mdash; ' + model.financeCubeVisId + ' &mdash; ' + model.description) for model in models.sourceCollection">
                <option value="">Model &mdash;&mdash; Finance Cube &mdash;&mdash; Description</option>
            </select>            
        </div>

        <div class="input-group filter-by-word pull-right">
            <input type="text" class="form-control" placeholder="filter by word..." ng-model="filter.byWord" ng-change="onFilterWordChange(filter.byWord)"/>
            <span class="input-group-addon clear-filter" ng-click="filter.byWord='';onFilterWordChange(filter.byWord)"><span class="glyphicon glyphicon-remove"></span></span>
        </div>
    </div>
</div>