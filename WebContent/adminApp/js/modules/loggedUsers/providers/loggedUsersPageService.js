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
        .module('adminApp.loggedUsersPage')
        .service('LoggedUsersPageService', LoggedUsersPageService);

    /* @ngInject */
    function LoggedUsersPageService($rootScope, $http, Flash) {

        var self = this;
        var isLoggedUsersLoaded = false;
        var isLoggedUsersLoading = false;
        var url = $BASE_PATH + 'adminPanel/loggedUsers';
        var loggedUsers = new wijmo.collections.CollectionView();
        var actions = [{
            name: "Logout",
            action: "logout",
            disabled: true,
        }];
        self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.logoutUser = logoutUser;
        self.getLoggedUsers = getLoggedUsers;
        self.getActions = getActions;
        
        /**
         *
         *Return Logged in Users data
         *
         */

        var getLoggedUsersFromDatabase = function() {
            isLoggedUsersLoaded = false;
            isLoggedUsersLoading = true;

            $http.get(url).success(function(data) {
                var isloggedUsersLoaded = true;
                if (data && data.length > 0) {
                    loggedUsers.sourceCollection = data;
                }
            });
        };

        /**
         * Logout Logged User
         * @param  {String} name
         */

        function logoutUser(name) {
            $http.get(url + "/" + name).success(function(data) {
                console.log(data);

                if (data.success == false) {

                    Flash.create('danger', data.message);
                } else {
                    getLoggedUsersFromDatabase();
                }
            })
        }
        /**
         * Return Logged in Users if were taken, otherwise call getting Logged In Users from database.
         */
        function getLoggedUsers() {
            getLoggedUsersFromDatabase();
            return loggedUsers;
        }

        /**
         * For top buttons
         */

        function getActions() {
            return actions;
        }
    }
})();