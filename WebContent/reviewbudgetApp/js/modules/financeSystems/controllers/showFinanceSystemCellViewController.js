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
		.module('app.financeSystemCell')
		.controller('ShowFinanceSystemCellViewController', ShowFinanceSystemCellViewController);

	/* @ngInject */
	function ShowFinanceSystemCellViewController($scope, $rootScope, $modalInstance, FinanceSystemCellService) {
		// private variables
		$scope.rows = null;
		$scope.columnNames = null;
		$scope.wholeValue = 0;
		$scope.wholeQuantity = 0;
		$scope.data = FinanceSystemCellService.getData();
		$scope.close = close;
		$scope.changePeriod = changePeriod;


		activate();

		function activate() {
			if ($scope.data !== null) {
				if ($scope.data.rows !== null) {
					console.log(" data rows " + $scope.data.rows.length);
				} else {
					console.log("$scope.data.rows null");
				}
			} else {
				console.log("$scope.data null");
			}
		}



		/************************************************** IMPLEMENTATION *************************************************************************/



		/**
		 * Close finance cell data modal and resets all properties in service
		 * @return {[type]} [description]
		 */
		function close() {
			$modalInstance.dismiss('close');
		}

		/**
		 * Change displayed data after changing period (select new start/end period)
		 * @param  {[type]} period [description]
		 * @return {[type]}        [description]
		 */
		function changePeriod(period) {
			if (period.name == "Start Period") {
				FinanceSystemCellService.startPeriod.value = period.value;
			} else {
				FinanceSystemCellService.endPeriod.value = period.value;
			}
			$rootScope.$broadcast('FinanceSystemCellService:DataUpdated');
		}


		/************************************************** EVENTS *********************************************************************/


		/**
		 * Handles event when data is loaded from database
		 * @return {[type]} [description]
		 */
		$scope.$on('FinanceSystemCellService:DataUpdated', function() {
			$scope.rows = FinanceSystemCellService.getRows();
			$scope.columnNames = FinanceSystemCellService.getColumnNames();
			
			$scope.wholeValue = FinanceSystemCellService.getWholeValue();
			$scope.wholeQuantity = FinanceSystemCellService.getWholeQuantity();
		});

		/**
		 * Handles any changes in timePeriod property, after inserting new time period in text input
		 * @param  {[type]} newValue [description]
		 * @param  {[type]} oldValue [description]
		 * @return {[type]}          [description]
		 */
		$scope.$watch('data.timePeriod.value', function(newValue, oldValue) {
			if (newValue != oldValue) {
				FinanceSystemCellService.timePeriod.value = newValue;
				if (FinanceSystemCellService.timePeriod.value.length > 3) {
					$rootScope.$broadcast('FinanceSystemCellService:DataUpdated');
				}
			}
		}, true);
	}
})();