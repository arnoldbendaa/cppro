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
		.service('DimensionCommonsService', DimensionCommonsService);

	/* @ngInject */
	function DimensionCommonsService($rootScope, $http, $compile) {

		var self = this;
		self.formatValidationInfo = formatValidationInfo;
		self.formatCreditDebit = formatCreditDebit;
		self.formatAugCreditDebit = formatAugCreditDebit;

		//----------------------------- Dimension Account/Business Elements on wijmo -----------------------------
		function formatValidationInfo(id, fieldName, validationDimensionElement, cell) {
			if (validationDimensionElement !== undefined) {
				if (validationDimensionElement[fieldName] !== undefined) {
					cell.classList.add("validation-error");
					cell.title = validationDimensionElement[fieldName];
				}
			}
		}

		function formatCreditDebit(value) {
			var newValue = null;
			switch (value) {
				case 1:
					newValue = "Credit";
					break;
				case 2:
					newValue = "Debit";
					break;
			}
			return newValue;
		}

		function formatAugCreditDebit(value) {
			var newValue = null;
			switch (value) {
				case 0:
					newValue = "No override";
					break;
				case 1:
					newValue = "Credit";
					break;
				case 2:
					newValue = "Debit";
					break;
			}
			return newValue;
		}

		//----------------------------- end of Dimension Account/Business Elements on wijmo -----------------------------

	}
})();