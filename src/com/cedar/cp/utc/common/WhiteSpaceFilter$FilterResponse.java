// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.WhiteSpaceFilter;
import com.cedar.cp.utc.common.WhiteSpaceFilter$1;
import com.cedar.cp.utc.common.WhiteSpaceFilter$FilterOutStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class WhiteSpaceFilter$FilterResponse extends HttpServletResponseWrapper {

   private WhiteSpaceFilter$FilterOutStream mOutputStream;
   // $FF: synthetic field
   final WhiteSpaceFilter this$0;


   private WhiteSpaceFilter$FilterResponse(WhiteSpaceFilter var1, HttpServletResponse response) {
      super(response);
      this.this$0 = var1;
   }

   public ServletOutputStream getOutputStream() throws IOException {
      if(this.mOutputStream == null) {
         this.mOutputStream = new WhiteSpaceFilter$FilterOutStream(this.this$0, super.getOutputStream(), (WhiteSpaceFilter$1)null);
      }

      return this.mOutputStream;
   }

   // $FF: synthetic method
   WhiteSpaceFilter$FilterResponse(WhiteSpaceFilter x0, HttpServletResponse x1, WhiteSpaceFilter$1 x2) {
      this(x0, x1);
   }
}
