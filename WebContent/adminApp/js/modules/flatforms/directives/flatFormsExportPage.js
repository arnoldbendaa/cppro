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
        .directive('flatFormsExport', flatFormsExport);

    /* @ngInject */
    function flatFormsExport($rootScope, $compile, Flash, FlatFormsPageService, CoreCommonsService, $timeout) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'flatforms/views/flatFormsExport.html',
            scope: {
                modal: "="
            },
            replace: true,
            controller: ['$scope',
                function($scope) {
                    $scope.cookieName = "adminPanel_modal_flatFormsExport";
                    $scope.availableFlatForms = FlatFormsPageService.getFlatForms().sourceCollection;
                    $scope.filePath = FlatFormsPageService.getFilePath();
                    $scope.selectedFlatForms = [];
                    $scope.isFinanceCubeColumnVisible = false;
                    $scope.isLastUpdatedColumnVisible = false;
                    $scope.modalInfo = "Select XMLForm to export:";
                    $scope.exportFlatForms = exportFlatForms;
                    $scope.close = close;
                    $scope.modalContext = null;

                    function exportFlatForms() {
                        $rootScope.$broadcast("modal:blockAllOperations");

                        var exportObject = {};
                        exportObject.flatFormIds = [];
                        exportObject.financeCubeVisIds = [];
                        for (var i = 0; i < $scope.selectedFlatForms.length; i++) {
                            exportObject.flatFormIds.push($scope.selectedFlatForms[i].flatFormId);
                            exportObject.financeCubeVisIds.push($scope.selectedFlatForms[i].financeCube.financeCubeVisId);
                        }
                        if (exportObject.flatFormIds.length === 0) {
                            $rootScope.$broadcast("modal:unblockAllOperations");
                            Flash.create('danger', "List Flat Forms for export is empty");
                        } else {
                            var exportListJSON = angular.toJson(exportObject);
                            var exportJSON = {
                                exportObject: exportListJSON
                            };
                            var url = $scope.filePath;
                            $scope.loaded = true;
                            $.fileDownload(url, {
                                httpMethod: 'POST',
                                data: exportJSON
                            }).done(function(url) {
                                $rootScope.$broadcast("modal:unblockAllOperations");
                                $scope.close();
                            }).fail(function(responseHtml, url) {
                                $rootScope.$broadcast("modal:unblockAllOperations");
                                Flash.create('danger', 'Export failed');
                            });
                        }
                    }

                    function close() {
                        $scope.modal.dismiss('cancel');
                    }

                    /****************************************** FLEX GRID Functions ****************************************************/
                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(sender, $scope.cookieName);
                    }

                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(sender, $scope.cookieName);
                    }

                    function addEvents() {
                        // parameters to resize modal
                        var modalDialog = $(".flat-forms-export").closest(".modal-dialog");
                        var elementToResize = $(".flat-form-chooser .flat-form-grid");
                        var additionalElementsToCalcResize = [$(".flat-form-filter"), 10];
                        // try to resize and move modal based on the cookie
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                        $timeout(function() { // timeout is necessary to pass asynchro
                            CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.modalContext);
                        }, 0);
                    }

                    /****************************************** WATCHERS **************************************************************/
                    $scope.$watch('modalContext', function() {
                        if ($scope.modalContext) {
                            var flex = $scope.modalContext.flex;
                            if (flex) {
                                //flex.onResizedColumn = resizedColumn;
                                flex.onSortedColumn = sortedColumn;
                            }
                            addEvents();
                        }
                    });
                }
            ]
        };
    }
})();