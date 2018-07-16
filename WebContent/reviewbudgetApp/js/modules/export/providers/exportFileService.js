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
        .module('app.exportFile')
        .service('ExportFileService', ExportFileService);

    /* @ngInject */
    function ExportFileService($rootScope, $http, SpreadSheetService) {
        var self = this;

        self.exportSpreadsheet = exportSpreadsheet;
        self.getOpenTypes = getOpenTypes;
        self.getPageLayouts = getPageLayouts;
        self.getPageTransitions = getPageTransitions;
        self.getPageTransitions = getPageTransitions;
        self.getDestinationTypes = getDestinationTypes;
        self.getScalingTypes = getScalingTypes;
        self.manageWorksheets = manageWorksheets;
        self.resetData = resetData;
        self.getData = getData;
        self.getSheetIndexes = getSheetIndexes;
        self.getDuplexModes = getDuplexModes;



        /************************************************** IMPLEMENTATION *************************************************************************/


        /**
         * Exports spreadsheet to pdf file. Not working!!!!! - problem with response
         * @return {[type]} [description]
         */
        function exportSpreadsheet() {
            var dataObj = {};
            dataObj.spread = SpreadSheetService.getSpreadSheet().toJSON();
            //dataObj.exportFileType = self.fileType;

            var json = {
                spread: SpreadSheetService.getSpreadSheet().toJSON(),
            };
        }

        function manageWorksheets() {
            var worksheets = [];
            var spreadsheet = SpreadSheetService.getSpreadSheet();
            var numberOfSheets = spreadsheet.getSheetCount();
            for (var i = 0; i < numberOfSheets; i++) {
                var worksheet = {
                    id: i,
                    name: spreadsheet.getSheet(i).getName()
                };
                worksheets.push(worksheet);
            }
            return worksheets;
        }

        /**
         * Method resets all data. Method is invoked before displaying modal
         * @return {[type]} [description]
         */
        function resetData() {
            self.choosedSheets = [];
        }

        /**
         * Method returns all data which is neccessary to template modal
         * @return {[type]} [description]
         */
        function getData() {
            return self;
        }

        /**
         * Methods returns array of choosed indexes
         * @return {[type]} [description]
         */
        function getSheetIndexes() {
            var indexes = [];
            var spreadsheet = SpreadSheetService.getSpreadSheet();
            var numberOfSheets = spreadsheet.getSheetCount();
            var choosedSheets = self.choosedSheets;

            for (var i = 0; i < numberOfSheets; i++) {
                var name = spreadsheet.getSheet(i).getName();
                for (var j = 0; j < choosedSheets.length; j++) {
                    var choosedName = choosedSheets[j].name;
                    if (choosedName.indexOf(name) !== -1)
                        indexes.push(i);
                }
            }
            return indexes;
        }

        function getDuplexModes() {
            return ["Default", "Simplex", "DuplexFlipShortEdge", "DuplexFlipLongEdge"];
        }

        function getScalingTypes() {
            return ["AppDefault", "None"];
        }

        function getDestinationTypes() {
            return ["Auto", "FitPage", "FitWidth", "FitHeight", "FitBox"];
        }

        function getPageTransitions() {
            return ["Default", "Split", "Blinds", "Box", "Wipe", "Dissolve", "Glitter", "Fly", "Push", "Cover", "Uncover", "Fade"];
        }

        function getPageLayouts() {
            return ["Auto", "SinglePage", "OneColumn", "TwoColumnLeft", "TwoColumnRight", "TwoPageLeft", "TwoPageRight"];
        }

        function getOpenTypes() {
            return ["Auto", "Use None", "Use Outlines", "Use Thumbs", "Full Screen", "Use OC", "Use Attachments"];
        }
    }
})();