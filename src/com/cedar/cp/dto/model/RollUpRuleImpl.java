// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.RollUpRule;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.RollUpRuleLineImpl;
import java.io.Serializable;

public class RollUpRuleImpl implements RollUpRule, Serializable {

   private RollUpRuleLineImpl mRollUpRuleLineImpl;
   private DimensionRefImpl mDimensionRef;
   private boolean mRollUp;


   public RollUpRuleImpl(RollUpRuleLineImpl line, DimensionRefImpl dimensionRef, boolean rollUp) {
      this.mRollUpRuleLineImpl = line;
      this.mDimensionRef = dimensionRef;
      this.mRollUp = rollUp;
   }

   public DimensionRef getDimension() {
      return this.mDimensionRef;
   }

   public boolean isRollUp() {
      return this.mRollUp;
   }

   public void setRollUp(boolean rollUp) {
      this.mRollUp = rollUp;
   }

   public DataTypeRef getDataType() {
      return this.mRollUpRuleLineImpl.getDataType();
   }
}
