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
        .module('flatFormTemplateApp.commons')
        .service('DataService', DataService);

    function DataService($rootScope, $timeout, $http) {

        var self = this;
        /*---------------------------------get Models--------------------------------------------------------*/
        var models = [];
        var modelsWithEmptyElement = [];
        var areModelsLoaded = false;
        var areModelsLoading = false;
        self.getModels = getModels;
        self.getDataTypes = getDataTypes;
        self.getFinanceCubes = getFinanceCubes;
        self.getDimensionsForModel = getDimensionsForModel;
        self.updateWorksheetPropertyName = updateWorksheetPropertyName;
        self.deleteWorksheetProperties = deleteWorksheetProperties;
        self.insertWorksheetProperties = insertWorksheetProperties;
        self.setDocumentTitle = setDocumentTitle;
        self.getHeadParameters = getHeadParameters;
        
        /**
         * Requests database for list of Models.
         */
        var getModelsFromDatabase = function() {
            areModelsLoaded = false;
            areModelsLoading = true;

            //$timeout(function() { // do celów testowych
            $http.get($BASE_PATH + "flatFormEditor/models").success(function(data) {
                areModelsLoaded = true;
                areModelsLoading = false;
                if (data && data.length > 0) {
                    models = data;
                    modelsWithEmptyElement = angular.copy(models);
                    modelsWithEmptyElement.unshift({
                        modelId: -1,
                        modelVisId: "",
                        modelDescription: null,
                        global: false
                    });
                }
            });
            // }, 10000);
        };
        /**
         * Get list of Models.
         */
        function getModels(withEmptyElement) {
            if (!areModelsLoaded && !areModelsLoading) {
                getModelsFromDatabase();
            }
            if (typeof withEmptyElement != "undefined" && withEmptyElement) {
                return modelsWithEmptyElement;
            } else {
                return models;
            }
        };
        /*---------------------------------------------------------------------------------------------------*/

        /*---------------------------------get DataTypes-----------------------------------------------------*/
        var dataTypes = [];
        var dataTypesWithEmptyElement = [];
        var areDataTypesLoaded = false;
        var areDataTypesLoading = false;

        /**
         * Requests database for list of DataTypes.
         */
        var getDataTypesFromDatabase = function() {
            areDataTypesLoaded = false;
            areDataTypesLoading = true;

            //$timeout(function() { // do celów testowych
            $http.get($BASE_PATH + "flatFormEditor/dataTypes").success(function(data) {
                areDataTypesLoaded = true;
                areDataTypesLoading = false;
                if (data && data.length > 0) {
                    dataTypes = data;
                    dataTypesWithEmptyElement = angular.copy(dataTypes);
                    dataTypesWithEmptyElement.unshift({
                        dataTypeId: -1,
                        dataTypeVisId: "",
                        dataTypeDescription: null
                    });
                }
            });
            // }, 10000);
        };
        /**
         * Get list of DataTypes.
         */
        function getDataTypes(withEmptyElement) {
            if (!areDataTypesLoaded && !areDataTypesLoading) {
                getDataTypesFromDatabase();
            }
            if (typeof withEmptyElement != "undefined" && withEmptyElement) {
                return dataTypesWithEmptyElement;
            } else {
                return dataTypes;
            }
        };
        /*---------------------------------------------------------------------------------------------------*/

        /*---------------------------------get FinanceCubes--------------------------------------------------*/
        var financeCubes = [];
        var areFinanceCubesLoaded = false;
        var areFinanceCubesLoading = false;

        /**
         * Requests database for list of FinanceCubes.
         */
        var getFinanceCubesFromDatabase = function() {
            areFinanceCubesLoaded = false;
            areFinanceCubesLoading = true;

            //$timeout(function() { // do celów testowych
            $http.get($BASE_PATH + "flatFormEditor/financeCubes").success(function(data) {
                areFinanceCubesLoaded = true;
                areFinanceCubesLoading = false;
                if (data && data.length > 0) {
                    angular.copy(data, financeCubes);
                }
            });
            // }, 10000);
        };
        /**
         * Get list of FinanceCubes.
         */
        function getFinanceCubes() {
            if (!areFinanceCubesLoaded && !areFinanceCubesLoading) {
                getFinanceCubesFromDatabase();
            }
            return financeCubes;
        };

        /*---------------------------------------------------------------------------------------------------*/

        /*-------------------------------Dimensions with Hierarchies for Model-------------------------------*/
        var modelDimensionsWithHierarchies = {};
        var currentModelDimensions = [];

        /**
         * We get Dimensions with hierarchies by ajax and put each time in array.
         * If we ask about model which we asked before, we find its dimensions in local object and return to user.
         * If we didn't ask about model before, we send request, put response in local object and return to user.
         */
        function getDimensionsForModel(modelId) {
            currentModelDimensions.length = 0;
            if (modelDimensionsWithHierarchies[modelId]) {
                angular.copy(modelDimensionsWithHierarchies[modelId], currentModelDimensions);
            } else {
                $http.get($BASE_PATH + "flatFormEditor/modelDimensionsWithHierarchies/" + modelId).success(function(data) {
                    if (data && data.length > 0) {
                        modelDimensionsWithHierarchies[modelId] = data;
                        angular.copy(modelDimensionsWithHierarchies[modelId], currentModelDimensions);
                    }
                });
            }
            return currentModelDimensions;
        };

        /*---------------------------------------------------------------------------------------------------*/

        function updateWorksheetPropertyName(worksheet, newName) {
            worksheet.name = newName;
        };

        function deleteWorksheetProperties(worksheets, worksheet) {
            var index = worksheets.indexOf(worksheet);
            worksheets.splice(index, 1);
        };

        function insertWorksheetProperties(worksheets, newName) {
            worksheets.push({
                cells: [],
                name: newName,
                properties: {}
            });
        };

        /*-------------------------------------html head-----------------------------------------------------*/
        var headParameters = {
            documentTitle: ""
        }
        function setDocumentTitle(title) {
            headParameters.documentTitle = (title !== undefined && title.length > 0) ? " - " + title : "";
        }
        function getHeadParameters() {
            return headParameters;
        }
        /*---------------------------------------------------------------------------------------------------*/

    }
})();