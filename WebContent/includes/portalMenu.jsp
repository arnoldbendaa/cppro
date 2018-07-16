<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
<table>
    <tr>
        <td>
            <cp:CPButton  onclick="loadAddress('homePage.do')">
                <bean:message key="cp.menu.home"/>
            </cp:CPButton>
        </td>
    </tr>
    <tr>
        <td>
            <cp:CPButton  onclick="loadAddress('mainMenu.do')">
                <bean:message key="cp.menu.mainMenu"/>
            </cp:CPButton>
        </td>
    </tr>
    <tr>
        <td>
            <cp:CPButton onclick="loadAddress('communicationListSetup.do')">
                <bean:message key="cp.menu.messages"/>
            </cp:CPButton>
        </td>
    </tr>
    <tr>
        <td>
            <cp:CPButton onclick="loadAddress('xmlReports.do')">
                <bean:message key="cp.menu.reports"/>
            </cp:CPButton>
        </td>        
    </tr>
</table>