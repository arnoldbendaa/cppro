package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import java.io.Serializable;

public class ExternalEntityImpl implements ExternalEntity, Serializable {

   private EntityRef mEntityRef;


   public ExternalEntityImpl() {}

   public ExternalEntityImpl(EntityRef entityRef) {
      this.mEntityRef = entityRef;
   }

   public EntityRef getEntityRef() {
      return this.mEntityRef;
   }

   public void setEntityRef(EntityRef entityRef) {
      this.mEntityRef = entityRef;
   }
}
