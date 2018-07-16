// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialogController;
import com.cedar.cp.util.flatform.model.Properties;
import com.cedar.cp.util.flatform.model.Worksheet;

class WorksheetEditDialogController$WorksheetWrapper {

   private Worksheet worksheet;
   private Properties originalProperties;
   private boolean isChange;
   // $FF: synthetic field
   final WorksheetEditDialogController this$0;


   public WorksheetEditDialogController$WorksheetWrapper(WorksheetEditDialogController var1, Worksheet worksheet) {
      this.this$0 = var1;
      this.worksheet = null;
      this.originalProperties = new Properties();
      this.isChange = false;
      this.worksheet = worksheet;
      if(worksheet.getProperties() != null) {
         this.originalProperties = WorksheetEditDialogController.accessMethod100(worksheet.getProperties());
      }

   }

   public void setProperties(Properties properties) {
      this.worksheet.setProperties(properties);
      this.isChange = true;
   }

   public boolean isChange() {
      return this.isChange;
   }

   public void restoreWorksheetProperties() {
      Properties oprops = WorksheetEditDialogController.accessMethod100(this.originalProperties);
      this.worksheet.setProperties(oprops);
      this.isChange = false;
   }

   public Worksheet getWorksheet() {
      return this.worksheet;
   }

   // $FF: synthetic method
   static boolean accessMethod000(WorksheetEditDialogController$WorksheetWrapper x0) {
      return x0.isChange;
   }
}
