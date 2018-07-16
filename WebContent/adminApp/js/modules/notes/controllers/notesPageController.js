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
        .module('adminApp.notesPage')
        .controller('NotesPageController', NotesPageController);

    /* @ngInject */
    function NotesPageController($rootScope, $scope, $compile, $modal, $timeout, NotesPageService, PageService, DataEditorService, FinanceCubesPageService, CoreCommonsService, ContextMenuService) {

        //Current selected elements.
        var selectedFinanceCubeId = 0;
        var rows = {};
        //Group table Notes by user.
        var groupDesc = new wijmo.collections.PropertyGroupDescription("user");
        var delay;
        $scope.financeCubes = [];
        $scope.choosedFinanceCube = {};
        //Id Model selected at control select.
        $scope.selectedModelId = 0;
        $scope.selectedModelVisId = '';
        $scope.selectedBudgetCycleId = 0;
        $scope.selectedDataEntryProfileId = 0;
        $scope.selectedUserId = 0;
        //Id Model Notes selected at table. On create table the same value with selectedModelId, but when user change there at control select they can by different.
        $scope.selectedModelIdInTable = 0;
        $scope.selectedCostCenters = [];
        // $scope.selectedCostCenterId = 0;
        $scope.selectedDate = "";
        $scope.fromDate = new Date(2012, 1, 11);
        $scope.toDate = new Date();
        $scope.notesLoader = NotesPageService.getNotesLoader();
        $scope.notesLoader.isNotesLoaded = false;
        $scope.notesLoader.isNotesLoading = false;
        $scope.openedCalendarFrom = false;
        $scope.openedCalendarTo = false;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_note";
        // CoreCommonsService.changeListingParams(modalDialog, $scope.cookieName);
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.openProfile = openProfile;
        $scope.onChange = onChange;
        $scope.search = search;
        $scope.openCalendarFrom = openCalendarFrom;
        $scope.openCalendarTo = openCalendarTo;
        activate();

        function activate() {
            rows = NotesPageService.getNotesFromService();
            PageService.setCurrentPageService(NotesPageService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            $scope.originalFinanceCubes = FinanceCubesPageService.getFinanceCubes();
            DataEditorService.resetFilters();
            $scope.costCenters = DataEditorService.getCostCenters();
            //Loaders management.
            $scope.loadingManager = DataEditorService.getLoadingManager();
            $scope.ctx = {
                flex: null,
                data: rows
            };
            // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.$parent.onFilterWordChange("");
        }

        if (rows.groupDescriptions.length == 0) {
            rows.groupDescriptions.push(groupDesc);
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        }

        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        }

        /**
         * Set selected table row as current.
         * If previous selected row id is different from current, change selectedDate, selectedCostCenterId.
         * Otherwise reset selectedDate, selectedCostCenterId ('cause Note was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                // $scope.selectedCostCenterId = current.costCenterId;
                $scope.selectedStructureElementId = current.structureElementId;
                $scope.selectedDate = current.date;
                $scope.selectedBudgetCycleId = current.budgetCycleId;
                $scope.selectedDataEntryProfileId = current.dataEntryProfileId;
                $scope.selectedUserId = current.userId;
            } else {
                //$scope.selectedCostCenterId = 0;
                $scope.selectedStructureElementId = 0;
                $scope.selectedDate = "";
                $scope.selectedBudgetCycleId = 0;
                $scope.selectedDataEntryProfileId = 0;
                $scope.selectedUserId = 0;
            }
            manageActions();
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            if (selectedFinanceCubeId != 0 && $scope.selectedDataEntryProfileId != 0) {
                openModal(selectedFinanceCubeId, $scope.selectedDataEntryProfileId);
            }
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = NotesPageService.getActions();

            if ($scope.selectedUsersId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for view Notes for Cost Center).
         * @param  {Integer} financeCubeId
         */
        var openModal = function(financeCubeId, costCenterId) {
            var modalInstance = $modal.open({
                template: '<notes-viewer finance-cube-id="financeCubeId" cost-center-id="costCenterId"></notes-viewer>',
                windowClass: 'notes-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.financeCubeId = financeCubeId;
                        $scope.costCenterId = costCenterId;
                        $scope.$on('NotesViewerPage:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        /**
         * Call generate profile link for Note and open it.
         */
        function openProfile() {
            var profileRef = 'DataEntryProfileCK|UserPK|' + $USER_ID + '|DataEntryProfilePK|' + $scope.selectedDataEntryProfileId + '';

            var uri = $BASE_PATH + 'reviewBudget/';
            var form = angular.element(
                '<form id="flatFormEditorPopUp" action="' + uri + '" method="post" target="_blank">' +
                '<input id="selectedStructureElementId" name="selectedStructureElementId" type="hidden" value="' + $scope.selectedStructureElementId + '">' +
                '<input id="modelId" name="modelId" type="hidden" value="' + $scope.selectedModelId + '">' +
                '<input id="budgetCycleId" name="budgetCycleId" type="hidden" value="' + $scope.selectedBudgetCycleId + '">' +
                '<input id="submitModelName" name="submitModelName" type="hidden" value="' + $scope.selectedModelVisId + '">' +
                '<input id="submitCycleName" name="submitCycleName" type="hidden" value="">' +
                '<input id="selectedDataEntryProfileId" name="profileRef" type="hidden" value="' + profileRef + '">' +
                '</form>'
            );

            angular.element(document.body).append(form);

            form[0].submit();
            form.remove();
        }

        /**
         * Manage data, when Finance Cube was change.
         */
        function onChange() {
            $scope.loadingManager.isRowsLoaded = false;
            $scope.loadingManager.isCostCenterLoaded = false;

            $scope.selectedCostCenters = [];
            manageSelectedIds();
            $timeout.cancel(delay);
            delay = $timeout(function() {
                DataEditorService.getCostsCentersAndExpenseCodes([$scope.selectedModelId]);
            }, 750);
        }

        /**
         * Manage Finance Cubes for selected control.
         */
        function manageFinanceCubes() {
            angular.forEach($scope.originalFinanceCubes.sourceCollection, function(financeCube) {
                $scope.financeCubes.push(financeCube.financeCubeVisId);
            });
        }

        /**
         * Manage FinanceCube and Model id, when Finance Cube was change.
         */
        function manageSelectedIds() {
            var financeCube = CoreCommonsService.findElementByKey($scope.originalFinanceCubes.sourceCollection, $scope.choosedFinanceCube, 'financeCubeVisId');
            selectedFinanceCubeId = financeCube.financeCubeId;
            $scope.selectedModelId = financeCube.model.modelId;
            $scope.selectedModelVisId = financeCube.model.modelVisId;
        }

        function search() {
            var date1 = $scope.fromDate.getFullYear() + "-" + ($scope.fromDate.getMonth() + 1) + "-" + $scope.fromDate.getDate();
            var date2 = $scope.toDate.getFullYear() + "-" + ($scope.toDate.getMonth() + 1) + "-" + $scope.toDate.getDate();
            var selectedCostCentersIds = [];

            var allModelDataForModel = DataEditorService.getAllModelDataForModel([$scope.selectedModelId]);
            var allCostCenterForModel = allModelDataForModel.costCenters;
            if ($scope.selectedCostCenters.length == 0 || $scope.selectedCostCenters.indexOf("All") != -1) {
                for (var i = 0; i < allCostCenterForModel.length; i++) {
                    selectedCostCentersIds.push(allCostCenterForModel[i].dimensionElementId);
                }
            } else {
                for (var i = 0; i < allCostCenterForModel.length; i++) {
                    for (var j = 0; j < $scope.selectedCostCenters.length; j++) {
                        if (allCostCenterForModel[i].dimensionElementVisId == $scope.selectedCostCenters[j]) {
                            selectedCostCentersIds.push(allCostCenterForModel[i].dimensionElementId);
                            j = $scope.selectedCostCenters.length;
                        }
                    }
                }
            }
            $scope.selectedModelIdInTable = $scope.selectedModelId;
            NotesPageService.getNotes(selectedCostCentersIds, selectedFinanceCubeId, date1, date2);
        }

        function openCalendarFrom($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.openedCalendarFrom = true;
        }

        function openCalendarTo($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.openedCalendarTo = true;
        }

        /******************************************************** CUSTOM CELLS ********************************************************/
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {

                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'buttons':
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="openProfile()" ' +
                            'ng-disabled="selectedModelIdInTable != selectedModelId || selectedModelId == 0">Open profile</button>' +
                            ' <button type="button" class="btn btn-primary btn-xs" ng-click="open()"' +
                            'ng-disabled="selectedModelIdInTable != selectedModelId || selectedModelId == 0">Show notes</button>';
                        break;
                }

                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }

            ContextMenuService.initialize($('.grid'));
        }

        /******************************************************** WATCHERS ********************************************************/
        $scope.$watch('ctx.flex', function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                flex.selectionMode = "Row";
                flex.headersVisibility = "Column";
                flex.isReadOnly = true;
                flex.itemFormatter = itemFormatter;
                var additionalElementsToCalcResize = [angular.element(".data-editor-filter")];
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie, additionalElementsToCalcResize);
            }
        });

        $scope.$watch("originalFinanceCubes.sourceCollection", function() {
            if ($scope.originalFinanceCubes.sourceCollection.length > 0) {
                manageFinanceCubes();
            }
        });

        $scope.$on("SubMenuController:openProfile", function() {
            $scope.openProfile();
        });
        $scope.$on('SubMenuController:showNotes', function() {
            $scope.open();
        });
    }
})();