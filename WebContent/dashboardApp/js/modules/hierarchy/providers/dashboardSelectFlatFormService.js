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
        .module('dashboardFormApp.dashboardForm')
        .factory('DashboardSelectFlatFormService', DashboardSelectFlatFormService);

    /* @ngInject */
    function DashboardSelectFlatFormService($modal, $rootScope, $http, $q, CoreCommonsService) {
        var self = this;
        var url = $BASE_PATH + 'adminPanel/flatForms/new';
        var totalVal = 100;
        var isLastPage = false;

        /************************************************** IMPLEMENTATION *************************************************************************/
        return {
            getAll: getAll,
            getIsLastPage: getIsLastPage,
            setIsLastPage: setIsLastPage,
            getFinanceCubeModels: getFinanceCubeModels,
            
        };

        function getAll(options, financeCube) {
            //  /getauction/pageSize/pageNumber 
            return $http.get(url + fillOptionsUrl(options, financeCube))
                .then(getAllComplete);

            function getAllComplete(response) {
                var result = {
                    data: response.data
                };
                angular.forEach(result.data, function(value, key) {
                    result.data[key].updated_time = new Date(value.updated_time);
                });
                if (result.data.length < options.pageSize) {
                    isLastPage = true;
                } else {
                    isLastPage = false;
                }
                return result;
            }
        }

        function fillOptionsUrl(options, financeCube) {
            var pageNum = options.page;
            var pageSize = options.pageSize;
            var orderBy;
            if (angular.isDefined(options.sort) && options.sort.length > 0) {
                orderBy = options.sort[0].field;
                if (options.sort[0].dir == 'desc') {
                    orderBy += " desc";
                } else {
                    orderBy += " asc";
                }
            } else {
                orderBy = "";
            }

            var optionsUrl = "?pageSize=" + pageSize + "&pageNumber=" + pageNum + "&orderby=" + orderBy + "&financeCube=" + financeCube;


            if (options.filter) {
                var filters = options.filter.filters;
                optionsUrl += "&filters=";

                var filtersCopy = angular.copy(filters);
                for (var filter in filtersCopy) {
                    if ("updated_time" === filters[filter].field) {
                        filters[filter].value = Date.parse(filters[filter].value);
                    }
                }
                angular.forEach(filters, function(filter, it) {
                    var isString;
                    if ((typeof filter.value) == 'string') {
                        isString = "String";
                    } else {
                        isString = "";
                    }
                    optionsUrl = optionsUrl + encodeURIComponent(filter.field) + " " + encodeURIComponent(filter.operator) + isString + " " + encodeURIComponent(filter.value);
                    if (it != filters.length - 1) {
                        optionsUrl += ",";
                    }
                });
            }
            return optionsUrl;
        }

        function getIsLastPage() {
            return isLastPage;
        }

        function setIsLastPage(val) {
            isLastPage = val;
        }

        function getFinanceCubeModels() {
            var financeCubeModels = {};

            $http.get($BASE_PATH + "adminPanel/models/financeCubeModels").success(function(data) {
                if (data && data.length > 0) {
                    financeCubeModels.listFinanceCubeModels = data;
                }
            });
            return financeCubeModels;
        };

    }

})();