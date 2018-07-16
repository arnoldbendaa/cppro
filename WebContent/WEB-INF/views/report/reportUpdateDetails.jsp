<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<html>
<head><title>Report Update Submission</title></head>
<body>
<table width="90%" >
<tr>
<td align="center">
<h1 class="header1"><bean:write name="reportUpdateForm" property="message"/></h1>
</td>
</tr>
</table>
</body>
</html>