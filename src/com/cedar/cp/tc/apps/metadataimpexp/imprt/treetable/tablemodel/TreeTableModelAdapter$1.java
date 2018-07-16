// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModelAdapter;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

class TreeTableModelAdapter$1 implements TreeExpansionListener {

   // $FF: synthetic field
   final TreeTableModelAdapter this$0;


   TreeTableModelAdapter$1(TreeTableModelAdapter var1) {
      this.this$0 = var1;
   }

   public void treeExpanded(TreeExpansionEvent event) {
      this.this$0.fireTableDataChanged();
   }

   public void treeCollapsed(TreeExpansionEvent event) {
      this.this$0.fireTableDataChanged();
   }
}
