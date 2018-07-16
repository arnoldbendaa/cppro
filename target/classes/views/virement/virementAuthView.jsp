<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>
<html:base/>
<script language="JavaScript1.2">
function showSpreadPage(groupNo, lineNo)
{
    dojo.byId("currentTab").value = 1;
    dojo.byId("currentGroup").value = groupNo;
    dojo.byId("currentLine").value = lineNo;
    dojo.byId("userAction").value = "showSpreadPage";
    dojo.byId("virementAuthEntryForm").submit();
}

function recalcForm()
{
	for(var groupNo = 0; ; groupNo++)
	{
		var groupNotes = getDocumentObjectByName('data.groups[' + groupNo + '].notes');
		if(groupNotes != null)
		{
			var lRemain = 0.0;
			for(lineNo = 0; ; lineNo++)
			{
				var toValue = getDocumentObjectByName('data.groups[' + groupNo + '].lines[' + lineNo + '].to');
				var lValue = getDocumentObjectByName('data.groups[' + groupNo + '].lines[' + lineNo + '].transferValue');

				if(lValue != null)
				{
					lValue.value = formatNumber(lValue.value);

					if(toValue.value == "true")
					{
						lRemain = lRemain - parseNumber(lValue.value);
					}
					else
					{
						lRemain = lRemain + parseNumber(lValue.value);
					}
				}
				else
					break;
			}
			tempRemainder = getDocumentObjectByName('data.groups[' + groupNo + '].remainder');
			tempRemainder.value = formatNumber(lRemain);
		}
		else
			break;
	}
}

function showTab(idx)
{
	showPanel(idx);
	dojo.byId("currentTab").value = idx;
}

dojo.addOnLoad(function()
{
//	showTab(getDocumentObject("currentTab").value);
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
function authPoint( authPointKey )
{
	dojo.byId("currentAuthPointKey").value = authPointKey;
	dojo.byId("userAction").value = "authorise";
	dojo.byId("virementAuthEntryForm").submit();
}
function rejectPoint( authPointKey )
{
    dojo.byId("currentAuthPointKey").value = authPointKey;
	dojo.byId("userAction").value = "reject";
	dojo.byId("virementAuthEntryForm").submit();
}
function quit()
{
	loadAddress("virements.do");
}
</script>
</head>
<body>

<div dojoType="dijit.layout.BorderContainer" gutters="false" style="width: 500px; height: 300px;">
	<div dojoType="dijit.layout.ContentPane" region="top">
	<div style="width:100%;text-align:right;">
	<cp:CPButton value="Quit" onclick="javascript:quit()"/>
	</div>
	<H5 class="header5"><bean:message key="cp.virement.auth.title"/></h5>
	<jsp:include page="../system/errorsInc.jsp" />
</div>
	<div dojoType="dijit.layout.ContentPane" region="center">

    <div id="mainTabContainer" region="centre" dojoType="dijit.layout.TabContainer" style="width:500px;height:100px">

        <div id="tabpanel0" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.virement.auth.tab0.label"/>' selected="true">

        <html:form action="/authVirement" styleId="virementAuthEntryForm1" >

			<p><bean:message key="cp.virement.auth.tab0.title"/></p>
		<table>
		<tr>
			<td align="left" nowrap valign="top"><bean:message key="cp.virement.auth.tab0.transfer_id"/></td>
			<td align="left" nowrap><nested:text property="data.transferId" size="20" readonly="true"/></td>
		</tr>
		<tr>
			<td align="left" nowrap valign="top"><bean:message key="cp.virement.auth.tab0.owner"/></td>
			<td align="left" nowrap><nested:text property="data.owner" size="20" readonly="true"/></td>
		</tr>
		<tr>
			<td align="left" nowrap valign="top"><bean:message key="cp.virement.auth.tab0.reason"/></td>
			<td align="left" nowrap><nested:textarea property="data.reason" rows="5" cols="60" readonly="true"/></td>
		</tr>
		<tr>
			<td align="left" nowrap><bean:message key="cp.virement.auth.tab0.reference"/></td>
			<td align="left" nowrap><nested:text property="data.reference" size="60" readonly="true"/></td>
		</tr>
	</table>

        </html:form>

		</div>

        <div id="tabpanel1" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.virement.auth.tab1.label"/>'>

        <html:form action="/authVirement" styleId="virementAuthEntryForm2" >

			 <p><bean:message key="cp.virement.auth.tab1.title"/></p>

			<table class="tabtable" width="100%">
	<tr>
		<td align="left" nowrap><bean:message key="cp.virement.auth.tab1.model"/></td>
		<td align="left" nowrap><nested:hidden property="data.modelId" styleId="data.modelId"/>
			<nested:text readonly="true" property="data.modelVisId" styleId="data.modelVisId"
						 size="40"/></td>
	</tr>
	<tr>
		<td align="left" nowrap><bean:message key="cp.virement.auth.tab1.finance_cube"/></td>
		<td align="left" nowrap><nested:hidden property="data.financeCubeId" styleId="data.financeCubeId"/>
			<nested:text property="data.financeCubeVisId" styleId="data.financeCubeVisId"
						 readonly="true" size="40"/></td>
	</tr>
	<tr>
		<td align="left" nowrap><bean:message key="cp.virement.auth.tab1.budget_cycle"/></td>
		<td align="left" nowrap><nested:hidden property="data.budgetCycleId" styleId="data.budgetCycleId"/>
			<nested:text property="data.budgetCycleVisId" styleId="data.budgetCycleVisId"
						 readonly="true" size="40"/></td>
	</tr>
</table>
			<table class="tabgroupcell" cellspacing="0" width="100%">
<tr>
<td valign="top">
<table class="tablepanel" width="100%">
<tr>
	<td>
        <table cellspacing="0" width="100%">
			<tr>
				<td valign="top" align="left">
					<table cellspacing="2">
						<tr>
							<th class="sth" align="left">
							</th>
							<bean:define id="numDimsPlusTwo" name="virementAuthEntryForm"
										 property="data.numDimsPlusTwo"/>
							<logic:iterate id="header" name="virementAuthEntryForm"
										   property="data.dimensionHeaders"
										   type="com.cedar.cp.api.dimension.DimensionRef">
								<th class="sth" align="left"><bean:write name="header"
																		 property="narrative"/></th>
							</logic:iterate>
							<th class="sth" algin="center">Spread</th>
							<th class="sth" align="left">
								<bean:message key="cp.virement.auth.tab2.notes_header"/>
							</th>
							<th class="sth" align="right">
								<bean:message key="cp.virement.auth.tab2.data_type_header"/>
							</th>
							<th class="sth" align="right">
								<bean:message key="cp.virement.auth.tab2.transfer_header"/>
							</th>
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
									<nested:iterate property="cells"
													type="com.cedar.cp.utc.picker.ElementDTO">
										<td nowrap title="<nested:write property="description" />">
											<nested:text size="15" property="identifier" readonly="true"/>
											<nested:hidden property="id"/>
											<nested:hidden property="structureId"/>
											<nested:hidden property="description"/>
										</td>
									</nested:iterate>
									<td align="center">
                                        <nested:equal value="true" property="summaryLine">
											<% String showSpreadPage = "javascript:showSpreadPage("+groupNo+","+lineNo+")"; %>
											<cp:CPButton buttonType="button" onclick="<%=showSpreadPage%>">
												<nested:write property="spreadProfileVisId"/>
											</cp:CPButton>
                                        </nested:equal>
                                    </td>
									<logic:equal name="lineNo" value="0">
										<td nowrap rowspan="<nested:write property="../numRows"/>">
											<nested:textarea property="../notes"
															 rows="<%=String.valueOf(groups.getNumRows())%>" cols="20"
															 readonly="true"/>
										</td>
									</logic:equal>
									<td>
										<nested:write property="dataTypeVisId"/>
									</td>
									<td nowrap align="right">
										<nested:hidden property="to"/>
										<nested:text size="12" styleClass="inputnumber"
													 property="transferValue" readonly="true"/>
									</td>
								</tr>
							</nested:iterate>
							<tr>
								<td colspan="<bean:write name="numDimsPlusTwo"/>">
								</td>
								<td/>
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
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
</td>
</tr>
</table>

        </html:form>

		</div>

        <div id="tabpanel2" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.virement.auth.tab2.label"/>'>

        <html:form action="/authVirement" styleId="virementAuthEntryForm" >

            <nested:hidden property="currentTab" styleId="currentTab"/>
            <nested:hidden property="currentAuthPointKey" styleId="currentAuthPointKey"/>
            <nested:hidden property="currentGroup" styleId="currentGroup"/>
            <nested:hidden property="currentLine" styleId="currentLine"/>
            <nested:hidden property="userAction" styleId="userAction"/>
            <nested:hidden property="data.key"/>
            <nested:hidden property="data.readOnly"/>
            <nested:hidden property="parentPage"/>

			<p><bean:message key="cp.virement.auth.tab2.title"/></p>
			<table width="100%">
				<tr>
					<th>Available Authorisers</th>
					<th>Budget Location</th>
					<th>Transfer Rows</th>
					<th>Actioned By</th>
					<th>Notes</th>
					<th>Status</th>
					<th>Action</th>
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
							<nested:textarea property="notes" cols="40" rows="3"/>
						</td>
						<td>
							<nested:write property="statusText"/>
						</td>
						<td>
							<nested:equal value="true" property="userCanAuth">
								<%
									String authPointClickHandler = "javascript:authPoint('"+authPoint.getKeyText()+"')";
									String rejectPointClickHandler = "javascript:rejectPoint('"+authPoint.getKeyText()+"')";
								%>
								<cp:CPButton buttonType="button" onclick="<%=authPointClickHandler%>" value="Authorise"/>
								<cp:CPButton buttonType="button" onclick="<%=rejectPointClickHandler%>" value="Reject"/>
							</nested:equal>
						</td>
						<nested:hidden property="userCanAuth"/>
						<nested:hidden property="status"/>
					</tr>
				</nested:iterate>
			</table>

        </html:form>

		</div>

	</div>

    </div>
</div>


</body>

