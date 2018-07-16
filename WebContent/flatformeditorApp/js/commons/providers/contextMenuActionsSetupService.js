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
        .module('flatFormEditorApp.commons')
        .service('ContextMenuActionsSetupService', ContextMenuActionsSetupService);

    /* @ngInject */
    function ContextMenuActionsSetupService(ContextMenuActionService) {
        var self = this;

        self.setActions = function(param) {
            var divider = {
                isDivider: true
            };
            var actions = [];
            var addDefault = false;
            switch (param) {
                case 'Home':
                    actions = [];
                    addDefault = true;
                    break;
                case 'Mappings':
                    actions = [{
                            name: "Copy Input to Output Mapping",
                            action: "MappingController:copyInputToOutputMapping",
                            isDivider: false,
                            isVisible: true,
                            visibilityDependsOn: "in"
                        }, {
                            name: "Copy Output to Input Mapping",
                            action: "MappingController:copyOutputToInputMapping",
                            isDivider: false,
                            isVisible: true,
                            visibilityDependsOn: "out"
                        },
                        angular.copy(divider), {
                            name: "Clear Input Mapping",
                            action: "MappingController:resetInputMapping",
                            isDivider: false,
                            visibilityDependsOn: "in"
                        }, {
                            name: "Clear Output Mapping",
                            action: "MappingController:resetOutputMapping",
                            isDivider: false,
                            visibilityDependsOn: "out"
                        }, {
                            name: "Clear Formula Mapping",
                            action: "MappingController:resetFormulaMapping",
                            isDivider: false,
                            visibilityDependsOn: "formula"
                        },
                        angular.copy(divider), {
                            name: "Clear All Mappings",
                            action: "MappingController:resetAllMappings",
                            isDivider: false,
                            visibilityDependsOn: "in out formula"
                        }, {
                            name: "Clear Cell",
                            action: "MappingController:resetWholeCell",
                            isDivider: false
                        },
                        // angular.copy(divider), {
                        //     name: "Set Year-to-Date property",
                        //     action: "MappingController:setYearToDate",
                        //     isDivider: false,
                        //     visibilityDependsOn: "in out"
                        // }, {
                        //     name: "Unset Year-to-Date property",
                        //     action: "MappingController:unSetYearToDate",
                        //     isDivider: false,
                        //     visibilityDependsOn: "in out"
                        // },
                        angular.copy(divider), {
                            name: "Cell Picker",
                            action: "MappingController:cellPicker",
                            isDivider: false
                        }
                    ];
                    addDefault = true;
                    break;
                case 'Insert':
                    actions = [{
                        name: "Edit Sparkline...",
                        action: "InsertController:editSparkline",
                        isDivider: false
                    }];
                    addDefault = true;
                    break;
                case 'View':
                    actions = [];
                addDefault = true;
                    break;
                default:
                    actions = [];
            }
            if (addDefault) {
                return ContextMenuActionService.addDefaultActions(actions);
            } else {
                return actions;
            }
            //ContextMenuService.updateActions(actions, addDefault);

        };
    }

})();