// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.hierarchy;

import com.cedar.awt.hierarchy.AbstractHierarchyModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class DefaultHierarchyModel extends AbstractHierarchyModel {

   public TreeModel getTreeModel() {
      return new DefaultTreeModel(new DefaultMutableTreeNode("Root"));
   }

   public void searchTree(String text) {
      try {
         Thread.sleep(3000L);
      } catch (Throwable var3) {
         ;
      }

   }
}
