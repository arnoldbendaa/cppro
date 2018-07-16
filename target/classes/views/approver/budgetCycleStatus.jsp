<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"%>

<head>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/budgetCycleStatus.js"></script>
	<meta name="title" content="<bean:message key="cp.reviewBudget.title"/>" />
</head>

<body>
	<logic:equal name="cpContext" property="newView" value="false">
		<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
		<div dojoType="dijit.layout.ContentPane" region="top" title="sss">
			<table width="100%">
				<tr>
					<td align="right" nowrap class="sp-table-with-legend">
			            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/red.gif"> <bean:message key="cp.approver.key.none" />&nbsp;
			            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/blue.gif"> <bean:message key="cp.approver.key.started" />&nbsp;
			            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/orange.gif"> <bean:message key="cp.approver.key.submit" />&nbsp;
			            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/green.gif"> <bean:message key="cp.approver.key.agree" />&nbsp;
			            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/purple.gif"> <bean:message key="cp.approver.key.not.plannable" />&nbsp;
			            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/grey.gif"> <bean:message key="cp.approver.key.disabled" />&nbsp;
	        		</td>
				</tr>
			</table>
			<jsp:include page="../system/errorsInc.jsp" />
		</div>
	</logic:equal>

	<logic:equal name="cpContext" property="newView" value="false">
		<div region="center" id="mainTabContainer" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%; height: 100%"  tabPosition="top">
	</logic:equal>
	
	<logic:equal name="cpContext" property="newView" value="true">
		<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
	</logic:equal>

		<logic:iterate name="budgetCycleStatusForm" id="model" property="model" type="com.cedar.cp.utc.struts.homepage.ModelDTO">
			<bean:define id="modelName" name="model" property="name" />
			<bean:define id="modelId" name="model" property="modelId" />
			<logic:iterate name="model" id="cycle" property="budgetCycle" type="com.cedar.cp.utc.struts.homepage.BudgetCycleDTO">
				<bean:define id="cycleName" name="cycle" property="budgetCycle" />
				<div id="data" class="totalsForBudget" region="top" dojoType="dijit.layout.ContentPane" title='<bean:write name="modelName"/> : <bean:write name="cycleName"/>'>
					<html:form action="budgetCycleStatus" styleId="budgetCycleStatusForm" style="padding:0;margin:0">
						<html:hidden styleId="submitModelName" property="submitModelName" value="<%=modelName.toString()%>" />
						<html:hidden styleId="submitCycleName" property="submitCycleName" value="<%=cycleName.toString()%>" />
						<table class="budgetRespArea" width="100%" cellpadding="0" cellspacing="0" name="totalsTable" id="totalsTable">
							<logic:iterate name="budgetCycleStatusForm" id="model" property="model" type="com.cedar.cp.utc.struts.homepage.ModelDTO">
								<logic:iterate name="model" id="cycle" property="budgetCycle" type="com.cedar.cp.utc.struts.homepage.BudgetCycleDTO">
									<bean:define id="bcId" name="cycle" property="budgetCycleId" />
									<bean:define id="structureId" name="cycle" property="structureId" />
									<html:hidden name="cycle" property="modelId" />
									<html:hidden name="cycle" property="budgetCycleId" />
									<html:hidden name="cycle" property="structureId" />
									<logic:equal name="cpContext" property="newView" value="false">
									<tr>
										<th class="col1_2" rowspan="2" colspan="2" style="border-top: 0px" nowrap>
											<logic:notEqual name="budgetCycleStatusForm" property="stateFilter"   value="0" >
												<logic:equal name="budgetCycleStatusForm" property="stateFilter" value="1" >
													&nbsp;<bean:message key="cp.approver.filter" />&nbsp;<bean:message key="cp.approver.key.none" />&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/red.gif">
												</logic:equal>
												<logic:equal name="budgetCycleStatusForm" property="stateFilter" value="2" >
													&nbsp;<bean:message key="cp.approver.filter" />&nbsp;<bean:message key="cp.approver.key.started" />&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/blue.gif">
												</logic:equal>
												<logic:equal name="budgetCycleStatusForm" property="stateFilter" value="3" >
													&nbsp;<bean:message key="cp.approver.filter" />&nbsp;<bean:message key="cp.approver.key.submit" />&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/orange.gif">
												</logic:equal>
												<logic:equal name="budgetCycleStatusForm" property="stateFilter" value="4" >
													&nbsp;<bean:message key="cp.approver.filter" />&nbsp;<bean:message key="cp.approver.key.agree" />&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/green.gif">
												</logic:equal>
											</logic:notEqual>
										</th>
										<logic:equal name="cycle" property="category" value="B">
										<th rowspan="2" class="col3" style="border-top: 0px" nowrap><bean:message key="cp.welcome.bc.title.status" /></th>
										<th colspan="4" class="col4_5_6_7" style="border-top: 0px" nowrap><bean:message key="cp.welcome.bc.title.rastats" /></th>
										</logic:equal>
										<th rowspan="2" class="col8" style="border-top: 0px" nowrap><bean:message key="cp.approver.actions" /></th>
									</tr>
									<tr>
									<logic:equal name="cycle" property="category" value="B">
										<th class="col4"><img alt="" height="11" style="vertical-align:middle" title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif"></th>
										<th class="col5"><img alt="" height="11" style="vertical-align:middle" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif"></th>
										<th class="col6"><img alt="" height="11" style="vertical-align:middle" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif"></th>
										<th class="col7"><img alt="" height="11" style="vertical-align:middle" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif"></th>
									</logic:equal>
									</tr>
									</logic:equal>
									<logic:iterate name="cycle" id="location" property="budgetLocations" type="com.cedar.cp.utc.struts.homepage.BudgetLocationDTO" indexId="tableCounter">
										<bean:define id="elementId" name="location" property="structureElementId" />
										<bean:define id="locationToolTip" name="location" property="description" />
										<bean:define id="actionById" name="location" property="lastUpdatedById" />
										<%
										    String tdClass = "";
										                            if (tableCounter % 2 == 0) {
										                                tdClass = "odd";
										                            } else {
										                                tdClass = "even";
										                            }
										%>
										<tr class="<%=tdClass%>">
											<td align="left" class="col1" nowrap title='<bean:write name="locationToolTip" />'>
												<bean:write name='budgetCycleStatusForm' property='HTMLCrumbs' filter="false" />
												<logic:notEmpty name="location" property="late">
													<logic:equal name="location" property="late" value="true">
														<img alt="" align="top" width="15" title='<bean:write name="location" property="lateMessage"/>' src='<%=request.getContextPath()%>/images/<bean:write name="location" property="warningImage"/>.gif'>
													</logic:equal>
													<logic:equal name="location" property="late" value="false">
														<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
													</logic:equal>
												</logic:notEmpty>
												<logic:empty name="location" property="late">
													<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
												</logic:empty>
											</td>
											<td class="col2 center" align="left" nowrap="true">
												<logic:equal name="cpContext" property="showBudgetActivity" value="true">
													<img alt="Budget Activity" title="Budget Activity" height="13" align="middle" src="<%=request.getContextPath()%>/images/settings.gif" onclick='checkBudgetActivity(<bean:write name="elementId"/>, <bean:write name="elementId"/>)' style="cursor:pointer"> 
													<img alt="Contact Budget Approver" title="Contact Budget Approver" height="13" align="middle" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactBudgetUser(<bean:write name="bcId" />,<bean:write name="elementId"/>, false, <bean:write name="structureId"/>, true)' style="cursor:pointer">
												</logic:equal>
											</td>
											<logic:equal name="cycle" property="category" value="B">
											<td class="col3 center">
													<logic:equal name="location" property="state" value="0">
														<img alt="" height="11"  title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif">
													</logic:equal>
													<logic:equal name="location" property="state" value="6">
														<img alt="" height="11"  title='<bean:message key="cp.approver.key.not.plannable" />' src="<%=request.getContextPath()%>/images/purple.gif">
													</logic:equal>
													<logic:equal name="location" property="state" value="5">
														<img alt="" height="11"  title='<bean:message key="cp.approver.key.disabled" />' src="<%=request.getContextPath()%>/images/grey.gif">
													</logic:equal>
													<logic:equal name="location" property="state" value="4">
														<img alt="" height="11"  title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif">
													</logic:equal>
													<logic:equal name="location" property="state" value="3">
														<img alt="" height="11"  title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif">
													</logic:equal>
													<logic:equal name="location" property="state" value="2">
														<img alt="" height="11"  title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif">
													</logic:equal>
											</td>
											<td class="col4" align="center">
													<a href='#' onclick='budgetStatus(<bean:write name="elementId"/>, 1 )'> <bean:write name="location" property="childNotStarted" />
													</a>
											</td>
											<td class="col5" align="center">
													<a href="#" onclick='budgetStatus(<bean:write name="elementId"/>, 2 )'> <bean:write name="location" property="childPreparing" />
													</a>
											</td>
											<td class="col6" align="center">
													<a href="#" onclick='budgetStatus(<bean:write name="elementId"/>, 3 )'> <bean:write name="location" property="childSubmited" />
													</a>
											</td>
											<td class="col7" align="center">
													<a href="#" onclick='budgetStatus(<bean:write name="elementId"/>, 4 )'> <bean:write name="location" property="childAgreed" />
													</a>
											</td>
											</logic:equal>
											<%
											    String startAction = "javascript:changeState(1," + bcId + "," + elementId + ")";
											                            String submitAction = "javascript:changeState(3," + bcId + "," + elementId + ")";
											                            String agreeAction = "javascript:changeState(4," + bcId + "," + elementId + ")";
											                            String rejectAction = "javascript:changeState(2," + bcId + "," + elementId + ")";
											                            String massSubmitAction = "javascript:massSubmit(3," + bcId + "," + elementId + ")";
											                            String massRejectAction = "javascript:massReject(10," + bcId + "," + elementId + ")";
											                            String reviewAction = "";
											%>
											<logic:present scope="session" name="cpContext">
												<%
												    // new features enabled
												                                reviewAction = "javascript:reviewBudget(" + elementId + " , " + elementId + ", null, true, " + bcId + ")";
												%>
											</logic:present>
											<td class="col8" align="left" nowrap>
												<logic:equal name="location" property="fullRights" value="false">
													<input type="button" class="coaButton" value="<bean:message key="cp.welcome.review" />" disabled />
												</logic:equal>
												<logic:equal name="location" property="fullRights" value="true">												
													<input type="button" class="coaButton" onmouseout="mouseOut(this)" value="<bean:message key="cp.welcome.review" />" onclick="<%=reviewAction%>" title="right click for RA specific actions"
														onmouseover='mouseOver(this);connectMenu(this, <%=modelId%>, <%=bcId%>, <%=structureId%>, <%=elementId%>, <%=elementId%>, <%=actionById%>,
											                           <bean:write name="location" property="showStart"/>, <bean:write name="location" property="showSubmit"/>,
											                           <bean:write name="location" property="showAgree"/>, <bean:write name="location" property="showReject"/>,
											                           <bean:write name="location" property="massSubmitable"/>, <bean:write name="location" property="massRejectable"/>,
											                           <bean:write name="cpContext" property="newFeaturesEnabled"/>)'>
													</input>
													<logic:equal name="cycle" property="category" value="B">
														<logic:equal name="location" property="massRejectable" value="true">
															<cp:CPButton onclick="<%=massRejectAction%>" securityString="WEB_PROCESS.BudgetStatus_MassReject" titleKey="cp.welcome.massReject.title">
																<bean:message key="cp.welcome.reject" />
															</cp:CPButton>
														</logic:equal>
														<logic:equal name="location" property="showReject" value="true">
															<cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
																<bean:message key="cp.welcome.reject" />
															</cp:CPButton>
														</logic:equal>
														<logic:equal name="location" property="showAgree" value="true">
															<cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
																<bean:message key="cp.welcome.reject" />
															</cp:CPButton>
															<cp:CPButton onclick="<%=agreeAction%>" securityString="WEB_PROCESS.BudgetStatus_Agree">
																<bean:message key="cp.welcome.agree" />
															</cp:CPButton>
														</logic:equal>
														<logic:equal name="location" property="showSubmit" value="true">
															<cp:CPButton onclick="<%=submitAction%>" securityString="WEB_PROCESS.BudgetStatus_Submit">
																<bean:message key="cp.welcome.submit" />
															</cp:CPButton>
														</logic:equal>
														<logic:equal name="location" property="showSubmit" value="false">
															<logic:equal name="location" property="massSubmitable" value="true">
																<cp:CPButton onclick="<%=massSubmitAction%>" securityString="WEB_PROCESS.BudgetStatus_MassSubmit" titleKey="cp.welcome.massSubmit.title">
																	<bean:message key="cp.welcome.submit" />
																</cp:CPButton>
															</logic:equal>
														</logic:equal>
														<logic:equal name="location" property="showStart" value="true">
															<cp:CPButton onclick="<%=startAction%>">
																<bean:message key="cp.welcome.start" />
															</cp:CPButton>
														</logic:equal>
													</logic:equal>
												</logic:equal>
											</td>
										</tr>
										<logic:iterate name="location" id="child" property="children" type="com.cedar.cp.utc.struts.homepage.BLChildDTO" indexId="tableCounter2">
											<bean:define id="childId" name="child" property="structureElementId" />
											<bean:define id="childToolTip" name="child" property="description" />
											<bean:define id="childActionBy" name="child" property="lastUpdateById" />
											<%
											    String tdClass2 = "";
											                                if (tableCounter2 % 2 == 0) {
											                                    if (tdClass.equals("odd")) {
											                                        tdClass2 = "even";
											                                    } else {
											                                        tdClass2 = "odd";
											                                    }
											                                } else {
											                                    if (tdClass2.equals("odd")) {
											                                        tdClass2 = "even";
											                                    } else {
											                                        tdClass2 = "odd";
											                                    }
											                                }
											%>
											<tr class="<%=tdClass2%>" 
													<logic:equal name="child" property="fullRights" value="true">
															onmouseover="connectMenu(this, <%=modelId%>, <%=bcId%>, <%=structureId%>, <%=elementId%>, <%=childId%>, <%=childActionBy%>,
							                                    <bean:write name="child" property="showStart"/>, <bean:write name="child" property="showSubmit"/>,
							                                    <bean:write name="child" property="showAgree"/>, <bean:write name="child" property="showReject"/>,
							                                    false, false,
							                           	    	<bean:write name="cpContext" property="newFeaturesEnabled"/>, '<bean:write name="modelName"/>', '<bean:write name="cycleName"/>')"
													</logic:equal>
											>
												<td align="left" class="col1 center" nowrap title='<bean:write name="childToolTip" />' onclick='budgetStatus(<bean:write name="childId"/>,null,<bean:write name="childId" />)'>
													<a href="javascript:void(0)" title="right click for RA specific actions" class="firstColumnIframe"> <bean:write name="child" property="identifier" />
													</a> 
													<a href="javascript:void(0)" title="right click for RA specific actions" class="secondColumnIframe"> <bean:write name="child" property="description" />
													</a> 													
													<logic:notEmpty name="location" property="late">
														<logic:equal name="child" property="late" value="true">
															<img alt="" align="top" width="15" class="warningIconIframe" title='<bean:write name="child" property="lateMessage"/>' src='<%=request.getContextPath()%>/images/<bean:write name="child" property="warningImage"/>.gif'>
														</logic:equal>
														<logic:equal name="child" property="late" value="false">
															<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
														</logic:equal>
													</logic:notEmpty> 
													<logic:empty name="child" property="late">
														<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
													</logic:empty>
												</td>
												<td align="left" class="col2 center" nowrap="true">
													<logic:equal name="cpContext" property="showBudgetActivity" value="true">
														<img alt="Budget Activity" title="Budget Activity" height="13" align="middle" src="<%=request.getContextPath()%>/images/settings.gif" onclick='checkBudgetActivity(<bean:write name="childId"/>,<bean:write name="childId"/>)' style="cursor:pointer">
													 	<logic:notEqual name="child" property="otherUserCount" value="0">
															<img alt="Contact Budget Holder" title="Contact Budget Holder" height="13" align="middle" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactBudgetUser(<bean:write name="bcId" />,<bean:write name="childId"/>, false, 0)' style="cursor:pointer">
															<img alt="Contact all area Budget Holders " title="Contact all area Budget Holders " align="middle" src="<%=request.getContextPath()%>/images/downarrow.gif" onclick='contactBudgetUser(<bean:write name="bcId" />,<bean:write name="childId"/>, true,<bean:write name="structureId"/>)' style="cursor:pointer">
														</logic:notEqual> 
														<logic:equal name="child" property="otherUserCount" value="0">
															&nbsp;
														</logic:equal>
													</logic:equal>
												</td>
												<logic:equal name="cycle" property="category" value="B">
												<td class="col3 center">
														<logic:lessThan name="child" property="state" value="5">
															&nbsp;
														</logic:lessThan>
														<logic:equal name="child" property="state" value="6">
															<img alt="" height="11"  title='<bean:message key="cp.approver.key.not.plannable" />' src="<%=request.getContextPath()%>/images/purple.gif">
														</logic:equal>
														<logic:equal name="child" property="state" value="5">
															<img alt="" height="11" title='<bean:message key="cp.approver.key.disabled" />' src="<%=request.getContextPath()%>/images/grey.gif">
														</logic:equal>
												</td>
												<td class="col4 center">
														<logic:equal name="child" property="state" value="0">
															<img alt="" height="11"  title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif">
														</logic:equal>
														<logic:notEqual name="child" property="state" value="0">
															&nbsp;
														</logic:notEqual>
												</td>
												<td class="col5 center">
														<logic:equal name="child" property="state" value="2">
															<img alt="" height="11" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif">
														</logic:equal>
														<logic:notEqual name="child" property="state" value="2">
															&nbsp;
														</logic:notEqual>
												</td>
												<td class="col6 center">
													<logic:equal name="child" property="state" value="3">
														<img alt="" height="11" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif">
													</logic:equal>
														<logic:notEqual name="child" property="state" value="3">
															&nbsp;
														</logic:notEqual>
												</td>
												<td class="col7 center">
													<logic:equal name="child" property="state" value="4">
															<img alt="" height="11" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif">
														</logic:equal>
														<logic:notEqual name="child" property="state" value="4">
															&nbsp;
														</logic:notEqual>
												</td>
												</logic:equal>
												<%
												    String workspaceAction = "javascript:workspaceBudget(" + childId + " , " + elementId + ", null )";
												                                startAction = "javascript:changeState(1," + bcId + "," + childId + ")";
												                                submitAction = "javascript:changeState(3," + bcId + "," + childId + ")";
												                                agreeAction = "javascript:changeState(4," + bcId + "," + childId + ")";
												                                rejectAction = "javascript:changeState(2," + bcId + "," + childId + ")";
												%>
												<logic:present scope="session" name="cpContext">
													<%
													    // new features enabled
													                                    reviewAction = "javascript:reviewBudget(" + childId + " , " + childId + ", null, true, " + bcId + ")";
													%>
												</logic:present>
												<td align="left" nowrap class="col8">
													<logic:equal name="child" property="fullRights" value="false">
															<input type="button" class="coaButton" value="<bean:message key="cp.welcome.review" />" disabled />
													</logic:equal>
													<logic:equal name="child" property="fullRights" value="true">
															<input type="button" class="coaButton" onmouseout="mouseOut(this)" value="<bean:message key='cp.welcome.review' />" onclick="<%=reviewAction%>" title="right click for RA specific actions" onmouseover="mouseOver(this)" />
													</logic:equal>

													<logic:equal name="cycle" property="category" value="B">
														<logic:equal name="child" property="showReject" value="true">
															<cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
																<bean:message key="cp.welcome.reject" />
															</cp:CPButton>
														</logic:equal>
														<logic:equal name="child" property="showAgree" value="true">
															<cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
																<bean:message key="cp.welcome.reject" />
															</cp:CPButton>
															<cp:CPButton onclick="<%=agreeAction%>" securityString="WEB_PROCESS.BudgetStatus_Agree">
																<bean:message key="cp.welcome.agree" />
																</cp:CPButton>
														</logic:equal>
														<logic:equal name="child" property="showSubmit" value="true">
																<cp:CPButton onclick="<%=submitAction%>" securityString="WEB_PROCESS.BudgetStatus_Submit">
																	<bean:message key="cp.welcome.submit" />
																</cp:CPButton>
														</logic:equal>
														<logic:equal name="child" property="showStart" value="true">
															<cp:CPButton onclick="<%=startAction%>">
																<bean:message key="cp.welcome.start" />
															</cp:CPButton>
														</logic:equal>
													</logic:equal>
												</td>
											</tr>
										</logic:iterate>
									</logic:iterate>
								</logic:iterate>
							</logic:iterate>
						</table>
						<div dojoType="dijit.Menu" id="actionMenu" style="display: none">
							<div dojoType="dijit.MenuItem" id="menuReview">Review</div>
							<div dojoType="dijit.MenuItem" id="menuChooseProfile">Choose Profile</div>
							<div dojoType="dijit.MenuItem" id="menuTopBRA">BRA</div>
							<div dojoType="dijit.PopupMenuItem" id="workFlow"><span>Workflow</span>
								<div dojoType="dijit.Menu" id="workFlowMenu">
									<div dojoType="dijit.MenuItem" id="menuStart">Start</div>
									<cp:hasSecurity securityString="WEB_PROCESS.BudgetStatus_Submit">
										<div dojoType="dijit.MenuItem" id="menuSubmit">Submit</div>
									</cp:hasSecurity>
									<cp:hasSecurity securityString="WEB_PROCESS.BudgetStatus_Agree">
										<div dojoType="dijit.MenuItem" id="menuAgree">Agree</div>
									</cp:hasSecurity>
									<cp:hasSecurity securityString="WEB_PROCESS.BudgetStatus_Reject">
										<div dojoType="dijit.MenuItem" id="menuReject">Reject</div>
									</cp:hasSecurity>
									<cp:hasSecurity securityString="WEB_PROCESS.BudgetStatus_MassSubmit">
										<div dojoType="dijit.MenuItem" id="menuMassSubmit">Mass Submit</div>
									</cp:hasSecurity>
									<cp:hasSecurity securityString="WEB_PROCESS.BudgetStatus_MassReject">
										<div dojoType="dijit.MenuItem" id="menuMassReject">Mass Reject</div>
									</cp:hasSecurity>
								</div>
							</div>
						</div>
						<div id="hiddenStuff" style="display:none;">
							<html:hidden property="oldUserCount" />
							<html:hidden property="oldDepth" />
							<html:hidden property="structureElementId" styleId="struc_id" />
							<html:hidden property="stateFilter" styleId="filter" />
							<!-- dont think we need topNode -->
							<input type="hidden" name="topNode" id="topNode" /> <input type="hidden" name="pageSource" id="pageSource" value="bcStatus" />
							<!-- change state values -->
							<input type="hidden" name="state" id="state" /> <input type="hidden" name="bcId" id="bcId" /> <input type="hidden" name="seId" id="seId" /> <input type="hidden" name="selectedStructureElementId" id="selectedStructureElementId" />
							<!-- ccid used on activity page-->
							<input type="hidden" name="CCId" id="CCId" /> <input type="hidden" name="profileRef" id="profileRef" />

							<html:hidden property="structureElementList" styleId="structureElementList" />
							<html:hidden property="visIdList" styleId="visIdList" />
							<html:hidden property="oldDepthList" styleId="oldDepthList" />
							<html:hidden property="oldUserCountList" styleId="oldUserCountList" />
							<html:hidden property="descriptionList" styleId="descriptionList" />

							<html:hidden property="addId" styleId="addId" />
							<html:hidden property="oldId" styleId="oldId" />
							<html:hidden property="full" styleId="full" />
							<input type="hidden" name="lastActionedBy" id="lastActionedBy" /> <input type="hidden" name="fromState" id="fromState" value="0" /> <input type="hidden" name="toState" id="toState" value="0" />
						</div>
					</html:form>
				</div>
			</logic:iterate>
		</logic:iterate>
	</div>
	</div>
	</div>
</body>
