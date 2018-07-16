// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.JNLPPostProcessor;
import com.cedar.cp.utc.common.JNLPPostProcessor$ByteArrayPrintWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class JNLPPostProcessor$1 extends HttpServletResponseWrapper {

   // $FF: synthetic field
   final JNLPPostProcessor$ByteArrayPrintWriter val$pw;
   // $FF: synthetic field
   final HttpServletResponse val$resp;
   // $FF: synthetic field
   final JNLPPostProcessor this$0;


   JNLPPostProcessor$1(JNLPPostProcessor var1, HttpServletResponse x0, JNLPPostProcessor$ByteArrayPrintWriter var3, HttpServletResponse var4) {
      super(x0);
      this.this$0 = var1;
      this.val$pw = var3;
      this.val$resp = var4;
   }

   public PrintWriter getWriter() {
      return this.val$pw.getWriter();
   }

   public ServletOutputStream getOutputStream() {
      return this.val$pw.getStream();
   }

   public void setContentType(String type) {
      this.val$resp.setContentType(type);
   }
}
