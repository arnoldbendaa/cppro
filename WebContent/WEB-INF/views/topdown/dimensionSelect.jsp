<%@ page import="com.cedar.cp.utc.struts.topdown.DimensionSelect" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title><bean:message key="cp.massupdate.popup.model.header.title"/></title>
<script language="javascript" type="text/javascript">
function getValuesFromApplet()
{
	var delim = "<%=DimensionSelect.sJSP_TOKEN%>";
	var applet = getApplet_byId('picker');
	var headers = "";
	var element_ids = "";
	var structure_ids = "";
	var element_identifier = "";
	var element_description = "";
	var selectedDataType;
	var okToCheck = true;

	var selected_header;
	var selected_id;
	var selected_structure_id;
	var selected_identifier;
	var selected_description;
	var count = 0;
	while (count < <bean:write name="dimensionForm" property="noOfDims" />)
	{
		selected_id = applet.getSelectedId(count);
		selected_structure_id = applet.getSelectedStructureId(count);
		selected_identifier = applet.getSelectedIdentifier(count);
		selected_description = applet.getSelectedIdentifier(count, true);
		selected_header = applet.getSelectedHeader(count);

		if (selected_id == 0)
		{
			txt = 'You must select a structure element for dimension ' + selected_header;
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
<logic:equal value="true" property="requireDataType" name="dimensionForm" >
	var selected_ids = "";
	if (okToCheck)
	{
	<logic:equal value="false" property="dataTypeMultiSelect" name="dimensionForm" >
		selected_id = applet.getSelectedId(count);
		selected_identifier = applet.getSelectedIdentifier(count);
		selected_description = applet.getSelectedIdentifier(count, true);
	</logic:equal>
	<logic:equal value="true" property="dataTypeMultiSelect" name="dimensionForm" >
		selected_id = applet.getSelectedId(count);
		selected_ids = applet.getSelectedIds(count);
		selected_identifier = applet.getSelectedIdentifiers(count);
		selected_description = applet.getSelectedIdentifiers(count, true);
	</logic:equal>
		if (selected_id == 0)
		{
			txt = 'You must select a datatype';
			alert(txt);
			okToCheck = false;
		}
		else
		{
		<logic:equal value="false" property="dataTypeMultiSelect" name="dimensionForm" >
			element_ids = element_ids + delim + selected_id;
			element_identifier = element_identifier + delim + selected_identifier;
		</logic:equal>
		<logic:equal value="true" property="dataTypeMultiSelect" name="dimensionForm" >
			element_ids = element_ids + delim + selected_ids;
			element_identifier = element_identifier + delim + selected_identifier;
		</logic:equal>
			element_description = element_description + delim + selected_description;
		}
	}
</logic:equal>
	if (okToCheck)
	{
		getDocumentObject('headers').value = headers;
		getDocumentObject('selectedIds').value = element_ids;
		getDocumentObject('selectedStructureIds').value = structure_ids;
		getDocumentObject('selectedIdentifiers').value = element_identifier;
		getDocumentObject('selectedDescriptions').value = element_description;
		getDocumentObject('dimensionForm').submit();
	}
}

function cancel()
{
	getDocumentObject('action').value = "cancel";
	getDocumentObject('dimensionForm').submit();
}

</script>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
	<div region="center" id="mainTabContainer" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" tabStrip="false" style="width: 100%" tabPosition="top">
		<div id="data" dojoType="dijit.layout.ContentPane" title="<bean:message key="cp.massupdate.dimselect.header"/>">
<html:form action="/dimensionSelect" styleId="dimensionForm" style="height:100%">
	<bean:define id="app_contextId" name="dimensionForm" property="contextId"/>
	<bean:define id="app_modelId" name="dimensionForm" property="massDTO.modelId"/>
	<bean:define id="app_financeCubeId" name="dimensionForm" property="massDTO.financeCubeId"/>
	<bean:define id="app_dataTypesRequired" name="dimensionForm" property="requiredDataType"/>
	<bean:define id="app_dataTypeMultiSelect" name="dimensionForm" property="dataTypeMultiSelect"/>
	<bean:define id="app_writeableDataTypes" name="dimensionForm" property="writeableDataTypes"/>

	<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName"/>
	<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
	<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL"/>
	<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag"/>
	<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher"/>
	<bean:define id="app_logonName" scope="session" name="cpContext" property="userId"/>

	<% StringBuffer extraParams = new StringBuffer();
		StringBuffer extraValues = new StringBuffer();
		extraParams.append("['cpContextId','modelId','financeCubeId','dataTypeMultiSelect','dataTypesRequired','writeableDataTypes',");
		extraParams.append("'systemName','finishedURL','logonName','internalUrl','remoteFlag','useCPDispatcher']");
		extraValues.append("['").append(app_contextId).append("','").append(app_modelId).append("','").append(app_financeCubeId).append("','").append(app_dataTypeMultiSelect).append("','").append(app_dataTypesRequired).append("','").append(app_writeableDataTypes).append("','");
		extraValues.append(app_systemName).append("','").append(app_finishedURL).append("/poopupClose.do").append("','").append(app_logonName).append("','");
		extraValues.append(app_internalUrl).append("','").append(app_remoteFlag).append("','").append(app_useCPDispatcher).append("']"); %>
	<jsp:include page="../system/applet2.jsp" flush="true">
		<jsp:param name="app_name" value="picker"/>
		<jsp:param name="app_width" value="100%"/>
		<jsp:param name="app_height" value="98%"/>
		<jsp:param name="app_class" value="com.cedar.cp.utc.picker.ElementPicker"/>

		<jsp:param name="app_params" value="<%=extraParams%>"/>
		<jsp:param name="app_values" value="<%=extraValues%>"/>
	</jsp:include>
	<html:hidden property="action" value="Click" styleId="action"/>
	<html:hidden property="headers" styleId="headers"/>
	<html:hidden property="selectedIds" styleId="selectedIds"/>
	<html:hidden property="selectedStructureIds" styleId="selectedStructureIds"/>
	<html:hidden property="selectedIdentifiers" styleId="selectedIdentifiers"/>
	<html:hidden property="selectedDescriptions" styleId="selectedDescriptions"/>
	<html:hidden property="requiredDataType"/>

	<html:hidden property="selectedCell"/>
</html:form>
		</div>
	</div>
	<div dojoType="dijit.layout.ContentPane" region="bottom">
		<cp:CPButton onclick="getValuesFromApplet()">
			<bean:message key="cp.massupdate.dimselect.ok"/>
		</cp:CPButton>

		<cp:CPButton onclick="cancel()">
			<bean:message key="cp.massupdate.dimselect.cancel"/>
		</cp:CPButton>
	</div>	
</div>
</body>