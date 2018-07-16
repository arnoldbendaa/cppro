<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>

<script type="text/javascript" >
function showWelcomePanel(id)
{
 	setWelcomePanel(id);
 	showPanel(id, null);
}

</script>

<style type="text/css" id="tableStyle">
    .bottomScroll
    {
        height: 102.4px;
        overflow:auto;
    }

</style>
</head>

<body >
<html:form action="/homePage" style="margin:0;padding:0" >
<table width="100%">
    <tr>
        <td class="header5" nowrap><bean:message key="cp.welcome.title" /></td>
        <td align="right" nowrap class="sp-table-with-legend">
            <br />
            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/red.gif"> <bean:message key="cp.approver.key.none" />&nbsp;
            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/blue.gif"> <bean:message key="cp.approver.key.started" />&nbsp;
            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/orange.gif"> <bean:message key="cp.approver.key.submit" />&nbsp;
            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/green.gif"> <bean:message key="cp.approver.key.agree" />&nbsp;
            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/purple.gif"> <bean:message key="cp.approver.key.not.plannable" />&nbsp;
            <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/grey.gif"> <bean:message key="cp.approver.key.disabled" />&nbsp;
            <br />
        </td>
    </tr>
</table>
<logic:notEmpty name="homeForm" property="model"  >
<table cellspacing="0" border="0">
  <tr>

    <logic:iterate name="homeForm" property="model" id="model" type="com.cedar.cp.utc.struts.homepage.ModelDTO" indexId="modelid" >
        <td>&nbsp;</td>
        <td id='tab<bean:write name="modelid"/>' class="tabbutton" onMouseOut='setState(<bean:write name="modelid"/>, null)' onMouseOver="hover(this);" onClick='showWelcomePanel(<bean:write name="modelid"/>);' >
            <bean:write name="model" property="name" />
        </td>
    </logic:iterate>

    <logic:equal value="true"name="homeForm" property="virementsToAuthorise">
	  <td id='tab-1' width="100%" align="right" valign="middle">
		  <a href="<%=request.getContextPath()%>/virements.do"  style="padding:0;margin:0;border:0;overflow:hidden;"
			 title="Show budget transfer requests awaiting your authorisation">
			<img alt="" title="Budget Transfer Authorisation" style="vertical-align:middle;border:0;padding:0;margin:0;overflow:hidden;" src="<%=request.getContextPath()%>/images/warnings.gif">
			  Budget Transfer Authorisation
		  </a>
	  </td>
	</logic:equal>
  </tr>
</table>

<table class="tabgroupcell" cellspacing="0" width="100%"  >
	<tr>
		<td >
			<logic:iterate name="homeForm" property="model" id="model" type="com.cedar.cp.utc.struts.homepage.ModelDTO" indexId="modelid" >
                <div id='tabpanel<bean:write name="modelid"/>' style="display:none;" >
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
                        <td class="groupcell" valign="top" colspan="2">
                            <div id='mainScroll<bean:write name="modelid"/>' style="height:563.2px;overflow:auto;">
                            <table width="100%" cellpadding="1" cellspacing="1">
                                <thead >
                                <tr>
                                    <th width="100%" colspan="8" nowrap><bean:message key="cp.welcome.bc.title"/></th>
                                </tr>
                                <tr>
                                    <th width="60%" rowspan="2" colspan="2" nowrap><bean:message key="cp.welcome.bc.title.bl" /></th>
                                    <th width="5%" rowspan="2" nowrap><bean:message key="cp.welcome.bc.title.status" /></th>
                                    <th width="16%" colspan="4" nowrap><bean:message key="cp.welcome.bc.title.rastats" /></th>
                                    <th width="19%" rowspan="2" nowrap><bean:message key="cp.welcome.bc.title.bi" /></th>
                                </tr>
                                <tr>
                                    <th width="4%"><img height="11" alt="" z src="<%=request.getContextPath()%>/images/red.gif" title='<bean:message key="cp.approver.key.none" />'></th>
                                    <th width="4%"><img height="11" alt="" align="middle" src="<%=request.getContextPath()%>/images/blue.gif" title='<bean:message key="cp.approver.key.started" />'></th>
                                    <th width="4%"><img height="11" alt="" align="middle" src="<%=request.getContextPath()%>/images/orange.gif" title='<bean:message key="cp.approver.key.submit" />'></th>
                                    <th width="4%"><img height="11" alt="" align="middle" src="<%=request.getContextPath()%>/images/green.gif" title='<bean:message key="cp.approver.key.agree" />'></th>
                                </tr>
                                </thead>

                                <logic:iterate name="model" property="budgetCycle" id="budgetCycle" type="com.cedar.cp.utc.struts.homepage.BudgetCycleDTO" >
                                    <tr>
                                        <td colspan="2" align="left">
                                            <a href="<%=request.getContextPath()%>/budgetCycleHistoryDetails.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId"/>&pageSource=welcome" >
                                                <bean:write name="budgetCycle" property="budgetCycle" />
                                            </a>
                                        </td>
                                        <td colspan="5">&nbsp;</td>
                                        <td >
                                            <logic:iterate name="budgetCycle" id="cycleBI" property="budgetCycleInstructions" type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO"   >
                                                <%String locationhref = "javascript:openBI(" + cycleBI.getId() + ")";%>
                                                <html:link href="<%=locationhref%>" >
                                                    <img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif" />
                                                    <bean:write name="cycleBI" property="identifier"  />
                                                </html:link>
                                            </logic:iterate>
                                        </td>
                                    </tr>

                                    <logic:iterate name="budgetCycle" id="location" property="budgetLocations" type="com.cedar.cp.utc.struts.homepage.BudgetLocationDTO" >
                                        <bean:define id="tooltip" name="location" property="description"  ></bean:define>
                                        <tr >
                                            <td colspan="2" align=right nowrap title='<bean:write name="tooltip" />'>
                                                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&addId=<bean:write name="location" property="structureElementId" />'>
                                                    <bean:write name="location" property="identifier" />&nbsp;-&nbsp;
                                                    <bean:write name="tooltip" />
                                                </a>
                                                <logic:equal name="location" property="late" value="true">
                                                    <img alt="" align="top" width="15" title='<bean:write name="location" property="lateMessage"/>' src='<%=request.getContextPath()%>/images/<bean:write name="location" property="warningImage"/>.gif'>
                                                </logic:equal>
                                                <logic:equal name="location" property="late" value="false">
                                                    <span >&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                                </logic:equal>
                                            </td>
                                            <td align="center">
                                                <logic:equal name="location" property="state" value="6" >
                                                    <img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.not.plannable" />' src="<%=request.getContextPath()%>/images/purple.gif">
                                                </logic:equal>
                                                <logic:equal name="location" property="state" value="5" >
                                                    <img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.disabled" />' src="<%=request.getContextPath()%>/images/grey.gif">
                                                </logic:equal>
                                                <logic:equal name="location" property="state" value="4" >
                                                    <img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif">
                                                </logic:equal>
                                                <logic:equal name="location" property="state" value="3" >
                                                    <img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif">
                                                </logic:equal>
                                                <logic:equal name="location" property="state" value="2" >
                                                    <img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif">
                                                </logic:equal>
                                                <logic:equal name="location" property="state" value="0" >
                                                    <img height="11" alt="" align="middle" title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif">
                                                </logic:equal>
                                            </td>

                                            <td align="center" >
                                                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=1'>
                                                    <bean:write name="location" property="childNotStarted" />
                                                </a>
                                            </td>
                                            <td align="center">
                                                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=2'>
                                                    <bean:write name="location" property="childPreparing" />
                                                </a>
                                            </td>

                                            <td align="center" >
                                                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=3'>
                                                    <bean:write name="location" property="childSubmited" />
                                                </a>
                                            </td>
                                            <td align="center" >
                                                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=4'>
                                                    <bean:write name="location" property="childAgreed" />
                                                </a>
                                            </td>
                                            <td >
                                                <logic:iterate name="location" id="locationBI" property="budgetInstruction" type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO"   >
                                                    <%String locationhref = "javascript:openBI(" + locationBI.getId() + ")";%>
                                                    <html:link href="<%=locationhref%>" >
                                                        <img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif" />
                                                        <bean:write name="locationBI" property="identifier"  />
                                                    </html:link>
                                                </logic:iterate>
                                            </td>
                                        </tr>
                                    </logic:iterate>
                                </logic:iterate>

                            </table>
                            </div>
                        </td>

                    </tr>

                    <tr>
                        <td width="70%" class="groupcell" valign="top">
                            <div class="bottomScroll">
                                <jsp:include page="../system/welcomeMessages.jsp" flush="true">
                                    <jsp:param name="class" value="" />
                                </jsp:include>
                            </div>
                        </td>
                        <td width="30%" class="groupcell" valign="top">
                            <div class="bottomScroll" >
                            <table width="100%">
                                <tr>
                                    <th ><bean:message key="cp.welcome.model.budgetinstruction.list.title"/></th>
                                </tr>
                                <logic:iterate name="model" id="biEntityList" property="budgetInstruction"  type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO" indexId="biIndex" >
                                <tr>
                                    <td>
                                        <%String href = "javascript:openBI(" + biEntityList.getId() + ")";%>
                                        <html:link href="<%=href%>" >
                                            <img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif" />
                                            <bean:write name="biEntityList" property="identifier"  />
                                        </html:link>
                                    </td>
                                </tr>
                                </logic:iterate>
                            </table>
                            </div>
                        </td>
                    </tr>

				</table>
                </div>
			</logic:iterate>
		</td>
	</tr>
</table>
</logic:notEmpty>

<!-- if no models show messages -->
<logic:empty name="homeForm" property="model"  >
    <jsp:include page="../system/welcomeMessages.jsp" flush="true">
        <jsp:param name="class" value="groupCell" />
    </jsp:include>
</logic:empty >

</html:form>
</body>