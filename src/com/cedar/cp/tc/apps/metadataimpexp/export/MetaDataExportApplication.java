// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplication;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.export.ExportOperation;
import com.cedar.cp.tc.apps.metadataimpexp.export.SelectDestinationFileExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.export.SelectItemTypeExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.export.SelectLookupTablesExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.export.SelectXMLFormsExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.export.TaskListExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.WizardStep;
import com.cedar.cp.util.awt.FeedbackReceiver;
import com.cedar.cp.util.awt.ProcessingDialog;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingUtilities;

public class MetaDataExportApplication implements Observer {

   private MetaDataImpExpApplication mApplication = null;
   private WizardStep currentStep = null;
   private WizardStep firstStep = new WizardStep();
   private String title = "Export";
   private static final int SELECT_ITEM_TYPE_STEP = 0;
   private static final int SELECT_DESTINATION_FILE_STEP = 1;
   private static final int SELECT_XMLFORM_TO_EXPORT_STEP = 2;
   private static final int SELECT_LOOKUP_TABLE_TO_EXPORT_STEP = 3;
   private static final int TASK_LIST_STEP = 4;
   private static final int FINISH_STEP = 4;
   private SelectItemTypeExportPanel selectItemTypePanel = new SelectItemTypeExportPanel();
   private SelectDestinationFileExportPanel selectDesFilePanel = new SelectDestinationFileExportPanel();
   private SelectXMLFormsExportPanel selectXmlFormPanel = new SelectXMLFormsExportPanel();
   private SelectLookupTablesExportPanel selectLookupTablesPanel = new SelectLookupTablesExportPanel();
   private TaskListExportPanel taskListExportPanel = new TaskListExportPanel();
   private CommonPanel[] mPanelList;


   public MetaDataExportApplication(MetaDataImpExpApplication application) {
      this.mPanelList = new CommonPanel[]{this.selectItemTypePanel, this.selectDesFilePanel, this.selectXmlFormPanel, this.selectLookupTablesPanel, this.taskListExportPanel};
      this.mApplication = application;
      this.setupScreenWizards();
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
            this.doFinish();
         }
      }

   }

   private boolean checkData() {
      boolean result = true;
      String message = "";
      switch(this.currentStep.getStepNo()) {
      case 0:
         if(!this.isSelectItemType()) {
            message = "Select type of item to export";
            this.mApplication.showMessageDialog(message, this.title);
            result = false;
         }

         this.reBuildStepWizard();
         break;
      case 1:
         String sDesFile = this.selectDesFilePanel.getDestinationFileName();
         if(sDesFile == null) {
            message = "Select file to export";
            this.mApplication.showMessageDialog(message, this.title);
            result = false;
         } else if(!this.selectDesFilePanel.isOverwriteDesFile()) {
            message = sDesFile + " already exists.\nSelect another one to export";
            this.mApplication.showMessageDialog(message, this.title);
            return false;
         }
         break;
      case 2:
         if(!this.isSelectedExportItem(0)) {
            message = "Select XMLForm item to export";
            this.mApplication.showMessageDialog(message, this.title);
            return false;
         }
         break;
      case 3:
         if(!this.isSelectedExportItem(1)) {
            message = "Select Lookup Table item to export";
            this.mApplication.showMessageDialog(message, this.title);
            return false;
         }
      }

      return result;
   }

   private void reBuildStepWizard() {
      Collection itemTypes = MetaDataImpExpApplicationState.getInstance().getItemList(2);
      Iterator iterator = itemTypes.iterator();

      while(iterator.hasNext()) {
         CommonImpExpItem item = (CommonImpExpItem)iterator.next();
         switch(item.getId()) {
         case 0:
            if(item.isSelected()) {
               this.addStep(2);
            } else {
               this.removeStep(2);
               this.resetSelectedExportItem(MetaDataImpExpApplicationState.getInstance().getItemList(0));
            }
            break;
         case 1:
            if(item.isSelected()) {
               this.addStep(3);
            } else {
               this.removeStep(3);
               this.resetSelectedExportItem(MetaDataImpExpApplicationState.getInstance().getItemList(1));
            }
         }
      }

   }

   private void resetSelectedExportItem(Collection itemList) {
      Iterator iterator = itemList.iterator();

      while(iterator.hasNext()) {
         ((CommonImpExpItem)iterator.next()).setSelected(false);
      }

   }

   private void addStep(int stepNo) {
      for(WizardStep w = this.firstStep; w.getNextStep() != null; w = w.getNextStep()) {
         if(w.getStepNo() == stepNo && w.isDeleted()) {
            w.setDeleted(false);
            break;
         }
      }

   }

   private void removeStep(int stepNo) {
      for(WizardStep w = this.firstStep; w.getNextStep() != null; w = w.getNextStep()) {
         if(w.getStepNo() == stepNo) {
            w.setDeleted(true);
            break;
         }
      }

   }

   private boolean isSelectItemType() {
      Collection itemTypes = MetaDataImpExpApplicationState.getInstance().getItemList(2);
      Iterator iterator = itemTypes.iterator();

      do {
         if(!iterator.hasNext()) {
            return false;
         }
      } while(!((CommonImpExpItem)iterator.next()).isSelected());

      return true;
   }

	private void doFinish() {
		String message = "";
		
		//ExportOperation a = ExportOperation.getIntance();
		//ExportOperation b = ExportOperation.getIntance();
		// ???

		try {
			final ExportOperation e = ExportOperation.getIntance();
			e.setDestFileName(this.selectDesFilePanel.getDestinationFileName());
			this.mApplication.doingExport(6);
			final ProcessingDialog processingDialog = new ProcessingDialog(this.mApplication.getShowingDialog(), "Meta Data Export");
			e.setDoneFeedbackReceiver(new FeedbackReceiver()
			{
				public void done() {
					processingDialog.done();
				}
			});
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run() {
					e.execute();
					e.destroyApplicationState();
				}
			});
			processingDialog.setVisible(true);
			this.mApplication.doingExport(4);
			message = "Export successful";
			this.mApplication.showMessageDialog(message, this.title);
		} catch (Exception var4) {
			this.mApplication.doingExport(5);
			message = "Export fail. " + var4.getMessage();
			this.mApplication.showErrorMessageDialog(message, this.title);
		}
	}

   private void fireScreenChange() {
      CommonPanel panel = this.currentStep.getView();
      Object listItem = null;
      switch(this.currentStep.getStepNo()) {
      case 0:
         panel.setData(MetaDataImpExpApplicationState.getInstance().getItemList(2));
         this.mApplication.getCenterPanel().showPanel(panel);
         break;
      case 1:
         this.mApplication.getCenterPanel().showPanel(panel);
         break;
      case 2:
         panel.setData(MetaDataImpExpApplicationState.getInstance().getItemList(0));
         this.mApplication.getCenterPanel().showPanel(panel);
         break;
      case 3:
         panel.setData(MetaDataImpExpApplicationState.getInstance().getItemList(1));
         this.mApplication.getCenterPanel().showPanel(panel);
         break;
      case 4:
         panel.setData(MetaDataImpExpApplicationState.getInstance().getAllTaskList());
         this.mApplication.getCenterPanel().showPanel(panel);
         break;
      default:
         return;
      }

   }

   private void setupScreenWizards() {
      WizardStep w = this.firstStep;

      for(int i = 0; i < this.mPanelList.length; ++i) {
         this.mApplication.getCenterPanel().addPanel(this.mPanelList[i]);
         w.setStepNo(i);
         w.setNextStep(new WizardStep());
         w.getNextStep().setPreviousStep(w);
         w.setView(this.mPanelList[i]);
         w = w.getNextStep();
      }

      w = w.getPreviousStep();
      w.getNextStep().setPreviousStep((WizardStep)null);
      w.setNextStep((WizardStep)null);
      this.currentStep = this.firstStep;
      this.changeWizadsStep();
   }

   private void changeWizadsStep() {
      this.fireScreenChange();
      if(this.currentStep.getPreviousStep() == null) {
         this.mApplication.setIsFirstStep();
      } else if(this.currentStep.getNextStep() == null) {
         this.mApplication.setIsLastStep();
      } else {
         this.mApplication.setIsInMiddleStep();
      }

   }

   private boolean isSelectedExportItem(int type) {
      Collection listItem = MetaDataImpExpApplicationState.getInstance().getItemList(type);
      if(listItem == null) {
         return true;
      } else {
         Iterator item = listItem.iterator();

         do {
            if(!item.hasNext()) {
               return false;
            }
         } while(!((CommonImpExpItem)item.next()).isSelected());

         return true;
      }
   }
}
