// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.services;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.util.flatform.model.Properties;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.reader.XMLReader;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.FormConfigFactory;
import com.cedar.cp.util.xmlform.LookupInput;

import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class XMLFormService {

   MetaDataImpExpApplicationState applicationState = MetaDataImpExpApplicationState.getInstance();


   public boolean hasNewFinanceCubeInForm(XMLFormModel xmlFormModel) {
      boolean hasNewCube = false;
      if(xmlFormModel != null && xmlFormModel.getFormType() > 2 && !xmlFormModel.isIgnore()) {
         hasNewCube = !this.applicationState.isFinanceCubeExists(xmlFormModel.getFinanceCubeVisId());
         if(xmlFormModel.getFormType() == 4 && !hasNewCube) {
            Workbook workBook = xmlFormModel.getWorkBook();
            if(workBook != null) {
               List workSheetList = workBook.getWorksheets();
               if(workSheetList != null && workSheetList.size() > 0) {
                  Iterator i$ = workSheetList.iterator();

                  while(i$.hasNext()) {
                     Worksheet worksheet = (Worksheet)i$.next();
                     Properties sheetProperties = worksheet.getProperties();
                     if(sheetProperties != null && sheetProperties.size() > 0) {
                        String financeCubeVisId = (String)sheetProperties.get(WorkbookProperties.FINANCE_CUBE_VISID.toString());
                        hasNewCube = !this.applicationState.isFinanceCubeExists(financeCubeVisId);
                     }
                  }
               }
            }
         }
      }

      return hasNewCube;
   }

   public List<CommonImpExpItem> getFormsWithNewCube(List<CommonImpExpItem> selectedItems) {
      if(selectedItems != null && selectedItems.size() > 0) {
         ArrayList formList = new ArrayList();
         Iterator i$ = selectedItems.iterator();

         while(i$.hasNext()) {
            CommonImpExpItem commonImpExpItem = (CommonImpExpItem)i$.next();
            if(commonImpExpItem.getTreeNodeType() == 2 && commonImpExpItem instanceof XMLFormModel) {
               XMLFormModel formModel = (XMLFormModel)commonImpExpItem;
               if((formModel.getFormType() == 3 || formModel.getFormType() == 4) && !formModel.isIgnore()) {
                  boolean hasNewCube = this.hasNewFinanceCubeInForm(formModel);
                  if(hasNewCube) {
                     formList.add(commonImpExpItem);
                  }
               }
            }
         }

         return formList;
      } else {
         return null;
      }
   }

   public XmlForm getXMLFormByVisId(String visId) throws ValidationException {
      return visId != null && visId.trim().length() > 0?this.applicationState.getXmlForm(visId):null;
   }

   public String updateSchemaLocation(FormConfig formConfig) throws Exception {
      if(formConfig != null) {
         StringWriter writer = new StringWriter();
         formConfig.writeSchemaXml(writer, this.applicationState.getSchemaAttribute());
         return writer.toString();
      } else {
         return "";
      }
   }

   public FormConfig parseXMLFormDefByDefFilePath(String defFilePath) throws Exception {
      String defFileContent = this.readDefFile(defFilePath);
      return defFileContent != null && defFileContent.trim().length() > 0?FormConfigFactory.createStandardForm(defFileContent):null;
   }

   public String readDefFile(String defFilePath) throws Exception {
      if(defFilePath != null && !defFilePath.trim().equals("")) {
         StringBuilder strBuilder = new StringBuilder("");
         TFile defFile = new TFile(defFilePath);
         if(defFile != null && defFile.exists() && defFile.isFile()) {
            Scanner scanner = new Scanner(new TFileInputStream(defFilePath));

            while(scanner.hasNext()) {
               String content = scanner.next();
               strBuilder.append(content);
               strBuilder.append(" ");
            }
         }

         return strBuilder.toString();
      } else {
         return "";
      }
   }
   
   /**
    * Read and returns (as byte[]) the excel file with the given path
    */
	public byte[] readExcelFile(String excelFilePath) throws Exception {
		byte[] buffer = new byte[0];
		if (excelFilePath != null && !excelFilePath.trim().equals("")) {
			StringBuilder strBuilder = new StringBuilder("");
			TFile excelFile = new TFile(excelFilePath);
			if (excelFile != null && excelFile.exists() && excelFile.isFile()) {
				//buffer = new byte[(int) excelFile.length()];
				buffer = new byte[65536];

				InputStream fileInputStream = null;
				try {
					fileInputStream = new TFileInputStream(excelFile);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					
					int nRead;
					while ((nRead = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
						baos.write(buffer, 0, nRead);
					}
					baos.flush();
					buffer = baos.toByteArray();
//					if (fileInputStream.read(buffer) == -1) {
//						throw new IOException("EOF reached while trying to read the whole excel file");
//					}
				} finally {
					try {
						if (fileInputStream != null) {
							fileInputStream.close();
						}
					} catch (IOException e) {}
				}
				return buffer;
			}
		}
		return buffer;
	}

   public Workbook parseFlatFormWorkBook(String xml) throws Exception {
      XMLReader reader = new XMLReader();
      reader.init();
      StringReader sr = new StringReader(xml);
      reader.parseConfigFile(sr);
      Workbook workbook = reader.getWorkbook();
      return workbook;
   }

   public Hashtable<String, LookupInput> getLookupTableRefInForm(XMLFormModel formModel) {
      Hashtable lookupRefNameHash = new Hashtable();
      FormConfig formConfig = formModel.getFormConfig();
      if(formConfig != null) {
         Iterator iterator = formConfig.getInputs();

         while(iterator.hasNext()) {
            Object input = iterator.next();
            if(input instanceof LookupInput) {
               LookupInput lookupInput = (LookupInput)input;
               lookupRefNameHash.put(lookupInput.getId(), lookupInput);
            }
         }
      }

      return lookupRefNameHash;
   }
}
