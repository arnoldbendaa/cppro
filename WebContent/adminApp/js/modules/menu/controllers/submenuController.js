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
        .module('adminApp.menu')
        .controller('SubMenuController', SubMenuController);

    /* @ngInject */
    function SubMenuController($rootScope, $scope, $timeout, PageService, CoreCommonsService, SessionService) {

        var url = $BASE_PATH;
        var currentService = {};
        $scope.isCreateDisabled = false;
        $scope.isOpenDisabled = false;
        $scope.isRefreshDisabled = false;
        $scope.isActionDisabled = false;
        $scope.actions = {};
        $scope.create = create;
        $scope.open = open;
        $scope.closeTab = closeTab;
        $scope.refresh = refresh;
        $scope.isActionDisabled = isActionDisabled;
        $scope.clearCookies = clearCookies;
        $scope.doAction = doAction;
        $scope.logout = logout;
       
        $scope.$on('PageService:changeService', function(event, args) {
            currentService = PageService.getCurrentPageService();
            if (currentService!==undefined){
                
                $scope.actions = currentService.getActions();
                
    
                $scope.isActionDisabled = true;
                $scope.isCreateDisabled = currentService.isCreateDisabled;
                $scope.isOpenDisabled = currentService.isOpenDisabled;
                $scope.isRefreshDisabled = currentService.isRefreshDisabled;
                
                angular.forEach($scope.actions, function(action) {
                    if (action.disabled == false) {
                        $scope.isActionDisabled = false;
                    }
                });
            }
        });

        function create() {
            $rootScope.$broadcast("SubMenuController:create");
        }

        function open() {
            $rootScope.$broadcast("SubMenuController:open");
        }

        function refresh() {
            $rootScope.$broadcast("SubMenuController:refresh");
        }
        function isActionDisabled() {

        }
        
        function closeTab(){
            CoreCommonsService.closeTab($timeout);
        }
        function clearCookies() {
            $rootScope.$broadcast("CommonsService:clearCookies");
        }

        function doAction(index) {
            if ($scope.actions[index] !== undefined) {
                $rootScope.$broadcast("SubMenuController:" + $scope.actions[index].action);
            }
        }

        function logout() {
            window.location = url + "/logout.do";
        }
    }
})();