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
		.service('BudgetInstructionsPageService', BudgetInstructionsPageService);

	function BudgetInstructionsPageService($rootScope, $http, Upload) {

		var self = this;
		// BudgetInstructions
		var areBudgetInstructionsLoaded = false;
		var areBudgetInstructionsLoading = false;
		var url = $BASE_PATH + 'adminPanel/budgetInstructions';
		var budgetInstructions = new wijmo.collections.CollectionView();
		var budgetInstructionDetails = {};
		var actions = [{
			name: "Delete",
			action: "deleteBudget",
			disabled: true
		}];
		self.isCreateDisabled = false;
		self.isOpenDisabled = true;
		self.isPrintDisabled = true;
		self.isAskDisabled = false;
		self.save = save;
		self.upload = upload;
		self.getBudgetInstructions = getBudgetInstructions;
		self.getBudgetInstructionDetails = getBudgetInstructionDetails;
		self.deleteBudget = deleteBudget;
		self.getActions = getActions;
		/**
		 * Call for  all budget instruction
		 */

		var getBudgetInstructionsFromDatabase = function() {
			areBudgetInstructionsLoaded = false;
			areBudgetInstructionsLoading = true;
			$http.get(url + "/").success(function(data) {
				areBudgetInstructionsLoaded = true;
				areBudgetInstructionsLoading = false;
				if (data && data.length >= 0) {
					budgetInstructions.sourceCollection = data;
				}
			});
		};

		function save(budgetInstruction) {
			$http.put(url + "/" + budgetInstruction.budgetInstructionId, budgetInstruction).success(function(data) {
				console.log(budgetInstruction);
				if (data.success == false) {

					$rootScope.$broadcast('BudgetInstructionsPageService:budgetInstructionDetailsSaveError', data);
				} else {
					budgetInstructions = [];

					getBudgetInstructionsFromDatabase();
					$rootScope.$broadcast('BudgetInstrucionDetails:close');

				}
			})
			//.error(function(response) {
			// 	$rootScope.$broadcast('DataTypesPageService:dataTypeDetailsSaveError', response);
			// })
			;
		};

		function upload(budgetInstruction) {
			if (budgetInstruction.budgetInstructionDocument === undefined || budgetInstruction.budgetInstructionDocument === null) {
				return Upload.upload({
					url: url + "/" + budgetInstruction.budgetInstructionId,
					method: 'POST',
					fileFormDataName: 'budgetInstructionDocument',
					fields: {
						'assignments': budgetInstruction.assignments,
						'budgetInstructionId': budgetInstruction.budgetInstructionId,
						'budgetInstructionVisId': budgetInstruction.budgetInstructionVisId,
						'budgetInstructionDocumentRef': budgetInstruction.budgetInstructionDocumentRef

					},

				}).success(function(data) {
					if (data.success == true) {
						$rootScope.$broadcast("BudgetInstructionDetails:close", budgetInstruction);
						getBudgetInstructionsFromDatabase();
					} else {
						$rootScope.$broadcast('BudgetInstructionsPageService:budgetInstructionDetailsSaveError', data);
					}
				});

			} else {
				return Upload.upload({
					url: url + "/" + budgetInstruction.budgetInstructionId,
					method: 'POST',
					fileFormDataName: 'budgetInstructionDocument',
					fields: {
						'assignments': budgetInstruction.assignments,
						'budgetInstructionId': budgetInstruction.budgetInstructionId,
						'budgetInstructionVisId': budgetInstruction.budgetInstructionVisId,
						'budgetInstructionDocumentRef': budgetInstruction.budgetInstructionDocument[0].name

					},
					file: budgetInstruction.budgetInstructionDocument[0]
				}).success(function(data) {
					if (data.success == true) {
						$rootScope.$broadcast("BudgetInstructionDetails:close", budgetInstruction);
						getBudgetInstructionsFromDatabase();
					} else {
						$rootScope.$broadcast('BudgetInstructionsPageService:budgetInstructionDetailsSaveError', data);
					}
				});
			}
		};

		function getBudgetInstructions() {

			getBudgetInstructionsFromDatabase();

			return budgetInstructions;
		};

		/**
		 * Call for budget instruction details
		 */
		function getBudgetInstructionDetails(budgetInstructionId) {
			budgetInstructionDetails = {};
			if (budgetInstructionId != -1) {
				$http.get(url + "/" + budgetInstructionId).success(function(response) {
					angular.copy(response, budgetInstructionDetails);

				});

			}

			return budgetInstructionDetails;
		};

		/**
		 * Delete budget instruction
		 */
		function deleteBudget(budgetInstructionId) {
			$http({
				method: 'DELETE',
				url: url + "/" + budgetInstructionId
			}).success(function(response) {
				if (response.success) {
					// refresh listing
					getBudgetInstructionsFromDatabase();
					$rootScope.$broadcast('BudgetInstructionsPageService:budgetInstructionDetailsDeleteSuccess');

				} else if (response.error) {
					$rootScope.$broadcast('BudgetInstructionsPageService:budgetInstructionDetailsDeleteError', response);
				};

			});

		};

		/**
		 * For top buttons
		 */
		function getActions() {
			return actions;
		};
	}
})()