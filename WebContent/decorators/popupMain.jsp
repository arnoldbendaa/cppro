<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<!DOCTYPE html>
<html>

<head>
    <meta content="popupMain" name="decorator"/>
    <title><decorator:title default="Collaborative Planning"/></title>
	<%@ include file="/includes/noCachingAllowed.jsp" %>
	<%@ include file="/includes/dojoControl.jsp" %>
	<script src="/cppro/js/coa.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cp.css" type="text/css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/softpro.css" type="text/css"/>

	<script src="<%=request.getContextPath()%>/js/cp.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/globals.js" type="text/javascript"></script>

    <script type="text/javascript">
        function getContextPath()
        {
            return '<%=request.getContextPath()%>';
        }
	</script>
    <decorator:head/>
</head>

<body class="claro">
<div dojoType="dijit.layout.BorderContainer" id="main" gutters="false">

    <%@ include file="/includes/titlePopup.jsp" %>

    <div dojoType="dijit.layout.ContentPane" region="center" id="mainFrame">
		<decorator:body/>
    </div>
</div>
</body>
</html>


