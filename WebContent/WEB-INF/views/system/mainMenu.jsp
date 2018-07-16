<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
<head>
	<meta content="<bean:message key="cp.menu.mainMenu"/>" name="title"/>
	<meta content="<bean:message key="cp.mainMenu.title" />" name="message"/>
	<script type="text/javascript">

	function webstartVersionCheck(versionString)
	{
		// First, determine if Web Start is available
		if(dojo.isIE)
			return navigator.javaEnabled();

        if(dojo.isChrome)
            return true;
		
		if (navigator.mimeTypes['application/x-java-jnlp-file'])
		{
			// Next, check for appropriate version family
			for (var i = 0; i < navigator.mimeTypes.length; ++i)
			{
				pluginType = navigator.mimeTypes[i].type;
				if (pluginType == "application/x-java-applet;version=" + versionString)
				{
					return true;
				}
			 }
		 }
		return false;
	 }

var check = false;
function setCheck(value)
{
	check = value;
}

    function clickMainMenu()
    {
        dojo.byId("mainMenu").click();
    }


dojo.addOnLoad(function()
{
	if (check && !webstartVersionCheck("1.6"))
	{
		dijit.byId("jreInstall").show();
	}
});
	</script>
</head>
<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

	<div region="center" id="menuTabContainer" widgetId="menuTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%" tabPosition="top">
		<logic:iterate id="role" name="menuForm" property="actions" indexId="tabIndex">
			<div id="tab<bean:write name="tabIndex"/>" dojoType="dijit.layout.ContentPane"
				 title='<bean:write name="role" property="description" />' preventCache="true"
				 <logic:equal name="tabIndex" value="0">selected="true"</logic:equal> >
<ul>
	<logic:iterate id="option" name="role" property="options" >
		<logic:equal value="/administration.jnlp" name="option" property="action">
			<script type="text/javascript">
				setCheck(true);
			</script>
		</logic:equal>
		<bean:define id="actionUrl" name="option" property="action"/>
		<bean:define id="cache" name="option" property="preventCache" type="Boolean"/>
		<%
			String optionUrl = request.getContextPath() + actionUrl;
			if(cache)
			{
				optionUrl = optionUrl + "?preventCache=" + System.currentTimeMillis();
			}
		%>
		<li onmouseover="mouseOver(this)" onmouseout="mouseOut(this)" onclick="location.href='<%=optionUrl%>'" class="menuItem">
					<bean:write name="option" property="label"/>
		</li>
	</logic:iterate>
</ul>
			</div>
		</logic:iterate>
	</div>
</div>

<div dojoType="dijit.Dialog" id="jreInstall" title="Install Required" style="display:none;width:400px">
	<div class="dialogContent">
		Click <a href="/3rdParty/jre-6u16-windows-i586.exe">here</a> to install the JRE required to run the admin application.
	</div>

	<div class="dialogActions">
		<cp:CPButton onclick="dijit.byId('jreInstall').hide();" >Close</cp:CPButton>
	</div>
</div>
</body>

