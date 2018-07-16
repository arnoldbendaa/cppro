<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<table width="100%" >
	<tr>
		<th><bean:message key="cp.welcome.model.budgetinstruction.list.title"/></th>
	</tr>
	<logic:iterate id="instruction" name="biRequestForm" property="modelInstructions" indexId="modelInstructionsId" type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO">
		<tr>
			<td>
				<%String href = "javascript:openBI(" + instruction.getId() + ");";%>
				<html:link href="<%=href%>">
					<img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif"/>
					<bean:write name="instruction" property="identifier"/>
				</html:link>
			</td>
		</tr>
	</logic:iterate>
</table>