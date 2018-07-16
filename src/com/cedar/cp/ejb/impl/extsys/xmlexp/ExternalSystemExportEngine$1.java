// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlexp;

import com.cedar.cp.ejb.impl.extsys.xmlexp.ExternalSystemExportEngine;
import com.cedar.cp.util.TemplateParser;

class ExternalSystemExportEngine$1 extends TemplateParser {

   // $FF: synthetic field
   final int val$dimensionCount;
   // $FF: synthetic field
   final String val$viewName;
   // $FF: synthetic field
   final ExternalSystemExportEngine this$0;


   ExternalSystemExportEngine$1(ExternalSystemExportEngine var1, String x0, String x1, String x2, int var5, String var6) {
      super(x0, x1, x2);
      this.this$0 = var1;
      this.val$dimensionCount = var5;
      this.val$viewName = var6;
   }

   public String parseToken(String token) {
      if(!token.equalsIgnoreCase("dimCols")) {
         if(token.equalsIgnoreCase("viewName")) {
            return this.val$viewName;
         } else {
            throw new IllegalStateException("Unexpected token in SQL template.");
         }
      } else {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < this.val$dimensionCount; ++i) {
            sb.append("vis_id");
            sb.append(i);
            sb.append(", ");
         }

         return sb.toString();
      }
   }
}
