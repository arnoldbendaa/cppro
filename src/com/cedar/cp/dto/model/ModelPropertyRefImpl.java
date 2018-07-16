// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelPropertyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.ModelPropertyCK;
import com.cedar.cp.dto.model.ModelPropertyPK;
import java.io.Serializable;

public class ModelPropertyRefImpl extends EntityRefImpl implements ModelPropertyRef, Serializable {

   public ModelPropertyRefImpl(ModelPropertyCK key, String narrative) {
      super(key, narrative);
   }

   public ModelPropertyRefImpl(ModelPropertyPK key, String narrative) {
      super(key, narrative);
   }

   public ModelPropertyPK getModelPropertyPK() {
      return this.mKey instanceof ModelPropertyCK?((ModelPropertyCK)this.mKey).getModelPropertyPK():(ModelPropertyPK)this.mKey;
   }
}
