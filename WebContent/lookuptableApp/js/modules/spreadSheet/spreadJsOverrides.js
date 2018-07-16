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
// var keyword_null = null,
//     keyword_undefined,
//     Math_min = Math.min,
//     Math_max = Math.max,
//     Math_floor = Math.floor,
//     const_undefined = "undefined",
//     const_function = "function",
//     const_number = "number",
//     const_string = "string",
//     const_boolean = "boolean";

// var _tag = "tag";
// var _value = "value";

// $.wijmo.wijspread.UndoRedo.ClearRangeValueUndoAction.prototype.canExecute = function(arg) {
//     var sheet = this._sheet;
//     var range = this._clearRange;

//     // OUR CHANGES - not to block clearing ranges with locked cells
//     // return !(sheet.isProtected === true && sheet._isAnyCellInRangeLocked(range)) && sheet._checkArrayFormula(range.row, range.col, range.rowCount, range.colCount)
//     // END OUR CHANGES
    
//     return sheet._checkArrayFormula(range.row, range.col, range.rowCount, range.colCount);
// };

// $.wijmo.wijspread.UndoRedo.ClipboardPasteRangeUndoAction.prototype.canExecute = function(arg) {
//     var sheet = this._sheet;
//     var target = this._pasteExtent.targetRange;
//     var source = this._pasteExtent.sourceRange;

//     // OUR CHANGES - not to block clearing ranges with locked cells
//     // return !(sheet.isProtected && sheet._isAnyCellInRangeLocked(target)) && sheet._checkArrayFormula(target.row, target.col, target.rowCount, target.colCount) && (!this._pasteExtent.isCutting || !(sheet.isProtected && sheet._isAnyCellInRangeLocked(source)) && sheet._checkArrayFormula(source.row, source.col, source.rowCount, source.colCount))
//     // END OUR CHANGES
    
//     return sheet._checkArrayFormula(target.row, target.col, target.rowCount, target.colCount) && (!this._pasteExtent.isCutting || sheet._checkArrayFormula(source.row, source.col, source.rowCount, source.colCount))
// };

// // var copyToOriginal = $.wijmo.wijspread.staticMembers.copyTo;
// // $.wijmo.wijspread.staticMembers.copyTo = function(src, srcRow, srcColumn, dest, destRow, destColumn, copyRowCount, copyColumnCount, option, ignoreFilteredOutRow) {
// //     console.log(dest.getCell(destRow, destColumn).locked());
// //     //OUR CHANGES - if value is mapping (depends also on view mode), function sets workbook cell and tag in spread cell
// //     // var spread = $.wijmo.wijspread;
// //     // var cell = sheet.getCell(rowIndex, columnIndex);
// //     // value = angular.element(document.getElementById('mappings')).scope().saveMappingByCellEditing(cell, value);
// //     // END OUR CHANGES 
// //     copyToOriginal(src, srcRow, srcColumn, dest, destRow, destColumn, copyRowCount, copyColumnCount, option, ignoreFilteredOutRow);
// // };

// $.wijmo.wijspread.Sheet.prototype._setValueInternal = function(row, col, value, sheetArea, ignoreRecalc) {
//     // console.log("_setValueInternal", value);
//     // OUR CHANGES
//     var spread = $.wijmo.wijspread;
//     var staticMembers = $.wijmo.wijspread.staticMembers;
//     // END OUR CHANGES
    
//     if (typeof(sheetArea) === const_undefined || sheetArea === keyword_null) {
//         sheetArea = 3
//     }
//     var self = this;

//     // OUR CHANGES
//     // var currencyService = angular.element('body').injector().get('CurrencyService');
//     // currencyService.saveTest(row, col, value);
//     var tag = self.getCell(row, col).tag();
//     if (tag == null || tag.isEditable === false) {
//         return;
//     }
//     // END OUR CHANGES
     
//     var m = self._getModel(sheetArea);
//     if (!m) {
//         return
//     }
//     var rowCount = m.getRowCount(),
//         colCount = m.getColumnCount();
//     if (row < 0 || row >= rowCount || col < 0 || col >= colCount) {
//         return
//     }
//     var isViewport = (sheetArea === 3);
//     // OUR CHANGES - Set default date format
//     if (typeof(value) !== const_undefined && value !== keyword_null && value.constructor === Date && (self.getCell(row, col).formatter() == undefined || self.getCell(row, col).formatter() == "General")) {
//         self.getCell(row, col).formatter("d-mmm-yy;;")
//     }
//     // END OUR CHANGES
//     value = staticMembers.tryConvertDateToOADate(value);
//     var oldValue = self._getValueImp(m, row, col, sheetArea);
//     var valueSet = false;
//     var tm = self._tableManager;
//     if (isViewport && tm && tm._tableList.length > 0) {
//         var table = tm.find(row, col);
//         if (table) {
//             if (table.showHeader() && row === table.headerIndex()) {
//                 if (table._hasColumnName(value)) {
//                     return
//                 }
//                 table._setHeaderName(col, value)
//             } else if (table.showFooter() && row === table.footerIndex()) {
//                 table._setFooterValue(col, value)
//             } else {
//                 valueSet = table._setValue(row, col, value)
//             }
//         }
//     }
//     if (isViewport) {
//         var bm = self._bindingManager;
//         if (!valueSet && bm && bm._dataSource) {
//             if (value !== bm.getValue(row, col)) {
//                 var oldItem = $.extend({}, self.getDataItem(row));
//                 m._updateDirty(row, col, {
//                     originalItem: oldItem,
//                     oldValue: oldValue
//                 })
//             }
//             valueSet = bm.setValue(row, col, value)
//         }
//         if (!valueSet || self.checkingChanges) {
//             m.setValue(row, col, value);
//             self._activeRowDirty = true
//         }
//     } else {
//         m.setValue(row, col, value)
//     }
//     if (isViewport && !ignoreRecalc) {
//         self._recalcCell(m, row, col)
//     }
//     var conditionalFormats = self._conditionalFormats;
//     if (conditionalFormats) {
//         conditionalFormats._clearCache()
//     }
//     var isEventsSuspended = (self._eventHandler._eventSuspended > 0);
//     if (!isEventsSuspended && oldValue !== value) {
//         self._trigger(spread.Events.CellChanged, {
//             sheet: self,
//             sheetName: self._name,
//             row: row,
//             col: col,
//             sheetArea: sheetArea,
//             propertyName: _value,
//             _oldValue: oldValue
//         })
//     }
// };

// $.wijmo.wijspread.Sheet.prototype.setStyleObject = function(row, col, value, sheetArea) {
//     // OUR CHANGES
//     var spread = $.wijmo.wijspread;
//     var staticMembers = $.wijmo.wijspread.staticMembers;
//     // END OUR CHANGES

//     if (typeof(sheetArea) === const_undefined || sheetArea === keyword_null) {
//         sheetArea = 3
//     }
//     var self = this;

//     // OUR CHANGES
//     var tag = self.getCell(row, col).tag();
//     if (tag !== null && tag.isEditable === true) {
//         return true;
//     }
//     var spread = $.wijmo.wijspread;
//     var staticMembers = $.wijmo.wijspread.staticMembers;
//     // END OUR CHANGES
//     var m = self._getModel(sheetArea);
//     if (m) {
//         var rowCount = m.getRowCount(),
//             colCount = m.getColumnCount();
//         if (row < -1 || row >= rowCount || col < -1 || col >= colCount) {
//             return
//         }
//         m.setStyle(row, col, value);
//         spread._DataValidatorCache.setStyle(value, self, row, col);
//         if (value && value.formatter && spread.features.formatter) {
//             var formatter = value.formatter;
//             if (formatter && typeof(formatter.Init) === const_function) {
//                 formatter.Init()
//             }
//         }
//         var pn = "[styleinfo]";
//         if (row !== -1 && col !== -1) {
//             self._trigger(spread.Events.CellChanged, {
//                 sheet: self,
//                 sheetName: self._name,
//                 row: row,
//                 col: col,
//                 sheetArea: sheetArea,
//                 propertyName: pn
//             })
//         } else if (row !== -1 && col === -1) {
//             self._trigger(spread.Events.RowChanged, {
//                 sheet: self,
//                 sheetName: self._name,
//                 row: row,
//                 sheetArea: sheetArea,
//                 propertyName: pn
//             })
//         } else if (row === -1 && col !== -1) {
//             self._trigger(spread.Events.ColumnChanged, {
//                 sheet: self,
//                 sheetName: self._name,
//                 col: col,
//                 sheetArea: sheetArea,
//                 propertyName: pn
//             })
//         }
//     }
// };

// // var setCellDataOriginal = $.wijmo.wijspread.staticMembers.setCellData;
// // $.wijmo.wijspread.staticMembers.setCellData = function(sheet, area, rowIndex, columnIndex, value, opt) {
// //     console.log("setCellData");
// //     //OUR CHANGES - if value is mapping (depends also on view mode), function sets workbook cell and tag in spread cell
// //     // var spread = $.wijmo.wijspread;
// //     // var cell = sheet.getCell(rowIndex, columnIndex);
// //     // value = angular.element(document.getElementById('mappings')).scope().saveMappingByCellEditing(cell, value);
// //     // END OUR CHANGES 

// //     setCellDataOriginal(sheet, area, rowIndex, columnIndex, value, opt);
// // };

// // var setEditorValueOriginal = $.wijmo.wijspread.TextCellType.prototype.setEditorValue;
// // $.wijmo.wijspread.TextCellType.prototype.setEditorValue = function(editorContext, value, context) {
// //     console.log("setEditorValueOriginal");

// //     // var viewModeService = angular.element('body').injector().get('ViewModeService');
// //     // var scope = angular.element(document.getElementById('mappings')).scope();
// //     // var cellMode = viewModeService.getCurrentViewMode().name;

// //     // var sheet = context.sheet;
// //     // var cell = sheet.getCell(context.row, context.col, $.wijmo.wijspread.SheetArea.viewport, true);
// //     // if (cellMode == "VALUE") {
// //     //     if (cell.formula()) {
// //     //         value = '=' + cell.formula();
// //     //     } else {
// //     //         value = cell.value();
// //     //     }
// //     // } else {
// //     //     if (cellMode == "INPUT") {
// //     //         value = scope.currentMapping.inputMapping;
// //     //     } else if (cellMode == "OUTPUT") {
// //     //         value = scope.currentMapping.outputMapping;
// //     //     } else if (cellMode == "FORMULA") {
// //     //         value = scope.currentMapping.formulaMapping;
// //     //     } else {
// //     //         value = "";
// //     //     }
// //     // }
// //     setEditorValueOriginal(editorContext, value, context);
// // };