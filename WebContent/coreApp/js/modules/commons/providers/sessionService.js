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
        .service('SessionService', SessionService);

    /* @ngInject */
    function SessionService($rootScope, $timeout, $cookieStore, $http) {
        var self = this;
        var url = $BASE_PATH;

        self.getCookie = getCookie;
        self.setCookie = setCookie;
        self.getSessionParams = getSessionParams;

        //checkSession();
        var testcookie;

        /************************************************** IMPLEMENTATION *************************************************************************/


        function getCookie(cookieName) {
            return $cookieStore.get(cookieName);
        }

        function setCookie(cookieName, cookieValue) {
            $cookieStore.put(cookieName, cookieValue);
        }

        function getSessionParams(index) {
            if (getCookie('sessionExpiry2') != undefined) {
                testcookie = getCookie('sessionExpiry2');
            }
            // var cookie = getCookie('sessionExpiry2');
            var sessionExpiry = Math.abs(testcookie.sessionExpiry);
            var x = new Date();
            var utcTime = x.getTime();
            $cookieStore.remove('sessionExpiry2');
            return [sessionExpiry, utcTime];
        }

        function checkSession(callOnce) {
//            var sessionParams = self.getSessionParams();
//            var sessionExpiry = sessionParams[0];
//            var utcTime = sessionParams[1];
//            $rootScope.sessionTime = ((sessionExpiry + 10000 - utcTime) / 1000 / 60).toFixed();
//            if (utcTime > (sessionExpiry - 10000)) { // 10 extra seconds to make sure
//                console.log(sessionExpiry);
//                //window.location = url + "/logout.do";
//            } else {
//                if ($rootScope.sessionTime == 10) {
//                    //swal("Session time!", "Your session will expire in 10 minutes!", "warning");
//                }
//                if (!callOnce) {
//                    $timeout(checkSession, 60000);
//                }
//            }
        }

        $rootScope.$watch(function() {
            return $http.pendingRequests.length > 0;
        }, function(hasPending) {
            if (!hasPending) {
                // one of request is finished, so update session timer
                $timeout(function() {
                    checkSession(true);
                }, 3000);
            }
        });

    }

})();