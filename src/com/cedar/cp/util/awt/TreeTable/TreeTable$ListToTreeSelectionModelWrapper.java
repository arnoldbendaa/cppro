// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTable;
import com.cedar.cp.util.awt.TreeTable.TreeTable$ListToTreeSelectionModelWrapper$ListSelectionHandler;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

class TreeTable$ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel {

   protected boolean updatingListSelectionModel;
   // $FF: synthetic field
   final TreeTable this$0;


   public TreeTable$ListToTreeSelectionModelWrapper(TreeTable var1) {
      this.this$0 = var1;
      this.getListSelectionModel().addListSelectionListener(this.createListSelectionListener());
   }

   ListSelectionModel getListSelectionModel() {
      return this.listSelectionModel;
   }

   public void resetRowSelection() {
      if(!this.updatingListSelectionModel) {
         this.updatingListSelectionModel = true;
         this.updatingListSelectionModel = false;
      }

   }

   protected ListSelectionListener createListSelectionListener() {
      return new TreeTable$ListToTreeSelectionModelWrapper$ListSelectionHandler(this);
   }

   protected void updateSelectedPathsFromSelectedRows() {
      if(!this.updatingListSelectionModel) {
         this.updatingListSelectionModel = true;

         try {
            int min = this.listSelectionModel.getMinSelectionIndex();
            int max = this.listSelectionModel.getMaxSelectionIndex();
            this.clearSelection();
            if(min != -1 && max != -1) {
               for(int counter = min; counter <= max; ++counter) {
                  if(this.listSelectionModel.isSelectedIndex(counter)) {
                     TreePath selPath = this.this$0.mTree.getPathForRow(counter);
                     if(selPath != null) {
                        this.addSelectionPath(selPath);
                     }
                  }
               }
            }
         } finally {
            this.updatingListSelectionModel = false;
         }
      }

   }
}
