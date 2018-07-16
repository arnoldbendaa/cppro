// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.flatform.model.Worksheet;
import java.text.ParseException;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class CellEdit extends AbstractUndoableEdit {

   private Worksheet mWorksheet;
   private int mViewLayer;
   private int mRow;
   private int mColumn;
   private String mOldText;
   private String mNewText;


   public CellEdit(Worksheet worksheet, int viewLayer, int row, int column, String oldText, String newText) {
      this.mWorksheet = worksheet;
      this.mViewLayer = viewLayer;
      this.mRow = row;
      this.mColumn = column;
      this.mOldText = oldText;
      this.mNewText = newText;
   }

   public void undo() throws CannotUndoException {
      super.undo();

      try {
         int e = this.mWorksheet.getViewLayer();
         if(e != this.mViewLayer) {
            this.mWorksheet.setViewLayer(this.mViewLayer);
         }

         this.mWorksheet.doSetCellValue(this.mOldText, this.mRow, this.mColumn);
         if(e != this.mWorksheet.getViewLayer()) {
            this.mWorksheet.setViewLayer(e);
         }

      } catch (ParseException var2) {
         throw new CannotUndoException();
      }
   }

   public void redo() throws CannotRedoException {
      super.redo();

      try {
         int e = this.mWorksheet.getViewLayer();
         if(e != this.mViewLayer) {
            this.mWorksheet.setViewLayer(this.mViewLayer);
         }

         this.mWorksheet.doSetCellValue(this.mNewText, this.mRow, this.mColumn);
         if(e != this.mWorksheet.getViewLayer()) {
            this.mWorksheet.setViewLayer(e);
         }

      } catch (ParseException var2) {
         throw new CannotUndoException();
      }
   }

   public String getPresentationName() {
      return "Cell edit";
   }

   public void die() {
      super.die();
      this.mWorksheet = null;
      this.mOldText = null;
      this.mNewText = null;
   }

   public String toString() {
      if(this.mWorksheet == null) {
         return "CellEdit is dead";
      } else {
         String cellAddr = this.mWorksheet.getColumnName(this.mColumn) + (this.mRow + 1);
         return "CellEdit[" + cellAddr + ",oldText=\'" + this.mOldText + "\',newText=\'" + this.mNewText + "\']";
      }
   }
   
   public String getOldText() {
	   return this.mOldText;
   }
   public String getNewText() {
	   return this.mNewText;
   }
   public Worksheet getSheet() {
	   return this.mWorksheet;
   }
   public int getRow() {
	   return this.mRow;
   }
   public int getCol() {
	   return this.mColumn;
   }
}
