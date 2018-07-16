<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<body>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

	<div region="center" id="mainTabContainer" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%" tabPosition="top">
			<div id="data" dojoType="dijit.layout.ContentPane" title="<bean:message key="cp.virement.entry.title"/>">

<html:form styleId="f1" action="/virementsGUI" style="padding:0;margin:0;height:100%">
			<bean:define id="app_contextId" name="virementsGUIForm" property="contextId"/>
			<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName" />
			<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
			<bean:define id="app_logonName" scope="session" name="cpContext" property="userId" />
			<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL" />
			<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag" />
			<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher" />
			<%
				StringBuffer extraParams = new StringBuffer();
				StringBuffer extraValues = new StringBuffer();
				extraParams.append("['cpContextId','systemName','finishedURL',");
				extraParams.append("'logonName','internalUrl','remoteFlag','useCPDispatcher']");
				extraValues.append("['").append(app_contextId).append("','").append( app_systemName).append("','").append( app_finishedURL).append("/poopupClose.do").append("','");
				extraValues.append( app_logonName).append("','").append( app_internalUrl).append("','").append( app_remoteFlag).append("','").append( app_useCPDispatcher).append("']");
			%>
			<jsp:include page="../system/applet2.jsp" flush="true">
				<jsp:param name="app_name" value="vde"/>
				<jsp:param name="app_width" value="100%"/>
				<jsp:param name="app_height" value="99%"/>
				<jsp:param name="app_class" value="com.cedar.cp.utc.virement.VirementListAppet" />

				<jsp:param name="app_params" value="<%=extraParams%>"/>
				<jsp:param name="app_values" value="<%=extraValues%>"/>
			</jsp:include>
</html:form>
			</div>
	</div>
</div>
</body>

