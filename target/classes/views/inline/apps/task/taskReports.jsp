<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<table border="1" width="90%" align="center" >
<tr>
    <th><bean:message key="cp.report.list.header.name" /></th>
    <th><bean:message key="cp.report.list.header.type" /></th>
    <th><bean:message key="cp.report.list.header.taskid" /></th>
	<th><bean:message key="cp.report.list.header.created" /></th>
    <th><bean:message key="cp.report.list.header.actions" /></th>
</tr>

<logic:iterate name="reportForm" id="report" property="reports" type="com.cedar.cp.utc.struts.report.ReportDTO" indexId="rowIndex" >
<tr>
    <td>
        <a href="<%=request.getContextPath()%>/reportDetails.do?reportId=<bean:write name="report" property="reportId"/>">
            <bean:write name="report" property="reportName" />
        </a>
    </td>
    <td>
        <bean:message  name="report" property="reportType" />
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/taskDetails.do?taskId=<bean:write name="report" property="taskId"/>&from=taskReportList">
            <bean:write name="report" property="taskId" />
        </a>
    </td>
    <td>
        <bean:write name="report" property="createdDate" formatKey="cp.date.format" />
    </td>
    <td>
        <%
            String reportKey = report.getReportKey();
        %>
        <img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/delete.gif" onclick="deleteReport('<%=reportKey%>');" title="Delete report" />
    </td>
</tr>
</logic:iterate>

</table>