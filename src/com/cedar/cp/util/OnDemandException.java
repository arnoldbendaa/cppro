// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public class OnDemandException extends RuntimeException {

   public OnDemandException(String reason) {
      super(reason);
   }

   public OnDemandException(String reason, Throwable detail) {
      super(reason, detail);
   }
}
