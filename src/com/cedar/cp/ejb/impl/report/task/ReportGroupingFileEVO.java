// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.task;

import com.cedar.cp.api.report.task.ReportGroupingFileRef;
import com.cedar.cp.dto.report.task.ReportGroupingFileCK;
import com.cedar.cp.dto.report.task.ReportGroupingFilePK;
import com.cedar.cp.dto.report.task.ReportGroupingFileRefImpl;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ReportGroupingFileEVO implements Serializable {

   private transient ReportGroupingFilePK mPK;
   private int mReportGroupingId;
   private int mReportGroupingFileId;
   private String mFileName;
   private byte[] mFileData;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ReportGroupingFileEVO() {}

   public ReportGroupingFileEVO(int newReportGroupingId, int newReportGroupingFileId, String newFileName, byte[] newFileData) {
      this.mReportGroupingId = newReportGroupingId;
      this.mReportGroupingFileId = newReportGroupingFileId;
      this.mFileName = newFileName;
      this.mFileData = newFileData;
   }

   public ReportGroupingFilePK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportGroupingFilePK(this.mReportGroupingId, this.mReportGroupingFileId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportGroupingId() {
      return this.mReportGroupingId;
   }

   public int getReportGroupingFileId() {
      return this.mReportGroupingFileId;
   }

   public String getFileName() {
      return this.mFileName;
   }

   public byte[] getFileData() {
      return this.mFileData;
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

   public void setReportGroupingId(int newReportGroupingId) {
      if(this.mReportGroupingId != newReportGroupingId) {
         this.mModified = true;
         this.mReportGroupingId = newReportGroupingId;
         this.mPK = null;
      }
   }

   public void setReportGroupingFileId(int newReportGroupingFileId) {
      if(this.mReportGroupingFileId != newReportGroupingFileId) {
         this.mModified = true;
         this.mReportGroupingFileId = newReportGroupingFileId;
         this.mPK = null;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setFileName(String newFileName) {
      if(this.mFileName != null && newFileName == null || this.mFileName == null && newFileName != null || this.mFileName != null && newFileName != null && !this.mFileName.equals(newFileName)) {
         this.mFileName = newFileName;
         this.mModified = true;
      }

   }

   public void setFileData(byte[] newFileData) {
      if(this.mFileData != null && newFileData == null || this.mFileData == null && newFileData != null || this.mFileData != null && newFileData != null && !this.mFileData.equals(newFileData)) {
         this.mFileData = newFileData;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportGroupingFileEVO newDetails) {
      this.setReportGroupingId(newDetails.getReportGroupingId());
      this.setReportGroupingFileId(newDetails.getReportGroupingFileId());
      this.setFileName(newDetails.getFileName());
      this.setFileData(newDetails.getFileData());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportGroupingFileEVO deepClone() {
      ReportGroupingFileEVO cloned = new ReportGroupingFileEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mReportGroupingId = this.mReportGroupingId;
      cloned.mReportGroupingFileId = this.mReportGroupingFileId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mFileName != null) {
         cloned.mFileName = this.mFileName;
      }

      if(this.mFileData != null) {
         cloned.mFileData = new byte[this.mFileData.length];
         int i = -1;

         try {
            while(true) {
               ++i;
               cloned.mFileData[i] = this.mFileData[i];
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

   public void prepareForInsert(ReportGroupingEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mReportGroupingFileId > 0) {
         newKey = true;
         if(parent == null) {
            this.setReportGroupingFileId(-this.mReportGroupingFileId);
         } else {
            parent.changeKey(this, this.mReportGroupingId, -this.mReportGroupingFileId);
         }
      } else if(this.mReportGroupingFileId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportGroupingFileId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(ReportGroupingEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mReportGroupingFileId < 1) {
         parent.changeKey(this, this.mReportGroupingId, startKey);
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public ReportGroupingFileRef getEntityRef(ReportGroupingEVO evoReportGrouping, String entityText) {
      return new ReportGroupingFileRefImpl(new ReportGroupingFileCK(evoReportGrouping.getPK(), this.getPK()), entityText);
   }

   public ReportGroupingFileCK getCK(ReportGroupingEVO evoReportGrouping) {
      return new ReportGroupingFileCK(evoReportGrouping.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportGroupingId=");
      sb.append(String.valueOf(this.mReportGroupingId));
      sb.append(' ');
      sb.append("ReportGroupingFileId=");
      sb.append(String.valueOf(this.mReportGroupingFileId));
      sb.append(' ');
      sb.append("FileName=");
      sb.append(String.valueOf(this.mFileName));
      sb.append(' ');
      sb.append("FileData=");
      sb.append(String.valueOf(this.mFileData));
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

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
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

      sb.append("ReportGroupingFile: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
