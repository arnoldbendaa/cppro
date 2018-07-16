// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.export.AbstractImpExpCommand;
import com.cedar.cp.tc.apps.metadataimpexp.export.ExportException;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.AbstractXMLFormExportProcessing;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.NonXMLFlatFormExportProcessing;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.XMLFlatFormExportProcessing;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.XMLFormExportProcessingException;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.FileServices;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpXMLForm;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpXMLForms;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class XMLFormExportCommand extends AbstractImpExpCommand {

   private Collection xmlForms = null;
   private HashMap<String, File> xmlFormTempFile = new HashMap();
   private HashMap<String, File> xmlFormDefTempFileList = new HashMap();
   private HashMap<String, File> xmlFormExcelTempFileList = new HashMap();
   private HashSet lookupTableExportList = new HashSet();


   public XMLFormExportCommand(Collection xmlForms) {
      this.xmlForms = xmlForms;
   }

   private Set retrieveLookupTable(ImpExpXMLForm parsedForm) {
      HashSet result = new HashSet();
      Set lookupTables = parsedForm.getLookupTableVisIds();
      if(lookupTables != null) {
         CommonImpExpItem item = null;
         Iterator lt = lookupTables.iterator();

         while(lt.hasNext()) {
            item = new CommonImpExpItem();
            item.setItemName((String)lt.next());
            result.add(item);
         }
      }

      return result;
   }

   public void execute() throws ExportException {
      File temFile = null;
      this.lookupTableExportList.clear();

      try {
         ImpExpXMLForms e = new ImpExpXMLForms();
         Iterator iterator = this.xmlForms.iterator();

         while(iterator.hasNext()) {
            CommonImpExpItem item = (CommonImpExpItem)iterator.next();
            XmlForm xmlForm = MetaDataImpExpApplicationState.getInstance().getXmlForm(item.getItemName());
            ImpExpXMLForm processedForm = null;

            try {
               processedForm = this.getXMLFormExportProcessing(xmlForm.getType()).process(xmlForm);
            } catch (XMLFormExportProcessingException var8) {
               continue;
            }

            if(processedForm != null) {
               e.addImpExpXMLForm(processedForm);
               if(processedForm.getType() != 4) {
                  this.lookupTableExportList.addAll(this.retrieveLookupTable(processedForm));
               }

               temFile = FileServices.createTemplateFile(processedForm.getDefFileName());
               this.xmlFormDefTempFileList.put(processedForm.getDefFileName(), temFile);
               FileServices.writeDataToFile(temFile, processedForm.getDefinition());
               
               if (processedForm.getType() == 6) {
            	   temFile = FileServices.createTemplateFile(processedForm.getExcelFileName());
                   this.xmlFormExcelTempFileList.put(processedForm.getExcelFileName(), temFile);
                   FileServices.writeDataToFile(temFile, xmlForm.getExcelFile());
               }
            }
         }

         temFile = FileServices.createTemplateFile("xmlforms.xml");
         this.xmlFormTempFile.put("xmlforms.xml", temFile);
         FileServices.writeDataToFile(temFile, e.toXML());
      } catch (ValidationException var9) {
         throw new ExportException(var9.getMessage());
      } catch (IOException var10) {
         throw new ExportException(var10.getMessage());
      }
   }

   private AbstractXMLFormExportProcessing getXMLFormExportProcessing(int type) {
      Object xmlFormProcessing = null;
      switch(type) {
      case 4: // flatform
         xmlFormProcessing = new XMLFlatFormExportProcessing();
         break;
      case 6: // xcellform (xls)
          xmlFormProcessing = new XMLFlatFormExportProcessing();
          break;
      default:
         xmlFormProcessing = new NonXMLFlatFormExportProcessing();
      }

      return (AbstractXMLFormExportProcessing)xmlFormProcessing;
   }

   public HashMap<String, File> getXmlFormTempFile() {
      return this.xmlFormTempFile;
   }

   public HashMap<String, File> getXmlFormDefTempFileList() {
      return this.xmlFormDefTempFileList;
   }
   
	public HashMap<String, File> getXmlForExcelTempFileList() {
		return this.xmlFormExcelTempFileList;
	}

   public HashSet getLookupTableExportList() {
      return this.lookupTableExportList;
   }
}
