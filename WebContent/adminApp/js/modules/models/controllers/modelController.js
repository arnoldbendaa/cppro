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
        .module('adminApp.model')
        .controller('ModelController', ModelController);

    /* @ngInject */
    function ModelController($rootScope, $scope, $compile, $timeout, $modal, PageService, FilterService, ModelService, CoreCommonsService, ContextMenuService) {

        var models = {};
        $scope.selectedModelId = -1;
        $scope.selectedModelVisId = "";
        $scope.isSelectedModelGlobal = false;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_model";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.openImport = openImport;
        $scope.deleteModel = deleteModel;
        activate();

        function activate() {
            PageService.setCurrentPageService(ModelService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            models = ModelService.getModels();
            $scope.ctx = {
                flex: null,
                filter: '',
                data: models
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
         * If previous selected row id is different from current, change selectedFinanceCubeId.
         * Otherwise reset selectedFinanceCubeId ('cause FinanceCube was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedModelId = current.modelId;
                $scope.selectedModelVisId = current.modelVisId;
                $scope.isSelectedModelGlobal = current.global;
            } else {
                $scope.selectedModelId = -1;
                $scope.selectedModelVisId = "";
                $scope.isSelectedModelGlobal = false;
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            openModal($scope.selectedModelId);
        }

        function openImport() {
            openImportModal($scope.selectedModelId);
        }

        function deleteModel() {
            if ($scope.selectedModelId == -1) {
                return;
            }
            openDeletingConfirmBox();
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = ModelService.getActions();

            ModelService.isOpenDisabled = $scope.selectedModelId === -1;

            if ($scope.selectedModelId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }

            // toggle active of Import button in top menu
            // evalAsync is necessary to prevent angular "Action Already In Progress" error
            $scope.$evalAsync(function() {
                if ($scope.isSelectedModelGlobal) {
                    PageService.enableAction(actions, "import", $scope);
                } else {
                    PageService.disableAction(actions, "import", $scope);
                }
                ContextMenuService.updateActions();
            });

            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for insert or update). Insert when modelId equals -1, update otherwise.
         * @param  {Integer} modelId
         */
        var openModal = function(modelId) {
            var modalInstance = $modal.open({
                template: '<model-details model="modelId"></model-details>',
                windowClass: 'model-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modelId = modelId;
                        $scope.$on('ModelDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        var openImportModal = function(modelId) {
            var modalInstance = $modal.open({
                template: '<model-import-data model="modelId"></model-import-data>',
                windowClass: 'model-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modelId = modelId;
                        $scope.$on('Model:dataImportClose', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        /**
         * Show alert before delete Model and then call deleting method.
         */
        var openDeletingConfirmBox = function() {
            swal({
                title: "Are you sure",
                text: "you want to delete Model \"" + $scope.selectedModelVisId + "\"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    ModelService.deleteModel($scope.selectedModelId, $scope.selectedModelVisId);
                }
            });
        };

        /******************************************************** FILTERS ********************************************************/
        var filters = ['byMainWordFilter', 'byModelFilter'];
        var filterFunction = FilterService.buildFilterFunction(filters);

        $rootScope.$watch(function() {
            return $rootScope.filter.byWord
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });

        $rootScope.$watch(function() {
            return $rootScope.currentModel
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
                        var disabled = (panel.rows[r].dataItem['global']) ? null : "disabled";
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open()">Open</button>' +
                        //' <button type="button" class="btn btn-info btn-xs ' + disabled + '" ng-click="openImport()">Import</button>' +
                        ' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteModel()">Delete</button>';
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
                flex.headersVisibility = "Column"
                flex.itemFormatter = itemFormatter;
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
            }
        });
        /**
         * Call "open" for update after click on button on the submenu.
         */
        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on('SubMenuController:create', function() {
            openModal(-1);
        });

        $scope.$on('SubMenuController:import', function() {
            $scope.openImport();
        });

        $scope.$on('SubMenuController:deleteModel', function() {
            $scope.deleteModel();
        });

        $scope.$on("SubMenuController:refresh", function() {
            ModelService.getModels(true);
        });
    }
})();