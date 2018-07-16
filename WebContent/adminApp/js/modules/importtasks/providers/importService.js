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
        .module('adminApp.import')
        .service('ImportService', ImportService);

    /* @ngInject */
    function ImportService($rootScope, Flash, $http) {

        var self = this;
        var isImportLoaded = false;
        var isImportLoading = false;
        var url = $BASE_PATH + 'adminPanel/import';
        var importTasks = new wijmo.collections.CollectionView();
        var actions = [{
            name: "Submit",
            action: "submit",
            disabled: true
        }];
        self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.getImport = getImport;
        self.submitImport = submitImport;
        self.getActions = getActions;
 
        /**
         * Get list of Import.
         */
        function getImport(hardReload) {
            if ((!isImportLoading && !isImportLoaded) || hardReload) {
                getImportFromDatabase();
            }
            return importTasks;
        }

        /**
         * Requests database for list of Import.
         */
        var getImportFromDatabase = function() {
            isImportLoaded = false;
            isImportLoading = true;

            $http.get(url + "/").success(function(data) {
                isImportLoaded = true;

                if (data && data.length >= 0) {
                    importTasks.sourceCollection = data;
                }
            });
        };

        /**
         * Submit selected Import.
         */
        function submitImport(importTaskId, importTaskVisId, externalSystemVisId) {
            var taskId = null;
            $http.put(url + "/submit/" + importTaskVisId + "/" + importTaskId + "/" + externalSystemVisId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message, {
                        timeout: 3000
                    });
                } else {
                    taskId = data.data.taskId;
                    var messageToReturn = 'Import (ID = ' + taskId + ') was submitted.';
                    Flash.create('success', messageToReturn);
                    $rootScope.$broadcast('ImportService:runCheckTaskStatus', taskId);
                }
            });
        }

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
    }
})();