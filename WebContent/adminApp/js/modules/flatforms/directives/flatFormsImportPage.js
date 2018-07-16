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
        .module('adminApp.flatFormsPage')
        .directive('flatFormsImport', flatFormsImport);

    /* @ngInject */
    function flatFormsImport($rootScope, $compile, Flash, FlatFormsPageService, CoreCommonsService, $timeout) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'flatforms/views/flatFormsImport.html',
            scope: {},
            replace: true,
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".flat-forms-import").closest(".modal-dialog");
                    var elementToResize = null; // $(".");
                    var additionalElementsToCalcResize = [];
                    $scope.cookieName = "adminPanel_modal_flatFormsImport";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);     
                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                    }, 0);
                    var flatForms = FlatFormsPageService.getFlatForms().sourceCollection;
                    $scope.filePath = FlatFormsPageService.getFilePath();
                    $scope.file = null;
                    $scope.selected = false;
                    $scope.isError = false;
                    $scope.messageError = "";
                    $scope.onFileSelect = function($files) {
                        $scope.file = $files;
                        if ($scope.file.type = "application/zip") {
                            $scope.selected = true;
                        } else {
                            $scope.selected = false;
                        }
                    };
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.importFlatForms = importFlatForms;
                    $scope.close = close;
                    
                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }
                    function importFlatForms() {
                        if ($scope.selected == true) {
                            $rootScope.$broadcast("modal:blockAllOperations");
                            FlatFormsPageService.importFlatForms($scope.file);
                        } else {
                            Flash.create('danger', 'File is not selected');
                        }
                    }

                    /**
                     * Update: On error after http->success() with error=true or http->error()
                     * @param  {[type]} event
                     * @param  {Object} data  [ResponseMessage]
                     */
                    $scope.$on("FlatFormsImport:flatFormsImportError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        // error from method http->success() and from field "error"
                        // clear previous validation messages
                        Flash.create('danger', data.message);
                    });

                    /**
                     * Close Flat Forms Import window
                     */
                    function close() {
                        $rootScope.$broadcast('FlatFormsImport:close');
                    }
                }
            ]
        };
    }
})();