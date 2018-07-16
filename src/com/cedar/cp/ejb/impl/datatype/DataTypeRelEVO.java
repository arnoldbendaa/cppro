// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.api.datatype.DataTypeRelRef;
import com.cedar.cp.dto.datatype.DataTypeRelCK;
import com.cedar.cp.dto.datatype.DataTypeRelPK;
import com.cedar.cp.dto.datatype.DataTypeRelRefImpl;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class DataTypeRelEVO implements Serializable {

   private transient DataTypeRelPK mPK;
   private short mDataTypeId;
   private short mRefDataTypeId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public DataTypeRelEVO() {}

   public DataTypeRelEVO(short newDataTypeId, short newRefDataTypeId) {
      this.mDataTypeId = newDataTypeId;
      this.mRefDataTypeId = newRefDataTypeId;
   }

   public DataTypeRelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DataTypeRelPK(this.mDataTypeId, this.mRefDataTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public short getDataTypeId() {
      return this.mDataTypeId;
   }

   public short getRefDataTypeId() {
      return this.mRefDataTypeId;
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

   public void setDataTypeId(short newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
         this.mPK = null;
      }
   }

   public void setRefDataTypeId(short newRefDataTypeId) {
      if(this.mRefDataTypeId != newRefDataTypeId) {
         this.mModified = true;
         this.mRefDataTypeId = newRefDataTypeId;
         this.mPK = null;
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

   public void setDetails(DataTypeRelEVO newDetails) {
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setRefDataTypeId(newDetails.getRefDataTypeId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public DataTypeRelEVO deepClone() {
      DataTypeRelEVO cloned = new DataTypeRelEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mDataTypeId = this.mDataTypeId;
      cloned.mRefDataTypeId = this.mRefDataTypeId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(DataTypeEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(DataTypeEVO parent, int startKey) {
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

   public DataTypeRelRef getEntityRef(DataTypeEVO evoDataType, String entityText) {
      return new DataTypeRelRefImpl(new DataTypeRelCK(evoDataType.getPK(), this.getPK()), entityText);
   }

   public DataTypeRelCK getCK(DataTypeEVO evoDataType) {
      return new DataTypeRelCK(evoDataType.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("RefDataTypeId=");
      sb.append(String.valueOf(this.mRefDataTypeId));
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

      sb.append("DataTypeRel: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
