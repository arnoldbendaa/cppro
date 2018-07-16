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
        .module('adminApp.usersPage')
        .controller('UsersPageController', UsersPageController);

    /* @ngInject */
    function UsersPageController($rootScope, $scope, $compile, $modal, PageService, FilterService, UsersPageService, CoreCommonsService, ContextMenuService) {
        
        var users = {};
        $scope.selectedUsersId = -1;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_users";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteUser = deleteUser;
        $scope.copyUser = copyUser;
        activate();
        
        function activate(){
            PageService.setCurrentPageService(UsersPageService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            users = UsersPageService.getUsers();
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: users
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
         * If previous selected row id is different from current, change selectedUsersId.
         * Otherwise reset selectedUsersId ('cause usersId was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedUsersId = current.userId;
            } else {
                $scope.selectedUsersId = -1;
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing
         * @param  {Integer} usersId [description]
         */
        function open() {
            openModal($scope.selectedUsersId);
        }

        /**
         * Call deleting User
         * @param  {Integer} usersId
         */
        function deleteUser() {
            if ($scope.selectedUsersId == -1) {
                return;
            }
            openDeletingConfirmBox();
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = UsersPageService.getActions();

            UsersPageService.isOpenDisabled = $scope.selectedUsersId === -1;
            UsersPageService.isPrintDisabled = $scope.selectedUsersId === -1;

            if ($scope.selectedUsersId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        var openModal = function(usersId) {
            var modalInstance = $modal.open({
                template: '<users-details id="usersId"></users-details>',
                windowClass: 'users-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.usersId = usersId;
                        $scope.$on('UsersDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };
        var openModalforCopy = function(userId,copy){
            var modalInstance = $modal.open({
                template: '<users-details id="usersId" copy="true"></users-details>',
                windowClass: 'users-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.usersId = userId;
                        $scope.$on('UsersDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        }

        var openDeletingConfirmBox = function() {
            swal({
                title: "Are you sure",
                text: "you want to delete User \"" + $scope.selectedUsersId + "\"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    UsersPageService.deleteUser($scope.selectedUsersId);
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
                            '<button type="button" class="btn btn-danger btn-xs" ng-click="deleteUser();">Delete</button>';
                        break;
                }
                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }
        
        function copyUser(){
        	console.log($scope.selectedUsersId);
        	openModalforCopy($scope.selectedUsersId,true);
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
            $scope.selectedUsersId = -1;
            $scope.open();
        });
        $scope.$on('SubMenuController:deleteUser', function() {
            $scope.deleteUser();
        });

        $scope.$on("UsersPageService:userDeleted", function(event, data) {
            $scope.users = usersPageService.getUsers();
        });
        $scope.$on("SubMenuController:copyUser",function(){
        	copyUser();
        });

        $scope.$on("SubMenuController:refresh", function() {
            UsersPageService.getUsers(true);
        });
    }
})();