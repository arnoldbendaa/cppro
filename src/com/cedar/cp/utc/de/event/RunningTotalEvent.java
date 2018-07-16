// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.de.event;

import java.util.EventObject;

public class RunningTotalEvent extends EventObject {

   private double mRunningTotal;


   public RunningTotalEvent(Object source, double total) {
      super(source);
      this.mRunningTotal = total;
   }

   public double getRunningTotal() {
      return this.mRunningTotal;
   }
}
