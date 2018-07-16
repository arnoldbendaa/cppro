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
/**
 * Flat forms page controller implementation
 */
(function() {
    'use strict';

    angular
        .module('adminApp.flatFormsPage')
        .controller('FlatFormsPageController', FlatFormsPageController);

    /* @ngInject */
    function FlatFormsPageController($rootScope, $scope, $compile, $modal, $window, $http, $location, PageService, FilterService, FlatFormsPageService, CoreCommonsService, ContextMenuService) {

        var flatForms = {};
        $scope.selectedFlatFormId = -1;
        $scope.selectedFlatFormVisId = "";
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_flatForm"; 
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.open = open;
        $scope.create = create;
        $scope.deleteFlatForm = deleteFlatForm;
        $scope.exported = exported;
        $scope.imported = imported; 
        $scope.deployWeb = deployWeb;
        $scope.undeployWeb = undeployWeb;
        $scope.deployMobile = deployMobile;
        $scope.undeployMobile = undeployMobile;
        $scope.selectionChangedHandler = selectionChangedHandler;
        activate();
        
        function activate(){
            PageService.setCurrentPageService(FlatFormsPageService);
            flatForms = FlatFormsPageService.getFlatForms();
            $scope.globalSettings = CoreCommonsService.globalSettings;
            $scope.ctx = {
                    flex: null,
                    filter: '',
                    data: flatForms
                };
            ContextMenuService.initialize($('.grid'));
            // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName); 
            $scope.$parent.onFilterWordChange("");
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        }
        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        }

        /**
         * Function is invoked on click the open button.
         */
        function open() {
            openFlatFormEditorInNewTab();
        }

        function create() {
            openFlatFormEditorInNewTab(-1);
        }

        /**
         * Opens flat form editor in new tab using post method.
         * TODO move it to directive
         */
        var openFlatFormEditorInNewTab = function(flatFormId) {
            if (flatFormId === undefined) {
                flatFormId = $scope.selectedFlatFormId;
            }
            var uri = $BASE_PATH + 'flatFormEditor/';
            var form = angular.element(
                '<form id="flatFormEditorPopUp" action="' + uri + '" method="post" target="_blank">' +
                '<input id="flatFormId" name="flatFormId" type="hidden" value="' + flatFormId + '">' +
                '</form>'
            );

            angular.element(document.body).append(form);

            form[0].submit();
            form.remove();
        };

        /**
         * Delete selected recalculate batch
         * Function is invoked after click in delete button on flat form list
         */
        function deleteFlatForm() {
            if ($scope.selectedFinanceCubeId == -1) {
                return;
            }
            swal({
                title: "Are you sure",
                text: "you want to delete Flat Form \"" + $scope.selectedFlatFormVisId + "\" and profiles used by it?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    FlatFormsPageService.deleteFlatForm($scope.selectedFlatFormId, $scope.selectedFlatFormVisId);
                }
            });
        }

        function exported() {
            openModalExport();
        }

        function imported() {
            openModalImport();
        }

        function deployWeb() {
            openModalDeploy(false);
        }

        function undeployWeb() {
            openModalUndeploy(false);
        }

        function deployMobile() {
            openModalDeploy(true);
        }

        function undeployMobile() {
            openModalUndeploy(true);
        }

        /**
         * Example how to open link in new tab programmatically
         */
        var openLinkInNewTab = function() {
            var tabWindowId = $window.open('about:blank', '_blank');
            tabWindowId.location.href = $BASE_PATH;
        }

        /**
         * Set selected table row as current.
         * If previous selected row id is different from current, change selectedFlatFormId.
         * Otherwise reset selectedFlatFormId ('cause FlatForm was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedFlatFormId = current.flatFormId;
                $scope.selectedFlatFormVisId = current.flatFormVisId;
            } else {
                $scope.selectedFlatFormId = -1;
                $scope.selectedFlatFormVisId = "";
            }
            manageActions();
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = FlatFormsPageService.getActions();
            FlatFormsPageService.isOpenDisabled = $scope.selectedFlatFormId === -1;
            FlatFormsPageService.isDeleteDisabled = $scope.selectedFlatFormId === -1;
            if ($scope.selectedFlatFormId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableAction(actions, 'deleteFlatForm');
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };


        /**
         * Open modal (for deploy).
         */
        var openModalDeploy = function(isMobile) {
            var flex = $scope.ctx.flex;
            var currentFlatForm = flex.collectionView ? flex.collectionView.currentItem : null;

            var modalInstance = $modal.open({
                template: '<profiles-deployment modal="modal" flat-form="flatForm" is-mobile="isMobile"></profiles-deployment>',
                windowClass: 'flat-forms-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modal = $modalInstance;
                        $scope.flatForm = currentFlatForm;
                        $scope.isMobile = isMobile;
                    }
                ]
            });
        };

        /**
         * Open modal (for undeploy web).
         */
        var openModalUndeploy = function(isMobile) {
            var selectedFlatFormId = $scope.selectedFlatFormId;
            var selectedFlatFormVisId = $scope.selectedFlatFormVisId;
            var modalInstance = $modal.open({
                template: '<profiles-un-deployment modal="modal" selected-flat-form-id="selectedFlatFormId" selected-flat-form-vis-id="selectedFlatFormVisId" is-mobile="isMobile"></profiles-un-deployment>',
                windowClass: 'flat-forms-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.selectedFlatFormId = selectedFlatFormId;
                        $scope.selectedFlatFormVisId = selectedFlatFormVisId;
                        $scope.isMobile = isMobile;
                        $scope.$on('profilesUnDeployment:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        /**
         * Open modal (for export).
         */
        var openModalExport = function() {
            var modalInstance = $modal.open({
                template: '<flat-forms-export modal="modal"></flat-form-export>',
                windowClass: 'flat-forms-export-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modal = $modalInstance;
                    }
                ]
            });
        };

        /**
         * Open modal (for import).
         */
        var openModalImport = function() {
            var modalInstance = $modal.open({
                template: '<flat-forms-import></flat-form-import>',
                windowClass: 'flat-forms-import-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.$on('FlatFormsImport:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

        /******************************************************** FILTERS ********************************************************/
        var filters = ['byMainWordFilter', 'byFinanceCubeFilter'];
        var filterFunction = FilterService.buildFilterFunction(filters);

        $rootScope.$watch(function() {
            return $rootScope.filter.byWord
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });

        $rootScope.$watch(function() {
            return $rootScope.currentModel
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });


        /******************************************************** CUSTOM CELLS ********************************************************/
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {

                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'index':
                        html = "" + (r + 1);
                        break;
                    case 'buttons':
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open(); ">Open</button> ' +
                            '<button type="button" class="btn btn-danger btn-xs" ng-click="deleteFlatForm();">Delete</button>';
                        break;
                }
                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }
        }

        /******************************************************** WATCHERS ********************************************************/
        $scope.$watch('ctx.flex', function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                flex.isReadOnly = true;
                flex.selectionMode = "Row";
                flex.headersVisibility = "Column"
                flex.itemFormatter = itemFormatter;
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
            }
        });

        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on("SubMenuController:create", function() {
            $scope.selectedRecalculateBatchId = -1;
            $scope.create();
        });

        $scope.$on("SubMenuController:deleteFlatForm", function() {
            $scope.deleteFlatForm();
        });

        $scope.$on("SubMenuController:submit", function() {
            $scope.submit();
        });

        $scope.$on("SubMenuController:exported", function() {
            $scope.exported();
        });

        $scope.$on("SubMenuController:imported", function() {
            $scope.imported();
        });

        $scope.$on("SubMenuController:deployWeb", function() {
            $scope.deployWeb();
        });

        $scope.$on("SubMenuController:undeployWeb", function() {
            $scope.undeployWeb();
        });

        $scope.$on("SubMenuController:deployMobile", function() {
            $scope.deployMobile();
        });

        $scope.$on("SubMenuController:undeployMobile", function() {
            $scope.undeployMobile();
        });

        $scope.$on("SubMenuController:refresh", function() {
            FlatFormsPageService.getFlatForms(true);
        });
    }
})();