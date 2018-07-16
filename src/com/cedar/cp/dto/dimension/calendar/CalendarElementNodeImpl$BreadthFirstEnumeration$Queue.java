// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl$BreadthFirstEnumeration;
import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode;
import java.util.NoSuchElementException;

final class CalendarElementNodeImpl$BreadthFirstEnumeration$Queue {

   CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode head;
   CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode tail;
   // $FF: synthetic field
   final CalendarElementNodeImpl$BreadthFirstEnumeration this$1;


   CalendarElementNodeImpl$BreadthFirstEnumeration$Queue(CalendarElementNodeImpl$BreadthFirstEnumeration var1) {
      this.this$1 = var1;
   }

   public void enqueue(Object anObject) {
      if(this.head == null) {
         this.head = this.tail = new CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode(this, anObject, (CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode)null);
      } else {
         this.tail.next = new CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode(this, anObject, (CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode)null);
         this.tail = this.tail.next;
      }

   }

   public Object dequeue() {
      if(this.head == null) {
         throw new NoSuchElementException("No more elements");
      } else {
         Object retval = this.head.object;
         CalendarElementNodeImpl$BreadthFirstEnumeration$Queue$QNode oldHead = this.head;
         this.head = this.head.next;
         if(this.head == null) {
            this.tail = null;
         } else {
            oldHead.next = null;
         }

         return retval;
      }
   }

   public Object firstObject() {
      if(this.head == null) {
         throw new NoSuchElementException("No more elements");
      } else {
         return this.head.object;
      }
   }

   public boolean isEmpty() {
      return this.head == null;
   }
}
