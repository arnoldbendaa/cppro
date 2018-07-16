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
        .module('adminApp.externalSystem')
        .service('ExternalSystemService', ExternalSystemService);

    /* @ngInject */
    function ExternalSystemService($rootScope, $http, Flash, CoreCommonsService) {

        var self = this;
        var loader = {};
        var url = $BASE_PATH + 'adminPanel/externalSystems';
        var externalSystems = new wijmo.collections.CollectionView();
        // var externalSystems = [];
        var externalSystemDetails = {};
        var actions = {};
        self.isCreateDisabled = false;
        self.isOpenDisabled = true;
        self.getLoader = getLoader;
        self.getExternalSystems = getExternalSystems;
        self.getExternalSystemDetails = getExternalSystemDetails;
        self.getSubSystemConfiguration = getSubSystemConfiguration;
        self.save = save;
        self.deleteExternalSystem = deleteExternalSystem;
        self.getActions = getActions;
        self.createEmptyExternalSystem = createEmptyExternalSystem;
        activate();
        
        function activate(){
            loader = {
                    externalSystemsLoaded: false,
                    externalSystemsLoading: false
                };
            actions = [{
                name: "Delete",
                action: "deleteExternalSystem",
                disabled: true
            }];
            
        }

        /**
         * Returns an object containing information about the status of loading data.
         */
        function getLoader() {
            return loader;
        }

        /**
         * [getExternalSystems description]
         */
        function getExternalSystems(hardReload) {
            if ((!loader.externalSystemsLoaded && !loader.externalSystemsLoading) || hardReload) {
                getExternalSystemsFromDatabase();
            }
            return externalSystems;
        }

        /**
         * Requests database for list of available external systems
         */
        var getExternalSystemsFromDatabase = function() {
            loader.externalSystemsLoaded = false;
            loader.externalSystemsLoading = true;
            $http.get(url).success(function(data) {
                externalSystems.sourceCollection = data;
                loader.externalSystemsLoaded = true;
                loader.externalSystemsLoading = false;
            });
        };

        /**
         * Get details of one External System (get always from database to have the newest version).
         * @param  {Integer} externalSystemId
         */
        function getExternalSystemDetails(externalSystemId) {
            externalSystemDetails = self.createEmptyExternalSystem();

            if (externalSystemId != -1) {
                $http.get(url + "/" + externalSystemId).success(function(data) {
                    angular.copy(data, externalSystemDetails);
                });
            }
            return externalSystemDetails;
        }

        /**
         * Get details of one External System(get always from database to have the newest version).
         * @param  {Integer} externalSystemId
         */
        function getSubSystemConfiguration(subsystemId) {
            var subSystem = {};
            if (externalSystemId != -1) {
                $http.get(url + "/subSystemConfiguration/" + subsystemId).success(function(response) {
                    angular.copy(response, subSystem);
                });
            }
            return subSystem;
        }

        /**
         * Saves whole object of external system
         */
        function save(externalSystem) {
            if (externalSystem === null) return;

            if (externalSystem.externalSystemId == -1) {
                insert(externalSystem);
            } else {
                update(externalSystem);
            }
        }

        /**
         * Sends request to update external system. On succes onSaveResponse is invoked
         */
        var update = function(externalSystem) {
            $http.put(url + "/" + externalSystem.externalSystemId, externalSystem).success(function(response) {
                onSaveResponse(response, externalSystem, false);
            });
        };

        /**
         * Sends request to insert a new external system. On succes onSaveResponse is invoked
         */
        var insert = function(externalSystem) {
            $http.post(url, externalSystem).success(function(response) {
                onSaveResponse(response, externalSystem, true);
            });
        };

        /**
         * Methods broadcasts results of saving budget cycles
         */
        var onSaveResponse = function(response, externalSystem, insert) {
            var messageToReturn;
            if (response.success) {
                if (insert){
                    getExternalSystemsFromDatabase();
                } else {
                var externalChange = CoreCommonsService.findElementByKey(externalSystems.sourceCollection, externalSystem.externalSystemId, 'externalSystemId');
                externalChange.externalSystemVisId = externalSystem.externalSystemVisId;
                externalChange.externalSystemDescription = externalSystem.externalSystemDescription;
                externalChange.systemType = externalSystem.systemType;
                externalChange.enabled = externalSystem.enabled;
                externalChange.location = externalSystem.location;
                externalSystems.refresh();
                }
                messageToReturn = 'External System "' + externalSystem.externalSystemVisId + '" was saved.';
                Flash.create('success', messageToReturn);

                $rootScope.$broadcast('ExternalSystemService:externalSystemSaved', response);
            } else if (response.error) {
                messageToReturn = response.message.split("\n")[0];
                Flash.create('danger', messageToReturn);

                $rootScope.$broadcast('ExternalSystemService:externalSystemSaveError', response);
            }
        };

        /**
         * Sends request to delete budget cycle.
         */
        function deleteExternalSystem(selectedExternalSystemId, selectedExternalSystemVisId) {
            if (selectedExternalSystemId == -1) {
                return;
            }
            $http.delete(url + "/" + selectedExternalSystemId).success(function(response) {
                var messageToReturn;
                if (response.success) {
                    getExternalSystemsFromDatabase();
                    messageToReturn = 'External system "' + selectedExternalSystemVisId + '" was deleted.';
                    Flash.create('success', messageToReturn);
                } else if (response.error) {
                    messageToReturn = response.title;
                    Flash.create('danger', messageToReturn);
                }
            });
        }

        /**
         * Get list of actions which will used in submenu
         */
        function getActions() {
            return actions;
        }


        /**
         * Create empty object of budget cycle
         */
        function createEmptyExternalSystem() {
            externalSystemDetails = {
                externalSystemId: -1,
                externalSystemVisId: "",
                externalSystemDescription: "",
                systemType: "Generic",
                location: "",
                enabled: false,
                connectorClass: "",
                importSource: "",
                exportTarget: "",
                versionNum: 0,
                subSystems: []
            };
            return externalSystemDetails;
        }
    }
})();