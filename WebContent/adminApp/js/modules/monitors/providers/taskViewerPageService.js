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
        .module('adminApp.taskViewerPage')
        .service('TaskViewerPageService', TaskViewerPageService);

    /* @ngInject */
    function TaskViewerPageService($rootScope, Flash, $http) {

        var self = this;

        var url = $BASE_PATH + 'adminPanel/taskViewer';
        var taskViewer = new wijmo.collections.CollectionView();
        var actions = [{
            name: "Delete",
            action: "deleteTask",
            disabled: true
        }, {
            name: "Restart task",
            action: "restart",
            disabled: true
        }, {
            name: "Wake up task despatcher",
            action: "wake",
            disabled: true
        }, {
            name: "Mark task as failed (unsafe)",
            action: "mark",
            disabled: true
        }, {
            name: "Delete task (unsafe)",
            action: "unsafeDelete",
            disabled: true
        }];
        var OFFSET = 50;
        self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.getTaskViewer = getTaskViewer;
        self.refreshTasks = refreshTasks;
        self.getTaskDetailsFromDatabase = getTaskDetailsFromDatabase;
        self.deleteTask = deleteTask;
        self.restartTask = restartTask;
        self.wakeUpTaskDespatcher = wakeUpTaskDespatcher;
        self.markTask = markTask;
        self.unsafeDeleteTask = unsafeDeleteTask;
        self.getOffset = getOffset;
        self.getActions = getActions;
        
        /**
         * Load tasks from database
         */
        function getTaskViewer(page) {
            return $http.get(url + "/tasks/" + page + "/" + OFFSET)
                .then(getTaskViewerComplete)
                .catch(getTaskViewerFailed);
            
            function getTaskViewerComplete(response) {
                return response.data;
            }
            
            function getTaskViewerFailed(error) {
                console.log('Get mobile profiles failed: ' + error.message);
            }
        }
        
        /**
         * Reload tasks from database
         */
        function refreshTasks(numberAllDownloadedTasks) {
            return $http.get(url + "/tasks/" + 0 + "/" + numberAllDownloadedTasks)
                .then(refreshTasksComplete)
                .catch(refreshTasksFailed);
            
            function refreshTasksComplete(response) {
                return response.data;
            }
            
            function refreshTasksFailed(error) {
                console.log('Get mobile profiles failed: ' + error.message);
            }
        }
        
        /**
         * Delete selected profile
         */
        function deleteTask(taskId, numberAllDownloadedTasks) {
            return $http.delete(url + "/delete/" + taskId)
                .then(deleteTaskComplete)
                .catch(deleteTaskFailed);
            
            function deleteTaskComplete(data) {
                if (data.data.success == false) {
                    Flash.create('danger', data.data.message);
                } else {
                    Flash.create('success', 'Task = "' + taskId + '" deleted.');
                    return self.refreshTasks(numberAllDownloadedTasks);
                }
            }
            
            function deleteTaskFailed(error) {
                console.log('Delete task failed: ' + error.message);
            }
        }

        /**
         * Get details for selected Task.
         */
        function getTaskDetailsFromDatabase(taskId) {
            var task = {};
            $http.get(url + "/" + taskId).success(function(data) {
                angular.copy(data, task);
            });
            return task;
        }

        /**
         * Restart selected Task.
         */
        function restartTask(taskId, numberAllDownloadedTasks) {
            return $http.put(url + "/restart/" + taskId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    return self.refreshTasks(numberAllDownloadedTasks);
                }
            });
        }

        /**
         * Wake up despatcher.
         */
        function wakeUpTaskDespatcher(numberAllDownloadedTasks) {
            return $http.put(url + "/wake").success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                    return deferred.promise;
                } else {
                    return self.refreshTasks(numberAllDownloadedTasks);
                }
            });
        }

        /**
         * Mark selected task as failed (unsafe).
         */
        function markTask(taskId, numberAllDownloadedTasks) {
            return $http.put(url + "/mark/" + taskId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    return self.refreshTasks(numberAllDownloadedTasks);
                }
            });
        }

        /**
         * Delete selected Task (unsafe).
         */
        function unsafeDeleteTask(taskId, numberAllDownloadedTasks) {
            return $http.put(url + "/unsafeDelete/" + taskId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    return self.refreshTasks(numberAllDownloadedTasks);
                }
            });
        }
       
        /**
         * Sets offset to scroll data
         */
        function getOffset(){
            return OFFSET;
        }

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
    }
})();