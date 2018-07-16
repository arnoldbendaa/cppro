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
        .service('LoggedHistoryPageService', LoggedHistoryPageService);

    /* @ngInject */
    function LoggedHistoryPageService($http, Flash, $rootScope) {

        var self = this;
        var url = $BASE_PATH + 'adminPanel/loggedhistory/';
        var actions = [];
        var originalRows = [];
        var workingRows = [];
        var rows = new wijmo.collections.CollectionView();
        var users = new wijmo.collections.CollectionView();
        var isUsersLoading;
        var isUsersLoaded;
        var isDataToUpdate = true;
        var OFFSET;
        rows.sourceCollection = workingRows;
        self.resetRows = resetRows;
        self.resetLoadingManager = resetLoadingManager;
        self.getRows = getRows;
        self.getLoadingManager = getLoadingManager;
        self.getActions = getActions;
        self.deleteRecords = deleteRecords;
        self.getUsers = getUsers;
        self.displayNotLoggedUsers = displayNotLoggedUsers;
        self.getPageSize = getPageSize;
        self.displayMoreRows = displayMoreRows;
        self.setDataToUpdate = setDataToUpdate;
        self.setOffset = setOffset;

        var loadingManager = {
            isRowsLoading: false,
            isRowsLoaded: false,
            isRowsSaving: false
        };

        /**
         * Requests database for list of Finance Cubes.
         */
        var getUsersFromDatabase = function() {
            isUsersLoaded = false;
            isUsersLoading = true;
            $http.get(url).success(function(data) {
                isUsersLoaded = false;
                if (data && data.length >= 0) {
                    users.sourceCollection = data;
                };
            });
        };

        /**
         * Get list of Finance Cubes.
         */
        function getUsers(hardReload) {
            if ((!isUsersLoaded && !isUsersLoading) || hardReload) {
                getUsersFromDatabase();
            }
            return users;
        }

        /**
         * Retrieve data of rows for filtered data (selected users)
         */

        function displayMoreRows(id, lastDate, getMore) {
            if (isDataToUpdate === false) {
                loadingManager.isRowsLoading = false;
                loadingManager.isRowsLoaded = true;
                return;

            }
            $http.get(url + "moreRows/" + id + "/" + lastDate + "/" + OFFSET).success(function(response) {
                if (getMore) {
                    if (response.length === 0) {
                        isDataToUpdate = false;
                    }
                    var tmpWorkingRows = workingRows.concat(response);
                    angular.copy(tmpWorkingRows, workingRows);
                } else {
                    originalRows = angular.copy(response);
                    angular.copy(originalRows, workingRows);
                }
                rows.refresh();
                loadingManager.isRowsLoading = false;
                loadingManager.isRowsLoaded = true;
            });
        };

        function displayNotLoggedUsers(filteredData) {
            $http.get(url + "display/" + filteredData.periodfrom + "/" + filteredData.periodto).success(function(response) {
                var UserNames = [];
                var UnicalNotLoggedUsers = [];
                originalRows = angular.copy(response);
                for (var i = 0; i < originalRows.length; i++) {
                    var index = UserNames.indexOf(originalRows[i].userName);
                    if (index === -1) {
                        UserNames.push(originalRows[i].userName);
                        UnicalNotLoggedUsers.push(originalRows[i]);
                    }
                }

                angular.copy(UnicalNotLoggedUsers, workingRows);
                rows.refresh();
                loadingManager.isRowsLoading = false;
                loadingManager.isRowsLoaded = true;
            });
        };

        function deleteRecords(periodFrom) {
            $http.delete(url + "delete/" + periodFrom).success(function(response) {
                $rootScope.$broadcast('refreshLoginHistory', {});
            });
        };

        function setDataToUpdate() {
            isDataToUpdate = true;
        }

        /**
         * Reset collection of working and original rows. Reference is not deleted.
         */
        function resetRows() {
            workingRows.length = 0;
            originalRows.length = 0;
        };

        /**
         * Reset loading manager to initial state
         */
        function resetLoadingManager() {
            loadingManager.isRowsLoading = false;
            loadingManager.isRowsLoaded = false;
            loadingManager.isRowsSaving = false;
        };

        /**
         * Retrieves data of rows
         */
        function getRows() {
            return rows;
        };

		/**
         * Sets offset to scroll data
         */
        function setOffset(offset){
            OFFSET = offset;
        };

        /**
         * Retrieves loading manager in actual state
         */
        function getLoadingManager() {
            return loadingManager;
        };

        /**
         * Retrieves actions available for data editor
         */
        function getActions() {
            return actions;
        };

        function getPageSize() {
            return pageSize;
        }
    }
})();