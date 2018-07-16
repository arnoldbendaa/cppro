// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.util.TemplateParser;

class CubeUpdateEngine$6 extends TemplateParser {

   // $FF: synthetic field
   final CubeUpdateEngine this$0;


   CubeUpdateEngine$6(CubeUpdateEngine var1, String x0) {
      super(x0);
      this.this$0 = var1;
   }

   public String parseToken(String token) {
      StringBuffer sb = new StringBuffer();
      if(token.equals("id_select_statement")) {
         for(int i = 0; i < this.this$0.getNumDims(); ++i) {
            sb.append("select dim");
            sb.append(String.valueOf(i));
            sb.append(" as seid from ");
            sb.append(this.this$0.getXactTableName());
            if(i < this.this$0.getNumDims() - 1) {
               sb.append(" union all \n");
            }
         }

         return sb.toString();
      } else {
         throw new IllegalStateException("Unexpected token :" + token);
      }
   }
}
