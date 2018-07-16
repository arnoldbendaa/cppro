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
        .directive('configurationsExcludeDimensions', configurationsExcludeDimensions);

    /* @ngInject */
    function configurationsExcludeDimensions(ConfigurationsService, CoreCommonsService) {
        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'modules/configurations/views/configurationsExcludeDimensions.html',
            scope: {
                openingDimension: '='
            },
            replace: true,
            controller: configurationsExcludeDimensionsController
        };

        function configurationsExcludeDimensionsController($rootScope, $scope, $timeout) {
            // parameters to resize modal
            var modalDialog = $(".configurations-exclude-dimensions").closest(".modal-dialog");
            var elementToResize = $(".elementToResize");
            var additionalElementsToCalcResize = [$(".getToCalc1"), $(".getToCalc2"), -40];
            var selectedDimensionsToExclude = [];
            $scope.cookieName = "flatFormTemplate_modal_configurationsExcludeDimensions";
            $timeout(function() { // timeout is necessary to pass asynchro
                CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
            }, 0);
            $scope.treeProcess = {};
            $scope.resizedColumn = resizedColumn;
            $scope.sortedColumn = sortedColumn;
            $scope.selectNode = selectNode;
            $scope.deselectNode = deselectNode;
            $scope.saveDimensions = saveDimensions;
            $scope.close = close;
            activate();

            /************************************************** IMPLEMENTATION *************************************************************************/

            function activate() {
                $scope.sourceModels = ConfigurationsService.getModels();
                angular.copy($scope.openingDimension.excludedDimensions, selectedDimensionsToExclude);
            };

            function resizedColumn(sender, args) {
                CoreCommonsService.resizedColumn(args, $scope.cookieName);
            };

            function sortedColumn(sender, args) {
                CoreCommonsService.sortedColumn(args, $scope.cookieName);
            };
            
            /**
             * Sets appropriate node disabled, opened and select node's checkbox by fillCheckboxs functions.
             */
            var manageStartTreeProcess = function(currentNode) {
                currentNode.state.disabled = true;
                if(currentNode.state.leaf !== true) {
                    if (currentNode.id === $scope.openingDimension.dimensionVisId) {
                        currentNode.state.opened = true;
                        fillCheckboxs(currentNode);
                    } else {
                        for (var i = 0; i < currentNode.children.length; i++) {
                            manageStartTreeProcess(currentNode.children[i]);
                        }
                    }
                }
            }

            /**
             * Find and select node's checkbox and that children.
             */
            var fillCheckboxs = function(currentNode) {
                for(var i = 0; i < currentNode.children.length; i++) {
                    for(var j = 0; j < $scope.openingDimension.excludedDimensions.length; j++) {
                        if(currentNode.children[i].id === $scope.openingDimension.excludedDimensions[j]) {
                            currentNode.children[i].state.selected = true;
                        }
                    }
                    if(currentNode.children[i].state.leaf !== true) {
                        fillCheckboxs(currentNode.children[i]);
                    }
                }
            };

            /**
             * select treeNode checkboxes.
             */
            function selectNode(e, data) {
                manageSelectedProcess(data.node);
            };

            /**
             * deselect treeNode checkboxes.
             */
            function deselectNode(e, data) {
                manageDeselectedProcess(data.node);
            };
            
            var manageSelectedProcess = function(node) {
                selectedDimensionsToExclude.push(node.id);
            };
            
            /**
             * Management deselected for tree process and children
             *
             * @param {[type]} node
             */
            var manageDeselectedProcess = function(node) {
                for(var i = 0; i < selectedDimensionsToExclude.length; i++) {
                    if(selectedDimensionsToExclude[i] === node.id) {
                        selectedDimensionsToExclude.splice(i, 1);
                    }
                }
            };

            function saveDimensions() {
                $scope.openingDimension.excludedDimensions = selectedDimensionsToExclude;
            };

            var loader = $(".configurationsTreeBusinessDimensionChooser");

            function close() {
                $rootScope.$broadcast("ConfigurationsExcludeDimensions:close");
            };


            /** ******************************************************* WATCHERS ******************************************************* */

            $scope.$watchCollection(
                // "This function returns the value being watched. It is called for each turn of the $digest loop"
                function() {
                    return $scope.treeProcess
                },
                // "This is the change listener, called when the value returned from the above function changes"
                function(newValue, oldValue) {
                    // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                    // To protect against this we check if newValue isn't empty.
                    if (newValue !== oldValue && Object.keys(newValue).length !== 0) {
                        manageStartTreeProcess($scope.treeProcess);
                    }
                }
            );

            $scope.$watch('treeProcess', function(newValue, oldValue) {
                // hide loader when new tree content is loaded
                if (Object.keys(newValue).length !== 0 && Object.keys(oldValue).length === 0) {
                    loader.hide();
                }
            }, true);

            $scope.$watch(
                // "This function returns the value being watched. It is called for each turn of the $digest loop"
                function() {
                    return $scope.sourceModels.sourceCollection.length;
                },
                // "This is the change listener, called when the value returned from the above function changes"
                function() {
                    // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
                    // To protect against this we check if newValue isn't empty.
                    if ($scope.sourceModels.sourceCollection.length > 0) {
                        $scope.models = $scope.sourceModels.sourceCollection;
                        for(var i = 0; i < $scope.models.length; i++) {
                            if($scope.models[i].modelVisId === $scope.openingDimension.modelVisId) {
                                $scope.treeProcess = ConfigurationsService.getTree($scope.models[i].modelId);
                            }
                        }
                    }
                }
            );
        }
    }
})();           