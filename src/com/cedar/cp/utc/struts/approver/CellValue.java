// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;


public class CellValue {

   private Object mValue;
   private int mCellNoteId;


   CellValue(Object value) {
      this.mValue = value;
   }

   CellValue(Object value, int note) {
      this.mValue = value;
      this.mCellNoteId = note;
   }

   public Object getValue() {
      return this.mValue;
   }

   public int getCellNoteId() {
      return this.mCellNoteId;
   }
}
