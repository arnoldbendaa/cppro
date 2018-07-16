<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
	<title><bean:message key="cp.cycle.activity.title.cc.select"/></title>
	<script language="javascript" type="text/javascript">
		function getValuesFromApplet()
		{
			var applet = getApplet_byId('singlePicker');
			var okToCheck = true;

            var selected_id = applet.getSelectedId(0);
			var selected_identifier = applet.getSelectedIdentifier(0);
			var selected_header = applet.getSelectedHeader(0);

			if (selected_id == 0)
			{
				txt = 'You must select a structure element for dimension ' + selected_header;
				alert(txt);
				okToCheck = false;
			}

			if (okToCheck)
			{
				var closeMe = true;
				if (window.opener.setCCId != null)
				{
					window.opener.setCCId(selected_id);
				}
				else if(window.opener.setMassUpdateCal != null)
				{
					closeMe = false;
					window.opener.setMassUpdateCal(selected_id, selected_identifier, this);
				}
				else if(window.opener.setSelected != null)
				{
					window.opener.setSelected(selected_id, selected_identifier);
				}

				if(closeMe)
					closeAppletWindow('singlePicker');
			}
		}
	</script>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" gutters="false">
	<div title="Element Picker" dojoType="dijit.layout.ContentPane" selected="true" region="center">
		<bean:define id="app_contextId" name="costCentreSelectForm" property="contextId"/>
		<bean:define id="app_modelId" name="costCentreSelectForm" property="modelId" />
		<bean:define id="app_rootRA" name="costCentreSelectForm" property="rootRa" />
		<bean:define id="app_financeCubeId" name="costCentreSelectForm" property="financeCubeId" />
		<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName"/>
		<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
		<bean:define id="app_logonName" scope="session" name="cpContext" property="userId"/>
		<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL"/>
		<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag"/>
		<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher"/>
		<bean:define id="app_dim0" name="costCentreSelectForm" property="costCentreSeqId"/>
		<% StringBuffer extraParams = new StringBuffer();
			StringBuffer extraValues = new StringBuffer();
			extraParams.append("['cpContextId','modelId','financeCubeId','dim0','rootRA','systemName','finishedURL',");
			extraParams.append("'logonName','internalUrl','remoteFlag','useCPDispatcher']");
			extraValues.append("['").append(app_contextId).append("','").append(app_modelId).append("','").append(app_financeCubeId).append("','").append(app_dim0).append("','").append(app_rootRA).append("','");
			extraValues.append(app_systemName).append("','").append(app_finishedURL).append("/poopupClose.do").append("','").append(app_logonName).append("','");
			extraValues.append(app_internalUrl).append("','").append(app_remoteFlag).append("','").append(app_useCPDispatcher).append("']"); %>
		<jsp:include page="../system/applet2.jsp" flush="true">
			<jsp:param name="app_name" value="singlePicker"/>
			<jsp:param name="app_width" value="100%"/>
			<jsp:param name="app_height" value="100%"/>
			<jsp:param name="app_class" value="com.cedar.cp.utc.picker.SingleElementPicker"/>

			<jsp:param name="app_params" value="<%=extraParams%>"/>
			<jsp:param name="app_values" value="<%=extraValues%>"/>
		</jsp:include>
	</div>
	<div dojoType="dijit.layout.ContentPane" region="bottom" >
		<div class="controlsBottom">
		<cp:CPButton onclick="getValuesFromApplet()">
			<bean:message key="cp.cycle.activity.title.cc.select.ok"/></cp:CPButton>
		&nbsp;
		<cp:CPButton onclick="javascript:closeAppletWindow('singlePicker')">
			<bean:message key="cp.cycle.activity.title.cc.select.cancel"/></cp:CPButton>
		</div>
	</div>
</div>
</body>