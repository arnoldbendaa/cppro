// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.DimensionEvent;

public class UpdateDimensionEvent implements DimensionEvent {

   private String mName;


   public UpdateDimensionEvent(String name) {
      this.mName = name;
   }

   public String getName() {
      return this.mName;
   }
}
