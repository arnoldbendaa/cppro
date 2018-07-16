<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"%>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
	<%
	    String beforeCategory = "";
	    int evenOddIndex = 0;
	%>
	<logic:iterate name="homeForm" property="model" id="model" type="com.cedar.cp.utc.struts.homepage.ModelDTO" indexId="modelid">
		<bean:define id="modelName" name="model" property="name" />
		<bean:define id="modelId" name="model" property="modelId" />
		<div dojoType="dijit.layout.ContentPane" region="center" widgetId="modelData">
			<logic:iterate name="model" property="budgetCycle" id="budgetCycle" type="com.cedar.cp.utc.struts.homepage.BudgetCycleDTO" indexId="tableCounter">
				<bean:define id="bcId" name="budgetCycle" property="budgetCycleId" />
				<bean:define id="cycleName" name="budgetCycle" property="budgetCycle" />
				<bean:define id="structureId" name="budgetCycle" property="structureId" />
				<bean:define id="hId" name="budgetCycle" property="hierachyId" />
				<bean:define id="category" name='budgetCycle' property='category' />
				<!-- Adding headers when the current BudgetCycle category changes  -->
				<%
					evenOddIndex++;
				%>
				<logic:notEqual name="budgetCycle" property="category" value="<%=beforeCategory%>">
					<table class="budgetRespArea topBudgetRespArea" width="100%" cellpadding="0" cellspacing="0">
						<thead style="moj-styl">
							<tr>
								<td style="padding: 0;" colspan="8">
									<div>
										<table style="width: 100%; border-collapse: collapse;">
											<tr>
												<th class="col1_2" style="text-align: left;" rowspan="2" colspan="2" nowrap>
													<logic:equal name="budgetCycle" property="category" value="B">
														<bean:message key="cp.welcome.bc.title" />
													</logic:equal>
													<logic:equal name="budgetCycle" property="category" value="F">
														<bean:message key="cp.welcome.bc.title.fc" />
													</logic:equal>
													<logic:equal name="budgetCycle" property="category" value="M">
														<bean:message key="cp.welcome.bc.title.ac" />
													</logic:equal>
												</th>
												<logic:equal name="budgetCycle" property="category" value="B">
													<th class="col3" rowspan="2" nowrap>
														<bean:message key="cp.welcome.bc.title.status" />
													</th>
													<th class="col4_5_6_7" colspan="4" nowrap>
														<bean:message key="cp.welcome.bc.title.rastats" />
													</th>
													<th class="col8"  style="text-align: left;" rowspan="2" nowrap>
														<bean:message key="cp.welcome.bc.title.actions" />
													</th>
												</logic:equal>
											</tr>
											<tr>
												<logic:equal name="budgetCycle" property="category" value="B">
													<th class="col4">
														<img height="11" alt="" align="middle" src="<%=request.getContextPath()%>/images/red.gif" title='<bean:message key="cp.approver.key.none" />'>
													</th>
													<th class="col5">
														<img height="11" alt="" align="middle" src="<%=request.getContextPath()%>/images/blue.gif" title='<bean:message key="cp.approver.key.started" />'>
													</th>
													<th class="col6">
														<img height="11" alt="" align="middle" src="<%=request.getContextPath()%>/images/orange.gif" title='<bean:message key="cp.approver.key.submit" />'>
													</th>
													<th class="col7">
														<img height="11" alt="" align="middle" src="<%=request.getContextPath()%>/images/green.gif" title='<bean:message key="cp.approver.key.agree" />'>
													</th>
												</logic:equal>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</thead>
						<tbody>
						
				</logic:notEqual>
							<logic:iterate name="budgetCycle" id="location" property="budgetLocations" type="com.cedar.cp.utc.struts.homepage.BudgetLocationDTO" indexId="tableCounterGroup">
								<%
									String cssClassName = "";
								    if (evenOddIndex % 2 == 1) {
			    						cssClassName = "even";
			    					} else {
			    						cssClassName = "odd";
									}
								%>

								<bean:define id="elementId" name="location" property="structureElementId" />
								<bean:define id="tooltip" name="location" property="description" />
								<tr
									<logic:notEqual name="cpContext" property="newView" value="true">
										 onclick="document.location = '<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&addId=<bean:write name="location" property="structureElementId" />';"
									</logic:notEqual>
									<logic:equal name="cpContext" property="newView" value="true">
									<%
										cssClassName += "Welcome";
									%>
										onclick="showAndHide(<bean:write name="budgetCycle" property="budgetCycleId" />,<bean:write name="location" property="structureElementId" />)"
									</logic:equal>
								>
									<td colspan="8" style="padding: 0px;"> 
										<div>
											<table style="width: 100%; border-collapse: collapse;">
												<tr class="<%=cssClassName%>">
													<td align="left" class="col1">
														<logic:equal name="cpContext" property="newView" value="true">
															<button class="plusMinus" id="button_<bean:write name="budgetCycle" property="budgetCycleId" />">+</button>
														</logic:equal>
														 <bean:write name="budgetCycle" property="budgetCycle" />
													</td>
													<td align="left" nowrap="true" class="col2"></td>
													<td align="center" class="col3">
														<logic:equal name="budgetCycle" property="category" value="B">
															<logic:equal name="location" property="state" value="6">
																<img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.not.plannable" />' src="<%=request.getContextPath()%>/images/purple.gif">
															</logic:equal>
															<logic:equal name="location" property="state" value="5">
																<img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.disabled" />' src="<%=request.getContextPath()%>/images/grey.gif">
															</logic:equal>
															<logic:equal name="location" property="state" value="4">
																<img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif">
															</logic:equal>
															<logic:equal name="location" property="state" value="3">
																<img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif">
															</logic:equal>
															<logic:equal name="location" property="state" value="2">
																<img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif">
															</logic:equal>
															<logic:equal name="location" property="state" value="0">
																<img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif">
															</logic:equal>
														</logic:equal>
													</td>
													<td align="center" class="col4">
														<logic:equal name="budgetCycle" property="category" value="B">
															<a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=1'> <bean:write name="location" property="childNotStarted" />
															</a>
														</logic:equal>
													</td>
													<td align="center" class="col5">
														<logic:equal name="budgetCycle" property="category" value="B">	
															<a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=2'> <bean:write name="location" property="childPreparing" />
															</a>
														</logic:equal>
													</td>
													<td align="center" class="col6">
														<logic:equal name="budgetCycle" property="category" value="B">
															<a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=3'> <bean:write name="location" property="childSubmited" />
															</a>
														</logic:equal>
													</td>
													<td align="center" class="col7">
														<logic:equal name="budgetCycle" property="category" value="B">
															<a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=4'> <bean:write name="location" property="childAgreed" />
															</a>
														</logic:equal>
													</td>
													<td class="col8">
														<logic:iterate name="location" id="locationBI" property="budgetInstruction" type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO">
															<%
															    String locationhref = "javascript:openBI(" + locationBI.getId() + ")";
															%>
															<html:link href="<%=locationhref%>">
																<img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif" />
																<bean:write name="locationBI" property="identifier" />
															</html:link>
														</logic:iterate>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<!-- Displaying iframe with an empty scr, before setting url by javascript -->
									<td colspan="8" class="budgetIframe">
										<logic:equal name="cpContext" property="newView" value="true">
											<iframe scrolling="no" id="bc<bean:write name="budgetCycle" property="budgetCycleId" />" name="bc<bean:write name="budgetCycle" property="budgetCycleId" />" marginheight="0" frameborder="0" onLoad="autoResize('bc<bean:write name="budgetCycle" property="budgetCycleId" />')" style="float: left" width="100%" height="0" src="">
											</iframe>		
										</logic:equal>
									</td>	
								</tr>
							</logic:iterate>
						<%
						    beforeCategory = (String) category;
						%>
						</logic:iterate>
						</tbody>
					</table>

		</div>
		<div dojoType="dijit.layout.ContentPane" region="bottom">
			<table cellpadding="0" cellspacing="0" width="100%" style="padding-top:2px">
				<tr>
					<td class="groupcell">
						<div dojoType="dijit.layout.ContentPane" href="<%=request.getContextPath()%>/tableRecentMessageList.do" preventCache="true" class="bottomScroll" id="comms_<bean:write name="model" property="modelId"/>"></div>
					</td>
					<logic:notEmpty name="homeForm" property="model">
						<td width="30%" class="groupcell">
							<div dojoType="dijit.layout.ContentPane" href='<%=request.getContextPath()%>/modelBudgetInstructions.do?modelId=<bean:write name="model" property="modelId"/>' preventCache="true" class="bottomScroll"></div>
						</td>
					</logic:notEmpty>
				</tr>
			</table>
		</div>
	</logic:iterate>
</div>

<html:form action="budgetCycleStatus" styleId="budgetCycleStatusForm" style="padding:0;margin:0">
	<!-- submitModelName and submitCycleName  are set in javascript function -->
	<html:hidden styleId="submitModelName" property="submitModelName" value="" />
	<html:hidden styleId="submitCycleName" property="submitCycleName" value="" />

	<html:hidden property="oldUserCount" />
	<html:hidden property="oldDepth" />

	<html:hidden property="structureElementId" styleId="struc_id" />
	<html:hidden property="stateFilter" styleId="filter" />
	<!-- dont think we need topNode -->
	<input type="hidden" name="topNode" id="topNode" />
	<input type="hidden" name="pageSource" id="pageSource" value="bcStatus" />
	<!-- change state values -->
	<input type="hidden" name="state" id="state" />
	<input type="hidden" name="bcId" id="bcId" />
	<input type="hidden" name="seId" id="seId" />
	<input type="hidden" name="selectedStructureElementId" id="selectedStructureElementId" />
	<!-- ccid used on activity page-->
	<input type="hidden" name="CCId" id="CCId" />
	<input type="hidden" name="profileRef" id="profileRef" />

	<html:hidden property="structureElementList" styleId="structureElementList" />
	<html:hidden property="visIdList" styleId="visIdList" />
	<html:hidden property="oldDepthList" styleId="oldDepthList" />
	<html:hidden property="oldUserCountList" styleId="oldUserCountList" />
	<html:hidden property="descriptionList" styleId="descriptionList" />

	<html:hidden property="addId" styleId="addId" />
	<html:hidden property="oldId" styleId="oldId" />
	<html:hidden property="full" styleId="full" />
	<input type="hidden" name="lastActionedBy" id="lastActionedBy" />

	<input type="hidden" name="fromState" id="fromState" value="0" />
	<input type="hidden" name="toState" id="toState" value="0" />

	<logic:present name="budgetCycle">
		<html:hidden name="budgetCycle" property="modelId" />
		<html:hidden name="budgetCycle" property="budgetCycleId" />
		<html:hidden name="budgetCycle" property="structureId" />
	</logic:present>

</html:form>
