// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.ValidationException;

public class InvalidCredentialsException extends ValidationException {

   private int mType = 0;


   public InvalidCredentialsException() {
      super("");
   }

   public InvalidCredentialsException(int type) {
      super("");
      this.mType = type;
   }

   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }
}
