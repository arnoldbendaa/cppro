<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
<script language="JavaScript1.2" type="text/javascript">
function showMassUpdatePanel()
{
	dijit.byId('mainTabContainer').selectChild(dijit.byId('1'));
}

dojo.addOnLoad(function()
{
	<logic:notEqual name="massUpdateForm" property="taskId" value="0" >
		<bean:define id="submittedTaskId" name="massUpdateForm" property="taskId" />
		alert('<bean:message key="cp.massupdate.submitted" arg0="<%=String.valueOf(submittedTaskId)%>" />');
		showMassUpdatePanel();
	</logic:notEqual>

    //since errors cause us to come back here
    var stringValue = dojo.byId("financeCubeId").value;
    if(stringValue != "0")
    {
        dojo.byId('finaceCubeIdButton').style.display='inline';
        dojo.byId('tab2_display').style.display='inline';
        dojo.byId('tab2_message').style.display='none';
        dojo.byId('tab4_display').style.display='inline';
        dojo.byId('tab4_message').style.display='none';
        dojo.byId('dataTypeIdButton').style.display='inline';
		dojo.byId('calIdButton').style.display='inline';
	}

    <logic:equal name="massUpdateForm" property="selectedCell" value="true" >
        dojo.byId('addChangeCells').style.display='none';
        dojo.byId('valueTypeSelection').style.display='inline';
        dojo.byId('modelIdButton').style.display='none';
        dojo.byId('finaceCubeIdButton').style.display='none';
    </logic:equal>

    displayChangeOptions();
});

function modelSearch()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=120,top=120';
	target = '<%=request.getContextPath()%>/popupSelector.do';
	window.open(target, '_blank', params);
}

function financeCubeSearch()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=120,top=120';
    var model_obj = dojo.byId("modelId");
	target = '<%=request.getContextPath()%>/popupSelector.do?type=1&modelId=' + model_obj.value;
	window.open(target, '_blank', params);
}

function dataTypeSearch()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=120,top=120';
    var fin_obj = dojo.byId("financeCubeId");
	target = '<%=request.getContextPath()%>/popupSelector.do?type=5&financecubeId=' + fin_obj.value;
    window.open(target, '_blank', params);
}

function calSearch()
{
	params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=120,top=120';
	var model_obj = dojo.byId("modelId");
	var fin_obj = dojo.byId("financeCubeId");
	var cal_seq_obj = dojo.byId("calSeqId");
	target = '<%=request.getContextPath()%>/popupCostCentreSelector.do?modelId='+ model_obj.value +'&financecubeId=' + fin_obj.value + '&costCentreSeqId=' + cal_seq_obj.value;
	window.open(target, '_blank', params);
}

function submitMassUpdate()
{
    var confirmValue;
    confirmValue = confirm('Are you sure you want to submit the update');

    if(confirmValue)
    {
        dojo.byId('passedAction').value = "massUpdate"
        dojo.byId('massUpdateForm').submit();
    }
}

function addHoldCells()
{
    dojo.byId('requiredDataType').value = '';
    dojo.byId('dataTypeMultiSelect').value = 'false';
    dojo.byId('writeableDataTypes').value = 'false';
    dimensionSelect();
}

function addChangeCells()
{
    dojo.byId('requiredDataType').value = '0,3';
    dojo.byId('dataTypeMultiSelect').value = 'true';
    dojo.byId('writeableDataTypes').value = 'false';
    dimensionSelect();
}

function dimensionSelect()
{
    var form = dojo.byId('massUpdateForm');
    form.action = "<%=request.getContextPath()%>/dimensionSelect.do";
    form.submit();
}

function displayChangeOptions()
{
	if(dojo.byId('changeType') != null && dojo.byId('changeType').value != null && dojo.byId('changeType').value == "Value")
    {
        dojo.byId('valueOptions').style.display = "inline";
        dojo.byId('percentageOptions').style.display = "none";
    }
    else
    {
        dojo.byId('valueOptions').style.display = "none";
        dojo.byId('percentageOptions').style.display = "inline";
    }
}

function deleteChangeLine(id)
{
    dojo.byId('deleteId').value = id;
    dojo.byId('passedAction').value = "deleteChange"
    var form = dojo.byId('massUpdateForm');
    form.action = "<%=request.getContextPath()%>/massUpdate.do";
    form.submit();
}

function deleteHoldLine(id)
{
    dojo.byId('deleteId').value = id;
    dojo.byId('passedAction').value = "deleteHold"
    var form = dojo.byId('massUpdateForm');
    form.action = "<%=request.getContextPath()%>/massUpdate.do";
    form.submit();
}

function calcValue(id)
{
    var currentValue = 0;
    currentValue = dojo.byId('currentValue').value / 1;
    if(id == 1) //to
    {
        if(dojo.byId('changeTo').value != "" )
        {
            dojo.byId('changeBy').value = "";
            dojo.byId('percent').value = "";
            dojo.byId('changeToDiv').innerHTML = formatCurrency(dojo.byId('changeTo').value);
            dojo.byId('changeToDiv').style.display = "inline";
            dojo.byId('changeByDiv').style.display = "none";
            dojo.byId('percentDiv').style.display = "none";
        }
    }
    else if(id == 2) //by
    {
        if(dojo.byId('changeBy').value != "")
        {
            var changeBy = 0;
            changeBy = dojo.byId('changeBy').value / 1;
            dojo.byId('changeTo').value = "";
            dojo.byId('percent').value = "";
            dojo.byId('changeByDiv').innerHTML = formatCurrency(changeBy + currentValue);
            dojo.byId('changeToDiv').style.display = "none";
            dojo.byId('changeByDiv').style.display = "inline";
            dojo.byId('percentDiv').style.display = "none";
        }
    }
    else if(id == 3) //percent
    {
        if(dojo.byId('percent').value != "")
        {
            var percent = 0;
            var percentTotal = 0;
            percent = dojo.byId('percent').value / 100;
            percentTotal = currentValue + (currentValue * percent);
            dojo.byId('changeBy').value = "";
            dojo.byId('changeTo').value = "";
            dojo.byId('percentDiv').innerHTML = formatCurrency(percentTotal);
            dojo.byId('changeToDiv').style.display = "none";
            dojo.byId('changeByDiv').style.display = "none";
            dojo.byId('percentDiv').style.display = "inline";
        }
    }
}

function formatCurrency(num)
{
    return new NumberFormat(num).toFormatted();
}

function setMassUpdateCal(selectedId, displayValue, popupWindow)
{
	var fin_obj = dojo.byId("financeCubeId");
	var src_cal_obj = dojo.byId("srcCalId");
	dojo.xhrGet({
		// The page that parses the POST request
		url: '<%=request.getContextPath()%>/ajaxValidateMassUpdateCal.do',
		// Loads this function if everything went ok
		load: function (data)
		{
			var evaldObj = dojo.fromJson(data);
			if(evaldObj.valid)
			{
				dojo.byId('calVisId').value = displayValue;
				dojo.byId('calId').value = selectedId;
			}
			else
			{
				alert(evaldObj.reason);
			}
			if(popupWindow != null)
				popupWindow.close();
		},
		// Call this function if an error happened
		error: function (data)
		{
			alert("An error occurred. " + data.message);
			if(popupWindow != null)
				popupWindow.close();
		},
		content: { action: 'validateCal', cubeId: fin_obj.value, srcCalElem: src_cal_obj.value, targetCalElem: selectedId },
		mimetype: "text/json",
		preventCache: true
	});
}

function setModel(name, id)
{
	dojo.byId("modelId").value  = name;
	dojo.byId("modelVisId").value = id;

	dojo.byId('finaceCubeIdButton').style.display = 'inline';

	//reset fincube and data type values
	dojo.byId('financeCubeId').value = 0;
	dojo.byId('financeCubeVisId').value = "";

	dojo.byId('dataTypeId').value = 0;
	dojo.byId('dataTypeVisId').value = "";
	dojo.byId('dataTypeIdButton').style.display = 'none';
}

function setFinanceCube(name, id)
{
	dojo.byId("financeCubeId").value = name;
	dojo.byId("financeCubeVisId").value = id;

	dojo.byId('dataTypeIdButton').display = 'inline';
	dojo.byId('tab2_display').style.display = 'inline';
	dojo.byId('tab2_message').style.display = 'none';
	dojo.byId('tab4_display').style.display = 'inline';
	dojo.byId('tab4_message').style.display = 'none';

	//reset datatype
	dojo.byId('dataTypeId').value = 0;
	dojo.byId('dataTypeVisId').value = "";
}

function setDataType(name, id)
{
	dojo.byId('dataTypeId').value = name;
	dojo.byId('dataTypeVisId').value = id;

	dojo.byId('finaceCubeIdButton').style.display = 'inline';
}
</script>

<style type="text/css">
	html, body, form { height: 100%; margin: 0; }
</style>
</head>

<body >

<html:form action="/submitMassUpdate" styleId="massUpdateForm" >
<div dojoType="dijit.layout.ContentPane" region="top" widgetId="massTop" >
	<h5 class="header5"><bean:message key="cp.massupdate.title" /></H5>
	<jsp:include page="../system/errorsInc.jsp" />
</div>
<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

	<div region="center" id="mainTabContainer" widgetId="mainTabContainer" dojoType="dijit.layout.TabContainer" persist="true" tabStrip="false" style="width: 100%" tabPosition="top">
		<div id="1" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.massupdate.tab1.title" />' selected="true" >
<p><bean:message key="cp.massupdate.tab1.header" /></p>
<table>
    <tr>
        <td>
            <bean:message key="cp.massupdate.tab1.model" />
        </td>
        <td>
            <html:text property="massDTO.modelVisId" readonly="true" size="60"  styleId="modelVisId" />
            <html:hidden property="massDTO.modelId"  styleId="modelId" />
            <cp:CPButton onclick="javascript:modelSearch()" property="modelIdButton" >
                <bean:message key="cp.commumication.message.label.search" />
            </cp:CPButton>
        </td>
    </tr>
    <tr>
        <td>
            <bean:message key="cp.massupdate.tab1.financecube" />
        </td>
        <td>
            <html:text property="massDTO.financeCubeVisId" readonly="true" size="60" styleId="financeCubeVisId" />
            <html:hidden  property="massDTO.financeCubeId" styleId="financeCubeId" />
            <cp:CPButton onclick="javascript:financeCubeSearch()" property="finaceCubeIdButton" style="display:none" >
                <bean:message key="cp.commumication.message.label.search" />
            </cp:CPButton>
        </td>
    </tr>
    <tr>
        <td valign="top" >
            <bean:message key="cp.massupdate.tab1.reason" />
        </td>
        <td>
            <html:textarea property="massDTO.reason" rows="5" cols="60"   />
        </td>
    </tr>
    <tr>
        <td>
            <bean:message key="cp.massupdate.tab1.reference" />
        </td>
        <td>
            <html:text property="massDTO.reference" size="60" />
        </td>
    </tr>
</table>
		</div>
            <!-- tab 2 -->
		<div id="2" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.massupdate.tab2.title" />'>
<p><bean:message key="cp.massupdate.tab2.header"/></p>
<br/>
<div style="display:none" id="valueTypeSelection">
    <bean:message key="cp.massupdate.tab2.changeType" />&nbsp;
    <html:select property="massDTO.changeType" onchange="displayChangeOptions()" styleId="changeType"  >
        <html:options property="changeOptions" />
    </html:select>

    <br/>&nbsp;<br/>

    <div style="display:inline" id="valueOptions" >
        <table>
            <tr>
                <td colspan="3" ><bean:message key="cp.massupdate.tab2.current"/></td>
                <td>&nbsp;<bean:write name="massUpdateForm" property="massDTO.currentValueForDisplay"/></td>
            </tr>
            <tr>
                <td><bean:message key="cp.massupdate.tab2.changeto" /></td>
                <td><html:text property="massDTO.changeTo" onblur="javascript:calcValue(1)" styleId="changeTo" /></td>
                <td><bean:message key="cp.massupdate.tab2.change" /></td>
                <td>&nbsp;<div id='changeToDiv' style="display:none;"></div></td>
            </tr>
            <tr>
                <td align="right"><bean:message key="cp.massupdate.tab2.changeby" /></td>
                <td><html:text property="massDTO.changeBy" onblur="javascript:calcValue(2)" styleId="changeBy" /></td>
                <td><bean:message key="cp.massupdate.tab2.giving" /></td>
                <td>&nbsp;<div id='changeByDiv' style="display:none;"></div></td>
            </tr>
        </table>
    </div>

    <div style="display:none" id="percentageOptions" >
        <table>
            <tr>
                <td colspan="3" ><bean:message key="cp.massupdate.tab2.current"/></td>
                <td><bean:write name="massUpdateForm" property="massDTO.currentValueForDisplay"/></td>
            </tr>
            <tr>
                <td><bean:message key="cp.massupdate.tab2.changeby2" /></td>
                <td><html:text property="massDTO.changePercent" onblur="javascript:calcValue(3)" styleId="percent" /></td>
                <td><bean:message key="cp.massupdate.tab2.percent" /></td>
                <td>&nbsp;<div id='percentDiv' style="display:none;"></div></td>
            </tr>
        </table>
    </div>
</div>

<div style="display:none" id="tab2_display" >
<p>
<a href="javascript:addChangeCells()" style="display:inline" id="addChangeCells" ><bean:message key="cp.massupdate.tab2.add" /></a>
</p>
<logic:notEmpty name="massUpdateForm" property="massDTO.changeCells">
<table width="100%" cellspacing="0">
    <tr>
        <logic:iterate id="header" name="massUpdateForm" property="massDTO.dimensionHeader" type="String" >
            <th nowrap align="left" ><bean:write name="header" /></th>
        </logic:iterate>
        <th nowrap align="left" ><bean:message key="cp.massupdate.tab2.table.header5" /></th>
        <th nowrap align="left" ><bean:message key="cp.massupdate.tab2.table.header6" /></th>
    </tr>
    <!-- lines -->
    <logic:iterate id="changedCell" name="massUpdateForm" property="massDTO.changeCells" indexId="rowId" >
      <tr>
        <logic:iterate name="changedCell" id="element" indexId="coll" type="com.cedar.cp.utc.picker.ElementDTO" >
            <td nowrap title="<bean:write name="element" property="description" />" >
                <bean:write name="element" property="identifier" />
            </td>
        </logic:iterate>
        <td nowrap><a href="javascript:deleteChangeLine(<bean:write name="rowId"/>)"><bean:message key="cp.massupdate.tab2.delete" /></a></td>
      </tr>
    </logic:iterate>
</table>
</logic:notEmpty>
</div>
<div style="display:inline" id="tab2_message" >
    <h1 class="header1"><bean:message key="cp.massupdate.tab2.message" /></h1>
</div>
        </div>
            <!-- tab 3 -->
		<div id="3" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.massupdate.tab3.title" />'>
<p><bean:message key="cp.massupdate.tab3.header" /></p>
<table>
	<tr>
		<td>
			<bean:message key="cp.massupdate.tab3.cal" />
		</td>
		<td>
            <html:text property="massDTO.calVisId" readonly="true" size="20"  styleId="calVisId" />
			<html:hidden property="massDTO.calSeqId" styleId="calSeqId" />
			<html:hidden property="massDTO.calId"  styleId="calId" />
			<html:hidden property="massDTO.srcCalId"  styleId="srcCalId" />
			<cp:CPButton onclick="javascript:calSearch()" property="calIdButton" style="display:none"  >
                <bean:message key="cp.commumication.message.label.search" />
            </cp:CPButton>
		</td>
	</tr>
	<tr>
        <td>
            <bean:message key="cp.massupdate.tab3.datatype" />
        </td>
        <td>
            <html:text property="massDTO.dataTypeVisId" readonly="true" size="20"  styleId="dataTypeVisId" />
            <html:hidden property="massDTO.dataTypeId"  styleId="dataTypeId" />
            <cp:CPButton onclick="javascript:dataTypeSearch()" property="dataTypeIdButton" style="display:none"  >
                <bean:message key="cp.commumication.message.label.search" />
            </cp:CPButton>
        </td>
    </tr>
    <tr>
        <td>
            <bean:message key="cp.massupdate.tab3.round" />
        </td>
        <td>
            <html:select property="massDTO.roundUnits" >
                <html:options property="unitSelection"  labelProperty="unitSelectionLabel"/>
            </html:select>
        </td>
    </tr>
</table>
         </div>
            <!-- tab 4-->
		<div id="4" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.massupdate.tab4.title" />'>
<p><bean:message key="cp.massupdate.tab4.header" /></p>

<html:checkbox property="massDTO.holdNegative" ><bean:message key="cp.massupdate.tab4.negative" /></html:checkbox>
<br/>
<html:checkbox property="massDTO.holdPositive" ><bean:message key="cp.massupdate.tab4.positive"/></html:checkbox>
<br/>
<div style="display:none" id="tab4_display" >
<p>
<a href="javascript:addHoldCells()"><bean:message key="cp.massupdate.tab4.add" /></a>
</p>
<logic:notEmpty name="massUpdateForm" property="massDTO.holdCells">
<table width="100%" cellspacing="0">
  <tr>
    <logic:iterate id="header" name="massUpdateForm" property="massDTO.dimensionHeader" type="String" >
        <th nowrap align="left" ><bean:write name="header" /></th>
    </logic:iterate>
	<th nowrap align="left" ><bean:message key="cp.massupdate.tab4.table.header6"/></th>
  </tr>
  <!-- lines -->
  <logic:iterate id="holdCell" name="massUpdateForm" property="massDTO.holdCells" indexId="rowId">
      <tr>
        <logic:iterate name="holdCell" id="element" indexId="coll" type="com.cedar.cp.utc.picker.ElementDTO" >
            <td nowrap title="<bean:write name="element" property="description" />" >
                <bean:write name="element" property="identifier" />
            </td>
        </logic:iterate>
        <td nowrap><a href="javascript:deleteHoldLine(<bean:write name="rowId"/>)"><bean:message key="cp.massupdate.tab2.delete" /></a></td>
      </tr>
  </logic:iterate>
</table>
</logic:notEmpty>
</div>
<div style="display:inline" id="tab4_message" >
    <h1 class="header1"><bean:message key="cp.massupdate.tab2.message" /></h1>
</div>
         </div>
            <!-- tab 5 -->
		<div id="5" dojoType="dijit.layout.ContentPane" title='<bean:message key="cp.massupdate.tab5.title" />'>
<p><bean:message key="cp.massupdate.tab5.header" /></p>
<p><bean:message key="cp.massupdate.tab5.info1" /></p>
<h4 class="header4"><bean:message key="cp.massupdate.tab5.info2" /></h4>
<p>
<html:checkbox property="massDTO.report" ><bean:message key="cp.massupdate.tab5.report"/></html:checkbox>
<br/>
<html:checkbox property="massDTO.cellPosting" ><bean:message key="cp.massupdate.tab5.posting"/></html:checkbox>
</p>
<cp:CPButton onclick="submitMassUpdate()" ><bean:message key="cp.massupdate.tab5.submit" /></cp:CPButton>
		 </div>
	</div>

<div>
<html:hidden property="passedAction" styleId="passedAction" />
<html:hidden property="deleteId" styleId="deleteId" />
<html:hidden property="selectedCell" />
<html:hidden property="massDTO.currentValue" styleId="currentValue" />
<input name="requiredDataType" id="requiredDataType" value="" type="hidden" />
<input name="dataTypeMultiSelect" id="dataTypeMultiSelect" value="false"  type="hidden" />
<input name="writeableDataTypes" id="writeableDataTypes" value="false"  type="hidden" />
</div>	

</div>
</html:form>

</body>