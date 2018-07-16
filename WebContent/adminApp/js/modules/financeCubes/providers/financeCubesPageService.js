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
        .module('adminApp.financeCubesPage')
        .service('FinanceCubesPageService', FinanceCubesPageService);

    /* @ngInject */
    function FinanceCubesPageService($rootScope, Flash, $http, CoreCommonsService) {

        var self = this;
        var isFinanceCubesLoaded = false;
        var isFinanceCubesLoading = false;
        var url = $BASE_PATH + 'adminPanel/financeCubes';
        var financeCubes = new wijmo.collections.CollectionView();
        var financeCubeDetails = {};
        var actions = {};
        self.clearCache = clearCache; 
        self.getFinanceCubes = getFinanceCubes;
        self.getFinanceCubeDetailsFromDatabase = getFinanceCubeDetailsFromDatabase;
        self.createEmptyFinanceCube = createEmptyFinanceCube;
        self.getDimensionsForNewFinanceCubeFromDatabase = getDimensionsForNewFinanceCubeFromDatabase;
        self.save = save;
        self.deleteFinanceCube = deleteFinanceCube;
        self.getActions = getActions;
        activate();
        
        function activate(){
            financeCubeDetails = {
                    financeCubeId: -1,
                    financeCubeVisId: "",
                    model: {
                        modelId: 0,
                        modelVisId: "test"
                    },
                    financeCubeDescription: "",
                    submitChangeManagement: true,
                    aggregatedDataTypes: null,
                    audited: false,
                    changeManagementOutstanding: false,
                    cubeFormulaEnabled: false,
                    dataTypes: [],
                    dimensions: [],
                    hasData: false,
                    insideChangeManagement: true,
                    internalKey: "",
                    lockedByTaskId: -1,
                    mappedDataTypes: [],
                    rollUpRuleLines: []
                };

                actions = [{
                    name: "Delete",
                    action: "deleteFinanceCube",
                    disabled: true
                }];
         }
        
        /**
         * Clear list of Finance Cubes.
         */
        function clearCache() {
            isFinanceCubesLoaded = false;
            isFinanceCubesLoading = false;
        }

        /**
         * Requests database for list of Finance Cubes.
         */
        var getFinanceCubesFromDatabase = function() {
            isFinanceCubesLoaded = false;
            isFinanceCubesLoading = true;

            $http.get(url).success(function(data) {
                isFinanceCubesLoaded = false;
                if (data && data.length >= 0) {
                    financeCubes.sourceCollection = data;
                };
            });
        };

        /**
         * Get list of Finance Cubes.
         */
        function getFinanceCubes(hardReload) {
            if ((!isFinanceCubesLoaded && !isFinanceCubesLoading) || hardReload) {
                getFinanceCubesFromDatabase();
            }
            return financeCubes;
        }

        /**
         * Gets details for selected Finance Cube.
         *
         * @param {[type]} financeCubeId [description]
         */
        function getFinanceCubeDetailsFromDatabase(selectedModelId, selectedFinanceCubeId) {
            financeCubeDetails = {};
            $http.get(url + "/" + selectedModelId + "/" + selectedFinanceCubeId).success(function(data) {
                angular.copy(data, financeCubeDetails);
            });
            return financeCubeDetails;
        }

        /**
         * Create new FinanceCube object to return to directive and have ready object for scope and template.
         */
        function createEmptyFinanceCube() {
            financeCubeDetails = {
                financeCubeId: -1,
                financeCubeVisId: "",
                model: {
                    modelId: 0,
                    modelVisId: "test"
                },
                financeCubeDescription: "",
                submitChangeManagement: true,
                aggregatedDataTypes: null,
                audited: false,
                changeManagementOutstanding: false,
                cubeFormulaEnabled: false,
                dataTypes: [],
                dimensions: [],
                hasData: false,
                insideChangeManagement: true,
                internalKey: "",
                lockedByTaskId: -1,
                mappedDataTypes: [],
                rollUpRuleLines: []
            };
            return financeCubeDetails;
        }

        /**
         * Get list of Dimensions fit to Model FinanceCube's.
         */
        function getDimensionsForNewFinanceCubeFromDatabase(selectedModelId) {
            $http.get(url + "/dimensions/" + selectedModelId).success(function(data) {
                financeCubeDetails.dimensions = data;
            });
        }

        /**
         * Save Finance Cube
         */
        function save(financeCubeDetails) {
            var insert;
            if (financeCubeDetails === null) {
                return;
            }
            if (financeCubeDetails.financeCubeId != -1) {
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
                url: url,
                data: financeCubeDetails
            }).success(function(response) {
                if (response.success) {
                    if (insert){
                        getFinanceCubesFromDatabase();
                    } else {
                    var financeCubesChange = CoreCommonsService.findElementByKey(financeCubes.sourceCollection, financeCubeDetails.financeCubeId, 'financeCubeId');
                    financeCubesChange.model.modelVisId = financeCubeDetails.model.modelVisId;
                    financeCubesChange.financeCubeVisId = financeCubeDetails.financeCubeVisId;
                    financeCubesChange.financeCubeDescription = financeCubeDetails.financeCubeDescription;
                    financeCubes.refresh();
                    }
                    var dataToReturn = response;
                    // to response object add information if it was PUT or POST action
                    dataToReturn.method = method;
                    $rootScope.$broadcast('FinanceCubeDetails:close', dataToReturn);
                } else if (response.error) {
                    $rootScope.$broadcast('FinanceCubePageService:financeCubesUpdatedError', response);
                };
            });
        }

        /**
         * Delete selected Finance Cube.
         */
        function deleteFinanceCube(selectedModelId, selectedFinanceCubeId, selectedFinanceCubeVisId) {
            $http.delete(url + "/" + selectedModelId + "/" + selectedFinanceCubeId).success(function(data) {
                if (data.success == false) {
                    Flash.create('danger', data.message);
                } else {
                    getFinanceCubesFromDatabase();
                    Flash.create('success', "Finance Cube = " + selectedFinanceCubeVisId + " deleted.");
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