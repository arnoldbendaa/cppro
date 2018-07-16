// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;
import java.util.List;

public class ImpExpLookupTable extends CommonElement {

   private String mVisId;
   private String mDescription;
   private String mGenTableName;
   private boolean mAutoSubmit;
   private List mTableData;
   private List mColumnDef;
   private String mExportExcelFile;


   public boolean isAutoSubmit() {
      return this.mAutoSubmit;
   }

   public void setAutoSubmit(boolean autoSubmit) {
      this.mAutoSubmit = autoSubmit;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String getGenTableName() {
      return this.mGenTableName;
   }

   public void setGenTableName(String genTableName) {
      this.mGenTableName = genTableName;
   }

   public String toXML() {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("<lookupTable>");
      strBuf.append("<visId>").append(this.replaceSpecialXMLCharacter(this.mVisId)).append("</visId>");
      strBuf.append("<description>").append(this.replaceSpecialXMLCharacter(this.mDescription)).append("</description>");
      strBuf.append("<genTableName>").append(this.mGenTableName).append("</genTableName>");
      strBuf.append("<autoSubmit>").append(this.isAutoSubmit()).append("</autoSubmit>");
      strBuf.append("<exportExcelFile>").append(this.getExportExcelFile()).append("</exportExcelFile>");
      strBuf.append("</lookupTable>");
      return strBuf.toString();
   }

   public String getExportExcelFile() {
      return this.mVisId + ".xls";
   }

   public List getTableData() {
      return this.mTableData;
   }

   public void setTableData(List tableData) {
      this.mTableData = tableData;
   }

   public List getColumnDef() {
      return this.mColumnDef;
   }

   public void setColumnDef(List columnDef) {
      this.mColumnDef = columnDef;
   }
}
