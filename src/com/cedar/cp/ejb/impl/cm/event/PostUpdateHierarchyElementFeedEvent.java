// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import java.io.Serializable;

public class PostUpdateHierarchyElementFeedEvent implements Serializable {

   private HierarchyElementFeedDAG mElementFeed;


   public PostUpdateHierarchyElementFeedEvent(HierarchyElementFeedDAG elementFeed) {
      this.mElementFeed = elementFeed;
   }

   public HierarchyElementFeedDAG getElementFeed() {
      return this.mElementFeed;
   }
}
