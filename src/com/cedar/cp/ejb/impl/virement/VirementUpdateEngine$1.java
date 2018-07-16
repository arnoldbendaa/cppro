// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.virement;

import com.cedar.cp.ejb.impl.virement.VirementUpdateEngine;
import com.cedar.cp.util.TemplateParser;

class VirementUpdateEngine$1 extends TemplateParser {

   // $FF: synthetic field
   final int val$financeCubeId;
   // $FF: synthetic field
   final VirementUpdateEngine this$0;


   VirementUpdateEngine$1(VirementUpdateEngine var1, String x0, int var3) {
      super(x0);
      this.this$0 = var1;
      this.val$financeCubeId = var3;
   }

   public String parseToken(String token) {
      if(token.equalsIgnoreCase("finance_cube_id")) {
         return String.valueOf(this.val$financeCubeId);
      } else if(!token.equalsIgnoreCase("additional_dimension_predicates")) {
         if(token.equalsIgnoreCase("account_dimension_index")) {
            return String.valueOf(VirementUpdateEngine.accessMethod000(this.this$0, this.val$financeCubeId) - 2);
         } else if(token.equalsIgnoreCase("calendar_dimension_index")) {
            return String.valueOf(VirementUpdateEngine.accessMethod000(this.this$0, this.val$financeCubeId) - 1);
         } else {
            throw new IllegalArgumentException("Unknown token[" + token + "] found in generateVirementCheckSQL()");
         }
      } else {
         StringBuffer sb = new StringBuffer();

         for(int i = 1; i < VirementUpdateEngine.accessMethod000(this.this$0, this.val$financeCubeId) - 2; ++i) {
            sb.append(" dim");
            sb.append(String.valueOf(i));
            sb.append(" = ? and ");
         }

         return sb.toString();
      }
   }
}
