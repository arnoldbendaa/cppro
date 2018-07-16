<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<head>
    <title>CP - Communications</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/communication.js"></script>
    <script language="javascript" type="text/javascript">
        var deleteURL =  '<%=request.getContextPath()%>/communicationList.do';

        function getStoreURL()
        {
            var result = '<%=request.getContextPath()%>/ajaxMessageData.do?';
            if(getSelectedPane().id == "inBox")
                result+='folder=0';
            else
                result+='folder=1';

            result+='&maxFetch=' + dojo.byId("maxFetch").value;

            return result;
        }

		dojo.addOnLoad(function()
		{
            init();

			dojo.connect(dijit.byId("commsTabContainer"), "selectChild",  function() { //context isn't required
                refreshTab();
                resetToggle();
            });
		});
	</script>
	<meta content="<bean:message key="cp.communication.title"/>" name="title"/>
</head>
<body>                                           

<div dojoType="dijit.Menu" id="gridMenu" style="display: none;">
    <div id="openMenuItem" dojoType="dijit.MenuItem" onclick="openMessageItem()">Open Message</div>
    <div id="deleteMenuItem" dojoType="dijit.MenuItem" onclick="deleteSelected()">Delete Message</div>
    <div id="clearMenuItem" dojoType="dijit.MenuItem" onclick="clearSelected()">Clear Selection</div>
    <div id="clearSortMenuItem" dojoType="dijit.MenuItem" onclick="clearSort()">Clear Sort</div>
    <div dojoType="dijit.MenuItem">Cancel</div>
</div>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

<div dojoType="dojox.layout.ExpandoPane" region="leading" style="width:200px;" duration="125" maxWidth="300" title="Actions" splitter="true">
	<div dojoType="dijit.layout.ContentPane" attachParent="true" style="padding-right:26px">
		<ul>
			<li><span id="commsNewMessage" class="menuItem" onclick="openMessage('new')"><bean:message key="cp.commumication.list.new"/></span></li>
			<li><span id="commsRefreshMessage" class="menuItem" onclick="refreshMailContent()"><bean:message key="cp.commumication.refresh"/></span></li>
			<li><span class="menuItem" onclick="selectAll()" id="selectAll"><bean:message key="cp.commumication.message.label.selectall"/></span></li>
			<li><span class="menuItem" onclick="deleteSelected()"><bean:message key="cp.commumication.list.delete"/></span></li>
			<li><span class="menuItem" onclick="emptyFolder()"><bean:message key="cp.commumication.list.empty"/></span></li>
            <li><span class="menuItem">Extract&nbsp;<input type="text" id="maxFetch" size="4" value="500" onchange="refreshTab(true)"/>&nbsp;rows</span></li>
		</ul>
	</div>
</div>

	<div region="center" id="commsTabContainer" widgetId="commsTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" tabPosition="top">
		<div id="inBox" dojoType="dijit.layout.ContentPane"
            title='<bean:message key="cp.commumication.inbox"/>' selected="true">
            <div id="inBoxGrid" style="height:100%;width:100%" ></div>
		</div>

		<div id="sentBox" dojoType="dijit.layout.ContentPane"
			title='<bean:message key="cp.commumication.sent"/>'>
            <div id="sentBoxGrid" style="height:100%;width:100%" ></div>
		</div>
	</div>
</div>

</body>
