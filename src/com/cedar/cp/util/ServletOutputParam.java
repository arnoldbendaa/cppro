// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.Serializable;

public class ServletOutputParam implements Serializable {

   private byte[] mBytes;


   public ServletOutputParam(byte[] bytes) {
      this.mBytes = bytes;
   }

   public byte[] getBytes() {
      return this.mBytes;
   }
}
