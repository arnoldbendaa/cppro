// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreport;

import com.cedar.cp.api.xmlreport.XmlReport;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import java.io.Serializable;

public class XmlReportImpl implements XmlReport, Serializable, Cloneable {

   private Object mPrimaryKey;
   private int mXmlReportFolderId;
   private String mVisId;
   private int mUserId;
   private String mDefinition;
   private int mVersionNum;


   public XmlReportImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mXmlReportFolderId = 0;
      this.mVisId = "";
      this.mUserId = 0;
      this.mDefinition = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (XmlReportPK)paramKey;
   }

   public int getXmlReportFolderId() {
      return this.mXmlReportFolderId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getDefinition() {
      return this.mDefinition;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setXmlReportFolderId(int paramXmlReportFolderId) {
      this.mXmlReportFolderId = paramXmlReportFolderId;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setUserId(int paramUserId) {
      this.mUserId = paramUserId;
   }

   public void setDefinition(String paramDefinition) {
      this.mDefinition = paramDefinition;
   }
}
