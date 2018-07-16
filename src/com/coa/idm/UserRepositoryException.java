// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm;


public class UserRepositoryException extends Exception {

   public UserRepositoryException(String msg) {
      super(msg);
   }

   public UserRepositoryException(String msg, Exception nested) {
      super(msg, nested);
   }
}
