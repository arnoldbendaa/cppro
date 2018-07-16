// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.budgetlimit;

import com.cedar.cp.api.model.budgetlimit.BudgetLimitViolation;
import java.math.BigDecimal;
import java.util.List;

public class BudgetLimitViolationImpl implements BudgetLimitViolation {

   private Integer mBudgetLimitId;
   private List mStructureElements;
   private String mDataType;
   private BigDecimal mMinimumValue;
   private BigDecimal mMaximumValue;
   private BigDecimal mCurrentValue;


   public List getStructureElements() {
      return this.mStructureElements;
   }

   public Integer getBudgetLimitId() {
      return this.mBudgetLimitId;
   }

   public void setBudgetLimitId(Integer budgetLimitId) {
      this.mBudgetLimitId = budgetLimitId;
   }

   public void setStructureElements(List structureElements) {
      this.mStructureElements = structureElements;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public BigDecimal getMinimumValue() {
      return this.mMinimumValue;
   }

   public void setMinimumValue(BigDecimal minimumValue) {
      this.mMinimumValue = minimumValue;
   }

   public BigDecimal getMaximumValue() {
      return this.mMaximumValue;
   }

   public void setMaximumValue(BigDecimal maximumValue) {
      this.mMaximumValue = maximumValue;
   }

   public BigDecimal getCurrentValue() {
      return this.mCurrentValue;
   }

   public void setCurrentValue(BigDecimal currentValue) {
      this.mCurrentValue = currentValue;
   }
}
