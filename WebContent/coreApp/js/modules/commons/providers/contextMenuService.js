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
        .service('ContextMenuService', ContextMenuService);

    /* @ngInject */
    function ContextMenuService(ContextMenuActionService) {
        var self = this;

        var actns = [];
        var element = {};

        var ctx = {
            element: element,
            actions: actns
        };


        self.initialize = initialize;
        self.getCtx = getCtx;
        self.updateActions = updateActions;
        //    self.updateVisibility = updateVisibility;


        /************************************************** IMPLEMENTATION *************************************************************************/


        function initialize(newElement) {
            ctx.element = newElement;
        }

        function getCtx() {
            return ctx;
        }

        function updateActions(newActions) {
            actns.length = 0;
            angular.forEach(newActions, function(newAction) {
                actns.push(newAction);
            });
        }

        // var setActions = function(newActions) {
        //     actns.length = 0;
        //     angular.forEach(newActions, function(newAction) {
        //         actns.push(newAction);
        //     });
        // };

        // function updateVisibility(inputMappingVisibility, outputMappingVisibility, formulaMappingVisibility) {

        //     angular.forEach(actns, function(action) {
        //         if (action.visibilityDependsOn === "in") {
        //             action.isVisible = inputMappingVisibility;
        //         } else if (action.visibilityDependsOn === "out") {
        //             action.isVisible = outputMappingVisibility;
        //         } else if (action.visibilityDependsOn === "formula") {
        //             action.isVisible = formulaMappingVisibility;
        //         } else if (action.visibilityDependsOn === "in out") {
        //             action.isVisible = inputMappingVisibility || outputMappingVisibility;
        //         } else if (action.visibilityDependsOn === "in out formula") {
        //             action.isVisible = inputMappingVisibility || outputMappingVisibility || formulaMappingVisibility;
        //         } else {
        //             action.isVisible = true;
        //         }
        //     });

        //     // Hide dividers next to each other
        //     var visibleElements = [];
        //     angular.forEach(actns, function(action) {
        //         if (action.isDivider && visibleElements.length === 0) {
        //             action.isVisible = false;
        //         } else if (action.isDivider) {
        //             visibleElements = [];
        //         }
        //         if (!action.isDivider && action.isVisible) {
        //             visibleElements.push(true);
        //         }
        //     });

        //     // Hide divider on the end of the list
        //     if (actns.length > 0 && actns[actns.length - 1].isDivider) {
        //         actns[actions.length - 1].isVisible = false;
        //     }
        // }
    }

})();