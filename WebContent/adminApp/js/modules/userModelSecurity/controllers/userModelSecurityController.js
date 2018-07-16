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
        .module('adminApp.userModelSecurity')
        .controller('UserModelSecurityController', UserModelSecurityController);

    /* @ngInject */
    function UserModelSecurityController($rootScope, $scope, $compile, $modal, PageService, FilterService, UserModelSecurityService, CoreCommonsService, ContextMenuService) {

        var usersSecurity = {};
        $scope.selectedUserId = -1;
        $scope.selectedUserDescription = "";
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_userModelSecurity";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        activate();
        
        function activate(){
            PageService.setCurrentPageService(UserModelSecurityService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            usersSecurity = UserModelSecurityService.getUsersSecurity();
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: usersSecurity
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
                $scope.selectedUserId = current.userId;
                $scope.selectedUserDescription = current.userFullName;
            } else {
                $scope.selectedUserId = -1;
                $scope.selectedUserDescription = "";
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            openModal($scope.selectedUserId, $scope.selectedUserDescription);
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = UserModelSecurityService.getActions();

            UserModelSecurityService.isOpenDisabled = $scope.selectedUserId === -1;
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for insert or update). Insert when userId equals -1, update otherwise.
         * @param  {Integer} userId
         */
        var openModal = function(userId, userDescription) {
            var modalInstance = $modal.open({
                template: '<user-model-security-details user="userId" fullname="userDescription"></user-model-security-details>',
                windowClass: 'model-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.userId = userId;
                        $scope.userDescription = userDescription;
                        $scope.$on('UserModelSecurityDetails:close', function(event, args) {
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
            return $rootScope.currentUser
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
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open()">Open</button>';
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
            UserModelSecurityService.getUsersSecurity(true);
        });
    }
})();