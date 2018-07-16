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
		.module('app.responsibilityAssignments')
		.service('ResponsibilityAssignmentsService', ResponsibilityAssignmentsService);

	/* @ngInject */
	function ResponsibilityAssignmentsService($rootScope, $http) {

		var vm = this;

		vm.getData = getData;
		vm.resetData = resetData;
		vm.getDataFromDatabase = getDataFromDatabase;


		/**
		 * Get all data containg information about users access from database
		 * @return {[type]} [description]
		 */
		function getDataFromDatabase() {
			$http.get(
				$BASE_PATH + 'reviewBudget/fetchBudgetRespAreas' +
				'?modelId=' + MODEL_ID
			).success(function(response) {
				vm.isDataLoaded = true;
				vm.model = response.modelVisId;
				vm.rows = response.respAreas;
			});
		}

		/**
		 * Reset data in template
		 * @return {[type]} [description]
		 */
		function resetData() {
			vm.isDataLoaded = false;
			vm.model = null;
			vm.rows = null;
		}

		/**
		 * Returns all data which used in modal window
		 * @return {[type]} [description]
		 */
		function getData() {
			return vm; //TODO ???
		}
	}
})();