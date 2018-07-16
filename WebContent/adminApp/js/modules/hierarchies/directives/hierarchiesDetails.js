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
        .module('adminApp.hierarchiesPage')
        .directive('hierarchyDetails', hierarchyDetails);

    /* @ngInject */
    function hierarchyDetails($rootScope, $modal, Flash, HierarchiesService, AccountService, BusinessService, $timeout, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'hierarchies/views/hierarchyDetails.html',
            scope: {
                id: '=',
                hierarchyId: '=hierarchyId',
                type: '=type' // type == false -> Business, type == true -> Account
            },
            replace: true,
            controller: ['$scope',
                function($scope) {
                
                    var modalDialog = null;
                    var elementToResize = null;
                    var additionalElementsToCalcResize = [];
                    var newElementId;
                    var nodeStaticNewElement;
                    var parentNode;
                    var position;
                    var listOfDisplayNameEvents = [];
                    
                    $timeout(function() {
                        // parameters to resize modal
                        modalDialog = $(".hierarchy-details").closest(".modal-dialog");
                        elementToResize = $(".elementToResize");
                        additionalElementsToCalcResize = [$(".in-use-label"), $(".dimension-main-data"), $(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3"), $(".edit-hier-elem"), $(".crud-buttons"), 15];
                        $scope.cookieName = "adminPanel_modal_hierarchy";
                        // try to resize and move modal based on the cookie
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                        $scope.resizedColumn = function(sender, args) {
                            CoreCommonsService.resizedColumn(args, $scope.cookieName);
                        };
                        $scope.sortedColumn = function(sender, args) {
                            CoreCommonsService.sortedColumn(args, $scope.cookieName);
                        };
                        $timeout(function() { // timeout is necessary to pass asynchro
                            CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                        }, 0);
                    }, 0);

                    var self = this;
                    $scope.isMainDataLoaded = false;
                    $scope.isError = false;
                    $scope.hierarchy = {};
                    $scope.select = {};
                    $scope.createdNode = {};
                    $scope.events = [];
                    $scope.validation = {
                        identifier: '',
                        description: '',
                        hierarchyElement: ''
                    }
                    $scope.insertBeforeAvailable = false;
                    $scope.insertAfterAvailable = false;
                    $scope.insertDependentAvailable = false;
                    $scope.deleteAvailable = false;
                    $scope.moveBeforeAvailable = false;
                    $scope.moveAfterAvailable = false;
                    $scope.title;                  
                    self.activate = activate;
                    self.activeNode = $scope.activeNode;
                    self.add = $scope.add;
                    self.manageSelecDimeElement = manageSelecDimeElement;
                    self.createTreeElement = $scope.createTreeElement;
                    self.deleteTreeElements = $scope.deleteTreeElements;
                    self.moveTreeElement = $scope.moveTreeElement;
                    self.changeValeuInNode = $scope.changeValeuInNode;
                    self.findParent = findParent;
                    self.save = $scope.save;
                    self.insertAndEdit = $scope.insertAndEdit;
                    self.readyTree = $scope.readyTree;
                    self.selectNode =  $scope.selectNode;
                    self.manageMoveButtonsAvailability = $scope.manageMoveButtonsAvailability;
                    self.createNode = $scope.createNode;
                    self.deleteNode = $scope.deleteNode;
                    self.dnd = $scope.dnd;
                    self.close = $scope.close;
                    self.getSelectedNodePosition = $scope.getSelectedNodePosition;
                    self.countOfNodesInSelectedNodeLevel = $scope.countOfNodesInSelectedNodeLevel;
                    self.changeDisplayNameAddEvent = $scope.changeDisplayNameAddEvent;
                    self.setTreeAjax = setTreeAjax;
                    self.activate();
                    function setTreeAjax(){
                    	$scope.treeAjax = '/cppro/adminPanel/hierarchies/' + $scope.id + '/' + $scope.hierarchyId + '/hierarchy/' + $scope.type;
                    }

                    function activate() {
                        console.log("modal", modalDialog);
                        if ($scope.id == -1) {
                            var type = $scope.type;
                            // Open modal while we are creating new hierarchies, and we shows list of available dimensions
                            var modalInstance = $modal.open({
                                template: '<dimension-chooser selected-dimension="selectedDimension" choose="choose()" close="close()" type="type"></dimension-chooser>',
                                windowClass: 'sub-system-modals softpro-modals',
                                backdrop: 'static',
                                size: 'lg',
                                controller: ['$scope', '$modalInstance',
                                    function($scope, $modalInstance) {
                                        $scope.type = type;
                                        $scope.selectedDimension = {};

                                        $scope.choose = function() {
                                            $modalInstance.close($scope.selectedDimension);
                                        };

                                        $scope.close = function() {
                                            $modalInstance.dismiss('cancel');
                                        };
                                    }
                                ]
                            });
                            modalInstance.result.then(function(selectedDimension) {
                                $scope.hierarchy.dimension = selectedDimension;
                                if($scope.type) {
                                    $scope.selectedDimensionDetails = AccountService.getAccountDetails(selectedDimension.dimensionId);
                                } else {
                                    $scope.selectedDimensionDetails = BusinessService.getBusinessDetails(selectedDimension.dimensionId);
                                }
                            }, function() {
                                $scope.close();
                            });

                            /**
                             * Tree element while we are creating new hierarchies
                             * 
                             * @type {Array}
                             */
                            nodeStaticNewElement = [{
                                children: [],
                                hierarchyElementId: 'hierarchy/-5000001',
                                text: 'New Element - .',
                                augCreditDebit: 0,
                                creditDebit: 2,
                                description: ".",
                                textNode: "New Element",
                                augentElement: true,
                                feedImpl: false,
                                canMove: false,
                                index: 0,
                                state: {
                                    selected: false
                                },
                            }];
                            /**
                             * Empty fields for new hierarchies
                             * 
                             * @type {Object}
                             */
                            $scope.hierarchy = {

                                hierarchyId: -1,
                                model: {},
                                externalSystemRefName: null,
                                hierarchyEvents: [],
                                readOnly: false,
                                inUseLabel: null,
                                augentMode: true,
                                operation: "createNew",
                                type: $scope.type
                            }
                            $scope.createdNode = nodeStaticNewElement;
                            newElementId = -5000002;
                            $scope.isMainDataLoaded = true;

                        } else {
                        	$scope.hierarchy = HierarchiesService.getHierarchyDetails($scope.id, $scope.hierarchyId, $scope.type,self.setTreeAjax);
                            
//                            $scope.treeAjax = '/cppro/adminPanel/hierarchies/' + $scope.id + '/' + $scope.hierarchyId + '/hierarchy/' + $scope.type;
                            $scope.isMainDataLoaded = true;
                            newElementId = -5000000;
                        }
                        
                        $scope.newNodeDate = Date.now();
                        
                        if($scope.type) {
                            $scope.title = "Account Hierarchy";
                        } else {
                            $scope.title = "Business Hierarchy";
                        }
                    }
                    $scope.activeNode = function(e, data) {
                        var treeId = $("#" + e.currentTarget.id);
                        if (treeId.selector != "#") {
                            var tree = $(treeId).jstree();
                            tree.settings.core.multiple = false;
                        }
                    };

                    /**
                     * This scope is not in use , but this scope moving element form table to tree
                     * 
                     * @param {[type]} element [description]
                     */
                    $scope.add = function(element) {
                        manageSelecDimeElement(element);
                    }
                    /**
                     * This function is creating node object after pressing insert button
                     * 
                     * @param {[type]} obj [description]
                     * @return {[type]} [description]
                     */
                    var manageSelecDimeElement = function(obj) {
                        var jsTree = angular.element("js-tree");
                        var tree = $(jsTree).jstree();
                        console.log(tree);
                        var data = tree._model.data;
                        var node = {};
                        angular.forEach(data, function(nodeObject) {
                            if (nodeObject.parent != null && nodeObject.parent == "#") {
                                node = nodeObject;
                            }
                        });
                        tree.create_node(node, {}, "last", function(new_node) {
                            new_node.original.insert = true;
                            new_node.original.textNode = obj.dimensionElementVisId;
                            new_node.original.description = obj.description;
                            new_node.original.hierarchyElementId = 'hierarchyElement/' + obj.dimensionElementId;
                            for (var i = 0; i < $scope.hierarchy.dimensionElement.length; i++) {
                                if (obj.dimensionElementId === $scope.hierarchy.dimensionElement[i].dimensionElementId) {
                                    $scope.hierarchy.dimensionElement.splice(i, 1);
                                }
                            }

                            setTimeout(function() {
                                tree.edit(new_node);
                            }, 0);
                        });
                    }

                    $scope.previousDescription;
                    $scope.previousTextNode;
                    /**
                     * Watcher on selected node
                     * 
                     * @return {[type]} [description]
                     */
                    $scope.$watch("select", function() {
                        $timeout(function() {
                            CoreCommonsService.resizeModalElement(modalDialog, elementToResize, additionalElementsToCalcResize);
                        }, 100);
                        if ($scope.select.lenght > 0 && $scope.id == -1 && $scope.hierarchy.hierarchyId != 'undefined') {
                            $scope.select.original.augentElement = true;
                            $scope.hierarchy.augentMode = true;
                            if (angular.isDefined($scope.select.text)) {
                                var jsTree = angular.element("js-tree");
                                var tree = $(jsTree).jstree();
                                var node = tree.get_node($scope.select);
                                var text = $scope.select.original.textNode + " - " + $scope.select.original.description;
                                tree.rename_node(node, text);
                                $scope.select.original.text = $scope.select.original.textNode + " - " + $scope.select.original.description;


                            }
                        }
                        if ($scope.select.parent == "#" && $scope.id != -1) {
                            return $scope.select.text;
                        }
                        if (angular.isDefined($scope.select.text)) {
                            var jsTree = angular.element("js-tree");
                            var tree = $(jsTree).jstree();
                            var node = tree.get_node($scope.select);
                            var text = $scope.select.original.textNode + " - " + $scope.select.original.description;
                            if (tree.rename_node(node, text)) {
                                $scope.previousDescription = $scope.select.original.description;
                                $scope.previousTextNode = $scope.select.original.textNode;
                                $scope.select.original.text = $scope.select.original.textNode + " - " + $scope.select.original.description;
                            } else {
                                $scope.select.original.description = $scope.previousDescription;
                                $scope.select.original.textNode = $scope.previousTextNode;
                                swal({
                                    title: "Duplicate child name",
                                    text: "I will close in 2 seconds.",
                                    type: "warning",
                                    timer: 2000
                                });
                            };
                        }
                    }, true);

                    /**
                     * This scope is showing context menu
                     * 
                     * @type {Object}
                     */
                    $scope.contextMenu = {
                        "Insert Before": {
                            "label": "Insert Before",
                            "action": function(data) {
                                if (!$scope.hierarchy.readOnly) {
                                    $scope.createTreeElement("before");
                                }
                            }
                        },
                        "Insert After": {
                            "label": "Insert After",
                            "action": function(data) {
                                if (!$scope.hierarchy.readOnly) {
                                    $scope.createTreeElement("after");
                                }
                            }
                        },
                        "Insert Dependent": {
                            "label": "Insert Dependent",
                            "action": function(data) {
                                if (!$scope.hierarchy.readOnly) {
                                    $scope.createTreeElement();
                                }
                            }
                        },                                              
                        "Delete": {
                            "label": "Delete",
                            "action": function(data) {
                                if (!$scope.hierarchy.readOnly) {
                                    $scope.deleteTreeElements();
                                }   
                            }
                        }
                    };

                    $scope.createTreeElement = function(place) {
                        var parentNode = null;
                        if (place == "before" || place == "after") {
                            var parentId = $scope.select.parent;
                            parentNode = $scope.treeInst.get_node(parentId);
                        } else {
                            parentNode = $scope.select;
                        }
                        var position = "last";
                        if (place == "before") {
                            position = parentNode.children.indexOf($scope.select.id);
                            if ($scope.select.index < 0) {
                                position = "first";
                            }
                        } else if (place == "after") {
                            position = parentNode.children.indexOf($scope.select.id) + 1;
                        }
                        if (parentNode.original.feedImpl) {
                            swal({
                                title: "You can't create new element here!",
                                text: "I will close in 2 seconds.",
                                type: "warning",
                                timer: 2000
                            });
                        } else {
                            $scope.newNodeDate = Date.now();
                            var newNode = {text: "New " + $scope.newNodeDate + " - ."};
                            // $scope.select.index
                            $scope.treeInst.create_node(parentNode, newNode, position, function(new_node) {});
                        }
                        $scope.manageMoveButtonsAvailability();
                    };

                    $scope.deleteTreeElements = function() {
                        if ($scope.hierarchy.readOnly) {
                            swal({
                                title: "You can't delete this file(s). Hierarchy is read only.",
                                text: "I will close in 2 seconds.",
                                type: "warning",
                                timer: 2000
                            });
                        } else {
                            var selectedNodes = $scope.jsTreeDomElement.jstree("get_selected", true);   
                            var notDeletedNodesNames = "";
                            // It is possible to delete all selected nodes, but now we don't have multiselect, so it's only one node in selectedNodes
                            angular.forEach(selectedNodes, function(node) {
                                if (node.original.augentElement) {
                                    $scope.treeInst.delete_node(node); // delete node
                                } else {
                                    notDeletedNodesNames += node.text + "\n" // catch name to show message
                                }
                            });
                            if (notDeletedNodesNames != "") {
                                swal({
                                    title: "You can't delete file(s):",
                                    text: notDeletedNodesNames,
                                    type: "warning"
                                });
                            }
                        }
                        $scope.manageMoveButtonsAvailability();
                    };

                    $scope.moveTreeElement = function(place) {
                        var parentId = $scope.select.parent;
                        parentNode = $scope.treeInst.get_node(parentId);
                        // position = parentNode.children.indexOf($scope.select.id)
                        if (place == "before") {
                            position = parentNode.children.indexOf($scope.select.id) - 1;
                            if (position < 0) {
                                position = "first";
                            }
                        } else if (place == "after") {
                            position = parentNode.children.indexOf($scope.select.id) + 2;
                        }
                        $scope.treeInst.move_node($scope.select, parentNode, position);
                        $scope.manageMoveButtonsAvailability();
                    };

                    /**
                     * This function is called while we are editing node or field and check if we are editing this same nodes then trying find this element and replace data
                     * 
                     * @return {[type]} [description]
                     */
                    $scope.changeValeuInNode = function() {
                        var editElement = angular.copy($scope.select);
                        if ($scope.events.length == 0) {
                            var readyElementToEdit = {};
                            var idElementHierarchy = editElement.original.hierarchyElementId;
                            if (typeof idElementHierarchy === "undefined") {
                                idElementHierarchy = editElement.original.id.split("/")[1];
                            } else {
                                idElementHierarchy = editElement.original.hierarchyElementId.split("/")[1];
                            }
                            var oneReadyElementToEdit = {
                                creditDebit: editElement.original.creditDebit,
                                augCreditDebit: editElement.original.augCreditDebit,
                                isAugentElement: editElement.original.isAugentElement,
                                description: editElement.original.description,
                                origVisId: editElement.text,
                                visId: editElement.original.textNode,
                                isFeedImpl: editElement.original.isFeedImpl,
                                disabled: editElement.original.disabled,
                                hierarchyElementId: idElementHierarchy,
                                index: editElement.original.index,
                                eventType: 'UPDATE'

                            };
                            $scope.events.push(oneReadyElementToEdit);
                        }
                        var found = false;

                        var actuallyId = editElement.original.hierarchyElementId;
                        if (typeof actuallyId === "undefined") {
                            actuallyId = editElement.original.id.split("/")[1];
                        } else {
                            actuallyId = editElement.original.hierarchyElementId.split("/")[1];
                        }

                        for (var i = 0; i < $scope.events.length; i++) {

                            var idFromListToEdit = $scope.events[i].hierarchyElementId;
                            var typeFromEventEdit = $scope.events[i].eventType;

                            if (idFromListToEdit == actuallyId && typeFromEventEdit === "UPDATE") {

                                idElementHierarchy = editElement.original.hierarchyElementId;
                                if (typeof idElementHierarchy === "undefined") {
                                    idElementHierarchy = editElement.original.id.split("/")[1];
                                } else {
                                    idElementHierarchy = editElement.original.hierarchyElementId.split("/")[1];
                                }
                                var oneReadyElementToEdit = {
                                    creditDebit: editElement.original.creditDebit,
                                    augCreditDebit: editElement.original.augCreditDebit,
                                    isAugentElement: editElement.original.isAugentElement,
                                    description: editElement.original.description,
                                    visId: editElement.original.textNode,
                                    origVisId: editElement.text,
                                    isFeedImpl: editElement.original.isFeedImpl,
                                    disabled: editElement.original.disabled,
                                    hierarchyElementId: idElementHierarchy,
                                    index: editElement.original.index,
                                    eventType: 'UPDATE'

                                };
                                if ($scope.id == -1) {
                                    oneReadyElementToEdit.origVisId = null;
                                }
                                $scope.events[i] = oneReadyElementToEdit;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            var idElementHierarchy = editElement.original.hierarchyElementId
                            if (typeof idElementHierarchy === "undefined") {
                                idElementHierarchy = editElement.original.id.split("/")[1];
                            } else {
                                idElementHierarchy = editElement.original.hierarchyElementId.split("/")[1];
                            }
                            var oneReadyElementToEdit = {
                                creditDebit: editElement.original.creditDebit,
                                augCreditDebit: editElement.original.augCreditDebit,
                                isAugentElement: editElement.original.isAugentElement,
                                description: editElement.original.description,
                                origVisId: editElement.text,
                                visId: editElement.original.textNode,
                                isFeedImpl: editElement.original.isFeedImpl,
                                disabled: editElement.original.disabled,
                                hierarchyElementId: idElementHierarchy,
                                index: editElement.original.index,
                                eventType: 'UPDATE'
                            };
                            if ($scope.id == -1) {
                                oneReadyElementToEdit.origVisId = null;
                            }

                            $scope.events.push(oneReadyElementToEdit);

                        }
                        insertAndEdit($scope.events[$scope.events.length - 1]);
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


                            if (treeNode.parent != null) {
                                if (parentId === treeNode.id) {
                                    parentObject = treeNode;
                                    return false;
                                }
                            }
                        });
                        return parentObject;
                    };

                    /**
                     * Save the data to the database and sends an event to do
                     * 
                     * @return {[type]} [description]
                     */
                    $scope.save = function() {
                        $scope.hierarchy.hierarchyEvents = $scope.events;
                        if($scope.type === false) {
                            $scope.hierarchy.displayNameEvents = listOfDisplayNameEvents;
                        }

                        if ($scope.hierarchyId <= 0) {
                            var modelId = $scope.selectedDimensionDetails.model.modelId;
                            $scope.hierarchy.model.modelId = modelId;
                        }
                        
                        if ($scope.hierarchy.model.modelId !== undefined && $scope.hierarchy.model.modelId > 0) {
                            swal({
                                title: "Changes will be saved.",
                                text: "Do you wish to submit the change management request immediately?",
                                type: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#d9534f",
                                confirmButtonText: "Yes",
                                cancelButtonText: "No",
                            }, function(isConfirm) {
                                if (isConfirm) {
                                    $scope.hierarchy.submitChangeManagementRequest = true;
                                } else {
                                    $scope.hierarchy.submitChangeManagementRequest = false;
                                }
                                $rootScope.$broadcast("modal:blockAllOperations");
                                HierarchiesService.save($scope.hierarchy);
                            });
                        } else {
                            $rootScope.$broadcast("modal:blockAllOperations");
                            HierarchiesService.save($scope.hierarchy);
                        }

                    };

                    /**
                     *  This method is call when new update element is add
                     *  and trying find this same elements update like is inserted in $scope.events
                     *  then replace fresh data to insert element and delete update element.
                     */
                    var insertAndEdit = function(addedUpdatedEvent) {
                        for (var i = 0; i < $scope.events.length; i++) {
                            if ((parseInt(addedUpdatedEvent.hierarchyElementId) === $scope.events[i].hierarchyElementId) && $scope.events[i].eventType === "INSERT") {
                                var parId = $scope.events[i].hierarchyParentElementId;
                                var creditDebit = addedUpdatedEvent.creditDebit;
                                var description = addedUpdatedEvent.description;
                                var index = addedUpdatedEvent.index;
                                var visId = addedUpdatedEvent.visId;
                                var hierarElementId = parseInt(addedUpdatedEvent.hierarchyElementId);
                                var parentVisId = $scope.events[i].parentVisId;
                                $scope.events[i].hierarchyParentElementId = parId;
                                $scope.events[i].hierarchyElementId = hierarElementId;
                                $scope.events[i].visId = visId;
                                $scope.events[i].index = index;
                                $scope.events[i].description = description;
                                $scope.events[i].parentVisId = parentVisId;
                                $scope.events[i].canMove = true;
                                $scope.events.pop();
                                break;
                            }
                        }
                    };


                    $scope.readyTree = function(e, data) {
                        $scope.jsTreeDomElement = angular.element("js-tree");
                        $scope.tree = $($scope.jsTreeDomElemen).jstree();
                        $scope.treeInst = $.jstree.reference($scope.jsTreeDomElement);
                    };

                    /**
                     * select node checkboxes.
                     */

                    $scope.selectNode = function(e, data) {
                        $scope.select = data.node;
                        if ($scope.select.parent == "#") {
                            $scope.insertBeforeAvailable = false;
                            $scope.insertAfterAvailable = false;
                            $scope.deleteAvailable = false;
                            $scope.moveBeforeAvailable = false;
                            $scope.moveAfterAvailable = false;
                        } else {
                            $scope.insertBeforeAvailable = true;
                            $scope.insertAfterAvailable = true; 
                            $scope.deleteAvailable = true;
                            $scope.manageMoveButtonsAvailability();
                        }
                        $scope.insertDependentAvailable = true;
                    };

                    $scope.manageMoveButtonsAvailability = function() {
                        var position = $scope.getSelectedNodePosition();
                        $scope.moveBeforeAvailable = (position == 0) ? false : true;
                        $scope.moveAfterAvailable = ($scope.countOfNodesInSelectedNodeLevel() == position + 1) ? false : true;      
                    };

                    /**
                     * Create a new node with the required properties
                     * 
                     * @param {[type]} e [description]
                     * @param {[type]} data [description]
                     * @return {[type]} [description]
                     */
                    $scope.createNode = function(e, data) {
                        var jsTree = angular.element("js-tree");
                        var tree = $(jsTree).jstree();
                        data.node.original.augentElement = true;
                        data.node.original.canMove = true;
                        data.node.original.index = data.position;
                        if (data.node.original.insert == true) {
                            tree.set_id(data.node, data.node.original.hierarchyElementId);
                            data.node.original.creditDebit = 1;
                            data.node.original.feedImpl = false;
                            var idParentElementHierarchy = data.node.parent.split("/")[1];
                            data.node.original.hierarchyParentElementId = idParentElementHierarchy;
                            newElementId = data.node.id.split("/")[1];
                        } else {
                            data.node.original.textNode = 'New ' + $scope.newNodeDate;
                            data.node.original.description = '.';
                            $scope.readyElementToSave = [];
                            var hierarchyElementId = 'hierarchyElement/' + newElementId;
                            tree.set_id(data.node, hierarchyElementId);

                            data.node.original.hierarchyElementId = hierarchyElementId;

                            var idParentElementHierarchy = data.node.parent.split("/")[1];
                            data.node.original.hierarchyParentElementId = idParentElementHierarchy;
                            if (typeof idParentElementHierarchy == "undefined") {
                                idParentElementHierarchy = '-5000001';
                            }
                        }

                        data.node.text = data.node.original.textNode + ' - ' + data.node.original.description;
                        console.log("create", data);
                        var newParentObject = findParent(data.node.parent, data.instance._model.data);
                        if (newParentObject.parents.length == 1) {
                            newParentObject.original.index++;
                        }



                        var oneReadyElement = {
                            creditDebit: data.node.original.creditDebit,
                            augCreditDebit: data.node.original.augCreditDebit,
                            isAugentElement: data.node.original.isAugentElement,
                            description: data.node.original.description,
                            visId: data.node.original.textNode,
                            isFeedImpl: data.node.original.isFeedImpl,
                            disabled: data.node.original.disabled,
                            hierarchyParentElementId: idParentElementHierarchy,
                            hierarchyElementId: newElementId,
                            parentVisId: newParentObject.original.textNode,
                            canMove: data.node.original.canMove,
                            index: data.node.original.index,
                            eventType: 'INSERT'

                        };
                        newElementId--;
                        $scope.events.push(oneReadyElement);
                    };

                    /**
                     * Delete node
                     * 
                     * @param {[type]} e [description]
                     * @param {[type]} data [description]
                     * @return {[type]} [description]
                     */
                    $scope.deleteNode = function(e, data) {
                        var idElementHierarchy = data.node.original.hierarchyElementId
                        if (typeof idElementHierarchy == "undefined") {
                            var idElementHierarchy = data.node.original.id.split("/")[1];
                            idElementHierarchy = parseInt(idElementHierarchy);
                        } else {
                            idElementHierarchy = data.node.original.hierarchyElementId.split("/")[1];
                        }
                        
                        var deleteEvent = {
                            creditDebit: data.node.original.creditDebit,
                            augCreditDebit: data.node.original.augCreditDebit,
                            isAugentElement: data.node.original.isAugentElement,
                            description: data.node.original.description,
                            visId: data.node.original.textNode,
                            isFeedImpl: data.node.original.isFeedImpl,
                            disabled: data.node.original.disabled,
                            hierarchyElementId: idElementHierarchy,
                            index: data.node.original.index,
                            eventType: 'REMOVE'
                            
                        };

                        $scope.events.push(deleteEvent);
                    };

                    /**
                     * Move tree node with required properties
                     * 
                     * @param {[type]} e [description]
                     * @param {[type]} data [description]
                     * @return {[type]} [description]
                     */
                    $scope.dnd = function(e, data) {
                        console.log("e", e);
                        console.log("data", data);

                        var newParentObject = findParent(data.node.parent, data.instance._model.data);
                        if (newParentObject.parents.length == 1) {
                            newParentObject.original.index++;
                        }
                        var oldParentObject = findParent(data.old_parent, data.instance._model.data);
                        if (oldParentObject.parents.length == 1) {
                            oldParentObject.original.index--;
                        }
                        console.log("newParentObject", newParentObject);
                        var index = newParentObject.children.length - 1; // if we move element inside
                        if (data.position != data.old_position) {
                            index = data.position;
                        }
                        var pVisId = newParentObject.original.textNode;
                        if (pVisId == null || typeof pVisId === "undefined") {
                            pVisId = newParentObject.original.text;
                        }

                        var elementMoveId = data.node.original.hierarchyElementId;
                        if (typeof elementMoveId === "undefined") {
                            elementMoveId = data.node.original.id.split("/")[1];
                        } else {
                            elementMoveId = data.node.original.hierarchyElementId.split("/")[1];
                        }
                        var newElementMoveId = newParentObject.original.hierarchyElementId;
                        if (typeof newElementMoveId === "undefined") {
                            newElementMoveId = newParentObject.original.id.split("/")[1];
                        } else {
                            newElementMoveId = newParentObject.original.hierarchyElementId.split("/")[1];
                        }
                        if (data.node.id.split("/")[0] === "dimensionElement") {

                            var readyDimensionElementToMove = {
                                creditDebit: data.node.original.creditDebit,
                                augCreditDebit: data.node.original.augCreditDebit,
                                description: data.node.original.description,
                                isAugentElement: data.node.original.isAugentElement,
                                isFeedImpl: data.node.original.isFeedImpl,
                                hierarchyParentElementId: newElementMoveId,
                                hierarchyElementId: elementMoveId,
                                visId: data.node.original.textNode, // name without description
                                parentVisId: pVisId,
                                index: index,
                                eventType: "MOVE_FEED"
                            };
                            $scope.events.push(readyDimensionElementToMove);

                        } else {
                            var readyElementToMove = {
                                creditDebit: data.node.original.creditDebit,
                                augCreditDebit: data.node.original.augCreditDebit,
                                description: data.node.original.description,
                                isAugentElement: data.node.original.isAugentElement,
                                isFeedImpl: data.node.original.isFeedImpl,
                                hierarchyParentElementId: newElementMoveId,
                                hierarchyElementId: elementMoveId,
                                visId: data.node.original.textNode, // name without description
                                parentVisId: pVisId,
                                index: index,
                                eventType: "MOVE"
                            };
                            $scope.events.push(readyElementToMove);
                        }
                    };

                    $scope.$on("HierarchiesService:hierarchiesDetailsSaveError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        $scope.isError = true;
                        // error from method http->success() from "error" field
                        Flash.create('danger', data.message);
                        // clear previous validation messages
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

                    $scope.$on("HierarchiesService:displayNameSaveError", function(event, data) {
                        Flash.create('danger', data.message);
                    });

                    $scope.close = function() {
                        $rootScope.$broadcast("HierarchyDetails:close");
                    };

                    $scope.getSelectedNodePosition = function() {
                        var parentId = $scope.select.parent;
                        parentNode = $scope.treeInst.get_node(parentId);
                        var position = parentNode.children.indexOf($scope.select.id);
                        return position;
                    };

                    $scope.countOfNodesInSelectedNodeLevel = function() {
                        var parentId = $scope.select.parent;
                        parentNode = $scope.treeInst.get_node(parentId);
                        var childrenCount = parentNode.children.length;
                        return childrenCount;
                    };
                    
                    $scope.changeDisplayNameAddEvent = function(displayName) {
                        var displayNameElementId = $scope.select.id.split("/")[1];
                        var event = CoreCommonsService.findElementByKey(listOfDisplayNameEvents, displayNameElementId, "structureElementId");
                        if (event !== null) {
                            event.structureElementDisplayName = displayName;
                        } else {
                            var displayNameData = {
                                    structureId: $scope.hierarchyId,
                                    structureElementId: displayNameElementId,
                                    structureElementDisplayName: displayName
                            }
                            listOfDisplayNameEvents.push(displayNameData);
                        }
                    }

                    $scope.$watchCollection(

                        function() {

                            return $scope.hierarchy;
                        },
                        function(newValue, oldValue) {
                            console.log("new ", $scope.hierarchy);
                            if (newValue !== oldValue) {
                                $scope.hierarchy = newValue;
                                // don't edit hierachy without model
                                if ($scope.id != -1 && !($scope.hierarchy.model.modelId > 0)) {
                                    $scope.hierarchy.readOnly = true;
                                    $scope.hierarchy.inUseLabel = "You can't edit Hierarchy if its Dimension is not assigned to Model.";
                                }
                            }
                        }
                    );
                }
            ]
        }
    }
})();