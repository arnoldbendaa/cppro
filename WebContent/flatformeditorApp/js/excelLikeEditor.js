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
/**
 * Created by jacool on 10/3/14.
 */

function zoomFactor_Changed(value) {
    var ss = $("#spreadSheet").wijspread("spread");
    var sheet = ss.getActiveSheet();
    sheet.zoom(value);
}

function selectionChanged(sender, args) {
    var spread = $("#spreadSheet").wijspread("spread");
    var sheet = spread.getActiveSheet();
    var position = sheet.getText(0, sheet.getActiveColumnIndex(), GC.Spread.Sheets.SheetArea.colHeader) + sheet.getText(sheet.getActiveRowIndex(), 0, GC.Spread.Sheets.SheetArea.rowHeader);
    $("#positionbox").val(position);
}

function setstatus(sheet) {
    var statusnow = sheet.editorStatus();
    if (statusnow === GC.Spread.Sheets.EditorStatus.ready) {
        $("#rapidInputMode").val("Ready");
    } else if (statusnow === GC.Spread.Sheets.EditorStatus.enter) {
        $("#rapidInputMode").val("Enter");
    } else if (statusnow === GC.Spread.Sheets.EditorStatus.edit) {
        $("#rapidInputMode").val("Edit");
    }
}

function getActualCellRange(cellRange, rowCount, columnCount) {
    if (cellRange.row == -1 && cellRange.col == -1) {
        return new GC.Spread.Sheets.Range(0, 0, rowCount, columnCount);
    } else if (cellRange.row == -1) {
        return new GC.Spread.Sheets.Range(0, cellRange.col, rowCount, cellRange.colCount);
    } else if (cellRange.col == -1) {
        return new GC.Spread.Sheets.Range(cellRange.row, 0, cellRange.rowCount, columnCount);
    }

    return cellRange;
}

function screenAdption() {
    var fullWidth = $(document).width();
    var fullHeight = $(document).height();
    var left = parseInt(fullWidth - 16, 10);
    $("#controlPanel").width(left);
    var top = $("#ribbon").height() + $("#formulaBar").height() + 8;
    $("#controlPanel").css("top", top);
    var spreadHeight = fullHeight - $("#ribbon").height() - $("#formulaBar").height() - $("#statusBar").height() - 16;
    $("#spreadSheet").height(spreadHeight);
    $("#spreadSheet").wijspread("refresh");
}
//sparkline
function rangeToString(range, sheet) {
    if (!range || !sheet) {
        return null;
    }

//    var spreadNS = $.wijmo.wijspread,
//        CalcNS = $.wijmo.wijspread.Calc;
    var spreadNS = GC.Spread.Sheets,
    	CalcNS = GC.Spread.Sheets.CalcEngine;
    var baseRow = sheet._activeRowIndex,
        baseCol = sheet._activeColIndex,
        useR1C1 = (sheet.referenceStyle() === spreadNS.ReferenceStyle.R1C1);

    if (range.row < 0 && range.col < 0) {
        range = new spreadNS.Range(0, -1, sheet.getRowCount(), -1);
    }
    return CalcNS.rangeToFormula(range, baseRow, baseCol, CalcNS.RangeReferenceRelative.allRelative, useR1C1);
}

function stringToRange(rangeText, sheet) {
    if (!rangeText || !sheet) {
        return null;
    }

//    var spreadNS = $.wijmo.wijspread,
//        CalcNS = $.wijmo.wijspread.Calc;
    var spreadNS = GC.Spread.Sheets,
	CalcNS = GC.Spread.Sheets.CalcEngine;

    var baseRow = sheet._activeRowIndex,
        baseCol = sheet._activeColIndex,
        useR1C1 = (sheet.referenceStyle() === spreadNS.ReferenceStyle.R1C1);

    try {
        return CalcNS.formulaToRange(rangeText, baseRow, baseCol, useR1C1);
    } catch (ex) {}
    return null;
}
//table
function getTableStyle() {
    var styleName = $("#tableStyles>option:selected").text();
    if (styleName) {
        return $.wijmo.wijspread.TableStyles[styleName.toLowerCase()]();
    }
    return null;
}

function getActualRange(range, maxRowCount, maxColCount) {
    var row = range.row < 0 ? 0 : range.row;
    var col = range.col < 0 ? 0 : range.col;
    var rowCount = range.rowCount < 0 ? maxRowCount : range.rowCount;
    var colCount = range.colCount < 0 ? maxColCount : range.colCount;

    return new GC.Spread.Sheets.Range(row, col, rowCount, colCount);
}

function getTableName(sheet) {
    var i = 1;
    while (true) {
        var name = "table" + i.toString();
        if (!sheet.findTableByName(name)) {
            return name;
        }
        i++;
    }
}
//Icon Change
function changeIcons() {
    iconChange($("button[title='Paste']"), true, 'fa fa-clipboard fa-2x');
    iconChange($("button[title='Table']"), true, 'fa fa-table fa-2x');
    iconChange($("button[title='Picture']"), true, 'fa fa-picture-o fa-2x');
    iconChange($("button[title='Sort A-Z']"), false, 'fa fa-sort-alpha-asc');
    iconChange($("button[title='Sort Z-A']"), false, 'fa fa-sort-alpha-desc');
    iconChange($("button[title='Filter']"), true, 'fa fa-filter fa-2x');
    iconChange($("button[title='Comment']"), true, 'fa fa-comment fa-2x');
    iconChange($("button[title='Remove Conditional Formats']"), false, 'fa fa-trash-o');
    iconChange($("button[title='Circle Invalid Data']"), false, 'fa fa-circle-thin');
    iconChange($("button[title='Clear Validation Circles']"), false, 'fa fa-trash-o');
    iconChange($("button[title='Clear CellType']"), false, 'fa fa-trash-o');
    iconChange($("button[title='Freeze Setting']"), false, 'fa fa-cog');
    iconChange($("button[title='Group']"), true, 'fa fa-link fa-2x');
    iconChange($("button[title='UnGroup']"), true, 'fa fa-chain-broken fa-2x');
    iconChange($("button[title='Show Detail']"), false, 'fa fa-plus-square-o');
    iconChange($("button[title='Hide Detail']"), false, 'fa fa-minus-square-o');
    iconChange($("button[title='Data Validation']"), true, 'fa fa-ban fa-2x');
    iconChange($("button[title='SparklineEx']"), true, 'fa fa-bar-chart-o fa-2x');
    iconChange($("button[title='Freeze Pane']"), true, 'fa fa-plus-square fa-2x');
    iconChange($("button[title='UnFreeze']"), true, 'fa fa-square-o fa-2x');
    iconChange($("button[title='Import']"), true, 'fa fa-folder-open-o fa-2x');
    iconChange($("button[title='Export']"), true, 'fa fa-floppy-o fa-2x');
}

function iconChange(element, isBig, cssClass) {
    element.children('.ui-icon').remove();
    var i = $('<i></i>');
    i.addClass(cssClass);
    if (isBig) {
        i.css({
            position: 'absolute',
            left: '50%',
            top: '50%',
            height: '32px',
            width: '32px',
            display: 'block',
            cursor: 'pointer',
            overflow: 'hidden'
        }).css('margin-left', '-17px').css('margin-top', '-22px').css('text-align', 'center');
    } else {
        i.css({
            position: 'absolute',
            left: '.5em',
            top: '50%',
            height: '16px',
            width: '16px',
            display: 'block',
            cursor: 'pointer',
            overflow: 'hidden'
        }).css('margin-top', '-7px').css('text-align', 'center');
    }
    element.prepend(i);
    return i;
}
//conditional format dialog
function setminColor() {
    $("#colordialog").dialog({
        title: "Min Color"
    });
    $("#colordialog").dialog("open");
}

function setmidColor() {
    $("#colordialog").dialog({
        title: "Mid Color"
    });
    $("#colordialog").dialog("open");
}

function setmaxColor() {
    $("#colordialog").dialog({
        title: "Max Color"
    });
    $("#colordialog").dialog("open");
}

function createIconCriteriaDOM() {
    var IconSetType = $.wijmo.wijspread.IconSetType,
        IconCriterion = $.wijmo.wijspread.IconCriterion,
        IconValueType = $.wijmo.wijspread.IconValueType;
    var iconSetType = parseInt($("#iconSetType").val(), 10);
    var iconCriteria = [];
    if (iconSetType >= IconSetType.ThreeArrowsColored && iconSetType <= IconSetType.ThreeSymbolsUncircled) {
        iconCriteria = new Array(2);
        iconCriteria[0] = new IconCriterion(true, IconValueType.Percent, 33);
        iconCriteria[1] = new IconCriterion(true, IconValueType.Percent, 67);
    } else if (iconSetType >= IconSetType.FourArrowsColored && iconSetType <= IconSetType.FourTrafficLights) {
        iconCriteria = new Array(3);
        iconCriteria[0] = new IconCriterion(true, IconValueType.Percent, 25);
        iconCriteria[1] = new IconCriterion(true, IconValueType.Percent, 50);
        iconCriteria[2] = new IconCriterion(true, IconValueType.Percent, 75);
    } else if (iconSetType >= IconSetType.FiveArrowsColored && iconSetType <= IconSetType.FiveBoxes) {
        iconCriteria = new Array(4);
        iconCriteria[0] = new IconCriterion(true, IconValueType.Percent, 20);
        iconCriteria[1] = new IconCriterion(true, IconValueType.Percent, 40);
        iconCriteria[2] = new IconCriterion(true, IconValueType.Percent, 60);
        iconCriteria[3] = new IconCriterion(true, IconValueType.Percent, 80);
    }

    $("#iconCriteriaSetting").empty();
    $.each(iconCriteria, function(i, v) {
        var $div = $("<div style='margin-top: 10px'></div>"),
            $selectOperator = $("<select></select>"),
            $input = $("<input style='margin-left: 10px'/>"),
            $selectType = $("<select style='margin-left: 10px'></select>");
        $selectOperator.html("<option value=1 " + getSelected(v.isGreaterThanOrEqualTo, true) + ">>=</option>" + "<option value=0 " + getSelected(v.isGreaterThanOrEqualTo, false) + ">></option>");
        $input.val(v.iconValue);
        $selectType.html("<option value=1 " + getSelected(v.iconValueType, 1) + ">Number</option>" + "<option value=4 " + getSelected(v.iconValueType, 4) + ">Percent</option>" + "<option value=7 " + getSelected(v.iconValueType, 7) + ">Formula</option>" + "<option value=5 " + getSelected(v.iconValueType, 5) + ">Percentile</option>");
        $div.append($selectOperator).append($input).append($selectType);
        $("#iconCriteriaSetting").append($div);
    });
}

function getSelected(v1, v2) {
    return v1 === v2 ? "selected='selected'" : "";
}

function setEnumTypeOfCF(rule, type) {
    switch (rule) {
        case "0":
            $("#ruletext").text("Format only cells with:");
            $("#andtext").hide();
            $("#formattext").hide();
            type.empty();
            type.show();
            $("#ComparisonOperator1").show();
            $("#value1").show();
            $("#value1").val("");
            $("#value2").hide();
            $("#colorScale").hide();
            type.append("<option value='0'>EqualsTo</option>");
            type.append("<option value='1'>NotEqualsTo</option>");
            type.append("<option value='2'>GreaterThan</option>");
            type.append("<option value='3'>GreaterThanOrEqualsTo</option>");
            type.append("<option value='4'>LessThan</option>");
            type.append("<option value='5'>LessThanOrEqualsTo</option>");
            type.append("<option value='6'>Between</option>");
            type.append("<option value='7'>NotBetween</option>");
            break;
        case "1":
            $("#ruletext").text("Format only cells with:");
            $("#andtext").hide();
            $("#formattext").hide();
            type.empty();
            type.show();
            $("#ComparisonOperator1").show();
            $("#value1").show();
            $("#value1").val("");
            $("#value2").hide();
            $("#colorScale").hide();
            type.append("<option value='0'>Contains</option>");
            type.append("<option value='1'>DoesNotContain</option>");
            type.append("<option value='2'>BeginsWith</option>");
            type.append("<option value='3'>EndsWith</option>");
            break;
        case "2":
            $("#ruletext").text("Format only cells with:");
            $("#andtext").hide();
            $("#formattext").hide();
            type.empty();
            type.show();
            $("#ComparisonOperator1").show();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").hide();
            type.append("<option value='0'>Today</option>");
            type.append("<option value='1'>Yesterday</option>");
            type.append("<option value='2'>Tomorrow</option>");
            type.append("<option value='3'>Last7Days</option>");
            type.append("<option value='4'>ThisMonth</option>");
            type.append("<option value='5'>LastMonth</option>");
            type.append("<option value='6'>NextMonth</option>");
            type.append("<option value='7'>ThisWeek</option>");
            type.append("<option value='8'>LastWeek</option>");
            type.append("<option value='9'>NextWeek</option>");
            break;
        case "3":
            $("#ruletext").text("Format values where this formula is true:");
            $("#andtext").hide();
            $("#formattext").show();
            $("#formattext").text("eg:=COUNTIF($B$1:$B$5,A1).");
            type.empty();
            $("#ComparisonOperator1").hide();
            $("#value1").show();
            $("#value1").val("");
            $("#value2").hide();
            $("#colorScale").hide();
            break;
        case "4":
            $("#ruletext").text("Format values that rank in the:");
            $("#andtext").hide();
            $("#formattext").hide();
            type.empty();
            $("#ComparisonOperator1").show();
            $("#value1").show();
            $("#value1").val("10");
            $("#value2").hide();
            $("#colorScale").hide();
            type.append("<option value='0'>Top</option>");
            type.append("<option value='1'>Bottom</option>");
            break;
        case "5":
            $("#ruletext").text("Format all:");
            $("#andtext").hide();
            $("#formattext").show();
            $("#formattext").text("values in the selected range.");
            type.empty();
            $("#ComparisonOperator1").hide();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").hide();
            break;
        case "6":
            $("#ruletext").text("Format all:");
            $("#andtext").hide();
            $("#formattext").show();
            $("#formattext").text("values in the selected range.");
            type.empty();
            $("#ComparisonOperator1").hide();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").hide();
            break;
        case "7":
            $("#ruletext").text("Format values that are:");
            $("#andtext").hide();
            $("#formattext").show();
            $("#formattext").text("the average for selected range.");
            type.empty();
            type.show();
            $("#ComparisonOperator1").show();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").hide();
            type.append("<option value='0'>Above</option>");
            type.append("<option value='1'>Below</option>");
            type.append("<option value='2'>EqualOrAbove</option>");
            type.append("<option value='3'>EqualOrBelow</option>");
            type.append("<option value='4'>Above1StdDev</option>");
            type.append("<option value='5'>Below1StdDev</option>");
            type.append("<option value='6'>Above2StdDev</option>");
            type.append("<option value='7'>Below2StdDev</option>");
            type.append("<option value='8'>Above3StdDev</option>");
            type.append("<option value='9'>Below3StdDev</option>");
            break;
        case "8":
            $("#ruletext").text("Format all cells based on their values:");
            $("#andtext").hide();
            $("#formattext").hide();
            type.empty();
            type.hide();
            $("#ComparisonOperator1").hide();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").show();
            $("#midpoint").hide();
            $("#midType").hide();
            $("#midValue").hide();
            $("#midColor").hide();
            break;
        case "9":
            $("#ruletext").text("Format all cells based on their values:");
            $("#andtext").hide();
            $("#formattext").hide();
            type.empty();
            type.hide();
            $("#ComparisonOperator1").hide();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").show();
            $("#midpoint").show();
            $("#midType").show();
            $("#midValue").show();
            $("#midColor").show();
            break;
        default:
            $("#andtext").hide();
            type.empty();
            type.show();
            $("#ComparisonOperator1").show();
            $("#value1").show();
            $("#value1").val("");
            $("#value2").hide();
            $("#colorScale").hide();
            type.append("<option value='0'>EqualsTo</option>");
            type.append("<option value='1'>NotEqualsTo</option>");
            type.append("<option value='2'>GreaterThan</option>");
            type.append("<option value='3'>GreaterThanOrEqualsTo</option>");
            type.append("<option value='4'>LessThan</option>");
            type.append("<option value='5'>LessThanOrEqualsTo</option>");
            type.append("<option value='6'>Between</option>");
            type.append("<option value='7'>NotBetween</option>");
            break;
    }
}
//DataBar dialog
function parseValue(value) {
    if (!isNaN(value) && isFinite(value)) {
        return parseFloat(value);
    } else {
        return value;
    }
}

function setBarColor() {
    $("#colordialog").dialog({
        title: "Bar Color"
    });
    $("#colordialog").dialog("open");
}

function setBarBorderColor() {
    $("#colordialog").dialog({
        title: "Bar Border Color"
    });
    $("#colordialog").dialog("open");
}

function setBarNegativeFillColor() {
    $("#colordialog").dialog({
        title: "Bar Negative Fill Color"
    });
    $("#colordialog").dialog("open");
}

function setBarNegativeBorderColor() {
    $("#colordialog").dialog({
        title: "Bar Negative Border Color"
    });
    $("#colordialog").dialog("open");
}

function setBarAxisColor() {
    $("#colordialog").dialog({
        title: "Bar Axis Color"
    });
    $("#colordialog").dialog("open");
}

function getFloatingObjectName(sheet) {
    var i = 0;
    while (true) {
        var name = "floatingObject" + i.toString();
        if (!sheet.findFloatingObject(name) && !sheet.findPicture(name)) {
            return name;
        }
        i++;
    }
}

function updateSheetList() {
    $("#sheetList").empty();
    var spread = $("#spreadSheet").wijspread("spread");
    if (spread && spread.sheets) {
        for (var index = 0; index < spread.sheets.length; index++) {
            var sheetName = spread.sheets[index].getName();
            $("#sheetList").append("<option value='" + sheetName + "'>" + sheetName + "</option>");
        }
    }
}
//group
function rowlabelactive() {
    $("#showRowGroupLabel").addClass("ui-state-active");
}

function rowlabelnoactive() {
    $("#showRowGroupLabel").removeClass("ui-state-active");
}

function collabelactive() {
    $("#showColGroupLabel").addClass("ui-state-active");
}

function collabelnoactive() {
    $("#showColGroupLabel").removeClass("ui-state-active");
}
//validation
function IsRangeSet() {
    if (validatorType == "NumberValidator" || validatorType == "TextLengthValidator") {
        if ($("#txtValidatorValue1").val().length == 0 || $("#txtValidatorValue2").val().length == 0) {
            alert("You must enter both a Maximum and Minimum.");
        }
    } else if (validatorType == "DateValidator") {
        if ($("#txtValidatorValue1").val().length == 0 || $("#txtValidatorValue2").val().length == 0) {
            alert("You must enter both a End Date and a Start Date.");
        }
    } else if (validatorType == "ListValidator") {
        if ($("#txtValidatorValue").val().length == 0) {
            alert("You must enter a Source.");
        }
    } else if (validatorType == "FormulaListValidator" || validatorType == "FormulaValidator") {
        if ($("#txtValidatorValue").val().length == 0) {
            alert("You must enter a Formula.");
        }
    }
}
//picture
function setPictureBackColor() {
    $("#colordialog").dialog({
        title: "Picture Back Color"
    });
    $("#colordialog").dialog("open");
}

function setPictureBorderColor() {
    $("#colordialog").dialog({
        title: "Picture Border Color"
    });
    $("#colordialog").dialog("open");
}
//border
function noborderclick() {
    $(":checkbox").removeAttr("checked");
    $("#border11").css("border-right", "none");
    $("#border11").css("border-bottom", "none");
    $("#border11").css("border-left", "none");
    $("#border11").css("border-top", "none");
    $("#border12").css("border-right", "none");
    $("#border12").css("border-bottom", "none");
    $("#border12").css("border-left", "none");
    $("#border12").css("border-top", "none");
    $("#border21").css("border-right", "none");
    $("#border21").css("border-bottom", "none");
    $("#border21").css("border-left", "none");
    $("#border21").css("border-top", "none");
    $("#border22").css("border-right", "none");
    $("#border22").css("border-bottom", "none");
    $("#border22").css("border-left", "none");
    $("#border22").css("border-top", "none");
    $("#bordertable").css("border", "none");
}

function outlineborderclick() {
    $(":checkbox").removeAttr("checked");
    $("[id*=Side]").attr("checked", true);
    $("#border11").css("border-left", "1px solid gray");
    $("#border11").css("border-top", "1px solid gray");
    $("#border12").css("border-right", "1px solid gray");
    $("#border12").css("border-top", "1px solid gray");
    $("#border21").css("border-left", "1px solid gray");
    $("#border21").css("border-bottom", "1px solid gray");
    $("#border22").css("border-bottom", "1px solid gray");
    $("#border22").css("border-right", "1px solid gray");
    $("#border11").css("border-right", "none");
    $("#border11").css("border-bottom", "none");
    $("#border12").css("border-left", "none");
    $("#border12").css("border-bottom", "none");
    $("#border21").css("border-right", "none");
    $("#border21").css("border-top", "none");
    $("#border22").css("border-top", "none");
    $("#border22").css("border-left", "none");
    $("#bordertable").css("border", "1px solid gray");
}

function allborderclick() {
    $(":checkbox").attr("checked", true);
    $("#border11").css("border-right", "1px solid gray");
    $("#border11").css("border-bottom", "1px solid gray");
    $("#border11").css("border-left", "1px solid gray");
    $("#border11").css("border-top", "1px solid gray");
    $("#border12").css("border-right", "1px solid gray");
    $("#border12").css("border-bottom", "1px solid gray");
    $("#border12").css("border-left", "1px solid gray");
    $("#border12").css("border-top", "1px solid gray");
    $("#border21").css("border-right", "1px solid gray");
    $("#border21").css("border-bottom", "1px solid gray");
    $("#border21").css("border-left", "1px solid gray");
    $("#border21").css("border-top", "1px solid gray");
    $("#border22").css("border-right", "1px solid gray");
    $("#border22").css("border-bottom", "1px solid gray");
    $("#border22").css("border-left", "1px solid gray");
    $("#border22").css("border-top", "1px solid gray");
    $("#bordertable").css("border", "1px solid gray");
}
// color dialog
function colorSelected(event) {
    var event = event || window.event;
    var target = event.srcElement || event.target;
    $("#selectedColor").val(target.bgColor);
    $("#colorSample").attr("style", "background-color:" + target.bgColor);
}
//Excel I/O
function importFileWithAjax(serverUrl, formData, successCallback, errorCallback) {
    $.ajax({
        url: serverUrl, //Server script to process data
        type: 'POST',
        success: function completeHandler(data, textStatus, jqXHR) {
            if (successCallback) {
                successCallback(jqXHR.responseText);
            }
        },
        error: function errorHandler(jqXHR, textStatus, errorThrown) {
            if (errorCallback) {
                errorCallback(errorThrown);
            }
        },

        data: formData, // Form data
        cache: false, //Options to tell jQuery not to process data or worry about content-type.
        contentType: false,
        processData: false,
        headers: { //Options to tell server return data with specified type
            "Accept": "application/json"
        }
    });
}

function importFileWithIFrame(serverUrl, $fileElement, options, successCallback, errorCallback) {
    var theFileContainer = $fileElement.parent();
    var theCloneFileElement = $fileElement.clone();

    var $iframe = $("<iframe id='xFrame' style='display: none' src='about:blank'></iframe>").appendTo("body");
    $iframe.ready(function() {
        var formDoc = getiframeDocument($iframe);
        formDoc.write("<html><head></head><body><form method='Post' enctype='multipart/form-data' action='" + serverUrl + "'></form>dummy windows for postback</body></html>");
        var $form = $(formDoc).find('form');
        //append options to form
        $.each(options, function(index, field) {
            $('<input type="hidden"/>').prop('name', field.name).val(field.value).appendTo($form);
        });
        //append Excel file to form
        $form.append($fileElement.attr("id", "tempFile1"));
        theFileContainer.prepend(theCloneFileElement);
        $form.submit();
    });
    $iframe.bind("load", function(e, args) {
        var formDoc = getiframeDocument($iframe);
        try {
            var responseText = formDoc.body ? formDoc.body.innerText : null;
            if (successCallback && responseText) {
                successCallback(responseText);
            }

        } catch (error) {
            if (errorCallback) {
                errorCallback(error);
            }
        }
    });
}

function exportFile(serverUrl, content) {
    var formInnerHtml = '<input type="hidden" name="type" value="application/json" />';
    formInnerHtml += '<input type="hidden" name="data" value="' + htmlSpecialCharsEntityEncode(content) + '" />';
    var $iframe = $("<iframe style='display: none' src='about:blank'></iframe>").appendTo("body");
    $iframe.ready(function() {
        var formDoc = getiframeDocument($iframe);
        formDoc.write("<html><head></head><body><form method='Post' action='" + serverUrl + "'>" + formInnerHtml + "</form>dummy windows for postback</body></html>");
        var $form = $(formDoc).find('form');
        $form.submit();
    });
}

function getiframeDocument($iframe) {
    var iframeDoc = $iframe[0].contentWindow || $iframe[0].contentDocument;
    if (iframeDoc.document) {
        iframeDoc = iframeDoc.document;
    }
    return iframeDoc;
}
var htmlSpecialCharsRegEx = /[<>&\r\n"']/gm;
var htmlSpecialCharsPlaceHolders = {
    '<': 'lt;',
    '>': 'gt;',
    '&': 'amp;',
    '\r': "#13;",
    '\n': "#10;",
    '"': 'quot;',
    "'": 'apos;' /*single quotes just to be safe*/
};

function htmlSpecialCharsEntityEncode(str) {
    return str.replace(htmlSpecialCharsRegEx, function(match) {
        return '&' + htmlSpecialCharsPlaceHolders[match];
    });
}

function showLoading() {
    $("#spreadSheet").css("position", "relative");
    var width = $("#spreadSheet").width() + 2,
        height = $("#spreadSheet").height();
    $("<span id='delaySpan'><span id='icon' style='display:inline-block'></span>Importing...</span>").css("left", width / 2 - 70).css("top", height / 2 - 30).css("position", "absolute").css("color", "#4f4f4f").css("background", "#ffffff").css("border", "1px solid #a8a8a8").css("border-radius", "3px").css("-webkit-border-radius", "3px").css("box-shadow", "0 0 10px rgba(0, 0, 0, 0.25").css("font-family", "Arial, sans-serif").css("font-size", "20px").css("padding", "0.4em").insertAfter("#spreadSheet");
    $("<div id='delayDiv'></div>").css("background", "#2D5972").css("opacity", 0.3).css("position", "absolute").css("top", 0).css("left", 0).css("width", width).css("height", height).insertAfter("#spreadSheet");
}

function hideLoading() {
    $("#delayDiv").remove();
    $("#delaySpan").remove();
}

function importSuccessCallback(responseText) {
    var spreadJson = JSON.parse(responseText);
    if (spreadJson.spread) {
        var spread = $("#spreadSheet").wijspread("spread");
        spread.fromJSON(spreadJson.spread);
        hideLoading();
    } else if (spreadJson.error) {
        hideLoading();
        alert(spreadJson.error);
    }
}

function importErrorCallback(error) {
    hideLoading();
    alert(error);
}

function excelImport() {
    try {
        var dataOnly = $('#import_excel_dataOnly').prop('checked') ? 1 : 0,
            dataAndFormulasOnly = $('#import_excel_dataAndFormulasOnly').prop('checked') ? 3 : 0,
            rowHeaders = $('#import_excel_rowHeaders').prop('checked') ? 4 : 0,
            columnHeaders = $('#import_excel_columnHeaders').prop('checked') ? 8 : 0,
            rowColumnHeaders = $('#import_excel_rowcolumnHeaders').prop('checked') ? 12 : 0,
            doNotRecalculateAfterLoad = $('#import_excel_donotrecalculateafterload').prop('checked') ? 1024 : 0;
        var excelOpenFlags = (dataOnly | dataAndFormulasOnly | rowHeaders | columnHeaders | rowColumnHeaders | doNotRecalculateAfterLoad);

        var serverUrl = $("#serviceUrl").val() + "/xsapi/import/";
        var formData;
        var theFile = $("#import_excel_file");

        if ($.browser.msie && parseInt($.browser.version, 10) <= 9) {
            formData = [];
            formData.push({
                name: "ExcelOpenFlags",
                value: excelOpenFlags
            });

            showLoading();
            importFileWithIFrame(serverUrl, theFile, formData, importSuccessCallback, importErrorCallback);
        } else {
            if (serverUrl && theFile[0]) {
                formData = new FormData();
                formData.append("ExcelOpenFlags", excelOpenFlags);
                formData.append("file", theFile[0].files[0]);

                showLoading();
                importFileWithAjax(serverUrl, formData, importSuccessCallback, importErrorCallback);
            }
        }
    } catch (ex) {
        alert(ex);
    }
}

function excelExport(spread) {
    var ExcelSaveFlags = {
        NoFlagsSet: 0,
        NoFormulas: 1,
        SaveCustomRowHeaders: 2,
        SaveCustomColumnHeaders: 4,
        SaveAsFiltered: 8,
        SaveBothCustomRowAndColumnHeaders: 6,
        SaveAsViewed: 136,
        DataOnly: 32,
        AutoRowHeight: 4096
    };
    var saveFlags = ExcelSaveFlags;
    var dataObj = {
        "spread": spread.toJSON(),
        "exportFileType": "xlsx",
        "excel": {
            "saveFlags": saveFlags
        }
    };
    var content = JSON.stringify(dataObj);
    var serverUrl = $("#serviceUrl").val() + "/xsapi/export/";
    exportFile(serverUrl, content);
}
//context menu
function getHitTest(pageX, pageY, sheet) {
    var offset = $("#spreadSheet").offset(),
        x = pageX - offset.left,
        y = pageY - offset.top;
    return sheet.hitTest(x, y);
}

function showMergeContextMenu(sheet) {
    var selections = sheet.getSelections();
    if (selections && selections.length > 0) {
        var spans = sheet.getSpans(selections[selections.length - 1], $.wijmo.wijspread.SheetArea.viewport);
        if (spans && spans.length > 0) {
            $(".context-merge").hide();
            $(".context-unmerge").show();
        } else {
            $(".context-merge").show();
            $(".context-unmerge").hide();
        }
    }
}
// initalize spread
function initSpread() {
    var spread = $("#spreadSheet").wijspread("spread"); // get instance of wijspread control
    spread.allowUndo(true);

    var sheet = spread.getActiveSheet(); // get active worksheet of the wijspread control
    spread.useWijmoTheme = true;
    spread.repaint();
    spread.bind("EnterCell", selectionChanged);
    //formulabox
//    var fbx = new $.wijmo.wijspread.FormulaTextBox(document.getElementById('formulabox'));
	var fbx = new GC.Spread.Sheets.FormulaTextBox.FormulaTextBox(document.getElementById("formulabox"));
	fbx.workbook(spread);
//    fbx.spread(spread);
    //show range group
    $("#showRowGroup").attr("disabled", true);
    $("#showColGroup").attr("disabled", true);
    $("#showRowGroupLabel").attr("disabled", true);
    $("#showColGroupLabel").attr("disabled", true);
    $("#showRowGroup").attr("value", "false");
    $("#showColGroup").attr("value", "false");
    $("#showRowGroupLabel").addClass("ui-state-disabled");
    $("#showColGroupLabel").addClass("ui-state-disabled");
    $("#showRowGroupLabel").bind("mouseup", rowlabelactive);
    $("#showColGroupLabel").bind("mouseup", collabelactive);

    $("#setTabStripColor").attr("disabled", true);
    $("#setTabStripColor").addClass("ui-state-disabled");
    //rapid input mode start
    setstatus(sheet);
    sheet.bind(GC.Spread.Sheets.Events.EditorStatusChanged, function() {
        setstatus(sheet);
    });
    //rapid input mode end

    // var url = '/cp/flatFormEditor/fetchFlatForm/' + FLAT_FORM_ID;

    // //    $.ajax({
    // //        url: url,
    // //        datatype: 'json',
    // //        success: function (data) {
    // //            showLoading();
    // //            spread.suspendPaint ();
    // //            var jsonForm = JSON.parse(data.jsonForm);
    // //            
    // //            spread.fromJSON(jsonForm.spread);
    // //            spread.resumePaint();
    // //            hideLoading();
    // //        },
    // //        error: function (ex) {
    // //            alert(ex);
    // //        }
    // //    });

    // $.ajax({
    //     async: true,
    //     type: 'GET',
    //     url: url,
    //     datatype: 'json',
    //     //data: { flatFormId: 333},
    //     beforeSend: function(xhr) {
    //         showLoading();
    //     },
    //     success: function(flatForm) {
    //         //alert("success loading workbook");
    //     },
    //     error: function(ex) {
    //         console.log(ex);
    //         hideLoading();
    //     }
    // }).done(function(data) {
    //     spread.suspendPaint ();
    //     var jsonForm = JSON.parse(data.jsonForm);
    //     spread.fromJSON(jsonForm.spread);
    //     spread.resumePaint();
    //     hideLoading();
    // });

    /**
    sheet.suspendPaint ();
    var cfs = sheet.getConditionalFormats();

    var table = sheet.addTable("tblOperatingExpenses2", 4, 1, 11, 6, new $.wijmo.wijspread.TableStyle());
    //table.rowFilter().setShowFilterButton(false);
    table.showFooter(true);

    var dataTable = [];
    dataTable[0] = new Array("STATUS", "OPERATING", "BUDGET", "ACTUAL", "DIFFERENCE ($)", "DIFFERENCE (%)");
    dataTable[1] = new Array(0, "Advertising", 600, 545, 0, 0);
    dataTable[2] = new Array(0, "Debts", 125, 150, 0, 0);
    dataTable[3] = new Array(0, "Benefits", 100, 100, 0, 0);
    dataTable[4] = new Array(0, "Supplies", 100, 90, 0, 0);
    dataTable[5] = new Array(0, "Postage", 150, 145, 0, 0);
    dataTable[6] = new Array(0, "Rent or mortgage", 1000, 1000, 0, 0);
    dataTable[7] = new Array(0, "Sales expenses", 500, 630, 0, 0);
    dataTable[8] = new Array(0, "Taxes", 350, 375, 0, 0);
    dataTable[9] = new Array(0, "Utilities", 400, 370, 0, 0);
    dataTable[10] = new Array(0, "Other", 500, 435, 0, 0);
    dataTable[11] = new Array(0, "Total Expenses", 0, 0, 0, 0);

    sheet.addSpan(0, 1, 1, 4);
    sheet.addSpan(0, 5, 1, 2);
    sheet.addSpan(1, 1, 1, 6);
    sheet.addSpan(2, 1, 1, 6);
    sheet.addSpan(3, 1, 1, 2);
    sheet.addSpan(16, 1, 1, 6);
    sheet.addSpan(17, 1, 1, 6);

    sheet.getDefaultStyle().vAlign = $.wijmo.wijspread.VerticalAlign.center;
    sheet.getDefaultStyle().font = "lighter 10pt Calibri";
    sheet.getDefaultStyle().foreColor = "rgb(68, 84, 106)";
    sheet.gridline = new $.wijmo.wijspread.LineBorder("Black", $.wijmo.wijspread.LineStyle.empty);
    sheet.setGridlineOptions({
        showVerticalGridline: false,
        showHorizontalGridline: false
    });
    sheet.getCell(0, 1).value("Expense Budget").font("lighter 28pt Calibri");
    sheet.getCell(0, 5).value("CONTOSO, 2013").font("11pt Calibri").foreColor("rgb(64, 64, 64)").hAlign($.wijmo.wijspread.HorizontalAlign.right).vAlign($.wijmo.wijspread.VerticalAlign.bottom);
    sheet.getCell(3, 1).value("OPERATING BUDGET").font("bold 13pt Calibri ").foreColor("rgb(64, 64, 64)");
    sheet.getCells(4, 1, 4, 6).font("bold 10pt Calibri").borderTop(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin));
    sheet.getCells(15, 1, 15, 6).font("bold 10pt Calibri").borderBottom(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin));
    sheet.getCells(3, 1, 3, 2).borderLeft(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin)).borderTop(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin)).borderRight(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin));
    sheet.getCells(4, 1, 15, 1).borderLeft(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin)).hAlign($.wijmo.wijspread.HorizontalAlign.center);
    sheet.getCells(4, 6, 15, 6).borderRight(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin));
    sheet.getCells(5, 1, 14, 6).borderBottom(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.dashed));
    ;

    for (var row = 4; row < 16; row++) {
        for (var col = 1; col < 7; col++) {
            sheet.setValue(row, col, dataTable[row - 4][col - 1]);
        }
    }

    var rowHeights = new Array(74, 4, 20);
    for (var row = 0; row < 3; row++) {
        sheet.setRowHeight(row, rowHeights[row]);
    }
    for (var row = 3; row < 17; row++) {
        sheet.setRowHeight(row, 24);
    }
    sheet.setRowHeight(17, 4);

    var colWidths = new Array(18, 83, 111, 84, 85, 126, 129);
    for (var col = 0; col < 7; col++) {
        sheet.setColumnWidth(col, colWidths[col]);
    }

    sheet.getCells(1, 1, 1, 6).borderTop(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thick)).borderBottom(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin));

    sheet.getCells(17, 1, 17, 6).borderTop(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thick)).borderBottom(new $.wijmo.wijspread.LineBorder("rgb(217,217,217)", $.wijmo.wijspread.LineStyle.thin));

    sheet.getCells(3, 3, 15, 5).formatter('"$"#,##0.00');
    sheet.getCells(3, 6, 14, 6).formatter('0%');
    sheet.getCell(15, 6).formatter('0.0%');

    var iconSetRule1 = new $.wijmo.wijspread.IconSetRule();
    iconSetRule1.ranges = [new $.wijmo.wijspread.Range(15, 1, 1, 1)];
    iconSetRule1.iconSetType($.wijmo.wijspread.IconSetType.ThreeSymbolsCircled).showIconOnly(true);
    var icons = iconSetRule1.iconCriteria();
    icons[0] = new $.wijmo.wijspread.IconCriterion(false, $.wijmo.wijspread.IconValueType.Number, -1);
    icons[1] = new $.wijmo.wijspread.IconCriterion(true, $.wijmo.wijspread.IconValueType.Number, 1);

    var cellValueRule1 = new $.wijmo.wijspread.CellValueRule($.wijmo.wijspread.ComparisonOperator.LessThan, 0);
    cellValueRule1.style = new $.wijmo.wijspread.Style();
    cellValueRule1.style.foreColor = "red";
    cellValueRule1.ranges = [new $.wijmo.wijspread.Range(15, 5, 1, 2)];

    var dataBarRule1 = new $.wijmo.wijspread.DataBarRule();
    dataBarRule1.minimumType($.wijmo.wijspread.ScaleValueType.Automin);
    dataBarRule1.maximumType($.wijmo.wijspread.ScaleValueType.Automax);
    dataBarRule1.ranges = [new $.wijmo.wijspread.Range(5, 4, 10, 1)];
    dataBarRule1.color("orange").showBorder(true).borderColor("orange").dataBarDirection($.wijmo.wijspread.BarDirection.RightToLeft);

    var dataBarRule2 = new $.wijmo.wijspread.DataBarRule();
    dataBarRule2.minimumType($.wijmo.wijspread.ScaleValueType.Automin);
    dataBarRule2.maximumType($.wijmo.wijspread.ScaleValueType.Automax);
    dataBarRule2.ranges = [new $.wijmo.wijspread.Range(5, 3, 10, 1)];
    dataBarRule2.color("rgb(0,138,239)").showBorder(true).borderColor("rgb(0,138,239)");

    var dataBarRule3 = new $.wijmo.wijspread.DataBarRule();
    dataBarRule3.minimumType($.wijmo.wijspread.ScaleValueType.LowestValue);
    dataBarRule3.maximumType($.wijmo.wijspread.ScaleValueType.HighestValue);
    dataBarRule3.ranges = [new $.wijmo.wijspread.Range(5, 6, 10, 1)];
    dataBarRule3.color("rgb(91,155,213)").showBorder(true).borderColor("rgb(91,155,213)").negativeFillColor("rgb(237,125,49)").useNegativeBorderColor(true).negativeBorderColor("rgb(237,125,49)").axisPosition($.wijmo.wijspread.DataBarAxisPosition.CellMidPoint);

    var iconSetRule2 = new $.wijmo.wijspread.IconSetRule();
    iconSetRule2.ranges = [new $.wijmo.wijspread.Range(5, 1, 10, 1)];
    iconSetRule2.iconSetType($.wijmo.wijspread.IconSetType.ThreeSymbolsUncircled).reverseIconOrder(true).showIconOnly(true);

    cfs.addRule(iconSetRule1);
    cfs.addRule(cellValueRule1);
    cfs.addRule(dataBarRule1);
    cfs.addRule(dataBarRule2);
    cfs.addRule(dataBarRule3);
    cfs.addRule(iconSetRule2);

    table.setColumnFormula(0, "=tblOperatingExpenses2[[#Totals],[DIFFERENCE (%)]]");
    table.setColumnFormula(2, "=SUBTOTAL(109,[BUDGET])");
    table.setColumnFormula(3, "=SUBTOTAL(109,[ACTUAL])");
    table.setColumnFormula(4, "=SUBTOTAL(109,[DIFFERENCE ($)])");
    table.setColumnFormula(5, '=IFERROR(SUM(tblOperatingExpenses2[[#Totals],[DIFFERENCE ($)]]/tblOperatingExpenses2[[#Totals],[BUDGET]]),"")');

    table.setColumnDataFormula(0, '=IFERROR([@ACTUAL]/[@BUDGET],"")');
    table.setColumnDataFormula(4, '=[@BUDGET]-[@ACTUAL]');
    table.setColumnDataFormula(5, '=IFERROR([@[DIFFERENCE ($)]]/[@BUDGET],"")');

    updateSheetList();
    sheet.resumePaint();
*/
}
var heightflag;

var validatorType;

var pictureUrl = "";
$(document).ready(function() {
    validatorType = "AnyValidator";
    $("#validatorTab1").hide();
    $("#validatorTab2").hide();
    $("#isIntTab").hide();
    $("#isTimeTab").hide();

    var barcolor = "#00FF00";
    var barbordercolor = "#008000";
    var barnegativefillcolor = "#008000";
    var barnegativebordercolor = "#000000";
    var baraxiscolor = "#0000FF";
    var mincolor = "red";
    var midcolor = "yellow";
    var maxcolor = "green";
    var selCellType = "TextCell";
    var pictureBackColor = "transparent";
    var pictureBorderColor = "black";

    var tableName = "";
    var isTableAdd = false;
    var isPictureAdd = false;
    var isCommentAdd = false;
    //theme change
    $('.premium-themes a, .jqueryui-themes a').click(function() {
        $("link[title='rocket-jqueryui']").attr("href", $(this).attr("href"));
        setTimeout(function() {
            var ss = $("#spreadSheet").wijspread("spread");
            ss.repaint();
        }, 100);

        return false;
    });

    //datavalidation
    $("#datavalidationdialog").wijribbon();
    $("#validationInputMessge").bind("mousedown", IsRangeSet);
    $("#validationErrorAlert").bind("mousedown", IsRangeSet);
    $("#validatorTypes").change(function() {
        validatorType = $(this).val();
        switch (validatorType) {
            case "AnyValidator":
                $("#validatorTab1").hide();
                $("#isTimeTab").hide();
                $("#isIntTab").hide();
                $("#validatorTab2").hide();
                break;
            case "DateValidator":
                $("#validatorTab1").show();
                $("#isTimeTab").show();
                $("#isIntTab").hide();
                $("#validatorTab2").hide();
                $("#rangeStart").text("Start date:");
                $("#rangeEnd").text("End date:");
                break;
            case "FormulaListValidator":
                $("#validatorTab2").show();
                $("#validatorTab1").hide();
                $("#rangevalidator").text("Formula:");
                $("#prompt").text("(e.g: E5:I5)");
                $("#datatitle").hide();
                break;
            case "FormulaValidator":
                $("#validatorTab2").show();
                $("#validatorTab1").hide();
                $("#rangevalidator").text("Formula:");
                $("#prompt").text("(e.g: =ISERROR(FIND(\" \",A1)))");
                $("#datatitle").hide();
                break;
            case "ListValidator":
                $("#validatorTab2").show();
                $("#validatorTab1").hide();
                $("#rangevalidator").text("Source");
                $("#prompt").text("(e.g: 1,2,3)");
                $("#datatitle").hide();
                break;
            case "NumberValidator":
                $("#validatorTab1").show();
                $("#isTimeTab").hide();
                $("#isIntTab").show();
                $("#validatorTab2").hide();
                $("#rangeStart").text("Minimum:");
                $("#rangeEnd").text("Maximum:");
                break;
            case "TextLengthValidator":
                $("#validatorTab1").show();
                $("#isTimeTab").hide();
                $("#isIntTab").hide();
                $("#validatorTab2").hide();
                $("#rangeStart").text("Minimum:");
                $("#rangeEnd").text("Maximum:");
                break;
        }
    });
    $("#chkValidatorIgnoreBlank").change(function() {
        var ss = $("#spreadSheet").wijspread("spread");
        var sheet = ss.getActiveSheet();
        var sels = sheet.getSelections();
        for (var i = 0; i < sels.length; i++) {
            var sel = getActualCellRange(sels[i], sheet.getRowCount(), sheet.getColumnCount());
            for (var r = 0; r < sel.rowCount; r++) {
                for (var c = 0; c < sel.colCount; c++) {
                    var dv = sheet.getDataValidator(sel.row + r, sel.col + c);
                    if (dv) {
                        dv.ignoreBlank = $(this).prop("checked");
                    }
                }
            }
        }
    });
    $("#chkShowError").change(function() {
//        var ss = $("#spreadSheet").wijspread("spread");
        var ss = new GC.Spread.Sheets.Workbook(document.getElementById('spread'));
        var sheet = ss.getActiveSheet();
        var checked = $("#chkShowError").prop("checked");
        if (checked) {
            ss.bind(GC.Spread.Sheets.Events.ValidationError	, function(event, data) {
                var dv = data.validator;
                if (dv) {
                    alert(dv.errorMessage);
                }
            });
        } else {
            ss.unbind(GC.Spread.Sheets.Events.ValidationError	);
        }
    });

    $("#datavalidationdialog").dialog({
        autoOpen: false,
        height: 410,
        width: 400,
        modal: true,
        resizable: false,
        buttons: {
            ClearValidator: function() {
                var ss = $("#spreadSheet").wijspread("spread");
                var sheet = ss.getActiveSheet();
                var sels = sheet.getSelections();
                for (var i = 0; i < sels.length; i++) {
                    var sel = getActualCellRange(sels[i], sheet.getRowCount(), sheet.getColumnCount());
                    for (var r = 0; r < sel.rowCount; r++) {
                        for (var c = 0; c < sel.colCount; c++) {
                            sheet.setDataValidator(sel.row + r, sel.col + c, null);
                        }
                    }
                }
                $("#validatorTypes").val("AnyValidator");
                $("#txtValidatorValue1").val("");
                $("#txtValidatorValue2").val("");
                $("#txtValidatorValue").val("");
                $("#validatorTab1").hide();
                $("#isTimeTab").hide();
                $("#isIntTab").hide();
                $("#validatorTab2").hide();
                $("#txtMessageTitle").val("");
                $("#txtMessageMessage").val("");
                $("#validatorErrorStyles").val("0");
                $("#txtErrorTitle").val("");
                $("#txtErrorMessage").val("");
                validatorType = "AnyValidator";
                $(this).dialog("close");
            },
            OK: function() {
                //setvalidator
                var gcdv = GC.Spread.Sheets.DataValidation.DefaultDataValidator;

                var ddv = null;
                var v1 = $("#txtValidatorValue1").val();
                var v2 = $("#txtValidatorValue2").val();
                switch (validatorType) {
                    case "AnyValidator":
                        ddv = new $.wijmo.wijspread.DefaultDataValidator();
                        break;
                    case "DateValidator":
                        if ($("#chkIsTime").prop("checked")) {
                            ddv = gcdv.createDateValidator(parseInt($("#validatorComparisonOperator").val(), 10), isNaN(v1) ? v1 : new Date(v1), isNaN(v2) ? v2 : new Date(v2), true);
                        } else {
                            ddv = gcdv.createDateValidator(parseInt($("#validatorComparisonOperator").val(), 10), isNaN(v1) ? v1 : new Date(v1), isNaN(v2) ? v2 : new Date(v2), false);
                        }
                        break;
                    case "FormulaListValidator":
                        ddv = gcdv.createFormulaListValidator($("#txtValidatorValue").val());
                        break;
                    case "FormulaValidator":
                        ddv = gcdv.createFormulaValidator($("#txtValidatorValue").val());
                        break;
                    case "ListValidator":
                        ddv = gcdv.createListValidator($("#txtValidatorValue").val());
                        break;
                    case "NumberValidator":
                        if ($("#chkIsInteger").prop("checked")) {
                            ddv = gcdv.createNumberValidator(parseInt($("#validatorComparisonOperator").val(), 10), isNaN(v1) ? v1 : parseInt(v1, 10), isNaN(v2) ? v2 : parseInt(v2, 10), true);
                        } else {
                            ddv = gcdv.createNumberValidator(parseInt($("#validatorComparisonOperator").val(), 10), isNaN(v1) ? v1 : parseFloat(v1), isNaN(v2) ? v2 : parseFloat(v2), false);
                        }
                        break;
                    case "TextLengthValidator":
                        ddv = gcdv.createTextLengthValidator(parseInt($("#validatorComparisonOperator").val(), 10), isNaN(v1) ? v1 : parseInt(v1, 10), isNaN(v2) ? v2 : parseInt(v2, 10));
                        break;
                }

                if (ddv != null) {
                    ddv.errorMessage = $("#txtErrorMessage").val();
                    ddv.errorStyle = parseInt($("#validatorErrorStyles").val(), 10);
                    ddv.errorTitle = $("#txtErrorTitle").val();
                    ddv.showErrorMessage = $("#chkShowError").prop("checked");
                    ddv.ignoreBlank = $("#chkValidatorIgnoreBlank").prop("checked");
                    var checked = $("#chkShowMessage").prop("checked");
                    if (checked) {
                        ddv.inputTitle = $("#txtMessageTitle").val();
                        ddv.inputMessage = $("#txtMessageMessage").val();
                    }

                    var ss = $("#spreadSheet").wijspread("spread");
                    var sheet = ss.getActiveSheet();
                    sheet.suspendPaint ();
                    var sels = sheet.getSelections();
                    for (var i = 0; i < sels.length; i++) {
                        var sel = getActualCellRange(sels[i], sheet.getRowCount(), sheet.getColumnCount());
                        for (var r = 0; r < sel.rowCount; r++) {
                            for (var c = 0; c < sel.colCount; c++) {
                                sheet.setDataValidator(sel.row + r, sel.col + c, ddv);
                            }
                        }
                    }
                    sheet.resumePaint();
                }
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });


    //Conditional format
    $("#Rule1").bind("change", function() {
        var rule = $("#Rule1").val();
        var type = $("#ComparisonOperator1");
        setEnumTypeOfCF(rule, type);
    });
    $("#ComparisonOperator1").bind("change", function() {
        var type = $("#ComparisonOperator1").val();
        switch (type) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                $("#andtext").hide();
                $("#value2").hide();
                break;
            case "6":
            case "7":
                $("#andtext").show();
                $("#andtext").text("and");
                $("#value2").show();
                break;
            default:
                $("#andtext").hide();
                $("#value2").hide();
                break;
        }
    });
    $("#iconSetType").bind("change", function() {
        createIconCriteriaDOM();
    });
    $("#conditionalformatdialog").dialog({
        autoOpen: false,
        height: 400,
        width: 500,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread1 = $("#spreadSheet").wijspread("spread");
                var sheet = spread1.getActiveSheet();
                var sels = sheet.getSelections();
                var style = new $.wijmo.wijspread.Style();
                style.backColor = "red";
                style.foreColor = "green";
                var value1 = $("#value1").val();
                var value2 = $("#value2").val();
                var cfs = sheet.getConditionalFormats();
                var rule = $("#Rule1").val();
                var operator = parseInt($("#ComparisonOperator1").val(), 10);

                var minType = parseInt($("#minType").val(), 10);
                var midType = parseInt($("#midType").val(), 10);
                var maxType = parseInt($("#maxType").val(), 10);
                var midColor = midcolor;
                var minColor = mincolor;
                var maxColor = maxcolor;
                var midValue = $("#midValue").val();
                var maxValue = $("#maxValue").val();
                var minValue = $("#minValue").val();

                switch (rule) {
                    case "0":
                        var doubleValue1 = parseFloat(value1);
                        var doubleValue2 = parseFloat(value2);
                        cfs.addCellValueRule(operator, isNaN(doubleValue1) ? value1 : doubleValue1, isNaN(doubleValue2) ? value2 : doubleValue2, style, sels);
                        break;
                    case "1":
                        cfs.addSpecificTextRule(operator, value1, style, sels);
                        break;
                    case "2":
                        cfs.addDateOccurringRule(operator, style, sels);
                        break;
                    case "3":
                        try {
                            cfs.addFormulaRule(value1, style, sels);
                        } catch (e) {
                            cfs.removeRule(cfs.getRule(cfs.count() - 1));
                            alert("Invalid Formula");
                        }
                        break;
                    case "4":
                        cfs.addTop10Rule(operator, parseInt(value1, 10), style, sels);
                        break;
                    case "5":
                        cfs.addUniqueRule(style, sels);
                        break;
                    case "6":
                        cfs.addDuplicateRule(style, sels);
                        break;
                    case "7":
                        cfs.addAverageRule(operator, style, sels);
                        break;
                    case "8":
                        cfs.add2ScaleRule(minType, minValue, minColor, maxType, maxValue, maxColor, sels);
                        break;
                    case "9":
                        cfs.add3ScaleRule(minType, minValue, minColor, midType, midValue, midColor, maxType, maxValue, maxColor, sels);
                        break;
                    default:
                        var doubleValue1 = parseFloat(value1);
                        var doubleValue2 = parseFloat(value2);
                        cfs.addCellValueRule(operator, isNaN(doubleValue1) ? value1 : doubleValue1, isNaN(doubleValue2) ? value2 : doubleValue2, style, sels);
                        break;
                }
                $(this).dialog("close");
                sheet.repaint();
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    $("#databardialog").dialog({
        autoOpen: false,
        height: 500,
        width: 420,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread1 = $("#spreadSheet").wijspread("spread");
                var sheet = spread1.getActiveSheet();
                sheet.suspendPaint ();

                var selections = sheet.getSelections();
                if (selections) {
                    var ranges = [];
                    $.each(selections, function(i, v) {
                        ranges.push(new (v.row, v.col, v.rowCount, v.colCount));
                    });
                    var cfs = sheet.getConditionalFormats();
                    var dataBarRule = new $.wijmo.wijspread.DataBarRule();
                    dataBarRule.ranges = ranges;
                    dataBarRule.minimumType(parseInt($("#minimumType").val(), 10));
                    dataBarRule.minimumValue(parseValue($("#minimumValue").val()));
                    dataBarRule.maximumType(parseInt($("#maximumType").val(), 10));
                    dataBarRule.maximumValue(parseValue($("#maximumValue").val()));
                    dataBarRule.gradient($("#gradient").prop("checked"));
                    dataBarRule.color(barcolor);
                    dataBarRule.showBorder($("#showBorder").prop("checked"));
                    dataBarRule.borderColor(barbordercolor);
                    dataBarRule.dataBarDirection(parseInt($("#dataBarDirection").val(), 10));
                    dataBarRule.negativeFillColor(barnegativefillcolor);
                    dataBarRule.useNegativeFillColor($("#useNegativeFillColor").prop("checked"));
                    dataBarRule.negativeBorderColor(barnegativebordercolor);
                    dataBarRule.useNegativeBorderColor($("#useNegativeBorderColor").prop("checked"));
                    dataBarRule.axisPosition(parseInt($("#axisPosition").val(), 10));
                    dataBarRule.axisColor(baraxiscolor);
                    dataBarRule.showBarOnly($("#showBarOnly").prop("checked"));
                    cfs.addRule(dataBarRule);
                }

                sheet.resumePaint();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    $("#iconsetdialog").dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread1 = $("#spreadSheet").wijspread("spread");
                var sheet = spread1.getActiveSheet();
                sheet.suspendPaint ();

                var selections = sheet.getSelections();
                if (selections) {
                    var ranges = [];
                    $.each(selections, function(i, v) {
                        ranges.push(new (v.row, v.col, v.rowCount, v.colCount));
                    });
                    var cfs = sheet.getConditionalFormats();
                    var iconSetRule = new $.wijmo.wijspread.IconSetRule();
                    iconSetRule.ranges = ranges;
                    iconSetRule.iconSetType(parseInt($("#iconSetType").val(), 10));
                    var $divs = $("#iconCriteriaSetting div");
                    var iconCriteria = iconSetRule.iconCriteria();
                    $.each($divs, function(i, v) {
                        var isGreaterThanOrEqualTo = parseInt($(v.children[0]).val(), 10) === 1;
                        var iconValueType = parseInt($(v.children[2]).val(), 10);
                        var iconValue = $(v.children[1]).val();
                        if (iconValueType !== $.wijmo.wijspread.IconValueType.Formula) {
                            iconValue = parseInt(iconValue, 10);
                        }
                        iconCriteria[i] = new $.wijmo.wijspread.IconCriterion(isGreaterThanOrEqualTo, iconValueType, iconValue);
                    });
                    iconSetRule.reverseIconOrder($("#reverseIconOrder").prop("checked"));
                    iconSetRule.showIconOnly($("#showIconOnly").prop("checked"));
                    cfs.addRule(iconSetRule);
                }

                sheet.resumePaint();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    //Cell Type
    $("#leftSide").change(function() {
        var checked = $("#leftSide").prop("checked");
        if (checked) {
            $("#border11").css("border-left", "1px solid gray");
            $("#border21").css("border-left", "1px solid gray");
            $("#bordertable").css("border-left", "1px solid gray");
        } else {
            $("#border11").css("border-left", "none");
            $("#border21").css("border-left", "none");
            $("#bordertable").css("border-left", "none");
        }
    });
    $("#topSide").change(function() {
        var checked = $("#topSide").prop("checked");
        if (checked) {
            $("#border11").css("border-top", "1px solid gray");
            $("#border12").css("border-top", "1px solid gray");
            $("#bordertable").css("border-top", "1px solid gray");
        } else {
            $("#border11").css("border-top", "none");
            $("#border12").css("border-top", "none");
            $("#bordertable").css("border-top", "none");
        }
    });
    $("#rightSide").change(function() {
        var checked = $("#rightSide").prop("checked");
        if (checked) {
            $("#border12").css("border-right", "1px solid gray");
            $("#border22").css("border-right", "1px solid gray");
            $("#bordertable").css("border-right", "1px solid gray");
        } else {
            $("#border12").css("border-right", "none");
            $("#border22").css("border-right", "none");
            $("#bordertable").css("border-right", "none");
        }
    });
    $("#bottomSide").change(function() {
        var checked = $("#bottomSide").prop("checked");
        if (checked) {
            $("#border21").css("border-bottom", "1px solid gray");
            $("#border22").css("border-bottom", "1px solid gray");
            $("#bordertable").css("border-bottom", "1px solid gray");
        } else {
            $("#border21").css("border-bottom", "none");
            $("#border22").css("border-bottom", "none");
            $("#bordertable").css("border-bottom", "none");
        }
    });
    $("#hInside").change(function() {
        var checked = $("#hInside").prop("checked");
        if (checked) {
            $("#border11").css("border-bottom", "1px solid gray");
            $("#border12").css("border-bottom", "1px solid gray");
            $("#border21").css("border-top", "1px solid gray");
            $("#border22").css("border-top", "1px solid gray");
        } else {
            $("#border11").css("border-bottom", "none");
            $("#border12").css("border-bottom", "none");
            $("#border21").css("border-top", "none");
            $("#border22").css("border-top", "none");
        }
    });
    $("#vInside").change(function() {
        var checked = $("#vInside").prop("checked");
        if (checked) {
            $("#border11").css("border-right", "1px solid gray");
            $("#border12").css("border-left", "1px solid gray");
            $("#border21").css("border-right", "1px solid gray");
            $("#border22").css("border-left", "1px solid gray");
        } else {
            $("#border11").css("border-right", "none");
            $("#border12").css("border-left", "none");
            $("#border21").css("border-right", "none");
            $("#border22").css("border-left", "none");
        }
    });
    $("#borderdialog").dialog({
        autoOpen: false,
        height: 450,
        width: 450,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                sheet.suspendPaint ();
                var sels = sheet.getSelections();
                //var lineBorder = new $.wijmo.wijspread.LineBorder($("#lineColor").val(), $("#lineStyle").val());
                var lineBorder = new $.wijmo.wijspread.LineBorder($("#lineColor").val(), $.wijmo.wijspread.LineStyle[$("#lineStyle").val()]);
                for (var n = 0; n < sels.length; n++) {
                    var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                    sheet.setBorder(sel, lineBorder, {
                        left: $("#leftSide").attr("checked") == "checked",
                        top: $("#topSide").attr("checked") == "checked",
                        right: $("#rightSide").attr("checked") == "checked",
                        bottom: $("#bottomSide").attr("checked") == "checked",
                        innerHorizontal: $("#hInside").attr("checked") == "checked",
                        innerVertical: $("#vInside").attr("checked") == "checked"
                    });
                }
                sheet.resumePaint();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    $("#celltypedialog").dialog({
        autoOpen: false,
        height: 350,
        width: 350,
        modal: true,
        resizable: false,
        buttons: {
            Set: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                var cellType;
                switch (selCellType) {
                    case "TextCell":
                        cellType = new $.wijmo.wijspread.TextCellType();
//                        IOCellType.prototype = new GC.Spread.Sheets.CellTypes.Text();
                        break;
                    case "ComboCell":
                        cellType = new $.wijmo.wijspread.ComboBoxCellType();
                        cellType.editorValueType(parseInt($("#selComboCellEditorValueType").val(), 10));
                        var itemsText = $("#txtComboCellItemsText").val().split(",");
                        var itemsValue = $("#txtComboCellItemsValue").val().split(",");
                        var itemsLength = itemsText.length > itemsValue.length ? itemsText.length : itemsValue.length;
                        var items = [];
                        for (var count = 0; count < itemsLength; count++) {
                            var t = itemsText.length > count && itemsText[0] != "" ? itemsText[count] : undefined;
                            var v = itemsValue.length > count && itemsValue[0] != "" ? itemsValue[count] : undefined;
                            if (t != undefined && v != undefined) {
                                items[count] = {
                                    text: t,
                                    value: v
                                };
                            } else if (t != undefined) {
                                items[count] = {
                                    text: t
                                };
                            } else if (v != undefined) {
                                items[count] = {
                                    value: v
                                };
                            }
                        }
                        cellType.items(items);
                        break;
                    case "CheckBoxCell":
                        cellType = new $.wijmo.wijspread.CheckBoxCellType();
                        if ($("#txtCheckBoxCellTextCaption").val() != "") {
                            cellType.caption($("#txtCheckBoxCellTextCaption").val());
                        }
                        if ($("#txtCheckBoxCellTextTrue").val() != "") {
                            cellType.textTrue($("#txtCheckBoxCellTextTrue").val());
                        }
                        if ($("#txtCheckBoxCellTextIndeterminate").val() != "") {
                            cellType.textIndeterminate($("#txtCheckBoxCellTextIndeterminate").val());
                        }
                        if ($("#txtCheckBoxCellTextFalse").val() != "") {
                            cellType.textFalse($("#txtCheckBoxCellTextFalse").val());
                        }
                        cellType.textAlign(parseInt($("#selCheckBoxCellAlign").val(), 10));
                        cellType.isThreeState($("#ckbCheckBoxCellIsThreeState").attr("checked") === "checked" ? true : false);
                        break;
                    case "ButtonCell":
                        cellType = new $.wijmo.wijspread.ButtonCellType();
                        if ($("#txtButtonCellMarginLeft").val() != "") {
                            cellType.marginLeft(parseFloat($("#txtButtonCellMarginLeft").val()));
                        }
                        if ($("#txtButtonCellMarginTop").val() != "") {
                            cellType.marginTop(parseFloat($("#txtButtonCellMarginTop").val()));
                        }
                        if ($("#txtButtonCellMarginRight").val() != "") {
                            cellType.marginRight(parseFloat($("#txtButtonCellMarginRight").val()));
                        }
                        if ($("#txtButtonCellMarginBottom").val() != "") {
                            cellType.marginBottom(parseFloat($("#txtButtonCellMarginBottom").val()));
                        }
                        if ($("#txtButtonCellText").val() != "") {
                            cellType.text($("#txtButtonCellText").val());
                        }
                        if ($("#txtButtonCellBackColor").val() != "") {
                            cellType.buttonBackColor($("#txtButtonCellBackColor").val());
                        }
                        break;
                    case "HyperLinkCell":
                        cellType = new $.wijmo.wijspread.HyperLinkCellType();
                        if ($("#txtHyperLinkCellLinkColor").val() != "") {
                            cellType.linkColor($("#txtHyperLinkCellLinkColor").val());
                        }
                        if ($("#txtHyperLinkCellVisitedLinkColor").val() != "") {
                            cellType.visitedLinkColor($("#txtHyperLinkCellVisitedLinkColor").val());
                        }
                        if ($("#txtHyperLinkCellText").val() != "") {
                            cellType.text($("#txtHyperLinkCellText").val());
                        }
                        if ($("#txtHyperLinkCellToolTip").val() != "") {
                            cellType.linkToolTip($("#txtHyperLinkCellToolTip").val());
                        }
                        break;
                }
                sheet.suspendPaint ();
                sheet.suspendEvent();
                var sels = sheet.getSelections();
                for (var i = 0; i < sels.length; i++) {
                    var sel = getActualCellRange(sels[i], sheet.getRowCount(), sheet.getColumnCount());
                    for (var r = 0; r < sel.rowCount; r++) {
                        for (var c = 0; c < sel.colCount; c++) {
                            sheet.setCellType(sel.row + r, sel.col + c, cellType, $.wijmo.wijspread.SheetArea.viewport);
                        }
                    }
                }
                sheet.resumeEvent();
                sheet.resumePaint();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    $("#colordialog").dialog({
        autoOpen: false,
        height: 420,
        width: 485,
        modal: true,
        resizable: false,
        buttons: {
            Select: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                sheet.suspendPaint ();
                var title = $("#colordialog").dialog("option", "title");
                if (title == "Back Color") {
                    var sels = sheet.getSelections();
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).backColor($("#selectedColor").val());
                    }
                } else if (title == "Fore Color") {
                    var sels = sheet.getSelections();
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).foreColor($("#selectedColor").val());
                    }
                } else if (title == "Tab Strip Color") {
                    if (sheet) {
                        var color = $("#selectedColor").val();
                        sheet.sheetTabColor(color);
                    }
                } else if (title == "FrozenLineColor") {
                    if (sheet) {
                        var color = $("#selectedColor").val();
                        sheet.options.frozenlineColor = (color);
                    }
                } else if (title == "SpreadBackColor") {
                    if (spread) {
                        var color = $("#selectedColor").val();
                        spread.backColor(color);
                    }
                } else if (title == "GrayAreaBackColor") {
                    if (spread) {
                        var color = $("#selectedColor").val();
                        spread.options.grayAreaBackColor = (color);
                    }
                } else if (title == "SelectionBorderColor") {
                    if (sheet) {
                        var color = $("#selectedColor").val();
                        sheet.selectionBorderColor(color);
                    }
                } else if (title == "SelectionBackColor") {
                    if (sheet) {
                        var color = $("#selectedColor").val();
                        sheet.selectionBackColor(color);
                    }
                } else if (title == "Indicator Border Color") {
                    if (spread) {
                        var color = $("#selectedColor").val();
                        spread.cutCopyIndicatorBorderColor(color);
                    }
                } else if (title == "Bar Color") {
                    barcolor = $("#selectedColor").val();
                    $("#color").css("background", barcolor);
                } else if (title == "Bar Border Color") {
                    barbordercolor = $("#selectedColor").val();
                    $("#borderColor").css("background", barbordercolor);
                } else if (title == "Bar Negative Fill Color") {
                    barnegativefillcolor = $("#selectedColor").val();
                    $("#negativeFillColor").css("background", barnegativefillcolor);
                } else if (title == "Bar Negative Border Color") {
                    barnegativebordercolor = $("#selectedColor").val();
                    $("#negativeBorderColor").css("background", barnegativebordercolor);
                } else if (title == "Bar Axis Color") {
                    baraxiscolor = $("#selectedColor").val();
                    $("#axisColor").css("background", baraxiscolor);
                } else if (title == "Min Color") {
                    mincolor = $("#selectedColor").val();
                    $("#minColor").css("background", mincolor);
                } else if (title == "Mid Color") {
                    midcolor = $("#selectedColor").val();
                    $("#midColor").css("background", mincolor);
                } else if (title == "Max Color") {
                    maxcolor = $("#selectedColor").val();
                    $("#maxColor").css("background", maxcolor);
                } else if (title == "Picture Back Color") {
                    pictureBackColor = $("#selectedColor").val();
                    $("#pictureBackColor").css("background", pictureBackColor);
                } else if (title == "Picture Border Color") {
                    pictureBorderColor = $("#selectedColor").val();
                    $("#pictureBorderColor").css("background", pictureBorderColor);
                }
                sheet.resumePaint();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    //Freeze setting
    $("#freezesettingdialog").dialog({
        autoOpen: false,
        height: 350,
        width: 350,
        modal: true,
        resizable: false,
        buttons: {
            Set: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                sheet.suspendPaint ();
                sheet.suspendEvent();
                var frozenRowCount = parseInt($("#frozenRowCount").val(), 10);
                sheet.setFrozenRowCount(frozenRowCount);
                var frozenColumnCount = parseInt($("#frozenColumnCount").val(), 10);
                sheet.setFrozenColumnCount(frozenColumnCount);
                var frozenTrailingRowCount = parseInt($("#frozenTrailingRowCount").val(), 10);
                sheet.setFrozenTrailingRowCount(frozenTrailingRowCount);
                var frozenTrailingColumnCount = parseInt($("#frozenTrailingColumnCount").val(), 10);
                sheet.setFrozenTrailingColumnCount(frozenTrailingColumnCount);
                sheet.resumePaint();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    //sheet visible
    $("#sheetList").bind('mouseover', updateSheetList);
    //add picture
    if (typeof FileReader === 'undefined') {
        result.innerHTML = "Sorry,Your browser doesn't support FileReader";
        $("#file_input").attr('disabled', 'disabled');
        $("#file_input1").attr('disabled', 'disabled');
    } else {
        $("#file_input").bind('change', function() {
            var file = this.files[0];
            if (!/image\/\w+/.test(file.type)) {
                alert("The file muse be image!");
                return false;
            }
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function(e) {
                pictureUrl = this.result;
            }
        });
        $("#file_input1").bind('change', function() {
            var file = this.files[0];
            if (!/image\/\w+/.test(file.type)) {
                alert("The file muse be image!");
                return false;
            }
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function(e) {
                pictureUrl = this.result;
            }
        });
    }
    $("#picturedialog").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                sheet.suspendPaint ();
                if (isPictureAdd) {
                    var sel = sheet.getSelections()[0];
                    if (pictureUrl !== "" && sel) {
                        var cr = getActualRange(sel, sheet.getRowCount(), sheet.getColumnCount());
                        var name = getFloatingObjectName(sheet);
                        sheet.addPicture(name, pictureUrl, cr.row, cr.col);
                    }
                } else {
                    var pictures = sheet.getPictures(),
                        dynamicSize = $("#dynamicSize").prop("checked"),
                        dynamicMove = $("#dynamicMove").prop("checked"),
                        backColor = pictureBackColor;
                    borderColor = pictureBorderColor;
                    borderWidth = parseInt($("#pictureBorderWidth").val()), borderRadius = parseInt($("#pictureBorderRadius").val()), borderStyle = $("#pictureBorderStyle").val(), stretch = parseInt($("#pictureStretch").val());
                    if (borderWidth < 0) {
                        borderWidth = 0;
                    }
                    if (borderRadius < 0) {
                        borderRadius = 0;
                    }
                    for (var index = 0, len = pictures.length; index < len; index++) {
                        var picture = pictures[index];
                        if (picture && picture.isSelected()) {
                            if (pictureUrl !== "") {
                                picture.src(pictureUrl);
                            }
                            picture.dynamicMove(dynamicMove);
                            picture.dynamicSize(dynamicSize);
                            picture.backColor(backColor);
                            picture.borderColor(borderColor);
                            picture.borderWidth(borderWidth);
                            picture.borderRadius(borderRadius);
                            picture.borderStyle(borderStyle);
                            picture.pictureStretch(stretch);
                        }
                    }
                }
                sheet.resumePaint();
                $(this).dialog("close");
            },
            Remove: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                sheet.suspendPaint ();
                var pictures = sheet.getPictures(),
                    pictureNames = [];
                for (var index = 0, len = pictures.length; index < len; index++) {
                    var picture = pictures[index];
                    if (picture && picture.isSelected()) {
                        pictureNames.push(picture.name());
                    }
                }
                for (var i = 0, len = pictureNames.length; i < len; i++) {
                    sheet.removePicture(pictureNames[i]);
                }
                sheet.resumePaint();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    // table
    $("#tabledialog").dialog({
        autoOpen: false,
        height: 350,
        width: 350,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                sheet.suspendPaint ();
                var table = null;
                var name = $("#tableName").val();
                var isCloseDialog = false;
                if (isTableAdd) {
                    if (name === "" || !name) {
                        $("#ckName").text("Need table name.");
                    } else if (name && name !== "" && sheet.findTableByName(name)) {
                        $("#ckName").text("Table name exists.");
                    } else {
                        isCloseDialog = true;
                        tableName = name;
                        try {
                            var cr = sheet.getSelections()[0];
                            if (cr) {
                                cr = getActualRange(cr, sheet.getRowCount(), sheet.getColumnCount());
                                table = sheet.addTable(tableName, cr.row, cr.col, cr.rowCount, cr.colCount, getTableStyle());
                                table.showHeader($("#showHeader").prop("checked"));
                                table.showFooter($("#showFooter").prop("checked"));
                                table.highlightFirstColumn($("#highlightFirstColumn").prop("checked"));
                                table.highlightLastColumn($("#highlightLastColumn").prop("checked"));
                                table.bandRows($("#bandRows").prop("checked"));
                                table.bandColumns($("#bandColumns").prop("checked"));
                            }
                        } catch (ex) {
                            alert(!!ex.message ? ex.message : ex);
                        }
                    }
                } else {
                    table = sheet.findTable(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                    if (name === "" || !name) {
                        $("#ckName").text("Need table name.");
                    } else if (name && name !== "" && name !== table.name() && sheet.findTableByName(name)) {
                        $("#ckName").text("Table name exists.");
                    } else {
                        isCloseDialog = true;
                        if (table) {
                            table.showHeader($("#showHeader").prop("checked"));
                            table.showFooter($("#showFooter").prop("checked"));
                            table.highlightFirstColumn($("#highlightFirstColumn").prop("checked"));
                            table.highlightLastColumn($("#highlightLastColumn").prop("checked"));
                            table.bandRows($("#bandRows").prop("checked"));
                            table.bandColumns($("#bandColumns").prop("checked"));
                            var style = getTableStyle();
                            if (style) {
                                table.style(style);
                            }
                            table.name($("#tableName").val());
                        }
                    }
                }

                sheet.resumePaint();
                if (isCloseDialog) {
                    $(this).dialog("close");
                }
            },
            Remove: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                try {
                    var table = sheet.findTable(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                    if (table) {
                        sheet.removeTable(table);
                    }
                } catch (ex) {
                    alert(!!ex.message ? ex.message : ex);
                }
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    //comment
    $("#commentdialog").dialog({
        autoOpen: false,
        height: 500,
        width: 420,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                sheet.suspendPaint ();
                var activeRowIndex = sheet.getActiveRowIndex(),
                    activeColumnIndex = sheet.getActiveColumnIndex();
                if (isCommentAdd) {
                    var comment = new $.wijmo.wijspread.Comment();
                    comment.text($("#txtText").val());
                    var location = $("#txtLocation").val();
                    if (location) {
                        var pos = location.split(",");
                        if (pos && pos.length === 2) {
                            comment.location(new $.wijmo.wijspread.Point(parseInt(pos[0]), parseInt(pos[1])));
                        }
                    }
                    comment.width(parseInt($("#txtWidth").val()));
                    comment.height(parseInt($("#txtHeight").val()));
                    sheet.setComment(activeRowIndex, activeColumnIndex, comment);
                } else {
                    var comment = sheet.getComment(activeRowIndex, activeColumnIndex);
                    comment.fontFamily($("#txtFontFamily").val());
                    comment.fontStyle($("#comboBoxFontStyle").val());
                    comment.fontSize($("#txtFontSize").val());
                    comment.fontWeight($("#txtFontWeight").val());
                    comment.borderWidth(parseInt($("#txtBorderWidth").val()));
                    comment.borderStyle($("#comboBoxBorderStyle").val());
                    comment.borderColor($("#txtBorderColor").val());
                    var padding = $("#txtPadding").val();
                    if (padding) {
                        var para = padding.split(",");
                        if (para.length === 1) {
                            comment.padding(new $.wijmo.wijspread.Padding(para[0]));
                        } else if (para.length === 4) {
                            comment.padding(new $.wijmo.wijspread.Padding(para[0], para[1], para[2], para[3]));
                        }
                    }
                    var textDecoration = $("#comboBoxTextDecoration").val();
                    switch (textDecoration.toLowerCase()) {
                        case "underline":
                            comment.textDecoration($.wijmo.wijspread.TextDecorationType.Underline);
                            break;
                        case "linethrough":
                            comment.textDecoration($.wijmo.wijspread.TextDecorationType.LineThrough);
                            break;
                        case "overline":
                            comment.textDecoration($.wijmo.wijspread.TextDecorationType.Overline);
                            break;
                        case "none":
                            comment.textDecoration($.wijmo.wijspread.TextDecorationType.None);
                            break;
                    }
                    comment.opacity(parseFloat($("#txtOpacity").val()) / 100);
                    comment.foreColor($("#txtForeColor").val());
                    comment.backColor($("#txtBackColor").val());
                    var horizontal = $("#comboBoxHorizontal").prop("value");
                    switch (horizontal) {
                        case "left":
                            comment.horizontalAlign($.wijmo.wijspread.HorizontalAlign.left);
                            break;
                        case "center":
                            comment.horizontalAlign($.wijmo.wijspread.HorizontalAlign.center);
                            break;
                        case "right":
                            comment.horizontalAlign($.wijmo.wijspread.HorizontalAlign.right);
                            break;
                        case "general":
                            comment.horizontalAlign($.wijmo.wijspread.HorizontalAlign.left);
                            break;
                    }
                    comment.displayMode(parseInt($("#comboBoxDisplayMode").val()));
                    comment.zIndex(parseInt($("#txtzIndex").val()));
                    comment.lockText($("#chkLockText").prop('checked'));
                    comment.dynamicMove($("#chkDynamicMove").prop('checked'));
                    comment.dynamicSize($("#chkDynamicSize").prop('checked'));
                    comment.showShadow($("#chkShowShadow").prop('checked'));
                }
                sheet.resumePaint();
                $(this).dialog("close");
            },
            Remove: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                var sel = sheet.getSelections()[0];
                if (sel) {
                    var cr = getActualRange(sel, sheet.getRowCount(), sheet.getColumnCount());
                    sheet.setComment(cr.row, cr.col, null);
                }
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    //sparklineEx
    $("#sparklineexdialog").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        resizable: false,
        buttons: {
            OK: function() {
                var spread = $("#spreadSheet").wijspread("spread");
                var sheet = spread.getActiveSheet();
                var sel = sheet.getSelections()[0],
                    direction = 0,
                    dataRangeStr = $("#dataRange").val(),
                    locationRangeStr = $("#locationRange").val(),
                    dataRange = stringToRange(dataRangeStr, sheet),
                    locationRange = stringToRange(locationRangeStr, sheet),
                    sparklineEx = $("#sparklineExType").val(),
                    postfix = "",
                    isIllegal = true;
                $("#dataRangeIllegal").text('');
                $("#locationRangeIllegal").text('');
                if (!dataRange) {
                    isIllegal = false;
                    $("#dataRangeIllegal").text('range is illegal!');
                }
                if (!locationRange) {
                    isIllegal = false;
                    $("#locationRangeIllegal").text('range is illegal!');
                }
                if (sel) {
                    var cr = getActualRange(sel, sheet.getRowCount(), sheet.getColumnCount());
                    var sparklineEx = $("#sparklineExType").val();
                    if (sparklineEx === 'LINESPARKLINE' || sparklineEx === 'COLUMNSPARKLINE' || sparklineEx === 'WINLOSSSPARKLINE') {
                        if (cr.rowCount === 1) {
                            direction = 1;
                        } else if (cr.colCount === 1) {
                            direction = 0;
                        } else {
                            $("#dataRangeIllegal").text('Range should be in a single row or column.');
                            isIllegal = false;
                        }
                    }
                }
                if (isIllegal) {
                    if (sparklineEx === 'LINESPARKLINE') {
                        postfix = ',' + direction;
                    } else if (sparklineEx === 'COLUMNSPARKLINE') {
                        postfix = ',' + direction;
                    } else if (sparklineEx === 'WINLOSSSPARKLINE') {
                        postfix = ',0' + direction;
                    }
                    var formulaStr = '=' + sparklineEx + '(' + dataRangeStr + postfix + ')';
                    sheet.setFormula(locationRange.row, locationRange.col, formulaStr);
                    $(this).dialog("close");
                }
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
    //ribbon
    $("#ribbon").wijribbon({
        click: function(e, cmd) {
            var spread = $("#spreadSheet").wijspread("spread");
            var sheet = spread.getActiveSheet();
            sheet.suspendPaint ();
            switch (cmd.commandName) {
                case "cut":
                    //$.wijmo.wijspread.SpreadActions.cut.call(sheet);
                    break;
                case "copy":
                    //$.wijmo.wijspread.SpreadActions.copy.call(sheet);
                    break;
                case "paste":
                    //$.wijmo.wijspread.SpreadActions.paste.call(sheet);
                    break;
                case "tabStop":
                    var sels = sheet.getSelections();
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount()),
                            isTabStop = sheet.getCell(sel.row, sel.col, $.wijmo.wijspread.SheetArea.viewport).tabStop();
                        if (isTabStop === false) {
                            isTabStop = true;
                        } else {
                            isTabStop = false;
                        }
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).tabStop(isTabStop);
                    }
                    break;
                case "wrapText":
                    var sels = sheet.getSelections();
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount()),
                            wordWrap = sheet.getCell(sel.row, sel.col, $.wijmo.wijspread.SheetArea.viewport).wordWrap();
                        if (wordWrap === true) {
                            wordWrap = false;
                        } else {
                            wordWrap = true;
                        }
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).wordWrap(wordWrap);
                    }
                    break;
                case "bold":
                case "italic":
                    // var styleEle = document.getElementById("colorSample");
                    // var font = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), $.wijmo.wijspread.SheetArea.viewport).font();
                    // if (font != undefined) {
                    //     styleEle.style.font = font;
                    // } else {
                    //     styleEle.style.font = "10pt Arial";
                    // }
                    // if (cmd.commandName == "bold") {
                    //     if (styleEle.style.fontWeight == "bold") {
                    //         styleEle.style.fontWeight = "";
                    //     } else {
                    //         styleEle.style.fontWeight = "bold";
                    //     }
                    // } else if (cmd.commandName == "italic") {
                    //     if (styleEle.style.fontStyle == "italic") {
                    //         styleEle.style.fontStyle = "";
                    //     } else {
                    //         styleEle.style.fontStyle = "italic";
                    //     }
                    // }
                    // var sels = sheet.getSelections();
                    // for (var n = 0; n < sels.length; n++) {
                    //     var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                    //     //sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).font(document.defaultView.getComputedStyle(styleEle, "").font);
                    //     sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).font(styleEle.style.font);
                    // }
                    break;
                case "borders":
                    $("#borderdialog").dialog("open");
                    break;
                case "backcolor":
                case "fontcolor":
                    $("#colordialog").dialog({
                        title: cmd.commandName == "backcolor" ? "Back Color" : "Fore Color"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "Underline":
                    var sels = sheet.getSelections(),
                        underline = $.wijmo.wijspread.TextDecorationType.Underline;
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount()),
                            textDecoration = sheet.getCell(sel.row, sel.col, $.wijmo.wijspread.SheetArea.viewport).textDecoration();
                        if ((textDecoration & underline) === underline) {
                            textDecoration = textDecoration - underline;
                        } else {
                            textDecoration = textDecoration | underline;
                        }
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).textDecoration(textDecoration);
                    }
                    break;
                case "Strikethrough":
                    var sels = sheet.getSelections(),
                        lineThrough = $.wijmo.wijspread.TextDecorationType.LineThrough;
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount()),
                            textDecoration = sheet.getCell(sel.row, sel.col, $.wijmo.wijspread.SheetArea.viewport).textDecoration();
                        if ((textDecoration & lineThrough) === lineThrough) {
                            textDecoration = textDecoration - lineThrough;
                        } else {
                            textDecoration = textDecoration | lineThrough;
                        }
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).textDecoration(textDecoration);
                    }
                    break;
                case "Overline":
                    var sels = sheet.getSelections(),
                        overline = $.wijmo.wijspread.TextDecorationType.Overline;
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount()),
                            textDecoration = sheet.getCell(sel.row, sel.col, $.wijmo.wijspread.SheetArea.viewport).textDecoration();
                        if ((textDecoration & overline) === overline) {
                            textDecoration = textDecoration - overline;
                        } else {
                            textDecoration = textDecoration | overline;
                        }
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).textDecoration(textDecoration);
                    }
                    break;
                case "merge":
                    var sels = sheet.getSelections();
                    var hasSpan = false;
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                        if (sheet.getSpans(sel, $.wijmo.wijspread.SheetArea.viewport).length > 0) {
                            for (var i = 0; i < sel.rowCount; i++) {
                                for (var j = 0; j < sel.colCount; j++) {
                                    sheet.removeSpan(i + sel.row, j + sel.col);
                                }
                            }
                            hasSpan = true;
                        }
                    }
                    if (!hasSpan) {
                        for (var n = 0; n < sels.length; n++) {
                            var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                            sheet.addSpan(sel.row, sel.col, sel.rowCount, sel.colCount);
                        }
                    }
                    break;
                case "justifyleft":
                case "justifycenter":
                case "justifyright":
                    var align = $.wijmo.wijspread.HorizontalAlign.left;
                    if (cmd.commandName == "justifycenter")
                        align = $.wijmo.wijspread.HorizontalAlign.center;
                    if (cmd.commandName == "justifyright")
                        align = $.wijmo.wijspread.HorizontalAlign.right;
                    var sels = sheet.getSelections();
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                        sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).hAlign(align);
                    }
                    break;
                case "increaseindent":
                case "decreaseindent":
                    var sels = sheet.getSelections();
                    var offset = 1;
                    if (cmd.commandName == "decreaseindent")
                        offset = -1;
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                        for (var i = 0; i < sel.rowCount; i++) {
                            for (var j = 0; j < sel.colCount; j++) {
                                var indent = sheet.getCell(i + sel.row, j + sel.col, $.wijmo.wijspread.SheetArea.viewport).textIndent();
                                if (isNaN(indent))
                                    indent = 0;
                                sheet.getCell(i + sel.row, j + sel.col, $.wijmo.wijspread.SheetArea.viewport).textIndent(indent + offset);
                            }
                        }
                    }
                    break;
                case "insertcol":
                    sheet.addColumns(sheet.getActiveColumnIndex(), 1);
                    break;
                case "insertrow":
                    sheet.addRows(sheet.getActiveRowIndex(), 1);
                    break;
                case "deleterow":
                    sheet.deleteRows(sheet.getActiveRowIndex(), 1);
                    break;
                case "deletecol":
                    sheet.deleteColumns(sheet.getActiveColumnIndex(), 1);
                    break;
                case "highlightcell":
                    $("#Rule1 option:eq(0)").attr("selected", "selected");
                    $("#Rule1").empty();
                    $("#Rule1").show();
                    $("#Rule1").append("<option value='0'>Cell Value</option>");
                    $("#Rule1").append("<option value='1'>Specific Text</option>");
                    $("#Rule1").append("<option value='2'>Date Occurring</option>");
                    $("#Rule1").append("<option value='5'>Unique</option>");
                    $("#Rule1").append("<option value='6'>Duplicate</option>");
                    var rule = "0";
                    var type = $("#ComparisonOperator1");
                    setEnumTypeOfCF(rule, type);
                    $("#conditionalformatdialog").dialog("open");
                    break;
                case "topbottom":
                    $("#Rule1 option:eq(1)").attr("selected", "selected");
                    $("#Rule1").empty();
                    $("#Rule1").show();
                    $("#Rule1").append("<option value='4'>Top10</option>");
                    $("#Rule1").append("<option value='7'>Average</option>");
                    var rule = "4";
                    var type = $("#ComparisonOperator1");
                    setEnumTypeOfCF(rule, type);
                    $("#conditionalformatdialog").dialog("open");
                    break;
                case "colorscale":
                    $("#Rule1 option:eq(2)").attr("selected", "selected");
                    $("#Rule1").empty();
                    $("#Rule1").show();
                    $("#Rule1").append("<option value='8'>2-Color Scale</option>");
                    $("#Rule1").append("<option value='9'>3-Color Scale</option>");
                    var rule = "8";
                    var type = $("#ComparisonOperator1");
                    setEnumTypeOfCF(rule, type);
                    $("#conditionalformatdialog").dialog("open");
                    break;
                case "otherofconditionalformat":
                    $("#Rule1 option:eq(3)").attr("selected", "selected");
                    $("#Rule1").empty();
                    $("#Rule1").show();
                    $("#Rule1").append("<option value='3'>FormulaRule</option>");
                    var rule = "3";
                    var type = $("#ComparisonOperator1");
                    setEnumTypeOfCF(rule, type);
                    $("#conditionalformatdialog").dialog("open");
                    break;
                case "databar":
                    $("#databardialog").dialog("open");
                    break;
                case "iconset":
                    createIconCriteriaDOM();
                    $("#iconsetdialog").dialog("open");
                    break;
                case "removeconditionalformats":
                    var cfs = sheet.getConditionalFormats();
                    var row = sheet.getActiveRowIndex(),
                        col = sheet.getActiveColumnIndex();
                    var rules = cfs.getRules(row, col);
                    $.each(rules, function(i, v) {
                        cfs.removeRule(v);
                    });
                    sheet.resumePaint();
                    break;
                case "table":
                    var table = sheet.findTable(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                    $("#ckName").text("");
                    if (!table) {
                        $("#showHeader").attr("checked", true);
                        $("#showFooter").attr("checked", false);
                        $("#highlightFirstColumn").attr("checked", false);
                        $("#highlightLastColumn").attr("checked", false);
                        $("#bandRows").attr("checked", true);
                        $("#bandColumns").attr("checked", false);
                        tableName = getTableName(sheet);
                        $("#tableName").val(tableName);
                        isTableAdd = true;
                        $("#tabledialog").dialog({
                            title: "Add Table"
                        });
                        $("#tabledialog").next().find("span").get(0).innerHTML = "Add";
                    } else {
                        $("#showHeader").attr("checked", table.showHeader());
                        $("#showFooter").attr("checked", table.showFooter());
                        $("#highlightFirstColumn").attr("checked", table.highlightFirstColumn());
                        $("#highlightLastColumn").attr("checked", table.highlightLastColumn());
                        $("#bandRows").attr("checked", table.bandRows());
                        $("#bandColumns").attr("checked", table.bandColumns());
                        var styleName = table.style().name();
                        $("#tableStyles option[value='" + styleName + "']").attr("selected", true);
                        $("#tableName").val(table.name());
                        isTableAdd = false;
                        $("#tabledialog").dialog({
                            title: "Design Table"
                        });
                        $("#tabledialog").next().find("span").get(0).innerHTML = "Update";
                    }
                    $("#tabledialog").dialog("open");
                    break;
                case "picture":
                    var pictures = sheet.getPictures(),
                        hasPictureSelected = false,
                        picture;
                    for (var index = 0, len = pictures.length; index < len; index++) {
                        var item = pictures[index];
                        if (item && item.isSelected()) {
                            hasPictureSelected = true;
                            picture = item;
                            break;
                        }
                    }
                    if (!hasPictureSelected) {
                        $("#picturedialog").dialog({
                            title: "Add Picture"
                        });
                        $("#picturedialog").next().find("span").get(0).innerHTML = "Add";
                        $("#pictureSettingsOptions").hide();
                        $("#pictureAddOptions").show();
                        isPictureAdd = true;
                    } else {
                        $("#picturedialog").dialog({
                            title: "Design Picture"
                        });
                        $("#picturedialog").next().find("span").get(0).innerHTML = "Update";
                        $("#pictureAddOptions").hide();
                        $("#pictureSettingsOptions").show();
                        $("#dynamicMove").attr("checked", picture.dynamicMove());
                        $("#dynamicSize").attr("checked", picture.dynamicSize());
                        $("#pictureBackColor").css("background", picture.backColor());
                        $("#pictureBorderColor").css("background", picture.borderColor());
                        $("#pictureBorderWidth").val(picture.borderWidth());
                        $("#pictureBorderRadius").val(picture.borderRadius());
                        var borderStyle = picture.borderStyle();
                        $("#pictureBorderStyle option[value='" + borderStyle + "']").attr("selected", true);
                        var pictureStretch = picture.pictureStretch();
                        $("#pictureStretch option[value='" + pictureStretch + "']").attr("selected", true);
                        isPictureAdd = false;
                    }
                    $("#picturedialog").dialog("open");
                    break;
                case "sortaz":
                case "sortza":
                    var sels = sheet.getSelections();
                    for (var n = 0; n < sels.length; n++) {
                        var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                        sheet.sortRange(sel.row, sel.col, sel.rowCount, sel.colCount, true, [{
                            index: sel.col,
                            ascending: cmd.commandName == "sortaz"
                        }]);
                    }
                    break;
                case "filter":
                    if (sheet.rowFilter()) {
                        sheet.rowFilter(null);
                    } else {
                        var sels = sheet.getSelections();
                        if (sels.length > 0) {
                            var sel = sels[0];
                            sheet.rowFilter(new $.wijmo.wijspread.HideRowFilter(sel));
                        }
                    }
                    break;
                case "group":
                    var sels = sheet.getSelections();
                    var sel = sels[0];

                    if (sel.col == -1) // row selection
                    {
                        var groupExtent = new $.wijmo.wijspread.UndoRedo.GroupExtent(sel.row, sel.rowCount);
                        var action = new $.wijmo.wijspread.UndoRedo.RowGroupUndoAction(sheet, groupExtent);
                        spread.doCommand(action);
                        $("#showRowGroup").attr("disabled", false);
                        if ($("#showRowGroup").attr("value") == "true") {
                            $("#showRowGroupLabel").unbind("mouseup", rowlabelnoactive);
                        }
                        $("#showRowGroup").attr("value", "true");
                        $("#showRowGroupLabel").attr("disabled", false);
                        $("#showRowGroupLabel").removeClass("ui-state-disabled");
                        $("#showRowGroupLabel").unbind("mouseup", rowlabelactive);
                        sheet.showRowRangeGroup(true);
                    } else if (sel.row == -1) // column selection
                    {
                        var groupExtent = new $.wijmo.wijspread.UndoRedo.GroupExtent(sel.col, sel.colCount);
                        var action = new $.wijmo.wijspread.UndoRedo.ColumnGroupUndoAction(sheet, groupExtent);
                        spread.doCommand(action);
                        $("#showColGroup").attr("disabled", false);
                        if ($("#showColGroup").attr("value") == "true") {
                            $("#showColGroupLabel").unbind("mouseup", collabelnoactive);
                        }
                        $("#showColGroup").attr("value", "true");
                        $("#showColGroupLabel").attr("disabled", false);
                        $("#showColGroupLabel").removeClass("ui-state-disabled");
                        $("#showColGroupLabel").unbind("mouseup", collabelactive);
                        sheet.showColumnRangeGroup(true);
                    } else // cell range selection
                    {
                        alert("please select a range of row or col");
                    }
                    break;
                case "ungroup":
                    var sels = sheet.getSelections();
                    var sel = sels[0];

                    if (sel.col == -1 && sel.row == -1) // sheet selection
                    {
                        sheet.rowRangeGroup.ungroup(0, sheet.getRowCount());
                        sheet.colRangeGroup.ungroup(0, sheet.getColumnCount());
                    } else if (sel.col == -1) // row selection
                    {
                        //sheet.rowRangeGroup.ungroup(sel.row, sel.rowCount);
                        var groupExtent = new $.wijmo.wijspread.UndoRedo.GroupExtent(sel.row, sel.rowCount);
                        var action = new $.wijmo.wijspread.UndoRedo.RowUngroupUndoAction(sheet, groupExtent);
                        spread.doCommand(action);
                        if (sheet.rowRangeGroup.getMaxLevel() < 0) {
                            $("#showRowGroup").attr("disabled", true);
                            $("#showRowGroup").attr("value", "true");
                            $("#showRowGroupLabel").attr("disabled", true);
                            $("#showRowGroupLabel").addClass("ui-state-disabled");
                            $("#showRowGroupLabel").bind("mouseup", rowlabelnoactive);
                        }
                    } else if (sel.row == -1) // column selection
                    {
                        //sheet.colRangeGroup.ungroup(sel.col, sel.colCount);
                        var groupExtent = new $.wijmo.wijspread.UndoRedo.GroupExtent(sel.col, sel.colCount);
                        var action = new $.wijmo.wijspread.UndoRedo.ColumnUngroupUndoAction(sheet, groupExtent);
                        spread.doCommand(action);
                        if (sheet.colRangeGroup.getMaxLevel() < 0) {
                            $("#showColGroup").attr("disabled", true);
                            $("#showColGroup").attr("value", "true");
                            $("#showColGroupLabel").attr("disabled", true);
                            $("#showColGroupLabel").addClass("ui-state-disabled");
                            $("#showColGroupLabel").bind("mouseup", collabelnoactive);
                        }
                    } else // cell range selection
                    {
                        alert("please select a range of row or col");
                    }
                    break;
                case "showdetail":
                case "hidedetail":
                    var sels = sheet.getSelections();
                    var sel = sels[0];

                    if (sel.col == -1 && sel.row == -1) // sheet selection
                    {} else if (sel.col == -1) // row selection
                    {
                        for (var i = 0; i < sel.rowCount; i++) {
                            var rgi = sheet.rowRangeGroup.find(sel.row + i, 0);
                            if (rgi) {
                                sheet.rowRangeGroup.expand(rgi.level, cmd.commandName == "showdetail");
                            }
                        }
                    } else if (sel.row == -1) // column selection
                    {
                        for (var i = 0; i < sel.colCount; i++) {
                            var rgi = sheet.colRangeGroup.find(sel.col + i, 0);
                            if (rgi) {
                                sheet.colRangeGroup.expand(rgi.level, cmd.commandName == "showdetail");
                            }
                        }
                    } else // cell range selection
                    {}
                    break;
                case "showrowrangegroup":
                    if (!$("#showRowGroup").attr("disabled")) {
                        sheet.showRowRangeGroup($("#showRowGroup").prop("checked"));
                    }
                    break;
                case "showcolrangegroup":
                    if (!$("#showColGroup").attr("disabled")) {
                        sheet.showColumnRangeGroup($("#showColGroup").prop("checked"));
                    }
                    break;
                case "datavalidation":
                    $("#datavalidationdialog").dialog("open");
                    break;
                case "circleinvaliddata":
                    spread.options.highlightInvalidData = (true);
                    break;
                case "clearvalidationcircles":
                    spread.options.highlightInvalidData = (false);
                    break;
                case "textboxcelltype":
                    $("#comboCellOptions").hide();
                    $("#checkBoxCellOptions").hide();
                    $("#buttonCellOptions").hide();
                    $("#hyperlinkCellOptions").hide();
                    var cellType = new $.wijmo.wijspread.TextCellType();
                    sheet.suspendEvent();
                    var sels = sheet.getSelections();
                    for (var i = 0; i < sels.length; i++) {
                        var sel = getActualCellRange(sels[i], sheet.getRowCount(), sheet.getColumnCount());
                        for (var r = 0; r < sel.rowCount; r++) {
                            for (var c = 0; c < sel.colCount; c++) {
                                sheet.setCellType(sel.row + r, sel.col + c, cellType, $.wijmo.wijspread.SheetArea.viewport);
                            }
                        }
                    }
                    sheet.resumeEvent();
                    break;
                case "comboboxcelltype":
                    $("#comboCellOptions").show();
                    $("#checkBoxCellOptions").hide();
                    $("#buttonCellOptions").hide();
                    $("#hyperlinkCellOptions").hide();
                    $("#celltypedialog").dialog("open");
                    selCellType = "ComboCell";
                    break;
                case "checkboxcelltype":
                    $("#comboCellOptions").hide();
                    $("#checkBoxCellOptions").show();
                    $("#buttonCellOptions").hide();
                    $("#hyperlinkCellOptions").hide();
                    $("#celltypedialog").dialog("open");
                    selCellType = "CheckBoxCell";
                    break;
                case "buttoncelltype":
                    $("#comboCellOptions").hide();
                    $("#checkBoxCellOptions").hide();
                    $("#buttonCellOptions").show();
                    $("#hyperlinkCellOptions").hide();
                    $("#celltypedialog").dialog("open");
                    selCellType = "ButtonCell";
                    break;
                case "hyperlinkcelltype":
                    $("#comboCellOptions").hide();
                    $("#checkBoxCellOptions").hide();
                    $("#buttonCellOptions").hide();
                    $("#hyperlinkCellOptions").show();
                    $("#celltypedialog").dialog("open");
                    selCellType = "HyperLinkCell";
                    break;
                case "alloweditorreservedlocations":
                    sheet.allowEditorReservedLocations($("#alleditorreserved").attr("checked") === "checked" ? true : false);
                    break;
                case "ClearCellType":
                    var sels = sheet.getSelections();
                    for (var i = 0; i < sels.length; i++) {
                        var sel = getActualCellRange(sels[i], sheet.getRowCount(), sheet.getColumnCount());
                        sheet.clear(sel.row, sel.col, sel.rowCount, sel.colCount, $.wijmo.wijspread.SheetArea.viewport, $.wijmo.wijspread.StorageType.Style);
                    }
                    break;
                case "comment":
                    var comment = sheet.getComment(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                    if (!comment) {
                        $("#commentdialog").dialog({
                            title: "Add Comment"
                        });
                        $("#commentdialog").next().find("span").get(0).innerHTML = "Add";
                        $("#commentSettingsOptions").hide();
                        $("#commentAddOptions").show();
                        isCommentAdd = true;
                    } else {
                        $("#commentdialog").dialog({
                            title: "Design Comment"
                        });
                        $("#commentdialog").next().find("span").get(0).innerHTML = "Update";
                        $("#commentAddOptions").hide();
                        $("#commentSettingsOptions").show();
                        $("#txtFontFamily").val(comment.fontFamily());
                        $("#comboBoxFontStyle").val(comment.fontStyle());
                        $("#txtFontSize").val(comment.fontSize());
                        $("#txtFontWeight").val(comment.fontWeight());
                        $("#txtBorderWidth").val(comment.borderWidth());
                        $("#comboBoxBorderStyle").val(comment.borderStyle());
                        $("#txtBorderColor").val(comment.borderColor());
                        var padding = comment.padding();
                        if (padding) {
                            $("#txtPadding").val(padding.left + ',' + padding.top + ',' + padding.right + ',' + padding.bottom);
                        } else {
                            $("#txtPadding").val(0);
                        }
                        $("#comboBoxTextDecoration").val(comment.textDecoration());
                        $("#txtOpacity").val(comment.opacity() * 100);
                        $("#txtForeColor").val(comment.foreColor());
                        $("#txtBackColor").val(comment.backColor());
                        $("#comboBoxHorizontal").val(comment.horizontalAlign());
                        $("#comboBoxDisplayMode").val(comment.displayMode());
                        $("#txtzIndex").val(comment.zIndex());
                        $("#chkLockText").val(comment.lockText());
                        $("#chkDynamicMove").prop('checked', comment.dynamicMove());
                        $("#chkDynamicSize").prop('checked', comment.dynamicSize());
                        $("#chkShowShadow").prop('checked', comment.showShadow());
                        isCommentAdd = false;
                    }
                    $("#commentdialog").dialog("open");
                    break;
                case "sparklineex":
                    var sel = sheet.getSelections()[0];
                    if (sel) {
                        var cr = getActualRange(sel, sheet.getRowCount(), sheet.getColumnCount());
                        if (cr) {
                            $("#dataRange").val(rangeToString(cr, sheet));
                        }
                    }
                    $("#sparklineexdialog").dialog("open");
                    break;
                case "rowheader":
//                    sheet.setRowHeaderVisible(!sheet.getRowHeaderVisible());
                	sheet.options.rowHeadervisible = !sheet.getRowHeaderVisible();
                    break;
                case "columnheader":
//                    sheet.setColumnHeaderVisible(!sheet.getColumnHeaderVisible());
                    sheet.options.colHeaderVisible = !sheet.getColumnHeaderVisible();
                    break;
                case "vgridline":
                    var vGridLine = sheet.gridline.showVerticalGridline;
                    var hGridLine = sheet.gridline.showHorizontalGridline;
//                    sheet.setGridlineOptions({
//                        showVerticalGridline: !vGridLine,
//                        showHorizontalGridline: hGridLine
//                    });
                    sheet.options.gridline = {showHorizontalGridline:!vGridLine,showVerticalGridline:hGridLine};
                    break;
                case "hgridline":
                    var hGridLine = sheet.gridline.showHorizontalGridline;
                    var vGridLine = sheet.gridline.showVerticalGridline;
//                    sheet.setGridlineOptions({
//                        showVerticalGridline: vGridLine,
//                        showHorizontalGridline: !hGridLine
//                    });
                    sheet.options.gridline = {showHorizontalGridline:vGridLine,showVerticalGridline:!hGridLine};
                    break;
                case "tabstrip":
                    spread.options.tabStripVisible = !spread.tabStripVisible();
                    break;
                case "newtab":
                    spread.options.newTabVisible = !spread.newTabVisible();
                    break;
                case "showhorizontalscrollbar":
                    var hScrollbar = spread.showHorizontalScrollbar();
                    spread.showHorizontalScrollbar(!hScrollbar);
                    break;
                case "showverticalscrollbar":
                    var vScrollbar = spread.showVerticalScrollbar();
                    spread.showVerticalScrollbar(!vScrollbar);
                    break;
                case "freezepane":
                    sheet.setFrozenRowCount(sheet.getActiveRowIndex());
                    sheet.setFrozenColumnCount(sheet.getActiveColumnIndex());
                    break;
                case "unfreeze":
                    sheet.setFrozenRowCount(0);
                    sheet.setFrozenColumnCount(0);
                    sheet.setFrozenTrailingRowCount(0);
                    sheet.setFrozenTrailingColumnCount(0);
                    break;
                case "frozenlinecolor":
                    $("#colordialog").dialog({
                        title: "FrozenLineColor"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "freezesetting":
                    $("#freezesettingdialog").dialog("open");
                    break;
                case "enableUseWijmoTheme":
                    if ($("#wijmothemecheck").prop("checked")) {
                        $("#setTabStripColor").attr("disabled", false);
                        $("#setTabStripColor").removeClass("ui-state-disabled");
                        spread.useWijmoTheme = false;
                        spread.repaint();
                    } else {
                        $("#setTabStripColor").attr("disabled", true);
                        $("#setTabStripColor").addClass("ui-state-disabled");
                        spread.useWijmoTheme = true;
                        spread.repaint();
                    }
                    break;
                case "tabStripColor":
                    $("#colordialog").dialog({
                        title: "Tab Strip Color"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "spreadbackcolor":
                    $("#colordialog").dialog({
                        title: "SpreadBackColor"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "grayareabackcolor":
                    $("#colordialog").dialog({
                        title: "GrayAreaBackColor"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "selectionbordercolor":
                    $("#colordialog").dialog({
                        title: "SelectionBorderColor"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "selectionbackcolor":
                    $("#colordialog").dialog({
                        title: "SelectionBackColor"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "isr1c1":
                    var result = $("#isR1C1").prop("checked");
                    if (result) {
                        spread.referenceStyle($.wijmo.wijspread.ReferenceStyle.R1C1);
                    } else {
                        spread.referenceStyle($.wijmo.wijspread.ReferenceStyle.A1);
                    }
                    break;
                case "canuserdragdrop":
                    var result = $("#canUserDragDrop").prop("checked");
                    sheet.canUserDragDrop(result);
                    break;
                case "canuserdragfill":
                    var result = $("#canUserDragFill").prop("checked");
                    sheet.canUserDragFill(result);
                    break;
                case "canusereditformula":
                    var result = $("#canUserEditFormula").prop("checked");
                    spread.canUserEditFormula(result);
                    break;
                case "showdragdroptip":
                    var dragDrop = $("#showDragDropTip").prop("checked");
                    spread.showDragDropTip(dragDrop);
                    break;
                case "showdragfilltip":
                    var dragFill = $("#showDragFillTip").prop("checked");
                    spread.showDragFillTip(dragFill);
                    break;
                case "autofitwithheadertext":
                    var autoFit = $("#autoFitWithHeaderText").prop("checked") ? $.wijmo.wijspread.AutoFitType.CellWithHeader : $.wijmo.wijspread.AutoFitType.Cell;
                    spread.autoFitType(autoFit);
                    break;
                case "allowsheetreorder":
                    var isAllow = $("#allowSheetReorder").prop("checked");
                    spread.allowSheetReorder(isAllow);
                    break;
                case "sheetVisible":
                    var sheetName = $("#sheetList").val();
                    if (sheetName && sheetName !== '') {
                        var visibleSheet = spread.getSheetFromName(sheetName);
                        if (visibleSheet && typeof(visibleSheet.visible) === 'function') {
                            var visible = visibleSheet.visible();
                            visibleSheet.visible(!visible);
                        }
                    }
                    break;
                case "cutcopyindicatorvisible":
                    var isVisible = $("#cutCopyIndicatorVisible").prop("checked");
                    spread.cutCopyIndicatorVisible(isVisible);
                    break;
                case "usetouchlayout":
                    var isUseTouch = $("#useTouchLayout").prop("checked");
                    spread.useTouchLayout(isUseTouch);
                    break;
                case "cutcopyindicatorcolor":
                    $("#colordialog").dialog({
                        title: "Indicator Border Color"
                    });
                    $("#colordialog").dialog("open");
                    break;
                case "importexcel":
                    excelImport();
                    break;
                case "exportexcel":
                    excelExport(spread);
                    break;
                default:
                    if (cmd.commandName.substr(0, 1) == "f") {
                        var styleEle = document.getElementById("colorSample");
                        var font = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), $.wijmo.wijspread.SheetArea.viewport).font();
                        if (font) {
                            styleEle.style.font = font;
                        } else {
                            styleEle.style.font = "10pt Arial";
                        }

                        if (cmd.commandName.substr(0, 2) == "fn")
                            styleEle.style.fontFamily = document.getElementById(cmd.commandName)["title"];
                        if (cmd.commandName.substr(0, 2) == "fs")
                            styleEle.style.fontSize = document.getElementById(cmd.commandName)["title"];
                        var sels = sheet.getSelections();
                        for (var n = 0; n < sels.length; n++) {
                            var sel = getActualCellRange(sels[n], sheet.getRowCount(), sheet.getColumnCount());
                            //sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).font(document.defaultView.getComputedStyle(styleEle, "").font);
                            sheet.getCells(sel.row, sel.col, sel.row + sel.rowCount - 1, sel.col + sel.colCount - 1, $.wijmo.wijspread.SheetArea.viewport).font(styleEle.style.font);
                        }
                    } else if (cmd.commandName.substr(0, 1) == "s") {
                        if (cmd.commandName.substr(0, 2) == "sp") {
                            var policy = 2;
                            if (document.getElementById(cmd.commandName)["title"] == "Single") {
                                policy = 0;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Range") {
                                policy = 1;
                            } else if (document.getElementById(cmd.commandName)["title"] == "MultiRange") {
                                policy = 2;
                            }
                            sheet.selectionPolicy(policy);
                        }
                        if (cmd.commandName.substr(0, 2) == "su") {
                            var unit = 0;
                            if (document.getElementById(cmd.commandName)["title"] == "Cell") {
                                unit = 0;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Row") {
                                unit = 1;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Column") {
                                unit = 2;
                            }
                            sheet.selectionUnit(unit);
                        }
                    } else if (cmd.commandName.substr(0, 1) == "t") {
                        if (cmd.commandName.substr(0, 2) == "ts") {
                            var scrollTip = 2;
                            if (document.getElementById(cmd.commandName)["title"] == "None") {
                                scrollTip = 0;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Horizontal") {
                                scrollTip = 1;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Vertical") {
                                scrollTip = 2;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Both") {
                                scrollTip = 3;
                            }
                            spread.showScrollTip(scrollTip);
                        }
                        if (cmd.commandName.substr(0, 2) == "tr") {
                            var resizeTip = 0;
                            if (document.getElementById(cmd.commandName)["title"] == "None") {
                                resizeTip = 0;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Column") {
                                resizeTip = 1;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Row") {
                                resizeTip = 2;
                            } else if (document.getElementById(cmd.commandName)["title"] == "Both") {
                                resizeTip = 3;
                            }
                            spread.showResizeTip(resizeTip);
                        }
                    } else {
                        alert(cmd.commandName);
                    }

                    break;
            }
            sheet.resumePaint();
        },
        create: function(e, ui) {
            changeIcons();
            $(".wijmo-wijribbon-group-label").hide();
        }
    });

    //Initalize spread
    // $("#spreadSheet").wijspread({
    //     sheetCount: 2
    // }); // create wijspread control
    // if ($.browser.msie && parseInt($.browser.version, 10) < 9) {
    //     //run for ie7/8
    //     var spread = $("#spreadSheet").wijspread("spread");
    //     spread.bind("SpreadsheetObjectLoaded", function() {
    //         initSpread();
    //     });
    // } else {
    //     initSpread();
    // }
    // heightflag = $(document).width();
    // screenAdption();
    // $(document).bind("DOMSubtreeModified", function() {
    //     if ($(document).width() != heightflag) {
    //         heightflag = $(document).width();
    //         screenAdption();
    //     }
    // });
    // //context menu
    // var sheetArea = $.wijmo.wijspread.SheetArea.viewport,
    //     isHideContextMenu = false;
    // $("#spreadSheet").mouseup(function(e) {
    //     // hide context menu when the mouse down on SpreadJS
    //     if (e.button !== 2) {
    //         $("#contextMenu").wijmenu("hideAllMenus");
    //     }
    // });
    // $("#spreadSheet").bind('contextmenu', function(e) {
    //     // move the context menu to the position of the mouse point
    //     var spread = $("#spreadSheet").wijspread("spread");
    //     var sheet = spread.getActiveSheet();
    //     var target = getHitTest(e.pageX, e.pageY, sheet);
    //     isHideContextMenu = false;
    //     sheetArea = target.hitTestType;
    //     if (target.hitTestType === $.wijmo.wijspread.SheetArea.colHeader) {
    //         if (sheet.getSelections().find(target.row, target.col) === null) {
    //             sheet.setSelection(-1, target.col, sheet.getRowCount(), 1);
    //         }
    //         if (target.row !== undefined && target.col !== undefined) {
    //             $(".context-header").show();
    //             $(".context-cell").show();
    //             showMergeContextMenu(sheet);
    //         }
    //     } else if (target.hitTestType === $.wijmo.wijspread.SheetArea.rowHeader) {
    //         if (sheet.getSelections().find(target.row, target.col) === null) {
    //             sheet.setSelection(target.row, -1, 1, sheet.getColumnCount());
    //         }
    //         if (target.row !== undefined && target.col !== undefined) {
    //             $(".context-header").show();
    //             $(".context-cell").show();
    //             showMergeContextMenu(sheet);
    //         }
    //     } else if (target.hitTestType === $.wijmo.wijspread.SheetArea.viewport) {
    //         if (sheet.getSelections().find(target.row, target.col) === null) {
    //             sheet.clearSelection();
    //             sheet.endEdit();
    //             sheet.setActiveCell(target.row, target.col);
    //         }
    //         if (target.row !== undefined && target.col !== undefined) {
    //             $(".context-header").hide();
    //             $(".context-cell").show();
    //             showMergeContextMenu(sheet);
    //         } else {
    //             isHideContextMenu = true;
    //         }
    //     } else if (target.hitTestType === $.wijmo.wijspread.SheetArea.corner) {
    //         sheet.setSelection(-1, -1, sheet.getRowCount(), sheet.getColumnCount());
    //         if (target.row !== undefined && target.col !== undefined) {
    //             $(".context-header").hide();
    //             $(".context-cell").show();
    //         }
    //     }
    //     $("#contextMenu").wijmenu("option", "position", {
    //         my: "left top",
    //         of: e
    //     });
    //     return false;
    // });
    $("#contextMenu").wijmenu({
        trigger: "#spreadSheet",
        triggerEvent: "rtclick",
        orientation: "vertical",
        showAnimation: {
            animated: 'slide',
            duration: 0,
            easing: null
        },
        hideAnimation: {
            animated: 'slide',
            duration: 0,
            easing: null
        },
        showing: function(e, item) {
            if (isHideContextMenu) {
                return false;
            } else {
                return true;
            }
        },
        select: function(e, data) {
            var options = data.item.options;
            var spread = $("#spreadSheet").wijspread("spread");
            var sheet = spread.getActiveSheet();
            switch (options.text) {
                case "Cut":
                    $.wijmo.wijspread.SpreadActions.cut.call(sheet);
                    break;
                case "Copy":
                    $.wijmo.wijspread.SpreadActions.copy.call(sheet);
                    break;
                case "Paste":
                    $.wijmo.wijspread.SpreadActions.paste.call(sheet);
                    break;
                case "Insert":
                    if (sheetArea === $.wijmo.wijspread.SheetArea.colHeader) {
                        sheet.addColumns(sheet.getActiveColumnIndex(), 1);
                    } else if (sheetArea === $.wijmo.wijspread.SheetArea.rowHeader) {
                        sheet.addRows(sheet.getActiveRowIndex(), 1);
                    }
                    break;
                case "Delete":
                    if (sheetArea === $.wijmo.wijspread.SheetArea.colHeader) {
                        sheet.deleteColumns(sheet.getActiveColumnIndex(), 1);
                    } else if (sheetArea === $.wijmo.wijspread.SheetArea.rowHeader) {
                        sheet.deleteRows(sheet.getActiveRowIndex(), 1);
                    }
                    break;
                case "Merge":
                    var sel = sheet.getSelections();
                    if (sel.length > 0) {
                        sel = sel[sel.length - 1];
                        sheet.addSpan(sel.row, sel.col, sel.rowCount, sel.colCount, $.wijmo.wijspread.SheetArea.viewport);
                    }
                    break;
                case "UnMerge":
                    var sels = sheet.getSelections();
                    for (var i = 0; i < sels.length; i++) {
                        var sel = getActualCellRange(sels[i], sheet.getRowCount(), sheet.getColumnCount());
                        for (var r = 0; r < sel.rowCount; r++) {
                            for (var c = 0; c < sel.colCount; c++) {
                                var span = sheet.getSpan(r + sel.row, c + sel.col, $.wijmo.wijspread.SheetArea.viewport);
                                if (span) {
                                    sheet.removeSpan(span.row, span.col, $.wijmo.wijspread.SheetArea.viewport);
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    });
    //excel i/o
    $.support.cors = true;
    $("#importBtn").attr("disabled", true);
    $("#importBtn").addClass("ui-state-disabled");
    $("#serviceUrl").change(function() {
        if ($("#serviceUrl").val() !== '' && $("#import_excel_file").val() !== '') {
            $("#importBtn").attr("disabled", false);
            $("#importBtn").removeClass("ui-state-disabled");
        } else {
            $("#importBtn").attr("disabled", true);
            $("#importBtn").addClass("ui-state-disabled");
        }
    });
    $("#import_excel_file").change(function() {
        if ($("#serviceUrl").val() !== '' && $("#import_excel_file").val() !== '') {
            $("#importBtn").attr("disabled", false);
            $("#importBtn").removeClass("ui-state-disabled");
        } else {
            $("#importBtn").attr("disabled", true);
            $("#importBtn").addClass("ui-state-disabled");
        }
    });
});