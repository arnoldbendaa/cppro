<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title><bean:message key="cp.reports.move.title" /></title>
</head>

<body>
<html:form action="/moveReportSetup" method="post">
    <html:hidden name="moveXmlReportForm" property="reportKey" />
    <table>
        <tr>
            <td>
                <bean:message key="cp.reports.move.help" />
                <html:select name="moveXmlReportForm" property="newFolderId">
                    <html:optionsCollection name="moveXmlReportForm" property="folders" label="optionLabel" value="folderId" filter="false"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td align="right">
    			<cp:CPButton buttonType="submit" ><bean:message key="cp.reports.move.submit" /></cp:CPButton>
	    		&nbsp;
            	<cp:CPButton onclick="javascript:window.close()" ><bean:message key="cp.reports.move.close" /></cp:CPButton>
            </td>
        </tr>
    </table>

</html:form>
</body>