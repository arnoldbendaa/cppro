<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<head>
<title >CP - Analysis Report</title>
<meta content="<bean:message key="cp.reports.title"/>" name="title"/>
</head>
<body>
    <div style="padding:30px">
        You dont have access to the report <bean:write name="xmlReportsForm" property="reportId"/>
    </div>
</body>