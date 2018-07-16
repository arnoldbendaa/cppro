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
        .module('adminApp.rolesPage')
        .directive('rolesDetails', rolesDetails);

    /* @ngInject */
    function rolesDetails($rootScope, RolesPageService, $timeout, CoreCommonsService, Flash) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'roles/views/rolesDetails.html',
            scope: {
                roleId: '=id'
            },

            replace: true,
            controller: ['$scope',
                function($scope) {

                    // parameters to resize modal
                    var modalDialog = $(".role-details").closest(".modal-dialog");
                    var elementToResize = $(".role-details .ro-panel");
                    var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), $(".getToCalc3")];
                    $scope.cookieName = "adminPanel_modal_roles";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                    }, 0);

                    /**
                     * validation messages: messageError for top message bar, validation fields for each form fields
                     * If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
                     *@type {Boolean}
                     */
                    $scope.isError = false;
                    $scope.messageError = "";
                    $scope.role = []; // object role
                    $scope.treeProcess = {}; //orginal object treeProces
                    $scope.copyTreeProcess = {}; // copy of treeNode
                    $scope.validation = {
                        roleVisId: '',
                        roleDescription: '',
                    };
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.close = close;
                    $scope.save = save;
                    $scope.readyTree = readyTree;
                    $scope.selectNode = selectNode;
                    $scope.deselectNode = deselectNode;
                    
                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }
                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }
                    
                    if ($scope.roleId == -1) {
                        $scope.isDataLoaded = false;
                        RolesPageService.getRoleDetailsFromDatabase($scope.roleId);
                    } else {
                        RolesPageService.getRoleDetailsFromDatabase($scope.roleId);
                    }
                    function close() {
                        $rootScope.$broadcast('RolesDetails:close');
                    }
                    /**
                     * Try to save Role after clic on button - save means uodate or insert.
                     * @return {[type]} [description]
                     */
                    function save() {
                        var jsTree = angular.element("js-tree");
                        var tree = $(jsTree).jstree();
                        var error = false;
                        var validationMessage = "Error!";
                        if($scope.role.roleVisId.length === 0) {
                            validationMessage += " Length VisId can't be 0!";
                            $scope.validation.roleVisId = "Length VisId can't be 0";
                            error = true;
                        } else {
                            $scope.validation.roleVisId = "";
                        }
                        if($scope.role.roleDescription.length === 0) {
                            validationMessage += " Length Description can't be 0!";
                            $scope.validation.roleDescription = "Length Description can't be 0";
                            error = true;
                        } else {
                            $scope.validation.roleDescription = "";
                        }
                        if(tree.get_checked().length === 0) {
                            validationMessage += " One or more security string must be selected!";
                            error = true;
                        }
                        if(error === false){
                            $rootScope.$broadcast("modal:blockAllOperations");
                            $scope.role.node = $scope.copyTreeProcess;
                            RolesPageService.updateRoleInDatabase($scope.role);
                        } else {
                            Flash.create('danger', validationMessage);
                        }
                    }
                    function readyTree(e, data) {
                        //console.log("readyTree", data);
                    }
                    /**
                     * select treeNode checkboxes.
                     */
                    function selectNode(e, data) {
                        manageSelectedProcess(data.node);
                    }

                    /**
                     * deselect treeNode checkboxes.
                     */
                    function deselectNode(e, data) {
                        manageDeselectedProcess(data.node);
                    }
                    /**
                     * Managment for selected process and children.
                     * @param  {[type]} node
                     */

                    var manageSelectedProcess = function(node) {
                        var processName;
                        var selectedElement;
                        //selected all children from process checkbox
                        if (node.children != undefined && node.children.length > 0) {
                            processName = node.id;
                            selectedElement = "process";
                        } else {
                            processName = node.parent;
                            selectedElement = "chldren";
                        }
                        for (var i = 0; i < $scope.copyTreeProcess.children.length; i++) {
                            //console.log($scope.copyTreeProcess);
                            var actuallyProcess = $scope.copyTreeProcess.children[i];
                            if (processName == actuallyProcess.id) {
                                for (var j = 0; j < actuallyProcess.children.length; j++) {
                                    var actuallyChild = actuallyProcess.children[j]
                                    if (selectedElement == "process") {
                                        actuallyChild.state.selected = true;
                                    } else {
                                        if (actuallyChild.id == node.id) {
                                            actuallyChild.state.selected = true;
                                        }
                                    }
                                }
                            }
                        }
                    };
                    /**
                     * Menagment deselected for tree process and children
                     * @param  {[type]} node
                     *
                     */
                    var manageDeselectedProcess = function(node) {
                        var processName;
                        var DeSelectedElement;

                        if (node.children != undefined && node.children.length > 0) {
                            processName = node.id;
                            DeSelectedElement = "process";


                        } else {
                            processName = node.parent;
                            DeSelectedElement = "chldren";

                        }
                        for (var i = 0; i < $scope.copyTreeProcess.children.length; i++) {
                            var actuallyProcess = $scope.copyTreeProcess.children[i];
                            if (processName == actuallyProcess.id) {
                                for (var j = 0; j < actuallyProcess.children.length; j++) {
                                    var actuallyChild = actuallyProcess.children[j]
                                    if (DeSelectedElement == "process") {
                                        actuallyChild.state.selected = false;
                                    } else {
                                        if (actuallyChild.id == node.id) {
                                            actuallyChild.state.selected = false;
                                        }
                                    }
                                }
                            }
                        }
                    };

                    $scope.$on("RolesPageService:rolesUpdatedError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                        // set new messages
                        angular.forEach(data.fieldErrors, function(error) {
                            if (error.fieldName in $scope.validation) {
                                $scope.validation[error.fieldName] = error.fieldMessage;
                                data.message += error.fieldMessage;
                            }
                        });
                        Flash.create('danger', data.message);
                    });

                    $scope.$on("RolesPageService:rolesDetailsUpdated", function(event, data) {
                        $scope.role = data;
                        $scope.isDataLoaded = true;
                        $scope.treeProcess = data.node;
                        $scope.copyTreeProcess = angular.copy($scope.treeProcess);
                    });
                }
            ]

        };
    }
})();