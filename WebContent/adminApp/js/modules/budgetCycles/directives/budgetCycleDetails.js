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
        .module('adminApp.budgetCycle')
        .directive('budgetCycleDetails', budgetCycleDetails);

    /* @ngInject */
    function budgetCycleDetails($timeout, BudgetCycleService, $rootScope, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'budgetCycles/views/budgetCycleDetails.html',
            scope: {
                id: '=',
                open: '=',
                close: '&'
            },
            replace: true,
            link: function linkFunction($scope, $element) {
                var modalDialog = null;
                var allElementsToResize = [];
                var allAdditionalElementsToCalcResize = [];
                var originalTo = '',originalFrom='';
                $timeout(function() {
                    // parameters to resize modal
                    modalDialog = $(".budget-cycle-details").closest(".modal-dialog");
                    // selected xml forms
                    var elementToResize = $(".budget-cycle-xml-forms .selected-forms .xml-forms");
                    // additional elements on selected xml forms view
                    var additionalElementsToCalcResize = [$(".getToCalc2"), $(".getToCalc3"), $(".getToCalc4"), $(".selected-forms .xml-forms-buttons"), 35];
                    // unselected xml forms
                    var elementToResize2 = $(".budget-cycle-xml-forms .unselected-forms .xml-forms");
                    // additional elements on uselected xml forms view
                    var additionalSubstract = 40;
                    var additionalElementsToCalcResize2 = [$(".unselected-forms .xml-forms-buttons"), $(".unselected-forms .forms-info"), $(".getToCalc5"), additionalSubstract];
                    var elementToResize3 = $(".budget-cycle-model-chooser .list");
                    var additionalElementsToCalcResize3 = [];
                    $scope.cookieName = "adminPanel_modal_budgetCycle";
                    $scope.resizedColumn = resizedColumn;
                    $scope.sortedColumn = sortedColumn;
                    $scope.save = save;
                    $scope.changePeriod = changePeriod;
                    $scope.onPeriodChange = onPeriodChange;
                    $scope.disableBudgetStatus = disableBudgetStatus;
                    $scope.onBudgetStatusChange = onBudgetStatusChange;
                    $scope.isValid = isValid;
                    $scope.change = false;
                    activate();

                    function activate() {
                        // try to resize and move modal based on the cookie
                        CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                    }

                    function resizedColumn(sender, args) {
                        CoreCommonsService.resizedColumn(args, $scope.cookieName);
                    }

                    function sortedColumn(sender, args) {
                        CoreCommonsService.sortedColumn(args, $scope.cookieName);
                    }
                    $timeout(function() { // timeout is necessary to pass asynchro
                        allElementsToResize = [elementToResize, elementToResize2 /*, elementToResize3*/ ];
                        allAdditionalElementsToCalcResize = [additionalElementsToCalcResize, additionalElementsToCalcResize2 /*, additionalElementsToCalcResize3*/ ];
                        CoreCommonsService.allowResizingModal(modalDialog, allElementsToResize, allAdditionalElementsToCalcResize, $scope.ctx);
                    }, 0);
                }, 0);
                
                $scope.categories = [
                        {key: "M", name: "Management Accounts"},
                        {key: "F", name: "Forecast"},
                        {key: "B", name: "Budget"}
                ];

                /**
                 * Manages statuses to choose for budget cycle (it depends on saved status and status (new, saved) budget cycle
                 */
                var manageStatuses = function() {
                    $scope.statuses = [];
                    if ($scope.id == -1) {
                        $scope.statuses.push(BudgetCycleService.findStatusWithId(0));
                        $scope.statuses.push(BudgetCycleService.findStatusWithId(1));
                    } else if ($scope.budgetCycle && $scope.budgetCycle.status === 0) {
                        $scope.statuses.push(BudgetCycleService.findStatusWithId(0));
                        $scope.statuses.push(BudgetCycleService.findStatusWithId(1));
                    } else if ($scope.budgetCycle && ($scope.budgetCycle.status === 1 || $scope.budgetCycle.status === 2)) {
                        $scope.statuses.push(BudgetCycleService.findStatusWithId(1));
                        $scope.statuses.push(BudgetCycleService.findStatusWithId(2));
                    }
                };

                /**
                 * Invokes request to save budget cycle. If is invalid budget cycle, request is not sended.
                 * @return {[type]} [description]
                 */
                function save() {
                	if((originalTo==''||originalTo==$scope.budgetCycle.periodToVisId)&&(originalFrom==''||originalFrom==$scope.budgetCycle.periodFromVisId)){
                        if ($scope.isValid($scope.budgetCycle)) {
                            $rootScope.$broadcast("modal:blockAllOperations");
                            BudgetCycleService.save($scope.budgetCycle);
                        }
                	}else{
            			swal({
            				title: "Are you sure",
            				text: "Are you sure you want to accept the changes for the selected budget cycles?",
            				type: "warning",
            				showCancelButton: true,
            				confirmButtonColor: "#d9534f"
            			}, function(isConfirm) {
            				if (isConfirm) {
                                $rootScope.$broadcast("modal:blockAllOperations");
                                BudgetCycleService.save($scope.budgetCycle);
            				}
            			});

                	}
                }

                /**
                 * Invokes during clicking in directive with calendar periods. Function was created to control if user click in period from or period to
                 */
                function changePeriod(type) {
                    isPeriodToEdited = (type == 'to') ? true : false;
                }

                /**
                 * Returning function from calendar period directive. It is invoked after choosing new period from calendar select.
                 * Updates budget cycle periods. Hides validation messages.
                 */
                function onPeriodChange(periodId, periodVisId) {
                    if (isPeriodToEdited) {
                        if($scope.change==false)
                        	originalTo = $scope.budgetCycle.periodToVisId;

                        $scope.budgetCycle.periodToId = periodId;
                        $scope.budgetCycle.periodToVisId = periodVisId;
                        $scope.validation.periodTo = "";
                        $scope.change = true;
                    } else {
                        if($scope.change==false)
                        	originalFrom = $scope.budgetCycle.periodFromVisId;

                        $scope.budgetCycle.periodFromId = periodId;
                        $scope.budgetCycle.periodFromVisId = periodVisId;
                        $scope.validation.periodFrom = "";
                        $scope.change = true;
                    }
                    $scope.validation.periods = "";
                }

                var isPeriodToEdited = false;
                if ($scope.id == -1) {
                    $scope.isModelChoosed = false;
                    $scope.budgetCycle = BudgetCycleService.createEmptyBudgetCycle();
                    manageStatuses();
                    $scope.budgetCycle.category = $scope.categories[0].key;
                } else {
                    $scope.isModelChoosed = true;
                    $timeout(function() { // zeby animacja sie nie zacinała
                        $scope.budgetCycle = BudgetCycleService.getBudgetCycle($scope.id);
                    }, 500);
                }

                var isBudgetStatusEdited = false;

                function disableBudgetStatus(status) {
                    return status === 2 && isBudgetStatusEdited === false;
                }

                function onBudgetStatusChange() {
                    isBudgetStatusEdited = true;
                }

                // cancel dragging and resizing for ModelChooser
                $scope.$watch("isModelChoosed", function() {
                    var modal = $(".modal.budget-cycle-modals .modal-dialog");
                    if (!$scope.isModelChoosed) {
                        modal.addClass("modalReset");
                    } else {
                        modal.removeClass("modalReset");
                    }
                });

                /****************************************** WATCHERS **************************************************************/
                $scope.$watch("budgetCycle", function(newBudgetCycle) {
                    if (angular.isDefined(newBudgetCycle) && angular.isDefined(newBudgetCycle.model) && newBudgetCycle.model.modelId !== -1 && newBudgetCycle.model.modelVisId !== "") {
                        $scope.isMainDataLoaded = true;
                        $scope.isModelChoosed = true;
                        manageStatuses();
                    }
                }, true);

                var isPlannedEndDateEdited = false;
                $scope.$watch("budgetCycle.plannedEndDate", function(newPlannedEndDate, oldPlannedEndDate) {
                    if (oldPlannedEndDate !== null && angular.isDefined(oldPlannedEndDate) &&
                        newPlannedEndDate !== null && angular.isDefined(newPlannedEndDate) &&
                        newPlannedEndDate !== oldPlannedEndDate) {

                        isPlannedEndDateEdited = true;
                        $scope.validation.plannedEndDate = "";
                    }
                });

                $scope.$watch("budgetCycle.xmlForms", function(newXMLForms, oldXMLForms) {
                    if (angular.isDefined(newXMLForms)) {
                        $scope.validation.xmlForms = "";
                        $element.find('.selected-forms table').removeClass('bg-danger').addClass('table-striped table-hover');
                    }
                }, true);

                $scope.$watch("budgetCycle.defaultXmlFormId", function(newXMLFormId, oldXMLFormId) {
                    if (angular.isDefined(newXMLFormId)) {
                        $scope.validation.xmlForms = "";
                        $element.find('.selected-forms table').removeClass('bg-danger').addClass('table-striped table-hover');
                    }
                }, true);

                /****************************************** VALIDATION **************************************************************/
                $scope.validation = {
                    budgetCycleVisId: "",
                    budgetCycleDescription: "",
                    periods: "",
                    periodFrom: "",
                    periodTo: "",
                    plannedEndDate: "",
                    xmlForms: ""
                };

                /**
                 * Clear whole validation object which is sended to template. All validation messages are disappeared
                 */
                var clearValidation = function() {
                    // clear previous validation messages
                    angular.forEach($scope.validation, function(message, field) {
                        $scope.validation[field] = "";
                    });
                };

                /**
                 * Checks if validation planned end date is correct. If planned end date is not edited, validation is omitted.
                 */
                var isValidPlannedEndDate = function(plannedEndDate) {
                    if (plannedEndDate === null) {
                        $scope.validation.plannedEndDate = "The planned end date must be selected.";
                        return false;
                    }
                    if (isPlannedEndDateEdited === false) {
                        return true;
                    }
                    var tmp = new Date(plannedEndDate).getTime();
                    var today = new Date().getTime();
                    if (tmp <= today) {
                        $scope.validation.plannedEndDate = "The planned end date must be after today.";
                        return false;
                    }
                    return true;
                };

                /**
                 * Checks if 'period to' is before 'period from'
                 */
                var isPeriodToBeforePeriodFrom = function(periodFrom, periodTo) {
                    var from = getYearAndMonthFromPeriod(periodFrom);
                    var to = getYearAndMonthFromPeriod(periodTo);
                    return (from[0] > to[0] || (from[0] == to[0] && from[1] > to[1]));
                };

                /**
                 * Retrieves year and month from periodVisId
                 */
                var getYearAndMonthFromPeriod = function(periodVisId) {
                    var yearAndMonth = [];
                    var tmp = periodVisId.split("/");
                    if (tmp.length == 2) { //only year
                        yearAndMonth[0] = parseInt(tmp[1]);
                        yearAndMonth[1] = 0;
                    } else if (tmp.length == 3) {
                        yearAndMonth[0] = parseInt(tmp[1]);
                        yearAndMonth[1] = parseInt(tmp[2]);
                    }
                    return yearAndMonth;
                };

                /**
                 * Checks if periods are correct and are in chronological order
                 */
                var isValidPeriods = function(budgetCycle) {
                    var isValid = true;
                    if (budgetCycle.periodFromId === 0) {
                        $scope.validation.periodFrom = "The period FROM must be selected.";
                        isValid = false;
                    }
                    if (budgetCycle.periodToId === 0) {
                        $scope.validation.periodTo = "The period TO must be selected.";
                        isValid = false;
                    }
                    if (isValid === false) {
                        return isValid;
                    }
                    if (isPeriodToBeforePeriodFrom(budgetCycle.periodFromVisId, budgetCycle.periodToVisId)) {
                        $scope.validation.periods = "Period FROM cannot be further than period TO.";
                        return false;
                    }
                    return true;
                };

                /**
                 * Manages view related to xml forms. Add validation error class to that view.
                 */
                var manageErrorsInfoToXMLFormView = function() {
                    $element.find('.selected-forms table').addClass('bg-danger').removeClass('table-striped table-hover');
                };

                /**
                 * Checks if xml forms for budget cycle are correct. If at least one is selected and if default xml form is selected.
                 */
                var isValidXMLForms = function(budgetCycle) {
                    var xmlForms = budgetCycle.xmlForms;
                    if (xmlForms === null || xmlForms.length === 0 || budgetCycle.defaultXmlFormId <= 0) {
                        $scope.validation.xmlForms = "No default xml form is selected.";
                        manageErrorsInfoToXMLFormView();
                        return false;
                    }
                    var isStoredInList = false;
                    var dataType = "";
                    angular.forEach(xmlForms, function(xmlForm) {
                        if (xmlForm.flatFormId == budgetCycle.defaultXmlFormId) {
                            isStoredInList = true;
                            dataType = xmlForm.dataType;
                            return;
                        }
                    });
                    if (isStoredInList === false) {
                        $scope.validation.xmlForms = "Selected default xml form is not stored in xml form list.";
                        manageErrorsInfoToXMLFormView();
                        return false;
                    }

                    if (budgetCycle.defaultXmlFormDataType === null || budgetCycle.defaultXmlFormDataType.length === 0) {
                        $scope.validation.xmlForms = "The default xml form must have defined data type!";
                        manageErrorsInfoToXMLFormView();
                        return false;
                    }
                    if (dataType !== budgetCycle.defaultXmlFormDataType) {
                        $scope.validation.xmlForms = "Data type not match to data type in selected default xml form.";
                        manageErrorsInfoToXMLFormView();
                        return false;
                    }
                    if (budgetCycle.defaultXmlFormDataType.length > 2) {
                        $scope.validation.xmlForms = "Length (" + budgetCycle.defaultXmlFormDataType.length + ") of data type must not exceed 2!";
                        manageErrorsInfoToXMLFormView();
                        return false;
                    }
                    return true;
                };

                /**
                 * Checks if budget cycle is correct, is valid.
                 */
                function isValid(budgetCycle) {
                    var isValid = true;
                    isValid = isValidPlannedEndDate(budgetCycle.plannedEndDate);
                    isValid = isValidPeriods(budgetCycle);
                    isValid = isValidXMLForms(budgetCycle);
                    return isValid;
                }

                /****************************************** RESPONSES **************************************************************/
                $scope.$on("BudgetCycleService:budgetCycleSaveError", function(event, data) {
                    $rootScope.$broadcast("modal:unblockAllOperations");
                    clearValidation();
                    // set new messages
                    angular.forEach(data.fieldErrors, function(error) {
                        if (error.fieldName in $scope.validation) {
                            $scope.validation[error.fieldName] = error.fieldMessage;
                        }
                    });
                });

                $scope.$on("BudgetCycleService:budgetCycleSaved", function(event, data) {
                    if (data.success) {
                        $scope.close();
                    }
                });
            }
        };
    }
})();