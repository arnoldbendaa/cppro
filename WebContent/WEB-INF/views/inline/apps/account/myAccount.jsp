<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>


<body>
<H5 class="header5"><bean:message key="cp.myAccount.title" />&nbsp;<bean:write scope="session" name="cpContext" property="userId" /></H5>

<jsp:include page="/jsp/system/errorsInc.jsp" />

<html:form method="POST" action="changeMyAccount.do">

<table border="0">
    <tr>
        <td align="right" ><bean:message key="cp.myAccount.name" /></td>
        <td align="left"><html:text property="name" maxlength="255" size="38" /></td>
    </tr>
    <tr>
        <td align="right" ><bean:message key="cp.myAccount.email" /></td>
        <td align="left"><html:text property="email" maxlength="255" size="38" /></td>
    </tr>
    <logic:equal scope="application" name= "cpSystemProperties" property="internalAuthentication" value="true">
    <tr>
        <td align="right" ><bean:message key="cp.myAccount.newPassword" /></td>
        <td align="left"><html:password property="newPassword" maxlength="50" size="16" /></td>
    </tr>
    <tr>
        <td align="right" ><bean:message key="cp.myAccount.confirmPassword" /></td>
        <td align="left"><html:password property="confirmPassword" maxlength="50" size="16" /></td>
    </tr>
    </logic:equal>
    <tr>
        <td align="right" ><bean:message key="cp.myAccount.finance.user" /></td>
        <td align="left"><html:text property="financeUser" maxlength="50" size="16" /></td>
    </tr>
    <tr>
    	<td align="right">&nbsp;</td>
        <td align="left">
            <cp:CPButton buttonType="submit" >
               	<bean:message key="cp.myAccount.submit" />
            </cp:CPButton>
            <!-- &nbsp;
            <cp:CPButton buttonType="button" onclick="loadAddress('challengeQuestionSetup.do')">
                Update Security Questions
            </cp:CPButton>-->
            &nbsp;
            <cp:CPButton buttonType="button" onclick="loadAddress('challengeWordSetup.do')">
                Password Reset Setup
            </cp:CPButton> 
        </td>
    </tr>
</table>
</html:form>
</body>
