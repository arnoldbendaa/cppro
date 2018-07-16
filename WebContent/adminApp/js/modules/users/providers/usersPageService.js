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
        .service('UsersPageService', UsersPageService);

    /* @ngInject */
    function UsersPageService($rootScope, $http, Flash, CoreCommonsService) {

        var self = this;
        var isUsersLoaded = false;
        var isUsersLoading = false;
        var url = $BASE_PATH + 'adminPanel/users';
        var users = new wijmo.collections.CollectionView();
        var actions = [{
            name: "Delete",
            action: "deleteUser",
            disabled: true,
        },{
        	name:"Copy user",
        	action:"copyUser",
        	disabled:true
        }];
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.getUsersDetailsFromDatabase = getUsersDetailsFromDatabase;
        self.updateUsersInDatabase = updateUsersInDatabase;
        self.deleteUser = deleteUser;
        self.save = save;
        self.getUsers = getUsers;
        self.getActions = getActions;
        self.copyUser = copyUser;
        //mobile
        self.getMobileProfilesFromDatabase = getMobileProfilesFromDatabase;
        self.deleteUsersProfile = deleteUsersProfile;

        /**
         * Call all Users from database.
         * [getUsersFromDatabase description]
         * @return {[type]} [description]
         */
        var copyUser = function(){
        	console.log("copy user");
        }

        var getUsersFromDatabase = function() {
            isUsersLoaded = false;
            isUsersLoading = true;
            $http.get(url + "/").success(function(data) {
                isUsersLoaded = true;
                if (data && data.length > 0) {
                    users.sourceCollection = data;
                }
            });
        };
        /**
         * Call user details from database.
         */

        function getUsersDetailsFromDatabase(userId) {
            isUsersLoaded = false;
            isUsersLoading = true;
            $http.get(url + "/" + userId).success(function(data) {
                isUsersLoaded = true;
                isUsersLoading = false;

                $rootScope.$broadcast('UsersPageService:usersDetailsUpdated', data);
            });
        }

        /**
         * Update user in database.
         *@param { } [user]
         */
        function updateUsersInDatabase(user) {
            $http.put(url + "/" + user.userId, user).success(function(data) {
                if (data.success == false) {
                    $rootScope.$broadcast('UsersPageService:usersUpdatedError', data);

                } else {

                    if (user.userId == -1) {
                        getUsersFromDatabase();
                    } else {
                        var userChange = CoreCommonsService.findElementByKey(users.sourceCollection, user.userId, 'userId');
                        userChange.userName = user.userName;
                        userChange.userFullName = user.userFullName;
                        userChange.userDisabled = user.userDisabled;
                        users.refresh();
                    }

                    $rootScope.$broadcast('UsersDetails:close');
                    Flash.create('success', 'User details are saved.');
                }

            })
        }
        /**
         * Delete one user in database.
         */
        function deleteUser(userId) {
            $http.delete(url + "/" + userId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    Flash.create('success', 'User "' + userId + '" is deleted.');
                    getUsersFromDatabase();
                }
            })
        }

        function save(users) {
            if (users === null) return;
        }

        function getUsers(hardReload) {
            if ((!isUsersLoaded && !isUsersLoading) || hardReload) {
                getUsersFromDatabase();
            }
            return users;
        }

        function getActions() {
            return actions;
        }


        //////////////////////////////////////// MOBILE PROFILES ////////////////////////////////////


        function getMobileProfilesFromDatabase(userId) {
            var urlMobileProfiles = $BASE_PATH + 'adminPanel/profiles/mobileProfiles';
            return $http.get(urlMobileProfiles + "?userId=" + userId).then(getAllComplete)

            function getAllComplete(response) {
                var result = {
                    data: response.data
                };
                return result;
            }
        }

        function deleteUsersProfile(userId, profileId) {
            $http.delete($BASE_PATH + "adminPanel/profiles/mobile/userId/" + userId + "/profile/" + profileId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    Flash.create('success', 'Profile "' + profileId + '" is deleted.');
                }
            })
        }
    }
})();