<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<script language="JavaScript1.2">
function syncReadOnlyFields()
{
	var modelVisId = getDocumentObject("data.modelVisId");
	if(modelVisId != null)
	{
		var modelROValue = getDocumentObject("modelROValue");
		modelROValue.value = modelVisId.value;
		var financeCubeVisId = getDocumentObject("data.financeCubeVisId");
		var financeCubeROValue = getDocumentObject("financeCubeROValue");
		financeCubeROValue.value = financeCubeVisId.value;
		var budgetCycleVisId = getDocumentObject("data.budgetCycleVisId");
		var budgetCycleROValue = getDocumentObject("budgetCycleROValue");
		budgetCycleROValue.value = budgetCycleVisId.value;
	}
}

function showSpreadPage(groupNo, lineNo)
{
    getDocumentObject("currentTab").value = 2;
    getDocumentObject("currentGroup").value = groupNo;
    getDocumentObject("currentLine").value = lineNo;
    getDocumentObject("userAction").value = "showSpreadPage";
    getDocumentObject("virementDataEntryForm").submit();
}

function showTab(idx)
{
	showPanel(idx);
	getDocumentObject("currentTab").value = idx;
}

dojo.addOnLoad(function()
{
	showTab(getDocumentObject("currentTab").value);
	syncReadOnlyFields();
	recalcForm();
});

function getDocumentObjectByName(name)
{
	var items = document.getElementsByName(name);
	if(items.length > 0)
		return items[0];
	else
		return null;
}

function recalcForm()
{
	for(var groupNo = 0; ; groupNo++)
	{
		var groupNotes = getDocumentObjectByName('data.groups[' + groupNo + '].notes');
		if(groupNotes != null)
		{
			var tRemain = 0.0;
			var pRemain = 0.0;
			for(lineNo = 0; ; lineNo++)
			{
				var toValue = getDocumentObjectByName('data.groups[' + groupNo + '].lines[' + lineNo + '].to');
				var pValue = getDocumentObjectByName('data.groups[' + groupNo + '].lines[' + lineNo + '].permanentValue');
				var tValue = getDocumentObjectByName('data.groups[' + groupNo + '].lines[' + lineNo + '].temporaryValue');

				if(tValue != null && pValue != null)
				{
					pValue.value = formatNumber(pValue.value);
					tValue.value = formatNumber(tValue.value);

					if(toValue.value == "true")
					{
						tRemain = tRemain - parseNumber(tValue.value);
						pRemain = pRemain - parseNumber(pValue.value);
					}
					else
					{
						tRemain = tRemain + parseNumber(tValue.value);
						pRemain = pRemain + parseNumber(pValue.value);
					}
				}
				else
					break;
			}
			tempRemainder = getDocumentObjectByName('data.groups[' + groupNo + '].tempRemainder');
			permanentRemainder = getDocumentObjectByName('data.groups[' + groupNo + '].permanentRemainder');
			tempRemainder.value = formatNumber(tRemain);
			permanentRemainder.value = formatNumber(pRemain);
		}
		else
			break;
	}
}

function quit()
{
	loadAddress('virements.do');
}

</script>
</head>

<body>
<H5 class="header5"><bean:message key="cp.virement.view.title"/></h5>
<html:form action="viewVirement.do" styleId="virementDataEntryForm">
<nested:hidden property="currentTab" styleId="currentTab"/>
<nested:hidden property="currentGroup" styleId="currentGroup"/>
<nested:hidden property="currentLine" styleId="currentLine"/>
<nested:hidden property="userAction" styleId="userAction"/>
<nested:hidden property="data.key"/>
<nested:hidden property="data.readOnly"/>
<jsp:include page="../system/errorsInc.jsp"/>
<%--Tabbed Panel Buttons--%>
<table cellspacing="0" border="0" width="100%" cellpadding="0">
	<tr>
		<td>
			<table cellspacing="0" border="0" cellpadding="0">
				<tr>
					<td>&nbsp;</td>
					<td id='tab0' class="tabbutton" onmouseout='setState(0, null)'
						onmouseover="hover(this);" onclick='showTab(0);'>
						<bean:message key="cp.virement.view.tab0.label"/>
					</td>
					<td id='tab1' class="tabbutton" onmouseout='setState(1, null)'
						onmouseover="hover(this);" onclick='showTab(1);'>
						<bean:message key="cp.virement.view.tab1.label"/>
					</td>
					<td id='tab2' class="tabbutton" onmouseout='setState(2, null)'
						onmouseover="hover(this);" onclick='showTab(2);'>
						<bean:message key="cp.virement.view.tab2.label"/>
					</td>
					<nested:notEmpty property="data.authPoints">
						<td id='tab4' class="tabbutton" onmouseout='setState(4, null)'
							onmouseover="hover(this);" onclick='showTab(4);'>
							<bean:message key="cp.virement.view.tab4.label"/>
						</td>
					</nested:notEmpty>
				</tr>
			</table>
		</td>
	<td>
		<table cellspacing="0" border="0" width="100%" cellpadding="0">
			<tr><td align="right"><td align="right"><cp:CPButton value="Quit" onclick="javascript:quit()"/></td></tr>
		</table>
	</tr>
</table>
<%--End Tabbed Panel Buttons--%>

<%--Start of Tabbed Pane table--%>
<table class="tabgroupcell" margin="0" cellspacing="0" width="100%" height="80%">
	<tr>
		<td valign="top" align="left" width="100%">

<div id='tabpanel0' style='display:none'>
	<p><bean:message key="cp.virement.view.tab0.title"/></p>
	<table>
	<tr>
		<td align="left" nowrap valign="top"><bean:message key="cp.virement.view.tab0.reason"/></td>
		<td align="left" nowrap><nested:textarea property="data.reason" readonly="true" rows="5"
												 cols="60"/></td>
	</tr>
	<tr>
		<td align="left" nowrap><bean:message key="cp.virement.view.tab0.reference"/></td>
		<td align="left" nowrap><nested:text property="data.reference" readonly="true" size="60"/></td>
	</tr>
	</table>
</div>

<div id='tabpanel1' style='display:none'>
	<p><bean:message key="cp.virement.view.tab1.title"/></p>
	<table class="tabtable">
		<tr>
			<td align="left" nowrap><bean:message key="cp.virement.view.tab1.model"/></td>
			<td align="left" nowrap><nested:hidden property="data.modelId" styleId="data.modelId"/>
				<nested:text readonly="true" property="data.modelVisId" styleId="data.modelVisId"
							 size="40"/></td>
			<td></td>
		</tr>
		<tr>
			<td align="left" nowrap><bean:message key="cp.virement.view.tab1.finance_cube"/></td>
			<td align="left" nowrap><nested:hidden property="data.financeCubeId" styleId="data.financeCubeId"/>
			<nested:text property="data.financeCubeVisId" styleId="data.financeCubeVisId"
						 readonly="true" size="40"/></td>
			<td></td>
		</tr>
		<tr>
			<td align="left" nowrap><bean:message key="cp.virement.view.tab1.budget_cycle"/></td>
			<td align="left" nowrap><nested:hidden property="data.budgetCycleId" styleId="data.budgetCycleId"/>
				<nested:text property="data.budgetCycleVisId" styleId="data.budgetCycleVisId"
							 readonly="true" size="40"/></td>
			<td></td>
		</tr>
	</table>
</div>

<div id='tabpanel2' style='display:none'>
<p><bean:message key="cp.virement.view.tab2.title"/></p>
<table class="tabtable">
	<tr>
		<td align="left" nowrap>
			<bean:message key="cp.virement.view.tab2.model"/>
		</td>
		<td align="left" nowrap>
			<input type="text" size="40" readonly="true" value="" id="modelROValue"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap>
			<bean:message key="cp.virement.view.tab2.finance_cube"/>
		</td>
		<td align="left" nowrap>
			<input type="text" size="40" readonly="true" value="" id="financeCubeROValue"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap>
			<bean:message key="cp.virement.view.tab2.budget_cycle"/>
		</td>
		<td align="left" nowrap>
			<input type="text" size="40" readonly="true" value="" id="budgetCycleROValue"/>
		</td>
	</tr>
</table>
<table class="tabgroupcell" cellspacing="0" width="100%">
<tr>
<td valign="top">
<table class="tablepanel">
<tr>
<td>
<table cellspacing="0">
<tr>
	<td valign="top" align="left">
		<nested:notEqual value="0" name="virementDataEntryForm" property="data.modelId">
			<table cellspacing="2">
				<tr>
					<th class="sth" align="left">
					</th>
					<bean:define id="numDimsPlusTwo" name="virementDataEntryForm" property="data.numDimsPlusTwo"/>
					<logic:iterate id="header" name="virementDataEntryForm"
								   property="data.dimensionHeaders"
								   type="com.cedar.cp.api.dimension.DimensionRef">
						<th class="sth" align="left"><bean:write name="header"
																 property="narrative"/></th>
					</logic:iterate>
					<th class="sth" algin="center">Spread</th>
					<th class="sth" align="left"><bean:message key="cp.virement.view.tab2.notes_header"/></th>
					<th class="sth" align="right"><bean:message key="cp.virement.view.tab2.data_type_header"/></th>
					<th class="sth" align="right"><bean:message key="cp.virement.view.tab2.transfer_header"/></th>
					<th class="sth" align="center"><bean:message key="cp.virement.view.tab2.action_header"/></th>
				</tr>

				<nested:iterate id="groups" property="data.groups"
								type="com.cedar.cp.utc.struts.virement.VirementGroupDTO"
								indexId="groupNo">

					<nested:hidden property="key"/>

					<nested:iterate id="lines" property="lines"
									type="com.cedar.cp.utc.struts.virement.VirementLineDTO"
									indexId="lineNo">

						<tr>
							<td nowrap class="information">
								<nested:hidden property="allocationThreshold"/>
                                <nested:hidden property="key"/>
                                <nested:hidden property="spreadProfileId"/>
                                <nested:hidden property="spreadProfileVisId"/>
                                <nested:hidden property="summaryLine"/>
                                <nested:equal property="fromTo" value="To">
									<bean:message key="cp.virement.to"/>
								</nested:equal>
								<nested:equal property="fromTo" value="From">
									<bean:message key="cp.virement.from"/>
								</nested:equal>
							</td>
							<nested:iterate property="cells" type="com.cedar.cp.utc.picker.ElementDTO">
								<td nowrap title="<nested:write property="description" />">
									<nested:text size="15" property="identifier" readonly="true"/>
									<nested:hidden property="id"/>
									<nested:hidden property="structureId"/>
									<nested:hidden property="description"/>
								</td>
							</nested:iterate>
							<td align="center">
                                <nested:equal value="true" property="summaryLine">
									<% String showSpreadPageHandler = "javascript:showSpreadPage("+groupNo+","+lineNo+")"; %>
									<cp:CPButton buttonType="button" onclick="<%=showSpreadPageHandler%>">
										<nested:write property="spreadProfileVisId"/>
									</cp:CPButton>
                                    <%--<nested:iterate property="spreadProfile"--%>
                                                    <%--type="com.cedar.cp.utc.struts.virement.VirementLineSpreadDTO">--%>
                                        <%--<nested:hidden property="key"/>--%>
                                        <%--<nested:hidden property="structureElementKey"/>--%>
                                        <%--<nested:hidden property="structureElementVisId"/>--%>
                                        <%--<nested:hidden property="index"/>--%>
                                        <%--<nested:hidden property="held"/>--%>
                                        <%--<nested:hidden property="weighting"/>--%>
                                        <%--<nested:hidden property="transferValue"/>--%>
                                    <%--</nested:iterate>--%>
                                </nested:equal>
                            </td>
							<logic:equal name="lineNo" value="0">
								<td nowrap rowspan="<nested:write property="../numRows"/>">
									<nested:textarea property="../notes" readonly="true"
													 rows="<%=String.valueOf(groups.getNumRows())%>" cols="20"/>
								</td>
							</logic:equal>
							<td nowrap align="right">

								<nested:select property="dataTypeId" onchange="javascript:selectedProfile()" disabled="true">
									<nested:optionsCollection property="../../dataTypes"
													label="narrative"
													value="key"/>
								</nested:select>

							</td>
							<td nowrap align="right">
								<nested:text size="12" styleClass="inputnumber" readonly="true"
											 onchange="recalcForm(); return true;"
											 property="transferValue"/>
							</td>
							<td nowrap>
							</td>
						</tr>
					</nested:iterate>
					<tr>
						<td colspan="<bean:write name="numDimsPlusTwo"/>">
						</td>
						<td></td>
						<td class="information" align="right">
							Remainder:
						</td>
						<td class="information" align="right">
							<nested:text property="remainder" size="12" styleClass="inputnumber"
										 readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</nested:iterate>
			</table>

		</nested:notEqual>
	</td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</div>

<nested:notEmpty property="data.authPoints">
<div id='tabpanel4' style='display:none'>
	<p>Previous authorisation details</p>
	<table width="100%">
		<tr>
			<th>Available Authorisers</th>
			<th>Budget Location</th>
			<th>Transfer Rows</th>
			<th>Actioned By</th>
			<th>Notes</th>
			<th>Status</th>
		</tr>
		<nested:iterate property="data.authPoints" id="authPoint" indexId="authPointIndex"
						type="com.cedar.cp.utc.struts.virement.VirementAuthPointDTO">
			<nested:hidden property="keyText"/>
			<tr>
				<td>
					<div>
					<nested:iterate property="availableAuthorisers" id="aAuth" indexId="aAuthIdx">
							<nested:write property="narrative"/><br>
                            <nested:hidden property="key"/>
                            <nested:hidden property="narrative"/>
                    </nested:iterate>
					</div>
				</td>
				<td>
                    <nested:nest property="budgetLocation">
                        <nested:write property="identifier"/>
                        <nested:hidden property="key"/>
                        <nested:hidden property="identifier"/>
                    </nested:nest>
                </td>
				<td>
					<div>
                        <nested:iterate property="lines" id="line" indexId="lineIdx">
                            <nested:write property="identifier"/><br>
                            <nested:hidden property="key"/>
                            <nested:hidden property="identifier"/>
                        </nested:iterate>
					</div>
				</td>
				<td>
                    <nested:nest property="authUser">
                        <nested:write property="narrative"/>
                        <nested:hidden property="key"/>
                        <nested:hidden property="narrative"/>
                    </nested:nest>
                </td>
				<td>
					<nested:textarea property="notes" cols="40" rows="3" readonly="true"/>
				</td>
				<td>
					<nested:write property="statusText"/>
				</td>
                <nested:hidden property="userCanAuth"/>
                <nested:hidden property="status"/>
            </tr>
		</nested:iterate>
	</table>
</div>
</nested:notEmpty>


</td>
</tr>
</table>
</html:form>
</body>

