<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
    <script type="text/javascript">
        dojo.addOnLoad(function()
        {
            dojo.byId('newPasswordCheck').focus();
        });
    </script>
</head>

<body>

<div align="center">
    <br />
    <h1 class="header1"><bean:message key="cp.changePassword.title"/></H1>

    <div id="errorMsg" class="error" style="height:40; overflow: auto;">
        <br />
            <p><bean:message key="cp.changePassword.errormsg"/></p>
        <br />
    </div>

    <jsp:include page="errorsInc.jsp"/>

    <html:form action="/changePassword.do">
        <table>
            <tr>
                <td class="softproLoginField">
                     <div class="left-inner-addon ">
                        <i class="sp-icon-password"></i>
                        <input type="password" name="newPassword" id="newPassword" size="32" placeholder="Password" class="form-control sp-form-control" required/>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="softproLoginField">
                     <div class="left-inner-addon ">
                        <i class="sp-icon-password"></i>
                        <input type="password" name="confirmPassword" id="confirmPassword" size="32" placeholder="Confirm Password" class="form-control sp-form-control" required/>
                    </div>
                </td>
            </tr>

            <tr>
                <td align="center" colspan="2">
                    <button class="btn btn-primary btn-lg sp-login-button" type="submit" id="submit">Submit</button> 
            </tr>
        </table>
    </html:form>
</div>
</body>
