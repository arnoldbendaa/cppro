// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.swing.FormModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class FormTreeModel extends DefaultTreeModel {

   private FormModel mFormModel;


   public FormTreeModel(FormConfig config) {
      super(config);
   }

   public void externalChangeToNode(Object obj, boolean structureChanged) {
      if(this.mFormModel != null) {
         this.mFormModel.setFormTreeModelChanged();
      }

      TreeNode node = (TreeNode)obj;
      if(structureChanged) {
         this.nodeStructureChanged(node);
      } else {
         this.nodeChanged(node);
      }

   }

   public void setFormModel(FormModel formModel) {
      this.mFormModel = formModel;
   }
}
