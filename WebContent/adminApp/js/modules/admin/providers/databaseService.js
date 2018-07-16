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
        .module('adminApp.admin')
        .service('DatabaseService', DatabaseService);

    function DatabaseService($http, Flash, CoreCommonsService) {

        var self = this;
        var url = $BASE_PATH + 'adminPanel/tidyTasks';
        var isTidyTasksLoaded = false;
        var isTidyTasksLoading = false;
        var tidyTasks = new wijmo.collections.CollectionView();
        var actions = [{
            name: "Submit",
            action: "submit",
            disabled: true
        }];
        self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.getTidyTasks = getTidyTasks;
        self.getTidyTask = getTidyTask;
        self.submit = submit;
        self.getActions = getActions;

        /**
         * Get list of tidy tasks
         */
        function getTidyTasks(hardReload) {
            if ((!isTidyTasksLoading && !isTidyTasksLoaded) || hardReload) {
                getTidyTasksFromDatabase();
            }
            return tidyTasks;
        }

        /**
         * Requests database for list of available tidy tasks
         */
        var getTidyTasksFromDatabase = function() {
            isTidyTasksLoaded = false;
            isTidyTasksLoading = true;

            $http.get(url).success(function(data) {
                isTidyTasksLoaded = true;
                if (data && data.length > 0) {
                    tidyTasks.sourceCollection = data;
                }
            });
        };

        /**
         * Gets details for selected tidy task. Details means commands for tidy tasks.
         */
        var getTaskDetailsFromDatabase = function(tidyTaskId) {
            $http.get(url + "/" + tidyTaskId).success(function(response) {
                var task = CoreCommonsService.findElementByKey(tidyTasks.items, tidyTaskId, 'tidyTaskId');
                task.commands = response.commands;
            });
        };

        function getTidyTask(tidyTaskId) {
            var task = CoreCommonsService.findElementByKey(tidyTasks.items, tidyTaskId, 'tidyTaskId');
            if (task === null) return;

            if (task.commands === null) {
                getTaskDetailsFromDatabase(tidyTaskId);
            }
            return task;
        }

        /**
         * Submit selected tidy task in database.
         */
        function submit(tidyTaskId) {
            var task = CoreCommonsService.findElementByKey(tidyTasks.items, tidyTaskId, 'tidyTaskId');
            if (task === null) return;

            $http.post(url + "/submit", task).success(function(response) {
                var data = {
                    taskNumber: response,
                    objectToSubmit: task
                };
                var messageToReturn = 'Task ("' + data.objectToSubmit.tidyTaskVisId + '") was submitted with ID = ' + data.taskNumber;
                Flash.create('success', messageToReturn);
            });
        }

        /**
         * Gets all actions available for database tab
         */
        function getActions() {
            return actions;
        }
    }
})();