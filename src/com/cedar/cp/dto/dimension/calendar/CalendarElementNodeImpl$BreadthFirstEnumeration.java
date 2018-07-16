// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl$BreadthFirstEnumeration$Queue;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.TreeNode;

final class CalendarElementNodeImpl$BreadthFirstEnumeration implements Enumeration<TreeNode> {

   protected CalendarElementNodeImpl$BreadthFirstEnumeration$Queue queue;
   // $FF: synthetic field
   final CalendarElementNodeImpl this$0;


   public CalendarElementNodeImpl$BreadthFirstEnumeration(CalendarElementNodeImpl var1, TreeNode rootNode) {
      this.this$0 = var1;
      Vector v = new Vector(1);
      v.addElement(rootNode);
      this.queue = new CalendarElementNodeImpl$BreadthFirstEnumeration$Queue(this);
      this.queue.enqueue(v.elements());
   }

   public boolean hasMoreElements() {
      return !this.queue.isEmpty() && ((Enumeration)this.queue.firstObject()).hasMoreElements();
   }

   public TreeNode nextElement() {
      Enumeration enumer = (Enumeration)this.queue.firstObject();
      TreeNode node = (TreeNode)enumer.nextElement();
      Enumeration children = node.children();
      if(!enumer.hasMoreElements()) {
         this.queue.dequeue();
      }

      if(children.hasMoreElements()) {
         this.queue.enqueue(children);
      }

      return node;
   }
}
