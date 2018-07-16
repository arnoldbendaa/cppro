<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title><bean:message key="cp.commumication.message.title.distributionuserlist.title"/></title>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

    <div dojoType="dijit.layout.ContentPane" region="top" widgetId="selectTop" style="padding-left:20px;padding-right:18px">		
        <table width="100%" >
			<logic:equal name="communicationDistributionUserListForm" property="internal" value="true">
            <tr>			
                <th width="40%">User ID</th>
                <th width="60%">Full Name</th>
            </tr>
			</logic:equal>
			<logic:equal name="communicationDistributionUserListForm" property="internal" value="false">
            <tr>			
                <th width="100%" align="left">Email Address</th>
            </tr>
			</logic:equal>
        </table>
    </div>

    <div dojoType="dijit.layout.ContentPane" region="center"  widgetId="selectClient" style="padding-left:20px;overflow:auto;">
        <table width="100%" >
		<logic:equal name="communicationDistributionUserListForm" property="internal" value="true">
            <logic:iterate id="dist" name="communicationDistributionUserListForm" property="users" type="com.cedar.cp.utc.struts.admin.report.user.UserDTO" indexId="rowIndex">
                <tr>
                    <td width="40%"><bean:write name="dist" property="name"/></td>
                    <td width="60%"><bean:write name="dist" property="fullName"/></td>
                </tr>
            </logic:iterate>
		</logic:equal>	
		<logic:equal name="communicationDistributionUserListForm" property="internal" value="false">
            <logic:iterate id="dist" name="communicationDistributionUserListForm" property="users" type="com.cedar.cp.utc.struts.admin.report.user.UserDTO" indexId="rowIndex">
                <tr>
                    <td width="100%" align="left"><bean:write name="dist" property="emailAddress"/></td>
                </tr>
            </logic:iterate>	
		</logic:equal>
        </table>
    </div>

    <div dojoType="dijit.layout.ContentPane" region="bottom"  >
		<div class="actionButtons">
			<cp:CPButton buttonType="button" onclick="self.close()">
				<bean:message key="cp.commumication.message.label.close"/>
			</cp:CPButton>
		</div>
    </div>
</div>
</body>