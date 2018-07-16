// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

class JNLPPostProcessor$ByteArrayServletStream extends ServletOutputStream {

   ByteArrayOutputStream baos;


   JNLPPostProcessor$ByteArrayServletStream(ByteArrayOutputStream baos) {
      this.baos = baos;
   }

   public void write(int param) throws IOException {
      this.baos.write(param);
   }
}
