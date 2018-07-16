<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>
	<title>Warning</title>
</head>
<body>
<table width="80%" cellpadding="10" cellspacing="10">
	<tr>
		<td align="center">
			<h1 class="header1">You dont have access to any Budget Locations</h1>
		</td>
	</tr>
	<tr>
		<td align="center">
			<cp:CPButton onclick="javascript:self.close();">
				<bean:message key="cp.cycle.activity.title.cc.select.cancel"/></cp:CPButton>
		</td>
	</tr>
</table>

</body>