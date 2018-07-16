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
        .module('adminApp.dashboardPage')
        .service('HierarchyFormDashboardService', hierarchyFormDashboardService);

    /* @ngInject */
    function hierarchyFormDashboardService($http, Flash, $rootScope) {
        var self = this;
        var url = $BASE_PATH + 'adminPanel';

        var hierarchyPrivileges = {
            open: false,
            edit: false
        };



        self.getActions = getActions;
        self.getFormHierarchies = getFormHierarchies;

        self.getHierarchyPrivilegeOpen = getHierarchyPrivilegeOpen;
        self.getHierarchyPrivilegeEdit = getHierarchyPrivilegeEdit;


        setHierarchyPrivileges();


        self.isOpenDisabled = !(getHierarchyPrivilegeOpen() || getHierarchyPrivilegeEdit());
        self.isCreateDisabled = !getHierarchyPrivilegeEdit();


        function setTopButtons() {}

        var actions = {};
        /**
         * For top buttons
         */
        function getActions() {
            return actions;
        }

        function setHierarchyPrivileges() {
            if (($ROLES.indexOf("Dashboard Hierarchy Open") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) {
                hierarchyPrivileges.open = true;
            }
            if (($ROLES.indexOf("Dashboard Hierarchy Edit") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) {
                hierarchyPrivileges.edit = true;
            }
        }



        function getHierarchyPrivilegeOpen() {
            return hierarchyPrivileges.open
        }

        function getHierarchyPrivilegeEdit() {
            return hierarchyPrivileges.edit
        }



        function getFormHierarchies() {
            return $http.get(url + "/formdashboard/hierarchy")
                .then(getFormHierarchiesComplete)
                .catch(getFormHierarchiesFailed);

            function getFormHierarchiesComplete(response) {
                return response.data;
            }

            function getFormHierarchiesFailed(error) {
                console.log('Get form Hierarchies failed: ' + error.message);
            }
        };


    }
})();