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
		.module('app.notes')
		.controller('CreateNoteController', CreateNoteController);

	/* @ngInject */
	function CreateNoteController($scope, $rootScope, $modalInstance, NotesService, cellObject) {

		$scope.note = {
			text: ''
		};

		$scope.save = save;
		$scope.cancel = cancel;
		$scope.onFileSelect = onFileSelect;
		$scope.saveAction = saveAction;


		/************************************************** IMPLEMENTATION *************************************************************************/



		function save() {
			$scope.saveAction();
		}

		function cancel() {
			$modalInstance.dismiss('cancel');
		}

		function onFileSelect($files) {
			$scope.file = $files;
		}

		function saveAction() {
			var file = $scope.file || null;
			var note = $scope.note.text;

			NotesService.saveNote(note, file, {
				cellPK: cellObject.cellObject.notes.notePk || null,
				financeCube: cellObject.workbook.worksheets[cellObject.worksheetId].properties.FINANCE_CUBE_ID,
				company: cellObject.cellObject.notes.company || null
			}).success(function(response) {
				$rootScope.notes = response.notes;
				$(".inner_table").animate({
					scrollTop: $(".inner_table > table").height()
				}, "slow");
				$modalInstance.close();
			});
		}
	}
})();