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
		.module('coreApp.validation')
		.service('validationService', validationService);

	/* @ngInject */
	function validationService($modal, CoreCommonsService) {
		var self = this;
		self.getValidationErrors = getValidationErrors;
		self.showErrors = showErrors;

		function getValidationErrors(workbook) {
			if (workbook == null || workbook.isValid == true) {
				return;
			}
			var sheets = workbook.worksheets;
			var errmsg = [];
			if (sheets != null) {
				angular.forEach(sheets, function(sheet) {
					if (sheet == null || sheet.isValid == true) {
						return;
					}
					var cells = sheet.cells;
					var cellmsg = [];
					angular.forEach(cells, function(cell) {
						var errors = cell.validationMessages;
						if (errors != null) {
							if (errors[0] != null && errors[0].length > 0) {
								// cellmsg.push(errors[0]);
								angular.forEach(errors[0], function(msg) {
									var cellMsg = composeMessage(cell, msg);
									cellmsg.push(cellMsg);
								})
							}
							// if (errors[1] !== null && errors[1] !== undefined) {
							// 	cellmsg.push(errors[1]);
							// }
						}
					});
					if (cellmsg.length > 0) {
						errmsg.push({
							sheet: "Sheet \"" + sheet.name + "\" has incorect mappings:",
							cell: cellmsg
						});
					}
				});
			}
			return errmsg;
		}

		function composeMessage(cell, msg) {
			var col = CoreCommonsService.getColumnNameByIndex(cell.column + 1);
			return "cell " + col + (cell.row + 1) + " : " + msg;
		}

		function showErrors(messages) {
			if (messages == null || messages.length === 0) {
				return;
			}
			var modalInstance = $modal.open({
				backdrop: 'static',
				windowClass: 'softpro-modals',
				size: 'lg',
				template: '<validation-display></validation-display>',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.worksheetMessages = messages;

						function close() {
							$modalInstance.dismiss('cancel');
						}

						$scope.$on('validationDisplay:close', function(event, args) {
							$modalInstance.close();
						});
					}

				]
			});
		};
	}
})();