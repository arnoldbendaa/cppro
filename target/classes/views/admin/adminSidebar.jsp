 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <nav id="sidebar">
        <div id="sidebar-scroll">
            <div class="sidebar-content">
                <div class="side-content">
                    <ul class="nav-main">
                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-bar-chart"></i><span class="sidebar-mini-hide">Dashboard</span></a>
                            <ul>
                                <li>
                                    <a href="#">Auction</a>
                                    <a href="#">Hierarchy</a>
                                    <a href="#">Free Form</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a   href="#"><i class="fa fa fa-tasks"></i><span class="sidebar-mini-hide">Batch Recalculate</span></a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-info-circle"></i> <span class="sidebar-mini-hide">Budget Instructions</span></a>
                        </li>
                        <c:choose>
                        	<c:when test="${sidebar eq 'budgetCycle'}">
                        		<li class="active">
                        	</c:when>
                        	<c:otherwise>
                        		<li>
                        	</c:otherwise>
                        </c:choose>
                            <a href="${base_url}BudgetCycle"><i class="fa fa-refresh"></i> <span class="sidebar-mini-hide">Budget Cycles</span></a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-pencil"></i><span class="sidebar-mini-hide">Data Editor</span></a>
                        </li>
                        <c:choose>
                        	<c:when test="${sidebar eq 'dataType'}">
                        		<li class="active">
                        	</c:when>
                        	<c:otherwise>
                        		<li>
                        	</c:otherwise>
                        </c:choose>
                            <a href="${base_url}DataType"><i class="fa fa-cogs"></i><span class="sidebar-mini-hide">Data Types</span></a>
                        </li>
	                        <c:choose>
	                        	<c:when test="${sidebar eq 'businessDimension' || sidebar eq 'calendarDimension' || sidebar eq 'accountDimension'}">
	                        		<li class="open">
	                        	</c:when>
	                        	<c:otherwise>
	                        		<li>
	                        	</c:otherwise>
	                        </c:choose>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-tachometer"></i><span class="sidebar-mini-hide">Dimensions</span></a>
                            <ul>
                                <li>
                                    <a href="${base_url}accountDimension">Account</a>
                                    <a href="${base_url}businessDimension">Business</a>
                                    <a href="${base_url}calendarDimension">Calendar</a>
                                </li>
                            </ul>
                        </li>
	                        <c:choose>
	                        	<c:when test="${sidebar eq 'hierarchy'}">
	                        		<li class="open">
	                        	</c:when>
	                        	<c:otherwise>
	                        		<li>
	                        	</c:otherwise>
	                        </c:choose>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-sitemap"></i><span class="sidebar-mini-hide">Hierarchies</span></a>
                            <ul>
                                <li>
                                    <a href="${base_url}accountHierarchy">Account</a>
                                    <a href="#">Business</a>
                                </li>
                            </ul>
                        </li>
	                        <c:choose>
	                        	<c:when test="${sidebar eq 'financeCube'}">
	                        		<li class="active">
	                        	</c:when>
	                        	<c:otherwise>
	                        		<li>
	                        	</c:otherwise>
	                        </c:choose>
                            <a href="${base_url}FinaceCube"><i class="fa fa-cubes"></i><span class="sidebar-mini-hide">Finance Cubes</span></a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-cubes"></i><span class="sidebar-mini-hide">Finance Cube Formula</span></a>
                        </li>
                        
	                        <c:choose>
	                        	<c:when test="${sidebar eq 'model'}">
	                        		<li class="active">
	                        	</c:when>
	                        	<c:otherwise>
	                        		<li>
	                        	</c:otherwise>
	                        </c:choose>
                            <a href="${base_url}Model"><i class="fa fa-list"></i><span class="sidebar-mini-hide">Models</span></a>
                        </li>
                        <c:choose>
	                        	<c:when test="${sidebar eq 'inputOutput'}">
	                        		<li class="active">
	                        	</c:when>
	                        	<c:otherwise>
	                        		<li>
	                        	</c:otherwise>
	                        </c:choose>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-exchange"></i><span class="sidebar-mini-hide">Input/Output</span></a>
                            <ul>
                                <li>
                                    <a href="${base_url}ExternalSystem">External Systems</a>
                                    <a href="${base_url}ModelMapping">Model Mappings</a>
                                    <a href="#">Import</a>
                                    <a href="#">Change Management</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-eye"></i><span class="sidebar-mini-hide">Lookup Tables</span></a>
                            <ul>
                                <li>
                                    <a href="#">Currency</a>
                                    <a href="#">Parameters</a>
                                    <a href="#">Auction</a>
                                    <a href="#">Project</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-desktop"></i><span class="sidebar-mini-hide">Monitors</span></a>
                            <ul>
                                <li>
                                    <a href="#">Task</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-archive"></i><span class="sidebar-mini-hide">Report</span></a>
                            <ul>
                                <li>
                                    <a href="#">External User Destinations</a>
                                    <a href="#">Internal User Destinations</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-lock"></i><span class="sidebar-mini-hide">Security</span></a>
                            <ul>
                                <li>
                                    <a class="nav-submenu" data-toggle="nav-submenu" href="#">Responsibility Areas</a>
                                    <ul>
                                        <li>
                                            <a href="base_ui_blocks.html">Model/Users</a>
                                        </li>
                                        <li>
                                            <a href="base_ui_blocks_api.html">User/Models</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#">Authentication</a>
                                </li>
                            </ul>
                        </li>
	                        <c:choose>
	                        	<c:when test="${sidebar eq 'systemProperty'}">
	                        		<li class="active">
	                        	</c:when>
	                        	<c:otherwise>
	                        		<li>
	                        	</c:otherwise>
	                        </c:choose>
                            <a href="${base_url}SystemPropery"><i class="fa fa-cogs"></i><span class="sidebar-mini-hide">System Properties</span></a>
                        </li>
                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-user"></i><span class="sidebar-mini-hide">User</span></a>
                            <ul>
                                <li>
                                    <a href="${base_url}users">Users</a>
                                    <a href="#">Roles</a>
                                    <a href="#">Logged in Users</a>
                                    <a href="#">Login History</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-comments-o"></i><span class="sidebar-mini-hide">Notes</span></a>
                        </li>

                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-file"></i><span >Forms</span></a>
                            <ul>
                                <li>
                                    <a href="#">Flat Forms</a>
                                    <a href="${base_url}AdminFormTemplate">Flat Forms Template Manager</a>
                                    <a href="${base_url}slideShow">Slide Show</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a class="nav-submenu" data-toggle="nav-submenu" href="#"><i class="fa fa-files-o"></i><span >Profiles</span></a>
                            <ul>
                                <li>
                                    <a href="#">Web profiles</a>
                                    <a href="#">Mobile profiles</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
