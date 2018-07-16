// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.HierarchyElementFeedRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import java.io.Serializable;

public class HierarchyElementFeedRefImpl extends EntityRefImpl implements HierarchyElementFeedRef, Serializable {

   public HierarchyElementFeedRefImpl(HierarchyElementFeedCK key, String narrative) {
      super(key, narrative);
   }

   public HierarchyElementFeedRefImpl(HierarchyElementFeedPK key, String narrative) {
      super(key, narrative);
   }

   public HierarchyElementFeedPK getHierarchyElementFeedPK() {
      return this.mKey instanceof HierarchyElementFeedCK?((HierarchyElementFeedCK)this.mKey).getHierarchyElementFeedPK():(HierarchyElementFeedPK)this.mKey;
   }
}
