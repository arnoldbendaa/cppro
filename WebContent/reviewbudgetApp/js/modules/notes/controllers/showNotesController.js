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
		.controller('ShowNotesCotroller', ShowNotesCotroller);

	/* @ngInject */
	function ShowNotesCotroller($scope, $rootScope, $modalInstance, cellObject, notesIsDataLoading) {
		$scope.createNew = createNew;
		$scope.edit = edit;
		$scope.deleteNote = deleteNote;
		$scope.close = close;
		$scope.notesIsDataLoading = notesIsDataLoading;

		$scope.$on('createManagerModal:dataWasLoaded', function() {
			console.log('zmieniono stan wczytywania danych.');
			$scope.notesIsDataLoading = false;
		});

		/************************************************** IMPLEMENTATION *************************************************************************/



		function close() {
			$modalInstance.dismiss('close');

			if (typeof $rootScope.notes !== 'undefined' && $rootScope.notes !== null && $rootScope.notes.length > 0) {
				if (typeof cellObject.cellObject.notes.notes == 'undefined') {
					cellObject.cellObject.notes.notes = [];
					cellObject.cellObject.notes.notes.push($rootScope.notes[$rootScope.notes.length - 1]);
				} else {
					cellObject.cellObject.notes.notes[0] = $rootScope.notes[$rootScope.notes.length - 1];
				}
			} else {
				delete cellObject.cellObject.notes.notes;
			}

			var spreadSheetElement = angular.element(document.querySelector('#spreadSheet'));
			var sheet = spreadSheetElement.wijspread("spread").getActiveSheet();
			sheet.setActiveCell(cellObject.Cell.row, cellObject.Cell.col);
			$rootScope.notes = "";
		}

		function createNew() {
			$rootScope.$broadcast('NotesController:openManagerCreateNewNote', cellObject);
		}

		function edit(noteObject) {
			$rootScope.$broadcast('NotesController:openManagerEditNote', {
				cellObject: cellObject,
				note: noteObject
			});
		}

		function deleteNote(noteObject) {
			$rootScope.$broadcast('NotesController:openManagerDeleteNote', {
				cellObject: cellObject,
				note: noteObject
			});

		}
	}
})();