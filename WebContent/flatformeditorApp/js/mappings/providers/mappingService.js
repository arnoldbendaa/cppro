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
        .module('flatFormEditorApp.mappings')
        .service('MappingService', MappingService);

    /* @ngInject */
    function MappingService($rootScope, $http, ViewModeService, SpreadSheetService, CoreCommonsService, CellService) {

        var self = this;
        var mappingService = this;
        var cellPickerLoader = {
            accountStructureElementLoaded: false,
            businessStructureElementLoaded: false,
            calendarStructureElementLoaded: false
        };

        var currentMapping = {
            inputMapping: null,
            outputMapping: null,
            formulaMapping: null
        };

        var validation = {
            inputMapping: true,
            outputMapping: true,
            formulaMapping: true
        };

        mappingService.override = false;
        mappingService.overrideFormula = false;
        self.getCellPickerLoader = getCellPickerLoader;
        self.getCurrentMapping = getCurrentMapping;
        self.getValidation = getValidation;
        self.refreshCurrentMapping = refreshCurrentMapping;
        self.refreshValidation = refreshValidation;
        self.updateTag = updateTag;
        self.saveMapping = saveMapping;
        self.createTag = createTag;
        self.checkIfInputMapping = checkIfInputMapping;
        self.checkIfOutputMapping = checkIfOutputMapping;
        self.checkIfFormula = checkIfFormula;
        self.checkIfMapping = checkIfMapping;
        self.checkIfMappingIsCorrect = checkIfMappingIsCorrect;
        self.findCellInWorkbook = findCellInWorkbook;
        self.fillByDimension = fillByDimension;
        self.getDataTypesForFinanceCube = getDataTypesForFinanceCube;
        self.getStructureElements = getStructureElements;
        self.getCalendarStructureElements = getCalendarStructureElements;

        /************************************************** IMPLEMENTATION *************************************************************************/

        function getCellPickerLoader() {
            return cellPickerLoader;
        }

        function getCurrentMapping() {
            return currentMapping;
        }

        function getValidation() {
            return validation;
        }

        /**
         * Refreshes current mapping object (which is used for examples in inputs above spreadsheet view)
         */
        function refreshCurrentMapping(cell) {
            var tag = cell.tag();
            if (tag) {
                currentMapping.inputMapping = tag.inputMapping;
                currentMapping.outputMapping = tag.outputMapping;
                currentMapping.formulaMapping = tag.text;
            } else {
                currentMapping.inputMapping = null;
                currentMapping.outputMapping = null;
                currentMapping.formulaMapping = null;
            }
        }

        /**
         * Refreshes validation object. Setting to default state.
         */
        function refreshValidation() {
            validation.inputMapping = true;
            validation.outputMapping = true;
            validation.formulaMapping = true;
        }

        /**
         * Function update tag for spread cell
         */
        function updateTag(spreadCell, tag) {
            var cellMode = ViewModeService.getCurrentViewMode().name;
            if (cellMode == "TEST") {
                return null;
            }
            var oldTag = null;
            if (cellMode !== "LOADING") {
                oldTag = spreadCell.tag();
                if (oldTag) {
                    oldTag = angular.copy(oldTag);
                }
            }
            if (self.override === false) {
                if (cellMode == "VALUE") {
                    return oldTag;
                }
                if (cellMode === "INPUT") {
                    if (tag === null) { // clear tag. in fact it depends on view mode
                        if (oldTag) {
                            oldTag.inputMapping = null;
                            tag = oldTag;
                        } else {
                            return tag; // cell hasn't got any tags and new tag is null - cell type not changed
                        }
                    } else {
                        if (oldTag) {
                            oldTag.inputMapping = tag.inputMapping;
                            tag = oldTag;
                        } else {
                            tag.outputMapping = null;
                            tag.text = null;
                        }
                    }
                }

                if (cellMode === "OUTPUT") {
                    if (tag === null) { // clear tag. in fact it depends on view mode
                        if (oldTag) {
                            oldTag.outputMapping = null;
                            tag = oldTag;
                        } else {
                            return tag; // cell hasn't got any tags and new tag is null - cell type not changed
                        }
                    } else {
                        if (oldTag) {
                            oldTag.outputMapping = tag.outputMapping;
                            tag = oldTag;
                        } else {
                            tag.inputMapping = null;
                            tag.text = null;
                        }
                    }
                }

                if (cellMode === "FORMULA") {
                    if (tag === null) { // clear tag. in fact it depends on view mode
                        if (oldTag) {
                            oldTag.text = null;
                            tag = oldTag;
                        } else {
                            return tag; // cell hasn't got any tags and new tag is null - cell type not changed
                        }
                    } else {
                        if (oldTag) {
                            oldTag.text = tag.text;
                            tag = oldTag;
                        } else {
                            tag.inputMapping = null;
                            tag.outputMapping = null;
                        }
                    }
                }
            }

            if (cellMode !== "LOADING") {
                tag.column = spreadCell.col;
                tag.row = spreadCell.row;
                tag = mappingService.saveMapping(spreadCell, tag);
            }
            //console.log("setTag", tag);
            return tag;
        }

        /**
         * Save mapping for current spreadCell with data stored in tag object. If some field are not valid, validation object is updated and incorrect field is set as null.
         */
        function saveMapping(spreadCell, tag) {
            var workbookCell = self.findCellInWorkbook(spreadCell);
            var ifInputMapping = self.checkIfMappingIsCorrect('input', tag.inputMapping);
            var ifOutputMapping = self.checkIfMappingIsCorrect('output', tag.outputMapping);
            var ifFormulaMapping = self.checkIfMappingIsCorrect('formula', tag.text);

            if (workbookCell === null) {
                workbookCell = createNewIOCell(spreadCell, tag);
            }

            if (ifInputMapping) {
                workbookCell.inputMapping = tag.inputMapping;
            } else {
                workbookCell.inputMapping = null;
                tag.inputMapping = null;
                validation.inputMapping = false;
            }

            if (ifOutputMapping) {
                workbookCell.outputMapping = tag.outputMapping;
            } else {
                workbookCell.outputMapping = null;
                tag.outputMapping = null;
                validation.outputMapping = false;
            }

            if (ifFormulaMapping) {
                workbookCell.text = tag.text;
            } else {
                workbookCell.text = null;
                tag.text = null;
                validation.formulaMapping = false;
            }
            workbookCell.tags = tag.tags;

            if (workbookCell.inputMapping === null && workbookCell.outputMapping === null && workbookCell.text === null) {
                clearCellFromWorkbook(spreadCell);
                return null;
            }
            return tag;
        }

        /**
         * Creates new workbook cell in specific worksheet. Data for workbook cell are copied from tag
         */
        function createNewIOCell(spreadCell, tag) {
            var flatForm = SpreadSheetService.getFlatForm();
            var workbookSheet = CoreCommonsService.findElementByKey(flatForm.xmlForm.worksheets, spreadCell.sheet.name(), "name");
            var workbookCells = workbookSheet.cells;
            var workbookCell = angular.copy(tag);
            workbookCells.push(workbookCell);
            return workbookCell;
        }

        /**
         * Creates empty tag
         */
        function createTag() {
            var tag = {
                row: null,
                column: null,
                text: null,
                inputMapping: null,
                outputMapping: null
            };
            return tag;
        }
        var errorMsg = "";
        /**
         * Restores all input mappings from passed string. Return result as an array of these inputs or null
         */
        function getInputMappings(string) {
            if (string === null || angular.isUndefined(string)) {
                return null;
            }
            if (string.indexOf("cedar.cp.financeCube(") === 0 || string.indexOf("cedar.cp.dim0Identifier()") === 0 ||
                string.indexOf("cedar.cp.dim0Description()") === 0 || string.indexOf("cedar.cp.dim1Identifier()") === 0 ||
                string.indexOf("cedar.cp.dim1Description()") === 0 || string.indexOf("cedar.cp.dim2Identifier()") === 0 ||
                string.indexOf("cedar.cp.dim2Description()") === 0 || string.indexOf("cedar.cp.param(") === 0 ||
                string.indexOf("cedar.cp.structures(") === 0 || string.indexOf("cedar.cp.getVisId(") === 0 ||
                string.indexOf("cedar.cp.getDescription(") === 0 || string.indexOf("cedar.cp.getLabel(") === 0 ||
                string.indexOf("cedar.cp.formLink(") === 0) {
                return [string];
            }
            return string.match(/(cedar\.cp\.(getCell|getGlob|cell|getBaseVal|getQuantity|getCumBaseVal|getCumQuantity|getCurrencyLookup|getParameterLookup|getAuctionLookup)\(.*?\))+?/g);
        }

        /**
         * Restores all output mappings from passed string. Return result as an array of one output or null
         */
        function getOutputMappings(string) {
            if (string === null || angular.isUndefined(string)) {
                return null;
            }
            return string.match(/cedar\.cp\.(putCell|post)\(.*?\)/g);
        }

        /**
         * Checks if string contains correct input mapping
         */
        function checkIfInputMapping(string) {
            var inputs = getInputMappings(string);
            return inputs !== null && inputs.length == 1 && inputs[0].length == string.length;
        }

        /**
         * Checks if string contains correct output mapping
         */
        function checkIfOutputMapping(string) {
            var outputs = getOutputMappings(string);
            return outputs !== null && outputs.length == 1 && outputs[0].length == string.length;
        }

        /**
         * Checks if string contains formula
         */
        function checkIfFormula(string) {
            return (string !== null && angular.isDefined(string) && string.indexOf("=") === 0);
        }

        /**
         * Checks if string contains correct mapping
         */
        function checkIfMapping(string) {
            if (string === null || angular.isUndefined(string)) {
                return false;
            }
            var outputs = getOutputMappings(string);
            if (outputs !== null) {
                return false;
            }
            var inputs = getInputMappings(string);
            return inputs !== null;
        }

        /**
         * Checks if value contains correct mapping for specified type
         */
        function checkIfMappingIsCorrect(type, value) {
            if (value === null || value === "") {
                return true; // clear mapping
            } else if (type === 'input') {
                return self.checkIfInputMapping(value);
            } else if (type === 'output') {
                return self.checkIfOutputMapping(value);
            } else if (type === 'formula') {
                var ifFormula = self.checkIfFormula(value);

                var cellMode = ViewModeService.getCurrentViewMode().name;
                if (cellMode === "FORMULA" || self.overrideFormula === true) {
                    return ifFormula;
                }

                var ifMapping = self.checkIfMapping(value);
                return ifFormula && ifMapping;
            }
            return false;
        }

        /**
         * Finds spreadCell (by row, column) related to specific sheet in workbook object.
         */
        function findCellInWorkbook(spreadCell) {
            var flatForm = SpreadSheetService.getFlatForm();
            var workbookSheet = CoreCommonsService.findElementByKey(flatForm.xmlForm.worksheets, spreadCell.sheet.name(), "name");

            //TODO info jesli nie ma workbooksheet - czyli jesli bedzie NULLem
            var workbookCells = workbookSheet.cells;
            var findedCell = null;
            angular.forEach(workbookCells, function(cell) {
                if (cell.row == spreadCell.row && cell.column == spreadCell.col) {
                    findedCell = cell;
                    return;
                }
            });
            return findedCell;
        }

        /**
         * Remove for workbook object cell which is related to spreadCell
         */
        function clearCellFromWorkbook(spreadCell) {
            var flatForm = SpreadSheetService.getFlatForm();
            var workbookSheet = CoreCommonsService.findElementByKey(flatForm.xmlForm.worksheets, spreadCell.sheet.name(), "name");
            var workbookCell = self.findCellInWorkbook(spreadCell);
            var workbookCells = workbookSheet.cells;
            var index = workbookCells.indexOf(workbookCell);
            workbookCells.splice(index, 1);
        }

        /**
         * Get dimensions (which will be used in autofill function) for specific model. Offset = number of dimension which we want autofill.
         */
        function fillByDimension(modelId, dimensionIndex, dimensionVisId, offset) {
            var url = $BASE_PATH + "flatFormEditor/structureElements/fill";
            var data = {
                modelId: modelId,
                dimensionIndex: dimensionIndex,
                dimensionVisId: dimensionVisId,
                offset: offset
            };
            return $http.post(url, data);
        }

        function getDataTypesForFinanceCube(dataTypes, financeCubeId, modelId) {
            var collection = [];
            $http({
                method: "GET",
                url: $BASE_PATH + "flatFormEditor/dataTypesForFinanceCubes/" + financeCubeId + "/" + modelId
            }).success(function(response) {
                angular.copy(response, collection);
            });
            return collection;
        }

        function getStructureElements(structureId, structureElementId, typeOfQuery, isAccount) {
            var collection = [];
            $http({
                method: "GET",
                url: $BASE_PATH + "flatFormEditor/structureElements/" + structureId + "/" + structureElementId + "/" + typeOfQuery
            }).success(function(response) {
                angular.copy(response, collection);
                if (isAccount === true) {
                    cellPickerLoader.accountStructureElementLoaded = true;
                } else {
                    cellPickerLoader.businessStructureElementLoaded = true;
                }
            });
            return collection;
        }

        function getCalendarStructureElements(modelId, structureElementId, typeOfQuery) {
            var collection = [];
            $http({
                method: "GET",
                url: $BASE_PATH + "flatFormEditor/structureElements/calendar/" + modelId + "/" + structureElementId + "/" + typeOfQuery
            }).success(function(response) {
                angular.copy(response, collection);
                cellPickerLoader.calendarStructureElementLoaded = true;
            });
            return collection;
        }
    }
})();