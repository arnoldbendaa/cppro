<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<head>
<title>CP Login Redirect</title>
    <script type="text/javascript">
		dojo.addOnLoad(function()
		{
			window.location='<%=request.getContextPath()%>/jsp/system/login.jsp'
		});
	</script>
</head>

<body>
</body>