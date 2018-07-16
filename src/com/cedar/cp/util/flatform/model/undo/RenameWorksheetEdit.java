// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.flatform.model.Workbook;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class RenameWorksheetEdit extends AbstractUndoableEdit {

   private Workbook mWorkbook;
   private String mNewName;
   private String mOldName;
   private boolean mNewHidden;
   private boolean mOldHidden;


   public RenameWorksheetEdit(Workbook workbook, String oldName, String newName) {
      this.mWorkbook = workbook;
      this.mOldName = oldName;
      this.mNewName = newName;
   }

   public RenameWorksheetEdit(Workbook workbook, String oldName, String newName, boolean oldHidden, boolean newHidden) {
      this.mWorkbook = workbook;
      this.mOldName = oldName;
      this.mNewName = newName;
      this.mOldHidden = oldHidden;
      this.mNewHidden = newHidden;
   }

   public void die() {
      super.die();
      this.mWorkbook = null;
      this.mNewName = null;
      this.mOldName = null;
      this.mOldHidden = false;
      this.mNewHidden = false;
   }

   public void redo() throws CannotRedoException {
      super.redo();
      this.mWorkbook.doRenameWorksheet(this.mOldName, this.mNewName, this.mOldHidden, this.mNewHidden);
   }

   public void undo() throws CannotUndoException {
      super.undo();
      this.mWorkbook.doRenameWorksheet(this.mNewName, this.mOldName, this.mNewHidden, this.mOldHidden);
   }

   public String getPresentationName() {
      return "Rename worksheet";
   }
}
