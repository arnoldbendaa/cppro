<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<body>
	<bean:define id="app_contextId" name="xmlReportsForm" property="contextId"/>
	<bean:define id="app_reportId" name="xmlReportsForm" property="reportId" />

	<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName"/>
	<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
	<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL"/>
	<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag"/>
	<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher"/>
	<bean:define id="app_logonName" scope="session" name="cpContext" property="userId"/>

	<% StringBuffer extraParams = new StringBuffer();
		StringBuffer extraValues = new StringBuffer();
		extraParams.append("['cpContextId','reportId',");
		extraParams.append("'systemName','finishedURL','logonName','internalUrl','remoteFlag','useCPDispatcher']");
		extraValues.append("['").append(app_contextId).append("','").append(app_reportId).append("','");
		extraValues.append(app_systemName).append("','").append(app_finishedURL).append("/poopupClose.do").append("','").append(app_logonName).append("','");
		extraValues.append(app_internalUrl).append("','").append(app_remoteFlag).append("','").append(app_useCPDispatcher).append("']");
		
		System.out.println( extraValues);
		
		%>
		
	<jsp:include page="../system/applet2.jsp" flush="true">
		<jsp:param name="app_name" value="report"/>
		<jsp:param name="app_width" value="100%"/>
		<jsp:param name="app_height" value="100%"/>
		<jsp:param name="app_class" value="com.cedar.cp.utc.de.report.ReportView"/>

		<jsp:param name="app_params" value="<%=extraParams%>"/>
		<jsp:param name="app_values" value="<%=extraValues%>"/>
	</jsp:include>
</body>