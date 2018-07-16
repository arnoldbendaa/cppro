// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.utc.struts.approver.BudgetCycleStatusForm;

public class ChangeBudgetCycleForm extends BudgetCycleStatusForm {

   private int modelId;
   private int bcId;
   private int seId;
   private int state;
   private String pageSource;
   private String mSubmitModelName;
   private String mSubmitCycleName;


   public int getBcId() {
      return this.bcId;
   }

   public void setBcId(int bcId) {
      this.bcId = bcId;
   }

   public int getSeId() {
      return this.seId;
   }

   public void setSeId(int seId) {
      this.seId = seId;
   }

   public String getPageSource() {
      return this.pageSource;
   }

   public void setPageSource(String pageSource) {
      this.pageSource = pageSource;
   }

   public int getState() {
      return this.state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public int getModelId() {
      return this.modelId;
   }

   public void setModelId(int modelId) {
      this.modelId = modelId;
   }

   public String getSubmitModelName() {
      return this.mSubmitModelName;
   }

   public void setSubmitModelName(String submitModelName) {
      this.mSubmitModelName = submitModelName;
   }

   public String getSubmitCycleName() {
      return this.mSubmitCycleName;
   }

   public void setSubmitCycleName(String submitCycleName) {
      this.mSubmitCycleName = submitCycleName;
   }
}
