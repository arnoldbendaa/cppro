// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.template;

import com.cedar.cp.api.report.template.ReportTemplate;
import com.cedar.cp.dto.report.template.ReportTemplateImpl;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.impl.report.template.ReportTemplateEditorSessionImpl;

public class ReportTemplateAdapter implements ReportTemplate {

   private ReportTemplateImpl mEditorData;
   private ReportTemplateEditorSessionImpl mEditorSessionImpl;


   public ReportTemplateAdapter(ReportTemplateEditorSessionImpl e, ReportTemplateImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ReportTemplateEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ReportTemplateImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ReportTemplatePK paramKey) {
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
