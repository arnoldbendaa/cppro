    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
    
<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="utf-8" />
<title>1/1 Forecast</title>
<link rel="icon" type="image/png" href="./favicon.ico" />
<link rel="stylesheet" href="./css/bootstrap.min.css" />
<link rel="stylesheet" href="./css/bootstrap-theme.min.css" />

<!--SpreadJS Css-->
<link rel="stylesheet"
	href="./css/gc.spread.sheets.excel2013white.10.0.0.css"
	title="spread-theme" />

<link rel="stylesheet" href="./css/inspector.css" />
<link rel="stylesheet" href="./css/insp-table-format.css" />
<link rel="stylesheet" href="./css/insp-slicer-format.css" />
<link rel="stylesheet" href="./css/colorpicker.css" />
<link rel="stylesheet" href="./css/borderpicker.css" />
<link rel="stylesheet" href="./css/sample.css" />
	<link href="./bower_components/components-font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="./fileMenu/fileMenu.css" rel="stylesheet" />
<link href="./css/excel2013.css" rel="stylesheet" />
<link href="./css/common.css" rel="stylesheet" />

<!--jQuery References-->
<script type="text/javascript" src="./scripts/jquery-1.11.1.min.js"
	onload="if (typeof (module) === 'object') { window.jQuery = window.$ = module.exports; }"></script>
<script type="text/javascript"
	src="./scripts/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="./scripts/bootstrap.min.js"></script>

<script type="text/javascript"
	src="./scripts/gc.spread.sheets.all.10.0.0.min.js"></script>
<script type="text/javascript"
	src="./scripts/gc.spread.excelio.10.0.0.min.js"></script>
<script type="text/javascript"
	src="./scripts/gc.spread.sheets.print.10.0.0.min.js"></script>
<script type="text/javascript" src="./scripts/license.js"></script>
<script type="text/javascript" src="./scripts/FileSaver.min.js"></script>
<script type="text/javascript" src="./scripts/resources.js"></script>
<script type="text/javascript" src="./scripts/ribbon-data.js"></script>
<script type="text/javascript" src="./scripts/ribbon.js"></script>
<script src="./fileMenu/fileMenu.js"></script>
<script src="./scripts/colorPicker.js"></script>
<script src="./scripts/bootstrap-waitingfor.min.js"></script>
<script type="text/javascript" src="./scripts/spreadActions.js"></script>
<script type="text/javascript" src="./scripts/sample.js"></script>
<script src="./scripts/highcharts.js"></script>
<script src="./scripts/highcharts-3d.js"></script>
</head>
<body class="unselectable">
	<div class="toolbar" id="toolbar"></div>
	<div class="content-container">
		<div id="inner-content-container">
			<table id="formulaBar" style="width: 100%;">
				<tbody>
					<tr>
						<td style="vertical-align: top;"><span id="positionbox"></span>
						</td>
						<td style="width: 100%; border-left: 1px solid #ccc;">
							<div id="formulabox" contenteditable="true" spellcheck="false"
								style="overflow: hidden; height: 36px; width: 100%; padding: 9px;"></div>
							<div class="vertical-splitter ui-draggable" id="verticalSplitter"></div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="spread-container" id="controlPanel"
				style="height: 600px; bottom: 0;">
				<div class="timer text-right color-white  bg-primary">
					Session timeout: 119 min | <a class="logout color-white" href="${base_url}logout">logout</a> | <a class="logout color-white" href="${base_url}home">To Home</a>
				</div>
				<div id="container-navbar" role="navigation">
					<div class="row bg-primary">
						<div class="col-md-12 btn-toolbar" role="toolbar">
							<div class="btn-group">
								<button type="button" class="btn btn-default btn-sm" ng-click="openNoteManager()" ng-disabled="isManageNotesDisabled">
									<i class="fa fa-comments-o"></i> Manage Notes
								</button>
								<button type="button" class="btn btn-default btn-sm" onclick="exportPptFile()" ng-disabled="!(isRecalculateDisabled || !isSpreadWasChanged) || isExportDisabled">
									<i id="excelExport" class="fa fa-upload"></i> Export to PPt
								</button>
							</div>

							<div class="btn-group">
								<button type="button" class="btn btn-default btn-sm" title="Ctrl + Z" ng-click="undo()" ng-disabled="isUndoDisabled">
									<i class="fa fa-arrow-left"></i> Undo
								</button>
								<button type="button" class="btn btn-default btn-sm" title="Ctrl + Y" ng-click="redo()" ng-disabled="isRedoDisabled">
									<i class="fa fa-arrow-right"></i> Redo
								</button>
								<button type="button" class="btn btn-default btn-sm btn-save" onclick="saveToPng()">
									<i class="fa fa-save"></i> Save
								</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-default btn-sm" ng-click="showHideHeadings()">
									<i class="fa fa-table"></i> Hide headings
								</button>
							</div>
							<div class="btn-group">
								<select class="btn btn-default btn-sm" id="chartType">
									<option value="line">Line Chart</option>
									<option value="column">Column Chart</option>
									<option value="area">Area Chart</option>
									<option value="spline">Spline Chart</option>
									<option value="pie">Pie Chart</option>
								</select>
							</div>
							<div class="btn-group">
								<select class="btn btn-default btn-sm" id="dimention">
									<option value="2d">2D Graph</option>
									<option value="3d">3D Graph</option>
								</select>
							</div>
							
							<div class="dropdown pull-right" ng-controller="ProfilesController">
								<select class="form-control" id="activeProfieSelectBox"
										onchange="changeFile()"
										ng-model="selectedProfile"
										role="fileNameLists">
										<option value="">Please select</option>
								     <c:forEach items="${fileNameLists}" var="fileName" varStatus="status">
										<option value="${fileName}">${fileName.split("---")[0]}</option>
								     </c:forEach>
								</select>
							</div>
						</div>
					</div>
				
					<div class="row bg-muted" style="padding:10px">
						<div class="col-md-10">
							<span>Forecast - Group / </span>
							<SPAN><a href="#">ZZ-ZZZ- -Finance Only</a>/</SPAN>
							<span><a href="#">5-9605 - Dividends - External </a>/</span>
							<span><a href="#">/2013/2 - February</a>/</span>
							<span><a href="#">AQ</a></span>						
						</div>
						<div class="col-md-2 text-right">
							Sum:0
						</div>
					</div>
					
				</div>

				<div id="ss" style="height: 50% ; border: 1px solid #ddd;"></div>
				<div id="container"
					style="min-width: 310px; height: 50%; margin: 0 auto"></div>

			</div>
		</div>
		<div class="setting-pane setting-container" id="setting-pane">
			<div class="pane-header">
				<button type="button" class="close" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="pane-title">Title</h4>
			</div>
			<div class="pane-content">
				<div id="borderSetting" class="hidden move-right">
					<div class="pane-row border-line-style">
						<label class="pane-label narrow-width localize">@cellTab.border.rangeBorderLine@</label>
						<div class="btn-group" data-name="border-line-style">
							<button type="button"
								class="btn btn-default dropdown-toggle btn-pane-dropdown border-line-style"
								data-toggle="dropdown" aria-expanded="false">
								<span class="line-style-thin"></span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu line-style">
								<li data-value="none"><a class="localize">@cellTab.border.noBorder@</a></li>
								<li data-value="hair"><a>
										<div class="line-style-hair"></div>
								</a></li>
								<li data-value="dotted"><a>
										<div class="line-style-dotted"></div>
								</a></li>
								<li data-value="dash-dot-dot"><a>
										<div class="line-style-dash-dot-dot"></div>
								</a></li>
								<li data-value="dash-dot"><a>
										<div class="line-style-dash-dot"></div>
								</a></li>
								<li data-value="dashed"><a>
										<div class="line-style-dashed"></div>
								</a></li>
								<li data-value="thin" class="selected"><a>
										<div class="line-style-thin"></div>
								</a></li>
								<li data-value="medium-dash-dot-dot"><a>
										<div class="line-style-medium-dash-dot-dot"></div>
								</a></li>
								<li data-value="slanted-dash-dot"><a>
										<div class="line-style-slanted-dash-dot"></div>
								</a></li>
								<li data-value="medium-dash-dot"><a>
										<div class="line-style-medium-dash-dot"></div>
								</a></li>
								<li data-value="medium-dashed"><a>
										<div class="line-style-medium-dashed"></div>
								</a></li>
								<li data-value="medium"><a>
										<div class="line-style-medium"></div>
								</a></li>
								<li data-value="thick"><a>
										<div class="line-style-thick"></div>
								</a></li>
								<li data-value="double"><a>
										<div class="line-style-double"></div>
								</a></li>
							</ul>
						</div>
					</div>
					<div class="pane-row border-line-color">
						<label class="pane-label narrow-width localize">@cellTab.border.rangeBorderColor@</label>
						<div class="btn-group pane-color-picker"
							data-name="border-line-color">
							<button type="button" class="btn btn-default btn-pane-dropdown">
								<div class="color-picker" style="background-color: black"></div>
							</button>
						</div>
					</div>
					<div class="pane-row">
						<label class="pane-label narrow-width localize">@cellTab.border.label@</label>
						<div class="border-type-items">
							<div class="text-center">
								<div class="border-type-item localize-tooltip"
									data-name="outside" title="@tooltips.border.outsideBorder@">
									<div class="border-type-image sprite BorderOutside"></div>
								</div>
								<div class="border-type-item localize-tooltip"
									data-name="inside" title="@tooltips.border.insideBorder@">
									<div class="border-type-image sprite BorderInside"></div>
								</div>
								<div class="border-type-item localize-tooltip" data-name="all"
									title="@tooltips.border.allBorder@">
									<div class="border-type-image sprite BordersAll"></div>
								</div>
							</div>
							<div class="text-center">
								<div class="border-type-item localize-tooltip" data-name="left"
									title="@tooltips.border.leftBorder@">
									<div class="border-type-image sprite BorderLeft"></div>
								</div>
								<div class="border-type-item localize-tooltip"
									data-name="innerVertical"
									title="@tooltips.border.innerVertical@">
									<div class="border-type-image sprite BorderInsideVertical"></div>
								</div>
								<div class="border-type-item localize-tooltip" data-name="right"
									title="@tooltips.border.rightBorder@">
									<div class="border-type-image sprite BorderRight"></div>
								</div>
							</div>
							<div class="text-center">
								<div class="border-type-item localize-tooltip" data-name="top"
									title="@tooltips.border.topBorder@">
									<div class="border-type-image sprite BorderTop"></div>
								</div>
								<div class="border-type-item localize-tooltip"
									data-name="innerHorizontal"
									title="@tooltips.border.innerHorizontal@">
									<div class="border-type-image sprite BorderInsideHorizontal"></div>
								</div>
								<div class="border-type-item localize-tooltip"
									data-name="bottom" title="@tooltips.border.bottomBorder@">
									<div class="border-type-image sprite BorderBottom"></div>
								</div>
							</div>
							<div class="text-center">
								<div class="border-type-item localize-tooltip" data-name="none"
									title="@tooltips.border.noBorder@">
									<div class="border-type-image sprite BorderNone"></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div id="cellTypeSetting" class="hidden">
					<div id="buttonCellTypeSetting" class="group-celltype"
						data-name="button">
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.buttonCellType.values.marginTop@</label>
							<input type="number" class="pane-input"
								data-name="buttonCellTypeMarginTop" value="2">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.buttonCellType.values.marginRight@</label>
							<input type="number" class="pane-input"
								data-name="buttonCellTypeMarginRight" value="4">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.buttonCellType.values.marginBottom@</label>
							<input type="number" class="pane-input"
								data-name="buttonCellTypeMarginBottom" value="2">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.buttonCellType.values.marginLeft@</label>
							<input type="number" class="pane-input"
								data-name="buttonCellTypeMarginLeft" value="4">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.buttonCellType.values.text@</label>
							<input type="text" class="pane-input"
								data-name="buttonCellTypeText" value="Button">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.buttonCellType.values.backColor@</label>
							<div class="btn-group pane-color-picker"
								data-name="buttonCellTypeBackColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(242, 242, 242);"></div>
								</button>
							</div>
						</div>
					</div>
					<div id="checkboxCellTypeSetting" class="group-celltype"
						data-name="checkbox">
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.checkBoxCellType.values.caption@</label>
							<input type="text" class="pane-input localize-value"
								data-name="checkboxCellTypeCaption"
								value="@defaultTexts.checkCaption@">
						</div>

						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.checkBoxCellType.values.textTrue@</label>
							<input type="text" class="pane-input"
								data-name="checkboxCellTypeTextTrue">
						</div>

						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.checkBoxCellType.values.textIndeterminate@</label>
							<input type="text" class="pane-input"
								data-name="checkboxCellTypeTextIndeterminate">
						</div>

						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.checkBoxCellType.values.textFalse@</label>
							<input type="text" class="pane-input"
								data-name="checkboxCellTypeTextFalse">
						</div>

						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.checkBoxCellType.values.textAlign.title@</label>
							<div class="btn-group" data-name="checkboxCellTypeTextAlign">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu text-align">
									<li><a class="text localize" data-value="0">@cellTypes.checkBoxCellType.values.textAlign.values.top@</a>
									</li>
									<li><a class="text localize" data-value="1">@cellTypes.checkBoxCellType.values.textAlign.values.bottom@</a>
									</li>
									<li><a class="text localize" data-value="2">@cellTypes.checkBoxCellType.values.textAlign.values.left@</a>
									</li>
									<li class="default"><a class="text localize"
										data-value="3">@cellTypes.checkBoxCellType.values.textAlign.values.right@</a>
									</li>
								</ul>
							</div>
						</div>

						<div class="pane-row">
							<label class="checkbox-inline pane-right"
								data-name="checkboxCellTypeIsThreeState"> <input
								type="checkbox"> <span class="localize">@cellTypes.checkBoxCellType.values.isThreeState@</span>
							</label>
						</div>
					</div>
					<div id="comboboxCellTypeSetting" class="group-celltype"
						data-name="combobox">
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.comboBoxCellType.values.editorValueType.title@</label>
							<div class="btn-group"
								data-name="comboboxCellTypeEditorValueType">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu text-align">
									<li><a class="text localize" data-value="0">@cellTypes.comboBoxCellType.values.editorValueType.values.text@</a>
									</li>
									<li><a class="text localize" data-value="1">@cellTypes.comboBoxCellType.values.editorValueType.values.index@</a>
									</li>
									<li class="default"><a class="text localize"
										data-value="2">@cellTypes.comboBoxCellType.values.editorValueType.values.value@</a>
									</li>
								</ul>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.comboBoxCellType.values.itemsText@</label>
							<input type="text" placeholder="(eg:123,456,789)"
								class="pane-input localize-value"
								data-name="comboboxCellTypeItemsText"
								value="@defaultTexts.comboText@">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.comboBoxCellType.values.itemsValue@</label>
							<input type="text" placeholder="(eg:abc,def,ghi)"
								class="pane-input localize-value"
								data-name="comboboxCellTypeItemsValue"
								value="@defaultTexts.comboValue@">
						</div>
					</div>
					<div id="hyperlinkCellTypeSetting" class="group-celltype"
						data-name="hyperlink">
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.hyperlinkCellType.values.linkColor@</label>
							<div class="btn-group pane-color-picker"
								data-name="hyperlinkCellTypeLinkColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(0, 102, 204);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.hyperlinkCellType.values.visitedLinkColor@</label>
							<div class="btn-group pane-color-picker"
								data-name="hyperlinkCellTypeVisitedLinkColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(51, 153, 255);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.hyperlinkCellType.values.address@</label>
							<input type="text" class="pane-input localize-value"
								data-name="hyperlinkCellTypeAddress"
								value="@defaultTexts.hyperlinkAddress@">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.hyperlinkCellType.values.text@</label>
							<input type="text" class="pane-input localize-value"
								data-name="hyperlinkCellTypeText"
								value="@defaultTexts.hyperlinkText@">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@cellTypes.hyperlinkCellType.values.linkToolTip@</label>
							<input type="text" class="pane-input localize-value"
								data-name="hyperlinkCellTypeLinkToolTip"
								value="@defaultTexts.hyperlinkToolTip@">
						</div>
					</div>

					<div class="group-item-divider"></div>

					<div class="pane-row">
						<button type="button"
							class="btn btn-primary pane-set-button localize"
							id="setCellTypeButton">@cellTypes.setButton@</button>
					</div>
				</div>

				<div id="commentSetting" class="hidden">
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@commentTab.general.title@</span></span>
						</div>
						<div class="pane-group-content move-right">
							<div class="pane-row">
								<label class="checkbox-inline" data-name="commentDynamicSize"><input
									type="checkbox" checked><span class="localize">@commentTab.general.dynamicSize@</span></label>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="commentDynamicMove"><input
									type="checkbox" checked><span class="localize">@commentTab.general.dynamicMove@</span></label>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="commentLockText"><input
									type="checkbox" checked><span class="localize">@commentTab.general.lockText@</span></label>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="commentShowShadow"><input
									type="checkbox"><span class="localize">@commentTab.general.showShadow@</span></label>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@commentTab.font.title@</span></span>
						</div>
						<div class="pane-group-content move-left">
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.font.fontFamily@</label>
								<div class="btn-group" data-name="commentFontFamily">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li class="default"><a>Arial</a></li>
										<li><a>Arial Black</a></li>
										<li><a>Calibri</a></li>
										<li><a>Cambria</a></li>
										<li><a>Century</a></li>
										<li><a>Courier New</a></li>
										<li><a>Comic Sans MS</a></li>
										<li><a>Garamond</a></li>
										<li><a>Georgia</a></li>
										<li><a>Malgun Gothic</a></li>
										<li><a>Mangal</a></li>
										<li><a>Meiryo</a></li>
										<li><a>MS Gothic</a></li>
										<li><a>MS Mincho</a></li>
										<li><a>MS PGothic</a></li>
										<li><a>MS PMincho</a></li>
										<li><a>Roboto</a></li>
										<li><a>Tahoma</a></li>
										<li><a>Times</a></li>
										<li><a>Times New Roman</a></li>
										<li><a>Trebuchet MS</a></li>
										<li><a>Verdana</a></li>
										<li><a>Wingdings</a></li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.font.fontSize@</label>
								<div class="btn-group" data-name="commentFontSize">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li><a>8</a></li>
										<li class="default"><a>9</a></li>
										<li><a>10</a></li>
										<li><a>11</a></li>
										<li><a>12</a></li>
										<li><a>13</a></li>
										<li><a>14</a></li>
										<li><a>15</a></li>
										<li><a>16</a></li>
										<li><a>18</a></li>
										<li><a>20</a></li>
										<li><a>22</a></li>
										<li><a>24</a></li>
										<li><a>26</a></li>
										<li><a>28</a></li>
										<li><a>36</a></li>
										<li><a>48</a></li>
										<li><a>72</a></li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.font.fontStyle.title@</label>
								<div class="btn-group" data-name="commentFontStyle">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li class="default"><a class="text localize"
											data-value="normal">@commentTab.font.fontStyle.values.normal@</a>
										</li>
										<li><a class="text localize" data-value="italic">@commentTab.font.fontStyle.values.italic@</a>
										</li>
										<li><a class="text localize" data-value="oblique">@commentTab.font.fontStyle.values.oblique@</a>
										</li>
										<li><a class="text localize" data-value="inherit">@commentTab.font.fontStyle.values.inherit@</a>
										</li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.font.fontWeight.title@</label>
								<div class="btn-group" data-name="commentFontWeight">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li class="default"><a class="text localize"
											data-value="normal">@commentTab.font.fontWeight.values.normal@</a>
										</li>
										<li><a class="text localize" data-value="bold">@commentTab.font.fontWeight.values.bold@</a>
										</li>
										<li><a class="text localize" data-value="bolder">@commentTab.font.fontWeight.values.bolder@</a>
										</li>
										<li><a class="text localize" data-value="lighter">@commentTab.font.fontWeight.values.lighter@</a>
										</li>
										<li><a class="text">100</a></li>
										<li><a class="text">200</a></li>
										<li><a class="text">300</a></li>
										<li><a class="text">400</a></li>
										<li><a class="text">500</a></li>
										<li><a class="text">600</a></li>
										<li><a class="text">700</a></li>
										<li><a class="text">800</a></li>
										<li><a class="text">900</a></li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.font.textDecoration.title@</label>
								<div class="btn-group">
									<button type="button"
										class="btn btn-default btn-comment-decoration"
										data-name="comment-underline" data-value="1">
										<span style="text-decoration: underline;">U</span>
									</button>
									<button type="button"
										class="btn btn-default btn-comment-decoration"
										data-name="comment-overline" data-value="4">
										<span style="text-decoration: overline;">O</span>
									</button>
									<button type="button"
										class="btn btn-default btn-comment-decoration"
										data-name="comment-strikethrough" data-value="2">
										<span style="text-decoration: line-through;">S</span>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@commentTab.border.title@</span></span>
						</div>
						<div class="pane-group-content move-left">
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.border.width@</label>
								<input type="number" class="pane-input"
									data-name="commentBorderWidth" value="1">
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.border.style.title@</label>
								<div class="btn-group" data-name="commentBorderStyle">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li><a class="text localize" data-value="none">@commentTab.border.style.values.none@</a></li>
										<li><a class="text localize" data-value="hidden">@commentTab.border.style.values.hidden@</a>
										</li>
										<li><a class="text localize" data-value="dotted">@commentTab.border.style.values.dotted@</a>
										</li>
										<li><a class="text localize" data-value="dashed">@commentTab.border.style.values.dashed@</a>
										</li>
										<li class="default"><a class="text localize"
											data-value="solid">@commentTab.border.style.values.solid@</a>
										</li>
										<li><a class="text localize" data-value="double">@commentTab.border.style.values.double@</a>
										</li>
										<li><a class="text localize" data-value="groove">@commentTab.border.style.values.groove@</a>
										</li>
										<li><a class="text localize" data-value="ridge">@commentTab.border.style.values.ridge@</a>
										</li>
										<li><a class="text localize" data-value="inset">@commentTab.border.style.values.inset@</a>
										</li>
										<li><a class="text localize" data-value="outset">@commentTab.border.style.values.outset@</a>
										</li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.border.color@</label>
								<div class="btn-group pane-color-picker"
									data-name="commentBorderColor">
									<button type="button" class="btn btn-default btn-pane-dropdown">
										<div class="color-picker"
											style="background-color: rgb(0, 0, 0);"></div>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@commentTab.appearance.title@</span></span>
						</div>
						<div class="pane-group-content move-left">
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.appearance.horizontalAlign.title@</label>
								<div class="btn-group" data-name="commentHorizontalAlign">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li class="default"><a class="text localize"
											data-value="0">@commentTab.appearance.horizontalAlign.values.left@</a>
										</li>
										<li><a class="text localize" data-value="1">@commentTab.appearance.horizontalAlign.values.center@</a>
										</li>
										<li><a class="text localize" data-value="2">@commentTab.appearance.horizontalAlign.values.right@</a>
										</li>
										<li><a class="text localize" data-value="3">@commentTab.appearance.horizontalAlign.values.general@</a>
										</li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.appearance.displayMode.title@</label>
								<div class="btn-group" data-name="commentDisplayMode">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li><a class="text localize" data-value="1">@commentTab.appearance.displayMode.values.alwaysShown@</a>
										</li>
										<li class="default"><a class="text localize"
											data-value="2">@commentTab.appearance.displayMode.values.hoverShown@</a>
										</li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.appearance.foreColor@</label>
								<div class="btn-group pane-color-picker"
									data-name="commentForeColor">
									<button type="button" class="btn btn-default btn-pane-dropdown">
										<div class="color-picker"
											style="background-color: rgb(0, 0, 0);"></div>
									</button>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.appearance.backColor@</label>
								<div class="btn-group pane-color-picker"
									data-name="commentBackColor">
									<button type="button" class="btn btn-default btn-pane-dropdown">
										<div class="color-picker"
											style="background-color: rgb(255, 255, 225);"></div>
									</button>
								</div>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.appearance.padding@</label>
								<input type="text" class="pane-input" data-name="commentPadding">
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@commentTab.appearance.opacity@</label>
								<br /> <input type="number" class="pane-input input-width-md"
									data-name="commentOpacity"><span
									class="center-align span-fixed-block">%</span>
							</div>
						</div>
					</div>
				</div>

				<div id="cellFormatSetting" class="hidden">
					<div class="pane-row">
						<div class="btn-group pane-item-fullwidth"
							data-name="commonFormat">
							<button type="button"
								class="btn btn-default dropdown-toggle btn-pane-dropdown pane-item-fullwidth"
								data-toggle="dropdown" aria-expanded="false">
								<span class="content"></span> <span class="caret fixed"></span>
							</button>
							<ul class="dropdown-menu">
							</ul>
						</div>
					</div>
					<div class="group-item-divider"></div>
					<div class="pane-row">
						<label class="pane-label narrow-width localize">@cellTab.format.custom@</label>
						<input type="text" class="pane-input wide-input"
							data-name="customFormat">
					</div>
					<div class="pane-row">
						<button type="button"
							class="btn btn-primary pane-set-button localize"
							id="setCustomFormat">@cellTab.format.setButton@</button>
					</div>
				</div>
				<div id="sparklineSetting" class="hidden">
					<div class="move-left">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.sparklineExType.title@</label>
							<div class="btn-group" data-name="sparklineExType">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown" aria-expanded="false">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu sparklineExType">
									<li><a class="text localize" data-value="line">@sparklineDialog.sparklineExType.values.line@</a>
									</li>
									<li><a class="text localize" data-value="column">@sparklineDialog.sparklineExType.values.column@</a>
									</li>
									<li><a class="text localize" data-value="winloss">@sparklineDialog.sparklineExType.values.winLoss@</a>
									</li>
									<li><a class="text localize" data-value="pie">@sparklineDialog.sparklineExType.values.pie@</a>
									</li>
									<li><a class="text localize" data-value="area">@sparklineDialog.sparklineExType.values.area@</a>
									</li>
									<li><a class="text localize" data-value="scatter">@sparklineDialog.sparklineExType.values.scatter@</a>
									</li>
									<li><a class="text localize" data-value="spread">@sparklineDialog.sparklineExType.values.spread@</a>
									</li>
									<li><a class="text localize" data-value="stacked">@sparklineDialog.sparklineExType.values.stacked@</a>
									</li>
									<li><a class="text localize" data-value="bullet">@sparklineDialog.sparklineExType.values.bullet@</a>
									</li>
									<li><a class="text localize" data-value="hbar">@sparklineDialog.sparklineExType.values.hbar@</a>
									</li>
									<li><a class="text localize" data-value="vbar">@sparklineDialog.sparklineExType.values.vbar@</a>
									</li>
									<li><a class="text localize" data-value="vari">@sparklineDialog.sparklineExType.values.variance@</a>
									</li>
									<li><a class="text localize" data-value="boxplot">@sparklineDialog.sparklineExType.values.boxplot@</a>
									</li>
									<li><a class="text localize" data-value="cascade">@sparklineDialog.sparklineExType.values.cascade@</a>
									</li>
									<li><a class="text localize" data-value="pareto">@sparklineDialog.sparklineExType.values.pareto@</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="group-item-divider"></div>
					<div id="lineContainer" class="move-left">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.lineSparkline.dataRange@</label>
							<input type="text" class="pane-input"
								data-name="txtLineDataRange">
						</div>
						<div class="pane-row">
							<label class="error localize" id="dataRangeError">@sparklineDialog.lineSparkline.dataRangeError@</label>
						</div>
						<div class="pane-row">
							<label class="error localize" id="singleDataRangeError">@sparklineDialog.lineSparkline.singleDataRange@</label>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.lineSparkline.locationRange@</label>
							<input type="text" class="pane-input"
								data-name="txtLineLocationRange">
						</div>
						<div class="pane-row">
							<label class="error localize" id="locationRangeError">@sparklineDialog.lineSparkline.locationRangeError@</label>
						</div>
					</div>
					<div id="bulletContainer" class="move-left">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.measure@</label>
							<input type="text" class="pane-input"
								data-name="txtBulletMeasure">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.target@</label>
							<input type="text" class="pane-input" data-name="txtBulletTarget">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.maxi@</label>
							<input type="text" class="pane-input" data-name="txtBulletMaxi">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.good@</label>
							<input type="text" class="pane-input" data-name="txtBulletGood">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.bad@</label>
							<input type="text" class="pane-input" data-name="txtBulletBad">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.forecast@</label>
							<input type="text" class="pane-input"
								data-name="txtBulletForecast">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.tickunit@</label>
							<input type="text" class="pane-input"
								data-name="txtBulletTickunit">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.bulletSparkline.colorScheme@</label>
							<div class="btn-group pane-color-picker"
								data-name="colorBulletColorScheme">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(160, 160, 160);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline pane-right"
								data-name="checkboxBulletVertial"> <input
								type="checkbox"> <span class="localize">@sparklineDialog.bulletSparkline.vertical@</span>
							</label>
						</div>
					</div>
					<div id="hbarContainer" class="move-left">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.hbarSparkline.value@</label>
							<input type="text" class="pane-input" data-name="txtHbarValue">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.hbarSparkline.colorScheme@</label>
							<div class="btn-group pane-color-picker"
								data-name="colorHbarColorScheme">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(160, 160, 160);"></div>
								</button>
							</div>
						</div>
					</div>
					<div id="varianceContainer" class="move-left">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.variance@</label>
							<input type="text" class="pane-input" data-name="txtVariance">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.reference@</label>
							<input type="text" class="pane-input"
								data-name="txtVarianceReference">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.mini@</label>
							<input type="text" class="pane-input" data-name="txtVarianceMini">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.maxi@</label>
							<input type="text" class="pane-input" data-name="txtVarianceMaxi">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.mark@</label>
							<input type="text" class="pane-input" data-name="txtVarianceMark">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.tickunit@</label>
							<input type="text" class="pane-input"
								data-name="txtVarianceTickUnit">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.colorPositive@</label>
							<div class="btn-group pane-color-picker"
								data-name="colorVariancePositive">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(0, 128, 0);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineDialog.varianceSparkline.colorNegative@</label>
							<div class="btn-group pane-color-picker"
								data-name="colorVarianceNegative">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(255, 0, 0);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline" data-name="checkboxVarianceLegend"
								style="padding-left: 60px;"> <input type="checkbox">
								<span class="localize">@sparklineDialog.varianceSparkline.legend@</span>
							</label> <label class="checkbox-inline pane-right"
								data-name="checkboxVarianceVertical"> <input
								type="checkbox"> <span class="localize">@sparklineDialog.varianceSparkline.vertical@</span>
							</label>
						</div>
					</div>
					<div class="group-item-divider"></div>
					<div class="pane-row">
						<button type="button"
							class="btn btn-primary pane-set-button localize"
							id="setSparklineButton">@sparklineExTab.setButton@</button>
					</div>
				</div>

				<div id="sparklineDetailSetting" class="hidden">
					<div id="pieSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.pieSparkline.values.percentage@</label>
							<input type="text" class="pane-input"
								data-name="pieSparklinePercentage">
						</div>
						<div id="pieSparklineColorContainer"></div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setPieSparkline">
								@sparklineExTab.pieSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="areaSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.areaSparkline.values.points@</label>
							<input type="text" class="pane-input"
								data-name="areaSparklinePoints">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.areaSparkline.values.minimumValue@</label>
							<input type="number" class="pane-input not-min-zero"
								data-name="areaSparklineMinimumValue">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.areaSparkline.values.maximumValue@</label>
							<input type="text" class="pane-input not-min-zero"
								data-name="areaSparklineMaximumValue">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.areaSparkline.values.line1@</label>
							<input type="number" class="pane-input not-min-zero"
								data-name="areaSparklineLine1">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.areaSparkline.values.line2@</label>
							<input type="number" class="pane-input not-min-zero"
								data-name="areaSparklineLine2">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.areaSparkline.values.positiveColor@</label>
							<div class="btn-group pane-color-picker"
								data-name="areaSparklinePositiveColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(120, 120, 120);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.areaSparkline.values.negativeColor@</label>
							<div class="btn-group pane-color-picker"
								data-name="areaSparklineNegativeColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(0, 128, 0);"></div>
								</button>
							</div>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setAreaSparkline">
								@sparklineExTab.areaSparkline.values.setButton@</button>
						</div>

					</div>
					<div id="boxplotSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.points@</label>
							<input type="text" class="pane-input"
								data-name="boxplotSparklinePoints">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.boxplotClass@</label>
							<div class="btn-group" data-name="boxplotClassType">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown" aria-expanded="false">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu">
									<li class="default"><a class="text localize"
										data-value="5ns">@sparklineExTab.boxplotClass.fiveNS@</a></li>
									<li><a class="text localize" data-value="7ns">@sparklineExTab.boxplotClass.sevenNS@</a>
									</li>
									<li><a class="text localize" data-value="tukey">@sparklineExTab.boxplotClass.tukey@</a>
									</li>
									<li><a class="text localize" data-value="bowley">@sparklineExTab.boxplotClass.bowley@</a></li>
									<li><a class="text localize" data-value="sigma3">@sparklineExTab.boxplotClass.sigma@</a>
									</li>
								</ul>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.scaleStart@</label>
							<input type="text" class="pane-input"
								data-name="boxplotSparklineScaleStart">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.scaleEnd@</label>
							<input type="text" class="pane-input"
								data-name="boxplotSparklineScaleEnd">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.acceptableStart@</label>
							<input type="text" class="pane-input"
								data-name="boxplotSparklineAcceptableStart">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.acceptableEnd@</label>
							<input type="text" class="pane-input"
								data-name="boxplotSparklineAcceptableEnd">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.colorScheme@</label>
							<div class="btn-group pane-color-picker"
								data-name="boxplotSparklineColorScheme">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(210, 210, 210);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.boxplotSparkline.values.style@</label>
							<div class="btn-group" data-name="boxplotSparklineStyleType">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown" aria-expanded="false">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu">
									<li class="default"><a class="text localize"
										data-value="0">@sparklineExTab.boxplotStyle.classical@</a></li>
									<li><a class="text localize" data-value="1">@sparklineExTab.boxplotStyle.neo@</a></li>
								</ul>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline check-2items-left"
								data-name="boxplotSparklineShowAverage"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.boxplotSparkline.values.showAverage@</span>
							</label> <label class="checkbox-inline"
								data-name="boxplotSparklineVertical"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.boxplotSparkline.values.vertical@</span>
							</label>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setBoxPlotSparkline">
								@sparklineExTab.boxplotSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="bulletSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.measure@</label>
							<input type="text" class="pane-input"
								data-name="bulletSparklineMeasure">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.target@</label>
							<input type="text" class="pane-input"
								data-name="bulletSparklineTarget">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.maxi@</label>
							<input type="text" class="pane-input"
								data-name="bulletSparklineMaxi">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.forecast@</label>
							<input type="text" class="pane-input"
								data-name="bulletSparklineForecast">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.good@</label>
							<input type="text" class="pane-input"
								data-name="bulletSparklineGood">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.bad@</label>
							<input type="text" class="pane-input"
								data-name="bulletSparklineBad">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.tickunit@</label>
							<input type="number" class="pane-input"
								data-name="bulletSparklineTickUnit">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.bulletSparkline.values.colorScheme@</label>
							<div class="btn-group pane-color-picker"
								data-name="bulletSparklineColorScheme">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(146, 208, 80);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline check-1item-right"
								data-name="bulletSparklineVertical"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.bulletSparkline.values.vertical@</span>
							</label>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setBulletSparkline">
								@sparklineExTab.bulletSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="cascadeSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.cascadeSparkline.values.pointsRange@</label>
							<input type="text" class="pane-input"
								data-name="cascadeSparklinePointsRange">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.cascadeSparkline.values.pointIndex@</label>
							<input type="number" class="pane-input"
								data-name="cascadeSparklinePointIndex">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.cascadeSparkline.values.labelsRange@</label>
							<input type="text" class="pane-input"
								data-name="cascadeSparklineLabelsRange">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.cascadeSparkline.values.minimum@</label>
							<input type="text" class="pane-input"
								data-name="cascadeSparklineMinimum">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.cascadeSparkline.values.maximum@</label>
							<input type="text" class="pane-input"
								data-name="cascadeSparklineMaximum">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.cascadeSparkline.values.positiveColor@</label>
							<div class="btn-group pane-color-picker"
								data-name="cascadeSparklinePositiveColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(140, 191, 100);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.cascadeSparkline.values.negativeColor@</label>
							<div class="btn-group pane-color-picker"
								data-name="cascadeSparklineNegativeColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(214, 96, 77);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline check-1item-right"
								data-name="cascadeSparklineVertical"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.cascadeSparkline.values.vertical@</span>
							</label>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setCascadeSparkline">
								@sparklineExTab.cascadeSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="compatibleSparklineSetting">
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@sparklineExTab.compatibleSparkline.values.data.title@</span></span>
							</div>
							<div class="pane-group-content">
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.data.title@</label>
									<input type="text" class="pane-input"
										data-name="compatibleSparklineData">
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.data.dataOrientation@</label>
									<div class="btn-group" data-name="dataOrientationType">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown" aria-expanded="false">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu sparklineExType">
											<li class="default"><a class="text localize"
												data-value="0">@sparklineExTab.orientation.vertical@</a></li>
											<li><a class="text localize" data-value="1">@sparklineExTab.orientation.horizontal@</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.data.dateAxisData@</label>
									<input type="text" class="pane-input"
										data-name="compatibleSparklineDateAxisData">
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.data.dataOrientation@</label>
									<div class="btn-group" data-name="dateAxisOrientationType">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown" aria-expanded="false">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu">
											<li class="default"><a class="text localize"
												data-value="0">@sparklineExTab.orientation.vertical@</a></li>
											<li><a class="text localize" data-value="1">@sparklineExTab.orientation.horizontal@</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.data.displayEmptyCellAs@</label>
									<div class="btn-group" data-name="emptyCellDisplayType">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown" aria-expanded="false">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a class="text localize" data-value="0">@sparklineExTab.emptyCellDisplayType.gaps@</a>
											</li>
											<li class="default"><a class="text localize"
												data-value="1">@sparklineExTab.emptyCellDisplayType.zero@</a>
											</li>
											<li><a class="text localize" data-value="2">@sparklineExTab.emptyCellDisplayType.connect@</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline"
										data-name="showDataInHiddenRowOrColumn"> <input
										type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.data.showDataInHiddenRowOrColumn@</span>
									</label>
								</div>
							</div>
						</div>

						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@sparklineExTab.compatibleSparkline.values.show.title@</span></span>
							</div>
							<div class="pane-group-content">
								<div class="pane-row">
									<label class="checkbox-inline check-2items-left"
										data-name="compatibleSparklineShowFirst"> <input
										type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.show.showFirst@</span>
									</label> <label class="checkbox-inline"
										data-name="compatibleSparklineShowLast"> <input
										type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.show.showLast@</span>
									</label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline check-2items-left"
										data-name="compatibleSparklineShowHigh"> <input
										type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.show.showHigh@</span>
									</label> <label class="checkbox-inline"
										data-name="compatibleSparklineShowLow"> <input
										type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.show.showLow@</span>
									</label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline check-2items-left"
										data-name="compatibleSparklineShowNegative"> <input
										type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.show.showNegative@</span>
									</label> <label class="checkbox-inline"
										data-name="compatibleSparklineShowMarkers"> <input
										type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.show.showMarkers@</span>
									</label>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@sparklineExTab.compatibleSparkline.values.group.title@</span></span>
							</div>

							<div class="pane-group-content">
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.group.minAxisType@</label>
									<div class="btn-group" data-name="minAxisType">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown" aria-expanded="false">
											<span class="content localize"></span> <span
												class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a class="text localize" data-value="individual">@sparklineExTab.axisType.individual@</a>
											</li>
											<li><a class="text localize" data-value="group">@sparklineExTab.axisType.group@</a></li>
											<li><a class="text localize" data-value="custom">@sparklineExTab.axisType.custom@</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.group.manualMin@</label>
									<input type="number" class="pane-input" data-name="manualMin">
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.group.maxAxisType@</label>
									<div class="btn-group" data-name="maxAxisType">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown" aria-expanded="false">
											<span class="content localize"></span> <span
												class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a class="text localize" data-value="individual">@sparklineExTab.axisType.individual@</a>
											</li>
											<li><a class="text localize" data-value="group">@sparklineExTab.axisType.group@</a></li>
											<li><a class="text localize" data-value="custom">@sparklineExTab.axisType.custom@</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.group.manualMax@</label>
									<input type="number" class="pane-input" data-name="manualMax">
								</div>
								<div class="pane-row">
									<label class="checkbox-inline check-2items-left"
										data-name="rightToLeft"> <input type="checkbox">
										<span class="localize">@sparklineExTab.compatibleSparkline.values.group.rightToLeft@</span>
									</label> <label class="checkbox-inline" data-name="displayXAxis">
										<input type="checkbox"> <span class="localize">@sparklineExTab.compatibleSparkline.values.group.displayXAxis@</span>
									</label>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@sparklineExTab.compatibleSparkline.values.style.title@</span></span>
							</div>
							<div class="pane-group-content">
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.negative@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineNegativeColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(165, 42, 42);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.markers@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineMarkersColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(36, 64, 98);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.axis@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineAxisColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(0, 0, 0);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.series@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineSeriesColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(36, 64, 98);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.highMarker@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineHighMarkerColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(0, 0, 255);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.lowMarker@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineLowMarkerColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(0, 0, 255);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.firstMarker@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineFirstMarkerColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(149, 179, 215);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.lastMarker@</label>
									<div class="btn-group pane-color-picker"
										data-name="compatibleSparklineLastMarkerColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(149, 179, 215);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.style.lineWeight@</label>
									<input type="number" class="pane-input"
										data-name="compatibleSparklineLastLineWeight">
								</div>
							</div>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setCompatibleSparkline">@sparklineExTab.compatibleSparkline.values.setButton@
							</button>
						</div>
					</div>

					<div id="hbarSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.hbarSparkline.values.value@</label>
							<input type="text" class="pane-input"
								data-name="hbarSparklineValue">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.hbarSparkline.values.colorScheme@</label>
							<div class="btn-group pane-color-picker"
								data-name="hbarSparklineColorScheme">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(128, 128, 128);"></div>
								</button>
							</div>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setHbarSparkline">
								@sparklineExTab.hbarSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="vbarSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.vbarSparkline.values.value@</label>
							<input type="text" class="pane-input"
								data-name="vbarSparklineValue">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.vbarSparkline.values.colorScheme@</label>
							<div class="btn-group pane-color-picker"
								data-name="vbarSparklineColorScheme">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(128, 128, 128);"></div>
								</button>
							</div>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setVbarSparkline">
								@sparklineExTab.vbarSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="paretoSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.paretoSparkline.values.points@</label>
							<input type="text" class="pane-input"
								data-name="paretoSparklinePoints">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.paretoSparkline.values.pointIndex@</label>
							<input type="text" class="pane-input"
								data-name="paretoSparklinePointIndex">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.paretoSparkline.values.colorRange@</label>
							<input type="text" class="pane-input"
								data-name="paretoSparklineColorRange">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.paretoSparkline.values.target@</label>
							<input type="text" class="pane-input"
								data-name="paretoSparklineTarget">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.paretoSparkline.values.target2@</label>
							<input type="text" class="pane-input"
								data-name="paretoSparklineTarget2">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.paretoSparkline.values.highlightPosition@</label>
							<input type="text" class="pane-input"
								data-name="paretoSparklineHighlightPosition">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.compatibleSparkline.values.data.dataOrientation@</label>
							<div class="btn-group" data-name="paretoLabelType">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown" aria-expanded="false">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu sparklineExType">
									<li><a class="text localize" data-value="0">@sparklineExTab.paretoLabel.none@</a></li>
									<li class="default"><a class="text localize"
										data-value="2">@sparklineExTab.paretoLabel.single@</a></li>
									<li><a class="text localize" data-value="1">@sparklineExTab.paretoLabel.cumulated@</a>
									</li>
								</ul>
							</div>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setParetoSparkline">
								@sparklineExTab.paretoSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="scatterSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.points1@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklinePoints1">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.points2@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklinePoints2">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.minX@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineMinX">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.maxX@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineMaxX">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.minY@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineMinY">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.maxY@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineMaxY">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.hLine@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineHLine">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.vLine@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineVLine">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.xMinZone@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineXMinZone">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.xMaxZone@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineXMaxZone">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.yMinZone@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineYMinZone">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.yMaxZone@</label>
							<input type="text" class="pane-input"
								data-name="scatterSparklineYMaxZone">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.color1@</label>
							<div class="btn-group pane-color-picker"
								data-name="scatterSparklineColor1">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(150, 150, 150);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.scatterSparkline.values.color2@</label>
							<div class="btn-group pane-color-picker"
								data-name="scatterSparklineColor2">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(203, 0, 0);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline check-2items-left"
								data-name="scatterSparklineTags"> <input type="checkbox">
								<span class="localize">@sparklineExTab.scatterSparkline.values.tags@</span>
							</label> <label class="checkbox-inline"
								data-name="scatterSparklineDrawSymbol"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.scatterSparkline.values.drawSymbol@</span>
							</label>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline check-2items-left"
								data-name="scatterSparklineDrawLines"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.scatterSparkline.values.drawLines@</span>
							</label> <label class="checkbox-inline"
								data-name="scatterSparklineDashLine"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.scatterSparkline.values.dashLine@</span>
							</label>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setScatterSparkline">
								@sparklineExTab.scatterSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="spreadSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.spreadSparkline.values.points@</label>
							<input type="text" class="pane-input"
								data-name="spreadSparklinePoints">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.spreadSparkline.values.scaleStart@</label>
							<input type="text" class="pane-input"
								data-name="spreadSparklineScaleStart">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.spreadSparkline.values.scaleEnd@</label>
							<input type="text" class="pane-input"
								data-name="spreadSparklineScaleEnd">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.spreadSparkline.values.style@</label>
							<div class="btn-group" data-name="spreadSparklineStyleType">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown" aria-expanded="false">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu sparklineExType">
									<li class="default"><a class="text localize"
										data-value="1">@sparklineExTab.spreadStyle.stacked@</a></li>
									<li><a class="text localize" data-value="2">@sparklineExTab.spreadStyle.spread@</a></li>
									<li><a class="text localize" data-value="3">@sparklineExTab.spreadStyle.jitter@</a></li>
									<li><a class="text localize" data-value="4">@sparklineExTab.spreadStyle.poles@</a></li>
									<li><a class="text localize" data-value="5">@sparklineExTab.spreadStyle.stackedDots@</a>
									</li>
									<li><a class="text localize" data-value="6">@sparklineExTab.spreadStyle.stripe@</a></li>
								</ul>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.spreadSparkline.values.colorScheme@</label>
							<div class="btn-group pane-color-picker"
								data-name="spreadSparklineColorScheme">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(100, 100, 100);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline check-2items-left"
								data-name="spreadSparklineShowAverage"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.spreadSparkline.values.showAverage@</span>
							</label> <label class="checkbox-inline"
								data-name="spreadSparklineVertical"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.spreadSparkline.values.vertical@</span>
							</label>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setSpreadSparkline">
								@sparklineExTab.spreadSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="stackedSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.points@</label>
							<input type="text" class="pane-input"
								data-name="stackedSparklinePoints">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.colorRange@</label>
							<input type="text" class="pane-input"
								data-name="stackedSparklineColorRange">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.labelRange@</label>
							<input type="text" class="pane-input"
								data-name="stackedSparklineLabelRange">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.maximum@</label>
							<input type="number" class="pane-input"
								data-name="stackedSparklineMaximum">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.targetRed@</label>
							<input type="number" class="pane-input"
								data-name="stackedSparklineTargetRed">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.targetGreen@</label>
							<input type="number" class="pane-input"
								data-name="stackedSparklineTargetGreen">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.targetBlue@</label>
							<input type="number" class="pane-input"
								data-name="stackedSparklineTargetBlue">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.targetYellow@</label>
							<input type="number" class="pane-input"
								data-name="stackedSparklineTargetYellow">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.color@</label>
							<div class="btn-group pane-color-picker"
								data-name="stackedSparklineColor">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(100, 100, 100);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.highlightPosition@</label>
							<input type="text" class="pane-input"
								data-name="stackedSparklineHighlightPosition">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.spreadSparkline.values.style@</label>
							<div class="btn-group"
								data-name="stackedSparklineTextOrientation">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown" aria-expanded="false">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu">
									<li><a class="text localize" data-value="1">@sparklineExTab.orientation.vertical@</a>
									</li>
									<li class="default"><a class="text localize"
										data-value="0">@sparklineExTab.orientation.horizontal@</a></li>
								</ul>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.stackedSparkline.values.textSize@</label>
							<input type="number" class="pane-input input-width-md"
								data-name="stackedSparklineTextSize"><span
								class="center-align span-fixed-block">px</span>
						</div>

						<div class="pane-row">
							<label class="checkbox-inline check-1item-right"
								data-name="stackedSparklineVertical"> <input
								type="checkbox"> <span class="localize">@sparklineExTab.stackedSparkline.values.vertical@</span>
							</label>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setStackedSparkline">
								@sparklineExTab.stackedSparkline.values.setButton@</button>
						</div>
					</div>
					<div id="variSparklineSetting">
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.variance@</label>
							<input type="text" class="pane-input"
								data-name="variSparklineVariance">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.reference@</label>
							<input type="text" class="pane-input"
								data-name="variSparklineReference">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.mini@</label>
							<input type="text" class="pane-input"
								data-name="variSparklineMini">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.maxi@</label>
							<input type="text" class="pane-input"
								data-name="variSparklineMaxi">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.mark@</label>
							<input type="text" class="pane-input"
								data-name="variSparklineMark">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.tickunit@</label>
							<input type="text" class="pane-input"
								data-name="variSparklineTickUnit">
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.colorPositive@</label>
							<div class="btn-group pane-color-picker"
								data-name="variSparklineColorPositive">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(0, 128, 0);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="pane-label localize">@sparklineExTab.variSparkline.values.colorNegative@</label>
							<div class="btn-group pane-color-picker"
								data-name="variSparklineColorNegative">
								<button type="button" class="btn btn-default btn-pane-dropdown">
									<div class="color-picker"
										style="background-color: rgb(255, 0, 0);"></div>
								</button>
							</div>
						</div>
						<div class="pane-row">
							<label class="checkbox-inline check-2items-left"
								data-name="variSparklineLegend"> <input type="checkbox">
								<span class="localize">@sparklineExTab.variSparkline.values.legend@</span>
							</label> <label class="checkbox-inline" data-name="variSparklineVertical">
								<input type="checkbox"> <span class="localize">@sparklineExTab.variSparkline.values.vertical@</span>
							</label>
						</div>
						<div class="group-item-divider"></div>
						<div class="pane-row">
							<button type="button"
								class="btn btn-primary pane-set-button localize"
								id="setVariSparkline">
								@sparklineExTab.variSparkline.values.setButton@</button>
						</div>
					</div>
				</div>

				<div id="dataValidationSetting" class="hidden">
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@dataTab.dataValidation.setting.title@</span></span>
						</div>
						<div class="pane-group-content">
							<div class="pane-row">
								<label class="pane-label localize">@dataTab.dataValidation.setting.values.validatorType.title@</label>
								<div class="btn-group" data-name="validatorType">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li class="default"><a class="text localize"
											data-value="anyvalue-validator">@dataTab.dataValidation.setting.values.validatorType.option.anyValue@</a>
										</li>
										<li><a class="text localize"
											data-value="number-validator">@dataTab.dataValidation.setting.values.validatorType.option.number@</a>
										</li>
										<li><a class="text localize" data-value="list-validator">@dataTab.dataValidation.setting.values.validatorType.option.list@</a>
										</li>
										<li><a class="text localize"
											data-value="formulalist-validator">@dataTab.dataValidation.setting.values.validatorType.option.formulaList@</a>
										</li>
										<li><a class="text localize" data-value="date-validator">@dataTab.dataValidation.setting.values.validatorType.option.date@</a>
										</li>
										<li><a class="text localize"
											data-value="textlength-validator">@dataTab.dataValidation.setting.values.validatorType.option.textLength@</a>
										</li>
										<li><a class="text localize"
											data-value="formula-validator">@dataTab.dataValidation.setting.values.validatorType.option.custom@</a>
										</li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline check-1item-right"
									data-name="ignoreBlank"> <input type="checkbox" checked>
									<span class="localize">@dataTab.dataValidation.setting.values.ignoreBlank@</span>
								</label>
							</div>
							<div class="group-item-divider"></div>
							<div id="validatorNumberType">
								<div class="pane-row">
									<label class="pane-label localize">@dataTab.dataValidation.setting.values.validatorComparisonOperator.title@</label>
									<div class="btn-group"
										data-name="numberValidatorComparisonOperator">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu text-align">
											<li class="default"><a class="text localize"
												data-value="6">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.between@</a>
											</li>
											<li><a class="text localize" data-value="7">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.notBetween@</a>
											</li>
											<li><a class="text localize" data-value="0">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.equalTo@</a>
											</li>
											<li><a class="text localize" data-value="1">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.notEqualTo@</a>
											</li>
											<li><a class="text localize" data-value="2">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.greaterThan@</a>
											</li>
											<li><a class="text localize" data-value="4">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.lessThan@</a>
											</li>
											<li><a class="text localize" data-value="3">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.greaterThanOrEqualTo@</a>
											</li>
											<li><a class="text localize" data-value="5">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.lessThanOrEqualTo@</a>
											</li>
										</ul>
									</div>
								</div>
								<div id="numberBetweenOperator">
									<div class="pane-row">
										<label class="pane-label localize">@dataTab.dataValidation.setting.values.number.minimum@</label>
										<input type="text" class="pane-input"
											data-name="numberMinimum">
									</div>
									<div class="pane-row">
										<label class="pane-label localize">@dataTab.dataValidation.setting.values.number.maximum@</label>
										<input type="text" class="pane-input"
											data-name="numberMaximum">
									</div>
								</div>
								<div id="numberValue">
									<div class="pane-row">
										<label class="pane-label localize">@dataTab.dataValidation.setting.values.number.value@</label>
										<input type="text" class="pane-input" data-name="numberValue">
									</div>
								</div>
								<div>
									<div class="pane-row">
										<label class="checkbox-inline check-1item-right"
											data-name="isInteger"> <input type="checkbox" checked>
											<span class="localize">@dataTab.dataValidation.setting.values.number.isInteger@</span>
										</label>
									</div>
								</div>
							</div>
							<div id="validatorListType">
								<div class="pane-row">
									<label class="pane-label localize">@dataTab.dataValidation.setting.values.source@</label>
									<input type="text" class="pane-input" data-name="listSource">
								</div>
							</div>
							<div id="validatorFormulaListType">
								<div class="pane-row">
									<label class="pane-label localize">@dataTab.dataValidation.setting.values.formula@</label>
									<input type="text" class="pane-input"
										data-name="formulaListFormula">
								</div>
							</div>
							<div id="validatorDateType">
								<div class="pane-row">
									<label class="pane-label localize">@dataTab.dataValidation.setting.values.validatorComparisonOperator.title@</label>
									<div class="btn-group"
										data-name="dateValidatorComparisonOperator">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu text-align">
											<li class="default"><a class="text localize"
												data-value="6">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.between@</a>
											</li>
											<li><a class="text localize" data-value="7">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.notBetween@</a>
											</li>
											<li><a class="text localize" data-value="0">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.equalTo@</a>
											</li>
											<li><a class="text localize" data-value="1">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.notEqualTo@</a>
											</li>
											<li><a class="text localize" data-value="2">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.greaterThan@</a>
											</li>
											<li><a class="text localize" data-value="4">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.lessThan@</a>
											</li>
											<li><a class="text localize" data-value="3">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.greaterThanOrEqualTo@</a>
											</li>
											<li><a class="text localize" data-value="5">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.lessThanOrEqualTo@</a>
											</li>
										</ul>
									</div>
								</div>
								<div id="dateBetweenOperator">
									<div class="pane-row">
										<label class="pane-label localize">@dataTab.dataValidation.setting.values.date.startDate@</label>
										<input type="text" class="pane-input" data-name="startDate">
									</div>
									<div class="pane-row">
										<label class="pane-label localize">@dataTab.dataValidation.setting.values.date.endDate@</label>
										<input type="text" class="pane-input" data-name="endDate">
									</div>
								</div>
								<div id="dateValue">
									<div class="pane-row">
										<label class="pane-label localize">@dataTab.dataValidation.setting.values.date.value@</label>
										<input type="text" class="pane-input" data-name="dateValue">
									</div>
								</div>
								<div>
									<div class="pane-row">
										<label class="checkbox-inline check-1item-right"
											data-name="isTime"> <input type="checkbox" checked>
											<span class="localize">@dataTab.dataValidation.setting.values.date.isTime@</span>
										</label>
									</div>
								</div>
							</div>
							<div id="validatorTextLengthType">
								<div class="pane-row">
									<label class="pane-label localize">@dataTab.dataValidation.setting.values.validatorComparisonOperator.title@</label>
									<div class="btn-group"
										data-name="textLengthValidatorComparisonOperator">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu text-align">
											<li class="default"><a class="text localize"
												data-value="6">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.between@</a>
											</li>
											<li><a class="text localize" data-value="7">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.notBetween@</a>
											</li>
											<li><a class="text localize" data-value="0">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.equalTo@</a>
											</li>
											<li><a class="text localize" data-value="1">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.notEqualTo@</a>
											</li>
											<li><a class="text localize" data-value="2">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.greaterThan@</a>
											</li>
											<li><a class="text localize" data-value="4">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.lessThan@</a>
											</li>
											<li><a class="text localize" data-value="3">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.greaterThanOrEqualTo@</a>
											</li>
											<li><a class="text localize" data-value="5">@dataTab.dataValidation.setting.values.validatorComparisonOperator.option.lessThanOrEqualTo@</a>
											</li>
										</ul>
									</div>
									<div id="textLengthBetweenOperator">
										<div class="pane-row">
											<label class="pane-label localize">@dataTab.dataValidation.setting.values.number.minimum@</label>
											<input type="number" class="pane-input"
												data-name="textLengthMinimum">
										</div>
										<div class="pane-row">
											<label class="pane-label localize">@dataTab.dataValidation.setting.values.number.maximum@</label>
											<input type="number" class="pane-input"
												data-name="textLengthMaximum">
										</div>
									</div>
									<div id="textLengthValue">
										<div class="pane-row">
											<label class="pane-label localize">@dataTab.dataValidation.setting.values.number.value@</label>
											<input type="number" class="pane-input"
												data-name="textLengthValue">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@dataTab.dataValidation.inputMessage.title@</span></span>
						</div>
						<div class="pane-group-content">
							<div class="pane-row">
								<label class="checkbox-inline" data-name="showInputMessage"><input
									type="checkbox" checked><span class="localize">@dataTab.dataValidation.inputMessage.values.showInputMessage@</span></label>
							</div>
							<div class="pane-row">
								<label class="normal localize">@dataTab.dataValidation.inputMessage.values.title@</label>
								<input type="text" class="pane-item-fullwidth"
									data-name="dataValidationInputTitle"
									id="dataValidationInputTitle">
							</div>
							<div class="pane-row">
								<label class="normal localize">@dataTab.dataValidation.inputMessage.values.message@</label>
								<textarea rows="5" data-name="dataValidationInputMessage"
									class="pane-item-fullwidth" id="dataValidationInputMessage"></textarea>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@dataTab.dataValidation.errorAlert.title@</span></span>
						</div>
						<div class="pane-group-content">
							<label class="pane-label localize">@dataTab.dataValidation.errorAlert.values.alertType.title@</label>
							<div class="btn-group" data-name="errorAlert">
								<button type="button"
									class="btn btn-default dropdown-toggle btn-pane-dropdown"
									data-toggle="dropdown" aria-expanded="false">
									<span class="content"></span> <span class="caret fixed"></span>
								</button>
								<ul class="dropdown-menu">
									<li class="default"><a class="text localize"
										data-value="0">@dataTab.dataValidation.errorAlert.values.alertType.option.stop@</a>
									</li>
									<li><a class="text localize" data-value="1">@dataTab.dataValidation.errorAlert.values.alertType.option.warning@</a>
									</li>
									<li><a class="text localize" data-value="2">@dataTab.dataValidation.errorAlert.values.alertType.option.information@</a>
									</li>
								</ul>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="showErrorAlert"><input
									type="checkbox" checked><span class="localize">@dataTab.dataValidation.errorAlert.values.showErrorAlert@</span></label>
							</div>
							<div class="pane-row">
								<label class="normal localize">@dataTab.dataValidation.errorAlert.values.title@</label>
								<input type="text" class="pane-item-fullwidth"
									data-name="dataValidationErrorAlertTitle"
									id="dataValidationErrorAlertTitle">
							</div>
							<div class="pane-row">
								<label class="normal localize">@dataTab.dataValidation.errorAlert.values.message@</label>
								<textarea rows="5" data-name="dataValidationErrorAlertMessage"
									class="pane-item-fullwidth"
									id="dataValidationErrorAlertMessage"></textarea>
							</div>
						</div>
					</div>

					<div class="group-item-divider"></div>

					<div class="pane-row">
						<button type="button" class="btn btn-primary localize"
							data-name="clearDataValidatorSettings"
							id="clearDataValidatorSettings">@dataTab.dataValidation.clearAllButton@
						</button>
						<button type="button" class="btn btn-primary localize"
							id="setDataValidator">
							@dataTab.dataValidation.setButton@</button>
					</div>
				</div>

				<div id="functionBuiilder" class="hidden">
					<div>
						<input type="text" placeholder="Search function"
							class="pane-item-fullwidth">
					</div>
					<br />
					<ul class="function-list">

					</ul>
					<div class="pane-row">
						<p class="function-description localize">
							@functions.setting.defaultDescription@</p>
					</div>
				</div>

				<div id="findOptions" class="findOptions hidden">
					<div class="pane-row">
						<label class="pane-label localize">@find.findwhat@</label> <input
							type="text" class="pane-input" data-name="findwhat">
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@find.options@</span></span>
						</div>
						<div class="pane-group-content">
							<div class="pane-row">
								<label class="pane-label localize">@find.within.title@</label> <input
									type="radio" name="findin" id="findin_worksheet"
									value="worksheet" checked><label class="localize"
									for="findin_worksheet">@find.within.worksheet@</label> <input
									type="radio" name="findin" id="findin_workbook"
									value="workbook"><label class="localize"
									for="findin_workbook">@find.within.workbook@</label>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@find.searchby.title@</label>
								<input type="radio" name="searchby" id="searchby_rows"
									value="rows" checked><label class="localize"
									for="searchby_rows">@find.searchby.rows@</label> <input
									type="radio" name="searchby" id="searchby_columns"
									value="columns"><label class="localize"
									for="searchby_columns">@find.searchby.columns@</label>
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@find.lookin.title@</label> <input
									type="radio" name="lookin" id="lookin_values" value="values"
									checked><label class="localize" for="lookin_values">@find.lookin.values@</label>
								<input type="radio" name="lookin" id="lookin_formulas"
									value="formulas"><label class="localize"
									for="lookin_formulas">@find.lookin.formulas@</label>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="findMatchCase"><input
									type="checkbox"><span class="localize">@find.matchcase@</span></label>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="findMatchExactly"><input
									type="checkbox"><span class="localize">@find.matchexactly@</span></label>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="findUseWildcards"><input
									type="checkbox"><span class="localize">@find.usewildcards@</span></label>
							</div>
						</div>
					</div>
					<div class="pane-row">
						<button type="button" class="btn btn-primary localize"
							id="findall">@find.findall@</button>
						<button type="button" class="btn btn-primary localize"
							id="findnext">@find.findnext@</button>
					</div>
					<div class="pane-row findoutput">
						<div class="findresult">
							<table class="resultlist">
								<thead>
									<tr>
										<th class="localize">@find.result.header.sheet@</th>
										<th class="localize">@find.result.header.cell@</th>
										<th class="localize">@find.result.header.value@</th>
										<th class="localize">@find.result.header.formula@</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<label class="resultcount localize"></label>
					</div>
				</div>

				<div id="slicerSetting" class="hidden">
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@slicerTab.general.title@</span></span>
						</div>

						<div class="pane-group-content">
							<div class="pane-row">
								<label class="pane-label localize">@slicerTab.general.name@</label>
								<input type="text" class="pane-input" data-name="slicerName">
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@slicerTab.general.captionName@</label>
								<input type="text" class="pane-input"
									data-name="slicerCaptionName">
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@slicerTab.general.itemSorting.title@</label>
								<div class="btn-group" data-name="slicerItemSorting">
									<button type="button"
										class="btn btn-default dropdown-toggle btn-pane-dropdown"
										data-toggle="dropdown">
										<span class="content"></span> <span class="caret fixed"></span>
									</button>
									<ul class="dropdown-menu text-align">
										<li><a class="text localize" data-value="0">@slicerTab.general.itemSorting.option.none@</a>
										</li>
										<li class="default"><a class="text localize"
											data-value="1">@slicerTab.general.itemSorting.option.ascending@</a>
										</li>
										<li><a class="text localize" data-value="2">@slicerTab.general.itemSorting.option.descending@</a>
										</li>
									</ul>
								</div>
							</div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="displaySlicerHeader"><input
									type="checkbox" checked><span class="localize">@slicerTab.general.displayHeader@</span></label>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@slicerTab.slicerStyle.title@</span></span>
						</div>

						<div class="pane-group-content">
							<div class="slicer-format-2013">
								<div class="light-format-slicer slicer-format-category-div">
									<div class="slicer-format-label-container">
										<label class="slicer-format-label localize">@slicerTab.slicerStyle.groupTitle.light@</label>
									</div>
									<div class="slicer-style-preview">
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.light.light1@">
											<div class="slicer-format-icon slicer-format-light1"
												data-name="light1"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.light.light2@">
											<div class="slicer-format-icon slicer-format-light2"
												data-name="light2"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.light.light3@">
											<div class="slicer-format-icon slicer-format-light3"
												data-name="light3"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.light.light4@">
											<div class="slicer-format-icon slicer-format-light4"
												data-name="light4"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.light.light5@">
											<div class="slicer-format-icon slicer-format-light5"
												data-name="light5"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.light.light6@">
											<div class="slicer-format-icon slicer-format-light6"
												data-name="light6"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.other.other1@">
											<div class="slicer-format-icon slicer-format-other1"
												data-name="other1"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.other.other2@">
											<div class="slicer-format-icon slicer-format-other2"
												data-name="other2"></div>
										</div>
									</div>
								</div>
								<div class="dark-format-slicer slicer-format-category-div">
									<div class="slicer-format-label-container">
										<label class="slicer-format-label localize">@slicerTab.slicerStyle.groupTitle.dark@</label>
									</div>
									<div class="slicer-style-preview">
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.dark.dark1@">
											<div class="slicer-format-icon slicer-format-dark1"
												data-name="dark1"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.dark.dark2@">
											<div class="slicer-format-icon slicer-format-dark2"
												data-name="dark2"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.dark.dark3@">
											<div class="slicer-format-icon slicer-format-dark3"
												data-name="dark3"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.dark.dark4@">
											<div class="slicer-format-icon slicer-format-dark4"
												data-name="dark4"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.dark.dark5@">
											<div class="slicer-format-icon slicer-format-dark5"
												data-name="dark5"></div>
										</div>
										<div class="slicer-format-item localize-tooltip"
											title="@slicerTab.slicerStyle.dark.dark6@">
											<div class="slicer-format-icon slicer-format-dark6"
												data-name="dark6"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@slicerTab.layout.title@</span></span>
						</div>

						<div class="pane-group-content">
							<div class="pane-row">
								<label class="pane-label localize">@slicerTab.layout.columnNumber@</label>
								<input type="number" class="pane-input"
									data-name="slicerColumnNumber">
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@slicerTab.layout.buttonHeight@</label>
								<input type="number" class="pane-input"
									data-name="slicerButtonHeight">
							</div>
							<div class="pane-row">
								<label class="pane-label localize">@slicerTab.layout.buttonWidth@</label>
								<input type="number" class="pane-input"
									data-name="slicerButtonWidth">
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@slicerTab.property.title@</span></span>
						</div>

						<div class="pane-group-content padding-left-space">
							<div class="pane-row">
								<input type="radio" name="slicerMoveAndSize"
									id="slicer-move-size" data-value="slicer-move-size" checked><label
									class="localize" for="slicer-move-size">@slicerTab.property.moveAndSize@</label>
							</div>
							<div class="pane-row">
								<input type="radio" name="slicerMoveAndSize"
									id="slicer-move-nosize" data-value="slicer-move-nosize"><label
									class="localize" for="slicer-move-nosize">@slicerTab.property.moveAndNoSize@</label>
							</div>
							<div class="pane-row">
								<input type="radio" name="slicerMoveAndSize"
									id="slicer-nomove-size" data-value="slicer-nomove-size" checked><label
									class="localize" for="slicer-nomove-size">@slicerTab.property.noMoveAndSize@</label>
							</div>
							<div class="group-item-divider"></div>
							<div class="pane-row">
								<label class="checkbox-inline" data-name="lockSlicer"><input
									type="checkbox" checked><span class="localize">@slicerTab.property.locked@</span></label>
							</div>
						</div>
					</div>
					<div class="pane-group">
						<div class="pane-group-title">
							<span><span
								class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
								class="localize">@slicerTab.filter.title@</span></span>
						</div>

						<div class="pane-group-content padding-left-space">
							<div class="pane-row">
								<label class="checkbox-inline" data-name="hide-no-data"><input
									type="checkbox"><span class="localize">@slicerTab.filter.hideItemsWithNoData@</span></label>
							</div>
							<div class="padding-left-space">
								<div class="pane-row">
									<label class="checkbox-inline" data-name="mark-no-data"><input
										type="checkbox"><span class="localize">@slicerTab.filter.markNoData@</span></label>
								</div>
								<div class="padding-left-space">
									<div class="pane-row">
										<label class="checkbox-inline" data-name="show-no-data-last"><input
											type="checkbox" checked><span class="localize">@slicerTab.filter.showNoDataLast@</span></label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="setfontstyle" style="display: none"></div>
		<div id="colorPicker" class="colorPickerContainer">
			<div class="header"></div>
			<div class="themeColorsLabel"></div>
			<div class="colorPicker themeColorsContainer">
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
			</div>
			<div class="standardColorsLabel"></div>
			<div class="colorPicker standardColorsContainer">
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
				<div class="color-cell"></div>
			</div>
			<div class="colorPicker nofill">
				<div class="nofill-Color" style="background-color: white;"></div>
				<div class="nofill-Text"></div>
			</div>
			<div class="colorPicker autocolor">
				<div class="auto-Color" style="background-color: white;"></div>
				<div class="auto-Text"></div>
			</div>
		</div>
		<ul id="spreadContextMenu" class="dropdown-menu" role="menu"
			style="display: none">
			<li><a class="localize" data-action="cut">@contextMenu.cutItem@</a></li>
			<li><a class="localize" data-action="copy">@contextMenu.copyItem@</a></li>
			<li><a class="localize" data-action="paste">@contextMenu.pasteItem@</a></li>
			<li class="context-header divider"></li>
			<li class="context-header"><a class="localize"
				data-action="insert">@contextMenu.insertItem@</a></li>
			<li class="context-header"><a class="localize"
				data-action="delete">@contextMenu.deleteItem@</a></li>
			<li class="context-cell divider"></li>
			<li class="context-cell context-merge"><a class="localize"
				data-action="merge">@contextMenu.mergeItem@</a></li>
			<li class="context-cell context-unmerge"><a class="localize"
				data-action="unmerge">@contextMenu.unmergeItem@</a></li>
		</ul>
		<div class="hidden">
			<input type="file" id="fileSelector" />
			<div id="pie-color-item-template">
				<div class="pane-row">
					<label class="pane-label"></label>
					<div class="btn-group pane-color-picker insp-col-4">
						<button type="button" class="btn btn-default btn-pane-dropdown"
							style="width: 100%;">
							<div class="color-picker"></div>
						</button>
					</div>
					<div class="insp-inline-row-item insp-col-2">
						<span class="ui-pie-sparkline-icon"></span>
					</div>
				</div>
			</div>
			<div id="function-description">
				<function name="ABS" param="value"
					description="This function calculates the absolute value of the specified value." />
				<function name="ACCRINT"
					param="issue,first,settle,rate,par,frequency,basis"
					description="This function calculates the accrued interest for a security that pays periodic interest." />
				<function name="ACCRINTM" param="issue,maturity,rate,par,basis"
					description="This function calculates the accrued interest at maturity for a security that pays periodic interest." />
				<function name="ACOS" param="value"
					description="This function calculates the arccosine, that is, the angle whose cosine is the specified value." />
				<function name="ACOSH" param="value"
					description="This function calculates the inverse hyperbolic cosine of the specified value." />
				<function name="ADDRESS" param="row,column,absnum,a1style,sheettext"
					description="This function uses the row and column numbers to create a cell address in text." />
				<function name="AMORDEGRC"
					param="cost,datepurchased,firstperiod,salvage,period,drate,basis"
					description="This function returns the depreciation for an accounting period, taking into consideration prorated depreciation, and applies a depreciation coefficient in the calculation based on the life of the assets." />
				<function name="AMORLINC"
					param="cost,datepurchased,firstperiod,salvage,period,drate,basis"
					description="This function calculates the depreciation for an accounting period, taking into account prorated depreciation." />
				<function name="AND" param="logical1,logical2"
					description="Check whether all argumengts are True,and returns True if all argements are True." />
				<function name="ASIN" param="value"
					description="This function calculates the arcsine, that is, the angle whose sine is the specified value." />
				<function name="ASINH" param="value"
					description="This function calculates the inverse hyperbolic sine of a number." />
				<function name="ATAN" param="value"
					description="This function calculates the arctangent, that is, the angle whose tangent is the specified value." />
				<function name="ATAN2" param="x,y"
					description="This function calculates the arctangent of the specified x- and y-coordinates." />
				<function name="ATANH" param="value"
					description="This function calculates the inverse hyperbolic tangent of a number." />
				<function name="AVEDEV" param="value1,value2,..."
					description="This function calculates the average of the absolute deviations of the specified values from their mean." />
				<function name="AVERAGE" param="value1,value2,..."
					description="This function calculates the average of the specified numeric values." />
				<function name="AVERAGEA" param="value1,value2,..."
					description="This function calculates the average of the specified values, including text or logical values as well as numeric values." />
				<function name="AVERAGEIF" param="value1,value2,...,condition"
					description="This function calculates the average of the specified numeric values provided that they meet the specified criteria." />
				<function name="AVERAGEIFS"
					param="value1,condition1,value2,...,condition2..."
					description="This function calculates the average of all cells that meet multiple specified criteria." />
				<function name="BESSELI" param="value,order"
					description="This function calculates the modified Bessel function of the first kind evaluated for purely imaginary arguments." />
				<function name="BESSELJ" param="value,order"
					description="This function calculates the Bessel function of the first kind." />
				<function name="BESSELK" param="value,order"
					description="This function calculates the modified Bessel function of the second kind evaluated for purely imaginary arguments." />
				<function name="BESSELY" param="value,order"
					description="This function calculates the Bessel function of the second kind." />
				<function name="BETADIST" param="x,alpha,beta,lower,upper"
					description="This function calculates the cumulative beta distribution function." />
				<function name="BETAINV" param="prob,alpha,beta,lower,upper"
					description="This function calculates the inverse of the cumulative beta distribution function." />
				<function name="BIN2DEC" param="number"
					description="This function converts a binary number to a decimal number" />
				<function name="BIN2HEX" param="number,places"
					description="This function converts a binary number to a hexadecimal number." />
				<function name="BIN2OCT" param="number,places"
					description="This function converts a binary number to an octal number." />
				<function name="BINOMDIST" param="x,n,p,cumulative"
					description="This function calculates the individual term binomial distribution probability." />
				<function name="CEILING" param="value,signif"
					description="This function rounds a number up to the nearest multiple of a specified value." />
				<function name="CHAR" param="value"
					description="This function returns the character specified by a number." />
				<function name="CHIDIST" param="value,deg"
					description="This function calculates the one-tailed probability of the chi-squared distribution." />
				<function name="CHIINV" param="prob,deg"
					description="This function calculates the inverse of the one-tailed probability of the chi-squared distribution" />
				<function name="CHITEST" param="obs_array,exp_array"
					description="This function calculates the test for independence from the chi-squared distribution." />
				<function name="CHOOSE" param="index,value1,value2,..."
					description="This function returns a value from a list of values." />
				<function name="CLEAN" param="text"
					description="This function removes all non-printable characters from text." />
				<function name="CODE" param="text"
					description="This function returns a numeric code to represent the first character in a text string. The returned code corresponds to the Windows character set (ANSI)." />
				<function name="COLUMN" param="reference"
					description="This function returns the column number of a reference." />
				<function name="COLUMNS" param="array"
					description="This function returns the number of columns in an array." />
				<function name="COMBIN" param="k,n"
					description="This function calculates the number of possible combinations for a specified number of items." />
				<function name="COMPLEX" param="realcoeff,imagcoeff,suffix"
					description="This function converts real and imaginary coefficients into a complex number." />
				<function name="CONCATENATE" param="text1,text2,...."
					description="This function combines multiple text strings or numbers into one text string." />
				<function name="CONFIDENCE" param="alpha,stdev,size"
					description="This function returns confidence interval for a population mean." />
				<function name="CONVERT" param="number,from-unit,to-unit"
					description="This function converts a number from one measurement system to its equivalent in another measurement system." />
				<function name="CORREL" param="array1,array2"
					description="This function returns the correlation coefficient of the two sets of data." />
				<function name="COS" param="angle"
					description="This function returns the cosine of the specified angle." />
				<function name="COSH" param="value"
					description="This function returns the hyperbolic cosine of the specified value." />
				<function name="COUNT" param="value1,value2,..."
					description="This function returns the number of cells that contain numbers." />
				<function name="COUNTA" param="value1,value2,..."
					description="This function returns the number of number of cells that contain numbers, text, or logical values." />
				<function name="COUNTBLANK" param="cellrange"
					description="This function returns the number of empty (or blank) cells in a range of cells on a sheet." />
				<function name="COUNTIF" param="cellrange,condition"
					description="This function returns the number of cells that meet a certain condition" />
				<function name="COUNTIFS" param="cellrange,condition"
					description="This function returns the number of cells that meet multiple conditions." />
				<function name="COUPDAYBS"
					param="settlement,maturity,frequency,basis"
					description="This function calculates the number of days from the beginning of the coupon period to the settlement date." />
				<function name="COUPDAYS"
					param="settlement,maturity,frequency,basis"
					description="This function returns the number of days in the coupon period that contains the settlement date." />
				<function name="COUPDAYSNC"
					param="settlement,maturity,frequency,basis"
					description="This function calculates the number of days from the settlement date to the next coupon date." />
				<function name="COUPNCD" param="settlement,maturity,frequency,basi"
					description="This function returns a date number of the next coupon date after the settlement date." />
				<function name="COUPNUM" param="settlement,maturity,frequency,basis"
					description="This function returns the number of coupons due between the settlement date and maturity date." />
				<function name="COUPPCD" param="settlement,maturity,frequency,basis"
					description="This function returns a date number of the previous coupon date before the settlement date." />
				<function name="COVAR" param="array1,array2"
					description="This function returns the covariance, which is the average of the products of deviations for each data point pair in two sets of numbers." />
				<function name="CRITBINOM" param="n,p,alpha"
					description="This function returns the criterion binomial, the smallest value for which the cumulative binomial distribution is greater than or equal to a criterion value." />
				<function name="CUMIPMT"
					param="rate,nper,pval,startperiod,endperiod,paytype"
					description="This function returns the cumulative interest paid on a loan between the starting and ending periods." />
				<function name="CUMPRINC"
					param="rate,nper,pval,startperiod,endperiod,paytype"
					description="This function returns the cumulative principal paid on a loan between the start and end periods." />
				<function name="DATE" param="year,month,day"
					description="This function returns the DateTime object for a particular date, specified by the year, month, and day." />
				<function name="DATEDIF" param="date1,date2,outputcode"
					description="This function returns the number of days, months, or years between two dates." />
				<function name="DATEVALUE" param="date_string"
					description="This function returns a DateTime object of the specified date." />
				<function name="DAVERAGE" param="database, field, criteria"
					description="This function calculates the average of values in a column of a list or database that match the specified conditions." />
				<function name="DAY" param="date"
					description="This function returns the day number of the month (integer 1 to 31) that corresponds to the specified date." />
				<function name="DAYS360" param="startdate,enddate,method"
					description="This function returns the number of days between two dates based on a 360-day year." />
				<function name="DB" param="cost,salvage,life,period,month"
					description="This function calculates the depreciation of an asset for a specified period using the fixed‑declining balance method" />
				<function name="DCOUNT" param="database, field, criteria"
					description="This function counts the cells that contain numbers in a column of a list or database that match the specified conditions" />
				<function name="DCOUNTA" param="database, field, criteria"
					description="This function counts the non-blank cells in a column of a list or database that match the specified conditions" />
				<function name="DDB" param="cost,salvage,life,period,factor"
					description="This function calculates the depreciation of an asset for a specified period using the double-declining balance method or another method you specify." />
				<function name="DEC2BIN" param="number,places"
					description="This function converts a decimal number to a binary number." />
				<function name="DEC2HEX" param="number,places"
					description="This function converts a decimal number to a hexadecimal number" />
				<function name="DEC2OCT" param="number,places"
					description="This function converts a decimal number to an octal number" />
				<function name="DEGREES" param="angle"
					description="This function converts the specified value from radians to degrees" />
				<function name="DELTA" param="value1,value2"
					description="This function identifies whether two values are equal. Returns 1 if they are equal; returns 0 otherwise." />
				<function name="DEVSQ" param="value1,value2, ..."
					description="This function calculates the sum of the squares of deviations of data points (or of an array of data points) from their sample mean." />
				<function name="DGET" param="database, field, criteria"
					description="This function extracts a single value from a column of a list or database that matches the specified conditions." />
				<function name="DISC" param="settle,mature,pricep,redeem,basis"
					description="This function calculates the discount rate for a security." />
				<function name="DMAX" param="database, field, criteria"
					description="This function returns the largest number in a column of a list or database that matches the specified conditions." />
				<function name="DMIN" param="database, field, criteria"
					description="This function returns the smallest number in a column of a list or database that matches the specified conditions." />
				<function name="DOLLAR" param="value,digits"
					description="This function converts a number to text using currency format, with the decimals rounded to the specified place." />
				<function name="DOLLARDE" param="fractionaldollar,fraction"
					description="This function converts a fraction dollar price to a decimal dollar price." />
				<function name="DOLLARFR" param="decimaldollar,fraction"
					description="This function converts a decimal number dollar price to a fraction dollar price." />
				<function name="DPRODUCT" param="database, field, criteria"
					description="This function multiplies the values in a column of a list or database that match the specified conditions." />
				<function name="DSTDEV" param="database, field, criteria"
					description="This function estimates the standard deviation of a population based on a sample by using the numbers in a column of a list or database that match the specified conditions." />
				<function name="DSTDEVP" param="database, field, criteria"
					description="This function calculates the standard deviation of a population based on the entire population using the numbers in a column of a list or database that match the specified conditions." />
				<function name="DSUM" param="database, field, criteria"
					description="This function adds the numbers in a column of a list or database that match the specified conditions." />
				<function name="DURATION"
					param="settlement,maturity,coupon,yield,frequency,basis"
					description="This function returns the Macauley duration for an assumed par value of $100." />
				<function name="DVAR" param="database, field, criteria"
					description="This function estimates the variance of a population based on a sample by using the numbers in a column of a list or database that match the specified conditions." />
				<function name="DVARP" param="database, field, criteria"
					description="This function calculates the variance of a population based on the entire population by using the numbers in a column of a list or database that match the specified conditions." />
				<function name="EDATE" param="startdate,months"
					description="This function calculates the date that is the indicated number of months before or after a specified date." />
				<function name="EFFECT" param="nomrate,comper"
					description="This function calculates the effective annual interest rate for a given nominal annual interest rate and the number of compounding periods per year." />
				<function name="EOMONTH" param="startdate,months"
					description="This function calculates the date for the last day of the month (end of month) that is the indicated number of months before or after the starting date." />
				<function name="ERF" param="limit,upperlimit"
					description="This function calculates the error function integrated between a lower and an upper limit." />
				<function name="ERFC" param="lowerlimit"
					description="This function calculates the complementary error function integrated between a lower limit and infinity." />
				<function name="ERROR.TYPE" param="errorvalue"
					description="This function returns a number corresponding to one of the error values." />
				<function name="EURO" param="code"
					description="This function returns the equivalent of one Euro based on the ISO currency code." />
				<function name="EUROCONVERT"
					param="currency,source,target,fullprecision,triangulation"
					description="This function converts currency from a Euro member currency (including Euros) to another Euro member currency (including Euros)." />
				<function name="EVEN" param="value"
					description="This function rounds the specified value up to the nearest even integer." />
				<function name="EXACT" param="text1,text2"
					description="This function returns true if two strings are the same; otherwise, false." />
				<function name="EXP" param="value"
					description="This function returns e raised to the power of the specified value." />
				<function name="EXPONDIST" param="value,lambda,cumulative"
					description="This function returns the exponential distribution or the probability density" />
				<function name="FACT" param="number"
					description="This function calculates the factorial of the specified number." />
				<function name="FACTDOUBLE" param="number"
					description="This function calculates the double factorial of the specified number." />
				<function name="FALSE" param=""
					description="This function returns the value for logical FALSE." />
				<function name="FDIST" param="value,degnum,degden"
					description="This function calculates the F probability distribution, to see degrees of diversity between two sets of data." />
				<function name="FIND" param="findtext,intext,start"
					description="This function finds one text value within another and returns the text value’s position in the text you searched." />
				<function name="FINV" param="p,degnum,degden"
					description="This function returns the inverse of the F probability distribution." />
				<function name="FISHER" param="value"
					description="This function returns the Fisher transformation for a specified value" />
				<function name="FISHERINV" param="value"
					description="This function returns the inverse of the Fisher transformation for a specified value." />
				<function name="FIXED" param="num,digits,notcomma"
					description="This function rounds a number to the specified number of decimal places, formats the number in decimal format using a period and commas (if so specified), and returns the result as text." />
				<function name="FLOOR" param="value,signif"
					description="This function rounds a number down to the nearest multiple of a specified value." />
				<function name="FORECAST" param="value,Yarray,Xarray"
					description="This function calculates a future value using existing values." />
				<function name="FREQUENCY" param="dataarray,binarray"
					description="This function calculates how often values occur within a range of values. This function returns a vertical array of numbers" />
				<function name="FTEST" param="array1,array2"
					description="This function returns the result of an F-test, which returns the one-tailed probability that the variances in two arrays are not significantly different." />
				<function name="FV" param="rate,numper,paymt,pval,type"
					description="This function returns the future value of an investment based on a present value, periodic payments, and a specified interest rate." />
				<function name="FVSCHEDULE" param="principal,schedule"
					description="This function returns the future value of an initial principal after applying a series of compound interest rates. Calculate future value of an investment with a variable or adjustable rate." />
				<function name="GAMMADIST" param="x,alpha,beta,cumulative"
					description="This function returns the gamma distribution." />
				<function name="GAMMAINV" param="p,alpha,beta"
					description="This function returns the inverse of the gamma cumulative distribution." />
				<function name="GAMMALN" param="value"
					description="This function returns the natural logarithm of the Gamma function, G(x)." />
				<function name="GCD" param="number1,number2"
					description="This function returns the greatest common divisor of two numbers." />
				<function name="GEOMEAN" param="value1,value2,..."
					description="This function returns the geometric mean of a set of positive data." />
				<function name="GESTEP" param="number,step"
					description="This function, greater than or equal to step, returns an indication of whether a number is equal to a threshold." />
				<function name="GROWTH" param="y,x,newx,constant"
					description="This function calculates predicted exponential growth. This function returns the y values for a series of new x values that are specified by using existing x and y values." />
				<function name="HARMEAN" param="value1,value2,..."
					description="This function returns the harmonic mean of a data set." />
				<function name="HEX2BIN" param="number, places"
					description="This function converts a hexadecimal number to a binary number." />
				<function name="HEX2DEC" param="number"
					description="This function converts a hexadecimal number to a decimal number." />
				<function name="HEX2OCT" param="number, places"
					description="This function converts a hexadecimal number to an octal number." />
				<function name="HLOOKUP" param="value,array,row,approx"
					description="This function searches for a value in the top row and then returns a value in the same column from a specified row." />
				<function name="HOUR" param="time"
					description="This function returns the hour that corresponds to a specified time." />
				<function name="HYPGEOMDIST" param="x,n,M,N"
					description="This function returns the hypergeometric distribution." />
				<function name="IF" param="valueTest,valueTrue,valueFalse"
					description="This function performs a comparison and returns one of two provided values based on that comparison." />
				<function name="IFERROR" param="value,error"
					description="This function evaluates a formula and returns a value you provide if there is an error or the formula result." />
				<function name="IMABS" param="complexnum"
					description="This function returns the absolute value or modulus of a complex number." />
				<function name="IMAGINARY" param="complexnum"
					description="This function returns the imaginary coefficient of a complex number." />
				<function name="IMARGUMENT" param="complexnum"
					description="This function returns the argument theta, which is an angle expressed in radians." />
				<function name="IMCONJUGATE" param="complexnum"
					description="This function returns the complex conjugate of a complex number." />
				<function name="IMCOS" param="complexnum"
					description="This function returns the cosine of a complex number." />
				<function name="IMDIV" param="complexnum,complexdenom"
					description="This function returns the quotient of two complex numbers." />
				<function name="IMEXP" param="complexnum"
					description="This function returns the exponential of a complex number." />
				<function name="IMLN" param="complexnum"
					description="This function returns the natural logarithm of a complex number." />
				<function name="IMLOG2" param="complexnum"
					description="This function returns the base-2 logarithm of a complex number." />
				<function name="IMLOG10" param="complexnum"
					description="This function returns the common logarithm of a complex number." />
				<function name="IMPOWER" param="complexnum,powernum"
					description="This function returns a complex number raised to a power." />
				<function name="IMPRODUCT" param="complexnum1,complexnum2, ..."
					description="This function returns the product of up to 29 complex numbers in the x+yi or x+yj text format" />
				<function name="IMREAL" param="complexnum"
					description="This function returns the real coefficient of a complex number in the x+yi or x+yj text format." />
				<function name="IMSIN" param="complexnum"
					description="This function returns the sine of a complex number in the x+yi or x+yj text format." />
				<function name="IMSQRT" param="complexnum"
					description="This function returns the square root of a complex number in the x+yi or x+yj text format." />
				<function name="IMSUB" param="complexnum1,complexnum2"
					description="This function returns the difference of two complex numbers in the x+yi or x+yj text format." />
				<function name="IMSUM" param="complexnum1,complexnum2, ..."
					description="This function returns the sum of two or more complex numbers in the x+yi or x+yj text format." />
				<function name="INDEX" param="return,row,col,area"
					description="This function returns a value or the reference to a value from within an array or range." />
				<function name="INT" param="value"
					description="This function rounds a specified number down to the nearest integer." />
				<function name="INTERCEPT" param="dependent,independent"
					description="This function returns the coordinates of a point at which a line intersects the y-axis, by using existing x values and y values." />
				<function name="INTRATE" param="settle,mature,invest,redeem,basis"
					description="This function calculates the interest rate for a fully invested security." />
				<function name="IPMT" param="rate,per,nper,pval,fval,type"
					description="This function calculates the payment of interest on a loan." />
				<function name="IRR" param="arrayvals,estimate"
					description="This function returns the internal rate of return for a series of cash flows represented by the numbers in an array." />
				<function name="ISBLANK" param="cellreference"
					description="This function tests whether a value, an expression, or contents of a referenced cell is empty." />
				<function name="ISERR" param="cellreference"
					description="This function, Is Error Other Than Not Available, tests whether a value, an expression, or contents of a referenced cell has an error other than not available (#N/A)." />
				<function name="ISERROR" param="cellreference"
					description="This function, Is Error of Any Kind, tests whether a value, an expression, or contents of a referenced cell has an error of any kind." />
				<function name="ISEVEN" param="cellreference"
					description="This function, Is Number Even, tests whether a value, an expression, or contents of a referenced cell is even." />
				<function name="ISLOGICAL" param="cellreference"
					description="This function tests whether a value, an expression, or contents of a referenced cell is a logical (Boolean) value." />
				<function name="ISNA" param="cellreference"
					description="This function, Is Not Available, tests whether a value, an expression, or contents of a referenced cell has the not available (#N/A) error value." />
				<function name="ISNONTEXT" param="cellreference"
					description="This function tests whether a value, an expression, or contents of a referenced cell has any data type other than text." />
				<function name="ISNUMBER" param="cellreference"
					description="This function tests whether a value, an expression, or contents of a referenced cell has numeric data." />
				<function name="ISODD" param="cellreference"
					description="This function, Is Number Odd, tests whether a value, an expression, or contents of a referenced cell has numeric data." />
				<function name="ISPMT" param="rate,per,nper,pv"
					description="This function calculates the interest paid during a specific period of an investment." />
				<function name="ISREF" param="cellreference"
					description="This function, Is Reference, tests whether a value, an expression, or contents of a referenced cell is a reference to another cell." />
				<function name="ISTEXT" param="cellreference"
					description="This function tests whether a value, an expression, or contents of a referenced cell has text data." />
				<function name="KURT" param="value1,value2,value3,value4,..."
					description="This function returns the kurtosis of a data set." />
				<function name="LARGE" param="array,n"
					description="This function returns the nth largest value in a data set, where n is specified." />
				<function name="LCM" param="number1,number2"
					description="This function returns the least common multiple of two numbers." />
				<function name="LEFT" param="mytext,num_chars"
					description="This function returns the specified leftmost characters from a text value." />
				<function name="LEN" param="value"
					description="This function returns the length of, the number of characters in, a text string." />
				<function name="LINEST" param="y,x,constant,stats"
					description="This function calculates the statistics for a line." />
				<function name="LN" param="value"
					description="This function returns the natural logarithm of the specified number." />
				<function name="LOG" param="number,base"
					description="This function returns the logarithm base Y of a number X." />
				<function name="LOG10" param="value"
					description="This function returns the logarithm base 10 of the number given." />
				<function name="LOGEST" param="y,x,constant,stats"
					description="This function calculates an exponential curve that fits the data and returns an array of values that describes the curve." />
				<function name="LOGINV" param="prob,mean,stdev"
					description="This function returns the inverse of the lognormal cumulative distribution function of x, where LN(x) is normally distributed with the specified mean and standard deviation." />
				<function name="LOGNORMDIST" param="x,mean,stdev"
					description="This function returns the cumulative natural log normal distribution of x, where LN(x) is normally distributed with the specified mean and standard deviation. Analyze data that has been logarithmically transformed with this function." />
				<function name="LOOKUP"
					param="lookupvalue,lookupvector,resultvector"
					description="This function searches for a value and returns a value from the same location in a second area." />
				<function name="LOWER" param="string"
					description="This function converts text to lower case letters." />
				<function name="MATCH" param="value1,array,type"
					description="This function returns the relative position of a specified item in a range." />
				<function name="MAX" param="value1,value2,..."
					description="This function returns the maximum value, the greatest value, of all the values in the arguments." />
				<function name="MAXA" param="value1,value2,..."
					description="This function returns the largest value in a list of arguments, including text and logical values." />
				<function name="MDETERM" param="array"
					description="This function returns the matrix determinant of an array." />
				<function name="MDURATION"
					param="settlement,maturity,coupon,yield,frequency,basis"
					description="This function calculates the modified Macauley duration of a security with an assumed par value of $100." />
				<function name="MEDIAN" param="value1,value2,..."
					description="This function returns the median, the number in the middle of the provided set of numbers; that is, half the numbers have values that are greater than the median, and half have values that are less than the median." />
				<function name="MID" param="text,start_num,num_chars"
					description="This function returns the requested number of characters from a text string starting at the position you specify." />
				<function name="MIN" param="value1,value2,..."
					description="This function returns the minimum value, the least value, of all the values in the arguments" />
				<function name="MINA" param="value1,value2,..."
					description="This function returns the minimum value in a list of arguments, including text and logical values." />
				<function name="MINUTE" param="time"
					description="This function returns the minute corresponding to a specified time." />
				<function name="MINVERSE" param="array"
					description="This function returns the inverse matrix for the matrix stored in an array." />
				<function name="MIRR" param="arrayvals,payment_int,income_int"
					description="This function returns the modified internal rate of return for a series of periodic cash flows." />
				<function name="MMULT" param="array1,array2"
					description="This function returns the matrix product for two arrays." />
				<function name="MOD" param="dividend,divisor"
					description="This function returns the remainder of a division operation." />
				<function name="MODE" param="value1,value2,..."
					description="This function returns the most frequently occurring value in a set of data." />
				<function name="MONTH" param="date"
					description="This function returns the month corresponding to the specified date value." />
				<function name="MROUND" param="number,multiple"
					description="This function returns a number rounded to the desired multiple." />
				<function name="MULTINOMIAL" param="value1,value2,..."
					description="This function calculates the ratio of the factorial of a sum of values to the product of factorials." />
				<function name="N" param="value"
					description="This function returns a value converted to a number." />
				<function name="NA" param=""
					description="This function returns the error value #N/A that means not available." />
				<function name="NEGBINOMDIST" param="x,r,p"
					description="This function returns the negative binomial distribution." />
				<function name="NETWORKDAYS" param="startdate,enddate,holidays"
					description="This function returns the total number of complete working days between the start and end dates." />
				<function name="NOMINAL" param="effrate,comper"
					description="This function returns the nominal annual interest rate for a given effective rate and number of compounding periods per year." />
				<function name="NORMDIST" param="x,mean,stdev,cumulative"
					description="This function returns the normal cumulative distribution for the specified mean and standard deviation." />
				<function name="NORMINV" param="prob,mean,stdev"
					description="This function returns the inverse of the normal cumulative distribution for the given mean and standard deviation." />
				<function name="NORMSDIST" param="value"
					description="This function returns the standard normal cumulative distribution function." />
				<function name="NORMSINV" param="prob"
					description="This function returns the inverse of the standard normal cumulative distribution. The distribution has a mean of zero and a standard deviation of one." />
				<function name="NOT" param="value"
					description="This function reverses the logical value of its argument." />
				<function name="NOW" param=""
					description="This function returns the current date and time." />
				<function name="NPER" param="rate,paymt,pval,fval,type"
					description="This function returns the number of periods for an investment based on a present value, future value, periodic payments, and a specified interest rate." />
				<function name="NPV" param="discount,value1,value2,..."
					description="This function calculates the net present value of an investment by using a discount rate and a series of future payments and income." />
				<function name="OCT2BIN" param="number,places"
					description="This function converts an octal number to a binary number." />
				<function name="OCT2DEC" param="number"
					description="This function converts an octal number to a decimal number." />
				<function name="OCT2HEX" param="number,places"
					description="This function converts an octal number to a hexadecimal number." />
				<function name="ODD" param="value"
					description="This function rounds the specified value up to the nearest odd integer." />
				<function name="ODDFPRICE"
					param="settle,maturity,issue,first,rate,yield,redeem,freq,basis"
					description="This function calculates the price per $100 face value of a security with an odd first period." />
				<function name="ODDFYIELD"
					param="settle,maturity,issue,first,rate,price,redeem,freq,basis"
					description="This function calculates the yield of a security with an odd first period." />
				<function name="ODDLPRICE"
					param="settle,maturity,last,rate,yield,redeem,freq,basis"
					description="This function calculates the price per $100 face value of a security with an odd last coupon period." />
				<function name="ODDLYIELD"
					param="settle,maturity,last,rate,price,redeem,freq,basis"
					description="This function calculates the yield of a security with an odd last period." />
				<function name="OFFSET" param="reference,rows,cols,height,width"
					description="This function returns a reference to a range. The range is a specified number of rows and columns from a cell or range of cells. The function returns a single cell or a range of cells." />
				<function name="OR" param="argument1,argument2..."
					description="This function calculates logical OR. It returns TRUE if any of its arguments are true; otherwise, returns FALSE if all arguments are false." />
				<function name="PEARSON" param="array_ind,array_dep"
					description="This function returns the Pearson product moment correlation coefficient, a dimensionless index between -1.0 to 1.0 inclusive indicative of the linear relationship of two data sets." />
				<function name="PERCENTILE" param="array,n"
					description="This function returns the nth percentile of values in a range." />
				<function name="PERCENTRANK" param="array,n,sigdig"
					description="This function returns the rank of a value in a data set as a percentage of the data set." />
				<function name="PERMUT" param="k,n"
					description="This function returns the number of possible permutations for a specified number of items." />
				<function name="PI" param=""
					description="This function returns PI as 3.1415926536." />
				<function name="PMT" param="rate,nper,pval,fval,type"
					description="This function returns the payment amount for a loan given the present value, specified interest rate, and number of terms." />
				<function name="POISSON" param="nevents,mean,cumulative"
					description="This function returns the Poisson distribution." />
				<function name="POWER" param="number,power"
					description="This function raises the specified number to the specified power." />
				<function name="PPMT" param="rate,per,nper,pval,fval,type"
					description="This function returns the amount of payment of principal for a loan given the present value, specified interest rate, and number of terms." />
				<function name="PRICE"
					param="settlement,maturity,rate,yield,redeem,frequency,basis"
					description="This function calculates the price per $100 face value of a periodic interest security" />
				<function name="PRICEDISC"
					param="settle,mature,discount,redeem,basis"
					description="This function returns the price per $100 face value of a discounted security." />
				<function name="PRICEMAT"
					param="settle,mature,issue,rate,yield,basis"
					description="This function returns the price at maturity per $100 face value of a security that pays interest." />
				<function name="PROB" param="array,probs,lower,upper"
					description="This function returns the probability that values in a range are between two limits." />
				<function name="PRODUCT" param="value1,value2,..."
					description="This function multiplies all the arguments and returns the product." />
				<function name="PROPER" param="text"
					description="This function capitalizes the first letter in each word of a text string." />
				<function name="PV" param="rate,numper,paymt,fval,type"
					description="This function returns the present value of an investment based on the interest rate, number and amount of periodic payments, and future value. The present value is the total amount that a series of future payments is worth now." />
				<function name="QUARTILE" param="array,quart"
					description="This function returns which quartile (which quarter or 25 percent) of a data set a value is." />
				<function name="QUOTIENT" param="numerator,denominator"
					description="This function returns the integer portion of a division. Use this to ignore the remainder of a division." />
				<function name="RADIANS" param="value"
					description="This function converts the specified number from degrees to radians." />
				<function name="RADIANS" param="value"
					description="This function converts the specified number from degrees to radians." />
				<function name="RAND" param=""
					description="This function returns an evenly distributed random number between 0 and 1." />
				<function name="RANDBETWEEN" param="lower,upper"
					description="This function returns a random number between the numbers you specify." />
				<function name="RANK" param="number,array,order"
					description="This function returns the rank of a number in a set of numbers. If you were to sort the set, the rank of the number would be its position in the list." />
				<function name="RATE" param="nper,pmt,pval,fval,type,guess"
					description="This function returns the interest rate per period of an annuity." />
				<function name="RECEIVED"
					param="settle,mature,invest,discount,basis"
					description="This function returns the amount received at maturity for a fully invested security." />
				<function name="REPLACE"
					param="old_text,start_char,num_chars,new_text"
					description="This function replaces part of a text string with a different text string." />
				<function name="REPT" param="text,number"
					description="This function repeats text a specified number of times." />
				<function name="RIGHT" param="text,num_chars"
					description="This function returns the specified rightmost characters from a text value." />
				<function name="ROMAN" param="number,style"
					description="This function converts an arabic numeral to a roman numeral text equivalent." />
				<function name="ROUND" param="value,places"
					description="This function rounds the specified value to the nearest number, using the specified number of decimal places." />
				<function name="ROUNDDOWN" param="value,places"
					description="This function rounds the specified number down to the nearest number, using the specified number of decimal places." />
				<function name="ROUNDUP" param="value,places"
					description="This function rounds the specified number up to the nearest number, using the specified number of decimal places." />
				<function name="ROW" param="reference"
					description="This function returns the number of a row from a reference." />
				<function name="ROWS" param="array"
					description="This function returns the number of rows in an array." />
				<function name="RSQ" param="array_dep,array_ind"
					description="This function returns the square of the Pearson product moment correlation coefficient (R‑squared) through data points in known y’s and known x’s." />
				<function name="SEARCH" param="string1,string2"
					description="This function finds one text string in another text string and returns the index of the starting position of the found text." />
				<function name="SECOND" param="time"
					description="This function returns the seconds (0 to 59) value for a specified time." />
				<function name="SERIESSUM" param="x,n,m,coeff"
					description="This function returns the sum of a power series." />
				<function name="SIGN" param="cellreference"
					description="This function returns the sign of a number or expression." />
				<function name="SIN" param="angle"
					description="This function returns the sine of the specified angle." />
				<function name="SINH" param="value"
					description="This function returns the hyperbolic sine of the specified number." />
				<function name="SKEW" param="number1,number2,..."
					description="This function returns the skewness of a distribution." />
				<function name="SLN" param="cost,salvage,life"
					description="This function returns the straight-line depreciation of an asset for one period." />
				<function name="SLOPE" param="array_dep,array_ind"
					description="This function calculates the slope of a linear regression." />
				<function name="SMALL" param="array,n"
					description="This function returns the nth smallest value in a data set, where n is specified." />
				<function name="SQRT" param="value"
					description="This function returns the positive square root of the specified number." />
				<function name="SQRTPI" param="multiple"
					description="This function returns the positive square root of a multiple of pi (p)." />
				<function name="STANDARDIZE" param="x,mean,stdev"
					description="This function returns a normalized value from a distribution characterized by mean and standard deviation." />
				<function name="STDEVA" param="value1,value2,..."
					description="This function returns the standard deviation for a set of numbers, text, or logical values." />
				<function name="STDEVP" param="value1,value2,..."
					description="This function returns the standard deviation for an entire specified population (of numeric values)." />
				<function name="STDEVPA" param="value1,value2,..."
					description="This function returns the standard deviation for an entire specified population, including text or logical values as well as numeric values." />
				<function name="STEYX" param="array_dep,array_ind"
					description="This function returns the standard error of the predicted y value for each x. The standard error is a measure of the amount of error in the prediction of y for a value of x." />
				<function name="SUBSTITUTE"
					param="text,old_piece,new_piece,instance"
					description="This function substitutes a new string for specified characters in an existing string." />
				<function name="SUBTOTAL" param="functioncode,value1,value2,..."
					description="This function calculates a subtotal of a list of numbers using a specified built-in function." />
				<function name="SUM" param="value1,value2,..."
					description="This function returns the sum of cells or range of cells." />
				<function name="SUMIF" param="array,condition,sumrange"
					description="This function adds the cells using a given criteria." />
				<function name="SUMIFS" param="array,conditionarray,condition,..."
					description="This function adds the cells in a range using multiple criteria." />
				<function name="SUMPRODUCT" param="array1,array2,..."
					description="This function returns the sum of products of cells. Multiplies corresponding components in the given arrays, and returns the sum of those products." />
				<function name="SUMSQ" param="value1,value2,..."
					description="This function returns the sum of the squares of the arguments." />
				<function name="SUMX2MY2" param="array_x,array_y"
					description="This function returns the sum of the difference of the squares of corresponding values in two arrays." />
				<function name="SUMX2PY2" param="array_x,array_y"
					description="This function returns the sum of the sum of squares of corresponding values in two arrays." />
				<function name="SUMXMY2" param="array_x,array_y"
					description="This function returns the sum of the square of the differences of corresponding values in two arrays." />
				<function name="SYD" param="cost,salvage,life,period"
					description="This function returns the sum-of-years’ digits depreciation of an asset for a specified period." />
				<function name="T" param="value"
					description="This function returns the text in a specified cell." />
				<function name="TAN" param="angle"
					description="This function returns the tangent of the specified angle." />
				<function name="TANH" param="value"
					description="This function returns the hyperbolic tangent of the specified number." />
				<function name="TBILLEQ" param="settle,mature,discount"
					description="This function returns the equivalent yield for a Treasury bill (or T-bill)" />
				<function name="TBILLPRICE" param="settle,mature,discount"
					description="This function returns the price per $100 face value for a Treasury bill (or T-bill)." />
				<function name="TBILLYIELD" param="settle,mature,priceper"
					description="This function returns the yield for a Treasury bill (or T-bill)." />
				<function name="TDIST" param="x,deg,tails"
					description="This function returns the probability for the t-distribution." />
				<function name="TEXT" param="value,text"
					description="This function formats a number and converts it to text." />
				<function name="TIME" param="hour,minutes,seconds"
					description="This function returns the TimeSpan object for a specified time." />
				<function name="TIMEVALUE" param="time_string"
					description="This function returns the TimeSpan object of the time represented by a text string." />
				<function name="TINV" param="prog,deg"
					description="This function returns the t-value of the student's t-distribution as a function of the probability and the degrees of freedom." />
				<function name="TODAY" param=""
					description="This function returns the date and time of the current date." />
				<function name="TRANSPOSE" param="array"
					description="This function returns a vertical range of cells as a horizontal range or a horizontal range of cells as a vertical range." />
				<function name="TREND" param="y,x,newx,constant"
					description="This function returns values along a linear trend. This function fits a straight line to the arrays known x and y values. Trend returns the y values along that line for the array of specified new x values." />
				<function name="TRIM" param="text"
					description="This function removes extra spaces from a string and leaves single spaces between words." />
				<function name="TRIMMEAN" param="array,percent"
					description="This function returns the mean of a subset of data excluding the top and bottom data." />
				<function name="TRUE" param=""
					description="This function returns the value for logical TRUE." />
				<function name="TRUNC" param="value,precision"
					description="This function removes the specified fractional part of the specified number." />
				<function name="TTEST" param="array1,array2,tails,type"
					description="This function returns the probability associated with a t-test." />
				<function name="TYPE" param="value"
					description="This function returns the type of value." />
				<function name="UPPER" param="string"
					description="This function converts text to uppercase letters." />
				<function name="VALUE" param="text"
					description="This function converts a text string that is a number to a numeric value." />
				<function name="VAR" param="value1,value2,..."
					description="This function returns the variance based on a sample of a population, which uses only numeric values." />
				<function name="VARA" param="value1,value2,..."
					description="This function returns the variance based on a sample of a population, which includes numeric, logical, or text values." />
				<function name="VARP" param="value1,value2,..."
					description="This function returns variance based on the entire population, which uses only numeric values." />
				<function name="VARPA" param="value1,value2,..."
					description="This function returns variance based on the entire population, which includes numeric, logical, or text values." />
				<function name="VDB"
					param="cost,salvage,life,start,end,factor,switchnot"
					description="This function returns the depreciation of an asset for any period you specify using the variable declining balance method." />
				<function name="VLOOKUP" param="value,array,colindex,approx"
					description="This function searches for a value in the leftmost column and returns a value in the same row from a column you specify." />
				<function name="WEEKDAY" param="date,type"
					description="This function returns the number corresponding to the day of the week for a specified date." />
				<function name="WEEKNUM" param="date,weektype"
					description="This function returns a number that indicates the week of the year numerically." />
				<function name="WEIBULL" param="x,alpha,beta,cumulative"
					description="This function returns the two-parameter Weibull distribution, often used in reliability analysis." />
				<function name="WORKDAY" param="startdate,numdays,holidays"
					description="This function returns the number of working days before or after the starting date." />
				<function name="XIRR" param="values,dates,guess"
					description="This function calculates the internal rate of return for a schedule of cash flows that may not be periodic." />
				<function name="XNPV" param="rate,values,dates"
					description="This function calculates the net present value for a schedule of cash flows that may not be periodic." />
				<function name="YEAR" param="date"
					description="This function returns the year as an integer for a specified date." />
				<function name="YEARFRAC" param="startdate,enddate,basis"
					description="This function returns the fraction of the year represented by the number of whole days between the start and end dates." />
				<function name="YIELD"
					param="settle,maturity,rate,price,redeem,frequency,basis"
					description="This function calculates the yield on a security that pays periodic interest." />
				<function name="YIELDDISC"
					param="settle,maturity,price,redeem,basis"
					description="This function calculates the annual yield for a discounted security." />
				<function name="YIELDMAT"
					param="settle,maturity,issue,issrate,price,basis"
					description="This function calculates the annual yield of a security that pays interest at maturity." />
				<function name="ZTEST" param="array,x,sigma"
					description="This function returns the significance value of a z-test. The z-test generates a standard score for x with respect to the set of data and returns the two-tailed probability for the normal distribution." />
			</div>
		</div>

		<div class="fileMenu hidden setting-container">
			<div class="menu-panel">
				<ul>
					<li id="file-menu-goback" class="nohover-effect">
						<div class="goback-image" />
					</li>
					<li><a id="filemenu-new" href="#" class="localize">@fileMenu.items.new@</a></li>
					<li><a href="#open-page" class="localize">@fileMenu.items.open.title@</a></li>
					<li><a href="#save-page" class="localize">@fileMenu.items.save@</a></li>
					<li><a href="#print-setting" class="localize">@fileMenu.items.print.title@</a></li>
					<li><a id="filemenu-close" href="#" class="localize">@fileMenu.items.close@</a></li>
					<li>
						<div class="gcui-ribbon-listseparator"></div>
					</li>
					<li><a href="#app-setting" class="localize">@fileMenu.items.settings.title@</a></li>
				</ul>

			</div>
			<div class="content-panel">
				<div class="menu-title">
					<span></span>
				</div>
				<div class="content-view">
					<div id="open-page" class="content-item">
						<div>
							<span class="option-title">Password:</span> <br> <input
								class="option-input" id="openPassword" type="password">
						</div>
						<div class="left-view">
							<ul>
								<li><a href="#recent-workbooks-page"
									data-bind="text: res.fileMenu.recentWorkbooks">Recent
										Workbooks</a></li>
								<li><a href="#computer-page"
									data-bind="text: res.fileMenu.computer">Computer</a></li>
							</ul>
						</div>
						<div class="right-view">
							<div id="recent-workbooks-page">
								<div class="submenu-title"
									data-bind="text: res.fileMenu.recentWorkbooks">Recent
									Workbooks</div>
							</div>
							<div id="computer-page">
								<div class="submenu-title"
									data-bind="text: res.fileMenu.computer">Computer</div>
								<div class="sub-title"
									data-bind="text: res.fileMenu.currentFolder">Current
									Folder</div>
								<div class="current-folder"></div>
								<div class="sub-title"
									data-bind="text: res.fileMenu.recentFolders">Recent
									Folders</div>
								<div class="recent-folders"></div>
								<div class="iconbutton" id="open-browser" data-action="open">
									<div class="iconbutton-big-image browse-button-icon"></div>
									<span data-bind="text: res.fileMenu.browse">Browse</span>
								</div>
							</div>
						</div>
					</div>
					<div id="save-page" class="content-item">
						<div>
							<div>
								<span class="option-title">Filename:</span> <br> <input
									class="option-input" id="saveAsFileName" autofocus
									placeholder="export">
							</div>
							<div>
								<span class="option-title">Password:</span> <br> <input
									class="option-input" id="savePassword" type="password">
							</div>
							<div id="download">
								<span class="option-title">Save File:</span> <br> <a
									id="downloadlink" data-contextmenu="true">Right Click To
									Download Linked File As...</a>
							</div>
							<div class="radio">
								<label><input type="radio" name="optradio"
									id="saveExcel" checked>EXCEL</label>
							</div>
							<div class="radio">
								<label><input type="radio" name="optradio" id="saveJson">JSON</label>
							</div>
							<div class="iconbutton" id="browser-save">
								<div class="iconbutton-big-image browse-button-icon"></div>
								<span>Save</span>
							</div>
						</div>
					</div>
					<div id="app-setting">
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.general.title@</span></span>
							</div>
							<div class="pane-group-content move-right">
								<div class="pane-row">
									<label class="checkbox-inline" data-name="canUserDragDrop"><input
										type="checkbox"><span class="localize">@spreadTab.general.allowDragDrop@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="canUserDragFill"><input
										type="checkbox"><span class="localize">@spreadTab.general.allowDragFill@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="allowZoom"><input
										type="checkbox"><span class="localize">@spreadTab.general.allowZoom@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="allowOverflow"><input
										type="checkbox"><span class="localize">@spreadTab.general.allowOverflow@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="showDragFillSmartTag"><input
										type="checkbox"><span class="localize">@spreadTab.general.showDragFillSmartTag@</span></label>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.sheets.title@</span></span>
							</div>
							<div class="pane-group-content">
								<div class="pane-row move-right">
									<label class="pane-label localize">@spreadTab.sheets.sheetName@</label>
									<div class="btn-group" data-name="sheetName">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown wide-dropdown"
											data-toggle="dropdown">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu text-align" id="sheetNameList">
										</ul>
									</div>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline sheetVisible"
										data-name="sheetVisible"><input type="checkbox"><span
										class="localize">@spreadTab.sheets.sheetVisible@</span></label>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.scrollBar.title@</span></span>
							</div>
							<div class="pane-group-content move-right">
								<div class="pane-row">
									<label class="checkbox-inline"
										data-name="showVerticalScrollbar"><input
										type="checkbox"><span class="localize">@spreadTab.scrollBar.showVertical@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline"
										data-name="showHorizontalScrollbar"><input
										type="checkbox"><span class="localize">@spreadTab.scrollBar.showHorizontal@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="scrollbarMaxAlign"><input
										type="checkbox"><span class="localize">@spreadTab.scrollBar.maxAlign@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="scrollbarShowMax"><input
										type="checkbox"><span class="localize">@spreadTab.scrollBar.showMax@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="scrollIgnoreHidden"><input
										type="checkbox"><span class="localize">@spreadTab.scrollBar.scrollIgnoreHidden@</span></label>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.tabStip.title@</span></span>
							</div>
							<div class="pane-group-content move-right">
								<div class="pane-row">
									<label class="checkbox-inline" data-name="tabStripVisible"><input
										type="checkbox"><span class="localize">@spreadTab.tabStip.visible@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="newTabVisible"><input
										type="checkbox"><span class="localize">@spreadTab.tabStip.newTabVisible@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="tabEditable"><input
										type="checkbox"><span class="localize">@spreadTab.tabStip.editable@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="showTabNavigation"><input
										type="checkbox"><span class="localize">@spreadTab.tabStip.showTabNavigation@</span></label>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.color.title@</span></span>
							</div>
							<div class="pane-group-content color-setting move-right">
								<div class="pane-row">
									<label class="pane-label localize">@spreadTab.color.spreadBackcolor@</label>
									<div class="btn-group pane-color-picker"
										data-name="spreadBackcolor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(255, 255, 255);"></div>
										</button>
									</div>
								</div>
								<div class="pane-row">
									<label class="pane-label localize">@spreadTab.color.grayAreaBackcolor@</label>
									<div class="btn-group pane-color-picker"
										data-name="grayAreaBackcolor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(0, 0, 0);"></div>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.spreadTheme.title@</span></span>
							</div>
							<div class="pane-group-content move-right">
								<div class="pane-row">
									<label class="pane-label narrow-width localize">@spreadTab.spreadTheme.theme.title@</label>
									<div class="btn-group" data-name="spreadTheme">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown wide-dropdown"
											data-toggle="dropdown">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu text-align">
											<li><a class="text localize"
												data-value="css/gc.spread.sheets.10.0.0.css">@spreadTab.spreadTheme.theme.option.spreadJS@</a>
											</li>
											<li class="default"><a class="text localize"
												data-value="css/gc.spread.sheets.excel2013white.10.0.0.css">@spreadTab.spreadTheme.theme.option.excel2013White@</a>
											</li>
											<li><a class="text localize"
												data-value="css/gc.spread.sheets.excel2013lightGray.10.0.0.css">@spreadTab.spreadTheme.theme.option.excel2013LightGray@</a>
											</li>
											<li><a class="text localize"
												data-value="css/gc.spread.sheets.excel2013darkGray.10.0.0.css">@spreadTab.spreadTheme.theme.option.excel2013DarkGray@</a>
											</li>
											<li><a class="text localize"
												data-value="css/gc.spread.sheets.excel2016colorful.10.0.0.css">@spreadTab.spreadTheme.theme.option.excel2016Colorful@</a>
											</li>
											<li><a class="text localize"
												data-value="css/gc.spread.sheets.excel2016darkGray.10.0.0.css">@spreadTab.spreadTheme.theme.option.excel2016DarkGray@</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.tip.title@</span></span>
							</div>
							<div class="pane-group-content move-right">
								<div class="move-left">
									<div class="pane-row">
										<label class="pane-label localize narrow-width">@spreadTab.tip.scrollTip.title@</label>
										<div class="btn-group" data-name="scrollTip">
											<button type="button"
												class="btn btn-default dropdown-toggle btn-pane-dropdown"
												data-toggle="dropdown">
												<span class="content"></span> <span class="caret fixed"></span>
											</button>
											<ul class="dropdown-menu text-align">
												<li class="default"><a class="text localize"
													data-value="0">@spreadTab.tip.scrollTip.values.none@</a></li>
												<li><a class="text localize" data-value="1">@spreadTab.tip.scrollTip.values.horizontal@</a>
												</li>
												<li><a class="text localize" data-value="2">@spreadTab.tip.scrollTip.values.vertical@</a>
												</li>
												<li><a class="text localize" data-value="3">@spreadTab.tip.scrollTip.values.both@</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="pane-row">
										<label class="pane-label localize narrow-width">@spreadTab.tip.resizeTip.title@</label>
										<div class="btn-group" data-name="resizeTip">
											<button type="button"
												class="btn btn-default dropdown-toggle btn-pane-dropdown"
												data-toggle="dropdown">
												<span class="content"></span> <span class="caret fixed"></span>
											</button>
											<ul class="dropdown-menu text-align">
												<li class="default"><a class="text localize"
													data-value="0">@spreadTab.tip.resizeTip.values.none@</a></li>
												<li><a class="text localize" data-value="1">@spreadTab.tip.resizeTip.values.column@</a>
												</li>
												<li><a class="text localize" data-value="2">@spreadTab.tip.resizeTip.values.row@</a>
												</li>
												<li><a class="text localize" data-value="3">@spreadTab.tip.resizeTip.values.both@</a>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="showDragDropTip"><input
										type="checkbox"><span class="localize">@spreadTab.tip.showDragDropTip@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="showDragFillTip"><input
										type="checkbox"><span class="localize">@spreadTab.tip.showDragFillTip@</span></label>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.cutCopyIndicator.title@</span></span>
							</div>
							<div class="pane-group-content">
								<div class="pane-row move-right">
									<label class="checkbox-inline"
										data-name="cutCopyIndicatorVisible"><input
										type="checkbox"><span class="localize">@spreadTab.cutCopyIndicator.visible@</span></label>
								</div>
								<div class="pane-row move-right">
									<label class="pane-label localize">@spreadTab.cutCopyIndicator.borderColor@</label>
									<div class="btn-group pane-color-picker"
										data-name="cutCopyIndicatorBorderColor">
										<button type="button"
											class="btn btn-default btn-pane-dropdown">
											<div class="color-picker"
												style="background-color: rgb(0, 0, 0);"></div>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@spreadTab.calculation.title@</span></span>
							</div>
							<div class="pane-group-content">
								<div class="pane-row referencestyle">
									<label class="pane-label localize">@spreadTab.calculation.referenceStyle.title@</label>
									<input type="radio" name="referenceStyle" id="a1style"
										data-value="a1style" checked><label
										class="small-width localize" for="a1style">@spreadTab.calculation.referenceStyle.A1@</label>
									<input type="radio" name="referenceStyle" id="r1c1style"
										data-value="r1c1style"><label
										class="small-width localize" for="r1c1style">@spreadTab.calculation.referenceStyle.R1C1@</label>
								</div>
							</div>
						</div>
					</div>
					<div id="print-setting" class="scrollable">
						<div class="pane-group">
							<div class="pane-group-title">
								<span><span
									class="glyphicon glyphicon-triangle-right pane-glyphicon"></span><span
									class="localize">@printSetting.options.title@</span></span>
							</div>
							<div class="pane-group-content move-right">
								<div class="pane-row">
									<label class="pane-label localize narrow-width">@printSetting.options.range.title@</label>
									<div class="btn-group" data-name="printRange">
										<button type="button"
											class="btn btn-default dropdown-toggle btn-pane-dropdown"
											data-toggle="dropdown">
											<span class="content"></span> <span class="caret fixed"></span>
										</button>
										<ul class="dropdown-menu text-align">
											<li class="default"><a class="text localize"
												data-value="0">@printSetting.options.range.items.activeSheet@</a>
											</li>
											<li><a class="text localize" data-value="1">@printSetting.options.range.items.workbook@</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="printShowBorder"><input
										type="checkbox"><span class="localize">@printSetting.options.showBorder@</span></label>
								</div>
								<div class="pane-row">
									<label class="checkbox-inline" data-name="printShowGridline"><input
										type="checkbox"><span class="localize">@printSetting.options.showGridline@</span></label>
								</div>
								<br />
								<div>
									<div class="pane-row">
										<label class="normal localize">@printSetting.options.headerAndFooter.title@</label>
									</div>
									<div class="pane-row">
										<label class="pane-label narrow-width localize">@printSetting.options.headerAndFooter.header.title@</label>
										<div class="btn-group" data-name="printHeader">
											<button type="button"
												class="btn btn-default dropdown-toggle btn-pane-dropdown"
												data-toggle="dropdown">
												<span class="content"></span> <span class="caret fixed"></span>
											</button>
											<ul class="dropdown-menu text-align" id="printHeaderList">
											</ul>
										</div>
										<button type="button" class="btn btn-default localize"
											id="customPrintHeader" data-name="header">@printSetting.options.headerAndFooter.header.custom@
										</button>
									</div>
									<div class="printFooterSetting">
										<div class="pane-row">
											<label class="pane-label narrow-width  localize">@printSetting.options.headerAndFooter.footer.title@</label>
											<div class="btn-group" data-name="printFooter">
												<button type="button"
													class="btn btn-default dropdown-toggle btn-pane-dropdown"
													data-toggle="dropdown">
													<span class="content"></span> <span class="caret fixed"></span>
												</button>
												<ul class="dropdown-menu text-align" id="printFooterList">
												</ul>
											</div>
											<button type="button" class="btn btn-default localize"
												id="customPrintFooter" data-name="footer">@printSetting.options.headerAndFooter.footer.custom@
											</button>
										</div>
									</div>
									<div class="printHeaderFooterDetail hidden">
										<div class="pane-row text-center">
											<div class="btn-group" data-name="text-content">
												<button type="button"
													class="btn btn-default dropdown-toggle btn-pane-dropdown"
													data-toggle="dropdown">
													<span class="content"></span> <span class="caret fixed"></span>
												</button>
												<ul class="dropdown-menu text-align" id="textContentList">
												</ul>
												<button type="button"
													class="btn btn-default localize localize-tooltip"
													title="@printSetting.options.headerAndFooter.custom.tooltips.insert@"
													id="customInsert">
													@printSetting.options.headerAndFooter.custom.insert@</button>
											</div>
											<div class="btn-space"></div>
											<div class="btn-group" data-name="image-content">
												<button type="button"
													class="btn btn-default dropdown-toggle btn-pane-dropdown localize-tooltip"
													title="@printSetting.options.headerAndFooter.custom.tooltips.imageList@"
													data-toggle="dropdown">
													<span class="content"></span> <span class="caret fixed"></span>
												</button>
												<ul class="dropdown-menu text-align" id="imageContentList">
												</ul>
												<button type="button"
													class="btn btn-default localize localize-tooltip"
													title="@printSetting.options.headerAndFooter.custom.tooltips.insertPicture@"
													id="insertPicture">
													@printSetting.options.headerAndFooter.custom.insertPicture@
												</button>
											</div>
										</div>
										<div class="pane-row height-fit-content">
											<div class="col-md-4">
												<div>
													<label class="normal localize">@printSetting.options.headerAndFooter.custom.left@</label>
												</div>
												<textarea class="printSetting active"
													data-name="left-detail"></textarea>
											</div>
											<div class="col-md-4">
												<div>
													<label class="normal localize">@printSetting.options.headerAndFooter.custom.center@</label>
												</div>
												<textarea class="printSetting" data-name="center-detail"></textarea>
											</div>
											<div class="col-md-4">
												<div>
													<label class="normal localize">@printSetting.options.headerAndFooter.custom.right@</label>
												</div>
												<textarea class="printSetting" data-name="right-detail"></textarea>
											</div>
										</div>
										<br />
										<div class="pane-row text-right button-group">
											<button type="button" class="btn btn-default localize"
												data-name="ok">@dialog.ok@</button>
											<button type="button" class="btn btn-default localize"
												data-name="cancel">@dialog.cancel@</button>
										</div>
									</div>
								</div>
								<br />
								<div id="print-setting-preview">
									<table>
										<tbody>
											<tr>
												<td class="col-md-4"></td>
												<td class="col-md-4 text-center"></td>
												<td class="col-md-4 text-right"></td>
											</tr>
											<tr></tr>
											<tr></tr>
											<tr>
												<td class="col-md-4"></td>
												<td class="col-md-4 text-center"></td>
												<td class="col-md-4 text-right"></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<br />
						<div class="pane-row">
							<button type="button" class="btn btn-primary localize"
								id="printSpread">@printSetting.printButton@</button>
						</div>
						<br /> <br />
					</div>
				</div>
			</div>
		</div>
		<div id="tableStyles" class="table-format-2013">
			<div class="light-format-table table-format-category-div">
				<div class="table-format-label-container">
					<label class="table-format-label localize">@tableTab.tableStyle.groupTitle.light@</label>
				</div>
				<div class="table-style-preview">
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.none@">
						<div class="table-format-icon table-format-none" data-name="none"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light1@">
						<div class="table-format-icon table-format-light1"
							data-name="light1"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light2@">
						<div class="table-format-icon table-format-light2"
							data-name="light2"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light3@">
						<div class="table-format-icon table-format-light3"
							data-name="light3"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light4@">
						<div class="table-format-icon table-format-light4"
							data-name="light4"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light5@">
						<div class="table-format-icon table-format-light5"
							data-name="light5"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light6@">
						<div class="table-format-icon table-format-light6"
							data-name="light6"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light7@">
						<div class="table-format-icon table-format-light7"
							data-name="light7"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light8@">
						<div class="table-format-icon table-format-light8"
							data-name="light8"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light9@">
						<div class="table-format-icon table-format-light9"
							data-name="light9"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light10@">
						<div class="table-format-icon table-format-light10"
							data-name="light10"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light11@">
						<div class="table-format-icon table-format-light11"
							data-name="light11"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light12@">
						<div class="table-format-icon table-format-light12"
							data-name="light12"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light13@">
						<div class="table-format-icon table-format-light13"
							data-name="light13"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light14@">
						<div class="table-format-icon table-format-light14"
							data-name="light14"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light15@">
						<div class="table-format-icon table-format-light15"
							data-name="light15"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light16@">
						<div class="table-format-icon table-format-light16"
							data-name="light16"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light17@">
						<div class="table-format-icon table-format-light17"
							data-name="light17"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light18@">
						<div class="table-format-icon table-format-light18"
							data-name="light18"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light19@">
						<div class="table-format-icon table-format-light19"
							data-name="light19"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light20@">
						<div class="table-format-icon table-format-light20"
							data-name="light20"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.light.light21@">
						<div class="table-format-icon table-format-light21"
							data-name="light21"></div>
					</div>
				</div>
			</div>

			<div class="medium-format-table table-format-category-div">
				<div class="table-format-label-container">
					<label class="table-format-label localize">@tableTab.tableStyle.groupTitle.medium@</label>
				</div>
				<div class="table-style-preview">
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium1@">
						<div class="table-format-icon table-format-medium1"
							data-name="medium1"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium2@">
						<div class="table-format-icon table-format-medium2"
							data-name="medium2"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium3@">
						<div class="table-format-icon table-format-medium3"
							data-name="medium3"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium4@">
						<div class="table-format-icon table-format-medium4"
							data-name="medium4"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium5@">
						<div class="table-format-icon table-format-medium5"
							data-name="medium5"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium6@">
						<div class="table-format-icon table-format-medium6"
							data-name="medium6"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium7@">
						<div class="table-format-icon table-format-medium7"
							data-name="medium7"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium8@">
						<div class="table-format-icon table-format-medium8"
							data-name="medium8"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium9@">
						<div class="table-format-icon table-format-medium9"
							data-name="medium9"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium10@">
						<div class="table-format-icon table-format-medium10"
							data-name="medium10"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium11@">
						<div class="table-format-icon table-format-medium11"
							data-name="medium11"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium12@">
						<div class="table-format-icon table-format-medium12"
							data-name="medium12"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium13@">
						<div class="table-format-icon table-format-medium13"
							data-name="medium13"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium14@">
						<div class="table-format-icon table-format-medium14"
							data-name="medium14"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium15@">
						<div class="table-format-icon table-format-medium15"
							data-name="medium15"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium16@">
						<div class="table-format-icon table-format-medium16"
							data-name="medium16"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium17@">
						<div class="table-format-icon table-format-medium17"
							data-name="medium17"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium18@">
						<div class="table-format-icon table-format-medium18"
							data-name="medium18"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium19@">
						<div class="table-format-icon table-format-medium19"
							data-name="medium19"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium20@">
						<div class="table-format-icon table-format-medium20"
							data-name="medium20"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium21@">
						<div class="table-format-icon table-format-medium21"
							data-name="medium21"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium22@">
						<div class="table-format-icon table-format-medium22"
							data-name="medium22"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium23@">
						<div class="table-format-icon table-format-medium23"
							data-name="medium23"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium24@">
						<div class="table-format-icon table-format-medium24"
							data-name="medium24"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium25@">
						<div class="table-format-icon table-format-medium25"
							data-name="medium25"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium26@">
						<div class="table-format-icon table-format-medium26"
							data-name="medium26"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium27@">
						<div class="table-format-icon table-format-medium27"
							data-name="medium27"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.medium.medium28@">
						<div class="table-format-icon table-format-medium28"
							data-name="medium28"></div>
					</div>
				</div>
			</div>

			<div class="dark-format-table table-format-category-div">
				<div class="table-format-label-container">
					<label class="table-format-label localize">@tableTab.tableStyle.groupTitle.dark@</label>
				</div>
				<div class="table-style-preview">
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark1@">
						<div class="table-format-icon table-format-dark1"
							data-name="dark1"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark2@">
						<div class="table-format-icon table-format-dark2"
							data-name="dark2"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark3@">
						<div class="table-format-icon table-format-dark3"
							data-name="dark3"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark4@">
						<div class="table-format-icon table-format-dark4"
							data-name="dark4"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark5@">
						<div class="table-format-icon table-format-dark5"
							data-name="dark5"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark6@">
						<div class="table-format-icon table-format-dark6"
							data-name="dark6"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark7@">
						<div class="table-format-icon table-format-dark7"
							data-name="dark7"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark8@">
						<div class="table-format-icon table-format-dark8"
							data-name="dark8"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark9@">
						<div class="table-format-icon table-format-dark9"
							data-name="dark9"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark10@">
						<div class="table-format-icon table-format-dark10"
							data-name="dark10"></div>
					</div>
					<div class="table-format-item localize-tooltip"
						title="@tableTab.tableStyle.dark.dark11@">
						<div class="table-format-icon table-format-dark11"
							data-name="dark11"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="successModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <div class="modal-content">
      <div class="modal-body">
        <h3 class="text-primary text-center">Success Saved.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
</body>
        <script type="text/javascript" src="./scripts/html2canvas.js"></script>
