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
        .module('adminApp.modelUserSecurity')
        .service('ModelUserSecurityService', ModelUserSecurityService);

    /* @ngInject */
    function ModelUserSecurityService($rootScope, $http, $timeout, CoreCommonsService) {

        var self = this;
        var isModelLoaded = false;
        var isModelLoading = false;
        var url = $BASE_PATH + 'adminPanel/modelUser';
        var modelsSecurity = new wijmo.collections.CollectionView();
        var actions = [];
        self.isCreateDisabled = true;
        self.isOpenDisabled = true;
        self.getModelUserSecurityDetailsFromDatabase = getModelUserSecurityDetailsFromDatabase;
        self.saveModel = saveModel;
        self.getModelsSecurity = getModelsSecurity;
        self.getActions = getActions;

        /**
         * Requests database for list of ModelsSecurity.
         */
        var getModelsSecurityFromDatabase = function() {
            isModelLoaded = false;
            isModelLoading = true;

            $http.get(url).success(function(data) {
                isModelLoaded = true;
                isModelLoading = false;
                if (data && data.length > 0) {
                    modelsSecurity.sourceCollection = data;
                }
            });
        };

        /**
         * Gets details for selected ModelUserSecurity.
         *
         * @param {[type]} selectedModelId [description]
         */
        function getModelUserSecurityDetailsFromDatabase(selectedModelId) {
            var modelUserSecurityDetails = {};
            $http.get(url + "/" + selectedModelId).success(function(data) {
                angular.copy(data, modelUserSecurityDetails);
            });
            return modelUserSecurityDetails;
        }

        /**
         * Save Model.
         */
        function saveModel(modelId, modelUserElementAccess, deployForms) {
            if (modelId === null || modelUserElementAccess === null || deployForms === null) {
                return;
            }
            var modelUserSaveData = {
                modelId: modelId,
                modelUserElementAccess: modelUserElementAccess,
                deployForms: deployForms
            };
            var method = "PUT";
            $http({
                method: method,
                url: url,
                data: modelUserSaveData

            }).success(function(response) {
                if (response.success) {
                    var modelChange = CoreCommonsService.findElementByKey(modelsSecurity.sourceCollection, modelUserSaveData.modelId, 'modelId');
                    modelsSecurity.refresh();
                    var dataToReturn = response;
                    // to response object add information if it was PUT or POST action
                    dataToReturn.method = method;
                    $rootScope.$broadcast('ModelUserSecurityDetails:close', dataToReturn);
                } else if (response.error) {
                    $rootScope.$broadcast('ModelUserSecurityDetails:modelsSecuritySaveError', response);
                };
            });
        }

        /**
         * Get list of ModelsSecurity.
         */
        function getModelsSecurity(hardReload) {
            if ((!isModelLoaded && !isModelLoading) || hardReload) {
                getModelsSecurityFromDatabase();
            }
            return modelsSecurity;
        }

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
    }
})();