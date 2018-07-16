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
        .module('adminApp.dimension')
        .service('CalendarService', CalendarService);
    
    /* @ngInject */
    function CalendarService($rootScope, $http, Flash, CoreCommonsService) {

        var insert;
        var self = this;
        var areCalendarsLoaded = false;
        var areCalendarsLoading = false;
        var url = $BASE_PATH + 'adminPanel/dimensions/calendars';
        var calendars = new wijmo.collections.CollectionView();
        var calendarDetails = {};
        var calendarsForModel = {}; // hash map with calendars. models (visId) are keys of this hash map
        self.isCreateDisabled = false;
        self.isOpenDisabled = false;
        self.isPrintDisabled = true;
        self.isAskDisabled = true;
        self.clearCache = clearCache;
        self.getCalendars = getCalendars;
        self.getCalendarDetails = getCalendarDetails;
        self.createEmptyCalendar = createEmptyCalendar;
        self.save = save;
        self.deleteCalendar = deleteCalendar;
        self.getCalendarsForModelVisId = getCalendarsForModelVisId;
        self.managePeriodName = managePeriodName;
        self.getActions = getActions;
     
        var actions = [{
            name: "Delete",
            action: "deleteCalendar",
            disabled: true
        }];

        /**
         * Clear list of Calendar Dimensions.
         */
        function clearCache() {
            areCalendarsLoaded = false;
            areCalendarsLoading = false;
        }

        var getCalendarsFromDatabase = function() {
            areCalendarsLoaded = false;
            areCalendarsLoading = true;
            $http.get(url).success(function(data) {
                areCalendarsLoaded = true;
                areCalendarsLoading = false;
                if (data && data.length >= 0) {
                    calendars.sourceCollection = data;
                }
            });
        };

        /**
         * Return calendars if were taken, otherwise call getting Calendars from database.
         */
        function getCalendars(hardReload) {
            if ((!areCalendarsLoaded && !areCalendarsLoading) || hardReload) {
                getCalendarsFromDatabase();
            }
            return calendars;
        }

        /**
         * Get details of one Calendar (get always from database to have the newest version).
         * @param  {Integer} calendarId [description]
         * @param  {Integer} hierarchyId [description]
         */
        function getCalendarDetails(calendarId, hierarchyId) {
            calendarDetails = {};
            if (calendarId != -1) {
                $http.get(url + "/" + calendarId + "/" + hierarchyId).success(function(response) {
                    angular.copy(response, calendarDetails);
                });
            }
            return calendarDetails;
        }

        /**
         * Create new Dmension Calendar object to return to directive and have ready object for scope and template. Now "id" is always -1.
         * @param  {Integer} id
         */
        function createEmptyCalendar(id) {
            calendarDetails = {
                dimensionId: id, // now id=-1
                dimensionVisId: "",
                dimensionDescription: "",
                model: {
                    modelId: null,
                    modelVisId: "Unassigned",
                    modelDescription: "",
                },
                type: 3,
                hierarchy: {
                    hierarchyId: null, // TODO
                    hierarchyVisId: null, // TODO
                    hierarchyDescription: null
                },
                externalSystemRefName: "N/A",
                years: [],
                readOnly: false,
                inUseLabel: null,
                versionNum: -1,
                submitChangeManagementRequest: false,
            };
            return calendarDetails;
        }

        /**
         * Save calendar details. It's "insert" (when calendar.id is -1) or "update" otherwise.
         * @param  {Object} calendar [calendar details]
         */
        function save(calendar) {
            
            if (calendar === null) {
                return;
            }
            var method = "";
            if (calendar.dimensionId != -1) {
                // edit Dimension
                method = "PUT";
                insert = false;
            } else {
                // create Dimension
                method = "POST";
                insert = true;
            }
            // Send request as PUT (for insert) or POST (for update)
            $http({
                method: method,
                url: url + "/",
                data: calendar
            }).success(function(response) {
                onSaveResponse(response, method,calendar);
            });
        }

        /**
         * Methods broadcasts results of saving calendars
         */
        var onSaveResponse = function(response, method,calendar) {
            var messageToReturn;
            if (response.success) {
                if (insert){
                    getCalendarsFromDatabase();
                } else {
                var calendarChange = CoreCommonsService.findElementByKey(calendars.sourceCollection, calendar.dimensionId, 'dimensionId');
                calendarChange.model.modelVisId = calendar.model.modelVisId;
                calendarChange.dimensionVisId = calendar.dimensionVisId;
                calendarChange.dimensionDescription = calendar.dimensionDescription;
                calendarChange.hierarchy.hierarchyVisId = calendar.hierarchy.hierarchyVisId;
                calendars.refresh();
                }
                var operation = "";
                if (method == "POST") {
                    operation = "created";
                } else if (method == "PUT") {
                    operation = "updated";
                }

                messageToReturn = "Calendar (" + calendarDetails.dimensionVisId + ") is " + operation + ".";
                Flash.create('success', messageToReturn);

                $rootScope.$broadcast('CalendarService:calendarSaved', response);
            } else if (response.error) {
                messageToReturn = response.message.split("\n")[0];
                Flash.create('danger', messageToReturn);

                $rootScope.$broadcast('CalendarService:calendarSaveError', response);
            }
        };


        /**
         * Delete Dimension
         * @param  {Integer} dimensionId
         */
        function deleteCalendar(dimensionId, hierarchyId) {
            $http({
                method: 'DELETE',
                url: url + "/" + dimensionId + "/" + hierarchyId
            }).success(function(response) {
                if (response.success) {
                    calendars.length = 0;
                    // refresh listing
                    getCalendarsFromDatabase();
                    $rootScope.$broadcast('CalendarService:calendarDetailsDeleteSuccess');
                } else if (response.error) {
                    $rootScope.$broadcast('CalendarService:calendarDetailsDeleteError', response);
                }
            });
        }


        function getCalendarsForModelVisId(modelVisId) {
            if (modelVisId == "") {
                return calendarsForModel;
            }
            if (calendarsForModel[modelVisId] != undefined) {
                return calendarsForModel[modelVisId];
            } else {
                calendarsForModel[modelVisId] = [];
                getCalendarsForModelVisIdFromDatabase(modelVisId);
            }
            return calendarsForModel[modelVisId];
        }

        var getCalendarsForModelVisIdFromDatabase = function(modelVisId) {
            var data = {
                modelVisId: modelVisId
            };

            $http.get(url + '/modelVisId/', {
                params: data
            }).success(function(data) {
                if (data && data.length >= 0) {
                    angular.forEach(data, function(calendar) {
                        calendarsForModel[modelVisId].push(calendar);
                    });
                }
            });
        };

        function managePeriodName(modelVisId, periodId) {
            var name = "";
            if (calendarsForModel[modelVisId] == null || calendarsForModel[modelVisId].length == 0 || periodId == 0) {
                return name;
            }
            angular.forEach(calendarsForModel[modelVisId], function(calendar) {
                if (calendar.id === periodId) {
                    name = calendar.name;
                    return;
                }
            });
            return name;
        }

        function getActions() {
            return actions; 
        }
    }
})();