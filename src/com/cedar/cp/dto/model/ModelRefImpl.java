// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import java.io.Serializable;

public class ModelRefImpl extends EntityRefImpl implements ModelRef, Serializable {

   public ModelRefImpl(ModelPK key, String narrative) {
      super(key, narrative);
   }

   public ModelPK getModelPK() {
      return (ModelPK)this.mKey;
   }
}
