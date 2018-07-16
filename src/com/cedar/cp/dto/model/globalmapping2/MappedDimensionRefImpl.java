package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedDimensionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionCK;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionPK;
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
