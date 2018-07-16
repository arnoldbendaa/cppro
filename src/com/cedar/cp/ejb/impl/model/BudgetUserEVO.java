// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.BudgetUserRef;
import com.cedar.cp.dto.model.BudgetUserCK;
import com.cedar.cp.dto.model.BudgetUserPK;
import com.cedar.cp.dto.model.BudgetUserRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class BudgetUserEVO implements Serializable {

   private transient BudgetUserPK mPK;
   private int mModelId;
   private int mStructureElementId;
   private int mUserId;
   private boolean mReadOnly;
   private int mCurrencyId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public BudgetUserEVO() {}

   public BudgetUserEVO(int newModelId, int newStructureElementId, int newUserId, boolean newReadOnly, int newCurrencyId, int newVersionNum) {
      this.mModelId = newModelId;
      this.mStructureElementId = newStructureElementId;
      this.mUserId = newUserId;
      this.mReadOnly = newReadOnly;
      this.mCurrencyId = newCurrencyId;
      this.mVersionNum = newVersionNum;
   }

   public BudgetUserPK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetUserPK(this.mModelId, this.mStructureElementId, this.mUserId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public boolean getReadOnly() {
      return this.mReadOnly;
   }

   public int getCurrencyId() {
      return this.mCurrencyId;
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

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
         this.mPK = null;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
         this.mPK = null;
      }
   }

   public void setReadOnly(boolean newReadOnly) {
      if(this.mReadOnly != newReadOnly) {
         this.mModified = true;
         this.mReadOnly = newReadOnly;
      }
   }

   public void setCurrencyId(int newCurrencyId) {
      if(this.mCurrencyId != newCurrencyId) {
         this.mModified = true;
         this.mCurrencyId = newCurrencyId;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(BudgetUserEVO newDetails) {
      this.setModelId(newDetails.getModelId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setUserId(newDetails.getUserId());
      this.setReadOnly(newDetails.getReadOnly());
      this.setCurrencyId(newDetails.getCurrencyId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public BudgetUserEVO deepClone() {
      BudgetUserEVO cloned = new BudgetUserEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mModelId = this.mModelId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mUserId = this.mUserId;
      cloned.mReadOnly = this.mReadOnly;
      cloned.mCurrencyId = this.mCurrencyId;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      return startKey;
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

   public BudgetUserRef getEntityRef(ModelEVO evoModel, String entityText) {
      return new BudgetUserRefImpl(new BudgetUserCK(evoModel.getPK(), this.getPK()), entityText);
   }

   public BudgetUserCK getCK(ModelEVO evoModel) {
      return new BudgetUserCK(evoModel.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("ReadOnly=");
      sb.append(String.valueOf(this.mReadOnly));
      sb.append(' ');
      sb.append("CurrencyId=");
      sb.append(String.valueOf(this.mCurrencyId));
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

      sb.append("BudgetUser: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
