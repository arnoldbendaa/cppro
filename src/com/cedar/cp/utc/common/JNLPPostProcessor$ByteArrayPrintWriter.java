// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.JNLPPostProcessor$1;
import com.cedar.cp.utc.common.JNLPPostProcessor$ByteArrayServletStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;

class JNLPPostProcessor$ByteArrayPrintWriter {

   private ByteArrayOutputStream baos;
   private PrintWriter pw;
   private ServletOutputStream sos;


   private JNLPPostProcessor$ByteArrayPrintWriter() {
      this.baos = new ByteArrayOutputStream();
      this.pw = new PrintWriter(this.baos);
      this.sos = new JNLPPostProcessor$ByteArrayServletStream(this.baos);
   }

   public PrintWriter getWriter() {
      return this.pw;
   }

   public ServletOutputStream getStream() {
      return this.sos;
   }

   byte[] toByteArray() {
      return this.baos.toByteArray();
   }

   // $FF: synthetic method
   JNLPPostProcessor$ByteArrayPrintWriter(JNLPPostProcessor$1 x0) {
      this();
   }
}
