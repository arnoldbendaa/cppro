<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"%>

<head>
<!-- I think this is not used but I wont remove it just yet-->
<style type="text/css">
.sectionheader {
	color: #1D5EAA;
	font-weight: bold;
	font-size: 12px
}
</style>

<script type="text/javascript">
	function back()
	{
		history.back (-1);
	}
</script>
</head>

<body>

<logic:present name="reportDetailForm" property="reportDetails">
	<table width="98%">
		<tr>
			<td width="1%">&nbsp;</td>
			<td width="98%">

			<table width="100%">
				<tr>
					<td colspan="2" align="left">
					<H5 class="header5">Task Report</H5>
					</td>
				</tr>
				<tr>
					<td width="80px" align="right">Name :</td>
					<td><bean:write name="reportDetailForm" property="reportDetails.reportName" /></td>
				</tr>
				<tr>
					<td align="right">Type :</td>
					<td><bean:write name="reportDetailForm" property="reportDetails.reportTypeText" /></TD>
				</tr>
				<tr>
					<td align="right">Task Id :</td>
					<td><a href="taskDetails.do?taskId=<bean:write name="reportDetailForm" property="reportDetails.taskId"/>">
						<bean:write name="reportDetailForm" property="reportDetails.taskId" /></a></td>
				</tr>
				<tr>
					<td align="right">Created :</td>
					<td><bean:write name="reportDetailForm" property="reportDetails.createdDate" /></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<cp:CPButton buttonType="button" onclick="back()">
							<bean:message key="cp.task.detail.button.text" />
						</cp:CPButton>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
					<hr />
					</td>
				</tr>
			</table>
			</td>
			<td width="1%">&nbsp;</td>
		</tr>



		<logic:equal name="reportDetailForm"
			property="reportDetails.hasUpdates" value="true">
			<tr>
				<td width="1%">&nbsp;</td>

				<td width="98%"><logic:equal name="reportDetailForm"
					property="reportDetails.updatesApplied" value="true">
							The updates associated with this report <b>have</b> been applied to the database. To <b>undo</b> these updates click <a
						href='undoReportUpdates.do?reportId=<bean:write name="reportDetailForm" property="reportDetails.reportId"/>'>here</a>.
						</logic:equal> <logic:equal name="reportDetailForm"
					property="reportDetails.updatesApplied" value="false">
							The updates associated with this report have <b>not</b> been applied to the database. To <b>apply</b> these updates click <a
						href='doReportUpdates.do?reportId=<bean:write name="reportDetailForm" property="reportDetails.reportId"/>'>here</a>.
						</logic:equal></td>
				<td width="1%">&nbsp;</td>
			</tr>
			<tr>
				<td width="1%">&nbsp;</td>
				<td width="98%">
				<hr />
				</td>
				<td width="1%">&nbsp;</td>
			</tr>
		</logic:equal>

		<tr>
			<td width="1%">&nbsp;</td>
			<td width="98%"><bean:write name="reportDetailForm" property="reportDetails.reportText" filter="false" />
			</td>
			<td width="1%">&nbsp;</td>
		</tr>

		<tr>
			<td colspan="1" width="1%">&nbsp;</td>
			<td width="98%" align="right"><cp:CPButton buttonType="button"
				onclick="back()">
				<bean:message key="cp.task.detail.button.text" />
			</cp:CPButton></td>
			<td width="1%">&nbsp;</td>
		</tr>



	</table>
</logic:present>


</body>
