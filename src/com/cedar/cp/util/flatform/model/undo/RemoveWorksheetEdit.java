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

public class RemoveWorksheetEdit extends AbstractUndoableEdit {

   private Workbook mWorkbook;
   private String mWorksheetName;
   private Worksheet mWorksheet;


   public RemoveWorksheetEdit(Workbook workbook, Worksheet worksheet, String worksheetName) {
      this.mWorkbook = workbook;
      this.mWorksheet = worksheet;
      this.mWorksheetName = worksheetName;
   }

   public void die() {
      super.die();
      this.mWorkbook = null;
      this.mWorksheet = null;
      this.mWorksheetName = null;
   }

   public void redo() throws CannotRedoException {
      super.redo();
      this.mWorksheet = this.mWorkbook.getWorksheet(this.mWorksheetName);
      if(this.mWorksheet == null) {
         throw new CannotRedoException();
      } else {
         this.mWorkbook.doRemoveWorksheet(this.mWorksheetName);
      }
   }

   public void undo() throws CannotUndoException {
      super.undo();
      if(this.mWorksheet == null) {
         throw new CannotUndoException();
      } else {
         this.mWorkbook.doAddWorksheet(this.mWorksheet);
      }
   }

   public String getPresentationName() {
      return "Remove worksheet";
   }
}
