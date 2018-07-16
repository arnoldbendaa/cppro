// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.AbstractTreeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class ImportItemsTableModel extends AbstractTreeTableModel {

   protected static String[] cNames = new String[]{"Import Items", "Select"};
   protected static Class[] cTypes = new Class[]{TreeTableModel.class, Boolean.class};
   ArrayList<DefaultMutableTreeNode> selectedTreeNodes = new ArrayList();


   public ArrayList<DefaultMutableTreeNode> getSelectedTreeNodes() {
      return this.selectedTreeNodes;
   }

   public void setSelectedTreeNodes(ArrayList<DefaultMutableTreeNode> selectedTreeNodes) {
      this.selectedTreeNodes = selectedTreeNodes;
   }

   public List<CommonImpExpItem> getSelectedItemsToImport() {
      ArrayList returnList = null;
      ArrayList selectedNode = this.getSelectedTreeNodes();
      if(selectedNode != null && selectedNode.size() > 0) {
         returnList = new ArrayList();
         Iterator i$ = selectedNode.iterator();

         while(i$.hasNext()) {
            DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode)i$.next();
            CommonImpExpItem userObject = (CommonImpExpItem)defaultMutableTreeNode.getUserObject();
            returnList.add(userObject);
         }
      }

      return returnList;
   }

   public ImportItemsTableModel(TreeNode treeNode) {
      super(treeNode);
   }

   public int getChildCount(Object node) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
      return treeNode.getChildCount();
   }

   public Object getChild(Object node, int i) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
      return treeNode.getChildAt(i);
   }

   public boolean isLeaf(Object node) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
      return treeNode.isLeaf();
   }

   public int getColumnCount() {
      return cNames.length;
   }

   public String getColumnName(int column) {
      return cNames[column];
   }

   public Class getColumnClass(int column) {
      return cTypes[column];
   }

   public Object getValueAt(Object node, int column) {
      if(node != null && node instanceof DefaultMutableTreeNode) {
         DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
         switch(column) {
         case 0:
            return treeNode.toString();
         case 1:
            return Boolean.valueOf(this.selectedTreeNodes.indexOf(treeNode) >= 0);
         }
      }

      return null;
   }

   public void setValueAt(Object value, Object node, int column) {
      if(node != null && node instanceof DefaultMutableTreeNode) {
         DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
         if(column != 0) {
            Boolean isSelected = (Boolean)value;
            if(isSelected.booleanValue()) {
               this.addSelectedItems(treeNode);
            } else {
               this.removeSelectedItems(treeNode);
            }
         }
      }

   }

   private void addSelectedItems(DefaultMutableTreeNode node) {
      if(!node.isLeaf()) {
         this.addChildren(node);
      }

      if(this.selectedTreeNodes.indexOf(node) < 0) {
         this.selectedTreeNodes.add(node);
      }

   }

   private void addChildren(DefaultMutableTreeNode node) {
      Enumeration children = node.children();

      while(children.hasMoreElements()) {
         DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)children.nextElement();
         if(this.selectedTreeNodes.indexOf(childNode) < 0) {
            this.selectedTreeNodes.add(childNode);
            if(!childNode.isLeaf()) {
               this.addChildren(childNode);
            }
         }
      }

   }

   private void removeChildren(DefaultMutableTreeNode node) {
      Enumeration children = node.children();

      while(children.hasMoreElements()) {
         DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)children.nextElement();
         this.selectedTreeNodes.remove(childNode);
         if(!childNode.isLeaf()) {
            this.removeChildren(childNode);
         }
      }

   }

   private void removeSelectedItems(DefaultMutableTreeNode node) {
      if(!node.isLeaf()) {
         this.removeChildren(node);
      }

      this.selectedTreeNodes.remove(node);
   }

}
