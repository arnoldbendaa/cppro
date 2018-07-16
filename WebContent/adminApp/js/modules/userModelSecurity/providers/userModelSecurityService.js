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
        .service('UserModelSecurityService', UserModelSecurityService);

    /* @ngInject */
    function UserModelSecurityService($rootScope, $http, $timeout, CoreCommonsService) {

        var self = this;
        var isUserLoaded = false;
        var isUserLoading = false;
        var url = $BASE_PATH + 'adminPanel/userModel';
        var usersSecurity = new wijmo.collections.CollectionView();
        var actions = [];
        self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.getUserModelSecurityDetailsFromDatabase = getUserModelSecurityDetailsFromDatabase;
        self.getTree = getTree;
        self.saveUser = saveUser;
        self.getUsersSecurity = getUsersSecurity;
        self.getActions = getActions;
        
        /**
         * Requests database for list of UsersSecurity.
         */
        var getUsersSecurityFromDatabase = function() {
            isUserLoaded = false;
            isUserLoading = true;

            $http.get(url).success(function(data) {
                isUserLoaded = true;
                isUserLoading = false;
                if (data && data.length > 0) {
                    usersSecurity.sourceCollection = data;
                }
            });
        };

        /**
         * Gets details for selected UserModelSecurity.
         *
         * @param {[type]} selectedUserId [description]
         */
        function getUserModelSecurityDetailsFromDatabase(selectedUserId) {
            var userModelSecurityDetails = {};
            $http.get(url + "/" + selectedUserId).success(function(data) {
                angular.copy(data, userModelSecurityDetails);
            });
            return userModelSecurityDetails;
        }
        
        /**
         * Gets tree for selected Model.
         *
         * @param {[type]} modelId [description]
         */
        function getTree(modelId) {
            var tree = {};
            $http.get(url + "/tree/" + modelId).success(function(data) {
                angular.copy(data, tree);
            });
            return tree;
        }

        /**
         * Save User.
         */
        function saveUser(userId, userModelElementAccess, deployForms) {
            if (userId === null || userModelElementAccess === null || deployForms === null) {
                return;
            }
            var userModelSaveData = {
                    userId: userId,
                    userModelElementAccess: userModelElementAccess,
                    deployForms: deployForms
            };
            var method = "PUT";
            $http({
                method: method,
                url: url,
                data: userModelSaveData
                
            }).success(function(response) {
                if (response.success) {
                    //getUsersSecurityFromDatabase();
                    
                    var modelChange = CoreCommonsService.findElementByKey(usersSecurity.sourceCollection, userModelSaveData.modelId, 'modelId');
                    usersSecurity.refresh();
                    var dataToReturn = response;
                    // to response object add information if it was PUT or POST action
                    dataToReturn.method = method;
                    $rootScope.$broadcast('UserModelSecurityDetails:close', dataToReturn);
                } else if (response.error) {
                    $rootScope.$broadcast('UserModelSecurityDetails:usersSecuritySaveError', response);
                };
            });
        }

        /**
         * Get list of UsersSecurity.
         */
        function getUsersSecurity(hardReload) {
            if ((!isUserLoaded && !isUserLoading) || hardReload) {
                getUsersSecurityFromDatabase();
            }
            return usersSecurity;
        }

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
    }
})();