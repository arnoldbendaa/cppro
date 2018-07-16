<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
<script language="javascript">
	function deleteReport(reportKey)
	{
		if (confirm("Are you sure you want to delete this task report"))
		{
			loadAddress( 'deleteTaskReport.do?reportKey=' + reportKey );
		}
	}

	function refreshContent(popupWindow)
	{
		var contentPane = dijit.byId("taskReportMain");
		contentPane.refresh();

		if(popupWindow != null)
			popupWindow.close();
	}
</script>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
	<div dojoType="dijit.layout.ContentPane" region="top" widgetId="taskReportTop" style="padding-left:20px">
		<H5 class="header5">
			<bean:message key="cp.report.list.title" />&nbsp;
			<cp:CPButton onclick="refreshContent()" buttonType="button"><bean:message key="cp.commumication.refresh"/></cp:CPButton>
		</H5>
	</div>

	<div dojoType="dijit.layout.ContentPane"
		 href="<%=request.getContextPath()%>/tableReportsList.do"
		 preventCache="true"
		 region="center" widgetId="taskReportMain" id="taskReportMain"
		 align="center" style="overflow:auto;">
	</div>
</div>
</body>
