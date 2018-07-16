    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="./bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="./bower_components/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.css" rel="stylesheet" type="text/css">
	<link href="./bower_components/components-font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<link href="./css/theme.css" rel="stylesheet" type="text/css">
	<link href="./css/layout.css" rel="stylesheet" type="text/css">
	<link href="./css/common.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="./bower_components/jquery/dist/jquery.min.js"></script>
	<script type="text/javascript" src="./bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./bower_components/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.js"></script>
	<link href="./css/home.css" rel="stylesheet" type="text/css">
	<title>Collaborative Planning</title>
</head>
<body>
<div class="row width100">
	<div class="col-md-1" style="margin-left:20px;">
		<img class="width100" src="./images/home.png" alt="Spread" />
	</div>
	<div class="col-md-1"></div>
	<div class="col-md-3 "><h4 style="margin-top:23px;">Welcome Super user</h4></div>
</div>
<div class="page-header md-shadow-z-1-i navbar ">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			<ul class="nav navbar-nav pull-right toolbarIcons">
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a  href="${base_url}admin" class="dropdown-toggle" >
					<span class="glyphicon  glyphicon-calendar">
					</span>
					</a>
				</li>
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a href="#" class="dropdown-toggle"  >
					<span class="glyphicon  glyphicon-home">
					</span>
					</a>
				</li>
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a href="javascript:;" class="btn dropdown-toggle" data-toggle="dropdown" id="menu1">
						<span class="glyphicon glyphicon-credit-card" style="font-size:25px">
						</span>
					</a>
				    <ul class="dropdown-menu" style="width:670px;right:0;left:auto;max-width:none;">
						<div class="container-fluid">
							<ul class="nav nav-tabs">
								<li class="active"><a data-toggle="tab" href="#home">System Administrator</a></li>
							</ul>
							<div class="tab-content"  style="border-width:0 1px 1px 1px; border-color:#ddd;border-style:solid;">
								<div id="home" class="tab-pane fade in active main-text-color">
									<ul>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="${base_url}adminPanel/">Admin Application-Launch the admin application.</a></li>
								   	  <li role="presentation"><a role="menuitem" tabindex="-1" href="${base_url}Analysis">Analysis-Access the analysis tool.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="${base_url}BudgetCycle">Budget Cycles-Access a list of budget cycles.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Budget Limits-Set / View Budget Limits.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Budget Transfers-Authorise budget transfers.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Budget Transfers-Maintain budget transfers.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Budget Transfers Query-Query budget transfers.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Diagnostic Lists-Additional lists for diagnostics.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Form Report-Show list of finance forms.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Logon History-Show the logon history.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Report Definitions-Show the list of Report Definitions.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Support Help-Perform tasks to help Advanced Business Solutions locate and fix problems.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">System Properties-Alter the system properties.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Task Reports-Show the list of background task reports.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Tasks-Show the list of background tasks.</a></li>    
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Top Down Budgeting-Apply rule based changes to cells.</a></li>
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">User Assignments-Check the User Budget Responsibility Area Assignments.</a></li>    
								      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">User Settings-Check the User role settings."</a></li>								      
									 </ul>
								</div>
							</div>
						</div>
				    </ul>
										
				</li>
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a href="${base_url}mail" class="dropdown-toggle" >
					<span class="glyphicon  glyphicon-envelope">
					</span>
					</a>
				</li>
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a href="${base_url}Analysis" class="dropdown-toggle" >
					<span class="glyphicon  glyphicon-list-alt">
					</span>
					</a>
				</li>
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a href="javascript:;" class="dropdown-toggle" >
					<span class="glyphicon  glyphicon-user">
					</span>
					</a>
				</li>
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a href="${base_url}logout" class="dropdown-toggle" >
					<span class="glyphicon  glyphicon-log-out">
					</span>
					</a>
				</li>
				<li class="dropdown dropdown-extended dropdown-inbox">
					<a href="javascript:;" class="dropdown-toggle" >
					<span class="glyphicon glyphicon-record">
					</span>
					</a>
				</li>
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>
