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
var keyword_null = null,
    keyword_undefined,
    Math_min = Math.min,
    Math_max = Math.max,
    Math_floor = Math.floor,
    const_undefined = "undefined",
    const_function = "function",
    const_number = "number",
    const_string = "string",
    const_boolean = "boolean";

var _tag = "tag";
var _value = "value";

$.wijmo.wijspread.Sheet.prototype.setActiveCell = function(row, col, rowViewportIndex, colViewportIndex) {
    var spread = $.wijmo.wijspread;
    this._setActiveCellAndSelection(row, col, rowViewportIndex, colViewportIndex, 2);
    this._trigger(spread.Events.SelectionChanged, {
        sheet: this,
        sheetName: this._name
    });
};

$.wijmo.wijspread.Sheet.prototype.setTag = function(row, col, tag, sheetArea) {
    // OUR CHANGES
    var spreadCell = this.getCell(row, col);
    var mappingService = angular.element('body').injector().get('MappingService');
    tag = mappingService.updateTag(spreadCell, tag);
    var spread = $.wijmo.wijspread;
    var staticMembers = $.wijmo.wijspread.staticMembers;
    // END OUR CHANGES
    if (typeof(sheetArea) === const_undefined || sheetArea === keyword_null) {
        sheetArea = 3
    }
    var self = this;
    var m = self._getModel(sheetArea);
    if (m) {
        var rowCount = m.getRowCount(),
            colCount = m.getColumnCount();
        if (row < -1 || row >= rowCount || col < -1 || col >= colCount) {
            return
        }
        tag = staticMembers.tryConvertDateToOADate(tag);
        var oldTag,
            isEventsSuspended = (self._eventHandler._eventSuspended > 0);
        if (!isEventsSuspended) {
            oldTag = m.getTag(row, col)
        }
        m.setTag(row, col, tag);
        if (!isEventsSuspended && oldTag !== tag) {
            if (row !== -1 && col !== -1) {
                self._trigger(spread.Events.CellChanged, {
                    sheet: self,
                    sheetName: self._name,
                    row: row,
                    col: col,
                    sheetArea: sheetArea,
                    propertyName: _tag,
                    oldTag: oldTag
                })
            } else if (row !== -1 && col === -1) {
                self._trigger(spread.Events.RowChanged, {
                    sheet: self,
                    sheetName: self._name,
                    row: row,
                    sheetArea: sheetArea,
                    propertyName: _tag,
                    oldTag: oldTag
                })
            } else if (row === -1 && col !== -1) {
                self._trigger(spread.Events.ColumnChanged, {
                    sheet: self,
                    sheetName: self._name,
                    col: col,
                    sheetArea: sheetArea,
                    propertyName: _tag,
                    oldTag: oldTag
                })
            }
        }
        // OUR CHANGES - updates cell type for edited cell
        var cellService = angular.element('body').injector().get('CellService');
        cellService.setUpCellType(spreadCell, tag);
        // END OUR CHANGES
    }
};

$.wijmo.wijspread.Sheet.prototype.setStyleObject = function(row, col, value, sheetArea) {
    // OUR CHANGES - TODO is it necessary all these if's
    var viewModeService = angular.element('body').injector().get('ViewModeService');
    var cellService = angular.element('body').injector().get('CellService');
    var cellMode = viewModeService.getCurrentViewMode().name;

    var oldStyle = this.getStyle(row, col);
    if (cellMode !== "LOADING") {
        var tag = this.getCell(row, col).tag();
        if (oldStyle) {
            //oldStyle = angular.copy(oldStyle);
            if (cellMode == "VALUE") {
                if (value === null) {
                    value = new $.wijmo.wijspread.Style();
                }
                if (tag) {
                    value.cellType = cellService.buildIOCellType();
                } else {
                    value.cellType = undefined;
                }
            } else if (cellMode == "INPUT" || cellMode == "OUTPUT" || cellMode == "FORMULA") {
                if (value) {
                    oldStyle.cellType = value.cellType;
                } else {
                    oldStyle.cellType = undefined;
                }
                value = oldStyle;
            }
        } else {
            if (tag) {
                value.cellType = cellService.buildIOCellType();
            } else if (value != null) {
                value.cellType = undefined;
            }
        }
    }
    //console.log("setStyleObject", value)
    var spread = $.wijmo.wijspread;
    var staticMembers = $.wijmo.wijspread.staticMembers;
    // END OUR CHANGES

    if (typeof(sheetArea) === const_undefined || sheetArea === keyword_null) {
        sheetArea = 3
    }
    var self = this;
    var m = self._getModel(sheetArea);
    if (m) {
        var rowCount = m.getRowCount(),
            colCount = m.getColumnCount();
        if (row < -1 || row >= rowCount || col < -1 || col >= colCount) {
            return
        }
        m.setStyle(row, col, value);
        spread._DataValidatorCache.setStyle(value, self, row, col);
        if (value && value.formatter && spread.features.formatter) {
            var formatter = value.formatter;
            if (formatter && typeof(formatter.Init) === const_function) {
                formatter.Init()
            }
        }
        var pn = "[styleinfo]";
        if (row !== -1 && col !== -1) {
            self._trigger(spread.Events.CellChanged, {
                sheet: self,
                sheetName: self._name,
                row: row,
                col: col,
                sheetArea: sheetArea,
                propertyName: pn
            })
        } else if (row !== -1 && col === -1) {
            self._trigger(spread.Events.RowChanged, {
                sheet: self,
                sheetName: self._name,
                row: row,
                sheetArea: sheetArea,
                propertyName: pn
            })
        } else if (row === -1 && col !== -1) {
            self._trigger(spread.Events.ColumnChanged, {
                sheet: self,
                sheetName: self._name,
                col: col,
                sheetArea: sheetArea,
                propertyName: pn
            })
        }
    }
};

$.wijmo.wijspread.Sheet.prototype._setValueInternal = function(row, col, value, sheetArea, ignoreRecalc) {
    // OUR CHANGES - set value depends on view mode. if view mode equals "VALUE", value of cell is overidden;
    var viewModeService = angular.element('body').injector().get('ViewModeService');
    var mappingService = angular.element('body').injector().get('MappingService');
    var cellMode = viewModeService.getCurrentViewMode().name;
    if (cellMode !== "VALUE" && cellMode !== "TEST" && mappingService.override == false) {
        return;
    }
    var spread = $.wijmo.wijspread;
    var staticMembers = $.wijmo.wijspread.staticMembers;
    // END OUR CHANGES

    if (typeof(sheetArea) === const_undefined || sheetArea === keyword_null) {
        sheetArea = 3
    }
    var self = this;
    var m = self._getModel(sheetArea);
    if (!m) {
        return
    }
    var rowCount = m.getRowCount(),
        colCount = m.getColumnCount();
    if (row < 0 || row >= rowCount || col < 0 || col >= colCount) {
        return
    }
    var isViewport = (sheetArea === 3);
    // OUR CHANGES - Set default date format
    if (typeof(value) !== const_undefined && value !== keyword_null && value.constructor === Date && (self.getCell(row, col).formatter() == undefined || self.getCell(row, col).formatter() == "General")) {
        self.getCell(row, col).formatter("d-mmm-yy;;")
    }
    // END OUR CHANGES
    value = staticMembers.tryConvertDateToOADate(value);
    var oldValue = self._getValueImp(m, row, col, sheetArea);
    var valueSet = false;
    var tm = self._tableManager;
    if (isViewport && tm && tm._tableList.length > 0) {
        var table = tm.find(row, col);
        if (table) {
            if (table.showHeader() && row === table.headerIndex()) {
                if (table._hasColumnName(value)) {
                    return
                }
                table._setHeaderName(col, value)
            } else if (table.showFooter() && row === table.footerIndex()) {
                table._setFooterValue(col, value)
            } else {
                valueSet = table._setValue(row, col, value)
            }
        }
    }
    if (isViewport) {
        var bm = self._bindingManager;
        if (!valueSet && bm && bm._dataSource) {
            if (value !== bm.getValue(row, col)) {
                var oldItem = $.extend({}, self.getDataItem(row));
                m._updateDirty(row, col, {
                    originalItem: oldItem,
                    oldValue: oldValue
                })
            }
            valueSet = bm.setValue(row, col, value)
        }
        if (!valueSet || self.checkingChanges) {
            m.setValue(row, col, value);
            self._activeRowDirty = true
        }
    } else {
        m.setValue(row, col, value)
    }
    if (isViewport && !ignoreRecalc) {
        self._recalcCell(m, row, col)
    }
    var conditionalFormats = self._conditionalFormats;
    if (conditionalFormats) {
        conditionalFormats._clearCache()
    }
    var isEventsSuspended = (self._eventHandler._eventSuspended > 0);
    if (!isEventsSuspended && oldValue !== value) {
        self._trigger(spread.Events.CellChanged, {
            sheet: self,
            sheetName: self._name,
            row: row,
            col: col,
            sheetArea: sheetArea,
            propertyName: _value,
            _oldValue: oldValue
        })
    }
};

var setCellDataOriginal = $.wijmo.wijspread.staticMembers.setCellData;
$.wijmo.wijspread.staticMembers.setCellData = function(sheet, area, rowIndex, columnIndex, value, opt) {
    //OUR CHANGES - if value is mapping (depends also on view mode), function sets workbook cell and tag in spread cell
    var spread = $.wijmo.wijspread;
    var cell = sheet.getCell(rowIndex, columnIndex);
    value = angular.element(document.getElementById('mappings')).scope().saveMappingByCellEditing(cell, value);
    // END OUR CHANGES 

    setCellDataOriginal(sheet, area, rowIndex, columnIndex, value, opt);
};

var setEditorValueOriginal = $.wijmo.wijspread.TextCellType.prototype.setEditorValue;
$.wijmo.wijspread.TextCellType.prototype.setEditorValue = function(editorContext, value, context) {
    var viewModeService = angular.element('body').injector().get('ViewModeService');
    var scope = angular.element(document.getElementById('mappings')).scope();
    var cellMode = viewModeService.getCurrentViewMode().name;

    var sheet = context.sheet;
    var cell = sheet.getCell(context.row, context.col, $.wijmo.wijspread.SheetArea.viewport, true);
    if (cellMode == "VALUE") {
        if (cell.formula()) {
            value = '=' + cell.formula();
        } else {
            value = cell.value();
        }
    } else {
        if (cellMode == "INPUT") {
            value = scope.currentMapping.inputMapping;
        } else if (cellMode == "OUTPUT") {
            value = scope.currentMapping.outputMapping;
        } else if (cellMode == "FORMULA") {
            value = scope.currentMapping.formulaMapping;
        } else {
            value = "";
        }
    }
    setEditorValueOriginal(editorContext, value, context);
};

$.wijmo.wijspread.Sheet.prototype._endEditImp = function(ignoreValueChange, endEditType) {
    var spread = $.wijmo.wijspread;

    var self = this,
        eventHandler = self._eventHandler;
    if (!self.isEditing())
        return true;
    var editor = self._editor;
    var action;
    var ct = self.getCellType(self._activeRowIndex, self._activeColIndex);
    var context = {
        sheet: self,
        row: self._activeRowIndex,
        col: self._activeColIndex,
        sheetArea: 3
    };
    if (!ct.isImeAware(context) && eventHandler) {
        eventHandler.changeFocusHolder()
    }
    if (editor && editor.parentNode) {
        var v = ct.getEditorValue(editor, context);
        //OUR CHANGES
        var spreadCell = self.getCell(self._activeRowIndex, self._activeColIndex);
        v = angular.element(document.getElementById('mappings')).scope().saveMappingByCellEditing(spreadCell, v);
        //END CHANGES

        var args = {
            sheet: self,
            sheetName: self._name,
            row: self._activeRowIndex,
            col: self._activeColIndex,
            editingText: v,
            cancel: false
        };
        self._trigger(spread.Events.EditEnd, args);
        if (args && args.cancel === true) {
            return
        }
        args["editor"] = editor;
        self._trigger(spread.Events.EditEnding, args);
        if (args && args.cancel === true) {
            return
        }
        self._detachFormulaTextBox();
        var attachedFbx = this.parent && this.parent._attachedFormulaTextBox;
        if (document.activeElement === (attachedFbx && attachedFbx._host)) {
            eventHandler.changeFocusHolder()
        }
        if (self._activeRowIndex >= 0 && self._activeColIndex >= 0) {
            var oldValue = editor._oldValue;
            if (ct.isEditingValueChanged(oldValue, v, context) || endEditType === 1) {
                if (ignoreValueChange) {
                    ct.setEditorValue(editor, oldValue, context)
                } else {
                    var autoFormat = (ct.hasOwnProperty("_autoFormatValue") ? ct._autoFormatValue : true);
                    var cellEditInfo = {
                        row: self._activeRowIndex,
                        col: self._activeColIndex,
                        newValue: v,
                        autoFormat: autoFormat
                    };
                    if (endEditType === 1) {
                        var selections = [self._getActiveSelectedRange()];
                        cellEditInfo.ranges = selections;
                        cellEditInfo.endEditType = endEditType
                    }
                    action = new spread.UndoRedo.CellEditUndoAction(self, cellEditInfo);
                    self._doCommand(action);
                    var needRetry = (action.applyResult === 2);
                    if (needRetry === true) {
                        ct.focus(editor, context);
                        self._attachFormulaTextBox(ct.getEditingElement());
                        return false
                    }
                }
                try {
                    ct.selectAll(editor, context)
                } catch (ex) {}
            }
        }
        self._dirty = true;
        ct.deactivateEditor(editor, context);
        if (!ct.isImeAware(context)) {
            var element = editor;
            while (element) {
                if (element.parentNode === document.body) {
                    document.body.removeChild(element)
                } else {
                    element = element.parentNode
                }
            }
        } else {
            $(editor).css("width", "0");
            $(editor).css("height", "0");
            $(editor).css("overflow", "hidden")
        }
    }
    self._editingTimeValue = false;
    self._trigger(spread.Events.EditEnded, {
        sheet: self,
        sheetName: self._name,
        row: self._activeRowIndex,
        col: self._activeColIndex,
        editingText: v
    });
    if (self._editorStatus !== 0) {
        var oldStatus = self._editorStatus;
        self._editorStatus = 0;
        self._trigger(spread.Events.EditorStatusChanged, {
            sheet: self,
            sheetName: self._name,
            oldStatus: oldStatus,
            newStatus: 0
        })
    }
    self._editor = keyword_null;
    if (action && (action.applyResult === 1)) {
        return false
    }
    return true
};

$.wijmo.wijspread.Sheet.prototype._attachFormulaTextBox = function(editingElement) {
    var spread = $.wijmo.wijspread;

    var self = this;
    if (!spread.features.formulatextbox || !editingElement || (self.parent && !self.parent.canUserEditFormula()) || !self._enableFormulaTextbox) {
        return
    }
    if (self._formulaTextBox) {
        self._formulaTextBox.destroy()
    }
    self._formulaTextBox = new spread.FormulaTextBox(editingElement);
    self._formulaTextBox.setSpread(self.parent);
    self._formulaTextBox.isInSpread(true);
    var text = self._formulaTextBox.text();
    self._formulaTextBox.text("");
    var formulaInfo = self.getFormulaInformation(self._activeRowIndex, self._activeColIndex);
    if (!formulaInfo || !formulaInfo.hasFormula) {
        if (text[0] === "=") {
            //OUR CHANGES
            //text = "'" + text
            //END CHANGES
        }
    }
    self._formulaTextBox.text(text);
    var fbx = self._formulaTextBox,
        eventHandler = self._eventHandler,
        render = self._render;
    fbx.bind("AppendStarted", function(e) {
        fbx.close();
        var EditorStatus = $.wijmo.wijspread.EditorStatus,
            oldStatus = self._editorStatus;
        if (oldStatus !== EditorStatus.Enter) {
            self._editorStatus = EditorStatus.Enter;
            self._trigger("EditorStatusChanged", {
                sheet: self,
                sheetName: self._name,
                oldStatus: oldStatus,
                newStatus: EditorStatus.Enter
            })
        }
    });
    fbx.bind("AppendEnded", function(e) {
        render.paintFormulaTextBox()
    });
    fbx.bind("TextChanged", function(e, eData) {
        if (eData && eData.type !== "input") {
            eventHandler.updateEditingEditor();
            var activeRow = self._activeRowIndex,
                activeCol = self._activeColIndex,
                ct = self.getCellType(activeRow, activeCol);
            self._trigger(spread.Events.EditChange, {
                sheet: self,
                sheetName: self._name,
                row: activeRow,
                col: activeCol,
                editingText: ct.getEditorValue(self._editor)
            })
        }
        render.paintFormulaTextBox();
        self._trigger("FormulaTextBoxTextChanged", {
            sheet: self,
            sheetName: self._name,
            text: fbx.text()
        })
    });
    fbx.bind("CaretChanged", function() {
        render.paintFormulaTextBox();
        self._trigger("FormulaTextBoxCaretChanged", {
            sheet: self,
            sheetName: self._name,
            caret: fbx.caret()
        })
    });
    fbx.add(spread.SR.getHelpFuncs());
    var fnds = this._functionDescriptions;
    if (fnds && fnds.length > 0) {
        fbx.add(fnds)
    }
    fnds = (this.parent && this.parent._functionDescriptions);
    if (fnds && fnds.length > 0) {
        fbx.add(fnds)
    }
    render.paintFormulaTextBox()
};