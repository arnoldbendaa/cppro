<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>
<script language="JavaScript1.2">
function quit()
{
	loadAddress("mainMenu.do");
}
function getDocumentObjectByName(name)
{
	var items = document.getElementsByName(name);
	if(items.length > 0)
		return items[0];
	else
		return null;
}
function next()
{
	var lineNo;

	for( lineNo=0; ; lineNo++ )
	{
		var lineSelectedInd = getDocumentObjectByName('modelsAndFinanceCubes['+lineNo+'].sel');
		var lineModelId = getDocumentObjectByName('modelsAndFinanceCubes['+lineNo+'].modelId');
		var lineFinanceCubeId = getDocumentObjectByName('modelsAndFinanceCubes['+lineNo+'].financeCubeId');

		if( lineSelectedInd == null )
			break;

		if( lineSelectedInd.checked )
		{
			getDocumentObjectByName('modelId').value = lineModelId.value;
			getDocumentObjectByName('financeCubeId').value = lineFinanceCubeId.value;
			break;
		}
	}

	if( getDocumentObjectByName('modelId').value == 0 )
		alert('A finance cube must be selected before you continue.');
	else
		getDocumentObject("virementQueryForm").submit();
}
function singleSelect( rowIndex )
{
	for( lineNo=0; ; lineNo++ )
	{
		var lineSelectedInd = getDocumentObjectByName('modelsAndFinanceCubes['+lineNo+'].sel');
		if( lineSelectedInd == null )
			break;
		if( lineNo != rowIndex )
			lineSelectedInd.checked = false;
	}
}
function getValuesFromApplet()
{
	var delim = "_*_";
	var applet = getApplet_byId('picker');
	var headers = "";
	var element_ids = "";
	var structure_ids = "";
	var element_identifier = "";
	var element_description = "";
	var selectedDataType;
	var okToCheck = true;

	var selected_obj;
	var selected_header;
	var selected_id;
	var selected_structure_id;
	var selected_identifier;
	var selected_description;
	var count = 0;
	var noOfDims = <bean:write name="virementQueryForm" property="noOfDims"/>;

	applet.setAllowRootCalendarSelection(true); 

	while (count < noOfDims)
	{
		selected_id = applet.getSelectedId(count);
		selected_structure_id = applet.getSelectedStructureId(count);
		selected_identifier = applet.getSelectedIdentifier(count);
		selected_description = applet.getSelectedIdentifier(count, true);
		selected_header = applet.getSelectedHeader(count);
		selected_leaf = applet.getSelectedLeaf(count);

		if (headers != "")
		{
			headers = headers + delim + selected_header;
			element_ids = element_ids + delim + selected_id;
			structure_ids = structure_ids + delim + selected_structure_id;
			element_identifier = element_identifier + delim + selected_identifier;
			element_description = element_description + delim + selected_description;
		}
		else
		{
			headers = headers + selected_header;
			element_ids = element_ids + selected_id;
			structure_ids = structure_ids + selected_structure_id;
			element_identifier = element_identifier + selected_identifier;
			element_description = element_description + selected_description;
		}

		count++;
	}
	if (okToCheck)
	{
		getDocumentObject('headers').value = headers;
		getDocumentObject('selectedIds').value = element_ids;
		getDocumentObject('selectedStructureIds').value = structure_ids;
		getDocumentObject('selectedIdentifiers').value = element_identifier;
		getDocumentObject('selectedDescriptions').value = element_description;
		getDocumentObject('userAction').value = 'executeQuery';
		getDocumentObject('virementQueryForm').submit();
	}
}
</script>
</head>
<body>
<H5 class="header5"><bean:message key="cp.virement.query.title"/></h5>
<html:form action="virementQuery.do" styleId="virementQueryForm" method="POST">
<jsp:include page="../system/errorsInc.jsp"/>

	<!-- The user must first select a finance cube to query virements -->

	<nested:hidden property="modelId"/>
	<nested:hidden property="financeCubeId"/>

<!--<div dojoType="dijit.layout.BorderContainer" class="main">-->
	<logic:notEmpty name="virementQueryForm" property="modelsAndFinanceCubes">

	<div dojoType="dijit.layout.ContentPane" region="center" widgetId="userSelectClient" style="padding-left:20px;padding-right:18px;overflow:auto;">

		<table width="100%">
			<tr>
				<th>
					<bean:message key="cp.virement.query.mandf.selected"/>
				</th>
				<th>
					<bean:message key="cp.virement.query.mandf.model"/>
				</th>
				<th>
					<bean:message key="cp.virement.query.mandf.finance_cube"/>
				</th>
			</tr>
		<nested:iterate id="line" name="virementQueryForm" property="modelsAndFinanceCubes"
					   type="com.cedar.cp.utc.struts.virement.VirementFinanceCubeDTO" indexId="rowIndex" >
			<tr>
				<td align="center">
					<input type="checkbox"
						   name="modelsAndFinanceCubes[<%=rowIndex%>].sel"
						   onclick="singleSelect(<%=rowIndex%>)"/>
				</td>
				<td>
					<nested:hidden property="modelId"/>
					<bean:write name="line" property="modelVisId" />
				</td>
				<td>
					<nested:hidden property="financeCubeId"/>
					<bean:write name="line" property="financeCubeVisId" />
				</td>
			</tr>
		</nested:iterate>
		</table>

	</div>

		<div dojoType="dijit.layout.ContentPane" region="bottom" widgetId="userSelectFooter" style="padding-left:20px">
			<table width="100%">
				<tr align="right">
					<td colspan="2" width="60%" nowrap="true">&nbsp;</td>
					<td style="padding-left:20px">
						<cp:CPButton onclick="next()">
							<bean:message key="cp.massupdate.dimselect.ok"/>
						</cp:CPButton>
						<cp:CPButton onclick="quit()">
							<bean:message key="cp.massupdate.dimselect.cancel"/>
						</cp:CPButton>
					</td>
					<td align="center">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</logic:notEmpty>

	<logic:empty name="virementQueryForm" property="modelsAndFinanceCubes">

		<!-- Once a finance cube has been selected the cell picker and other controls can be displayed -->
		<div dojoType="dijit.layout.ContentPane" region="center" widgetId="selectionTab" style="padding-left:20px;padding-right:20px">
		<fieldset>
		<table width="100%" height="40%" cellpadding="2" cellspacing="1">
			<tr>
				<td colspan="4" align="center">
					<bean:define id="app_contextId" name="virementQueryForm" property="contextId"/>
					<bean:define id="app_modelId" name="virementQueryForm" property="modelId"/>
					<bean:define id="app_financeCubeId" name="virementQueryForm" property="financeCubeId"/>
					<bean:define id="app_dimCount" name="virementQueryForm" property="noOfDims"/>
					<bean:define id="app_disableRASecurity" name="virementQueryForm" property="disableRASecurity"/>
					<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName"/>
					<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
					<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL"/>
					<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag"/>
					<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher"/>
					<bean:define id="app_logonName" scope="session" name="cpContext" property="userId"/>
					<%
					StringBuffer extraParams = new StringBuffer();
					StringBuffer extraValues = new StringBuffer();
					extraParams.append("['cpContextId','modelId','financeCubeId','dataTypesRequired',");
					extraParams.append("'systemName','finishedURL','logonName','internalUrl','remoteFlag','useCPDispatcher','disableRASecurity','dimCount']");
					extraValues.append("['").append(app_contextId).append("','").append(app_modelId).append("','").append(app_financeCubeId).append("','");
					extraValues.append(' ').append("','");
					extraValues.append(app_systemName).append("','").append(app_finishedURL).append("/poopupClose.do").append("','").append(app_logonName).append("','");
					extraValues.append(app_internalUrl).append("','").append(app_remoteFlag).append("','").append(app_useCPDispatcher);
					extraValues.append("','").append(app_disableRASecurity).append("','").append(app_dimCount).append("']");
					%>
					<jsp:include page="../system/applet2.jsp" flush="true">
						<jsp:param name="app_name" value="picker"/>
						<jsp:param name="app_width" value="99%"/>
						<jsp:param name="app_height" value="400"/>
						<jsp:param name="app_class" value="com.cedar.cp.utc.picker.ElementPicker"/>
						<jsp:param name="app_params" value="<%=extraParams%>"/>
						<jsp:param name="app_values" value="<%=extraValues%>"/>
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td>Request Owner:</td>
				<td>
					<nested:select property="owner">
						<nested:optionsCollection property="originators"
										label="narrative"
										value="tokenizedKey"/>
					</nested:select>
				</td>
				<td>Request Authorisers:</td>
				<td>
					<nested:select property="authoriser">
						<nested:optionsCollection property="authorisers"
										label="narrative"
										value="tokenizedKey"/>
					</nested:select>
				</td>
				</tr>
				<tr>
				<td>Transfer Id:</td>
				<td>
					<nested:text size="12" styleClass="inputnumber" property="requestId"/>
					<html:errors property="requestId"/>
				</td>
				<td>Status:</td>
				<td>
					<nested:select property="status">
						<nested:optionsCollection property="stati" label="name" value="id"/>
					</nested:select>
				</td>
				</tr>
				<tr>
				<td>From Value:</td>
				<td>
					<nested:text size="12" styleClass="inputnumber" property="fromValue"/>
				</td>
				<td>To Value:</td>
				<td>
					<nested:text size="12" styleClass="inputnumber" property="toValue"/>
				</td>
				</tr>
				<tr>
				<td>Creation Date From:</td>
				<td>
					<nested:text size="12" styleClass="inputdate" property="fromCreationDate"/>
				</td>
				<td>Creation Date To:</td>
				<td>
					<nested:text size="12" styleClass="inputdate" property="toCreationDate"/>
				</td>
				</tr>
			<tr>
				<td height="8px">&nbsp;</td>
			</tr>
			<tr align="right">
				<td colspan="4" align="right" style="padding-left:20px">
					<cp:CPButton onclick="getValuesFromApplet()">
						<bean:message key="cp.massupdate.dimselect.ok"/>
					</cp:CPButton>
					<cp:CPButton onclick="quit()">
						<bean:message key="cp.massupdate.dimselect.cancel"/>
					</cp:CPButton>
				</td>
			</tr>
		</table>
		</fieldset>
		<html:hidden property="headers" styleId="headers"/>
		<html:hidden property="selectedIds" styleId="selectedIds"/>
		<html:hidden property="selectedStructureIds" styleId="selectedStructureIds"/>
		<html:hidden property="selectedIdentifiers" styleId="selectedIdentifiers"/>
		<html:hidden property="selectedDescriptions" styleId="selectedDescriptions"/>
		<html:hidden property="selectedCell"/>
		<html:hidden property="userAction" styleId="userAction"/>
		</div>
	</logic:empty>
	<!--</div>-->

</html:form>
</body>

