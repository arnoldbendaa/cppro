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
		.module('flatFormTemplateApp.templates')
		.directive('saveTemplate', saveTemplate);

	/* @ngInject */
	function saveTemplate(TemplatesService, SpreadSheetService, SpreadSheetMainMenuService) {
		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'modules/templates/views/saveTemplate.html',
			scope: {
				template: '='
			},
			replace: true,
			controller: saveTemplateController
		};

		/* @ngInject */
		function saveTemplateController($rootScope, $scope) {

			$scope.templateTypes = angular.copy(TemplatesService.templateTypes);
			$scope.save = save;
			activate();

			/************************************************** IMPLEMENTATION *************************************************************************/

			function activate() {
				onTemplateChanged();
				deleteDirectoryType();
			}

			function deleteDirectoryType() {
				$scope.templateTypes.splice(0, 1);
			}

			function save() {
				var veil = angular.element('#veil-informations').find('h1');
				var text = veil.text();
				veil.text('Template is saving, please wait...');
				$rootScope.$broadcast('veil:show');

				$scope.template.jsonForm = SpreadSheetService.getJsonForm();
				TemplatesService.save($scope.template).then(function(data) {
					if (data.success) {
						$rootScope.$broadcast('veil:hide');
						veil.text(text);

						swal({
							title: "Template has been saved.",
							text: "",
							type: "info",
						});
						SpreadSheetMainMenuService.hideMenu();
					}
				});
			}

			function manageCurrentTemplateType(type) {
				var index = $scope.templateTypes.indexOf(type);
				if (index == -1) {
					$scope.currentTemplateType = $scope.templateTypes[1];
				} else {
					$scope.currentTemplateType = $scope.templateTypes[index];
				}
			}

			function onTemplateChanged() {
				$scope.$watch('template', function() {
					if ($scope.template) {
						manageCurrentTemplateType($scope.template.type);
					}
				});
			}

		}
	}
})();