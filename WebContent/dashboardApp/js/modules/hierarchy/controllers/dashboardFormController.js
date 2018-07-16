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
/* global formVisID */
(function() {
    'use strict';

    angular
        .module('dashboardFormApp.dashboardForm')
        .controller('DashboardFormController', DashboardFormController);

    /* @ngInject */
    function DashboardFormController($rootScope, $scope, DashboardFormService, $timeout, ImportService, TaskStatusService, CoreCommonsService, $modal, dashboardSpreadSheetService) {
        var rangePrototype = {
            sheetName: null,
            firstHeader: null,
            lastHeader: null,
            firstNumber: null,
            lastNumber: null,
            selectedRanges: {
                row: null,
                col: null,
                rowCount: null,
                colCount: null
            }
        };
        var selectionPrototype = {
            tab: {
                // title: null,
                // id: null,
                // isConfigured: false,
                // active: false
            },
            values: {
                // rows: null,
                // columns: null,
                // values: null
            },
            ranges: {
                // rows: angular.copy(rangePrototype),
                // cols: angular.copy(rangePrototype),
                // values: angular.copy(rangePrototype),
                // selected: dashboardSpreadSheetService.consts.none,
                // rows: rangePrototype,
                // columns: rangePrototype,
                // values: rangePrototype,
                //selected: dashboardSpreadSheetService.consts.none,
                // readHiddenCells: false
            },
            chartType: {
                // value: null,
                // label: null
            }
        };
        var url = $BASE_PATH;
        var oldChartDataValues = {};
        var oldContextData = {};
        var editionMode = false;
        var spread;
        var xmlForm;
        var highestID = 0;
        var fullHeightFlag = $(window).height();
        var redrawDelay;
        var hasChanged = true;
        var isValid = false;

        var formCtrl = this;

        formCtrl.editMode = ISEDIT;
        formCtrl.controllButtonsEnable = false;
        formCtrl.showOnlySpread = false;
        formCtrl.showCharts = false;
        formCtrl.readFromHiddenCells = false;
        formCtrl.tabID = -1;
        formCtrl.dashboardName; // = "Dashboard name..."
        formCtrl.tabs = [];
        formCtrl.values = null;
        formCtrl.chartAvailableTypes = [{
            value: 'column',
            label: 'Columns'
        }, {
            value: 'bar',
            label: 'Bars'
        }, {
            value: 'line',
            label: 'Lines'
        }, {
            value: 'pie',
            label: 'Pie'
        }];
        formCtrl.chartAvailableTypesNoPie = [formCtrl.chartAvailableTypes[0], formCtrl.chartAvailableTypes[1], formCtrl.chartAvailableTypes[2]];
        formCtrl.contextDataPlaceholder = {
            business: 'select Business...',
            account: 'select Account Dimension...',
            calendarfrom: 'select Calendar from...',
            calendarto: 'select Calendar to...',
            dataType: 'select Data Type...'
        };
        formCtrl.contextData = {
            ccTreeString: formCtrl.contextDataPlaceholder.business,
            expTreeString: formCtrl.contextDataPlaceholder.account,
            callFromTreeString: formCtrl.contextDataPlaceholder.calendarfrom,
            callToTreeString: formCtrl.contextDataPlaceholder.calendarto,
            dataTypeString: formCtrl.contextDataPlaceholder.dataType
        }
        // formCtrl.ccTreeString = formCtrl.contextDataPlaceholder.business;
        // formCtrl.expTreeString = formCtrl.contextDataPlaceholder.account;
        // formCtrl.callFromTreeString = formCtrl.contextDataPlaceholder.calendarfrom;
        // formCtrl.callToTreeString = formCtrl.contextDataPlaceholder.calendarto;
        // formCtrl.dataTypeString = formCtrl.contextDataPlaceholder.dataType;
        formCtrl.selectedFlatForm = {
            vis_id: "",
            xml_form_id: ""
        };
        formCtrl.consts = {
            rows: "rows",
            cols: "columns",
            vals: "values",
            none: "none"
        };
        formCtrl.views = {
            create: "create",
            edit: "edit",
            open: "open"
        };
        formCtrl.types = {
            hierarchy: "hierarchy",
            freeform: "freeform"
        }
        formCtrl.selected = formCtrl.consts.none;


        formCtrl.logout = logout;
        formCtrl.closeTab = closeTab;

        formCtrl.setRows = setRows;
        formCtrl.setColumns = setColumns;
        formCtrl.setValues = setValues;
        formCtrl.changeChartType = changeChartType;
        formCtrl.isSelected = isSelected;

        formCtrl.selectData = selectData;
        formCtrl.confirmDataSelect = confirmDataSelect;
        formCtrl.cancelDataSelect = cancelDataSelect;

        formCtrl.addTab = addTab;
        formCtrl.renameTab = renameTab;
        formCtrl.deleteTab = deleteTab;
        formCtrl.onTabSelect = onTabSelect;

        formCtrl.saveDashboard = saveDashboard;
        formCtrl.testDashboard = testDashboard;
        formCtrl.showModal = showModal;
        formCtrl.toggleAskIfReload = toggleAskIfReload;
        formCtrl.setContextData = setContextData;


        init();


        function init() {
            //var url = window.location.href;
            //var urlTab = window.location.href.split('/');
            var uuid = $UUID;

            if (VIEW === formCtrl.types.freeform) {
                switch (ISEDIT) {
                    case formCtrl.views.create:
                        {
                            showModal();
                            break;
                        }
                    case formCtrl.views.edit:
                        {
                            loadDashboardData(uuid);
                            break;
                        }
                    case formCtrl.views.open:
                        {
                            loadDashboardData(uuid);
                            break;
                        }
                    default:
                };
            } else if (VIEW === formCtrl.types.hierarchy) {
                switch (ISEDIT) {
                    case formCtrl.views.create:
                        {
                            showModal();
                            break;
                        }
                    case formCtrl.views.edit:
                        {
                            loadDashboardData(uuid);
                            break;
                        }
                    case formCtrl.views.open:
                        {
                            loadDashboardData(uuid);
                            break;
                        }
                    default:
                };
            }
            // if (VIEW == 'freeform') {
            //     console.log("I am freeform, edition:", ISEDIT, editionMode);
            //     iAmIn = 'FREEFORM';
            //     if (editionMode) {
            //         editionMode = true;
            //         loadDashboardData(uuid);
            //     } else {
            //         //loadForms();
            //         showModal();
            //     }
            //     // DO FREEFORM STUFF
            // } else if (VIEW == 'hierarchy') {
            //     console.log("I am hierarchy, edition:", ISEDIT, editionMode);
            //     iAmIn = 'HIERARCHY';
            //     // DO HIERARCHY STUFF
            //     if (editionMode) {
            //         loadDashboardData(uuid);
            //     } else {
            //         //loadForms();
            //         showModal();
            //     }
            // }

            $(document).ready(function() {
                redraw();
                $(window).resize(function() {
                    var actualFullHeight = $(window).height();
                    if (fullHeightFlag != actualFullHeight) {
                        fullHeightFlag = actualFullHeight;
                        redraw();
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

        function toggleAskIfReload() {
            CoreCommonsService.askIfReload = true;
        }

        function getCellPosition(unparsed) {
            var parsed = unparsed.split(':');
            return {
                col: parseInt(parsed[0]),
                row: parseInt(parsed[1])
            };
        }
        //return charttype{value:, label:}
        function getChartTypeByname(name) {
            for (var x = 0; x < formCtrl.chartAvailableTypes.length; x++) {
                if (formCtrl.chartAvailableTypes[x].value == name) {
                    return angular.copy(formCtrl.chartAvailableTypes[x]);
                }
            }
        }

        function setTab(dashboardTab, index) {
            var selection = angular.copy(selectionPrototype);
            selection.tab = {
                title: dashboardTab.tabName,
                id: 'chart' + index,
                isConfigured: true,
                active: false
            };
            selection.ranges.readHiddenCells = dashboardTab.hideCells === "true" ? false : true;

            selection.chartType = getChartTypeByname(dashboardTab.chartType);
            var firstCell = getCellPosition(dashboardTab.rowsRange.firstCell);
            var lastCell = getCellPosition(dashboardTab.rowsRange.lastCell);
            var firstHeader = dashboardSpreadSheetService.getColumnNameByIndex(firstCell.col + 1) + (firstCell.row + 1);
            var lastHeader = dashboardSpreadSheetService.getColumnNameByIndex(lastCell.col + 1) + (lastCell.row);
            var rowsRange = {
                sheetName: dashboardTab.rowsRange.sheetName,
                firstHeader: firstHeader,
                lastHeader: lastHeader,
                firstNumber: dashboardTab.rowsRange.firstCell,
                lastNumber: dashboardTab.rowsRange.lastCell,
                selectedRanges: {
                    rowCount: lastCell.row - firstCell.row,
                    colCount: lastCell.col - firstCell.col + 1,
                    row: firstCell.row,
                    col: firstCell.col
                }
            };
            selection.ranges.rows = rowsRange;
            //setCoulmns
            if (dashboardTab.chartType === 'pie') {
                // we will see
            } else {
                firstCell = getCellPosition(dashboardTab.colsRange.firstCell);
                lastCell = getCellPosition(dashboardTab.colsRange.lastCell);
                firstHeader = dashboardSpreadSheetService.getColumnNameByIndex(firstCell.col + 1) + (firstCell.row + 1);
                lastHeader = dashboardSpreadSheetService.getColumnNameByIndex(lastCell.col + 1) + (lastCell.row);
                var colsRange = {
                    sheetName: dashboardTab.colsRange.sheetName,
                    firstHeader: firstHeader,
                    lastHeader: lastHeader,
                    firstNumber: dashboardTab.colsRange.firstCell,
                    lastNumber: dashboardTab.colsRange.lastCell,
                    selectedRanges: {
                        rowCount: lastCell.row - firstCell.row,
                        colCount: lastCell.col - firstCell.col + 1,
                        row: firstCell.row,
                        col: firstCell.col
                    }
                };
                selection.ranges.columns = colsRange;
            }

            //setValues 
            firstCell = getCellPosition(dashboardTab.valuesRange.firstCell);
            lastCell = getCellPosition(dashboardTab.valuesRange.lastCell);
            firstHeader = dashboardSpreadSheetService.getColumnNameByIndex(firstCell.col + 1) + (firstCell.row + 1);
            lastHeader = dashboardSpreadSheetService.getColumnNameByIndex(lastCell.col + 1) + (lastCell.row);
            var valuesRange = {
                sheetName: dashboardTab.valuesRange.sheetName,
                firstHeader: firstHeader,
                lastHeader: lastHeader,
                firstNumber: dashboardTab.valuesRange.firstCell,
                lastNumber: dashboardTab.valuesRange.lastCell,
                selectedRanges: {
                    rowCount: lastCell.row - firstCell.row,
                    colCount: lastCell.col - firstCell.col + 1,
                    row: firstCell.row,
                    col: firstCell.col
                }
            };
            selection.ranges.values = valuesRange;
            return selection;
        }

        //load saved dashboard
        function loadDashboardData(uuid) {
            DashboardFormService.getDashboard(uuid)
                .then(function(response) {
                    var dashboardData = response.data;
                    formCtrl.dashboardName = dashboardData.dashboardTitle;
                    highestID = dashboardData.tabs.length;
                    //formCtrl.xmlFormId = dashboardData.formId;
                    //formCtrl.UUID = dashboardData.dashboardUUID;
                    formCtrl.selectedModelID = dashboardData.modelId;

                    DashboardFormService.getChosenForm(dashboardData.formId)
                        .then(function(responseSpread) {
                            var jsonForm = responseSpread.data.jsonForm;

                            if (!$('#spreadContainer').wijspread('spread')) {
                                $('#spreadContainer').wijspread({
                                    sheetCount: 3
                                });
                            }
//                            spreadSheetElement = angular.element(document.querySelector('#spreadContainer'));
//                          spreadSheet = spreadSheetElement.wijspread("spread");
//                            spread = new GC.Spread.Sheets.Workbook(document.getElementById("spreadContainer"),{sheetCount:3});


                            spread = $('#spreadContainer').wijspread('spread');
                            loadSpreadDocument(jsonForm, spread);

                            formCtrl.selectedFlatForm.xml_form_id = responseSpread.data.flatFormId;
                            formCtrl.selectedFlatForm.vis_id = responseSpread.data.visId;

                            formCtrl.xmlForm = responseSpread.data.xmlForm;
                            // var spread = $('#spreadContainer').wijspread('spread');
                            // spread.grayAreaBackColor("#FCFDFD");
                            for (var i = 0; i < dashboardData.tabs.length; i++) {
                                var selection = setTab(dashboardData.tabs[i], i);
                                formCtrl.tabs[i] = selection;
                            }
                            if (formCtrl.tabs != null && formCtrl.tabs.length > 0) {
                                formCtrl.tabID = 0;
                                formCtrl.controllButtonsEnable = true;
                            } else {
                                formCtrl.tabID = -1;
                            }

                            if (VIEW == 'hierarchy') {
                                if (dashboardData.contextData) {
                                    formCtrl.contextData.expTreeString = dashboardData.contextData.exp;
                                    formCtrl.contextData.callFromTreeString = dashboardData.contextData.calendarFrom;
                                    formCtrl.contextData.callToTreeString = dashboardData.contextData.calendarTo;
                                    formCtrl.contextData.dataTypeString = dashboardData.contextData.dataType;
                                    formCtrl.contextData.ccTreeString = dashboardData.contextData.cc;
                                }
                                formCtrl.xmlForm = clearXmlForm(formCtrl.xmlForm);
                                formCtrl.xmlForm = setContextInXmlForm(formCtrl.xmlForm);
                                DashboardFormService.getSecurityList(formCtrl.selectedModelID).then(function(response) {
                                    var securityList = response.data;
                                    var isAccess = false;

                                    for (var i = 0; i < securityList.length; i++) {
                                        if (securityList[i] === formCtrl.contextData.ccTreeString.trim()) {
                                            isAccess = true
                                            i = securityList.length;
                                        }
                                    }

                                    if (isAccess === false) {
                                        formCtrl.contextData.ccTreeString = formCtrl.contextDataPlaceholder.account;
                                        formCtrl.xmlForm.properties.DIMENSION_0_VISID = formCtrl.contextDataPlaceholder.account;
                                    } else {
                                        testDashboard();
                                        //confirmDataSelect(true);
                                    }
                                });
                            } else {
                                //confirmDataSelect(true);
                            }
                            if (formCtrl.tabs.length > 0) {
                                formCtrl.tabID = 0;
                                formCtrl.tabs[formCtrl.tabID].tab.active = true;

                                formCtrl.readFromHiddenCells = formCtrl.tabs[formCtrl.tabID].ranges.readHiddenCells;
                                confirmDataSelect(true);
                            }
                        });
                });
        }

        function redraw() {
            if (formCtrl.showOnlySpread) {
                $timeout.cancel(redrawDelay);
                redrawDelay = $timeout(function() {
                    var fullHeight = $(window).height();
                    var otherElementsHeight = $("#container-navbar").outerHeight(true) + $("#container-main").outerHeight(true) + 5;
                    $("#spreadContainer").height(fullHeight - otherElementsHeight);
                    $("#spreadContainer").wijspread("refresh");
                }, 5);
            }
        }



        //********************************* Tabs *********************************************************
        //************************************ Tabs ******************************************************
        //*************************************** Tabs ***************************************************


        function getTabIndex(tabID) {
            for (var i = 0; i < formCtrl.tabs.length; i++) {
                if (formCtrl.tabs[i] !== undefined && formCtrl.tabs[i].tab.id == tabID) {
                    return i;
                }
            }
            return -1;
        }

        function validateTabName(newTabName, addTab, tabID) {
            if (!newTabName) {
                swal.showInputError('Tab name can not be empty');
                return false;
            }

            if (getTabIndex(newTabName) > -1) {
                swal.showInputError('Tab name have to be unique');
                return false;
            }
            return true;
        }

        function addTab() {
            swal({
                title: 'Add new tab',
                text: 'Chart name:',
                type: 'input',
                showCancelButton: true,
                closeOnConfirm: false,
                animation: 'slide-from-top',
                inputPlaceholder: 'Chart name...'
            }, function(inputValue) {
                if (validateTabName(inputValue)) {
                    swal.close();
                    //TODO some init
                    var selection = {};
                    selection.tab = {
                        title: inputValue,
                        id: 'chart' + highestID,
                        isConfigured: false,
                        active: true
                    };
                    selection.ranges = {
                        readHiddenCells: false
                    };
                    selection.values = {};
                    selection.chartType = {
                        value: formCtrl.chartAvailableTypes[0].value,
                        label: formCtrl.chartAvailableTypes[0].label
                    };
                    formCtrl.tabs.push(selection);
                    //formCtrl.controllButtonsEnable = false;
                    formCtrl.tabID = formCtrl.tabs.length - 1;
                    highestID = highestID + 1;

                    isValid = false;
                    formCtrl.showCharts = false;
                    formCtrl.showOnlySpread = false;

                    toggleAskIfReload();
                    $scope.$apply();
                }
            });
        }

        function renameTab(tabID) {
            var inputValue = "";
            for (var tab in formCtrl.tabs) {
                if (formCtrl.tabs[tab].tab.id === tabID) {
                    inputValue = formCtrl.tabs[tab].tab.title;
                }
            }
            swal({
                title: 'Rename tab',
                text: 'Chart name:',
                type: 'input',
                inputValue: inputValue,
                showCancelButton: true,
                closeOnConfirm: false,
                animation: 'slide-from-top',
                inputPlaceholder: 'Chart name...'
            }, function(inputValue) {
                if (validateTabName(inputValue)) {
                    swal.close();
                    for (var i = 0; i < formCtrl.tabs.length; i++) {
                        if (formCtrl.tabs[i] != null && formCtrl.tabs[i].tab.id === tabID) {
                            formCtrl.tabs[i].tab.title = inputValue;
                            break;
                        }
                    }
                    toggleAskIfReload();
                    $scope.$apply();
                }
            });
        }

        function deleteTab(tabID) {
            swal({
                    title: 'Are you sure you want to delete this tab?',
                    text: 'You will not be able to recover this tab!',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Yes, delete it!',
                    cancelButtonText: 'No, cancel!',
                    closeOnConfirm: true,
                    closeOnCancel: true
                },
                function(isConfirm) {
                    if (isConfirm) {
                        var foundedIndex;
                        for (var i = 0; i < formCtrl.tabs.length; i++) {
                            if (formCtrl.tabs[i] != null && formCtrl.tabs[i].tab.id === tabID) {
                                foundedIndex = i;
                                break;
                            }
                        }
                        formCtrl.tabs.splice(foundedIndex, 1);
                        if ('chart' + (highestID - 1) === tabID) {
                            highestID--;
                        }
                        isValid = false;
                        formCtrl.showCharts = false;
                        formCtrl.showOnlySpread = false;

                        if (formCtrl.tabs.length > 0) {
                            formCtrl.tabID = 0;
                            formCtrl.tabs[formCtrl.tabID].tab.active = true;

                            formCtrl.readFromHiddenCells = formCtrl.tabs[formCtrl.tabID].ranges.readHiddenCells;
                            confirmDataSelect(true);
                        }
                        $scope.$apply();
                    }
                });
        }

        function onTabSelect(tabName) {
            formCtrl.tabID = getTabIndex(tabName);
            formCtrl.tabs[formCtrl.tabID].tab.active = true;

            formCtrl.readFromHiddenCells = formCtrl.tabs[formCtrl.tabID].ranges.readHiddenCells;
            confirmDataSelect(true);
        }


        function setTabConfigured(tabName) {
            var foundedIndex;
            for (var i = 0; i < formCtrl.tabs.length; i++) {
                if (formCtrl.tabs[i] !== undefined && formCtrl.tabs[i].tab.id == tabName) {
                    foundedIndex = i;
                    break;
                }
            }
            formCtrl.tabs[foundedIndex].tab.isConfigured = true;
        }



        //********************************* Chart *********************************************************
        //************************************ Chart ******************************************************
        //*************************************** Chart ***************************************************


        function clearAllChartsData() {
            var i;
            for (i = 0; i < formCtrl.tabs.length; i++) {
                var selection = formCtrl.tabs[i];
                selection.tab.isConfigured = false;
                selection.ranges = {
                    readHiddenCells: false
                };
                formCtrl.readFromHiddenCells = false;
                selection.values = {};
                selection.chartType = {
                    value: formCtrl.chartAvailableTypes[0].value,
                    label: formCtrl.chartAvailableTypes[0].label
                };

                if (oldChartDataValues == null) {
                    oldChartDataValues = {
                        ranges: null
                    };
                } else {
                    oldChartDataValues.ranges = {
                        readHiddenCells: false
                    };
                    formCtrl.readFromHiddenCells = formCtrl.tabs[formCtrl.tabID].ranges.readHiddenCells;
                    oldChartDataValues.chartType = {
                        value: formCtrl.chartAvailableTypes[0].value,
                        label: formCtrl.chartAvailableTypes[0].label
                    };
                }

                isValid = false;
                formCtrl.showCharts = false;

                toggleAskIfReload();
                //$scope.$apply();
            }
        }

        function changeChartType(tabName) {
            for (var index in formCtrl.chartAvailableTypes) {
                if (formCtrl.chartAvailableTypes[index].label === formCtrl.tabs[formCtrl.tabID].chartType.label) {
                    formCtrl.tabs[formCtrl.tabID].chartType.value = formCtrl.chartAvailableTypes[index].value;
                    break;
                }
            }
            if (formCtrl.showCharts == true) {
                drawChart4Tab(formCtrl.tabs[formCtrl.tabID], formCtrl.tabs[formCtrl.tabID].values);
            }
        }

        function selectData(tabName) {
            formCtrl.tabID = getTabIndex(tabName);
            //hide charts if visible
            formCtrl.showCharts = false;
            //create backup           
            if (oldChartDataValues == null) {
                oldChartDataValues = {
                    ranges: null
                };
            } else {
                oldChartDataValues.ranges = angular.copy(formCtrl.tabs[formCtrl.tabID].ranges);
                formCtrl.readFromHiddenCells = formCtrl.tabs[formCtrl.tabID].ranges.readHiddenCells;
                oldChartDataValues.chartType = angular.copy(formCtrl.tabs[formCtrl.tabID].chartType);
            }
            //show spread
            formCtrl.showOnlySpread = true;
            dashboardSpreadSheetService.setSelectionColor("rgba(0,0,0, 0.3)", spread);

            $timeout(function() {
                redraw();
            }, 0);
            // $timeout(function () {
            //     redraw();
            // }, 1000);
        }

        function confirmDataSelect(silently) {
            //validate
            var validationError = validateCellsSelection(formCtrl.tabs[formCtrl.tabID]);
            if (validationError !== "") {
                if (silently === false) {
                    showValidationError('Chart will not be draw:', validationError);
                }
                return;
            }
            formCtrl.tabs[formCtrl.tabID].ranges.readHiddenCells = formCtrl.readFromHiddenCells;
            var values = dashboardSpreadSheetService.refreshSpreadValues(spread, formCtrl.tabs[formCtrl.tabID].ranges);
            var isPie = formCtrl.tabs[formCtrl.tabID].chartType.value === "pie";
            validationError = validateChartData(values, isPie);
            if (validationError !== '') {
                if (silently === false) {
                    showValidationError('Chart will not be draw:', validationError);
                }
                return;
            }
            formCtrl.tabs[formCtrl.tabID].values = values;
            //formCtrl.values = values;
            //      set global values
            //      set isValid = true;
            formCtrl.selected = formCtrl.consts.none;
            isValid = true;
            //      draw charts
            drawChart4Tab(formCtrl.tabs[formCtrl.tabID], values);
            //      hide spread
            formCtrl.showOnlySpread = false;
            //      show charts
            formCtrl.showCharts = true;
        }

        function cancelDataSelect() {
            //copy backup to global
            if (oldChartDataValues == null) {
                oldChartDataValues = {
                    ranges: null
                };
            } else {
                if (oldChartDataValues.ranges != null) {
                    formCtrl.tabs[formCtrl.tabID].ranges = angular.copy(oldChartDataValues.ranges);
                    //formCtrl.tabs[formCtrl.tabID].ranges.readHiddenCells = formCtrl.readFromHiddenCells;
                }
                if (oldChartDataValues.chartType != null) {
                    // formCtrl.tabs[formCtrl.tabID].chartType = angular.copy(oldChartDataValues.chartType);
                    formCtrl.tabs[formCtrl.tabID].chartType.value = oldChartDataValues.chartType.value;
                    formCtrl.tabs[formCtrl.tabID].chartType.label = oldChartDataValues.chartType.label;
                }
            }
            formCtrl.selected = formCtrl.consts.none;
            //hide spread
            formCtrl.showOnlySpread = false;
            //check isValid
            if (isValid) {
                //redraw
                //show charts
                formCtrl.showCharts = true;
            }
        }

        function validateCellsSelection(selection) {
            if (selection.chartType == null) {
                return 'Chart type is not defined';
            }
            var validationError = '';
            var ranges = selection.ranges;
            if (validationError === '') {
                validationError = ranges.rows != null ? '' : 'Rows are not defined';
            } else {
                validationError += ranges.rows != null ? '' : ', rows are not defined';
            }


            if (validationError === '') {
                validationError = ranges.values != null ? '' : 'Values are not defined';
            } else {
                validationError += ranges.values != null ? '' : ', values are not defined';
            }

            if (selection.chartType.value !== 'pie') {
                if (validationError === '') {
                    validationError = ranges.columns != null ? '' : 'Columns are not defined';
                } else {
                    validationError += ranges.columns != null ? '' : ', columns are not defined';
                }
            }
            if (validationError !== '') {
                return validationError;
            }
            if (selection.chartType.value === 'pie') {
                var valRanges = selection.ranges.values.selectedRanges;
                var rowRanges = selection.ranges.rows.selectedRanges;
                if ((rowRanges.rowCount !== valRanges.rowCount && rowRanges.rowCount !== valRanges.colCount) ||
                    (rowRanges.rowCount === valRanges.rowCount && rowRanges.rowCount === valRanges.colCount && valRanges.rowCount !== 1) ||
                    (rowRanges.rowCount === valRanges.rowCount && valRanges.colCount !== 1) ||
                    (rowRanges.rowCount === valRanges.colCount && valRanges.rowCount !== 1)) {
                    validationError = 'Pie chart requires flat data, labels count need to be equal value count.';
                }
            } else {
                var valRanges = selection.ranges.values.selectedRanges;
                var colRanges = selection.ranges.columns.selectedRanges;
                var rowRanges = selection.ranges.rows.selectedRanges;
                if (rowRanges.rowCount !== valRanges.rowCount || colRanges.colCount !== valRanges.colCount) {
                    validationError = 'You selected labels with range size: ' + rowRanges.rowCount + 'x' + colRanges.colCount + ' but values range size is: ' + valRanges.rowCount + 'x' + valRanges.colCount;
                }
            }
            return validationError;
        }

        function validateChartData(values, isPie) {
            var validationError = '';
            if (isPie) {
                if (values.rows == null || values.values == null) {
                    validationError = "Null pointer exception";
                } else if (values.rows.length === 0) {
                    validationError = "Label for rows must be of a valid string";
                }
            } else {
                if (values.rows == null || values.columns == null || values.values == null) {
                    validationError = "Null pointer exception";
                } else if (values.rows.length === 0 || values.columns.length === 0) {
                    validationError = "Label for rows and columns must be of a valid string";
                }
            }
            return validationError;
        }

        function showValidationError(validationTitle, validationError) {
            swal({
                title: validationTitle,
                text: validationError,
                type: 'warning'
            });
        }



        function drawChart4Tab(selection, values) {
            var i;
            //var title = formCtrl.chartTitles[tabName];
            var title = selection.tab.title;
            var series = [];
            var categories = [];
            var isPie = selection.chartType ? selection.chartType.value === 'pie' : undefined;

            if (!isPie) {
                for (i = 0; i < values.rows.length; i++) {
                    series.push({
                        name: values.rows[i],
                        data: values.values[i]
                    });
                    if (selection.chartType.value != null) {
                        series[i].type = selection.chartType.value;
                    }
                }
                categories = values.columns;
            } else {
                var data = [];
                var length = values.values[0].length === 1 ? values.values.length : values.values[0].length;
                for (i = 0; i < length; i++) {
                    data.push({
                        category: values.rows[i],
                        // value: values.values.length == 1 ? values.values[0][i] : values.values[i][0]
                        value: values.values[0].length === 1 ? values.values[i][0] : values.values[0][i]
                    });
                }

                series.push({
                    data: data,
                    type: 'pie'
                });

                //categories = values.rows;
            }

            //var validationResult = validateChartData(selection.ranges, isPie);
            //if (!validationResult.validationOK) {
            // if (validationResult !== "") {
            //     showValidationError('Chart will not be draw:', validationResult);
            //     return;
            // } else {


            // formCtrl.setRowsFlag = false;
            // formCtrl.setColumnsFlag = false;
            // formCtrl.setValuesFlag = false;



            //setTabConfigured(id);
            selection.tab.isConfigured = true;
            //}



            if (!isPie) {
                $timeout(function() {
                    $('#myChart-' + selection.tab.id).kendoChart({
                        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
                        title: {
                            text: title
                        },
                        legend: {
                            position: 'top'
                        },
                        series: series,
                        categoryAxis: {
                            categories: categories
                        },
                        tooltip: {
                            visible: true,
                            format: '{0}',
                            template: '#= series.name #: #= value #'
                        }
                    });
                }, 0);

            } else {
                $timeout(function() {
                    $('#myChart-' + selection.tab.id).kendoChart({
                        seriesColors: ["#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#70AD47", "#C70A0A", "#8C12B8", "#980000", "#CFB53B", "#38B0DE", "#DB70DB", "#2F2F4F", "#32CD32", "#E47833", "#6B238E"],
                        plotArea: {
                            padding: 10
                        },
                        series: series,
                        tooltip: {
                            visible: true,
                            format: '{0}',
                            template: '#= category #: #= value #'
                        },
                        labels: {
                            visible: true
                        },
                        legend: {
                            position: 'top'
                        }
                    });
                }, 0);
            }
        }



        //********************************* SPREAD *********************************************************
        //************************************ SPREAD ******************************************************
        //*************************************** SPREAD ***************************************************


        function loadSpreadDocument(jsonForm) {
            if (jsonForm == null || jsonForm.trim() === "") {
                return;
            }
            if (!$('#spreadContainer').wijspread('spread')) {
                $('#spreadContainer').wijspread({
                    sheetCount: 3
                });
            }
            spread = $('#spreadContainer').wijspread('spread');
            //  else if (spreadIt != null) {
            // 	spread = spreadIt;
            // }

            spread.fromJSON(JSON.parse(jsonForm).spread);

            // block spread edition
            dashboardSpreadSheetService.blockSpreadEdition(spread);

            // bind spread selection
            for (var i = 0; i < spread.getSheetCount(); i++) {
                var sheet = spread.getSheet(i);
                sheet.bind($.wijmo.wijspread.Events.SelectionChanged, function(sender, args) {                    //nie wiem jak rozroznic zaznaczenie od rozwijania grupy
                    var selection = angular.copy(dashboardSpreadSheetService.getSelectedCellsAddresses(spread));
                    if (selection == null) {
                        return;
                    } else if (selection.isMultiselect) {
                        showValidationError('Chart will not be draw:', "Can't process multiselection.");
                    } else if (formCtrl.selected === formCtrl.consts.rows) {
                        if (selection.selectedRanges.colCount > 1) {
                            showValidationError('Selection error:', 'Rows can not be multidimensional');
                        } else {
                            formCtrl.tabs[formCtrl.tabID].ranges.rows = selection;
                            $scope.$apply();
                        }
                    } else if (formCtrl.selected === formCtrl.consts.cols) {
                        if (selection.selectedRanges.rowCount > 1) {
                            showValidationError('Selection error:', 'Columns can not be multidimensional');
                        } else {
                            formCtrl.tabs[formCtrl.tabID].ranges.columns = selection;
                            $scope.$apply();
                        }
                    } else if (formCtrl.selected === formCtrl.consts.vals) {
                        if (formCtrl.tabs[formCtrl.tabID].chartType.value != null && formCtrl.tabs[formCtrl.tabID].chartType.value === "pie" &&
                            selection.selectedRanges.colCount !== 1 && selection.selectedRanges.rowCount !== 1) {
                            showValidationError('Selection error:', 'Values can not be multidimensional');
                        } else {
                            formCtrl.tabs[formCtrl.tabID].ranges.values = selection;
                            $scope.$apply();
                        }
                    } else if (formCtrl.selected === formCtrl.consts.none) {
                        dashboardSpreadSheetService.setSelectionColor("rgba(0,0,0, 0.3)", spread);
                    }

                });
            }
        }

        function isSelected(type) {
            return formCtrl.selected === type;
        }

        function setRows() {
            dashboardSpreadSheetService.setSelectionColor("rgba(238,0,0, 0.3)", spread);
            formCtrl.selected = formCtrl.consts.rows;
            formCtrl.showOnlySpread = true;

            if (formCtrl.tabs[formCtrl.tabID].ranges != null && formCtrl.tabs[formCtrl.tabID].ranges.rows != null) {
                dashboardSpreadSheetService.addSelection(formCtrl.tabs[formCtrl.tabID].ranges.rows, spread);
            }
            //$scope.$apply();
        }

        function setColumns() {
            dashboardSpreadSheetService.setSelectionColor("rgba(0,238,0, 0.3)", spread);
            formCtrl.selected = formCtrl.consts.cols;
            formCtrl.showOnlySpread = true;
            if (formCtrl.tabs[formCtrl.tabID].ranges != null && formCtrl.tabs[formCtrl.tabID].ranges.columns != null) {
                dashboardSpreadSheetService.addSelection(formCtrl.tabs[formCtrl.tabID].ranges.columns, spread);
            }
            //$scope.$apply();
        }

        function setValues() {
            dashboardSpreadSheetService.setSelectionColor("rgba(0,0,238, 0.3)", spread);
            formCtrl.selected = formCtrl.consts.vals;

            formCtrl.showOnlySpread = true;
            if (formCtrl.tabs[formCtrl.tabID].ranges != null && formCtrl.tabs[formCtrl.tabID].ranges.values != null) {
                dashboardSpreadSheetService.addSelection(formCtrl.tabs[formCtrl.tabID].ranges.values, spread);
            }
            //$scope.$apply();
        }



        //********************************* Select Form *********************************************************
        //************************************ Select Form ******************************************************
        //*************************************** Select Form ***************************************************

        function showModal() {
            var modalInstance = $modal.open({
                backdrop: 'static',
                windowClass: 'softpro-modals',
                size: 'lg',
                template: '<dashboard-select-flat-form></dashboard-select-flat-form>',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {

                        function close() {
                            $modalInstance.dismiss('cancel');
                            $scope.apply();
                        }

                        $scope.$on('dashboardSelectFlatForm:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        }

        $scope.$on('dashboardSelectFlatForm:formSelected', function(event, data) {
            // formCtrl.selectedFlatForm = {
            //     vis_id: formCtrl.item.vis_id,
            //     xml_form_id: formCtrl.item.xml_form_id
            // };
            // angular.copy(data, formCtrl.item);
            formSelected(data);
        });

        function manageFormChanged4Freeform(newModelID, jsonForm, form) {
            if (formCtrl.selectedModelID) {
                if (newModelID !== formCtrl.selectedModelID) {
                    swal({
                            title: "Are you sure?",
                            text: "You will have to select all chart data",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonClass: "btn-danger",
                            confirmButtonText: "Yes, change form.",
                            closeOnConfirm: false
                        },
                        function(isConfirm) {
                            if (isConfirm) {
                                formCtrl.selectedModelID = newModelID;
                                loadSpreadDocument(jsonForm);
                                formCtrl.selectedFlatForm = form;
                                clearAllChartsData();
                                toggleAskIfReload();
                            } else {
                                formCtrl.selectedFlatForm = oldContextData.form;
                                //formCtrl.item = oldContextData.form;
                            }


                            swal.close();
                        });
                } else {
                    formCtrl.selectedFlatForm = form;
                    loadSpreadDocument(jsonForm);
                    clearAllChartsData();
                }
            } else {
                formCtrl.selectedFlatForm = form;
                formCtrl.selectedModelID = newModelID;
                loadSpreadDocument(jsonForm);
            }
        }

        function manageFormChanged4Hierarchy(newModelID, jsonForm, form) {
            if (form == null || newModelID == null || jsonForm == null) {
                return;
            }
            //modelId: string or number???
            //parse to number and compare
            var newId = parseInt(newModelID);
            var oldId = parseInt(formCtrl.selectedModelID);
            //check for NaN, NaN !== NaN
            if (newId !== newId) {
                newId = null;
            }
            if (oldId !== oldId) {
                oldId = null;
            }
            if (newId == null) {
                return;
            }
            //           if (newModelID !== formCtrl.selectedModelID) {
            if (newId !== oldId) {
                if (formCtrl.contextData.expTreeString !== formCtrl.contextDataPlaceholder.account ||
                    formCtrl.contextData.callFromTreeString !== formCtrl.contextDataPlaceholder.calendarfrom ||
                    formCtrl.contextData.callToTreeString !== formCtrl.contextDataPlaceholder.calendarto ||
                    formCtrl.contextData.dataTypeString !== formCtrl.contextDataPlaceholder.dataType ||
                    formCtrl.contextData.ccTreeString !== formCtrl.contextDataPlaceholder.business) {

                    swal({
                            title: "Are you sure?",
                            text: "You will have to select all context and chart data",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonClass: "btn-danger",
                            confirmButtonText: "Yes, change form.",
                            closeOnConfirm: false
                        },
                        function(isConfirm) {
                            console.log('HANDLE IT!', isConfirm);
                            if (isConfirm) {
                                formCtrl.contextData.expTreeString = formCtrl.contextDataPlaceholder.account;
                                formCtrl.contextData.callFromTreeString = formCtrl.contextDataPlaceholder.calendarfrom;
                                formCtrl.contextData.callToTreeString = formCtrl.contextDataPlaceholder.calendarto;
                                formCtrl.contextData.dataTypeString = formCtrl.contextDataPlaceholder.dataType;
                                formCtrl.contextData.ccTreeString = formCtrl.contextDataPlaceholder.business;
                                formCtrl.selectedModelID = newModelID;
                                loadSpreadDocument(jsonForm);
                                formCtrl.selectedFlatForm = form;
                                clearAllChartsData();
                                formCtrl.xmlForm = setContextInXmlForm(formCtrl.xmlForm);
                            } else {
                                formCtrl.selectedCcTree = oldContextData.selectedCcTree;
                                formCtrl.selectedExpTree = oldContextData.selectedExpTree;
                                formCtrl.selectedCallTree = oldContextData.selectedCallTree;
                                formCtrl.selectedDataType = oldContextData.selectedDataType;
                                formCtrl.contextData.expTreeString = oldContextData.expTreeString;
                                formCtrl.contextData.callFromTreeString = oldContextData.callFromTreeString;
                                formCtrl.contextData.callToTreeString = oldContextData.callToTreeString;
                                formCtrl.contextData.dataTypeString = oldContextData.dataTypeString;
                                formCtrl.contextData.ccTreeString = oldContextData.ccTreeString;
                                formCtrl.selectedFlatForm = oldContextData.form;
                                //formCtrl.item = oldContextData.form;
                                formCtrl.xmlForm = oldContextData.workbook;
                            }
                            swal.close();
                        });
                } else {
                    formCtrl.selectedModelID = newModelID;
                    loadSpreadDocument(jsonForm);
                    clearAllChartsData();
                    formCtrl.selectedFlatForm = form;
                }
            } else {
                loadSpreadDocument(jsonForm);
                clearAllChartsData();
                formCtrl.selectedFlatForm = form;
            }
            //hide chart
        }

        function formSelected(form) {

            oldContextData = {
                //back up context
                workbook: formCtrl.xmlForm,
                form: formCtrl.selectedFlatForm,
                selectedCcTree: formCtrl.selectedCcTree,
                selectedExpTree: formCtrl.selectedExpTree,
                selectedCallTree: formCtrl.selectedCallTree,
                selectedDataType: formCtrl.selectedDataType,
                expTreeString: formCtrl.contextData.expTreeString,
                callFromTreeString: formCtrl.contextData.callFromTreeString,
                callToTreeString: formCtrl.contextData.callToTreeString,
                dataTypeString: formCtrl.contextData.dataTypeString,
                ccTreeString: formCtrl.contextData.ccTreeString
            };

            //formCtrl.xmlFormId = form.xml_form_id;
            DashboardFormService.getChosenForm(form.xml_form_id)
                .then(function(response) {
                    var jsonForm = response.data.jsonForm;
                    formCtrl.xmlForm = response.data.xmlForm;
                    formCtrl.xmlForm = clearXmlForm(formCtrl.xmlForm);

                    //clearAllChartsData();

                    DashboardFormService.getModelId(response.data.financeCubeId)
                        .then(function(response2) {
                            if (VIEW === formCtrl.types.hierarchy) {
                                manageFormChanged4Hierarchy(response2.data, jsonForm, form);
                            } else {
                                manageFormChanged4Freeform(response2.data, jsonForm, form);
                            }
                        });
                    formCtrl.controllButtonsEnable = true;
                });

        }

        function clearXmlForm(xmlForm) {
            if (xmlForm != null) {
                delete xmlForm.properties.DIMENSION_0_VISID;
                delete xmlForm.properties.DIMENSION_1_VISID;
                delete xmlForm.properties.DIMENSION_2_VISID;
                delete xmlForm.properties.DATA_TYPE;
            }
            return xmlForm;
        }

        function setContextInXmlForm(xmlForm) {
            xmlForm.properties.DIMENSION_1_VISID = formCtrl.contextData.expTreeString.trim();
            xmlForm.properties.DIMENSION_2_VISID_FROM = formCtrl.contextData.callFromTreeString.trim();
            xmlForm.properties.DIMENSION_2_VISID_TO = formCtrl.contextData.callToTreeString.trim();
            xmlForm.properties.DATA_TYPE = formCtrl.contextData.dataTypeString.trim();
            xmlForm.properties.DIMENSION_0_VISID = formCtrl.contextData.ccTreeString.trim();
            return xmlForm;
        }



        // function isEqualChartSettings() {
        //     var tabID = formCtrl.tabID;
        //     if (formCtrl.tabs[tabID].ranges.rows != oldChartDataValues.ranges.rows)
        //         return false;
        //     if (formCtrl.tabs[tabID].ranges.columns != oldChartDataValues.ranges.columns)
        //         return false;
        //     if (formCtrl.tabs[tabID].ranges.values != oldChartDataValues.ranges.values)
        //         return false;
        //     if (formCtrl.tabs[tabID].charType != oldChartDataValues.charType)
        //         return false;
        //     if (formCtrl.tabs[tabID].ranges.readHiddenCells != oldChartDataValues.ranges.readHiddenCells) {
        //         return false;
        //     } else {
        //         return true;
        //     }
        // }



        //********************************* Test Dashboard *********************************************************
        //************************************ Test Dashboard ******************************************************
        //*************************************** Test Dashboard ***************************************************


        function createErrorMessageForSheetName(chartTitle, sheetName) {
            var errorMsg = 'Chart: \'' + chartTitle + '\' tries to use data from sheet: \'' + sheetName +
                '\' but loaded form do not contain such sheet.';
            return errorMsg;
        }



        function testDashboard() {
            //formCtrl.xmlForm = setContextInXmlForm(formCtrl.xmlForm);
            validateXmlFormProperties();
        }

        function loadTestDashboard() {
            var showErrors = true;
            // if (ISEDIT === formCtrl.views.open) {
            //     showErrors = false;
            // }
            // else{
            //     showErrors = true;
            // }
            DashboardFormService.testDashboard(formCtrl.xmlForm, showErrors).then(function(response) {
                if (response.data.error === true) {
                    swal("Error!", response.data.message, "error");
                } else {
                    //var xmlForm = response.data;
                    // var xmlForm = clearXmlForm(response.data);
                    // xmlForm = setContextInXmlForm(xmlForm);
                    dashboardSpreadSheetService.setUpCells(response.data, spread);

                    if (formCtrl.tabs.length > 0) {
                        if (formCtrl.tabs[formCtrl.tabID].tab.active === true) {
                            confirmDataSelect(true);
                        }
                    }
                    //refreshSpreadValues();
                    //swal("Success!", response.data.message, "success");
                }
            });
        }

        function validateXmlFormProperties() {
            var props = formCtrl.xmlForm.properties;
            var modelVisId = props.MODEL_VISID;
            if (modelVisId == null || modelVisId === "") {
                // if model is not set - don't allow run test
                swal({
                    title: "Model is not set.",
                    text: "Please select Model in Workbook Properties window in Flat Form Editor.",
                    type: "warning",
                    confirmButtonColor: "#5cb85c",
                }, function(isConfirm) {});
            } else if (props.DIMENSION_0_VISID === undefined || props.DIMENSION_0_VISID === "" || props.DIMENSION_0_VISID === null || props.DIMENSION_0_VISID === formCtrl.contextDataPlaceholder.business ||
                props.DIMENSION_1_VISID === undefined || props.DIMENSION_1_VISID === "" || props.DIMENSION_1_VISID === null || props.DIMENSION_1_VISID === formCtrl.contextDataPlaceholder.account ||
                props.DIMENSION_2_VISID_FROM === undefined || props.DIMENSION_2_VISID_FROM === "" || props.DIMENSION_2_VISID_FROM === null || props.DIMENSION_2_VISID_FROM === formCtrl.contextDataPlaceholder.calendarfrom ||
                props.DIMENSION_2_VISID_TO === undefined || props.DIMENSION_2_VISID_TO === "" || props.DIMENSION_2_VISID_TO === null || props.DIMENSION_2_VISID_TO === formCtrl.contextDataPlaceholder.calendarto ||
                props.DATA_TYPE === undefined || props.DATA_TYPE === "" || props.DATA_TYPE === null || props.DATA_TYPE === formCtrl.contextDataPlaceholder.dataType
            ) {
                // if data type or any of dimension hierarchies is not set - ask if run test
                swal({
                    title: "Some test parameters \n are not set",
                    text: "Please make sure that all context variables are set.",
                    type: "warning",
                    //showCancelButton: true,
                    confirmButtonColor: "#5cb85c",
                    confirmButtonText: "Ok"
                }, function(isConfirm) {
                    if (isConfirm) {
                        //formCtrl.onTabSelect('Test');

                    }
                });
            } else {
                var from = props.DIMENSION_2_VISID_FROM.split("/");
                var to = props.DIMENSION_2_VISID_TO.split("/");
                var yearFrom = parseInt(from[1]);
                var yearTo = parseInt(to[1]);
                if (yearFrom > yearTo) {
                    showWarning("Selected years are not overlapping", "No range for years " + yearFrom + " - " + yearTo, "warning", false, "Ok");
                    return;
                }
                if (from.length === 3 && to.length === 3) {
                    var monthFrom = parseInt(from[2]);
                    var monthTo = parseInt(to[2]);
                    if (yearFrom === yearTo) {
                        if (monthFrom > monthTo && monthTo > 0) {
                            showWarning("Selected months are not overlapping", "No range for months " + monthFrom + " - " + monthTo, "warning", false, "Ok");
                            return;
                        }
                    }
                }
                // if all parameters are set - run test
                //formCtrl.onTabSelect('Test');
                loadTestDashboard();
            }
        }

        function showWarning(title, message, type, showCancelButton, confirmButtontext, action) {
            swal({
                title: title,
                text: message,
                type: type,
                showCancelButton: showCancelButton,
                confirmButtonColor: "#5cb85c",
                confirmButtonText: confirmButtontext
            }, action);
        }
        // function changeHideCells() {
        //     formCtrl.hideValues[formCtrl.tabID] = !formCtrl.hideCells;
        // }



        //********************************* Save Dashboard *********************************************************
        //************************************ Save Dashboard ******************************************************
        //*************************************** Save Dashboard ***************************************************


        function validateContextDimensionsForHierarchy() {
            if (formCtrl.contextData.expTreeString === formCtrl.contextDataPlaceholder.account) {
                return false;
            }
            if (formCtrl.contextData.callFromTreeString === formCtrl.contextDataPlaceholder.calendarfrom) {
                return false;
            }
            if (formCtrl.contextData.callToTreeString === formCtrl.contextDataPlaceholder.calendarto) {
                return false;
            }
            if (formCtrl.contextData.dataTypeString === formCtrl.contextDataPlaceholder.dataType) {
                return false;
            }
            if (formCtrl.contextData.ccTreeString === formCtrl.contextDataPlaceholder.business) {
                return false;
            }
            return true;
        }

        function validateSaveDashboard() {
            if (!formCtrl.tabs.length) {
                return "You can not save dashboard with no tabs.";
            }

            if (formCtrl.dashboardName == null || formCtrl.dashboardName === "") {
                return "Dashboard name can not be empty";
            }
            if (VIEW === formCtrl.types.hierarchy) {
                if (!validateContextDimensionsForHierarchy()) {
                    return "Please select all dimensions";
                }
            }

            var validationError = '';
            var notConfiguredTabs = '';
            for (var i = 0; i < formCtrl.tabs.length; i++) {
                if (!formCtrl.tabs[i].tab.isConfigured) {
                    notConfiguredTabs = notConfiguredTabs === '' ? notConfiguredTabs + formCtrl.tabs[i].tab.title : notConfiguredTabs + ', ' + formCtrl.tabs[i].tab.title;
                }

            }
            if (notConfiguredTabs !== '') {
                validationError = 'You can not save, because those tabs are not configured: ' + notConfiguredTabs;
                return validationError;
            }

            //var spread = $('#spreadContainer').wijspread('spread');
            var sheet;
            for (var i = 0; i < formCtrl.tabs.length; i++) {
                //sheet = spread.getSheetFromName(formCtrl.rowsRange[formCtrl.tabs[i].id].sheetName);
                sheet = spread.getSheetFromName(formCtrl.tabs[i].ranges.rows.sheetName);
                if (sheet == null) {
                    return createErrorMessageForSheetName(formCtrl.tabs[i].tab.title, formCtrl.tabs[i].ranges.rows.sheetName);
                }

                if (formCtrl.tabs[i].chartType.value !== 'pie') {
                    sheet = spread.getSheetFromName(formCtrl.tabs[i].ranges.columns.sheetName);
                    if (sheet == null) {
                        return createErrorMessageForSheetName(formCtrl.tabs[i].tab.title, formCtrl.tabs[i].ranges.columns.sheetName);
                    }
                }

                sheet = spread.getSheetFromName(formCtrl.tabs[i].ranges.values.sheetName);
                if (sheet == null) {
                    return createErrorMessageForSheetName(formCtrl.tabs[i].tab.title, formCtrl.tabs[i].ranges.values.sheetName);
                }
            }
            return validationError;
        }

        //formVisId
        //modelId
        //context
        //tabs
        //dashboardname
        function saveDashboard() {
            var validationError = validateSaveDashboard();
            if (validationError !== '') {
                showValidationError('Can not save:', validationError);
                return;
            }
            var tabFormSelected = formCtrl.selectedFlatForm.xml_form_id;
            var dashboardToSave = {
                formId: tabFormSelected,
                modelId: formCtrl.selectedModelID,
                contextData: {
                    exp: formCtrl.contextData.expTreeString,
                    calendarFrom: formCtrl.contextData.callFromTreeString,
                    calendarTo: formCtrl.contextData.callToTreeString,
                    dataType: formCtrl.contextData.dataTypeString,
                    cc: formCtrl.contextData.ccTreeString
                },
                //contextData: formCtrl.contextData,
                tabs: [],
                type: VIEW.toUpperCase(),
                dashboardUUID: $UUID === 'null' ? null : $UUID
            };
            dashboardToSave.dashboardTitle = formCtrl.dashboardName;

            for (var i = 0; i < formCtrl.tabs.length; i++) {
                if (formCtrl.tabs[i] != null) {
                    //var tabID = formCtrl.tabs[i].tab.id;
                    var tabRowsRange = formCtrl.tabs[i].ranges.rows;
                    var tabColsRange = formCtrl.tabs[i].ranges.columns;
                    var tabValuesRange = formCtrl.tabs[i].ranges.values;
                    var tabTitle = formCtrl.tabs[i].tab.title;
                    var tabChartType = formCtrl.tabs[i].chartType.value;
                    var tabHideCell = "false";
                    if (formCtrl.tabs[i].ranges.readHiddenCells != null) {
                        tabHideCell = "" + !formCtrl.tabs[i].ranges.readHiddenCells;
                    }
                    dashboardToSave.tabs.push({
                        tabName: tabTitle,
                        rowsRange: {
                            sheetName: tabRowsRange.sheetName,
                            firstCell: tabRowsRange.firstNumber,
                            lastCell: tabRowsRange.lastNumber
                        },
                        hideCells: tabHideCell,
                        colsRange: {
                            sheetName: tabChartType === 'pie' ? null : tabColsRange.sheetName,
                            firstCell: tabChartType === 'pie' ? null : tabColsRange.firstNumber,
                            lastCell: tabChartType === 'pie' ? null : tabColsRange.lastNumber
                        },
                        valuesRange: {
                            sheetName: tabValuesRange.sheetName,
                            firstCell: tabValuesRange.firstNumber,
                            lastCell: tabValuesRange.lastNumber
                        },
                        chartType: tabChartType
                    });
                }
            }

            DashboardFormService.saveDashboard(dashboardToSave).then(function(response) {
                if (response.data.error === true) {
                    swal("Error!", response.data.message, "error");
                } else {
                    swal("Success!", response.data.message, "success");
                    if (response.data.data.uuid != null && response.data.data.uuid != "") {
                        $UUID = response.data.data.uuid;
                    }
                    CoreCommonsService.askIfReload = false;
                }
            });
        }



        //********************************* Context Data *********************************************************
        //************************************ Context Data ******************************************************
        //*************************************** Context Data ***************************************************



        function setContextData(type) {
            var modelId = formCtrl.selectedModelID;
            if (modelId === null || modelId === undefined) {
                sweetAlert("Not selected Flat Form", "Please select Flat Form first");
                return;
            }
            var selectedExpTree, selectedCallTree, selectedDataType, selectedCcTree;
            var treesVisibility, modalTitle, checkboxState, tabIndex;
            var multiple = false;
            switch (type) {
                case "account":
                    selectedExpTree = [];
                    modalTitle = "Choose Account Dimension";
                    treesVisibility = [false, true, false, false];
                    tabIndex = 0;
                    break;
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
                case "dataType":
                    selectedDataType = [];
                    modalTitle = "Choose Data Type";
                    treesVisibility = [false, false, false, true];
                    tabIndex = 2;
                    break;
                case "business":
                    selectedCcTree = [];
                    modalTitle = "Choose Business Dimension";
                    treesVisibility = [true, false, false, false];
                    tabIndex = 2;
                    break;
            }

            if (type === "business") {
                var modalInstance = $modal.open({
                    template: '<business-chooser modal-title="modalTitle" selected-cc-tree="selectedCcTree" model-id="modelId" ok="ok()" close="close()"></business-chooser>',
                    windowClass: 'sub-system-modals softpro-modals',
                    backdrop: 'static',
                    size: 'lg',
                    controller: ['$scope', '$modalInstance',
                        function($scope, $modalInstance) {
                            $scope.modalTitle = "Choose Business Dimension";
                            $scope.selectedCcTree = selectedCcTree;
                            $scope.modelId = modelId;

                            $scope.ok = function() {
                                updateContextData($scope.selectedCcTree, "business");
                                $scope.close();
                            };

                            $scope.close = function() {
                                $modalInstance.dismiss('cancel');
                            };
                        }
                    ]
                });
            } else {
                var modalInstance = $modal.open({
                    template: '<trees-chooser modal-title="modalTitle" selected-exp-tree="selectedExpTree" selected-call-tree="selectedCallTree" selected-data-type="selectedDataType" model-id="modelId" tab-index="tabIndex" multiple="multiple" ok="ok()" trees-visibility="treesVisibility" close="close()" checkbox-state="checkboxState"></trees-chooser>',
                    windowClass: 'sub-system-modals softpro-modals',
                    backdrop: 'static',
                    size: 'lg',
                    controller: ['$scope', '$modalInstance',
                        function($scope, $modalInstance) {
                            $scope.modalTitle = modalTitle;
                            $scope.selectedExpTree = selectedExpTree;
                            $scope.selectedCallTree = selectedCallTree;
                            $scope.selectedDataType = selectedDataType;
                            $scope.modelId = modelId;
                            $scope.tabIndex = tabIndex;
                            $scope.multiple = multiple;
                            $scope.treesVisibility = treesVisibility;
                            $scope.checkboxState = checkboxState;

                            //formCtrl.multiple = true;

                            $scope.ok = function() {
                                var dimensions;
                                switch (type) {
                                    case "account":
                                        updateContextData($scope.selectedExpTree, "account");
                                        break;
                                    case "calendarfrom":
                                        updateContextData($scope.selectedCallTree, "calendarfrom");
                                        break;
                                    case "calendarto":
                                        updateContextData($scope.selectedCallTree, "calendarto");
                                        break;
                                    case "dataType":
                                        updateContextData($scope.selectedDataType, "dataType");
                                        break;

                                }
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
            switch (type) {
                case "account":
                    if (str == "") {
                        formCtrl.contextData.expTreeString = formCtrl.contextDataPlaceholder.account;
                        formCtrl.xmlForm.properties.DIMENSION_1_VISID = null;
                    } else {
                        if (str != formCtrl.xmlForm.properties.DIMENSION_1_VISID) {
                            toggleAskIfReload();
                        }
                        formCtrl.contextData.expTreeString = str;
                        formCtrl.xmlForm.properties.DIMENSION_1_VISID = str.trim();
                    }
                    break;
                case "calendarfrom":
                    if (str == "") {
                        formCtrl.contextData.callFromTreeString = formCtrl.contextDataPlaceholder.calendarfrom;
                        formCtrl.xmlForm.properties.DIMENSION_2_VISID_FROM = null;
                    } else {
                        if (str != formCtrl.xmlForm.properties.DIMENSION_2_VISID_FROM) {
                            toggleAskIfReload();
                        }
                        formCtrl.contextData.callFromTreeString = str;
                        formCtrl.xmlForm.properties.DIMENSION_2_VISID_FROM = str.trim();
                        if (formCtrl.contextData.callToTreeString === formCtrl.contextDataPlaceholder.calendarto) {
                            formCtrl.contextData.callToTreeString = str;
                            formCtrl.xmlForm.properties.DIMENSION_2_VISID_TO = str.trim();
                        }
                    }
                    break;
                case "calendarto":
                    if (str == "") {
                        formCtrl.contextData.callToTreeString = formCtrl.contextDataPlaceholder.calendarto;
                        formCtrl.xmlForm.properties.DIMENSION_2_VISID_TO = null;
                    } else {
                        if (str != formCtrl.xmlForm.properties.DIMENSION_2_VISID_TO) {
                            toggleAskIfReload();
                        }
                        formCtrl.contextData.callToTreeString = str;
                        formCtrl.xmlForm.properties.DIMENSION_2_VISID_TO = str.trim();
                        if (formCtrl.contextData.callFromTreeString === formCtrl.contextDataPlaceholder.calendarfrom) {
                            formCtrl.contextData.callFromTreeString = str;
                            formCtrl.xmlForm.properties.DIMENSION_2_VISID_FROM = str.trim();
                        }
                    }
                    break;
                case "dataType":
                    if (str == "") {
                        formCtrl.contextData.dataTypeString = formCtrl.contextDataPlaceholder.dataType;
                        formCtrl.xmlForm.properties.DATA_TYPE = null;
                    } else {
                        if (str != formCtrl.xmlForm.properties.DATA_TYPE) {
                            toggleAskIfReload();
                        }
                        formCtrl.contextData.dataTypeString = str;
                        formCtrl.xmlForm.properties.DATA_TYPE = str.trim();
                    }
                    break;
                case "business":
                    if (str == "") {
                        formCtrl.contextData.ccTreeString = formCtrl.contextDataPlaceholder.business;
                        formCtrl.xmlForm.properties.DIMENSION_0_VISID = null;
                    } else {
                        if (str != formCtrl.xmlForm.properties.DIMENSION_0_VISID) {
                            toggleAskIfReload();
                        }
                        formCtrl.contextData.ccTreeString = str;
                        formCtrl.xmlForm.properties.DIMENSION_0_VISID = str.trim();
                    }
                    break;
            }
            if (ISEDIT === formCtrl.views.open) {
                testDashboard();
            }
        }
    }
})();