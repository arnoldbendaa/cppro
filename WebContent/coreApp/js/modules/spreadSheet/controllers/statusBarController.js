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
		.module('coreApp.spreadSheet')
		.controller('StatusBarController', StatusBarController);

	/* @ngInject */
	function StatusBarController($scope, $timeout, ZoomService) {
		var isSpreadInvoking = false;
		$scope.zoom = ZoomService.getZoomData();

		activate();
		/////////////////////////////////////////////////////////////

		function activate() {
			initZoomSlider();
			onUserZooming();
			onSheetTabClick();
			onZoomStatusChanged();
		}

		function initZoomSlider() {
			var zoomSlider = $('#zoom-controller').slider({
				min: 25,
				max: 400,
				step: 5,
				value: $scope.zoom.status,
				tooltip: 'hide',
				formatter: function(value) {
					return 'Current value: ' + value;
				}
			}).on('slide', function(ev) {
				$scope.zoom.status = ev.value;
				$scope.$apply('zoom.status');
			});
		}

		function onZoomStatusChanged() {
			$scope.$watch("zoom.status", function() {
				$('#zoom-controller').slider('setValue', $scope.zoom.status);
				updateSpread();
			});
		}

		function updateSpread() {
			if (angular.isDefined($scope.spread) && isSpreadInvoking === false) {
				var sheet = $scope.spread.getActiveSheet();
				if (sheet) {
					sheet.zoom($scope.zoom.status / 100);
				}
			}
			isSpreadInvoking = false;
		}

		function onUserZooming() {
			$scope.$on("SpreadSheetController:UserZooming", function(e, info) {
				isSpreadInvoking = true;
				$scope.zoom.status = Math.round(info.newZoomFactor * 100);
				$scope.$apply('zoom.status');
			});
		}

		function onSheetTabClick() {
			$scope.$on("SpreadSheetController:SheetTabClick", function(e, info) {
				isSpreadInvoking = true;
				var sheet;
				if (info.sheet === null) {
					$scope.zoom.status = 100;
				} else {
					sheet = info.sheet;
					$scope.zoom.status = Math.round(sheet.zoom() * 100);
				}
				$scope.$apply('zoom.status');
			});
		}

		/* If user wants to set the same zoom as current, we change $scope.status just a little to force spread zooming.
		I.e.: When spread is 100%, user use ctrl+scroll to zoom spread and then click on 100% button, spread doesn't react. Changing $scope.status for a moment forces zooming.*/
		$scope.$on("ZoomController:zoomTo", function(e, value) {
			if ($scope.zoom.status != value) {
				$timeout(function() {
					$scope.zoom.status = value;
				}, 0);
			} else {
				$scope.zoom.status = value + 0.01;
				$timeout(function() {
					$scope.zoom.status = value;
				}, 0);
			}
		});
	}

})();