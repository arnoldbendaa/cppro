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

<script type="text/javascript">
    function getContextPath()
    {
        return '<%=request.getContextPath()%>';
    }
</script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/cp.css" type="text/css"/>
    <script src="<%=request.getContextPath()%>/js/cp.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/globals.js" type="text/javascript"></script>
<decorator:head/>
</head>

<body bgcolor="#FFFFFF" style="padding:0;margin:0" class="claro" >
	<decorator:body/>
</body>
</html>