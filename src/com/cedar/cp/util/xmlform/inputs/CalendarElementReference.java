// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.inputs.StructureElementReference;

public class CalendarElementReference extends StructureElementReference {

   private int mCalElemType;


   public CalendarElementReference(Object id, String label, int calElemType, int position) {
      super(id, label, (String)null, (String)null, (String)null, position);
      this.mCalElemType = calElemType;
   }

   public int getCalElemType() {
      return this.mCalElemType;
   }
}
