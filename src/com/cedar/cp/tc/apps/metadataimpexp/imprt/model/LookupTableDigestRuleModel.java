// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.model;


public class LookupTableDigestRuleModel {

   private String visId = null;
   private String description = null;
   private String genTableName = null;
   private boolean autoSubmit = false;
   private String exportExcelFile = null;


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

   public String getGenTableName() {
      return this.genTableName;
   }

   public void setGenTableName(String genTableName) {
      this.genTableName = genTableName;
   }

   public boolean isAutoSubmit() {
      return this.autoSubmit;
   }

   public void setAutoSubmit(boolean autoSubmit) {
      this.autoSubmit = autoSubmit;
   }

   public String getExportExcelFile() {
      return this.exportExcelFile;
   }

   public void setExportExcelFile(String exportExcelFile) {
      this.exportExcelFile = exportExcelFile;
   }
}
