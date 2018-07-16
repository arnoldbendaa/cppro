/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 * 
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without
 * the prior written consent of IT Services Jacek Kurasiewicz. Contact The
 * Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok. 43, 00-673
 * Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 * 
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING
 * LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION,
 * EVEN IF IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING
 * DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES
 * JACEK KURASIEWICZ HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 * ENHANCEMENTS, OR MODIFICATIONS.
 ******************************************************************************/
(function() {
	'use strict';

	angular.module('dashboardViewApp.dashboardView').service(
			'DashboardViewService', DashboardViewService);

	/* @ngInject */
	function DashboardViewService($http) {

		var self = this;
		var url = $BASE_PATH + 'dashboard/getauction/';

		self.getForms = getForms;
		self.getChosenForm = getChosenForm;
		self.getDashboard = getDashboard;

		/**
		 * ************************************************ IMPLEMENTATION
		 * ************************************************************************
		 */

		function getForms() {
			console.log($BASE_PATH);
			var getFormsUrl = $BASE_PATH + "adminPanel/flatForms";
			return $http.get(getFormsUrl).then(getAllComplete);

			function getAllComplete(response) {
				var result = {
					data : response.data
				};
				return result;
			}
		}

		function getChosenForm(flatFormId) {
			var getChosenFormUrl = $BASE_PATH + "flatFormEditor/flatForm/"
					+ flatFormId;
			return $http.get(getChosenFormUrl).then(getAllComplete);

			function getAllComplete(response) {
				var result = {
					data : response.data
				};
				return result;
			}
		}

		function getDashboard(uuid) {
			return $http.get("/cppro/dashboard/form/data/" + uuid).then(
					getAllComplete);

			function getAllComplete(response) {
				var result = {
					data : response.data
				};
				return result;
			}
		}
	}
})();