// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.AugHierarchyElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.AugHierarchyElementCK;
import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
import java.io.Serializable;

public class AugHierarchyElementRefImpl extends EntityRefImpl implements AugHierarchyElementRef, Serializable {

   public AugHierarchyElementRefImpl(AugHierarchyElementCK key, String narrative) {
      super(key, narrative);
   }

   public AugHierarchyElementRefImpl(AugHierarchyElementPK key, String narrative) {
      super(key, narrative);
   }

   public AugHierarchyElementPK getAugHierarchyElementPK() {
      return this.mKey instanceof AugHierarchyElementCK?((AugHierarchyElementCK)this.mKey).getAugHierarchyElementPK():(AugHierarchyElementPK)this.mKey;
   }
}
