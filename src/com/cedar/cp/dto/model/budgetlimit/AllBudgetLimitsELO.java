// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.budgetlimit;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllBudgetLimitsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetLimit", "FinanceCube", "Model"};
   private transient BudgetLimitRef mBudgetLimitEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mDataType;
   private transient Long mMinValue;
   private transient Long mMaxValue;


   public AllBudgetLimitsELO() {
      super(new String[]{"BudgetLimit", "FinanceCube", "Model", "DataType", "MinValue", "MaxValue"});
   }

   public void add(BudgetLimitRef eRefBudgetLimit, FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, String col1, Long col2, Long col3) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetLimit);
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(col1);
      l.add(col2);
      l.add(col3);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mBudgetLimitEntityRef = (BudgetLimitRef)l.get(index);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mDataType = (String)l.get(var4++);
      this.mMinValue = (Long)l.get(var4++);
      this.mMaxValue = (Long)l.get(var4++);
   }

   public BudgetLimitRef getBudgetLimitEntityRef() {
      return this.mBudgetLimitEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public Long getMinValue() {
      return this.mMinValue;
   }

   public Long getMaxValue() {
      return this.mMaxValue;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
