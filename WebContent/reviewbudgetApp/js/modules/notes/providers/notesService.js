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
		.service('NotesService', NotesService);

	/* @ngInject */
	function NotesService($http, Upload, $rootScope) {

		var vm = this;
		vm.getNotes = getNotes;
		vm.saveNote = saveNote;
		vm.updateNote = updateNote;
		vm.deleteNote = deleteNote;
		vm.getNotePk = getNotePk;


		/************************************************** IMPLEMENTATION *************************************************************************/


		function getNotes(cellPK, financeCube) {

			var url = $BASE_PATH + "reviewBudget" +
				"/notes/cell/" + cellPK +
				"/financeCube/" + financeCube;

			return $http.get(url);
		}

		/**
		 * [saveNote description]
		 * @param  {[type]} noteText [description]
		 * @param  {[type]} file     [description]
		 * @param  {[type]} config   [description]
		 * @return {[type]}          [description]
		 */
		function saveNote(noteText, file, config) {

			var url = $BASE_PATH + "reviewBudget" +
				"/note/cell/" + config.cellPK +
				"/financeCube/" + config.financeCube +
				"/dataEntryProfile/" + $rootScope.selectedProfile.profileId +
				"/budgetCycle/" + BUDGET_CYCLE_ID;

			if (config.company !== null) {
				url += "?company=" + config.company;
			}

			if (file === null) {
				return Upload.upload({
					url: url,
					method: 'POST',
					fileFormDataName: 'attachment',
					fields: {
						'note': noteText
					},
				});

			} else {
				return Upload.upload({
					url: url,
					method: 'POST',
					fileFormDataName: 'attachment',
					fields: {
						'note': noteText
					},
					file: file[0]
				});
			}
		}

		/**
		 * [updateNote description]
		 * @param  {[type]} note   [description]
		 * @param  {[type]} file   [description]
		 * @param  {[type]} config [description]
		 * @return {[type]}        [description]
		 */
		function updateNote(note, file, config) {
			var dataEntryProfileId = $rootScope.selectedProfile.profileId;

			var url = $BASE_PATH + "reviewBudget" +
				"/note/cell/" + config.cellPK +
				"/financeCube/" + config.financeCube +
				"/shouldDeleteOldAttachment/" + config.shouldDeleteOldAttachment +
				"/noteTime/" + note.time +
				"/link/" + note.linkId +
				"/author/" + note.author;

			if (config.company !== null) {
				url += "?company=" + config.company;
			}

			if (file === null) {
				return Upload.upload({
					url: url,
					method: 'POST',
					fileFormDataName: 'attachment',
					fields: {
						'note': note.text
					},
				});
			} else {
				return Upload.upload({
					url: url,
					method: 'POST',
					fileFormDataName: 'attachment',
					fields: {
						'note': note.text
					},
					file: file[0]
				});
			}
		}

		/**
		 * [deleteNote description]
		 * @param  {[type]} note   [description]
		 * @param  {[type]} config [description]
		 * @return {[type]}        [description]
		 */
		function deleteNote(note, config) {

			var url = $BASE_PATH + "reviewBudget" +
				"/note/cell/" + config.cellPK +
				"/financeCube/" + config.financeCube +
				"/noteTime/" + note.time +
				"/link/" + note.linkId +
				"/author/" + note.author;

			if (config.company !== null) {
				url += "?company=" + config.company;
			}

			return $http.delete(url);
		}

		/**
		 * [getNotePk description]
		 * @param  {[type]} mapping         [description]
		 * @param  {[type]} dim0            [description]
		 * @param  {[type]} dim1            [description]
		 * @param  {[type]} dim2            [description]
		 * @param  {[type]} contextDataType [description]
		 * @param  {[String]} sheetModel    [description]
		 * @return {[type]}                 [description]
		 */
		function getNotePk(mapping, dim0, dim1, dim2, contextDataType, sheetModel) {
			return $http.get(
				$BASE_PATH + 'reviewBudget/fetchCellPK' +
				'?modelId=' + MODEL_ID +
				'&sheetModel=' + sheetModel +
				'&mapping=' + mapping +
				'&contextDim0=' + dim0 +
				'&contextDim1=' + dim1 +
				'&contextDim2=' + dim2 +
				'&contextDataType=' + contextDataType);
		}
	}
})();