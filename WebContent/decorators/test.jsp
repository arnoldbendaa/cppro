<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<html>

<head>
<meta content="test" name="decorator"/>
<title><decorator:title default="Collaborative Planning"/></title>
<%@ include file="/includes/noCachingAllowed.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/dojo/cp.css" type="text/css"></link>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/globals.js" type="text/javascript"></script>    
<script type="text/javascript"  src="<%=request.getContextPath()%>/js/test/cp.js"></script>
<script type="text/javascript">
    function onUnLoad()
    {
        if (window.unloading)
            unloading();
    }

    //helper method to check if we are running in httpunit or not
    function checkBrowserOk()
    {
        var result = true;

        if (navigator == null ||
            navigator.userAgent.indexOf("httpunit") >= 0)
        {
            result = false;
        }

        return result;
    }

    function getContextPath()
    {
        return '<%=request.getContextPath()%>';
    }

    <logic:notPresent cookie="welcomePanel" >
    //set all cookies once
    setCookie("welcomePanel", "0");
    setCookie("massUpdatePanel", "0");
    setCookie("budgetLimitPanel", "0");
    </logic:notPresent>
</script>

<decorator:head/>
</head>

<body bgcolor="#FFFFFF" style="padding:0;margin:0" onunload="onUnLoad()" class="tundra">

<decorator:body/>

</body>
</html>