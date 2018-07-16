// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

final class CalendarElementNodeImpl$PostorderEnumeration implements Enumeration<TreeNode> {

   protected TreeNode root;
   protected Enumeration<TreeNode> children;
   protected Enumeration<TreeNode> subtree;
   // $FF: synthetic field
   final CalendarElementNodeImpl this$0;


   public CalendarElementNodeImpl$PostorderEnumeration(CalendarElementNodeImpl var1, TreeNode rootNode) {
      this.this$0 = var1;
      this.root = rootNode;
      this.children = this.root.children();
      this.subtree = CalendarElementNodeImpl.EMPTY_ENUMERATION;
   }

   public boolean hasMoreElements() {
      return this.root != null;
   }

   public TreeNode nextElement() {
      TreeNode retval;
      if(this.subtree.hasMoreElements()) {
         retval = (TreeNode)this.subtree.nextElement();
      } else if(this.children.hasMoreElements()) {
         this.subtree = new CalendarElementNodeImpl$PostorderEnumeration(this.this$0, (TreeNode)this.children.nextElement());
         retval = (TreeNode)this.subtree.nextElement();
      } else {
         retval = this.root;
         this.root = null;
      }

      return retval;
   }
}
