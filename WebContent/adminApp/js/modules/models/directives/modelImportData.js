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
        .module('adminApp.model')
        .directive('modelImportData', modelImportData);

    /* @ngInject */
    function modelImportData($rootScope, $timeout, ModelService, Flash, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'models/views/modelImportData.html',
            scope: {
                modelId: '=model',
                visId: '=',
                close: '&'
            },
            replace: true,
            link: function linkFunction($scope) {

                // parameters to resize modal
                var modalDialog = $(".model-import-data").closest(".modal-dialog");
                var elementToResize = $(".elementToResize1");
                var additionalElementsToCalcResize = [55];
                var elementToResize2 = $(".elementToResize2");
                var additionalElementsToCalcResize2 = [55];
                var createEmptyDataToSend = function() {
                    var empty = {
                        "models": [],
                        "dataTypes": []
                    };
                    return empty;
                };
                $scope.importData = ModelService.getImportData($scope.modelId);
                $scope.dataTosend = createEmptyDataToSend();
                $scope.modelAllSelection = false;
                $scope.dataTypeAllSelection = false;
                $scope.cookieName = "adminPanel_modal_modelImportData";
                // try to resize and move modal based on the cookie
                CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                $scope.resizedColumn = resizedColumn;
                $scope.sortedColumn = sortedColumn;
                $scope.close = close;
                $scope.save = save;
                $timeout(function() { // timeout is necessary to pass asynchro
                    allElementsToResize = [elementToResize, elementToResize2];
                    allAdditionalElementsToCalcResize = [additionalElementsToCalcResize, additionalElementsToCalcResize2];
                    CoreCommonsService.allowResizingModal(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx);
                }, 0);

                function resizedColumn(sender, args) {
                    CoreCommonsService.resizedColumn(args, $scope.cookieName);
                }

                function sortedColumn(sender, args) {
                    CoreCommonsService.sortedColumn(args, $scope.cookieName);
                }

                function close() {
                    $rootScope.$broadcast('Model:dataImportClose');
                }

                function save() {
                    // Selected models and data types are copied (without "*selection" field) from $scope.importData to $scope.dataTosend.
                    $scope.dataTosend = createEmptyDataToSend();
                    angular.forEach($scope.importData.models, function(model) {
                        if (model.modelSelection) {
                            var newModel = angular.copy(model);
                            delete newModel.modelSelection;
                            $scope.dataTosend.models.push(newModel);
                        }
                    });
                    angular.forEach($scope.importData.dataTypes, function(dataType) {
                        if (dataType.dataTypeSelection) {
                            var newDataType = angular.copy(dataType);
                            delete newDataType.dataTypeSelection;
                            $scope.dataTosend.dataTypes.push(newDataType);
                        }
                    });
                    $rootScope.$broadcast("modal:blockAllOperations");
                    ModelService.sendImportData($scope.dataTosend, $scope.modelId);
                }

                function changeSelectionForModels(whatToDo) {
                    angular.forEach($scope.importData.models, function(model) {
                        switch (whatToDo) {
                            case "all":
                                model.modelSelection = true;
                                break;
                            case "none":
                                model.modelSelection = false;
                                break;
                            case "invert":
                                model.modelSelection = !model.modelSelection;
                                break;
                        }
                    });
                }

                function changeSelectionForDataTypes(whatToDo) {
                    angular.forEach($scope.importData.dataTypes, function(dataType) {
                        switch (whatToDo) {
                            case "all":
                                dataType.dataTypeSelection = true;
                                break;
                            case "none":
                                dataType.dataTypeSelection = false;
                                break;
                            case "invert":
                                dataType.dataTypeSelection = !dataType.dataTypeSelection;
                                break;
                        }
                    });
                }

                $scope.$on("Model:dataImportError", function(event, data) {
                    $rootScope.$broadcast("modal:unblockAllOperations");
                    Flash.create('danger', data.message);
                });

                $scope.$on("Model:dataImportClose", function(event, data) {
                    if (typeof data != "undefined") {
                        if (data.success) {
                            Flash.create('success', "Import Data task is started.");
                        }
                    }
                });

                $scope.$watch("modelAllSelection", function() {
                    if ($scope.modelAllSelection) {
                        changeSelectionForModels('all');
                    } else {
                        changeSelectionForModels('none');
                    }
                });

                $scope.$watch("dataTypeAllSelection", function() {
                    if ($scope.dataTypeAllSelection) {
                        changeSelectionForDataTypes('all');
                    } else {
                        changeSelectionForDataTypes('none');
                    }
                });

            }
        };
    }
})();