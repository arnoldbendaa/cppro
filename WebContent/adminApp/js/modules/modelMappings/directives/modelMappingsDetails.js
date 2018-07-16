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
        .module('adminApp.modelMappings')
        .directive('modelMappingsDetails', modelMappingsDetails);

    /* @ngInject */
    function modelMappingsDetails($rootScope, $compile, $timeout, Flash, ModelMappingsService, ExternalSystemService, CoreCommonsService, DictionaryService, $modal) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'modelMappings/views/modelMappingsDetails.html',
            scope: {
                mappedModelId: '=model',
                global: '=global'
            },
            replace: true,
            controller: ['$scope', '$element',
                function($scope, $element) {
                    // parameters to resize modal
                    var modalDialog = $(".model-mappings-details-form").closest(".modal-dialog");
                    var elementToResize1 = $(".elementToResize1");
                    var additionalElementsToCalcResize1 = [$(".getToCalc1-1"), $(".getToCalc1-2")];
                    var elementToResize2 = $(".elementToResize2");
                    var additionalElementsToCalcResize2 = [$(".getToCalc2-1"), $(".getToCalc2-2")];
                    var elementToResize3 = $(".elementToResize3");
                    var additionalElementsToCalcResize3 = [$(".getToCalc3-1"), $(".getToCalc3-2")];
                    var elementToResize5ccLeft = $(".elementToResize5ccLeft");
                    var elementToResize5ccRight = $(".elementToResize5ccRight");
                    var elementToResize5ecLeft = $(".elementToResize5ecLeft");
                    var elementToResize5ecRight = $(".elementToResize5ecRight");
                    var additionalElementsToCalcResize5 = [$(".getToCalc5-1"), $(".getToCalc5-2"), $(".step5tabset .nav-tabs"), 30];
                    var elementToResize6ccLeft = $(".elementToResize6ccLeft");
                    var elementToResize6ccRight = $(".elementToResize6ccRight");
                    var elementToResize6ecLeft = $(".elementToResize6ecLeft");
                    var elementToResize6ecRight = $(".elementToResize6ecRight");
                    var additionalElementsToCalcResize6 = [$(".getToCalc6-1"), $(".getToCalc6-2"), $(".step6tabset .nav-tabs"), 30];

                    $scope.cookieName = "adminPanel_modal_modelMappings";
                    // try to resize and move modal based on the cookie
                    CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    $scope.resizedColumn = function(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    };
                    $scope.sortedColumn = function(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    };
                    var allElementsToResize = [
                        elementToResize1,
                        elementToResize2,
                        elementToResize3,
                        elementToResize5ccLeft,
                        elementToResize5ccRight,
                        elementToResize5ecLeft,
                        elementToResize5ecRight,
                        elementToResize6ccLeft,
                        elementToResize6ccRight,
                        elementToResize6ecLeft,
                        elementToResize6ecRight
                    ];
                    var allAdditionalElementsToCalcResize = [
                        additionalElementsToCalcResize1,
                        additionalElementsToCalcResize2,
                        additionalElementsToCalcResize3,
                        additionalElementsToCalcResize5,
                        additionalElementsToCalcResize5,
                        additionalElementsToCalcResize5,
                        additionalElementsToCalcResize5,
                        additionalElementsToCalcResize6,
                        additionalElementsToCalcResize6,
                        additionalElementsToCalcResize6,
                        additionalElementsToCalcResize6
                    ];
                    $timeout(function() { // timeout is necessary to pass asynchro
                        CoreCommonsService.allowResizingModal(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize);
                    }, 0);
                    // resizeModalElement - ie. after click on previous/next button
                    $scope.resizeModalElement = function() {
                        CoreCommonsService.resizeModalElement(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize);
                    };
                    $scope.$on("CoreCommonsService:CanTryToRefreshFlex", function() {
                        switch ($scope.actualStep) {
                            case 0: // Step 1 - External System
                                if ($scope.externalSystemsCtx.data) {
                                    $scope.externalSystemsCtx.data.refresh();
                                }
                                break;
                            case 1: // Step 2 - Company
                                $scope.externalCompaniesCtx.data.refresh();
                                break;
                            case 2: // Step 3 - Ledger
                                $scope.externalLedgersCtx.data.refresh();
                                break;
                            case 3:
                                break;
                            case 4: // Step 5 - Map Dimension 
                                businessDimensionGrid.refresh();
                                accountDimensionGrid.refresh();
                                break;
                            case 5: // Step 6 - Map Dimension Elements
                                businessDimensionElementGrid.refresh();
                                accountDimensionElementGrid.refresh();
                                break;
                            case 6: // Step 7 - Calendar
                                console.log(calendarGrid);
                                calendarGrid.refresh();
                                break;
                            case 7: // Step 8 - Finance Cubes
                                $scope.financeCubeCtx.data.refresh();
                                break;
                        }
                    });

                    $scope.globalSettings = CoreCommonsService.globalSettings;
                    $scope.stepNextDisable = true;
                    $scope.stepPreviousDisable = true;
                    $scope.actualStep = 0;
                    $scope.calendarLoaded = false;


                    $scope.steps = [{
                        active: true,
                        disable: false
                    }, {
                        active: false,
                        disable: true
                    }, {
                        active: false,
                        disable: true
                    }, {
                        active: false,
                        disable: true
                    }, {
                        active: false,
                        disable: true
                    }, {
                        active: false,
                        disable: true
                    }, {
                        active: false,
                        disable: true
                    }, {
                        active: false,
                        disable: true
                    }, {
                        active: false,
                        disable: true
                    }];

                    // Model Mappings - General
                    $scope.mappedModelLoader = ModelMappingsService.getLoader();
                    $scope.mappedModel = null;

                    // Step 1 - External System
                    var copyOfSelectedExternalSystem = {};
                    $scope.externalSystemsLoader = ExternalSystemService.getLoader();
                    $scope.externalSystemsCtx = {
                        flex: null,
                        filter: '',
                        data: null
                    };
                    $scope.selectedExternalSystem = null;

                    // Step 2 - Company
                    var externalCompaniesView = new wijmo.collections.CollectionView();
                    var copyOfSelectedCompanies = []; // is used to check if user change selectedExternalCompanies collection. if not - we don't ask for external ledger data and next dimensions data

                    $scope.externalCompaniesCtx = {
                        flex: null,
                        filter: '',
                        data: externalCompaniesView
                    };
                    $scope.selectedExternalCompanies = [];

                    // Step 3 - Ledger
                    var externalLedgersView = new wijmo.collections.CollectionView();
                    var copyOfSelectedExternalLedger = {};

                    $scope.externalLedgersCtx = {
                        flex: null,
                        filter: '',
                        data: externalLedgersView
                    };
                    $scope.selectedExternalLedger = null;

                    // Step 4 - Model details
                    $scope.suggestedModel = {
                        modelVisId: null,
                        modelDescription: null
                    };
                    var modelSuggestedAndDimension;

                    // Step 5 - Map Dimension
                    var basicColumns = [{
                        header: 'Vis Id',
                        binding: 'visId',
                        width: '*'
                    }, {
                        header: 'Description',
                        binding: 'description',
                        width: '*'
                    }];

                    var businessAdditionalColumns = [{
                        header: 'R.A.Hier',
                        binding: 'responsibilityAreaHierarchy',
                        width: '*',
                        name: 'responsibilityAreaHierarchy'
                    }];

                    var companyColumns = [];
                    var businessDimensionGrid;
                    var accountDimensionGrid;

                    var businessDimensionView = new wijmo.collections.CollectionView();
                    var accountDimensionView = new wijmo.collections.CollectionView();

                    $scope.accountDimensionTreeModel = {};
                    $scope.businessDimensionTreeModel = {};

                    // Step 6 - Map Dimension Elements
                    var basicDimensionElementsColumns = [{
                        header: 'Vis Id',
                        binding: 'visId1',
                        width: '*'
                    }, {
                        header: 'Mapping',
                        binding: 'mapping',
                        width: '*'
                    }];

                    var businessDimensionElementGrid;
                    var accountDimensionElementGrid;
                    var businessDimensionElementTree;
                    var accountDimensionElementTree;

                    var businessDimensionElementView = new wijmo.collections.CollectionView();
                    var accountDimensionElementView = new wijmo.collections.CollectionView();

                    // Step 7 - Calendar
                    var ourCalendarLevels = ["Year", "Month", "Opening Balance"];
                    var calendarLevels = ["Year", "Half Year", "Quarter", "Month", "Week", "Day", "Opening Balance", "Adjustment Period", "Additional Period 1", "Additional Period 2"];
                    var calendarGrid;
                    var calendarRows = [];
                    var calendarColumns = null;

                    var basicCalendarColumns = [{
                        header: 'Level',
                        binding: 'name',
                        width: '2*'
                    }];

                    var calendarView = new wijmo.collections.CollectionView();
                    $scope.modelSuggestedCalendar = {
                        calendarVisId: null,
                        calendarDescription: null,
                        years: null
                    };

                    // Step 8 - FinanceCubes
                    var financeCubeView = new wijmo.collections.CollectionView();
                    $scope.isEditFinanceCubeButtonDisabled = false;
                    $scope.isDeleteFinanceCubeButtonDisabled = false;

                    $scope.financeCubeCtx = {
                        flex: null,
                        filter: '',
                        data: financeCubeView
                    };

                    $scope.currentFinanceCube = null;

                    /********************************* Model Mappings - General ***************************/
                    if ($scope.mappedModelId != -1) {
                        $scope.mappedModel = ModelMappingsService.getMappedModel($scope.mappedModelId, $scope.global);
                    } else {
                        $scope.mappedModel = ModelMappingsService.createEmptyMappedModel();
                        $scope.mappedModel.validationError = null;
                        $scope.mappedModel.global = $scope.global;
                    }

                    $scope.$watch('mappedModelLoader.mappedModelDetailsLoaded', function() {
                        if ($scope.mappedModelLoader.mappedModelDetailsLoaded) {
                            if ($scope.mappedModel.validationError) {
                                $scope.isFinishBtnDisabled = true;
                                $scope.stepPreviousDisable = true;
                                $scope.stepNextDisable = true;
                            } else {
                                getExternalSystems();
                            }
                        }
                    });

                    /********************************* Step 1 - External System ***************************/
                    function getExternalSystems() {

                        $scope.externalSystemsCtx.data = ExternalSystemService.getExternalSystems(true);

                    }

                    $scope.$watch('externalSystemsLoader.externalSystemsLoaded', function() {
                        if ($scope.mappedModelLoader.mappedModelDetailsLoaded && $scope.externalSystemsLoader.externalSystemsLoaded) {

                            if ($scope.mappedModel && $scope.mappedModel.externalSystem) {
                                angular.forEach($scope.externalSystemsCtx.data.items, function(item) {
                                    if ($scope.mappedModel.externalSystem.externalSystemId === item.externalSystemId) {
                                        $scope.selectedExternalSystem = item;
                                        angular.copy($scope.selectedExternalSystem, copyOfSelectedExternalSystem);

                                        getExternalCompanies();
                                        updateStep1Wizard();
                                    }
                                });
                            }
                        }
                    });

                    $scope.$watch('steps[0].active', function() {
                        if ($scope.steps[0].active) {
                            var flex = $scope.externalSystemsCtx.flex;
                            if (flex) {
                                $timeout(function() {
                                    flex.refresh();
                                }, 10);
                            }
                        }
                    });

                    $scope.selectExternalSystem = function(externalSystem) {
                        if (externalSystem.enabled) {
                            if ($scope.selectedExternalSystem != externalSystem) {
                                $scope.selectedExternalSystem = externalSystem;
                                //getExternalCompanies();
                            } else {
                                $scope.selectedExternalSystem = null;
                                $scope.selectedExternalCompanies.length = 0;
                            }
                            updateStep1Wizard();
                        }
                    };

                    function ifStep1IsValid() {
                        return $scope.selectedExternalSystem !== null;
                    }

                    function updateStep1Wizard() {
                        if (ifStep1IsValid()) {
                            $scope.steps[1].disable = false;
                            $scope.stepNextDisable = false;
                        } else {
                            $scope.steps[1].disable = true;
                            $scope.stepNextDisable = true;
                        }
                    }

                    function checkIfExternalSystemChanged() {
                        return $scope.selectedExternalSystem.externalSystemId !== copyOfSelectedExternalSystem.externalSystemId;
                    }
                    /********************************* Step 2 - Company ***********************************/
                    function getExternalCompanies() {
                        if ($scope.selectedExternalSystem !== null) {
                            var externalRequest = {
                                global: $scope.global,
                                externalSystem: $scope.selectedExternalSystem
                            };
                            externalCompaniesView.sourceCollection = ModelMappingsService.getExternalCompaniesFromServer(externalRequest);
                        }
                    }

                    $scope.$watch('mappedModelLoader.externalCompaniesLoaded', function() {
                        if ($scope.mappedModelLoader.externalCompaniesLoaded) {
                            externalCompaniesView.refresh();

                            if ($scope.mappedModel.companies) {
                                $scope.selectedExternalCompanies.length = 0;

                                var companyIds = $scope.mappedModel.companies.split(", ");
                                angular.forEach(externalCompaniesView.items, function(item) {
                                    if (companyIds.indexOf(item.companyId.toString()) > -1) {
                                        $scope.selectedExternalCompanies.push(item);
                                    }
                                });
                                angular.copy($scope.selectedExternalCompanies, copyOfSelectedCompanies);
                                updateStep2Wizard();
                                getExternalLedgers();
                            }
                        }
                    });

                    $scope.$watch('steps[1].active', function() {
                        if ($scope.steps[1].active) {
                            if (checkIfExternalSystemChanged()) {
                                angular.copy($scope.selectedExternalSystem, copyOfSelectedExternalSystem);
                                getExternalCompanies();
                            } else {
                                var flex = $scope.externalCompaniesCtx.flex;
                                if (flex) {
                                    $timeout(function() {
                                        flex.refresh();
                                    }, 10);
                                }
                            }
                        }
                    });

                    $scope.isDisabledExternalCompany = function(externalCompany) {
                        if ($scope.steps[1].active && $scope.mappedModel.companies && externalCompany) {
                            if ($scope.global === false) {
                                return $scope.mappedModel.mappedModelId !== -1;
                            } else {
                                var companyIds = $scope.mappedModel.companies.split(", ");
                                return companyIds.indexOf(externalCompany.companyId.toString()) > -1;
                            }
                        }
                        return false;
                    };

                    $scope.isSelectedExternalCompany = function(externalCompany) {
                        var index = $scope.selectedExternalCompanies.indexOf(externalCompany);
                        if (index == -1) { // not selected
                            return false;
                        } else { // selected
                            return true;
                        }
                    };

                    $scope.selectExternalCompany = function(externalCompany) {
                        if ($scope.global) {
                            var index = $scope.selectedExternalCompanies.indexOf(externalCompany);
                            if (index == -1) { // not selected
                                $scope.selectedExternalCompanies.push(externalCompany);
                            } else { // selected
                                $scope.selectedExternalCompanies.splice(index, 1);
                            }
                            $scope.selectedExternalCompanies.sort(function(company1, company2) {
                                if (parseInt(company1.companyId) < parseInt(company2.companyId)) {
                                    return -1;
                                } else if (parseInt(company1.companyId) > parseInt(company2.companyId)) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            });
                        } else {
                            $scope.selectedExternalCompanies.length = 0;
                            $scope.selectedExternalCompanies.push(externalCompany);
                        }
                        updateStep2Wizard();
                    };

                    function ifStep2IsValid() {
                        return $scope.selectedExternalCompanies.length > 0;
                    }

                    function updateStep2Wizard() {
                        if (ifStep2IsValid()) {
                            $scope.steps[2].disable = false;
                            $scope.stepNextDisable = false;
                        } else {
                            $scope.steps[2].disable = true;
                            $scope.stepNextDisable = true;
                        }
                    }

                    function checkIfCompaniesChanged() {
                        if ($scope.selectedExternalCompanies.length !== copyOfSelectedCompanies.length) {
                            return true;
                        }
                        var isCompaniesChanged = false;
                        angular.forEach($scope.selectedExternalCompanies, function(company) {
                            var copyCompany = CoreCommonsService.findElementByKey(copyOfSelectedCompanies, company.companyId, "companyId");
                            if (copyCompany === null) {
                                isCompaniesChanged = true;
                                return;
                            }
                        });
                        return isCompaniesChanged;
                    }

                    /********************************* Step 3 - Ledger ***********************************/
                    function getExternalLedgers() {
                        if ($scope.selectedExternalCompanies.length > 0) {
                            var ledgerRequest = {
                                global: $scope.global,
                                externalSystem: $scope.selectedExternalSystem,
                                companies: $scope.selectedExternalCompanies
                            };
                            externalLedgersView.sourceCollection = ModelMappingsService.getExternalLedgersFromServer(ledgerRequest);
                        }
                    }

                    $scope.$watch('mappedModelLoader.externalLedgersLoaded', function() {
                        if ($scope.mappedModelLoader.externalLedgersLoaded) {
                            externalLedgersView.refresh();
                            var elements = externalLedgersView.items;
                            if ($scope.selectedExternalLedger === null && $scope.mappedModel && $scope.mappedModel.ledgerVisId) {
                                $scope.selectedExternalLedger = CoreCommonsService.findElementByKey(elements, $scope.mappedModel.ledgerVisId, "ledgerVisId");
                                angular.copy($scope.selectedExternalLedger, copyOfSelectedExternalLedger);
                                getModelSuggestedAndDimensions();
                                updateStep3Wizard();
                            } else if ($scope.selectedExternalLedger !== null) {
                                $scope.selectedExternalLedger = CoreCommonsService.findElementByKey(elements, $scope.selectedExternalLedger.ledgerVisId, "ledgerVisId");
                                angular.copy($scope.selectedExternalLedger, copyOfSelectedExternalLedger);
                                getModelSuggestedAndDimensions();
                                updateStep3Wizard();
                            }
                        }
                    });

                    $scope.$watch('steps[2].active', function() {
                        if ($scope.steps[2].active) {
                            if (checkIfCompaniesChanged()) {
                                angular.copy($scope.selectedExternalCompanies, copyOfSelectedCompanies);
                                getExternalLedgers();

                                if (businessDimensionElementTree) {
                                    businessDimensionElementTree.close_all();
                                    businessDimensionElementTree.refresh();
                                    businessDimensionElementView.sourceCollection.length = 0;
                                }
                                if (accountDimensionElementTree) {
                                    accountDimensionElementTree.close_all();
                                    accountDimensionElementTree.refresh();
                                    businessDimensionElementView.sourceCollection.length = 0;
                                }
                            } else {
                                var flex = $scope.externalLedgersCtx.flex;
                                if (flex) {
                                    $timeout(function() {
                                        flex.refresh();
                                    }, 10);
                                }
                            }
                        }
                    });

                    $scope.selectExternalLedger = function(externalLedger) {
                        if ($scope.selectedExternalLedger != externalLedger) {
                            $scope.selectedExternalLedger = externalLedger;
                            //getModelSuggestedAndDimensions();
                        } else {
                            $scope.selectedExternalLedger = null;
                        }
                        updateStep3Wizard();
                    };

                    function ifStep3IsValid() {
                        return $scope.selectedExternalLedger !== null;
                    }

                    function updateStep3Wizard() {
                        if (ifStep3IsValid()) {
                            $scope.steps[3].disable = false;
                            $scope.stepNextDisable = false;
                        } else {
                            $scope.steps[3].disable = true;
                            $scope.stepNextDisable = true;
                        }
                    }

                    function checkIfExternalLedgerChanged() {
                        return $scope.selectedExternalLedger.ledgerVisId !== copyOfSelectedExternalLedger.ledgerVisId;
                    }

                    /********************************* Step 4 - Model details ***********************************/
                    function getModelSuggestedAndDimensions() {
                        var dimensionRequest = {
                            global: $scope.global,
                            externalSystem: $scope.selectedExternalSystem,
                            ledger: $scope.selectedExternalLedger
                        };
                        modelSuggestedAndDimension = ModelMappingsService.getModelSuggestedAndDimensionsFromServer(dimensionRequest);
                    }

                    $scope.$watch('mappedModelLoader.externalModelSuggestedAndDimensionsLoaded', function() {
                        if ($scope.mappedModelLoader.externalModelSuggestedAndDimensionsLoaded) {
                            if (!$scope.suggestedModel.modelVisId) {
                                $scope.suggestedModel.modelVisId = modelSuggestedAndDimension.suggestedModelVisId;
                                if ($scope.mappedModel.mappedModelVisId) {
                                    $scope.suggestedModel.modelVisId = $scope.mappedModel.mappedModelVisId;
                                }
                            }

                            if (!$scope.suggestedModel.modelDescription) {
                                $scope.suggestedModel.modelDescription = modelSuggestedAndDimension.suggestedModelDescription;
                                if ($scope.mappedModel.mappedModelDescription) {
                                    $scope.suggestedModel.modelDescription = $scope.mappedModel.mappedModelDescription;
                                }
                            }

                            //TODO do poprawy !!!!!!!!!!!!!!!!!!!!!!!!!!!
                            var mappedBusinessDimensions = null;
                            var mappedAccountDimensions = null;
                            businessDimensionView.sourceCollection.length = 0;
                            accountDimensionView.sourceCollection.length = 0;
                            if ($scope.mappedModel && $scope.mappedModel.mappedDimensions && $scope.mappedModel.mappedDimensions) {
                                mappedBusinessDimensions = ($scope.mappedModel.mappedDimensions[0].type === 2) ? $scope.mappedModel.mappedDimensions[0] : $scope.mappedModel.mappedDimensions[1];
                                mappedAccountDimensions = ($scope.mappedModel.mappedDimensions[1].type === 1) ? $scope.mappedModel.mappedDimensions[1] : $scope.mappedModel.mappedDimensions[0];
                            }
                            $scope.businessDimensionTreeModel = manageDimensionView(modelSuggestedAndDimension.businessDimension, mappedBusinessDimensions, businessDimensionView);
                            businessDimensionView.refresh();
                            $scope.accountDimensionTreeModel = manageDimensionView(modelSuggestedAndDimension.accountDimension, mappedAccountDimensions, accountDimensionView);
                            accountDimensionView.refresh();
                            updateStep4Wizard();
                        }
                    });

                    function manageDimensionView(suggestedDimensions, mappedDimensions, dimensionView) {
                        if (!mappedDimensions || !suggestedDimensions) {
                            return suggestedDimensions;
                        }

                        var sourceCollection = dimensionView.sourceCollection;
                        // clear source collection
                        sourceCollection.length = 0;

                        var mappedChildren = mappedDimensions.mappedHierchies;
                        var suggestedChildren = angular.copy(suggestedDimensions.children);

                        for (var i = 0; i < suggestedChildren.length; i++) {
                            var suggestedChild = suggestedChildren[i];

                            var mappedChild = CoreCommonsService.findElementByKey(mappedChildren, suggestedChild.id, 'mappedHierarchyVisId');
                            var index = suggestedChildren.indexOf(suggestedChild);
                            if (mappedChild) {
                                suggestedChild.state.disabled = true;
                                var row = {
                                    visId: mappedChild.hierarchyVisId,
                                    description: mappedChild.hierarchyDescription,
                                    responsibilityAreaHierarchy: mappedChild.responsibilityAreaHierarchy,
                                    id: mappedChild.mappedHierarchyVisId,
                                    visId1: mappedChild.visId1,
                                    visId2: mappedChild.visId2
                                };

                                if ($scope.global) {
                                    var companies = suggestedChild.companies;
                                    for (var j = 0; j < companies.length; j++) {
                                        var company = companies[j];
                                        if (mappedChild.selectedCompanies.indexOf(company) !== -1) {
                                            row[company] = true;
                                        } else {
                                            row[company] = false;
                                        }
                                    }
                                } else {
                                    row["" + $scope.selectedExternalCompanies[0].companyId] = true;
                                }
                                dimensionView.sourceCollection.push(row);
                            } else {
                                suggestedChildren.splice(index, 1);
                                i--;
                            }
                        }
                        return suggestedDimensions;
                    }

                    $scope.$watch('steps[3].active', function() {
                        if ($scope.steps[3].active) {
                            if (checkIfExternalLedgerChanged()) {
                                angular.copy($scope.selectedExternalLedger, copyOfSelectedExternalLedger);
                                getModelSuggestedAndDimensions();
                            }
                        }
                    });

                    $scope.$watch('suggestedModel', function() {
                        if ($scope.mappedModelLoader.externalModelSuggestedAndDimensionsLoaded) {
                            updateStep4Wizard();
                        }
                    }, true);

                    function ifStep4IsValid() {
                        if (!$scope.suggestedModel.modelVisId || $scope.suggestedModel.modelVisId === "") {
                            return false;
                        } else if (!$scope.suggestedModel.modelDescription || $scope.suggestedModel.modelDescription === "") {
                            return false;
                        }
                        return true;
                    }

                    function updateStep4Wizard() {
                        if (ifStep4IsValid() === true) {
                            $scope.steps[4].disable = false;
                            $scope.stepNextDisable = false;
                        } else {
                            $scope.steps[4].disable = true;
                            $scope.stepNextDisable = true;
                        }
                    }
                    /********************************* Step 5 - Dimensions and Hierarchies ***********************************/
                    function manageCompanyColumns(companies) {
                        companyColumns.length = 0;
                        for (var i = 0; i < companies.length; i++) {
                            var object = {
                                header: companies[i].toString(),
                                binding: '' + companies[i],
                                width: '*',
                                name: '' + companies[i],
                                align: 'center',
                                dataType: 'String'
                            };
                            companyColumns.push(object);
                        }
                    }

                    $scope.$watch('steps[4].active', function() {
                        if ($scope.steps[4].active) {
                            manageCompanyColumns($scope.selectedExternalLedger.companies);
                            createDimensionFlexes();
                            updateStep5Wizard();
                        }
                    });

                    function createDimensionFlexes() {
                        var businessColumns = basicColumns.concat(businessAdditionalColumns).concat(companyColumns);
                        businessDimensionGrid = createFlex(businessDimensionGrid, "#businessDimensionTable", businessDimensionView, businessColumns, "selectBusiness");

                        var accountColumns = basicColumns.concat(companyColumns);
                        accountDimensionGrid = createFlex(accountDimensionGrid, "#accountDimensionTable", accountDimensionView, accountColumns, "selectAccount");
                    }

                    function createFlex(dimensionFlexGrid, elementName, dimensionView, gridColumns, onSelectCheckboxNameFunction) {
                        if (angular.isUndefined(dimensionFlexGrid)) {
                            dimensionFlexGrid = new wijmo.grid.FlexGrid(elementName);
                            dimensionFlexGrid.autoGenerateColumns = false;
                            dimensionFlexGrid.isReadOnly = true;
                            dimensionFlexGrid.selectionMode = "Row";
                            dimensionFlexGrid.allowSorting = false;
                        }

                        dimensionFlexGrid.columns.length = 0;
                        for (var i = 0; i < gridColumns.length; i++) {
                            var col = new wijmo.grid.Column();
                            wijmo.copy(col, gridColumns[i]);
                            dimensionFlexGrid.columns.push(col);
                        }

                        dimensionFlexGrid.itemsSource = dimensionView;
                        dimensionFlexGrid.itemFormatter = function(panel, r, c, cell) {
                            var column, html;
                            if (c === 0 && panel.cellType == wijmo.grid.CellType.Cell) {
                                column = panel.columns[c];
                                html = cell.innerHTML;
                                var data = panel.rows[r].dataItem;
                                if ($scope.steps[4].active && $scope.mappedModelId === -1) {
                                    html = "" + $scope.suggestedModel.modelVisId + data.visId;
                                }
                                if (html != cell.innerHTML) {
                                    cell.innerHTML = html;
                                    $compile(cell)($scope);
                                }
                            }
                            if (c > 1 && panel.cellType == wijmo.grid.CellType.Cell) {
                                column = panel.columns[c];
                                html = cell.innerHTML;
                                data = panel.rows[r].dataItem;

                                html = "";
                                if (angular.isDefined(data[column._name])) {
                                    html = '<input type="checkbox" ';
                                    html += 'ng-click="' + onSelectCheckboxNameFunction + '(' + r + ',\'' + column._name + '\')" ';
                                    if (data[column._name] === true) {
                                        html += 'checked ';
                                    }
                                    html += '/>';
                                }
                                if (html != cell.innerHTML) {
                                    cell.innerHTML = html;
                                    $compile(cell)($scope);
                                }
                            }
                        };
                        return dimensionFlexGrid;
                    }

                    function addDimensionNode(dimensionView, node, type) {
                        if (angular.isDefined(node.text)) {
                            var row = {
                                visId: node.original.visId,
                                description: node.original.description,
                                //responsibilityAreaHierarchy: false,
                                id: node.id,
                                visId1: node.original.visId1,
                                visId2: node.original.visId2
                            };

                            if (type == "business") {
                                row.responsibilityAreaHierarchy = false;
                            } else if (type == "account") {
                                row.responsibilityAreaHierarchy = "";
                            }

                            if ($scope.global) {
                                var companies = (node.original.companies) ? node.original.companies : [];
                                for (var i = 0; i < companies.length; i++) {
                                    row[companies[i]] = false;
                                }
                            } else {
                                row["" + $scope.selectedExternalCompanies[0].companyId] = true;
                            }

                            dimensionView.sourceCollection.push(row);
                            dimensionView.refresh();
                            updateStep5Wizard();
                        }
                    }

                    $scope.selectBusinessDimensionNode = function(e, data) {
                        addDimensionNode(businessDimensionView, data.node, "business");
                    };

                    $scope.selectAccountDimensionNode = function(e, data) {
                        addDimensionNode(accountDimensionView, data.node, "account");
                    };

                    function removeDimensionNode(dimensionView, node) {
                        if (angular.isDefined(node.text)) {
                            for (var i = 0; i < dimensionView.sourceCollection.length; i++) {
                                if (node.original.id === dimensionView.sourceCollection[i].id)
                                    dimensionView.sourceCollection.splice(i, 1);
                            }
                            dimensionView.refresh();
                        }
                        updateStep5Wizard();
                    }

                    $scope.deselectBusinessDimensionNode = function(e, data) {
                        removeDimensionNode(businessDimensionView, data.node);
                    };

                    $scope.deselectAccountDimensionNode = function(e, data) {
                        removeDimensionNode(accountDimensionView, data.node);
                    };

                    $scope.selectBusiness = function(rowNumber, columnName) {
                        var dimensionView = businessDimensionView;
                        var dimensionCollection = dimensionView.sourceCollection;
                        var dimensionRow = dimensionCollection[rowNumber];

                        var booleanValue = dimensionRow[columnName];
                        var property;
                        if (columnName === "responsibilityAreaHierarchy") {
                            if (booleanValue === true) {
                                console.log("walidacja - nie mozna odznaczyc responsibilityAreaHierarchy");
                            } else {
                                for (var i = 0; i < $scope.selectedExternalLedger.companies.length; i++) {
                                    var company = $scope.selectedExternalLedger.companies[i];
                                    var property;
                                    for (property in dimensionRow) {
                                        if (property === company.toString()) {
                                            dimensionRow[property] = true;
                                        }
                                    }
                                }
                                updateResponsibilityAreaHierarchy(rowNumber);
                            }
                        } else {
                            if (booleanValue === true) {
                                if ($scope.mappedModel.mappedModelId !== -1) {
                                    var responsibilityAreaHierarchy = dimensionRow.responsibilityAreaHierarchy;
                                    if (responsibilityAreaHierarchy) {
                                        console.log("walidacja - nie mozna odznaczyc responsibilityAreaHierarchy");
                                    } else {
                                        for (i = 0; i < $scope.selectedExternalLedger.companies.length; i++) {
                                            company = $scope.selectedExternalLedger.companies[i];
                                            for (property in dimensionRow) {
                                                if (property === company.toString()) {
                                                    dimensionRow[property] = !booleanValue;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    dimensionRow.responsibilityAreaHierarchy = !booleanValue;
                                    for (i = 0; i < $scope.selectedExternalLedger.companies.length; i++) {
                                        company = $scope.selectedExternalLedger.companies[i];
                                        for (property in dimensionRow) {
                                            if (property === company.toString()) {
                                                dimensionRow[property] = !booleanValue;
                                            }
                                        }
                                    }
                                }
                            } else {
                                for (i = 0; i < $scope.selectedExternalLedger.companies.length; i++) {
                                    company = $scope.selectedExternalLedger.companies[i];
                                    for (property in dimensionRow) {
                                        if (property === company.toString()) {
                                            dimensionRow[property] = true;
                                        }
                                    }
                                }
                                updateResponsibilityAreaHierarchy(rowNumber);
                            }
                        }
                        dimensionView.refresh();
                        updateStep5Wizard();
                    };

                    function updateResponsibilityAreaHierarchy(rowNumber) {
                        var sourceCollection = businessDimensionView.sourceCollection;
                        for (var i = 0; i < sourceCollection.length; i++) {
                            if (i !== rowNumber) {
                                sourceCollection[i].responsibilityAreaHierarchy = false;
                            } else {
                                sourceCollection[i].responsibilityAreaHierarchy = true;
                            }
                        }
                    }

                    $scope.selectAccount = function(rowNumber, columnName) {
                        var dimensionView = accountDimensionView;
                        var dimensionCollection = dimensionView.sourceCollection;
                        var dimensionRow = dimensionCollection[rowNumber];

                        var booleanValue = dimensionRow[columnName];
                        for (var i = 0; i < $scope.selectedExternalLedger.companies.length; i++) {
                            var company = $scope.selectedExternalLedger.companies[i];
                            for (var property in dimensionRow) {
                                if (property === company.toString()) {
                                    dimensionRow[property] = !booleanValue;
                                }
                            }
                        }
                        dimensionView.refresh();
                        updateStep5Wizard();
                    };

                    function ifStep5IsValid() {
                        var isAccountValid = false;
                        var sourceCollection = accountDimensionView.sourceCollection;
                        for (var i = 0; i < sourceCollection.length; i++) {
                            var dimensionRow = sourceCollection[i];
                            for (var j = 0; j < $scope.selectedExternalLedger.companies.length; j++) {
                                var company = $scope.selectedExternalLedger.companies[j];
                                for (var property in dimensionRow) {
                                    if (property === company.toString() && dimensionRow[property] === true) {
                                        isAccountValid = true;
                                        break;
                                    }
                                }
                            }
                        }

                        var isBusinessValid = false;
                        sourceCollection = businessDimensionView.sourceCollection;
                        for (i = 0; i < sourceCollection.length; i++) {
                            if (sourceCollection[i].responsibilityAreaHierarchy === true) {
                                isBusinessValid = true;
                                break;
                            }
                        }
                        return isAccountValid && isBusinessValid;
                    }

                    function updateStep5Wizard() {
                        if (ifStep5IsValid() === true) {
                            $scope.steps[5].disable = false;
                            $scope.stepNextDisable = false;
                        } else {
                            $scope.steps[5].disable = true;
                            $scope.stepNextDisable = true;
                        }
                    }

                    $scope.refreshStep5Flex = function() {
                        businessDimensionGrid.refresh();
                        accountDimensionGrid.refresh();
                        $scope.resizeModalElement();
                    };

                    /********************************* Step 6 - Dimensions Elements ***********************************/
                    $scope.$watch('steps[5].active', function() {
                        if ($scope.steps[5].active) {
                            var businessElements = manageFilteredDimensions(businessDimensionView.sourceCollection);
                            var accountElements = manageFilteredDimensions(accountDimensionView.sourceCollection);

                            getDimensionElements(businessElements, accountElements);
                            manageDimensionElements();
                            createDimensionElementsFlexes();
                            updateStep6Wizard();
                        }
                    });

                    function manageFilteredDimensions(sourceCollection) {
                        var filteredList = [];
                        for (var i = 0; i < sourceCollection.length; i++) {
                            var dimensionRow = sourceCollection[i];
                            for (var j = 0; j < $scope.selectedExternalLedger.companies.length; j++) {
                                var company = $scope.selectedExternalLedger.companies[j];
                                var isFinded = false;
                                for (var property in dimensionRow) {
                                    if (property === company.toString() && dimensionRow[property] === true) {
                                        filteredList.push(dimensionRow.id);
                                        isFinded = true;
                                        break;
                                    }
                                }
                                if (isFinded === true) {
                                    break;
                                }
                            }
                        }
                        return filteredList;
                    }

                    function getDimensionElements(businessElements, accountElements) {
                        if ($scope.global) {
                            $scope.treeAjaxBusinessDimElement = '/cppro/adminPanel/modelMappings/externalElementsGlobal/business/' + (businessElements).toString();
                            $scope.treeAjaxAccountDimElement = '/cppro/adminPanel/modelMappings/externalElementsGlobal/account/' + (accountElements).toString();
                        } else {
                            $scope.treeAjaxBusinessDimElement = '/cppro/adminPanel/modelMappings/externalElements/business/' + (businessElements).toString();
                            $scope.treeAjaxAccountDimElement = '/cppro/adminPanel/modelMappings/externalElements/account/' + (accountElements).toString();
                        }
                    }

                    $scope.readyDimensionElementTree = function(e, data) {
                        if (e.currentTarget.id === 'businessDimNode' && businessDimensionElementTree === undefined) {
                            businessDimensionElementTree = data.instance;
                        } else if (e.currentTarget.id === 'accountDimNode' && accountDimensionElementTree === undefined) {
                            accountDimensionElementTree = data.instance;
                        }
                    };

                    function manageDimensionElements() {
                        var mappedBusinessDimensions = null;
                        var mappedAccountDimensions = null;
                        if ($scope.mappedModel && $scope.mappedModel.mappedDimensions && $scope.mappedModel.mappedDimensions) {
                            mappedBusinessDimensions = ($scope.mappedModel.mappedDimensions[0].type === 2) ? $scope.mappedModel.mappedDimensions[0] : $scope.mappedModel.mappedDimensions[1];
                            mappedAccountDimensions = ($scope.mappedModel.mappedDimensions[1].type === 1) ? $scope.mappedModel.mappedDimensions[1] : $scope.mappedModel.mappedDimensions[0];
                        }

                        var mappedBusinessDimensionsElements = null;
                        if (mappedBusinessDimensions) {
                            mappedBusinessDimensionsElements = mappedBusinessDimensions.mappedDimensionElements;
                        }
                        manageDimensionElementView(mappedBusinessDimensionsElements, businessDimensionElementView, "business");
                        var mappedAccountDimensionsElements = null;
                        if (mappedAccountDimensions) {
                            mappedAccountDimensionsElements = mappedAccountDimensions.mappedDimensionElements;
                        }
                        manageDimensionElementView(mappedAccountDimensionsElements, accountDimensionElementView, "account");
                    }

                    function manageDimensionElementView(dimensionElements, dimensionElementsView, type) {
                        if (!dimensionElements) {
                            return;
                        }
                        if (dimensionElementsView.sourceCollection.length > 0) {
                            return;
                        }
                        dimensionElementsView.sourceCollection.length = 0;
                        for (var i = 0; i < dimensionElements.length; i++) {
                            var de = dimensionElements[i];
                            var row = {
                                visId1: de.visId1,
                                visId2: de.visId2,
                                visId3: de.visId3,
                                mapping: "",
                                mappingType: de.mappingType
                            };
                            if (de.mappingType === 0) {
                                row.id = "groupId/All     ," + de.visId1;
                                row.mapping = "Specific element: " + de.visId1;
                            } else if (de.mappingType === 1) {
                                row.id = "groupId/Prefixes," + de.visId1;
                                row.mapping = "Prefix: " + de.visId1;
                            } else if (de.mappingType === 3) {
                                row.id = "hierarchyId/" + de.visId2 + ',' + de.visId1;
                                row.mapping = "Hierarchy element: " + de.visId1 + " hierarchy: " + de.visId2;
                            }

                            if ($scope.global) {
                                for (var j = 0; j < $scope.selectedExternalLedger.companies.length; j++) {
                                    var company = $scope.selectedExternalLedger.companies[j];
                                    if (de.selectedCompanies.indexOf("" + company) !== -1) {
                                        row[company] = true;
                                    } else if (type == "account") {
                                        row[company] = false;
                                    }
                                }
                            } else {
                                row["" + $scope.selectedExternalCompanies[0].companyId] = true;
                            }

                            dimensionElementsView.sourceCollection.push(row);
                        }
                        dimensionElementsView.refresh();
                    }

                    function createDimensionElementsFlexes() {
                        var businessDimensionElementsColumns = basicDimensionElementsColumns.concat(companyColumns);
                        businessDimensionElementGrid = createFlex(businessDimensionElementGrid, "#businessDimensionElementTable", businessDimensionElementView, businessDimensionElementsColumns, "selectBusinessDimElementCheckbox");

                        var accountDimensionElementsColumns = basicDimensionElementsColumns.concat(companyColumns);
                        accountDimensionElementGrid = createFlex(accountDimensionElementGrid, "#accountDimensionElementTable", accountDimensionElementView, accountDimensionElementsColumns, "selectAccountDimElementCheckbox");
                    }

                    function selectOneTreePreviousElements(allSelected, listOpenedNodes, jsTreeInstance) {
                        angular.forEach(listOpenedNodes, function(nodeObject) {
                            if (nodeObject.parent !== null && nodeObject.parent !== "#") {
                                var parent = nodeObject.parent;
                                if (parent) {
                                    parent = jsTreeInstance.get_node(parent);
                                }

                                angular.forEach(allSelected, function(selectedObject) {
                                    var toCompare = selectedObject.mapping;
                                    var nodeObjectName = "";
                                    if (nodeObject.original.mapping !== undefined && nodeObject.original.mapping !== null) {
                                        nodeObjectName = nodeObject.original.mapping;
                                    }
                                    if (nodeObjectName === toCompare && parent.state.selected === false) {
                                        nodeObject.state.selected = true;
                                    }
                                });
                            }
                        });
                    }

                    $scope.beforeOpen = function(e, data) {
                        var listOpenedNodes = data.instance._model.data;
                        switch (e.currentTarget.id) {
                            case "businessDimNode": // CC
                                selectOneTreePreviousElements(businessDimensionElementView.sourceCollection, listOpenedNodes, data.instance);
                                break;
                            case "accountDimNode": // Exp
                                selectOneTreePreviousElements(accountDimensionElementView.sourceCollection, listOpenedNodes, data.instance);
                                break;
                        }
                        var treeId = $("#" + e.currentTarget.id);
                        var tree = $(treeId).jstree();
                        $timeout(function() {
                            tree.redraw(true);
                        }, 0);
                    };

                    function updateSourceCollection(sourceCollection, row, method) {
                        var mappedChild = CoreCommonsService.findElementByKey(sourceCollection, row.mapping, "mapping");
                        if (method == "add") {
                            if (mappedChild === null) {
                                sourceCollection.push(row);
                            }
                        } else if (method == "delete") {
                            if (mappedChild) {
                                var index = sourceCollection.indexOf(mappedChild);
                                sourceCollection.splice(index, 1);
                            }
                        }
                    }

                    function onSelectDimElementNode(jsTreeInstance, dimensionElementView, type) {
                        var sourceCollection = dimensionElementView.sourceCollection;

                        var availableOpenedNodes = jsTreeInstance._model.data;
                        angular.forEach(availableOpenedNodes, function(node) {
                            if (node.state.disabled === false) {
                                var id = node.id;
                                var row = {
                                    id: node.id,
                                    mapping: node.original.mapping,
                                    visId1: node.original.visId1,
                                    visId2: node.original.visId2,
                                    visId3: node.original.visId3,
                                    mappingType: node.original.mappingType
                                };

                                if ($scope.global) {
                                    var j;
                                    if (type === "business") {
                                        for (j = 0; j < node.original.companies.length; j++) {
                                            row[node.original.companies[j]] = true;
                                        }
                                    } else if (type === "account") {
                                        for (j = 0; j < $scope.selectedExternalLedger.companies.length; j++) {
                                            row[$scope.selectedExternalLedger.companies[j]] = true;
                                        }
                                    }
                                } else {
                                    row["" + $scope.selectedExternalCompanies[0].companyId] = true;
                                }

                                if (node.state.selected === true) {
                                    var parent = node.parent;
                                    if (parent) {
                                        parent = jsTreeInstance.get_node(parent);
                                    }
                                    if (parent.state.disabled === true || parent.state.selected === false) {
                                        updateSourceCollection(sourceCollection, row, "add");
                                    } else {
                                        updateSourceCollection(sourceCollection, row, "delete");
                                    }
                                } else {
                                    updateSourceCollection(sourceCollection, row, "delete");
                                }
                            }
                        });

                        updateStep6Wizard();
                    }

                    $scope.selectBusinessDimElementNode = function(e, data) {
                        onSelectDimElementNode(data.instance, businessDimensionElementView, "business");
                    };

                    $scope.deselectBusinessDimElementNode = function(e, data) {
                        onSelectDimElementNode(data.instance, businessDimensionElementView, "business");
                    };

                    $scope.deselectAccountDimElementNode = function(e, data) {
                        onSelectDimElementNode(data.instance, accountDimensionElementView, "account");
                    };

                    $scope.selectAccountDimElementNode = function(e, data) {
                        onSelectDimElementNode(data.instance, accountDimensionElementView, "account");
                    };

                    $scope.selectBusinessDimElementCheckbox = function(rowNumber, colName) {
                        var booleanValue = businessDimensionElementView.sourceCollection[rowNumber][colName];
                        if (booleanValue === false) {
                            for (var i = 0; i < $scope.selectedExternalLedger.companies.length; i++) {
                                var company = $scope.selectedExternalLedger.companies[i];
                                for (var property in businessDimensionElementView.sourceCollection[rowNumber]) {
                                    if (property === company.toString()) {
                                        businessDimensionElementView.sourceCollection[rowNumber][property] = false;
                                    }
                                }
                            }
                        }
                        businessDimensionElementView.sourceCollection[rowNumber][colName] = !booleanValue;
                        businessDimensionElementView.refresh();
                        updateStep6Wizard();
                    };

                    $scope.selectAccountDimElementCheckbox = function(rowNumber, colName) {
                        var booleanValue = accountDimensionElementView.sourceCollection[rowNumber][colName];
                        for (var i = 0; i < $scope.selectedExternalLedger.companies.length; i++) {
                            var company = $scope.selectedExternalLedger.companies[i];
                            for (var property in accountDimensionElementView.sourceCollection[rowNumber]) {
                                if (property === company.toString()) {
                                    accountDimensionElementView.sourceCollection[rowNumber][property] = !booleanValue;
                                }
                            }
                        }
                        accountDimensionElementView.refresh();
                        updateStep6Wizard();
                    };

                    function ifStep6IsValid() {
                        return businessDimensionElementView.sourceCollection.length > 0 && accountDimensionElementView.sourceCollection.length > 0;
                    }

                    function updateStep6Wizard() {
                        if (ifStep6IsValid() === true) {
                            $scope.steps[6].disable = false;
                            $scope.stepNextDisable = false;
                        } else {
                            $scope.steps[6].disable = true;
                            $scope.stepNextDisable = true;
                        }
                    }

                    $scope.refreshStep6Flex = function() {
                        businessDimensionElementGrid.refresh();
                        accountDimensionElementGrid.refresh();
                        $scope.resizeModalElement();
                    };

                    /********************************* Step 7 - Calendar Dimension ***********************************/
                    $scope.$watch('steps[6].active', function() {
                        if ($scope.steps[6].active) {
                            manageCalendar();
                        }
                    });

                    function manageCalendar() {
                        manageCalendarVisId();
                        manageCalendarDescription();
                        manageCalendarRows();

                        updateStep7Wizard();
                    }

                    function manageCalendarVisId() {
                        var calendarVisId = "";
                        if ($scope.mappedModel && $scope.mappedModel.mappedCalendar) {
                            calendarVisId = $scope.mappedModel.mappedCalendar.calendarVisId;
                        } else {
                            calendarVisId = $scope.suggestedModel.modelVisId + "-Cal";
                        }
                        if ($scope.modelSuggestedCalendar.calendarVisId === null) {
                            $scope.modelSuggestedCalendar.calendarVisId = calendarVisId;
                        }
                    }

                    function manageCalendarDescription() {
                        var calendarDescription = "";
                        if ($scope.mappedModel && $scope.mappedModel.mappedCalendar) {
                            calendarDescription = $scope.mappedModel.mappedCalendar.calendarDescription;
                        } else {
                            calendarDescription = "Calendar";
                        }
                        if ($scope.modelSuggestedCalendar.calendarDescription === null) {
                            $scope.modelSuggestedCalendar.calendarDescription = calendarDescription;
                        }
                    }

                    function manageCalendarRows() {
                        calendarRows.length = 0;
                        for (var i = 0; i < ourCalendarLevels.length; i++) {
                            var row = {
                                year: false,
                                name: ourCalendarLevels[i]
                            };
                            calendarRows.push(row);
                        }
                        
                         manageCalendarYears();
                    }

                    function manageCalendarYears() {
                        if (calendarColumns === null) {
                            var years;
                            if ($scope.mappedModel && $scope.mappedModel.mappedCalendar) {
                                years = angular.copy($scope.mappedModel.mappedCalendar.years);
                            } else {
                                var date = new Date();
                                var actualYear = date.getFullYear();
                                years = [{
                                    yearId: -1,
                                    yearVisId: actualYear,
                                    spec: [true, false, false, true, false, false, false, false, false, false]
                                }];
                            }
                            if ($scope.modelSuggestedCalendar.years === null) {
                                $scope.modelSuggestedCalendar.years = years;
                            }
                            manageCalendarYearSelections(years);
                            
                        }
                    }

                    function manageCalendarYearSelections(years) {
                        if (!years || years.length <= 0 || calendarGrid) {
                            return;
                        }
                        for (var i = 0; i < years.length; i++) {
                            var spec = years[i].spec;
                            for (var j = 0; j < ourCalendarLevels.length; j++) {
                                var name = calendarRows[j].name;
                                var index = calendarLevels.indexOf(name);
                                if (index !== -1) {
                                    calendarRows[j].year = spec[index];
                                } else {
                                    calendarRows[j].year = false;
                                }
                            }
                            break;
                        }
                        createCalendarColumns();
                    }

                    function createCalendarColumns() {
                        var calendarYearColumns = [];
                        var years = $scope.modelSuggestedCalendar.years;
                        for (var i = 0; i < years.length; i++) {
                            var year = years[i];
                            var column = {
                                header: '' + year.yearVisId,
                                binding: 'year',
                                name: '' + year.yearVisId,
                                width: '*'
                            };
                            calendarYearColumns.push(column);
                        }
                        calendarColumns = basicCalendarColumns.concat(calendarYearColumns);
                        
                        createCalendarFlex();
                    }

                    function createCalendarFlex() {
                        if (!calendarGrid) {
                            // create grid and show data
                            calendarGrid = new wijmo.grid.FlexGrid('#calendarTable');
                            //calendarGrid.isReadOnly = true;
                            calendarGrid.selectionMode = "Row";
                            calendarGrid.headersVisibility = "Column";
                            calendarGrid.autoGenerateColumns = false; // before setting itemsSource
                            calendarGrid.itemsSource = calendarView;
                            calendarView.sourceCollection = calendarRows;


                            calendarGrid.columns.length = 0;
                            for (var i = 0; i < calendarColumns.length; i++) {
                                var col = new wijmo.grid.Column();
                                wijmo.copy(col, calendarColumns[i]);
                                calendarGrid.columns.push(col);
                            }
                            calendarView.refresh();
                        }
                    }

                    $scope.addYear = function(param) {
                        var col = new wijmo.grid.Column();
                        var column;

                        if (param === 'right') {
                            var pushLast = parseInt(calendarColumns[calendarColumns.length - 1].header) + 1;
                            column = {
                                header: '' + pushLast,
                                binding: 'year',
                                name: '' + pushLast,
                                width: '*'
                            };
                            calendarColumns.push(column);
                            wijmo.copy(col, column);
                            calendarGrid.columns.push(col);
                        }
                        if (param === 'left') {
                            var left = parseInt(calendarColumns[1].header) - 1;
                            column = {
                                header: '' + left,
                                binding: 'year',
                                name: '' + left,
                                width: '*'
                            };
                            calendarColumns.splice(1, 0, column);
                            wijmo.copy(col, column);
                            calendarGrid.columns.splice(1, 0, col);
                        }
                        updateStep6Wizard();
                    };

                    $scope.deleteYear = function(param) {
                        if (calendarColumns.length == 2) {
                            return;
                        }
                        if (param === 'right') {
                            calendarColumns.pop();
                            calendarGrid.columns.pop();
                        }
                        if (param === 'left') {
                            calendarColumns.splice(1, 1);
                            calendarGrid.columns.splice(1, 1);
                        }
                        updateStep6Wizard();
                    };

                    // function validate(colView1, colView2) {
                    //     var flag1, flag2 = false;
                    //     var comp = $scope.selectedExternalLedger.companies[0];
                    //     for (var i = 0; i < colView1.length; i++) {
                    //     }
                    // }

                    function getYears() {
                        var openAccountYear = modelSuggestedAndDimension.lastExternalYear;
                        var list = [];
                        for (var i = 1; i < calendarColumns.length; i++) {
                            if (parseInt(calendarColumns[i].name) <= openAccountYear) {
                                var obj = {
                                    name: calendarColumns[i].name,
                                    key: i,
                                };
                                list.push(obj);
                            }
                        }
                        return list;
                    }

                    function ifStep7IsValid() {
                        return true;
                    }

                    function updateStep7Wizard() {
                        var isAddRightDisabled = false;
                        if (ifStep7IsValid() === true) {
                            $scope.steps[7].disable = false;
                            $scope.stepNextDisable = false;
                        } else {
                            $scope.steps[7].disable = true;
                            $scope.stepNextDisable = true;
                        }
                    }

                    /********************************* Step 8 - Finance Cube ***********************************/
                    $scope.$watch('steps[7].active', function() {
                        if ($scope.steps[7].active) {
                            manageFinanceCubeView();
                        }
                    });

                    function manageFinanceCubeView() {
                        if (financeCubeView.sourceCollection.length === 0) {
                            if ($scope.mappedModel.mappedModelId !== -1) {
                                financeCubeView.sourceCollection = $scope.mappedModel.mappedFinanceCubes;
                            } else {
                                financeCubeView.sourceCollection = [];
                            }
                        }

                        var flex = $scope.financeCubeCtx.flex;
                        $scope.currentFinanceCube = flex.collectionView ? flex.collectionView.currentItem : null;

                        $scope.isEditFinanceCubeButtonDisabled = $scope.currentFinanceCube === null;
                        $scope.isDeleteFinanceCubeButtonDisabled = $scope.currentFinanceCube === null || $scope.currentFinanceCube.financeCubeId !== -1;

                        updateStep8Wizard();
                    }

                    $scope.selectionChangedHandler = function() {
                        var flex = $scope.financeCubeCtx.flex;
                        $scope.currentFinanceCube = flex.collectionView ? flex.collectionView.currentItem : null;

                        $scope.isEditFinanceCubeButtonDisabled = $scope.currentFinanceCube === null;
                        $scope.isDeleteFinanceCubeButtonDisabled = $scope.currentFinanceCube === null || $scope.currentFinanceCube.financeCubeId !== -1;
                    };

                    $scope.addFinanceCube = function() {
                        var currentFinanceCube = {
                            financeCubeId: -1,
                            financeCubeVisId: $scope.suggestedModel.modelVisId + "-Cube",
                            financeCubeDescription: $scope.suggestedModel.modelDescription,
                            mappedDataTypes: []
                        };
                        openModal(currentFinanceCube);
                    };

                    $scope.editFinanceCube = function() {
                        openModal($scope.currentFinanceCube);
                    };

                    function openModal(financeCube) {
                        var global = $scope.global;
                        var collectionView = financeCubeView;
                        var years = getYears();

                        var modalInstance = $modal.open({
                            template: '<mapped-finance-cube-chooser global="global" years="years" finance-cube="financeCube" collection-view="collectionView" cancel="cancel()"></finance-cube-chooser>',
                            windowClass: 'sub-system-modals softpro-modals',
                            backdrop: 'static',
                            size: 'lg',
                            controller: ['$scope', '$modalInstance',
                                function($scope, $modalInstance) {
                                    $scope.global = global;
                                    $scope.years = years;
                                    $scope.financeCube = financeCube;
                                    $scope.collectionView = collectionView;

                                    $scope.cancel = function() {
                                        updateStep8Wizard();
                                        $modalInstance.dismiss('cancel');
                                    };
                                }
                            ]
                        });
                    }

                    $scope.deleteFinanceCube = function() {
                        if ($scope.currentFinanceCube.financeCubeId == -1) {
                            financeCubeView.sourceCollection.length = 0;
                            financeCubeView.refresh();
                        }
                    };

                    $scope.$watch('steps[7].active', function() {
                        if ($scope.steps[7].active) {
                            var flex = $scope.financeCubeCtx.flex;
                            if (flex) {
                                $timeout(function() {
                                    flex.refresh();
                                }, 10);
                            }
                        }
                    });

                    function updateStep8Wizard() {
                        if (financeCubeView.sourceCollection.length > 0) {
                            $scope.isFinishBtnDisabled = false;
                        }
                    }

                    /********************************* Next / Previous ***************************/
                    $scope.isFinishBtnDisabled = true;

                    $scope.next = function() {
                        $timeout(function() {
                            $scope.resizeModalElement();
                        }, 1000);
                        var nextStep = $scope.actualStep + 1;
                        if (nextStep < $scope.steps.length && $scope.steps[nextStep].active === false && $scope.steps[nextStep].disable === false) {
                            $scope.steps[$scope.actualStep].active = false;
                            $scope.steps[nextStep].active = true;
                            $scope.actualStep = $scope.actualStep + 1;
                            // previous button
                            $scope.stepPreviousDisable = false;
                            // next button
                            var nextNextStep = nextStep + 1;
                            if ($scope.steps.length > nextNextStep && $scope.steps[nextNextStep].disable === false) {
                                $scope.stepNextDisable = false;
                            } else {
                                $scope.stepNextDisable = true;
                            }
                        }
                    };

                    $scope.previous = function() {
                        $scope.isFinishBtnDisabled = true;

                        $timeout(function() {
                            $scope.resizeModalElement();
                        }, 1000);
                        var previousStep = $scope.actualStep - 1;
                        if (previousStep >= 0 && $scope.steps[previousStep].active === false && $scope.steps[previousStep].disable === false) {
                            $scope.steps[$scope.actualStep].active = false;
                            $scope.steps[previousStep].active = true;
                            $scope.actualStep = $scope.actualStep - 1;
                            // next button
                            $scope.stepNextDisable = false;
                            // previous button
                            var previousPreviousStep = previousStep - 1;
                            if (previousPreviousStep >= 0 && $scope.steps[previousPreviousStep].disable === false) {
                                $scope.stepPreviousDisable = false;
                            } else {
                                $scope.stepPreviousDisable = true;

                            }
                        }
                    };

                    /********************************* Close / Save ***************************/
                    $scope.close = function() {
                        ModelMappingsService.clearLoader();
                        $rootScope.$broadcast('ModelMappingsDetails:close');
                    };

                    function saveCompanies() {
                        var companies = "";
                        var listCompanies = [];
                        for (var i = 0; i < $scope.selectedExternalCompanies.length; i++) {
                            companies += $scope.selectedExternalCompanies[i].companyId;
                            if (i !== $scope.selectedExternalCompanies.length - 1) {
                                companies += ", ";
                            }
                            listCompanies.push("" + $scope.selectedExternalCompanies[i].companyId);
                        }
                        $scope.mappedModel.companies = companies;
                        $scope.mappedModel.listCompanies = listCompanies;
                    }

                    function saveSelectedCompanies(row) {
                        var companies = [];
                        for (var i = 0; i < $scope.selectedExternalCompanies.length; i++) {
                            var company = "" + $scope.selectedExternalCompanies[i].companyId;
                            if (row[company] && row[company] === true) {
                                companies.push(company);
                            }
                        }
                        return companies;
                    }

                    function saveHierarchies(dimensionView) {
                        var hierarchies = [];
                        for (var i = 0; i < dimensionView.sourceCollection.length; i++) {
                            var row = dimensionView.sourceCollection[i];
                            var companies = saveSelectedCompanies(row);
                            if (companies.length > 0) {
                                var hierarchy = {
                                    hierarchyId: -1,
                                    hierarchyVisId: ($scope.mappedModelId === -1) ? $scope.suggestedModel.modelVisId + row.visId : row.visId,
                                    hierarchyDescription: row.description,
                                    mappedHierarchyVisId: row.id,
                                    visId1: row.visId1,
                                    visId2: row.visId2,
                                    selectedCompanies: companies,
                                    responsibilityAreaHierarchy: (row.responsibilityAreaHierarchy === true) ? true : false
                                };
                                hierarchies.push(hierarchy);
                            }
                        }
                        return hierarchies;
                    }

                    function saveDimensionElements(dimensionElementView) {
                        var dimensionElements = [];
                        for (var i = 0; i < dimensionElementView.sourceCollection.length; i++) {
                            var row = dimensionElementView.sourceCollection[i];
                            var companies = saveSelectedCompanies(row);
                            if (companies.length > 0) {
                                var dimensionElement = {
                                    visId1: row.visId1,
                                    visId2: row.visId2,
                                    visId3: row.visId3,
                                    mappingType: row.mappingType,
                                    selectedCompanies: companies
                                };
                                dimensionElements.push(dimensionElement);
                            }
                        }
                        return dimensionElements;
                    }

                    function saveCostCentre() {
                        var dimension = {
                            dimensionId: -1,
                            dimensionVisId: $scope.suggestedModel.modelVisId + "-cc",
                            dimensionDescription: "Cost Centre",
                            type: 2,
                            pathVisId: "C"
                        };
                        var hierarchies = saveHierarchies(businessDimensionView);
                        dimension.mappedHierchies = hierarchies;

                        var dimensionElements = saveDimensionElements(businessDimensionElementView);
                        dimension.mappedDimensionElements = dimensionElements;
                        return dimension;
                    }

                    function saveExpenseCode() {
                        var dimension = {
                            dimensionId: -1,
                            dimensionVisId: $scope.suggestedModel.modelVisId + "-exp",
                            dimensionDescription: "Expense Code",
                            type: 1,
                            pathVisId: "E"
                        };
                        var hierarchies = saveHierarchies(accountDimensionView);
                        dimension.mappedHierchies = hierarchies;

                        var dimensionElements = saveDimensionElements(accountDimensionElementView);
                        dimension.mappedDimensionElements = dimensionElements;
                        return dimension;
                    }

                    function saveCalendarYears() {
                        var years = [];
                        var newYearTempId = -1;
                        var year;
                        var sourceCollection = calendarView.sourceCollection;

                        // we omit first column which are labels;
                        for (var i = 1; i < calendarColumns.length; i++) {
                            year = {
                                yearId: newYearTempId,
                                yearVisId: calendarColumns[i].header,
                                spec: [sourceCollection[0].year, false, false, sourceCollection[1].year, false, false, sourceCollection[2].year, false, false, false]
                            };
                            newYearTempId--;
                            years.push(year);
                        }

                        if ($scope.mappedModel.mappedCalendar && $scope.mappedModel.mappedCalendar.years) {
                            for (i = 0; i < years.length; i++) {
                                var oldYear = CoreCommonsService.findElementByKey($scope.mappedModel.mappedCalendar.years, years[i].year, 'year');
                                if (oldYear) {
                                    years[i].yearId = oldYear.yearId;
                                }
                            }
                        }

                        return years;
                    }

                    function saveFinanceCubes() {
                        var years = $scope.mappedModel.mappedCalendar.years;
                        var yearStart = parseInt(years[0].yearVisId);
                        var yearEnd = parseInt(years[years.length - 1].yearVisId);

                        var maxOffset = yearStart - yearEnd;
                        for (var i = 0; i < financeCubeView.sourceCollection.length; i++) {
                            var financeCube = financeCubeView.sourceCollection[i];
                            if (financeCube) {
                                for (var j = 0; j < financeCube.mappedDataTypes.length; j++) {
                                    var dataType = financeCube.mappedDataTypes[j];
                                    if (dataType.impStartYearOffset < maxOffset) {
                                        dataType.impStartYearOffset = maxOffset;
                                    }
                                    if (dataType.impEndYearOffset < maxOffset) {
                                        dataType.impEndYearOffset = maxOffset;
                                    }
                                    if (dataType.expStartYearOffset < maxOffset) {
                                        dataType.expStartYearOffset = maxOffset;
                                    }
                                    if (dataType.expEndYearOffset < maxOffset) {
                                        dataType.expEndYearOffset = maxOffset;
                                    }
                                }
                            }
                        }
                        return financeCubeView.sourceCollection;
                    }

                    $scope.save = function() {
                        $rootScope.$broadcast("modal:blockAllOperations");
                        $scope.mappedModel.externalSystem = {};
                        $scope.mappedModel.externalSystem.externalSystemId = $scope.selectedExternalSystem.externalSystemId;
                        $scope.mappedModel.externalSystem.externalSystemVisId = $scope.selectedExternalSystem.externalSystemVisId;
                        $scope.mappedModel.externalSystem.externalSystemDescription = $scope.selectedExternalSystem.externalSystemDescription;

                        $scope.mappedModel.externalSystemVisId = $scope.selectedExternalSystem.externalSystemVisId;
                        //saveCompanies();
                        // TODO: dodaj wywolanie funkcji synchronicznie
                        var companies = "";
                        var listCompanies = [];
                        for (var i = 0; i < $scope.selectedExternalCompanies.length; i++) {
                            companies += $scope.selectedExternalCompanies[i].companyId;
                            if (i !== $scope.selectedExternalCompanies.length - 1) {
                                companies += ", ";
                            }
                            listCompanies.push("" + $scope.selectedExternalCompanies[i].companyId);
                        }
                        $scope.mappedModel.companies = companies;
                        $scope.mappedModel.listCompanies = listCompanies;
                        //
                        $scope.mappedModel.ledgerVisId = $scope.selectedExternalLedger.ledgerVisId;

                        $scope.mappedModel.mappedModelVisId = $scope.suggestedModel.modelVisId;
                        $scope.mappedModel.mappedModelDescription = $scope.suggestedModel.modelDescription;
                        $scope.mappedModel.model = {};
                        $scope.mappedModel.model.modelId = -1;
                        $scope.mappedModel.model.modelVisId = $scope.suggestedModel.modelVisId;
                        $scope.mappedModel.model.modelDescription = $scope.suggestedModel.modelDescription;

                        $scope.mappedModel.mappedDimensions = [];
                        //var costCentre = saveCostCentre();
                        // TODO: dodaj wywolanie funkcji synchronicznie
                        var costCentre = {
                            dimensionId: -1,
                            dimensionVisId: $scope.suggestedModel.modelVisId + "-cc",
                            dimensionDescription: "Cost Centre",
                            type: 2,
                            pathVisId: "C"
                        };
                        //costCentre.mappedHierchies = saveHierarchies(businessDimensionView);
                        costCentre.mappedHierchies = [];
                        for (var i = 0; i < businessDimensionView.sourceCollection.length; i++) {
                            var row1 = businessDimensionView.sourceCollection[i];
                            var companies1 = saveSelectedCompanies(row1);
                            if (companies1.length > 0) {
                                var hierarchy1 = {
                                    hierarchyId: -1,
                                    hierarchyVisId: ($scope.mappedModelId === -1) ? $scope.suggestedModel.modelVisId + row1.visId : row1.visId,
                                    hierarchyDescription: row1.description,
                                    mappedHierarchyVisId: row1.id,
                                    visId1: row1.visId1,
                                    visId2: row1.visId2,
                                    selectedCompanies: companies1,
                                    responsibilityAreaHierarchy: (row1.responsibilityAreaHierarchy === true) ? true : false
                                };
                                costCentre.mappedHierchies.push(hierarchy1);
                            }
                        }
                        //costCentre.mappedDimensionElements = saveDimensionElements(businessDimensionElementView);
                        costCentre.mappedDimensionElements = [];
                        for (var i = 0; i < businessDimensionElementView.sourceCollection.length; i++) {
                            var row2 = businessDimensionElementView.sourceCollection[i];
                            var companies2 = saveSelectedCompanies(row2);
                            if (companies2.length > 0) {
                                var dimensionElement1 = {
                                    visId1: row2.visId1,
                                    visId2: row2.visId2,
                                    visId3: row2.visId3,
                                    mappingType: row2.mappingType,
                                    selectedCompanies: companies2
                                };
                                costCentre.mappedDimensionElements.push(dimensionElement1);
                            }
                        }
                        
                        //                       
                        $scope.mappedModel.mappedDimensions.push(costCentre);
                        
                        //var expenseCode = saveExpenseCode();
                        // TODO: dodaj wywolanie funkcji synchronicznie
                       var expenseCode = {
                            dimensionId: -1,
                            dimensionVisId: $scope.suggestedModel.modelVisId + "-exp",
                            dimensionDescription: "Expense Code",
                            type: 1,
                            pathVisId: "E"
                        };
                        //expenseCode.mappedHierchies = saveHierarchies(accountDimensionView);
                        expenseCode.mappedHierchies = [];
                        for (var i = 0; i < accountDimensionView.sourceCollection.length; i++) {
                            var row3 = accountDimensionView.sourceCollection[i];
                            var companies3 = saveSelectedCompanies(row3);
                            if (companies3.length > 0) {
                                var hierarchy2 = {
                                    hierarchyId: -1,
                                    hierarchyVisId: ($scope.mappedModelId === -1) ? $scope.suggestedModel.modelVisId + row3.visId : row3.visId,
                                    hierarchyDescription: row3.description,
                                    mappedHierarchyVisId: row3.id,
                                    visId1: row3.visId1,
                                    visId2: row3.visId2,
                                    selectedCompanies: companies3,
                                    responsibilityAreaHierarchy: (row3.responsibilityAreaHierarchy === true) ? true : false
                                };
                                expenseCode.mappedHierchies.push(hierarchy2);
                            }
                        }
                        //
                        //expenseCode.mappedDimensionElements = saveDimensionElements(accountDimensionElementView);
                        expenseCode.mappedDimensionElements = [];
                        for (var i = 0; i < accountDimensionElementView.sourceCollection.length; i++) {
                            var row4 = accountDimensionElementView.sourceCollection[i];
                            var companies4 = saveSelectedCompanies(row4);
                            if (companies4.length > 0) {
                                var dimensionElement2 = {
                                    visId1: row4.visId1,
                                    visId2: row4.visId2,
                                    visId3: row4.visId3,
                                    mappingType: row4.mappingType,
                                    selectedCompanies: companies4
                                };
                                expenseCode.mappedDimensionElements.push(dimensionElement2);
                            }
                        }
                        //
                        $scope.mappedModel.mappedDimensions.push(expenseCode);

                        $scope.mappedModel.mappedCalendar = {};
                        $scope.mappedModel.mappedCalendar.calendarVisId = $scope.modelSuggestedCalendar.calendarVisId;
                        $scope.mappedModel.mappedCalendar.calendarDescription = $scope.modelSuggestedCalendar.calendarDescription;
                        
                        //var years = saveCalendarYears();
                        // TODO: dodaj wywolanie funkcji synchronicznie
                        var years = [];
                        var newYearTempId = -1;
                        var year;
                        var sourceCollection = calendarView.sourceCollection;

                        // we omit first column which are labels;
                        for (var i = 1; i < calendarColumns.length; i++) {
                            year = {
                                yearId: newYearTempId,
                                yearVisId: calendarColumns[i].header,
                                spec: [sourceCollection[0].year, false, false, sourceCollection[1].year, false, false, sourceCollection[2].year, false, false, false]
                            };
                            newYearTempId--;
                            years.push(year);
                        }

                        if ($scope.mappedModel.mappedCalendar && $scope.mappedModel.mappedCalendar.years) {
                            for (i = 0; i < years.length; i++) {
                                var oldYear = CoreCommonsService.findElementByKey($scope.mappedModel.mappedCalendar.years, years[i].year, 'year');
                                if (oldYear) {
                                    years[i].yearId = oldYear.yearId;
                                }
                            }
                        }
                        //
                        $scope.mappedModel.mappedCalendar.years = years;
                        
                        //$scope.mappedModel.mappedFinanceCubes = saveFinanceCubes();
                        var yearStart = parseInt(years[0].yearVisId);
                        var yearEnd = parseInt(years[years.length - 1].yearVisId);

                        var maxOffset = yearStart - yearEnd;
                        for (var i = 0; i < financeCubeView.sourceCollection.length; i++) {
                            var financeCube = financeCubeView.sourceCollection[i];
                            if (financeCube) {
                                for (var j = 0; j < financeCube.mappedDataTypes.length; j++) {
                                    var dataType = financeCube.mappedDataTypes[j];
                                    if (dataType.impStartYearOffset < maxOffset) {
                                        dataType.impStartYearOffset = maxOffset;
                                    }
                                    if (dataType.impEndYearOffset < maxOffset) {
                                        dataType.impEndYearOffset = maxOffset;
                                    }
                                    if (dataType.expStartYearOffset < maxOffset) {
                                        dataType.expStartYearOffset = maxOffset;
                                    }
                                    if (dataType.expEndYearOffset < maxOffset) {
                                        dataType.expEndYearOffset = maxOffset;
                                    }
                                }
                            }
                        }
                        $scope.mappedModel.mappedFinanceCubes = financeCubeView.sourceCollection;
                        //
                        ModelMappingsService.save($scope.mappedModel);
                        DictionaryService.importDictionariesFromDatabase("company");
                    };

                    $scope.validation = {};

                    /**
                     * Clear whole validation object which is sended to template. All validation messages are disappeared
                     */
                    var clearValidation = function() {
                        // clear previous validation messages
                        angular.forEach($scope.validation, function(message, field) {
                            $scope.validation[field] = "";
                        });
                    };

                    $scope.$on("ModelMappingsService:modelMappingSaveError", function(event, data) {
                        $rootScope.$broadcast("modal:unblockAllOperations");
                        clearValidation();
                        // set new messages
                        angular.forEach(data.fieldErrors, function(error) {
                            if (error.fieldName in $scope.validation) {
                                $scope.validation[error.fieldName] = error.fieldMessage;
                            }
                        });
                    });

                    $scope.$on("ModelMappingsService:modelMappingSaved", function(event, data) {
                        if (data.success) {
                            $scope.close();
                        }
                    });
                }
            ]
        };
    }
})();