// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;


class SwingWorker$ThreadVar {

   private Thread thread;


   SwingWorker$ThreadVar(Thread t) {
      this.thread = t;
   }

   synchronized Thread get() {
      return this.thread;
   }

   synchronized void clear() {
      this.thread = null;
   }
}
