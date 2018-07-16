// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;

public class CalendarHierarchyElementImpl extends HierarchyElementImpl implements HierarchyElement {

   private int mCalElemType;


   public CalendarHierarchyElementImpl(Object paramKey) {
      super(paramKey);
   }

   public int getCalElemType() {
      return this.mCalElemType;
   }

   public void setCalElemType(int calElemType) {
      this.mCalElemType = calElemType;
   }
}
