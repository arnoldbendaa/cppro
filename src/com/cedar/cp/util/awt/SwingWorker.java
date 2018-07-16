// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.SwingWorker$1;
import com.cedar.cp.util.awt.SwingWorker$2;
import com.cedar.cp.util.awt.SwingWorker$ThreadVar;

public abstract class SwingWorker {

   private Object value;
   private SwingWorker$ThreadVar threadVar;


   protected synchronized Object getValue() {
      return this.value;
   }

   private synchronized void setValue(Object x) {
      this.value = x;
   }

   public abstract Object construct();

   public void finished() {}

   public void interrupt() {
      Thread t = this.threadVar.get();
      if(t != null) {
         t.interrupt();
      }

      this.threadVar.clear();
   }

   public Object get() {
      while(true) {
         Thread t = this.threadVar.get();
         if(t == null) {
            return this.getValue();
         }

         try {
            t.join();
         } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
            return null;
         }
      }
   }

   public SwingWorker() {
      SwingWorker$1 doFinished = new SwingWorker$1(this);
      SwingWorker$2 doConstruct = new SwingWorker$2(this, doFinished);
      Thread t = new Thread(doConstruct);
      this.threadVar = new SwingWorker$ThreadVar(t);
   }

   public void start() {
      Thread t = this.threadVar.get();
      if(t != null) {
         t.start();
      }

   }

   // $FF: synthetic method
   static void accessMethod000(SwingWorker x0, Object x1) {
      x0.setValue(x1);
   }

   // $FF: synthetic method
   static SwingWorker$ThreadVar accessMethod100(SwingWorker x0) {
      return x0.threadVar;
   }
}
