<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<!DOCTYPE html>
<html>

<head>
	<meta content="main" name="decorator"/>
	<title><decorator:title default="Collaborative Planning"/></title>
	<%@ include file="/includes/noCachingAllowed.jsp" %>
	<%@ include file="/includes/dojoControl.jsp" %>
	
	<script src="/cppro/js/coa.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cp.css?version=<bean:message key="cp.footer.version"/>" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/entypo.css" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/softpro.css?version=<bean:message key="cp.footer.version"/>" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/NumberFormat154.js?version=<bean:message key="cp.footer.version"/>"></script>
	<script src="<%=request.getContextPath()%>/js/cp.js?version=<bean:message key="cp.footer.version"/>" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/globals.js?version=<bean:message key="cp.footer.version"/>" type="text/javascript"></script>
	<script type="text/javascript">
	function getContextPath()
	{
		return '<%=request.getContextPath()%>';
	}
	
	function getCookie(name) 
	{
		  var value = "; " + document.cookie;
		  var parts = value.split("; " + name + "=");
		  if (parts.length == 2) return parts.pop().split(";").shift();
	}
	
	function checkVersion()
	{
		// Get version from view
		var version = document.getElementsByClassName("softproAppVersion")[0].textContent;
		version = version.substring(version.indexOf('('),version.indexOf(')'));
		version = version.substring(version.indexOf('-r') + 2);

			// Get version from cookie
			var versionCookie = getCookie("lastRevision");
			// Compare versions
			if (versionCookie == undefined || parseInt(version) > parseInt(versionCookie)) {
				// Mark icon to make user check changes
				if (document.getElementsByClassName("cpChangeLogIcon")[0] !== undefined) {
				    document.getElementsByClassName("cpChangeLogIcon")[0].style.color = 'rgb(245, 0, 0)';
				    setInterval(function() {
				        flashtext('cpChangeLogIcon','rgb(245, 0, 0)');
				    }, 500 );
				}
			}
	}
	
	function flashtext(ele,color) {
	    var tmpColCheck = document.getElementsByClassName(ele)[0].style.color;

	    if (tmpColCheck === 'rgb(235, 235, 235)') {
	    	document.getElementsByClassName(ele)[0].style.color = color;
	    } else if (tmpColCheck === 'rgb(245, 0, 0)' || tmpColCheck === 'red') {
	      	document.getElementsByClassName(ele)[0].style.color = 'rgb(235, 235, 235)';
	    }
	} 
	</script>
	<decorator:head/>
</head>

<body class="claro" onload="checkVersion()">
	<bean:define id="mailto" scope="application" name="cpSystemProperties" property="mailto"/>

	<logic:present scope="session" name="cpContext">

		<div dojoType="dijit.layout.BorderContainer" id="main" gutters="false">

			<!--if we set the splitter to true users can size the header-->
			<div dojoType="dijit.layout.ContentPane" region="top" splitter="false">
				<%@ include file="/includes/title.jsp" %>
			</div>

			<div dojoType="dijit.layout.ContentPane" region="center" >
				<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
					<div dojoType="dijit.layout.ContentPane" region="center" id="mainFrame" >
						<decorator:body/>
					</div>
				</div>
			</div>
		</div>
	</logic:present>

	<logic:notPresent scope="session" name="cpContext">
	    <div dojoType="dijit.layout.BorderContainer" id="main" gutters="false">

			<div dojoType="dijit.layout.ContentPane" region="top" >
				<%@ include file="/includes/title.jsp" %>
			</div>

			<div dojoType="dijit.layout.ContentPane" region="center" >
				<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
					<div dojoType="dijit.layout.ContentPane" region="center" >
	                    <decorator:body/>
					</div>
				</div>
			</div>
		</div>
	</logic:notPresent>

	<input type="hidden" id="dblclkValue" value="0"/>
	
	<div dojoType="dijit.Dialog" id="profileDialog" title="DataEntry Profile Picker" style="display:none">
	    <h3>Please select a Profile</h3>
	    <table>
	        <tr>
	            <td><input id="profileCombo"></td>
	            <td><cp:CPButton onclick="useProfile()">Ok</cp:CPButton></td>
	            <td><cp:CPButton onclick="cancelProfile()">Cancel</cp:CPButton></td>
	        </tr>
	    </table>
	</div>
	<div dojoType="dijit.Dialog" id="topMenuBRA" title="BRA" style="display:none">
	</div>

</body>
</html>