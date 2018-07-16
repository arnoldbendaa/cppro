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
        .module('lookupTableApp.spreadSheet')
        .service('DrawingService', DrawingService);

    /* @ngInject */
    function DrawingService($rootScope, $timeout) {
        var self = this;
        var fullHeightFlag = $(window).height();
        var redrawDelay;

        self.redraw = redraw;

        activate();
        /************************************************** IMPLEMENTATION *************************************************************************/

        function activate() {
            $(document).ready(function() {
                redraw();
                $(window).resize(function() {
                    var actualFullHeight = $(window).height();
                    if (fullHeightFlag != actualFullHeight) {
                        fullHeightFlag = actualFullHeight;
                        redraw();
                    }
                });
            });
        }

        function redraw() {
            $timeout.cancel(redrawDelay);
            redrawDelay = $timeout(function() {
                var fullHeight = $(window).height();
                var containerHeight = $("#container-navbar").outerHeight(true);

                var height = fullHeight - containerHeight - 2;
                $("#spreadsheet").height(height);
                var spread = GC.Spread.Sheets.findControl(document.getElementById('spreadsheet'));
//                if(spread == undefined || spread==null) 
//                	spread = new GC.Spread.Sheets.Workbook(document.getElementById("spreadsheet"));
                if(spread != undefined && spread!=null) 
                	spread.refresh();

//                $("#spreadsheet").wijspread("refresh");
            }, 5);
        }
    }

})();