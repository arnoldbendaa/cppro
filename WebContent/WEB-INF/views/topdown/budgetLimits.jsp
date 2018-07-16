<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
<script language="JavaScript1.2"  type="text/javascript">
function financeCubeSearch()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=120,top=120';
	target = '<%=request.getContextPath()%>/popupSelector.do?type=3';
	window.open(target, '_blank', params);
}

function changeRA()
{
    getDocumentObject("budgetLimitForm").submit();
}

function addBudgetLimit()
{
    var form = getDocumentObject('budgetLimitForm');
    form.action = "<%=request.getContextPath()%>/editBudgetLimit.do";
    form.submit();
}

function deleteLimit(id)
{
    var confirmValue;
    confirmValue = confirm('Are you sure you want to delete budget limit :' + id);

    if(confirmValue)
    {
        var form = getDocumentObject('budgetLimitForm');
        getDocumentObject('selectedAction').value = "delete";
        getDocumentObject('deleteId').value = id;
        form.action = "<%=request.getContextPath()%>/editBudgetLimit.do";
        form.submit();
    }
}

function editLimit(id)
{
    var form = getDocumentObject('budgetLimitForm');
    getDocumentObject('selectedAction').value = "editSetup";
    getDocumentObject('editId').value = id;
    form.action = "<%=request.getContextPath()%>/editBudgetLimit.do";
    form.submit();
}

function contactBudgetUser(modelId, structureElementId)
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
	params = params + ',width=630,height=430,left=100,top=100';

	url = '<%=request.getContextPath()%>/communicationNewSetup.do?MessageType=new&pageSource=budgetLimit&modelId=' + modelId;
    url = url + '&structureElementId=' + structureElementId;
	window.open(url, '_blank', params);
}

function haveFinanceCube(flag)
{
	if(flag)
	{
		getDocumentObject("noFinanceCube").style.display='none';
		getDocumentObject("yesFinanceCube").style.display='inline';
		getDocumentObject("yesFinanceCube2").style.display='inline';
	}
	else
	{
		getDocumentObject("noFinanceCube").style.display='inline';
    	getDocumentObject("yesFinanceCube").style.display='none';
		getDocumentObject("yesFinanceCube2").style.display='none';
	}
}

function setFinanceCube(name, id)
{
	dojo.byId("financeCubeId").value = name;
	dojo.byId("financeCubeVisId").value = id;

	dojo.byId('budgetLimitForm').submit();
}

dojo.addOnLoad(function()
{
	haveFinanceCube(0 != <bean:write name="budgetLimitForm" property="financeCubeId"/>);
});
</script>



</head>
<body>


<div dojoType="dijit.layout.BorderContainer" gutters="false" class="main" >
	<div dojoType="dijit.layout.ContentPane" region="top">
		<h5 class="header5"><bean:message key="cp.budgetlimit.title" /></H5>

		<div id="noFinanceCube" style="display:none" >
			<a href="javascript:financeCubeSearch()" ><bean:message key="cp.budgetlimits.click" /></a>
		</div>
		<div id="yesFinanceCube2" >
		<html:form action="budgetLimit" styleId="budgetLimitForm">
		<table  style="padding-bottom:8px">
			<tr>
				<td>
					<bean:message key="cp.budgetlimits.model" />
				</td>
				<td>
					<bean:write name="budgetLimitForm" property="modelDisplay"/>
					<html:hidden property="modelId" />
					<html:hidden property="modelVisId" />
					<html:hidden property="modelDescription" />
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="cp.budgetlimits.fc" />
				</td>
				<td>
					<bean:write name="budgetLimitForm" property="financeCubeDisplay"/>
					<html:hidden property="financeCubeId" styleId="financeCubeId"  />
					<html:hidden property="financeCubeVisId"  styleId="financeCubeVisId" />
					<html:hidden property="financeCubeDescription"  styleId="financeCubeDescription" />
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="cp.budgetlimits.ra" />
				</td>
				<td>
					<html:select property="selectedRA" onchange="changeRA()"  >
						<html:options labelName="budgetLimitForm" property="respAreaStructureElement"  labelProperty="respAreaDisplay" />
					</html:select>
				</td>
			</tr>
		</table>
		<input type="hidden" name="selectedAction" id="selectedAction" />
		<input type="hidden" name="deleteId" id="deleteId" />
		<input type="hidden" name="editId" id="editId" />
		<html:hidden property="noOfDims" />
		</html:form>
		</div>	
	</div>

	<div region="center" id="yesFinanceCube" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%" tabPosition="top">
		<div id="tab0" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.budgetlimit.tab1.title" />' selected="true" >
<logic:notEqual value="0" name="budgetLimitForm" property="modelId" >
<p><bean:message key="cp.budgetlimit.tab1.para1" /></p>
<table width="100%" cellspacing="0">
  <tr>
    <th>&nbsp;</th>
    <th nowrap align="left" ><bean:message key="cp.budgetlimit.tab1.table.t1"/></th>
    <logic:iterate id="header" name="budgetLimitForm" property="headings" >
        <th nowrap align="left" title="<bean:write name="header" property="description" />"  ><bean:write name="header" property="identifier" /></th>
    </logic:iterate>
	<th nowrap align="left" ><bean:message key="cp.budgetlimit.tab1.table.t2"/></th>
    <th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t3"/></th>
    <th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t4"/></th>
    <th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t5"/></th>
  </tr>
  <!-- lines -->
  <logic:iterate id="lines" name="budgetLimitForm" property="imposedLimits"  type="com.cedar.cp.utc.struts.topdown.LimitsDTO" >
  <logic:equal name="lines" property="violated"  value="true" >
    <tr bgcolor="#ffcccc" >
    <td><img alt="warning" src="<%=request.getContextPath()%>/images/warnings.gif" /></td>
  </logic:equal>
  <logic:equal name="lines" property="violated"  value="false" >
    <tr>
    <td>&nbsp;</td>
  </logic:equal>
        <logic:iterate id="line" name="lines" property="structureElement" type="String" indexId="rowId" >
            <logic:equal value="0" name="rowId">
                <td nowrap="true" >
                    <img alt="Contact Budget Holder" height="13" align="middle" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactBudgetUser(<bean:write name="budgetLimitForm" property="modelId"  />,<bean:write name="lines" property="respAreaId"  />)' style="cursor:pointer">
                    <bean:write name="line" />
                </td>
            </logic:equal>
            <logic:notEqual value="0" name="rowId">
                <td nowrap="true" ><bean:write name="line" /></td>
            </logic:notEqual>
        </logic:iterate>
        <td align="right" ><bean:write name="lines" property="minString" format="#,##0.00" /></td>
        <td align="right" ><bean:write name="lines" property="maxString" format="#,##0.00" /></td>
        <td align="right" ><bean:write name="lines" property="currentValue" format="#,##0.00" /></td>
    </tr>
  </logic:iterate>
</table>
</logic:notEqual>
		</div>

		<div id="tab1" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.budgetlimit.tab2.title" />'>
<logic:notEqual value="0" name="budgetLimitForm" property="modelId" >
	<a href="javascript:addBudgetLimit()"><bean:message key="cp.budgetlimit.tab1.table.action1" /></a>
	<br/>
	<table width="100%" cellspacing="0">
	  <tr>
		<th nowrap align="left" ><bean:message key="cp.budgetlimit.tab1.table.t7"/></th>
		<logic:iterate id="header" name="budgetLimitForm" property="headings" >
			<th nowrap align="left" title="<bean:write name="header" property="description" />"  ><bean:write name="header" property="identifier" /></th>
		</logic:iterate>
		<th nowrap align="left" ><bean:message key="cp.budgetlimit.tab1.table.t2"/></th>
		<th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t3"/></th>
		<th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t4"/></th>
		<th nowrap align="left" ><bean:message key="cp.budgetlimit.tab1.table.t6"/></th>
	  </tr>
	  <!-- lines -->
	<logic:iterate id="lines" name="budgetLimitForm" property="setlimits"  type="com.cedar.cp.utc.struts.topdown.LimitsDTO" indexId="rowId" >
		<tr>
			<logic:iterate id="line" name="lines" property="structureElement" type="String" >
				<td nowrap="true" ><bean:write name="line" /></td>
			</logic:iterate>
			<td align="right" ><bean:write name="lines" property="minString" /></td>
			<td align="right" ><bean:write name="lines" property="maxString" /></td>
			<td align="right">
				<a href="javascript:editLimit(<bean:write name="lines" property="budgetLimitId"  />)"><bean:message key="cp.budgetlimit.tab1.table.action3" /></a>&nbsp;
				<a href="javascript:deleteLimit(<bean:write name="lines" property="budgetLimitId"  />)"><bean:message key="cp.budgetlimit.tab1.table.action2" /></a>
			</td>
		</tr>
	  </logic:iterate>
	</table>

	</logic:notEqual>
		</div>
	</div>
</div>

</body>