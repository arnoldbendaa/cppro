<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<head>
    <script language="javascript" type="text/javascript">

		function messageObject(messageId, messageUserId)
		{
			this.messageId = messageId;
			this.messageUserId = messageUserId;
		}
		messageObject.prototype.toString = messageObjectToString;

		function messageObjectToString()
		{
			return this.messageId;
		}

		function getSelectedPane()
		{
			var tabbedPane = dijit.byId("commsTabContainer");
			return tabbedPane.selectedChildWidget;
		}

		function refreshMailContent(popupWindow)
		{
			getSelectedPane().refresh();

			if(popupWindow != null)
				popupWindow.close();
		}

		function doDelete(messageId, messageUserId)
		{
			if(confirm("Do you wish to delete message id " + messageId))
			{
				dojo.xhrGet({
					// The page that parses the POST request
					url: '<%=request.getContextPath()%>/communicationList.do',
					// Loads this function if everything went ok
					load: function (data)
					{
						refreshMailContent();
					},
					// Call this function if an error happened
					error: function (data)
					{
					},
					content: { messageId: messageId, messageUserId: messageUserId },
					mimetype: "text/json",
					preventCache: true
				});
			}
		}

		function emptyFolder()
		{
			var tabbedPane = dijit.byId("commsTabContainer");
			var contentPane = tabbedPane.selectedChildWidget;
			if(confirm("Do you wish to delete all message in your " + contentPane.title))
			{
				dojo.xhrGet({
					// The page that parses the POST request
					url: '<%=request.getContextPath()%>/communicationList.do',
					// Loads this function if everything went ok
					load: function (data)
					{
						contentPane.refresh();
					},
					// Call this function if an error happened
					error: function (data)
					{
					},
					content: { folder: contentPane.id},
					mimetype: "text/json",
					preventCache: true
				});
			}
		}

		var select_flag = true;
		function selectAll()
		{
			var i=0;
			for (i=0;i<=1000;i++) //if they have more than a 1000 in the list then they can do it again
			{
			   	var name = getSelectedPane().id + i;
				var checkBox = dojo.byId(name);
			   	if(checkBox == null)
					break;

			   	checkBox.checked = select_flag;
				checkBox.onclick();
			}

            if (select_flag)
            {
                select_flag = false;
                dojo.byId('selectAll').innerHTML = 'De-select All';
            }
            else
            {
                select_flag = true;
                dojo.byId('selectAll').innerHTML = 'Select All';
            }
		}

		function toggle(obj, messageId, messageUserId)
		{
			if(obj.checked)
				addToSelected( new messageObject(messageId, messageUserId));
			else
				removeFromSelected(new messageObject(messageId, messageUserId));
		}

		function deleteSelected()
		{
			var message ="";
			if(mSelected.length > 10)
				message = "Do you wish to delete the selected messages";
			else
				message = "Do you wish to delete message id(s) " + mSelected.join();

			if(confirm(message))
			{
				dojo.xhrGet({
					// The page that parses the POST request
					url: '<%=request.getContextPath()%>/communicationList.do',
					load: function (data)
					{
						mSelected = new Array();
						refreshMailContent();						
					},
					content: { messageIds: dojo.toJson(mSelected) },
					mimetype: "text/json",
					preventCache: true
				});

			}
		}

		function resetToggle()
		{
			select_flag = true;
			clearSelection();
			dojo.byId('selectAll').innerHTML = 'Select All';
		}

		dojo.addOnLoad(function()
		{
			dojo.connect(dijit.byId("inBox"), "onLoad", "resetToggle");
			dojo.connect(dijit.byId("sentBox"), "onLoad", "resetToggle");
		});
	</script>
	<meta content="<bean:message key="cp.communication.title"/>" name="title"/>
</head>
<body>                                           

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

<div dojoType="dojox.layout.ExpandoPane" region="leading" style="width:200px;" duration="125" maxWidth="300" title="Actions" splitter="true">
	<div dojoType="dijit.layout.ContentPane" attachParent="true" style="padding-right:26px">
		<ul>
			<li><span id="commsNewMessage" class="menuItem" onclick="openMessage('new')"><bean:message key="cp.commumication.list.new"/></span></li>
			<li><span id="commsRefreshMessage" class="menuItem" onclick="refreshMailContent()"><bean:message key="cp.commumication.refresh"/></span></li>
			<li><span class="menuItem" onclick="selectAll()" id="selectAll"><bean:message key="cp.commumication.message.label.selectall"/></span></li>
			<li><span class="menuItem" onclick="deleteSelected()"><bean:message key="cp.commumication.list.delete"/></span></li>
			<li><span class="menuItem" onclick="emptyFolder()"><bean:message key="cp.commumication.list.empty"/></span></li>
		</ul>
	</div>
</div>

	<div region="center" id="commsTabContainer" widgetId="commsTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" tabPosition="top">
		<div id="inBox" widgetId="inBox" dojoType="dijit.layout.ContentPane"
			 title='<bean:message key="cp.commumication.inbox"/>'
			 href="<%=request.getContextPath()%>/tableMessageList.do"
			 preventCache="true" refreshOnShow="true" selected="true" >
		</div>

		<div id="sentBox" widgetId="sentBox" dojoType="dijit.layout.ContentPane"
			title='<bean:message key="cp.commumication.sent"/>'
			href="<%=request.getContextPath()%>/tableSentMessageList.do"
			preventCache="true"	refreshOnShow="true" selected="true">
		</div>
	</div>
</div>

</body>
