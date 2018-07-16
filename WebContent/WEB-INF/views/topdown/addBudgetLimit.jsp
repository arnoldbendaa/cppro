<%@ page import="com.cedar.cp.utc.struts.topdown.DimensionSelect" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<script language="javascript" type="text/javascript">
<logic:equal value="0" property="editId" name="budgetLimitEditForm" >
function getValuesFromApplet()
{
    var delim = "<%=DimensionSelect.sJSP_TOKEN%>";
    var applet = getApplet_byId('picker');
    var element_ids = "";
    var element_identifier = "";
    var element_description = "";
    var selectedDataType;
    var okToCheck = true;

    var selected_header;
    var selected_id;
    var selected_identifier;
    var selected_description;
    var count = 0;
    while (count < <bean:write name="budgetLimitEditForm" property="noOfDims" />)
    {
        selected_id = applet.getSelectedId(count);
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

        if (element_ids != "")
        {
            element_ids = element_ids + delim + selected_id;
            element_identifier = element_identifier + delim + selected_identifier;
            element_description = element_description + delim + selected_description;
        }
        else
        {
            element_ids = element_ids + selected_id;
            element_identifier = element_identifier + selected_identifier;
            element_description = element_description + selected_description;
        }

        count++;
    }
<logic:equal value="true" property="requireDataType" name="budgetLimitEditForm" >
    if (okToCheck)
    {
        selected_id = applet.getSelectedId(count);
        selected_identifier = applet.getSelectedIdentifier(count);
        selected_description = applet.getSelectedIdentifier(count, true);
        if (selected_id == 0)
        {
            txt = 'You must select a datatype';
            alert(txt);
            okToCheck = false;
        }
        else
        {
            element_ids = element_ids + delim + selected_id;
            element_identifier = element_identifier + delim + selected_identifier;
            element_description = element_description + delim + selected_description;
        }
    }
</logic:equal>
    if (okToCheck)
    {
        getDocumentObject('selectedIds').value = element_ids;
        getDocumentObject('selectedIdentifiers').value = element_identifier;
        getDocumentObject('selectedDescriptions').value = element_description;
        getDocumentObject('selectedAction').value = "add";
        getDocumentObject('budgetLimitEditForm').submit();
    }
}
</logic:equal>
function doEdit()
{
    getDocumentObject('selectedAction').value = "edit";
    getDocumentObject('budgetLimitEditForm').submit();
}

function cancel()
{
    getDocumentObject('selectedAction').value = "cancel";
    getDocumentObject('budgetLimitEditForm').submit();
}
</script>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

	<div region="center" id="mainTabContainer" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%" tabPosition="top">
		<div dojoType="dijit.layout.ContentPane"
			 <logic:notEqual value="0" property="editId" name="budgetLimitEditForm">title="<bean:message key="cp.budgetlimit.edit.title.edit"/>"</logic:notEqual>
			<logic:equal value="0" property="editId" name="budgetLimitEditForm">title="<bean:message key="cp.budgetlimit.edit.title.add"/>"</logic:equal>
			 id="data">

<html:form action="/editBudgetLimit" styleId="budgetLimitEditForm" style="padding:0;margin:0;height:100%">
<html:hidden property="selectedIds" styleId="selectedIds"/>
<html:hidden property="selectedIdentifiers" styleId="selectedIdentifiers"/>
<html:hidden property="selectedDescriptions" styleId="selectedDescriptions"/>
<html:hidden property="selectedAction" styleId="selectedAction"/>

<!-- pass back values -->
<html:hidden property="selectedRA"/>
<html:hidden property="modelId"/>
<html:hidden property="modelVisId"/>
<html:hidden property="modelDescription"/>
<html:hidden property="financeCubeId" styleId="financeCubeId"/>
<html:hidden property="financeCubeVisId" styleId="financeCubeVisId"/>
<html:hidden property="financeCubeDescription" styleId="financeCubeDescription"/>
<html:hidden property="noOfDims"/>
<html:hidden property="editId"/>

<logic:equal value="0" property="editId" name="budgetLimitEditForm">
	<bean:define id="app_contextId" name="budgetLimitEditForm" property="contextId"/>
	<bean:define id="app_modelId" name="budgetLimitEditForm" property="modelId"/>
	<bean:define id="app_financeCubeId" name="budgetLimitEditForm" property="financeCubeId"/>
	<bean:define id="app_rootRA" name="budgetLimitEditForm" property="selectedRA"/>
	<bean:define id="app_dataTypesRequired" name="budgetLimitEditForm" property="requiredDataType"/>
	<bean:define id="app_writeableDataTypes" name="budgetLimitEditForm" property="writeableDataTypes"/>

	<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName"/>
	<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
	<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL"/>
	<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag"/>
	<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher"/>
	<bean:define id="app_logonName" scope="session" name="cpContext" property="userId"/>
	<% StringBuffer extraParams = new StringBuffer();
		StringBuffer extraValues = new StringBuffer();
		extraParams.append("['cpContextId','modelId','financeCubeId','rootRA','dataTypesRequired','writeableDataTypes',");
		extraParams.append("'systemName','finishedURL','logonName','internalUrl','remoteFlag','useCPDispatcher']");
		extraValues.append("['").append(app_contextId).append("','").append(app_modelId).append("','").append(app_financeCubeId).append("','").append(app_rootRA).append("','");
		extraValues.append(app_dataTypesRequired).append("','").append(app_writeableDataTypes).append("','");
		extraValues.append(app_systemName).append("','").append(app_finishedURL).append("/poopupClose.do").append("','").append(app_logonName).append("','");
		extraValues.append(app_internalUrl).append("','").append(app_remoteFlag).append("','").append(app_useCPDispatcher).append("']"); %>
	<jsp:include page="../system/applet2.jsp" flush="true">
		<jsp:param name="app_name" value="picker"/>
		<jsp:param name="app_width" value="100%"/>
		<jsp:param name="app_height" value="94%"/>
		<jsp:param name="app_class" value="com.cedar.cp.utc.picker.ElementPicker"/>

		<jsp:param name="app_params" value="<%=extraParams%>"/>
		<jsp:param name="app_values" value="<%=extraValues%>"/>
	</jsp:include>
</logic:equal>
<logic:notEqual value="0" property="editId" name="budgetLimitEditForm">
    <table width="70%" align="centre">
        <logic:iterate name="budgetLimitEditForm" property="selectedElements" id="id" type="String">
            <tr>
                <td width="10%">&nbsp;</td>
                <td><bean:write name="id"/></td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEqual>

<table width="100%" cellpadding="0" cellspacing="0" style="padding-top:10px;">
    <tr>
        <td align="right"><bean:message key="cp.budgetlimit.edit.min"/></td>
        <td align="left"><html:text property="minValue"/></td>
        <td align="right"><bean:message key="cp.budgetlimit.edit.max"/></td>
        <td align="left"><html:text property="maxValue"/></td>
    </tr>
</table>

</html:form >
		</div>
	</div>

	<div dojoType="dijit.layout.ContentPane" region="bottom" >
<table width="100%" cellpadding="0" cellspacing="0" style="padding-bottom:10px">
    <tr>
        <td colspan="4" style="text-align:right;">
            <logic:equal value="0" property="editId" name="budgetLimitEditForm">
                <cp:CPButton onclick="getValuesFromApplet()">
                    <bean:message key="cp.massupdate.dimselect.ok"/>
                </cp:CPButton>
            </logic:equal>

            <logic:notEqual value="0" property="editId" name="budgetLimitEditForm">
                <cp:CPButton onclick="doEdit()">
                    <bean:message key="cp.massupdate.dimselect.ok"/>
                </cp:CPButton>
            </logic:notEqual>

            <cp:CPButton onclick="cancel()">
                <bean:message key="cp.massupdate.dimselect.cancel"/>
            </cp:CPButton>
        </td>
    </tr>
</table>
	</div>
</div>

</body>