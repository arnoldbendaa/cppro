// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.mapping.extsys.ExternalEntity;
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
