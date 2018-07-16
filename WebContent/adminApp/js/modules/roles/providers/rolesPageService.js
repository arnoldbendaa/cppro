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
        .service('RolesPageService', RolesPageService);

    /* @ngInject */
    function RolesPageService($rootScope, $http, Flash, CoreCommonsService) {

        var self = this;
        var isRolesLoaded = false;
        var isRolesLoading = false;
        var url = $BASE_PATH + 'adminPanel/roles';
        var roles = new wijmo.collections.CollectionView();
        var actions = [{
            name: "Delete",
            action: "deleteRole",
            disabled: true,
        }];
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.getRoles = getRoles;
        self.getRoleDetailsFromDatabase = getRoleDetailsFromDatabase;
        self.updateRoleInDatabase = updateRoleInDatabase;
        self.deleteRole = deleteRole;
        self.save = save;
        self.getActions = getActions;
        
        /**
         * Call all roles from database.
         * [getRolesFromDatabase description]
         * @return {[type]} [description]
         */
        var getRolesFromDatabase = function() {
            isRolesLoaded = false;
            isRolesLoading = true;

            $http.get(url + "/").success(function(data) {
                isRolesLoaded = true;
                isRolesLoading = false;

                if (data && data.length > 0) {
                    roles.sourceCollection = data;
                }
            });
        };

        /**
         *Return Roles if were taken, otherwise call getting Roles from database.
         */
        function getRoles(hardReload) {
            if ((!isRolesLoaded && !isRolesLoading) || hardReload){
                getRolesFromDatabase();
            }
            return roles;
        }

        /**
         * Call role details from database.
         */
        function getRoleDetailsFromDatabase(roleId) {
            $http.get(url + "/" + roleId).success(function(data) {
                $rootScope.$broadcast('RolesPageService:rolesDetailsUpdated', data);
            });
        }

        /**
         * Update role in database.
         *@param { } [role]
         */
        function updateRoleInDatabase(role) {
            $http.put(url + "/" + role.roleId, role).success(function(data) {
                if (data.success == false) {
                    $rootScope.$broadcast("RolesPageService:rolesUpdatedError", data);
                } else {
                    
                    if (role.roleId == -1){
                        getRolesFromDatabase();
                        } else{
                            var roleChange = CoreCommonsService.findElementByKey(roles.sourceCollection, role.roleId, 'roleId');
                            roleChange.roleVisId = role.roleVisId;
                            roleChange.roleDescription = role.roleDescription;
                            roles.refresh();  
                        }
                    
                    Flash.create('success', 'Role was saved.');
                    $rootScope.$broadcast('RolesDetails:close');
                }
            })
        }

        /**
         * Delete one role in database.
         */
        function deleteRole(roleId) {
            $http.delete(url + "/" + roleId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    Flash.create('success', 'Role was deleted.');
                    getRolesFromDatabase();
                }
            })
        }

        function save(roles) {
            if (roles === null) return;
            self.update(roles);
        }
        function getActions() {
            return actions;
        }
    }
})();