// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.flatform.model.Worksheet;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class RemoveColumnsEdit extends AbstractUndoableEdit {

   private Worksheet mWorksheet;
   private int mColumnIndex;
   private int mColumnCount;


   public RemoveColumnsEdit(Worksheet worksheet, int columnIndex, int columnCount) {
      this.mWorksheet = worksheet;
      this.mColumnIndex = columnIndex;
      this.mColumnCount = columnCount;
   }

   public void undo() throws CannotUndoException {
      super.undo();
      this.mWorksheet.doInsertColumns(this.mColumnIndex, this.mColumnCount);
   }

   public void redo() throws CannotRedoException {
      super.redo();
      this.mWorksheet.doRemoveColumns(this.mColumnIndex, this.mColumnCount);
   }

   public String getPresentationName() {
      return "Remove columns";
   }

   public void die() {
      super.die();
      this.mWorksheet = null;
   }
}
