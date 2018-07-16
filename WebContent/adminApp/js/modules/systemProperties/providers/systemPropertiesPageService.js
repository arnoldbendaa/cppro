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
        .service('SystemPropertiesPageService', SystemPropertiesPageService);

    /* @ngInject */
    function SystemPropertiesPageService($rootScope, $http, Flash, CoreCommonsService) {

        var self = this;
        var isSystemPropertiesLoaded = false;
        var isSystemPropertiesLoading = false;
        var url = $BASE_PATH + 'adminPanel/systemProperties';
        var systemProperties = new wijmo.collections.CollectionView();
        var systemProperty = {};
        var actions = [];
        self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.getSystemProperties = getSystemProperties;
        self.getSystemPropertyDetailsFromDatabase = getSystemPropertyDetailsFromDatabase;
        self.updateSystemPropertyInDatabase = updateSystemPropertyInDatabase;
        self.getActions = getActions;

        /**
         * Get list of System Properties.
         */
        function getSystemProperties(hardReload) {
            if ((!isSystemPropertiesLoaded && !isSystemPropertiesLoading) || hardReload) {
                getSystemPropertiesFromDatabase();
            }
            return systemProperties;
        }

        /**
         * Requests database for list of System Properties.
         */
        var getSystemPropertiesFromDatabase = function() {
            isSystemPropertiesLoaded = false;
            isSystemPropertiesLoading = true;

            $http.get(url + "/").success(function(data) {
                isSystemPropertiesLoaded = true;
                if (data && data.length > 0) {
                    systemProperties.sourceCollection = data;
                };
            });
        };

        /**
         * Gets details for selected System Property.
         * @param  {[type]} systemPropertyId [description]
         */
        function getSystemPropertyDetailsFromDatabase(systemPropertyId) {
            systemProperty = {};
            $http.get(url + "/" + systemPropertyId).success(function(data) {
                angular.copy(data, systemProperty);
            });
            return systemProperty;
        }

        /**
         * Update System Property at database.
         */
        function updateSystemPropertyInDatabase(systemProperty) {
            $http.put(url + "/" + systemProperty.systemPropertyId, systemProperty).success(function(data) {
                if (data.success == false) {
                    $rootScope.$broadcast('SystemPropertiesPageService:systemPropertiesUpdatedError', data);
                } else {
                    var systemPropertiesChange = CoreCommonsService.findElementByKey(systemProperties.sourceCollection, systemProperty.systemPropertyId, 'systemPropertyId');
                    systemPropertiesChange.value = systemProperty.value;
                    systemProperties.refresh();
                    $rootScope.$broadcast('SystemPropertyDetails:close');
                }
            })
        }

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
    }
})();