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
        .module('adminApp.hierarchiesPage')
        .service('HierarchiesService', HierarchiesService);

    /* @ngInject */
    function HierarchiesService($rootScope, $http, CoreCommonsService, ModelMappingsService) {

        var self = this;
        // BudgetInstructions
        var areHierarchiesLoaded = false;
        var areHierarchiesLoading = false;
        var url = $BASE_PATH + 'adminPanel/hierarchies/';
        var hierarchies = new wijmo.collections.CollectionView();
        var availableDimensionForInsert = [];
        var hierarchyDetails = {};
        var actions = [{
            name: "Delete",
            action: "deleteHierarchy",
            disabled: true
        }];
        self.dimension = {};
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.isPrintDisabled = true;
        self.isAskDisabled = false;
        // self.flag = true;
        self.clearCache = clearCache;
        self.save = save;
        self.getHierarchies = getHierarchies;
        self.getHierarchyDetails = getHierarchyDetails;
        self.getAvailableDimensionForInsert = getAvailableDimensionForInsert;
        self.findDimensionById = findDimensionById;
        self.deleteHierarchy = deleteHierarchy;
        self.getActions = getActions;
       
        /**
         * Clear list of Hierarchies.
         */
        function clearCache() {
            areHierarchiesLoaded = false;
            areHierarchiesLoading = false;
        };

        /**
         * Call for  all budget instruction
         */
        var getHierarhiesFromDatabase = function(type) {
            areHierarchiesLoaded = false;
            areHierarchiesLoading = true;
            $http.get(url + "/" + type).success(function(data) {
                areHierarchiesLoaded = true;
                areHierarchiesLoading = false;
                if (data && data.length >= 0) {
                    hierarchies.sourceCollection = data;
                }
            });
        };

        function save(hierarchy) {
            var insert;
            if (hierarchy === null) {
                return;
            }
            if (hierarchy.hierarchyId != -1 && hierarchy.hierarchyId != 0) {
                insert = false;
                var method = "PUT";
            } else {
                // create Dimension
                var method = "POST";
                insert = true;
            }
            if(hierarchy.type === true) {
                var type = true;
            } else {
                var type = false;
            }
            // Send request as PUT (for insert) or POST (for update)
            $http({
                method: method,
                url: url + type + "/",
                data: hierarchy
            }).success(function(response) {
                if (response.success) {
                    hierarchies.length = 0;
                    // refresh listing
                    if (insert){
                        getHierarhiesFromDatabase(hierarchy.type);
                    } else {
                    var hierarchyChange = CoreCommonsService.findElementByKey(hierarchies.sourceCollection, hierarchy.hierarchyId, 'hierarchyId');
                    hierarchyChange.hierarchyDescription = hierarchy.hierarchyDescription;
                    hierarchyChange.hierarchyVisId = hierarchy.hierarchyVisId;
                    hierarchies.refresh();
                    ModelMappingsService.setSelectedModel(hierarchyChange);
                    }
                    var dataToReturn = response;
                    dataToReturn.hierarchyId = hierarchy.hierarchyId;
                    // to response object add information if it was PUT or POST action
                    dataToReturn.method = method;
                    $rootScope.$broadcast('HierarchyDetails:close', dataToReturn);
                } else if (response.error) {
                    $rootScope.$broadcast('HierarchiesService:hierarchiesDetailsSaveError', response);
                };
            });
        };

        function getHierarchies(type) {

            getHierarhiesFromDatabase(type);

            return hierarchies;
        };



        function getHierarchyDetails(dimHierarchyId, hierarchyId, type,callback) {
            hierarchyDetails = {};
            $http.get(url + type + "/"  + dimHierarchyId + "/" + hierarchyId).success(function(response) {
                angular.copy(response, hierarchyDetails);
                callback();
            });
            return hierarchyDetails;
        };

        var getAvailableDimensionForInsertFromDatabase = function(type) {
            $http.get(url + "/availableDimensionForInsert/" + type).success(function(data) {

                if (data && data.length > 0) {
                    angular.copy(data, availableDimensionForInsert);

                }
            });
            // }, 10000);
        };
        function getAvailableDimensionForInsert(type) {

            getAvailableDimensionForInsertFromDatabase(type);
            console.log("availableDimensionForInsert", availableDimensionForInsert);
            return availableDimensionForInsert;

        };

        function findDimensionById(dimensionId) {
            var findedModel = null;
            if (!angular.isDefined(dimensionId)) {
                return findedModel;
            }
            if (availableDimensionForInsert.length != 0) {
                angular.forEach(availableDimensionForInsert, function(dimension) {
                    if (dimension.dimensionId === dimensionId) {
                        findedModel = dimension;

                        return;
                    }
                });
            }
            console.log("dimension", self.dimension);
            self.dimension.dimensionId = findedModel.dimensionId;
            self.dimension.dimensionVisId = findedModel.dimensionVisId;
            self.dimension.dimensionDescription = findedModel.dimensionDescription;
            return findedModel;
        }

        /**
         * Delete budget instruction
         */

        function deleteHierarchy(type, dimensionId, hierarchyId) {
            console.log("dim id ", dimensionId);
            console.log("hier id", hierarchyId);
            $http({
                method: 'DELETE',
                url: url + type + "/" + dimensionId + "/" + hierarchyId
            }).success(function(response) {
                if (response.success) {
                    // refresh listing
                    getHierarhiesFromDatabase(type);
                    $rootScope.$broadcast('HierarchiesService:budgetInstructionDetailsDeleteSuccess');

                } else if (response.error) {
                    $rootScope.$broadcast('HierarchiesService:budgetInstructionDetailsDeleteError', response);
                };

            });

        };

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        };
    }
})()