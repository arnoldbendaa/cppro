<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title>Cell Note</title>
</head>

<body >

<html:form action="/cellNote" >
<jsp:include page="/jsp/system/errorsInc.jsp" />
<table  width="96%" align="center" class="groupCell" >
	<tr>
		<th colspan="2">Cell Note</th>
	</tr>
	<tr>
		<td width="30%" nowrap>New Note :</td>
		<td width="70%">
			<html:textarea property="newNote" cols="80" rows="4"  ></html:textarea>
		</td>
	</tr>
	<tr>
		<td width="30%" nowrap>Old Notes :	</td>
		<td width="70%">
			<html:textarea property="oldNotes" cols="80"readonly="true" rows="8"  ></html:textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right">
			<cp:CPButton buttonType="submit"  >update</cp:CPButton>
			&nbsp;
			<cp:CPButton onclick="javascript:window.close()" ><bean:message key="cp.commumication.message.label.close" /></cp:CPButton>
		</td>
	</tr>
</table>

<html:hidden property="financeCubeId" />
<html:hidden property="cellNoteId" />
<html:hidden property="modelId" />
</html:form>
</body>