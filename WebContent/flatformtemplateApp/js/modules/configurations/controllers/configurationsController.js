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
        .module('flatFormTemplateApp.configurations')
        .controller('ConfigurationsController', ConfigurationsController);

    /* @ngInject */
    function ConfigurationsController($rootScope, $scope, $compile, $modal, ConfigurationsService, CoreCommonsService, FlatFormTemplateCommonsService, $timeout) {

        // ------------------- resize left pane, right pane (with flex)
        var windowResizeNamespace = "configurations";
        var rightPane = $(".configurations.paneContainer .rightPane");
        var leftPane = $(".configurations.paneContainer .leftPane");
        var additionElements = [60];
        var loader = $(".frontend-cover.paneCover");
        var flexToResize = $(".configurations.paneContainer .rightPane .flexToResize");
        var isTotalAll = false;
        var editConfigurationDetailsElement = {};
        var selectTreeElement = {};
        var changedName = "";
        var createdSheets = new wijmo.collections.CollectionView();
        var dimensions = [];
        var totals = [];
        var startRow;

        $timeout(function() {
            FlatFormTemplateCommonsService.allowResizingPane(leftPane, rightPane, $scope.ctx, flexToResize, additionElements, windowResizeNamespace);
        }, 0);

        selectTreeElement.original = {};
        selectTreeElement.original.configurationVisId = "";
        $scope.configurationDetails = null;
        $scope.nodeNameInput = "";
        $scope.isConfigurationEdit = false;
        $scope.editMod = false; // false - edit name; true - general edit.
        $scope.availableOperations = {};
        $scope.contextMenu = {};
        $scope.readyTrees = {};
        $scope.readyTree = readyTree;
        $scope.showHideLoader = showHideLoader;
        $scope.openModalTree = openModalTree;
        $scope.openModalAddTotal = openModalAddTotal;
        $scope.addAllDimensions = addAllDimensions;
        $scope.fixIndex = fixIndex;
        $scope.refreshTable = refreshTable;
        $scope.deleteRow = deleteRow;
        $scope.changeSelection = changeSelection;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.moveRow = moveRow;
        $scope.editTotal = editTotal;
        $scope.cancel = cancel;
        $scope.rename = rename;
        $scope.edit = edit;
        $scope.deleteTreeElement = deleteTreeElement;
        $scope.saveConfChanges = saveConfChanges;
        $scope.selectNode = selectNode;
        $scope.insertTreeElement = insertTreeElement;
        $scope.updateData = updateData;
        $scope.openTreeElement = openTreeElement;
        $scope.isSelectedHide = isSelectedHide;
        $scope.selectHide = selectHide;
        $scope.selectAllHide = selectAllHide;
        $scope.insertTreeElementOnPosition = insertTreeElementOnPosition;
        $scope.openExcludeDimensions = openExcludeDimensions;
        $scope.openModalExcludeDimensions = openModalExcludeDimensions;
        activate();

        /************************************************** IMPLEMENTATION *************************************************************************/

        function activate() {
            for (var i = 1; i <= 5; i++) {
                additionElements.push(rightPane.find(".getToCalc" + i));
            }
            $scope.typesConfig = FlatFormTemplateCommonsService.getTypesConfig("configurations");
            $scope.configurationsTreeAjaxUrl = ConfigurationsService.getConfigurationsFromDatabaseUrl();
            $rootScope.$broadcast('veil:hide');
            FlatFormTemplateCommonsService.showHideMenuLoader(true);
            CoreCommonsService.askIfReload = false;
        }

        $scope.readyTrees = {
            configurations: false,
        };

        // sometimes Veil (loader) doesn't disappear, so we hide it when tree is ready too
        $scope.$watch("readyTrees", function(newValue, oldValue) {
            if (newValue.configurations) {
                $rootScope.$broadcast('veil:hide');
                $scope.showHideLoader(false);
                FlatFormTemplateCommonsService.showHideMenuLoader(false);
            }
        }, true);

        $scope.availableOperations = { // to enable/disable jsTree Context Menu operations and Buttons above tree
            createDirectory: false,
            createConfiguration: false,
            createConfigurationAfter: true,
            createConfigurationBefore: true,
            edit: false,
            delete: false,
            rename: false
        };

        $scope.$watch("availableOperations", function(newValue, oldValue) {
            $scope.contextMenu.CreateDirectory._disabled = !newValue.createDirectory;
            $scope.contextMenu.CreateConfiguration._disabled = !newValue.createConfiguration;
            $scope.contextMenu.CreateConfigurationAfter._disabled = !newValue.createConfigurationAfter;
            $scope.contextMenu.CreateConfigurationBefore._disabled = !newValue.createConfigurationBefore;
            $scope.contextMenu.Edit._disabled = !newValue.edit;
            $scope.contextMenu.Rename._disabled = !newValue.rename;
            $scope.contextMenu.Delete._disabled = !newValue.delete;
        }, true);



        function readyTree(e, data) {
            $scope.jsTreeDomElement = angular.element("js-tree#manageConfigurationsTree");
            $scope.tree = $($scope.jsTreeDomElement).jstree();
            $scope.treeInst = $.jstree.reference($scope.jsTreeDomElement);
            $scope.tree.settings.core.multiple = false;
            $scope.readyTrees.configurations = true;
            $scope.$apply();

            // handle double click (edit)
            $scope.jsTreeDomElement.on("dblclick", "li a", function() {
                if ($(this).find("i.directory").length === 0) {
                    $scope.edit(data);
                }
            });
        }

        // -------------------------------------------------------
        // 
        // 



        function showHideLoader(show, withIcon) {
            FlatFormTemplateCommonsService.showHidePaneLoader(loader, show, withIcon);
        }



        $scope.ctx = {
            flex: null,
            filter: '',
            data: createdSheets
        };

        function openModalTree() {
            var modalInstance = $modal.open({
                template: '<configurations-tree dimensions="dimensions" created-sheets="createdSheets"></configurations-tree>',
                windowClass: 'softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.dimensions = dimensions;
                        $scope.createdSheets = createdSheets;
                        $scope.$on('ConfigurationsTree:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        }

        function openModalExcludeDimensions(openingDimension) {
            var modalInstance = $modal.open({
                template: '<configurations-exclude-dimensions opening-dimension="openingDimension"></configurations-exclude-dimensions>',
                windowClass: 'softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.openingDimension = openingDimension;
                        $scope.$on('ConfigurationsExcludeDimensions:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        }

        $scope.$on('ConfigurationsExcludeDimensions:close', function(event, args) {
            $scope.ctx.data.refresh();
        });

        function openModalAddTotal(indexTotal) {
            var modalInstance = $modal.open({
                template: '<configurations-add-total index-total="indexTotal" totals="totals" dimensions="dimensions" created-sheets="createdSheets"></configurations-add-total>',
                windowClass: 'softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.indexTotal = indexTotal;
                        $scope.totals = totals;
                        $scope.createdSheets = createdSheets;
                        $scope.dimensions = dimensions;
                        $scope.$on('ConfigurationsAddTotal:close', function(event, args) {
                            $modalInstance.close();
                            fixIndex();
                        });
                    }
                ]
            });
        }

        function addAllDimensions() {
            CoreCommonsService.askIfReload = true;
            var isGrandTotal = false;
            for (var i = 0; i < createdSheets.sourceCollection.length; i++) {
                if (createdSheets.sourceCollection[i].grandTotal !== undefined && createdSheets.sourceCollection[i].grandTotal === true) {
                    isGrandTotal = true;
                    break;
                }
            }
            if (!isGrandTotal) {
                var sheet = {
                    sheetName: 'Grand Total',
                    dimensionList: [],
                    grandTotal: true,
                    index: 1,
                    hidden: false,
                    totalUUID: null
                };
                createdSheets.sourceCollection.unshift(sheet);
                totals.unshift(sheet);
                $scope.refreshTable();
                $scope.fixIndex();
            } else {
                swal({
                    title: "You can't create Grand Total",
                    text: "because already exist under the name: " + createdSheets.sourceCollection[i].sheetName,
                    type: "warning",
                });
            }
            $scope.fixIndex();
        }

        function fixIndex() {
            for (var i = 0; i < createdSheets.sourceCollection.length; i++) {
                if (angular.isDefined(createdSheets.sourceCollection[i].totalUUID)) {
                    if (totals.length !== 0) {
                        for (var j = 0; j < totals.length; j++) {
                            if (createdSheets.sourceCollection[i].sheetName === totals[j].sheetName) {
                                totals[j].index = (i + 1);
                            }
                        }
                    }
                } else {
                    if (dimensions.length !== 0) {
                        for (var j = 0; j < dimensions.length; j++) {
                            if (createdSheets.sourceCollection[i].dimensionVisId === dimensions[j].dimensionVisId && createdSheets.sourceCollection[i].modelVisId === dimensions[j].modelVisId) {
                                dimensions[j].index = (i + 1);
                            }
                        }
                    }
                }
            }
        }

        function refreshTable() {
            createdSheets.refresh();
        }

        function deleteRow(index) {
            CoreCommonsService.askIfReload = true;
            if (angular.isDefined($scope.ctx.data.sourceCollection[(index)].dimensionVisId)) {

                var tmpModelVisId = $scope.ctx.data.sourceCollection[(index)].modelVisId;
                var tmpDimensionVisId = $scope.ctx.data.sourceCollection[(index)].dimensionVisId;

                for (var l = 0; l < dimensions.length; l++) {
                    if (dimensions[l].modelVisId === tmpModelVisId && dimensions[l].dimensionVisId === tmpDimensionVisId) {
                        dimensions.splice(l, 1);
                    }
                }


                for (var i = 0; i < $scope.ctx.data.sourceCollection.length; i++) {
                    if ((angular.isDefined($scope.ctx.data.sourceCollection[i].totalUUID) && $scope.ctx.data.sourceCollection[i].dimensionList !== null)) { //sprawdzam czy jestem ustawiony na totalu i sprawdzam czy total zawiera jakies dimensiony
                        for (var j = 0; j < $scope.ctx.data.sourceCollection[i].dimensionList.length; j++) {
                            var a = $scope.ctx.data.sourceCollection[i].dimensionList[j].modelVisId;
                            var b = $scope.ctx.data.sourceCollection[i].dimensionList[j].dimensionVisId;
                            if (a == tmpModelVisId && b == tmpDimensionVisId) {
                                $scope.ctx.data.sourceCollection[i].dimensionList.splice(j, 1);
                            }
                        }
                    }
                }
            }
            //Check if currently find total
            if (angular.isUndefined($scope.ctx.data.sourceCollection[(index)].dimensionVisId)) {

                var tmpSheetName = $scope.ctx.data.sourceCollection[(index)].sheetName;
                for (var l = 0; l < totals.length; l++) {
                    if (totals[l].sheetName === tmpSheetName) {
                        totals.splice(l, 1);
                    }
                }
            }
            $scope.ctx.data.sourceCollection.splice(index, 1);
            $scope.fixIndex();
            $scope.refreshTable();
        }


        function changeSelection(direction) {
            var flex = $scope.ctx.flex;
            var selectedCellRange = flex.selection;
            if (direction === "prev" && selectedCellRange.row !== 0) {
                selectedCellRange.row = selectedCellRange.row - 1;
            } else if (direction == "next") {
                selectedCellRange.row = selectedCellRange.row + 1;
            }
            flex.select(selectedCellRange, true);
        }


        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            $scope.current = current;
        }

        function moveRow(rowNumber, direction) {
            CoreCommonsService.askIfReload = true;
            var flex = $scope.ctx.flex;
            var selectedCellRange = flex.selection;
            if (direction === -1 && selectedCellRange.row !== 0) {
                selectedCellRange.row = selectedCellRange.row - 1;
            } else if (direction == 1) {
                selectedCellRange.row = selectedCellRange.row + 1;
            }
            flex.select(selectedCellRange, true);

            var sourceCollection = $scope.ctx.data.sourceCollection;
            var sheet = sourceCollection.splice(rowNumber, 1)[0];

            var toggledSheet = (direction > 0) ? sourceCollection[rowNumber] : sourceCollection[rowNumber - 1];
            toggledSheet.index = toggledSheet.index - direction;

            sourceCollection.splice(rowNumber + direction, 0, sheet);
            var clickedSheet = sourceCollection[rowNumber + direction];
            clickedSheet.index = toggledSheet.index + direction;

            $scope.refreshTable();
        }

        /**
         * Function start when user start drag row from table. Function save start position of row in table and createdSheets.sourceCollection.
         */
        function onDraggingRow(param1) {
            startRow = param1.row;
            return true;
        }

        /**
         * Function start when user start drop row on table. Function replace element in createdSheets.sourceCollection, and fix index.
         */
        function onDraggedRow(param1) {
            var finishRow = param1.row;
            var moveElement;

            moveElement = createdSheets.sourceCollection[startRow];
            createdSheets.sourceCollection.splice(startRow, 1);
            createdSheets.sourceCollection.splice(finishRow, 0, moveElement);
            fixIndex();

            return true;
        }

        function editTotal(indexTotal) {
            $scope.openModalAddTotal(indexTotal);
        }

        function openExcludeDimensions(indexDimension) {
            var openingDimension;
            for (var i = 0; i < dimensions.length; i++) {
                if (dimensions[i].index === (indexDimension + 1)) {
                    openingDimension = dimensions[i];
                }
            }
            $scope.openModalExcludeDimensions(openingDimension);
        }

        function cancel() {
            $scope.isConfigurationEdit = false;
            $scope.editMod = false;
            $scope.showHideLoader(false);
            CoreCommonsService.askIfReload = false;
        }

        /**
         * This scope is showing context menu
         * @type {Object}
         */
        $scope.contextMenu = {
            "CreateDirectory": {
                "label": "Create Directory",
                "action": function(data) {
                    $scope.insertTreeElement("directory");
                }
            },
            "CreateConfiguration": {
                "label": "Create configuration",
                "action": function(data) {
                    $scope.insertTreeElement("configuration");
                },
                separator_after: true
            },
            "CreateConfigurationBefore": {
                "label": "Create Configuration Before",
                "action": function(data) {
                    $scope.insertTreeElementOnPosition(false);
                }
            },
            "CreateConfigurationAfter": {
                "label": "Create Configuration After",
                "action": function(data) {
                    $scope.insertTreeElementOnPosition(true);
                },
                separator_after: true
            },
            "Delete": {
                "label": "Delete",
                "action": function(data) {
                    $scope.deleteTreeElement();
                }
            },
            "Edit": {
                "label": "Edit",
                "action": function(data) {
                    $scope.edit(data);
                    $scope.$apply();
                }
            },
            "Rename": {
                "label": "Rename",
                "action": function(data) {
                    $scope.rename();
                    $scope.$apply();
                }
            }
        };

        $scope.dnd = function(e, data) {
            console.log("e", e);
            console.log("data", data);

            var newParentObject = findParent(data.node.parent, data.instance._model.data);

            var moveEvent = {
                configurationUUID: data.node.original.configurationUUID,
                oldParentUUID: data.node.original.parentUUID,
                newParentUUID: newParentObject.original.configurationUUID,
                newIndex: data.position,
                oldIndex: data.old_position
            };

            ConfigurationsService.updatePosition(moveEvent, data.node);
        };

        /**
         * This function returns parent for selected node
         *
         * @param {[type]} parentId [description]
         * @param {[type]} availableTreeNodes [description]
         * @return {[type]} [description]
         */
        var findParent = function(parentId, availableTreeNodes) {
            var parentObject = null;
            angular.forEach(availableTreeNodes, function(treeNode) {


                if (treeNode.parent !== null) {
                    if (parentId === treeNode.id) {
                        parentObject = treeNode;
                        return false;
                    }
                }
            });
            return parentObject;
        };

        function rename() {
            $scope.showHideLoader(true, false);
            $scope.editMod = false;
            $scope.isConfigurationEdit = true;
            $scope.nodeNameInput = selectTreeElement.original.configurationVisId;
            $rootScope.$broadcast('veil:hide');
        }

        function edit(data) {
            $rootScope.$broadcast('veil:show');
            $scope.showHideLoader(true, false);
            var inst = $.jstree.reference(data.reference),
                obj = selectTreeElement;
            $scope.openTreeElement(obj, inst);
            $scope.isConfigurationEdit = true;
            $scope.editMod = true;
            $scope.nodeNameInput = selectTreeElement.original.configurationVisId;
            $rootScope.$broadcast('veil:hide');
        }

        function deleteTreeElement() {
            $scope.showHideLoader(true);
            var inst = $scope.treeInst;
            var obj = selectTreeElement;
            if (obj.original.parentUUID === null) {
                swal({
                    title: "You can't delete",
                    text: "main directory.",
                    type: "warning",
                });
                $scope.showHideLoader(false);
            } else {
                var message = "";
                if (obj.type == "directory") {
                    message = "Directory \"" + obj.text + "\" with its all Directories and Configurations?";
                } else if (obj.type == "configuration ") {
                    message = "Configuration\"" + obj.text + "\"?";
                }
                swal({
                    title: "Are you sure",
                    text: "you want to delete " + message,
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#d9534f"
                }, function(isConfirm) {
                    if (isConfirm) {
                        var responseData = {
                            inst: inst,
                            obj: obj
                        };
                        ConfigurationsService.deleteTreeElement(responseData);
                    } else {
                        $scope.showHideLoader(false);
                    }
                });
            }
        }

        function saveConfChanges() {
            if ($scope.editMod) {
                $rootScope.$broadcast('veil:show');
                var obj = editConfigurationDetailsElement;
                obj.totals = totals;
                obj.dimensions = dimensions;
                obj.configurationVisId = $scope.nodeNameInput;
                changedName = $scope.nodeNameInput;

                if (obj.totals !== undefined) {
                    for (var i = 0; i < obj.totals.length; i++) {
                        delete obj.totals[i].totalID;
                    }
                }
                if (obj.dimensions !== undefined) {
                    for (var i = 0; i < obj.dimensions.length; i++) {
                        delete obj.dimensions[i].dimensionId;
                        delete obj.dimensions[i].oldDimension;
                        delete obj.dimensions[i].toSave;
                    }
                }
                ConfigurationsService.updateTreeElement(obj);
            } else {
                $scope.showHideLoader(false);
                var obj = selectTreeElement.original;
                obj.configurationVisId = $scope.nodeNameInput;
                changedName = $scope.nodeNameInput;
                ConfigurationsService.updateNameTreeElement(obj);
            }
        }

        function selectNode(e, data) {
            selectTreeElement = data.node;
            $scope.data = data;

            if (selectTreeElement.original.parentUUID !== null) { //if not root
                $scope.availableOperations.delete = true;
                $scope.availableOperations.rename = true;
                $scope.availableOperations.createConfigurationAfter = true;
                $scope.availableOperations.createConfigurationBefore = true;
            } else { //if root
                $scope.availableOperations.delete = false;
                $scope.availableOperations.rename = false;
                $scope.availableOperations.createConfigurationAfter = false;
                $scope.availableOperations.createConfigurationBefore = false;
            }

            if (selectTreeElement.original.directory) { //if directory
                $scope.availableOperations.createDirectory = true;
                $scope.availableOperations.createConfiguration = true;
                $scope.availableOperations.edit = false;
            } else {
                $scope.availableOperations.createDirectory = false;
                $scope.availableOperations.createConfiguration = false;
                $scope.availableOperations.edit = true;
            }

            $scope.renameMode = false;
            //if called from contex menu then use apply
            $scope.$apply();
        }

        function insertTreeElement(type) {
            $scope.showHideLoader(true);
            var obj = selectTreeElement;
            if (obj.type != "directory") {
                swal({
                    title: "You can't create",
                    text: "new element here.",
                    type: "warning",
                });
            } else {
                var directory = (type === "directory");
                var newNode;
                if (directory) {
                    newNode = ConfigurationsService.generateEmptyFolder();
                } else {
                    newNode = ConfigurationsService.getEmptyEditTreeElement();
                }
                newNode.parentUUID = obj.original.configurationUUID;
                newNode.tree_index = obj.children.length;
                newNode.directory = directory;
                var responseData = {
                    inst: $scope.treeInst,
                    type: type,
                    obj: obj
                };

                ConfigurationsService.insertTreeElement(newNode, responseData, true);
            }
        }

        function insertTreeElementOnPosition(after) {
            $scope.showHideLoader(true);
            var obj = selectTreeElement;
            var parentId = obj.parent;
            var parentNode = $scope.treeInst.get_node(parentId);
            var position = parentNode.children.indexOf(selectTreeElement.id) + 1;
            var newNode = ConfigurationsService.getEmptyEditTreeElement();
            newNode.parentUUID = obj.original.parentUUID;
            newNode.tree_index = position + (after ? 0 : -1);
            //newNode.type = type;
            newNode.directory = false;
            var responseData = {
                obj: obj,
                inst: $scope.treeInst,
                type: "configuration"
            };
            ConfigurationsService.insertTreeElement(newNode, responseData, false);
        }

        function updateData() {
            $rootScope.$broadcast('veil:show');
            $scope.showHideLoader(true, false);
            var obj = selectTreeElement;
            $scope.configurationDetails = ConfigurationsService.getConfigurationsDetailsFromDatabase(obj.original.configurationUUID);
            $scope.isConfigurationEdit = true;
            $scope.editMod = true;
        }

        function openTreeElement(obj, inst) {
            var responseData = {
                inst: inst,
                obj: obj
            };
            $scope.configurationDetails = ConfigurationsService.getConfigurationsDetailsFromDatabase(obj.original.configurationUUID);
        }


        $scope.$on("ConfigurationsService:deleteSuccess", function(e, responseData) {
            if (responseData.data.success) {
                responseData.inst.delete_node(responseData.obj);
                $scope.availableOperations.createConfiguration = false;
                $scope.availableOperations.createDirectory = false;
                $scope.availableOperations.edit = false;
                $scope.availableOperations.delete = false;
                $scope.availableOperations.rename = false;
            }
            $scope.showHideLoader(false); // TODO remove
        });

        $scope.$on("ConfigurationsService:insertFolderSuccess", function(e, responseData) {
            var insertedData = responseData.data;
            var inst = responseData.inst;
            var newNode = responseData.newNode;
            if (newNode.directory === true) {
                var type = "directory";
            }
            var obj = responseData.obj;

            newNode.configurationUUID = insertedData.configurationUUID;
            newNode.type = type;
            newNode.text = newNode.configurationVisId;
            var newNodeId = inst.create_node(obj, newNode, "last", function(new_node) {});
            inst.deselect_all();
            $timeout(function() {
                inst.select_node(newNodeId);
                $scope.rename();
            }, 0);
            $scope.showHideLoader(false);
            $rootScope.$broadcast('veil:hide');
        });

        $scope.$on("ConfigurationsService:insertSuccess", function(e, responseData, dependent) {
            var insertedData = responseData.data;
            var inst = responseData.inst;
            var newNode = responseData.newNode;
            var obj = responseData.obj;
            if (newNode.directory === false) {
                var type = "configuration";
            }
            if (insertedData.configurationUUID !== undefined && insertedData.configurationUUID !== null && insertedData.configurationUUID !== "") {
                newNode.configurationUUID = insertedData.configurationUUID;
                newNode.type = type;
                newNode.text = newNode.configurationVisId;

                if (dependent) {
                    var newNodeId = inst.create_node(obj, newNode, "last", function(new_node) {});
                } else {
                    var newNodeId = inst.create_node(obj.parent, newNode, newNode.tree_index, function(new_node) {});
                }

                inst.deselect_all();
                $timeout(function() {
                    inst.select_node(newNodeId);
                    $scope.rename();
                }, 0);
            }
            $scope.showHideLoader(false);
            $rootScope.$broadcast('veil:hide');
        });

        $scope.$on("ConfigurationsService:updateSuccess", function(e, responseData) {
            CoreCommonsService.askIfReload = false;
            if (responseData.success) {
                var jsTree = angular.element("js-tree");
                var tree = $(jsTree).jstree();
                var node = tree.get_node(selectTreeElement);
                $scope.updateData();
                editConfigurationDetailsElement.versionNum++;
                selectTreeElement.original.versionNum++;
                if (tree.rename_node(node, changedName)) {
                    selectTreeElement.original.configurationVisId = changedName;
                    swal({
                        title: "Configuration has been saved",
                        text: "",
                        type: "info",
                    });
                    CoreCommonsService.askIfReload = false;
                }
            }
            $rootScope.$broadcast('veil:hide');
        });

        $scope.$on("ConfigurationsService:updateNameSuccess", function(e, responseData) {
            if (responseData.success) {
                var jsTree = angular.element("js-tree");
                var tree = $(jsTree).jstree();
                var node = tree.get_node(selectTreeElement);
                selectTreeElement.original.versionNum++;
                if (tree.rename_node(node, changedName)) {
                    selectTreeElement.original.configurationVisId = changedName;
                }
                $scope.isConfigurationEdit = false;
            }
            $rootScope.$broadcast('veil:hide');
        });

        $scope.$on("ConfigurationsService:updateIndexSuccess", function(e, moveEvent, node) {
            node.original.parentUUID = moveEvent.newParentUUID;
        });

        $scope.$on("ConfigurationsService:updateIndexProblem", function(e, moveEvent) {
            Flash.create('danger', "Move element operation failed!");
            $scope.treeInst.refresh();
        });

        /** ****************************************************** hide ************************************************************* */

        function isSelectedHide(sheetName) {
            var sheet = CoreCommonsService.findElementByKey(createdSheets.sourceCollection, sheetName, 'sheetName');
            if (sheet.hidden) { // not selected
                return false;
            } else { // selected
                return true;
            }
        }

        function selectHide(sheetName) {
            var sheet = CoreCommonsService.findElementByKey(createdSheets.sourceCollection, sheetName, 'sheetName');
            var indexSheets = createdSheets.sourceCollection.indexOf(sheet);
            if (sheet.hidden) {
                createdSheets.sourceCollection[indexSheets].hidden = false;
            } else {
                createdSheets.sourceCollection[indexSheets].hidden = true;
            }
            CoreCommonsService.askIfReload = true;
        }

        function selectAllHide() {
            for (var i = 0; i < createdSheets.sourceCollection.length; i++) {
                createdSheets.sourceCollection[i].hidden = true;
            }
            if ($scope.mainCheckboxSelected) {
                // select all
                angular.forEach(createdSheets.sourceCollection, function(sheet) {
                    $scope.selectHide(sheet.sheetName);
                });
            }
        }

        /** ****************************************************** CUSTOM CELLS ******************************************************* */
        function itemFormatter(panel, r, c, cell) {
            var flex = panel.grid;

            if (panel.cellType == wijmo.grid.CellType.ColumnHeader) {
                var col = flex.columns[c];

                if (col.name == "checked") {
                    // prevent sorting on click
                    col.allowSorting = false;

                    cell.innerHTML = '<input type="checkbox"> Show' + cell.innerHTML;
                    var cb = cell.firstChild;
                    cb.checked = $scope.mainCheckboxSelected;

                    // apply checkbox value to cells
                    cb.addEventListener('click', function(e) {
                        flex.beginUpdate();
                        $scope.mainCheckboxSelected = !$scope.mainCheckboxSelected;
                        $scope.selectAllHide();
                        flex.endUpdate();
                    });

                }
            } else if ($scope.ctx.data.sourceCollection[r] && panel.cellType == wijmo.grid.CellType.Cell) {
                var col = panel.columns[c],
                    html = cell.innerHTML;


                if ($scope.ctx.data.sourceCollection[r].totalUUID) {
                    cell.className = cell.className + " total";
                }

                switch (col.name) {
                    case 'checked':

                        var sheetName = flex.getCellData(r, 1, true);
                        var checked = ($scope.isSelectedHide(sheetName)) ? "checked" : "";
                        html = '<input type="checkbox" name="externalSystemCheckbox" ' + checked + ' ng-click="selectHide(\'' + sheetName + '\'); $event.stopPropagation();" />';
                        break;
                    case 'index':
                        html = "" + (r + 1);
                        break;

                    case 'buttons':
                        if ((isTotalAll && r === 0) || (isTotalAll && r === 1 && $scope.ctx.data.sourceCollection.length === 2)) {
                            html = '';
                        } else if (((r === 0 && !isTotalAll) || (r == 1 && isTotalAll)) && $scope.ctx.data.sourceCollection.length !== 1) {
                            html = '<button ng-click="moveRow(' + r + ', 1)" type = "button" class="btn btn-defaul btn-xs"> DOWN </button>' +
                                '<button disabled type = "button" class="btn btn-defaul btn-xs"> UP';
                        } else if ((r + 1) === $scope.ctx.data.sourceCollection.length && $scope.ctx.data.sourceCollection.length !== 1) {
                            html = '<button disabled type = "button" class="btn btn-defaul btn-xs"> DOWN' +
                                '<button ng-click="moveRow(' + r + ', -1)" type = "button" class="btn btn-defaul btn-xs"> UP </button>';
                        } else if ($scope.ctx.data.sourceCollection.length !== 1) {
                            html = '<button ng-click="moveRow(' + r + ', 1)" type = "button" class="btn btn-defaul btn-xs"> DOWN </button>' +
                                '<button ng-click="moveRow(' + r + ', -1)" type = "button" class="btn btn-defaul btn-xs"> UP </button>';
                        }
                        break;

                    case 'delete':
                        if ((angular.isDefined($scope.ctx.data.sourceCollection[r].totalUUID)) && $scope.ctx.data.sourceCollection[r].sheetName !== 'Grand Total') {
                            html = '<button ng-click="deleteRow(' + r + ')" type = "button" class="btn btn-danger btn-xs"> DELETE </button>' +
                                '<button ng-click="editTotal(' + r + ')" type = "button" class="btn btn-defaul btn-xs"> EDIT </button>';
                        } else if (isTotalAll && r === 0) {
                            html = '';
                        } else {
                            html = '<button ng-click="deleteRow(' + r + ')" type = "button" class="btn btn-danger btn-xs"> DELETE </button>' +
                                '<button ng-click="editTotal(' + r + ')" type = "button" class="btn btn-defaul btn-xs"> EDIT </button>';
                        }
                        var excludedDimensions = $scope.ctx.data.sourceCollection[r].excludedDimensions;
                        if (excludedDimensions !== undefined && excludedDimensions !== null) {
                            html += '<button ng-click="openExcludeDimensions(' + r + ')" type = "button" class="btn btn-defaul btn-xs"> EXCLUDE </button>';
                        }
                        break;

                    case 'dimensionList':
                        break;
                }
                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }
        /** ******************************************************* WATCHERS ******************************************************* */



        $scope.$watch('nodeNameInput', function(newValue, oldValue) {
            if (newValue != oldValue) {
                CoreCommonsService.askIfReload = true;
            }
        }, true);



        $scope.$watch('ctx.flex', function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                var host = flex.hostElement;

                // show a message when the user double-clicks a cell
                host.addEventListener('dblclick', function(e) {

                    if ($(e.srcElement).hasClass("wj-state-selected")) {
                        var sel = flex.selection;
                        $scope.editTotal(sel.row);
                        console.log("sel row", sel.row);
                    }

                });

                // prevent FlexGrid from handling the double-click
                flex.hostElement.addEventListener('mousedown', function(e) {
                    flex.isReadOnly = true;
                });

                // restore editing when the user starts typing
                flex.hostElement.addEventListener('keydown', function(e) {
                    flex.isReadOnly = false;
                });

                flex.isReadOnly = true;
                flex.selectionMode = "Row";
                flex.itemFormatter = itemFormatter;
                flex.onDraggingRow = onDraggingRow;
                flex.onDraggedRow = onDraggedRow;
            }
        });

        $scope.$watchCollection(
            // "This function returns the value being watched. It is called for each turn of the $digest loop"
            function() {
                return $scope.configurationDetails;
            },
            // "This is the change listener, called when the value returned from the above function changes"
            function(newValue, oldValue) {

                // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                // To protect against this we check if newValue isn't empty.
                if (newValue !== oldValue && Object.keys(newValue).length !== 0) {
                    dimensions = $scope.configurationDetails.dimensions;
                    totals = $scope.configurationDetails.totals;
                    createdSheets.sourceCollection = [];
                    editConfigurationDetailsElement = $scope.configurationDetails;


                    if (totals !== undefined && dimensions !== undefined) {
                        createdSheets.sourceCollection.length = dimensions.length + totals.length;
                    }
                    var index;
                    if (totals !== undefined && totals.length !== 0) {
                        for (var i = 0; i < totals.length; i++) {
                            index = totals[i].index;
                            createdSheets.sourceCollection[index - 1] = totals[i];
                        }
                    }
                    if (dimensions !== undefined && dimensions.length !== 0) {
                        for (var i = 0; i < dimensions.length; i++) {
                            index = dimensions[i].index;
                            createdSheets.sourceCollection[index - 1] = dimensions[i];
                        }
                    }
                }
                $scope.refreshTable();
                if ($scope.ctx.flex !== null && $scope.ctx.flex !== undefined) {
                    FlatFormTemplateCommonsService.resizeFlex(rightPane, $scope.ctx, flexToResize, additionElements);
                }
            }
        );
    }

})();