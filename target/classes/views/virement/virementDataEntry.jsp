<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/virement/data_entry.js"></script>
</head>

<body>

<html:form action="editVirement.do" styleId="virementDataEntryForm" method="POST">

<div dojoType="dijit.layout.ContentPane" region="top" widgetId="vireTuo" >
	<H5 class="header5"><bean:message key="cp.virement.entry.title"/></H5>
	<jsp:include page="../system/errorsInc.jsp" />
</div>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

	<div dojoType="dijit.layout.ContentPane" region="center">

	<div id="mainTabContainer" widgetId="mainTabContainer"
		 dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false"
		 style="width:100%" tabPosition="top">


	<div id="tabpanel0" dojoType="dijit.layout.ContentPane"
	   	 title="<bean:message key="cp.virement.entry.tab0.label"/>"
		 selected="true" style="display:none">
	  <table>
		  <tr>
			  <td align="left" nowrap valign="top"><bean:message key="cp.virement.entry.tab0.reason"/></td>
			  <td align="left" nowrap>
				  <nested:textarea property="data.reason" rows="5" cols="60"/>
			  </td>
		  </tr>
		  <tr>
			  <td align="left" nowrap><bean:message key="cp.virement.entry.tab0.reference"/></td>
			  <td align="left" nowrap>
				  <nested:text property="data.reference" size="60"/>
			  </td>
		  </tr>
	  </table>
	</div>
	<div id="tabpanel1" dojoType="dijit.layout.ContentPane"
	   title="<bean:message key="cp.virement.entry.tab1.label"/>" selected="true" style="display:none">
	  <table class="tabtable">
		  <tr>
			  <td align="left" nowrap><bean:message key="cp.virement.entry.tab1.model"/></td>
			  <td align="left" nowrap>
				  <nested:hidden property="data.modelId" styleId="data.modelId"/>
				  <nested:text readonly="true" property="data.modelVisId" styleId="data.modelVisId"
							   size="40"/>
			  </td>
			  <td>
			  <% String pickBudgetCycle = "javascript:pickBudgetCycle('"+request.getContextPath()+"')"; %>
			  <cp:CPButton onclick="<%=pickBudgetCycle%>" property="modelIdButton">
				  Change
			  </cp:CPButton>
			  </td>
		  </tr>
		  <tr>
			  <td align="left" nowrap><bean:message key="cp.virement.entry.tab1.finance_cube"/></td>
			  <td align="left" nowrap>
				  <nested:hidden property="data.financeCubeId" styleId="data.financeCubeId"/>
				  <nested:text property="data.financeCubeVisId" styleId="data.financeCubeVisId"
							   readonly="true" size="40"/>
			  </td>
			  <td>
				  <cp:CPButton onclick="<%=pickBudgetCycle%>" property="finaceCubeIdButton">
					  Change
				  </cp:CPButton>
			  </td>
		  </tr>
		  <tr>
			  <td align="left" nowrap><bean:message key="cp.virement.entry.tab1.budget_cycle"/></td>
			  <td align="left" nowrap>
				  <nested:hidden property="data.budgetCycleId" styleId="data.budgetCycleId"/>
				  <nested:text property="data.budgetCycleVisId" styleId="data.budgetCycleVisId"
							   readonly="true" size="40"/>
			  </td>
			  <td>
				  <cp:CPButton onclick="<%=pickBudgetCycle%>" property="budgetCycleIdButton">
					  Change
				  </cp:CPButton>
			  </td>
		  </tr>
	  </table>
	</div>
	<div id="tabpanel2" dojoType="dijit.layout.ContentPane"
		 title="<bean:message key="cp.virement.entry.tab2.label"/>" style="display:none">
	<div style="margin:4px;font-weight:bolder;font-size:larger;"><bean:message key="cp.virement.entry.tab2.title"/></div>
	<div align="right" width="100%">
			  <% String addGroupJS = "javascript:addVirementGroup('"+request.getContextPath()+"')"; %>
			  <cp:CPButton onclick="<%=addGroupJS%>"
						   property="addVirementGroupIdButton">Add Group</cp:CPButton>
	</div>
	<div id="tab2-scoller" style="height:665.2px;overflow-y:auto;overflow-x:auto;border:1px;margin:1px;" >

	<table class="tabgroupcell" cellspacing="0" width="98%">
	<tr>
	<td valign="top">
	<table class="tablepanel">
	<tr>
	<td>
	<table cellspacing="0" width="100%">
	<tr>
	<td valign="top" align="left">
	<nested:notEqual value="0" name="virementDataEntryForm" property="data.modelId">
	<!-- Write out the data types picker so server does not have to re-populate them each time -->
	<nested:iterate id="dataTypes" property="data.dataTypes"
				  type="com.cedar.cp.utc.struts.virement.DataTypeDTO"
					indexId="dataTypeNo">
	  <nested:hidden property="key"/>
	  <nested:hidden property="narrative"/>
	</nested:iterate>

	  <table cellspacing="2" width="100%">
		  <!-- Orig location of herader tr -->

		  <!-- If there are no groups yet output a summary line with addLine, addBlankLine buttons -->
		  <nested:equal property="data.groups.empty" value="true">
			  <tr>
				  <td colspan="5" align="left">
					  <div>

						  <cp:CPButton onclick="javascript:addVirementBlankLine(-1)"
									   property="addVirementBlankLineIdButton">Add Blank Line</cp:CPButton>

						  <cp:CPButton onclick="<%=addGroupJS%>"
									   property="addVirementGroupIdButton">Add Line With Cell Picker</cp:CPButton>

					  </div>
				  </td>
			  </tr>
		  </nested:equal>

		  <nested:iterate id="groups" property="data.groups"
						  type="com.cedar.cp.utc.struts.virement.VirementGroupDTO"
						  indexId="groupNo">

			  <tr>
				  <th class="sth" align="left"><nested:hidden property="key"/>
					  Group <%=String.valueOf(groupNo+1)%></th>
				  <bean:define id="numDimsPlusTwo" name="virementDataEntryForm" property="data.numDimsPlusTwo"/>
				  <logic:iterate id="header" name="virementDataEntryForm"
								 property="data.dimensionHeaders"
								 type="com.cedar.cp.api.dimension.DimensionRef">
					  <th class="sth" align="left">
						  <bean:write name="header" property="narrative"/>
					  </th>
				  </logic:iterate>
				  <th class="sth" align="center">Spread</th>
				  <th class="sth" align="left"><bean:message key="cp.virement.entry.tab2.notes_header"/></th>
				  <th class="sth" align="right"><bean:message key="cp.virement.entry.tab2.data_type_header"/></th>
				  <th class="sth" align="right"><bean:message key="cp.virement.entry.tab2.transfer_header"/></th>
				  <th class="sth" align="center"><bean:message key="cp.virement.entry.tab2.action_header"/></th>
			  </tr>

			  <nested:iterate id="lines" property="lines"
							  type="com.cedar.cp.utc.struts.virement.VirementLineDTO"
							  indexId="lineNo">

				  <tr>
					  <td nowrap class="information">
						  <nested:hidden property="allocationThreshold"/>
						  <nested:hidden property="key"/>
						  <nested:hidden property="spreadProfileId"/>
						  <nested:hidden property="spreadProfileVisId"/>
						  <nested:hidden property="to"/>
						  <a href="javascript:toggleVirementLine(<%=groupNo%>,<%=lineNo%>)" id="to_<%=groupNo%>_<%=lineNo%>">
							  <nested:equal property="fromTo" value="To"><bean:message key="cp.virement.to"/></nested:equal>
							  <nested:equal property="fromTo" value="From"><bean:message key="cp.virement.from"/></nested:equal>
						  </a>
					  </td>
					  <nested:iterate property="cells" type="com.cedar.cp.utc.picker.ElementDTO">
						  <td nowrap title="<nested:write property="description" />">
							  <nested:text size="12" property="identifier"
										   onfocus="setCurrentField(this);"
										   onkeydown="return inputKeyFilter(this);"/>
							  <nested:hidden property="id"/>
							  <nested:hidden property="structureId"/>
							  <nested:hidden property="description"/>
						  </td>
					  </nested:iterate>
					  <td align="center">
						  <nested:hidden property="summaryLine"/>
						  <nested:equal value="true" property="summaryLine">
							  <%
								  String showSpreadPage = "javascript:showSpreadPage("+groupNo+","+lineNo+")";
							  %>
							  <cp:CPButton buttonType="button" onclick="<%=showSpreadPage%>">
								  <nested:write property="spreadProfileVisId"/>
							  </cp:CPButton>
							  <nested:iterate property="spreadProfile"
											  type="com.cedar.cp.utc.struts.virement.VirementLineSpreadDTO">
								  <nested:hidden property="key"/>
								  <nested:hidden property="structureElementKey"/>
								  <nested:hidden property="structureElementVisId"/>
								  <nested:hidden property="index"/>
								  <nested:hidden property="held"/>
								  <nested:hidden property="weighting"/>
								  <nested:hidden property="transferValue"/>
							  </nested:iterate>
						  </nested:equal>
					  </td>
					  <logic:equal name="lineNo" value="0">
						  <td nowrap rowspan="<nested:write property="../numRows"/>">
							  <nested:textarea property="../notes"
											   rows="<%=String.valueOf(groups.getNumRows())%>" cols="30"/>
						  </td>
					  </logic:equal>
					  <td nowrap align="right">

						  <nested:select property="dataTypeId" onchange="javascript:selectedProfile()">
							  <nested:optionsCollection property="../../dataTypes"
											  label="narrative"
											  value="key"/>
						  </nested:select>

					  </td>
					  <td nowrap align="right">
						  <nested:text size="12" styleClass="inputnumber"
									   onfocus="setCurrentField(this);"
									   onchange="recalcForm(); return true;"
									   onkeydown="return inputKeyFilter(this);"
									   property="transferValue"/>
					  </td>
					  <td nowrap>
						  <a href="javascript:deleteVirementLine(<%=groupNo%>,<%=lineNo%>)">
							  Delete
						  </a>
					  </td>
				  </tr>
			  </nested:iterate>
			  <tr>
				  <td colspan="<bean:write name="numDimsPlusTwo"/>">
					  <div>
						  <%
							  String addBlankLine = "javascript:addVirementBlankLine("+groupNo+")";
							  String addLine = "javascript:addVirementLine('"+request.getContextPath()+"',"+groupNo+")";
						  %>
						  <cp:CPButton buttonType="button" onclick="<%=addBlankLine%>">Add Blank Line</cp:CPButton>
						  <cp:CPButton buttonType="button" onclick="<%=addLine%>">Add Line With Cell Picker</cp:CPButton>
					  </div>
				  </td>
				  <td/>
				  <td class="information" align="right">
					  Remainder:
				  </td>
				  <td class="information" align="right">
					  <nested:text property="remainder" size="12" styleClass="inputnumber"
								   readonly="true"/>
				  </td>
			  </tr>
			  <tr>
				  <td>&nbsp;</td>
			  </tr>
		  </nested:iterate>
	  </table>

	</nested:notEqual>
	<nested:equal value="0" name="virementDataEntryForm" property="data.modelId">
	  <p>Please select a model, finance cube and budget cycle</p>
	</nested:equal>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	</div>
	</div>
	<div id="tabpanel3" dojoType="dijit.layout.ContentPane"
	   title="<bean:message key="cp.virement.entry.tab3.label"/>" style="display:none">
	  <p><bean:message key="cp.virement.entry.tab3.title"/></p>
	  <table width="100%">
		  <tr>
			  <td valign="top">
				  <table>
					  <tr>
						  <td>
							  <cp:CPButton buttonType="button" value="Save" onclick="javascript:saveRequest()"/>
						  </td>
						  <td>Save the budget transfer</td>
					  </tr>
					  <tr>
						  <td>
							  <cp:CPButton buttonType="button" value="Submit" onclick="javascript:submitRequest()"/>
						  </td>
						  <td>Submit the budget transfer</td>
					 </tr>
					 <tr>
						  <td>
							  <cp:CPButton buttonType="button" value="Quit" onclick="javascript:quit()"/>
						  </td>
						  <td>Quit and lose any changes made</td>
					 </tr>
				  </table>
			  </td>
		  </tr>
	  </table>

	</div>

<nested:notEmpty property="data.authPoints">
	<div id="tabpanel4" dojoType="dijit.layout.ContentPane"
			   title="<bean:message key="cp.virement.entry.tab4.label"/>" style="display:none">
		  <p>Previous authorisation details</p>
		  <table width="100%">
			  <tr>
				  <th>Available Authorisers</th>
				  <th>Budget Location</th>
				  <th>Transfer Rows</th>
				  <th>Actioned By</th>
				  <th>Notes</th>
				  <th>Status</th>
			  </tr>
			  <nested:iterate property="data.authPoints" id="authPoint" indexId="authPointIndex"
							  type="com.cedar.cp.utc.struts.virement.VirementAuthPointDTO">
				  <nested:hidden property="keyText"/>
				  <tr>
					  <td>
						  <div>
						  <nested:iterate property="availableAuthorisers" id="aAuth" indexId="aAuthIdx">
								  <nested:write property="narrative"/><br>
								  <nested:hidden property="key"/>
								  <nested:hidden property="narrative"/>
						  </nested:iterate>
						  </div>
					  </td>
					  <td>
						  <nested:nest property="budgetLocation">
							  <nested:write property="identifier"/>
							  <nested:hidden property="key"/>
							  <nested:hidden property="identifier"/>
						  </nested:nest>
					  </td>
					  <td>
						  <div>
							  <nested:iterate property="lines" id="line" indexId="lineIdx">
								  <nested:write property="identifier"/><br>
								  <nested:hidden property="key"/>
								  <nested:hidden property="identifier"/>
							  </nested:iterate>
						  </div>
					  </td>
					  <td>
						  <nested:nest property="authUser">
							  <nested:write property="narrative"/>
							  <nested:hidden property="key"/>
							  <nested:hidden property="narrative"/>
						  </nested:nest>
					  </td>
					  <td>
						  <nested:textarea property="notes" cols="40" rows="3" readonly="true"/>
					  </td>
					  <td>
						  <nested:write property="statusText"/>
					  </td>
					  <nested:hidden property="userCanAuth"/>
					  <nested:hidden property="status"/>
				  </tr>
			  </nested:iterate>
		  </table>
	  </div>
</nested:notEmpty>
</div>

	</div>
<input type="submit" value="Validate" style="display:none;"/>

<nested:hidden property="currentTab" styleId="currentTab"/>
<nested:hidden property="currentGroup" styleId="currentGroup"/>
<nested:hidden property="currentLine" styleId="currentLine"/>
<nested:hidden property="currentField" styleId="currentField"/>
<nested:hidden property="userAction" styleId="userAction"/>
<nested:hidden property="data.key"/>
<nested:hidden property="hasData" styleId="hasData" value="true"/>

</div>

</html:form>

</body>

