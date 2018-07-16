<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<body>

<div id="mainTabContainer" dojoType="TabContainer" style="width: 100%; height: 100%" selectedChild="tab1" 
    templateCssPath="<%=request.getContextPath()%>/css/dojo/TabContainer.css">
<bean:define id="model" name="workbenchForm" property="root" type="com.cedar.cp.utc.struts.homepage.ModelDTO"/>
<bean:define id="budgetCycle" name="model" property="budgetCycle[0]" type="com.cedar.cp.utc.struts.homepage.BudgetCycleDTO"/>
<div id="tab1" dojoType="ContentPane" selected="true" label='<bean:write name="budgetCycle" property="budgetCycle"/> '>
<table width="100%">
    <tr>
        <td align="right" nowrap>
            <img height="11" width="11" hspace="2" alt="" align="absmiddle" src="<%=request.getContextPath()%>/images/red.gif"> <bean:message key="cp.approver.key.none"/>&nbsp;
            <img height="11" width="11" hspace="2" alt="" align="absmiddle" src="<%=request.getContextPath()%>/images/blue.gif"> <bean:message key="cp.approver.key.started"/>&nbsp;
            <img height="11" width="11" hspace="2" alt="" align="absmiddle" src="<%=request.getContextPath()%>/images/orange.gif"> <bean:message key="cp.approver.key.submit"/>&nbsp;
            <img height="11" width="11" hspace="2" alt="" align="absmiddle" src="<%=request.getContextPath()%>/images/green.gif"> <bean:message key="cp.approver.key.agree"/>&nbsp;
            <img height="11" width="11" hspace="2" alt="" align="absmiddle" src="<%=request.getContextPath()%>/images/purple.gif"> <bean:message key="cp.approver.key.not.plannable"/>&nbsp;
            <img height="11" width="11" hspace="2" alt="" align="absmiddle" src="<%=request.getContextPath()%>/images/grey.gif"> <bean:message key="cp.approver.key.disabled"/>&nbsp;
        </td>
    </tr>
</table>

<table width="100%" cellpadding="1" cellspacing="1">

    <tr>
        <th width="100%" colspan="7" nowrap><bean:message key="cp.welcome.bc.title"/></th>
    </tr>
    <tr>
        <th width="60%" rowspan="2" colspan="2" nowrap><bean:message key="cp.welcome.bc.title.bl"/></th>
        <th width="5%" rowspan="2" colspan="1" nowrap><bean:message key="cp.welcome.bc.title.status"/></th>
        <th width="16%" colspan="4" nowrap><bean:message key="cp.welcome.bc.title.rastats"/></th>
    </tr>
    <tr>
        <th width="4%"><img height="11" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/red.gif" title='<bean:message key="cp.approver.key.none" />'></th>
        <th width="4%"><img height="11" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/blue.gif" title='<bean:message key="cp.approver.key.started" />'></th>
        <th width="4%"><img height="11" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/orange.gif" title='<bean:message key="cp.approver.key.submit" />'></th>
        <th width="4%"><img height="11" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/green.gif" title='<bean:message key="cp.approver.key.agree" />'></th>
    </tr>

    <tr>
        <td colspan="2" align="left">
            <a href="<%=request.getContextPath()%>/budgetCycleHistoryDetails.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId"/>&pageSource=welcome">
                <bean:write name="budgetCycle" property="budgetCycle"/>
            </a>
        </td>
        <td colspan="5">&nbsp;</td>
    </tr>

    <logic:iterate name="budgetCycle" id="location" property="budgetLocations" type="com.cedar.cp.utc.struts.homepage.BudgetLocationDTO">
        <bean:define id="tooltip" name="location" property="description"></bean:define>
        <tr>
            <td colspan="2" align=right nowrap title='<bean:write name="tooltip" />'>
                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&addId=<bean:write name="location" property="structureElementId" />'>
                    <bean:write name="location" property="identifier"/>&nbsp;-&nbsp;
                    <bean:write name="tooltip"/>
                </a>
                <logic:equal name="location" property="late" value="true">
                    <img alt="" align="top" width="15" title='<bean:write name="location" property="lateMessage"/>' src='<%=request.getContextPath()%>/images/<bean:write name="location" property="warningImage"/>.gif'>
                </logic:equal>
                <logic:equal name="location" property="late" value="false">
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                </logic:equal>
            </td>
            <td align="center">
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
            </td>

            <td align="center">
                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=1'>
                    <bean:write name="location" property="childNotStarted"/>
                </a>
            </td>
            <td align="center">
                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=2'>
                    <bean:write name="location" property="childPreparing"/>
                </a>
            </td>

            <td align="center">
                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=3'>
                    <bean:write name="location" property="childSubmited"/>
                </a>
            </td>
            <td align="center">
                <a href='<%=request.getContextPath()%>/budgetCycleStatus.do?budgetCycleId=<bean:write name="budgetCycle" property="budgetCycleId" />&structureElementId=<bean:write name="location" property="structureElementId" />&stateFilter=4'>
                    <bean:write name="location" property="childAgreed"/>
                </a>
            </td>
        </tr>
    </logic:iterate>

</table>
</div>

<div id="tab2" dojoType="ContentPane" selected="false" label="Budget Instructions" href='<%=request.getContextPath()%>/budgetInstructions.do?modelId=<bean:write name="workbenchForm" property="modelId"/>&budgetCycleId=<bean:write name="workbenchForm" property="budgetCycleId"/>' style="overflow:auto;display:none">
</div>
</div>

</body>