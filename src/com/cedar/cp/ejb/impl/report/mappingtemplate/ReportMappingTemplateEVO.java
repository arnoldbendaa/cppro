// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.mappingtemplate;

import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplateRef;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class ReportMappingTemplateEVO implements Serializable {

   private transient ReportMappingTemplatePK mPK;
   private int mReportMappingTemplateId;
   private String mVisId;
   private String mDescription;
   private String mDocumentName;
   private byte[] mDocument;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;


   public ReportMappingTemplateEVO() {}

   public ReportMappingTemplateEVO(int newReportMappingTemplateId, String newVisId, String newDescription, String newDocumentName, byte[] newDocument, int newVersionNum) {
      this.mReportMappingTemplateId = newReportMappingTemplateId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mDocumentName = newDocumentName;
      this.mDocument = newDocument;
      this.mVersionNum = newVersionNum;
   }

   public ReportMappingTemplatePK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportMappingTemplatePK(this.mReportMappingTemplateId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportMappingTemplateId() {
      return this.mReportMappingTemplateId;
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

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setReportMappingTemplateId(int newReportMappingTemplateId) {
      if(this.mReportMappingTemplateId != newReportMappingTemplateId) {
         this.mModified = true;
         this.mReportMappingTemplateId = newReportMappingTemplateId;
         this.mPK = null;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDocumentName(String newDocumentName) {
      if(this.mDocumentName != null && newDocumentName == null || this.mDocumentName == null && newDocumentName != null || this.mDocumentName != null && newDocumentName != null && !this.mDocumentName.equals(newDocumentName)) {
         this.mDocumentName = newDocumentName;
         this.mModified = true;
      }

   }

   public void setDocument(byte[] newDocument) {
      if(this.mDocument != null && newDocument == null || this.mDocument == null && newDocument != null || this.mDocument != null && newDocument != null && !this.mDocument.equals(newDocument)) {
         this.mDocument = newDocument;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportMappingTemplateEVO newDetails) {
      this.setReportMappingTemplateId(newDetails.getReportMappingTemplateId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setDocumentName(newDetails.getDocumentName());
      this.setDocument(newDetails.getDocument());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportMappingTemplateEVO deepClone() {
      ReportMappingTemplateEVO cloned = new ReportMappingTemplateEVO();
      cloned.mModified = this.mModified;
      cloned.mReportMappingTemplateId = this.mReportMappingTemplateId;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mDocumentName != null) {
         cloned.mDocumentName = this.mDocumentName;
      }

      if(this.mDocument != null) {
         cloned.mDocument = new byte[this.mDocument.length];
         int i = -1;

         try {
            while(true) {
               ++i;
               cloned.mDocument[i] = this.mDocument[i];
            }
         } catch (ArrayIndexOutOfBoundsException var4) {
            ;
         }
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mReportMappingTemplateId > 0) {
         newKey = true;
         this.mReportMappingTemplateId = 0;
      } else if(this.mReportMappingTemplateId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportMappingTemplateId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mReportMappingTemplateId < 1) {
         this.mReportMappingTemplateId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public ReportMappingTemplateRef getEntityRef() {
      return new ReportMappingTemplateRefImpl(this.getPK(), this.mVisId);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportMappingTemplateId=");
      sb.append(String.valueOf(this.mReportMappingTemplateId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("DocumentName=");
      sb.append(String.valueOf(this.mDocumentName));
      sb.append(' ');
      sb.append("Document=");
      sb.append(String.valueOf(this.mDocument));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("ReportMappingTemplate: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
