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
        .module('adminApp.budgetCycle')
        .service('BudgetCycleService', BudgetCycleService);

    function BudgetCycleService($rootScope, $http, Flash, CoreCommonsService) {

        var self = this;
        var isBudgetCyclesLoaded = false;
        var isBudgetCyclesLoading = false;
        var url = $BASE_PATH + 'adminPanel/budgetCycles';
        var budgetCycles = new wijmo.collections.CollectionView();
        var budgetCycleDetails = {};
        var statuses = {};
        var actions = {};
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.getBudgetCycle = getBudgetCycle;
        self.save = save;
        self.deleteBudget = deleteBudget;
        self.updatePeriodsForBudgetCycle = updatePeriodsForBudgetCycle;
        self.getBudgetCyclesForModel = getBudgetCyclesForModel;
        self.getXMLFormsForModel = getXMLFormsForModel;
        self.getStructureLevelDatesForModel = getStructureLevelDatesForModel;
        self.createEmptyBudgetCycle = createEmptyBudgetCycle;
        self.getBudgetCycles = getBudgetCycles;
        self.manageStatusName = manageStatusName;
        self.findStatusWithId = findStatusWithId;
        self.getStatuses = getStatuses;
        self.getActions = getActions;
        activate();

        function activate() {
            statuses = [{
                statusId: 0,
                label: "Initiated"
            }, {
                statusId: 1,
                label: "In Progress"
            }, {
                statusId: 2,
                label: "Complete"
            }, {
                statusId: 3,
                label: "Archived"
            }];

            actions = [{
                name: "Submit Budget State",
                action: "submit",
                disabled: true
            }, {
                name: "Submit Budget State Rebuild",
                action: "submitRebuild",
                disabled: true
            }, {
                name: "Delete",
                action: "deleteBudget",
                disabled: true
            }];
        }



        /**
         * Requests database for list of available budget cycles for user
         */
        var getBudgetCyclesFromDatabase = function() {
            isBudgetCyclesLoaded = false;
            isBudgetCyclesLoading = true;

            $http.get(url).success(function(data) {
                isBudgetCyclesLoaded = true;
                if (data && data.length > 0) {
                    budgetCycles.sourceCollection = data;
                }
            });
        };

        /**
         * Gets details for selected budget cycles.
         */
        function getBudgetCycle(budgetCycleId) {
            //force to empty data
            budgetCycleDetails = self.createEmptyBudgetCycle();

            var budgetCycle = CoreCommonsService.findElementByKey(budgetCycles.sourceCollection, budgetCycleId, 'budgetCycleId');
            if (budgetCycle !== null) {
                $http.get(url + '/' + budgetCycle.budgetCycleId + '/models/' + budgetCycle.model.modelId).success(function(data) {
                    angular.copy(data, budgetCycleDetails);
                });
            }
            return budgetCycleDetails;
        }

        /**
         * Saves whole object of budget cycles
         */
        function save(budgetCycle) {
            if (budgetCycle === null) return;

            if (budgetCycle.budgetCycleId == -1) {
                insert(budgetCycle);
            } else {
                update(budgetCycle);
            }
        }

        /**
         * Sends request to update budget cycle. On succes onSaveResponse is invoked
         */
        var update = function(budgetCycle) {
            $http.put(url, budgetCycle).success(function(response) {
                onSaveResponse(response,budgetCycle, false);
            });
        };

        /**
         * Sends request to insert a new budget cycle. On succes onSaveResponse is invoked
         */
        var insert = function(budgetCycle) {
            $http.post(url, budgetCycle).success(function(response) {
                onSaveResponse(response, budgetCycle, true);
            });
        };

        /**
         * Methods broadcasts results of saving budget cycles
         */
        var onSaveResponse = function(response, budgetCycle, insert) {
            var messageToReturn;
            if (response.success) {
                
                if (insert){
                    getBudgetCyclesFromDatabase();
                } else {
                var budgetCycleChange = CoreCommonsService.findElementByKey(budgetCycles.sourceCollection, budgetCycle.budgetCycleId, 'budgetCycleId');
                budgetCycleChange.budgetCycleDescription = budgetCycle.budgetCycleDescription;
                budgetCycleChange.budgetCycleVisId = budgetCycle.budgetCycleVisId;
                budgetCycleChange.status = budgetCycle.status;
                budgetCycleChange.category = budgetCycle.category;
                budgetCycleChange.periodFromVisId = budgetCycle.periodFromVisId;
                budgetCycleChange.periodToVisId = budgetCycle.periodToVisId;
                budgetCycles.refresh();
                }
                messageToReturn = 'Budget cycle (' + budgetCycleDetails.budgetCycleVisId + ') was saved.';
                Flash.create('success', messageToReturn);
                $rootScope.$broadcast('BudgetCycleService:budgetCycleSaved', response);
            } else if (response.error) {
                messageToReturn = response.message.split("\n")[0];
                Flash.create('danger', messageToReturn);

                $rootScope.$broadcast('BudgetCycleService:budgetCycleSaveError', response);
            }
        };

        /**
         * Sends request to delete budget cycle.
         */
        function deleteBudget(budgetCycleId, modelId) {
            if (budgetCycleId == -1 || modelId == -1) {
                return;
            }
            $http.delete(url + '/' + budgetCycleId + '/models/' + modelId).success(function(response) {
                var messageToReturn;

                if (response.success) {
                    getBudgetCyclesFromDatabase();
                    messageToReturn = 'Budget cycle (ID = ' + budgetCycleId + ') was deleted.';
                    Flash.create('success', messageToReturn);
                } else if (response.error) {
                    messageToReturn = response.title;
                    Flash.create('danger', messageToReturn);
                }
            });
        }

        /**
         * Methods returns function which is used to update periods for budget cycle
         */
        function updatePeriodsForBudgetCycle(budgetCycle) {
            return $http.post(url + '/period/update', budgetCycle);
        }

        /**
         * Get list of budget cycles related to model with modelId
         */
        function getBudgetCyclesForModel(modelId) {
            return $http.get(url + '/models/' + modelId);
        }

        /**
         * Get list of xmlForms related to model with modelId
         */
        function getXMLFormsForModel(modelId) {
            return $http.get(url + '/xmlforms/' + modelId);
        }

        /**
         * Get list of structure level end dates (list of timestamps which depends on modelId and planned end date)
         */
        function getStructureLevelDatesForModel(data) {
            return $http.post(url + '/structureLevelDates/', data);
        }

        /**
         * Create empty object of budget cycle
         */
        function createEmptyBudgetCycle() {
            budgetCycleDetails = {
                budgetCycleId: -1,
                budgetCycleVisId: "",
                budgetCycleDescription: "",
                model: {
                    modelId: -1,
                    modelVisId: ""
                },
                status: 0,
                periodFromId: 0,
                periodToId: 0,
                periodFromVisId: "",
                periodToVisId: "",
                defaultXmlFormId: 0,
                defaultXmlFormDataType: null,
                plannedEndDate: null,
                startDate: null,
                endDate: null,
                levelDates: [],
                xmlForms: []
            };
            return budgetCycleDetails;
        }

        /**
         * Get list of budget cycles. If not cached in service we send a request to database
         */
        function getBudgetCycles(hardReload) {
            if ((!isBudgetCyclesLoaded && !isBudgetCyclesLoading) || hardReload) {
                getBudgetCyclesFromDatabase();
            }
            return budgetCycles;
        }

        /**
         * Manage status name for specified statusId
         */
        function manageStatusName(statusId) {
            var status = self.findStatusWithId(statusId);
            if (status) {
                return status.label;
            }
            return "";
        }

        /**
         * Find status object in collection with specified statusId
         */
        function findStatusWithId(statusId) {
            var findedStatus = null;
            angular.forEach(statuses, function(status) {
                if (status.statusId === statusId) {
                    findedStatus = status;
                    return;
                }
            });
            return findedStatus;
        }

        /**
         * Get list of statuses
         */
        function getStatuses() {
            return statuses;
        }

        /**
         * Get list of actions which will used in submenu
         */
        function getActions() {
            return actions;
        }
    }
})();