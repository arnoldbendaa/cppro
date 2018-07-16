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
        .controller('TaskViewerPageController', TaskViewerPageController);

    /* @ngInject */
    function TaskViewerPageController($rootScope, $scope, $compile, $timeout, $modal, PageService, FilterService, TaskViewerPageService, CoreCommonsService, ContextMenuService) {

        var page = 0;
        var offset = TaskViewerPageService.getOffset();
        var isDataToUpdate = true;
        var taskViewer = new wijmo.collections.CollectionView();
        var numberAllDownloadedTasks;
        $scope.isTasksLoaded = false;
        $scope.selectedTaskId = -1;
        $scope.selectedUserName = "";
        $scope.ctx = {
                flex: null,
                filter: '',
                data: taskViewer
            };
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_taskViewer";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteTask = deleteTask;
        activate();

        function activate() {
            PageService.setCurrentPageService(TaskViewerPageService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.$parent.onFilterWordChange("");
            return getTaskViewer();
        }

        function getTaskViewer() {
            if (isDataToUpdate === false) {
                return;
            }
            $scope.isTasksLoaded = false;
            
            return TaskViewerPageService.getTaskViewer(page)
                .then(function(data) {
                    $scope.isTasksLoaded = true;
                    if(page == 0) {
                        taskViewer.sourceCollection = data;
                        taskViewer.refresh();
                    } else {
                        taskViewer.sourceCollection = taskViewer.sourceCollection.concat(data);
                    }
                    numberAllDownloadedTasks = taskViewer.sourceCollection.length;
                    if (data.length === 0) {
                        isDataToUpdate = false;
                    }
                    page++;
                });
        }

        function onScrollPositionChanged() {
            var myDiv = $('#gsFlexGrid').find('div[wj-part="root"]');
            if (myDiv.prop('offsetHeight') + myDiv.scrollTop() >= myDiv.prop('scrollHeight')) {
                return getTaskViewer();
            }
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        }

        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        }

        /**
         * Set selected table row as current.
         * If previous selected row id is different from current, change selectedTaskId.
         * Otherwise reset selectedTaskId (cause Task was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedTaskId = current.taskId;
                $scope.selectedUserName = current.userName;
            } else {
                $scope.selectedTaskId = -1;
                $scope.selectedUserName = "";
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            openModal($scope.selectedTaskId, $scope.selectedUserName);
        }

        function refresh() {
            TaskViewerPageService.refreshTasks(numberAllDownloadedTasks)
                .then(function(data) {
                    taskViewer.sourceCollection = data;
                    taskViewer.refresh();
                });
        }

        function deleteTask() {
            if ($scope.selectedTaskId == -1) {
                return;
            }
            openDeletingConfirmBox();
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = TaskViewerPageService.getActions();

            TaskViewerPageService.isOpenDisabled = $scope.selectedTaskId === -1;

            if ($scope.selectedTaskId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableAction(actions, "deleteTask");
                PageService.disableAction(actions, "restart");
                PageService.disableAction(actions, "mark");
                PageService.disableAction(actions, "unsafeDelete");
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for insert or update). Insert when taskId equals -1, update otherwise.
         * @param  {Integer} taskId
         */
        var openModal = function(taskId, userName) {
            var modalInstance = $modal.open({
                template: '<task-viewer-details id="taskId" user="userName"></task-viewer-details>',
                windowClass: 'task-viewer-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.taskId = taskId;
                        $scope.userName = userName;
                        $scope.$on('TaskViewerDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        var openDeletingConfirmBox = function() {
            swal({
                title: "Are you sure",
                text: "you want to delete Task \"" + $scope.selectedTaskId + "\"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    TaskViewerPageService.deleteTask($scope.selectedTaskId, numberAllDownloadedTasks)
                        .then(function(data) {
                            refresh();
                        });
                }
            });
        };

        /**
         * Display warning message.
         */
        var openMarkingConfirmBox1 = function() {
            if ($scope.selectedTaskId == -1) {
                return;
            }
            var confirmText = "you want to mark (TaskID=" + $scope.selectedTaskId + ") as failed? ONLY use this action after advice from your support representative!!!!!! \n " +
                " Data could be lost permanently if you are not certain what you are doing."
            swal({
                title: "Are you SURE",
                text: confirmText,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    $timeout(function() {
                        openMarkingConfirmBox2($scope.selectedTaskId);
                    }, 150);
                }
            });
        };

        /**
         * Display warning message.
         */
        var openMarkingConfirmBox2 = function() {
            if ($scope.selectedTaskId == -1) {
                return;
            }
            var confirmText = "you want to mark (TaskID=" + $scope.selectedTaskId + ") as failed? ONLY USE THIS ACTION AFTER ADVICE FROM YOUR SUPPORT REPRESENTATIVE!!!!!! \n " +
                " DATA COULD BE LOST PERMANENTLY IF YOU ARE NOT CERTAIN WHAT YOU ARE DOING."
            swal({
                title: "Are you REALLY sure",
                text: confirmText,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    TaskViewerPageService.markTask($scope.selectedTaskId, numberAllDownloadedTasks)
                        .then(function(data) {
                            refresh();
                        });
                }
            });
        };

        /**
         * Display warning message.
         */
        var openDeletingConfirmBox1 = function() {
            if ($scope.selectedTaskId == -1) {
                return;
            }
            var confirmText = "you want to delete (TaskId=" + $scope.selectedTaskId + ")? ONLY use this action after advice from your support representative!!!!!! \n " +
                " Data could be lost permanently if you are not certain what you are doing."
            swal({
                title: "Are you SURE",
                text: confirmText,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    $timeout(function() {
                        openDeletingConfirmBox2($scope.selectedTaskId);
                    }, 150);
                }
            });
        };

        /**
         * Display warning message.
         */
        var openDeletingConfirmBox2 = function() {
            if ($scope.selectedTaskId == -1) {
                return;
            }
            var confirmText = "you want to delete (TaskID=" + $scope.selectedTaskId + ")? ONLY USE THIS ACTION AFTER ADVICE FROM YOUR SUPPORT REPRESENTATIVE!!!!!! \n " +
                " DATA COULD BE LOST PERMANENTLY IF YOU ARE NOT CERTAIN WHAT YOU ARE DOING."
            swal({
                title: "Are you REALLY sure",
                text: confirmText,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    TaskViewerPageService.unsafeDeleteTask($scope.selectedTaskId, numberAllDownloadedTasks)
                        .then(function(data) {
                            refresh();
                        });
                }
            });
        };

        /******************************************************** FILTERS ********************************************************/
        var filters = ['byMainWordFilter'];
        var filterFunction = FilterService.buildFilterFunction(filters);

        $rootScope.$watch(function() {
            return $rootScope.filter.byWord
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });

        /******************************************************** CUSTOM CELLS ********************************************************/
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {

                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'index':
                        html = "" + (r + 1);
                        break;
                    case 'buttons':
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open();">Open</button> ' +
                            '<button type="button" class="btn btn-danger btn-xs" ng-click="deleteTask();">Delete</button>';
                        break;
                }
                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }

        /******************************************************** WATCHERS ********************************************************/
        $scope.$watch("ctx.flex", function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                flex.isReadOnly = true;
                flex.selectionMode = "Row";
                flex.headersVisibility = "Column";
                flex.onScrollPositionChanged = onScrollPositionChanged;
                flex.itemFormatter = itemFormatter;
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);

                ContextMenuService.initialize($('.grid'));
            }
        });

        /**
         * Call "open" for open details window after click on button on the submenu.
         */
        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        /**
         * Call "delete" for delete selected task after click on button on the submenu.
         */
        $scope.$on("SubMenuController:deleteTask", function() {
            $scope.deleteTask();
        });

        /**
         * Call "refresh" for refresh list after click on button on the submenu.
         */
        $scope.$on("SubMenuController:refresh", function() {
            refresh();
        });

        /**
         * Call "restart" for restart task after click on button on the submenu.
         */
        $scope.$on("SubMenuController:restart", function() {
            TaskViewerPageService.restartTask($scope.selectedTaskId, numberAllDownloadedTasks)
                .then(function(data) {
                    refresh();
                });
        });

        /**
         * Call "wake" for wake up task despatcher after click on button on the submenu.
         */
        $scope.$on("SubMenuController:wake", function() {
            TaskViewerPageService.wakeUpTaskDespatcher(numberAllDownloadedTasks)
                .then(function(data) {
                    refresh();
                });
        });

        /**
         * Call indirectly "mark" for mark task as failed click on button on the submenu. Operation is unsafe and call warning, warning calls second warning
         * and this warning calls "mark"
         */
        $scope.$on("SubMenuController:mark", function() {
            openMarkingConfirmBox1($scope.selectedTaskId);
        });

        /**
         * Call indirectly "unsafeDelete" for mark task as failed click on button on the submenu. Operation is unsafe and call warning, warning calls second warning
         * and this warning calls "unsafeDelete"
         */
        $scope.$on("SubMenuController:unsafeDelete", function() {
            openDeletingConfirmBox1($scope.selectedTaskId);
        });

    }
})();