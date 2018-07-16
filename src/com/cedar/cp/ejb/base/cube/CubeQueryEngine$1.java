// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CubeQueryEngine;
import com.cedar.cp.util.TemplateParser;

class CubeQueryEngine$1 extends TemplateParser {

   // $FF: synthetic field
   final String val$columnName;
   // $FF: synthetic field
   final CubeQueryEngine this$0;


   CubeQueryEngine$1(CubeQueryEngine var1, String x0, String var3) {
      super(x0);
      this.this$0 = var1;
      this.val$columnName = var3;
   }

   public String parseToken(String token) {
      StringBuffer sb = new StringBuffer();
      if(token.equals("dft-table-name")) {
         sb.append("DFT" + CubeQueryEngine.accessMethod000(this.this$0));
      } else {
         if(!token.equals("column")) {
            throw new IllegalStateException("Unexpected token:" + token);
         }

         sb.append(this.val$columnName);
      }

      return sb.toString();
   }
}
