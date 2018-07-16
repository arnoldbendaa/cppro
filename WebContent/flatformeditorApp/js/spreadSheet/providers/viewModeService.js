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
        .module('flatFormEditorApp.spreadSheet')
        .service('ViewModeService', ViewModeService);

    /* @ngInject */
    function ViewModeService(CoreCommonsService) {

        var self = this;
        var modes = [{
            id: 0,
            name: "LOADING",
            label: "LOADING"
        }, {
            id: 1,
            name: "TEST",
            label: "TEST"
        }, {
            id: 2,
            name: "VALUE",
            label: "VALUE"
        }, {
            id: 3,
            name: "FORMULA",
            label: "FORMULA MAPPING"
        }, {
            id: 4,
            name: "INPUT",
            label: "INPUT MAPPING"
        }, {
            id: 5,
            name: "OUTPUT",
            label: "OUTPUT MAPPING"
        }];

        var viewMode = {
            items: modes,
            currentItem: CoreCommonsService.findElementByKey(modes, "LOADING", "name")
        };

        self.setCurrentViewMode = function(name) {
            viewMode.currentItem = self.findMode(name);
        };

        self.getCurrentViewMode = function() {
            return viewMode.currentItem;
        };

        self.getViewMode = function() {
            return viewMode;
        };

        self.getViewModes = function() {
            return modes;
        };

        self.findMode = function(modeName) {
            return CoreCommonsService.findElementByKey(modes, modeName, "name");
        };
    }

    angular
    .module('flatFormEditorApp.spreadSheet')
    .filter('notTestLoadingFilter', notTestLoadingFilter);

/* @ngInject */
function notTestLoadingFilter() {

        return function(modes) {
            var filteredModes = [];
            angular.forEach(modes, function(mode) {
                if (mode.name != "TEST" && mode.name != "LOADING")
                    filteredModes.push(mode);
            });
            return filteredModes;
        };
    };



})();