// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.FinanceCubeDataTypeRef;
import com.cedar.cp.dto.model.FinanceCubeDataTypeCK;
import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
import com.cedar.cp.dto.model.FinanceCubeDataTypeRefImpl;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class FinanceCubeDataTypeEVO implements Serializable {

   private transient FinanceCubeDataTypePK mPK;
   private int mFinanceCubeId;
   private short mDataTypeId;
   private Timestamp mCubeLastUpdatedTime;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public FinanceCubeDataTypeEVO() {}

   public FinanceCubeDataTypeEVO(int newFinanceCubeId, short newDataTypeId, Timestamp newCubeLastUpdatedTime) {
      this.mFinanceCubeId = newFinanceCubeId;
      this.mDataTypeId = newDataTypeId;
      this.mCubeLastUpdatedTime = newCubeLastUpdatedTime;
   }

   public FinanceCubeDataTypePK getPK() {
      if(this.mPK == null) {
         this.mPK = new FinanceCubeDataTypePK(this.mFinanceCubeId, this.mDataTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public short getDataTypeId() {
      return this.mDataTypeId;
   }

   public Timestamp getCubeLastUpdatedTime() {
      return this.mCubeLastUpdatedTime;
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

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
         this.mPK = null;
      }
   }

   public void setDataTypeId(short newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
         this.mPK = null;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setCubeLastUpdatedTime(Timestamp newCubeLastUpdatedTime) {
      if(this.mCubeLastUpdatedTime != null && newCubeLastUpdatedTime == null || this.mCubeLastUpdatedTime == null && newCubeLastUpdatedTime != null || this.mCubeLastUpdatedTime != null && newCubeLastUpdatedTime != null && !this.mCubeLastUpdatedTime.equals(newCubeLastUpdatedTime)) {
         this.mCubeLastUpdatedTime = newCubeLastUpdatedTime;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(FinanceCubeDataTypeEVO newDetails) {
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setCubeLastUpdatedTime(newDetails.getCubeLastUpdatedTime());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public FinanceCubeDataTypeEVO deepClone() {
      FinanceCubeDataTypeEVO cloned = new FinanceCubeDataTypeEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mFinanceCubeId = this.mFinanceCubeId;
      cloned.mDataTypeId = this.mDataTypeId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mCubeLastUpdatedTime != null) {
         cloned.mCubeLastUpdatedTime = Timestamp.valueOf(this.mCubeLastUpdatedTime.toString());
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(FinanceCubeEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(FinanceCubeEVO parent, int startKey) {
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

   public FinanceCubeDataTypeRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, String entityText) {
      return new FinanceCubeDataTypeRefImpl(new FinanceCubeDataTypeCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK()), entityText);
   }

   public FinanceCubeDataTypeCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube) {
      return new FinanceCubeDataTypeCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("CubeLastUpdatedTime=");
      sb.append(String.valueOf(this.mCubeLastUpdatedTime));
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

      sb.append("FinanceCubeDataType: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
