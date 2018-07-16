<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
<head>
</head>
<body>
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
                Active user Count : <bean:write name="activeUserForm" property="size"/>
                <a href="#" onclick='contactAll()'> [Contact All] </a>
                <a href="#" onclick='forceOffUser("all")'> [Force Off All non admin users] </a>
            </td>
			<td align="right">
				<cp:CPButton buttonType="button" onclick="refreshTab('active')" >Refresh</cp:CPButton>
			</td>
		</tr>
	</table>

	<table width="100%">
	<tr>
        <th class="groupcell">Active Users</th>
    </tr>
    <tr>
        <td align="center">
            <table width="96%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th style="border-bottom-width:1px; border-bottom-style: solid; border-bottom-color: gray; border-left-width:1px; border-left-style: solid; border-left-color: gray;" colspan="3">Name</th>
					<th style="border-bottom-width:1px; border-bottom-style: solid; border-bottom-color: gray;" colspan="2">Host</th>
					<th style="border-bottom-width:1px; border-bottom-style: solid; border-bottom-color: gray;" colspan="2">IP Address</th>
					<th style="border-bottom-width:1px; border-bottom-style: solid; border-bottom-color: gray;" colspan="2">Created</th>
					<th style="border-bottom-width:1px; border-bottom-style: solid; border-bottom-color: gray; border-right-width:1px; border-right-style: solid; border-right-color: gray;" colspan="2">Last Used</th>
				</tr>
				<logic:iterate id="user" name="activeUserForm" property="activeUsers" type="com.cedar.cp.utc.struts.admin.user.ActiveUserDTO" >
                    <tr>
                    <logic:equal value="true" name="user" property="you" >
                            <td width="6%" class="activeUserYou" colspan="2">&nbsp;</td>
                            <td class="activeUserYou"><bean:write name="user" property="name"/></td>
                            <td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="8%" class="activeUserYou"><bean:write name="user" property="clientHost"/></td>
							<td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="8%" class="activeUserYou"><bean:write name="user" property="clientIP"/></td>
							<td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="10%" class="activeUserYou"><bean:write name="user" property="creationTime"/></td>
							<td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="10%" class="activeUserYou"><bean:write name="user" property="lastAccessed"/></td>
					</logic:equal>
                    <logic:equal value="false" name="user" property="you" >
                            <td width="5%" class="activeUser">
                                <img alt="Contact User" title="Contact User" height="13" align="middle" src="<%=request.getContextPath()%>/images/contact.gif" onclick='contactUser(<bean:write name="user" property="PK" />)' style="cursor:pointer">
                                <img alt="Force Off" title="Force User Off System" height="13" align="middle" src="<%=request.getContextPath()%>/images/delete.gif" onclick='forceOffUser("<bean:write name="user" property="id" />")' style="cursor:pointer">
                            </td>
                            <td width="1%" class="activeUser">&nbsp;</td>
                            <td class="activeUserYou"><bean:write name="user" property="name"/></td>
                            <td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="8%" class="activeUserYou"><bean:write name="user" property="clientHost"/></td>
							<td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="8%" class="activeUserYou"><bean:write name="user" property="clientIP"/></td>
							<td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="10%" class="activeUserYou"><bean:write name="user" property="creationTime"/></td>
							<td width="1%" class="activeUserYou">&nbsp;</td>
							<td width="10%" class="activeUserYou"><bean:write name="user" property="lastAccessed"/></td>
					</logic:equal>
                    </tr>
                </logic:iterate>
            </table>
        </td>
    </tr>
</table>
</body>