package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2Ref;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import java.io.Serializable;

public class GlobalMappedModel2RefImpl extends EntityRefImpl implements GlobalMappedModel2Ref, Serializable {

   public GlobalMappedModel2RefImpl(GlobalMappedModel2PK key, String narrative) {
      super(key, narrative);
   }

   public GlobalMappedModel2PK getMappedModelPK() {
      return (GlobalMappedModel2PK)this.mKey;
   }
}
