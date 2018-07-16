// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDataTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedDataTypeCK;
import com.cedar.cp.dto.model.mapping.MappedDataTypePK;
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
