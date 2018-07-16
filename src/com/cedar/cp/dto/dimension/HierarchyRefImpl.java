// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import java.io.Serializable;

public class HierarchyRefImpl extends EntityRefImpl implements HierarchyRef, Serializable {

   public HierarchyRefImpl(HierarchyCK key, String narrative) {
      super(key, narrative);
   }

   public HierarchyRefImpl(HierarchyPK key, String narrative) {
      super(key, narrative);
   }

   public HierarchyPK getHierarchyPK() {
      return this.mKey instanceof HierarchyCK?((HierarchyCK)this.mKey).getHierarchyPK():(HierarchyPK)this.mKey;
   }
}
