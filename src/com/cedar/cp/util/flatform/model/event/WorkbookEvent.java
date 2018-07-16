// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.event;

import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import java.util.EventObject;

public class WorkbookEvent extends EventObject {

   private Worksheet mWorksheet;


   public WorkbookEvent(Workbook workbook, Worksheet worksheet) {
      super(workbook);
      this.mWorksheet = worksheet;
   }

   public Workbook getWorkbook() {
      return (Workbook)this.getSource();
   }

   public Worksheet getWorksheet() {
      return this.mWorksheet;
   }
}
