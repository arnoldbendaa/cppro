// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.event;

import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorkbookEvent;

public class WorkbookReorderWorksheetsEvent extends WorkbookEvent {

   private int mOldIndex;
   private int mNewIndex;


   public WorkbookReorderWorksheetsEvent(Workbook workbook, Worksheet worksheet, int oldIndex, int newIndex) {
      super(workbook, worksheet);
      this.mOldIndex = oldIndex;
      this.mNewIndex = newIndex;
   }

   public int getOldIndex() {
      return this.mOldIndex;
   }

   public int getNewIndex() {
      return this.mNewIndex;
   }
}
