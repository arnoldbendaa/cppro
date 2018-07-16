// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp;

import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplication;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class MetaDataImpExpApplication$1 extends WindowAdapter {

   // $FF: synthetic field
   final MetaDataImpExpApplication this$0;


   MetaDataImpExpApplication$1(MetaDataImpExpApplication var1) {
      this.this$0 = var1;
   }

   public void windowClosing(WindowEvent e) {
      this.this$0.doCancel();
   }
}
