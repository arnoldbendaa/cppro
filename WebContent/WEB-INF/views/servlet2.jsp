    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
     
     <jsp:include page="header1.jsp"></jsp:include>
<div class="page-container">
    <div class="row text-right width100">
        <span class="glyphicon glyphicon-stop text-red"><span class="text-black">Not Started</span></span>
        <span class="glyphicon glyphicon-stop text-blue"><span class="text-black">Preparing</span></span>
        <span class="glyphicon glyphicon-stop text-yellow"><span class="text-black">Submitted</span></span>
        <span class="glyphicon glyphicon-stop text-green"><span class="text-black">Agreed</span></span>
        <span class="glyphicon glyphicon-stop text-magenta"><span class="text-black">Not Plannable</span></span>
        <span class="glyphicon glyphicon-stop text-gray"><span class="text-black">Disabled</span></span>
    </div>
    <ul class="nav nav-tabs main-text-color" role="tablist">
        <li role="presentation" class="active"><a href="#tab1" role="tab" data-toggle="tab">${tabName }</a></li>
    </ul>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="tab1">
            <table class="table table-striped">
            <thead>
                    <tr>
                        <th>&nbsp;&nbsp;</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <c:if test="${category=='B'}">
							<th rowspan="2" class="col3" style="border-top: 0px;text-align:center" nowrap>Status</th>
	                        <th class="text-center" style="width:1%;white-space: nowrap;">
								  <div style="border-bottom:1px solid #aaa;padding:0 5px;">Reporting Status</div>
								  <span class="glyphicon glyphicon-stop text-red"></span>
								  <span class="glyphicon glyphicon-stop text-blue"></span>
								  <span class="glyphicon glyphicon-stop text-yellow"></span>
								  <span class="glyphicon glyphicon-stop text-green"></span>
							  </th>
                        </c:if>
                        
                        <th>Available Actions</th>
                    </tr>
                    </thead>
                    <tbody>
 					<c:forEach items="${budgetList}" var="budget" varStatus="status">
                       <tr>
                        <td></td>
                        <td title="right click for RA specific actions" class="firstColumnIframe">${budget.getIdentifier() }</td>
                        <td title="right click for RA specific actions" class="secondColumnIframe">${budget.getDescription() }</td>
                        <c:if test="${category=='B'}">
                        <td></td>
                        <td style="text-align:center">
                  			<c:if test="${budget.getState()==\"0\" }">
								 <span class="glyphicon glyphicon-stop text-red"></span>
							</c:if>
                  			<c:if test="${budget.getState()==\"6\" }">
								<span class="glyphicon glyphicon-stop text-magenta"></span>
							</c:if>
                  			<c:if test="${budget.getState()==\"5\" }">
                  				<span class="glyphicon glyphicon-stop text-gray"></span>
							</c:if>
                  			<c:if test="${budget.getState()==\"4\" }">
            					<span class="glyphicon glyphicon-stop text-green"></span>
                  			</c:if>
                  			<c:if test="${budget.getState()==\"3\" }">
								  <span class="glyphicon glyphicon-stop text-yellow"></span>
							</c:if>
                  			<c:if test="${budget.getState()==\"2\" }">
								<span class="glyphicon glyphicon-stop text-blue"></span>
							</c:if>
                         </td>
                        <td style="text-align:center">${budget.getChildNotStarted() }&nbsp;&nbsp;${budget.getChildPreparing() }&nbsp;&nbsp;${budget.getChildSubmited() }&nbsp;&nbsp;${budget.getChildAgreed() }</td>
                        </c:if>
                        
                        <td>
                        	<input type="button" href="/cppro/Excel" value="Review" onclick="location.href='/cppro/Excel?modelId=${modelId}&budgetCycleId=${budgetCycleId}&structureElementId=${structureElemenetId}&modelName=${modelName }&cycleName=${cycleName }'"/>
                        	<c:if test="${category=='B'}">
	                        	<c:if test="${budget.isMassRejectable()==true}">
	                        		<input type="button" value="Reject" onclick="massReject(10,'${budgetCycleId }','${structureElemenetId}')"/>
								</c:if>
								<c:if test="${budget.isRejectable()==true}">
									<input type="button" value="Reject" onclick="changeState(2,'${budgetCycleId }','${structureElemenetId}')" />
								</c:if>
								<c:if test="${budget.isAgreeable()==true}">
									<input type="button" value="Reject" onclick="changeState(2,'${budgetCycleId }','${structureElemenetId}')"/>
									<input type="button" value="Agree" onclick="changeState(4,'${budgetCycleId }','${structureElemenetId}')"/>
								</c:if>
								<c:if test="${budget.isShowSubmit()==true}">
									<input type="button" value="submit" onclick="changeState(3,'${budgetCycleId }','${structureElemenetId}')"/>
								</c:if>
								<c:if test="${budget.isShowSubmit()==false}">
									<c:if test="${budget.isMassSubmitable()==true}">
										<input type="button" value="submit" onclick="changeState(3,'${budgetCycleId }','${structureElemenetId}')"/>
									</c:if>
								</c:if>
								<c:if test="${budget.isShowStart()==true}">
									<input type="button" value="start" onclick="changeState(1,'${budgetCycleId }','${structureElemenetId}')"/>
								</c:if>
                        	</c:if>
                        </td>
                        <c:if test="${category!='B'}">
                        	<td></td>
                        </c:if>
                      </tr>
                      <c:forEach items="${budget.getChildren()}" var="child" varStatus="status">
	                      <tr>
	                        <td></td>
	                        <td title="right click for RA specific actions" class="firstColumnIframe">${child.getIdentifier() }</td>
	                        <td title="right click for RA specific actions" class="secondColumnIframe">${child.getDescription() }</td>
	                        <c:if test="${category=='B'}">
		                        <td></td><td></td>
		                        <td style="text-align:right">
		                  			<c:if test="${child.getState()==\"0\" }">
										 <span class="glyphicon glyphicon-stop text-red"></span>
									</c:if>
		                  			<c:if test="${child.getState()==\"6\" }">
										<span class="glyphicon glyphicon-stop text-magenta"></span>
									</c:if>
		                  			<c:if test="${child.getState()==\"5\" }">
		                  				<span class="glyphicon glyphicon-stop text-gray"></span>
									</c:if>
		                  			<c:if test="${child.getState()==\"4\" }">
		            					<span class="glyphicon glyphicon-stop text-green"></span>
		                  			</c:if>
		                  			<c:if test="${child.getState()==\"3\" }">
										  <span class="glyphicon glyphicon-stop text-yellow"></span>
									</c:if>
		                  			<c:if test="${child.getState()==\"2\" }">
										<span class="glyphicon glyphicon-stop text-blue"></span>
									</c:if>
		                         </td>
	                        </c:if>
	                        <td>
	                        	<input type="button" href="/cppro/Excel" value="Review" onclick="location.href='/cppro/Excel?modelId=${modelId}&budgetCycleId=${budgetCycleId}&structureElementId=${structureElemenetId}&modelName=${modelName }&cycleName=${cycleName }'"/>
		                        <c:if test="${category=='B'}">
									<c:if test="${child.isRejectable()==true}">
										<input type="button" value="Reject" onclick="changeState(2,'${budgetCycleId }','${structureElemenetId}')" />
									</c:if>
									<c:if test="${child.isShowAgree()==true}">
										<input type="button" value="Reject" onclick="changeState(2,'${budgetCycleId }','${structureElemenetId}')"/>
										<input type="button" value="Agree" onclick="changeState(4,'${budgetCycleId }','${structureElemenetId}')"/>
									</c:if>
									<c:if test="${child.isShowSubmit()==true}">
										<input type="button" value="submit" onclick="changeState(3,'${budgetCycleId }','${structureElemenetId}')"/>
									</c:if>
									<c:if test="${child.isShowStart()==true}">
										<input type="button" value="start" onclick="changeState(1,'${budgetCycleId }','${structureElemenetId}')"/>
									</c:if>
	                        	</c:if>
	                        </td>
	                        <c:if test="${category!='B'}">
	                        	<td></td>
	                        </c:if>
	                      </tr>
                      </c:forEach>
                      </c:forEach>
                      </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>