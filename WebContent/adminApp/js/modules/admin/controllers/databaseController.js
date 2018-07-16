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
        .controller('DatabaseController', DatabaseController);

    /* @ngInject */
    function DatabaseController($rootScope, $scope, $compile, $modal, PageService, FilterService, DatabaseService, CoreCommonsService, ContextMenuService) {
        var tidyTasks = DatabaseService.getTidyTasks();
        $scope.selectedTidyTaskId = -1;
        $scope.ctx = {};
        $scope.cookieName = "adminPanel_database";
        $scope.currentCookie = {};
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.submit = submit;
        activate();

        function activate() {
            PageService.setCurrentPageService(DatabaseService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            
            $scope.ctx = {
                flex: null,
                filter: '',
                data: tidyTasks
            };
            ContextMenuService.initialize($('.grid'));
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.$parent.onFilterWordChange("");
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        }

        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        }

        /**
         * Handles when row with tidy task is selected. Updates selectedTaskId. If user clicks in the same element, controller deselects row.
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current !== null) {
                $scope.selectedTidyTaskId = current.tidyTaskId;
            }
            manageActions();
        }

        /**
         * Function is invoked after click in open button (on the list of tidy task or stored in submenu)
         */
        function open() {
            openModal($scope.selectedTidyTaskId);
        }

        /**
         * Function is invoked after click in submit button (on the list of tidy task or stored in submenu)
         */
        function submit(taskId) {
            if ($scope.selectedTidyTaskId == -1) {
                return;
            }
            DatabaseService.submit($scope.selectedTidyTaskId);
        }

        /**
         * Method disables or enables buttons in submenu. When none of tidy task is selected, buttons are disabled
         */
        var manageActions = function() {
            var actions = DatabaseService.getActions();

            DatabaseService.isOpenDisabled = $scope.selectedTidyTaskId === -1;

            if ($scope.selectedTidyTaskId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Opens modal with details of tidy task (with tidyTaskId)
         */
        var openModal = function(taskId) {
            var modalInstance = $modal.open({
                template: '<task-details id="taskId" close="close()"></task-details>',
                windowClass: 'task-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.taskId = taskId;

                        $scope.close = function() {
                            $modalInstance.close();
                        };
                    }
                ]
            });
        };

        /**
         * ****************************************************** FILTERS *******************************************************
         */
        var filters = ['byMainWordFilter'];
        var filterFunction = FilterService.buildFilterFunction(filters);

        $rootScope.$watch(function() {
            return $rootScope.filter.byWord;
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });

        /**
         * ****************************************************** CUSTOM CELLS *******************************************************
         */
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {

                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'index':
                        html = "" + (r + 1);
                        break;
                    case 'buttons':
                        html = '<button type="button" class="btn btn-primary btn-xs" ng-click="submit();">Submit</button> ' +
                            '<button type="button" class="btn btn-warning btn-xs" ng-click="open(); ">Open</button>';
                        break;
                }
                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }

        /**
         * **************************************** WATCHERS *************************************************************
         */
        $scope.$watch('ctx.flex', function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                flex.isReadOnly = true;
                flex.selectionMode = "Row";
                flex.headersVisibility = "Column";
                flex.itemFormatter = itemFormatter;
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
            }
        });

        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on("SubMenuController:create", function() {
            $scope.selectedTidyTaskId = -1;
            $scope.open();
        });

        $scope.$on("SubMenuController:submit", function() {
            $scope.submit($scope.selectedTidyTaskId);
        });

        $scope.$on("SubMenuController:refresh", function() {
            DatabaseService.getTidyTasks(true);
        });
    }
})();