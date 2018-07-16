// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.mappingtemplate;

import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplate;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateImpl;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.impl.report.mappingtemplate.ReportMappingTemplateEditorSessionImpl;

public class ReportMappingTemplateAdapter implements ReportMappingTemplate {

   private ReportMappingTemplateImpl mEditorData;
   private ReportMappingTemplateEditorSessionImpl mEditorSessionImpl;


   public ReportMappingTemplateAdapter(ReportMappingTemplateEditorSessionImpl e, ReportMappingTemplateImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ReportMappingTemplateEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ReportMappingTemplateImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ReportMappingTemplatePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public String getDocumentName() {
      return this.mEditorData.getDocumentName();
   }

   public byte[] getDocument() {
      return this.mEditorData.getDocument();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setDocumentName(String p) {
      this.mEditorData.setDocumentName(p);
   }

   public void setDocument(byte[] p) {
      this.mEditorData.setDocument(p);
   }
}
