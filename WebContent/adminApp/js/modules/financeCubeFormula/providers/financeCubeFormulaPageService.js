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
        .module('adminApp.financeCubeFormulaPage')
        .service('FinanceCubeFormulaPageService', FinanceCubeFormulaPageService);

    /* @ngInject */
    function FinanceCubeFormulaPageService($rootScope, Flash, $http, $compile, CoreCommonsService) {

        var self = this;
        var isFinanceCubesLoaded = false;
        var isFinanceCubesLoading = false;
        var url = $BASE_PATH + 'adminPanel/financeCubeFormula';
        var financeCubes = new wijmo.collections.CollectionView();
        var actions = {};
        self.clearCache = clearCache;
        self.getFinanceCubeFormula = getFinanceCubeFormula;
        self.compileFormula = compileFormula;
        self.getDimensionsHeader = getDimensionsHeader;
        self.getFinanceCubeFormulaDetailsFromDatabase = getFinanceCubeFormulaDetailsFromDatabase;
        self.createEmptyFinanceCube = createEmptyFinanceCube;
        self.save = save;
        self.deleteFinanceCubeFormula = deleteFinanceCubeFormula;
        self.getActions = getActions;
        activate();
        
        function activate(){
            actions = [{
                name: "Delete",
                action: "deleteFinanceCubeFormula",
                disabled: true
            }];
         }
        
        /**
         * Clear list of Finance Cubes Formula.
         */
        function clearCache() {
            isFinanceCubesLoaded = false;
            isFinanceCubesLoading = false;
            financeCubes = new wijmo.collections.CollectionView();
        }

        /**
         * Requests database for list of Finance Cubes Formula.
         */
        var getFinanceCubesFromDatabase = function() {
            isFinanceCubesLoaded = false;
            isFinanceCubesLoading = false;

            $http.get(url + "/").success(function(data) {
                isFinanceCubesLoaded = false;
                if (data && data.length >= 0) {
                    financeCubes.sourceCollection = data;
                };
            });
        };

        /**
         * Get list of Finance Cubes Formula.
         */
        function getFinanceCubeFormula() {
            // isFinanceCubesLoaded = true;
            if (!isFinanceCubesLoading && !isFinanceCubesLoaded) {
                getFinanceCubesFromDatabase();
            }
            return financeCubes;
        }

        /**
         * Get dimensions and hierarchies.
         * @param {[type]} modelId [description]
         */
        var currentModelDimensions = [];
        self.getDimensionsForModel = function(modelId) {
            currentModelDimensions.length = 0;
            var modelDimensionsWithHierarchies = [];
            if (modelDimensionsWithHierarchies[modelId]) {
                angular.copy(modelDimensionsWithHierarchies[modelId], currentModelDimensions);
            } else {
                $http.get(url + "/modelDimensionsWithHierarchies/" + modelId).success(function(data) {
                    if (data && data.length > 0) {
                        modelDimensionsWithHierarchies[modelId] = data;
                        angular.copy(modelDimensionsWithHierarchies[modelId], currentModelDimensions);
                    }
                });
            }
            return currentModelDimensions;
        };

        /**
         * Get compiled formula
         * @param  {[type]} selectedModelId              [description]
         * @param  {[type]} selectedFinanceCubeId        [description]
         * @param  {[type]} selectedFinanceCubeFormulaId [description]
         * @param  {[type]} formulaText                  [description]
         * @param  {[type]} type                         [description]
         * @return {[type]}                              [description]
         */
        function compileFormula(selectedModelId, selectedFinanceCubeId, selectedFinanceCubeFormulaId, formulaText, type) {
            var financeCubeFormulaDetails = {};
            if (formulaText === "") {
                return;
            }
            $http.get(url + "/" + selectedModelId + "/" + selectedFinanceCubeId + "/" + selectedFinanceCubeFormulaId + "/compile" + "/" + formulaText + "/" + type).success(function(data) {
                console.log("data = ", data);
                financeCubeFormulaDetails.formulaMessage = data;
            });
        }
        /**
         * Get Dimension headers For table
         * @param  {[type]} selectedModelId [description]
         * @return {[type]}                 [description]
         */
        function getDimensionsHeader(selectedModelId) {
            var financeCubeFormulaDetails = {};
            $http.get(url + "/" + selectedModelId + "/").success(function(data) {
                console.log("data = ", data);
                financeCubeFormulaDetails.dimensions = data;
            });
        }

        /**
         * Get details for selected Finance Cube Formula.
         * @param  {[type]} selectedModelId              [description]
         * @param  {[type]} selectedFinanceCubeId        [description]
         * @param  {[type]} selectedFinanceCubeFormulaId [description]
         * @return {[type]}                              [description]
         */
        function getFinanceCubeFormulaDetailsFromDatabase(selectedModelId, selectedFinanceCubeId, selectedFinanceCubeFormulaId) {
            var financeCubeFormulaDetails = {};
            $http.get(url + "/" + selectedModelId + "/" + selectedFinanceCubeId + "/" + selectedFinanceCubeFormulaId).success(function(data) {
                angular.copy(data, financeCubeFormulaDetails);
            });
            return financeCubeFormulaDetails;
        }

        /**
         * Create new FinanceCubeFormula object to return to directive and have ready object for scope and template.
         */
        function createEmptyFinanceCube() {
            var financeCubeFormulaDetails = {
                financeCubeFormulaId: -1,
                financeCubeFormulaVisId: "",
                model: {
                    modelId: -1,
                    modelVisId: ""
                },
                financeCube: {
                    financeCubeVisId: "",
                    financeCubeId: -1
                },
                deploymentInd: false,
                financeCubeFormulaDescription: "",
                formulaMessage: "",
                formulaText: "",
                type: 1,
                formulaLine: [],
                dimensions: [],
                financeCubeFormulaEnabled: false,

            };
            return financeCubeFormulaDetails;
        }


        /**
         * Save Finance Cube Formula
         */
        function save(financeCubeFormulaDetails) {
            var insert;
            if (financeCubeFormulaDetails === null) {
                return;
            }
            if (financeCubeFormulaDetails.financeCubeFormulaId != -1) {
                // edit FinanceCubeFormuula
                var method = "PUT";
                insert = false;
            } else {
                // create FinanceCubeFormuula
                var method = "POST";
                insert = true;
            }
            $http({
                method: method,
                url: url + "/",
                data: financeCubeFormulaDetails
            }).success(function(response) {
                if (response.success) {
                    if (insert){
                        getFinanceCubesFromDatabase();
                    } else {
                    var financeCubesChange = CoreCommonsService.findElementByKey(financeCubes.sourceCollection, financeCubeFormulaDetails.financeCubeFormulaId, 'financeCubeFormulaId');
                    financeCubesChange.model.modelVisId = financeCubeFormulaDetails.model.modelVisId;
                    financeCubesChange.financeCube.financeCubeVisId = financeCubeFormulaDetails.financeCube.financeCubeVisId;
                    financeCubesChange.financeCubeFormulaVisId = financeCubeFormulaDetails.financeCubeFormulaVisId;
                    financeCubesChange.financeCubeFormulaDescription = financeCubeFormulaDetails.financeCubeFormulaDescription;
                    financeCubesChange.type = parseInt(financeCubeFormulaDetails.type);
                    financeCubesChange.deployed = financeCubeFormulaDetails.deployed;
                    financeCubes.refresh();
                    }
                    var dataToReturn = response;
                    // to response object add information if it was PUT or POST action
                    dataToReturn.method = method;
                    $rootScope.$broadcast('FinanceCubeFormulaDetails:close', dataToReturn);
                } else if (response.error) {
                    $rootScope.$broadcast('FinanceCubeFormulaDetails:financeCubesUpdatedError', response);
                };
            });
        }


        /**
         * Delete selected Finance Cube Formula.
         */
        function deleteFinanceCubeFormula(selectedModelId, selectedFinanceCubeId, selectedFinanceCubeFormulaId, selectedFinanceCubeVisId, selectedFinanceCubeFormulaVisId) {
            $http.delete(url + "/" + selectedModelId + "/" + selectedFinanceCubeId + "/" + selectedFinanceCubeFormulaId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    getFinanceCubesFromDatabase();
                    Flash.create('success', "Finance Cube Formula \"" + selectedFinanceCubeFormulaVisId + "\" deleted.");
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