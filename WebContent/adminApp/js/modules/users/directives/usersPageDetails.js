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
        .module('adminApp.usersPage')
        .directive('usersDetails', usersDetails);

    /* @ngInject */
    function usersDetails($rootScope, $modal, $compile, UsersPageService, RolesPageService, Flash, $timeout, CoreCommonsService, FlatFormsPageService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'users/views/usersDetails.html',
            scope: {
                id: '=',
                copy:'='
            },
            replace: true,
            controller: ['$scope',
                function($scope) {
                    var modalDialog = null;
                    var allElementsToResize = null;
                    var allAdditionalElementsToCalcResize = null;
                    $timeout(function() {


                        // parameters to resize modal
                        modalDialog = $(".user-details").closest(".modal-dialog");
                        var navTabs = modalDialog.find("ul.nav");
                        var navSecurityTabs = modalDialog.find(".security-content ul.nav");

                        //tab Details - Nothing to resize                        

                        // tab Role
                        var elementToResize2 = $(".ud-role-panel-table.elementToResize");
                        var additionalElementsToCalcResize2 = [navTabs, $(".add-selected-element-to-table")];


                        // tab Security > Models                                           
                        var elementToResize3_models = $(".security-models-tree .elementToResize");
                        var additionalElementsToCalcResize3_models = [navTabs, navSecurityTabs, $(".getToCalc5"), 5];

                        // tab Security > Admin Apps   
                        var elementToResize3_admin = $(".security-admin-tree .elementToResize");
                        var additionalElementsToCalcResize3_admin = [navTabs, navSecurityTabs, $(".getToCalc6"), 5];

                        // tab Security > Budget Cycles App
                        var elementToResize3_budget = $(".security-budget-tree .elementToResize");
                        var additionalElementsToCalcResize3_budget = [navTabs, navSecurityTabs, $(".getToCalc7"), 5];

                        // tab Security > Dashboards App
                        var elementToResize3_dashboard = $(".security-dashboard-tree .elementToResize");
                        var additionalElementsToCalcResize3_dashboard = [navTabs, navSecurityTabs, $(".getToCalc8"), 5];

                        // tab Flat Forms
                        var elementToResize4 = $(".xmlformsFront.elementToResize");
                        var additionalElementsToCalcResize4 = [navTabs, $(".getToCalc2"), $(".getToCalc3"), 14];

                        //tab Mobile Profiles
                        var elementToResize5 = $(".mobileProfilesFlex");
                        var additionalElementsToCalcResize5 = [navTabs, $(".getToCalc4"), 28];


                        $scope.cookieName = "adminPanel_modal_user";
                        // try to resize and move modal based on the cookie
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                        $scope.resizedColumn = function(sender, args) {
                            CoreCommonsService.resizedColumn(args, $scope.cookieName);
                        };
                        $scope.sortedColumn = function(sender, args) {
                            CoreCommonsService.sortedColumn(args, $scope.cookieName);
                        };
                        allElementsToResize = [elementToResize2, elementToResize3_models, elementToResize3_admin, elementToResize3_budget, elementToResize3_dashboard, elementToResize4, elementToResize5];
                        allAdditionalElementsToCalcResize = [additionalElementsToCalcResize2, additionalElementsToCalcResize3_models, additionalElementsToCalcResize3_admin, additionalElementsToCalcResize3_budget, additionalElementsToCalcResize3_dashboard, additionalElementsToCalcResize4, additionalElementsToCalcResize5];
                        $timeout(function() { // timeout is necessary to pass asynchro
                            CoreCommonsService.allowResizingModal(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx);
                        }, 0);
                    }, 0);


                    $scope.validation = {
                        visId: "",
                        userFullName: "",
                        emailAddress: "",
                        password: "",
                        externalSystemUserName: "",
                        logonAlias: ""
                    };
                    /**
                     * validation messages: validation fields for each form fields
                     * If value (=message) for field is empty (empty string), message won't be displayed and form element won't have red border (or font color).
                     *@type {Boolean}
                     */

                    $scope.user = [];

                    //$scope.user.showBudgetActicity = false;


                    var profilesToSave = [];
                    $scope.roles = [];
                    $scope.separate = [];
                    $scope.update = {};
                    $scope.selectedXmlFormIds = [];
                    $scope.selectedAvailableXml = -1;
                    $scope.selectMode = false;
                    $scope.profileNumberId = 0;
                    $scope.isRolesDataLoaded = false;
                    $scope.isDataLoaded = false;
                    $scope.selectedXmlForms = [];
                    $scope.notSelectedXmlForms = [];
                    UsersPageService.getUsersDetailsFromDatabase($scope.id);
                    $scope.treeAjax = '/cppro/adminPanel/users/' + $scope.id + '/security/';
                    var choosedRole = null;
                    $scope.changeRoles = changeRoles;
                    $scope.tabSelected = tabSelected;
                    $scope.addUserRole = addUserRole;
                    self.deleteSelectRole = deleteSelectRole;
                    $scope.deleteUserRole = deleteUserRole;
                    self.separateDuplicatedRoles = separateDuplicatedRoles;
                    $scope.openRoleForUserChooser = openRoleForUserChooser;
                    $scope.readyTreeAdmin = readyTreeAdmin;
                    $scope.readyTreeDashboard = readyTreeDashboard;
                    $scope.readyTreeBudgetCycle = readyTreeBudgetCycle;
                    $scope.selectNode = selectNode;
                    $scope.deselectNode = deselectNode;
                    $scope.selectAdminAppNode = selectAdminAppNode;
                    $scope.deselectAdminAppNode = deselectAdminAppNode;

                    $scope.selectBudgetCycleAppNode = selectBudgetCycleAppNode;
                    $scope.deselectBudgetCycleAppNode = deselectBudgetCycleAppNode;

                    $scope.selectDashboardAppNode = selectDashboardAppNode;
                    $scope.deselectDashboardAppNode = deselectDashboardAppNode;

                    $scope.selectXmlForm = selectXmlForm;
                    $scope.resizeModalElement = resizeModalElement;
                    $scope.remove = remove;
                    $scope.addXmlForms = addXmlForms;
                    $scope.close = close;
                    $scope.save = save;
                    $scope.checkedAll = false;
                    $scope.checkStat = {};
                    
                    var firstRebuild = true;

                    // resize modal elements after switching tabs
                    function tabSelected() {
                        $scope.resizeModalElement();
                    }


                    ///////////////////////////////////////////////////////////////////////// SECURITY ////////////////////////////////////////////////////////////////////////////////////////////

                    /**
                     * select node checkboxes.
                     */
                    function selectNode(e, data) {
                        manageSecurity(data.instance);
                    }

                    /**
                     * deselect node checkboxes.
                     */
                    function deselectNode(e, data) {
                        var modelId = 0;
                        if (isSecurityRoot(data.node.id)) {
                            modelId = parseInt(data.node.id.split("/")[1]);
                        } else {
                            modelId = getModelIdFromSecurityNode(data.node);
                        }
                        deleteAllSecurityForModel(modelId);

                        manageSecurity(data.instance);
                    }

                    /**
                     * Managment for deselected data and children.
                     */
                    function manageSecurity(tree) {
                        var listOfSelectedNode = tree.get_top_checked(true);

                        // delete security
                        for (var i = 0; i < listOfSelectedNode.length; i++) {
                            var node = listOfSelectedNode[i];
                            var modelId = 0;

                            if (isSecurityRoot(node.id)) {
                                modelId = parseInt(node.id.split("/")[1]);
                            } else {
                                modelId = getModelIdFromSecurityNode(node);
                            }
                            deleteAllSecurityForModel(modelId);
                        }

                        // add security
                        for (var i = 0; i < listOfSelectedNode.length; i++) {
                            var node = listOfSelectedNode[i];
                            var security = {};

                            if (isSecurityRoot(node.id)) {
                                // add all children
                                security.modelId = parseInt(node.id.split("/")[1]);
                                var childId = node.children[0];
                                security.structureId = parseInt(childId.split("/")[1]);
                                security.structureElementId = parseInt(childId.split("/")[3]);
                            } else {
                                // add security node
                                security.modelId = getModelIdFromSecurityNode(node);
                                security.structureId = parseInt(node.id.split("/")[1]);
                                security.structureElementId = parseInt(node.id.split("/")[3]);
                                security.narrative = node.text;
                            }
                            $scope.security.push(security);
                        }
                    }

                    function isSecurityRoot(id) {
                        return (id.lastIndexOf("modelId/", 0) === 0);
                    }

                    function getModelIdFromSecurityNode(node) {
                        var modelId = 0;
                        var parents = node.parents;
                        var i = parents.length;

                        if (i > 1) {
                            var id = parents[i - 2];
                            modelId = parseInt(id.split("/")[1]);
                        }
                        return modelId;
                    }

                    function deleteAllSecurityForModel(modelId) {
                        var newSecurity = [];

                        for (var i = 0; i < $scope.security.length; i++) {
                            if ($scope.security[i].modelId !== modelId) {
                                newSecurity.push($scope.security[i]);
                            }
                        }
                        $scope.security = newSecurity;
                    }

                    ///////////////////////////////////////////////////////////////////////// ROLES ////////////////////////////////////////////////////////////////////////////////////////////

                    /**
                     * Managment for selected data and children.
                     * @param  {[type]} adminApp
                     */
                    var manageSelectedAdminApp = function(adminApp, jsTree) {
                        console.log($scope.user.hiddenRoles1 == $scope.hiddenRoles1);
                        $scope.hiddenRoles1.length = 0;
                        var listOpenedNodes = adminApp.instance._model.data;
                        angular.forEach(listOpenedNodes, function(nodeObject) {
                            if (nodeObject.state.selected === true && nodeObject.parent !== null && nodeObject.parent !== "#") {
                                if (nodeObject.children.length === 0) {
                                    var obj = {
                                        roleId: nodeObject.id,
                                        roleVisId: nodeObject.text,
                                        roleDescription: null
                                    };
                                    $scope.hiddenRoles1.push(obj);
                                }
                            }
                        });


                    };

                    /**
                     * Managment for selected data and children.
                     * @param  {[type]} budgetCycleApp
                     */
                    var manageSelectedBudgetCycleApp = function(budgetCycleApp, jsTree) {
                        console.log($scope.user.hiddenRoles2 == $scope.hiddenRoles2);
                        $scope.hiddenRoles2.length = 0;
                        var listOpenedNodes = budgetCycleApp.instance._model.data;

                        angular.forEach(listOpenedNodes, function(nodeObject) {
                            if (nodeObject.state.selected === true && nodeObject.parent !== null && nodeObject.parent !== "#") {
                                if (nodeObject.children.length === 0) {
                                    var obj = {
                                        roleId: nodeObject.id,
                                        roleVisId: nodeObject.text,
                                        roleDescription: null
                                    };
                                    $scope.hiddenRoles2.push(obj);
                                }
                            }
                        });
                    };

                    /**
                     * Managment for selected data and children.
                     * @param  {[type]} budgetCycleApp
                     */
                    var manageSelectedDashboardApp = function(dashboardApp, jsTree) {
                        console.log($scope.user.hiddenRoles3 == $scope.hiddenRoles3);
                        $scope.hiddenRoles3.length = 0;
                        var listOpenedNodes = dashboardApp.instance._model.data;

                        angular.forEach(listOpenedNodes, function(nodeObject) {
                            if (nodeObject.state.selected === true && nodeObject.parent !== null && nodeObject.parent !== "#") {
                                if (nodeObject.children.length === 0) {
                                    var obj = {
                                        roleId: nodeObject.id,
                                        roleVisId: nodeObject.text,
                                        roleDescription: null
                                    };
                                    $scope.hiddenRoles3.push(obj);
                                }
                            }
                        });
                    };



                    function changeRoles(role) {
                        choosedRole = role;
                    }


                    function addUserRole(choosedRole) {
                        if (choosedRole) {
                            deleteSelectRole(choosedRole.roleId);
                            var roles = $scope.user.roles;
                            roles.push(choosedRole);
                            resizeModalElement();
                        }
                        choosedRole = null;
                    }


                    function deleteSelectRole(roleId) {
                        var selectRoles = $scope.separate;
                        for (var i = 0; i < selectRoles.length; i++) {
                            if (selectRoles[i].roleId === roleId) {
                                selectRoles.splice(i, 1);
                            }
                        }
                    }

                    function deleteUserRole(roleId) {
                        var selectRoles = $scope.separate;
                        var userRoles = $scope.user.roles;

                        for (var i = 0; i < userRoles.length; i++) {
                            if (userRoles[i].roleId === roleId) {
                                selectRoles.push(userRoles[i]);
                                userRoles.splice(i, 1);
                            }
                        }
                    }

                    // self.deleteDuplicatedRoles = function() {
                    //     var selectRoles = $scope.roles;
                    //     $scope.copy = selectRoles;
                    //     var userRoles = $scope.user.roles;

                    //     for (i = 0; i < userRoles.length; i++) {
                    //         for (j = 0; j < $scope.copy.length; j++) {
                    //             if (userRoles[i].id === $scope.copy[j].id) {
                    //                 $scope.copy.remove($scope.copy[j]);
                    //             }
                    //         }

                    //     }
                    // }

                    function separateDuplicatedRoles() {
                        var selectRoles = $scope.roles.sourceCollection;
                        var userRoles = $scope.user.roles;
                        var ifHavedByRoles = false;
                        var lenght = 0;
                        var i, j;
                        for (i = 0; i < selectRoles.length; i++) {
                            for (j = 0; j < userRoles.length; j++) {

                                if (userRoles[j].roleId === selectRoles[i].roleId) {
                                    ifHavedByRoles = true;
                                }

                            }
                            if (ifHavedByRoles === false) {
                                $scope.separate[lenght] = selectRoles[i];
                                lenght++;
                            }
                            ifHavedByRoles = false;
                        }

                    }

                    function openRoleForUserChooser() {
                        var separate = $scope.separate;
                        var modalInstance = $modal.open({
                            templateUrl: $BASE_TEMPLATE_PATH + 'users/userRoleChooser.html',
                            template: '<user-role-chooser></user-role-chooser>',
                            windowClass: 'user-modals softpro-modals',
                            backdrop: 'static',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.collectionRoles = separate;
                                    $scope.modal = $modalInstance;
                                }
                            ]
                        });
                        modalInstance.result.then(function(selectedRole) {
                            if (selectedRole !== undefined) {
                                $scope.addUserRole(selectedRole);
                            }
                        }, function() {

                        });
                    }


                    function readyTreeAdmin(e, data) {
                        manageSelectedAdminApp(data, data.instance);
                    }

                    function readyTreeBudgetCycle(e, data) {
                        manageSelectedBudgetCycleApp(data, data.instance);
                    }

                    function readyTreeDashboard(e, data) {
                        manageSelectedDashboardApp(data, data.instance);
                    }


                    /**
                     * select node checkboxes.
                     */
                    function selectAdminAppNode(e, data) {
                        manageSelectedAdminApp(data, data.instance);
                    }

                    /**
                     * deselect node checkboxes.
                     */
                    function deselectAdminAppNode(e, data) {
                        manageDeselectedAdminApp(data, data.instance);
                    }


                    function selectBudgetCycleAppNode(e, data) {
                        manageSelectedBudgetCycleApp(data, data.instance);
                    }

                    /**
                     * deselect node checkboxes.
                     */
                    function deselectBudgetCycleAppNode(e, data) {

                        manageDeselectedBudgetCycleApp(data, data.instance);
                    }


                    function selectDashboardAppNode(e, data) {
                        manageSelectedDashboardApp(data, data.instance);
                    }

                    /**
                     * deselect node checkboxes.
                     */
                    function deselectDashboardAppNode(e, data) {

                        manageDeselectedDashboardApp(data, data.instance);
                    }

                    /////////////////////////////////////////////////////////////////////////XML FORMS ////////////////////////////////////////////////////////////////////////////////////////////
                    /**
                     * select row in tab xmlForms
                     * @param  [xmlFormId] [description]
                     */
                    function selectXmlForm(xmlFormId) {
                        if ($scope.selectedXmlFormIds.includes(xmlFormId)) {
                        	var index = $scope.selectedXmlFormIds.indexOf(xmlFormId);
                        	if(index>-1)
                        		$scope.selectedXmlFormIds.splice(index,1);
                        } else {
                            $scope.selectedXmlFormIds.push(xmlFormId);
                        }
                        console.log($scope.checkStat);
                        
                    }

                    function resizeModalElement() {
                        var resetModalHeight = CoreCommonsService.setModalFullHeight($scope.cookieName, modalDialog);
                        $timeout(function() { // timeout is necessary to pass asynchro
                            CoreCommonsService.resizeModalElement(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx, resetModalHeight);
                        }, 0);
                    }

                    function remove() {
                        if ($scope.selectedXmlFormIds.length <1) {
                            return;
                        }
                        for(var i = 0 ; i <$scope.selectedXmlFormIds.length;i++){
                            var elementToDelete = findXmlInCollection($scope.selectedXmlFormIds[i], $scope.xmlForms);
                            var index = $scope.xmlForms.indexOf(elementToDelete);
                            $scope.xmlForms.splice(index, 1);
                        }
                        $scope.selectedXmlFormIds = [];
                        divideAllAvailableXmlForms();
                        console.log($scope.xmlForms);
                    }

                    function addXmlForms() {
                        var notSelectedXmlForms = $scope.notSelectedXmlForms;
                        var cookieName = 'coreApp_modal_userDetailsChooserModal';
                        var modalInstance = $modal.open({
                            template: '<flat-form-chooser-modal modal="modal" available="notSelectedXmlForms" cookie-name="cookieName"></flat-form-chooser-modal>',
                            windowClass: 'softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.modal = $modalInstance;
                                    $scope.notSelectedXmlForms = notSelectedXmlForms;
                                    $scope.cookieName = cookieName;
                                }
                            ]
                        });

                        modalInstance.result.then(function(xmlForms) {
                            angular.forEach(xmlForms, function(xmlForm) {
                                $scope.xmlForms.push(xmlForm);
                            });
                            divideAllAvailableXmlForms();
                            $scope.resizeModalElement();
                        }, function() {

                        });
                    }

                    /**
                     * Divides all available xmlForms which is related to user on selectedXmlForms and notSelectedXmlForms
                     */
                    var divideAllAvailableXmlForms = function() {
                        $scope.selectedXmlForms.length = 0;
                        $scope.notSelectedXmlForms.length = 0;

                        angular.forEach(availableXmlForms, function(availableXmlForm) {
                            if (checkIfXmlFormIsSelectedXmlForm(availableXmlForm)) {
                                $scope.selectedXmlForms.push(availableXmlForm);
                            } else {
                                $scope.notSelectedXmlForms.push(availableXmlForm);
                            }
                        });
                    };

                    /**
                     * Finds xml with xmlFormId in collection xmlForms
                     */
                    var findXmlInCollection = function(xmlFormId, xmlForms) {
                        var findedXml = null;
                        angular.forEach(xmlForms, function(xmlForm) {
                            if (xmlForm.flatFormId === xmlFormId) {
                                findedXml = xmlForm;
                                return;
                            }
                        });
                        return findedXml;
                    };

                    /**
                     * Check if xmlForm is stored in user xmlForms
                     */
                    var checkIfXmlFormIsSelectedXmlForm = function(availableXmlForm) {
                        var isSelected = false;
                        angular.forEach($scope.xmlForms, function(selectXmlForm) {
                            if (availableXmlForm.flatFormId == selectXmlForm.flatFormId) {
                                isSelected = true;
                                return;
                            }
                        });
                        return isSelected;
                    };

                    var validateUserAndSaveIfIsCorrect = function() {
                        $scope.isError = false;
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                        if ($scope.user.userName === null || $scope.user.userName === undefined || $scope.user.userName.length === 0) {
                            $scope.isError = true;
                            $scope.visId = "Please supply an Identifier";
                        } else if ($scope.user.userFullName === null || $scope.user.userFullName === undefined || $scope.user.userFullName.length === 0) {
                            $scope.isError = true;
                            $scope.validation.userFullName = "FullName cannot be empty";
                        } else if ($scope.user.password !== null && $scope.user.password !== undefined && $scope.user.password.length < 4) {
                            $scope.isError = true;
                            $scope.validation.password = "Password's length must be at least 4 chars";
                        } else if ($scope.user.password !== null && $scope.user.confirmPassword !== null && $scope.user.password !== undefined && $scope.user.confirmPassword !== undefined && $scope.user.password !== $scope.user.confirmPassword) {
                            $scope.isError = true;
                            $scope.validation.password = "Password is not confirmed properly";
                        }

                        if ($scope.isError === true) {
                            Flash.create('danger', "Error during " + $scope.operator + " operation");
                        } else {
                            $rootScope.$broadcast("modal:blockAllOperations");
                            UsersPageService.updateUsersInDatabase($scope.user);
                        }
                    };

                    /////////////////////////////////////////////////////////////////////////END XML FORMS ////////////////////////////////////////////////////////////////////////////////////////////



                    ///////////////////////////////////////////////////////////////////////// MOBILE PROFILES ////////////////////////////////////////////////////////////////////////////////////////////
                    $scope.selectedElementId = -1;
                    $scope.selectedElement = {};
                    var mobileProfilesWijmoCollection = new wijmo.collections.CollectionView();
                    $scope.mobileProfiles = [];

                    var userId = $scope.id;

                    // UsersPageService.getMobileProfilesFromDatabase(userId).then(function(data) {
                    //     $scope.mobileProfiles = data.data;
                    // });

                    // create main ctx object
                    $scope.ctx = {
                        flex: null,
                        filter: ''
                    };

                    $scope.$watch('ctx.flex', function() {
                        var flex = $scope.ctx.flex;
                        flexInitialize();
                    });

                    $scope.$watch('mobileProfiles', function() {
                        if ($scope.mobileProfiles !== undefined) {
                            mobileProfilesWijmoCollection.sourceCollection = $scope.mobileProfiles;
                            $scope.ctx.data = mobileProfilesWijmoCollection;
                            //flexInitialize();

                        }
                    });

                    var flexInitialize = function() {
                        var flex = $scope.ctx.flex;
                        var data = $scope.ctx.data;

                        if (flex && data && data.sourceCollection && data.sourceCollection.length >= 0) {
                            flex.isReadOnly = true;
                            flex.selectionMode = "Row";
                            flex.headersVisibility = "Column";
                            flex.itemFormatter = itemFormatter;
                            $timeout(function() { // timeout is necessary to pass asynchro
                                $scope.ctx.data.refresh();
                                //CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
                            }, 0);
                        }
                    };

                    function itemFormatter(panel, r, c, cell) {
                        if (panel.cellType == wijmo.grid.CellType.Cell) {

                            var col = panel.columns[c],
                                html = cell.innerHTML;
                            var profileId = panel.rows[r].dataItem['profileId'];
                            var forDeletion = panel.rows[r].dataItem['forDeletion'];

                            switch (col.name) {
                                case 'index':
                                    html = "" + (r + 1);
                                    break;
                                case 'buttons':
                                    html = '<button type="button" class="btn btn-danger btn-xs" ng-disabled="' + forDeletion + '" ng-click="deleteProfile(' + profileId + ')";">Delete</button>';
                                    break;
                            }
                            if (html != cell.innerHTML) {
                                cell.innerHTML = html;
                                $compile(cell)($scope);
                            }
                        }
                    }
                    var flatForms = FlatFormsPageService.getFlatForms();
                    $scope.deleteProfile = deleteProfile;
                    $scope.selectionChangedHandler = selectionChangedHandler;
                    $scope.addProfile = addProfile;

                    function addProfile() {
                        openModalDeploy();
                    }

                    var openModalDeploy = function() {
                        var flex = $scope.ctx.flex;
                        var xmlForms = flatForms.sourceCollection;
                        var cookieName = 'coreApp_modal_userDetailsChooserModal';
                        var isProfileModule = 1;
                        var modalInstance = $modal.open({
                            template: '<flat-form-chooser-modal modal="modal" available="xmlForms" cookie-name="cookieName" is-profile-module="isProfileModule" ></flat-form-chooser-modal>',
                            windowClass: 'softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.modal = $modalInstance;
                                    $scope.xmlForms = xmlForms;
                                    $scope.cookieName = cookieName;
                                    $scope.isProfileModule = isProfileModule;
                                    $scope.$on('FlatFormChooserModal:close', function(event, args) {
                                        openMobileDeploy(args[0]);
                                    });
                                }
                            ]
                        });
                    };

                    var openMobileDeploy = function(flatForm) {
                        var flex = $scope.ctx.flex;
                        var modalInstance = $modal.open({
                            template: '<profiles-for-users modal="modal" flat-form="flatForm"></profiles-for-users>',
                            windowClass: 'flat-forms-modals softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.modal = $modalInstance;
                                    $scope.flatForm = flatForm;

                                }
                            ]
                        });
                    };

                    $scope.$on('ProfilesForUsers:add', function(event, args) {
                        var profileToAdd = {
                            forDeletion: false,
                            modelId: null,
                            modelVisId: args.modelVisId,
                            profileDescription: args.description,
                            profileId: --$scope.profileNumberId,
                            profileVisId: args.identifier,
                            updatedTime: null,
                            userId: null,
                            userName: null,
                            xmlFormId: null
                        };
                        args.profileId = $scope.profileNumberId;
                        $scope.mobileProfiles.push(profileToAdd);
                        $scope.ctx.data.refresh();
                        args.modelVisId = undefined;
                        profilesToSave.push(args);
                    });

                    function deleteProfile(profileId) {
                        // UsersPageService.deleteUsersProfile($scope.id, profileId);
                        var itemToDelete = CoreCommonsService.findElementByKey($scope.mobileProfiles, profileId, "profileId");
                        if (profileId < 0) {
                            var mobileProfileIndex = findObjectIndexInArray(itemToDelete, $scope.mobileProfiles);
                            var profileToSaveIndex = findObjectIndexInArray(itemToDelete, profilesToSave);
                            $scope.mobileProfiles.splice(mobileProfileIndex, 1);
                            profilesToSave.splice(profileToSaveIndex, 1);
                        } else {
                            itemToDelete.forDeletion = true;
                        }
                        $scope.ctx.data.refresh();
                    }

                    function findObjectIndexInArray(item, array) {
                        for (var i = 0; i < array.length; i++) {
                            if (array[i].profileId === item.profileId) {
                                return i;
                            }
                        }
                        return -1;
                    }

                    function selectionChangedHandler() {
                        var flex = $scope.ctx.flex;
                        var current = flex.collectionView ? flex.collectionView.currentItem : null;
                        if (current !== null) {
                            $scope.selectedElement = current;
                            $scope.selectedElementId = current.ElementId;
                        } else {
                            $scope.selectedElement = {};
                            $scope.selectedElementId = -1;
                        }
                    }
                    $scope.refresh = function() {
                        $scope.ctx.data.refresh();
                    };


                    ///////////////////////////////////////////////////////////////////////// END MOBILE PROFILES ////////////////////////////////////////////////////////////////////////////////////////////
                    function close() {
                        $rootScope.$broadcast('UsersDetails:close');
                    }
                    /**
                     * Try to save Role after clic on button - save means uodate or insert.
                     * @return {[type]} [description]
                     */

                    function save() {
                        $scope.user.userSecurityAssignments = $scope.security;
                        $scope.user.userAdminApp[0] = $scope.adminApp;
                        $scope.user.userAdminApp[1] = $scope.budgetCycleApp;
                        $scope.user.userAdminApp[2] = $scope.dashboardApp;
                        $scope.user.xmlForm = $scope.selectedXmlForms;
                        $scope.user.profilesToSave = profilesToSave;
                        var tmpHiddenRoles = [];
                        var tmpHiddenRoles1 = [];
                        tmpHiddenRoles1 = $scope.hiddenRoles1.concat($scope.hiddenRoles2, $scope.hiddenRoles3);
                        Array.prototype.push.apply(tmpHiddenRoles, $scope.hiddenRoles1);
                        Array.prototype.push.apply(tmpHiddenRoles, $scope.hiddenRoles2);
                        Array.prototype.push.apply(tmpHiddenRoles, $scope.hiddenRoles3);
                        $scope.user.hiddenRoles = angular.copy(tmpHiddenRoles);

                        //.concat($scope.hiddenRoles2).concat($scope.hiddenRoles3));
                        //console.log("user", $scope.user);
                        validateUserAndSaveIfIsCorrect();
                    }

                    $scope.$on("UsersPageService:usersUpdatedError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
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

                    var availableXmlForms = [];
                    $scope.selectedXmlForms = [];
                    $scope.notSelectedXmlForms = [];

                    $scope.$on("UsersPageService:usersDetailsUpdated", function(event, data) {
                    	
                    	if($scope.copy){
                    		data.emailAddress = '';
                    		data.logonAlias = '';
                    		data.userFullName = '';
                    		data.userName = '';
                    		data.userId = -1;
                    	}
                        $scope.user = data;
                        if ($scope.user.userId === -1 || $scope.copy) {
                            $scope.operator = "create";
                        } else {
                            $scope.operator = "edit";
                        }
                        $scope.roles = RolesPageService.getRoles();
                        $scope.security = data.userSecurityAssignments;
                        $scope.adminApp = data.userAdminApp[0];
                        $scope.budgetCycleApp = data.userAdminApp[1];
                        $scope.dashboardApp = data.userAdminApp[2];
                        $scope.hiddenRoles1 = angular.copy(data.hiddenRoles);
                        $scope.hiddenRoles2 = angular.copy(data.hiddenRoles);
                        $scope.hiddenRoles3 = angular.copy(data.hiddenRoles);

                        $scope.xmlForms = data.xmlForm;
                        availableXmlForms = data.availableXmlForm;
                        divideAllAvailableXmlForms();
                        $scope.isDataLoaded = true;

                        $scope.mobileProfiles = data.mobileProfiles;
                    });

                    $scope.$on("RolesPageService:rolesUpdated", function(event, data) {
                        $scope.roles = RolesPageService.getRoles();
                        // deleteDuplicatedRoles();
                    });

                    $scope.$watch('user.userName', function() {
                        $scope.user.userName = angular.lowercase($scope.user.userName);
                    });

                    $scope.$watchCollection(
                        // "This function returns the value being watched. It is called for each turn of the $digest loop"
                        function() {
                            return $scope.roles;
                        },
                        // "This is the change listener, called when the value returned from the above function changes"
                        function(newValue, oldValue) {
                            // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                            // To protect against this we check if oldValue isn't empty.
                            if (newValue !== oldValue && Object.keys(newValue).length !== 0) {
                                separateDuplicatedRoles();
                            }
                        }
                    );

                }
            ]

        };
    }
})();