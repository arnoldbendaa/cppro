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
        .module('adminApp.systemPropertiesPage')
        .controller('SystemPropertiesPageController', SystemPropertiesPageController);

    /* @ngInject */
    function SystemPropertiesPageController($rootScope, $scope, $compile, $modal, PageService, FilterService, SystemPropertiesPageService, CoreCommonsService, ContextMenuService) {

        var systemProperties = {};
        $scope.selectedSystemPropertyId = -1;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_systemProperties";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        activate();

        function activate() {
            PageService.setCurrentPageService(SystemPropertiesPageService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            systemProperties = SystemPropertiesPageService.getSystemProperties();
            $scope.ctx = {
                flex: null,
                filter: '',
                data: systemProperties
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
         * If previous selected row id is different from current, change selectedSystemPropertyId.
         * Otherwise reset selectedSystemPropertyId ('cause SystemProperty was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedSystemPropertyId = current.systemPropertyId;
            } else {
                $scope.selectedSystemPropertyId = -1;
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            openModal($scope.selectedSystemPropertyId);
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = SystemPropertiesPageService.getActions();

            SystemPropertiesPageService.isOpenDisabled = $scope.selectedSystemPropertyId === -1;

            if ($scope.selectedSystemPropertyId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for insert or update). Insert when systemPropertyId equals -1, update otherwise.
         * @param  {Integer} systemPropertyId
         */
        var openModal = function(systemPropertyId) {
            var modalInstance = $modal.open({
                template: '<system-property-details system-property="systemPropertyId"></system-properties-details>',
                windowClass: 'system-property-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.systemPropertyId = systemPropertyId;
                        $scope.$on('SystemPropertyDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
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
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open(); ">Open</button> ';
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

        $scope.$on("SubMenuController:refresh", function() {
            SystemPropertiesPageService.getSystemProperties(true);
        });
    }
})();