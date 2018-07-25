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
        .module('adminApp.modelMappings')
        .service('ModelMappingsService', ModelMappingsService);

    /* @ngInject */
    function ModelMappingsService($rootScope, Flash, $http, $timeout, ModelService, CoreCommonsService) {

        var self = this;
        var loader = {};
        var url = $BASE_PATH + 'adminPanel/';
        var mappedModels = new wijmo.collections.CollectionView();
        var actions = {};
        var selectedModel;
        // var mappedModelDetails;
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.clearCache = clearCache;
        self.getLoader = getLoader;
        self.clearLoader = clearLoader;
        self.getMappedModels = getMappedModels;
        self.checkMappedModelVisId = checkMappedModelVisId;
        self.getMappedModel = getMappedModel;
        self.createEmptyMappedModel = createEmptyMappedModel;
        self.getExternalCompaniesFromServer = getExternalCompaniesFromServer;
        self.getExternalLedgersFromServer = getExternalLedgersFromServer;
        self.getModelSuggestedAndDimensionsFromServer = getModelSuggestedAndDimensionsFromServer;
        self.getDataTypesDetails = getDataTypesDetails;
        self.save = save;
        self.importSafe = importSafe;
        self.importNotSafe = importNotSafe;
        self.deleteModelMapping = deleteModelMapping;
        self.getActions = getActions;
        self.setSelectedModel = setSelectedModel;
        self.mutiImportSafe = mutiImportSafe;
        self.getTaskStatus = getTaskStatus; 
        self.getTaskTime = getTaskTime; 
        self.deleteFailedTask = deleteFailedTask;
        activate();

        function activate() {
            loader = {
                mappedModelsLoaded: false,
                mappedModelsLoading: false,
                mappedModelDetailsLoaded: false,
                externalCompaniesLoaded: false,
                externalLedgersLoaded: false,
                externalModelSuggestedAndDimensionsLoaded: false,
                dimensionElementsLoaded: false,
                calendarLoaded: false,
                financeCubesLoaded: false
            };
            actions = [
            {
            	name:"Muliple Import",
            	action:"mutiImportSafe",
            	disabled:true
            },{
                name: "Import",
                action: "importSafe",
                disabled: true
            }, {
                name: "Import (skip validation)",
                action: "importNotSafe",
                disabled: true
            }, {
                name: "Delete",
                action: "deleteModelMapping",
                disabled: true
            }];
            
            
            
        }

        /**
         * Clear list of Models, reload FinanceCubeModels.
         */
        function clearCache() {
            loader.mappedModelsLoaded = false;
            loader.mappedModelsLoading = false;

            ModelService.clearCache();
        }

        /**
         * Returns an object containing information about the status of loading data.
         */
        function getLoader() {
            return loader;
        }

        function clearLoader() {
            // clear previous validation messages
            angular.forEach(loader, function(message, field) {
                loader[field] = false;
            });
        }

        /**
         * Get list of Mapped Models.
         */
        function getMappedModels(hardReload) {
            if ((!loader.mappedModelsLoaded && !loader.mappedModelsLoading) || hardReload) {
                getMappedModelsFromServer();
                
            }
            return mappedModels;
        }

        /**
         * Check mapped model name. Return true if not exists.
         */
        function checkMappedModelVisId(mappedModelVisId) {
            if (mappedModels.sourceCollection) {
                angular.forEach(mappedModels.sourceCollection, function(item) {
                    if (item.mappedModelVisId === mappedModelVisId) {
                        return false;
                    }
                });
            } else {
                return false;
            }
            return true;
        }

        /**
         * Requests database for list of Models.
         */
        var getMappedModelsFromServer = function() {
            loader.mappedModelsLoaded = false;
            loader.mappedModelsLoading = true;

            $http.get(url + "modelMappings").success(function(data) {
                mappedModels.sourceCollection = data;
                
                if (selectedModel != undefined && selectedModel != null ){
                setSelectedModel(selectedModel);
                }
                loader.mappedModelsLoaded = true;
                loader.mappedModelsLoading = false;
            });
        };

        /**
         * Gets details
         */
        function getMappedModel(selectedMappedModelId, global) {
            self.clearLoader();
            var mappedModelDetails = {};

            $http.get(url + "modelMappings/" + selectedMappedModelId).success(function(data) {
        	data.global = global; // quick fix
                angular.copy(data, mappedModelDetails);
                loader.mappedModelDetailsLoaded = true;
            });
            return mappedModelDetails;
        }

        function createEmptyMappedModel() {
            self.clearLoader();
            var mappedModelDetails = {
                mappedModelId: -1,
                global: false
            };
            loader.mappedModelDetailsLoaded = true;
            return mappedModelDetails;
        }

        /**
         * [getExternalCompaniesFromDatabase description]
         */
        var externalCompanies = [];

        function getExternalCompaniesFromServer(externalRequest) {
            if (loader.mappedModelDetailsLoaded === false) {
                return;
            }
            loader.externalCompaniesLoaded = false;
            loader.externalLedgersLoaded = false;
            loader.externalModelSuggestedAndDimensionsLoaded = false;
            loader.dimensionElementsLoaded = false;
            loader.calendarLoaded = false;
            loader.financeCubesLoaded = false;

            externalCompanies.length = 0;
            $http.post(url + "modelMappings/externalCompanies", externalRequest).success(function(data) {
                if (loader.mappedModelDetailsLoaded === true) {
                    angular.copy(data, externalCompanies);
                    loader.externalCompaniesLoaded = true;
                }
            });
            return externalCompanies;
        }

        /**
         * [getExternalLedgersFromDatabase description
         */
        var externalLedgers = [];

        function getExternalLedgersFromServer(ledgerRequest) {
            if (loader.mappedModelDetailsLoaded === false && loader.externalCompaniesLoaded === false) {
                return;
            }
            loader.externalLedgersLoaded = false;
            loader.externalModelSuggestedAndDimensionsLoaded = false;
            loader.dimensionElementsLoaded = false;
            loader.calendarLoaded = false;
            loader.financeCubesLoaded = false;

            externalLedgers.length = 0;
            $http.post(url + "modelMappings/externalLedgers", ledgerRequest).success(function(data) {
                if (loader.mappedModelDetailsLoaded === true && loader.externalCompaniesLoaded === true) {
                    angular.copy(data, externalLedgers);
                    loader.externalLedgersLoaded = true;
                }
            });
            return externalLedgers;
        }

        /**
         * [getModelSuggestedAndDimensionsFromServer description]
         */
        var externalModelSuggestedAndDimensions = {};

        function getModelSuggestedAndDimensionsFromServer(dimensionRequest) {
            if (loader.mappedModelDetailsLoaded === false && loader.externalCompaniesLoaded === false && loader.externalLedgersLoaded === false) {
                return;
            }
            loader.externalModelSuggestedAndDimensionsLoaded = false;
            loader.dimensionElementsLoaded = false;
            loader.calendarLoaded = false;
            loader.financeCubesLoaded = false;

            $http.post(url + "modelMappings/externalDimensions", dimensionRequest).success(function(data) {
                if (loader.mappedModelDetailsLoaded === true && loader.externalCompaniesLoaded === true && loader.externalLedgersLoaded === true) {
                    angular.copy(data, externalModelSuggestedAndDimensions);
                    loader.externalModelSuggestedAndDimensionsLoaded = true;
                }
            });
            return externalModelSuggestedAndDimensions;
        }

        var dataTypes = [];
        function getDataTypesDetails() {
            dataTypes.length = 0;
            $http.get(url + "modelMappings/dataTypes").success(function(data) {
                angular.copy(data, dataTypes);
            });
            return dataTypes;
        }

        /*
         * Save Mapped Model.
         */
        function save(mappedModelDetails) {
            var insert;
            if (mappedModelDetails === null) {
                return;
            }
            
            var testSpec = mappedModelDetails.mappedCalendar.years[0].spec;
            if (testSpec[0] === false && testSpec[1] === false && testSpec[2] === false && testSpec[3] === false && testSpec[4] === false && testSpec[5] === false && testSpec[6] === false && testSpec[7] === false && testSpec[8] === false && testSpec[9] === false) {
                swal("Error!", "Error while reading data! Please try again!", "warning");
                self.clearCache();
                $rootScope.$broadcast('ModelMappingsService:modelMappingSaved', {success: true});
                return;
            }
            
            var method;
            if (mappedModelDetails.mappedModelId != -1) {
                method = "PUT";
                insert = false;
            } else {
                method = "POST";
                insert = true;
            }
            $http({
                method: method,
                url: url + "modelMappings",
                data: mappedModelDetails
            }).success(function(response) {
                var dataToReturn = response;
                // to response object add information if it was PUT or POST action
                dataToReturn.method = method;
                onSaveResponse(dataToReturn, mappedModelDetails, insert);
            });
        }

        /**
         * Methods broadcasts results of saving budget cycles
         */
        var onSaveResponse = function(data, mappedModelDetails, insert) {
            var messageToReturn;
            if (data.success) {
                self.clearCache();
                if (insert){
                    getMappedModelsFromServer();
                } else {
                var mappedModelChange = CoreCommonsService.findElementByKey(mappedModels.sourceCollection, mappedModelDetails.mappedModelId, 'mappedModelId');
                mappedModelChange.mappedModelVisId = mappedModelDetails.mappedModelVisId;
                mappedModelChange.externalSystemVisId = mappedModelDetails.externalSystemVisId;
                mappedModelChange.mappedModelDescription = mappedModelDetails.mappedModelDescription;
                mappedModelChange.companies = mappedModelDetails.companies;
                mappedModelChange.ledgerVisId = mappedModelDetails.ledgerVisId;
                mappedModelChange.global = mappedModelDetails.global;
                mappedModels.refresh();
                }
                
                messageToReturn = 'Model Mapping (' + mappedModelDetails.mappedModelVisId + ') was saved.';
                Flash.create('success', messageToReturn);

                $rootScope.$broadcast('ModelMappingsService:modelMappingSaved', data);
            } else if (data.error) {
                messageToReturn = data.message.split("\n")[0];
                Flash.create('danger', messageToReturn);

                $rootScope.$broadcast('ModelMappingsService:modelMappingSaveError', data);
            }
        };

        function importSafe(mappedModel,callback) {
            if (mappedModel === null) {
                return;
            }
            $http({
                method: "POST",
                url: url + "modelMappings/importSafe",
                data: mappedModel
            }).success(function(response) {
                var messageToReturn = "Import Task = '" + response + "'' issued for mapped model = '" + mappedModel.mappedModelVisId + "'.";
                Flash.create('success', messageToReturn);
                if(callback)
                	callback(response);
            });
        }

        function importNotSafe(mappedModel) {
            if (mappedModel === null) {
                return;
            }
            $http({
                method: "POST",
                url: url + "modelMappings/importNotSafe",
                data: mappedModel
            }).success(function(response) {
                var messageToReturn = "Import Task = '" + response + "'' issued for mapped model = '" + mappedModel.mappedModelVisId + "'.";
                Flash.create('success', messageToReturn);
            });
        }
        function mutiImportSafe(mappedModel,callback){
            if (mappedModel === null) {
                return;
            }
            $http({
                method: "POST",
                url: url + "modelMappings/multiImportSafe",
                data: mappedModel
            }).success(function(response) {
                var messageToReturn = "Import Task = '" + response + "'' issued for mapped model = '" + mappedModel.mappedModelVisId + "'.";
                Flash.create('success', messageToReturn);
                if(callback)
                	callback(response);
            });
        }
        function getTaskStatus(taskId,callback){
        	$http({
        		method:"GET",
        		url:url+"modelMappings/getTaskStatus/"+taskId
        	}).success(function(response){
        		callback(response);
        	})
        }
        function getTaskTime(taskId,callback){
        	$http({
        		method:"GET",
        		url:url+"modelMappings/getTaskTime/"+taskId
        	}).success(function(response){
        		callback(response);
        	})
        }

        /**
         * Delete selected Mapped Model.
         *
         * @param {[type]} selectedMappedModelId [description]
         */
        function deleteModelMapping(mappedModel) {
            if (mappedModel === null) {
                return;
            }
            $http({
                method: "POST",
                url: url + "modelMappings/delete",
                data: mappedModel
            }).success(function(response) {
                var messageToReturn;
                if (response.success) {
                    self.clearCache();
                    getMappedModelsFromServer();
                    messageToReturn = "Mapped Model = " + mappedModel.mappedModelVisId + " deleted.";
                    Flash.create('success', messageToReturn);
                } else if (response.error) {
                    messageToReturn = response.message.split("\n")[0];
                    Flash.create('danger', messageToReturn);
                }
            });
        }

        function setSelectedModel(model){
            
            selectedModel = model;
            if (selectedModel != null){
                for (var i=0;i<mappedModels.sourceCollection.length;i++){
                    if (mappedModels.sourceCollection[i].modelId == selectedModel.model.modelId){
                        mappedModels.moveCurrentTo(mappedModels.sourceCollection[i]);
                    }   
                }
            }
        }
        
        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
        function deleteFailedTask(taskId,callback){
        	$http({
        		method:"GET",
        		url:url+"modelMappings/deleteFailedTask/"+taskId
        	}).success(function(response){
        		if(callback)
        			callback(response);
        	})
        }
    }
})();