// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public class InterpreterException extends Exception {

   public InterpreterException() {}

   public InterpreterException(String reason, Throwable arg1) {
      super(reason, arg1);
   }

   public InterpreterException(String reason) {
      super(reason);
   }

   public InterpreterException(Throwable arg0) {
      super(arg0);
   }

   public InterpreterException(String reason, Exception e) {
      super(reason, e);
   }
}
