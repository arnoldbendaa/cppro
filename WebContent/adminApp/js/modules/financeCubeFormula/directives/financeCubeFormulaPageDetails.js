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
        .module('adminApp.financeCubeFormulaPage')
        .directive('financeCubeFormulaDetails', financeCubeFormulaDetails);

    /* @ngInject */
    function financeCubeFormulaDetails($rootScope, $modal, $http, Flash, FinanceCubeFormulaPageService, DataTypesPageService, $timeout, CoreCommonsService, $compile) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'financeCubeFormula/views/financeCubeFormulaDetails.html',
            scope: {
                financeCubeId: '=financeCube',
                modelId: '=model',
                financeCubeFormulaId: '=financeCubeFormula'
            },
            replace: true,
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".finance-cube-formula-details").closest(".modal-dialog");
                    var elementToResize = $(".deployments-grid");
                    var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3"), 45];
                    $scope.cookieName = "adminPanel_modal_financeCubeFormula";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    
                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize);
                    }, 0);

                    $scope.isError = false;
                    $scope.messageError = "";
                    $scope.financeCube = null;
                    $scope.messageError = "";
                    $scope.isDataLoaded = false;
                    $scope.validation = {
                        visualId: "",
                        description: "",
                        formulaEnabled: ""
                    };
                    $scope.dimensionsTable = [];
                    $scope.selectedLineIndex = null;
                    $scope.formulaDeploymentLine = [];
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.compile = compile;
                    $scope.closeModal = closeModal;
                    $scope.save = save;
                    $scope.add = add;
                    $scope.remove = remove;
                    $scope.selectionChangedHandler = selectionChangedHandler;
                    
                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    };
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    };
                    
                    if ($scope.financeCubeId != -1) {
                        $scope.isDataLoaded = false;
                        $scope.isModelChoosed = true;
                        $scope.financeCube = FinanceCubeFormulaPageService.getFinanceCubeFormulaDetailsFromDatabase($scope.modelId, $scope.financeCubeId, $scope.financeCubeFormulaId);
                        $scope.operator = "save";
                    } else {
                        /**
                         * Open modal for choosing finance cube while $scope.financeCubeId == -1
                         * 
                         * @param {[type]} $scope [description]
                         * @param {Object} $modalInstance) { $scope.selectedCube [description]
                         * @return {[type]} [description]
                         */
                        var modalInstance = $modal.open({
                            template: '<finance-cube-chooser selected-cube="selectedCube" choose="choose()" close-modal="closeModal()"></finance-cube-chooser>',
                            windowClass: 'sub-system-modals softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.selectedCube = {};

                                    $scope.choose = function() {
                                        $modalInstance.close($scope.selectedCube);
                                    };

                                    $scope.closeModal = function() {
                                        $modalInstance.dismiss('cancel');
                                    };
                                }
                            ]
                        });
                        /**
                         * Set properties for new finance cube formula , and gets dimension header for table
                         */
                        modalInstance.result.then(function(selectedCube) {
                            $scope.financeCube = FinanceCubeFormulaPageService.createEmptyFinanceCube($scope.modelId);
                            $scope.financeCube.financeCube.financeCubeVisId = selectedCube.financeCubeVisId;
                            $scope.financeCube.model.modelVisId = selectedCube.model.modelVisId;
                            $scope.financeCube.model.modelId = selectedCube.model.modelId;
                            $scope.modelId = selectedCube.model.modelId;
                            $scope.financeCube.financeCube.financeCubeId = selectedCube.financeCubeId;
                            $scope.financeCube.dimensions = FinanceCubeFormulaPageService.getDimensionsHeader($scope.modelId);

                        }, function() {
                            $scope.closeModal();
                        });

                        $scope.isModelChoosed = true;
                    }
                    /**
                     * Try to compile inserted formula
                     * 
                     * @return {[type]} [description]
                     */
                    function compile() {
                        FinanceCubeFormulaPageService.compileFormula($scope.modelId, $scope.financeCube.financeCube.financeCubeId, $scope.financeCubeFormulaId, $scope.financeCube.formulaText, $scope.financeCube.type);
                    }
                    $scope.isDataLoaded = true;
                    /**
                     * Close main modal
                     * 
                     * @return {[type]} [description]
                     */

                    function closeModal() {
                        $rootScope.$broadcast('FinanceCubeFormulaDetails:close');
                    };

                    /**
                     * Try to update changed Finance Cube
                     */
                    function save() {
                        $rootScope.$broadcast("modal:blockAllOperations");
                        FinanceCubeFormulaPageService.save($scope.financeCube);
                    };
                    /**
                     * Open model for choosing deployment line
                     */

/*
 * $scope.edit = function(lineIndex) { var lineObject = null; angular.forEach($scope.formulaDeploymentLine, function(line) { if (line.lineIndex == lineIndex) { lineObject = line; } });
 * 
 * var selectedCcTree = []; var selectedExpTree = []; var selectedCallTree = []; var selectedDataType = []; var previousSelectedDataTypes = lineObject.dataTypes; angular.forEach(previousSelectedDataTypes, function(elem) { selectedDataType.push({ structureElementVisId: elem.dataTypeVisId }); });
 * 
 * angular.forEach(lineObject.deploymentDimensionEntries, function(elem) { switch (elem.dimension.type) { case 1: selectedExpTree.push({ structureElementVisId: elem.structureElementVisId, }); break; case 2: selectedCcTree.push({ structureElementVisId: elem.structureElementVisId, }); break; case 3:
 * selectedCallTree.push({ structureElementVisId: elem.structureElementVisId, }); break; } });
 * 
 * var editObj = { selectedDataType: selectedDataType, selectedExpTree: selectedExpTree, selectedCcTree: selectedCcTree, selectedCallTree: selectedCallTree };
 * 
 * $scope.add(editObj, lineObject, lineIndex);
 *  };
 */

                    // add (or edit in the future)
                    function add(editObj, line, lineIndex) {
                        var modelId = $scope.modelId;
                        var financeCubeId = $scope.financeCubeId;
                        var treesVisibility = [true, true, true, true];

                        if (editObj !== undefined) {
                            var selectedCcTree = (editObj.selectedCcTree !== undefined) ? editObj.selectedCcTree : [];
                            var selectedExpTree = (editObj.selectedExpTree !== undefined) ? editObj.selectedExpTree : [];
                            var selectedCallTree = (editObj.selectedCallTree !== undefined) ? editObj.selectedCallTree : [];
                            var selectedDataType = (editObj.selectedDataType !== undefined) ? editObj.selectedDataType : [];
                        } else {
                            var selectedCcTree = [];
                            var selectedExpTree = [];
                            var selectedCallTree = [];
                            var selectedDataType = [];
                        }

                        var modalTitle = "Cube Formula Deployment Line";
                        var modalInstance = $modal.open({
                            template: '<trees-chooser modal-title="modalTitle" selected-cc-tree="selectedCcTree" selected-exp-tree="selectedExpTree" selected-call-tree="selectedCallTree" selected-data-type="selectedDataType" model-id="modelId"  finance-cube-id="financeCubeId" multiple="multiple" ok="ok()"  trees-visibility="treesVisibility" close="close()"></trees-chooser>',
                            windowClass: 'sub-system-modals softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.modalTitle = modalTitle;
                                    $scope.selectedCcTree = selectedCcTree;
                                    $scope.selectedExpTree = selectedExpTree;
                                    $scope.selectedCallTree = selectedCallTree;
                                    $scope.selectedDataType = selectedDataType;
                                    $scope.modelId = modelId;
                                    $scope.financeCubeId = financeCubeId;
                                    $scope.multiple = true;
                                    $scope.treesVisibility = treesVisibility;

                                    $scope.ok = function() {
                                        if ($scope.selectedCcTree.length === 0 || $scope.selectedExpTree.length === 0 || $scope.selectedCallTree.length === 0 || $scope.selectedDataType.length === 0) {
                                            swal({
                                                title: "A positive selection (a tick) must be made in dimension.",
                                                text: "I will close in 3 seconds.",
                                                type: "warning",
                                                timer: 3000
                                            });
                                        } else {
                                            createListObject($scope.selectedCcTree);
                                            createListObject($scope.selectedExpTree);
                                            createListObject($scope.selectedCallTree);
                                            listandObjectDataType($scope.selectedDataType);
                                            $modalInstance.close();
                                        }

                                    };

                                    $scope.close = function() {
                                        $modalInstance.dismiss('cancel');
                                    };
                                }
                            ]
                        });
                        modalInstance.result.then(function() {
                            var length = $scope.formulaDeploymentLine.length;

                            if (lineIndex !== undefined && lineIndex >= 0){
                                // update object TODO
                                // line.dimensions = $scope.financeCube.dimensions;
                                // TODO line.deploymentDimensionEntries <- list;
                                angular.forEach(line.deploymentDimensionEntries, function(oldDimension) {
                                    angular.forEach(list, function(newDimension) {
                                        var oldDimension = null;
                                        if (oldDimension.structureElementVisId == newDimension.structureElementVisId) {
                                        }
                                    });
                                })
                                // TODO line.dataTypes <- listDataType;
                                // line.lineIndex = length;
                                // line.formulaDeploymentId = -1;
                                // $scope.formulaDeploymentLine.push(line);
                            } else {
                                // create ready object to push
                                var readyObject = {
                                    dimensions: $scope.financeCube.dimensions,
                                    deploymentDimensionEntries: list,
                                    dataTypes: listDataType,
                                    lineIndex: length,
                                    formulaDeploymentId: -1,
                                }
                                $scope.formulaDeploymentLine.push(readyObject);
                            }

                            // After press ok, clear lists, and draw table
                            list = [];
                            listDataType = [];
                            createTable();
                        }, function() {

                        });
                    };
                    /**
                     * Change selectedLists to main list with all objects
                     * 
                     * @param {[type]} listSelected [description]
                     * @return {[type]} [description]
                     */
                    var list = [];
                    var createListObject = function(objList) {
                        angular.forEach(objList, function(selectObj) {
                            selectObj.value = true;
                            list.push(selectObj);
                        });
                    };

                    /**
                     * Change selectedList to main list and do correct object
                     * 
                     * @param {[type]} listSelected [description]
                     * @return {[type]} [description]
                     */
                    var listDataType = [];
                    var listandObjectDataType = function(objList) {
                        angular.forEach(objList, function(selectObj) {
                            var obj = {
                                dataTypeVisId: selectObj.structureElementVisId,
                                dataTypeDescription: selectObj.structureElementDescription,
                                dataTypeId: selectObj.structureElementId,
                            };
                            listDataType.push(obj);
                        });
                    }

                    var formulaDeploymentLineTable = new wijmo.collections.CollectionView();

                    $scope.ctx = {
                        flex: null,
                        data: formulaDeploymentLineTable
                    };
                    /**
                     * Create table like flex collection
                     * 
                     * @return {[type]} [description]
                     */
                    var createTable = function() {
                        formulaDeploymentLineTable.sourceCollection.length = 0;
                        for (var j = 0; j < $scope.formulaDeploymentLine.length; j++) {
                            formulaDeploymentLineTable.sourceCollection.push($scope.formulaDeploymentLine[j]);
                        }
                        $timeout(function() {
                            $scope.ctx.data.refresh();
                            $scope.isDataLoaded = true;
                            $timeout(function() {
                                // CoreCommonsService.resizeModalElement(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx);
                            }, 0);
                        }, 1000)
                    };
                    /**
                     * Remove selected line
                     * 
                     * @return {[type]} [description]
                     */
                    function remove() {
                        if ($scope.selectedLineIndex != -1) {
                            for (var i = 0; i < $scope.formulaDeploymentLine.length; i++) {
                                if ($scope.selectedLineIndex == $scope.formulaDeploymentLine[i].lineIndex) {
                                    $scope.formulaDeploymentLine.splice(i, 1);
                                    createTable();
                                }
                            }
                        } else {
                            return;
                        }

                    };


                    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * CUSTOM CELLS * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                    function itemFormatter(panel, r, c, cell) {
                        if (panel.cellType == wijmo.grid.CellType.Cell) {
                            var col = panel.columns[c],
                                html = cell.innerHTML;

                            switch (col.name) {
                                case 'index':
                                    html = "" + (r + 1);
                                    break;
                                case 'buttons':
                                    html = '<button type="button" class="btn btn-warning btn-xs" ng-click="edit(' + panel.rows[r].dataItem['lineIndex'] + ')">Edit</button>';
                                    break;
                                case 'dataType':
                                    html = formatDataType(panel.rows[r].dataItem['dataTypes']);
                                    break;
                                case 'cc':
                                    html = formatDeploymentDimensionEntries(panel.rows[r].dataItem['deploymentDimensionEntries'], 2);
                                    break;
                                case 'exp':
                                    html = formatDeploymentDimensionEntries(panel.rows[r].dataItem['deploymentDimensionEntries'], 1);
                                    break;
                                case 'cal':
                                    html = formatDeploymentDimensionEntries(panel.rows[r].dataItem['deploymentDimensionEntries'], 3);
                                    break;
                            }

                            if (html != cell.innerHTML) {
                                cell.innerHTML = html;
                                $compile(cell)($scope);
                            }
                        }
                    }
                    /**
                     * Draw multiple values as string
                     * 
                     * @param {[type]} value [description]
                     * @return {[type]} [description]
                     */
                    var formatDataType = function(value) {
                        var narratives = "";
                        var pointer = ", ";
                        for (var i = 0; i < value.length; i++) {
                            if (value.length > 1) {
                                narratives = value[i].dataTypeVisId + " - " + value[i].dataTypeDescription + pointer + narratives;
                            } else {
                                narratives = value[i].dataTypeVisId + " - " + value[i].dataTypeDescription;
                            }

                        }
                        return narratives;
                    }
                    var formatDeploymentDimensionEntries = function(value, type) {
                        var narratives = "";
                        var pointer = ", ";
                        var counter = 0;
                        for (var i = 0; i < value.length; i++) {
                            if (value[i].dimension.type == type) {
                                counter++;
                                if (counter > 1) {
                                    narratives = value[i].structureElementVisId + pointer + narratives;
                                } else {
                                    narratives = value[i].structureElementVisId;
                                }
                            }
                        }
                        return narratives;
                    }

                    /**
                     * Draw multiple values as string, type cc 2, exp 1, cal 3
                     * 
                     * @param {[type]} value [description]
                     * @return {[type]} [description]
                     */
                    function selectionChangedHandler() {
                        var flex = $scope.ctx.flex;
                        var current = flex.collectionView ? flex.collectionView.currentItem : null;
                        if (current != null) {
                            $scope.selectedLineIndex = current.lineIndex;

                        } else {
                            $scope.selectedLineIndex = -1;

                        }
                    }


                    /** ****************************************************** WATCHERS ******************************************************* */
                    $scope.$watch('ctx.flex', function() {
                        var flex = $scope.ctx.flex;
                        if (flex) {
                            flex.selectionMode = "Row";
                            flex.headersVisibility = "Column";
                            flex.itemFormatter = itemFormatter;
                            flex.groupHeaderFormat = "{value}";
                            flex.allowSorting = false;
                            $timeout(function() { // timeout is necessary to pass asynchro
                                CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                            }, 0);
                        }
                    });


                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.financeCube;
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if newValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                                $scope.isDataLoaded = true;
                                $scope.formulaDeploymentLine = newValue.formulaLine;
                                if ($scope.isDataLoaded) {
                                    createTable();
                                }
                            }
                        }
                    );



                    /**
                     * Update: On error after http->success() with error=true or http->error()
                     * 
                     * @param {[type]} event
                     * @param {Object} data [ResponseMessage]
                     */
                    $scope.$on("FinanceCubeFormulaDetails:financeCubesUpdatedError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        // error from method http->success() and from field "error"
                        // clear previous validation messages
                        $scope.isError = true;
                        Flash.create('danger', data.message);
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                        // set new messages
                        angular.forEach(data.fieldErrors, function(error) {
                            if (error.fieldName in $scope.validation) {
                                $scope.validation[error.fieldName] = error.fieldMessage;
                            }
                        });
                    });

                    $scope.$on("FinanceCubeFormulaDetails:close", function(event, data) {
                        if (typeof data != "undefined") {
                            if (data.success) {
                                if (data.method == "POST") {
                                    var operation = "created";
                                } else if (data.method == "PUT") {
                                    var operation = "updated";
                                }
                                Flash.create('success', "Finance Cube Formula (" + $scope.financeCube.financeCubeFormulaVisId + ") is " + operation + ".");
                            }
                        }
                    });

                }
            ]
        };
    }
})();