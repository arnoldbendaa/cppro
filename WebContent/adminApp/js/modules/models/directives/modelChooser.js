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
        .module('adminApp.model')
        .directive('modelChooser', modelChooser);

    /* @ngInject */
    function modelChooser(ModelService, $timeout, CoreCommonsService) {

        return {
            restrict: 'E',
            templateUrl: $BASE_TEMPLATE_PATH + 'models/views/modelChooser.html',
            scope: {
                id: '=',
                visId: '=',
                close: '&'
            },
            replace: true,
            link: function linkFunction($scope) {

/*                // parameters to resize modal
                var modalDialog = $(".model-chooser").closest(".modal-dialog");
                var elementToResize = $(".elementToResize");
                var additionalElementsToCalcResize = [];
                $scope.cookieName = "adminPanel_modal_modelChooser";
                // try to resize and move modal based on the cookie
                CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
                $scope.resizedColumn = function(sender, args) {
                    CoreCommonsService.resizedColumn(args, $scope.cookieName);
                };
                $scope.sortedColumn = function(sender, args) {
                    CoreCommonsService.sortedColumn(args, $scope.cookieName);
                };
                $timeout(function() { // timeout is necessary to pass asynchro
                    CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize);
                }, 0);*/

                var originalId = $scope.id;
                $scope.models = ModelService.getModels();
                $scope.selectedModelId = $scope.id;

                $scope.choose = function(selectedModelId) {
                    $scope.id = selectedModelId;
                };

                $scope.select = function(modelId) {
                    $scope.selectedModelId = modelId;
                };

                $scope.$watch("id", function(newModelId) {
                    if (angular.isDefined(newModelId)) {
                        $scope.id = newModelId;
                        $scope.visId = ModelService.manageModelName(newModelId);
                        $scope.selectedModelId = $scope.id;
                    }
                })
            }
        };
    }
})();