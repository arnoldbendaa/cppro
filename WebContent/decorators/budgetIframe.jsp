<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
<head>
    <meta content="budgetIframe" name="decorator" />
    <title><decorator:title default="Collaborative Planning" /></title>
    <%@ include file="/includes/noCachingAllowed.jsp" %>
    <%@ include file="/includes/dojoControl.jsp" %>			
        
    <script src="/cppro/js/coa.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cp.css" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/entypo.css" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/softpro.css" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/NumberFormat154.js"></script>
	<script src="<%=request.getContextPath()%>/js/cp.js" type="text/javascript"></script>


	
	<script src="<%=request.getContextPath()%>/js/globals.js" type="text/javascript"></script>
	
	
	<script type="text/javascript">
	function getContextPath()
	{
		return '<%=request.getContextPath()%>';
	}
	</script>
    
    
    <decorator:head />
</head>

<body class="claro">
	<logic:equal name="cpContext" property="newView" value="false">
	    <div dojoType="dijit.layout.BorderContainer" id="main" gutters="false">
			<div dojoType="dijit.layout.ContentPane" region="top" >
					<div dojoType="dijit.layout.ContentPane" region="top" splitter="false">
						<%@ include file="/includes/title.jsp" %>
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
					</div>
			</div>
				
			<div dojoType="dijit.layout.ContentPane" region="center" >
				<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">
					<div dojoType="dijit.layout.ContentPane" region="center" >
						<decorator:body />
					</div>
				</div>
			</div>
		</div>
	</logic:equal>
	<logic:equal name="cpContext" property="newView" value="true">
		<decorator:body/>
	</logic:equal>
</body>
</html>