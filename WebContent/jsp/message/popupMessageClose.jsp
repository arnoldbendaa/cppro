<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<head>

<script language="javascript">
<logic:equal value="list" name="communicationOpenForm" property="source"   >
function closeWindow()
{
	window.opener.location.href = "<%=request.getContextPath()%>/communicationListSetup.do";
	self.close();
}
</logic:equal>

<logic:equal value="sentList" name="communicationOpenForm" property="source"   >
function closeWindow()
{
	window.opener.location.href = "<%=request.getContextPath()%>/communicationSentItemListSetup.do";
	self.close();
}
</logic:equal>

<logic:equal value="last" name="communicationOpenForm" property="source"   >
function closeWindow()
{
	window.opener.location.href = "<%=request.getContextPath()%>/homePage.do?refresh=true";
	self.close();
}
</logic:equal>
</script>
</head>
<body>
<script language="javascript">closeWindow();</script>
</body>