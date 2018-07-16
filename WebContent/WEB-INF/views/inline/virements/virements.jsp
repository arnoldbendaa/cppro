<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
	<div dojoType="dijit.layout.ContentPane" region="top" widgetId="virementsTop" >
		<H5 class="header5">Budget Transfers</h5>
		<span style="float:right;">
			<html:form action="/virements.do" styleId="virementsForm">
			<html:hidden property="userAction" name="virementsForm"/>
			<html:hidden property="requestId" name="virementsForm"/>
			<html:checkbox style="vertical-align:middle;" property="includeChildRespAreas">
					Include all requests in my responsibility area(s)
			</html:checkbox>
			<cp:CPButton buttonType="submit" onclick="javascript:refreshList()">Refresh</cp:CPButton>
			</html:form>
		</span>
	</div>

	<div region="center" id="virTabContainer" widgetId="virTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%" tabPosition="top">
		<div id="auth" dojoType="dijit.layout.ContentPane"
			title='Transfers Requiring Your Authorisation'>
			<jsp:include page="virementsForAuth.jsp"/>
		</div>
	</div>
</div>
