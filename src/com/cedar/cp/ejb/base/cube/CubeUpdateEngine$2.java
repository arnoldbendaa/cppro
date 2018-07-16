// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.util.TemplateParser;

class CubeUpdateEngine$2 extends TemplateParser {

   // $FF: synthetic field
   final CubeUpdateEngine this$0;


   CubeUpdateEngine$2(CubeUpdateEngine var1, String x0) {
      super(x0);
      this.this$0 = var1;
   }

   public String parseToken(String token) {
      StringBuffer sb = new StringBuffer();
      if(token.equals("cft_table_name")) {
         sb.append(CubeUpdateEngine.accessMethod200(this.this$0, CubeUpdateEngine.accessMethod100(this.this$0)));
      } else if(token.equals("outer_dim_cols_to_select")) {
         sb.append(CubeUpdateEngine.accessMethod300(this.this$0, (String)null));
      } else if(token.equals("merge_join_clause")) {
         sb.append(CubeUpdateEngine.accessMethod400(this.this$0, "cft", "delta"));
      } else if(token.equals("insert_cols_clause")) {
         sb.append(CubeUpdateEngine.accessMethod500(this.this$0));
      } else {
         if(!token.equals("insert_vals_clause")) {
            throw new IllegalStateException("Unexpected token [" + token + "]");
         }

         sb.append(CubeUpdateEngine.accessMethod600(this.this$0));
      }

      return sb.toString();
   }
}
