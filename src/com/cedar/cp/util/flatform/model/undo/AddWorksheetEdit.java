// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class AddWorksheetEdit extends AbstractUndoableEdit {

   private Workbook mWorkbook;
   private Worksheet mWorksheet;


   public AddWorksheetEdit(Workbook workbook, Worksheet worksheet) {
      this.mWorkbook = workbook;
      this.mWorksheet = worksheet;
   }

   public void die() {
      super.die();
      this.mWorkbook = null;
      this.mWorksheet = null;
   }

   public void redo() throws CannotRedoException {
      super.redo();
      this.mWorkbook.doAddWorksheet(this.mWorksheet);
   }

   public void undo() throws CannotUndoException {
      super.undo();
      this.mWorkbook.doRemoveWorksheet(this.mWorksheet.getName());
   }

   public String getPresentationName() {
      return "Add worksheet";
   }
}
