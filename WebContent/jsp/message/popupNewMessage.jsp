<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<script language="javascript" type="text/javascript">

dojo.require("dijit.Editor");

dojo.require("dijit.form.FilteringSelect");
dojo.require("dojo.data.ItemFileReadStore");


function nameSearch()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1';
    params = params + ',width=630,height=430,left=120,top=120';
	target = '<%=request.getContextPath()%>/communicationListUserSetup.do';
    window.open(target, '_blank', params);
}

function distributionListSearch()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=120,top=120';
	target = '<%=request.getContextPath()%>/communicationDistributionListSetup.do';
    window.open(target, '_blank', params);
}

    function appendValue(id, value)
    {
        currentValue = dojo.byId(id).value;
        if(currentValue != null && currentValue.length > 0)
            value = currentValue + ", " + value;

        dojo.byId(id).value = value;
    }


	function doSubmit()
	{
		dojo.byId("messageForm").submit();
	}

function editorChange(editorValue)
{
    dojo.byId("content").value = editorValue;
}

dojo.addOnUnload(function()
{
    dijit.byId("editor1").set("value", dojo.byId("content").value);
});

</script>
<title><bean:message key="cp.commumication.message.label.sendMessage" /></title>
</head>

<body>
<div dojoType="dijit.layout.BorderContainer" gutters="false">
	<div dojoType="dijit.layout.ContentPane" region="top">
		<jsp:include page="/jsp/system/errorsInc.jsp" />
	</div>
	<div dojoType="dijit.layout.ContentPane" region="center">
<html:form action="/communicationNew" enctype="multipart/form-data" method="post" styleId="messageForm">
<table class="groupCell" align="center">
	<tr>
		<td  align="right"><bean:message key="cp.commumication.message.label.user" /></td>
		<td>
			<html:textarea property="message.toUser" styleId="message.toUser" readonly="true" cols="50" rows="0"  ></html:textarea>
			<html:hidden property="message.toUser_VisID" styleId="message.toUser_VisID"  />
            <html:hidden property="message.toUserEmailAddress" styleId="message.toUserEmailAddress"  />
            <html:hidden property="message.originalMessageId"/>
            <a href="javascript:nameSearch()"><bean:message key="cp.commumication.message.label.search" /></a>
        </td>
	</tr>
    <tr>
        <td  align="right"><bean:message key="cp.commumication.message.label.distribution.label" /></td>
        <td>
            <html:text property="message.toDist" styleId="message.toDist" readonly="true" size="40"/>
			<html:hidden property="message.toDistType" styleId="message.toDistType"/>
            <html:link href="javascript:distributionListSearch()" titleKey="cp.commumication.message.label.distribution.desc"><bean:message key="cp.commumication.message.label.search" /></html:link>
        </td>
    </tr>
    <tr>
		<td  align="right"><bean:message key="cp.commumication.message.label.messageType"/></td>
		<td>
			<html:select property="message.messageType" styleClass="optionClass" size="1"  >
				<html:options property="messageType" labelProperty="messageTypeLabel" />
			</html:select>
		</td>
	</tr>
	<tr>
		<td  align="right"><bean:message key="cp.commumication.message.label.subject" /></td>
		<td>
			<html:text property="message.subject" size="80"  ></html:text>
		</td>
	</tr>
	<tr>
		<td  valign="top" align="right"><bean:message key="cp.commumication.message.label.message"/></td>
		<td>
			<div data-dojo-type="dijit.Editor" id="editor1" name="message.content" style="height:200px;background-color:#fefefe;" data-dojo-props="height:200,onChange:function(){editorChange(arguments[0])},
			plugins:['cut','copy','paste','|','bold','italic','underline','strikethrough','subscript','superscript','|', 'indent', 'outdent', 'justifyLeft', 'justifyCenter', 'justifyRight']">
			</div>
			<html:textarea styleId="content" property="message.content" cols="80" rows="12" style="display:none;" ></html:textarea>
		</td>
	</tr>
	<tr>
		<td  align="right"><bean:message key="cp.commumication.message.label.attach"/></td>
		<td colspan="3">
			<html:file property="message.attachment[0].attatch" size="50" ></html:file>
		</td>
	</tr>
</table>
</html:form>
	</div>

	<div dojoType="dijit.layout.ContentPane" region="bottom">
		<div class="actionButtons">
		<cp:CPButton onclick="doSubmit()"  ><bean:message key="cp.commumication.message.label.send" /></cp:CPButton>
		<cp:CPButton onclick="javascript:window.close()" ><bean:message key="cp.commumication.message.label.close" /></cp:CPButton>
		</div>
	</div>
</div>


</body>