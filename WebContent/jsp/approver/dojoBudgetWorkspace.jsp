<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
	<script type="text/javascript">
		dojo.require("dojox.layout.FloatingPane");

		function budgetStatus(structureElement, state, addId, oldId)
		{
			var form;
			var seField;
			var filterField;
			var addIdField;
			var oldIdField;

			seField = getDocumentObject('struc_id');
			seField.value = structureElement;

			if (state == null)
				state = 0;
			filterField = getDocumentObject('filter');
			filterField.value = state;

			addIdField = getDocumentObject('addId');
			if (addId == null)
				addId = "";
			addIdField.value = addId;
			oldIdField = getDocumentObject('oldId');
			if (oldId == null)
				oldId = "";
			oldIdField.value = oldId;

			form = getDocumentObject('budgetWorkspaceForm');
			form.action = "<%=request.getContextPath()%>/budgetCycleStatus.do";
			form.submit();
		}

		var fTitles = ["Recent Messages", "Data Entry Profiles", "Data Entry", "The BBC", "Data Entry 2"];
		var hRefs = ["<%=request.getContextPath()%>/gadgetRecentMessage.do", "<%=request.getContextPath()%>/gadgetDataEntryProfile.do",
			"<%=request.getContextPath()%>/gadgetDataEntryView.do", "http://bbc.co.uk",
			"<%=request.getContextPath()%>/gadgetDataEntryView.do"];
		var hRefParam = ["", "", "&profileVisId=gadget1", "", "&profileVisId=gadget2"];
		var fPositions = [ [40, 14] , [254, 14], [352, 436 ], [40, 436], [470, 14] ];
		var fSize = [[404, 202],[404, 202], [820, 440], [820, 302], [404, 322] ];

		dojo.addOnLoad(function()
		{
			makeFloaters();
			positionFloaters();

			setTimeout(function hideLoader(){
				var loader = dojo.byId('loader');
				dojo.fadeOut({ node: loader, duration:1000,
					onEnd: function(){
						loader.style.display = "none";
					}
				}).play();
			}, 150);
		});

		function makeFloaters()
		{
			for (var i = 0; i < fTitles.length; i++)
			{
				var fHref = hRefs[i];
				fHref = fHref + '?gadget=true&modelId=' + dojo.byId("modelId").value;
				fHref = fHref + '&budgetCycleId=' + dojo.byId("budgetCycleId").value;
				fHref = fHref + '&structureElementId=' + dojo.byId("struc_id").value;
				fHref = fHref + '&topNodeId=' + dojo.byId("topNodeId").value;
				fHref = fHref + hRefParam[i];
				var nodeID = "floater" + i;
				var fStyle = 'width:' + fSize[i][0] + 'px;height:' + fSize[i][1] + 'px';
				var tmp = new dojox.layout.FloatingPane({
					title: fTitles[i],
					id:nodeID,
					closable:false,
					resizable:true,
					maxable:true,
					dockTo: "myDock",
					style: fStyle
				}, nodeID);
				tmp.startup();
				dojo.byId("if_" + nodeID).src = fHref;
			}
		}

		function positionFloaters()
		{
			for (var i = 0; i < fTitles.length; i++)
			{
				var nodeID = "floater" + i;
				var floater = dojo.byId(nodeID);
				dojo.style(floater, 'top', fPositions[i][0] + 'px');
				dojo.style(floater, 'left', fPositions[i][1] + 'px');
			}
		}

		function tileFloaters()
		{
			var topStart = 50;
			var leftStart = 10;
			for (var i = 0; i < fTitles.length; i++)
			{
				var nodeID = "floater" + i;
				var floater = dojo.byId(nodeID);
				var topValue = topStart + (20 * i );
				var leftValue = leftStart + (10 * i);
				dojo.style(floater, 'top', topValue + 'px');
				dojo.style(floater, 'left', leftValue + 'px');
			}
		}
	</script>

	<bean:define id="dojoVersion" value="1.1.1"/>
	<style type="text/css">
		@import "http://o.aolcdn.com/dojo/<%=dojoVersion%>/dojox/layout/resources/FloatingPane.css";
		@import "http://o.aolcdn.com/dojo/<%=dojoVersion%>/dojox/layout/resources/ResizeHandle.css";
		.myDock {
			position:absolute;
			background-color:#ededed;
			right:0; bottom:2px;
			border-left:1px solid #ccc;
		}

		#loader {
			padding:0;
			margin:0;
			position:absolute;
			top:0; left:0;
			width:100%; height:100%;
			background:#ededed;
			z-index:999;
		}
	</style>
</head>

<body>
<html:form styleId="budgetWorkspaceForm" action="/budgetWorkspace.do" style="padding:0;margin:0;">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="header5" style="text-align:left" nowrap height="26px">
				<bean:write name="budgetWorkspaceForm" property="submitModelName"/>&nbsp;:&nbsp;
				<bean:write name="budgetWorkspaceForm" property="submitCycleName"/>
				<html:hidden property="topNodeId" styleId="topNodeId"/>
				<html:hidden property="modelId" styleId="modelId"/>
				<html:hidden property="budgetCycleId" styleId="budgetCycleId"/>
			</td>
			<td align="right" nowrap style="padding-right:5px">
				<bean:define id="crumbsize" name="budgetWorkspaceForm" property="crumbSize"/>
				<logic:greaterThan value="0" name="crumbsize">
					<logic:iterate id="crumb" name="budgetWorkspaceForm" property="crumbs" type="com.cedar.cp.utc.struts.approver.CrumbDTO" indexId="crumId" length="crumbsize">
						&nbsp; /
						<a class="smalllink" href='javascript:budgetStatus(<bean:write name="crumb" property="structureElementId" />, null, null, <bean:write name="crumb" property="structureElementId" /> )' title='<bean:write name="crumb" property="description" />'>
							<bean:write name="crumb" property="visId"/>
						</a>
					</logic:iterate>
				</logic:greaterThan>
			</td>
		</tr>
	</table>
	<html:hidden property="oldUserCount" />
	<html:hidden property="oldDepth" />
	<html:hidden property="structureElementId" styleId="struc_id" />
	<html:hidden property="stateFilter" styleId="filter" />
	<html:hidden property="structureElementList" styleId="structureElementList"/>
	<html:hidden property="visIdList" styleId="visIdList"/>
	<html:hidden property="oldDepthList" styleId="oldDepthList"/>
	<html:hidden property="oldUserCountList"  styleId="oldUserCountList"/>
	<html:hidden property="descriptionList" styleId="descriptionList"/>
	<html:hidden property="addId" styleId="addId" />
	<html:hidden property="oldId" styleId="oldId" />
	<input type="hidden" name="selectedStructureElementId" id="selectedStructureElementId" />	
	<input type="hidden" name="pageSource" id="pageSource" value="workSpace"/>
</html:form>
<div id="loader"> </div>
<div dojoType="dojox.layout.Dock" id="myDock" class="myDock"> </div>

<div id="floater0" >
	<iframe src="" height="98%" width="98%" id="if_floater0" >
	</iframe>
</div>
<div id="floater1" >
	<iframe src="" height="98%" width="98%" id="if_floater1" >
	</iframe>
</div>
<div id="floater2">
	<iframe src="" height="98%" width="98%" id="if_floater2" >
	</iframe>
</div>
<div id="floater3">
	<iframe src="" height="98%" width="98%" id="if_floater3" >
	</iframe>
</div>
<div id="floater4">
	<iframe src="" height="98%" width="98%" id="if_floater4" >
	</iframe>
</div>

</body>
