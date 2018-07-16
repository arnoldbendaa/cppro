<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
<title >CP - Analysis</title>
<script language="javascript" type="text/javascript">
function refreshContent()
{
	var tabbedPane = dijit.byId("xmlReportTabContainer");
	var contentPane = tabbedPane.selectedChildWidget;
	contentPane.refresh();
}

function selectReport(id,report)
{
    var currentReportId =  getCurrentReport();
    var currentId =  getCurrentReportItems();

    if(currentReportId != null && typeof(currentReportId) != "undefined" )
    {
        var currentReport = getObj(currentReportId);
        var currentReportItems = getObj(currentId);
        resetReport(currentReportItems,currentReport);
        var reportIco = getObj(currentReportId+'ico');
        if (reportIco != null && typeof(reportIco) != "undefined")
        {
            reportIco.src = '<%=request.getContextPath()%>/images/squares_0.gif';
        }
    }

	currentReportItems = getObj(id);

    if(currentReportItems != null && typeof(currentReportItems) != "undefined" )
    {
	    currentReportItems.style.display = 'block';
    }

	currentReport = getObj(report);

	reportIco = getObj(report+'ico');
    if (reportIco != null && typeof(reportIco) != "undefined")
    {
		reportIco.src = '<%=request.getContextPath()%>/images/squares_0_hl.gif';
	}

     setCurrentReport( report );
     setCurrentReportItems( id );
}

function selectReportItem(id)
{
    var currentReportId =  getCurrentReportItem();
	var currentReportItem = getObj(currentReportId);
    resetReportItem(currentReportItem);

	currentReportItem = getObj(id);
    setReportItemIcon(currentReportItem,'<%=request.getContextPath()%>/images/squares_0_hl.gif');

    setCurrentReportItem( id );
}

function resetReport(reportitems,report)
{
    if (reportitems != null && typeof(reportitems) != "undefined")
	{
	    reportitems.style.display = 'none';
	}
}

function resetReportItem(report)
{
	if (report != null && typeof(report) != "undefined")
	{
	    setReportItemIcon(report,'<%=request.getContextPath()%>/images/squares_0.gif');
	}
}

function setReportItemIcon(report,imgsrc)
{
    if (report.hasChildNodes())
    {
    	var child = report.firstChild;
    	// IE and Netscape have different dom structures
    	if (child.hasChildNodes())
    	{
	    	var img = child.firstChild;
			img.src = imgsrc;
    	}
    	else
    	{
	    	var img = child.nextSibling.firstChild;
			img.src = imgsrc;
		}
	}
}


function newChildFolder(parentId,isPublic)
{
    var name = prompt("Enter the name for the new child folder", "new folder");
    if (name != null)
    {
        var tabbedPane = dijit.byId("xmlReportTabContainer");
    	var contentPane = tabbedPane.selectedChildWidget;
        
    	dojo.xhrGet({
			// The page that parses the POST request
			url: '<%=request.getContextPath()%>/organiseReportFolder.do?action=newFolder&name=' + name +
            									'&parentFolderId=' + parentId +
            									'&public=' + isPublic,
			// Loads this function if everything went ok
			load: function (data)
			{
				contentPane.refresh();
			},
			// Call this function if an error happened
			error: function (data)
			{
			},
			content: { folder: contentPane.id},
			mimetype: "text/json",
			preventCache: true
		});
    }
}

function alterFolder(folderKey,oldName)
{
    var name = prompt("Enter the new name of the folder",oldName);
    if (name != null && name != oldName)
    {
    	var tabbedPane = dijit.byId("xmlReportTabContainer");
    	var contentPane = tabbedPane.selectedChildWidget;
        
    	dojo.xhrGet({
			// The page that parses the POST request
			url: '<%=request.getContextPath()%>/organiseReportFolder.do?action=alterFolder&folderKey=' + folderKey + '&name=' + name,
			// Loads this function if everything went ok
			load: function (data)
			{
				contentPane.refresh();
			},
			// Call this function if an error happened
			error: function (data)
			{
			},
			content: { folder: contentPane.id},
			mimetype: "text/json",
			preventCache: true
		});
    }
}

function deleteFolder(folderKey)
{
	var tabbedPane = dijit.byId("xmlReportTabContainer");
	var contentPane = tabbedPane.selectedChildWidget;
	if (confirm("Are you sure you want to delete this folder and all it's content"))
	{
		setCurrentReport(null);
        setCurrentReportItems(null);
		dojo.xhrGet({
			// The page that parses the POST request
			url: '<%=request.getContextPath()%>/organiseReportFolder.do?action=deleteFolder&folderKey=' + folderKey,
			// Loads this function if everything went ok
			load: function (data)
			{
				contentPane.refresh();
			},
			// Call this function if an error happened
			error: function (data)
			{
			},
			content: { folder: contentPane.id},
			mimetype: "text/json",
			preventCache: true
		});
	}
}

function deleteReport(reportKey)
{
	var tabbedPane = dijit.byId("xmlReportTabContainer");
	var contentPane = tabbedPane.selectedChildWidget;
	if (confirm("Are you sure you want to delete this report"))
	{
		setCurrentReport(null);
        setCurrentReportItems(null);
		dojo.xhrGet({
			// The page that parses the POST request
			url: '<%=request.getContextPath()%>/organiseReportFolder.do?action=deleteReport&reportKey=' + reportKey,
			// Loads this function if everything went ok
			load: function (data)
			{
				contentPane.refresh();
			},
			// Call this function if an error happened
			error: function (data)
			{
			},
			content: { folder: contentPane.id},
			mimetype: "text/json",
			preventCache: true
		});
	}
}

function moveReport(reportKey)
{
    params = 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1';
    params = params + ',width=500,height=100,left=120,top=120';
    target = '<%=request.getContextPath()%>/moveReportSetup.do?reportKey=' + reportKey;

    window.open(target, '_blank', params);
}

function hideShow(id, obj)
{
	var div = document.getElementById(id);
	if (obj.alt == "Hide")
	{
		div.style.display = "none";
		obj.alt = "Show";
		obj.src = "<%=request.getContextPath()%>/images/add.jpg";
	}
	else
	{
		div.style.display = "block";
		obj.alt = "Hide";
		obj.src = "<%=request.getContextPath()%>/images/sub.jpg";
	}	
}

</script>
<style type="text/css">
	.tablediv {		
		color: #000000;
  		padding: 0;  
  		width: auto;
	}
	.celldiv1 {
		border: solid 1 px; float: left; margin: 0; padding: 0; width: 75%;
	}
	.celldiv2 {
		border: solid 1 px; float: left; margin: 0; padding: 0; width: 15%;
		position:absolute;
		right:10px;
	}
	.rowdiv  {
		background: none; 
		border: solid 1px; 
		color: #000000;
  		margin-top: 0; margin-right: 0; margin-bottom: 0; margin-left: 0;
  		padding: 0; text-align: left; width: 100%;
  		height: 20px;
  		border-color: #cccccc;
	}
	.thLefDiv {
	  	float:left;
		color: #1D5EAA;
		font-family: Verdana, Arial,serif;
		font-weight: bold;
		font-size: 12px;
		background-color: #E4F4FE;
		text-align:center;
		width: 70%;
		border-bottom: solid 1px;
		height: 20px;
		border-color: #cccccc;
	}
	
	.thRightDiv {
	  	float:left;
		color: #1D5EAA;
		font-family: Verdana, Arial,serif;
		font-weight: bold;
		font-size: 12px;
		background-color: #E4F4FE;
		text-align:center;
		width: 30%;
		border-bottom: solid 1px;
		height: 20px;
		border-color: #cccccc;
	}
</style>
<meta content="<bean:message key="cp.reports.title"/>" name="title"/>
</head>
<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

<div dojoType="dojox.layout.ExpandoPane" region="leading" style="width:220px;" duration="125" maxWidth="220" title="Actions">
	<div dojoType="dijit.layout.ContentPane" style="padding-right:26px">
		<ul>
            <li><cp:CPButton securityString="XMLREPORT_PROCESS.New" onclick="loadAddress('/xmlReportView.do?reportId=new', 'analysisReport')"><bean:message key="cp.reports.new"/></cp:CPButton></li>
            <li><cp:CPButton onclick="refreshContent()"><bean:message key="cp.reports.refresh"/></cp:CPButton></li>
		</ul>
	</div>
</div>	

	<div region="center" id="xmlReportTabContainer" widgetId="xmlReportTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" tabPosition="top">
		
		<div id="publicTab" dojoType="dijit.layout.ContentPane" 
							title='<bean:message key="cp.reports.public.title"/>' 
							href="<%=request.getContextPath()%>/xmlPublicReports.do"
							preventCache="true" refreshOnShow="true" selected="true">
		</div>

		<div id="privateTab" dojoType="dijit.layout.ContentPane" 
							 title='<bean:message key="cp.reports.private.title"/>' 
							 href="<%=request.getContextPath()%>/xmlPrivateReports.do"
							 preventCache="true" refreshOnShow="true" >
		</div>
	</div>	
</div>
</body>