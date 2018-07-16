// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.ModelDimensionRelCK;
import com.cedar.cp.dto.model.ModelDimensionRelPK;
import java.io.Serializable;

public class ModelDimensionRelRefImpl extends EntityRefImpl implements ModelDimensionRelRef, Serializable {

   public ModelDimensionRelRefImpl(ModelDimensionRelCK key, String narrative) {
      super(key, narrative);
   }

   public ModelDimensionRelRefImpl(ModelDimensionRelPK key, String narrative) {
      super(key, narrative);
   }

   public ModelDimensionRelPK getModelDimensionRelPK() {
      return this.mKey instanceof ModelDimensionRelCK?((ModelDimensionRelCK)this.mKey).getModelDimensionRelPK():(ModelDimensionRelPK)this.mKey;
   }
}
