// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog;
import java.awt.CardLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

class WorksheetEditDialog$5 implements TreeSelectionListener {

   // $FF: synthetic field
   final WorksheetEditDialog this$0;


   WorksheetEditDialog$5(WorksheetEditDialog var1) {
      this.this$0 = var1;
   }

   public void valueChanged(TreeSelectionEvent e) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)WorksheetEditDialog.accessMethod100(this.this$0).getLastSelectedPathComponent();
      CardLayout c1 = (CardLayout)WorksheetEditDialog.accessMethod200(this.this$0).getLayout();
      if(node.isLeaf()) {
         c1.show(WorksheetEditDialog.accessMethod200(this.this$0), "PRO");
         WorksheetEditDialog.accessMethod000(this.this$0).handleSelectedWorksheet(node);
      } else {
         c1.show(WorksheetEditDialog.accessMethod200(this.this$0), "MSG");
      }

   }
}
