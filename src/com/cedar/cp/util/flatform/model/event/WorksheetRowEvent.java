// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.event;

import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorksheetEvent;

public class WorksheetRowEvent extends WorksheetEvent {

   public static final int sINSERT_ROWS = 0;
   public static final int sREMOVE_ROWS = 1;
   private int mType;
   private int mRow;
   private int mRowCount;


   public WorksheetRowEvent(Worksheet source, int type, int row, int rowCount) {
      super(source);
      this.mType = type;
      this.mRow = row;
      this.mRowCount = rowCount;
   }

   public int getType() {
      return this.mType;
   }

   public int getRow() {
      return this.mRow;
   }

   public int getRowCount() {
      return this.mRowCount;
   }
}
