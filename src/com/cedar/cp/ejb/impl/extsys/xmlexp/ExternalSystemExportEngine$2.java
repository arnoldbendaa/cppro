// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlexp;

import com.cedar.cp.ejb.impl.extsys.xmlexp.ExternalSystemExportEngine;
import com.cedar.cp.util.TemplateParser;
import java.util.Map;

class ExternalSystemExportEngine$2 extends TemplateParser {

   // $FF: synthetic field
   final Map val$tokens;
   // $FF: synthetic field
   final ExternalSystemExportEngine this$0;


   ExternalSystemExportEngine$2(ExternalSystemExportEngine var1, String x0, String x1, String x2, Map var5) {
      super(x0, x1, x2);
      this.this$0 = var1;
      this.val$tokens = var5;
   }

   public String parseToken(String token) {
      String replacement = (String)this.val$tokens.get(token.toLowerCase());
      return ExternalSystemExportEngine.accessMethod000(this.this$0, replacement);
   }
}
