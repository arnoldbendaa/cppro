<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
	<%
	    String defaultShow = "";
	%>
	<div class="cpAutoScroll" >
	<h4 class="header4 changelogHeader">Changelog</h4>
	
	<logic:iterate id="role" name="roadMapForm" property="roadMapElements" indexId="elementIndex">
		<%
		    if (elementIndex == 0) {
		    	defaultShow = "block";
			} else {
		    	defaultShow = "none";
		    }
		%>
		<div  class="roadMap" id="road-map-level1-<bean:write name='role' property='id' />">
			<span class="roadMapRevTitle" onClick="showHideDescription(<bean:write name='role' property='id' />)">&#183; Revision <bean:write name="role" property="revision" /></span> (<bean:write name="role" property="versionDate" />)
			<div class="roadMapRevDesc" id="road-map-level2-<bean:write name='role' property='id'/>" style="display:<%=defaultShow%>">
				<bean:write name='role' property='description' filter='false'/>
			</div>
		</div>
	</logic:iterate>
	</div>