<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
	<script type="text/javascript">
		function openBI(id)
		{
			//standard window params
			var params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1';
			params = params + ',width=630,height=430,left=100,top=100';
			var url = '<%=request.getContextPath()%>/viewBISetup.do';
			url = url + '?BI_ID=' + id;

			window.open(url, '_blank', params);
		}

		function refreshMailContent(popupWindow)
		{
			var tabbedPane = dijit.byId("mainTabContainer");
			var commsId;
			if(tabbedPane != null)
			{
				var contentPane = tabbedPane.selectedChildWidget;
				var id = contentPane.id;
				commsId = "comms_"+ id;
			}
			else
			{
				commsId = "comms";
			}
			var comms = dijit.byId(commsId);
			comms.refresh();

			if(popupWindow != null)
				popupWindow.close();
		}

		dojo.addOnLoad(function()
        {
            if (checkBrowserOk())
            {
				var tab = dojo.byId("vtab");
				var tabContainer = dijit.byId("mainTabContainer");
				if(tab != null)
				{
					var vtab = tabContainer.tablist.domNode.children[tabContainer.tablist.domNode.children.length - 1];
					dojo.create("img", {src:'<%=request.getContextPath()%>/images/warnings.gif' }, vtab.children[0].children[0]);
					dojo.addClass(vtab.children[0].children[0], "altDijitTab");
				}
			}
        });
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/virement/virement.js"></script>
	<style type="text/css" id="tableStyle">
		.bottomScroll {
			height: 102.4px;
			width: 100%;
			overflow-y: auto;
			overflow-x: hidden;
		}

		.altDijitTab{
			font-size:larger;
		}
	</style>

	<!-- this title will be used by the decorator -->
	<meta name="title" content="welcome to super user"/>
</head>

<body>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
	<div dojoType="dijit.layout.ContentPane" region="top" >
		<table width="100%">
			<tr>
				<td align="right" nowrap class="sp-table-with-legend">
					<img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/red.gif"> <bean:message key="cp.approver.key.none"/>&nbsp;
					<img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/blue.gif"> <bean:message key="cp.approver.key.started"/>&nbsp;
					<img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/orange.gif"> <bean:message key="cp.approver.key.submit"/>&nbsp;
					<img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/green.gif"> <bean:message key="cp.approver.key.agree"/>&nbsp;
					<img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/purple.gif"> <bean:message key="cp.approver.key.not.plannable"/>&nbsp;
					<img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/grey.gif"> <bean:message key="cp.approver.key.disabled"/>&nbsp;
				</td>
			</tr>
		</table>
	</div>

	<logic:notEmpty name="homeForm" property="model"  >
	<div region="center" id="mainTabContainer" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%" tabPosition="top">
		<logic:iterate name="homeForm" property="model" id="model" type="com.cedar.cp.utc.struts.homepage.ModelDTO" indexId="modelid">
		<div id="<bean:write name="model" property="modelId"/>" dojoType="dijit.layout.ContentPane"
			 title='<bean:write name="model" property="fullName"/>'
			 href="<%=request.getContextPath()%>/tableWelcomeModelData.do?modelId=<bean:write name="model" property="modelId"/>"
			 <logic:equal name="modelid" value="0">selected="true"</logic:equal>				
			 preventCache="true" refreshOnShow="true" >
		</div>
		</logic:iterate>
		<logic:equal value="true" name="homeForm" property="virementsToAuthorise">
		<div id="vtab" dojoType="dijit.layout.ContentPane"
			 title='Budget Transfer Authorisation'
			 href="<%=request.getContextPath()%>/tableVirements.do" preventCache="true" refreshOnShow="true" >
		</div>			
		</logic:equal>
	</div>
	</logic:notEmpty>

	<!-- if no models show messages -->
	<logic:empty name="homeForm" property="model"  >
		<div region="center" id="commsTabContainer" widgetId="commsTabContainer" dojoType="dijit.layout.TabContainer" tabStrip="false"  tabPosition="top">
			<div dojoType="dijit.layout.ContentPane" title="Recent Messages">
				<table cellpadding="0" cellspacing="0" width="100%" style="padding-top:2px"><tr><td class="groupcell">
					<div dojoType="dijit.layout.ContentPane"
						 href="<%=request.getContextPath()%>/tableRecentMessageList.do"
						 preventCache="true" class="bottomScroll" id="comms" >
					</div>
				</td></tr></table>
			</div>
		</div>
	</logic:empty >
</div>

</body>