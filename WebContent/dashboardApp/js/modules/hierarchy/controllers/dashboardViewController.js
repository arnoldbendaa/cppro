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
(function() {
    'use strict';

    angular
        .module('dashboardViewApp.dashboardView')
        .controller('DashboardViewController', DashboardViewController);

    /* @ngInject */
    function DashboardViewController($rootScope, $scope, DashboardViewService, $timeout, ImportService, TaskStatusService, DashboardFormService, CoreCommonsService, $modal) {
        var dashboard;
        var url = $BASE_PATH;
        $scope.logout = logout;
        $scope.closeTab = closeTab;
        $scope.tabs = [];
        $scope.gridRows = {};
        $scope.gridCols = {};
        $scope.gridValues = {};
        $scope.repaint = repaint;
        $scope.setContextData = setContextData;
        $scope.contextDataPlaceholder = {
            business: 'select Business...',
            account: 'select Account Dimension...',
            calendarfrom: 'select Calendar from...',
            calendarto: 'select Calendar to...',
            dataType: 'select Data Type...'
        };
        $scope.ccTreeString = $scope.contextDataPlaceholder.business;
        $scope.expTreeString = $scope.contextDataPlaceholder.account;
        $scope.callFromTreeString = $scope.contextDataPlaceholder.calendarfrom;
        $scope.callToTreeString = $scope.contextDataPlaceholder.calendarto;
        $scope.dataTypeString = $scope.contextDataPlaceholder.dataType;

        var iAmIn = '';

        init();

        function init() {
            if (VIEW == 'freeform') {
                console.log("I am freeform");
                iAmIn = 'FREEFORM';
                // DO FREEFORM STUFF
            } else if (VIEW == 'hierarchy') {
                console.log("I am hierarchy");
                iAmIn = 'HIERARCHY';
                // DO HIERARCHY STUFF
            }
            //var url = window.location.href;
            //var urlTab = window.location.href.split('/');
            var uuid = UUID;

            DashboardViewService.getDashboard(uuid).then(function(response) {
                console.log(response.data);
                dashboard = response.data;
                $scope.dashboardName = dashboard.dashboardTitle;
                $scope.selectedModelID = dashboard.modelId;
                loadContextData(dashboard.contextData);
                createTabs(dashboard);

                DashboardViewService.getChosenForm(dashboard.formId)
                    .then(function(response) {
                        var jsonForm = response.data.jsonForm;
                        loadSpreadDocument(jsonForm);

                        if (iAmIn == 'HIERARCHY') {
                            $scope.xmlForm = response.data.xmlForm;
                            drawHierarchyChartsAndGridsWithContextData();
                        } else if (iAmIn == 'FREEFORM') {
                            for (var j = 0; j < dashboard.tabs.length; j++) {
                                var tab = dashboard.tabs[j];
                                var tabData = getData4Tab(tab);
                                drawChart4Tab(tab.tabName, tab.chartType, 'chart' + j, tabData.rowValues, tabData.colValues, tabData.valueValues);
                                createGrid4Tab(tab.tabName, tab.chartType, 'chart' + j, tabData.rowValues, tabData.colValues, tabData.valueValues);
                            }
                        }
                    });
            });
        }

        function logout() {
            window.location = url + "/logout.do";
        }

        function closeTab() {
            CoreCommonsService.closeTab($timeout);
        }

        function drawHierarchyChartsAndGridsWithContextData() {

            $scope.xmlForm.properties.DIMENSION_1_VISID = $scope.expTreeString.trim();
            $scope.xmlForm.properties.DIMENSION_2_VISID_FROM = $scope.callFromTreeString.trim();
            $scope.xmlForm.properties.DIMENSION_2_VISID_TO = $scope.callToTreeString.trim();
            $scope.xmlForm.properties.DATA_TYPE = $scope.dataTypeString.trim();
            $scope.xmlForm.properties.DIMENSION_0_VISID = $scope.ccTreeString.trim();
            
            DashboardFormService.getSecurityList($scope.selectedModelID).then(function(response) {
                var securityList = response.data;
                var isAccess = false;
                
                for (var i = 0; i < securityList.length; i++) {
                    if(securityList[i] === $scope.xmlForm.properties.DIMENSION_0_VISID) {
                        isAccess = true
                        i = securityList.length;
                    }
                }
                
                if (isAccess === true) {
                    testDashboard();
                } else {
                    $scope.ccTreeString = 'select Business...';
                    businessChooser();
                }
            });
        }
        
        function testDashboard() {
            DashboardFormService.testDashboard($scope.xmlForm).then(function(response) {
                if (response.data.error === true) {
                    swal("Error!", response.data.message, "error");
                } else {
                    var xmlForm = response.data;
                    console.log(xmlForm);
                    setUpCells(xmlForm);
                    for (var j = 0; j < dashboard.tabs.length; j++) {
                        var tab = dashboard.tabs[j];
                        var tabData = getData4Tab(tab);

                        drawChart4Tab(tab.tabName, tab.chartType, 'chart' + j, tabData.rowValues, tabData.colValues, tabData.valueValues);
                        createGrid4Tab(tab.tabName, tab.chartType, 'chart' + j, tabData.rowValues, tabData.colValues, tabData.valueValues);

                    }
                    //swal("Success!", response.data.message, "success");
                }
            });
        }

        function setUpCell(spreadCell, workbookCell) {
            var tag = spreadCell.tag();
            if (tag === null || tag === undefined) {
                return;
            }

            tag.valueExcel = spreadCell.value();
            tag.formulaExcel = spreadCell.formula();

            spreadCell.formula("");
            if (workbookCell.date !== null && workbookCell.date !== undefined) {
                var dateStr = workbookCell.date;
                var arr = dateStr.split('/');
                if (spreadCell.formatter() === undefined || spreadCell.formatter() === "General") {
                    spreadCell.formatter("d-mmm-yy;;");
                }
                spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, new Date(arr[2], parseInt(arr[1]) - 1, arr[0]));
            } else if (workbookCell.text !== null) {
                spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, workbookCell.text);
            } else if (workbookCell.formula !== null) {
                spreadCell.sheet.setFormula(spreadCell.row, spreadCell.col, workbookCell.formula);
            } else if (workbookCell.value !== null) {
                if (spreadCell.formatter() === "@" || spreadCell.formatter() === "d-mmm-yy;;") {
                    // text value
                    spreadCell.sheet.setText(spreadCell.row, spreadCell.col, workbookCell.value + "");
                } else {
                    // numeric value
                    spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, workbookCell.value);
                }
            } else {
                if (spreadCell.formatter() === "@") {
                    spreadCell.sheet.setText(spreadCell.row, spreadCell.col, "");
                } else {
                    spreadCell.sheet.setValue(spreadCell.row, spreadCell.col, 0);
                }
            }

        }

        function setUpCells(xmlForm) {
            var spread = $('#spreadContainer').wijspread('spread');
            spread.suspendCalcService(false);
            spread.isPaintSuspended(true);
            var worksheets = xmlForm.worksheets;
            for (var i = 0; i < worksheets.length; i++) {
                var sheet = spread.getSheetFromName(worksheets[i].name);
                if (sheet) {
                    var cells = worksheets[i].cells;
                    for (var c = 0; c < cells.length; c++) {
                        var workbookCell = angular.copy(cells[c]);
                        var spreadCell = sheet.getCell(workbookCell.row, workbookCell.column);
                        setUpCell(spreadCell, workbookCell);
                    }
                }
            }
            spread.resumeCalcService(true);
            spread.isPaintSuspended(false);
        }

        function loadContextData(contextData) {
            $scope.expTreeString = contextData.exp;
            $scope.callFromTreeString = contextData.calendarFrom;
            $scope.callToTreeString = contextData.calendarTo;
            $scope.dataTypeString = contextData.dataType;
            $scope.ccTreeString = contextData.cc;
        }

        function repaint(tabID) {
            $timeout(function() {
                var chart = $("#myChart-" + tabID).data("kendoChart");
                chart.redraw();
            }, 0);

        }

        function loadSpreadDocument(jsonForm) {
            // Obtain spread instance
            if (!$("#spreadContainer").wijspread("spread")) {
                $("#spreadContainer").wijspread({
                    sheetCount: 3
                });
            }
            var spread = $("#spreadContainer").wijspread("spread");

            //jsonForm = '{"spread":{"version":"3.0","activeSheetIndex":1,"sheetCount":2,"sheets":{"Sheet1":{"name":"Sheet1","selections":{"0":{"row":0,"rowCount":5,"col":0,"colCount":5}},"rowCount":200,"columnCount":20,"activeRow":4,"activeCol":4,"data":{"dataTable":{"1":{"1":{"value":"fdgsh"},"2":{"value":"lll;mmlm"},"3":{"value":";;"},"4":{"value":"kmo"}},"2":{"0":{"value":"RFEARF"},"1":{"value":243},"2":{"value":345},"3":{"value":345},"4":{"value":4543}},"3":{"0":{"value":"GFD"},"1":{"value":12},"2":{"value":3},"3":{"value":4},"4":{"value":5}},"4":{"0":{"value":"GFD"},"1":{"value":243},"2":{"value":234},"3":{"value":324},"4":{"value":324}}}}},"Sheet2":{"name":"Sheet2","selections":{"0":{"row":16,"rowCount":1,"col":18,"colCount":1}},"rowCount":200,"columnCount":20,"activeRow":16,"activeCol":18,"data":{"dataTable":{"0":{"0":{"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"1":{"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"2":{"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"3":{"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"4":{"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}}},"1":{"0":{},"1":{},"2":{},"3":{},"4":{}},"2":{"0":{},"1":{},"2":{},"3":{},"4":{}},"3":{"0":{},"1":{},"2":{},"3":{},"4":{}},"4":{"0":{},"1":{},"2":{},"3":{},"4":{}},"13":{"12":{"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"13":{"value":"row11","style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"14":{"value":"row22","style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"15":{"value":"row33","style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"16":{"value":"row44","style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}}},"14":{"12":{"value":"col11","style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"13":{"value":456,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"14":{"value":654,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"15":{"value":7,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"16":{"value":65,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}}},"15":{"12":{"value":"col22","style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"13":{"value":465,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"14":{"value":645,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"15":{"value":54,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"16":{"value":456,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}}},"16":{"12":{"value":"col33","style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"13":{"value":54,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"14":{"value":6,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"15":{"value":45654,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}},"16":{"value":54656,"style":{"foreColor":"#000000","hAlign":3,"vAlign":0,"imeMode":1}}},"17":{"13":{}},"18":{"13":{}},"19":{"13":{}}}}}}}}';

            // Get active sheet in spread instance
            //var activeSheet = spread.getActiveSheet();
            spread.fromJSON(JSON.parse(jsonForm).spread);
        }

        function createGrid4Tab(chartTitle, chartType, chartID, rowValues, colValues, valueValues) {
            $scope.gridRows[chartID] = rowValues;
            $scope.gridCols[chartID] = colValues;
            $scope.gridValues[chartID] = valueValues;
        }

        function drawChart4Tab(chartTitle, chartType, chartID, rowValues, colValues, valueValues) {
            var title = chartTitle;
            var series = [];
            var categories = [];
            var isPie = chartType === 'pie';

            if (!isPie) {
                for (var i = 0; i < rowValues.length; i++) {
                    series.push({
                        name: rowValues[i],
                        data: valueValues[i],
                        type: chartType
                    });
                }
                categories = colValues;
            } else {
                var data = [];
                for (var i = 0; i < rowValues.length; i++) {
                    data.push({
                        category: rowValues[i],
                        value: valueValues.length == 1 ? valueValues[0][i] : valueValues[i][0]
                    });
                }

                series.push({
                    data: data,
                    type: "pie"
                });
            }


            if (!isPie) {
                $timeout(function() {
                    $("#myChart-" + chartID).kendoChart({
                        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
                        title: {
                            text: title
                        },
                        legend: {
                            position: "top"
                        },
                        series: series,
                        categoryAxis: {
                            categories: categories
                        },
                        tooltip: {
                            visible: true,
                            format: "{0}",
                            template: "#= series.name #: #= value #"
                        }
                    });
                }, 0);

            } else {
                $timeout(function() {
                    $("#myChart-" + chartID).kendoChart({
                        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
                        plotArea: {
                            padding: 10
                        },
                        series: series,
                        tooltip: {
                            visible: true,
                            format: "{0}",
                            template: "#= category #: #= value #"
                        },
                        labels: {
                            visible: true
                        },
                        legend: {
                            position: "top"
                        }
                    });
                }, 0);
            }
        }

        function getCellPosition(unparsed) {
            var parsed = unparsed.split(':');
            return {
                col: parsed[0],
                row: parsed[1]
            };
        }

        function getCellsValues(sheetName, cellsAddresses, isMultiDimensional) {
            var spread = $("#spreadContainer").wijspread("spread");
            var sheet = spread.getSheetFromName(sheetName);
            var colFirstIndex = cellsAddresses.first.col;
            var rowFirstIndex = cellsAddresses.first.row;
            var colLastIndex = cellsAddresses.last.col;
            var rowLastIndex = cellsAddresses.last.row;
            var result = [];

            for (var z = parseInt(rowFirstIndex); z < parseInt(rowLastIndex); z++) {
                var temp = [];
                for (var x = parseInt(colFirstIndex); x <= parseInt(colLastIndex); x++) {
                    var value = sheet.getCell(z, x).value();
                    if (isMultiDimensional) {
                        temp.push(value);
                    } else {
                        result.push(value);
                    }
                }
                if (isMultiDimensional) {
                    result.push(temp);
                }
            }

            return result;
        }

        function createTabs(dashboard) {
            for (var i = 0; i < dashboard.tabs.length; i++) {
                $scope.tabs.push({
                    title: dashboard.tabs[i].tabName,
                    id: 'chart' + i,
                    chartType: dashboard.tabs[i].chartType
                });
            }
        }

        function getData4Tab(tab) {
            var tooltip = tab.tooltip;
            var isPie = tab.chartType === 'pie';

            var rowSheetName = tab.rowsRange.sheetName;
            var rowCells = {
                first: getCellPosition(tab.rowsRange.firstCell),
                last: getCellPosition(tab.rowsRange.lastCell),
                sheetName: rowSheetName
            };
            // var rowValues = getCellsValues(rowSheetName, rowCells, false);

            var colSheetName = isPie ? '' : tab.colsRange.sheetName;
            var colCells = isPie ? {} : {
                first: getCellPosition(tab.colsRange.firstCell),
                last: getCellPosition(tab.colsRange.lastCell),
                sheetName: colSheetName
            };
            // var colValues = isPie ? [] : getCellsValues(colSheetName, colCells, false);

            var valueSheetName = tab.valuesRange.sheetName;
            var valueCells = {
                first: getCellPosition(tab.valuesRange.firstCell),
                last: getCellPosition(tab.valuesRange.lastCell),
                sheetName: valueSheetName
            };
            // var valueValues = getCellsValues(valueSheetName, valueCells, true);
            var readHidden;
            if (tab.hideCells === "true") {
                readHidden = false;
            } else {
                readHidden = true;
            }
            var temp = removeEmptyLabelsAndHidenGroups(rowCells, colCells, valueCells, isPie, readHidden);

            var result = {
                tooltip: tooltip,
                rowSheetName: rowSheetName,
                rowCells: rowCells,
                rowValues: temp.rows,
                colSheetName: colSheetName,
                colCells: colCells,
                colValues: temp.cols,
                valueSheetName: valueSheetName,
                valueCells: valueCells,
                valueValues: temp.values
            };

            return result;
        }


        function getValuesFrom(sheet, columnStartIndex, columnEndIndex, rowStartIndex, rowEndIndex) {
            var values = [];

            // if (selectedRanges.length == 1) { // allow only one selected range
            //     var selectedOneRange = selectedRanges[0];
            //     var columnStartIndex = selectedOneRange.col;
            //     var columnEndIndex = selectedOneRange.col + selectedOneRange.colCount;
            //     var rowStartIndex = selectedOneRange.row;
            //     var rowEndIndex = selectedOneRange.row + selectedOneRange.rowCount;
            for (var i = rowStartIndex; i < rowEndIndex; i++) {
                var oneRowValues = [];
                for (var j = columnStartIndex; j < columnEndIndex + 1; j++) {
                    oneRowValues.push(sheet.getValue(i, j));
                }
                values.push(oneRowValues);
            }
            //}
            return values;
        }

        function getLabelsForRows(sheet, column, rowStartIndex, rowEndIndex, values, readFromHidenCells) {
            var labels = [];
            var i;
            // if (selectedRanges.length == 1) { // allow only one selected range
            //     var selectedOneRange = selectedRanges[0];
            //     var rowStartIndex = selectedOneRange.row;
            //     var rowEndIndex = selectedOneRange.row + selectedOneRange.rowCount;
            //     var column = selectedOneRange.col;

            for (i = rowStartIndex; i < rowEndIndex; i++) {
                if (!readFromHidenCells) {
                    if (sheet.getRow(i).visible() === false) {
                        values[i - rowStartIndex] = null;
                        continue;
                    }
                }
                var value = sheet.getValue(i, column);
                if (sheet.getValue(i, column) != null && typeof value === "string" && sheet.getValue(i, column).trim() !== "") {
                    labels.push(sheet.getValue(i, column));
                } else {
                    //values.splice(i - rowStartIndex, 1);
                    values[i - rowStartIndex] = null;
                }
            }
            var temp = values;
            for (i = values.length - 1; i > -1; i--) {
                if (temp[i] == null) {
                    temp.splice(i, 1);
                }
            }
            //}
            //}
            return labels;
        }

        function getLabelsForCols(sheet, columnStartIndex, columnEndIndex, row, values, readFromHidenCells) {
            var labels = [];
            var i;
            // if (selectedRanges.length == 1) { // allow only one selected range
            //     var selectedOneRange = selectedRanges[0];
            //     // if (selectedOneRange.rowCount == 1) {
            //     var columnStartIndex = selectedOneRange.col;
            //     var columnEndIndex = selectedOneRange.col + selectedOneRange.colCount;
            //     var row = selectedOneRange.row;

            for (i = columnEndIndex; i >= columnStartIndex; i--) {
                if (!readFromHidenCells) {
                    if (sheet.getColumn(i).visible() === false) {
                        for (var j = 0; j < values.length; j++) {
                            values[j].splice(i - columnStartIndex, 1);
                        }
                        continue;
                    }
                }
                var value = sheet.getValue(row, i);
                if (value != null && typeof value === "string" && sheet.getValue(row, i).trim() !== "") {
                    labels.unshift(sheet.getValue(row, i));
                } else {
                    for (var j = 0; j < values.length; j++) {
                        values[j].splice(i - columnStartIndex, 1);
                        //values[j][i - columnStartIndex] = null;
                    }
                }
            }
            // }
            return labels;
        }

        function removeEmptyLabelsAndHidenGroups(rows, columns, values, isPie, readHidden) {
            var spread = $("#spreadContainer").wijspread("spread");
            var sheet;
            var result = {};

            values = parseToInt(values);
            var columnStartIndex = values.first.col;
            var rowStartIndex = values.first.row;
            var columnEndIndex = values.last.col;
            var rowEndIndex = values.last.row;

            sheet = spread.getSheetFromName(values.sheetName);
            result.values = getValuesFrom(sheet, columnStartIndex, columnEndIndex, rowStartIndex, rowEndIndex);
            result.values = formatValues(result.values);

            rows = parseToInt(rows);
            columnStartIndex = rows.first.col;
            rowStartIndex = rows.first.row;
            //columnEndIndex = rows.last.col;
            rowEndIndex = rows.last.row;

            sheet = spread.getSheetFromName(rows.sheetName);
            result.rows = getLabelsForRows(sheet, columnStartIndex, rowStartIndex, rowEndIndex, result.values, readHidden);

            if (!isPie) {
                columns = parseToInt(columns);
                columnStartIndex = columns.first.col;
                rowStartIndex = columns.first.row;
                columnEndIndex = columns.last.col;
                //rowEndIndex = columns.last.row;

                sheet = spread.getSheetFromName(columns.sheetName);
                result.cols = getLabelsForCols(sheet, columnStartIndex, columnEndIndex, rowStartIndex, result.values, readHidden);
            }

            return result;
        }

        function parseToInt(values) {
            values.first = {
                col: parseInt(values.first.col),
                row: parseInt(values.first.row)
            };
            values.last = {
                col: parseInt(values.last.col),
                row: parseInt(values.last.row)
            };
            return values;
        }

        function formatValues(values) {
            for (var i in values) {
                for (var j in values[i]) {
                    var value = values[i][j];
                    value = parseFloat(value);
                    if (value !== value) {
                        value = parseFloat(0).toFixed(2);
                    } else {
                        value = value.toFixed(2); //toFixed() returns a string not a number
                    }
                    values[i][j] = value;
                }
            }
            return values;
        }

        function setContextData(type) {
            var modelId = $scope.selectedModelID;
            if (modelId === null || modelId === undefined) {
                sweetAlert("Not selected Flat Form", "Please select Flat Form first");
                return;
            }
            var selectedCallTree, selectedCcTree;
            var treesVisibility, modalTitle, checkboxState, tabIndex;
            var multiple = false;
            switch (type) {
                case "calendarfrom":
                    selectedCallTree = [];
                    modalTitle = "Choose Calendar Dimension (from...)";
                    treesVisibility = [false, false, true, false];
                    tabIndex = 1;
                    break;
                case "calendarto":
                    selectedCallTree = [];
                    modalTitle = "Choose Calendar Dimension (to...)";
                    treesVisibility = [false, false, true, false];
                    tabIndex = 1;
                    break;
                case "business":
                    selectedCcTree = [];
                    modalTitle = "Choose Business Dimension";
                    treesVisibility = [true, false, false, false];
                    tabIndex = 2;
                    break;
            }

            if (type === "business") {
                businessChooser();
            } else {
                var modalInstance = $modal.open({
                    template: '<trees-chooser modal-title="modalTitle" selected-call-tree="selectedCallTree" model-id="modelId" tab-index="tabIndex" multiple="multiple" ok="ok()" trees-visibility="treesVisibility" close="close()" checkbox-state="checkboxState"></trees-chooser>',
                    windowClass: 'sub-system-modals softpro-modals',
                    backdrop: 'static',
                    size: 'lg',
                    controller: ['$scope', '$modalInstance',
                        function($scope, $modalInstance) {
                            $scope.modalTitle = modalTitle;
                            $scope.selectedCallTree = selectedCallTree;
                            $scope.modelId = modelId;
                            $scope.tabIndex = tabIndex;
                            $scope.multiple = multiple;
                            $scope.treesVisibility = treesVisibility;
                            $scope.checkboxState = checkboxState;

                            $scope.ok = function() {
                                var dimensions;
                                switch (type) {
                                    case "calendarfrom":
                                        updateContextData($scope.selectedCallTree, "calendarfrom");
                                        break;
                                    case "calendarto":
                                        updateContextData($scope.selectedCallTree, "calendarto");
                                        break;

                                }
                                drawHierarchyChartsAndGridsWithContextData();
                                $scope.close();
                            };

                            $scope.close = function() {
                                $modalInstance.dismiss('cancel');
                            };
                        }
                    ]
                });
            }
        }
        
        var businessChooser = function() {
            var modelId = $scope.selectedModelID;
            if (modelId === null || modelId === undefined) {
                sweetAlert("Not selected Flat Form", "Please select Flat Form first");
                return;
            }
            var modalInstance = $modal.open({
                template: '<business-chooser modal-title="modalTitle" selected-cc-tree="selectedCcTree" model-id="modelId" ok="ok()" close="close()"></business-chooser>',
                windowClass: 'sub-system-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modalTitle = "Choose Business Dimension";
                        $scope.selectedCcTree = [];
                        $scope.modelId = modelId;

                        $scope.ok = function() {
                            updateContextData($scope.selectedCcTree, "business");
                            drawHierarchyChartsAndGridsWithContextData();
                            $scope.close();
                        };

                        $scope.close = function() {
                            $modalInstance.dismiss('cancel');
                        };
                    }
                ]
            });
        }

        function updateContextData(dimensions, type) {
            var str = "";
            for (var i = 0; i < dimensions.length; i++) {
                var dimension = dimensions[i];
                if (type === "calendarfrom" || type === "calendarto") {
                    if (dimension.parentVisId) {
                        str += " /" + dimension.parentVisId + "/" + dimension.structureElementVisId;
                    } else {
                        str += " /" + dimension.structureElementVisId;
                    }
                } else {
                    str += " " + dimension.structureElementVisId;
                }
                if (i !== dimensions.length - 1) {
                    str += ",";
                }
            }
            if (str !== "") {
                switch (type) {
                    case "calendarfrom":
                        $scope.callFromTreeString = str;
                        $scope.DIMENSION_2_VISID_FROM = str.trim();
                        break;
                    case "calendarto":
                        $scope.callToTreeString = str;
                        $scope.DIMENSION_2_VISID_TO = str.trim();
                        break;
                    case "business":
                        $scope.ccTreeString = str;
                        $scope.DIMENSION_0_VISID = str.trim();
                        break;
                }
            }
        }

    }
})();