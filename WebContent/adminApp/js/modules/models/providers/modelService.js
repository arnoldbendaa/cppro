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
        .module('adminApp.model')
        .service('ModelService', ModelService);

    /* @ngInject */
    function ModelService($rootScope, Flash, $http, $timeout, AccountService, BusinessService, CalendarService, FinanceCubesPageService, CoreCommonsService) {

        var self = this;
        var isFinanceCubeModelLoaded = false;
        var isFinanceCubeModelLoading = false;
        var isModelLoaded = false;
        var isModelLoading = false;
        var url = $BASE_PATH + 'adminPanel/';
        var financeCubeModels = new wijmo.collections.CollectionView();
        var models = new wijmo.collections.CollectionView();
        var importData = {};
        var actions = [{
            name: "Delete",
            action: "deleteModel",
            disabled: true
        }, {
            name: "Import",
            action: "import",
            disabled: true
        }];
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.clearCache = clearCache;
        self.getFinanceCubeModels = getFinanceCubeModels;
        self.getModelDetailsFromDatabase = getModelDetailsFromDatabase;
        self.getImportData = getImportData;
        self.saveModel = saveModel;
        self.sendImportData = sendImportData;
        self.findModelById = findModelById;
        self.manageModelName = manageModelName;
        self.manageModelNameWithDescription = manageModelNameWithDescription;
        self.getModels = getModels;
        self.deleteModel = deleteModel;
        self.getActions = getActions;

        /**
         * Clear list of Models and dependencies.
         */
        function clearCache() {
            getFinanceCubeModelsFromDatabase();

            isModelLoaded = false;
            isFinanceCubeModelLoading = false;

            AccountService.clearCache();
            BusinessService.clearCache();
            CalendarService.clearCache();
            //HierarchiesService.clearCache();
            FinanceCubesPageService.clearCache();
        }

        var getFinanceCubeModelsFromDatabase = function() {
            isFinanceCubeModelLoaded = false;
            isFinanceCubeModelLoading = true;

            $http.get(url + "models/financeCubeModels").success(function(data) {
                isFinanceCubeModelLoaded = true;
                isFinanceCubeModelLoading = false;
                if (data && data.length > 0) {
                    financeCubeModels.sourceCollection = data;
                }
            });
        };

        function getFinanceCubeModels() {
            if (!isFinanceCubeModelLoaded && !isFinanceCubeModelLoading) {
                getFinanceCubeModelsFromDatabase();
            }
            return financeCubeModels;
        }

        /**
         * Requests database for list of Models.
         */
        var getModelsFromDatabase = function() {
            isModelLoaded = false;
            isModelLoading = true;

            //$timeout(function() { // do celów testowych
            $http.get(url + "models").success(function(data) {
                isModelLoaded = true;
                isModelLoading = false;
                if (data && data.length > 0) {
                    models.sourceCollection = data;
                }
            });
            // }, 10000);
        };

        /**
         * Gets details for selected Model or empty Model with lists available references dimensions and hierarchies.
         *
         * @param {[type]} selectedModelId [description]
         */
        function getModelDetailsFromDatabase(selectedModelId) {
            var modelDetails = {};
            $http.get(url + "models/" + selectedModelId).success(function(data) {
                angular.copy(data, modelDetails);
            });
            return modelDetails;
        }

        /**
         * Get models and data types to let user prepare import task event
         * @param  {Integer} modelId
         */
        function getImportData(modelId) {
            importData = {};
            if (modelId != -1) {
                $http.get(url + "models/import/" + modelId).success(function(response) {
                    angular.copy(response, importData);
                    angular.forEach(importData.models, function(model) {
                        // model is selected by default, if ends with "1"
                        model.modelSelection = (model.modelVisId.slice(-1) == "1") ? true : false;
                    });
                    angular.forEach(importData.dataTypes, function(dataType) {
                        // all data types (except "AQ" and "AV") are selected by default
                        dataType.dataTypeSelection = (dataType.dataTypeVisId == "AQ" || dataType.dataTypeVisId == "AV") ? false : true;
                    });
                });
            }
            return importData;
        }

        /**
         * Save Model.
         */
        function saveModel(modelDetails) {
            var insert;
            if (modelDetails === null) {
                return;
            }
            if (modelDetails.modelId != -1) {
                // edit DataType
                var method = "PUT";
                insert = false;
            } else {
                // create DataType
                var method = "POST";
                insert = true;
            }
            $http({
                method: method,
                url: url + "models",
                data: modelDetails
            }).success(function(response) {
                if (response.success) {
                    self.clearCache();
                    if (insert){
                        getModelsFromDatabase();
                    } else {
                    var modelChange = CoreCommonsService.findElementByKey(models.sourceCollection, modelDetails.modelId, 'modelId');
                    modelChange.modelVisId = modelDetails.modelVisId;
                    modelChange.modelDescription = modelDetails.modelDescription;
                    models.refresh();
                    }
                    var dataToReturn = response;
                    // to response object add information if it was PUT or POST action
                    dataToReturn.method = method;
                    $rootScope.$broadcast('ModelDetails:close', dataToReturn);
                } else if (response.error) {
                    $rootScope.$broadcast('ModelDetails:modelsSaveError', response);
                };
            });
        }

        function sendImportData(dataToSend, modelId) {
            $http({
                method: "POST",
                url: url + "models/import/" + modelId,
                data: dataToSend
            }).success(function(response) {
                if (response.success) {
                    $rootScope.$broadcast('Model:dataImportClose', response);
                } else if (response.error) {
                    $rootScope.$broadcast('Model:dataImportError', response);
                };
            });
        }

        function findModelById(modelId) {
            var findedModel = null;
            if (!angular.isDefined(modelId)) {
                return findedModel;
            }
            if (models.sourceCollection.length != 0) {
                angular.forEach(models.sourceCollection, function(model) {
                    if (model.modelId === modelId) {
                        findedModel = model;
                        return;
                    }
                });
            } else if (financeCubeModels.sourceCollection.length != 0) {
                angular.forEach(financeCubeModels.sourceCollection, function(model) {
                    if (model.modelId === modelId) {
                        findedModel = model;
                        return;
                    }
                });
            }
            return findedModel;
        }

        function manageModelName(modelId) {
            var model = self.findModelById(modelId);
            if (model == null) {
                return "";
            }
            return model.modelVisId;
        }

        function manageModelNameWithDescription(modelId) {
            var model = self.findModelById(modelId);
            if (model == null) {
                return "";
            }
            return model.modelVisId + " - " + model.modelDescription;
        }

        /**
         * Get list of Models.
         */
        function getModels(hardReload) {
            if ((!isModelLoaded && !isModelLoading) || hardReload) {
                getModelsFromDatabase();
            }
            return models;
        }

        /**
         * Delete selected Model.
         *
         * @param {[type]} modelId [description]
         */
        function deleteModel(selectedModelId, selectedModelVisId) {
            $http.delete(url + "models/" + selectedModelId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    self.clearCache();
                    getModelsFromDatabase();
                    Flash.create('success', "Model = " + selectedModelVisId + " deleted.");
                }
            });
        }

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
    }
})();