// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.dto.dimension.HierarchyImpl;

public class NewHierarchyEvent implements HierarchyEvent {

   private HierarchyImpl mHierarchy;


   public NewHierarchyEvent(HierarchyImpl hierarchy) {
      this.mHierarchy = hierarchy;
   }

   public HierarchyImpl getHierarchy() {
      return this.mHierarchy;
   }
}
