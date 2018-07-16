package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.dto.recalculate.*;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;

import java.io.Serializable;

public class RecalculateBatchTaskFormEVO implements Serializable {

   private transient RecalculateBatchTaskFormPK mPK;
   private int mRecalculateBatchTaskId;
   private int mRecalculateBatchTaskFormId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public RecalculateBatchTaskFormEVO() {}

   public RecalculateBatchTaskFormEVO(int newRecalculateBatchTaskId, int newRecalculateBatchTaskFormId) {
      this.mRecalculateBatchTaskId = newRecalculateBatchTaskId;
      this.mRecalculateBatchTaskFormId = newRecalculateBatchTaskFormId;
   }

   public RecalculateBatchTaskFormPK getPK() {
      if(this.mPK == null) {
         this.mPK = new RecalculateBatchTaskFormPK(this.mRecalculateBatchTaskId, this.mRecalculateBatchTaskFormId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRecalculateBatchTaskId() {
      return this.mRecalculateBatchTaskId;
   }

   public int getRecalculateBatchTaskFormId() {
      return this.mRecalculateBatchTaskFormId;
   }


   public void setRecalculateBatchTaskId(int newRecalculateBatchTaskId) {
      if(this.mRecalculateBatchTaskId != newRecalculateBatchTaskId) {
         this.mModified = true;
         this.mRecalculateBatchTaskId = newRecalculateBatchTaskId;
         this.mPK = null;
      }
   }

   public void setRecalculateBatchTaskFormId(int newRecalculateBatchTaskFormId) {
      if(this.mRecalculateBatchTaskFormId != newRecalculateBatchTaskFormId) {
         this.mModified = true;
         this.mRecalculateBatchTaskFormId = newRecalculateBatchTaskFormId;
         this.mPK = null;
      }
   }


   public void setDetails(RecalculateBatchTaskFormEVO newDetails) {
      this.setRecalculateBatchTaskId(newDetails.getRecalculateBatchTaskId());
      this.setRecalculateBatchTaskFormId(newDetails.getRecalculateBatchTaskFormId());
   }

   public RecalculateBatchTaskFormEVO deepClone() {
	   RecalculateBatchTaskFormEVO cloned = new RecalculateBatchTaskFormEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mRecalculateBatchTaskId = this.mRecalculateBatchTaskId;
      cloned.mRecalculateBatchTaskFormId = this.mRecalculateBatchTaskFormId;

      return cloned;
   }

   public void prepareForInsert(RecalculateBatchTaskEVO parent) {
//      boolean newKey = this.insertPending();
//      if(this.mRecalculateBatchTaskFormId > 0) {
//         newKey = true;
//         if(parent == null) {
//            this.setRecalculateBatchTaskFormId(-this.mRecalculateBatchTaskFormId);
//         } else {
//            parent.changeKey(this, this.mRecalculateBatchTaskId, -this.mRecalculateBatchTaskFormId);
//         }
//      } else if(this.mRecalculateBatchTaskFormId < 1) {
//         newKey = true;
//      }
//
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRecalculateBatchTaskFormId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(RecalculateBatchTaskEVO parent, int startKey) {
      int nextKey = startKey;
//      if(this.mRecalculateBatchTaskFormId < 1) {
//         parent.changeKey(this, this.mRecalculateBatchTaskId, startKey);
//         nextKey = startKey + 1;
//      }

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

   public RecalculateBatchTaskFormCK getCK(RecalculateBatchTaskEVO evoRecalculateBatchTask) {
      return new RecalculateBatchTaskFormCK(evoRecalculateBatchTask.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RecalculateBatchTaskId=");
      sb.append(String.valueOf(this.mRecalculateBatchTaskId));
      sb.append(' ');
      sb.append("RecalculateBatchTaskFormId=");
      sb.append(String.valueOf(this.mRecalculateBatchTaskFormId));
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

      sb.append("RecalculateBatchTaskForm: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
