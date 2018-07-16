<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<head>
    <script language="javascript" type="text/javascript">
		dojo.require("dijit.Dialog");
		dojo.require("dijit.form.Form");
		dojo.require("dijit.form.ComboBox");

		function doSubmitOptions(prefix, id)
		{
			if(prefix == 'cellcalc')
			{
				dojo.style(dojo.byId("depthRow"), "display", "none");
			}
			else
			{
				try
				{
					dojo.style(dojo.byId("depthRow"), "display", "table-row");
				}
				catch(exception)
				{
					dojo.style(dojo.byId("depthRow"), "display", "inline");
				}
			}

			dojo.byId("title").innerHTML = dojo.byId(prefix + "_vis_id_" + id).innerHTML + " - " +  dojo.byId(prefix + "_description_" + id).innerHTML;
			dojo.byId("visId").value = dojo.byId(prefix + "_vis_id_" + id).innerHTML;

			dojo.byId("modelId").value = dojo.byId(prefix + "_modelId_" + id).value;
			dojo.byId("rootNodeId").value = dojo.byId(prefix + "_ccId_" + id).value;
			dojo.byId("rootNode").value = dojo.byId(prefix + "_ccVisId_" + id).value;

			if(dojo.byId(prefix + "_depth_" + id) != null)
				dojo.attr(dijit.byId("selectedDepth"), "value", dojo.byId(prefix + "_depth_" + id).innerHTML);

			if(dojo.byId(prefix + "_defaultParam_" + id) != null)
				dojo.byId("paramOption").value = dojo.byId(prefix + "_defaultParam_" + id).value;

			showDialog("managerDialog");
		}

		function doSubmit()
		{
			closeDialog("managerDialog");
			dojo.xhrPost({
				// The page that parses the POST request
				url: '<%=request.getContextPath()%>/webReportSubmit.do',

				// LPW - Fix for issue 638959 - UTF8 not passed in content-type header with IE through dojo xhrpost
				headers: { "content-type": "application/x-www-form-urlencoded; charset=UTF-8" },
				
				// Name of the Form we want to submit
				form: 'reportDefForm',

				// Loads this function if everything went ok
				load: function (data)
				{
					var response = dojo.fromJson(data);
					if(response != null && response.taskId != null)
						alert("Your report has been submitted as task id: " + response.taskId);
				},
				// Call this function if an error happened
				error: function (error)
				{
					alert('Error: ' + error);
				}
			});
		}

		function doCCSelect()
		{
			params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
			params = params + ',width=630,height=430,left=120,top=120';

			target = '<%=request.getContextPath()%>/popupCostCentreSelector.do?modelId=' + dojo.byId("modelId").value;
			window.open(target, '_blank', params);
		}

		function setSelected(id, vis_id)
		{
			dojo.byId("rootNodeId").value = id;
			dojo.byId("rootNode").value = vis_id;
		}
	</script>

	<meta name="title" content="<bean:message key="cp.reportdef.title"/>"/>
</head>

<body>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

	<div region="center" id="mainTabContainer" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" tabPosition="top">
		<div id="form" dojoType="dijit.layout.ContentPane"
			 title='<bean:message key="cp.reportdef.list.form"/>'
			 href="<%=request.getContextPath()%>/formReportList.do"
			 preventCache="true" refreshOnShow="true" selected="true" >
		</div>
		<div id="mappedExcel" dojoType="dijit.layout.ContentPane"
			title='<bean:message key="cp.reportdef.list.mappedexcel"/>'
			href="<%=request.getContextPath()%>/mappedExcelReportList.do"
			preventCache="true"	refreshOnShow="true" >
		</div>
		<div id="calculator" dojoType="dijit.layout.ContentPane"
			title='<bean:message key="cp.reportdef.list.calculator"/>'
			href="<%=request.getContextPath()%>/calculatorReportList.do"
			preventCache="true"	refreshOnShow="true" >
		</div>
		<div id="summaryCalc" dojoType="dijit.layout.ContentPane"
			title='<bean:message key="cp.reportdef.list.summarycalc"/>'
			href="<%=request.getContextPath()%>/summaryCalcReportList.do"
			preventCache="true"	refreshOnShow="true" >
		</div>
	</div>
</div>

<jsp:include page="submitDialog.jsp"/>

</body>
