<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<head>
<script language="javascript">
function closeWindow()
{
	opener.location.reload(true);	
	self.close();
}
</script>
</head>

<body >
<script>closeWindow();</script>
<div style="padding:30px">
    If this does not close on its own its normally because the system prop
    for root url is fully qualified and you used the short version.
    <input type="button" value="close" onclick="closeWindow()">
</div>
</body>