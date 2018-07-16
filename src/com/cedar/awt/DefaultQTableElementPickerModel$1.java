// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.DefaultQTableElementPickerModel;
import javax.swing.table.DefaultTableModel;

class DefaultQTableElementPickerModel$1 extends Thread {

   // $FF: synthetic field
   final DefaultQTableElementPickerModel this$0;


   DefaultQTableElementPickerModel$1(DefaultQTableElementPickerModel var1) {
      this.this$0 = var1;
   }

   public void run() {
      try {
         Thread.sleep(500L);
      } catch (InterruptedException var2) {
         ;
      }

      DefaultTableModel model = new DefaultTableModel(new String[][]{{"Group"}, {"UK"}, {"USA"}, {"Asia"}, {"France"}}, new String[]{"Model"});
      DefaultQTableElementPickerModel.accessMethod002(this.this$0, model);
      DefaultQTableElementPickerModel.accessMethod100(this.this$0).firePropertyChange("Model_List", (Object)null, model);
   }
}
