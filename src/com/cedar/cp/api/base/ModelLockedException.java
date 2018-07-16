// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;

public class ModelLockedException extends ValidationException {

   public ModelLockedException(String reason) {
      super(reason);
   }

   public ModelLockedException(ModelRef ref, String reason) {
      super(ref, reason);
   }
}
