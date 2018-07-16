// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp;

import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplication;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.WizardStep;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public abstract class AbstractMetaDataApplication implements Observer {

   protected MetaDataImpExpApplication mApplication;
   protected WizardStep currentStep = null;
   protected CommonImpExpItem[] listItemType = null;
   protected HashMap<Integer, CommonImpExpItem[]> mListItemToExportMap = new HashMap();
   protected WizardStep firstStep = new WizardStep();
   protected CommonPanel[] listPanels = null;


   public AbstractMetaDataApplication(MetaDataImpExpApplication application) {
      this.mApplication = application;
   }

   protected void setupScreenWizards() {
      WizardStep w = this.firstStep;

      for(int i = 0; i < this.listPanels.length; ++i) {
         this.mApplication.getCenterPanel().addPanel(this.listPanels[i]);
         w.setStepNo(i);
         w.setNextStep(new WizardStep());
         w.getNextStep().setPreviousStep(w);
         w.setView(this.listPanels[i]);
         w = w.getNextStep();
      }

      w = w.getPreviousStep();
      w.getNextStep().setPreviousStep((WizardStep)null);
      w.setNextStep((WizardStep)null);
      this.currentStep = this.firstStep;
      this.changeWizadsStep();
   }

   public void update(Observable o, Object arg) {
      if(arg instanceof Integer) {
         int action = ((Integer)arg).intValue();
         switch(action) {
         case 0:
            if(this.currentStep.getNextStep() != null) {
               if(!this.checkData()) {
                  return;
               }

               for(this.currentStep = this.currentStep.getNextStep(); this.currentStep.isDeleted() && this.currentStep.getNextStep() != null; this.currentStep = this.currentStep.getNextStep()) {
                  ;
               }

               this.changeWizadsStep();
            }
            break;
         case 1:
            if(this.currentStep.getPreviousStep() != null) {
               for(this.currentStep = this.currentStep.getPreviousStep(); this.currentStep.isDeleted() && this.currentStep.getPreviousStep() != null; this.currentStep = this.currentStep.getPreviousStep()) {
                  ;
               }

               this.changeWizadsStep();
            }
            break;
         case 2:
            if(!this.checkData()) {
               return;
            }

            this.doFinish();
         }
      }

   }

   protected void changeWizadsStep() {
      this.fireScreenChange();
      if(this.currentStep.getPreviousStep() == null) {
         this.mApplication.setIsFirstStep();
      } else if(this.currentStep.getNextStep() == null) {
         this.mApplication.setIsLastStep();
      } else {
         this.mApplication.setIsInMiddleStep();
      }

   }

   protected abstract void reBuildStepWizard();

   protected abstract void doFinish();

   protected abstract void fireScreenChange();

   protected abstract boolean checkData();

   protected void addStep(int stepNo) {
      for(WizardStep w = this.firstStep; w.getNextStep() != null; w = w.getNextStep()) {
         if(w.getStepNo() == stepNo && w.isDeleted()) {
            w.setDeleted(false);
            break;
         }
      }

   }

   protected void removeStep(int stepNo) {
      for(WizardStep w = this.firstStep; w.getNextStep() != null; w = w.getNextStep()) {
         if(w.getStepNo() == stepNo) {
            w.setDeleted(true);
            break;
         }
      }

   }
}
