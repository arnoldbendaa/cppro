// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.event;

import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorksheetEvent;

public class WorksheetFormatEvent extends WorksheetEvent {

   private int mStartRow;
   private int mStartColumn;
   private int mEndRow;
   private int mEndColumn;


   public WorksheetFormatEvent(Worksheet source, int startRow, int startColumn, int endRow, int endColumn) {
      super(source);
      this.mStartRow = startRow;
      this.mStartColumn = startColumn;
      this.mEndRow = endRow;
      this.mEndColumn = endColumn;
   }

   public int getStartRow() {
      return this.mStartRow;
   }

   public int getStartColumn() {
      return this.mStartColumn;
   }

   public int getEndRow() {
      return this.mEndRow;
   }

   public int getEndColumn() {
      return this.mEndColumn;
   }
}
