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
        .module('adminApp.profilesPage')
        .controller('MobileProfilesPageController', MobileProfilesPageController);

    /* @ngInject */
    function MobileProfilesPageController($rootScope, $scope, $compile, $modal, PageService, FilterService, FlatFormsPageService, CoreCommonsService, ContextMenuService, ProfilesPageService) {

        var flatForms = FlatFormsPageService.getFlatForms();
        var page = 0;
        var offset = ProfilesPageService.getOffset();
        var isDataToUpdate = true;
        var profiles = new wijmo.collections.CollectionView();
        var numberAllDownloadedProfiles;
        $scope.isProfilesLoaded = false;
        $scope.isMobile = true;
        $scope.selectedFormId = -1;
        $scope.selectedProfileId = -1;
        $scope.selectedUserId = -1;
        $scope.selectedProfileVisId = "";
        $scope.ctx = {
            flex: null,
            filter: '',
            data: profiles
        };
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_mobile_profiles";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.open = open;
        $scope.create = create;
        $scope.deleteProfile = deleteProfile;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.getMobileProfiles = getMobileProfiles;
        $scope.refresh = refresh;
        $scope.edit = edit;
        activate();

        /******************************************************** IMPLEMENTATION ********************************************************/

        function activate() {
            PageService.setCurrentPageService(ProfilesPageService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            ContextMenuService.initialize($('.grid'));
            // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.$parent.onFilterWordChange("");
            return getMobileProfiles();
        }

        function getMobileProfiles() {
            if (isDataToUpdate === false) {
                return;
            }
            $scope.isProfilesLoaded = false;

            return ProfilesPageService.getMobileProfiles(page)
                .then(function(data) {
                    $scope.isProfilesLoaded = true;
                    if (page == 0) {
                        profiles.sourceCollection = data;
                        profiles.refresh();
                    } else {
                        profiles.sourceCollection = profiles.sourceCollection.concat(data);
                    }
                    numberAllDownloadedProfiles = profiles.sourceCollection.length;
                    if (data.length === 0) {
                        isDataToUpdate = false;
                    }
                    page++;
                });
        }

        function onScrollPositionChanged() {
            var myDiv = $('#gsFlexGrid').find('div[wj-part="root"]');
            if (myDiv.prop('offsetHeight') + myDiv.scrollTop() >= myDiv.prop('scrollHeight')) {
                return getMobileProfiles();
            }
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        }

        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        }

        function create() {
            openModalDeploy();
        }

        /**
         * Delete selected selected profile
         * Function is invoked after click in delete button on profile list
         */
        function deleteProfile() {
            if ($scope.selectedProfileId == -1) {
                return;
            }
            swal({
                title: "Are you sure",
                text: "you want to delete profile \"" + $scope.selectedProfileVisId + "\" ?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d9534f"
            }, function(isConfirm) {
                if (isConfirm) {
                    ProfilesPageService.deleteProfile($scope.selectedUserId, $scope.selectedProfileId, true, numberAllDownloadedProfiles)
                        .then(function(data) {
                            refresh();
                        });
                }
            });
        }

        /**
         * Set selected table row as current.
         * If previous selected row id is different from current, change selectedProfileId.
         * Otherwise reset selectedProfileId ('cause Profile was deselected).
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            if (current != null) {
                $scope.selectedProfileId = current.profileId;
                $scope.selectedUserId = current.userId;
                $scope.selectedProfileVisId = current.profileVisId;
                $scope.selectedFormId = current.xmlFormId;
            } else {
                $scope.selectedProfileId = -1;
                $scope.selectedUserId = -1;
                $scope.selectedProfileVisId = "";
                $scope.selectedFormId = -1;
            }
            manageActions();
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = ProfilesPageService.getActions();
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };
        
        function edit(){
            var flatForm = CoreCommonsService.findElementByKey(flatForms.sourceCollection, $scope.selectedFormId, 'flatFormId');
            var profileId = $scope.selectedProfileId
            openMobileDeploy(flatForm, profileId);
        }
        
        var openModalDeploy = function() {
            var isMobile = $scope.isMobile;
            var flex = $scope.ctx.flex;
            var xmlForms = flatForms.sourceCollection;
            var cookieName = 'coreApp_modal_userDetailsChooserModal';
            var isProfileModule = 1;
            var modalInstance = $modal.open({
                template: '<flat-form-chooser-modal modal="modal" available="xmlForms" cookie-name="cookieName" is-profile-module="isProfileModule" is-mobile="isMobile"></flat-form-chooser-modal>',
                windowClass: 'softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modal = $modalInstance;
                        $scope.xmlForms = xmlForms;
                        $scope.cookieName = cookieName;
                        $scope.isProfileModule = isProfileModule;
                        $scope.isMobile = isMobile;
                        $scope.$on('FlatFormChooserModal:close', function(event, args) {
                            openMobileDeploy(args[0], -1);
                        });

                    }
                ]
            });

        };

        var openMobileDeploy = function(flatForm, profileId) {
            var isMobile = $scope.isMobile;
            var flex = $scope.ctx.flex;
            var modalInstance = $modal.open({
                template: '<profiles-deployment modal="modal" flat-form="flatForm" is-mobile="isMobile" profile-id="profileId"></profiles-deployment>',
                windowClass: 'flat-forms-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modal = $modalInstance;
                        $scope.flatForm = flatForm;
                        $scope.isMobile = isMobile;
                        $scope.profileId = profileId;
                    }
                ]
            });
        };

        /**
         * Function is invoked on click the open button.
         */
        function open() {
            openFlatFormEditorInNewTab();
        }

        function refresh() {
            ProfilesPageService.refreshMobileProfiles(numberAllDownloadedProfiles)
                .then(function(data) {
                    profiles.sourceCollection = data;
                    profiles.refresh();
                });
        }

        /**
         * Opens flat form editor in new tab using post method.
         * TODO move it to directive
         */
        var openFlatFormEditorInNewTab = function(flatFormId) {
            if (flatFormId === undefined) {
                flatFormId = $scope.selectedFormId;
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

        /******************************************************** FILTERS ********************************************************/
        var filters = ['byMainWordFilter', 'byModelFilter'];
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
                        html = '<button type="button" class="btn btn-primary btn-xs" ng-click="edit(); ">Edit</button> ' +
                            '<button type="button" class="btn btn-danger btn-xs" ng-click="deleteProfile();">Delete</button>';
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
                flex.headersVisibility = "Column";
                flex.onScrollPositionChanged = onScrollPositionChanged;
                flex.itemFormatter = itemFormatter;
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
            }
        });

        $scope.$on("SubMenuController:create", function() {
            $scope.selectedRecalculateBatchId = -1;
            $scope.create();
        });

        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on("SubMenuController:deleteProfile", function() {
            $scope.deleteProfile();
        });

        $scope.$on("SubMenuController:refresh", function() {
            refresh();
        });

    }
})();