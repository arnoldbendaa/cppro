package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.importtask.ImportTaskLinkRef;
import com.cedar.cp.dto.importtask.ImportTaskLinkCK;
import com.cedar.cp.dto.importtask.ImportTaskLinkPK;
import com.cedar.cp.dto.importtask.ImportTaskLinkRefImpl;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ImportTaskLinkEVO implements Serializable {

   private transient ImportTaskLinkPK mPK;
   private int mImportTaskId;
   private int mImportTaskLinkId;
   private int mSeq;
   private int mType;
   private String mCmd;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int UPDATE = 0;
   public static final int QUERY = 1;
   public static final int REPORT_PACKAGE = 2;
   public static final int CLASS = 3;
   public static final int UPDATE_PACKAGE = 4;


   public ImportTaskLinkEVO() {}

   public ImportTaskLinkEVO(int newImportTaskId, int newImportTaskLinkId, int newSeq, int newType, String newCmd) {
      this.mImportTaskId = newImportTaskId;
      this.mImportTaskLinkId = newImportTaskLinkId;
      this.mSeq = newSeq;
      this.mType = newType;
      this.mCmd = newCmd;
   }

   public ImportTaskLinkPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ImportTaskLinkPK(this.mImportTaskId, this.mImportTaskLinkId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getImportTaskId() {
      return this.mImportTaskId;
   }

   public int getImportTaskLinkId() {
      return this.mImportTaskLinkId;
   }

   public int getSeq() {
      return this.mSeq;
   }

   public int getType() {
      return this.mType;
   }

   public String getCmd() {
      return this.mCmd;
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

   public void setImportTaskId(int newImportTaskId) {
      if(this.mImportTaskId != newImportTaskId) {
         this.mModified = true;
         this.mImportTaskId = newImportTaskId;
         this.mPK = null;
      }
   }

   public void setImportTaskLinkId(int newImportTaskLinkId) {
      if(this.mImportTaskLinkId != newImportTaskLinkId) {
         this.mModified = true;
         this.mImportTaskLinkId = newImportTaskLinkId;
         this.mPK = null;
      }
   }

   public void setSeq(int newSeq) {
      if(this.mSeq != newSeq) {
         this.mModified = true;
         this.mSeq = newSeq;
      }
   }

   public void setType(int newType) {
      if(this.mType != newType) {
         this.mModified = true;
         this.mType = newType;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setCmd(String newCmd) {
      if(this.mCmd != null && newCmd == null || this.mCmd == null && newCmd != null || this.mCmd != null && newCmd != null && !this.mCmd.equals(newCmd)) {
         this.mCmd = newCmd;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ImportTaskLinkEVO newDetails) {
      this.setImportTaskId(newDetails.getImportTaskId());
      this.setImportTaskLinkId(newDetails.getImportTaskLinkId());
      this.setSeq(newDetails.getSeq());
      this.setType(newDetails.getType());
      this.setCmd(newDetails.getCmd());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ImportTaskLinkEVO deepClone() {
      ImportTaskLinkEVO cloned = new ImportTaskLinkEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mImportTaskId = this.mImportTaskId;
      cloned.mImportTaskLinkId = this.mImportTaskLinkId;
      cloned.mSeq = this.mSeq;
      cloned.mType = this.mType;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mCmd != null) {
         cloned.mCmd = this.mCmd;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(ImportTaskEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mImportTaskLinkId > 0) {
         newKey = true;
         if(parent == null) {
            this.setImportTaskLinkId(-this.mImportTaskLinkId);
         } else {
            parent.changeKey(this, this.mImportTaskId, -this.mImportTaskLinkId);
         }
      } else if(this.mImportTaskLinkId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mImportTaskLinkId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(ImportTaskEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mImportTaskLinkId < 1) {
         parent.changeKey(this, this.mImportTaskId, startKey);
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

   public ImportTaskLinkRef getEntityRef(ImportTaskEVO evoImportTask, String entityText) {
      return new ImportTaskLinkRefImpl(new ImportTaskLinkCK(evoImportTask.getPK(), this.getPK()), entityText);
   }

   public ImportTaskLinkCK getCK(ImportTaskEVO evoImportTask) {
      return new ImportTaskLinkCK(evoImportTask.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ImportTaskId=");
      sb.append(String.valueOf(this.mImportTaskId));
      sb.append(' ');
      sb.append("ImportTaskLinkId=");
      sb.append(String.valueOf(this.mImportTaskLinkId));
      sb.append(' ');
      sb.append("Seq=");
      sb.append(String.valueOf(this.mSeq));
      sb.append(' ');
      sb.append("Type=");
      sb.append(String.valueOf(this.mType));
      sb.append(' ');
      sb.append("Cmd=");
      sb.append(String.valueOf(this.mCmd));
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

      sb.append("ImportTaskLink: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
