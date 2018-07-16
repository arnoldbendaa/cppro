// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDimensionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionCK;
import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
import java.io.Serializable;

public class MappedDimensionRefImpl extends EntityRefImpl implements MappedDimensionRef, Serializable {

   public MappedDimensionRefImpl(MappedDimensionCK key, String narrative) {
      super(key, narrative);
   }

   public MappedDimensionRefImpl(MappedDimensionPK key, String narrative) {
      super(key, narrative);
   }

   public MappedDimensionPK getMappedDimensionPK() {
      return this.mKey instanceof MappedDimensionCK?((MappedDimensionCK)this.mKey).getMappedDimensionPK():(MappedDimensionPK)this.mKey;
   }
}
