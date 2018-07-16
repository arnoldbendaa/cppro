// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.model;

import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.xmlform.FormConfig;

public class XMLFormModel extends CommonImpExpItem {

   private int formType = 0;
   private String defFileName = null;
   private int financeCubeId = 0;
   private String financeCubeVisId = null;
   private FormConfig formConfig = null;
   private Workbook workBook = null;
   private String defFileContent = null;
   private byte[] excelFile = new byte[0];

   public Workbook getWorkBook() {
      return this.workBook;
   }

   public void setWorkBook(Workbook workBook) {
      this.workBook = workBook;
   }

   public String getDefFileContent() {
      return this.defFileContent;
   }

   public void setDefFileContent(String defFileContent) {
      this.defFileContent = defFileContent;
   }

   public FormConfig getFormConfig() {
      return this.formConfig;
   }

   public void setFormConfig(FormConfig formConfig) {
      this.formConfig = formConfig;
   }

   public String getFinanceCubeVisId() {
      return this.financeCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.financeCubeVisId = financeCubeVisId;
   }

   public int getFinanceCubeId() {
      return this.financeCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.financeCubeId = financeCubeId;
   }

   public int getFormType() {
      return this.formType;
   }

   public void setFormType(int formType) {
      this.formType = formType;
   }

   public String getDefFileName() {
      return this.defFileName;
   }

   public void setDefFileName(String defFileName) {
      this.defFileName = defFileName;
   }
   
   /**
    * Get excel file
    */
	public byte[] getExcelFile() {
		return this.excelFile;
	}
	
	/**
	 * Set excel file
	 */
	public void setExcelFile(byte[] excelFile) {
		this.excelFile = excelFile;
	}
}
