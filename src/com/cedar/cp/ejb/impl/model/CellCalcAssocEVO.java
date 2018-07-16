// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.CellCalcAssocRef;
import com.cedar.cp.dto.model.CellCalcAssocCK;
import com.cedar.cp.dto.model.CellCalcAssocPK;
import com.cedar.cp.dto.model.CellCalcAssocRefImpl;
import com.cedar.cp.ejb.impl.model.CellCalcEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class CellCalcAssocEVO implements Serializable {

   private transient CellCalcAssocPK mPK;
   private int mCellCalcAssocId;
   private int mCellCalcId;
   private int mAccountElementId;
   private String mFormField;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CellCalcAssocEVO() {}

   public CellCalcAssocEVO(int newCellCalcAssocId, int newCellCalcId, int newAccountElementId, String newFormField) {
      this.mCellCalcAssocId = newCellCalcAssocId;
      this.mCellCalcId = newCellCalcId;
      this.mAccountElementId = newAccountElementId;
      this.mFormField = newFormField;
   }

   public CellCalcAssocPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CellCalcAssocPK(this.mCellCalcAssocId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCellCalcAssocId() {
      return this.mCellCalcAssocId;
   }

   public int getCellCalcId() {
      return this.mCellCalcId;
   }

   public int getAccountElementId() {
      return this.mAccountElementId;
   }

   public String getFormField() {
      return this.mFormField;
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

   public void setCellCalcAssocId(int newCellCalcAssocId) {
      if(this.mCellCalcAssocId != newCellCalcAssocId) {
         this.mModified = true;
         this.mCellCalcAssocId = newCellCalcAssocId;
         this.mPK = null;
      }
   }

   public void setCellCalcId(int newCellCalcId) {
      if(this.mCellCalcId != newCellCalcId) {
         this.mModified = true;
         this.mCellCalcId = newCellCalcId;
      }
   }

   public void setAccountElementId(int newAccountElementId) {
      if(this.mAccountElementId != newAccountElementId) {
         this.mModified = true;
         this.mAccountElementId = newAccountElementId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setFormField(String newFormField) {
      if(this.mFormField != null && newFormField == null || this.mFormField == null && newFormField != null || this.mFormField != null && newFormField != null && !this.mFormField.equals(newFormField)) {
         this.mFormField = newFormField;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(CellCalcAssocEVO newDetails) {
      this.setCellCalcAssocId(newDetails.getCellCalcAssocId());
      this.setCellCalcId(newDetails.getCellCalcId());
      this.setAccountElementId(newDetails.getAccountElementId());
      this.setFormField(newDetails.getFormField());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public CellCalcAssocEVO deepClone() {
      CellCalcAssocEVO cloned = new CellCalcAssocEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mCellCalcAssocId = this.mCellCalcAssocId;
      cloned.mCellCalcId = this.mCellCalcId;
      cloned.mAccountElementId = this.mAccountElementId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mFormField != null) {
         cloned.mFormField = this.mFormField;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(CellCalcEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCellCalcAssocId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCellCalcAssocId(-this.mCellCalcAssocId);
         } else {
            parent.changeKey(this, -this.mCellCalcAssocId);
         }
      } else if(this.mCellCalcAssocId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCellCalcAssocId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(CellCalcEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCellCalcAssocId < 1) {
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

   public CellCalcAssocRef getEntityRef(ModelEVO evoModel, CellCalcEVO evoCellCalc, String entityText) {
      return new CellCalcAssocRefImpl(new CellCalcAssocCK(evoModel.getPK(), evoCellCalc.getPK(), this.getPK()), entityText);
   }

   public CellCalcAssocCK getCK(ModelEVO evoModel, CellCalcEVO evoCellCalc) {
      return new CellCalcAssocCK(evoModel.getPK(), evoCellCalc.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CellCalcAssocId=");
      sb.append(String.valueOf(this.mCellCalcAssocId));
      sb.append(' ');
      sb.append("CellCalcId=");
      sb.append(String.valueOf(this.mCellCalcId));
      sb.append(' ');
      sb.append("AccountElementId=");
      sb.append(String.valueOf(this.mAccountElementId));
      sb.append(' ');
      sb.append("FormField=");
      sb.append(String.valueOf(this.mFormField));
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

      sb.append("CellCalcAssoc: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
