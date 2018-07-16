<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
<head>
	<script type="text/javascript">
		function refreshContent(popupWindow)
		{
			var contentPane = dijit.byId("taskMain");
			contentPane.refresh();

			if(popupWindow != null)
				popupWindow.close();
		}
	</script>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
	<div dojoType="dijit.layout.ContentPane" region="top" widgetId="taskTop" style="padding-left:20px">
		<H5 class="header5">
			<bean:message key="cp.task.list.title" />&nbsp;
			<cp:CPButton onclick="refreshContent()" buttonType="button"><bean:message key="cp.commumication.refresh"/></cp:CPButton>
		</H5>
	</div>

	<div dojoType="dijit.layout.ContentPane"
		 href="<%=request.getContextPath()%>/tableTaskList.do"
		 preventCache="true"
		 region="center" widgetId="taskMain" id="taskMain"
		 align="center" style="overflow:auto;">
	</div>
</div>

</body>
