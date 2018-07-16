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
adminApp.service('PageService', ['$rootScope', 'CoreCommonsService',
    function($rootScope, coreCommonsService) {
        var self = this;
        var currentPageService = {};

        /**
         * [setCurrentPageService description]
         * @param {[type]} pageService [description]
         */
        self.setCurrentPageService = function(pageService) {
            currentPageService = pageService;
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * [getCurrentPageService description]
         * @return {[type]} [description]
         */
        self.getCurrentPageService = function() {
            return currentPageService;
        };

        /**
         * [enableActions description]
         * @param  {[type]} actions [description]
         * @return {[type]}         [description]
         */
        self.enableActions = function(actions) {
            angular.forEach(actions, function(action, key) {
                action.disabled = false;
            });
        };

        /**
         * Find action by "action" field and enable it.
         */
        self.enableAction = function(actions, action) {
            var currentAction = coreCommonsService.findElementByKey(actions, action, "action");
            currentAction.disabled = false;
        }

        /**
         * [disableActions description]
         * @param  {[type]} actions [description]
         * @return {[type]}         [description]
         */
        self.disableActions = function(actions) {
            angular.forEach(actions, function(action, key) {
                action.disabled = true;
            });
        };

        /**
         * Find action by "action" field and disable it.
         */
        self.disableAction = function(actions, action) {
            var currentAction = coreCommonsService.findElementByKey(actions, action, "action");
            currentAction.disabled = true;
        }

        /**
         * [isFunction description]
         * @param  {[type]}  functionToCheck [description]
         * @return {Boolean}                 [description]
         */
        self.isFunction = function(functionToCheck) {
            var getType = {};
            return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
        }
    }
]);