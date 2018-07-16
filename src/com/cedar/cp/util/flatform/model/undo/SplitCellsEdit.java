// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.flatform.model.Worksheet;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class SplitCellsEdit extends AbstractUndoableEdit {

   private Worksheet mWorksheet;
   private Collection<RTree.Rect> mSplitRects;


   public SplitCellsEdit(Worksheet worksheet, Collection<RTree.Rect> splitRects) {
      this.mWorksheet = worksheet;
      this.mSplitRects = splitRects;
   }

   public void undo() throws CannotUndoException {
      super.undo();
      Iterator i$ = this.mSplitRects.iterator();

      while(i$.hasNext()) {
         RTree.Rect splitRect = (RTree.Rect)i$.next();
         this.mWorksheet.doMergeCells(splitRect);
      }

   }

   public void redo() throws CannotRedoException {
      super.redo();
      Iterator i$ = this.mSplitRects.iterator();

      while(i$.hasNext()) {
         RTree.Rect splitRect = (RTree.Rect)i$.next();
         this.mWorksheet.doSplitCells(splitRect);
      }

   }

   public String getPresentationName() {
      return "Split Cells";
   }

   public void die() {
      super.die();
      this.mWorksheet = null;
   }
}
