// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.parallel;

import java.util.Collection;
import java.util.LinkedList;

public class BlockingQueue {

   private Integer mMaxLength;
   private LinkedList<Object> mItems;


   public BlockingQueue() {
      this(Integer.MAX_VALUE);
   }

   public BlockingQueue(int maxLength) {
      this.mItems = new LinkedList();
      if(maxLength <= 0) {
         throw new IllegalArgumentException("max length must be greater than zero");
      } else {
         this.mMaxLength = Integer.valueOf(maxLength);
      }
   }

   public int getMaxQueueLength() {
      return this.mMaxLength.intValue();
   }

   public final synchronized void enqueue(Object item) throws InterruptedException {
      if(this.mItems.size() == this.mMaxLength.intValue()) {
         this.wait();
      }

      this.mItems.addLast(item);
      this.notify();
   }

   public final synchronized void dequeue(Collection holder) throws InterruptedException {
      while(this.mItems.isEmpty()) {
         this.wait();
      }

      holder.add(this.mItems.removeFirst());
      this.notify();
   }

   public final synchronized Object dequeue() throws InterruptedException {
      while(this.mItems.isEmpty()) {
         this.wait();
      }

      Object result = this.mItems.removeFirst();
      this.notify();
      return result;
   }

   public synchronized int size() {
      return this.mItems.size();
   }
}
