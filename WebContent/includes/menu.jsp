<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
	<style>
	.dijit.dijitReset.dijitInline.dijitButton,.dijit.dijitReset.dijitInline.dijitDropDownButton{
		display:inline-block;
		margin-top:-10px;
	}
	</style>

<div style="float:left;text-align:left;line-height:16px">
  <div style="padding-left:6px" id="message"><cp:CPDecorator property="meta.message" default="&nbsp;" /></div>
  <div style="padding-left:6px" id="crumb"><cp:CPDecorator property="meta.breadcrumb" default="&nbsp;" writeEntireProperty="false"/></div>
</div>

<div style="float:right;vertical-align:bottom;">
<logic:present scope="session" name="cpContext">
    <logic:equal value="false" name="cpContext" scope="session" property="mustChangePassword">
      <logic:equal value="true" name="cpContext" scope="session" property="dashboardAvailable">
          <div dojoType="dijit.form.DropDownButton" iconClass="cpDashboardIcon" showLabel="false" label="Dashboard" onclick="setContentHref('dashboardId', '<%=request.getContextPath()%>/dashboardMenu.do')">
                <div id="dashboardId" dojoType="dijit.TooltipDialog" preventCache="true" style="width:600px"></div>
            </div> 
      </logic:equal>      
              <logic:equal value="true" name="cpContext" scope="session" property="roadMapAvailable">
          <div dojoType="dijit.form.DropDownButton" iconClass="cpChangeLogIcon" showLabel="false" label="Changelog" onclick="setContentHref('roadMapId', '<%=request.getContextPath()%>/roadMapMenu.do')">
                  <div id="roadMapId" dojoType="dijit.TooltipDialog" preventCache="true" style="width:600px"></div>
            </div>
            </logic:equal>
              <button dojoType="dijit.form.Button" iconClass="cpHomeIcon" label="Home" showLabel="false" onclick="loadAddress('homePage.do')">
              </button>
              <div dojoType="dijit.form.DropDownButton" iconClass="cpMenuIcon" showLabel="false" label="Menu" onclick="setContentHref('myMenu', '<%=request.getContextPath()%>/mainMenu.do')">
                <div id="myMenu" dojoType="dijit.TooltipDialog" preventCache="true" style="width:600px"></div>
               </div>
              <button dojoType="dijit.form.Button" iconClass="cpCommsIcon" label="Comms" showLabel="false" onclick="loadAddress('communicationListSetup.do', 'comms')" title="Communications">
              </button>

              <cp:hasSecurity securityString="WEB_PROCESS.XMLReports">
                  <button dojoType="dijit.form.Button" iconClass="cpAnalysisIcon" label="Analysis" showLabel="false" onclick="loadAddress('xmlReports.do', 'xmlReport')">
                  </button>
              </cp:hasSecurity>

              <div dojoType="dijit.form.DropDownButton" iconClass="cpAccountIcon" showLabel="false" label="My Account" onclick="setContentHref('account', '<%=request.getContextPath()%>/myAccountSetup.do')">
                <div id="account" dojoType="dijit.TooltipDialog" preventCache="true" style="width:500px;"></div>
               </div>

    <logic:equal value="false" name="cpContext" scope="session" property="singleSignon">
      <button dojoType="dijit.form.Button" iconClass="cpLogoutIcon" label="Log Out" showLabel="false" onclick="logout()"></button>
    </logic:equal>
    </logic:equal>
</logic:present>

<button dojoType="dijit.form.Button" iconClass="cpHelpIcon" label="Help" showLabel="false" onclick="openHelp()">
</button>
</div>