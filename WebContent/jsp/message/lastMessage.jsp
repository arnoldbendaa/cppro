<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
</head>
<body>

<table width="100%">
<tr>
	<th>&nbsp;</th>
	<th><bean:message key="cp.commumication.list.from" /></th>
	<th><bean:message key="cp.commumication.list.recdate" /></th>
	<th align="left"><bean:message key="cp.commumication.list.subject" /></th>
</tr>

<logic:empty name="communicationListForm" property="messages"  >
     <tr>
	 	<td colspan="4" align="center"><bean:message key="cp.commumication.message.label.noMessage"/></td>
	 </tr>
</logic:empty>

<logic:notEmpty name="communicationListForm" property="messages"  >
<logic:iterate name="communicationListForm"  id="messageList" property="messages" type="com.cedar.cp.utc.struts.message.MessageDTO" indexId="rowIndex" length="5"  >
<%
	String scriptString = "javascript:openMessage('system', '" + messageList.getMessageFile() + "' )";
%>
<tr>
	<td nowrap>
		<logic:equal value="" name="messageList" property="attachName"  >
			&nbsp;
		</logic:equal>
		<logic:notEqual value="" name="messageList" property="attachName"  >
			<img src="<%=request.getContextPath()%>/images/attach.gif" />
		</logic:notEqual>
	</td>
	<td nowrap>
		<html:link href="<%=scriptString%>" ><bean:write name="messageList" property="fromUser" /></html:link>
	</td>
	<td nowrap>
		<html:link href="<%=scriptString%>" ><bean:write name="messageList" property="date" /></html:link>
	</td>
	<td nowrap width="70%">
		<logic:equal value="" name="messageList" property="subject"  >
			<html:link href="<%=scriptString%>" ><bean:message key="cp.commumication.list.no.subject" /></html:link>
 		</logic:equal>
		<logic:notEqual value="" name="messageList" property="subject"  >
		 	<html:link href="<%=scriptString%>" ><bean:write name="messageList" property="subject" /></html:link>
 		</logic:notEqual>
	</td>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>

</body>

