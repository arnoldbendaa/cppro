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
        .module('flatFormTemplateApp.spreadSheet')
        .service('SpreadSheetMainMenuService', SpreadSheetMainMenuService);

    /* @ngInject */
    function SpreadSheetMainMenuService($rootScope, $http, $timeout) {
        var self = this;
        var mainMenu;

        self.showMenu = showMenu;
        self.hideMenu = hideMenu;

        activate();
        /************************************************** IMPLEMENTATION *************************************************************************/

        function activate() {
            // main menu must be selected every time - it can't be global variable, because menu sometimes stops working properly (probably ng-include for menu makes problems)
            // mainMenu = $("#mainMenu");
        }

        function manageMainMenu() {
            if (!mainMenu || mainMenu.length === 0) {
                mainMenu = $("#mainMenu");
                mainMenu.bind('clickoutside', function(event) {
                    var target = $(event.target);
                    if (target.closest(".sweet-overlay").length > 0 || target.closest(".sweet-alert").length > 0 || target.closest(".showMenu").length > 0) {
                        // don't hide if click on sweet alert
                    } else {
                        hideMenu();
                    }
                });

                // mainMenu.resizable({
                //     minWidth: 821,
                //     containment: "parent",
                //     handles: "e",
                //     resize: function() {
                //         //$rootScope.$broadcast("MainMenu:refreshCopyTemplateTo");
                //     }
                // });
            }
        }

        function showMenu() {
            manageMainMenu();
            mainMenu.fadeIn(100);
        }

        function hideMenu() {
            mainMenu.unbind('clickoutside');
            mainMenu.fadeOut(250, function() {
                mainMenu = null;
            });

        }
    }
})();