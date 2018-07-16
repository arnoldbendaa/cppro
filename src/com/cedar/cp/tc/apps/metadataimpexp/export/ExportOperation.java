// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.export.ExportException;
import com.cedar.cp.tc.apps.metadataimpexp.export.LookupTableExportCommand;
import com.cedar.cp.tc.apps.metadataimpexp.export.MetaDataExportCommand;
import com.cedar.cp.tc.apps.metadataimpexp.export.XMLFormExportCommand;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.ZipServices;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.metadata.MetaData;
import com.cedar.cp.util.awt.FeedbackReceiver;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.SwingWorker;

public class ExportOperation extends SwingWorker<Void, Void> {

   private FeedbackReceiver doneFeedbackReceiver = null;
   private static ExportOperation intance;
   private String destFileName = "";
   private static MetaDataImpExpApplicationState applicationState;

   public static ExportOperation getIntance() {
      if(intance == null) {
         intance = new ExportOperation();
         applicationState = MetaDataImpExpApplicationState.getInstance();
      }

      return intance;
   }

   public void setDestFileName(String destFileName) {
      this.destFileName = destFileName;
   }

   public void doExport() throws ExportException {
      boolean isExportXMLForm = false;
      boolean isExportLookupTables = false;
      if(this.destFileName.length() != 0) {
         XMLFormExportCommand xmlFormExportCommand = null;
         LookupTableExportCommand lookupTableExportCommand = null;
         Collection xmlForms = applicationState.getSelectedExportItemList(0);
         if(!xmlForms.isEmpty()) {
            xmlFormExportCommand = new XMLFormExportCommand(xmlForms);
            xmlFormExportCommand.execute();
            isExportXMLForm = true;
         }

         Collection lookupTables = applicationState.getSelectedExportItemList(1);
         if(xmlFormExportCommand != null) {
            lookupTables.addAll(xmlFormExportCommand.getLookupTableExportList());
         }

         if(!lookupTables.isEmpty()) {
            HashMap metaData = new HashMap();
            Iterator metaDataExportCommand = lookupTables.iterator();

            while(metaDataExportCommand.hasNext()) {
               CommonImpExpItem fileList = (CommonImpExpItem)metaDataExportCommand.next();
               metaData.put(fileList.getItemName(), fileList);
            }

            lookupTables.clear();
            Set metaDataExportCommand2 = metaData.keySet();
            Iterator fileList1 = metaDataExportCommand2.iterator();

            while(fileList1.hasNext()) {
               lookupTables.add(metaData.get(fileList1.next()));
            }

            if(!lookupTables.isEmpty()) {
               lookupTableExportCommand = new LookupTableExportCommand(lookupTables);
               lookupTableExportCommand.execute();
               isExportLookupTables = true;
            }
         }

         MetaData metaData1 = new MetaData();
         if(isExportXMLForm) {
            metaData1.setXmlFormFileName("xmlforms.xml");
         }

         if(isExportLookupTables) {
            metaData1.setLookupTableName("lokuptbl.xml");
         }

         if(xmlFormExportCommand != null) {
            metaData1.setXmlFormDefFileNameList(xmlFormExportCommand.getXmlFormDefTempFileList().keySet());
         }

         if(lookupTableExportCommand != null) {
            metaData1.setLookupTableExportFileNameList(lookupTableExportCommand.getLookupTableExpExcelTempFileList().keySet());
         }

         MetaDataExportCommand metaDataExportCommand1 = new MetaDataExportCommand(metaData1);
         metaDataExportCommand1.execute();
         HashMap fileList2 = new HashMap();
         if(xmlFormExportCommand != null) {
            fileList2.putAll(xmlFormExportCommand.getXmlFormTempFile());
            fileList2.putAll(xmlFormExportCommand.getXmlForExcelTempFileList()); // add excel files to the list of packaged files
            fileList2.putAll(xmlFormExportCommand.getXmlFormDefTempFileList());
         }

         if(lookupTableExportCommand != null) {
            fileList2.putAll(lookupTableExportCommand.getLookupTableTempFile());
            fileList2.putAll(lookupTableExportCommand.getLookupTableExpExcelTempFileList());
         }

         if(metaDataExportCommand1 != null) {
            fileList2.putAll(metaDataExportCommand1.getMetaDataTempFileList());
         }

         try {
            ZipServices.zipFiles(fileList2, this.destFileName);
         } catch (IllegalArgumentException var11) {
            throw new ExportException(var11.getMessage());
         } catch (IOException var12) {
            throw new ExportException(var12.getMessage());
         }
      }
   }

   protected Void doInBackground() throws Exception {
      this.doExport();
      return null;
   }

   protected void done() {
      if(this.doneFeedbackReceiver != null) {
         this.doneFeedbackReceiver.done();
      }

   }

   public FeedbackReceiver getDoneFeedbackReceiver() {
      return this.doneFeedbackReceiver;
   }

   public void setDoneFeedbackReceiver(FeedbackReceiver doneFeedbackReceiver) {
      this.doneFeedbackReceiver = doneFeedbackReceiver;
   }
   
	public void destroyApplicationState() {
		intance = null;
	}
}
