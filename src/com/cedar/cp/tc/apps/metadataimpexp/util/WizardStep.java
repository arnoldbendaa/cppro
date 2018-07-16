// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;

import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;

public class WizardStep {

   private int stepNo;
   private String name;
   private WizardStep previousStep = null;
   private WizardStep nextStep = null;
   private boolean isDeleted = false;
   private CommonPanel view;


   public int getStepNo() {
      return this.stepNo;
   }

   public void setStepNo(int stepNo) {
      this.stepNo = stepNo;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public WizardStep getPreviousStep() {
      return this.previousStep;
   }

   public void setPreviousStep(WizardStep previousStep) {
      this.previousStep = previousStep;
   }

   public WizardStep getNextStep() {
      return this.nextStep;
   }

   public void setNextStep(WizardStep nextStep) {
      this.nextStep = nextStep;
   }

   public CommonPanel getView() {
      return this.view;
   }

   public void setView(CommonPanel view) {
      this.view = view;
   }

   public String toString() {
      return String.valueOf(this.stepNo);
   }

   public boolean isDeleted() {
      return this.isDeleted;
   }

   public void setDeleted(boolean isDeleted) {
      this.isDeleted = isDeleted;
   }
}
