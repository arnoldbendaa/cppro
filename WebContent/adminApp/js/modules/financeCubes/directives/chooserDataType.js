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
        .module('adminApp.financeCubesPage')
        .directive('chooserDataType', chooserDataType);

    /* @ngInject */
    function chooserDataType(CoreCommonsService, $compile, $timeout) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'financeCubes/views/chooserDataType.html',
            scope: {
                selectedDataTypes: '=selected',
                availableDataTypes: '=available',
                modalContext: '='
            },
            replace: true,
            link: function(scope) {
                //scope.modalContext = scope.ctx;
            },
            controller: ['$rootScope', '$scope',
                function($rootScope, $scope) {

                    var collectionView = new wijmo.collections.CollectionView();
                    $scope.ctx = {
                        flex: null,
                        filter: '',
                        data: collectionView
                    };
                    $scope.modalContext = $scope.ctx;
                    $scope.isSelectedDataType = isSelectedDataType;
                    $scope.selectDataType = selectDataType;
                    $scope.selectAllDataTypes = selectAllDataTypes;
                    
                    function isSelectedDataType(dataTypeVisId) {
                        if (angular.isUndefined($scope.selectedDataTypes)) {
                            return false;
                        }
                        var dataType = CoreCommonsService.findElementByKey($scope.availableDataTypes, dataTypeVisId, 'dataTypeVisId');
                        var index = $scope.selectedDataTypes.indexOf(dataType);
                        if (index == -1) { // not selected
                            return false;
                        } else { // selected
                            return true;
                        }
                    };                   
                
                    function selectDataType(dataTypeVisId) {
                        if (angular.isUndefined($scope.selectedDataTypes)) {
                            return false;
                        }
                        var dataType = CoreCommonsService.findElementByKey($scope.availableDataTypes, dataTypeVisId, 'dataTypeVisId');
                        var index = $scope.selectedDataTypes.indexOf(dataType);
                        if (index === -1) {
                            $scope.selectedDataTypes.push(dataType);
                        } else {
                            $scope.selectedDataTypes.splice(index, 1);
                        }
                    }                   

                    function selectAllDataTypes() {
                        $scope.selectedDataTypes.length = 0;
                        if ($scope.mainCheckboxSelected) {
                            // select all
                            angular.forEach($scope.availableDataTypes, function(dataType) {
                                $scope.selectDataType(dataType.dataTypeVisId);
                            });                         
                        }
                    }

                    $scope.mainCheckboxSelected = false;

                    // formatter to add checkboxes to boolean columns http://wijmo.com/topic/adding-a-new-checkbox-in-header-column-name-of-the-flex-grid/
                    function itemFormatter(panel, r, c, cell) {
                        var flex = panel.grid;
                        if (panel.cellType == wijmo.grid.CellType.ColumnHeader) {
                            var col = flex.columns[c];

                            if (col.name == "checked") {
                                // prevent sorting on click
                                col.allowSorting = false;

                                // count true values to initialize checkbox
                                // var cnt = 0;
                                // for (var i = 0; i < flex.rows.length; i++) {
                                //     var dataTypeVisId = flex.getCellData(i, 0, true);                                 
                                //     if ($scope.isSelectedDataType(dataTypeVisId)) cnt++;
                                // }

                                // create and initialize checkbox
                                cell.innerHTML = '<input type="checkbox"> ' + cell.innerHTML;
                                var cb = cell.firstChild;
                                cb.checked = $scope.mainCheckboxSelected;//cnt > 0;
                                // cb.indeterminate = cnt > 0 && cnt < flex.rows.length;

                                // apply checkbox value to cells
                                cb.addEventListener('click', function (e) {
                                    flex.beginUpdate();
                                    $scope.mainCheckboxSelected = !$scope.mainCheckboxSelected;
                                    $scope.selectAllDataTypes();
                                    flex.endUpdate();
                                });
                            }
                        } else if (panel.cellType == wijmo.grid.CellType.Cell) {
                            var col = panel.columns[c],
                                html = cell.innerHTML;
                            if (col.name == "checked") {
                                var dataTypeVisId = flex.getCellData(r, 0, true);
                                var checked = ($scope.isSelectedDataType(dataTypeVisId)) ? "checked" : "";
                                html = '<input type="checkbox" name="externalSystemCheckbox" ' + checked + ' ng-click="selectDataType(\'' + dataTypeVisId + '\'); $event.stopPropagation();" />';
                                if (html != cell.innerHTML) {
                                    cell.innerHTML = html;
                                    $compile(cell)($scope);
                                }
                            }    
                        }
                    };

                    $scope.$watch('availableDataTypes.length', function() {
                        if (angular.isDefined($scope.availableDataTypes) && $scope.availableDataTypes.length > 0) {
                            collectionView.sourceCollection = $scope.availableDataTypes;
                        }
                    });

                    $scope.$watch('ctx.flex', function() {
                        var flex = $scope.ctx.flex;
                        if (flex) {
                            flex.isReadOnly = true;
                            flex.selectionMode = "Row";
                            flex.headersVisibility = "Column";
                            flex.itemFormatter = itemFormatter;
                        }
                    });

                    function filterFunction(item) {
                        var f = $scope.ctx.filter;
                        if (f && item) {

                            // split string into terms to enable multi-field searches such as 'us gadget red'
                            var terms = f.toUpperCase().split(' ');

                            // look for any term in any string field
                            for (var i = 0; i < terms.length; i++) {
                                var termFound = false;
                                for (var key in item) {
                                    var value = item[key];
                                    if (angular.isString(value) && value.toUpperCase().indexOf(terms[i]) > -1) {
                                        termFound = true;
                                        break;
                                    }
                                }

                                // fail if any of the terms is not found
                                if (!termFound) {
                                    return false;
                                }
                            }
                        }

                        // include item in view
                        return true;
                    }

                    // apply filter (applied on a 500 ms timeOut)
                    var toFilter;
                    $scope.$watch('ctx.filter', function() {
                        if (toFilter) {
                            clearTimeout(toFilter);
                        }
                        toFilter = setTimeout(function() {
                            toFilter = null;
                            if ($scope.ctx.flex) {
                                $scope.selectedDataTypes.length = 0;

                                var cv = $scope.ctx.flex.collectionView;
                                if (cv) {
                                    if (cv.filter != filterFunction) {
                                        cv.filter = filterFunction;
                                    } else {
                                        cv.refresh();
                                    }
                                    $scope.$apply('ctx.flex.collectionView');
                                }
                            }
                        }, 500);
                    });

                    // $rootScope.$on("CoreCommonsService:ModalResize", function() {
                    //  $(".flat-form-grid").css("maxHeight", "100%");
                    //  var flex = $scope.ctx.flex;
                    //  if (flex) {
                    //      flex.refresh();
                    //  }
                    // });

                    // $rootScope.$on("CoreCommonsService:CanTryToRefreshFlex", function() {
                    //  $(".flat-form-grid").css("maxHeight", "100%");
                    //  var flex = $scope.ctx.flex;
                    //  if (flex) {
                    //      flex.refresh();
                    //  }
                    // });
                }
            ]
        };
    }
})();