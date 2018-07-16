<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<script language="JavaScript1.2" type="text/javascript">
function contactBudgetUser(bcId, structureElementId, cascade, structureId, contact_approver)
{
    params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=100,top=100';

    url = '<%=request.getContextPath()%>/communicationNewSetup.do?MessageType=new&pageSource=budgetState&budgetCycleId=' + bcId;
    url = url + '&structureElementId=' + structureElementId;
    url = url + '&cascade=' + cascade;
    url = url + '&structureId=' + structureId;
    url = url + '&contactApprover=' + contact_approver;
    window.open(url, '_blank', params);
	//window.open(url, '_newMessage', params);
}

function budgetStatus(structureElement, state, addId, oldId)
{
    var form;
    var seField;
    var filterField;
    var addIdField;
    var oldIdField;

    seField = dojo.byId('struc_id');
    seField.value = structureElement;

    if (state == null)
        state = 0;
    filterField = dojo.byId('filter');
    filterField.value = state;

    addIdField = dojo.byId('addId');
    if (addId == null)
        addId = "";
    addIdField.value = addId;
    oldIdField = dojo.byId('oldId');
    if (oldId == null)
        oldId = "";
    oldIdField.value = oldId;

    form = dojo.byId('workbenchForm');
    form.submit();
}

function reviewBudget(structureElement, addId, oldId, doSubmit)
{
    reviewBudgetVersion(structureElement, addId, oldId, doSubmit, "/reviewBudget/");
}

function reviewBudgetOld(structureElement, addId, oldId, doSubmit)
{
    reviewBudgetVersion(structureElement, addId, oldId, doSubmit, "/reviewBudget.do");
}

function reviewBudgetVersion(structureElement, addId, oldId, address)
{
    var form;
    var topNode = 0;
    dojo.byId('topNode').value = topNode;

    if (addId == null)
        addId = "";
    dojo.byId('addId').value = addId;
    if (oldId == null)
        oldId = "";
    dojo.byId('oldId').value = oldId;

    if (structureElement == null)
        alert('An error has occured please contact your administrator');

    dojo.byId('struc_id').value = "" + structureElement;
    dojo.byId('selectedStructureElementId').value = "" + structureElement;

    form = dojo.byId('workbenchForm');
    form.action = "<%=request.getContextPath()%>" + address;
	
    form.submit();
}

function changeState(state, bcId, seId)
{
    var form;

    if (state == null)
        alert('An error has occured please contact your administrator');
    dojo.byId('state').value = "" + state;

    if (bcId == null)
        alert('An error has occured please contact your administrator');
    dojo.byId('bcId').value = "" + bcId;

    if (seId == null)
        alert('An error has occured please contact your administrator');
    dojo.byId('seId').value = "" + seId;

    dojo.byId('addId').value = '';
    dojo.byId('oldId').value = '';

    form = dojo.byId('workbenchForm');
    form.action = "<%=request.getContextPath()%>/changeBudgetCycleState.do";
    form.submit();
}

function checkBudgetActivity(elementId, addId, oldId)
{
    dojo.byId('CCId').value = elementId;
    if (addId == null)
        addId = '';
    if (oldId == null)
        oldId = '';
    dojo.byId('addId').value = addId;
    dojo.byId('oldId').value = oldId;

    var form = dojo.byId('workbenchForm');
    form.action = "<%=request.getContextPath()%>/budgetCycleActivityList.do";
    form.submit();
}
</script>
</head>

<body>

<div id="mainTabContainer" dojoType="TabContainer" style="width: 100%; height: 100%" selectedChild="tab1"
    templateCssPath="<%=request.getContextPath()%>/css/dojo/TabContainer.css">
    <bean:define id="model" name="workbenchForm" property="root" type="com.cedar.cp.utc.struts.homepage.ModelDTO"/>
    <bean:define id="modelName" name="model" property="name"/>
    <bean:define id="cycle" name="model" property="budgetCycle[0]" type="com.cedar.cp.utc.struts.homepage.BudgetCycleDTO"/>
    <bean:define id="cycleName" name="cycle" property="budgetCycle"/>
    <div id="tab1" dojoType="ContentPane" selected="true" label='<bean:write name="cycle" property="budgetCycle"/>' style="overflow:auto;">

        <html:form action="structureElementDetails" styleId="workbenchForm">

        <jsp:include page="../system/errorsInc.jsp"/>

        <html:hidden property="submitModelName" value="<%=modelName%>"/>
        <html:hidden property="submitCycleName" value="<%=cycleName%>"/>
        <table>
            <tr>
                <td width="100%">
                    &nbsp;
                </td>
                <td align="right" nowrap class="sp-table-with-legend">
                    <br />
                    <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/red.gif"> <bean:message key="cp.approver.key.none"/>&nbsp;
                    <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/blue.gif"> <bean:message key="cp.approver.key.started"/>&nbsp;
                    <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/orange.gif"> <bean:message key="cp.approver.key.submit"/>&nbsp;
                    <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/green.gif"> <bean:message key="cp.approver.key.agree"/>&nbsp;
                    <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/purple.gif"> <bean:message key="cp.approver.key.not.plannable"/>&nbsp;
                    <img height="11" width="11" hspace="2" alt="" style="vertical-align:middle" src="<%=request.getContextPath()%>/images/grey.gif"> <bean:message key="cp.approver.key.disabled"/>&nbsp;
                    <br />
                </td>
            </tr>
        </table>

        <bean:define id="crumbsize" name="workbenchForm" property="crumbSize"/>
        <logic:greaterThan value="0" name="crumbsize">
            <logic:iterate id="crumb" name="workbenchForm" property="crumbs" type="com.cedar.cp.utc.struts.approver.CrumbDTO" indexId="crumId" length="crumbsize">
                &nbsp; /
                <a class="smalllink" href='javascript:budgetStatus(<bean:write name="crumb" property="structureElementId" />, null, null, <bean:write name="crumb" property="structureElementId" /> )' title='<bean:write name="crumb" property="description" />'>
                    <bean:write name="crumb" property="visId"/>
                </a>
            </logic:iterate>
        </logic:greaterThan>
        <logic:equal value="0" name="crumbsize">
            &nbsp;
        </logic:equal>

        <table width="100%">
        <logic:iterate name="model" id="cycle" property="budgetCycle" type="com.cedar.cp.utc.struts.homepage.BudgetCycleDTO">
        <bean:define id="bcId" name="cycle" property="budgetCycleId"/>
        <bean:define id="structureId" name="cycle" property="structureId"/>
        <tr>
            <th width="72%" rowspan="2" colspan="2" nowrap>
                <bean:message key="cp.welcome.bc.title.bl"/>
                <html:hidden name="cycle" property="modelId"/>
                <html:hidden name="cycle" property="budgetCycleId"/>
                <html:hidden name="cycle" property="structureId"/>

                <logic:notEqual name="workbenchForm" property="stateFilter" value="0">
                    <logic:equal name="workbenchForm" property="stateFilter" value="1">
                        &nbsp;<bean:message key="cp.approver.filter"/>&nbsp;<bean:message key="cp.approver.key.none"/>&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/red.gif">
                    </logic:equal>
                    <logic:equal name="workbenchForm" property="stateFilter" value="2">
                        &nbsp;<bean:message key="cp.approver.filter"/>&nbsp;<bean:message key="cp.approver.key.started"/>&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/blue.gif">
                    </logic:equal>
                    <logic:equal name="workbenchForm" property="stateFilter" value="3">
                        &nbsp;<bean:message key="cp.approver.filter"/>&nbsp;<bean:message key="cp.approver.key.submit"/>&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/orange.gif">
                    </logic:equal>
                    <logic:equal name="workbenchForm" property="stateFilter" value="4">
                        &nbsp;<bean:message key="cp.approver.filter"/>&nbsp;<bean:message key="cp.approver.key.agree"/>&nbsp;<img alt="" height="11" align="middle" src="<%=request.getContextPath()%>/images/green.gif">
                    </logic:equal>
                </logic:notEqual>
            </th>
            <th rowspan="2" colspan="2" nowrap><bean:message key="cp.welcome.bc.title.status"/></th>
            <th colspan="4" nowrap><bean:message key="cp.welcome.bc.title.rastats"/></th>
            <th rowspan="2" nowrap><bean:message key="cp.approver.actions"/></th>
        </tr>
        <tr>
            <th><img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif"></th>
            <th><img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif"></th>
            <th><img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif"></th>
            <th><img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif"></th>
        </tr>

        <logic:iterate name="cycle" id="location" property="budgetLocations" type="com.cedar.cp.utc.struts.homepage.BudgetLocationDTO">
        <bean:define id="elementId" name="location" property="structureElementId"/>
        <bean:define id="locationToolTip" name="location" property="description"/>
        <tr>
            <td align="left" colspan="2" nowrap title='<bean:write name="locationToolTip" />'>
                <a href='javascript:budgetStatus(<bean:write name="elementId"/>)'>
                    <bean:write name="location" property="identifier"/>&nbsp;-&nbsp;
                    <bean:write name="locationToolTip"/>
                </a>
                <logic:equal name="location" property="late" value="true">
                    <img alt="" align="top" width="15" title='<bean:write name="location" property="lateMessage"/>' src='<%=request.getContextPath()%>/images/<bean:write name="location" property="warningImage"/>.gif'>
                </logic:equal>
                <logic:equal name="location" property="late" value="false">
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                </logic:equal>
            </td>
            <td align="left" nowrap="true">
                <img alt="Budget Activity" title="Budget Activity" height="13" align="middle" src="<%=request.getContextPath()%>/images/settings.gif" onclick='checkBudgetActivity(<bean:write name="elementId"/>, <bean:write name="elementId"/>)' style="cursor:pointer">
                <img alt="Contact Budget Approver" title="Contact Budget Approver" height="13" align="middle" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactBudgetUser(<bean:write name="bcId" />,<bean:write name="elementId"/>, false, <bean:write name="structureId"/>, true)' style="cursor:pointer">
            </td>
            <td align="center">
                <logic:equal name="location" property="state" value="0">
                    <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif">
                </logic:equal>
                <logic:equal name="location" property="state" value="6">
                    <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.not.plannable" />' src="<%=request.getContextPath()%>/images/purple.gif">
                </logic:equal>
                <logic:equal name="location" property="state" value="5">
                    <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.disabled" />' src="<%=request.getContextPath()%>/images/grey.gif">
                </logic:equal>
                <logic:equal name="location" property="state" value="4">
                    <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif">
                </logic:equal>
                <logic:equal name="location" property="state" value="3">
                    <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif">
                </logic:equal>
                <logic:equal name="location" property="state" value="2">
                    <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif">
                </logic:equal>
            </td>
            <td align="center">
                <a href='javascript:budgetStatus(<bean:write name="elementId"/>, 1 )'>
                    <bean:write name="location" property="childNotStarted"/>
                </a>
            </td>
            <td align="center">
                <a href='javascript:budgetStatus(<bean:write name="elementId"/>, 2 )'>
                    <bean:write name="location" property="childPreparing"/>
                </a>
            </td>
            <td align="center">
                <a href='javascript:budgetStatus(<bean:write name="elementId"/>, 3 )'>
                    <bean:write name="location" property="childSubmited"/>
                </a>
            </td>
            <td align="center">
                <a href='javascript:budgetStatus(<bean:write name="elementId"/>, 4 )'>
                    <bean:write name="location" property="childAgreed"/>
                </a>
            </td>
            <%
                String reviewAction = "";
                String startAction = "javascript:changeState(1," + bcId + "," + elementId + ")";
                String submitAction = "javascript:changeState(3," + bcId + "," + elementId + ")";
                String agreeAction = "javascript:changeState(4," + bcId + "," + elementId + ")";
                String rejectAction = "javascript:changeState(2," + bcId + "," + elementId + ")";
            %>
			 <logic:present scope="session" name="cpContext">
	  			<logic:equal value="true" name="cpContext" scope="session" property="newFeaturesEnabled">
	  			 <% // new features enabled
	  			 reviewAction = "javascript:reviewBudget(" + elementId + " , " + elementId + ", null )"; %>
	  			</logic:equal>
	  			<logic:notEqual value="true" name="cpContext" scope="session" property="newFeaturesEnabled">
	  			<% // new features disabled
	  			 reviewAction = "javascript:reviewBudgetOld(" + elementId + " , " + elementId + ", null )"; %>
	  			</logic:notEqual>
	  		</logic:present>
            <td align="left" nowrap>
            <logic:present scope="session" name="cpContext">
	  			<logic:equal value="true" name="cpContext" scope="session" property="newFeaturesEnabled">
	  			xxx
	  			</logic:equal>
	  		</logic:present>
                <cp:CPButton onclick="<%=reviewAction%>">
                    <bean:message key="cp.welcome.review"/>
                </cp:CPButton>
                <logic:equal name="location" property="showReject" value="true">
                    <cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
                        <bean:message key="cp.welcome.reject"/>
                    </cp:CPButton>
                </logic:equal>
                <logic:equal name="location" property="showAgree" value="true">
                    <cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
                        <bean:message key="cp.welcome.reject"/>
                    </cp:CPButton>
                    <cp:CPButton onclick="<%=agreeAction%>" securityString="WEB_PROCESS.BudgetStatus_Agree">
                        <bean:message key="cp.welcome.agree"/>
                    </cp:CPButton>
                </logic:equal>
                <logic:equal name="location" property="showSubmit" value="true">
                    <cp:CPButton onclick="<%=submitAction%>" securityString="WEB_PROCESS.BudgetStatus_Submit">
                        <bean:message key="cp.welcome.submit"/>
                    </cp:CPButton>
                </logic:equal>
                <logic:equal name="location" property="showStart" value="true">
                    <cp:CPButton onclick="<%=startAction%>">
                        <bean:message key="cp.welcome.start"/>
                    </cp:CPButton>
                </logic:equal>
            </td>
        </tr>

        <logic:iterate name="location" id="child" property="children" type="com.cedar.cp.utc.struts.homepage.BLChildDTO">
            <bean:define id="childId" name="child" property="structureElementId"/>
            <bean:define id="childToolTip" name="child" property="description"/>
            <tr>
                <td align=right colspan="2" nowrap title='<bean:write name="childToolTip" />'>
                    <a href='javascript:budgetStatus(<bean:write name="childId"/>,null,<bean:write name="childId" /> )'>
                        <bean:write name="child" property="identifier"/>&nbsp;-&nbsp;
                        <bean:write name="childToolTip"/>
                    </a>
                    <logic:equal name="child" property="late" value="true">
                        <img alt="" align="top" width="15" title='<bean:write name="child" property="lateMessage"/>' src='<%=request.getContextPath()%>/images/<bean:write name="child" property="warningImage"/>.gif'>
                    </logic:equal>
                    <logic:equal name="child" property="late" value="false">
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    </logic:equal>
                </td>
                <td align="left" nowrap="true">
                    <img alt="Budget Activity" title="Budget Activity" height="13" align="middle" src="<%=request.getContextPath()%>/images/settings.gif" onclick='checkBudgetActivity(<bean:write name="childId"/>,<bean:write name="childId"/>)' style="cursor:pointer">
                    <logic:notEqual name="child" property="otherUserCount" value="0">
                        <img alt="Contact Budget Holder" title="Contact Budget Holder" height="13" align="middle" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactBudgetUser(<bean:write name="bcId" />,<bean:write name="childId"/>, false, 0)' style="cursor:pointer">
                        <img alt="Contact all area Budget Holders " title="Contact all area Budget Holders " align="middle" src="<%=request.getContextPath()%>/images/downarrow.gif" onclick='contactBudgetUser(<bean:write name="bcId" />,<bean:write name="childId"/>, true,<bean:write name="structureId"/>)' style="cursor:pointer">
                    </logic:notEqual>
                    <logic:equal name="child" property="otherUserCount" value="0">
                        &nbsp;
                    </logic:equal>
                </td>
                <td align="center">
                    <logic:equal name="child" property="state" value="0">
                        <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.none" />' src="<%=request.getContextPath()%>/images/red.gif">
                    </logic:equal>
                    <logic:equal name="child" property="state" value="6">
                        <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.not.plannable" />' src="<%=request.getContextPath()%>/images/purple.gif">
                    </logic:equal>
                    <logic:equal name="child" property="state" value="5">
                        <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.disabled" />' src="<%=request.getContextPath()%>/images/grey.gif">
                    </logic:equal>
                    <logic:equal name="child" property="state" value="4">
                        <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.agree" />' src="<%=request.getContextPath()%>/images/green.gif">
                    </logic:equal>
                    <logic:equal name="child" property="state" value="3">
                        <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.submit" />' src="<%=request.getContextPath()%>/images/orange.gif">
                    </logic:equal>
                    <logic:equal name="child" property="state" value="2">
                        <img alt="" height="11" align="middle" title='<bean:message key="cp.approver.key.started" />' src="<%=request.getContextPath()%>/images/blue.gif">
                    </logic:equal>
                </td>
                <td colspan="4">&nbsp;</td>
                <%
                    startAction = "javascript:changeState(1," + bcId + "," + childId + ")";
                    submitAction = "javascript:changeState(3," + bcId + "," + childId + ")";
                    agreeAction = "javascript:changeState(4," + bcId + "," + childId + ")";
                    rejectAction = "javascript:changeState(2," + bcId + "," + childId + ")";
                %>
				 <logic:present scope="session" name="cpContext">
		  			<logic:equal value="true" name="cpContext" scope="session" property="newFeaturesEnabled">
		  			 <% // new features enabled
		  			 reviewAction = "javascript:reviewBudget(" + elementId + " , " + elementId + ", null )"; %>
		  			</logic:equal>
		  			<logic:notEqual value="true" name="cpContext" scope="session" property="newFeaturesEnabled">
		  			<% // new features disabled
		  			 reviewAction = "javascript:reviewBudgetOld(" + elementId + " , " + elementId + ", null )"; %>
		  			</logic:notEqual>
		  		</logic:present>
                <td align="left" nowrap>
                    <cp:CPButton onclick="<%=reviewAction%>">
                        <bean:message key="cp.welcome.review"/>
                    </cp:CPButton>
                    <logic:equal name="child" property="showReject" value="true">
                        <cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
                            <bean:message key="cp.welcome.reject"/>
                        </cp:CPButton>
                    </logic:equal>
                    <logic:equal name="child" property="showAgree" value="true">
                        <cp:CPButton onclick="<%=rejectAction%>" securityString="WEB_PROCESS.BudgetStatus_Reject">
                            <bean:message key="cp.welcome.reject"/>
                        </cp:CPButton>
                        <cp:CPButton onclick="<%=agreeAction%>" securityString="WEB_PROCESS.BudgetStatus_Agree">
                            <bean:message key="cp.welcome.agree"/>
                        </cp:CPButton>
                    </logic:equal>
                    <logic:equal name="child" property="showSubmit" value="true">
                        <cp:CPButton onclick="<%=submitAction%>" securityString="WEB_PROCESS.BudgetStatus_Submit">
                            <bean:message key="cp.welcome.submit"/>
                        </cp:CPButton>
                    </logic:equal>
                    <logic:equal name="child" property="showStart" value="true">
                        <cp:CPButton onclick="<%=startAction%>">
                            <bean:message key="cp.welcome.start"/>
                        </cp:CPButton>
                    </logic:equal>
                </td>
            </tr>
        </logic:iterate>
        </logic:iterate>
        </logic:iterate>
        </table>
        <html:hidden property="oldUserCount"/>
        <html:hidden property="oldDepth"/>
        <html:hidden property="structureElementId" styleId="struc_id"/>
        <html:hidden property="stateFilter" styleId="filter"/>
        <!-- dont think we need topNode -->
        <input type="hidden" name="topNode" id="topNode"/>
        <input type="hidden" name="pageSource" id="pageSource" value="bcStatus"/>
        <!-- change state values -->
        <input type="hidden" name="state" id="state"/>
        <input type="hidden" name="bcId" id="bcId"/>
        <input type="hidden" name="seId" id="seId"/>
        <input type="hidden" name="selectedStructureElementId" id="selectedStructureElementId"/>
        <!-- ccid used on activity page-->
        <input type="hidden" name="CCId" id="CCId"/>

        <br/>
        <html:hidden property="structureElementList" styleId="structureElementList"/>
        <html:hidden property="visIdList" styleId="visIdList"/>
        <html:hidden property="oldDepthList" styleId="oldDepthList"/>
        <html:hidden property="oldUserCountList" styleId="oldUserCountList"/>
        <html:hidden property="descriptionList" styleId="descriptionList"/>
        <br/>
        <html:hidden property="addId" styleId="addId"/>
        <html:hidden property="oldId" styleId="oldId"/>
        </html:form>
    </div>

    <div id="tab2" dojoType="ContentPane" selected="false" label="Budget Instructions" href='<%=request.getContextPath()%>/budgetInstructions.do?modelId=<bean:write name="workbenchForm" property="modelId"/>&budgetCycleId=<bean:write name="workbenchForm" property="budgetCycleId"/>&structureElementId=<bean:write name="workbenchForm" property="structureElementId"/>' style="overflow:auto;display:none">
    </div>
</div>



</body>
