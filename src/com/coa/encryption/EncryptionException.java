// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.encryption;


public class EncryptionException extends RuntimeException {

   private static final long serialVersionUID = -6789599646552736414L;


   public EncryptionException() {}

   public EncryptionException(String message, Throwable cause) {
      super(message, cause);
   }

   public EncryptionException(String message) {
      super(message);
   }

   public EncryptionException(Throwable cause) {
      super(cause);
   }
}
