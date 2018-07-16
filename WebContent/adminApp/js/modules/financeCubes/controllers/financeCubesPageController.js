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
        .module('adminApp.financeCubesPage')
        .controller('FinanceCubesPageController', FinanceCubesPageController);

    /* @ngInject */
    function FinanceCubesPageController($rootScope, $scope, $compile, $modal, PageService, FilterService, FinanceCubesPageService, CoreCommonsService, ContextMenuService) {
      
        var financeCubes;
        $scope.selectedFinanceCubeId = -1;
        $scope.selectedFinanceCubeVisId = "";
        $scope.selectedModelId = -1;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_financeCube";   
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteFinanceCube = deleteFinanceCube;
        activate();
       
        function activate(){
            PageService.setCurrentPageService(FinanceCubesPageService);
            financeCubes = FinanceCubesPageService.getFinanceCubes();
            $scope.globalSettings = CoreCommonsService.globalSettings;
            // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: financeCubes
                };
            ContextMenuService.initialize($('.grid'));
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
                $scope.selectedFinanceCubeId = current.financeCubeId;
                $scope.selectedFinanceCubeVisId = current.financeCubeVisId;
                $scope.selectedModelId = current.model.modelId;
            } else {
                $scope.selectedFinanceCubeId = -1;
                $scope.selectedFinanceCubeVisId = "";
                $scope.selectedModelId = -1;
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            openModal($scope.selectedModelId, $scope.selectedFinanceCubeId);
        }

        function deleteFinanceCube() {
            if ($scope.selectedFinanceCubeId == -1) {
                return;
            }
            swal({
                title: "Are you sure",
                text: "you want to delete Finance Cube \"" + $scope.selectedFinanceCubeVisId + "\"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    FinanceCubesPageService.deleteFinanceCube($scope.selectedModelId, $scope.selectedFinanceCubeId, $scope.selectedFinanceCubeVisId);
                }
            });
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = FinanceCubesPageService.getActions();

            FinanceCubesPageService.isOpenDisabled = $scope.selectedFinanceCubeId === -1;

            if ($scope.selectedFinanceCubeId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for insert or update). Insert when financeCubeId equals -1, update otherwise.
         * @param  {Integer} financeCubeId
         */
        var openModal = function(modelId, financeCubeId) {
            var modalInstance = $modal.open({
                template: '<finance-cube-details finance-cube="financeCubeId" model="modelId"></finance-cube-details>',
                windowClass: 'finance-cube-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.financeCubeId = financeCubeId;
                        $scope.modelId = modelId;
                        $scope.$on('FinanceCubeDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
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
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open()">Open</button>' +
                            ' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteFinanceCube()">Delete</button>';
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

        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on("SubMenuController:create", function() {
            openModal(-1, -1);
        });

        $scope.$on("SubMenuController:deleteFinanceCube", function() {
            $scope.deleteFinanceCube();
        });

        $scope.$on("SubMenuController:refresh", function() {
            FinanceCubesPageService.getFinanceCubes(true);
        });
    }
})();