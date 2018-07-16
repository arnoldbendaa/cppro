// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.hierarchy;

import com.cedar.awt.hierarchy.HierarchyModel;
import java.awt.AWTEvent;
import javax.swing.tree.TreeNode;

public class HierarchySearchEvent extends AWTEvent {

   private TreeNode mTreeNode;


   public HierarchySearchEvent(HierarchyModel model, TreeNode node) {
      super(model, 0);
      this.mTreeNode = node;
   }

   public HierarchyModel getHierarchyModel() {
      return (HierarchyModel)this.getSource();
   }

   public TreeNode getTreeNode() {
      return this.mTreeNode;
   }
}
