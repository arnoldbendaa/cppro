<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"%>

<head>
<script language="JavaScript1.2" type="text/javascript">
function viewDetail(id)
{
    getDocumentObject('selectedActivityId').value = id;

    var form = getDocumentObject('cycleActivityForm');
    form.action = "<%=request.getContextPath()%>/budgetCycleActivityDetail.do";
    form.submit();
}

function selectCostCentre()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=120,top=120';
	target = '<%=request.getContextPath()%>/popupCostCentreSelector.do?modelId=<bean:write name="cycleActivityForm" property="modelDTO.id" />';
	window.open(target, '_blank', params);
	//window.open(target, '_selectCC', params);
}

function setCCId(id)
{
    getDocumentObject('CCId').value = id;
    var form = getDocumentObject('cycleActivityForm');
    form.submit();
}


function budgetStatus(structureElement, state, addId, oldId)
{
    var form;
    var addIdField;
    var oldIdField;

    getDocumentObject('struc_id').value = structureElement;

    addIdField = getDocumentObject('addId');
    if(addId == null)
        addId = "";
    addIdField.value = addId;
    oldIdField = getDocumentObject('oldId');
    if(oldId == null)
        oldId = "";
    oldIdField.value = oldId;

    form = getDocumentObject('cycleActivityForm');
    form.action = "<%=request.getContextPath()%>/budgetCycleStatus.do";
    form.submit();
}

function loadList()
{
    loadAddress('budgetCycles.do');
}
</script>
</head>

<body>
<p class="header5"><bean:message key="cp.cycle.activity.title" /> <!-- crumbs -->
<bean:define id="crumbsize" name="cycleActivityForm"
	property="crumbSize" /> <logic:greaterThan value="0" name="crumbsize">
	<br />
	<logic:iterate id="crumb" name="cycleActivityForm" property="crumbs"
		type="com.cedar.cp.utc.struts.approver.CrumbDTO" indexId="crumId"
		length="crumbsize">
    &nbsp; /
    <a class="smalllink"
			href='javascript:budgetStatus(<bean:write name="crumb" property="structureElementId" />, null, null, <bean:write name="crumb" property="structureElementId" /> )'
			title='<bean:write name="crumb" property="description" />'> <bean:write
			name="crumb" property="visId" /> </a>
	</logic:iterate>
</logic:greaterThan></p>

<html:form action="budgetCycleActivityList" styleId="cycleActivityForm">

	<table width="98%">
		<tr>
			<td width="20%"><bean:message key="cp.cycle.activity.title.model" /></td>
			<td width="78%" colspan="2"><H5><bean:write name="cycleActivityForm" property="modelDTO" /></H5></td>
		</tr>
		<tr>
			<td width="20%"><bean:message key="cp.cycle.activity.title.cycle" /></td>
			<td width="78%" colspan="2"><H5><bean:write name="cycleActivityForm" property="budgetCycle" /></H5></td>
		</tr>
		<tr>
			<td width="20%"><bean:message key="cp.cycle.activity.title.ba" /></td>

			<td width="58%"><html:hidden property="CCId" styleId="CCId" /> <logic:notEqual
				value="0" name="cycleActivityForm" property="CCId">
				<H5><bean:write name="cycleActivityForm" property="CCElement" /></H5>
			</logic:notEqual> <logic:equal value="0" name="cycleActivityForm" property="CCId">
				<cp:CPButton onclick="selectCostCentre()">
					<bean:message key="cp.cycle.activity.title.cc.select" />
				</cp:CPButton>
			</logic:equal></td>

			<td width="20%" align="right"><logic:notEqual value="0" name="cycleActivityForm"
				property="CCId">
				<cp:CPButton onclick="selectCostCentre()">
					<bean:message key="cp.cycle.activity.title.cc.change" />
				</cp:CPButton>
			</logic:notEqual></td>
		</tr>
	</table>

	<logic:equal value="budgetCycleList" name="cycleActivityForm"
		property="pageSource">
		<table width="98%">
			<tr>
				<td align="right"><cp:CPButton onclick="loadList()">
					<bean:message key="cp.performanceType.back" />
				</cp:CPButton>
				</td>
			</tr>
		</table>
	</logic:equal>

	<br />

	<table width="98%" cellspacing="0" border="0" align="center">
		<tr>
			<th class="groupcell" colspan="2"><bean:message
				key="cp.cycle.activity.table.th1" /></th>
			<th class="groupcell"><bean:message
				key="cp.cycle.activity.table.th2" /></th>
			<th class="groupcell" colspan="1"><bean:message
				key="cp.cycle.activity.table.th3" /></th>
			<th class="groupcell"><bean:message
				key="cp.cycle.activity.table.th4" /></th>
			<th class="groupcell"><bean:message
				key="cp.cycle.activity.table.th5" /></th>
		</tr>

		<logic:iterate id="activity" name="cycleActivityForm"
			property="activity"
			type="com.cedar.cp.utc.struts.cycleactivity.ActivityDTO">
			<tr>
				<td><img alt=""
					src="<%=request.getContextPath()%>/images/note.gif"
					style="cursor: pointer"
					onclick='viewDetail( <bean:write name="activity" property="activityId" /> )' /></td>
				<td nowrap="true"><bean:write name="activity" property="userId" /></td>
				<td nowrap="true"><bean:write name="activity" property="fullName" /></td>
				<td nowrap="true"><bean:write name="activity" property="date"
					formatKey="cp.date.format.time" /></td>
				<td nowrap="true"><bean:message name="activity" property="typeKey" /></td>
				<td width="60%"><bean:write name="activity"
					property="description" /></td>
			</tr>
		</logic:iterate>
	</table>

	<html:hidden property="selectedActivityId" styleId="selectedActivityId" />
	<bean:define id="bc" name="cycleActivityForm" property="budgetCycle"
		type="com.cedar.cp.utc.picker.ElementDTO" />
	<input type="hidden" name="selectedBudgetCycleId"
		value="<%=bc.getId()%>" />
	<input type="hidden" name="selectedBudgetCycleIdentifier"
		value="<%=bc.getIdentifier()%>" />
	<input type="hidden" name="selectedBudgetCycleDescription"
		value="<%=bc.getDescription()%>" />

	<logic:notEmpty name="cycleActivityForm" property="CCElement">
		<bean:define id="cc" name="cycleActivityForm" property="CCElement"
			type="com.cedar.cp.utc.picker.ElementDTO" />
		<input type="hidden" name="selectedCostCentreId"
			value="<%=cc.getId()%>" />
		<input type="hidden" name="selectedCostCentreIdentifier"
			value="<%=cc.getIdentifier()%>" />
		<input type="hidden" name="selectedCostCentreDescription"
			value="<%=cc.getDescription()%>" />
	</logic:notEmpty>

	<html:hidden property="budgetCycleId" />
	<html:hidden property="pageSource" />
	<html:hidden property="oldUserCount" />
	<html:hidden property="oldDepth" />
	<!-- hiden fields for budget status page -->
	<html:hidden property="addId" styleId="addId" />
	<html:hidden property="oldId" styleId="oldId" />
	<html:hidden property="structureElementId" styleId="struc_id" />
	<br />
	<html:hidden property="structureElementList"
		styleId="structureElementList" />
	<html:hidden property="visIdList" styleId="visIdList" />
	<html:hidden property="oldDepthList" styleId="oldDepthList" />
	<html:hidden property="oldUserCountList" styleId="oldUserCountList" />
	<html:hidden property="descriptionList" styleId="descriptionList" />

</html:form>

</body>