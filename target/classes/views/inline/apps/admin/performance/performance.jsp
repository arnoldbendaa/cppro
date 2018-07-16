<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<body>
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><bean:message key="cp.performance.title" /></td>
			<td align="right">
				<cp:CPButton buttonType="button" onclick="refreshTab('performance')" >Refresh</cp:CPButton>
			</td>
		</tr>
	</table>

<html:form action="/performance" method="post" >

    <table width="100%">
        <tr>
            <th align="left">Type</th>
            <th align="left">Description</th>
        </tr>
        <logic:iterate name="performanceForm" property="performanceTypes" id="element" >
            <tr>
                <td><html:link action="/performanceType"
                               paramId="requiredType" paramName="element" paramProperty="key" >
                        <bean:write name="element" property="key" />
                    </html:link>
                </td>
                <td><bean:write name="element" property="value" /></td>
            </tr>
        </logic:iterate>
    </table>

</html:form>
</body>