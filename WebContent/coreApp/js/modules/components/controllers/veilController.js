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
		.module('coreApp.components')
		.controller('VeilController', VeilController);

	/* @ngInject */
	function VeilController($scope, $rootScope) {

		$scope.isOpened = true;
		//property for disable loader on init 
		if ($scope.entry == false) {
			hideVeil();
		}
		$rootScope.$on('veil:show', function(event, args) {
			angular.element('#veil').show();
			angular.element('#veil-informations').show();
		});

		$rootScope.$on('veil:hide', function(event, args) {
			hideVeil();
		});

		function hideVeil() {
			angular.element('#veil').hide();
			angular.element('#veil-informations').hide();
			$scope.isOpened = false;
		}

		$rootScope.$on('veil:hideInformation', function(event, args) {
			angular.element('#veil-informations').hide();
		});

		$rootScope.$on('veil:showInformation', function(event, args) {
			angular.element('#veil-informations').show();
		});

		$rootScope.$on('veil:hideCover', function(event, args) {
			angular.element('#veil').hide();
		});

		$rootScope.$on('veil:showCover', function(event, args) {
			angular.element('#veil').show();
		});

		$rootScope.$on('exceptionViewer:foundError', function(event, args) {
			if ($scope.isOpened) {
				angular.element('#veil-informations').hide();
				angular.element('#veil-critical-error').show();
			}
		});
	}

})();