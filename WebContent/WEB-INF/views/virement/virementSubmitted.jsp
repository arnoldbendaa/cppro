<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<body>
<H5 class="header5"><bean:message key="cp.virement.entry.submitted.title"/></h5>
<center>
<p>
<%
	// A taskId of zero indicates the request requires authorisation
	Integer taskId = (Integer)request.getAttribute("TaskId");
	if( taskId != null && taskId.intValue() != 0 )
	{
%>
<bean:message key="cp.virement.entry.submitted.task.message" arg0="<%=String.valueOf(taskId)%>" />
<%
	}
	else
	{
%>
<bean:message key="cp.virement.entry.submitted.auth.message"/>
<%
	}
%>
<p>
<input type="button" value="OK" class="button" onClick="loadAddress('virements.do')"/>
</center>
</body>
