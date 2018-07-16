<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<!DOCTYPE html>
<html>
<head>
    <meta content="noDecoration" name="decorator" />
    <title><decorator:title default="Collaborative Planning" /></title>
    <%@ include file="/includes/noCachingAllowed.jsp" %>
    <decorator:head />
</head>

<body style="margin:0;padding:0;overflow:auto;height:100%">
<decorator:body />
</body>
</html>


