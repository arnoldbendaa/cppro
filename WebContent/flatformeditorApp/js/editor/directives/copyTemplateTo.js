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
        .module('flatFormEditorApp.editor')
        .directive('copyTemplateTo', copyTemplateTo);

    /* @ngInject */
    function copyTemplateTo(CoreCommonsService, SpreadSheetService, $timeout, $rootScope, $compile) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'editor/views/copyTemplateTo.html',
			scope: {
				id: "=",
				sourceid: "=sourceid"
			},
			replace: true,
			controller: ['$scope',
				function($scope) {
					$scope.showLoader = true;
					$scope.flatForms = SpreadSheetService.getAllFlatForms();
					$scope.isFinanceCubeColumn = true;
					$scope.selectedFlatForms = [];

					function filterFlatForms() {
						var flatFormToDelete = CoreCommonsService.findElementByKey($scope.flatForms, $scope.sourceid, "flatFormId");
						if (flatFormToDelete) {
							var index = $scope.flatForms.indexOf(flatFormToDelete);
							$scope.flatForms.splice(index, 1);
						}
					}

					function manageFlatFormIds() {
						var flatFormIds = [$scope.sourceid];
						angular.forEach($scope.selectedFlatForms, function(flatForm) {
							flatFormIds.push(flatForm.flatFormId);
						});
						return flatFormIds;
					}

					$scope.save = function() {
						var flatFormIds = manageFlatFormIds();
						$scope.showLoader = true;
						SpreadSheetService.copyTemplate(flatFormIds);

					};

					$scope.$on("CopyTemplateTo:resetData", function() {
						$scope.selectedFlatForms.length = 0;
					});

					$scope.$on("CopyTemplateTo:toggleLoader", function(e, show) {
						if (show) {
							$scope.showLoader = true;
						} else {
							$scope.showLoader = false;
						}
					});

					$scope.$watch('flatForms.length', function(newValue) {
						if ($scope.flatForms !== undefined && $scope.flatForms.length > 0) {
							$scope.showLoader = false;
							filterFlatForms();
						}
					});
				}
			]
		};
	}
})();