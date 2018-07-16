// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.dto.model.SecurityAccessParserListener;
import com.cedar.cp.impl.model.SecurityAccessDefEditorImpl;
import java.text.ParseException;

class SecurityAccessDefEditorImpl$1 implements SecurityAccessParserListener {

   // $FF: synthetic field
   final SecurityAccessDefEditorImpl this$0;


   SecurityAccessDefEditorImpl$1(SecurityAccessDefEditorImpl var1) {
      this.this$0 = var1;
   }

   public void registerRangeRefernce(String rangeName) throws ParseException {
      if(!SecurityAccessDefEditorImpl.accessMethod000(this.this$0, rangeName)) {
         throw new ParseException("Unknown range reference[" + rangeName + "]", 0);
      }
   }
}
