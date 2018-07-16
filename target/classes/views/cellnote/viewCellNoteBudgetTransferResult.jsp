<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"%>

<head>
<script language="JavaScript1.2">
function back()
{
	history.back(-1);
}
</script>
</head>
<body>

<bean:write name="virementQueryForm" property="outputText"
	filter="false" />

<br />
<logic:present name="virementQueryForm" property="budgetCycleId">
	<table width='100%'>
		<tr>
			<td><a class="smalllink" href='javascript:back()' title='Back'>Back</a></td>
		</tr>
	</table>
</logic:present>

</body>

