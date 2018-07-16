// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.cc.RuntimeCcDeploymentTarget;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import java.util.ArrayList;
import java.util.List;

public class OnDemandFinanceFormDataImpl implements OnDemandFinanceFormData {

   private FormInputModel mFormInputModel;
   private List<RuntimeCcDeploymentTarget>[] mCellCalculationDeployments;
   private int mSheetProtectionLevel;
   private boolean mNoCellCalcDeploymentsAllowed;
   private boolean mForSecondaryStructure;
   public List<String> mPerformanceStats = new ArrayList();
   private int[] mPositions;


   public FormInputModel getFormInputModel() {
      return this.mFormInputModel;
   }

   public void setFormInputModel(FormInputModel formInputModel) {
      this.mFormInputModel = formInputModel;
   }

   public List<RuntimeCcDeploymentTarget>[] getCellCalculationDeployments() {
      return this.mCellCalculationDeployments;
   }

   public void setCellCalculationDeployments(List<RuntimeCcDeploymentTarget>[] cellCalculationDeployments) {
      this.mCellCalculationDeployments = cellCalculationDeployments;
   }

   public int getSheetProtectionLevel() {
      return this.mSheetProtectionLevel;
   }

   public void setSheetProtectionLevel(int sheetProtectionLevel) {
      this.mSheetProtectionLevel = sheetProtectionLevel;
   }

   public boolean isNoCellCalcDeploymentsAllowed() {
      return this.mNoCellCalcDeploymentsAllowed;
   }

   public void setNoCellCalcDeploymentsAllowed(boolean noCellCalcDeploymentsAllowed) {
      this.mNoCellCalcDeploymentsAllowed = noCellCalcDeploymentsAllowed;
   }

   public boolean isForSecondaryStructure() {
      return this.mForSecondaryStructure;
   }

   public void setForSecondaryStructure(boolean forSecondaryStructure) {
      this.mForSecondaryStructure = forSecondaryStructure;
   }

   public void addPerformanceStat(String statistic) {
      this.mPerformanceStats.add(statistic);
   }

   public List<String> getPerformanceStats() {
      return this.mPerformanceStats;
   }

   public int[] getPositions() {
      return this.mPositions;
   }

   public void setPositions(int[] positions) {
      this.mPositions = positions;
   }
}
