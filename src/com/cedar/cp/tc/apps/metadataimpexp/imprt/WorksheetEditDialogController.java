// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
//import com.cedar.cp.tc.apps.xmlform.flatform.FlatFormDesignPanelController;
//import com.cedar.cp.tc.apps.xmlform.flatform.WorksheetPropertiesDialog;
import com.cedar.cp.util.flatform.model.Properties;
import com.cedar.cp.util.flatform.model.Properties.PropEntry;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;

public class WorksheetEditDialogController {

   private WorksheetEditDialog mWorksheetEditDialog = null;
   private Workbook mWorkbook = null;
   private Worksheet mCurrentWorksheet = null;
   private Map<String, WorksheetEditDialogController$WorksheetWrapper> mWorkSheetWrapperMap = new HashMap();


   public WorksheetEditDialogController() {
      this.mWorksheetEditDialog = new WorksheetEditDialog(this);
   }

   public void setWorkbook(Workbook workbook) {
      this.mWorkbook = workbook;
      this.updateWorksheetsTree(workbook);
      this.buildWorkSheetWrapperMap(workbook);
   }

   private void buildWorkSheetWrapperMap(Workbook workbook) {
      List ws = workbook.getWorksheets();
      Iterator tws = ws.iterator();

      while(tws.hasNext()) {
         Worksheet worksheet = (Worksheet)tws.next();
         WorksheetEditDialogController$WorksheetWrapper worksheetWrapper = new WorksheetEditDialogController$WorksheetWrapper(this, worksheet);
         this.mWorkSheetWrapperMap.put(worksheet.getName(), worksheetWrapper);
      }

   }

   private static Properties cloneWorksheetProperties(Properties pros) {
      Properties op = new Properties();
      Set propKeys = pros.keySet();
      Iterator i = propKeys.iterator();


      while(i.hasNext()) {
         String key = (String)i.next();
         PropEntry propEntry = new PropEntry();
         propEntry.setName(key);
         propEntry.setValue((String)pros.get(key));
         op.addPropEntry(propEntry);
      }

      return op;
   }

   public void showWorksheetEditDialog() {
      this.mWorksheetEditDialog.setSize(700, 400);
      this.mWorksheetEditDialog.setModal(true);
      this.mWorksheetEditDialog.setVisible(true);
   }

   private void updateWorksheetsTree(Workbook workbook) {
      DefaultMutableTreeNode root = this.mWorksheetEditDialog.getTreeRoot();
      root.removeAllChildren();
      List wss = workbook.getWorksheets();
      Iterator k = wss.iterator();

      while(k.hasNext()) {
         root.add(new DefaultMutableTreeNode(((Worksheet)k.next()).getName()));
      }

      this.mWorksheetEditDialog.refresh();
   }

   public void handleSelectedWorksheet(DefaultMutableTreeNode node) {
      if(this.mWorkbook != null) {
         String wsName = (String)node.getUserObject();
         WorksheetEditDialogController$WorksheetWrapper workSheetWrapper = (WorksheetEditDialogController$WorksheetWrapper)this.mWorkSheetWrapperMap.get(wsName);
         Worksheet workSheet = workSheetWrapper.getWorksheet();
         if(workSheet != null) {
            this.mCurrentWorksheet = workSheet;
            this.updateWorksheetDialogProperties(workSheet.getProperties());
            this.mWorksheetEditDialog.enableRestoreButtons(WorksheetEditDialogController$WorksheetWrapper.accessMethod000(workSheetWrapper));
         }
      }

   }

   private void updateWorksheetDialogProperties(Properties properties) {
      Properties props = properties;
      if(properties == null) {
         props = this.buildEmptyWorksheetPrperties();
      }

      this.mWorksheetEditDialog.setWSPropertiesData(props);
   }

   private Properties buildEmptyWorksheetPrperties() {
      return new Properties();
   }

   public void editWorksheetProperties() {
//arnold all
	   //      if(this.mCurrentWorksheet != null) {
//         Properties properties = this.mCurrentWorksheet.getProperties();
//         if(properties == null) {
//            properties = new Properties();
//         }
//
//         CPConnection cpConnection = MetaDataImpExpApplicationState.getInstance().getConnection();
//         WorksheetPropertiesDialog dialog = new WorksheetPropertiesDialog((JFrame)null, (FlatFormDesignPanelController)null, cpConnection, properties);
//         if(dialog.doModal() && !properties.isEmpty()) {
//            WorksheetEditDialogController$WorksheetWrapper workSheetWrapper = (WorksheetEditDialogController$WorksheetWrapper)this.mWorkSheetWrapperMap.get(this.mCurrentWorksheet.getName());
//            workSheetWrapper.setProperties(properties);
//            this.updateWorksheetDialogProperties(properties);
//            this.mWorksheetEditDialog.enableRestoreButtons(WorksheetEditDialogController$WorksheetWrapper.accessMethod000(workSheetWrapper));
//         }
//
//      }
   }

   public void restoreWorksheetProperties() {
      if(this.mCurrentWorksheet != null) {
         WorksheetEditDialogController$WorksheetWrapper worksheetWrapper = (WorksheetEditDialogController$WorksheetWrapper)this.mWorkSheetWrapperMap.get(this.mCurrentWorksheet.getName());
         if(WorksheetEditDialogController$WorksheetWrapper.accessMethod000(worksheetWrapper)) {
            worksheetWrapper.restoreWorksheetProperties();
            Properties cProps = worksheetWrapper.getWorksheet().getProperties();
            this.updateWorksheetDialogProperties(cProps);
            this.mWorksheetEditDialog.enableRestoreButtons(WorksheetEditDialogController$WorksheetWrapper.accessMethod000(worksheetWrapper));
         }
      }

   }

   public void handleOKButtonClient() {
      this.mWorksheetEditDialog.dispose();
   }

   public void handleCancelButtonClient() {
      boolean isWorksheetModified = false;
      Set keyShet = this.mWorkSheetWrapperMap.keySet();
      Iterator ans = keyShet.iterator();

      while(ans.hasNext()) {
         String i = (String)ans.next();
         if(WorksheetEditDialogController$WorksheetWrapper.accessMethod000((WorksheetEditDialogController$WorksheetWrapper)this.mWorkSheetWrapperMap.get(i))) {
            isWorksheetModified = true;
            break;
         }
      }

      if(isWorksheetModified) {
         int ans1 = JOptionPane.showConfirmDialog(this.mWorksheetEditDialog, "Some worksheet has been modified.\nSave changed?", "Edit worksheet propeties", 1, 3);
         if(ans1 == 1) {
            Iterator i1 = keyShet.iterator();

            while(i1.hasNext()) {
               String key = (String)i1.next();
               if(WorksheetEditDialogController$WorksheetWrapper.accessMethod000((WorksheetEditDialogController$WorksheetWrapper)this.mWorkSheetWrapperMap.get(key))) {
                  ((WorksheetEditDialogController$WorksheetWrapper)this.mWorkSheetWrapperMap.get(key)).restoreWorksheetProperties();
               }
            }
         } else if(ans1 == 2) {
            return;
         }
      }

      this.mWorkSheetWrapperMap.clear();
      this.mWorksheetEditDialog.dispose();
   }

   // $FF: synthetic method
   static Properties accessMethod100(Properties x0) {
      return cloneWorksheetProperties(x0);
   }
}
