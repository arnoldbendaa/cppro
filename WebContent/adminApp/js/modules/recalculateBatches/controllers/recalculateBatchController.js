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
        .module('adminApp.recalculateBatch')
        .controller('RecalculateBatchController', RecalculateBatchController);

    /* @ngInject */
    function RecalculateBatchController($rootScope, $scope, $compile, $modal, PageService, FilterService, RecalculateBatchService, CoreCommonsService, ContextMenuService) {

        var recalculateBatches = {};
        $scope.selectedRecalculateBatchId = -1;
        $scope.ctx = {};
        $scope.cookieName = "adminPanel_recalculateBatch";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.submit = submit;
        $scope.deleteRecalculateBatch = deleteRecalculateBatch;
        activate();
        
        function activate(){
            PageService.setCurrentPageService(RecalculateBatchService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            recalculateBatches = RecalculateBatchService.getRecalculateBatches();
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: recalculateBatches
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
         * Handles when row with recalculate batch is selected. Updates selectedRecalculateBatchId.
         * If user clicks in the same element, controller deselects row.
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current !== null) {
                $scope.selectedRecalculateBatchId = current.recalculateBatchId;
            } else {
                $scope.selectedRecalculateBatchId = -1;
            }
            manageActions();
        }

        /**
         * Function is invoked after click in open button (on the list of recalculate batches
         * or stored in submenu)
         */
        function open() {
            openModal($scope.selectedRecalculateBatchId);
        }

        function submit() {
            if ($scope.selectedRecalculateBatchId == -1) {
                return;
            }
            RecalculateBatchService.submit($scope.selectedRecalculateBatchId);
        }

        /**
         * Delete selected recalculate batch
         * Function is invoked after click in delete button (on the list of recalculate batches
         * or stored in submenu)
         */
        function deleteRecalculateBatch() {
            if ($scope.selectedRecalculateBatchId == -1) {
                return;
            }
            swal({
                title: "Are you sure",
                text: "to delete recalulcate batch (ID = \"" + $scope.selectedRecalculateBatchId + "\") ?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    RecalculateBatchService.deleteRecalculateBatch($scope.selectedRecalculateBatchId);
                }
            });
        }

        /**
         * Method disables or enables buttons in submenu. When none of recalculate batches is selected,
         * buttons are disabled
         */
        var manageActions = function() {
            var actions = RecalculateBatchService.getActions();

            RecalculateBatchService.isOpenDisabled = $scope.selectedRecalculateBatchId === -1;
            RecalculateBatchService.isPrintDisabled = $scope.selectedRecalculateBatchId === -1;

            if ($scope.selectedRecalculateBatchId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Opens modal with details of recalculate batch (with recalculateBatchId)
         */
        var openModal = function(recalculateBatchId) {
            var modalInstance = $modal.open({
                template: '<recalculate-batch-details id="recalculateBatchId" close="close()"></recalculate-batch-details>',
                windowClass: 'recalculate-batch-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.recalculateBatchId = recalculateBatchId;

                        $scope.close = function() {
                            $modalInstance.close();
                        };
                    }
                ]
            });
        };

        /******************************************************** FILTERS ********************************************************/
        var filters = ['byMainWordFilter', 'byModelFilter'];
        var filterFunction = FilterService.buildFilterFunction(filters);
        FilterService.doFilter($scope, filterFunction);

        $rootScope.$watch(function() {
            return $rootScope.filter.byWord;
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });

        $rootScope.$watch(function() {
            return $rootScope.currentModel;
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
                        html = '<button type="button" class="btn btn-primary btn-xs" ng-click="submit();">Submit</button>' +
                            ' <button type="button" class="btn btn-warning btn-xs" ng-click="open()">Open</button>' +
                            ' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteRecalculateBatch()">Delete</button>';
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
            openModal(-1);
        });

        $scope.$on("SubMenuController:deleteRecalculateBatch", function() {
            $scope.deleteRecalculateBatch();
        });

        $scope.$on("SubMenuController:submit", function() {
            $scope.submit();
        });

        $scope.$on("SubMenuController:refresh", function() {
            RecalculateBatchService.getRecalculateBatches(true);
        });
    }
})();