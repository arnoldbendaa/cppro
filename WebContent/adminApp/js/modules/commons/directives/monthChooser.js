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
        .directive('monthChooser', monthChooser);

    /* @ngInject */
    function monthChooser() {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'commons/views/monthChooser.html',
			scope: {
				date: '='
			},
			replace: true,
			link: function linkFunction(scope) {
				if (!angular.isDefined(scope.date) || (scope.date instanceof Date) == false)
					scope.date = new Date();

				scope.increase = function($event) {
					$event.stopPropagation();

					var month = scope.date.getMonth();
					month = month + 1;
					scope.date = new Date(scope.date.getFullYear(), month, scope.date.getDate());
				}

				scope.decrease = function($event) {
					$event.stopPropagation();

					var month = scope.date.getMonth();
					month = month - 1;
					scope.date = new Date(scope.date.getFullYear(), month, scope.date.getDate());
				}
			}
		};
	};

angular
.module('adminApp.commons')
.directive('monthFormat', monthFormat);

/* @ngInject */
function monthFormat() {

	return {
		require: 'ngModel',
		link: function(scope, element, attrs, ctrl) {
			ctrl.$parsers.unshift(function(modelValue) {
				if (modelValue.indexOf("/") == -1) {
					ctrl.$setValidity('monthFormat', false);
					return null;
				}

				var temp = modelValue.split("/");
				if (temp.length != 2 || temp[0].length > 2 || temp[1].length != 4) {
					ctrl.$setValidity('monthFormat', false);
					return null;
				}
				var month = parseInt(temp[0]);
				var year = parseInt(temp[1]);
				if (isNaN(month) || month <= 0 || month > 12 || isNaN(year)) {
					ctrl.$setValidity('monthFormat', false);
					return null;
				}
				ctrl.$setValidity('monthFormat', true);
				return new Date(year, month - 1, 1);
			});
			ctrl.$formatters.unshift(function(modelValue) {
				if (!modelValue || (modelValue instanceof Date) == false)
					return "";
				var month = modelValue.getMonth() + 1;
				month = (month < 10) ? "0" + month : "" + month;
				var retVal = month + "/" + modelValue.getFullYear();
				return retVal;
			});
		}
	  };
    };
})();