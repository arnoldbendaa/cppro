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
        .module('adminApp.loggedHistoryPage')
        .controller('LoggedHistoryPageController', LoggedHistoryPageController);

    /* @ngInject */
    function LoggedHistoryPageController($scope, $compile, $timeout, PageService, CoreCommonsService, LoggedHistoryPageService) {

        $scope.users = [];
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_loggedHisotry";
        // try to resize and sort colums based on the cookie
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        var selectedUsersIds = [];
        var rows = {};
        var originalRow;
        var delay;
        var data = {};
        var availablePagination;
        const OFFSET = 20;
        var startDate = new Date();
        $scope.model = {
            selectedUser: "",
            periodFrom: new Date(),
            dateFrom: new Date(),
            dateTo: getTomorrowDate(startDate)
        };
        var oldDate;
        $scope.format = 'dd/MMM/yy';
        $scope.isDateChanged = false;
        $scope.openDateFrom = openDateFrom;
        $scope.openDateTo = openDateTo;
        $scope.min = {};
        $scope.max = {};
        $scope.ctx = {};
        $scope.validation = {};
        $scope.display = display;
        $scope.displayNotLoggedUsers = displayNotLoggedUsers;
        $scope.deleteRecords = deleteRecords;
        $scope.clear = clear;
        $scope.onChange = onChange;
        $scope.tabSelected = tabSelected;
        $scope.getMoreRows = getMoreRows;
        activate();

        /************************************************** IMPLEMENTATION *************************************************************************/

        function activate() {
            availablePagination = true;
            LoggedHistoryPageService.setOffset(OFFSET);
            PageService.setCurrentPageService(LoggedHistoryPageService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            $scope.originalUsers = LoggedHistoryPageService.getUsers();
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            resetData();
            $scope.loadingManager = LoggedHistoryPageService.getLoadingManager();
            selectedUsersIds.push("Last");
            display(false);
            rows = LoggedHistoryPageService.getRows();
            $scope.ctx = {
                flex: null,
                data: rows
            };
            $scope.validation = {
                users: "",
            };
            clearValidation();
            $scope.$parent.onFilterWordChange("");
        }

        function stopPropagationFrom(event) {
            event.preventDefault();
            event.stopPropagation();
        }

        function stopPropagationTo(event) {
            event.preventDefault();
            event.stopPropagation();
        }

        /**
         * Opens date picker modal
         */
        function openDateFrom($event) {
            $scope.mindate = null;
            if ($scope.max.length != undefined) {
                $scope.maxdate = getYesterdayDate($scope.max);
            } else {
                $scope.maxdate = getTodayDate();
            }
            stopPropagationFrom($event);
            $scope.openedDateFrom = true;
        }

        function openDateTo($event) {
            var today = new Date();
            if ($scope.min.length != undefined) {
                $scope.mindate = getTomorrowDate($scope.min);
            }
            $scope.maxdate = getTomorrowDate(today);
            stopPropagationTo($event);
            $scope.openedDateTo = true;
        }

        function getYesterdayDate(data) {
            var todayDate = data;
            var dateInMilis = Date.parse(todayDate);
            var data = new Date(dateInMilis);
            data.setDate(data.getDate() - 1);
            var day = data.getDate();
            var month = data.getMonth() + 1;
            var year = data.getFullYear();
            if (day < 10) {
                day = '0' + day
            }
            if (month < 10) {
                month = '0' + month
            }
            var data = year + "-" + month + "-" + day;
            return data;
        }

        function getTomorrowDate(today) {

            if (today.length != undefined) {
                var todayDate = today;
                var dateInMilis = Date.parse(todayDate);
                var dateTime = new Date(dateInMilis);
                dateTime.setDate(dateTime.getDate() + 1);
                var day = dateTime.getDate();
                var month = dateTime.getMonth() + 1;
                var year = dateTime.getFullYear();
                if (day < 10) {
                    day = '0' + day;
                }
                if (month < 10) {
                    month = '0' + month;
                }
                var dateNew = year + "-" + month + "-" + day;

            } else {
                var tomorrow = new Date();
                var newDate = new Date();
                tomorrow.setDate(newDate.getDate() + 1);
                var day = tomorrow.getDate();
                var month = tomorrow.getMonth() + 1;
                var year = tomorrow.getFullYear();
                if (day < 10) {
                    day = '0' + day
                }
                if (month < 10) {
                    month = '0' + month
                }
                var dateNew = year + "-" + month + "-" + day;
            }
            return dateNew;
        }

        function getTodayDate() {
            var todayDate = new Date();
            var todayDateInStringFormat = getStringFormatOfDate(todayDate);
            var year = todayDateInStringFormat.slice(6, 10);
            var month = todayDateInStringFormat.slice(3, 5);
            var day = todayDateInStringFormat.slice(0, 2);
            var todayDateInRevertFormat = year + "-" + month + "-" + day;
            return todayDateInRevertFormat;
        }

        function getMoreRows(userId, getMore) {
            if (!getMore) {
                var currentTime = new Date();
                var dataFormat = currentTime.getFullYear() + "-" + (currentTime.getMonth() + 1) + "-" + currentTime.getDate() + " " + currentTime.getHours() + ":" + currentTime.getMinutes() + ":" + currentTime.getSeconds(); // yyyy-mm-dd hh24:mm:ss
                LoggedHistoryPageService.displayMoreRows(userId, dataFormat, false);
            } else {
                var date = $scope.ctx.data.sourceCollection[$scope.ctx.data.sourceCollection.length - 1].date;
                var hour = $scope.ctx.data.sourceCollection[$scope.ctx.data.sourceCollection.length - 1].hour;
                LoggedHistoryPageService.displayMoreRows(userId, date + " " + hour, true);
            }
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        };

        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        };

        /**
         * Reset data in angular service to initial values
         */
        function resetData() {
            LoggedHistoryPageService.resetRows();
            LoggedHistoryPageService.resetLoadingManager();
        }

        function tabSelected() {
                availablePagination = !availablePagination;
                resetData();
                rows.refresh();

            }
            /**
             * Handles any changes in selected user and manage dimensions (cost centers, expense codes) for selected users.
             */
        function onChange() {
            $scope.loadingManager.isRowsLoaded = false;
            //$scope.$apply();
            manageSelectedIds();
            LoggedHistoryPageService.setDataToUpdate();
        };

        /**
         * Manage list of user visIds which will be shown in multiple selector
         */
        function manageUsers() {
            angular.forEach($scope.originalUsers.sourceCollection, function(user) {
                $scope.users.push(user.userName);
            });
        }

        /**
         * Manage list of user visIds which will be shown in multiple selector
         */
        function manageUsers() {
            if ($scope.users[0] !== "All") {
                $scope.users.push("All");
            }
            angular.forEach($scope.originalUsers.sourceCollection, function(user) {
                var a = $scope.users.indexOf(user.userName);
                if (a === -1) {
                    $scope.users.push(user.userName);
                }
            });
            $scope.model.selectedUser = $scope.users[0];
        }

        /**
         * Manage selected model ids and users ids. Selected users are necessary to retrieve data rows for selected dimensions.
         * Selected model ids are necessary to retrieves dimensions (cost centers, expense codes) during uploading data rows from file.
         */
        function manageSelectedIds() {
            selectedUsersIds.length = 0;
            if ($scope.model.selectedUser === "All") {
                selectedUsersIds.push("All");
            } else {
                var user = CoreCommonsService.findElementByKey($scope.originalUsers.sourceCollection, $scope.model.selectedUser, 'userName');
                selectedUsersIds.push(user.userName);
            }
        }

        /**
         * Clear all validation for any filters
         */
        function clearValidation() {
            angular.forEach($scope.validation, function(message, field) {
                $scope.validation[field] = "";
            });
        }

        /**
         * Check if all filters (which are used to display data rows) are selected in proper way.
         */
        function isValidToDisplay(data) {
            var isValid = true;
            if (data.loggedHistory.length === 0) {
                isValid = false;
                $scope.validation.users = "You haven't selected any Users";
            }
            return isValid;
        }

        /**
         * Displays data rows for filtered data (all selected data in our filters)
         */
        function display(getMore) {
            data = {
                loggedHistory: selectedUsersIds,
            };
            if (isValidToDisplay(data)) {
                $scope.loadingManager.isRowsLoading = true;
                $scope.loadingManager.isRowsLoaded = false;
                //LoggedHistoryPageService.display(data);
                //LoggedHistoryPageService.displayMoreRows(selectedUsersIds[0], );
                getMoreRows(selectedUsersIds[0], getMore);
            }
        };

        function displayNotLoggedUsers() {
            var periodfrom = getStringFormatOfDate($scope.model.dateFrom);
            var periodto = getStringFormatOfDate($scope.model.dateTo);
            data = {
                periodfrom: periodfrom,
                periodto: periodto
            };
            $scope.loadingManager.isRowsLoading = true;
            $scope.loadingManager.isRowsLoaded = false;
            LoggedHistoryPageService.displayNotLoggedUsers(data);
        };

        function getStringFormatOfDate(calendarDate) {
            var dateTime;
            var year;
            var month;
            var day;
            if (typeof calendarDate.getMonth === 'function') {
                var today = new Date();
                day = today.getDate();
                month = today.getMonth() + 1;
                year = today.getFullYear();
                if (day < 10) {
                    day = '0' + day
                }
                if (month < 10) {
                    month = '0' + month
                }
            } else {
                year = calendarDate.slice(0, 4);
                month = calendarDate.slice(5, 7);
                day = calendarDate.slice(8, 10);
            }
            dateTime = day + "-" + month + "-" + year;
            return dateTime;
        };

        function clear() {
            $scope.model.selectedUser = $scope.users[0];
            selectedUsersIds[0] = $scope.users[0];
            display(false);
        };

        function deleteRecords() {
            swal({
                title: "Are you sure",
                text: "you want to delete those informations from login history?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                var year = $scope.model.periodFrom.getFullYear();
                var month = $scope.model.periodFrom.getMonth() + 1;
                if (month < 10) {
                    var date = "01-0" + month + "-" + year;
                } else {
                    var date = "01-" + month + "-" + year;
                }
                LoggedHistoryPageService.deleteRecords(date);
            });
        };

        $scope.$on("refreshLoginHistory", function() {
            display(false);
        });

        /**
         * Handles event when selected row in flex grid is changed
         */
        function onSelectionChanged(event) {
            var flex = $scope.ctx.flex;
            var selectedRow = flex.collectionView ? flex.collectionView.currentItem : null;
            originalRow = angular.copy(selectedRow);
        }


        function onScrollPositionChanged() {
                var myDiv = $('#gsFlexGrid').find('div[wj-part="root"]');
                if (myDiv.prop('offsetHeight') + myDiv.scrollTop() >= myDiv.prop('scrollHeight')) {
                    if (selectedUsersIds[0] !== "All" && selectedUsersIds[0] !== "Last" && availablePagination === true) {
                        display(true);
                    }
                }
            }
            /******************************************************** CUSTOM CELLS ********************************************************/
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {
                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'index':
                        html = "" + (r + 1);
                        break;
                }
                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }

        /******************************************************** WATCHERS ********************************************************/
        $scope.$watch('ctx.flex', function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                flex.selectionMode = "Row";
                flex.headersVisibility = "Column";
                flex.isReadOnly = true;
                flex.onSelectionChanged = onSelectionChanged;
                flex.onScrollPositionChanged = onScrollPositionChanged;
                flex.itemFormatter = itemFormatter;
                var additionalElementsToCalcResize = [angular.element(".data-editor-filter"), angular.element(".data-editor-buttons")];
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie, additionalElementsToCalcResize);
            }
        });

        $scope.$watch("originalUsers.sourceCollection", function() {
            if ($scope.originalUsers.sourceCollection.length > 0) {
                manageUsers();
            }
        });

        $scope.$watch('model.selectedUser', function() {
            $scope.validation.users = "";
        }, true);

        $scope.$watch('model.dateFrom', function() {
            if ($scope.model.dateFrom != null && angular.isDefined($scope.model.dateFrom)) {
                if (angular.isUndefined(oldDate)) {
                    oldDate = $scope.model.dateFrom;
                }
                if (oldDate !== $scope.model.dateFrom) {
                    $scope.isDateChanged = true;

                } else {
                    $scope.isDateChanged = false;
                }
                $scope.min = $scope.model.dateFrom;
            }
        });

        $scope.$watch('model.dateTo', function() {
            if ($scope.model.dateTo != null && angular.isDefined($scope.model.dateTo)) {
                if (angular.isUndefined(oldDate)) {
                    oldDate = $scope.model.dateTo;
                }
                if (oldDate !== $scope.model.dateTo) {
                    $scope.isDateChanged = true;

                } else {
                    $scope.isDateChanged = false;
                }
                $scope.max = $scope.model.dateTo;
            }
        });
    }
})();
