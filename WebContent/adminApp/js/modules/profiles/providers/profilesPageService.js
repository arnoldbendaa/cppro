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
        .module('adminApp.profilesPage')
        .service('ProfilesPageService', ProfilesPageService);

    /* @ngInject */
    function ProfilesPageService($http, Flash, $rootScope, $q, BudgetCycleService) {

        var self = this;
        var url = $BASE_PATH + 'adminPanel';
        var actions = [{
            name: "Delete",
            action: "deleteProfile",
            disabled: false
        }];
        var OFFSET = 50;
        self.isOpenDisabled = true;
        self.getActions = getActions;
        self.deleteProfile = deleteProfile;
        self.deployFlatForm = deployFlatForm;
        self.undeployFlatForm = undeployFlatForm;
        self.getMobileProfiles = getMobileProfiles;
        self.getWebProfiles = getWebProfiles;
        self.refreshMobileProfiles = refreshMobileProfiles;
        self.refreshWebProfiles = refreshWebProfiles;
        self.getOffset = getOffset;
        self.getProfile = getProfile;
        self.editProfile = editProfile;
        self.getDataForDeployment = getDataForDeployment;
        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }

        function getDataForDeployment(financeCubeModelId, profileId) {
            return $q.all([BudgetCycleService.getBudgetCyclesForModel(financeCubeModelId), getProfile(profileId)])
                .then(function(res) {
                    return {
                        res1: res[0].data,
                        res2: res[1]
                    };
                }); //the error case is handled automatically
        };


        function getProfile(profileId) {
            return $http.get(url + "/profiles/profile/" + profileId)
                .then(getMobileProfilesComplete)
                .catch(getMobileProfilesFailed);

            function getMobileProfilesComplete(response) {
                return response.data;
            }

            function getMobileProfilesFailed(error) {
                console.log('Get mobile profiles failed: ' + error.message);
            }
        }

        function editProfile(flatFormDeployment) {
            return $http({
                    method: "PUT",
                    url: url + "/profiles/update",
                    data: flatFormDeployment
                }).then(onDeployFlatForm)
                .catch(function(message) {});

            function onDeployFlatForm(data, status, headers, config) {
                return data.data;
            }
        }

        /**
         * Load mobile profiles from database
         */
        function getMobileProfiles(page) {
            return $http.get(url + "/profiles/mobile/" + page + "/" + OFFSET)
                .then(getMobileProfilesComplete)
                .catch(getMobileProfilesFailed);

            function getMobileProfilesComplete(response) {
                return response.data;
            }

            function getMobileProfilesFailed(error) {
                console.log('Get mobile profiles failed: ' + error.message);
            }
        }

        /**
         * Load web profiles from database
         */
        function getWebProfiles(page, isMobile) {
            return $http.get(url + "/profiles/web/" + page + "/" + OFFSET)
                .then(getWebProfilesComplete)
                .catch(getWebProfilesFailed);

            function getWebProfilesComplete(response) {
                return response.data;
            }

            function getWebProfilesFailed(error) {
                console.log('Get web profiles failed: ' + error.message);
            }
        };

        function refreshMobileProfiles(numberAllDownloadedProfiles) {
            return $http.get(url + "/profiles/mobile/" + 0 + "/" + numberAllDownloadedProfiles)
                .then(refreshMobileProfilesComplete)
                .catch(refreshMobileProfilesFailed);

            function refreshMobileProfilesComplete(response) {
                return response.data;
            }

            function refreshMobileProfilesFailed(error) {
                logger.error('Get mobile profiles failed' + error.data);
            }
        }

        function refreshWebProfiles(numberAllDownloadedProfiles) {
            return $http.get(url + "/profiles/web/" + 0 + "/" + numberAllDownloadedProfiles)
                .then(refreshWebProfilesComplete)
                .catch(refreshWebProfilesFailed);

            function refreshWebProfilesComplete(response) {
                return response.data;
            }

            function refreshWebProfilesFailed(error) {
                logger.error('Get web profiles failed' + error.data);
            }
        }

        /**
         * Delete selected profile
         */
        function deleteProfile(userId, profileId, isMobile, numberAllDownloadedProfiles) {
            return $http.delete(url + "/profiles/userId/" + userId + "/profile/" + profileId)
                .then(deleteMobileProfilesComplete)
                .catch(deleteMobileProfilesFailed);

            function deleteMobileProfilesComplete(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    Flash.create('success', 'profile "' + profileId + '" deleted.');
                }
                if (isMobile) {
                    return self.refreshMobileProfiles(numberAllDownloadedProfiles);
                } else {
                    return self.refreshWebProfiles(numberAllDownloadedProfiles);
                }
            }

            function deleteMobileProfilesFailed(error) {
                console.log('Delete profiles failed: ' + error.message);
            }
        }

        /**
         * Deploy Flat Form
         */
        function deployFlatForm(flatFormDeployment) {
            return $http({
                    method: "POST",
                    url: url + "/profiles/deploy",
                    data: flatFormDeployment
                }).then(onDeployFlatForm)
                .catch(function(message) {});

            function onDeployFlatForm(data, status, headers, config) {
                return data.data;
            }
        }

        /**
         * Undeploy Flat Form
         */
        function undeployFlatForm(flatFormUndeployment, flatFormVisId) {
            $http.post(url + "/profiles/undeploy", flatFormUndeployment).success(function(data) {
                if (data.success) {
                    Flash.create('success', 'Flat Form = ' + flatFormVisId + ' has been undeployed.');
                    $rootScope.$broadcast('profilesUnDeployment:close');
                } else {
                    Flash.create('danger', data.message);
                }
            });
        }

        /**
         * Sets offset to scroll data
         */
        function getOffset() {
            return OFFSET;
        };

    }
})();