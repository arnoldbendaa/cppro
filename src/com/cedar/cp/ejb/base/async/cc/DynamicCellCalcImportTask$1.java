// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cc;

import com.cedar.cp.ejb.base.async.cc.DynamicCellCalcImportTask;
import java.io.IOException;
import java.io.Writer;

class DynamicCellCalcImportTask$1 extends Writer {

   // $FF: synthetic field
   final DynamicCellCalcImportTask this$0;


   DynamicCellCalcImportTask$1(DynamicCellCalcImportTask var1) {
      this.this$0 = var1;
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.this$0.log(new String(cbuf, off, len));
   }

   public void flush() throws IOException {}

   public void close() throws IOException {}
}
