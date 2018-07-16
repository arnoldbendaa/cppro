<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<html:form action="/virements.do" styleId="virementsForm">
<html:hidden property="userAction" name="virementsForm"/>
<html:hidden property="requestId" name="virementsForm"/>
</html:form>
<table width="100%">
	<logic:notEmpty name="virementsForm" property="authorisableVirements">
		<!-- Virements awaiting your authorisation -->
		<tr>
			<th colspan="8">Transfers Requiring Your Authorisation</th>
		</tr>
		<tr>
			<th>Model</th>
			<th>Id</th>
			<th>Date Submitted</th>
			<th>Owner</th>
			<th>Authorisers</th>
			<th>Reference</th>
			<th>Status</th>
			<th align="center">Action</th>
		</tr>
		<logic:iterate id="tran" name="virementsForm" property="authorisableVirements"
					   type="com.cedar.cp.utc.struts.virement.VirementSummaryDTO">
			<tr>
				<td><bean:write name="tran" property="modelRef"/></td>
				<td><bean:write name="tran" property="requestId"/></td>
				<td><bean:write name="tran" property="dateSubmitted"/></td>
				<td><bean:write name="tran" property="ownerRef"/></td>
				<td>
					<logic:iterate id="auth" name="tran" property="authorisers" type="com.cedar.cp.api.user.UserRef">
						<bean:write name="auth" property="narrative"/>&nbsp;
					</logic:iterate>
				</td>
				<td><bean:write name="tran" property="reference"/></td>
				<td><bean:write name="tran" property="statusText"/></td>
				<td>
				<logic:equal value="1" property="status" name="tran">
					<% String authVirement = "javascript:authVirement('"+tran.getRequestRef().getTokenizedKey()+"')"; %>
					<cp:CPButton buttonType="button" onclick="<%=authVirement%>">Review</cp:CPButton>
				</logic:equal>
				<td>
			</tr>
		</logic:iterate>
		<tr></tr>
		<tr></tr>
	</logic:notEmpty>
</table>
