<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title>Not Logged On</title>
<script type="text/javascript">
	function login()
	{
		location.href = '<%=request.getContextPath()%>/homePage.do';
	}
</script>
</head>

<body >
    <FONT class="textnormal">
        Access has been refused as you have not logged on yet !
    </FONT>
	<cp:CPButton buttonType="button" onclick="javascript:login()">Go to Login</cp:CPButton>
</body>
