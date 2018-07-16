<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<body >
<H5 class="header5"><bean:message key="cp.cycle.history.title" /></H5>

<table border="1" width="90%" align="center" >
<tr>
    <th><bean:message key="cp.cycle.history.modelid.title" /></th>
    <th><bean:message key="cp.cycle.history.id.title" /></th>
	<th><bean:message key="cp.cycle.history.description.title" /></th>
    <th><bean:message key="cp.cycle.history.status.title" /></th>
    <th><bean:message key="cp.cycle.history.action.title" /></th>
</tr>

<logic:iterate name="cycleHistoryForm" id="budgetCycle" property="budgetCycles" type="com.cedar.cp.utc.struts.cyclehistory.BudgetCycleDeatailedDTO" indexId="rowIndex" >
<tr>
    <td>
        <bean:write name="budgetCycle" property="modelIdentifier" />
    </td>
    <td>
        <bean:write name="budgetCycle" property="identifier" />
    </td>
    <td>
        <bean:write name="budgetCycle" property="description" />
    </td>
    <td>
        <bean:message name="budgetCycle" property="statusKey" />
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/budgetCycleHistoryDetails.do?budgetCycleId=<bean:write name="budgetCycle" property="id"/>&pageSource=budgetCycleList"><bean:message key="cp.cycle.history.action.history"/></a>
        &nbsp;
        <a href="<%=request.getContextPath()%>/budgetCycleActivityList.do?budgetCycleId=<bean:write name="budgetCycle" property="id"/>&pageSource=budgetCycleList"><bean:message key="cp.cycle.history.action.activity"/></a>
    </td>
</tr>
</logic:iterate>

</table>

</body>
