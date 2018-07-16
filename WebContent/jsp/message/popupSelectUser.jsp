<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<script language="javascript" src="<%=request.getContextPath()%>/js/stringTokenizer.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectUser.js"></script>
<script language="javascript" type="text/javascript">

    function getStoreURL()
    {
        return '<%=request.getContextPath()%>/jsonList.do?Type=NonDisabledUser&Columns=FullName,EMailAddress';
    }

    dojo.addOnLoad(function()
    {
        init();
        dojo.connect(window, "onresize", resizeGrid);
    });

</script>
<title><bean:message key="cp.commumication.message.label.sendMessage" /></title>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

    <div dojoType="dijit.layout.ContentPane" region="center" widgetId="selectClient" style="overflow:auto">
        <div id="userGrid" style="height:100%;width:100%;overflow:hidden;"></div>
    </div>

    <div dojoType="dijit.layout.ContentPane" region="bottom" >
		<div class="actionButtons">
			<cp:CPButton buttonType="button" onclick="returnUserNames()">
				<bean:message key="cp.commumication.message.label.ok"/>
			</cp:CPButton>
			<cp:CPButton buttonType="button" onclick="self.close()">
				<bean:message key="cp.commumication.message.label.close"/>
			</cp:CPButton>
		</div>
	</div>
</div>
</body>