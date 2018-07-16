<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
<head>
</head>

<body>
<H5 class="header5"><bean:message key="cp.support.title" /></H5>

<hr/>
<H5 class="header5"><bean:message key="cp.support.serverlog.title" /></H5>
<html:form action="sendServerLog" >

<logic:notEmpty name="supportForm" property="actionError" >
<ul class="error">
<logic:iterate name="supportForm" property="actionError" id="errors" type="java.lang.String" >
    <li><bean:write name="errors" /></li>
</logic:iterate>
</ul>
<br/>
</logic:notEmpty>


<table width="70%">
<tr>
    <td width="20%">
        <bean:message key="cp.support.to" />
    </td>
    <td>
        <html:text property="cedarAddess" size="30" />
    </td>
</tr>
<tr>
    <td>
        <bean:message key="cp.support.from" />
    </td>
    <td>
        <html:text property="fromAddress" size="30" />
    </td>
</tr>
<tr>
    <td>
        <bean:message key="cp.support.additional" />
    </td>
    <td >
        <html:textarea property="message" cols="60" rows="6"  />
    </td>
</tr>
<tr>
    <td>
        <bean:message key="cp.support.file" />
    </td>
    <td>
        <html:text property="logFile" size="30" />
    </td>
</tr>
<tr>
    <td colspan="2" align="right" >
        <cp:CPButton buttonType="submit" >
            <bean:message key="cp.support.sendlog" />
        </cp:CPButton>
    </td>
</tr>
</table>
<html:hidden property="siteId" />
</html:form>

<br/>
<div dojoType="dijit.TitlePane" title="Available Log Files" open="false" >

<table cellpadding="10" cellspacing="6">
	<tr>
		<th>File Name</th>
		<th>Size</th>
		<th>Last modified</th>
	</tr>
	<logic:iterate id="file" name="supportForm" property="logFiles">
	<tr>
		<td>
			<html:link action="downLoadLog" name="file" property="param" target="_logFile">
				<bean:write name="file" property="name"/>
			</html:link>
			&nbsp;
		</td>
		<td>
			<bean:write name="file" property="size"/>&nbsp;
		</td>
		<td>
			<bean:write name="file" property="date"/>
		</td>
	</tr>
	</logic:iterate>
</table>
</div>

<hr>

<H5 class="header5"><bean:message key="cp.support.debuglevel.title" /></H5>

<html:form action="changeDebugLevel">
<table width="70%">
<tr>
    <td width="20%"><bean:message key="cp.support.debuglevel.curent"/></td>
    <td>
        <html:select property="logLevel" size="1"  >
            <html:options property="levelIntegers" labelProperty="levelStrings" />
        </html:select>
    </td>
    <td>&nbsp;</td>
    <td align="right">
        <cp:CPButton buttonType="submit" ><bean:message key="cp.support.debug.level.change"/></cp:CPButton>
    </td>
</tr>
</table>
</html:form>

<hr>

<H5 class="header5"><bean:message key="cp.admin.clearcache" /></H5>

<table width="70%">
<tr>
    <td ><bean:message key="cp.admin.clearcache.description"/></td>
    <td align="right">
        <cp:CPButton buttonType="button" onclick="clearCache()" >Clear</cp:CPButton>
    </td>
</tr>
</table>

<hr>
<H5 class="header5">Download speed test</H5>
<table width="70%">
<tr>
	<td>
		Size of file to transfer (mb)
	</td>
	<td>
		<input dojoType="dijit.form.NumberSpinner"
			style="font-size:150%"
			value="2" largeDelta="2" constraints="{min:1,max:20}" name="integerspinner" id="integerspinner">
	</td>
</tr>
<tr>
    <td width="20%"><input type="button" name="startTimer" id="startTimer" value="Start Test" onclick="startDownloadTest()"/></td>
	<td>Time to Download <input id="timeTaken" name="timeTaken" value="0:0.0" size="4" disabled="true"/> Min : Sec . Millis</td>
</tr>
</table>

<hr>
<H5 class="header5">Run traceroute on server to your machine</H5>
<table width="70%">
<tr>
	<td width="20%" valign="top"><input type="button" name="startTrace" id="startTrace" value="Start Test" onclick="runTraceRoute()"/></td>
	<td>
		<div id="routeList" style="height:50px;overflow:auto;">N/A</div>
	</td>
</tr>
</table>

</body>