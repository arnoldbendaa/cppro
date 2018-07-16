<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>

</head>
<body>
<div style="width:100%;height:100%">
	<bean:define id="app_contextId" name="gadgetAppletViewForm" property="contextId"/>
	<bean:define id="app_topNodeId" name="gadgetAppletViewForm" property="topNodeId"/>
	<bean:define id="app_modelId" name="gadgetAppletViewForm" property="modelId"/>
	<bean:define id="app_budgetCycleId" name="gadgetAppletViewForm" property="budgetCycleId"/>
	<bean:define id="app_profileRef" name="gadgetAppletViewForm" property="profileRef"/>
	<bean:define id="app_noDecoration" name="gadgetAppletViewForm" property="noDecoration"/>
	<bean:define id="app_dimensions" name="reviewBudgetForm" property="dimensions"/>
	<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName"/>
	<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
	<bean:define id="app_logonName" scope="session" name="cpContext" property="userId"/>
	<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL"/>
	<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag"/>
	<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher"/>

	<% StringBuffer extraParams = new StringBuffer();
		StringBuffer extraValues = new StringBuffer();
		extraParams.append("['cpContextId','topNodeId','modelId','budgetCycleId','profileRef', 'noDecoration', 'dimensions',");
		extraParams.append("'systemName','finishedURL','logonName','internalUrl','remoteFlag','useCPDispatcher']");
		extraValues.append("['").append(app_contextId).append("','").append(app_topNodeId).append("','").append(app_modelId).append("','").append(app_budgetCycleId).append("','");
		extraValues.append(app_profileRef).append("','").append(app_noDecoration).append("','").append(app_dimensions).append("','");
		extraValues.append(app_systemName).append("','").append(app_finishedURL).append("/poopupClose.do").append("','").append(app_logonName).append("','");
		extraValues.append(app_internalUrl).append("','").append(app_remoteFlag).append("','").append(app_useCPDispatcher).append("']"); %>
	<jsp:include page="../../../system/applet2.jsp" flush="true">
		<jsp:param name="app_name" value="de"/>
		<jsp:param name="app_width" value="100%"/>
		<jsp:param name="app_height" value="400"/>
		<jsp:param name="app_class" value="com.cedar.cp.utc.de.DataEntry"/>

		<jsp:param name="app_params" value="<%=extraParams%>"/>
		<jsp:param name="app_values" value="<%=extraValues%>"/>
	</jsp:include>
</div>
</body>