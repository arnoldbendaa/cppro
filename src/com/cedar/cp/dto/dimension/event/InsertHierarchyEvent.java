// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.HierarchyEvent;

public class InsertHierarchyEvent implements HierarchyEvent {

   private String mVisId;
   private String mDescription;


   public InsertHierarchyEvent(String visId, String description) {
      this.mVisId = visId;
      this.mDescription = description;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }
}
