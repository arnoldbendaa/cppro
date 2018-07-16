// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreportfolder;

import com.cedar.cp.api.xmlreportfolder.XmlReportFolderRef;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class XmlReportFolderEVO implements Serializable {

   private transient XmlReportFolderPK mPK;
   private int mXmlReportFolderId;
   private int mParentFolderId;
   private String mVisId;
   private int mUserId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;


   public XmlReportFolderEVO() {}

   public XmlReportFolderEVO(int newXmlReportFolderId, int newParentFolderId, String newVisId, int newUserId, int newVersionNum) {
      this.mXmlReportFolderId = newXmlReportFolderId;
      this.mParentFolderId = newParentFolderId;
      this.mVisId = newVisId;
      this.mUserId = newUserId;
      this.mVersionNum = newVersionNum;
   }

   public XmlReportFolderPK getPK() {
      if(this.mPK == null) {
         this.mPK = new XmlReportFolderPK(this.mXmlReportFolderId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getXmlReportFolderId() {
      return this.mXmlReportFolderId;
   }

   public int getParentFolderId() {
      return this.mParentFolderId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public int getUserId() {
      return this.mUserId;
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

   public void setXmlReportFolderId(int newXmlReportFolderId) {
      if(this.mXmlReportFolderId != newXmlReportFolderId) {
         this.mModified = true;
         this.mXmlReportFolderId = newXmlReportFolderId;
         this.mPK = null;
      }
   }

   public void setParentFolderId(int newParentFolderId) {
      if(this.mParentFolderId != newParentFolderId) {
         this.mModified = true;
         this.mParentFolderId = newParentFolderId;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(XmlReportFolderEVO newDetails) {
      this.setXmlReportFolderId(newDetails.getXmlReportFolderId());
      this.setParentFolderId(newDetails.getParentFolderId());
      this.setVisId(newDetails.getVisId());
      this.setUserId(newDetails.getUserId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public XmlReportFolderEVO deepClone() {
      XmlReportFolderEVO cloned = new XmlReportFolderEVO();
      cloned.mModified = this.mModified;
      cloned.mXmlReportFolderId = this.mXmlReportFolderId;
      cloned.mParentFolderId = this.mParentFolderId;
      cloned.mUserId = this.mUserId;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
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
      if(this.mXmlReportFolderId > 0) {
         newKey = true;
         this.mXmlReportFolderId = 0;
      } else if(this.mXmlReportFolderId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mXmlReportFolderId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mXmlReportFolderId < 1) {
         this.mXmlReportFolderId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public XmlReportFolderRef getEntityRef() {
      return new XmlReportFolderRefImpl(this.getPK(), this.mVisId);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("XmlReportFolderId=");
      sb.append(String.valueOf(this.mXmlReportFolderId));
      sb.append(' ');
      sb.append("ParentFolderId=");
      sb.append(String.valueOf(this.mParentFolderId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
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

      sb.append("XmlReportFolder: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
