<%@ page import="com.cedar.cp.api.base.EntityRef"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
<script language="javascript" >

dojo.addOnLoad(function()
{
    // If we have a report use it, otherwise select the private report
    var currentReport = getCurrentReport();
    var currentReportItems = getCurrentReportItems();
    if (currentReport != null && typeof(currentReport) != "undefined")
    {
        selectReport(currentReportItems,currentReport);
    }
    else
    {
        selectReport('PRIR0','PRIF');
    }
});

var reportbg = "#EAF4FE";
var reportcolor = "#FFFFF4";

function selectReport(id,report)
{
    // Get the current items
    var currentReportId = getCurrentReport();
    var currentId = getCurrentReportItems();
    var currentReport = getObj(currentReportId);
    var currentReportItems = getObj(currentId);

    if (currentReport == null || typeof(currentReport) == "undefined")
    {
        // We have an id but it was not found, so reset
        report = 'PRIF';
        id = 'PRIR0';
    }
    else
    {
        // Clear the current item
	    resetReport(currentReportItems,currentReport);

	    var reportIco = getObj(currentReportId+'ico');
	    if (reportIco != null && typeof(reportIco) != "undefined")
	    {
		    reportIco.src = '<%=request.getContextPath()%>/images/squares_0.gif';
	    }
    }

    // Get the new report
    currentReportItems = getObj(id);
    currentReportItems.style.display = 'block';

    currentReport = getObj(report);
    setBackground(currentReport,reportbg,true);

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
    var currentReportId = getCurrentReportItem();
	var currentReportItem = getReportItem(currentReportId);

    resetReportItem(currentReportItem);

	currentReportItem = getReportItem(id);
	setBackground(currentReportItem,reportbg,true);
    setReportItemIcon(currentReportItem,'<%=request.getContextPath()%>/images/squares_0_hl.gif');

    setCurrentReportItem( id );
}

function getReportItem(id)
{
    var thisTR = getObj(id);
    if (thisTR != null && typeof(thisTR) != "undefined")
    {
	    if (thisTR.hasChildNodes())
	    {
	    	var child = thisTR.firstChild;
	    	// IE and Mozilla have different DOM structures
	    	if (child.hasChildNodes())
	    	{
	    		return child;
	    	}
			else
			{
				return child.nextSibling;
			}
		}
	}
}

function resetReport(reportitems,report)
{
    if (reportitems != null && typeof(reportitems) != "undefined")
	{
	    reportitems.style.display = 'none';
	}
	if (report != null && typeof(report) != "undefined")
	{
	    setBackground(report,reportcolor,false);
	}
}

function resetReportItem(report)
{
	if (report != null && typeof(report) != "undefined")
	{
	    setBackground(report,reportcolor,false);
	    setReportItemIcon(report,'<%=request.getContextPath()%>/images/squares_0.gif');
	}
}

function setReportItemIcon(report,imgsrc)
{
    if (report.hasChildNodes())
    {
        var img;
    	var child = report.firstChild;
     	// IE and Mozilla have different DOM structures
    	if (child.nodeName == 'IMG')
    	{
    		img = child;
		}
		else
		{
		    img = child.nextSibling;
		}
		img.src = imgsrc;
	}
}

function setCursor(cursortype,thisobj)
{
	if (thisobj == null && typeof(thisobj) != "undefined")
	{
	    document.body.style.cursor = cursortype;
	}
	else
	{
	    getObj(thisobj).style.cursor = cursortype;
	}
}

function setBackground(thisobj, color, border)
{
    if (thisobj != null && typeof(thisobj) != "undefined")
	{
		var obj = getObj(thisobj);
        obj.style.background = color;
        if (border)
        {
        	obj.style.borderColor = '#1D5EAA';
        }
        else
        {
        	obj.style.borderColor = '#FFFFF4';
        }
	}
}

function newChildFolder(parentId,isPublic)
{
    var name = prompt("Enter the name for the new child folder", "new folder");
    if (name != null)
    {
        var param = 'organiseReportFolder.do?action=newFolder&name=' + name +
                    '&parentFolderId=' + parentId +
                    '&public=' + isPublic;
        loadAddress( param );
    }
}

function alterFolder(folderKey,oldName)
{
    var name = prompt("Enter the new name of the folder",oldName);
    if (name != null && name != oldName)
    {
        loadAddress( 'organiseReportFolder.do?action=alterFolder&folderKey=' + folderKey + '&name=' + name );
    }
}

function deleteFolder(folderKey)
{
    if (confirm("Are you sure you want to delete this folder and all it's content"))
    {
        setCurrentReport(null);
        setCurrentReportItems(null);
        loadAddress( 'organiseReportFolder.do?action=deleteFolder&folderKey=' + folderKey );
    }
}

function deleteReport(reportKey)
{
    if (confirm("Are you sure you want to delete this report"))
    {
        loadAddress( 'organiseReportFolder.do?action=deleteReport&reportKey=' + reportKey );
    }
}

function moveReport(reportKey)
{
    params = 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1';
    params = params + ',width=500,height=100,left=120,top=120';
    target = '<%=request.getContextPath()%>/moveReportSetup.do?reportKey=' + reportKey;

    window.open(target, '_blank', params);
    //window.open(target, '_moveReport', params);
}

</script>
</head>

<body >
<H5 class="header5"><bean:message key="cp.reports.organise.title" />
    <cp:CPButton onclick="loadAddress('organiseReports.do')" >
        <bean:message key="cp.reports.organise.refresh" />
    </cp:CPButton>
</H5>

<table border="0" width="90%" align="center" height="82%">
    <tr height="20">
	    <th class="groupcell">Folders</th>
		<th class="groupcell">Reports</th>
	</tr>
    <tr>
	    <td valign="top" width="40%" class="groupcell">
            <table width="100%" border="0">
                <logic:iterate name="xmlReportsForm" property="folders" id="folder" type="com.cedar.cp.utc.struts.xmlreports.FolderDTO" >
                    <%
                        String folderKey = folder.getFolderKey();
                        String folderId = folder.getFolderId();
                        String reportsId = folder.getReportsId();
                        boolean isPublic = folder.isPublic();
                        String name = folder.getName();
                    %>
                    <!-- Dont put out public reports if not administrator -->
                    <logic:equal name="folder" property="editable" value="true">
                        <tr onmouseover="selectReport('<%=reportsId%>','<%=folderId%>');"><td class="reporttext" width="100%" style='padding-left: <bean:write name="folder" property="level"/>em;' id='<%=folderId%>' >
                            <img id="<%=folderId%>ico" src="<%=request.getContextPath()%>/images/squares_0.gif" border="0"/>
                            <bean:write name="folder" property="name" />
                            </td>
                            <td align="left" nowrap>
                                <img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/new.gif" title="New child folder" onclick="newChildFolder('<%=folderId%>',<%=isPublic%>);" >
                                <logic:notEqual name="folder" property="level" value="1">
                                    <img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/settings.gif" onclick="alterFolder('<%=folderKey%>','<%=name%>');" title="Folder properties" />
                                    <img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/delete.gif" onclick="deleteFolder('<%=folderKey%>');" title="Delete folder" />
                                </logic:notEqual>
                            </td>
                        </tr>
                    </logic:equal>
                </logic:iterate>
            </table>
		</td>
        <td valign="top" class="groupcell">
            <logic:iterate name="xmlReportsForm" property="folders" id="folder" type="com.cedar.cp.utc.struts.xmlreports.FolderDTO">
                <%
                    String reportsId = folder.getReportsId();
                %>
                <!-- Dont put out public reports if not administrator -->
                <logic:equal name="folder" property="editable" value="true">
                    <div id="<%=reportsId%>" style="display:none;" >
                        <table width="100%" border="0">
                        <logic:iterate name="folder" property="reports" id="report"  type="com.cedar.cp.utc.struts.xmlreports.FolderReportDTO">
                            <%
                                String reportId = report.getReportId();
                            %>
                            <tr onmouseover="selectReportItem(this)">
                                <td class="reportitemtext" width="100%">
                                    <img src="<%=request.getContextPath()%>/images/squares_0.gif" border="0"/>
                                    <bean:write name="report" property="title" />
                                </td>
                                <td align="left" nowrap>
                                    <img src="<%=request.getContextPath()%>/images/move.gif" title="Move Report" onclick="moveReport('<%=reportId%>');" >
                                    <img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/delete.gif" onclick="deleteReport('<%=reportId%>');" title="Delete report" />
                                </td>
                            </tr>
                        </logic:iterate>
                        </table>
                    </div>
                </logic:equal>
            </logic:iterate>
        </td>
    </tr>
</table>
</body>