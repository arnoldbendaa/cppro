// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula;


public class FinanceCubeInfo {

   private int mModelId;
   private int mFinanceCubeId;
   private int[] mDims;
   private int mCalHierId;
   private boolean mCubeFormulaEnabled;


   public FinanceCubeInfo(int modelId, int financeCubeId, int dims, int calHierId, boolean cubeFormulaEnabled) {
      this.mModelId = modelId;
      this.mFinanceCubeId = financeCubeId;
      this.mDims = new int[dims];

      for(int i = 0; i < dims; this.mDims[i] = i++) {
         ;
      }

      this.mCalHierId = calHierId;
      this.mCubeFormulaEnabled = cubeFormulaEnabled;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public int[] getODims() {
      int[] ans = new int[this.getNumDims() - 1];

      for(int i = 0; i < ans.length; ++i) {
         ans[i] = this.getDims()[i];
      }

      return ans;
   }

   public int[] getDims() {
      return this.mDims;
   }

   public void setDims(int[] dims) {
      this.mDims = dims;
   }

   public int getNumDims() {
      return this.mDims.length;
   }

   public int getCalHierId() {
      return this.mCalHierId;
   }

   public void setCalHierId(int calHierId) {
      this.mCalHierId = calHierId;
   }

   public boolean isCubeFormulaEnabled() {
      return this.mCubeFormulaEnabled;
   }

   public void setCubeFormulaEnabled(boolean cubeFormulaEnabled) {
      this.mCubeFormulaEnabled = cubeFormulaEnabled;
   }
}
