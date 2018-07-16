// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.tc.apps.metadataimpexp.AbstractMetaDataApplication;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplication;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.ImportDuplicatedItemPanel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.ImportItemSelectionPanel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.ImportProgressDialog;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.PreImportListPanel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.SelectFileToImportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.SelectNewFinaceCubePanel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.ImportItemsTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.services.XMLFormService;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.ZipServices;
import de.schlichtherle.truezip.file.TFile;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JLabel;

public class MetaDataImportApplication extends AbstractMetaDataApplication {

   private static final int SELECT_IMPORT_FILE_STEP = 0;
   private static final int SELECT_ITEMS_TO_IMPORT_STEP = 1;
   private static final int SELECT_DUPLICATED_ITEMS_STEP = 2;
   private static final int SELECT_NEW_FINANCE_CUBE_STEP = 3;
   private static final int FINAL_IMPORT_LIST = 4;
   private String title = "Import";
   protected SelectFileToImportPanel selectFileToImportPanel = new SelectFileToImportPanel(this);
   protected ImportItemSelectionPanel importItemSelectionPanel = new ImportItemSelectionPanel(this);
   protected ImportDuplicatedItemPanel importDuplicatedItemPanel = new ImportDuplicatedItemPanel();
   protected SelectNewFinaceCubePanel selectNewFinaceCubePanel = new SelectNewFinaceCubePanel();
   protected PreImportListPanel preImportListPanel = new PreImportListPanel();
   protected List<CommonImpExpItem> duplicatedItems = null;
   protected List<CommonImpExpItem> selectedImportItems = null;
   private MetaDataImpExpApplication mainApplication = null;
   XMLFormService xmlFormService = new XMLFormService();
   private TFile importZIPFile = null;
   MetaDataImpExpApplicationState applicationState = MetaDataImpExpApplicationState.getInstance();


   public TFile getImportZIPFile() {
      return this.importZIPFile;
   }

   public void setImportZIPFile(TFile importFile) {
      this.importZIPFile = importFile;
   }

   public MetaDataImportApplication(MetaDataImpExpApplication application) {
      super(application);
      this.mainApplication = application;
      this.listPanels = new CommonPanel[]{this.selectFileToImportPanel, this.importItemSelectionPanel, this.importDuplicatedItemPanel, this.selectNewFinaceCubePanel, this.preImportListPanel};
      this.setupScreenWizards();
   }

   protected boolean checkData() {
      int stepId = this.currentStep.getStepNo();
      switch(stepId) {
      case 0:
         try {
            ZipServices.verifyImportZipFile(this.importZIPFile);
            return true;
         } catch (Exception var9) {
            this.mApplication.showErrorMessageDialog(var9.getMessage(), "Import error");
            return false;
         }
      case 1:
         ImportItemSelectionPanel panel = (ImportItemSelectionPanel)this.currentStep.getView();
         ImportItemsTableModel importItemTableModel = panel.getImportItemsTableModel();
         this.selectedImportItems = importItemTableModel.getSelectedItemsToImport();
         if(this.selectedImportItems != null && this.selectedImportItems.size() != 0) {
            this.duplicatedItems = (List)this.applicationState.getDuplicatedItems(this.selectedImportItems);
            if(this.duplicatedItems != null && this.duplicatedItems.size() > 0) {
               return true;
            }

            this.reBuildStepWizard();
            return true;
         }

         this.mApplication.showErrorMessageDialog("Please select item to import", this.title);
         return false;
      case 2:
         if(this.duplicatedItems != null && this.duplicatedItems.size() > 0) {
            Iterator i$ = this.duplicatedItems.iterator();

            while(i$.hasNext()) {
               CommonImpExpItem commonImpExp = (CommonImpExpItem)i$.next();
               if(commonImpExp.hasAlternativeName()) {
                  String alternativeName = commonImpExp.getAlternativeName();
                  if(alternativeName != null && !alternativeName.trim().equals("")) {
                     try {
                        UdefLookup e = null;
                        XmlForm xmlForm = null;
                        if(commonImpExp.getTreeNodeType() == 1) {
                           e = this.applicationState.getUdefLookup(commonImpExp.getAlternativeName());
                        }

                        if(commonImpExp.getTreeNodeType() == 2) {
                           xmlForm = this.xmlFormService.getXMLFormByVisId(commonImpExp.getAlternativeName());
                        }

                        if(e == null && xmlForm == null) {
                           continue;
                        }

                        this.mApplication.showErrorMessageDialog("Alternative name already exist", this.title);
                        commonImpExp.setHasError(true);
                        return false;
                     } catch (ValidationException var10) {
                        this.mApplication.showErrorMessageDialog(var10.getMessage(), this.title);
                        return false;
                     }
                  }

                  this.mApplication.showMessageDialog("Please enter alternative name", this.title);
                  return false;
               }
            }
         }

         this.reBuildStepWizard();
         return true;
      default:
         return true;
      }
   }

   private boolean canPerformSelectNewCubePanel() {
      if(this.selectedImportItems != null && this.selectedImportItems.size() > 0) {
         Iterator i$ = this.selectedImportItems.iterator();

         while(i$.hasNext()) {
            CommonImpExpItem commonImpExpItem = (CommonImpExpItem)i$.next();
            if(commonImpExpItem.getTreeNodeType() == 2 && commonImpExpItem instanceof XMLFormModel) {
               XMLFormModel formModel = (XMLFormModel)commonImpExpItem;
               boolean canPerform = this.xmlFormService.hasNewFinanceCubeInForm(formModel);
               if(canPerform) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   protected void doFinish() {
      if(this.selectedImportItems != null && this.selectedImportItems.size() > 0) {
         if(this.duplicatedItems != null && this.duplicatedItems.size() > 0) {
            Iterator message = this.duplicatedItems.iterator();

            while(message.hasNext()) {
               CommonImpExpItem progressBar = (CommonImpExpItem)message.next();
               this.selectedImportItems.remove(progressBar);
            }

            this.selectedImportItems.addAll(this.duplicatedItems);
         }

         if(this.isNecessaryToImport(this.selectedImportItems)) {
            JLabel message1 = new JLabel("Importing data", 0);
            ImportProgressDialog progressBar1 = new ImportProgressDialog(this.mainApplication.getMainFrame(), "Meta Data Importer", message1, this.selectedImportItems);
            progressBar1.runAndShow();
            if(progressBar1 instanceof ImportProgressDialog) {
               ImportProgressDialog dialog = (ImportProgressDialog)progressBar1;
               List failedItem = dialog.getImportFailedList();
               if(failedItem != null && failedItem.size() > 0) {
                  this.mApplication.showMessageDialog("Some items failed to import", this.title);
               } else {
                  this.mApplication.showMessageDialog("Import successful", this.title);
               }
            }
         } else {
            this.mApplication.showMessageDialog("Nothing to import", this.title);
         }

         ((PreImportListPanel)this.currentStep.getView()).displayImportResult();
         this.mainApplication.finish();
      }

   }

   private boolean isNecessaryToImport(List<CommonImpExpItem> selectedImportItems) {
      if(selectedImportItems != null && selectedImportItems.size() > 0) {
         Iterator i$ = selectedImportItems.iterator();

         while(i$.hasNext()) {
            CommonImpExpItem commonImpExpItem = (CommonImpExpItem)i$.next();
            if(commonImpExpItem.getTreeNodeType() != 0 && !commonImpExpItem.isIgnore()) {
               return true;
            }
         }
      }

      return false;
   }

   protected void fireScreenChange() {
      int stepNo = this.currentStep.getStepNo();
      CommonPanel panel = this.currentStep.getView();
      this.mApplication.getCenterPanel().showPanel(panel);
      switch(stepNo) {
      case 1:
         try {
            panel.init();
         } catch (Exception var4) {
            this.mApplication.showErrorMessageDialog(var4.getMessage(), "Error");
            var4.printStackTrace();
         }
         break;
      case 2:
         if(this.duplicatedItems != null && this.duplicatedItems.size() > 0) {
            panel.setData((Collection)this.duplicatedItems);
            panel.init();
         }
         break;
      case 3:
         List formHasNewCubeList = this.xmlFormService.getFormsWithNewCube(this.selectedImportItems);
         if(formHasNewCubeList != null && formHasNewCubeList.size() > 0) {
            panel.setData((Collection)formHasNewCubeList);
            panel.init();
         }
         break;
      case 4:
         panel.setData((Collection)this.selectedImportItems);
         panel.init();
      }

   }

   protected void reBuildStepWizard() {
      int stepId = this.currentStep.getStepNo();
      switch(stepId) {
      case 1:
         this.removeStep(2);
         if(!this.canPerformSelectNewCubePanel()) {
            this.removeStep(3);
         }
         break;
      case 2:
         if(!this.canPerformSelectNewCubePanel()) {
            this.removeStep(3);
         }
      }

   }
}
