<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
<script language="JavaScript1.2" type="text/javascript">
function changeRa()
{
    var form;

    depthField = getDocumentObject('selectedDepth');
    stateField = getDocumentObject('selectedState');

    depthField.value = -2;
    stateField.value = -1;

    form = getDocumentObject('budgetCycleHistoryDetailsForm');
    form.submit();
}

function filterElements(depth, state)
{
    var form;
    var depthField;
    var stateField;

    depthField = getDocumentObject('selectedDepth');
    stateField = getDocumentObject('selectedState');

    depthField.value = depth;
    stateField.value = state;

    form = getDocumentObject('budgetCycleHistoryDetailsForm');
    form.submit();
}

function loadList()
{
    loadAddress('budgetCycles.do');
}

function contactBudgetUser(bcId, structureElementId, cascade, structureId)
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
	params = params + ',width=630,height=430,left=100,top=100';

	url = '<%=request.getContextPath()%>/communicationNewSetup.do?MessageType=new&pageSource=budgetHistory&budgetCycleId=' + bcId;
	url = url + '&structureElementId=' + structureElementId;
    url = url + '&cascade=' + cascade;
    url = url + '&structureId=' + structureId;
    window.open(url, '_blank', params);
	//window.open(url, '_newMessage', params);
}

<bean:define id="noInList" name="cycleHistoryDetailsForm" property="elementSize" type="Integer"  />
<bean:define id="bcId" name="cycleHistoryDetailsForm" property="budgetCycleId" type="Integer"/>
function contactAllUsers()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
	params = params + ',width=630,height=430,left=100,top=100';

	url = '<%=request.getContextPath()%>/communicationNewSetup.do?MessageType=new&pageSource=budgetHistoryAll&budgetCycleId=' + <%=bcId%>;

    var keyString = "";
    var contactString = "";
    for( var i=0 ; i < <%=noInList%> ; i++ )
	{
        keyString = "elements_" + i + "_structureElementId";
	    if( getDocumentObject(keyString) != null )
        {
            contactString = '&contactId=' + getDocumentObject(keyString).value;
            url = url + contactString;
        }
    }

    window.open(url, '_blank', params);
	//window.open(url, '_newMessage', params);
}

</script>
</head>

<body>
<H5 class="header5"><bean:message key="cp.cycle.detail.history.title" /></H5>
<html:form action="budgetCycleHistoryDetails" styleId="budgetCycleHistoryDetailsForm" >
<!-- header -->
<table width="90%" >
<tr>
    <td><bean:message key="cp.cycle.detail.history.header.model" /></td>
    <td>&nbsp;</td>
    <td colspan="1" ><bean:write name="cycleHistoryDetailsForm" property="selectedCycle.modelIdentifier" /></td>
    <logic:empty  name="cycleHistoryDetailsForm" property="depths" >
        <td align="right" width="40%" rowspan="5" colspan="2" >&nbsp;</td>
    </logic:empty >
    <logic:notEmpty name="cycleHistoryDetailsForm" property="depths" >
    <td align="right" width="30%" rowspan="5" valign="top" ><bean:message key="cp.cycle.detail.history.header.ra" /></td>
    <td rowspan="5" valign="top"  >
        <html:select property="selectedStructureElement" styleId="selectedStructureElement"  onchange="changeRa()" >
            <html:options property="structureElements" labelName="cycleHistoryDetailsForm" labelProperty="depthLabels" />
        </html:select>
    </td>
    </logic:notEmpty>
</tr>
<tr>
    <td><bean:message key="cp.cycle.detail.history.header.cycle" /></td>
    <td>&nbsp;</td>
    <td colspan="1" ><bean:write name="cycleHistoryDetailsForm" property="selectedCycle.identifier" /></td>
</tr>
<tr>
    <td><bean:message key="cp.cycle.detail.history.header.planed" /></td>
    <td>&nbsp;</td>
    <td colspan="1" ><bean:write name="cycleHistoryDetailsForm" property="selectedCycle.plannedEndDate_Timestamp" formatKey="cp.date.format" /></td>
</tr>
<tr>
    <td><bean:message key="cp.cycle.detail.history.header.complete" /></td>
    <td>&nbsp;</td>
    <td colspan="1" ><bean:write name="cycleHistoryDetailsForm" property="selectedCycle.endDate_Timestamp" formatKey="cp.date.format"/></td>
</tr>
<tr>
    <td><bean:message key="cp.cycle.detail.history.header.overrun" /></td>
    <td>&nbsp;</td>
    <td><bean:write name="cycleHistoryDetailsForm" property="selectedCycle.overRunBy" /></td>
</tr>
</table>

<br/>
<html:hidden property="pageSource" />
<logic:equal value="budgetCycleList" name="cycleHistoryDetailsForm" property="pageSource" >
    <cp:CPButton onclick="loadList()" >
        <bean:message key="cp.performanceType.back" />
    </cp:CPButton>
    <br/>
</logic:equal>

<table width="99%" cellspacing="0" border="0" >
<!-- table header -->
<tr>
    <th rowspan="2" class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.rd"/></th>
    <th rowspan="2" class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.elements"/></th>
    <th rowspan="2" class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.not"/></th>
    <th rowspan="2" class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.prepare"/></th>
    <th rowspan="2" class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.submitted"/></th>
    <th rowspan="2" class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.agreed"/></th>
    <th rowspan="2" class="groupcell" title='<bean:message key="cp.cycle.detail.history.table.header.na.description" />'><bean:message key="cp.cycle.detail.history.table.header.na"/></th>
    <th colspan="5" class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.complete"/></th>
</tr>
<tr>
    <th class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.before"/></th>
    <th class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.on"/></th>
    <th class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.g1"/></th>
    <th class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.g2"/></th>
    <th class="groupcell"><bean:message key="cp.cycle.detail.history.table.header.g7"/></th>
</tr>

<logic:iterate id="stateLine" name="cycleHistoryDetailsForm"  property="detailStateLines" type="com.cedar.cp.utc.struts.cyclehistory.CycleStateRADepthLineDTO" >
    <tr>
        <td align="center" >
            <logic:equal value="-1" name="stateLine" property="depth" >
                <bean:message key="cp.cycle.detail.history.table.detail.leaf" />
            </logic:equal>
            <logic:notEqual value="-1" name="stateLine" property="depth" >
                <bean:write name="stateLine" property="depth" />
            </logic:notEqual>
        </td>
        <td align="center" >
            <bean:write name="stateLine" property="elementCount" />
        </td>
        <td align="center" >
            <a href='javascript:filterElements(<bean:write name="stateLine" property="depth"/>, 0 )'>
                <bean:write name="stateLine" property="notStartedCount" />
            </a>
        </td>
        <td align="center" >
            <a href='javascript:filterElements(<bean:write name="stateLine" property="depth"/>, 2 )'>
                <bean:write name="stateLine" property="preparingCount" />
            </a>
        </td>
        <td align="center" >
            <a href='javascript:filterElements(<bean:write name="stateLine" property="depth"/>, 3 )'>
                <bean:write name="stateLine" property="submittedCount" />
            </a>
        </td>
        <td align="center" >
            <a href='javascript:filterElements(<bean:write name="stateLine" property="depth"/>, 4 )'>
                <bean:write name="stateLine" property="agreedCount" />
            </a>
        </td>
        <td align="center" >
            <a href='javascript:filterElements(<bean:write name="stateLine" property="depth"/>, 5 )'>
                <bean:write name="stateLine" property="NACount" />
            </a>
        </td>
        <td align="center" >
            <bean:write name="stateLine" property="agreedBefore" />
        </td>
        <td align="center" >
            <bean:write name="stateLine" property="agreedOn" />
        </td>
        <td align="center" >
            <bean:write name="stateLine" property="agreed1" />
        </td>
        <td align="center" >
            <bean:write name="stateLine" property="agreed2_7" />
        </td>
        <td align="center" >
            <bean:write name="stateLine" property="agreed7" />
        </td>
    </tr>
</logic:iterate>
</table>

<logic:notEqual value="-2" name="cycleHistoryDetailsForm"  property="selectedDepth">
<bean:define id="detailDepth" name="cycleHistoryDetailsForm" property="selectedDepth" />
<bean:define id="detailState" name="cycleHistoryDetailsForm" property="selectedState" />
<bean:define id="detailStateText" value="Not Started" />
<%
    int test = ((Integer)detailState).intValue();
    switch(test)
    {
        case 2:
            detailStateText = "Preparing";
            break;
        case 3:
            detailStateText = "Submitted";
            break;
        case 4:
            detailStateText = "Agreed";
            break;
        case 5: //this containd state 5 and 6  
            detailStateText = "N/A";
            break;
    }
%>
<br/>

    <table width="99%" cellspacing="0" border="0">
        <tr>
            <th class="groupcell" colspan="4">
                <logic:equal value="-1" name="cycleHistoryDetailsForm" property="selectedDepth" >
                    <bean:message key="cp.cycle.detail.history.table.detail.title.leaf" arg0="<%=String.valueOf(detailStateText)%>" />
                </logic:equal>
                <logic:notEqual value="-1" name="cycleHistoryDetailsForm" property="selectedDepth" >
                    <bean:message key="cp.cycle.detail.history.table.detail.title" arg0="<%=String.valueOf(detailDepth)%>" arg1="<%=String.valueOf(detailStateText)%>" />
                </logic:notEqual>
            </th>
        </tr>
        <tr>
            <th class="groupcell" colspan="2" >
                <img alt="Contact All Budget Holders" title="Contact All Budget Holders" height="13" align="left" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactAllUsers()' style="cursor:pointer">
                <bean:message key="cp.cycle.detail.history.table.detail.identifier" />
            </th>
            <th class="groupcell">
                <bean:message key="cp.cycle.detail.history.table.detail.description" />
            </th>
            <th class="groupcell">
                <bean:message key="cp.cycle.detail.history.table.detail.date" />
            </th>
        </tr>

        <logic:iterate id="elements" name="cycleHistoryDetailsForm" property="elements"  type="com.cedar.cp.utc.struts.cyclehistory.DetailedBudgetStateLineDTO" indexId="rowIndex" >
        <logic:equal value="<%=detailState.toString()%>" name="elements" property="newState" >
        <tr>
            <td align="left" width="2%" nowrap="true" >
                <logic:equal name="elements" property="contact" value="true" >
                    <img alt="Contact Budget Holder" title="Contact Budget Holder" height="13" align="absmiddle" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactBudgetUser(<bean:write name="cycleHistoryDetailsForm" property="budgetCycleId"  />,<bean:write name="elements" property="structureElementId" />, false, 0)' style="cursor:pointer">
                    <img alt="Contact all area Budget Holders " title="Contact all area Budget Holders " align="absmiddle" src="<%=request.getContextPath()%>/images/downarrow.gif" onclick='contactBudgetUser(<bean:write name="cycleHistoryDetailsForm" property="budgetCycleId"  />,<bean:write name="elements" property="structureElementId" />, true, <bean:write name="elements" property="structureId" />)' style="cursor:pointer">
                    <input type="hidden" id='<%="elements_" + rowIndex + "_structureElementId" %>' name='<%="elements_" + rowIndex + "_structureElementId" %>' value='<%=elements.getStructureElementId()%>'/>
                </logic:equal>
                <logic:equal name="elements" property="contact" value="false" >
                    &nbsp;
                </logic:equal>
            </td>
            <td align="left" >
                <bean:write name="elements" property="visId" />
            </td>
            <td>
                <bean:write name="elements" property="description" />
            </td>
            <td>
                <bean:write name="elements" property="dateChanged" />
            </td>
        </tr>
        </logic:equal>
        </logic:iterate>
    </table>
</logic:notEqual>

<html:hidden property="budgetCycleId" />
<html:hidden property="selectedDepth" styleId="selectedDepth"/>
<html:hidden property="selectedState" styleId="selectedState"/>
</html:form>
</body>