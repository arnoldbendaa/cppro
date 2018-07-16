// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.AbstractTreeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class SelectNewFinanceCubeTableModel extends AbstractTreeTableModel {

   protected static String[] cNames = new String[]{"XML Forms", "New Finance Cubes"};
   protected static Class[] cTypes = new Class[]{TreeTableModel.class, CommonImpExpItem.class};


   public SelectNewFinanceCubeTableModel(TreeNode root) {
      super(root);
   }

   public int getColumnCount() {
      return cNames.length;
   }

   public String getColumnName(int column) {
      return cNames[column];
   }

   public Object getValueAt(Object node, int column) {
      if(node != null && node instanceof DefaultMutableTreeNode) {
         DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
         switch(column) {
         case 0:
            return treeNode.toString();
         case 1:
            CommonImpExpItem itemNode = (CommonImpExpItem)treeNode.getUserObject();
            return itemNode;
         }
      }

      return null;
   }

   public void setValueAt(Object value, Object node, int column) {}

   public Object getChild(Object parent, int index) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)parent;
      return treeNode.getChildAt(index);
   }

   public int getChildCount(Object parent) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)parent;
      return treeNode.getChildCount();
   }

   public Class getColumnClass(int column) {
      return cTypes[column];
   }

}
