<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
</head>
<body>

<table width="100%" >
    <tr>
        <th>&nbsp;</th>
        <th><bean:message key="cp.commumication.list.from"/></th>
        <th><bean:message key="cp.commumication.list.recdate"/></th>
        <th align="left"><bean:message key="cp.commumication.list.subject"/></th>
    </tr>
    <logic:empty name="homeForm" property="messages">
        <tr>
            <td colspan="4" align="center"><bean:message key="cp.commumication.message.label.noMessage"/></td>
        </tr>
    </logic:empty>

    <logic:notEmpty name="homeForm" property="messages">
        <logic:iterate name="homeForm" id="messageList" property="messages" type="com.cedar.cp.utc.struts.message.MessageDTO" indexId="rowIndex" length="5">
            <%String scriptString = "javascript:openMessage('system', '" + messageList.getMessageId() + "' , 'recent')";%>
            <tr>
                <td nowrap>
                    <logic:equal name="messageList" property="hasAttachment" value="false">
                        &nbsp;
                    </logic:equal>
                    <logic:equal name="messageList" property="hasAttachment" value="true">
                        <img alt="" src="<%=request.getContextPath()%>/images/attach.gif"/>
                    </logic:equal>
                </td>
                <td nowrap>
                    <html:link href="<%=scriptString%>"><bean:write name="messageList" property="fromUser"/></html:link>
                </td>
                <td nowrap>
                    <html:link href="<%=scriptString%>"><bean:write name="messageList" property="date" formatKey="cp.date.format"/></html:link>
                </td>
                <td nowrap width="70%">
                    <logic:equal value="" name="messageList" property="subject">
                        <html:link href="<%=scriptString%>"><bean:message key="cp.commumication.list.no.subject"/></html:link>
                    </logic:equal>
                    <logic:notEqual value="" name="messageList" property="subject">
                        <html:link href="<%=scriptString%>"><bean:write name="messageList" property="subject"/></html:link>
                    </logic:notEqual>
                </td>
            </tr>
        </logic:iterate>
    </logic:notEmpty>
</table>

</body>

