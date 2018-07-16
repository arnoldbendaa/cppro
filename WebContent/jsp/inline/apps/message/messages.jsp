<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<table border="1" width="100%">
	<tr>
		<th colspan="3">&nbsp;</th>
		<th><bean:message key="cp.commumication.list.from"/></th>
		<th><bean:message key="cp.commumication.list.subject"/></th>
		<th><bean:message key="cp.commumication.list.action"/></th>
	</tr>

	<logic:empty name="communicationListForm" property="messages">
		<tr>
			<td colspan="5" align="center"><bean:message key="cp.commumication.message.label.noMessage"/></td>
		</tr>
	</logic:empty>
	<logic:notEmpty name="communicationListForm" property="messages">
		<logic:iterate name="communicationListForm" id="messageList" property="messages" type="com.cedar.cp.utc.struts.message.MessageDTO" indexId="rowIndex">
			<% String scriptString = "javascript:openMessage('system', '" + messageList.getMessageId() + "' )"; %>
			<% String toggleString = "javascript:toggle(this, '" + messageList.getMessageId() + "', '" + messageList.getMessageUserId()  + "' )"; %>
			<tr>
				<td>
					<input type="checkbox" onclick="<%=toggleString%>" value="select" id="inBox<%=rowIndex%>"/>
				</td>
				<td align="center" width="3%">
					<logic:equal value="false" name="messageList" property="hasAttachment">
						&nbsp;
					</logic:equal>
					<logic:equal value="true" name="messageList" property="hasAttachment">
						<img src="<%=request.getContextPath()%>/images/attach.gif"/>
					</logic:equal>
				</td>
				<td align="center" width="3%">
					<logic:equal value="true" name="messageList" property="read">
						<img src="<%=request.getContextPath()%>/images/message_read.gif"/>
					</logic:equal>
					<logic:notEqual value="true" name="messageList" property="read">
						<img src="<%=request.getContextPath()%>/images/message_notread.gif"/>
					</logic:notEqual>
				</td>
				<td align="left">
					<html:link href="<%=scriptString%>"><bean:write name="messageList" property="fromUser"/></html:link>
				</td>
				<td align="left">
					<logic:equal value="" name="messageList" property="subject">
						<html:link href="<%=scriptString%>"><bean:message key="cp.commumication.list.no.subject"/></html:link>
					</logic:equal>
					<logic:notEqual value="" name="messageList" property="subject">
						<html:link href="<%=scriptString%>"><bean:write name="messageList" property="subject"/></html:link>
						<html:hidden name="messageList" property="messageId" indexed="true"/>
						<html:hidden name="messageList" property="messageUserId" indexed="true"/>
					</logic:notEqual>
				</td>
				<td align="center">
					<span class="menuItem" onclick="doDelete(<%=messageList.getMessageId()%>, <%=messageList.getMessageUserId()%>)">delete</span>
				</td>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
</table>