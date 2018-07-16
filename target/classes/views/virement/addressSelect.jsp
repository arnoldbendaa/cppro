<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title><bean:message key="cp.massupdate.dimselect.header"/></title>
<script language="javascript" type="text/javascript">
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
	var noOfDims = <bean:write name="virementAddressForm" property="noOfDims"/>;
	while (count < noOfDims)
	{
		selected_id = applet.getSelectedId(count);
		selected_structure_id = applet.getSelectedStructureId(count);
		selected_identifier = applet.getSelectedIdentifier(count);
		selected_description = applet.getSelectedIdentifier(count, true);
		selected_header = applet.getSelectedHeader(count);
		selected_leaf = applet.getSelectedLeaf(count);

		if (selected_id == 0)
		{
			txt = 'You must select a structure element for dimension ' + selected_header;
			alert(txt);
			okToCheck = false;
			break;
		}

		if (selected_leaf == "false" && count != (noOfDims - 1))
		{
			txt = 'Budget transfers are not allowed at a summary level. \n' + 'Please select leaf elements for all dimensions.';
			alert(txt);
			okToCheck = false;
			break;
		}

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
		getDocumentObject('virementAddressForm').submit();
	}
}

function cancelWindow()
{
	getDocumentObject('userAction').value = "cancel";
	getDocumentObject('virementAddressForm').submit();
}

function appletResize()
{
	var applet = getApplet_byId('picker');
	if (applet)
	{
		var clientDiv = dojo.byId('mainFrame');
		var realClientHeight = dojo.style(clientDiv, 'height');

		applet.height = realClientHeight - 155;
	}
}

dojo.addOnLoad(function()
{
	if (checkBrowserOk())
	{
		appletResize();
		dojo.connect(window, "onresize", appletResize);
	}
});

</script>
</head>

<body>
<h2 class="header2"><bean:message key="cp.virement.address_select.header"/></h2>
<html:form action="/addVirementDetail" styleId="virementAddressForm">
	<fieldset>
		<legend><bean:message key="cp.virement.transfer_direction"/></legend>
        <label >
            <html:radio property="transferType" styleId="tranferFromId" value="F" />
            <bean:message key="cp.virement.from"/>
        </label>
        <label >
            <html:radio property="transferType" styleId="tranferToId" value="T" />
            <bean:message key="cp.virement.to"/>
        </label>
	</fieldset>

	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				<bean:define id="app_contextId" name="virementAddressForm" property="contextId"/>
				<bean:define id="app_modelId" name="virementAddressForm" property="data.modelId"/>
				<bean:define id="app_financeCubeId" name="virementAddressForm" property="data.financeCubeId"/>
				<bean:define id="app_dimCount" name="virementAddressForm" property="noOfDims"/>
				<bean:define id="app_disableRASecurity" name="virementAddressForm" property="disableRASecurity"/>

				<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName"/>
				<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
				<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL"/>
				<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag"/>
				<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher"/>
				<bean:define id="app_logonName" scope="session" name="cpContext" property="userId"/>

				<%  StringBuffer extraParams = new StringBuffer();
					StringBuffer extraValues = new StringBuffer();
					extraParams.append("['cpContextId','modelId','financeCubeId','dataTypesRequired',");
					extraParams.append("'systemName','finishedURL','logonName','internalUrl','remoteFlag','useCPDispatcher','disableRASecurity','dimCount']");
					extraValues.append("['").append(app_contextId).append("','").append(app_modelId).append("','").append(app_financeCubeId).append("','");
					extraValues.append(' ').append("','");
					extraValues.append(app_systemName).append("','").append(app_finishedURL).append("/poopupClose.do").append("','").append(app_logonName).append("','");
					extraValues.append(app_internalUrl).append("','").append(app_remoteFlag).append("','").append(app_useCPDispatcher);
					extraValues.append("','").append(app_disableRASecurity).append("','").append(app_dimCount).append("']");  %>
				
				<jsp:include page="../system/applet2.jsp" flush="true">
					<jsp:param name="app_name" value="picker"/>
					<jsp:param name="app_width" value="99%"/>
					<jsp:param name="app_height" value="300"/>
					<jsp:param name="app_class" value="com.cedar.cp.utc.picker.ElementPicker"/>

					<jsp:param name="app_params" value="<%=extraParams%>"/>
					<jsp:param name="app_values" value="<%=extraValues%>"/>
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td height="8px">&nbsp;</td>
		</tr>
		<tr>
			<td style="padding-left:20px">
				<cp:CPButton onclick="getValuesFromApplet()">
					<bean:message key="cp.massupdate.dimselect.ok"/>
				</cp:CPButton>
				<cp:CPButton onclick="cancelWindow()">
					<bean:message key="cp.massupdate.dimselect.cancel"/>
				</cp:CPButton>
			</td>
		</tr>
	</table>
	<html:hidden property="userAction" value="CellSelected" styleId="userAction"/>
	<html:hidden property="addGroup" styleId="addGroup"/>
	<html:hidden property="currentGroup" styleId="currentGroup"/>
	<html:hidden property="currentTab" styleId="currentTab"/>
	<html:hidden property="headers" styleId="headers"/>
	<html:hidden property="selectedIds" styleId="selectedIds"/>
	<html:hidden property="selectedStructureIds" styleId="selectedStructureIds"/>
	<html:hidden property="selectedIdentifiers" styleId="selectedIdentifiers"/>
	<html:hidden property="selectedDescriptions" styleId="selectedDescriptions"/>
	<html:hidden property="selectedCell"/>
	<html:hidden property="data.key"/>
</html:form>
</body>