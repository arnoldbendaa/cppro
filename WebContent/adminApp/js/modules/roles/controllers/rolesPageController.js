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
        .module('adminApp.rolesPage')
        .controller('RolesPageController', RolesPageController);

    /* @ngInject */
    function RolesPageController($rootScope, $scope, $compile, $modal, PageService, FilterService, RolesPageService, CoreCommonsService, ContextMenuService) {

        var roles = {};
        $scope.selectedRoleId = -1;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_roles";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteRole = deleteRole;
        activate();

        function activate() {
            PageService.setCurrentPageService(RolesPageService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            roles = RolesPageService.getRoles();
            $scope.ctx = {
                flex: null,
                filter: '',
                data: roles
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
         * If previous selected row id is different from current, change selectedRoleId.
         * Otherwise reset selectedRoleId ('cause Role was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedRoleId = current.roleId;
            }
            ContextMenuService.updateActions();
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing
         */
        function open() {
            openModal($scope.selectedRoleId);
        }

        /**
         * Call deleting Data Type
         * @param  {Integer} roleId
         */
        function deleteRole() {
            if ($scope.selectedRoleId == -1) {
                return;
            }
            openDeletingConfirmBox();
        }


        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = RolesPageService.getActions();

            RolesPageService.isOpenDisabled = $scope.selectedRoleId === -1;
            RolesPageService.isPrintDisabled = $scope.selectedRoleId === -1;

            if ($scope.selectedRoleId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            $rootScope.$broadcast('PageService:changeService', {});
        };

        var openModal = function(roleId) {
            var modalInstance = $modal.open({
                template: '<roles-details id="roleId"></roles-details>',
                windowClass: 'roles-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.roleId = roleId;
                        $scope.$on('RolesDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        /**
         * Show alert before delete Role and then call deleting method.
         */
        var openDeletingConfirmBox = function() {
            swal({
                title: "Are you sure",
                text: "you want to delete role?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    RolesPageService.deleteRole($scope.selectedRoleId);
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
                            '<button type="button" class="btn btn-danger btn-xs" ng-click="deleteRole();">Delete</button>';
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
        $scope.$on('SubMenuController:create', function() {
            openModal(-1);
        });
        $scope.$on('SubMenuController:deleteRole', function() {
            $scope.deleteRole();
        });

        $scope.$on("SubMenuController:refresh", function() {
            RolesPageService.getRoles(true);
        });
    }
})();