// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.event;

import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorkbookEvent;

public class WorksheetRenamedEvent extends WorkbookEvent {

   private String mOriginalName;
   private boolean mOriginalHidden;


   public WorksheetRenamedEvent(Workbook workbook, Worksheet worksheet, String originalName) {
      super(workbook, worksheet);
      this.mOriginalName = originalName;
   }

   public WorksheetRenamedEvent(Workbook workbook, Worksheet worksheet, String originalName, boolean originalHidden) {
      super(workbook, worksheet);
      this.mOriginalName = originalName;
      this.mOriginalHidden = originalHidden;
   }

   public String getOriginalName() {
      return this.mOriginalName;
   }

   public boolean isOriginalHidden() {
      return this.mOriginalHidden;
   }
}
