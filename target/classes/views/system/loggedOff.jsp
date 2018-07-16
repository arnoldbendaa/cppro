<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<html>
<head>
	<script type="text/javascript">
		function login()
		{
			location.href = '<%=request.getContextPath()%>/homePage.do';
		}
	</script>
</head>
<body>
<div align="center">
	<h1 class="softproHeader1"><bean:message key="cp.logout.heading"/></h1>

	<br/>
	<img src="<%=request.getContextPath()%>/images/bonhams.png" alt="splash"/>
	<br/>
	<br/>
	<br/>
	<button onclick="javascript:login()" class="btn btn-primary btn-lg sp-login-button" type="submit" id="submit">Go to login page</button> 
</div>
</body>
</html>