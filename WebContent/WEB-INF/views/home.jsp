    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

     <jsp:include page="header1.jsp"></jsp:include>

<div class="page-container">
	<div class="page-content-wrapper main-text-color">
		<div class="row text-right width100">
			<span class="glyphicon glyphicon-stop text-red"><span class="text-black">Not Started</span></span>
			<span class="glyphicon glyphicon-stop text-blue"><span class="text-black">Preparing</span></span>
			<span class="glyphicon glyphicon-stop text-yellow"><span class="text-black">Submitted</span></span>
			<span class="glyphicon glyphicon-stop text-green"><span class="text-black">Agreed</span></span>
			<span class="glyphicon glyphicon-stop text-magenta"><span class="text-black">Not Plannable</span></span>
			<span class="glyphicon glyphicon-stop text-gray"><span class="text-black">Disabled</span></span>
		</div>
		<ul class="nav nav-tabs" id="homeTabs" role="tablist">
		     <c:forEach items="${tabLists}" var="tabName" varStatus="status">
			 	<li role="presentation"><a href="#tab1" onclick="getTabContent(${tabName.getModelId()})" role="tab" data-toggle="tab">${tabName.getDescription()}</a></li>
		     </c:forEach>
		</ul>
		<div class="tab-content">
		  <div role="tabpanel" class="tab-pane active" id="tab1">
			  <div class="scrollY">
				  <table class="table table-striped" id="manageTable">
					  <thead>
					  <tr>
						  <th class='col-md-1'>&nbsp;&nbsp;</th>
						  <th class='col-md-9'>Management Accounts</th>
					  </tr>
					  </thead>
					  <tbody>
					  <tr>
						  <td></td>
						  <td><a href="/cppro/Servlet2"> (a)-2016 Management Accounts</a></td>
					  </tr>
					  <tr>
						  <td></td>
						  <td><a href="javascript:;"> (b)-2016 Flash Sales</a></td>
					  </tr>
					  <tr>
						  <td></td>
						  <td><a href="javascript:;"> (c)-2016 Sale Calendar</a></td>
					  </tr>
					  </tbody>
				  </table>
				  <table class="table table-striped" id="forecastTable">
					  <thead>
					  <tr>
						  <th class='col-md-1'>&nbsp;&nbsp;&nbsp;</th>
						  <th class='col-md-9'>Forecast</th>
					  </tr>
					  </thead>
					  <tbody>
					  <tr>
						  <td></td>
						  <td><a href="javascript:;"> (a)-2016 Forecast</a></td>
					  </tr>
					  <tr>
						  <td></td>
						  <td><a href="javascript:;"> (b)-2016 Forecast[Finance user only]</a></td>
					  </tr>
					  </tbody>
				  </table>
				  <table class="table table-striped" id="BudgetTable">
					  <thead>
					  <tr>
						  <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
						  <th>Budget Cycle Summarry</th>
						  <th class="text-right" style="line-height:41px;">Status</th>
						  <th class="text-center" style="width:1%;white-space: nowrap;">
							  <div style="border-bottom:1px solid #aaa;padding:0 5px;">Reporting Status</div>
							  <span class="glyphicon glyphicon-stop text-red"></span>
							  <span class="glyphicon glyphicon-stop text-blue"></span>
							  <span class="glyphicon glyphicon-stop text-yellow"></span>
							  <span class="glyphicon glyphicon-stop text-green"></span>
						  </th>
						  <th style="line-height:41px;">Actions</th>
					  </tr>
					  </thead>
					  <tbody>
					  <tr>
						  <td></td>
						  <td><a href="javascript:;">(a)-2016 Forecast</a></td>
						  <td></td>
						  <td class="text-cener">						  
						  	<span >0</span>
							  <span>0</span>
							  <span>2</span>
							  <span >0</span>
						  </td>
						  <td></td>
					  </tr>
					  </tbody>
				  </table>
			  </div>
			  <table class="table table-striped" id="bottomTable">
				  <thead>
				  <tr>
					  <th class="text-center">From</th>
					  <th class="text-center">Date</th>
					  <th class="text-center">Subject</th>
					  <th class="text-center">Model Budget Instruction(s)</th>
				  </tr>
				  </thead>
				  <tbody>
				  <c:forEach items="${messageList}" var="message" varStatus="status">
					  <tr>
						  <td>${message[4]}</td>
						  <td>${message[6]}</td>
						  <td class="rightBordered">${message[0]}</td>
						  <td> &nbsp;</td>
					  </tr>
				  </c:forEach>
				  </tbody>
			  </table>
		  </div>
		  <div role="tabpanel" class="tab-pane" id="tab2">Tab 2 content...</div>
		  <div role="tabpanel" class="tab-pane" id="tab3">Tab 3 content...</div>
		  <div role="tabpanel" class="tab-pane" id="tab4">Tab 4 content...</div>
		  <div role="tabpanel" class="tab-pane" id="tab5">Tab 5 content...</div>
		</div>
	</div>
</div>
<script>
jQuery(document).ready(function(){
	getTabContent(${tabLists[0].getModelId()});
})
</script>
<script src="./scripts/home.js"></script>
</body>
</html>