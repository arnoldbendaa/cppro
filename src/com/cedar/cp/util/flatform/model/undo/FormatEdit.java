// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.util.Collection;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class FormatEdit extends AbstractUndoableEdit {

   private Worksheet mWorksheet;
   private RTree.Rect mRect;
   private Collection<FormatProperty> mFormatProprties;
   private int mVersionNumber;


   public FormatEdit(Worksheet worksheet, RTree.Rect rect, Collection<FormatProperty> formatProprties, int versionNumber) {
      this.mWorksheet = worksheet;
      this.mRect = rect;
      this.mFormatProprties = formatProprties;
      this.mVersionNumber = versionNumber;
   }

   public void undo() throws CannotUndoException {
      super.undo();
      this.mWorksheet.doRemoveCellFormat(this.mRect, this.mVersionNumber);
   }

   public void redo() throws CannotRedoException {
      super.redo();
      this.mWorksheet.doSetCellFormat(this.mRect.mStartRow, this.mRect.mStartColumn, this.mRect.mEndRow, this.mRect.mEndColumn, this.mFormatProprties);
      this.mVersionNumber = this.mWorksheet.getLastCellFormatVersion();
   }

   public String getPresentationName() {
      return "Cell Format";
   }

   public void die() {
      super.die();
      this.mWorksheet = null;
      this.mRect = null;
      this.mFormatProprties.clear();
      this.mFormatProprties = null;
   }
}
