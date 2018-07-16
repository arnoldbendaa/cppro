<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
	<script type="text/javascript">
dojo.addOnLoad(function()
{
	addOverFlow();
});
	</script>
</head>

<body>
<H5 class="header5"><bean:message key="cp.task.detail.title" /></H5>

<html:form action="/taskDetails" >

<logic:present name="taskDetailForm" property="taskDetails">
    <table width="80%" align="center"  >
    <tr>
        <td><bean:message key="cp.task.detail.id"/></td>
        <td width="80%" ><bean:write name="taskDetailForm" property="taskDetails.taskId" /></td>
    </tr>
    <tr>
        <td><bean:message key="cp.task.detail.user"/></td>
        <td><bean:write name="taskDetailForm" property="taskDetails.userName" /></td>
    </tr>
    <tr>
        <td><bean:message key="cp.task.detail.name"/></td>
        <td><bean:write name="taskDetailForm" property="taskDetails.taskName" /></td>
    </tr>
    <tr>
        <td><bean:message key="cp.task.detail.status"/></td>
        <td><bean:message name="taskDetailForm" property="taskDetails.statusKey" /></td>
    </tr>
    <tr>
        <td><bean:message key="cp.task.detail.created" /></td>
        <td><bean:write name="taskDetailForm" property="taskDetails.createdDate" formatKey="cp.date.format" /></td>
    </tr>

    <tr>
        <td>&nbsp;</td>
        <td align="right" >
			<logic:notEmpty name="taskDetailForm" property="from" >
				<logic:equal value="taskList" name="taskDetailForm" property="from">
					<cp:CPButton buttonType="button" onclick="loadAddress('tasks.do')" >
						<bean:message key="cp.task.detail.button.text" />
					</cp:CPButton>
				</logic:equal>
				<logic:equal value="taskReportList" name="taskDetailForm" property="from">
					<cp:CPButton buttonType="button" onclick="loadAddress('reports.do')" >
						<bean:message key="cp.task.detail.button.text" />
					</cp:CPButton>
				</logic:equal>
			</logic:notEmpty>
		</td>
	</tr>
    </table>

    <table border="0" width="90%" align="center" >
    <tr>
        <th class="groupcell" ><bean:message key="cp.task.detail.event.header" /></th>
    </tr>

    <logic:iterate name="taskDetailForm" id="event" property="events" type="com.cedar.cp.utc.struts.task.EventDTO" indexId="rowIndex" >
    <tr>
        <td>
            <bean:write  name="event" property="description" />
            <logic:notEmpty name="event" property="clientException" >
                <table width="100%" >
                    <tr>
                        <td valign="top" ><bean:message key="cp.task.detail.event.client"/></td>
                        <td style="color:red" ><bean:write  name="event" property="clientException" /></td>
                    </tr>
                </table>
            </logic:notEmpty>
            <logic:notEmpty name="event" property="exception" >
                <table width="100%" >
                    <tr>
                        <td valign="top" ><bean:message key="cp.task.detail.event.server"/></td>
                        <td style="color:red" ><bean:write  name="event" property="exception" /></td>
                    </tr>
                </table>
            </logic:notEmpty>
        </td>
    </tr>
    </logic:iterate>
    </table>

    <table width="80%" align="center">
    <tr>
        <td align="right" >
			<logic:notEmpty name="taskDetailForm" property="from" >
				<logic:equal value="taskList" name="taskDetailForm" property="from">
					<cp:CPButton buttonType="button" onclick="loadAddress('tasks.do')" >
						<bean:message key="cp.task.detail.button.text" />
					</cp:CPButton>
				</logic:equal>
				<logic:equal value="taskReportList" name="taskDetailForm" property="from">
					<cp:CPButton buttonType="button" onclick="loadAddress('reports.do')" >
						<bean:message key="cp.task.detail.button.text" />
					</cp:CPButton>
				</logic:equal>
			</logic:notEmpty>
        </td>
    </tr>
    </table>
</logic:present>

<logic:notPresent name="taskDetailForm" property="taskDetails">
    <p>
    <bean:message key="cp.task.detail.notask" />
    <p>
</logic:notPresent>

</html:form>
</body>
