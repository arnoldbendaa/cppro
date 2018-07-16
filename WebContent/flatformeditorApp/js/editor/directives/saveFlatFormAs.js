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
        .directive('saveFlatFormAs', saveFlatFormAs);

    /* @ngInject */
    function saveFlatFormAs(SpreadSheetService, $timeout, $rootScope) {

		return {
			restrict: 'E',
			templateUrl: $BASE_TEMPLATE_PATH + 'editor/views/saveFlatFormAs.html',
			scope: {
				saveAsParams: "=saveAsParams",
				spread: "=spread",
				flatform: "=flatform",
				close: '&',
			},
			replace: true,
			link: function linkFunction($scope) {
				var delay;

				$scope.save = function() {
		            var flatform = angular.copy($scope.flatform);
					// validate some data
					if (flatform.financeCubeId <= 0) {
						swal("Error", "Please supply Finance Cube", "warning");
						return;
					}					
		            var json = {
		                spread: $scope.spread.toJSON()
		            };
		            flatform.jsonForm = JSON.stringify(json);
		            flatform.visId = $scope.saveAsParams.visId;
		            flatform.description = $scope.saveAsParams.description;
		            $timeout.cancel(delay);
		            delay = $timeout(function() {
		                SpreadSheetService.saveAs(flatform);
		            }, 300);					
				};

				$scope.close = function() {
					$rootScope.$broadcast("SaveFlatFormAs:close");
				};
			}
		};
	}
})();