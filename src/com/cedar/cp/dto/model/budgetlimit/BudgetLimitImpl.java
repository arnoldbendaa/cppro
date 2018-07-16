// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.budgetlimit;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.budgetlimit.BudgetLimit;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
import java.io.Serializable;

public class BudgetLimitImpl implements BudgetLimit, Serializable, Cloneable {

   private String mDim0Txt;
   private String mDim1Txt;
   private String mDim2Txt;
   private String mDim3Txt;
   private String mDim4Txt;
   private String mDim5Txt;
   private String mDim6Txt;
   private String mDim7Txt;
   private String mDim8Txt;
   private String mDim9Txt;
   private Object mPrimaryKey;
   private int mBudgetLocationElementId;
   private int mDim0;
   private int mDim1;
   private int mDim2;
   private int mDim3;
   private int mDim4;
   private int mDim5;
   private int mDim6;
   private int mDim7;
   private int mDim8;
   private int mDim9;
   private String mDataType;
   private Long mMinValue;
   private Long mMaxValue;
   private int mVersionNum;
   private FinanceCubeRef mFinanceCubeRef;
   private ModelRef mModelRef;


   public BudgetLimitImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mBudgetLocationElementId = 0;
      this.mDim0 = 0;
      this.mDim1 = 0;
      this.mDim2 = 0;
      this.mDim3 = 0;
      this.mDim4 = 0;
      this.mDim5 = 0;
      this.mDim6 = 0;
      this.mDim7 = 0;
      this.mDim8 = 0;
      this.mDim9 = 0;
      this.mDataType = "";
      this.mMinValue = null;
      this.mMaxValue = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (BudgetLimitPK)paramKey;
   }

   public void setPrimaryKey(BudgetLimitCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getBudgetLocationElementId() {
      return this.mBudgetLocationElementId;
   }

   public int getDim0() {
      return this.mDim0;
   }

   public int getDim1() {
      return this.mDim1;
   }

   public int getDim2() {
      return this.mDim2;
   }

   public int getDim3() {
      return this.mDim3;
   }

   public int getDim4() {
      return this.mDim4;
   }

   public int getDim5() {
      return this.mDim5;
   }

   public int getDim6() {
      return this.mDim6;
   }

   public int getDim7() {
      return this.mDim7;
   }

   public int getDim8() {
      return this.mDim8;
   }

   public int getDim9() {
      return this.mDim9;
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

   public FinanceCubeRef getFinanceCubeRef() {
      return this.mFinanceCubeRef;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setFinanceCubeRef(FinanceCubeRef ref) {
      this.mFinanceCubeRef = ref;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setBudgetLocationElementId(int paramBudgetLocationElementId) {
      this.mBudgetLocationElementId = paramBudgetLocationElementId;
   }

   public void setDim0(int paramDim0) {
      this.mDim0 = paramDim0;
   }

   public void setDim1(int paramDim1) {
      this.mDim1 = paramDim1;
   }

   public void setDim2(int paramDim2) {
      this.mDim2 = paramDim2;
   }

   public void setDim3(int paramDim3) {
      this.mDim3 = paramDim3;
   }

   public void setDim4(int paramDim4) {
      this.mDim4 = paramDim4;
   }

   public void setDim5(int paramDim5) {
      this.mDim5 = paramDim5;
   }

   public void setDim6(int paramDim6) {
      this.mDim6 = paramDim6;
   }

   public void setDim7(int paramDim7) {
      this.mDim7 = paramDim7;
   }

   public void setDim8(int paramDim8) {
      this.mDim8 = paramDim8;
   }

   public void setDim9(int paramDim9) {
      this.mDim9 = paramDim9;
   }

   public void setDataType(String paramDataType) {
      this.mDataType = paramDataType;
   }

   public void setMinValue(Long paramMinValue) {
      this.mMinValue = paramMinValue;
   }

   public void setMaxValue(Long paramMaxValue) {
      this.mMaxValue = paramMaxValue;
   }

   public String getDim0Txt() {
      return this.mDim0Txt;
   }

   public void setDim0Txt(String dim0Txt) {
      this.mDim0Txt = dim0Txt;
   }

   public String getDim1Txt() {
      return this.mDim1Txt;
   }

   public void setDim1Txt(String dim1Txt) {
      this.mDim1Txt = dim1Txt;
   }

   public String getDim2Txt() {
      return this.mDim2Txt;
   }

   public void setDim2Txt(String dim2Txt) {
      this.mDim2Txt = dim2Txt;
   }

   public String getDim3Txt() {
      return this.mDim3Txt;
   }

   public void setDim3Txt(String dim3Txt) {
      this.mDim3Txt = dim3Txt;
   }

   public String getDim4Txt() {
      return this.mDim4Txt;
   }

   public void setDim4Txt(String dim4Txt) {
      this.mDim4Txt = dim4Txt;
   }

   public String getDim5Txt() {
      return this.mDim5Txt;
   }

   public void setDim5Txt(String dim5Txt) {
      this.mDim5Txt = dim5Txt;
   }

   public String getDim6Txt() {
      return this.mDim6Txt;
   }

   public void setDim6Txt(String dim6Txt) {
      this.mDim6Txt = dim6Txt;
   }

   public String getDim7Txt() {
      return this.mDim7Txt;
   }

   public void setDim7Txt(String dim7Txt) {
      this.mDim7Txt = dim7Txt;
   }

   public String getDim8Txt() {
      return this.mDim8Txt;
   }

   public void setDim8Txt(String dim8Txt) {
      this.mDim8Txt = dim8Txt;
   }

   public String getDim9Txt() {
      return this.mDim9Txt;
   }

   public void setDim9Txt(String dim9Txt) {
      this.mDim9Txt = dim9Txt;
   }
}
