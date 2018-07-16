// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreport;

import com.cedar.cp.api.xmlreport.XmlReport;
import com.cedar.cp.dto.xmlreport.XmlReportImpl;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.impl.xmlreport.XmlReportEditorSessionImpl;

public class XmlReportAdapter implements XmlReport {

   private XmlReportImpl mEditorData;
   private XmlReportEditorSessionImpl mEditorSessionImpl;


   public XmlReportAdapter(XmlReportEditorSessionImpl e, XmlReportImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected XmlReportEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected XmlReportImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(XmlReportPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getXmlReportFolderId() {
      return this.mEditorData.getXmlReportFolderId();
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public int getUserId() {
      return this.mEditorData.getUserId();
   }

   public String getDefinition() {
      return this.mEditorData.getDefinition();
   }

   public void setXmlReportFolderId(int p) {
      this.mEditorData.setXmlReportFolderId(p);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setUserId(int p) {
      this.mEditorData.setUserId(p);
   }

   public void setDefinition(String p) {
      this.mEditorData.setDefinition(p);
   }
}
