<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<body>

<table width="100%" cellpadding="1" cellspacing="1">
    <tr>
        <th nowrap >Vis Id</th>
        <th nowrap >Description</th>
        <th nowrap >Model</th>
        <th nowrap >RA Selection</th>
        <th nowrap >RA Depth</th>
        <th nowrap >Template</th>
        <th nowrap >Column Map</th>
        <th nowrap >Actions</th>
    </tr>
    <logic:iterate id="dto" name="formReportDef" property="dtoList" type="com.cedar.cp.utc.struts.admin.report.reportdefinition.dto.ReportDefinitionDTO" indexId="index">
	<logic:notEmpty name="dto" property="RAVisId">
	<tr>
		<td id="sum_cellcalc_vis_id_<%=index.intValue()%>"><bean:write name="dto" property="visId"/></td>
		<td id="sum_cellcalc_description_<%=index.intValue()%>"><bean:write name="dto" property="description"/></td>
		<td><bean:write name="dto" property="modelVisId"/></td>
		<td>
			<bean:define id="modelId" name="dto" property="modelId"/>
			<bean:define id="ccId" name="dto" property="RAStructureElementId"/>
			<bean:define id="ccVisId" name="dto" property="RAVisId"/>
			<input type="hidden" id="sum_cellcalc_modelId_<%=index.intValue()%>" value="<%=modelId%>"/>
			<input type="hidden" id="sum_cellcalc_ccId_<%=index.intValue()%>" value="<%=ccId%>"/>
			<input type="hidden" id="sum_cellcalc_ccVisId_<%=index.intValue()%>" value="<%=ccVisId%>"/>
			<bean:write name="dto" property="RAParam"/>
		</td>
		<td id="sum_cellcalc_depth_<%=index.intValue()%>"><bean:write name="dto" property="reportDepth"/></td>
		<td><bean:write name="dto" property="templateVisId"/></td>
		<td><bean:write name="dto" property="columnMapping"/></td>
		<td align="center">
			<%
				String action = "doSubmitOptions('sum_cellcalc'," + index + ")";
			%>
			<cp:CPButton onclick="<%=action%>">Submit</cp:CPButton>
		</td>
	</tr>
	</logic:notEmpty>
	</logic:iterate>
</table>

</body>
</html>