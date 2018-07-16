/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/

function pickBudgetCycle(contextPath)
{
	var params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
	params = params + ',width=630,height=430,left=120,top=120';
	target = contextPath+'/selectVirementBudgetCycle.do';
	if(checkBrowserOk())
		window.open(target, '_blank', params);
	else
		window.open(target, '_nameSearch', params);
}

function setModel(pModelId, pModelVisId)
{
	var modelId = getDocumentObject("data.modelId");
	var modelVisId = getDocumentObject("data.modelVisId");

	if(modelId.value != pModelId.value)
	{
		modelId.value = pModelId;
		modelVisId.value = pModelVisId;

		setFinanceCube(0, "");
		getDocumentObject("data.budgetCycleId").value = 0;
		getDocumentObject("data.budgetCycleVisId").value = "";

		if(modelId.value == 0)
		{
			getDocumentObject("finaceCubeIdButton").disabled = true;
			getDocumentObject("budgetCycleIdButton").disabled = true;
		}
		else
		{
			getDocumentObject("finaceCubeIdButton").disabled = false;
			getDocumentObject("budgetCycleIdButton").disabled = false;
		}
	}
}

function setFinanceCube(pFinanceCubeId, pFinanceCubeVisId)
{
	getDocumentObject("data.financeCubeId").value = pFinanceCubeId;
	getDocumentObject("data.financeCubeVisId").value = pFinanceCubeVisId;
}

function setBudgetCycle(pBudgetCycleId, pBudgetCycleVisId)
{
	getDocumentObject("data.budgetCycleId").value = pBudgetCycleId;
	getDocumentObject("data.budgetCycleVisId").value = pBudgetCycleVisId;
	getDocumentObject("currentTab").value = 1;
	getDocumentObject("virementDataEntryForm").submit();
}

function addVirementGroup(contextPath)
{
	getDocumentObject("currentTab").value = 2;
	getDocumentObject("userAction").value = "addGroup";
	getDocumentObject("virementDataEntryForm").action = contextPath+"/addVirementDetail.do";
	getDocumentObject("virementDataEntryForm").submit();
}

function addVirementBlankLine(groupNo)
{
	getDocumentObject("currentTab").value = 2;
	getDocumentObject("currentGroup").value = groupNo;
	getDocumentObject("userAction").value = "addBlankLine";
	getDocumentObject("virementDataEntryForm").submit();
}

function addVirementLine(contextPath,groupNo)
{
    getDocumentObject
    getDocumentObject("currentTab").value = 2;
	getDocumentObject("currentGroup").value = groupNo;
	getDocumentObject("userAction").value = "addLine";
	getDocumentObject("virementDataEntryForm").action = contextPath+"/addVirementDetail.do";
	getDocumentObject("virementDataEntryForm").submit();
}

function deleteVirementLine(groupNo, lineNo)
{
	getDocumentObject("currentTab").value = 2;
	getDocumentObject("currentGroup").value = groupNo;
	getDocumentObject("currentLine").value = lineNo;
	getDocumentObject("userAction").value = "deleteLine";
	getDocumentObject("virementDataEntryForm").submit();
}

function toggleVirementLine(groupNo, lineNo)
{
    getDocumentObject("currentTab").value = 2;
	getDocumentObject("currentGroup").value = groupNo;
	getDocumentObject("currentLine").value = lineNo;
	getDocumentObject("userAction").value = "toggleLine";
	getDocumentObject("virementDataEntryForm").submit();
}

function showSpreadPage(groupNo, lineNo)
{
    getDocumentObject("currentTab").value = 2;
    getDocumentObject("currentGroup").value = groupNo;
    getDocumentObject("currentLine").value = lineNo;
    getDocumentObject("userAction").value = "showSpreadPage";
    getDocumentObject("virementDataEntryForm").submit();
}

function showTab(idx)
{
//	showPanel(idx);
//	getDocumentObject("currentTab").value = idx;
}

function pageDivSize()
{
//    var clientDiv = dojo.byId('mainFrame');
//    var scrollDiv = dojo.byId('tab2-scoller');
//    var realClientHeight = dojo.style(clientDiv, 'height');
//    var result = realClientHeight - 105;
//    dojo.style(scrollDiv, 'height', result+"px");
}

dojo.addOnLoad(function()
{
//    pageDivSize();
//    dojo.connect(window, "onresize", pageDivSize);
//
//    if( getDocumentObject("currentTab") != null )
//        showTab(getDocumentObject("currentTab").value);
//
//	recalcForm();
//    var currentField = getDocumentObject("currentField").value;
//    if( currentField != null && currentField.length > 0 && getDocumentObjectByName(currentField) != null )
//	{
//		try	{ getDocumentObjectByName(currentField).focus(); } catch( ex ){ /* Shame never mind... */ }
//	}
}
);

function getDocumentObjectByName(name)
{
	var items = document.getElementsByName(name);
	if(items.length > 0)
		return items[0];
	else
		return null;
}

function recalcForm()
{
	for(var groupNo = 0; ; groupNo++)
	{
		var groupNotes = getDocumentObjectByName('data.groups[' + groupNo + '].notes');
		if(groupNotes != null)
		{
			var lRemain = 0.0;
			for(lineNo = 0; ; lineNo++)
			{
				var toValue = getDocumentObjectByName('data.groups[' + groupNo + '].lines[' + lineNo + '].to');
				var lValue = getDocumentObjectByName('data.groups[' + groupNo + '].lines[' + lineNo + '].transferValue');

				if(lValue != null)
				{
					lValue.value = formatNumber(lValue.value);

					if(toValue.value == "true")
					{
						lRemain = lRemain - parseNumber(lValue.value);
					}
					else
					{
						lRemain = lRemain + parseNumber(lValue.value);
					}
				}
				else
					break;
			}
			tempRemainder = getDocumentObjectByName('data.groups[' + groupNo + '].remainder');
			tempRemainder.value = formatNumber(lRemain);
		}
		else
			break;
	}
}

function inputKeyFilter( field )
{
    if( field == null  )
        return true;
    if( (field.event && field.event.keyCode && field.event.keyCode == 13) ||
        (window && window.event && window.event.keyCode && window.event.keyCode == 13) )
    {
        getDocumentObject("virementDataEntryForm").submit();
        return false;
    }
    return true;
}

function submitRequest()
{
	if(confirm("Are you sure you wish to submit this budget transfer?"))
	{
		getDocumentObject("userAction").value = "submit";
		getDocumentObject("virementDataEntryForm").submit();
	}
}
function saveRequest()
{
	getDocumentObject("userAction").value = "save";
	getDocumentObject("virementDataEntryForm").submit();
}
function quit()
{
	loadAddress("virements.do");
}
function setCurrentField(field)
{
    getDocumentObject("currentField").value=field.name;
}
