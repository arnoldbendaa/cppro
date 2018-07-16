<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
    <title><bean:message key="cp.commumication.message.label.openMessage"/></title>
    <script language="javascript" type="text/javascript">
        function openAttachNew(messageId, attachId)
        {
            var params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1';
            var target = '<%=request.getContextPath()%>/communicationViewNewAttach.do?messageId=' + messageId + '&attachId=' + attachId + '&noCache='  + new Date().valueOf();
            params = params + ',width=630,height=430,left=120,top=120';
            window.open(target, '_blank', params);
        }
		function deleteMessage()
        {
            var obj = getDocumentObject("cpmessage");
			var postURL = obj.action;
			dojo.xhrPost({
				// The page that parses the POST request
				url: postURL,

				// Name of the Form we want to submit
				form: 'cpmessage',

				// Loads this function if everything went ok
				load: function (data)
				{
					closeMessage();
				},
				// Call this function if an error happened
				error: function (error)
				{
					alert('Delete error: ' + error);
				}
			});
        }
        function newMessage(type)
        {
            var target = '<%=request.getContextPath()%>/communicationNewSetup.do?MessageType=' + type;
            var messageID = getDocumentObject("messageId").value;
            target = target + '&messageId=' + messageID;
            location.href = target;
        }

        function closeMessage()
        {
            if (window.opener.refreshMailContent)
                window.opener.refreshMailContent(this);
			else
				self.close();
		}

		<logic:notEmpty property="message.attachments" name="communicationOpenForm">
		var dAttDlg;
		function showDlg()
		{
			if(!dAttDlg)
			{
				var dStyle = "width:400px;overflow-x:hidden;overflow-y:auto";
				var noOfAtt = '<bean:write name="communicationOpenForm" property="message.attachmentSize"/>';
				if(noOfAtt > 12)
					dStyle = "width:400px;height:250px;overflow-x:hidden;overflow-y:auto";
				var pane  = dojo.byId('attDlg');
				dAttDlg = new dijit.Dialog({
					id: "dAttDlg",
					title: "Attachments",
					style: dStyle
				},pane);
			}
			dAttDlg.show();
		}
		</logic:notEmpty>
	</script>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" gutters="false">
	<div dojoType="dijit.layout.ContentPane" region="center">
<html:form action="/communicationOpen" styleId="cpmessage">
	<table class="groupCell" align="center" width="100%" >
        <tr>
            <logic:equal value="sentList" name="communicationOpenForm" property="source">
                <td align="right" class="sp-table-title"><bean:message key="cp.commumication.message.label.to"/></td>
                <td width="50%"><bean:write name="communicationOpenForm" property="message.toUser"/></td>
            </logic:equal>
            <logic:notEqual value="sentList" name="communicationOpenForm" property="source">
                <td align="right" class="sp-table-title"><bean:message key="cp.commumication.message.label.from"/></td>
                <td><bean:write name="communicationOpenForm" property="message.fromUser"/></td>
            </logic:notEqual>
            <td align="right" class="sp-table-title"><bean:message key="cp.commumication.message.label.date"/></td>
            <td><bean:write name="communicationOpenForm" property="message.date" formatKey="cp.date.format"/></td>
        </tr>
        <tr>
            <td class="sp-table-title"><bean:message key="cp.commumication.message.label.messageType"/></td>
            <td colspan="3"><bean:message key="cp.commumication.message.label.messageType_0"/></td>
        </tr>
        <tr>
            <td align="right" class="sp-table-title"><bean:message key="cp.commumication.message.label.subject"/></td>
            <td colspan="3"><bean:write name="communicationOpenForm" property="message.subject"/></td>
        </tr>
        <tr>
            <td valign="top" align="right" class="sp-table-title"><bean:message key="cp.commumication.message.label.message"/></td>
            <td colspan="3">
                <div id="textarea" class="sp-show-message" style="height:250px;width:500px;overflow:auto;background:white;border:solid thin gray">
                    <bean:write name="communicationOpenForm" property="message.content" filter="false"/>
                </div>
            </td>
        </tr>
        <tr>
        	<br>
            <logic:notEmpty property="message.attachments" name="communicationOpenForm">
                <td align="right"><bean:message key="cp.commumication.message.label.attach"/></td>
				<td colspan="3" >
				<logic:greaterThan value="2" property="message.attachmentSize" name="communicationOpenForm">
					<cp:CPButton buttonType="button" onclick="javascript:showDlg()">You have attachments</cp:CPButton>
						<div id="attDlg" style="display:none">
							<logic:iterate id="att" name="communicationOpenForm" property="message.attachments" type="com.cedar.cp.utc.struts.message.MessageAttachmentDTO" indexId="rowId">
									<a href="javascript:openAttachNew(<bean:write name="communicationOpenForm" property="message.messageId" />, <bean:write name="rowId"/>)"><bean:write name="att" property="attatchReadName"/></a>&nbsp;<br/>
							</logic:iterate>
						</div>
				</logic:greaterThan>
				<logic:lessThan value="3" property="message.attachmentSize" name="communicationOpenForm" >
					<logic:iterate id="att" name="communicationOpenForm" property="message.attachments" type="com.cedar.cp.utc.struts.message.MessageAttachmentDTO" indexId="rowId">
						<a href="javascript:openAttachNew(<bean:write name="communicationOpenForm" property="message.messageId" />, <bean:write name="rowId"/>)"><bean:write name="att" property="attatchReadName"/></a>&nbsp;<br/>
					</logic:iterate>
				</logic:lessThan>
				</td>
            </logic:notEmpty>
            <logic:empty property="message.attachments" name="communicationOpenForm">
                <td colspan="4">&nbsp;</td>
            </logic:empty>
        </tr>
    </table>

    <html:hidden property="messageId" styleId="messageId"/>
    <html:hidden property="source"/>

    <html:hidden property="message.messageId"/>
    <html:hidden property="message.messageUserId"/>
</html:form>
	</div>
	<div dojoType="dijit.layout.ContentPane" region="bottom">
		<div class="actionButtons sp-blue-buttons-group">
			<logic:notEqual value="sentList" name="communicationOpenForm" property="source">
				<cp:CPButton onclick="newMessage('reply')"><bean:message key="cp.commumication.message.label.reply"/></cp:CPButton>
			</logic:notEqual>
			<cp:CPButton onclick="newMessage('forward')"><bean:message key="cp.commumication.message.label.forward"/></cp:CPButton>
			<cp:CPButton onclick="deleteMessage()"><bean:message key="cp.commumication.message.label.delete"/></cp:CPButton>
			<cp:CPButton onclick="closeMessage()"><bean:message key="cp.commumication.message.label.close"/></cp:CPButton>
		</div>
	</div>
</body>