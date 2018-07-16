// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.RollUpRule;
import com.cedar.cp.api.model.RollUpRuleLine;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class RollUpRuleLineImpl implements RollUpRuleLine, Serializable {

   private FinanceCubeImpl mFinanceCube;
   private DataTypeRefImpl mDataType;
   private List<RollUpRule> mRollUpRules;


   public RollUpRuleLineImpl(FinanceCubeImpl financeCube, DataTypeRefImpl dataType, List<RollUpRule> rollUpRules) {
      this.mFinanceCube = financeCube;
      this.mDataType = dataType;
      this.mRollUpRules = rollUpRules;
   }

   public FinanceCubeImpl getFinanceCube() {
      return this.mFinanceCube;
   }

   public void setFinanceCube(FinanceCubeImpl financeCube) {
      this.mFinanceCube = financeCube;
   }

   public DataTypeRefImpl getDataType() {
      return this.mDataType;
   }

   public void setDataType(DataTypeRefImpl dataType) {
      this.mDataType = dataType;
   }

   public List<RollUpRule> getRollUpRules() {
      return this.mRollUpRules;
   }

   public void setRollUpRules(List<RollUpRule> rollUpRules) {
      this.mRollUpRules = rollUpRules;
   }

   public RollUpRule getRollUpRule(DimensionRef dimension) {
      return this.getRollUpRule(((DimensionRefImpl)dimension).getDimensionPK().getDimensionId());
   }

   public boolean rollsUp(DimensionRef dimension) {
      return this.rollsUp(((DimensionRefImpl)dimension).getDimensionPK().getDimensionId());
   }

   public RollUpRule getRollUpRule(int dimensionId) {
      Iterator i$ = this.mRollUpRules.iterator();

      RollUpRule rur;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         rur = (RollUpRule)i$.next();
      } while(((DimensionPK)rur.getDimension().getPrimaryKey()).getDimensionId() != dimensionId);

      return rur;
   }

   public boolean rollsUp(int dimensionId) {
      RollUpRule rur = this.getRollUpRule(dimensionId);
      if(rur == null) {
         throw new IllegalArgumentException("Unexpected dimensionId supplied:" + dimensionId);
      } else {
         return rur.isRollUp();
      }
   }

   public int hashCode() {
      return this.mFinanceCube.hashCode() + this.mDataType.hashCode();
   }

   public boolean[] getRollUpRulesAsBooleans() {
      boolean[] result = new boolean[this.mRollUpRules.size()];

      for(int i = 0; i < this.mRollUpRules.size(); ++i) {
         result[i] = ((RollUpRule)this.mRollUpRules.get(i)).isRollUp();
      }

      return result;
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof RollUpRuleLineImpl)) {
         return false;
      } else {
         RollUpRuleLineImpl other = (RollUpRuleLineImpl)obj;
         return this.mFinanceCube.equals(other.mFinanceCube) && this.mDataType.equals(other.mDataType);
      }
   }
}
