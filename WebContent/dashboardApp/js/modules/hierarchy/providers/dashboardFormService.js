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
(function () {
    'use strict';

    angular
        .module('dashboardFormApp.dashboardForm')
        .service('DashboardFormService', DashboardFormService);

    /* @ngInject */
    function DashboardFormService($http, validationService) {

        var self = this;
        var url = $BASE_PATH + 'dashboard/getauction/';

        self.getForms = getForms;
        self.getModelId = getModelId;
        self.getChosenForm = getChosenForm;
        self.saveDashboard = saveDashboard;
        self.testDashboard = testDashboard;
        self.getDashboard = getDashboard;
        self.getSecurityList = getSecurityList;

        /************************************************** IMPLEMENTATION *************************************************************************/


        function getForms() {
            console.log($BASE_PATH);
            var getFormsUrl = $BASE_PATH + "adminPanel/flatForms";
            return $http.get(getFormsUrl)
                .then(getAllComplete);

            function getAllComplete(response) {
                var result = {
                    data: response.data
                };
                return result;
            }
        }

        function getModelId(financeCubeID) {
            console.log($BASE_PATH);
            var getFormsUrl = $BASE_PATH + "dashboard/form/hierarchy/exchangeformodelid/" + financeCubeID;
            return $http.get(getFormsUrl)
                .then(getAllComplete);

            function getAllComplete(response) {
                var result = {
                    data: response.data
                };
                return result;
            }
        }

        function getChosenForm(flatFormId) {
            var getChosenFormUrl = $BASE_PATH + "flatFormEditor/flatForm/" + flatFormId;
            return $http.get(getChosenFormUrl, {
                coverAllView: true,
                statement: "Loading data, please wait..."
            })
                .then(getAllComplete);

            function getAllComplete(response) {
                var result = {
                    data: response.data
                };
                return result;
            }
        }

        function testDashboard(dashboardToTest, showErrors) {
            return $http.post("/cppro/dashboard/form/test", dashboardToTest, {
                coverAllView: true
            }).then(testComplete);

            function testComplete(response) {
                if (showErrors) {
                    var workbook = response.data;
                    var errmsg = validationService.getValidationErrors(workbook);
                    validationService.showErrors(errmsg);
                }
                var result = {
                    data: response.data
                };
                return result;
            }
        }

        function saveDashboard(dashboardToSave) {
            return $http.post("/cppro/dashboard/form/insert", dashboardToSave, {
                coverAllView: true
            }).then(saveComplete);

            function saveComplete(response) {
                var result = {
                    data: response.data
                };
                return result;
            }
        }

        function getDashboard(uuid) {
            return $http.get("/cppro/dashboard/form/data/" + uuid)
                .then(getAllComplete);

            function getAllComplete(response) {
                var result = {
                    data: response.data
                };
                return result;
            }
        }

        function getSecurityList(modelId) {
            return $http.get($BASE_PATH + "adminPanel/businessChooser/securityList/" + modelId)
                .then(getAllComplete);

            function getAllComplete(response) {
                var result = {
                    data: response.data
                };
                return result;
            }
        }
    }
})();