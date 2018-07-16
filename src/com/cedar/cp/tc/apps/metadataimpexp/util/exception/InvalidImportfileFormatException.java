// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.exception;

import com.cedar.cp.api.base.CPException;

public class InvalidImportfileFormatException extends CPException {

   private static final long serialVersionUID = 1L;


   public InvalidImportfileFormatException(String reason) {
      super(reason);
   }
}
