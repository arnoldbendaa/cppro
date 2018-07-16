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
        .module('app.spreadSheet')
        .service('ContextVariablesService', ContextVariablesService);

    /* @ngInject */
    function ContextVariablesService($rootScope) {
        var self = this;

        self.contextVariables = null;
        self.selectedDimension = [];
        self.dim0Id = null;
        self.dim1Id = null;
        self.dim2Id = null;
        self.dataType = null;

        self.getContextVariables = getContextVariables;
        self.setContextVariables = setContextVariables;
        self.setDimensions = setDimensions;
        self.getContextVariables = getContextVariables;

        function getContextVariables() {
            return self.contextVariables;
        }

        function setContextVariables(contextVariables) {
            $rootScope.contextVariables = contextVariables; //TODO do wywalenia

            self.contextVariables = contextVariables;
            $rootScope.$broadcast('ContextVariablesService:dataUpdated');
        }

        function setDimensions(args) {
            self.dim0Id = null;
            self.dim1Id = null;
            self.dim2Id = null;
            self.dataType = null;

            // 1. selected dimensions and data type are defined?
            if ($rootScope.selectedDimensions && $rootScope.selectedDataType) {
                // 1.1 yes, so the selectedProfile was loaded earlier -> use defined data
                self.dim0Id = args.dim0Id || $rootScope.selectedDimensions[0].id;
                self.dim1Id = args.dim1Id || $rootScope.selectedDimensions[1].id;
                self.dim2Id = args.dim2Id || $rootScope.selectedDimensions[2].id;
                self.dataType = args.dataType || $rootScope.selectedDataType;
            } else {
                // 1.2 no, so this is the start application
                self.dim0Id = 0;
                self.dim1Id = 0;
                self.dim2Id = 0;
                self.dataType = "";
            }
        }
    }
})();