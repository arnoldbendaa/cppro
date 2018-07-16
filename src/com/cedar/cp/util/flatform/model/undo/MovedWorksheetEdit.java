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

public class MovedWorksheetEdit extends AbstractUndoableEdit {

   private Workbook mWorkbook;
   private Worksheet mWorksheet;
   private int mOldIndex;
   private int mNewIndex;


   public MovedWorksheetEdit(Workbook workbook, Worksheet worksheet, int oldIndex, int newIndex) {
      this.mWorkbook = workbook;
      this.mWorksheet = worksheet;
      this.mOldIndex = oldIndex;
      this.mNewIndex = newIndex;
   }

   public void die() {
      super.die();
      this.mWorkbook = null;
      this.mWorksheet = null;
   }

   public void redo() throws CannotRedoException {
      super.redo();
      if(!this.mWorkbook.doMoveWorksheet(this.mWorksheet, this.mNewIndex)) {
         throw new CannotRedoException();
      }
   }

   public void undo() throws CannotUndoException {
      super.undo();
      if(!this.mWorkbook.doMoveWorksheet(this.mWorksheet, this.mOldIndex)) {
         throw new CannotRedoException();
      }
   }

   public String getPresentationName() {
      return "Move worksheet";
   }
}
