// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public class Semaphore {

   public synchronized void block() {
      try {
         this.wait();
      } catch (InterruptedException var2) {
         ;
      }

   }

   public synchronized void block(long timeout) {
      try {
         this.wait(timeout);
      } catch (InterruptedException var4) {
         ;
      }

   }

   public synchronized void wakeUp() {
      this.notifyAll();
   }

   public synchronized void wakeUpOne() {
      this.notify();
   }
}
