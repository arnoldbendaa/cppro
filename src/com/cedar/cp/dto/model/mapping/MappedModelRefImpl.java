// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedModelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import java.io.Serializable;

public class MappedModelRefImpl extends EntityRefImpl implements MappedModelRef, Serializable {

   public MappedModelRefImpl(MappedModelPK key, String narrative) {
      super(key, narrative);
   }

   public MappedModelPK getMappedModelPK() {
      return (MappedModelPK)this.mKey;
   }
}
