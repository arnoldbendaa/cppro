// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.WorksheetColumnMapping;
import com.cedar.cp.util.flatform.model.parser.ParserTest;

class ParserTest$1 implements WorksheetColumnMapping {

   // $FF: synthetic field
   final ParserTest this$0;


   ParserTest$1(ParserTest var1) {
      this.this$0 = var1;
   }

   public int getColumn(String columnName) {
      return 65 - columnName.charAt(0) + 1;
   }
}
