// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.HierarchyElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import java.io.Serializable;

public class HierarchyElementRefImpl extends EntityRefImpl implements HierarchyElementRef, Serializable {

   public HierarchyElementRefImpl(HierarchyElementCK key, String narrative) {
      super(key, narrative);
   }

   public HierarchyElementRefImpl(HierarchyElementPK key, String narrative) {
      super(key, narrative);
   }

   public HierarchyElementPK getHierarchyElementPK() {
      return this.mKey instanceof HierarchyElementCK?((HierarchyElementCK)this.mKey).getHierarchyElementPK():(HierarchyElementPK)this.mKey;
   }
}
