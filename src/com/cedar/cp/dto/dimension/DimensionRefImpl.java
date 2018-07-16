// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import java.io.Serializable;

public class DimensionRefImpl extends EntityRefImpl implements DimensionRef, Serializable {

   int mType;


   public DimensionRefImpl(DimensionPK key, String narrative, int paramType) {
      super(key, narrative);
      this.mType = paramType;
   }

   public DimensionPK getDimensionPK() {
      return (DimensionPK)this.mKey;
   }

   public int getType() {
      return this.mType;
   }

   public String toString() {
      return this.getNarrative();
   }
}
