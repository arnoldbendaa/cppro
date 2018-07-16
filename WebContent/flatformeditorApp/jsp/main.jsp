<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html ng-app="flatFormEditorApp">
    <head ng-controller="HeadController">
        <title ng-bind="'Flat Form' + documentTitle">Flat Form</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

        <!-- <link type="text/css" rel="stylesheet" href="<%= path %>/adminApp/css/reset.css" /> -->

        <!-- jQuery -->
            <script src="http://code.jquery.com/jquery-1.8.2.min.js" type="text/javascript"></script>
        
        <script type="text/javascript" src="<%=path %>/libs/jquery-ui/jquery-ui.min.js"></script>
        <link rel="stylesheet" href="<%=path %>/libs/jquery-ui/jquery-ui.css">
        <script type="text/javascript" src="<%=path %>/libs/jquery/jquery.ba-outside-events.min.js"></script>

        <!-- Angular -->
        <script type="text/javascript" src="<%= path %>/libs/ng-file-upload/ng-file-upload-shim.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular.min.js"></script>
<!--  		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.js"></script> -->
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-route.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-animate.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-sanitize.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/1.2.25/angular-cookies.min.js"></script>

        <script type="text/javascript" src="<%= path %>/libs/angularjs/ui-bootstrap/ui-bootstrap-tpls-0.11.2.min.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/angular-flash.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/ng-file-upload/ng-file-upload.min.js"></script>

        <!-- Sweet Alert -->
        <script type="text/javascript" src="<%= path %>/libs/sweetalert/sweet-alert.min.js"></script>
        <link href="<%= path %>/libs/sweetalert/sweet-alert.css" type="text/css" rel="stylesheet" >
        
        <!-- Bootstrap -->
        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/3.2.0/css/bootstrap-submenu.css">
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/font-awesome/4.2.0/css/font-awesome.min.css">
        <script type="text/javascript" src="<%= path %>/libs/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="<%= path %>/libs/bootstrap/bootstrap-slider/css/slider.css">
        <script type="text/javascript" src="<%= path %>/libs/bootstrap/bootstrap-slider/js/bootstrap-slider.js"></script>

                <!-- Wijmo -->
        <link type="text/css" rel="stylesheet" href="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/styles/wijmo.min.css" />
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/controls/wijmo.grid.min.js" type="text/javascript"></script>
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/interop/angular/wijmo.angular.min.js" type="text/javascript"></script>

        <!-- SpreadJS -->
    <link rel="stylesheet" type="text/css" href="<%=path %>/libs/wijmo/SpreadJS/11.0.0/css/gc.spread.sheets.excel2013white.11.0.0.css">
        <link rel="stylesheet" href="<%= path %>/libs/wijmo/SpreadJS/8.40.20151.4/css/sterling/jquery-wijmo.css">
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/gc.spread.sheets.all.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/interop/angularjs/gc.spread.sheets.angularjs.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/plugins/gc.spread.sheets.charts.11.0.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/libs/wijmo/SpreadJS/11.0.0/scripts/interop/gc.spread.excelio.11.0.0.min.js"></script>

        <!-- Wijmo-Angular interop -->
        <script src="<%= path %>/libs/wijmo/Wijmo5/5.20143.32/interop/angular/wijmo.angular.min.js" type="text/javascript"></script>
        
        <!-- Spectrum -->
        <link rel="stylesheet" href="<%= path %>/libs/spectrum/spectrum-bootstrap.css">
        <script type="text/javascript" src="<%= path %>/libs/spectrum/spectrum.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/angularjs/angular-spectrum-colorpicker-master/angular-spectrum-colorpicker.js"></script>

        <!-- SoftproIdeas style -->
        <link rel="stylesheet" href="<%= path %>/css/commons.css?">
        <link rel="stylesheet" href="<%= path %>/css/spreadsheet.css?">
        <link rel="stylesheet" href="<%= path %>/css/spreadsheetMainMenu.css?">
        <link rel="stylesheet" href="<%= path %>/flatformeditorApp/css/main.css?">

        <!-- jsTree -->
        <link rel="stylesheet" href="<%= path %>/libs/jsTree/style.min.css" />
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jstree.js"></script>
        <script type="text/javascript" src="<%= path %>/libs/jsTree/jsTree.directive.js"></script>
        
        <!-- Angular Loading Bar -->
        <link rel='stylesheet' href='<%= path %>/libs/loadingBar/loading-bar.css' type='text/css' media='all' />
        <script type='text/javascript' src='<%= path %>/libs/loadingBar/loading-bar.js'></script>

        <!-- Project basePath, global variable form java to js -->
        <script type="text/javascript">
            var $BASE_PATH = '<%=basePath%>';
            var $BASE_TEMPLATE_PATH = '<%=basePath%>' + 'flatformeditorApp/js/';
            var FLAT_FORM_ID = "${flatFormId}";
        </script>
        <link rel='stylesheet' href='<%= path %>/css/colorpicker.css' type='text/css' media='all' />
       
    </head>
    <body ng-controller="SpreadSheetController" class="bonhamsflatFormApp">
        <div flash-message="3000"></div>
        <context-menu on-show="onShowHandler(event)"></context-menu>
        <veil></veil>
        <%@include file="menu.html"%>        
       
        <div id="container-full">
            <div id="container-navbar" role="navigation" ng-show="isDocumentCompleted" class="ng-hide"> 
        		<div class="timer">Session timeout: {{sessionTime}} min | <a class="logout" ng-click="logout()">logout</a> | <a class="logout" ng-click="exit()">close tab</a></div>
                <tabset>
                    <tab heading="File" disabled="true" ng-click="showMenu()" class="showMenu"></tab>
                    <tab heading="Home" select="onTabSelect('Home')">
                        <%@include file="templates/tabs/home.html"%>
                    </tab>
                    <tab heading="Insert" select="onTabSelect('Insert')">
                        <%@include file="templates/tabs/insert.html"%>
                    </tab>                    
                    <tab heading="Data" select="onTabSelect('Mappings')">
                        <%@include file="templates/tabs/mappings.html"%>
                    </tab>
                    <tab heading="View" select="onTabSelect('View')">
                        <%@include file="templates/tabs/view.html"%>
                    </tab>
                    <tab heading="Settings" select="onTabSelect('Settings')">
                        <%@include file="templates/tabs/settings.html"%>
                    </tab>                 
                    <tab heading="Test" ng-click="tryRunWorkbookTest()" class="runWorkbookTest">
                        <%@include file="templates/tabs/workbookTest.html"%>
                    </tab>                    
                    <tab heading="Chart" ng-click="onTabSelect('Chart')">
                        <%@include file="templates/tabs/chart.html"%>
                    </tab>                    
                </tabset>
            </div>
            <div id="formula-bar">
                <input id="position-box" type="text" disabled="disabled" value=""/>
                <div id="formula-box" contenteditable="true" spellcheck="false"></div>
                <div class="clear-both"></div>
            </div>
            <div id="control-panel">
                <div id="spreadsheet" gcuielement="gcSpread" class="gc-host-none-user-select"></div>
            </div>
            <div id="status-bar" class="clearfix" ng-controller="StatusBarController">
                <div class="zoom-state pull-right">
                    <input id="zoomStatus" class="form-control" type="text" ng-model="zoom.status" zoom-status/>
                </div>
                <div class="zoom-container pull-right">
                    <input id="zoom-controller" data-slider-id='zoomController' type="text"/>
                </div>
            </div>
        </div>
        
        <aside class="main-sidebar" style="position:absolute;top:36px;height:100%;background-color:black;left:{{menuLeft}}px;z-index:1000;">
		    <section class="sidebar navbar" style="height: auto;">
			    <ul>
					<li>
						<div class="item-label" ng-click="dashboardsExpand=!dashboardsExpand" ng-class="{'expanded': dashboardsExpand}">Column Chart</div>
						<ul class="slide ng-hide" ng-show="dashboardsExpand">
							<li><a class="item-label"  ng-click="insertChart('column3D')">column3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('columnClustered')">columnClustered</a></li>
							<li><a class="item-label"  ng-click="insertChart('columnClustered3D')">columnClustered3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('columnStacked')">columnStacked</a></li>
							<li><a class="item-label"  ng-click="insertChart('columnStacked3D')">columnStacked3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('columnStacked100')">columnStacked100</a></li>
							<li><a class="item-label"  ng-click="insertChart('columnStacked1003D')">columnStacked1003D</a></li>
						</ul>
					</li>
					<li>
						<div class="item-label" ng-click="linechart=!linechart" ng-class="{'expanded': linechart}">Line Chart</div>
						<ul class="slide ng-hide" ng-show="linechart">
							<li><a class="item-label"  ng-click="insertChart('line')">line</a></li>
							<li><a class="item-label"  ng-click="insertChart('line3D')">line3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('lineMarkers')">lineMarkers</a></li>
							<li><a class="item-label"  ng-click="insertChart('lineMarkersStacked')">lineMarkersStacked</a></li>
							<li><a class="item-label"  ng-click="insertChart('lineMarkersStacked100')">lineMarkersStacked100</a></li>
							<li><a class="item-label"  ng-click="insertChart('lineStacked')">lineStacked</a></li>
							<li><a class="item-label"  ng-click="insertChart('lineStacked100')">lineStacked100</a></li>
						</ul>
					</li>
					<li>
						<div class="item-label" ng-click="piechart=!piechart" ng-class="{'expanded': piechart}">Pie Chart</div>
						<ul class="slide ng-hide" ng-show="piechart">
							<li><a class="item-label"  ng-click="insertChart('pie')">pie</a></li>
							<li><a class="item-label"  ng-click="insertChart('pie3D')">pie3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('pieExploded')">pieExploded</a></li>
							<li><a class="item-label"  ng-click="insertChart('pieExploded3D')">pieExploded3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('pieOfPie')">pieOfPie</a></li>
						</ul>
					</li>
					<li>
						<div class="item-label" ng-click="areachart=!areachart" ng-class="{'expanded': areachart}">Area Chart</div>
						<ul class="slide ng-hide" ng-show="areachart">
							<li><a class="item-label"  ng-click="insertChart('area')">area</a></li>
							<li><a class="item-label"  ng-click="insertChart('area3D')">area3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('areaStacked')">areaStacked</a></li>
							<li><a class="item-label"  ng-click="insertChart('areaStacked3D')">areaStacked3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('areaStacked100')">areaStacked100</a></li>
							<li><a class="item-label"  ng-click="insertChart('areaStacked1003D')">areaStacked1003D</a></li>
						
						</ul>
					</li>
					<li>
						<div class="item-label" ng-click="barchart=!barchart" ng-class="{'expanded': barchart}">Bar Chart</div>
						<ul class="slide ng-hide" ng-show="barchart">
							<li><a class="item-label"  ng-click="insertChart('barClustered')">barClustered</a></li>
							<li><a class="item-label"  ng-click="insertChart('barClustered3D')">barClustered3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('barOfPie')">barOfPie</a></li>
							<li><a class="item-label"  ng-click="insertChart('barStacked')">barStacked</a></li>
							<li><a class="item-label"  ng-click="insertChart('barStacked3D')">barStacked3D</a></li>
							<li><a class="item-label"  ng-click="insertChart('barStacked100')">barStacked100</a></li>
							<li><a class="item-label"  ng-click="insertChart('barStacked1003D')">barStacked1003D</a></li>
						</ul>
					</li>
					<li>
						<div class="item-label" ng-click="scatterchart=!scatterchart" ng-class="{'expanded': scatterchart}">XYScatter Chart</div>
						<ul class="slide ng-hide" ng-show="scatterchart">
							<li><a class="item-label"  ng-click="insertChart('xyScatter')">xyScatter</a></li>
							<li><a class="item-label"  ng-click="insertChart('xyScatterLines')">xyScatterLines</a></li>
							<li><a class="item-label"  ng-click="insertChart('xyScatterLinesNoMarkers')">xyScatterLinesNoMarkers</a></li>
							<li><a class="item-label"  ng-click="insertChart('xyScatterSmooth')">xyScatterSmooth</a></li>
							<li><a class="item-label"  ng-click="insertChart('xyScatterSmoothNoMarkers')">xyScatterSmoothNoMarkers</a></li>
						
						</ul>
					</li>
					<li>
						<div class="item-label" ng-click="stockchart=!stockchart" ng-class="{'expanded': stockchart}">Stock Chart</div>
						<ul class="slide ng-hide" ng-show="stockchart">
							<li><a class="item-label"  ng-click="insertChart('stockHLC')">stockHLC</a></li>
							<li><a class="item-label"  ng-click="insertChart('stockOHLC')">stockOHLC</a></li>
							<li><a class="item-label"  ng-click="insertChart('stockVHLC')">stockVHLC</a></li>
							<li><a class="item-label"  ng-click="insertChart('stockVOHLC')">stockVOHLC</a></li>
						
						</ul>
					</li>
				</ul>
		    </section>
		</aside>
		<div class="insp-container ui-draggable ui-draggable-disabled ui-state-disabled" aria-disabled="true" style="left: auto; top: 0px; display: block;right:{{menuRight}}px;z-index:10000;">
			<div class="tab-pane active" id="chartExTab">
				<div class="insp-pane">
					<div class="insp-group-layout">
						<div class="insp-group expanded">
                                <div class="insp-group-title">
                                     <span>
                                         <span class="group-state fa fa-caret-down"></span>
                                     <span class="groupheader localize">Chart Title</span>
                                     </span>
                                </div>

                                <div class="insp-group-content">
                                    <div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-text insp-inline-row" data-name="chartTitletext">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Text
                                                    </div>
                                                    <input class="editor insp-inline-row-item insp-col-6" type="text">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row"  >
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Font Size
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
                                                           <select style="width: 100%; height: 100%" id="chartTitleFontSize">
                                                           	<option value='8'>8</option>
                                                           	<option value='9'>9</option>
                                                           	<option value='10'>10</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='12'>12</option>
                                                           	<option value='13'>13</option>
                                                           	<option value='14'>14</option>
                                                           	<option value='15'>15</option>
                                                           	<option value='16'>16</option>
                                                           	<option value='17'>17</option>
                                                           	<option value='18'>18</option>
                                                           	<option value='20'>20</option>
                                                           	<option value='22'>22</option>
                                                           	<option value='24'>24</option>
                                                           	<option value='26'>26</option>
                                                           	<option value='28'>28</option>
                                                           	<option value='36'>36</option>
                                                           	<option value='48'>48</option>
                                                           	<option value='72'>72</option>
                                                           </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Font Family
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
 														<select style="width: 100%; height: 100%" id="chartTitleFontFamily">
 															<option value='Arial'>Arial</option>
 															<option value='Arial Black'>Arial Black</option>
 															<option value='Calibri'>Calibri</option>
 															<option value='Cambria'>Cambria</option>
 															<option value='Century'>Century</option>
 															<option value='Courier New'>Courier New</option>
 															<option value='Comic Sans MS'>Comic Sans MS</option>
 															<option value='Garamond'>Garamond</option>
 															<option value='Georgia'>Georgia</option>
 															<option value='Malgun Gothic'>Malgun Gothic</option>
 															<option value='Mangal'>Mangal</option>
 															<option value='Meiryo'>Meiryo</option>
 															<option value='MS Gothic'>MS Gothic</option>
 															<option value='MS Mincho'>MS Mincho</option>
 															<option value='MS PGothic'>MS PGothic</option>
 															<option value='MS PMincho'>MS PMincho</option>
 															<option value='Roboto'>Roboto</option>
 															<option value='Tahoma'>Tahoma</option>
 															<option value='Times'>Times</option>
 															<option value='Times New Roman'>Times New Roman</option>
 															<option value='Trebuchet MS'>Trebuchet MS</option>
 															<option value='Verdana'>Verdana</option>
 															<option value='Wingdings'>Wingdings</option>
 														</select>
                                                     </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row" data-name="chartTitleColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Color
                                                    </div>
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                        <spectrum-colorpicker ng-model="chartTitleColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="group-item-divider"></div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="insp-row col-md-offset-8">
                                                <div class="button btn btn-default group-set localize" id="setChartTitle" ng-click="applyChartTitle()">
                                                    Set
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="insp-group-layout">
                            <div class="insp-group">
                                <div class="insp-group-title">
                                                <span>
                                                    <span class="group-state fa fa-caret-down"></span>
                                                <span class="groupheader localize">Chart Area</span>
                                                </span>
                                </div>
                                <div class="insp-group-content" style="display: block;">
                                    <div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row"
                                                     data-name="chartTitleColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Background color
                                                    </div>                                                     
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                    	<spectrum-colorpicker ng-model="chartAreaBackColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row" data-name="chartAreaColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Color
                                                    </div>
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                    	<spectrum-colorpicker ng-model="chartAreaColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Font Size
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
                                                           <select style="width: 100%; height: 100%" id="chartAreaFontSize">
                                                           	<option value='8'>8</option>
                                                           	<option value='9'>9</option>
                                                           	<option value='10'>10</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='12'>12</option>
                                                           	<option value='13'>13</option>
                                                           	<option value='14'>14</option>
                                                           	<option value='15'>15</option>
                                                           	<option value='16'>16</option>
                                                           	<option value='17'>17</option>
                                                           	<option value='18'>18</option>
                                                           	<option value='20'>20</option>
                                                           	<option value='22'>22</option>
                                                           	<option value='24'>24</option>
                                                           	<option value='26'>26</option>
                                                           	<option value='28'>28</option>
                                                           	<option value='36'>36</option>
                                                           	<option value='48'>48</option>
                                                           	<option value='72'>72</option>
                                                           </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Font Family
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
 														<select style="width: 100%; height: 100%" id="chartAreaFontFamily">
 															<option value='Arial'>Arial</option>
 															<option value='Arial Black'>Arial Black</option>
 															<option value='Calibri'>Calibri</option>
 															<option value='Cambria'>Cambria</option>
 															<option value='Century'>Century</option>
 															<option value='Courier New'>Courier New</option>
 															<option value='Comic Sans MS'>Comic Sans MS</option>
 															<option value='Garamond'>Garamond</option>
 															<option value='Georgia'>Georgia</option>
 															<option value='Malgun Gothic'>Malgun Gothic</option>
 															<option value='Mangal'>Mangal</option>
 															<option value='Meiryo'>Meiryo</option>
 															<option value='MS Gothic'>MS Gothic</option>
 															<option value='MS Mincho'>MS Mincho</option>
 															<option value='MS PGothic'>MS PGothic</option>
 															<option value='MS PMincho'>MS PMincho</option>
 															<option value='Roboto'>Roboto</option>
 															<option value='Tahoma'>Tahoma</option>
 															<option value='Times'>Times</option>
 															<option value='Times New Roman'>Times New Roman</option>
 															<option value='Trebuchet MS'>Trebuchet MS</option>
 															<option value='Verdana'>Verdana</option>
 															<option value='Wingdings'>Wingdings</option>
 														</select>
                                                     </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="group-item-divider"></div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="insp-row col-md-offset-8">
                                                <div class="button btn btn-default group-set localize" id="setChartArea" ng-click="applyChartAreaSetting()">
                                                    Set
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                            </div>
                        </div>
                        
                        
                        
                        <div class="insp-group-layout" id="chartLegendGroup">
                            <div class="insp-group">
                                <div class="insp-group-title">
                                                <span>
                                                    <span class="group-state fa fa-caret-down"></span>
                                                <span class="groupheader localize">Legend</span>
                                                </span>
                                </div>

                                <div class="insp-group-content" style="display: block;">
                                    <div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" >
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Position
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
														<select style="width: 100%; height: 100%" id="chartLegendPositionList">
															<option value="3">Left</option>
															<option value="2">Right</option>
															<option value="1">Top</option>
															<option value="4">Bottom</option>
														</select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
												 <label><input type="checkbox" style="vertical-align: bottom;position: relative;top: -2px;" id="showChartLegend"/> Show Legend</label>												
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="group-item-divider"></div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="insp-row col-md-offset-8">
                                                <div class="button btn btn-default group-set localize" id="setChartLegend" ng-click="applyChartLegendSetting()">
                                                    Set
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        
                        
                        
                        
                        <div class="insp-group-layout" id="chartDataLabelsGroup">
                            <div class="insp-group">
                                <div class="insp-group-title">
                                                <span>
                                                    <span class="group-state fa fa-caret-down"></span>
                                                <span class="groupheader localize">Data Labels</span>
                                                </span>
                                </div>

                                <div class="insp-group-content" style="display: block;">
                                    <div>
                                        <div class="insp-row">
                                            <div>
                                            	<label><input type="checkbox" style="vertical-align: bottom;position: relative;top: -2px;" id="showDataLabelsValue"/> Show Value</label>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                            <label><input type="checkbox" style="vertical-align: bottom;position: relative;top: -2px;" id="showDataLabelsSeriesName"/> Show Series Name</label>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                            	<label><input type="checkbox" style="vertical-align: bottom;position: relative;top: -2px;" id="showDataLabelsCategoryName"/> Show Category Name</label>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="group-item-divider"></div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="insp-row col-md-offset-8">
                                                <div class="button btn btn-default group-set localize" id="setChartDataLabels" ng-click="applyChartDataLabelsSetting()">
                                                    Set
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="insp-group-layout" id="chartAxesGroup" style="display: block;">
                            <div class="insp-group">
                                <div class="insp-group-title">
                                                <span>
                                                    <span class="group-state fa fa-caret-down"></span>
                                                <span class="groupheader localize">Axes</span>
                                                </span>
                                </div>

                                <div class="insp-group-content" style="display: block;">
                                    <div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="chartAxisTypeList" data-name="chartAxieType">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Axis Type
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
														<select style="width: 100%; height: 100%" id="chartAxieType">
															<option value="0">PrimaryCategory</option>
															<option value="1">PrimaryValue</option>
														</select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row" data-name="chartAixsColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Color
                                                    </div>
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                    	<spectrum-colorpicker ng-model="chartAixsColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="fontSizeList" data-name="chartAxesFontSize">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Font Size
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
                                                           <select style="width: 100%; height: 100%" id="chartAxesFontSize">
                                                           	<option value='8'>8</option>
                                                           	<option value='9'>9</option>
                                                           	<option value='10'>10</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='12'>12</option>
                                                           	<option value='13'>13</option>
                                                           	<option value='14'>14</option>
                                                           	<option value='15'>15</option>
                                                           	<option value='16'>16</option>
                                                           	<option value='17'>17</option>
                                                           	<option value='18'>18</option>
                                                           	<option value='20'>20</option>
                                                           	<option value='22'>22</option>
                                                           	<option value='24'>24</option>
                                                           	<option value='26'>26</option>
                                                           	<option value='28'>28</option>
                                                           	<option value='36'>36</option>
                                                           	<option value='48'>48</option>
                                                           	<option value='72'>72</option>
                                                           </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="fontFamilyList" data-name="chartAxesFontFamily">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Font Family
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
 														<select style="width: 100%; height: 100%" id="chartAxesFontFamily">
 															<option value='Arial'>Arial</option>
 															<option value='Arial Black'>Arial Black</option>
 															<option value='Calibri'>Calibri</option>
 															<option value='Cambria'>Cambria</option>
 															<option value='Century'>Century</option>
 															<option value='Courier New'>Courier New</option>
 															<option value='Comic Sans MS'>Comic Sans MS</option>
 															<option value='Garamond'>Garamond</option>
 															<option value='Georgia'>Georgia</option>
 															<option value='Malgun Gothic'>Malgun Gothic</option>
 															<option value='Mangal'>Mangal</option>
 															<option value='Meiryo'>Meiryo</option>
 															<option value='MS Gothic'>MS Gothic</option>
 															<option value='MS Mincho'>MS Mincho</option>
 															<option value='MS PGothic'>MS PGothic</option>
 															<option value='MS PMincho'>MS PMincho</option>
 															<option value='Roboto'>Roboto</option>
 															<option value='Tahoma'>Tahoma</option>
 															<option value='Times'>Times</option>
 															<option value='Times New Roman'>Times New Roman</option>
 															<option value='Trebuchet MS'>Trebuchet MS</option>
 															<option value='Verdana'>Verdana</option>
 															<option value='Wingdings'>Wingdings</option>
 														</select>
                                                     </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-text insp-inline-row" data-name="chartAixsTitletext">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Title
                                                    </div>
                                                    <input class="editor insp-inline-row-item insp-col-6" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="fontSizeList" data-name="chartAxesTitleFontSize">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Title Size
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
                                                           <select style="width: 100%; height: 100%" id="chartAxesTitleFontSize">
                                                           	<option value='8'>8</option>
                                                           	<option value='9'>9</option>
                                                           	<option value='10'>10</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='11'>11</option>
                                                           	<option value='12'>12</option>
                                                           	<option value='13'>13</option>
                                                           	<option value='14'>14</option>
                                                           	<option value='15'>15</option>
                                                           	<option value='16'>16</option>
                                                           	<option value='17'>17</option>
                                                           	<option value='18'>18</option>
                                                           	<option value='20'>20</option>
                                                           	<option value='22'>22</option>
                                                           	<option value='24'>24</option>
                                                           	<option value='26'>26</option>
                                                           	<option value='28'>28</option>
                                                           	<option value='36'>36</option>
                                                           	<option value='48'>48</option>
                                                           	<option value='72'>72</option>
                                                           </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="fontFamilyList" data-name="chartAxesTitleFontFamily">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Title Font
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
 														<select style="width: 100%; height: 100%" id="chartAxesTitleFontFamily">
 															<option value='Arial'>Arial</option>
 															<option value='Arial Black'>Arial Black</option>
 															<option value='Calibri'>Calibri</option>
 															<option value='Cambria'>Cambria</option>
 															<option value='Century'>Century</option>
 															<option value='Courier New'>Courier New</option>
 															<option value='Comic Sans MS'>Comic Sans MS</option>
 															<option value='Garamond'>Garamond</option>
 															<option value='Georgia'>Georgia</option>
 															<option value='Malgun Gothic'>Malgun Gothic</option>
 															<option value='Mangal'>Mangal</option>
 															<option value='Meiryo'>Meiryo</option>
 															<option value='MS Gothic'>MS Gothic</option>
 															<option value='MS Mincho'>MS Mincho</option>
 															<option value='MS PGothic'>MS PGothic</option>
 															<option value='MS PMincho'>MS PMincho</option>
 															<option value='Roboto'>Roboto</option>
 															<option value='Tahoma'>Tahoma</option>
 															<option value='Times'>Times</option>
 															<option value='Times New Roman'>Times New Roman</option>
 															<option value='Trebuchet MS'>Trebuchet MS</option>
 															<option value='Verdana'>Verdana</option>
 															<option value='Wingdings'>Wingdings</option>
 														</select>
                                                     </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row" data-name="chartAixsTitleColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Title Color
                                                    </div>
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                    	<spectrum-colorpicker ng-model="chartAixsTitleColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row" data-name="chartAixsLineColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Line Color
                                                    </div>
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                    	<spectrum-colorpicker ng-model="chartAixsLineColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-text insp-inline-row" data-name="chartAixsLineWidth">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Line Width
                                                    </div>
                                                    <input class="editor insp-inline-row-item insp-col-6" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-text insp-inline-row" data-name="chartAixsMajorUnit">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Major Unit
                                                    </div>
                                                    <input class="editor insp-inline-row-item insp-col-6" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-text insp-inline-row" data-name="chartAixsMinorUnit">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Minor Unit
                                                    </div>
                                                    <input class="editor insp-inline-row-item insp-col-6" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-text insp-inline-row" data-name="chartAixsMajorGridlineWidth">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Major Gridline Width
                                                    </div>
                                                    <input class="editor insp-inline-row-item insp-col-6" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row" data-name="chartAixsMajorGridlineColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Major Gridline Color
                                                    </div>
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                    	<spectrum-colorpicker ng-model="chartAixsMajorGridlineColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-text insp-inline-row" data-name="chartAixsMinorMinorGridlineWidth">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Minor Gridline Width
                                                    </div>
                                                    <input class="editor insp-inline-row-item insp-col-6" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-color-picker insp-inline-row" data-name="chartAixsMinorGridlineColor">
                                                    <div class="title  insp-inline-row-item insp-col-6 localize">
                                                        Minor Gridline Color
                                                    </div>
                                                    <div class="picker insp-inline-row-item insp-col-6">
                                                    	<spectrum-colorpicker ng-model="chartAixsMinorGridlineColor" format="'rgb'" style="width: 100%; height: 100%"></spectrum-colorpicker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="chartTickPositionList" data-name="chartMajorTickPosition">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Major Tick Position
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
                                                       	<select style="width: 100%; height: 100%" id="chartMajorTickPosition">
                                                       		<option value="0">Cross</option>
                                                       		<option value="1">Inside</option>
                                                       		<option value="2">None</option>
                                                       		<option value="3">Outside</option>
                                                       	</select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="chartTickPositionList" data-name="chartMinorTickPosition">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Minor Tick Position
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item  btn-default insp-text-right insp-col-6">
                                                        <select style="width: 100%; height: 100%" id="chartMinorTickPosition">
                                                       		<option value="0">Cross</option>
                                                       		<option value="1">Inside</option>
                                                       		<option value="2">None</option>
                                                       		<option value="3">Outside</option>
                                                       	</select>                                                    
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div>
                                                <div class="insp-dropdown-list insp-inline-row" data-list-ref="chartTickLabelPositionList" data-name="chartTickLabelPosition">
                                                    <div class="title insp-inline-row-item insp-col-6 localize">
                                                        Tick Label Position
                                                    </div>
                                                    <div class="dropdown insp-inline-row-item btn-default insp-text-right insp-col-6">
                                                        <select style="width: 100%; height: 100%" id="chartTickLabelPosition">
                                                       		<option value="3">None</option>
                                                       		<option value="2">NextToAxis</option>
                                                       	</select>                                                    
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="insp-row">
                                            <div>
                                                <label><input type="checkbox" style="vertical-align: bottom;position: relative;top: -2px;" id="showAxis"/> Show Axis</label>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                                <label><input type="checkbox" style="vertical-align: bottom;position: relative;top: -2px;" id="showMajorGridline"/> Show Major Gridline</label>
                                            </div>
                                        </div>
                                        <div class="insp-row">
                                            <div>
                                                <label><input type="checkbox" style="vertical-align: bottom;position: relative;top: -2px;" id="showMinorGridline"/> Show Minor Gridline</label>
                                            </div>
                                        </div>

                                        <div class="insp-row">
                                            <div class="group-item-divider"></div>
                                        </div>
                                        <div class="insp-row">
                                            <div class="insp-row col-md-offset-8">
                                                <div class="button btn btn-default group-set localize" id="setChartAxes" ng-click="applyChartAxesSetting()">
                                                    Set
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
				</div>
			</div>
		</div>
    </body>

    <!-- DEVELOPER -->
    <script type="text/javascript" src="<%= path %>/js/resources.js"></script>
    
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/flatFormEditorApp.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/app.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/commons.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/controllers/contextMenuController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/sessionService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/coreCommonsService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/directives/contextMenu.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/providers/contextMenuActionService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/commons/directives/numbersOnly.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/components.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/config.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/run.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/flatFormChooserController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/veilController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/controllers/exceptionViewModalCtrl.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/veil.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewer.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/providers/exceptionViewerHttpProvider.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/treesChooser.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/selectUsers.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/components/directives/flatFormChooser.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/editor.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/undoRedoController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/clipboardController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/formatController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/formatCellEditorController.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/outlineController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/viewPortController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/colorController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/fontController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/sheetEditorController.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/tableBuilderController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/alignmentController.js"></script>
    <!-- <script type="text/javascript" src="/coreApp/js/modules/editor/controllers/cellsController.js"></script> -->
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/controllers/insertController.js"></script>   
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/sheetEditor.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/insertRowsColumns.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/formatCellEditor.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/insertChart.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/directives/insertSparkline.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/formatService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/colorService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/viewPortService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/editor/providers/insertService.js"></script>    
    
            
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/spreadSheet.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/controllers/zoomController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/controllers/statusBarController.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/providers/zoomService.js"></script>
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/spreadSheet/directives/zoomStatus.js"></script>

    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validation.app.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationController.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationDisplayDirective.js"></script>    
    <script type="text/javascript" src="<%= path %>/coreApp/js/modules/validation/validationService.js"></script>    
    
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/editor/editor.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/editor/controllers/cellsController.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/editor/controllers/sheetEditorController.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/editor/directives/saveFlatFormAs.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/editor/directives/copyTemplateTo.js"></script>    
    
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/spreadSheet/spreadSheet.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/spreadSheet/controllers/spreadSheetController.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/spreadSheet/providers/spreadSheetService.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/spreadSheet/providers/cellService.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/spreadSheet/providers/viewModeService.js"></script>

    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/commons/commons.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/commons/providers/dataService.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/commons/providers/contextMenuActionsSetupService.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/commons/directives/bootstrapSelect.js"></script>

    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/mappings/mappings.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/mappings/controllers/mappingController.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/mappings/providers/mappingService.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/mappings/directives/cellPicker.js"></script>

    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/details/details.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/details/controllers/detailsController.js"></script>

    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/settings/settings.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/settings/controllers/settingsController.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/settings/directives/manageUsers.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/settings/directives/workbookProperties.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/settings/directives/worksheetProperties.js"></script>
	
	<script type="text/javascript">
		GC.Spread.Sheets.LicenseKey = "localhost,438998912661945#A03V2csFmZ0IiczRmI1pjIs9WQisnOiQkIsISP3c6SLdWcwYmNBJnV0hFVwdjQa3ENttyRxNWR4RFdLlmY4F7NtxWcxIkQtZnSvRlWXBlM6F7aNJjUr9WMBdUMCFVOrRDVwR7QFdzS5MlUHZVV5dkQwd6TiojITJCL8YzM4cTN6gTO0IicfJye#4Xfd5nIzImNnJiOiMkIsISMx8idgMlSgQWYlJHcTJiOi8kI1tlOiQmcQJCLikDM4QzMwASNyYDM8EDMyIiOiQncDJCLiQ7cvhGbhN6bsJiOiMXbEJCLiQGdMByM9cTMgMXbhhmbvJkI0ISYONkIsISN4kTM6YjMxkDO9kDOzQjI0ICZcJjL";
    </script>
	
	
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/head/head.js"></script>
    <script type="text/javascript" src="<%= path %>/flatformeditorApp/js/head/controllers/headController.js"></script>    
    
</html>
