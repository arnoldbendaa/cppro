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
        .module('flatFormTemplateApp.configurations')
        .service('ConfigurationsService', ConfigurationsService);


    function ConfigurationsService($rootScope, $http, $timeout) {

        var self = this;
        var url = $BASE_PATH + 'flatFormTemplate/configurations/';
        var configurations = new wijmo.collections.CollectionView();
        var models = new wijmo.collections.CollectionView();
        var isEmptyConfigurationLoaded = false;
        var isEmptyConfigurationLoading = false;

        self.getConfigurationsFromDatabaseUrl = getConfigurationsFromDatabaseUrl;
        self.getConfigurationsFromDatabaseForGenerateUrl = getConfigurationsFromDatabaseForGenerateUrl;
        self.generateEmptyFolder = generateEmptyFolder;
        self.deleteTreeElement = deleteTreeElement;
        self.insertTreeElement = insertTreeElement;
        self.updateTreeElement = updateTreeElement;
        self.updateNameTreeElement = updateNameTreeElement;
        self.getEmptyEditTreeElement = getEmptyEditTreeElement;
        self.getConfigurationsFromDatabase = getConfigurationsFromDatabase;
        self.getConfigurationsFromDatabaseForGenerate = getConfigurationsFromDatabaseForGenerate;
        self.getConfigurationsDetailsFromDatabase = getConfigurationsDetailsFromDatabase;
        self.getTree = getTree;
        self.saveModel = saveModel;
        self.getModels = getModels;
        self.updatePosition = updatePosition;

        function getConfigurationsFromDatabaseUrl() {
            return url;
        };

        function getConfigurationsFromDatabaseForGenerateUrl() {
            return url + "generate";
        };

        self.newNodeDate = Date.now();

        function generateEmptyFolder() {
            var emptyFolder = {
                configurationUUID: null,
                configurationVisId: "New " + self.newNodeDate,
                parentUUID: null,
                directory: true,
                versionNum: 0
            };
            self.newNodeDate = Date.now();
            return emptyFolder;
        };

        function getEmptyEditTreeElement() {
            var editTreeElement = {
                configurationUUID: null,
                configurationVisId: "New " + self.newNodeDate,
                versionNum: 0,
                parentUUID: null,
            };
            return editTreeElement;
        };

        function deleteTreeElement(responseData) {
            $http.delete("/cppro/flatFormTemplate/configurations/" + responseData.obj.original.configurationUUID).success(function(data) {
                console.log("deleted", data);
                responseData.data = data;
                $rootScope.$broadcast("ConfigurationsService:deleteSuccess", responseData);
            });
        };

        function insertTreeElement(newNode, responseData, dependent) {
            $http.post("/cppro/flatFormTemplate/configurations/", newNode).success(function(data) {
                console.log("inserted", data);
                responseData.data = data;
                responseData.newNode = newNode;
                if (newNode.directory === true) {
                    $rootScope.$broadcast("ConfigurationsService:insertFolderSuccess", responseData);
                } else {
                    $rootScope.$broadcast("ConfigurationsService:insertSuccess", responseData, dependent);
                }
            });
        };

        function updateTreeElement(editedNode) {
            $http.put("/cppro/flatFormTemplate/configurations/" + editedNode.configurationUUID, editedNode).success(function(data) {
                console.log("updated", data);
                $rootScope.$broadcast("ConfigurationsService:updateSuccess", data);
            });
        };

        function updateNameTreeElement(editedNode) {
            $http.put("/cppro/flatFormTemplate/configurations/" + editedNode.configurationUUID + "/rename", editedNode).success(function(data) {
                console.log("updated", data);
                $rootScope.$broadcast("ConfigurationsService:updateNameSuccess", data);
            });
        };

        function updatePosition(moveEvent, node) {
            return $http({
                    method: "PUT",
                    url: url + "index",
                    data: moveEvent
                })
                .then(onSaveConfiguration)
                .catch(onErrorHandler);

            function onSaveConfiguration(data, status, headers, config) {
                $rootScope.$broadcast("ConfigurationsService:updateIndexSuccess", moveEvent, node);
            }

            function onErrorHandler(message) {
                $rootScope.$broadcast("ConfigurationsService:updateIndexProblem", moveEvent);
            }
        }
        /**
         * Requests database for list of ModelsSecurity.
         */
        function getConfigurationsFromDatabase() {
            var confTree = {};
            $http.get(url).success(function(data) {
                angular.copy(data, confTree);
            });
            return confTree;
        };

        function getConfigurationsFromDatabaseForGenerate() {
            var confTree = {};
            $http.get(url + "/generate").success(function(data) {
                angular.copy(data, confTree);
            });
            return confTree;
        };

        /**
         * Gets details for selected Configurations.
         *
         * @param {[type]} selectedModelId [description]
         */
        function getConfigurationsDetailsFromDatabase(selectedConfigurationId) {
            var configurationsDetails = {};
            $http.get(url + selectedConfigurationId).success(function(data) {
                angular.copy(data, configurationsDetails);
            });
            return configurationsDetails;
        };

        function getTree(modelId) {
            var tree = {};
            $http.get($BASE_PATH + "adminPanel/userModel/tree/" + modelId).success(function(data) {
                angular.copy(data, tree);
            });
            return tree;
        };

        /**
         * Save Model.
         */
        function saveModel(configuration) {
            if (configuration === null) {
                return;
            }
            var method = "PUT";
            $http({
                method: method,
                url: url,
                data: configuration

            }).success(function(response) {
                if (response.success) {
                    getConfigurationsFromDatabase();
                    var dataToReturn = response;
                    // to response object add information if it was PUT or POST action
                    dataToReturn.method = method;
                    $rootScope.$broadcast('ConfigurationsDetails:close', dataToReturn);
                } else if (response.error) {
                    $rootScope.$broadcast('ConfigurationsDetails:modelsSecuritySaveError', response);
                };
            });
        };

        /**
         * Requests database for list of Models.
         */
        function getModels() {

            $http.get($BASE_PATH + "adminPanel/models").success(function(data) {
                if (data && data.length > 0) {
                    models.sourceCollection = data;
                }
            });
            return models;
        };
    }
})();