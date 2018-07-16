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
        .module('adminApp.externalSystem')
        .controller('ExternalSystemController', ExternalSystemController);

    /* @ngInject */
    function ExternalSystemController($rootScope, $scope, $compile, $modal, Flash, PageService, FilterService, CoreCommonsService, ContextMenuService, ExternalSystemService) {
        
        var externalSystems;
        $scope.selectedExternalSystemId = -1;
        $scope.ctx = {};
        $scope.cookieName = "adminPanel_externalSystem";    
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteExternalSystem = deleteExternalSystem;
        $scope.changeSelectedExternalSystem = changeSelectedExternalSystem;
        activate();
        
        function activate(){
            PageService.setCurrentPageService(ExternalSystemService);
            externalSystems = ExternalSystemService.getExternalSystems();
            $scope.globalSettings = CoreCommonsService.globalSettings; 
            // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: externalSystems
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
         * Handles when row with budget cycle is selected. Updates selectedBudgetCycleId.
         * If user clicks in the same element, controller deselects row.
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            $scope.current = current;
            if (current !== null) {
                $scope.selectedExternalSystemId = current.externalSystemId;
            } else {
                $scope.selectedExternalSystemId = -1;
            }
            manageActions();
        }

        /**
         * Function is invoked after click in open button (on the list of external system or stored in submenu).
         */
        function open() {
            openModal($scope.selectedExternalSystemId);
        }

        /**
         * Delete selected budget cycle (if it status is completed)
         * Function is invoked after click in delete button (on the list of budget cycles
         * or stored in submenu)
         */
        function deleteExternalSystem() {
            if ($scope.selectedExternalSystemId == -1) {
                return;
            }
            var externalSystemToDelete = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, $scope.selectedExternalSystemId, 'externalSystemId');
            swal({
                title: "Are you sure",
                text: "to delete external system \"" + externalSystemToDelete.externalSystemVisId + "\"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    ExternalSystemService.deleteExternalSystem(externalSystemToDelete.externalSystemId, externalSystemToDelete.externalSystemVisId);
                    $scope.selectedExternalSystemId = -1;
                }
            });
        }

        /**
         * Opens modal with details of external system.
         */
        var openModal = function(externalSystemId) {
            var modalInstance = $modal.open({
                template: '<external-system-details id="externalSystemId" close="close()"></external-system-details>',
                windowClass: 'external-system-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.externalSystemId = externalSystemId;

                        $scope.close = function() {
                            $modalInstance.close();
                        };
                    }
                ]
            });
        };

        /**
         * Invokes when row is selected. Updates selectedExternalSystemId.
         * If user clicks in the same element, controller deselects row.
         */
        function changeSelectedExternalSystem(externalSystemId) {
            if ($scope.selectedExternalSystemId !== externalSystemId) {
                $scope.selectedExternalSystemId = externalSystemId;
            } else {
                $scope.selectedExternalSystemId = -1;
            }
            manageActions();
        }

        /**
         * Method disables or enables buttons in submenu.
         * When none of row is selected, buttons are disabled
         */
        var manageActions = function() {
            var actions = ExternalSystemService.getActions();

            ExternalSystemService.isOpenDisabled = $scope.selectedExternalSystemId === -1;

            if ($scope.selectedExternalSystemId !== -1) {
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
            return $rootScope.filter.byWord;
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
                            ' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteExternalSystem()">Delete</button>';
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
            $scope.selectedExternalSystemId = -1;
            $scope.open();
        });

        $scope.$on("SubMenuController:deleteExternalSystem", function() {
            $scope.deleteExternalSystem();
        });

        $scope.$on("SubMenuController:refresh", function() {
            ExternalSystemService.getExternalSystems(true);
        });
    }
})();