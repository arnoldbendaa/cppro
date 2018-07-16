// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.metadata;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MetaData extends CommonElement {

   private String mXmlFormFileName = "";
   private String mLookupTableName = "";
   private Set<String> mXmlFormDefFileNameList = new HashSet();
   private Set<String> mLookupTableExportFileNameList = new HashSet();


   public String getXmlFormFileName() {
      return this.mXmlFormFileName;
   }

   public void setXmlFormFileName(String xmlFormFileName) {
      this.mXmlFormFileName = xmlFormFileName;
   }

   public String getLookupTableName() {
      return this.mLookupTableName;
   }

   public void setLookupTableName(String lookupTableName) {
      this.mLookupTableName = lookupTableName;
   }

   public Set<String> getXmlFormDefFileNameList() {
      return this.mXmlFormDefFileNameList;
   }

   public void setXmlFormDefFileNameList(Set<String> xmlFormDefFileNameList) {
      this.mXmlFormDefFileNameList.addAll(xmlFormDefFileNameList);
   }

   public Set<String> getLookupTableExportFileNameList() {
      return this.mLookupTableExportFileNameList;
   }

   public void setLookupTableExportFileNameList(Set<String> lookupTableExportFileNameList) {
      this.mLookupTableExportFileNameList = lookupTableExportFileNameList;
   }

   public void addXmlFormDefFileName(String xmlFormDefFileName) {
      this.mXmlFormDefFileNameList.add(xmlFormDefFileName);
   }

   public void addLookupTableExportFileName(String fileName) {
      this.mLookupTableExportFileNameList.add(fileName);
   }

   public String toXML() {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      strBuf.append("<meta-data>");
      strBuf.append("<xmlForm>").append(this.mXmlFormFileName).append("</xmlForm>");
      strBuf.append("<lookupTable>").append(this.mLookupTableName).append("</lookupTable>");
      Iterator fileName = this.mXmlFormDefFileNameList.iterator();

      while(fileName.hasNext()) {
         strBuf.append("<xmlFormDef>").append((String)fileName.next()).append("</xmlFormDef>");
      }

      fileName = this.mLookupTableExportFileNameList.iterator();

      while(fileName.hasNext()) {
         strBuf.append("<lookupTableExportExcelFile>").append((String)fileName.next()).append("</lookupTableExportExcelFile>");
      }

      strBuf.append("</meta-data>");
      return strBuf.toString();
   }
}
