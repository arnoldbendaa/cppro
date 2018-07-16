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
        .module('adminApp.notesPage')
        .service('NotesPageService', NotesPageService);

    /* @ngInject */
    function NotesPageService($rootScope, $http) {

        var self = this;
        var notesLoader = {
            isNotesLoaded: false,
            isNotesLoading: false
        };
        var url = $BASE_PATH + 'adminPanel/notes';
        var notes = new wijmo.collections.CollectionView();
        var notesListForViewer = [];
        var actions = [{
            name: "Open profile",
            action: "openProfile",
            disabled: true,
        }, {
            name: "Show notes",
            action: "showNotes",
            disabled: true,
        }];
        self.isOpenDisabled = true;
        self.isCreateDisabled = true;
        self.isRefreshDisabled = true;
        self.getNotesFromService = getNotesFromService;
        self.getNotes = getNotes;
        self.getNotesForViewer = getNotesForViewer;
        self.openProfile = openProfile;
        self.getNotesLoader = getNotesLoader;
        self.getActions = getActions;

        /**
         * Return reference on list of Note.
         */
        function getNotesFromService() {
            return notes;
        }

        /**
         * Requests database for list of Notes.
         */
        function getNotes(costCenters, financeCubeIds, fromDate, toDate) {
            notesLoader.isNotesLoaded = false;
            notesLoader.isNotesLoading = true;

            $http.get(url + "/" + costCenters + "/" + financeCubeIds + "/" + fromDate + "/" + toDate).success(function(data) {
                notesLoader.isNotesLoaded = true;

                if (data && data.length >= 0) {
                    notes.sourceCollection = data;
                    return notes;
                }
            });
        }

        /**
         * Requests database for list of Notes.
         */
        function getNotesForViewer(costCenters, financeCubeIds, fromDate, toDate) {
            notesListForViewer = [];
            $http.get(url + "/" + costCenters + "/" + financeCubeIds + "/" + fromDate + "/" + toDate).success(function(data) {
                angular.copy(data, notesListForViewer);
            });
            return notesListForViewer;
        }

        /**
         * Open profile selected Note.
         */
        function openProfile(modelId, costCenter, financeCubeIds, date) {
            notesListForViewer = [];
            $http.get(url + "/openProfile/" + modelId + "/" + costCenter + "/" + financeCubeIds + "/" + date).success(function(data) {
                window.open(data);
            });
        }

        function getNotesLoader() {
            return notesLoader;
        }

        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }
    }
})();