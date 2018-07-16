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
        .directive('budgetCycleXmlForms', budgetCycleXmlForms);

    /* @ngInject */
    function budgetCycleXmlForms($rootScope, $modal, $timeout, BudgetCycleService, DataTypesPageService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'budgetCycles/views/budgetCycleXmlForms.html',
            scope: {
                modelId: '=',
                xmlForms: '=',
                defaultXmlFormId: '=',
                defaultXmlFormDataType: '='
            },
            replace: true,
            link: function linkFunction($scope) {

                $scope.selectXmlForm = selectXmlForm;
                $scope.deleteXmlForm = deleteXmlForm;
                $scope.changeDataType = changeDataType;
                $scope.changeDefaultXML = changeDefaultXML;
                $scope.addXmlForms = addXmlForms;

                /**
                 * Selects xml form on list with selected xml forms in budget cycle
                 */
                function selectXmlForm(xmlFormId) {
                    if ($scope.selectedXmlFormId == xmlFormId) {
                        $scope.selectedXmlFormId = -1;
                    } else {
                        $scope.selectedXmlFormId = xmlFormId;
                    }
                }

                /**
                 * Deletes xml form if it selected. If deleted xml form is default xml form, properties defaultXmlFormId and defaultXmlFormDateType are reset.
                 */
                function deleteXmlForm() {
                    if ($scope.selectedXmlFormId == -1) {
                        return;
                    }
                    var elementToDelete = findXmlInCollection($scope.selectedXmlFormId, $scope.xmlForms);
                    if (elementToDelete === null) {
                        return;
                    }
                    if (elementToDelete.flatFormId == $scope.defaultXmlFormId) {
                        $scope.defaultXmlFormId = 0;
                        $scope.defaultXmlFormDataType = null;
                    }
                    var index = $scope.xmlForms.indexOf(elementToDelete);
                    $scope.xmlForms.splice(index, 1);
                    $scope.selectedXmlFormId = -1;
                    divideAllAvailableXmlForms();
                }

                /**
                 * Changes data type in specific xmlForm passed in param
                 */
                function changeDataType(xmlForm) {
                    var findedXmlForm = findXmlInCollection(xmlForm.flatFormId, $scope.xmlForms);
                    findedXmlForm.dataType = xmlForm.dataType;
                    if ($scope.defaultXmlFormId == xmlForm.flatFormId) {
                        $scope.defaultXmlFormDataType = findedXmlForm.dataType;
                    }
                }

                /**
                 * Changes default xml form. Updates also defaultXmlFormDataType property
                 */
                function changeDefaultXML(xmlFormId) {
                    $scope.defaultXmlFormId = xmlFormId;
                    var findedXmlForm = findXmlInCollection(xmlFormId, $scope.xmlForms);
                    if (findedXmlForm !== null) {
                        $scope.defaultXmlFormDataType = findedXmlForm.dataType;
                    }
                }

                /**
                 * Function is invoked after click in add button. Add new xmlForms to selected xmlForms in budget cycle
                 */
                function addXmlForms() {
                    var notSelectedXmlForms = $scope.notSelectedXmlForms;
                    var cookieName = 'coreApp_modal_budgetCycleChooserModal';
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
                            var element = {
                                flatFormId: xmlForm.flatFormId,
                                dataType: $scope.dataTypes.sourceCollection[0].dataTypeVisId
                            };
                            $scope.xmlForms.push(element);
                        });
                        divideAllAvailableXmlForms();
                        //$scope.resizeModalElement();
                    }, function() {

                    });
                }

                /**
                 * Divides all available xmlForms which is related to budget cycle on selectedXmlForms and notSelectedXmlForms
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
                 * Check if xmlForm is stored in budget cycle xmlForms (collection with specific properties - only id of xmlForm and dateType)
                 */
                var checkIfXmlFormIsSelectedXmlForm = function(availableXmlForm) {
                    var isSelected = false;
                    angular.forEach($scope.xmlForms, function(selectXmlForm) {
                        if (availableXmlForm.flatFormId == selectXmlForm.flatFormId) {
                            availableXmlForm.dataType = selectXmlForm.dataType;
                            isSelected = true;
                            return;
                        }
                    });
                    return isSelected;
                };

                $scope.isDataLoaded = false;
                $scope.dataTypes = DataTypesPageService.getDataTypes();
                $scope.selectMode = false;

                $scope.selectedXmlFormId = -1;
                var availableXmlForms = [];
                $scope.selectedXmlForms = [];
                $scope.notSelectedXmlForms = [];
                /****************************************** WATCHERS **************************************************************/
                $scope.$watch("modelId", function(newModelId) {
                    if (angular.isDefined(newModelId) && newModelId != -1) {
                        $timeout(function() { // zeby animacja sie nie zacinała
                            BudgetCycleService.getXMLFormsForModel($scope.modelId).success(function(data) {
                                $scope.selectMode = false;
                                $scope.isDataLoaded = true;
                                availableXmlForms = data;
                                divideAllAvailableXmlForms();
                            });
                        }, 200);
                    }
                });
            }
        };
    }
})();