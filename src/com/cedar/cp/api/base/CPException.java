// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import java.io.Serializable;

public class CPException extends RuntimeException implements Serializable {

   public CPException(String reason) {
      super(reason);
   }

   public CPException(String reason, Throwable detail) {
      super(reason, detail);
   }
}
