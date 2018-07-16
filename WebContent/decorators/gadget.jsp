<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<!DOCTYPE html>
<html>

<head>
<meta content="gadget" name="decorator"/>
<bean:define id="dev" scope="application" name="cpSystemProperties" property="dev"/>	
<%@ include file="/includes/noCachingAllowed.jsp" %>
<%@ include file="/includes/dojoControl.jsp" %>
	
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cp.css" type="text/css"/>
<script src="<%=request.getContextPath()%>/js/globals.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/cp.js" type="text/javascript"></script>
<script type="text/javascript">
function getContextPath()
{
	return '<%=request.getContextPath()%>';
}		
</script>
	<decorator:head/>
</head>

<body style="margin:0">
	<decorator:body/>
</body>
</html>