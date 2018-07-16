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
        .module('adminApp.commons')
        .service('ContextMenuService', ContextMenuService);
    
    /* @ngInject */
    function ContextMenuService(PageService) {

        var self = this;
        var actions = [];
        var element = null;
        var ctx = {
            element: element,
            actions: actions
        };

        self.initialize = function(newElement) {
            ctx.element = newElement;
        };

        self.getCtx = function() {
            return ctx;
        };

        self.updateActions = function() {
            //console.log(PageService.getCurrentPageService().getActions())
            //var actions = test(PageService.getCurrentPageService().getActions());//contextMenuActionService.get(param);
            actions.length = 0;
            //var newActions = [];
            var isOpenDisabled = PageService.getCurrentPageService().isOpenDisabled;
            if (!isOpenDisabled) {
                var open = {
                    name: "Open",
                    action: "open",
                    disabled: isOpenDisabled,
                    isDivider: false
                };
                actions.push(open);
            }
            var isCreateDisabled = PageService.getCurrentPageService().isCreateDisabled;
            if (!isCreateDisabled) {
                var create = {
                    name: "Create",
                    action: "create",
                    disabled: isCreateDisabled,
                    isDivider: false
                };
                actions.push(create);
            }
            var divider = {
                name: "Divider",
                action: null,
                disabled: true,
                isDivider: true
            };
            actions.push(divider);
            var moduleActions = PageService.getCurrentPageService().getActions();
            angular.forEach(moduleActions, function(action) {
                var newAction = {};
                angular.copy(action, newAction);
                if (newAction.isDivider == undefined) {
                    newAction.isDivider = false;
                }
                actions.push(newAction);
            });
            //setActions(newActions);
        };

        function setActions(newActions) {
            actions.length = 0;
            angular.forEach(newActions, function(action) {
                var newAction = {};
                angular.copy(action, newAction);
                newAction.isDivider = false;
                actions.push(newAction);
            });
        };
    }
})();