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
		.controller('CellNotesController', CellNotesController);

	/* @ngInject */
	function CellNotesController($scope, $rootScope) {


		$scope.openNote = openNote;
		$scope.closeNote = closeNote;
		$scope.createShortNoteAndFullNote = createShortNoteAndFullNote;
		$scope.createShortNote = createShortNote;
		$scope.showFullNote = showFullNote;
		$scope.hideFullNote = hideFullNote;
		$scope.setNotePosition = setNotePosition;

		$scope.$on('NotesModule:openNote', function(event, args) {
			console.log(args);
			$scope.openNote(args);
			$scope.setNotePosition(args.x, args.y);
		});

		$scope.$on('NoteModule:closeNote', function(event, args) {
			console.log(args);
			$scope.closeNote();
		});


		/************************************************** IMPLEMENTATION *************************************************************************/


		/**
		 * [openNote description]
		 * @param  {[type]} args [description]
		 * @return {[type]}      [description]
		 */
		function openNote(args) {
			angular.element('#cell-note').show();
			angular.element('#cell-note > .controlls > #show-full-note').show();
			angular.element('#cell-note > .controlls > #hide-full-note').hide();
			$scope.createShortNoteAndFullNote(args.note.author, args.note.text);

			if (args.note.linkId !== 0) {
				angular.element('#cell-note > #note-attachment-in-cell').html('<a href="' + $BASE_PATH + 'viewAttachmentSetup.do?ID=' + args.note.linkId + '" target="_blank"><span class="glyphicon glyphicon-paperclip"></span> Attachment</a>');
			}
		}

		/**
		 * [closeNote description]
		 * @return {[type]} [description]
		 */
		function closeNote() {
			angular.element('#cell-note').hide();
			angular.element('#cell-note > #note-attachment-in-cell').html('');
		}

		/**
		 * [createShortNoteAndFullNote description]
		 * @return {[type]} [description]
		 */
		function createShortNoteAndFullNote(author, text) {
			var teaserLength = 160;
			var noteText = "<strong>" + author + ":</strong> " + text;

			angular.element('#cell-note > .note').html(noteText);

			if (noteText.length > teaserLength) {
				angular.element('#cell-note > .short-note').html($scope.createShortNote(teaserLength, noteText));
				angular.element('#cell-note > .short-note').show();
				angular.element('#cell-note > .controlls').show();
				angular.element('#cell-note > .note').hide();
			} else {
				angular.element('#cell-note > .controlls').hide();
				angular.element('#cell-note > .short-note').hide();
				angular.element('#cell-note > .note').show();
			}
		}

		/**
		 * This function is creating the shotr text form note tiser.
		 * The last charts are ...
		 *
		 * @param  {[type]} n      [description]
		 * @param  {[type]} string [description]
		 * @return {[type]}        [description]
		 */
		function createShortNote(n, string) {
			var toLong = string.length > n,
				s_ = toLong ? string.substr(0, n - 1) : string;
			s_ = toLong ? s_.substr(0, s_.lastIndexOf(' ')) : s_;
			return toLong ? s_ + "..." : s_;
		}

		/**
		 * [showFullNote description]
		 * @return {[type]} [description]
		 */
		function showFullNote() {
			angular.element('#cell-note > .controlls > #show-full-note').hide();
			angular.element('#cell-note > .controlls > #hide-full-note').show();
			angular.element('#cell-note > .note').show();
			angular.element('#cell-note > .short-note').hide();
		}

		/**
		 * [hideFullNote description]
		 * @return {[type]} [description]
		 */
		function hideFullNote() {
			angular.element('#cell-note > .controlls > #show-full-note').show();
			angular.element('#cell-note > .controlls > #hide-full-note').hide();
			angular.element('#cell-note > .note').hide();
			angular.element('#cell-note > .short-note').show();
		}

		/**
		 * [setNotePosition description]
		 * @param {[type]} x [description]
		 * @param {[type]} y [description]
		 */
		function setNotePosition(x, y) {
			angular.element('#cell-note').css('top', y).css('left', x);
		}
	}
})();