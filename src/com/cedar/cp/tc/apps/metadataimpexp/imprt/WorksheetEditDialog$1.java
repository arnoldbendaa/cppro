// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WorksheetEditDialog$1 implements ActionListener {

   // $FF: synthetic field
   final WorksheetEditDialog this$0;


   WorksheetEditDialog$1(WorksheetEditDialog var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      WorksheetEditDialog.accessMethod000(this.this$0).editWorksheetProperties();
   }
}
