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
        .module('adminApp.commons')
        .directive('multipleChooser', multipleChooser);

    /* @ngInject */
    function multipleChooser() {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'commons/views/multipleChooser.html',
			scope: {
				itemsSource: "=",
				itemsSelected: "=",
				itemsLoaded: "=",
				allOption: "=",
				onChange: "&"
			},
			replace: true,
			link: function linkFunction(scope, element) {
				var select = element.find("select");
				var loader = $(element).find(".loader");

				if (scope.allOption === true) {
					scope.itemsSelected.push('All');
				}
				manageLoader();

				/**
				 * Change handler (jQuery event). Invoked when user selects something in multiselect
				 */
				select.change(function() {
					scope.itemsSelected.length = 0;

					$(select).find("option:selected").each(function() {
						var text = $(this).text();
						//if (scope.itemsSelected.indexOf(text) == -1) {
						scope.itemsSelected.push(text);
						//}
					});
					scope.$apply('itemsSelected');

					if (angular.isDefined(scope.onChange)) {
						scope.onChange();
					}
				});

				// scope.removeFromSelected = function(item) {
				// 	$(select).prop("disabled", true);
				// 	var index = scope.itemsSelected.indexOf(item);
				// 	scope.itemsSelected.splice(index, 1);
				// 	renderItems();
				// };

				/**
				 * Disable/Enable select depends on items are loading or not.
				 */
				function manageSelectDisability() {
					$(select).prop("disabled", !scope.itemsLoaded);
				}

				/**
				 * Show loader during loading items
				 */
				function manageLoader() {
					if (scope.itemsLoaded) {
						loader.hide();
					} else {
						loader.show();
					}
				}

				/**
				 * Render items in multiselect. Rendering by jQuery
				 */
				function renderItems() {
					var str = "";
					if (scope.itemsSource.length > 0 && scope.allOption === true) {
						str += "<option selected>All</option>";
					}
					for (var i = 0; i < scope.itemsSource.length; i++) {
						if (scope.itemsSelected.indexOf(scope.itemsSource[i]) == -1) {
							str += "<option>" + scope.itemsSource[i] + "</option>";
						} else {
							str += "<option selected>" + scope.itemsSource[i] + "</option>";
						}
					}
					select.html(str);

					scope.itemsLoaded = true;
				}

				scope.$watch("itemsLoaded", function() {
					manageSelectDisability();
					manageLoader();
				});

				scope.$watch("itemsSource", function() {
					renderItems();
				}, true);
			}
		};
	}
})();