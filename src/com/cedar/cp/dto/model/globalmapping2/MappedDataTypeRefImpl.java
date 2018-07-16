package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedDataTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDataTypeCK;
import com.cedar.cp.dto.model.globalmapping2.MappedDataTypePK;
import java.io.Serializable;

public class MappedDataTypeRefImpl extends EntityRefImpl implements MappedDataTypeRef, Serializable {

   public MappedDataTypeRefImpl(MappedDataTypeCK key, String narrative) {
      super(key, narrative);
   }

   public MappedDataTypeRefImpl(MappedDataTypePK key, String narrative) {
      super(key, narrative);
   }

   public MappedDataTypePK getMappedDataTypePK() {
      return this.mKey instanceof MappedDataTypeCK?((MappedDataTypeCK)this.mKey).getMappedDataTypePK():(MappedDataTypePK)this.mKey;
   }
}
