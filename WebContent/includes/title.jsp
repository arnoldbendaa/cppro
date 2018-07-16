<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>

<div dojoType="dijit.layout.ContentPane" region="top" class="softproHeader">
    <table width="100%" cellpadding="0" cellspacing="0" >
        <tr>
            <td class="softproUtcWebappLogo">
                <a href="<%=request.getContextPath()%>/homePage.do" class="sp-logo-link">
                	<img src="<%=request.getContextPath()%>/images/bonhams.png" alt="logo">
                </a>
            </td>
			<td class="softproHeaderTitle">
            <logic:present scope="session" name="cpContext">
	            <logic:equal value="false" name="cpContext" scope="session" property="mustChangePassword">
					<span class="softproHelloUserText">
						<bean:message key="cp.header.welcome"/>
						<bean:write scope="session" name="cpContext" property="userName"/>
					</span>
				</logic:equal>
			</logic:present>
			</td>
			<td class="softproHeaderDevelopment">
				<bean:write scope="application" name="cpSystemProperties" property="developmentNotification"/>
			</td>
			<td class="softproAppVersion">
				<bean:write scope="application" name="cpSystemProperties" property="systemName"/> (<bean:message key="cp.footer.version"/>)
			</td>
        </tr>
    </table>		
</div>
<div dojoType="dijit.layout.ContentPane" region="bottom">
	<div class="coaMenuBar">
		<%@ include file="/includes/menu.jsp" %>
	</div>
</div>