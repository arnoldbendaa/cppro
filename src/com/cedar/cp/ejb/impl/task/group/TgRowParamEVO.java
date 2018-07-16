// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.api.task.group.TgRowParamRef;
import com.cedar.cp.dto.task.group.TgRowParamCK;
import com.cedar.cp.dto.task.group.TgRowParamPK;
import com.cedar.cp.dto.task.group.TgRowParamRefImpl;
import com.cedar.cp.ejb.impl.task.group.TaskGroupEVO;
import com.cedar.cp.ejb.impl.task.group.TgRowEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class TgRowParamEVO implements Serializable {

   private transient TgRowParamPK mPK;
   private int mTgRowParamId;
   private int mTgRowId;
   private String mKey;
   private String mParam;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public TgRowParamEVO() {}

   public TgRowParamEVO(int newTgRowParamId, int newTgRowId, String newKey, String newParam) {
      this.mTgRowParamId = newTgRowParamId;
      this.mTgRowId = newTgRowId;
      this.mKey = newKey;
      this.mParam = newParam;
   }

   public TgRowParamPK getPK() {
      if(this.mPK == null) {
         this.mPK = new TgRowParamPK(this.mTgRowParamId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getTgRowParamId() {
      return this.mTgRowParamId;
   }

   public int getTgRowId() {
      return this.mTgRowId;
   }

   public String getKey() {
      return this.mKey;
   }

   public String getParam() {
      return this.mParam;
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

   public void setTgRowParamId(int newTgRowParamId) {
      if(this.mTgRowParamId != newTgRowParamId) {
         this.mModified = true;
         this.mTgRowParamId = newTgRowParamId;
         this.mPK = null;
      }
   }

   public void setTgRowId(int newTgRowId) {
      if(this.mTgRowId != newTgRowId) {
         this.mModified = true;
         this.mTgRowId = newTgRowId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setKey(String newKey) {
      if(this.mKey != null && newKey == null || this.mKey == null && newKey != null || this.mKey != null && newKey != null && !this.mKey.equals(newKey)) {
         this.mKey = newKey;
         this.mModified = true;
      }

   }

   public void setParam(String newParam) {
      if(this.mParam != null && newParam == null || this.mParam == null && newParam != null || this.mParam != null && newParam != null && !this.mParam.equals(newParam)) {
         this.mParam = newParam;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(TgRowParamEVO newDetails) {
      this.setTgRowParamId(newDetails.getTgRowParamId());
      this.setTgRowId(newDetails.getTgRowId());
      this.setKey(newDetails.getKey());
      this.setParam(newDetails.getParam());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public TgRowParamEVO deepClone() {
      TgRowParamEVO cloned = new TgRowParamEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mTgRowParamId = this.mTgRowParamId;
      cloned.mTgRowId = this.mTgRowId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mKey != null) {
         cloned.mKey = this.mKey;
      }

      if(this.mParam != null) {
         cloned.mParam = this.mParam;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(TgRowEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mTgRowParamId > 0) {
         newKey = true;
         if(parent == null) {
            this.setTgRowParamId(-this.mTgRowParamId);
         } else {
            parent.changeKey(this, -this.mTgRowParamId);
         }
      } else if(this.mTgRowParamId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mTgRowParamId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(TgRowEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mTgRowParamId < 1) {
         parent.changeKey(this, startKey);
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

   public TgRowParamRef getEntityRef(TaskGroupEVO evoTaskGroup, TgRowEVO evoTgRow, String entityText) {
      return new TgRowParamRefImpl(new TgRowParamCK(evoTaskGroup.getPK(), evoTgRow.getPK(), this.getPK()), entityText);
   }

   public TgRowParamCK getCK(TaskGroupEVO evoTaskGroup, TgRowEVO evoTgRow) {
      return new TgRowParamCK(evoTaskGroup.getPK(), evoTgRow.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("TgRowParamId=");
      sb.append(String.valueOf(this.mTgRowParamId));
      sb.append(' ');
      sb.append("TgRowId=");
      sb.append(String.valueOf(this.mTgRowId));
      sb.append(' ');
      sb.append("Key=");
      sb.append(String.valueOf(this.mKey));
      sb.append(' ');
      sb.append("Param=");
      sb.append(String.valueOf(this.mParam));
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

      sb.append("TgRowParam: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
