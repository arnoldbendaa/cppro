<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title><bean:message key="cp.commumication.message.title.distributionlist.title"/></title>
<bean:define id="noInList" type="Integer" property="internalListCount" name="communicationDistributionListForm"/>
<bean:define id="noExList" type="Integer" property="externalListCount" name="communicationDistributionListForm"/>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/stringTokenizer.js"></script>

<script type="text/javascript">
    //global values for this page
    var separator = ";";
    var select_flag = true;

    function getAlreadySelectedDists()
    {
        var nameList = getDocumentObjectFromObject(window.opener.document, 'message.toDist');
        var name = '';

        if (nameList.value.length > 0 && nameList.value.indexOf(separator) > 0)
        {
            var nameTokenizer = new StringTokenizer (nameList.value, separator);

            while (nameTokenizer.hasMoreTokens())
            {
                name = nameTokenizer.nextToken();

                setSelected(name);
            }
        }
        else if (nameList.value.length > 0)
        {
            name = nameList.value;
            setSelected(name);
        }
    }

    function setSelected(name)
    {
        var select_obj;
        var name_obj;

        for (var i = 0; i < <%=noInList + noExList%>; i++)
        {
            name_obj = getDocumentObject("selected_name_" + i);

            if (name_obj.value == name)
            {
                select_obj = getDocumentObject("select_" + i);
                select_obj.checked = true;
                if (select_flag)
                {
                    select_flag = false;
                    getDocumentObject('select_all').value = 'De-select All';
                }
                break;
            }
        }
    }

    function returnDist()
    {
        var returnObject = getDocumentObjectFromObject(window.opener.document, 'message.toDist');
        var hidToDistType = getDocumentObjectFromObject(window.opener.document, 'message.toDistType');

        var select_obj;

        var name = "";
        var name_obj;
        var distType = "";

        for (var i = 0; i < <%=noInList + noExList%>; i++)
        {
            select_obj = getDocumentObject("select_" + i);
            if (select_obj.checked)
            {
                name_obj = getDocumentObject("selected_name_" + i);
                if (name.length > 0)
                {
                    name = name + separator;
                    distType = distType + separator;
                }
                name = name + name_obj.value;
                if (i < <%=noInList%>) 
                {
                	distType = distType + "internal";
                }
                else 
                {
                	distType = distType + "external";
                }
            }
        }

        returnObject.value = name;
        hidToDistType.value = distType;

        self.close();
    }


    function selectAll()
    {
        for (var i = 0; i < <%=noInList + noExList%>; i++)
        {
            select_obj = getDocumentObject("select_" + i);
            select_obj.checked = select_flag;
        }

        if (select_flag)
        {
            select_flag = false;
            getDocumentObject('select_all').value = 'De-select All';
        }
        else
        {
            select_flag = true;
            getDocumentObject('select_all').value = 'Select All';
        }
    }

    function showUsers(id, internal)
    {
        params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
        params = params + ',width=630,height=430,left=120,top=120';
        target = '<%=request.getContextPath()%>/communicationDistributionUserListSetup.do?listId=' + id + '&internal=' + internal;
        window.open(target, '_blank', params);
    }

dojo.addOnLoad(function()
{
	getAlreadySelectedDists();
});

</script>
</head>

<body>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

    <div dojoType="dijit.layout.ContentPane" region="top" widgetId="selectTop" style="padding-left:20px;padding-right:18px">
        <table width="100%" >
            <tr>
                <th width="10%"><bean:message key="cp.commumication.message.label.user.select"/></th>
                <th width="35%"><bean:message key="cp.commumication.message.title.distributionlist.visid"/></th>
                <th width="45%"><bean:message key="cp.commumication.message.title.distributionlist.description"/></th>
                <th width="10%"><bean:message key="cp.commumication.message.title.distributionlist.contents"/></th>
            </tr>
        </table>
    </div>


    <div dojoType="dijit.layout.ContentPane" region="center" widgetId="selectClient" style="padding-left:20px;overflow:auto;">
        <table width="100%" >
            <logic:empty name="communicationDistributionListForm" property="internalDistributions" >
				<logic:empty name="communicationDistributionListForm" property="externalDistributions" >
                <tr>
                    <td colspan="4" align="center">No Distribution lists defined</td>
                </tr>
				</logic:empty>
            </logic:empty>
			<% int idx = 0; %>
            <logic:iterate id="dist" name="communicationDistributionListForm" property="internalDistributions" type="java.util.List" indexId="rowIndex">
                <tr>
                    <td align="center" width="10%">
                        <input type="checkbox" id="select_<%=idx%>" name="select_<%=idx%>"/>
                        <input type="hidden" id='<%="selected_name_" + idx %>' value='<%=dist.get(0)%>' name='<%="selected_name_" + idx %>'/>
                    </td>
                    <td width="35%"><%=dist.get(0)%></td>
                    <td width="45%"><%=dist.get(1)%></td>
                    <td width="9%" >
                        <%String function = "showUsers(" + dist.get(2) + ", true" + ")";%>
                        <cp:CPButton buttonType="button" onclick="<%=function%>" >
                            <bean:message key="cp.commumication.message.label.users"/>
                        </cp:CPButton>
                    </td>
                </tr>
				<% idx++; %>
            </logic:iterate>
			<logic:iterate id="dist" name="communicationDistributionListForm" property="externalDistributions" type="java.util.List" indexId="rowIndex">
                <tr>
                    <td align="center" width="10%">
                        <input type="checkbox" id="select_<%=idx%>" name="select_<%=idx%>"/>
                        <input type="hidden" id='<%="selected_name_" + idx %>' value='<%=dist.get(0)%>' name='<%="selected_name_" + idx %>'/>
                    </td>
                    <td width="35%"><%=dist.get(0)%></td>
                    <td width="45%"><%=dist.get(1)%></td>
                    <td width="9%" >
                        <%String function = "showUsers(" + dist.get(2) + ", false" + ")";%>
                        <cp:CPButton buttonType="button" onclick="<%=function%>" >
                            <bean:message key="cp.commumication.message.label.users"/>
                        </cp:CPButton>
                    </td>
                </tr>
				<% idx++; %>
            </logic:iterate>
        </table>
    </div>

    <div dojoType="dijit.layout.ContentPane" region="bottom" widgetId="selectFooter" style="padding-left:20px">
		<div class="actionButtons">
			<cp:CPButton buttonType="button" onclick="selectAll()" property="select_all">
				<bean:message key="cp.commumication.message.label.selectall"/>
			</cp:CPButton>

			<cp:CPButton buttonType="button" onclick="returnDist()">
				<bean:message key="cp.commumication.message.label.ok"/>
			</cp:CPButton>

			<cp:CPButton buttonType="button" onclick="self.close()">
				<bean:message key="cp.commumication.message.label.close"/>
			</cp:CPButton>
		</div>
    </div>
</div>
</body>