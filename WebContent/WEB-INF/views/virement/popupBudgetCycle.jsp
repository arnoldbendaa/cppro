<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title><bean:message key="cp.virement.popup.budgetcycle.header.title" /></title>
<bean:define id="listSize" type="Integer" property="size" name="virementBudgetCycleForm"></bean:define>
<script language="javascript" >
function returnValue()
{
	var l_selectedRow;
	var l_selectedModelId;
	var l_selectedModelVisId;
	var l_selectedFinanceCubeId;
	var l_selectedFinanceCubeVisId;
	var l_selectedBudgetCycleId;
	var l_selectedBudgetCycleVisId;

	for( var i=0 ; i < <%=listSize%> ; i++ )
	{
		l_selectedRow = getDocumentObject("select_" + i);
		if(l_selectedRow.checked)
		{
        	l_selectedModelId = getDocumentObject("selected_modelId_" + i).value;
        	l_selectedModelVisId = getDocumentObject("selected_modelVisId_" + i).value;
			l_selectedFinanceCubeId = getDocumentObject("selected_financeCubeId_" + i).value;
			l_selectedFinanceCubeVisId = getDocumentObject("selected_financeCubeVisId_" + i).value;
			l_selectedBudgetCycleId = getDocumentObject("selected_budgetCycleId_" + i).value;
			l_selectedBudgetCycleVisId = getDocumentObject("selected_budgetCycleVisId_" + i).value;

			window.opener.setModel( l_selectedModelId, l_selectedModelVisId );
			window.opener.setFinanceCube( l_selectedFinanceCubeId, l_selectedFinanceCubeVisId );
			window.opener.setBudgetCycle( l_selectedBudgetCycleId, l_selectedBudgetCycleVisId );

			break;
		}
	}

	self.close();
}

function singleSelect(newSelection)
{
    var select_obj;
	for( var i=0 ; i < <%=listSize%> ; i++ )
	{
        if(i == newSelection)
            continue;

        select_obj = getDocumentObject("select_" + i);
        select_obj.checked = false;
    }
}
</script>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

    <div dojoType="dijit.layout.ContentPane" region="center" widgetId="userSelectClient"  style="padding-left:20px;padding-right:18px;overflow:auto;">

<!--<form id="userList">-->
			<table width="100%">
				<tr>
					<th>
						<bean:message key="cp.virement.popup.budget_cycle.selected"/>
					</th>
					<th>
						<bean:message key="cp.virement.popup.budget_cycle.model" />
					</th>
					<th>
						<bean:message key="cp.virement.popup.budget_cycle.finance_cube" />
					</th>
					<th>
						<bean:message key="cp.virement.popup.budgetcycl.budget_cycle" />
					</th>
					<th>
						<bean:message key="cp.virement.popup.budgetcycl.budget_cycle_descr" />
					</th>
				</tr>
<!--</form>-->
<logic:iterate id="line" name="virementBudgetCycleForm" property="budgetCycles"
			   type="com.cedar.cp.utc.struts.virement.VirementBudgetCycleDTO" indexId="rowIndex" >
	<tr>
		<td align="center">
			<input type="checkbox" id="select_<%=rowIndex%>" name="select_<%=rowIndex%>" onclick="singleSelect(<%=rowIndex%>)" />
		</td>
		<td>
            <bean:write name="line" property="modelVisId" />
		</td>
		<td>
            <bean:write name="line" property="financeCubeVisId" />
		</td>
		<td>
			<bean:write name="line" property="budgetCycleVisId"/>
		</td>
		<td>
			<bean:write name="line" property="budgetCycleDescription"/>
			<input type="hidden" id='<%="selected_modelId_" + rowIndex %>' value='<%=line.getModelId()%>' name='<%="selected_modelId_" + rowIndex %>'/>
			<input type="hidden" id='<%="selected_modelVisId_" + rowIndex %>' value='<%=line.getModelVisId()%>' name='<%="selected_modelVisId_" + rowIndex %>'/>
			<input type="hidden" id='<%="selected_financeCubeId_" + rowIndex %>' value='<%=line.getFinanceCubeId()%>' name='<%="selected_financeCubeId_" + rowIndex %>'/>
			<input type="hidden" id='<%="selected_financeCubeVisId_" + rowIndex %>' value='<%=line.getFinanceCubeVisId()%>' name='<%="selected_financeCubeVisId_" + rowIndex %>'/>
			<input type="hidden" id='<%="selected_budgetCycleId_" + rowIndex %>' value='<%=line.getBudgetCycleId()%>' name='<%="selected_budgetCycleId_" + rowIndex %>'/>
			<input type="hidden" id='<%="selected_budgetCycleVisId_" + rowIndex %>' value='<%=line.getBudgetCycleVisId()%>' name='<%="selected_budgetCycleVisId_" + rowIndex %>'/>
		</td>
	</tr>
</logic:iterate>
<logic:empty name="virementBudgetCycleForm" property="budgetCycles" >
	<tr>
		<td colspan="5"><bean:message key="cp.virement.no_budget_cycles"/></td>
	</tr>
</logic:empty>
			</table>

	</div>

	<div dojoType="dijit.layout.ContentPane" region="bottom" widgetId="userSelectFooter" style="padding-left:20px">
		<table width="100%">
			<tr>
				<td colspan="2" width="60%" nowrap="true">&nbsp;</td>
				<td align="right" nowrap="true">
					<cp:CPButton buttonType="button" onclick="returnValue()">
						<bean:message key="cp.commumication.message.label.ok"/>
					</cp:CPButton>
					<cp:CPButton buttonType="button" onclick="self.close()">
						<bean:message key="cp.commumication.message.label.close"/>
					</cp:CPButton>
				</td>
				<td align="center">
					&nbsp;
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
