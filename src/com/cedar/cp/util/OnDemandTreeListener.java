// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.OnDemandMutableTreeNode;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;

public class OnDemandTreeListener implements TreeWillExpandListener, TreeExpansionListener {

   private Component mComponent;
   private boolean waitCursor = false;


   public OnDemandTreeListener(Component pComponent) {
      this.mComponent = pComponent;
   }

   public void setCursor(Cursor cursor) {
      SwingUtilities.getRoot(this.mComponent).setCursor(cursor);
   }

   public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
      if(e.getPath().getLastPathComponent() instanceof OnDemandMutableTreeNode && ((OnDemandMutableTreeNode)e.getPath().getLastPathComponent()).isFakeNode()) {
         this.waitCursor = true;
         this.setCursor(Cursor.getPredefinedCursor(3));
      }

   }

   public void treeExpanded(TreeExpansionEvent event) {
      if(this.waitCursor) {
         this.setCursor(Cursor.getPredefinedCursor(0));
      }

   }

   public void treeWillCollapse(TreeExpansionEvent e) throws ExpandVetoException {}

   public void treeCollapsed(TreeExpansionEvent event) {}
}
