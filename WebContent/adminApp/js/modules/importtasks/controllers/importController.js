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
        .controller('ImportController', ImportController);

    /* @ngInject */
    function ImportController($rootScope, $scope, $compile, $modal, Flash, PageService, FilterService, ImportService, CoreCommonsService, ContextMenuService, TaskStatusService) {

        var importTasks = {};
        $scope.selectedImportTaskId = -1;
        $scope.selectedImportTaskVisId = "";
        $scope.selectedExternalSystemVisId = "";
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_importTasks";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.submit = submit;
        activate();
        
        function activate(){
            PageService.setCurrentPageService(ImportService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            importTasks = ImportService.getImport();
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: importTasks
                };
            ContextMenuService.initialize($('.grid'));
            // try to resize and sort colums based on the cookie
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
         * Set selected table row as current.
         * If previous selected row id is different from current, change selectedimportTaskId.
         * Otherwise reset selectedImportTaskId (cause Import was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedImportTaskId = current.importId;
                $scope.selectedImportTaskVisId = current.importVisId;
                $scope.selectedExternalSystemVisId = current.externalSystemVisId;
            } else {
                $scope.selectedImportTaskId = -1;
                $scope.selectedImportTaskVisId = "";
                $scope.selectedExternalSystemVisId = "";
            }
            manageActions();
        }

        function submit() {
            ImportService.submitImport($scope.selectedImportTaskId, $scope.selectedImportTaskVisId, $scope.selectedExternalSystemVisId);
        };

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = ImportService.getActions();

            // ImportService.isOpenDisabled = $scope.selectedImportTaskId === -1;

            if ($scope.selectedImportTaskId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
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
                        html = '<button type="button" class="btn btn-primary btn-xs" ng-click="submit();">Submit</button> '
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
                flex.headersVisibility = "Column"
                flex.itemFormatter = itemFormatter;
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
            }
        });
        function infoAlert(message) {
            Flash.create('success', message, 'custom-class');
        }
        
        function failAlert(message) {
            Flash.create('danger', message, 'custom-class');
        }
        
        $scope.$on('ImportService:runCheckTaskStatus', function(event, args) {
            TaskStatusService.checkTaskStatus(args);
        });
        
        $scope.$on('TaskStatusService:statusIsChanged', function(event, args) {
            if (args=="Failed"){
                failAlert("Task status is changed to - " + args);
            } else{
                infoAlert("Task status is changed to - " + args);
            }
            if (args==="Complete" || args==="Failed" || args==="Complete (exceptions)"){
                $scope.checked = false; //enable import button
            }
        });
        
        /**
         * Call "submit" for submit import after click on button on the submenu.
         */
        $scope.$on("SubMenuController:submit", function() {
            $scope.submit()
        });

        $scope.$on("SubMenuController:refresh", function() {
            ImportService.getImport(true);
        });
    }
})();