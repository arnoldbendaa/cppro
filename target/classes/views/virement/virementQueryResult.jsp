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
function returnToQueryParams()
{
	getDocumentObject('userAction').value = 'queryParams';
	getDocumentObject('virementQueryForm').submit();
}
</script>
</head>
<body>
<html:form action="virementQuery.do" styleId="virementQueryForm" method="POST">
	<table width="100%">
		<tr align="right">
			<td align="right" style="padding-left:20px">
				<cp:CPButton onclick="returnToQueryParams()">Query Params</cp:CPButton>
				<cp:CPButton onclick="quit()">Quit</cp:CPButton>
			</td>
		</tr>
	</table>
	<nested:hidden property="modelId"/>
	<nested:hidden property="financeCubeId"/>
	<nested:hidden property="owner"/>
	<nested:hidden property="authoriser"/>
	<nested:hidden property="requestId"/>
	<nested:hidden property="status"/>
	<nested:hidden property="fromValue"/>
	<nested:hidden property="toValue"/>
	<nested:hidden property="fromCreationDate"/>
	<nested:hidden property="toCreationDate"/>
	<nested:hidden property="headers"/>
	<nested:hidden property="selectedIds"/>
	<nested:hidden property="selectedStructureIds"/>
	<nested:hidden property="selectedIdentifiers"/>
	<nested:hidden property="selectedDescriptions"/>
	<nested:hidden property="selectedCell"/>
	<nested:hidden property="userAction" styleId="userAction"/>
	<div>
	<bean:write name="virementQueryForm" property="outputText" filter="false"/>
	</div>
</html:form>
</body>

<bean:write name="virementQueryForm" property="outputText" filter="false"/> 