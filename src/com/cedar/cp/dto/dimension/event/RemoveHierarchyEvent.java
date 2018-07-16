// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.dto.dimension.HierarchyPK;

public class RemoveHierarchyEvent implements HierarchyEvent {

   private HierarchyPK mHierarchyPK;


   public RemoveHierarchyEvent(HierarchyPK hierarchyPK) {
      this.mHierarchyPK = hierarchyPK;
   }

   public HierarchyPK getHierarchyPK() {
      return this.mHierarchyPK;
   }
}
