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
        .controller('NotesController', NotesController);

    /* @ngInject */
    function NotesController($scope, $rootScope, $controller, $modal, NotesService) {


        $scope.createNoteWindow = createNoteWindow;
        $scope.createManagerModal = createManagerModal;
        $scope.createAddNewNoteView = createAddNewNoteView;
        $scope.createEditNoteView = createEditNoteView;
        $scope.createDeleteNoteView = createDeleteNoteView;



        /************************************************** IMPLEMENTATION *************************************************************************/



        /**
         * [createNoteWidow description]
         * @return {[type]} [description]
         */
        function createNoteWindow(cellObject) {

            var mapping = cellObject.cellObject.getMapping(),
                dim0 = $rootScope.selectedDimensions[0].name,
                dim1 = $rootScope.selectedDimensions[1].name,
                dim2 = $rootScope.selectedDimensions[2].name,
                contextDataType = $rootScope.selectedDataType,
                financeCubeId = cellObject.workbook.worksheets[cellObject.worksheetId].properties.FINANCE_CUBE_ID,
                sheetModel = $rootScope.workbook.worksheets[cellObject.worksheetId].properties.MODEL_ID,
                cellType = cellObject.cellObject;

            $scope.createManagerModal(cellObject, true);
            $rootScope.USER_NAME = USER_NAME;
            $rootScope.$BASE_PATH = $BASE_PATH;

            if (cellType.notes !== null) {

                var notePk = cellObject.cellObject.notes.notePk || null;

                //Check is object has notes in db
                NotesService.getNotes(notePk, financeCubeId).success(function(response) {
                    if (response.notes !== null) {
                        $rootScope.notes = response.notes;
                        $rootScope.$broadcast('createManagerModal:dataWasLoaded');
                    } else {
                        NotesService.getNotePk(mapping, dim0, dim1, dim2, contextDataType, sheetModel).success(function(response) {
                            if (response !== null) {
                                cellObject.cellObject.notes = {
                                    notePk: response
                                };
                                $rootScope.$broadcast('createManagerModal:dataWasLoaded');
                            }
                        });
                    }
                });
            } else {
                NotesService.getNotePk(mapping, dim0, dim1, dim2, contextDataType, sheetModel).success(function(response) {
                    if (response !== null) {
                        cellObject.cellObject.notes = {
                            notePk: response
                        };
                        NotesService.getNotes(response, financeCubeId).success(function(response) {

                            $rootScope.notes = response.notes || null;
                            $rootScope.$broadcast('createManagerModal:dataWasLoaded');
                        });
                    }
                });
            }
        }

        /**
         * [createManagerModal description]
         * @param  {[type]} cellObject [description]
         * @return {[type]}            [description]
         */
        function createManagerModal(cellObject, notesIsDataLoading) {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'notes/views/showNotes.html',
                backdrop: 'static',
                windowClass: 'cell-note-modal',
                controller: 'ShowNotesCotroller',
                resolve: {
                    cellObject: function() {
                        return cellObject;
                    },
                    notesIsDataLoading: function() {
                        return notesIsDataLoading;
                    }

                }
            });
        }

        /**
         * [createAddNewNoteView description]
         * @param  {[type]} cellObject [description]
         */
        function createAddNewNoteView(cellObject) {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'notes/views/createNote.html',
                backdrop: 'static',
                windowClass: 'cell-note-modal',
                controller: 'CreateNoteController',
                resolve: {
                    cellObject: function() {
                        return cellObject;
                    }
                }
            });
        }

        /**
         * [createEditNoteView description]
         * @param  {[type]} cellObject  [description]
         * @param  {[type]} $noteObject [description]
         */
        function createEditNoteView(cellObject, $noteObject) {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'notes/views/editNote.html',
                backdrop: 'static',
                windowClass: 'cell-note-modal',
                controller: 'EditNoteController',
                resolve: {
                    cellObject: function() {
                        return cellObject;
                    },
                    noteObject: function() {
                        return $noteObject;
                    }
                }
            });
        }

        /**
         * [createDeleteNoteView description]
         * @param  {[type]} cellObject  [description]
         * @param  {[type]} $noteObject [description]
         */
        function createDeleteNoteView(cellObject, $noteObject) {
            var modalInstance = $modal.open({
                templateUrl: $BASE_TEMPLATE_PATH + 'notes/views/deleteNote.html',
                controller: 'DeleteNoteController',
                resolve: {
                    cellObject: function() {
                        return cellObject;
                    },
                    noteObject: function() {
                        return $noteObject;
                    }
                }
            });
        }


        /************************************************** EVENTS *********************************************************************/



        /**
         * [description]
         * @param  {[type]} event [description]
         * @param  {[type]} args  [description]
         */
        $rootScope.$on('NotesController:OpenNotesManager', function(event, cellObject) {
            $scope.createNoteWindow(cellObject);
        });

        /**
         * [description]
         * @param  {[type]} event [description]
         * @param  {[type]} args  [description]
         */
        $rootScope.$on('NotesController:openManagerCreateNewNote', function(event, cellObject) {
            $scope.createAddNewNoteView(cellObject);
        });

        /**
         * [description]
         * @param  {[type]} event [description]
         * @param  {[type]} args  [description]
         */
        $rootScope.$on('NotesController:openManagerEditNote', function(event, args) {
            $scope.createEditNoteView(args.cellObject, args.note);
        });

        /**
         * [description]
         * @param  {[type]} event [description]
         * @param  {[type]} args  [description]
         */
        $rootScope.$on('NotesController:openManagerDeleteNote', function(event, args) {
            $scope.createDeleteNoteView(args.cellObject, args.note);
        });

    }
})();