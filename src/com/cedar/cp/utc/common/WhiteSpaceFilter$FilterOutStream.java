// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.WhiteSpaceFilter;
import com.cedar.cp.utc.common.WhiteSpaceFilter$1;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

class WhiteSpaceFilter$FilterOutStream extends ServletOutputStream {

   private static final int sSpace = 32;
   private static final int sNewline = 10;
   private static final int sTab = 9;
   private boolean mFollowingNewLine;
   private ServletOutputStream mStream;
   // $FF: synthetic field
   final WhiteSpaceFilter this$0;


   private WhiteSpaceFilter$FilterOutStream(WhiteSpaceFilter var1, ServletOutputStream underLyingStream) {
      this.this$0 = var1;
      this.mFollowingNewLine = true;
      this.mStream = underLyingStream;
   }

   public void write(int b) throws IOException {
      if(!this.mFollowingNewLine) {
         this.mStream.write(b);
         if(b == 10) {
            this.mFollowingNewLine = true;
         }
      } else if(b != 32 && b != 9 && b != 10) {
         this.mStream.write(b);
         this.mFollowingNewLine = false;
      }

   }

   public void close() throws IOException {
      this.mStream.close();
   }

   public void flush() throws IOException {
      this.mStream.flush();
   }

   // $FF: synthetic method
   WhiteSpaceFilter$FilterOutStream(WhiteSpaceFilter x0, ServletOutputStream x1, WhiteSpaceFilter$1 x2) {
      this(x0, x1);
   }
}
