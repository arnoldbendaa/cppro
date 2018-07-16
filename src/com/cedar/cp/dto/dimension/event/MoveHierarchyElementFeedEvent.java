// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;

public class MoveHierarchyElementFeedEvent extends HierarchyElementEvent {

   private Object mParentKey;
   private String mParentVisId;
   private int mIndex;


   public MoveHierarchyElementFeedEvent(Object parentKey, String parentVisId, int index, Object elementKey, String visId) {
      super(elementKey, visId);
      this.mParentKey = parentKey;
      this.mParentVisId = parentVisId;
      this.mIndex = index;
   }

   public Object getParentKey() {
      return this.mParentKey;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public String getParentVisId() {
      return this.mParentVisId;
   }
}
