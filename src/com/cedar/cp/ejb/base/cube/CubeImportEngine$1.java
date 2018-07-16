// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CubeImportEngine;
import com.cedar.cp.util.TemplateParser;

class CubeImportEngine$1 extends TemplateParser {

   // $FF: synthetic field
   final int val$financeCubeId;
   // $FF: synthetic field
   final int val$numDims;
   // $FF: synthetic field
   final CubeImportEngine this$0;


   CubeImportEngine$1(CubeImportEngine var1, String x0, int var3, int var4) {
      super(x0);
      this.this$0 = var1;
      this.val$financeCubeId = var3;
      this.val$numDims = var4;
   }

   public String parseToken(String token) {
      StringBuffer sb = new StringBuffer();
      if(token.equals("tft_table_name")) {
         sb.append("tx1_" + this.val$financeCubeId);
      } else {
         if(!token.equals("dims_to_select")) {
            throw new IllegalStateException("Unexpected token:" + token);
         }

         for(int i = 0; i < this.val$numDims; ++i) {
            sb.append("dim");
            sb.append(i);
            sb.append(", ");
         }
      }

      return sb.toString();
   }
}
