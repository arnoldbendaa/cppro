<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
<script language="JavaScript1.2" >
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
    getDocumentObject('cycleActivityForm').submit();
}

function viewBudgetTransferResult(virementTransferId, budgetCycleId)
{
	var form;
	form = getDocumentObject('cycleActivityForm');
    form.action = "<%=request.getContextPath()%>/viewVirementQuery.do?requestId="+virementTransferId +"&budgetCycleId=" +budgetCycleId;
    form.submit();
}


</script>
</head>

<body>
<p class="header5" ><bean:message key="cp.cycle.activity.title.details" />
<!-- crumbs -->
<bean:define id="crumbsize" name="cycleActivityForm" property="crumbSize" />
<logic:greaterThan value="0" name="crumbsize">
<br/>
<logic:iterate id="crumb" name="cycleActivityForm" property="crumbs"  type="com.cedar.cp.utc.struts.approver.CrumbDTO" indexId="crumId" length="crumbsize" >
    &nbsp; /
    <a class="smalllink" href='javascript:budgetStatus(<bean:write name="crumb" property="structureElementId" />, null, null, <bean:write name="crumb" property="structureElementId" /> )' title='<bean:write name="crumb" property="description" />'>
        <bean:write name="crumb" property="visId" />
    </a>
</logic:iterate>
</logic:greaterThan >
</p>

<html:form action="budgetCycleActivityList" styleId="cycleActivityForm" >

<table width="100%">
    <tr>
        <td><bean:message key="cp.cycle.activity.title.model"/></td>
        <td><H5><bean:write name="cycleActivityForm" property="modelDTO" /></H5></td>
    </tr>
    <tr>
        <td><bean:message key="cp.cycle.activity.title.cycle"/></td>
        <td><H5><bean:write name="cycleActivityForm" property="budgetCycle" /></H5></td>
    </tr>
    <tr>
        <td><bean:message key="cp.cycle.activity.title.ba"/></td>
        <td><H5><bean:write name="cycleActivityForm" property="CCElement" /></H5></td>
    </tr>
    <tr>
        <td><bean:message key="cp.cycle.activity.title.description" /></td>
        <td><H5><bean:write name="cycleActivityForm" property="selectedActivity.description"  /></H5></td>
    </tr>
    <tr>
        <td><bean:message key="cp.cycle.activity.title.created"/></td>
        <td><H5><bean:write name="cycleActivityForm" property="selectedActivity.date"  /></H5></td>
    </tr>
	<tr>
        <td><bean:message key="cp.cycle.activity.title.user"/></td>
        <td><H5><bean:write name="cycleActivityForm" property="selectedActivity.userId"  /> -
			<bean:write name="cycleActivityForm" property="selectedActivity.fullName"  /></H5>
		</td>
    </tr>
	<logic:equal name="cycleActivityForm" property="selectedActivity.undoEntry" value="true">
	<tr>
		<td colspan="2">
			<b>
			<bean:message key="cp.cycle.activity.title.undo_entry"/>
			</b>
		</td>
	</tr>
	</logic:equal>
	<tr>
		<td colspan="3">
		<table width="98%">
            <tr>
                <td align="right" width="98%">
                    <cp:CPButton onclick="loadList()" ><bean:message key="cp.performanceType.back" /></cp:CPButton>
                </td>
            </tr>
        </table>
		</td>
	</tr>
</table>

<bean:write name="cycleActivityForm" property="selectedActivity.transformDetails" filter="false" />

<logic:greaterThan name="cycleActivityForm" property="selectedActivity.ownerId"  value="-1">
<logic:equal name="cycleActivityForm" property="selectedActivity.type"  value="3">
<br />
<table width="98%">
            <tr>            	
                <td align="left" width="98%">
                    <a class="smalllink" href='javascript:viewBudgetTransferResult(<bean:write name="cycleActivityForm" property="selectedActivity.ownerId"  />,<bean:write name="cycleActivityForm" property="budgetCycleId"  />)' title='<bean:message key="cp.cycle.activity.viewBudgetTransferResult" />'>
        				<bean:message key="cp.cycle.activity.viewBudgetTransferResult" />        				
        			</a>                                    
                </td>
            </tr>
        </table>
</logic:equal>
</logic:greaterThan>


<html:hidden property="budgetCycleId" />
<html:hidden property="CCId" />
<!-- hiden fields for budget status page -->
<html:hidden property="addId" styleId="addId" />
<html:hidden property="oldId" styleId="oldId" />
<html:hidden property="structureElementId" styleId="struc_id" />
<html:hidden property="oldUserCount" />
<html:hidden property="oldDepth" />
<br/>
<html:hidden property="structureElementList" styleId="structureElementList"/>
<html:hidden property="visIdList" styleId="visIdList"/>
<html:hidden property="oldDepthList" styleId="oldDepthList"/>
<html:hidden property="oldUserCountList"  styleId="oldUserCountList"/>
<html:hidden property="descriptionList" styleId="descriptionList"/>

</html:form>

</body>