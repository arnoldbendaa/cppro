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
        .module('app.dimensions')
        .controller('DimensionController', DimensionController);

    /* @ngInject */
    function DimensionController($rootScope, $scope, $modal, $http, dimensionFactory) {

        $scope.accountTreeModel = null;
        $scope.bussinessTreeModel = null;
        $scope.calendarTreeModel = null;
        $scope.dataTypeTreeModel = null;
        $scope.placeToSendTheResult = null;

        $scope.showDimension = showDimension;
        $scope.showDataType = showDataType;



        $scope.$on('DimensionController:showDataTypeTreeView', function(event, args) {
            $scope.placeToSendTheResult = args.placeToSendTheResult;
            if (!$scope.dataTypeTreeModel) {
                getDataTypeTreeModelandShowView();
            } else {
                showDataTypeTreeView();
            }
        });
        /**
         * Handle if want to open list of dimensions
         */
        $scope.$on('DimensionController:showDimension', function(event, args) {
            $scope.placeToSendTheResult = args.placeToSendTheResult;
            $scope.showDimension(args.dimensionNumber);
        });


        /************************************************** IMPLEMENTATION *************************************************************************/


        /**
         * Show dimension tree view.
         * @param  {integer} dimensionNumber number of dimension to show (0=bussiness,1=account,2=calendar)
         */
        function showDimension(dimensionNumber) {
            switch (dimensionNumber) {
                case 0: // bussiness dimensions
                    if (!$scope.bussinessTreeModel) {
                        getBussinessTreeModelandShowView();
                    } else {
                        showBussinessTreeView();
                    }
                    break;
                case 1: // account dimensions
                    if (!$scope.accountTreeModel) {
                        getAccountTreeModelandShowView();
                    } else {
                        showAccountTreeView();
                    }
                    break;
                case 2: // calendar dimensions
                    if (!$scope.calendarTreeModel) {
                        getCalendarTreeModelandShowView();
                    } else {
                        showCalendarTreeView();
                    }
                    break;
                default:
                    return;
            }
        }

        /**
         * Show data type tree view.
         */
        function showDataType() {
            if (!$scope.dataTypeTreeModel || $scope.dataTypeTreeModel === null) {
                getDataTypeTreeModelandShowView();
            } else {
                showDataTypeTreeView();
            }
        }


        /************************************************** PRIVATE MEMBERS *************************************************************************/

        // Account Dimensions
        function getAccountTreeModelandShowView() {
            dimensionFactory.getDimensionTreeModel("ACCOUNT")
                .success(function(data) {
                    $scope.accountTreeModel = data;
                    showAccountTreeView();
                });
        }

        function showAccountTreeView() {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'dimensions/views/accountTreeViewModal.html',
                backdrop: 'static',
                controller: 'AccountTreeViewModalController',
                resolve: {
                    accountTreeModel: function() {
                        return angular.copy($scope.accountTreeModel);
                    },
                    selectedElement: function() {
                        return angular.copy($rootScope.selectedDimensions[1]);
                    }
                }
            });
            modalInstance.result.then(function(selectedElement) {
                if ($scope.placeToSendTheResult !== null) {
                    $rootScope.$broadcast($scope.placeToSendTheResult, {
                        recivedDimension: 1,
                        dim: {
                            dimId: selectedElement.id,
                            dimName: selectedElement.name,
                            dimStructureId: selectedElement.structureId
                        }
                    });
                    $scope.placeToSendTheResult = null;
                } else if (($rootScope.selectedDimensions[1] != selectedElement) && (selectedElement.selectable)) {
                    $rootScope.$broadcast('veil:show', {});
                    $rootScope.$broadcast("spreadSheetController:loadSpreadSheet", {
                        dim1Id: selectedElement.id
                    });
                }
            });
        }

        // Bussiness Dimensions

        function getBussinessTreeModelandShowView() {
            dimensionFactory.getDimensionTreeModel("BUSSINESS")
                .success(function(data) {
                    $scope.bussinessTreeModel = data;
                    showBussinessTreeView();
                });
        }

        function showBussinessTreeView() {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'dimensions/views/bussinessTreeViewModal.html',
                backdrop: 'static',
                controller: 'BussinessTreeViewModalController',
                resolve: {
                    bussinessTreeModel: function() {
                        return angular.copy($scope.bussinessTreeModel);
                    },
                    selectedElement: function() {
                        return angular.copy($rootScope.selectedDimensions[0]);
                    }
                }
            });
            modalInstance.result.then(function(selectedElement) {
                if ($scope.placeToSendTheResult !== null) {
                    $rootScope.$broadcast($scope.placeToSendTheResult, {
                        recivedDimension: 0,
                        dim: {
                            dimId: selectedElement.id,
                            dimName: selectedElement.name,
                            dimStructureId: selectedElement.structureId
                        }
                    });
                    $scope.placeToSendTheResult = null;
                } else if (($rootScope.selectedDimensions[0] != selectedElement) && (selectedElement.selectable)) {
                    $rootScope.$broadcast('veil:show', {});
                    $rootScope.$broadcast("spreadSheetController:loadSpreadSheet", {
                        dim0Id: selectedElement.id
                    });
                }
            });
        }

        // Calendar Dimensions, filtered by the range

        function getCalendarTreeModelandShowView() {
            dimensionFactory.getDimensionTreeModel("CALENDAR")
                .success(function(data) {
                    $scope.calendarTreeModel = data;
                    showCalendarTreeView();
                });
        }

        function showCalendarTreeView() {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'dimensions/views/calendarTreeViewModal.html',
                backdrop: 'static',
                controller: 'CalendarTreeViewModalController',
                resolve: {
                    calendarTreeModel: function() {
                        return angular.copy($scope.calendarTreeModel);
                    },
                    selectedElement: function() {
                        return angular.copy($rootScope.selectedDimensions[2]);
                    }
                }
            });
            modalInstance.result.then(function(selectedElement) {
                if ($scope.placeToSendTheResult !== null) {
                    $rootScope.$broadcast($scope.placeToSendTheResult, {
                        recivedDimension: 2,
                        dim: {
                            dimId: selectedElement.id,
                            dimName: selectedElement.name,
                            dimStructureId: selectedElement.structureId
                        }
                    });
                    $scope.placeToSendTheResult = null;
                } else if (($rootScope.selectedDimensions[2] != selectedElement) && (selectedElement.selectable)) {
                    $rootScope.$broadcast('veil:show', {});
                    $rootScope.$broadcast("spreadSheetController:loadSpreadSheet", {
                        dim2Id: selectedElement.id
                    });
                }
            });
        }

        // Data Types

        function getDataTypeTreeModelandShowView() {
            dimensionFactory.getDataTypeTreeModel()
                .success(function(data) {
                    $scope.dataTypeTreeModel = data;
                    showDataTypeTreeView();
                });
        }

        function showDataTypeTreeView() {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'dimensions/views/dataTypeTreeViewModal.html',
                backdrop: 'static',
                controller: 'DataTypeTreeViewModalController',
                resolve: {
                    dataTypeTreeModel: function() {
                        return angular.copy($scope.dataTypeTreeModel);
                    },
                    selectedElementName: function() {
                        return angular.copy($rootScope.selectedDataType);
                    }
                }
            });
            modalInstance.result.then(function(selectedElement) {
                if ($scope.placeToSendTheResult !== null) {
                    $rootScope.$broadcast($scope.placeToSendTheResult, {
                        recivedDimension: 3,
                        dim: {
                            dimId: selectedElement.id,
                            dimName: selectedElement.name,
                            dimStructureId: selectedElement.structureId
                        }
                    });
                    $scope.placeToSendTheResult = null;
                } else if (($rootScope.selectedDataType != selectedElement.name) && (selectedElement.selectable)) {
                    $rootScope.$broadcast('veil:show', {});
                    $rootScope.$broadcast("spreadSheetController:loadSpreadSheet", {
                        dataType: selectedElement.name
                    });
                }
            });
        }
    }
})();