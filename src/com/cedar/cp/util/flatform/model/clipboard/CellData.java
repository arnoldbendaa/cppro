// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.clipboard;

import com.cedar.cp.util.flatform.model.Cell;

public class CellData {

   private int mRow;
   private int mColumn;
   private String mText;
   private String mInputMapping;
   private String mOutputMapping;


   public CellData(Cell cell) {
      this.mRow = cell.getRow();
      this.mColumn = cell.getColumn();
      this.mText = cell.getText();
      this.mOutputMapping = cell.getOutputMapping();
      this.mInputMapping = cell.getInputMapping();
   }

   public int getRow() {
      return this.mRow;
   }

   public int getColumn() {
      return this.mColumn;
   }

   public String getText() {
      return this.mText;
   }

   public String getInputMapping() {
      return this.mInputMapping;
   }

   public String getOutputMapping() {
      return this.mOutputMapping;
   }
}
