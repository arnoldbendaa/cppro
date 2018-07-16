// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class SwingUtils {

   public static List<TreeNode> locateNodesInTree(TreeModel tm, EntityList elements) {
      ArrayList nodes = new ArrayList();
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)tm.getRoot();
      int row = elements.getNumRows() - 1;

      while(row >= 0) {
         int sId = ((Integer)elements.getValueAt(row, "StructureId")).intValue();
         int seId = ((Integer)elements.getValueAt(row, "StructureElementId")).intValue();
         int depth = ((Integer)elements.getValueAt(row, "Depth")).intValue();
         int childCount = node.getChildCount();
         int i = 0;

         while(true) {
            if(i < childCount) {
               DefaultMutableTreeNode cNode = (DefaultMutableTreeNode)node.getChildAt(i);
               StructureElementNodeImpl seNode = (StructureElementNodeImpl)cNode.getUserObject();
               if(seNode.getStructureId() != sId || seNode.getStructureElementId() != seId) {
                  ++i;
                  continue;
               }

               node = cNode;
            }

            if(row == 0 || ((Integer)elements.getValueAt(row - 1, "Depth")).intValue() < depth) {
               nodes.add(node);
               node = (DefaultMutableTreeNode)tm.getRoot();
            }

            --row;
            break;
         }
      }

      return nodes;
   }

   public static void locateNodesInTree(DefaultMutableTreeNode root, Collection userObjects, List<TreeNode> results, Comparator comparator) {
      Iterator i$ = userObjects.iterator();

      while(i$.hasNext()) {
         Object userObject = i$.next();
         TreeNode node = locateNode(root, userObject, comparator);
         if(node != null) {
            results.add(node);
         }
      }

   }

   private static TreeNode locateNode(DefaultMutableTreeNode node, Object userObject, Comparator comparator) {
      if(comparator.compare(userObject, node.getUserObject()) == 0) {
         return node;
      } else {
         for(int i = 0; i < node.getChildCount(); ++i) {
            TreeNode ans = locateNode((DefaultMutableTreeNode)node.getChildAt(i), userObject, comparator);
            if(ans != null) {
               return ans;
            }
         }

         return null;
      }
   }

   public static Window getWindowForComponent(Component parentComponent) throws HeadlessException {
      return (Window)(parentComponent == null?JOptionPane.getRootFrame():(!(parentComponent instanceof Frame) && !(parentComponent instanceof Dialog)?getWindowForComponent(parentComponent.getParent()):(Window)parentComponent));
   }
}
