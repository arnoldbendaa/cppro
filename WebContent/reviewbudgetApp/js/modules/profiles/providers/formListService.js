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
		.module('app.profiles')
		.service('FormListService', FormListService);

	/* @ngInject */
	function FormListService($http, $rootScope) {
		var vm = this;
		var url = $BASE_PATH + 'reviewBudget/formlist/' + MODEL_ID + '/' + BUDGET_CYCLE_ID;
		vm.formList = [];

		vm.getFormListFromDatabase = getFormListFromDatabase;
		vm.getFormList = getFormList;
		vm.setFormList = setFormList;
		vm.getFormListNames = getFormListNames;
		vm.findFormById = findFormById;

		function getFormListFromDatabase() {
			$http.get(url).success(function(data) {
				vm.setFormList(data);
			});
		}

		function getFormList() {
			return vm.formList;
		}

		function setFormList(formList) {
			vm.formList = formList;
			$rootScope.$broadcast('FormListService:formListUpdated');
		}

		function findFormById(formId) {
			if (!vm.formList) {
				return null;
			}
			var form = null;
			angular.forEach(vm.formList, function(value, key) {
				if (value.formId == formId) {
					form = vm.formList[key];
				}
			});
			return form;
		}

		function getFormListNames() {
			var formListNames = [];
			angular.forEach(vm.formList, function(formList) {
				formListNames.push(formList.name);
			});
			return formListNames;
		}
	}
})();