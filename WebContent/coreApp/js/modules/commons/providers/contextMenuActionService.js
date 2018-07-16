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
        .module('coreApp.commons')
        .service('ContextMenuActionService', ContextMenuActionService);

    /* @ngInject */
    function ContextMenuActionService() {
        var self = this;
        var defaults = [];
        var addDefault = true;
        var divider = {
            isDivider: true
        };
        self.addDefaultActions = addDefaultActions;

        activate();

        function activate() {
            var cut = {
                name: '<i class="fa wijmo-wijribbon-cut"></i> Cut',
                action: "ClipboardController:cut",
                isDivider: false,
                withIcon: true,
                isVisible: true
            };
            var copy = {
                name: '<i class="fa wijmo-wijribbon-copy"></i> Copy',
                action: "ClipboardController:copy",
                isDivider: false,
                withIcon: true,
                isVisible: true
            };
            var paste = {
                name: '<i class="fa wijmo-wijribbon-paste"></i> Paste',
                action: "ClipboardController:paste",
                isDivider: false,
                withIcon: true,
                isVisible: true
            };
            var merge = {
                name: "Merge & Center",
                action: "AlignmentController:mergeCells",
                isDivider: false,
                isVisible: true
            };
            var formatCells = {
                name: "Format Cells...",
                action: "TableBuilderController:formatCells",
                isDivider: false,
                isVisible: true
            };

            defaults.unshift(cut, copy, paste, merge, formatCells);
        }
        //Adds default actions for context menu to given array.
        //Appends cut, copy, paste and divider at the begining
        //of the array and divider, merge and format cells at the end.
        function addDefaultActions(actions) {
            var actns = [];
            if (actions !== null && actions !== undefined) {
                actns = actions;
            }

            actns.unshift(defaults[0], defaults[1], defaults[2], angular.copy(divider));

            // don't add divider if actions contains only: cut, copy, paste, divider (no special elements for current tabs)
            if (actns.length != 4) {
                actns.push(angular.copy(divider));
            }

            actns.push(defaults[3], defaults[4]);

            return actns;
        }
    }

})();