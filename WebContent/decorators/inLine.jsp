<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<html>

<head>
<%@ include file="/includes/noCachingAllowed.jsp" %>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/libs/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
<script type="text/javascript">
    dojo.require("dijit.form.Button");
</script>
<style type="text/css">
	@import "<%=request.getContextPath()%>/css/coa_dojo_1_9_3.css";
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cp.css" type="text/css"/>
    <decorator:head/>
</head>

<body class="soria">
<!-- this forces color into tabbed content pane
	it may cause errors so ne aware            -->
<decorator:body />
</body>
</html>


