// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.flatform.model.Worksheet;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class RemoveRowsEdit extends AbstractUndoableEdit {

   private Worksheet mWorksheet;
   private int mRowIndex;
   private int mRowCount;


   public RemoveRowsEdit(Worksheet worksheet, int rowIndex, int rowCount) {
      this.mWorksheet = worksheet;
      this.mRowIndex = rowIndex;
      this.mRowCount = rowCount;
   }

   public void undo() throws CannotUndoException {
      super.undo();
      this.mWorksheet.doInsertRows(this.mRowIndex, this.mRowCount);
   }

   public void redo() throws CannotRedoException {
      super.redo();
      this.mWorksheet.doRemoveRows(this.mRowIndex, this.mRowCount);
   }

   public String getPresentationName() {
      return "Insert rows";
   }

   public void die() {
      super.die();
      this.mWorksheet = null;
   }
}
