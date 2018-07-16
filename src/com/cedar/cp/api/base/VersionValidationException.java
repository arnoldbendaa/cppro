// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;

public class VersionValidationException extends ValidationException {

   public VersionValidationException(String reason) {
      super(reason);
   }

   public VersionValidationException(EntityRef ref, String reason) {
      super(ref, reason);
   }
}
