// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.api.xmlreport.XmlReportRef;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.dto.xmlreport.XmlReportRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class XmlReportEVO implements Serializable {

   private transient XmlReportPK mPK;
   private int mXmlReportId;
   private int mXmlReportFolderId;
   private String mVisId;
   private int mUserId;
   private String mDefinition;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;


   public XmlReportEVO() {}

   public XmlReportEVO(int newXmlReportId, int newXmlReportFolderId, String newVisId, int newUserId, String newDefinition, int newVersionNum) {
      this.mXmlReportId = newXmlReportId;
      this.mXmlReportFolderId = newXmlReportFolderId;
      this.mVisId = newVisId;
      this.mUserId = newUserId;
      this.mDefinition = newDefinition;
      this.mVersionNum = newVersionNum;
   }

   public XmlReportPK getPK() {
      if(this.mPK == null) {
         this.mPK = new XmlReportPK(this.mXmlReportId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getXmlReportId() {
      return this.mXmlReportId;
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

   public void setXmlReportId(int newXmlReportId) {
      if(this.mXmlReportId != newXmlReportId) {
         this.mModified = true;
         this.mXmlReportId = newXmlReportId;
         this.mPK = null;
      }
   }

   public void setXmlReportFolderId(int newXmlReportFolderId) {
      if(this.mXmlReportFolderId != newXmlReportFolderId) {
         this.mModified = true;
         this.mXmlReportFolderId = newXmlReportFolderId;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
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

   public void setDefinition(String newDefinition) {
      if(this.mDefinition != null && newDefinition == null || this.mDefinition == null && newDefinition != null || this.mDefinition != null && newDefinition != null && !this.mDefinition.equals(newDefinition)) {
         this.mDefinition = newDefinition;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(XmlReportEVO newDetails) {
      this.setXmlReportId(newDetails.getXmlReportId());
      this.setXmlReportFolderId(newDetails.getXmlReportFolderId());
      this.setVisId(newDetails.getVisId());
      this.setUserId(newDetails.getUserId());
      this.setDefinition(newDetails.getDefinition());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public XmlReportEVO deepClone() {
      XmlReportEVO cloned = new XmlReportEVO();
      cloned.mModified = this.mModified;
      cloned.mXmlReportId = this.mXmlReportId;
      cloned.mXmlReportFolderId = this.mXmlReportFolderId;
      cloned.mUserId = this.mUserId;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDefinition != null) {
         cloned.mDefinition = this.mDefinition;
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
      if(this.mXmlReportId > 0) {
         newKey = true;
         this.mXmlReportId = 0;
      } else if(this.mXmlReportId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mXmlReportId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mXmlReportId < 1) {
         this.mXmlReportId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public XmlReportRef getEntityRef() {
      return new XmlReportRefImpl(this.getPK(), this.mVisId);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("XmlReportId=");
      sb.append(String.valueOf(this.mXmlReportId));
      sb.append(' ');
      sb.append("XmlReportFolderId=");
      sb.append(String.valueOf(this.mXmlReportFolderId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("Definition=");
      sb.append(String.valueOf(this.mDefinition));
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

      sb.append("XmlReport: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
