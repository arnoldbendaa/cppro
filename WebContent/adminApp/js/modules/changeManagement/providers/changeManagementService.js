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
        .module('adminApp.budgetInstructionsPage')
        .service('ChangeManagementService', ChangeManagementService);

    function ChangeManagementService($rootScope, $http) {

		var self = this;
		var areChangeMgmtsLoaded = false;
		var areChangeMgmtsLoading = false;
		var url = $BASE_PATH + 'adminPanel/changemanagement';
		var changeMgmts = new wijmo.collections.CollectionView();
		var actions = [{
			name: "Submit",
			action: "submit",
			disabled: true
		}];
		self.isCreateDisabled = true;
		self.isOpenDisabled = true;
		self.getChangeMgmts = getChangeMgmts;
		self.submit = submit;
		self.getActions = getActions;
		
		var getChangeMgmtsFromDatabase = function() {
			areChangeMgmtsLoaded = false;
			areChangeMgmtsLoading = true;
			$http.get(url).success(function(data) {
				areChangeMgmtsLoaded = true;
				areChangeMgmtsLoading = false;
				if (data && data.length >= 0) {
					changeMgmts.sourceCollection = data;
				}
			});
		};

		/**
		 * Return ChangeMgmts if were taken, otherwise call getting ChangeMgmts from database.
		 */
		function getChangeMgmts() {
			//if (!areChangeMgmtsLoaded && !areChangeMgmtsLoading) {
				getChangeMgmtsFromDatabase();
			//}
			return changeMgmts;
		}

		/**
		 * Submit ChangeMgmt
		 * @param  {Integer} changeMgmtId
		 */
		function submit(changeMgmtId) {
			$http({
				method: 'POST',
				url: url + "/" + changeMgmtId
			}).success(function(response) {
				if (response.success) {
					changeMgmts.length = 0;
					// refresh listing
					getChangeMgmtsFromDatabase();
					$rootScope.$broadcast('ChangeManagementService:ChangeManagementSubmitSuccess');
				} else if (response.error) {
					$rootScope.$broadcast('ChangeManagementService:ChangeManagementSubmitError', response);
				}
			});
		}

		/**
		 * For top buttons
		 */
		function getActions() {
			return actions;
		}
	}
})();