// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.model;

import java.util.ArrayList;

public class XMLFormDigestRuleModel {

   private String visId = null;
   private String description = null;
   private int type = 0;
   private int financeCubeId = 0;
   private String financeCubeVisId = null;
   private String defFileName = null;
   private String excelFileName = null; 
   private ArrayList<String> lookupTableVisIdList = new ArrayList();


   public String getFinanceCubeVisId() {
      return this.financeCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.financeCubeVisId = financeCubeVisId;
   }

   public String getVisId() {
      return this.visId;
   }

   public void setVisId(String visId) {
      this.visId = visId;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getType() {
      return this.type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getFinanceCubeId() {
      return this.financeCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.financeCubeId = financeCubeId;
   }

   public String getDefFileName() {
      return this.defFileName;
   }

   public void setDefFileName(String defFileName) {
      this.defFileName = defFileName;
   }
   
   public ArrayList<String> getLookupTableVisIdList() {
      return this.lookupTableVisIdList;
   }

   public void lookupTableVisIdList(ArrayList<String> lookupTableList) {
      this.lookupTableVisIdList = lookupTableList;
   }

   public void addLookupTable(String table) {
      this.lookupTableVisIdList.add(table);
   }
   
	/**
	 * Get excel file name
	 * 
	 * @return
	 */
	public String getExcelFileName() {
		return this.excelFileName;
	}

	/**
	 * Set excel file name
	 * 
	 * @param excelFileName
	 */
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
}
