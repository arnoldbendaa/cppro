<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>

</head>

<body >

<div align="center">
    <h1 class="header1"><bean:message key="cp.login.title"/></H1>

    <jsp:include page="../system/errorsInc.jsp"/>

    <html:form action="testLogin.do">
        <table>
            <tr>
                <td align="right" ><bean:message key="cp.login.userId"/></td>
                <td align="left">
                    <html:text property="userId" size="16" styleId="userId"/>
                </td>
            </tr>
            <tr>
                <td align="right" ><bean:message key="cp.login.password"/></td>
                <td align="left">
                    <html:password property="password" size="16"/>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                    <cp:CPButton buttonType="submit">
                        <bean:message key="cp.login.submit"/>
                    </cp:CPButton>
                </td>
            </tr>
        </table>
    </html:form>
</div>
</body>
