<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<table border="1" width="90%" align="center" >
<tr>
    <th><bean:message key="cp.task.list.header.id" /></th>
    <th><bean:message key="cp.task.list.header.name" /></th>
    <th><bean:message key="cp.task.list.header.status" /></th>
    <th><bean:message key="cp.task.list.header.userid" /></th>
	<th><bean:message key="cp.task.list.header.created" /></th>
    <th><bean:message key="cp.task.list.header.note" /></th>
</tr>

<logic:iterate name="taskForm" id="task" property="tasks" type="com.cedar.cp.utc.struts.task.TaskDTO" indexId="rowIndex" >
<tr>
    <td>
        <a href="<%=request.getContextPath()%>/taskDetails.do?taskId=<bean:write name="task" property="taskId"/>&from=taskList">
            <bean:write name="task" property="taskId" />
        </a>
    </td>
    <td>
        <bean:write name="task" property="taskName" />
    </td>
    <td>
        <bean:message name="task" property="statusKey" />
    </td>
    <td>
        <bean:write name="task" property="userName" />
    </td>
    <td>
        <bean:write name="task" property="createdDate" formatKey="cp.date.format" />
    </td>
    <td>
        <bean:write name="task" property="step" />
    </td>
</tr>
</logic:iterate>

</table>
