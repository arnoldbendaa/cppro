<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
    <script type="text/javascript" language="javascript">
		dojo.addOnLoad(function()
        {
			getFocus();
		});

		function doInit()
		{
			var params = 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0';
			params = params + ',width=430,height=230,left=100,top=100';

			var url = getContextPath() + '/jvmInit.do';
			var popup = window.open(url, '_jvmInit', params);
			popup.blur();
		}

		function getFocus()
		{
			window.focus();
			dojo.byId('userId').focus();
		}
	</script>
</head>

<body >
<jsp:include page="errorsIncV2.jsp"/>
<div align="center">
    <html:form action="/login.do">
        <table>
			<tr>
				<td colspan="2" class="softproSplash">
					<img src="<%=request.getContextPath()%>/images/bonhams.png" alt="splash"/>
				</td>
			</tr>
			<logic:equal scope="application" name="cpSystemProperties" property="runLevel" value="1">
            <tr><td colspan="2" align="center">
                    Access to the system is currently restricted to System Administrators.
            </td></tr>

            </logic:equal>
			<tr>
                <td colspan="2" align="center" class="softproLoginNote">
                    Note: user ids and passwords are case sensitive
                </td>
            </tr>
            <tr>
                <td class="softproLoginField softproFirstField">
                    <div class="left-inner-addon ">
                        <i class="sp-icon-user"></i>
                        <input type="text" name="userId" id="userId" size="32" placeholder="User id" class="form-control sp-form-control" required autofocus/>
                    </div>

                   
                </td>
            </tr>
            <tr>
                <td class="softproLoginField">
                     <div class="left-inner-addon ">
                        <i class="sp-icon-password"></i>
                        <input type="password" name="password" id="password" size="32" placeholder="Password" class="form-control sp-form-control" required/>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                    <button class="btn btn-primary btn-lg sp-login-button" type="submit" id="submit">Login</button> 
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                    <logic:notEqual scope="application" name="cpSystemProperties" property="resetMethod" value="None">
                        <br /><br />
                        <a class="sp-reset-password-link" href="<%=request.getContextPath()%>/reset.do">
                            Reset Password
                        </a>
                     </logic:notEqual>
                </td>
            </tr>
        </table>
    </html:form>
</div>

</body>
