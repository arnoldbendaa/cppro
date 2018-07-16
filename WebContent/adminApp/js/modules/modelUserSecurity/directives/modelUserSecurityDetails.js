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
        .module('adminApp.modelUserSecurity')
        .directive('modelUserSecurityDetails', modelUserSecurityDetails);

    /* @ngInject */
    function modelUserSecurityDetails($rootScope, $compile, Flash, ModelUserSecurityService, CoreCommonsService, UsersPageService, $timeout) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'modelUserSecurity/views/modelUserSecurityDetails.html',
            scope: {
                modelId: '=model'
            },
            replace: true,
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".security-details").closest(".modal-dialog");
                    var elementToResize = $(".elementToResize1");
                    var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3")];
                    var elementToResize2 = $(".elementToResize2");
                    var additionalElementsToCalcResize2 = [$(".getToCalc1"), $(".getToCalc2"), $(".getToCalc4"), $(".getToCalc5")];
                    var allElementsToResize = [elementToResize, elementToResize2];
                    var allAdditionalElementsToCalcResize = [additionalElementsToCalcResize, additionalElementsToCalcResize2];
                    $scope.cookieName = "adminPanel_modal_modelUser";
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.model = null;
                    $scope.isChoosedUser = false;
                    $scope.deploy = false;
                    $scope.choosedUser = {};
                    $scope.treeProcess = {};
                    //If this flag is true block update on responsibilityTable, and should block this operation when tree is loaded.
                    var initialLoadingSelected = false;
                    $scope.isDataLoaded = false;
                    var sourceUsers = {};
                    $scope.users = [];
                    var responsibilityTable = new wijmo.collections.CollectionView();
                    // Group table Model Element Access by user.
                    var groupDesc = new wijmo.collections.PropertyGroupDescription("user");
                    responsibilityTable.groupDescriptions.push(groupDesc);
                    $scope.ctx = {};
                    $scope.onChange = onChange;
                    $scope.editReadOnly = editReadOnly;
                    $scope.selectNode = selectNode;
                    $scope.deselectNode = deselectNode;
                    $scope.close = close;
                    $scope.save = save;
                    activate();

                    function activate() {
                        // try to resize and move modal based on the cookie
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                        $scope.model = ModelUserSecurityService.getModelUserSecurityDetailsFromDatabase($scope.modelId);
                        sourceUsers = UsersPageService.getUsers();
                        $scope.ctx = {
                            flex: null,
                            data: responsibilityTable
                        };
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    }

                    /**
                     * Update responsibilityTable of $scope.responsibility.
                     */
                    var createTable = function() {
                        responsibilityTable.sourceCollection.length = 0;

                        for (var j = 0; j < $scope.responsibility.length; j++) {
                            for (var i = 0; i < $scope.users.length; i++) {
                                if ($scope.users[i].userId == $scope.responsibility[j].userId) {
                                    var userElement = {
                                        user: $scope.users[i].userName + " - " + $scope.users[i].userFullName,
                                        element: $scope.responsibility[j].structureVisId + " - " + $scope.responsibility[j].description,
                                        readOnly: $scope.responsibility[j].readOnly,
                                        structureVisId: $scope.responsibility[j].structureVisId,
                                        structureDescription: $scope.responsibility[j].description,
                                        structureId: $scope.responsibility[j].structureId,
                                        structureElementId: $scope.responsibility[j].structureElementId,
                                        userId: $scope.responsibility[j].userId,
                                        userVisId: $scope.responsibility[j].userVisId
                                    };
                                    responsibilityTable.sourceCollection.push(userElement);
                                }
                            }
                        }
                        $timeout(function() {
                            $scope.ctx.data.refresh();
                            $scope.isDataLoaded = true;
                            $timeout(function() {
                                CoreCommonsService.resizeModalElement(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx);
                            }, 0);
                        }, 1000);
                    };

                    /**
                     * Find fit place in responsonilityTable and map responsibility tree user on responsibilityTable by moveSelectedIntoTheTableResponsibility.
                     */
                    var updateTreeResponsibility = function(tree) {
                        var fitIndeks = null;
                        for (var i = 0; i < responsibilityTable.sourceCollection.length; i++) {
                            if (responsibilityTable.sourceCollection[i].userVisId == $scope.choosedUser.userName) {
                                responsibilityTable.sourceCollection.splice(i, 1);
                                if (fitIndeks == null) {
                                    fitIndeks = i;
                                }
                                i--;
                            }
                        }
                        if (fitIndeks == null) {
                            for (var i = 0; i < responsibilityTable.sourceCollection.length; i++) {
                                if (responsibilityTable.sourceCollection[i].userVisId > $scope.choosedUser.userName) {
                                    fitIndeks = i;
                                    break;
                                }
                            }
                        }
                        moveSelectedIntoTheTableResponsibility(fitIndeks, tree);
                    }

                    /**
                     * Map user responsibility tree user on responsibilityTable.
                     */
                    var moveSelectedIntoTheTableResponsibility = function(fitIndeks, tree) {
                        var listOfSelectedNode = tree.get_top_checked(true);
                        for (var i = 0; i < listOfSelectedNode.length; i++) {
                            var userElement = {
                                user: $scope.choosedUser.userName + " - " + $scope.choosedUser.userFullName,
                                element: listOfSelectedNode[i].text,
                                readOnly: false,
                                structureVisId: listOfSelectedNode[i].id,
                                structureDescription: listOfSelectedNode[i].original.description,
                                structureId: listOfSelectedNode[i].original.structureId,
                                structureElementId: listOfSelectedNode[i].original.structureElementId,
                                userId: $scope.choosedUser.userId,
                                userVisId: $scope.choosedUser.userName
                            };
                            responsibilityTable.sourceCollection.splice(fitIndeks, 0, userElement);
                        }
                    }

                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }

                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }

                    /**
                     * When choosedUser is changing this function clear tree, and select checkbox by data from responsibilityTable.
                     */
                    function onChange() {
                        initialLoadingSelected = true;
                        var jsTree = angular.element("js-tree");
                        var tree = $(jsTree).jstree();
                        tree.uncheck_all();

                        $scope.isChoosedUser = true;
                        $scope.treeProcess = angular.copy($scope.model.node);

                        var table = responsibilityTable.sourceCollection;
                        for (var j = 0; j < table.length; j++) {
                            if (table[j].userId == $scope.choosedUser.userId) {
                                if ($scope.treeProcess.text == table[j].structureVisId) {
                                    tree.check_node($scope.treeProcess);
                                } else {
                                    for (var i = 0; i < $scope.treeProcess.children.length; i++) {
                                        findAndSelect(table[j].structureElementId, $scope.treeProcess.children[i], tree);
                                    }
                                }
                            }
                        }

                        initialLoadingSelected = false;
                    }

                    /**
                     * Find and select node's checkbox.
                     */
                    var findAndSelect = function(lookingNodeId, currentNode, tree) {
                        if (currentNode.structureElementId == lookingNodeId) {
                            tree.check_node(currentNode);
                            return true;
                        } else {
                            if (currentNode.children != undefined && currentNode.children.length > 0) {
                                for (var i = 0; i < currentNode.children.length; i++) {
                                    if (findAndSelect(lookingNodeId, currentNode.children[i], tree) == true) {
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                    }

                    /**
                     * Change property readOnly.
                     */
                    function editReadOnly($event) {
                        var flex = $scope.ctx.flex;
                        $scope.current = flex.collectionView ? flex.collectionView.currentItem : null;
                        $scope.current.readOnly = !$scope.current.readOnly;

                        if (flex != undefined) {
                            flex.refresh();
                        }
                    }

                    /**
                     * select treeNode checkboxes.
                     */
                    function selectNode(e, data) {
                        if (initialLoadingSelected === false) {
                            updateTreeResponsibility(data.instance);
                        }
                    }

                    /**
                     * deselect treeNode checkboxes.
                     */
                    function deselectNode(e, data) {
                        updateTreeResponsibility(data.instance);
                    }

                    /**
                     * Update modelUserElementAccess by actually responsibilityTable and model data.
                     */
                    var updateModelUserElementAccess = function() {
                        var actuallyTable = responsibilityTable.sourceCollection;
                        $scope.model.modelUserElementAccess.length = 0;
                        for (var i = 0; i < actuallyTable.length; i++) {
                            var elementAccess = {
                                description: actuallyTable[i].structureDescription,
                                modelId: $scope.model.modelId,
                                modelVisId: $scope.model.modelVisId,
                                readOnly: actuallyTable[i].readOnly,
                                structureId: actuallyTable[i].structureId,
                                structureElementId: actuallyTable[i].structureElementId,
                                structureVisId: actuallyTable[i].id,
                                userId: actuallyTable[i].userId,
                                userVisId: actuallyTable[i].userVisId
                            }
                            $scope.model.modelUserElementAccess.push(elementAccess);
                        }
                    }

                    function close() {
                        $rootScope.$broadcast('ModelUserSecurityDetails:close');
                    }

                    /**
                     * Try to update changed Model
                     */
                    function save() {
                        
                        $rootScope.$broadcast("modal:blockAllOperations");
                        updateModelUserElementAccess();
                        ModelUserSecurityService.saveModel($scope.model.modelId, $scope.model.modelUserElementAccess, $scope.deploy);
                    }

                    /** ****************************************************** CUSTOM CELLS ******************************************************* */
                    function itemFormatter(panel, r, c, cell) {
                        if (panel.cellType == wijmo.grid.CellType.Cell) {

                            var col = panel.columns[c],
                                html = cell.innerHTML;
                            switch (col.name) {
                                case 'Read Only':
                                    var readOnly = panel.rows[r].dataItem['readOnly'];
                                    if (readOnly == null) {
                                        html = '';
                                    } else if (readOnly == true) {
                                        html = '<button type="button" class="btn btn-success btn-xs" ng-click="editReadOnly($event); $event.stopPropagation();">true</button>';
                                    } else {
                                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="editReadOnly($event); $event.stopPropagation();">false</button>';
                                    }
                                    break;
                            }

                            if (html != cell.innerHTML) {
                                cell.innerHTML = html;
                                $compile(cell)($scope);
                            }
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
                                CoreCommonsService.allowResizingModal(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx);
                            }, 0);
                        }
                    });

                    $scope.$on("ModelUserSecurityDetails:modelsSecuritySaveError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        Flash.create('danger', data.message);
                    });

                    $scope.$on("ModelUserSecurityDetails:close", function(event, data) {
                        if (typeof data != "undefined") {
                            if (data.success) {
                                Flash.create('success', "Model (" + $scope.model.modelVisId + ") is updated.");
                            }
                        }
                    });

                    var isSourceUsersLoaded = false;
                    var isModelLoaded = false;

                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return sourceUsers.sourceCollection
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function() {
                            if (sourceUsers.sourceCollection.length > 0) {
                                isSourceUsersLoaded = true;
                                $scope.users = sourceUsers.sourceCollection;
                            }

                            if (isSourceUsersLoaded && isModelLoaded) {
                                $scope.isDataLoaded = true;
                                createTable();
                            }
                        }
                    );

                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.model
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if newValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                                isModelLoaded = true;
                                $scope.model = newValue;
                                $scope.responsibility = $scope.model.modelUserElementAccess;
                                $scope.treeProcess = angular.copy($scope.model.node);

                                if (isSourceUsersLoaded && isModelLoaded) {
                                    createTable();
                                }
                            }
                        }
                    );
                }
            ]
        };
    }
})();