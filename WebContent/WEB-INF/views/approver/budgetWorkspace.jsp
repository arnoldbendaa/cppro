<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
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

            form = getDocumentObject('budgetWorkspaceForm');
            form.action = "<%=request.getContextPath()%>/budgetCycleStatus.do";
            form.submit();
        }

        function reviewBudget(structureElement, addId, oldId)
        {
            var form;
            var topNode = 0;
            getDocumentObject('topNode').value = topNode;

            if(addId == null)
                addId = "";
            getDocumentObject('addId').value = addId;
            if(oldId == null)
                oldId = "";
            getDocumentObject('oldId').value = oldId;

            if(structureElement == null)
                alert('An error has occured please contact your administrator');

            getDocumentObject('struc_id').value = "" + structureElement;
            getDocumentObject('selectedStructureElementId').value = "" + structureElement;

            form = getDocumentObject('budgetWorkspaceForm');
            if (confirm('Would you like to try new spreadsheet form?')) {
    			form.action = "<%=request.getContextPath()%>/reviewBudget/";
			} else {
    			form.action = "<%=request.getContextPath()%>/reviewBudget.do";
			}
            form.target = "_blank";
            form.submit();
        }

        function openReportView(id)
        {
            //standard window params
            params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,reportbar=0,resizable=1';

            var cmd = '<%=request.getContextPath()%>/xmlReportView.do?' +
                      'reportId=' + id;

            params = params + ',width=' + (screen.availWidth - 10) +
                    ',height=' + (screen.availHeight - 20) + ',left=0,top=0';
            window.open(cmd, '_blank', params);
        }
        
    </script>
    <style>
    th
    {
        background-color: #dadada;
        border-bottom: solid 1px black;
        background:url(<%=request.getContextPath()%>/images/buttons.bmp) no-repeat top right;
    }
    .portal
    {
        border: solid 1px black;
    }

    </style>    
</head>

<body>

<html:form styleId="budgetWorkspaceForm" action="budgetWorkspace.do" style="padding:0;margin:0;">
<table width="100%" cellpadding="0" cellspacing="0">
    <tr >
        <td class="header5" style="text-align:left" nowrap height="26px" >
            <bean:write name="budgetWorkspaceForm" property="submitModelName" />&nbsp;:&nbsp;
            <bean:write name="budgetWorkspaceForm" property="submitCycleName" />
            <html:hidden property="topNodeId" />
            <html:hidden property="modelId" />
            <html:hidden property="budgetCycleId" />
        </td>
        <td align="right" nowrap style="padding-right:5px">
            <bean:define id="crumbsize" name="budgetWorkspaceForm" property="crumbSize" />
            <logic:greaterThan value="0" name="crumbsize">
            <logic:iterate id="crumb" name="budgetWorkspaceForm" property="crumbs"  type="com.cedar.cp.utc.struts.approver.CrumbDTO" indexId="crumId" length="crumbsize" >
                &nbsp; /
                <a class="smalllink" href='javascript:budgetStatus(<bean:write name="crumb" property="structureElementId" />, null, null, <bean:write name="crumb" property="structureElementId" /> )' title='<bean:write name="crumb" property="description" />'>
                    <bean:write name="crumb" property="visId" />
                </a>
            </logic:iterate>
            </logic:greaterThan >
        </td>
    </tr>
</table>

<table width="100%" cellpadding="5">
    <tr>
	   <td width="33%" valign="top">
           <table width="100%" class="portal" cellspacing="0" >
               <tr><th class="head" align="left" >Budget Cycle Status</th></tr>
               <tr><td><bean:write name="budgetWorkspaceForm" property="raName" /></td></tr>
               <tr><td>Due Date : 30th Oct 2008 (94 days)</td></tr>
               <tr><td>State:
                   <logic:equal name="budgetWorkspaceForm" property="state" value="0" >
                       <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif"> Not Started
                   </logic:equal>
                   <logic:equal name="budgetWorkspaceForm" property="state" value="2" >
                       <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif"> Preparing
                   </logic:equal>
                   <logic:equal name="budgetWorkspaceForm" property="state" value="3" >
                       <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif"> Submitted
                   </logic:equal>
                   <logic:equal name="budgetWorkspaceForm" property="state" value="4" >
                       <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif"> Agreed
                   </logic:equal>
                   <input  type="button" value="Submit" onclick="loadAddress('homePage.do')" onmouseover="javascript:this.className = 'buttonOver';" onmouseout="javascript:this.className = 'button'" class="button">
               </td></tr>
           </table>

           <br/>
           <table width="100%" class="portal" cellspacing="0" >
               <tr><th class="head" align="left" >Data Entry Profiles</th></tr>
               <logic:iterate id="profile" name="budgetWorkspaceForm" property="dataEntryProfiles" >
                   <tr><td>
                       <a href='javascript:reviewBudget(<bean:write name="budgetWorkspaceForm" property="selectedStructureElementId" />, <bean:write name="budgetWorkspaceForm" property="selectedStructureElementId" />, "" )'>
                           <bean:write name="profile" property="dataEntryProfileEntityRef" /> - <bean:write name="profile" property="description" />
                       </a>
                   </td></tr>
               </logic:iterate>
           </table>

           <br/>
           <table width="100%" class="portal" cellspacing="0" >
               <tr><th class="head" align="left" >Cell Calculations</th></tr>
               <tr><td><a href="#">Salary ERB</a></td></tr>
               <tr><td><a href="#">Machines and Hardware</a></td></tr>
               <tr><td><a href="#">Software Services</a></td></tr>
           </table>

	   </td>
       <td width="33%" valign="top">
           <table width="100%" class="portal" cellspacing="0" >
               <tr><th class="head" align="left" >Budget Violations</th></tr>
               <tr><td>None</td></tr>
           </table>

           <br/>
           <table width="100%" class="portal" cellspacing="0" style="background-color:white;">
               <tr><th class="head" align="left" colspan="2">Sales Profit</th></tr>
               <tr><td>Sales</td><td>£20,000</td></tr>
               <tr><td>Cost of Sales</td><td>£10,000</td></tr>
               <tr><td>Profit</td><td>£10,000</td></tr>
           </table>

           <br/>
        <table width="100%" class="portal" cellspacing="0" style="background-color:white;">
            <tr><th class="head" align="left" colspan="4">Budget Summary</th></tr>
            <tr><td></td><td><b>Actual</b></td><td><b>Budget</b></td><td><b>Variance</b></td></tr>
            <tr><td>A Passenger Revenue</td><td>20,000</td><td>5,000</td><td style="color:red;">-15,000</td></tr>
            <tr><td>H Miscellaneous DOCS</td><td>10,000</td><td>15,000</td><td>5,000</td></tr>
            <tr><td>K Airport Charges</td><td>10,000</td><td>15,000</td><td>5,000</td></tr>
            <tr><td>M Maintenance</td><td>10,000</td><td>15,000</td><td>5,000</td></tr>
            <tr><td>R Distribution</td><td>10,000</td><td>15,000</td><td>5,000</td></tr>
        </table>

        <br>
        <table width="100%" class="portal" cellspacing="0">
            <tr><th class="head" align="left" colspan="4">Embedded Finance Form</th></tr>
            <tr><td><img src="<%=request.getContextPath()%>/images/review.bmp"></td></tr>
        </table>
                  </td>
                  <td width="33%" valign="top">
           <table width="100%" class="portal" cellspacing="0" >
               <tr><th class="head" align="left" >Analysis Reports</th></tr>
               <logic:iterate id="report" name="budgetWorkspaceForm" property="xmlReports" >
                   <tr><td>
                   <a href='javascript:openReportView( "<bean:write name="report" property="xmlReportEntityRef.tokenizedKey" />")'><bean:write name="report" property="xmlReportEntityRef" /></a>
                   </td></tr>
               </logic:iterate>
           </table>

           <br/>
                      <table width="100%" class="portal" cellspacing="0" >
                          <tr><th class="head" align="left" >Excel Reports</th></tr>
                          <tr><td><a href="#">Expenditure</a></td></tr>
                          <tr><td><a href="#">Profit and Loss</a></td></tr>
                          <tr><td><a href="#">Quick Summary</a></td></tr>
                      </table>

	   </td>
</tr>	   

</table>

    
<html:hidden property="oldUserCount" />
<html:hidden property="oldDepth" />
<html:hidden property="structureElementId" styleId="struc_id" />
<html:hidden property="stateFilter" styleId="filter" />
<input type="hidden" name="topNode" id="topNode" />
<input type="hidden" name="pageSource" id="pageSource" value="bcStatus" />
<input type="hidden" name="selectedStructureElementId" id="selectedStructureElementId" />
<html:hidden property="structureElementList" styleId="structureElementList"/>
<html:hidden property="visIdList" styleId="visIdList"/>
<html:hidden property="oldDepthList" styleId="oldDepthList"/>
<html:hidden property="oldUserCountList"  styleId="oldUserCountList"/>
<html:hidden property="descriptionList" styleId="descriptionList"/>
<html:hidden property="addId" styleId="addId" />
<html:hidden property="oldId" styleId="oldId" />
</html:form>

</body>
