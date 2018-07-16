// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskLinkRef;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkCK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkRefImpl;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class TidyTaskLinkEVO implements Serializable {

   private transient TidyTaskLinkPK mPK;
   private int mTidyTaskId;
   private int mTidyTaskLinkId;
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


   public TidyTaskLinkEVO() {}

   public TidyTaskLinkEVO(int newTidyTaskId, int newTidyTaskLinkId, int newSeq, int newType, String newCmd) {
      this.mTidyTaskId = newTidyTaskId;
      this.mTidyTaskLinkId = newTidyTaskLinkId;
      this.mSeq = newSeq;
      this.mType = newType;
      this.mCmd = newCmd;
   }

   public TidyTaskLinkPK getPK() {
      if(this.mPK == null) {
         this.mPK = new TidyTaskLinkPK(this.mTidyTaskId, this.mTidyTaskLinkId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getTidyTaskId() {
      return this.mTidyTaskId;
   }

   public int getTidyTaskLinkId() {
      return this.mTidyTaskLinkId;
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

   public void setTidyTaskId(int newTidyTaskId) {
      if(this.mTidyTaskId != newTidyTaskId) {
         this.mModified = true;
         this.mTidyTaskId = newTidyTaskId;
         this.mPK = null;
      }
   }

   public void setTidyTaskLinkId(int newTidyTaskLinkId) {
      if(this.mTidyTaskLinkId != newTidyTaskLinkId) {
         this.mModified = true;
         this.mTidyTaskLinkId = newTidyTaskLinkId;
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

   public void setDetails(TidyTaskLinkEVO newDetails) {
      this.setTidyTaskId(newDetails.getTidyTaskId());
      this.setTidyTaskLinkId(newDetails.getTidyTaskLinkId());
      this.setSeq(newDetails.getSeq());
      this.setType(newDetails.getType());
      this.setCmd(newDetails.getCmd());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public TidyTaskLinkEVO deepClone() {
      TidyTaskLinkEVO cloned = new TidyTaskLinkEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mTidyTaskId = this.mTidyTaskId;
      cloned.mTidyTaskLinkId = this.mTidyTaskLinkId;
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

   public void prepareForInsert(TidyTaskEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mTidyTaskLinkId > 0) {
         newKey = true;
         if(parent == null) {
            this.setTidyTaskLinkId(-this.mTidyTaskLinkId);
         } else {
            parent.changeKey(this, this.mTidyTaskId, -this.mTidyTaskLinkId);
         }
      } else if(this.mTidyTaskLinkId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mTidyTaskLinkId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(TidyTaskEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mTidyTaskLinkId < 1) {
         parent.changeKey(this, this.mTidyTaskId, startKey);
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

   public TidyTaskLinkRef getEntityRef(TidyTaskEVO evoTidyTask, String entityText) {
      return new TidyTaskLinkRefImpl(new TidyTaskLinkCK(evoTidyTask.getPK(), this.getPK()), entityText);
   }

   public TidyTaskLinkCK getCK(TidyTaskEVO evoTidyTask) {
      return new TidyTaskLinkCK(evoTidyTask.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("TidyTaskId=");
      sb.append(String.valueOf(this.mTidyTaskId));
      sb.append(' ');
      sb.append("TidyTaskLinkId=");
      sb.append(String.valueOf(this.mTidyTaskLinkId));
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

      sb.append("TidyTaskLink: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
