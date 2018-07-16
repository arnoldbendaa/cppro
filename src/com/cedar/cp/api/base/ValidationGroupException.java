// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.ValidationException;
import java.util.Collection;

public class ValidationGroupException extends ValidationException {

   private Collection<ValidationException> mExceptions;


   public ValidationGroupException(String reason, Collection<ValidationException> exceptions) {
      super(reason);
      this.mExceptions = exceptions;
   }

   public Collection<ValidationException> getExceptions() {
      return this.mExceptions;
   }
}
