<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<nav class="navbar" ng-controller="MenuController">
	<div class="logo">
		<a href="#"><img src="/cppro/images/bonhams.png" alt="logo"/></a>
	</div>
	<ul>
		<c:if test="${fn:contains(rolesString, '/Dashboard Auction/') || fn:contains(rolesString, '/Dashboard Free Form/') || fn:contains(rolesString, '/Dashboard Hierarchy Open/') ||  fn:contains(rolesString, '/Dashboard Hierarchy Open/') ||  fn:contains(rolesString, '/Dashboard Hierarchy Edit/') || fn:contains(rolesString, '/Dashboard Free Form/') || fn:contains(rolesString, '/Dashboard Free Form Open/') || fn:contains(rolesString, '/Dashboard Free Form Edit/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="dashboardsExpand=!dashboardsExpand" ng-class="{'expanded': dashboardsExpand}"><span class="fa fa-bar-chart"></span> Dashboard</div>
				<ul class="slide" ng-show="dashboardsExpand">
					<c:if test="${fn:contains(rolesString, '/Dashboard Auction/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="<%=basePath%>dashboard/auction" target="_blank" active-link="active">Auction</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Dashboard Hierarchy Open/') || fn:contains(rolesString, '/Dashboard Hierarchy Edit/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#dashboard/hierarchy/" active-link="active">Hierarchy</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Dashboard Free Form Open/') || fn:contains(rolesString, '/Dashboard Free Form Edit/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#dashboard/freeform/" active-link="active">Free Form</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Batch Recalculate/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#batch-recalculate/" active-link="active"><span class="fa fa-tasks"></span> Batch Recalculate</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Budget Instructions/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#budget-instructions/" active-link="active"><span class="fa fa-info-circle"></span> Budget Instructions</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Budget Cycles/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#budget-cycles/" active-link="active"><span class="fa fa-refresh"></span> Budget Cycles</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Data Editor/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#data-editor/" active-link="active"><span class="fa fa-pencil"></span> Data Editor</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Data Types/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#data-types/" active-link="active"><span class="fa fa-cogs"></span> Data Types</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Account/') || fn:contains(rolesString, '/Business/') || fn:contains(rolesString, '/Calendar/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="dimensionsExpand=!dimensionsExpand" ng-class="{'expanded': dimensionsExpand}"><span class="fa fa-tachometer"></span> Dimensions</div>
				<ul class="slide" ng-show="dimensionsExpand">
					<c:if test="${fn:contains(rolesString, '/Account/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#dimensions/account/" active-link="active">Account</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Business/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#dimensions/business/" active-link="active">Business</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Calendar/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#dimensions/calendar/" active-link="active">Calendar</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Hierarchies Account/') || fn:contains(rolesString, '/Hierarchies Business/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="hierarchiesExpand=!hierarchiesExpand" ng-class="{'expanded': hierarchiesExpand}"><span class="fa fa-sitemap"></span> Hierarchies</div>
				<ul class="slide" ng-show="hierarchiesExpand">
					<c:if test="${fn:contains(rolesString, '/Hierarchies Account/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#hierarchies/account/" active-link="active">Account</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Hierarchies Business/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#hierarchies/business/" active-link="active">Business</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Finance Cubes/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#finance-cubes/" active-link="active"><span class="fa fa-cubes"></span> Finance Cubes</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Finance Forms/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#finance-cube-formula/" active-link="active"><span class="fa fa-cube"></span> Finance Cube Formula</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Models/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#models/" active-link="active"><span class="fa fa-list"></span> Models</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/External Systems/') || fn:contains(rolesString, '/Model Mappings/') || fn:contains(rolesString, '/Import/') || fn:contains(rolesString, '/Change Management/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="inputOutputExpand=!inputOutputExpand" ng-class="{'expanded': inputOutputExpand}"><span class="fa fa-exchange"></span> Input/Output</div>
				<ul class="slide" ng-show="inputOutputExpand">
					<c:if test="${fn:contains(rolesString, '/External Systems/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#input-output/external-systems/" active-link="active">External Systems</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Model Mappings/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#input-output/model-mappings/" active-link="active">Model Mappings</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Import/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#input-output/import/" active-link="active">Import</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Change Management/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#input-output/change-management/" active-link="active">Change Management</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Currency/') || fn:contains(rolesString, '/Parameters/') || fn:contains(rolesString, '/Auction/') || fn:contains(rolesString, '/Project/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="lookupTablesExpand=!lookupTablesExpand" ng-class="{'expanded': lookupTablesExpand}"><span class="fa fa-eye"></span> Lookup Tables</div>
				<ul class="slide" ng-show="lookupTablesExpand">
					<c:if test="${fn:contains(rolesString, '/Currency/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="<%=basePath%>lookupTable/currency/" target="_blank">Currency</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Parameters/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="<%=basePath%>lookupTable/parameters/" target="_blank">Parameters</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Auction/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="<%=basePath%>lookupTable/auction/" target="_blank">Auction</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Project/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="<%=basePath%>lookupTable/project/" target="_blank">Project</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		
		<c:if test="${fn:contains(rolesString, '/Task/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="monitorsExpand=!monitorsExpand" ng-class="{'expanded': monitorsExpand}"><span class="fa fa-desktop"></span> Monitors</div>
				<ul class="slide" ng-show="monitorsExpand">
					<li><a class="item-label" href="#monitors/task/" active-link="active">Task</a></li>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/External User Destinations/') || fn:contains(rolesString, '/Internal Users Destinations/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="reportExpand=!reportExpand" ng-class="{'expanded': reportExpand}"><span class="fa fa-archive"></span> Report</div>
				<ul class="slide" ng-show="reportExpand">
					<c:if test="${fn:contains(rolesString, '/External User Destinations/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#report/external-user-destinations/" active-link="active">External User Destinations</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Internal Users Destinations/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#report/internal-user-destinations/" active-link="active">Internal User Destinations</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Authentication/') || fn:contains(rolesString, '/Model/Users/') || fn:contains(rolesString, '/User/Models/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="securityExpand=!securityExpand" ng-class="{'expanded': securityExpand}" ng-class="{'expanded': dimensionsExpand}"><span class="fa fa-lock"></span> Security</div>
				<ul class="slide" ng-show="securityExpand">
					<c:if test="${fn:contains(rolesString, '/Model/Users/') || fn:contains(rolesString, '/User/Models/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li>
							<div class="item-label" ng-click="responsibilityAreasExpand=!responsibilityAreasExpand" ng-class="{'expanded': responsibilityAreasExpand}">Responsibility Areas</div>
							<ul class="slide" ng-show="responsibilityAreasExpand">
								<c:if test="${fn:contains(rolesString, '/Model/Users/') || fn:contains(rolesString, '/SystemAdministrator/')}">
									<li><a class="item-label" href="#security/responsibility-areas/models-users/" active-link="active">Model/Users</a></li>
								</c:if>
								<c:if test="${fn:contains(rolesString, '/User/Models/') || fn:contains(rolesString, '/SystemAdministrator/')}">
									<li><a class="item-label" href="#security/responsibility-areas/users-models/" active-link="active">User/Models</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Authentication/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#security/authentication/" active-link="active">Authentication</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/System Properties/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#system-properties/" active-link="active"><span class="fa fa-cog"></span> System Properties</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/All Users/') || fn:contains(rolesString, '/Roles/') || fn:contains(rolesString, '/Logged in Users/') || fn:contains(rolesString, '/Login History/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="userExpand=!userExpand" ng-class="{'expanded': userExpand}"><span class="fa fa-user"></span> User</div>
				<ul class="slide" ng-show="userExpand">
					<c:if test="${fn:contains(rolesString, '/All Users/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#user/users/" active-link="active">Users</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Roles/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#user/roles/" active-link="active">Roles</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Logged in Users/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#user/logged-in-users/" active-link="active">Logged in Users</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Login History/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#user/logged-history/" active-link="active">Login History</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Notes/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<a class="item-label" href="#notes/" active-link="active"><span class="fa fa-comments-o"></span> Notes</a>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/Forms/') || fn:contains(rolesString, '/Flat Form Editor/') || fn:contains(rolesString, '/Flat Forms - Template Manager/') || fn:contains(rolesString, '/SystemAdministrator/')}">
			<li>
				<div class="item-label" ng-click="xmlFormsExpand=!xmlFormsExpand" ng-class="{'expanded': xmlFormsExpand}"><span class="fa fa-file"></span> Forms</div>
				<ul class="slide" ng-show="xmlFormsExpand">
					<c:if test="${fn:contains(rolesString, '/Flat Form Editor/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#xml-forms/flat-forms/" active-link="active">Flat Forms</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Flat Forms - Template Manager/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="<%=basePath%>flatFormTemplate/" target="_blank" active-link="active">Flat Forms Template Manager</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/SystemAdministrator/')}">
						<li style="display:none"><a class="item-label" href="#xml-forms/slide-show/" active-link="active">Slide Show</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:contains(rolesString, '/SystemAdministrator/') || fn:contains(rolesString, '/Web Profiles/') || fn:contains(rolesString, '/Mobile Profiles/') || fn:contains(rolesString, '/Profiles/')}">
			<li>
				<div class="item-label" ng-click="profilesExpand=!profilesExpand" ng-class="{'expanded': profilesExpand}"><span class="fa fa-files-o"></span> Profiles</div>
				<ul class="slide" ng-show="profilesExpand">
					<c:if test="${fn:contains(rolesString, '/Web Profiles/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#profiles/web/" active-link="active">Web profiles</a></li>
					</c:if>
					<c:if test="${fn:contains(rolesString, '/Mobile Profiles/') || fn:contains(rolesString, '/SystemAdministrator/')}">
						<li><a class="item-label" href="#profiles/mobile/" active-link="active">Mobile profiles</a></li>
					</c:if>
				</ul>
			</li>
		</c:if>
	</ul>
</nav>