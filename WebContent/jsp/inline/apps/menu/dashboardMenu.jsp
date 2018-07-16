<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
	<%
	    String defaultShow = "";
	%>
<div class="cpAutoScroll">
	<logic:equal name="dashboardForm" property="auctionAccess" value="true">	
		<div class="cpDashboardMenuIcon cpAuctionIcon">
			<a href="<%=request.getContextPath()%>/dashboard/auction" target="_blank" >Auction</a>
		</div>
	</logic:equal>
	<logic:equal name="dashboardForm" property="hierarchyAccess" value="true">	
		<div class="cpDashboardMenuIcon cpHierarchyIcon">Hierarchy</div>
		<div class="cpDashboardList">
			<div class="header" onClick="showHideBoardsDiv('hierarchy')">choose board <span class="arrow" ></span></div>
			<ul class="list" id="hierarchy" style="display:none">
				<logic:iterate id="form" name="dashboardForm" property="dashboards" indexId="elementIndex">
					<logic:equal name="form" property="dashboardType" value="HIERARCHY">
						<li>
							<a class="item-label" href="<%=request.getContextPath()%>/dashboard/form/hierarchy/<bean:write name="form" property="sUUID" />" target="_blank" active-link="active"><bean:write name="form" property="dashboardName" /></a>
						</li>
					</logic:equal>	
				</logic:iterate>
			</ul>
		</div>
	</logic:equal>
	<logic:equal name="dashboardForm" property="freeFormAccess" value="true">	
		<div class="cpDashboardMenuIcon cpFreeFormIcon">Free form</div>
		<div class="cpDashboardList">
			<div class="header" onclick="showHideBoardsDiv('free')">choose board <span class="arrow" ></span></div>
			<ul class="list" id="free" style="display:none">
				<logic:iterate id="form" name="dashboardForm" property="dashboards" indexId="elementIndex">
					<logic:equal name="form" property="dashboardType" value="FREEFORM">
						<li>
							<a class="item-label" href="<%=request.getContextPath()%>/dashboard/form/freeform/<bean:write name="form" property="sUUID" />" target="_blank" active-link="active"><bean:write name="form" property="dashboardName" /></a>
						</li>
					</logic:equal>	
				</logic:iterate>
			</ul>
		</div>
	</logic:equal>
	<br class="clear-both">
</div>