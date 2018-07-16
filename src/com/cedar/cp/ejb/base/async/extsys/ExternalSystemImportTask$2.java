// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.extsys;

import com.cedar.cp.ejb.base.async.extsys.ExternalSystemImportTask;
import java.io.IOException;
import java.io.Writer;

class ExternalSystemImportTask$2 extends Writer {

   // $FF: synthetic field
   final ExternalSystemImportTask this$0;


   ExternalSystemImportTask$2(ExternalSystemImportTask var1) {
      this.this$0 = var1;
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.this$0.logInfo(new String(cbuf, off, len));
   }

   public void flush() throws IOException {}

   public void close() throws IOException {}
}
