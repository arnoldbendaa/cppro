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
        .directive('cellPicker', cellPicker);

    /* @ngInject */
    function cellPicker($rootScope, Flash, DataService, SpreadSheetService, MappingService, $timeout, $modal, CoreCommonsService) {

    return {
      restrict: 'E',
      templateUrl: $BASE_TEMPLATE_PATH + 'mappings/views/cellPicker.html',
      // scope: {
      // id: '='
      // },
      replace: true,
      controller: ['$scope',
        function($scope) {

          // parameters to resize modal
          var modalDialog = $(".cell-picker").closest(".modal-dialog");
          var elementToResize = null; // $(".");
          var additionalElementsToCalcResize = [];
          $scope.cookieName = "flatFormEditor_modal_cellPicker";
          // try to resize and move modal based on the cookie
          CoreCommonsService.changeModalParams(modalDialog, $scope.cookieName);
          $scope.resizedColumn = function(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
          };
          $scope.sortedColumn = function(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName);
          };
          $timeout(function() { // timeout is necessary to pass asynchro
            CoreCommonsService.allowResizingModal(modalDialog, elementToResize, additionalElementsToCalcResize, $scope.ctx);
          }, 0);

          var DIMENSION_CALENDAR_TYPE_NUMBER = 2;
          var DIMENSION_ACCOUNT_TYPE_NUMBER = 0;
          var DIMENSION_BUSINESS_TYPE_NUMBER = 1;
          var currentSpreadSheetName = $scope.currentSpreadSheet.name();
          $scope.flatForm = SpreadSheetService.getFlatForm();
          var financeCube = {};
          var firstLoading = true;
          $scope.cells = [];
          $scope.cellPickerLoader = MappingService.getCellPickerLoader();
          $scope.useContextualCalendarSyntax = false;
          $scope.yearOffset;
          $scope.periodOffset;
          $scope.dataTypesForFinanceCube = {};
          $scope.includeVisId = false;
          $scope.includeDescription = false;
          $scope.dataOnPreview = false;
          $scope.loaded = false;
          $scope.chosenDataType = null;
          $scope.ioSelections = ["Input", "Output", "Input & Output"];
          $scope.chosenIOSelection = "Input";
          $scope.outputTypes = ["Movements", "Balances", "Cell Notes"];
          $scope.chosenOutputType = "Movements";
          $scope.orientation = ["Fixed", "Columns", "Rows"];
          $scope.query = ["None", "Immediate Children", "Leaves", "Cascade", "Contextual"];
          $scope.modelSheet = [];
          $scope.validation = {
            table: ''
          };
          $scope.changeHierarchy = changeHierarchy;
          $scope.changeOrientationType = changeOrientationType;
          $scope.validateCellPicker = validateCellPicker;
          $scope.runQueryForOriginalSheet = runQueryForOriginalSheet;
          $scope.runQueryForPreviewSheet = runQueryForPreviewSheet;
          $scope.openTreePicker = openTreePicker;
          $scope.close = close;
          
          /************************************************** IMPLEMENTATION *************************************************************************/
          
          /**
           * Change hierarchy. If hierarchy is chosen then this position in array was changed on first.
           */
          function changeHierarchy(hierarchy, indeks) {
            for (var i = 1; i < $scope.currentModelDimensions[indeks].hierarchies.length; i++) {
              if ($scope.currentModelDimensions[indeks].hierarchies[i].hierarchyId == hierarchy.hierarchyId) {
                var change = $scope.currentModelDimensions[indeks].hierarchies[0];
                $scope.currentModelDimensions[indeks].hierarchies[0] = $scope.currentModelDimensions.hierarchies[i];
                $scope.currentModelDimensions[indeks].hierarchies[i] = change;
              }
            }
          };

          /**
           * Change orientation. If element have orientation Columns or Rows and other have the same orientation this function change orientation other element on Fixed.
           */
          function changeOrientationType(orientation, indeks) {
            if (orientation === "Columns") {
              for (var i = 0; i < 3; i++) {
                if (indeks !== i && $scope.currentDimensionElementGroups[i].chosenOrentation === orientation) {
                  $scope.currentDimensionElementGroups[i].chosenOrentation = "Fixed";
                }
              }
              $scope.currentDimensionElementGroups[indeks].chosenOrentation = orientation;
            } else if (orientation === "Rows") {
              for (var i = 0; i < 3; i++) {
                if (indeks !== i && $scope.currentDimensionElementGroups[i].chosenOrentation === orientation) {
                  $scope.currentDimensionElementGroups[i].chosenOrentation = "Fixed";
                }
              }
              $scope.currentDimensionElementGroups[indeks].chosenOrentation = orientation;
            }
          }

          /**
           * Check if Cell Picker update not broke conditions
           */
          function validateCellPicker() {
            $scope.isError = false;
            angular.forEach($scope.validation, function(message, field) {
              $scope.validation[field] = "";
            });
            for (var i = 0; i < 3; i++) {
              if ($scope.currentDimensionElementGroups[i].id.length === 0) {
                $scope.isError = true;
                $scope.validation.table = "No structure element selected for dimemsion" + $scope.currentModelDimensions[i].dimensionVisId + "<br>Row: " + (i + 1);
                i = 3;
              }
            }
            if ($scope.isError === false) {
              for (var i = 0; i < 3; i++) {
                if ($scope.currentDimensionElementGroups[i].chosenOrentation === "Fixed" && $scope.currentDimensionElementGroups[i].id.length > 1) {
                  $scope.isError = true;
                  $scope.validation.table = "Invalid query<br>Multi Vis Id selected while orientation is fixed.<br>Row: " + (i + 1) + ". Dimension: " + $scope.currentModelDimensions[i].dimensionVisId;
                  i = 3;
                }
              }
            }
            if ($scope.isError === false) {
              if (($scope.currentDimensionElementGroups[2].chosenQuery === "None" || $scope.currentDimensionElementGroups[2].chosenQuery === "Cascade") && $scope.currentDimensionElementGroups[2].group[0].structureElementDescription === "Calendar") {
                $scope.isError = true;
                $scope.validation.table = "The query mode of Root Calendar must not be None or Cascade";
                i = 3;
              }
            }

            if ($scope.isError === true) {
              Flash.create('danger', "Error during run query:<br>" + $scope.validation.table);
            } else {
              $scope.loaded = true;
              getStructureElementLists();
            }
          };

          /**
           * Get and direct structure element lists to dimensions depending on chosen queries.
           */
          var getStructureElementLists = function() {
            $scope.dimensions = [];
            if ($scope.currentDimensionElementGroups[0].chosenQuery !== "None" && $scope.currentDimensionElementGroups[0].chosenQuery !== "Contextual") {
              $scope.account = MappingService.getStructureElements($scope.currentModelDimensions[0].hierarchies[0].hierarchyId, $scope.currentDimensionElementGroups[0].id.toString(), $scope.currentDimensionElementGroups[0].chosenQuery, true);
              $scope.dimensions.push($scope.account);
            } else {
              if ($scope.currentDimensionElementGroups[0].chosenQuery === "None") {
                $scope.account = $scope.currentDimensionElementGroups[0].group;
              } else {
                $scope.account = ["Contextual"];
              }
              $scope.dimensions.push($scope.account);
              $scope.cellPickerLoader.accountStructureElementLoaded = true;
            }
            if ($scope.currentDimensionElementGroups[1].chosenQuery !== "None" && $scope.currentDimensionElementGroups[1].chosenQuery !== "Contextual") {
              $scope.business = MappingService.getStructureElements($scope.currentModelDimensions[1].hierarchies[0].hierarchyId, $scope.currentDimensionElementGroups[1].id.toString(), $scope.currentDimensionElementGroups[1].chosenQuery, false);
              $scope.dimensions.push($scope.business);
            } else {
              if ($scope.currentDimensionElementGroups[1].chosenQuery === "None") {
                $scope.business = $scope.currentDimensionElementGroups[1].group;
              } else {
                $scope.business = ["Contextual"];
              }
              $scope.dimensions.push($scope.business);
              $scope.cellPickerLoader.businessStructureElementLoaded = true;
            }
            if ($scope.currentDimensionElementGroups[2].chosenQuery !== "None" && $scope.currentDimensionElementGroups[2].chosenQuery !== "Contextual") {
              $scope.calendar = MappingService.getCalendarStructureElements($scope.currentFinanceCube.model.modelId, $scope.currentDimensionElementGroups[2].id.toString(), $scope.currentDimensionElementGroups[2].chosenQuery);
              $scope.dimensions.push($scope.calendar);
            } else {
              $scope.calendar = $scope.currentDimensionElementGroups[2].group;
              $scope.dimensions.push($scope.calendar);
              $scope.cellPickerLoader.calendarStructureElementLoaded = true;
            }
          };

          /**
           * Product values, input and output mappings for chosen group cells.
           */
          var cellPicker = function() {
            var startCol = 0;
            var startRow = 0;
            var colDim = -1;
            var rowDim = -1;
            var colContextual = -1;
            var rowContextual = -1;
            var colCalIndex = -1;
            var rowCalIndex = -1;

            var rowCount;

            var accountStructureElement;
            var businessStructureElement;
            var calendarStructureElement;

            var input;
            var output;

            calculateYearAndPeriodOffsetsForCalendarDimension();

            for (rowCount = 0; rowCount < 3; rowCount++) {
              var outputRow = $scope.currentDimensionElementGroups[rowCount].chosenOrentation;
              var outputColumn = $scope.currentDimensionElementGroups[rowCount].chosenQuery;
              if (outputRow === "Rows") {
                rowDim = rowCount;
                if (outputColumn === "Contextual") {
                  rowContextual = rowCount;
                }
              } else if (outputRow === "Columns") {
                colDim = rowCount;
                if (outputColumn === "Contextual") {
                  colContextual = rowCount;
                }
              }
            }

            rowCount = rowDim === -1 ? 1 : $scope.dimensions[rowDim].length;
            var colCount = colDim === -1 ? 1 : $scope.dimensions[colDim].length;
            var currentRowOfCell = startRow;

            if ($scope.original === false) {
              if (rowCount > 200) {
                $scope.previewSpread.sheets[0].addRows(200, rowCount - 200, GC.Spread.Sheets.SheetArea.viewport);
              }

              if (colCount > 19) {
                $scope.previewSpread.sheets[0].addColumns(19, colCount - 19, GC.Spread.Sheets.SheetArea.viewport);
              }

              $scope.dataOnPreview = true;
            }

            for (var row = 0; row < rowCount; row++) {
              var currentColOfCell = startCol;
              var isCalendar;
              var col;
              var structElemetVec;
              var text;
              if (row === 0 && colDim !== -1 && ($scope.includeVisId || $scope.includeDescription)) {

                if (rowDim != -1) {
                  currentColOfCell = startCol + 1;
                }

                for (col = 0; col < colCount; ++col) {
                  isCalendar = colDim === DIMENSION_CALENDAR_TYPE_NUMBER;
                  if (colContextual !== -1) {

                    text = getLabelTextWithContextual($scope.dimensions[colDim][col], colDim, isCalendar);
                    writeCell(currentRowOfCell, currentColOfCell, null, text, null);
                  } else {
                    if (isCalendar) {
                      text = getLabelTextForDimmesion($scope.dimensions[colDim][col], true);
                      if ($scope.useContextualCalendarSyntax === false) {
                        writeCell(currentRowOfCell, currentColOfCell, text, null, null);
                      } else {
                        writeCell(currentRowOfCell, currentColOfCell, null, text, null);
                      }
                    } else {
                      text = getLabelTextForDimmesion($scope.dimensions[colDim][col], false);
                      writeCell(currentRowOfCell, currentColOfCell, text, null, null);
                    }

                  }
                  ++currentColOfCell;
                }

                currentColOfCell = startCol;
                ++currentRowOfCell;
              }

              for (col = 0; col < colCount; ++col) {
                if (col === 0 && rowDim !== -1 && ($scope.includeVisId || $scope.includeDescription)) {
                  isCalendar = rowDim === DIMENSION_CALENDAR_TYPE_NUMBER;
                  if (rowContextual !== -1) {

                    text = getLabelTextWithContextual($scope.dimensions[rowDim][row], rowDim, isCalendar);
                    writeCell(currentRowOfCell, currentColOfCell, null, text, null);
                  } else {
                    if (isCalendar) {
                      text = getLabelTextForDimmesion($scope.dimensions[rowDim][row], true);
                      if ($scope.useContextualCalendarSyntax === false) {
                        writeCell(currentRowOfCell, currentColOfCell, text, null, null);
                      } else {
                        writeCell(currentRowOfCell, currentColOfCell, null, text, null);
                      }
                    } else {
                      text = getLabelTextForDimmesion($scope.dimensions[rowDim][row], false);
                      writeCell(currentRowOfCell, currentColOfCell, text, null, null);
                    }
                  }
                  if (row === 0 && $scope.original === false) {
                    $scope.previewSpread.sheets[0].setColumnWidth(currentColOfCell, 400, GC.Spread.Sheets.SheetArea.viewport);
                  }
                  ++currentColOfCell;
                }

                if ($scope.currentDimensionElementGroups[DIMENSION_ACCOUNT_TYPE_NUMBER].chosenQuery === "Contextual") {
                  accountStructureElement = null;
                } else {
                  if ($scope.currentDimensionElementGroups[DIMENSION_ACCOUNT_TYPE_NUMBER].chosenOrentation === "Rows") {
                    accountStructureElement = $scope.dimensions[DIMENSION_ACCOUNT_TYPE_NUMBER][row];
                  }
                  if ($scope.currentDimensionElementGroups[DIMENSION_ACCOUNT_TYPE_NUMBER].chosenOrentation === "Columns") {
                    accountStructureElement = $scope.dimensions[DIMENSION_ACCOUNT_TYPE_NUMBER][col];
                  } else {
                    accountStructureElement = $scope.dimensions[DIMENSION_ACCOUNT_TYPE_NUMBER][0];
                  }
                }
                if ($scope.currentDimensionElementGroups[DIMENSION_BUSINESS_TYPE_NUMBER].chosenQuery === "Contextual") {
                  businessStructureElement = null;
                } else {
                  if ($scope.currentDimensionElementGroups[DIMENSION_BUSINESS_TYPE_NUMBER].chosenOrentation === "Rows") {
                    businessStructureElement = $scope.dimensions[DIMENSION_BUSINESS_TYPE_NUMBER][row];
                  } else if ($scope.currentDimensionElementGroups[DIMENSION_BUSINESS_TYPE_NUMBER].chosenOrentation === "Columns") {
                    businessStructureElement = $scope.dimensions[DIMENSION_BUSINESS_TYPE_NUMBER][col];
                  } else {
                    businessStructureElement = $scope.dimensions[DIMENSION_BUSINESS_TYPE_NUMBER][0];
                  }
                }
                if ($scope.currentDimensionElementGroups[DIMENSION_CALENDAR_TYPE_NUMBER].chosenOrentation === "Rows") {
                  calendarStructureElement = $scope.dimensions[DIMENSION_CALENDAR_TYPE_NUMBER][row];
                } else if ($scope.currentDimensionElementGroups[DIMENSION_CALENDAR_TYPE_NUMBER].chosenOrentation === "Columns") {
                  calendarStructureElement = $scope.dimensions[DIMENSION_CALENDAR_TYPE_NUMBER][col];
                } else {
                  calendarStructureElement = $scope.dimensions[DIMENSION_CALENDAR_TYPE_NUMBER][0];
                }
                if ($scope.currentDimensionElementGroups[DIMENSION_CALENDAR_TYPE_NUMBER].chosenQuery === "Contextual") {
                  var contextual = {
                    structureElementVisId: "Contextual",
                    parentVisId: null,
                    yearOffset: calendarStructureElement.yearOffset,
                    periodOffset: calendarStructureElement.periodOffset
                  };
                  calendarStructureElement = contextual;
                }

                if ($scope.chosenIOSelection === "Input" || $scope.chosenIOSelection === "Input & Output") {
                  input = generateMapping("Input", accountStructureElement, businessStructureElement, calendarStructureElement)
                }
                if ($scope.chosenIOSelection === "Output" || $scope.chosenIOSelection === "Input & Output") {
                  output = generateMapping("Output", accountStructureElement, businessStructureElement, calendarStructureElement)
                }

                writeCell(currentRowOfCell, currentColOfCell, null, input, output);

                if (row === 0 && $scope.original === false) {
                  $scope.previewSpread.sheets[0].setColumnWidth(currentColOfCell, 400, GC.Spread.Sheets.SheetArea.viewport);
                }
                ++currentColOfCell;
              }

              ++currentRowOfCell;
            }
            $scope.loaded = false;
            if ($scope.original === true) {
              $rootScope.$broadcast("CellPicker:close");
            } else {
              clearYearAndPeriodOffset();
            }
          }

          /**
           * Write value, input mapping, output mapping or input and output mappings for single cell.
           */
          var writeCell = function(row, column, text, inputMapping, outputMapping) {
            if ($scope.original) {
              var sheet = $scope.currentSpreadSheet;
              var cell = sheet.getCell(sheet.getActiveRowIndex() + row, sheet.getActiveColumnIndex() + column, GC.Spread.Sheets.SheetArea.viewport, true);
              console.log(cell);

              MappingService.override = true;
              if ((inputMapping !== null && inputMapping !== undefined) || (outputMapping !== null && outputMapping !== undefined)) {
                var tag = MappingService.createTag();

                tag.row = cell.row;
                tag.column = cell.col;

                if (inputMapping !== null && inputMapping !== undefined) {
                  tag.inputMapping = inputMapping;
                }
                if (outputMapping !== null && outputMapping !== undefined) {
                  tag.outputMapping = outputMapping;
                }
                cell.tag(tag);
              } else if (text !== null) {
                var formula = cell.formula();
                if (formula !== null) {
                  cell.formula(null);
                }
                cell.value(text);
              }
              MappingService.override = false;
            } else {
              var sheet = $scope.previewSpread.sheets[0];
              var cell = sheet.getCell(row, column, GC.Spread.Sheets.SheetArea.viewport, true);

              if ((inputMapping !== null && inputMapping !== undefined) || (outputMapping !== null && outputMapping !== undefined)) {

                if ((inputMapping !== null && inputMapping !== undefined) && (outputMapping !== null && outputMapping !== undefined)) {
                  var ioMapping = "IM{{" + inputMapping + "}}+OM{{" + outputMapping + "}}";
                  cell.value(ioMapping);
                } else if (inputMapping !== null && inputMapping !== undefined) {
                  var inputMapping = "IM{{" + inputMapping + "}}";
                  cell.value(inputMapping);
                } else if (outputMapping !== null && outputMapping !== undefined) {
                  var outputMapping = "OM{{" + outputMapping + "}}";
                  cell.value(outputMapping);
                }
              } else if (text !== null) {
                var formula = cell.formula();
                if (formula !== null) {
                  cell.formula(null);
                }
                cell.value(text);
              }
            }
          }

          /**
           * Generate input or output mapping.
           */
          var generateMapping = function(type, dimension0, dimension1, dimension2) {
            var comma = false;
            var content;
            var dimmensionVisId0 = getVisIdFromDimension(dimension0, false);
            var dimmensionVisId1 = getVisIdFromDimension(dimension1, false);
            var dimmensionVisId2 = getVisIdFromDimension(dimension2, true);
            if (type === "Input") {
              content = "cedar.cp.getCell(";
            } else {
              content = "cedar.cp.putCell(";
            }
            if ($scope.chosenOutputType !== null && $scope.chosenOutputType !== 'Movements') {
              comma = true;
              if ($scope.chosenOutputType === "Balances") {
                content += "type=\"B\"";
              } else if ($scope.chosenOutputType === "Cell Notes") {
                content += "type=\"N\"";
              }
            }
            if (dimmensionVisId0 !== null && dimmensionVisId0 !== undefined) {
              if (comma === true) {
                content += ",";
              }
              comma = true;
              content += "dim0=\"" + dimmensionVisId0 + "\"";
            }
            if (dimmensionVisId1 !== null && dimmensionVisId1 !== undefined) {
              if (comma === true) {
                content += ",";
              }
              comma = true;
              content += "dim1=\"" + dimmensionVisId1 + "\"";
            }
            if (dimmensionVisId2 !== null && dimmensionVisId2 !== undefined && dimmensionVisId2 !== "Contextual") {
              if (comma === true) {
                content += ",";
              }
              comma = true;
              content = content + "dim2=\"" + dimmensionVisId2 + "\"";
            }
            if (dimension2 !== null && dimension2.yearOffset !== null && dimension2.yearOffset !== undefined && dimension2.yearOffset !== "") {
              if (comma === true) {
                content += ";";
              }
              comma = true;
              content += "year=\"" + dimension2.yearOffset + "\"";
            }
            if (dimension2 !== null && dimension2.periodOffset !== null && dimension2.periodOffset !== undefined && dimension2.periodOffset !== "") {
              if (comma === true) {
                content += ";";
              }
              comma = true;
              content += "period=\"" + dimension2.periodOffset + "\"";
            }
            if ($scope.chosenDataType !== null && $scope.chosenDataType !== undefined && $scope.chosenDataType.dataTypeVisId !== "Contextual") {
              if (comma === true) {
                content += ",";
              }
              comma = true;
              content += "dt=\"" + $scope.chosenDataType.dataTypeVisId + "\"";
            }
            content += ")";
            return content;
          }

          /**
           * Get label text for dimension depending on chosen options.
           */
          var getLabelTextForDimmesion = function(dimObject, isCalendar) {
            var dimensionVisId = getVisIdFromDimension(dimObject, isCalendar);
            var dimensionDescription = getDescriptionFromDimension(dimObject);
            var content = "";
            var cal = "";
            var yearOffset = null;
            var periodOffset = null;
            if ($scope.includeDescription && $scope.includeVisId) {
              cal = dimensionVisId + " " + dimensionDescription;
            } else if ($scope.includeDescription) {
              cal = dimensionDescription;
            } else if ($scope.includeVisId) {
              cal = dimensionVisId;
            }

            if (isCalendar === true) {
              if ($scope.useContextualCalendarSyntax === true) {
                if (dimObject.parentVisId === null) {
                  cal = "?";
                } else {
                  cal = "?/" + dimObject.structureElementVisId;
                }
                if ($scope.includeVisId && $scope.includeDescription) {
                  content = "cedar.cp.getLabel(dim2=";
                } else if ($scope.includeVisId) {
                  content = "cedar.cp.getVisId(dim2=";
                } else if ($scope.includeDescription) {
                  content = "cedar.cp.getDescription(dim2=";
                }

                content += ("\"" + cal + "\"");
                yearOffset = dimObject.yearOffset;
                if (yearOffset != null && yearOffset !== "") {
                  content += ";year=\"" + yearOffset + "\"";
                }

                periodOffset = dimObject.periodOffset;
                if (periodOffset != null && !periodOffset !== "") {
                  content += ";period=\"" + periodOffset + "\"";
                }

                content += ")";
              }

              if (content.length === 0) {
                content = cal;
              }
            } else {
              content = cal;
            }

            return content;
          }

          /**
           * Get contextual label text for dimension type depending on chosen options.
           */
          var getLabelTextWithContextual = function(dimObject, type, isCalendar) {
            if ($scope.includeVisId && $scope.includeDescription) {
              content = "cedar.cp.getLabel(";
            } else if ($scope.includeVisId) {
              content = "cedar.cp.getVisId(";
            } else if ($scope.includeDescription) {
              content = "cedar.cp.getDescription(";
            }

            if (isCalendar && ($scope.yearOffset || $scope.periodOffset)) {
              if ($scope.yearOffset) {
                content += "year=\"" + dimObject.yearOffset + "\"";
              }
              if ($scope.yearOffset && $scope.periodOffset) {
                content += ";"
              }
              if ($scope.periodOffset) {
                content += "period=\"" + dimObject.periodOffset + "\"";
              }
            } else {
              content += "dim" + type;
            }
            content += ")";
            return content;
          }

          /**
           * Get visId from difference kinds of type dimension object.
           */
          var getVisIdFromDimension = function(dimension, isCalendar) {
            if (dimension === null) {
              return null;
            }
            var visId = null;
            if (dimension.parentVisId !== undefined && dimension.parentVisId !== null) {
              if (dimension.visualId !== undefined) {
                if ($scope.useContextualCalendarSyntax === false || isCalendar === false) {
                  visId = "/" + dimension.parentVisId + "/" + dimension.visualId;
                } else {
                  visId = "?/" + dimension.structureElementVisId;
                }
              } else {
                if (dimension.structureElementVisId === "Contextual") {
                  visId = "Contextual";
                } else if ($scope.useContextualCalendarSyntax === false || isCalendar === false) {
                  visId = "/" + dimension.parentVisId + "/" + dimension.structureElementVisId;
                } else {
                  visId = "?/" + dimension.structureElementVisId;
                }
              }
            } else {
              if ($scope.useContextualCalendarSyntax === false || isCalendar === false) {
                if (dimension.visualId !== undefined) {
                  visId = dimension.visualId;
                } else {
                  if (dimension.structureElementVisId === "Contextual") {
                    visId = "Contextual";
                  } else if (isCalendar) {
                    visId = "/" + dimension.structureElementVisId;
                  } else {
                    visId = dimension.structureElementVisId;
                  }
                }
              } else {
                if (dimension.structureElementVisId === "Contextual") {
                  visId = "Contextual";
                } else {
                  visId = "?";
                }
              }
            }
            return visId;
          }

          /**
           * Get description from difference kinds of dimension object type.
           */
          var getDescriptionFromDimension = function(dimension) {
            if (dimension === null) {
              return null;
            }
            var description = null;
            if (dimension.description !== undefined) {
              description = dimension.description;
            } else {
              description = dimension.structureElementDescription;
            }
            return description;
          }

          /**
           * Calculate and set year and period offsets for calendar dimension structure elements.
           */
          var calculateYearAndPeriodOffsetsForCalendarDimension = function() {
            if ($scope.yearOffset || $scope.periodOffset) {
              var minYear = 3000;
              var maxYear = 0;
              var currentYear;
              var currentMonth;
              var yearsLenght;
              var transformator;
              var minMonthsByYear = [];
              var testerType = $scope.calendar[0];
              if (testerType.structureElementVisId === undefined) {
                for (var i = 0; i < $scope.calendar.length; i++) {
                  testerType = $scope.calendar[i];
                  if (testerType.parentVisId === undefined || testerType.parentVisId === null) {
                    currentYear = +$scope.calendar[i].visualId;
                  } else {
                    currentYear = +$scope.calendar[i].parentVisId;
                  }
                  if (currentYear < minYear) {
                    minYear = currentYear;
                  }
                  if (currentYear > maxYear) {
                    maxYear = currentYear;
                  }
                }
                yearsLenght = maxYear - minYear + 1;
                for (var i = 0; i < yearsLenght; i++) {
                  minMonthsByYear.push(13);
                }
                for (var i = 0; i < $scope.calendar.length; i++) {
                  testerType = $scope.calendar[i];
                  if (testerType.parentVisId === undefined || testerType.parentVisId === null) {
                    currentYear = +$scope.calendar[i].visualId;
                  } else {
                    currentYear = +$scope.calendar[i].parentVisId;
                  }
                  if ($scope.yearOffset) {
                    transformator = +$scope.yearOffset;
                    $scope.calendar[i].yearOffset = transformator + currentYear - minYear;
                  }
                  if (testerType.parentVisId !== undefined || testerType.parentVisId !== null) {
                    currentMonth = +$scope.calendar[i].visualId;
                    if (minMonthsByYear[currentYear - minYear] > currentMonth) {
                      minMonthsByYear[currentYear - minYear] = currentMonth;
                    }
                  }
                }
                if ($scope.periodOffset) {
                  for (var i = 0; i < $scope.calendar.length; i++) {
                    testerType = $scope.calendar[i];
                    if (testerType.parentVisId === undefined || testerType.parentVisId === null) {
                      $scope.calendar[i].periodOffset = +$scope.periodOffset;
                    } else {
                      currentMonth = +$scope.calendar[i].visualId;
                      transformator = +$scope.periodOffset;
                      $scope.calendar[i].periodOffset = transformator + currentMonth - minMonthsByYear[currentYear - minYear];
                    }
                  }
                }
              } else {
                for (var i = 0; i < $scope.calendar.length; i++) {
                  testerType = $scope.calendar[i];
                  if (testerType.parentVisId === undefined || testerType.parentVisId === null) {
                    currentYear = +$scope.calendar[i].structureElementVisId;
                  } else {
                    currentYear = +$scope.calendar[i].parentVisId;
                  }
                  if (currentYear < minYear) {
                    minYear = currentYear;
                  }
                  if (currentYear > maxYear) {
                    maxYear = currentYear;
                  }
                }
                yearsLenght = maxYear - minYear + 1;
                for (var i = 0; i < yearsLenght; i++) {
                  minMonthsByYear.push(13);
                }
                for (var i = 0; i < $scope.calendar.length; i++) {
                  testerType = $scope.calendar[i];
                  if (testerType.parentVisId === undefined || testerType.parentVisId === null) {
                    currentYear = +$scope.calendar[i].structureElementVisId;
                  } else {
                    currentYear = +$scope.calendar[i].parentVisId;
                  }
                  if ($scope.yearOffset) {
                    transformator = +$scope.yearOffset;
                    $scope.calendar[i].yearOffset = transformator + currentYear - minYear;
                  }
                  if (testerType.parentVisId !== undefined || testerType.parentVisId !== null) {
                    currentMonth = +$scope.calendar[i].structureElementVisId;
                    if (minMonthsByYear[currentYear - minYear] > currentMonth) {
                      minMonthsByYear[currentYear - minYear] = currentMonth;
                    }
                  }
                }
                if ($scope.periodOffset) {
                  for (var i = 0; i < $scope.calendar.length; i++) {
                    testerType = $scope.calendar[i];
                    if (testerType.parentVisId === undefined || testerType.parentVisId === null) {
                      $scope.calendar[i].periodOffset = +$scope.periodOffset;
                    } else {
                      currentMonth = +$scope.calendar[i].structureElementVisId;
                      transformator = +$scope.periodOffset;
                      $scope.calendar[i].periodOffset = transformator + currentMonth - minMonthsByYear[currentYear - minYear];
                    }
                  }
                }
              }
            }
          }

          var clearYearAndPeriodOffset = function() {
            for (var i = 0; i < $scope.calendar[i].length; i++) {
              $scope.calendar[i].yearOffset = "";
              $scope.calendar[i].periodOffset = "";
            }

          }

          function runQueryForOriginalSheet() {
            $scope.original = true;
            $scope.validateCellPicker();
          }

          function runQueryForPreviewSheet() {
            $scope.original = false;
            if ($scope.dataOnPreview === true) {
              $scope.previewSpread.sheets[0].reset();
            }
            $scope.validateCellPicker();
          }

          function openTreePicker() {
            var modelId = $scope.currentFinanceCube.model.modelId;
            var financeCubeId = $scope.financeCubeId;
            var treesVisibility = [true, true, true, false];

            // var selectedCcTree = ($scope.selectedCcTree !== undefined) ? $scope.selectedCcTree : [];
            // var selectedExpTree = ($scope.selectedExpTree !== undefined) ? $scope.selectedExpTree : [];
            // var selectedCallTree = ($scope.selectedCallTree !== undefined) ? $scope.selectedCallTree : [];
            var selectedCcTree = [];
            var selectedExpTree = [];
            var selectedCallTree = [];

            var modalTitle = "Cell Picker Tree";
            var modalInstance = $modal.open({
              template: '<trees-chooser modal-title="modalTitle" selected-cc-tree="selectedCcTree" selected-exp-tree="selectedExpTree" selected-call-tree="selectedCallTree" selected-data-type="selectedDataType" model-id="modelId"  finance-cube-id="financeCubeId" multiple="multiple" ok="ok()"  trees-visibility="treesVisibility" tab-index="tabIndex" close="close()"></trees-chooser>',
              windowClass: 'sub-system-modals softpro-modals trees-chooser-dialog',
              backdrop: 'static',
              size: 'lg',
              controller: ['$scope', '$modalInstance',
                function($scope, $modalInstance) {
                  $scope.modalTitle = modalTitle;
                  $scope.selectedCcTree = selectedCcTree;
                  $scope.selectedExpTree = selectedExpTree;
                  $scope.selectedCallTree = selectedCallTree;
                  $scope.selectedDataType = [];
                  $scope.modelId = modelId;
                  $scope.financeCubeId = financeCubeId;
                  $scope.multiple = false;
                  $scope.multiple = true;
                  // if (tabIndex == 3) { // if EXCLUDE_DATA_TYPES
                  // $scope.multiple = true;
                  // }
                  $scope.treesVisibility = treesVisibility;
                  // $scope.tabIndex = tabIndex;

                  $scope.ok = function() {
                    var obj = {
                      selectedCcTree: $scope.selectedCcTree,
                      selectedCallTree: $scope.selectedCallTree,
                      selectedExpTree: $scope.selectedExpTree,
                      selectedDataType: $scope.selectedDataType,
                    };

                    $modalInstance.close(obj);
                  };
                  $scope.close = function() {
                    $modalInstance.dismiss('cancel');
                  };
                }
              ]
            });
            modalInstance.result.then(function(obj) {
              if (obj.selectedCcTree[0] !== undefined) {
                $scope.selectedCcTree = obj.selectedCcTree;
                $scope.currentDimensionElementGroups[0].group = obj.selectedCcTree;
                $scope.currentDimensionElementGroups[0].id.length = 0;
                $scope.currentDimensionElementGroups[0].id.push(obj.selectedCcTree[0].structureElementId);
                $scope.currentDimensionElementGroups[0].visualId.length = 0;
                $scope.currentDimensionElementGroups[0].visualId.push(obj.selectedCcTree[0].structureElementVisId);
                $scope.currentDimensionElementGroups[0].description.length = 0;
                $scope.currentDimensionElementGroups[0].description.push(obj.selectedCcTree[0].structureElementDescription);
                for (var i = 1; i < obj.selectedCcTree.length; i++) {
                  $scope.currentDimensionElementGroups[0].id.push(obj.selectedCcTree[i].structureElementId);
                  $scope.currentDimensionElementGroups[0].visualId.push(obj.selectedCcTree[i].structureElementVisId);
                  $scope.currentDimensionElementGroups[0].description.push(obj.selectedCcTree[i].structureElementDescription);
                }
              }
              if (obj.selectedExpTree[0] !== undefined) {
                $scope.selectedExpTree = obj.selectedExpTree;
                $scope.currentDimensionElementGroups[1].group = obj.selectedExpTree;
                $scope.currentDimensionElementGroups[1].id.length = 0;
                $scope.currentDimensionElementGroups[1].id.push(obj.selectedExpTree[0].structureElementId);
                $scope.currentDimensionElementGroups[1].visualId.length = 0;
                $scope.currentDimensionElementGroups[1].visualId.push(obj.selectedExpTree[0].structureElementVisId);
                $scope.currentDimensionElementGroups[1].description.length = 0;
                $scope.currentDimensionElementGroups[1].description.push(obj.selectedExpTree[0].structureElementDescription);
                for (var i = 1; i < obj.selectedExpTree.length; i++) {
                  $scope.currentDimensionElementGroups[1].id.push(obj.selectedExpTree[i].structureElementId);
                  $scope.currentDimensionElementGroups[1].visualId.push(obj.selectedExpTree[i].structureElementVisId);
                  $scope.currentDimensionElementGroups[1].description.push(obj.selectedExpTree[i].structureElementDescription);
                }
              }
              if (obj.selectedCallTree[0] !== undefined) {
                $scope.selectedCallTree = obj.selectedCallTree;
                $scope.currentDimensionElementGroups[2].group = obj.selectedCallTree;
                $scope.currentDimensionElementGroups[2].id.length = 0;
                $scope.currentDimensionElementGroups[2].id.push(obj.selectedCallTree[0].structureElementId);
                $scope.currentDimensionElementGroups[2].visualId.length = 0;
                $scope.currentDimensionElementGroups[2].visualId.push(obj.selectedCallTree[0].structureElementVisId);
                $scope.currentDimensionElementGroups[2].description.length = 0;
                $scope.currentDimensionElementGroups[2].description.push(obj.selectedCallTree[0].structureElementDescription);
                for (var i = 1; i < obj.selectedCallTree.length; i++) {
                  $scope.currentDimensionElementGroups[2].id.push(obj.selectedCallTree[i].structureElementId);
                  $scope.currentDimensionElementGroups[2].visualId.push(obj.selectedCallTree[i].structureElementVisId);
                  $scope.currentDimensionElementGroups[2].description.push(obj.selectedCallTree[i].structureElementDescription);
                }
              }

            }, function() {

            });
          }

          function close() {
            $rootScope.$broadcast("CellPicker:close");
          }

          $scope.$watch("cellPickerLoader", function() {
            if ($scope.cellPickerLoader.accountStructureElementLoaded === true && $scope.cellPickerLoader.businessStructureElementLoaded === true && $scope.cellPickerLoader.calendarStructureElementLoaded === true) {
              $scope.cellPickerLoader.accountStructureElementLoaded = false;
              $scope.cellPickerLoader.businessStructureElementLoaded = false;
              $scope.cellPickerLoader.calendarStructureElementLoaded = false;
              cellPicker();
            }
          }, true);

          $scope.$watchCollection(
            // "This function returns the value being watched. It is called for each turn of the $digest loop"
            function() {
              return $scope.dataTypesForFinanceCube.collection
            },
            // "This is the change listener, called when the value returned from the above function changes"
            function(newValue, oldValue) {
              // This function is called when watcher is initialized too (when newValue and oldValue are two empty different objects).
              // To protect against this we check if newValue isn't empty.
              if (newValue !== oldValue && Object.keys(newValue).length != 0) {
                $scope.dataTypesForFinanceCube = $scope.dataTypesForFinanceCube;
                var positionLastElement = $scope.dataTypesForFinanceCube.collection.length - 1;
                if ($scope.dataTypesForFinanceCube.collection[positionLastElement].dataTypeId != -1) {
                  var contextual = {
                    dataTypeId: -1,
                    dataTypeVisId: "Contextual"
                  }
                  $scope.dataTypesForFinanceCube.collection.push(contextual);
                  $scope.chosenDataType = $scope.dataTypesForFinanceCube.collection[0];
                }
              }
            }
          );

          $scope.$watch('flatForm.xmlForm.worksheets.length', function(newValue, oldValue) {
            // only if we initialize properties or save ones
            if (firstLoading || newValue != oldValue) {
              firstLoading = false;
              $scope.originalFlatFormWorksheet = CoreCommonsService.findElementByKey($scope.flatForm.xmlForm.worksheets, $scope.currentSpreadSheet.name(), "name");
              $scope.currentFlatFormWorksheet = angular.copy($scope.originalFlatFormWorksheet);
              if ($scope.currentFlatFormWorksheet !== null) {
                $scope.financeCubes = DataService.getFinanceCubes();
                $scope.currentFinanceCube = CoreCommonsService.findElementByKey($scope.financeCubes, $scope.currentFlatFormWorksheet.properties.FINANCE_CUBE_VISID, "financeCubeVisId");
                if ($scope.currentFinanceCube != null) {
                  $scope.dataTypesForFinanceCube.collection = MappingService.getDataTypesForFinanceCube($scope.dataTypesForFinanceCube, $scope.currentFinanceCube.financeCubeId, $scope.currentFinanceCube.model.modelId);
                } else {
                  // swal("Error", "Please select Finance Cube in Settings in Worksheet Properties window.", "warning");


                  swal({
                    title: "Error",
                    text: "Please select Finance Cube in Settings in Worksheet Properties window.",
                    type: "warning",
                    // confirmButtonColor: "#5cb85c",
                  }, function(isConfirm) {
                    $scope.close();
                  });

                }
                if ($scope.currentFinanceCube != null) {
                  $scope.currentModelDimensions = DataService.getDimensionsForModel($scope.currentFinanceCube.model.modelId);
                }
                $scope.currentDimensionElementGroups = [];
                for (var i = 0; i < 3; i++) {
                  $scope.currentDimensionElementGroups[i] = {};
                  $scope.currentDimensionElementGroups[i].id = [];
                  $scope.currentDimensionElementGroups[i].visualId = [];
                  $scope.currentDimensionElementGroups[i].description = [];
                  $scope.currentDimensionElementGroups[i].group = [];
                  $scope.currentDimensionElementGroups[i].chosenOrentation = "Fixed";
                  $scope.currentDimensionElementGroups[i].chosenQuery = "None";
                }
              }
            }
          }, true);

        }
      ],
      link: function linkFunction(scope, element, attrs) {
        var spreadElement = angular.element(document.querySelector('#cell-picker-preview'));
//        var previewSpread = scope.previewSpread = spreadElement.wijspread("spread");
        var previewSpread = scope.previewSpread = new GC.Spread.Sheets.Workbook(document.getElementById('cell-picker-preview'));

        organizePreviewProperties(previewSpread);
        var delay;
        scope.redraw = function(height) {
          $timeout.cancel(delay);
          delay = $timeout(function() {
            $("#cell-picker-preview").height(height);
//            $("#cell-picker-preview").wijspread("refresh");
			var spread = GC.Spread.Sheets.findControl(document.getElementById('cell-picker-preview'));
			if(spread==undefined||spread==null)
				spread = new GC.Spread.Sheets.Workbook(document.getElementById("cell-picker-preview"));

			spread.refresh();

          }, 5);
        };

        scope.redraw(200);

        $("#cellPickerPreview").resizable({
          //containment: "parent",
          minHeight: 100,
          minWidth: 100,
          resize: function(event, ui) {
            scope.redraw($("#cellPickerPreview").height());
          },
        });

      }
    };

    function organizePreviewProperties(spread) {
      spread.suspendPaint ();
      spread.useWijmoTheme = true;
      for (var i = 0; i < spread.getSheetCount(); i++) {
        spread.removeSheet(i);
      }
      spread.addSheet(0, new GC.Spread.Sheets.Worksheet("PREVIEW"));

      spread.setActiveSheetIndex(0);
      spread.options.newTabVisible = false;
      spread.options.tabStripVisible = false;
      //            spread.showHorizontalScrollbar(false);
      //            spread.showVerticalScrollbar(false);
      spread.resumePaint();
      spread.options.canUserDragDrop = false;
      spread.options.canUserDragFill = false;

      var sheet = spread.getActiveSheet();
      sheet.isProtected = (true);
      sheet.setRowCount(200);
      sheet.setColumnCount(20);
      sheet.clearSelection();
      spread.resumePaint();
    }
  }
})();