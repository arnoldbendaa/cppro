// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;

public class UpdateHierarchyElementChildOrderEvent extends HierarchyElementEvent {

   private Object[] mChildKeys;


   public UpdateHierarchyElementChildOrderEvent(Object elementKey, String visId, Object[] childKeys) {
      super(elementKey, visId);
      this.mChildKeys = childKeys;
   }

   public Object[] getChildKeys() {
      return this.mChildKeys;
   }
}
