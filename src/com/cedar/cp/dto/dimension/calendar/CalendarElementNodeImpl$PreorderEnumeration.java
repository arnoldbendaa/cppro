// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;
import javax.swing.tree.TreeNode;

final class CalendarElementNodeImpl$PreorderEnumeration implements Enumeration<TreeNode> {

   protected Stack stack;
   // $FF: synthetic field
   final CalendarElementNodeImpl this$0;


   public CalendarElementNodeImpl$PreorderEnumeration(CalendarElementNodeImpl var1, TreeNode rootNode) {
      this.this$0 = var1;
      Vector v = new Vector(1);
      v.addElement(rootNode);
      this.stack = new Stack();
      this.stack.push(v.elements());
   }

   public boolean hasMoreElements() {
      return !this.stack.empty() && ((Enumeration)this.stack.peek()).hasMoreElements();
   }

   public TreeNode nextElement() {
      Enumeration enumer = (Enumeration)this.stack.peek();
      TreeNode node = (TreeNode)enumer.nextElement();
      Enumeration children = node.children();
      if(!enumer.hasMoreElements()) {
         this.stack.pop();
      }

      if(children.hasMoreElements()) {
         this.stack.push(children);
      }

      return node;
   }
}
