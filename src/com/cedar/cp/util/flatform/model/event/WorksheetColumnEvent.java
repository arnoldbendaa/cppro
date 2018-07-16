// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.event;

import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorksheetEvent;

public class WorksheetColumnEvent extends WorksheetEvent {

   public static final int sINSERT_COLUMNS = 0;
   public static final int sREMOVE_COLUMNS = 1;
   private int mType;
   private int mColumn;
   private int mColumnCount;


   public WorksheetColumnEvent(Worksheet source, int type, int column, int columnCount) {
      super(source);
      this.mType = type;
      this.mColumn = column;
      this.mColumnCount = columnCount;
   }

   public int getType() {
      return this.mType;
   }

   public int getColumn() {
      return this.mColumn;
   }

   public int getColumnCount() {
      return this.mColumnCount;
   }
}
