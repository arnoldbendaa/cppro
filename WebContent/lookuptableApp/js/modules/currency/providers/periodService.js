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
	'use_strict';

	angular
		.module('lookupCurrencyApp.currency')
		.service('PeriodService', PeriodService);

	/* @ngInject */
	function PeriodService() {
		var self = this;
		var periods;

		self.getPeriods = getPeriods;

		activate();

		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			periods = [{
				id: 0,
				name: "Opening Balance"
			}, {
				id: 1,
				name: "Jan"
			}, {
				id: 2,
				name: "Feb"
			}, {
				id: 3,
				name: "Mar"
			}, {
				id: 4,
				name: "Apr"
			}, {
				id: 5,
				name: "May"
			}, {
				id: 6,
				name: "Jun"
			}, {
				id: 7,
				name: "Jul"
			}, {
				id: 8,
				name: "Aug"
			}, {
				id: 9,
				name: "Sep"
			}, {
				id: 10,
				name: "Oct"
			}, {
				id: 11,
				name: "Nov"
			}, {
				id: 12,
				name: "Dec"
			}, {
				id: 13,
				name: "Closing Balance"
			}, {
				id: 14,
				name: "Budget"
			}, {
				id: 15,
				name: "Forecast"
			}];
		}

		function getPeriods() {
			return periods;
		}

	};
})();