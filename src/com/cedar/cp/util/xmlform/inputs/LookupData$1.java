// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.inputs.LookupData;
import java.util.Comparator;

class LookupData$1 implements Comparator<Object[]> {

   // $FF: synthetic field
   final LookupData this$0;


   LookupData$1(LookupData var1) {
      this.this$0 = var1;
   }

   public int compare(Object[] o1, Object[] o2) {
      return ((Integer)o1[0]).intValue() - ((Integer)o2[0]).intValue();
   }
}
