// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.DimensionElementEvent;

public class MoveDimensionElementEvent extends DimensionElementEvent {

   private Object mParentKey;
   private int mIndex;


   public MoveDimensionElementEvent(Object parentKey, int index, Object elementKey) {
      super(elementKey);
      this.mParentKey = parentKey;
      this.mIndex = index;
   }

   public Object getParentKey() {
      return this.mParentKey;
   }

   public int getIndex() {
      return this.mIndex;
   }
}
