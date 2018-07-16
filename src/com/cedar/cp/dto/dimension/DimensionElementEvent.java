// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionEvent;

public class DimensionElementEvent implements DimensionEvent {

   private Object mElementKey;


   public DimensionElementEvent(Object elementKey) {
      this.mElementKey = elementKey;
   }

   public Object getElementKey() {
      return this.mElementKey;
   }
}
