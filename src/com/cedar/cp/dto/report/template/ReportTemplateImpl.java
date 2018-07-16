// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.template;

import com.cedar.cp.api.report.template.ReportTemplate;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import java.io.Serializable;

public class ReportTemplateImpl implements ReportTemplate, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private String mDocumentName;
   private byte[] mDocument;
   private int mVersionNum;


   public ReportTemplateImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mDocumentName = "";
      this.mDocument = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ReportTemplatePK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getDocumentName() {
      return this.mDocumentName;
   }

   public byte[] getDocument() {
      return this.mDocument;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setDocumentName(String paramDocumentName) {
      this.mDocumentName = paramDocumentName;
   }

   public void setDocument(byte[] paramDocument) {
      this.mDocument = paramDocument;
   }
}
