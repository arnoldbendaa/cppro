// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable;
import javax.swing.tree.DefaultTreeSelectionModel;

class JTreeTable$1 extends DefaultTreeSelectionModel {

   // $FF: synthetic field
   final JTreeTable this$0;


   JTreeTable$1(JTreeTable var1) {
      this.this$0 = var1;
      this.this$0.setSelectionModel(this.listSelectionModel);
   }
}
