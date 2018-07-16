<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
    <title>CP- DataEntry (<bean:write name="reviewBudgetForm" property="submitModelName"/>)</title>
    <script type="text/javascript" language="JavaScript1.2">

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

            form = getDocumentObject('f1');
            form.action = "<%=request.getContextPath()%>/budgetCycleStatus.do";
            form.submit();
        }

		window.onbeforeunload = confirmExit;
		function confirmExit()
		{
			applet = getApplet_byId('de');
			if (applet != null)
			{
                if(applet.checkForEdits())
                    return "Edits exist do you want to discard these";
			}
		}

		function skipRemove()
		{
			return true;
		}
	</script>
	<meta name="breadcrumb" content="<bean:write name='reviewBudgetForm' property='HTMLCrumbs' filter="true"/>"/>
</head>

<body >

<html:form styleId="f1" action="/reviewBudget" style="padding:0;margin:0;height:100%">
            <html:hidden property="topNodeId" />
            <html:hidden property="modelId" />
            <html:hidden property="budgetCycleId" />

			<bean:define id="app_contextId" name="reviewBudgetForm" property="contextId"/>
			<bean:define id="app_topNodeId" name="reviewBudgetForm" property="topNodeId" />
			<bean:define id="app_modelId"  name="reviewBudgetForm" property="modelId" />
			<bean:define id="app_budgetCycleId" name="reviewBudgetForm" property="budgetCycleId" />
			<bean:define id="app_profileRef" name="reviewBudgetForm" property="profileRef" />
			<bean:define id="app_dimensions" name="reviewBudgetForm" property="dimensions"/>
			<bean:define id="app_systemName" scope="application" name="cpSystemProperties" property="systemName" />
			<bean:define id="app_finishedURL" scope="application" name="cpSystemProperties" property="rootUrl"/>
			<bean:define id="app_logonName" scope="session" name="cpContext" property="userId" />
			<bean:define id="app_internalUrl" scope="application" name="cpSystemProperties" property="connectionURL" />
			<bean:define id="app_remoteFlag" scope="application" name="cpSystemProperties" property="remoteConnectionFlag" />
			<bean:define id="app_useCPDispatcher" scope="application" name="cpSystemProperties" property="useCPDispatcher" />
			<%
				StringBuffer extraParams = new StringBuffer();
				StringBuffer extraValues = new StringBuffer();
				extraParams.append("['cpContextId','topNodeId','modelId','budgetCycleId','profileRef', 'dimensions', 'systemName','finishedURL',");
				extraParams.append("'logonName','internalUrl','remoteFlag','useCPDispatcher']");
				extraValues.append("['").append(app_contextId).append("','").append(app_topNodeId).append("','").append(app_modelId).append("','").append( app_budgetCycleId).append("','");
				extraValues.append( app_profileRef).append("','").append(app_dimensions).append("','").append( app_systemName).append("','").append( app_finishedURL).append("/poopupClose.do").append("','");
				extraValues.append( app_logonName).append("','").append( app_internalUrl).append("','").append( app_remoteFlag).append("','").append( app_useCPDispatcher).append("']");
			%>
			<jsp:include page="../system/applet2.jsp" flush="true">
				<jsp:param name="app_name" value="de"/>
				<jsp:param name="app_width" value="100%"/>
				<jsp:param name="app_height" value="98%"/>
				<jsp:param name="app_class" value="com.cedar.cp.utc.de.DataEntry" />

				<jsp:param name="app_params" value="<%=extraParams%>"/>
				<jsp:param name="app_values" value="<%=extraValues%>"/>
			</jsp:include>

	<html:hidden property="oldUserCount" />
	<html:hidden property="oldDepth" />
	<html:hidden property="structureElementId" styleId="struc_id" />
	<html:hidden property="stateFilter" styleId="filter" />
	<input type="hidden" name="pageSource" id="pageSource" value="bcStatus" />
	<html:hidden property="structureElementList" styleId="structureElementList"/>
	<html:hidden property="visIdList" styleId="visIdList"/>
	<html:hidden property="oldDepthList" styleId="oldDepthList"/>
	<html:hidden property="oldUserCountList"  styleId="oldUserCountList"/>
	<html:hidden property="descriptionList" styleId="descriptionList"/>
	<html:hidden property="addId" styleId="addId" />
	<html:hidden property="oldId" styleId="oldId" />

</html:form>

</body>
