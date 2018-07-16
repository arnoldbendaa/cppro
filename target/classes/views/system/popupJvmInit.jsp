<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<html>
<head>
	<script type="text/javascript">
		function closeInitWindow()
		{
			setTimeout(function(){closeAppletWindow('jvmInit');},"2000");
		}
	</script>
</head>
<body>
	<div style="width:400px;text-align:center;padding-bottom:10px;padding-top:5px;text-decoration:underline"><bean:message key="cp.jvm.title"/></div>
	<div style="width:200px;float:left;padding-left:15px">
		<bean:message key="cp.jvm.description"/>
	</div>
	<div style="width:190px;float:right;padding-right:15px">
		<jsp:include page="applet2.jsp" flush="true">
			<jsp:param name="app_name" value="jvmInit"/>
			<jsp:param name="app_width" value="190"/>
			<jsp:param name="app_height" value="100"/>
			<jsp:param name="app_class" value="com.cedar.cp.utc.de.PluginKickStart"/>
		</jsp:include>
		&nbsp;
	</div>
</body>
</html>