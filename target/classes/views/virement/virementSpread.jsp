<%-- Budget Transfer Spread Page --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>
<title>Budget Transfer Line Spread Profile</title>
<style type="text/css">
.outputnumber { text-align:right; padding:0; margin:0; border:0; background-color:transparent; }
</style>
<%--<script language="JavaScript1.2" src="js/cp.js"></script>--%>
<script language="JavaScript1.2">
function getElem( name )
{
    var items = document.getElementsByName(name);
    if( items.length > 0 )
        return items[0];
    else
        return null;
}

function recalcForm()
{
    // Reform the values to spread just for presentation sake
    getElem("line.transferValue").value = formatNumber( parseNumber(getElem("line.transferValue").value) );

    var numRows = getElem("numRows").value;
    var factorTotal = 0;
    for( i=0; i<numRows; i++ )
    {
        var weighting = Number(getElem( "line.spreadProfile["+i+"].weighting").value);
        var held = getElem("line.spreadProfile["+i+"].held").checked;
        if( !held )
            factorTotal = factorTotal + weighting;
    }

	allocate( numRows, factorTotal, "transferValue", parseNumber(getElem("line.transferValue").value));
}
// This is called when any of the fields are updated by the user - which indicates the
// the spread profile type is now a 'custom' type
function fieldUpdated()
{
    getElem("line.spreadProfileId").value = "WeightingProfilePK|0"; // Force selection of 'custom profile'
    recalcForm();
}
function allocate( p_numRows, p_factorTotal, p_idStr, p_val )
{
	var l_i = 0;
	var l_allocated = 0.0;
	var l_totalToAllocate = parseNumber(p_val);
	var l_remainder = 0.0;

	var l_absAllocThreshold = new Number( getElem("line.allocationThreshold").value );

	var l_spreadPrecision = 1;
	if( Math.abs(l_totalToAllocate) < l_absAllocThreshold )
		l_spreadPrecision = 100;

//alert("l_absAllocThreshold:"+l_absAllocThreshold+" l_spreadPrecision:"+l_spreadPrecision);

	if( p_factorTotal != 0 )
	{
		for( l_i=0; l_i<p_numRows; l_i++ )
		{
			var l_weighting = parseNumber(getElem("line.spreadProfile["+l_i+"].weighting").value);
			var l_held = getElem("line.spreadProfile["+l_i+"].held").checked;
			var l_alloc = 0.0;

			if( !l_held && l_weighting != 0 && Math.abs(l_allocated) < Math.abs(l_totalToAllocate) )
			{
				var l_tmp = 0.0 + l_totalToAllocate * l_spreadPrecision * l_weighting / p_factorTotal;
				var l_lvalue = l_tmp > 0.0 ? Math.floor(l_tmp) : Math.ceil(l_tmp);
				l_alloc = l_lvalue / l_spreadPrecision;
			}
			l_allocated = l_allocated + l_alloc;

			getElem("line.spreadProfile["+l_i+"]."+p_idStr).value = formatNumber(l_alloc);
		}
	}

//alert("l_totalToAllocate:"+l_totalToAllocate+" "+typeof(l_totalToAllocate));
//alert("l_allocated:"+l_allocated+" "+typeof(l_allocated));

  l_remainder = l_totalToAllocate - l_allocated;

//alert("l_remainder"+l_remainder+" "+typeof(l_remainder));

	if( l_remainder != 0.00 )
	{
        l_remainder = parseNumber(l_remainder.toFixed(2));

        for( l_i=p_numRows-1; l_i>=0; l_i-- )
		{
            var l_weighting = parseNumber(getElem("line.spreadProfile["+l_i+"].weighting").value);
            var l_held = getElem("line.spreadProfile["+l_i+"].held").checked;
            if( !l_held && l_weighting != 0 )
			{
				var l_origValue = 0.0 + parseNumber(getElem("line.spreadProfile["+l_i+"]."+p_idStr).value);
				var l_newValue = l_origValue + l_remainder;
//alert("l_origValue:"+l_origValue+" "+typeof(l_origValue));
//alert("l_newValue:"+l_newValue+" "+typeof(l_newValue));
//alert("l_remainder"+l_remainder+" "+typeof(l_remainder));
//alert( "Calculated last value should be "+(l_newValue)+" formatNumber gives "+formatNumber( l_newValue ));
				getElem("line.spreadProfile["+l_i+"]."+p_idStr).value = formatNumber( l_newValue );
				l_allocated += l_remainder;
				break;
			}
		}
	}

//	alert("Allocated:"+l_allocated+
//		  " Remainder:"+remainder);
}
dojo.addOnLoad(function()
{
    recalcForm();
});
function quit()
{
    getDocumentObject("userAction").value = "quit";
    getDocumentObject("virementSpreadForm").submit();
}
function ok()
{
    getDocumentObject("userAction").value = "ok";
    getDocumentObject("virementSpreadForm").submit();
}
function reselectProfile()
{
    getDocumentObject("userAction").value = "profileSelected";
    getDocumentObject("virementSpreadForm").submit();
}
</script>
</head>
<body onload="recalcForm()">
<H5 class="header5">Budget Transfer Line Spread Profile</h5>
<center>
<html:form action="editVirementSpread.do" styleId="virementSpreadForm">
<nested:hidden property="currentTab" styleId="currentTab"/>
<nested:hidden property="currentGroup" styleId="currentGroup"/>
<nested:hidden property="currentLine" styleId="currentLine"/>
<nested:hidden property="userAction" styleId="userAction"/>
<nested:hidden property="data.key"/>
<nested:hidden property="data.readOnly"/>
<nested:hidden property="parentPage"/>
<nested:hidden property="numRows" styleId="numRows"/>
<jsp:include page="../system/errorsInc.jsp"/>
<table class="groupcell">
    <tr>
        <td colspan="5">
            <div>
                <table width="100%">
                    <tr>
                        <logic:iterate id="header" name="virementSpreadForm"
                                       property="data.dimensionHeaders"
                                       type="com.cedar.cp.api.dimension.DimensionRef">
                            <th class="sth" align="left">
                                <bean:write name="header" property="narrative"/>
                            </th>
                        </logic:iterate>
                    </tr>
                    <tr>
                        <nested:iterate id="cell" name="virementSpreadForm"
                                       property="line.cells"
                                       type="com.cedar.cp.utc.picker.ElementDTO">
                            <td nowrap title="<nested:write name="cell" property="description"/>">
                                <nested:write property="identifier"/>
                                <nested:hidden property="id"/>
                                <nested:hidden property="structureId"/>
                                <nested:hidden property="description"/>
                                <nested:hidden property="identifier"/>
                            </td>
                        </nested:iterate>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <th colspan="2">Spread Profile</th>
        <th>Data Type</th>
        <th>Transfer Value</th>
    </tr>
    <tr>
		<td align="center" colspan="2">

			<html:select property="line.spreadProfileId" onchange="javascript:reselectProfile()">
				<nested:optionsCollection property="profiles"
								label="weightingProfileRef.narrative"
								value="weightingProfileRef.tokenizedKey"/>
            </html:select>
        </td>
        <td align="center">
			<html:select property="line.dataTypeId" onchange="javascript:reselectProfile()">
				<nested:optionsCollection property="data.dataTypes"
								label="narrative"
								value="key"/>
			</html:select>
		</td>
        <td align="right">
            <nested:text size="12" styleClass="inputnumber" onchange="recalcForm(); return true;"
                         property="line.transferValue"/>
        </td>
    </tr>
    <tr>
        <th>Period</th>
        <th>Held</th>
        <th>Spread Ratio</th>
        <th>Transfer Value</th>
    </tr>
<nested:hidden property="line.allocationThreshold"/>
<nested:iterate id="item" property="line.spreadProfile"
                type="com.cedar.cp.utc.struts.virement.VirementLineSpreadDTO" indexId="spreadNo">
    <tr>
        <td>
            <nested:write property="structureElementVisId"/>
        </td>
        <td align="center">
            <nested:checkbox property="held" onclick="fieldUpdated(); return true;" onchange="fieldUpdated(); return true;"/>
        </td>
        <td align="right">
            <nested:text size="5" styleClass="inputnumber" onchange="fieldUpdated(); return true;" property="weighting"/>
        </td>
        <td class="inputnumber">
            <nested:text size="12" styleClass="outputnumber" property="transferValue" readonly="true"/>
        </td>
        <nested:hidden property="structureElementVisId"/>
        <nested:hidden property="structureElementKey"/>
        <nested:hidden property="index"/>
    </tr>
</nested:iterate>
</table>
<table width="100%">
    <tr>
        <td colspan="5" align="right">
            <nested:equal value="false" property="data.readOnly">
				<cp:CPButton buttonType="button" value="OK" onclick="javascript:ok()"/>
            </nested:equal>
			<cp:CPButton buttonType="button" value="Cancel" onclick="javascript:quit()"/>
        </td>
    </tr>
</table>
</html:form>
</center>
</body>
