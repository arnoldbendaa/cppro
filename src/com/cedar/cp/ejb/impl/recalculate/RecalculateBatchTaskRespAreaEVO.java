package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.dto.recalculate.*;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;

import java.io.Serializable;

public class RecalculateBatchTaskRespAreaEVO implements Serializable {

   private transient RecalculateBatchTaskRespAreaPK mPK;
   private int mRecalculateBatchTaskId;
   private int mRecalculateBatchTaskRespAreaId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public RecalculateBatchTaskRespAreaEVO() {}

   public RecalculateBatchTaskRespAreaEVO(int newRecalculateBatchTaskId, int newRecalculateBatchTaskRespAreaId) {
      this.mRecalculateBatchTaskId = newRecalculateBatchTaskId;
      this.mRecalculateBatchTaskRespAreaId = newRecalculateBatchTaskRespAreaId;
   }

   public RecalculateBatchTaskRespAreaPK getPK() {
      if(this.mPK == null) {
         this.mPK = new RecalculateBatchTaskRespAreaPK(this.mRecalculateBatchTaskId, this.mRecalculateBatchTaskRespAreaId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRecalculateBatchTaskId() {
      return this.mRecalculateBatchTaskId;
   }

   public int getRecalculateBatchTaskRespAreaId() {
      return this.mRecalculateBatchTaskRespAreaId;
   }


   public void setRecalculateBatchTaskId(int newRecalculateBatchTaskId) {
      if(this.mRecalculateBatchTaskId != newRecalculateBatchTaskId) {
         this.mModified = true;
         this.mRecalculateBatchTaskId = newRecalculateBatchTaskId;
         this.mPK = null;
      }
   }

   public void setRecalculateBatchTaskRespAreaId(int newRecalculateBatchTaskRespAreaId) {
      if(this.mRecalculateBatchTaskRespAreaId != newRecalculateBatchTaskRespAreaId) {
         this.mModified = true;
         this.mRecalculateBatchTaskRespAreaId = newRecalculateBatchTaskRespAreaId;
         this.mPK = null;
      }
   }


   public void setDetails(RecalculateBatchTaskRespAreaEVO newDetails) {
      this.setRecalculateBatchTaskId(newDetails.getRecalculateBatchTaskId());
      this.setRecalculateBatchTaskRespAreaId(newDetails.getRecalculateBatchTaskRespAreaId());
   }

   public RecalculateBatchTaskRespAreaEVO deepClone() {
	   RecalculateBatchTaskRespAreaEVO cloned = new RecalculateBatchTaskRespAreaEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mRecalculateBatchTaskId = this.mRecalculateBatchTaskId;
      cloned.mRecalculateBatchTaskRespAreaId = this.mRecalculateBatchTaskRespAreaId;

      return cloned;
   }

   public void prepareForInsert(RecalculateBatchTaskEVO parent) {
//      boolean newKey = this.insertPending();
//      if(this.mRecalculateBatchTaskRespAreaId > 0) {
//         newKey = true;
//         if(parent == null) {
//            this.setRecalculateBatchTaskRespAreaId(-this.mRecalculateBatchTaskRespAreaId);
//         } else {
//            parent.changeKey(this, this.mRecalculateBatchTaskId, -this.mRecalculateBatchTaskRespAreaId);
//         }
//      } else if(this.mRecalculateBatchTaskRespAreaId < 1) {
//         newKey = true;
//      }
//
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRecalculateBatchTaskRespAreaId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(RecalculateBatchTaskEVO parent, int startKey) {
      int nextKey = startKey;
//      if(this.mRecalculateBatchTaskRespAreaId < 1) {
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

   public RecalculateBatchTaskRespAreaCK getCK(RecalculateBatchTaskEVO evoRecalculateBatchTask) {
      return new RecalculateBatchTaskRespAreaCK(evoRecalculateBatchTask.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RecalculateBatchTaskId=");
      sb.append(String.valueOf(this.mRecalculateBatchTaskId));
      sb.append(' ');
      sb.append("RecalculateBatchTaskRespAreaId=");
      sb.append(String.valueOf(this.mRecalculateBatchTaskRespAreaId));
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

      sb.append("RecalculateBatchTaskRespArea: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
