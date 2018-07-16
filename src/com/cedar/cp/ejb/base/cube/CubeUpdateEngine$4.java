// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.util.TemplateParser;

class CubeUpdateEngine$4 extends TemplateParser {

   // $FF: synthetic field
   final CubeUpdateEngine this$0;


   CubeUpdateEngine$4(CubeUpdateEngine var1, String x0) {
      super(x0);
      this.this$0 = var1;
   }

   public String parseToken(String token) {
      StringBuffer sb = new StringBuffer();
      if(token.equals("string_table_name")) {
         sb.append(CubeUpdateEngine.accessMethod1000(this.this$0, CubeUpdateEngine.accessMethod900(this.this$0)));
      } else if(token.equals("dim_cols")) {
         sb.append(CubeUpdateEngine.accessMethod700(this.this$0));
      } else if(token.equals("dim_cols_bind_vars")) {
         sb.append(CubeUpdateEngine.accessMethod800(this.this$0));
      } else {
         if(!token.equals("sft_sequence")) {
            throw new IllegalStateException("Unexpected token[" + token + "]");
         }

         sb.append(CubeUpdateEngine.accessMethod1100(this.this$0));
      }

      return sb.toString();
   }
}
