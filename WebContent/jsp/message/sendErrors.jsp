<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<html>
<head>
<title><bean:message key="cp.commumication.message.label.openMessage" /></title>

</head>

<body style="overflow:hidden" bgcolor='#000000' leftmargin='1' topmargin='1'>
<table border="0" width="100%" height="98%">
	<tr>
		<td align="left"><img src="<%=request.getContextPath()%>/images/cp.gif"/></td>
	</tr>

	<tr>
		<th>The following errors occured whilst sending the message(s)</th>
	</tr>

	<tr height="75%" ><td align="center" valign="top">
	<table border="0" width="70%" >
	<tr><td>&nbsp;</td></tr>
	<logic:iterate id="messageErrors" property="messageSendErrors" name="communicationForm"  type="String"  >
		<tr>
			<td>
				<bean:write name="messageErrors" />
			</td>
		</tr>
	</logic:iterate>
	</table>
	</td></tr>

	<tr>
		<td align="right">
			<cp:CPButton onclick="javascript:window.close()" ><bean:message key="cp.commumication.message.label.close" /></cp:CPButton>
		</td>
	</tr>
</table>

</body>
</html>