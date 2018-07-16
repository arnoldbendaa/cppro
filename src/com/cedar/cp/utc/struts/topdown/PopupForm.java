// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;

public class PopupForm extends CPForm {

   private int mType;
   private int mModelId;
   private int mFinancecubeId;
   private int mBudgetCycleId;
   private List mSelection;
   public static final int sModelType = 0;
   public static final int sFinanceCubeType = 1;
   public static final int sDataTypeType = 2;
   public static final int sFinanceCubeType1 = 3;
   public static final int sBudgetCycleType = 4;
   public static final int sDataTypeType1 = 5;


   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getFinancecubeId() {
      return this.mFinancecubeId;
   }

   public void setFinancecubeId(int financecubeId) {
      this.mFinancecubeId = financecubeId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(int budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }

   public List getSelection() {
      return this.mSelection;
   }

   public void setSelection(List selection) {
      this.mSelection = selection;
   }

   public int getSize() {
      return this.mSelection == null?0:this.mSelection.size();
   }

   public boolean isModel() {
      return 0 == this.getType();
   }

   public boolean isFinanceCube() {
      return 1 == this.getType();
   }

   public boolean isFinanceCube1() {
      return 3 == this.getType();
   }

   public boolean isDataType() {
      return 2 == this.getType();
   }

   public boolean isBudgetCycleType() {
      return 4 == this.getType();
   }

   public boolean isDataType1() {
      return 5 == this.getType();
   }
}
