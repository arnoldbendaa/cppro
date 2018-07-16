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
        .module('adminApp.financeCubeFormulaPage')
        .controller('FinanceCubeFormulaPageController', FinanceCubeFormulaPageController);

    /* @ngInject */
    function FinanceCubeFormulaPageController($rootScope, $scope, $compile, $modal, PageService, FilterService, FinanceCubeFormulaPageService, CoreCommonsService, ContextMenuService) {
  
        var financeCubeFormula;
        $scope.selectedFinanceCubeId = -1;
        $scope.selectedFinanceCubeVisId = "";
        $scope.selectedFinanceCubeFormulaId = -1;
        $scope.selectedModelId = -1;
        $scope.ctx = {};       
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_financeCubeFormula";
        // try to resize and sort colums based on the cookie
        $scope.currentCookie = {};
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteFinanceCubeFormula = deleteFinanceCubeFormula;
        activate();
      
        function activate(){
            PageService.setCurrentPageService(FinanceCubeFormulaPageService);
            financeCubeFormula = FinanceCubeFormulaPageService.getFinanceCubeFormula();
            $scope.globalSettings = CoreCommonsService.globalSettings;
         // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: financeCubeFormula
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

        ContextMenuService.initialize($('.grid'));

        /**
         * Set selected table row as current. If previous selected row id is different from current, change selectedFinanceCubeId. Otherwise reset selectedFinanceCubeId ('cause FinanceCube was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedFinanceCubeId = current.financeCube.financeCubeId;
                $scope.selectedFinanceCubeVisId = current.financeCube.financeCubeVisId;
                $scope.selectedFinanceCubeFormulaId = current.financeCubeFormulaId;
                $scope.selectedFinanceCubeFormulaVisId = current.financeCubeFormulaVisId;
                $scope.selectedModelId = current.model.modelId;
            } else {
                $scope.selectedFinanceCubeId = -1;
                $scope.selectedFinanceCubeVisId = "";
                $scope.selectedFinanceCubeFormulaId = -1;
                $scope.selectedFinanceCubeFormulaVisId = "";
                $scope.selectedModelId = -1;
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            openModal($scope.selectedModelId, $scope.selectedFinanceCubeId, $scope.selectedFinanceCubeFormulaId);
        }

        function deleteFinanceCubeFormula() {
            if ($scope.selectedFinanceCubeId == -1) {
                return;
            }
            swal({
                title: "Are you sure",
                text: "you want to delete Finance Cube Formula \"" + $scope.selectedFinanceCubeFormulaVisId + "\" for Finance Cube \"" + $scope.selectedFinanceCubeVisId + "\"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    FinanceCubeFormulaPageService.deleteFinanceCubeFormula($scope.selectedModelId, $scope.selectedFinanceCubeId, $scope.selectedFinanceCubeFormulaId, $scope.selectedFinanceCubeVisId, $scope.selectedFinanceCubeFormulaVisId);
                }
            });
        }


        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = FinanceCubeFormulaPageService.getActions();

            FinanceCubeFormulaPageService.isOpenDisabled = $scope.selectedFinanceCubeId === -1;

            if ($scope.selectedFinanceCubeId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for insert or update). Insert when financeCubeFormulaId equals -1, update otherwise.
         * 
         * @param {Integer} financeCubeFormulaId
         * @param {Integer} modelId
         * @param {Integer} financeCubeId
         */
        var openModal = function(modelId, financeCubeId, financeCubeFormulaId) {
            var modalInstance = $modal.open({
                template: '<finance-cube-formula-details model="modelId" finance-cube="financeCubeId" finance-cube-formula="financeCubeFormulaId" ></finance-cube-formula-details>',
                windowClass: 'finance-cube-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.financeCubeId = financeCubeId;
                        $scope.modelId = modelId;
                        $scope.financeCubeFormulaId = financeCubeFormulaId;
                        $scope.$on('FinanceCubeFormulaDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        /** ****************************************************** FILTERS ******************************************************* */
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

        /** ****************************************************** CUSTOM CELLS ******************************************************* */
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
                            ' <button type="buttType == on" class="btn btn-danger btn-xs" ng-click="deleteFinanceCubeFormula()">Delete</button>';
                        break;
                    case 'type':
                        html = formatTypeAutomaticManual(panel.rows[r].dataItem['type']);
                }
                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }
        /** ****************************************************** WATCHERS ******************************************************* */
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
        /** *****************************************************Change type 0,1 to string ************************************** */
        var formatTypeAutomaticManual = function(value) {
            var newValue = null;
            switch (value) {
                case 0:
                    newValue = "Automatic";
                    break;
                case 1:
                    newValue = "Manual";
                    break;
            }
            return newValue; 
        };

        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on("SubMenuController:create", function() {
            openModal(-1, -1, -1);
        });

        $scope.$on("SubMenuController:deleteFinanceCubeFormula", function() {
            $scope.deleteFinanceCubeFormula();
        });
        $scope.$on("SubMenuController:refresh", function() {
            FinanceCubeFormulaPageService.getFinanceCubeFormula();
        });
    }
})();