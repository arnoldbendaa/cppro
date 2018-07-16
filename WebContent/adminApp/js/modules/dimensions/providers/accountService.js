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
        .module('adminApp.dimension')
        .service('AccountService', AccountService);
    
    /* @ngInject */
    function AccountService($rootScope, $http, CoreCommonsService) {

		var self = this;
		var areAccountsLoaded = false;
		var areAccountsLoading = false;
		var url = $BASE_PATH + 'adminPanel/dimensions/accounts';
		var accounts = new wijmo.collections.CollectionView();
		var accountDetails = {};
		var actions = {};
		self.isCreateDisabled = false;
		self.isOpenDisabled = true;
		self.clearCache = clearCache;
		self.getAccounts = getAccounts;
		self.getAccountDetails = getAccountDetails;
		self.createEmptyAccount = createEmptyAccount;
		self.save = save;
		self.deleteAccount = deleteAccount;
		self.getActions = getActions;
		activate();
		
		function activate(){
		    actions = [{
	            name: "Delete",
	            action: "deleteAccount",
	            disabled: true
	        }];   
		}
		
        /**
         * Clear list of Account Dimensions.
         */
        function clearCache() {
            areAccountsLoaded = false;
            areAccountsLoading = false;
        }

		var getAccountsFromDatabase = function() {
			areAccountsLoaded = false;
			areAccountsLoading = true;
			$http.get(url).success(function(data) {
				areAccountsLoaded = true;
				areAccountsLoading = false;
				if (data && data.length >= 0) {
					accounts.sourceCollection = data;
				}
			});
		};

		/**
		 * Return accounts if were taken, otherwise call getting Accounts from database.
		 */
		function getAccounts(hardReload) {
			if ((!areAccountsLoaded && !areAccountsLoading) || hardReload) {
				getAccountsFromDatabase();
			}
			return accounts;
		}

		/**
		 * Get details of one Account (get always from database to have the newest version).
		 * @param  {Integer} accountId [description]
		 */
		function getAccountDetails(accountId) {
			accountDetails = {};
			if (accountId != -1) {
				$http.get(url + "/" + accountId).success(function(response) {
					angular.copy(response, accountDetails);
				});
			}
			return accountDetails;
		}

		/**
		 * Create new Dmension Account object to return to directive and have ready object for scope and template. Now "id" is always -1.
		 * @param  {Integer} id
		 */
		function createEmptyAccount(id) {
			var account = {
				dimensionId: id, // now id=-1
				dimensionVisId: "",
				model: {
					modelId: null,
					modelVisId: "Unassigned",
				},
				dimensionDescription: "",
				type: 1,
				sequence: -1,
				hierarchies: -1,
				externalSystemRefName: "N/A",
				dimensionElements: [],
				readOnly: false,
				inUseLabel: null,
				augentMode: null,
				versionNum: -1,
				submitChangeManagementRequest: false,
			};
			return account;
		}

		/**
		 * Save account details. It's "insert" (when account.id is -1) or "update" otherwise.
		 * @param  {Object} account [account details]
		 */
		function save(account) {
			if (account === null) {
				return;
			}
			var method = "";
			var insert;
			if (account.dimensionId != -1) {
				// edit Dimension
				method = "PUT";
				insert = false;
			} else {
				// create Dimension
				method = "POST";
				insert = true;
			}
			// Send request as PUT (for insert) or POST (for update)
			$http({
				method: method,
				url: url + "/",
				data: account
			}).success(function(response) {
				if (response.success) {
					accounts.length = 0;
					// refresh listing
					if (insert){
					    getAccountsFromDatabase();
                    } else {
					var accountsChange = CoreCommonsService.findElementByKey(accounts.sourceCollection, account.dimensionId, 'dimensionId');
					accountsChange.model.modelVisId = account.model.modelVisId;
					accountsChange.dimensionVisId = account.dimensionVisId;
					accountsChange.dimensionDescription = account.dimensionDescription;
                    accounts.refresh();
                    }
					var dataToReturn = response;
					// to response object add information if it was PUT or POST action
					dataToReturn.method = method;
					$rootScope.$broadcast('AccountDetails:close', dataToReturn);
				} else if (response.error) {
					$rootScope.$broadcast('AccountService:accountDetailsSaveError', response);
				}
			});
		}

		/**
		 * Delete Dimension
		 * @param  {Integer} dimensionId
		 */
		function deleteAccount(dimensionId) {
			$http({
				method: 'DELETE',
				url: url + "/" + dimensionId
			}).success(function(response) {
				if (response.success) {
					accounts.length = 0;
					// refresh listing
					getAccountsFromDatabase();
					$rootScope.$broadcast('AccountService:accountDetailsDeleteSuccess');
				} else if (response.error) {
					$rootScope.$broadcast('AccountService:accountDetailsDeleteError', response);
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