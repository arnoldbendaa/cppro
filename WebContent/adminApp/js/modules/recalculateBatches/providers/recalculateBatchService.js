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
        .module('adminApp.recalculateBatch')
        .service('RecalculateBatchService', RecalculateBatchService);

    /* @ngInject */
    function RecalculateBatchService($rootScope, $http, Flash, CoreCommonsService) {

        var self = this;
        var isRecalculateBatchesLoaded = false;
        var isRecalculateBatchesLoading = false;
        var url = $BASE_PATH + 'adminPanel/recalculateBatches';
        var recalculateBatches = new wijmo.collections.CollectionView();
        var recalculateBatchDetails = {};
        var actions = [{
            name: "Submit",
            action: "submit",
            disabled: true
        }, {
            name: "Delete",
            action: "deleteRecalculateBatch",
            disabled: true
        }];
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.getRecalculateBatches = getRecalculateBatches;
        self.getRecalculateBatch = getRecalculateBatch;
        self.getProfilesFromDatabase = getProfilesFromDatabase;
        self.submit = submit;
        self.createEmptyRecalculateBatch = createEmptyRecalculateBatch;
        self.save = save;
        self.deleteRecalculateBatch = deleteRecalculateBatch;
        self.getActions = getActions;

        function getRecalculateBatches(hardReload) {
            if ((!isRecalculateBatchesLoaded && !isRecalculateBatchesLoading) || hardReload) {
                getRecalculateBatchesFromDatabase();
            }
            return recalculateBatches;
        }

        var getRecalculateBatchesFromDatabase = function() {
            isRecalculateBatchesLoading = true;
            $http.get(url).success(function(data) {
                isRecalculateBatchesLoaded = true;
                if (data && data.length > 0) {
                    recalculateBatches.sourceCollection = data;
                }
            });
        };

        function getRecalculateBatch(recalculateBatchId) {
            recalculateBatchDetails = {};

            var recalculateBatch = CoreCommonsService.findElementByKey(recalculateBatches.sourceCollection, recalculateBatchId, 'recalculateBatchId');
            if (recalculateBatch != null) {
                $http.get(url + '/' + recalculateBatchId).success(function(data) {
                    angular.copy(data, recalculateBatchDetails);
                });
            }
            return recalculateBatchDetails;
        }

        function getProfilesFromDatabase(recalculateBatchId, modelId, budgetCycleId) {
            return $http.get(url + '/' + recalculateBatchId + "/models/" + modelId + "/budgetCycles/" + budgetCycleId + "/profiles");
        }

        function submit(recalculateBatchId) {
            if (recalculateBatchId == -1) {
                return;
            }

            var recalculateBatchToSubmit = CoreCommonsService.findElementByKey(recalculateBatches.sourceCollection, recalculateBatchId, 'recalculateBatchId');
            if (recalculateBatchToSubmit === null) return;
            $http.post(url + "/submit", recalculateBatchToSubmit).success(function(response) {
                var data = {
                    taskNumber: response,
                    objectToSubmit: recalculateBatchToSubmit
                };
                var messageToReturn = 'Recalculate batch (ID = ' + recalculateBatchId + ') was submitted.';
                Flash.create('success', messageToReturn);
            });
        }

        function createEmptyRecalculateBatch() {
            recalculateBatchDetails = {
                recalculateBatchId: -1,
                recalculateBatchVisId: "",
                recalculateBatchDescription: "",
                model: {
                    modelId: -1,
                    modelVisId: ""
                },
                budgetCycleId: -1,
                responsibilityAreas: [],
                profiles: []
            };
            return recalculateBatchDetails;
        }

        function save(recalculateBatch) {
            if (recalculateBatch === null) return;

            if (recalculateBatch.recalculateBatchId == -1) {
                insert(recalculateBatch);
            } else {
                update(recalculateBatch);
            }
        }

        var update = function(recalculateBatch) {
            $http.put(url, recalculateBatch).success(function(response) {
                onSaveResponse(response,recalculateBatch, false);
            });
        };

        var insert = function(recalculateBatch) {
            $http.post(url, recalculateBatch).success(function(response) {
                onSaveResponse(response,recalculateBatch, true);
            });
        };

        var onSaveResponse = function(response,recalculateBatch, insert) {
            if (response.success) {
                
                if (insert){
                  getRecalculateBatchesFromDatabase();  
                } else {
                    var budgetCycleChange = CoreCommonsService.findElementByKey(recalculateBatches.sourceCollection, recalculateBatch.recalculateBatchId, 'recalculateBatchId');
                    budgetCycleChange.recalculateBatchVisId = recalculateBatch.recalculateBatchVisId;
                    budgetCycleChange.recalculateBatchDescription = recalculateBatch.recalculateBatchDescription;
                    budgetCycleChange.model.modelVisId = recalculateBatch.model.modelVisId + " - " + recalculateBatch.model.modelDescription;
                    recalculateBatches.refresh();
                }
                var messageToReturn = 'Recalculate batch (' + recalculateBatchDetails.recalculateBatchVisId + ') was saved.';
                Flash.create('success', messageToReturn);

                $rootScope.$broadcast('RecalculateBatchService:recalculateBatchSaved', response);
            } else if (response.error) {
                var messageToReturn = response.message.split("\n")[0];
                Flash.create('danger', messageToReturn);

                $rootScope.$broadcast('RecalculateBatchService:recalculateBatchSaveError', response);
            };
        };

        function deleteRecalculateBatch(recalculateBatchId) {
            if (recalculateBatchId == -1) {
                return;
            }
            $http.delete(url + '/' + recalculateBatchId).success(function(response) {
                if (response.success) {
                    getRecalculateBatchesFromDatabase();
                    var messageToReturn = 'Recalculate batch (ID = ' + recalculateBatchId + ') was deleted.';
                    Flash.create('success', messageToReturn);
                } else if (response.error) {
                    $scope.$on("RecalculateBatchService:recalculateBatchDeleteError", function(event, response) {
                        var messageToReturn = response.message.split("\n")[0];
                        Flash.create('danger', messageToReturn);
                    });

                };
            });
        }

        function getActions() {
            return actions;
        }
    }
})();