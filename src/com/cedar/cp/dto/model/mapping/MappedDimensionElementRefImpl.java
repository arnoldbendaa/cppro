// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDimensionElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementCK;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementPK;
import java.io.Serializable;

public class MappedDimensionElementRefImpl extends EntityRefImpl implements MappedDimensionElementRef, Serializable {

   public MappedDimensionElementRefImpl(MappedDimensionElementCK key, String narrative) {
      super(key, narrative);
   }

   public MappedDimensionElementRefImpl(MappedDimensionElementPK key, String narrative) {
      super(key, narrative);
   }

   public MappedDimensionElementPK getMappedDimensionElementPK() {
      return this.mKey instanceof MappedDimensionElementCK?((MappedDimensionElementCK)this.mKey).getMappedDimensionElementPK():(MappedDimensionElementPK)this.mKey;
   }
}
