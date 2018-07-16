// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileDAO;
import com.cedar.cp.util.TemplateParser;

class WeightingProfileDAO$1 extends TemplateParser {

   // $FF: synthetic field
   final int[] val$structureElementIds;
   // $FF: synthetic field
   final int val$financeCubeId;
   // $FF: synthetic field
   final WeightingProfileDAO this$0;


   WeightingProfileDAO$1(WeightingProfileDAO var1, String x0, String x1, String x2, int[] var5, int var6) {
      super(x0, x1, x2);
      this.this$0 = var1;
      this.val$structureElementIds = var5;
      this.val$financeCubeId = var6;
   }

   public String parseToken(String token) {
      StringBuffer sb = new StringBuffer();
      int i;
      if(!token.equalsIgnoreCase("dimColumns") && !token.equalsIgnoreCase("seDimColumns")) {
         if(token.equalsIgnoreCase("accountIdx")) {
            sb.append(this.val$structureElementIds.length - 2);
         } else if(token.equalsIgnoreCase("calendarIdx")) {
            sb.append(this.val$structureElementIds.length - 1);
         } else if(token.equalsIgnoreCase("financeCubeId")) {
            sb.append(this.val$financeCubeId);
         } else if(token.equalsIgnoreCase("busParams")) {
            for(i = 0; i < this.val$structureElementIds.length - 2; ++i) {
               sb.append("? as bus_se");
               sb.append(i);
               sb.append(", ");
            }
         } else if(token.equalsIgnoreCase("busColSel")) {
            for(i = 0; i < this.val$structureElementIds.length - 2; ++i) {
               sb.append("bus_se");
               sb.append(i);
               sb.append(" as dim");
               sb.append(i);
               sb.append(", ");
            }
         }
      } else {
         for(i = 0; i < this.val$structureElementIds.length; ++i) {
            if(token.equalsIgnoreCase("seDimColumns")) {
               sb.append("se.");
            }

            sb.append("dim");
            sb.append(i);
            sb.append(", ");
         }
      }

      return sb.toString();
   }
}
