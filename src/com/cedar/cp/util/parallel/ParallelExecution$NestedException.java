// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.parallel;


public class ParallelExecution$NestedException extends RuntimeException {

   private Throwable mThrowable;


   public ParallelExecution$NestedException(Throwable t) {
      this.mThrowable = t;
      t.printStackTrace();
   }

   public void printStackTrace() {
      System.out.println("ParallelExecutionNestedException for ");
      if(this.mThrowable != null) {
         this.mThrowable.printStackTrace();
      }

   }
}
