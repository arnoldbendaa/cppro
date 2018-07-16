package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedDimensionElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementCK;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementPK;
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
