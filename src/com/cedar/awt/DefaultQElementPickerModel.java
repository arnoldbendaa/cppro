// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.AbstractQElementPickerModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class DefaultQElementPickerModel extends AbstractQElementPickerModel {

   private TreeModel mTreeModel = this.createDefaultTreeModel();
   private List mNodes = new ArrayList();


   public boolean isEditable() {
      return true;
   }

   public void toggleNodeSelection(Object node) {
      if(this.mNodes.contains(node)) {
         this.mNodes.remove(node);
         this.fireItemListenerEvent(2, node);
      } else {
         this.mNodes.add(node);
         this.fireItemListenerEvent(1, node);
      }

   }

   public int queryNodeStatus(Object node) {
      return this.isNodeSelected(node)?1:3;
   }

   public boolean isNodeSelected(Object node) {
      return this.mNodes.contains(node);
   }

   public TreeModel getTreeModel() {
      return this.mTreeModel;
   }

   public Collection getSelectedNodes() {
      return this.mNodes;
   }

   public String getTreeName() {
      return "Undefined";
   }

   private TreeModel createDefaultTreeModel() {
      return new DefaultTreeModel(new DefaultMutableTreeNode("root"));
   }

   public void searchTree(String text) {}

   public void loadSelectionIntoTreeModel() {}
}
