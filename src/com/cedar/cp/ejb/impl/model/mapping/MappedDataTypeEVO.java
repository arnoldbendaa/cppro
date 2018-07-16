// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:45
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDataTypeRef;
import com.cedar.cp.dto.model.mapping.MappedDataTypeCK;
import com.cedar.cp.dto.model.mapping.MappedDataTypePK;
import com.cedar.cp.dto.model.mapping.MappedDataTypeRefImpl;
import com.cedar.cp.ejb.impl.model.mapping.MappedFinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class MappedDataTypeEVO implements Serializable {

   private transient MappedDataTypePK mPK;
   private int mMappedDataTypeId;
   private int mMappedFinanceCubeId;
   private int mDataTypeId;
   private String mValueTypeVisId1;
   private String mValueTypeVisId2;
   private String mValueTypeVisId3;
   private int mImpExpStatus;
   private Integer mImpStartYearOffset;
   private Integer mImpEndYearOffset;
   private Integer mExpStartYearOffset;
   private Integer mExpEndYearOffset;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int IMPORT_ONLY = 0;
   public static final int EXPORT_ONLY = 1;
   public static final int IMPORT_EXPORT = 2;


   public MappedDataTypeEVO() {}

   public MappedDataTypeEVO(int newMappedDataTypeId, int newMappedFinanceCubeId, int newDataTypeId, String newValueTypeVisId1, String newValueTypeVisId2, String newValueTypeVisId3, int newImpExpStatus, Integer newImpStartYearOffset, Integer newImpEndYearOffset, Integer newExpStartYearOffset, Integer newExpEndYearOffset) {
      this.mMappedDataTypeId = newMappedDataTypeId;
      this.mMappedFinanceCubeId = newMappedFinanceCubeId;
      this.mDataTypeId = newDataTypeId;
      this.mValueTypeVisId1 = newValueTypeVisId1;
      this.mValueTypeVisId2 = newValueTypeVisId2;
      this.mValueTypeVisId3 = newValueTypeVisId3;
      this.mImpExpStatus = newImpExpStatus;
      this.mImpStartYearOffset = newImpStartYearOffset;
      this.mImpEndYearOffset = newImpEndYearOffset;
      this.mExpStartYearOffset = newExpStartYearOffset;
      this.mExpEndYearOffset = newExpEndYearOffset;
   }

   public MappedDataTypePK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedDataTypePK(this.mMappedDataTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedDataTypeId() {
      return this.mMappedDataTypeId;
   }

   public int getMappedFinanceCubeId() {
      return this.mMappedFinanceCubeId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public String getValueTypeVisId1() {
      return this.mValueTypeVisId1;
   }

   public String getValueTypeVisId2() {
      return this.mValueTypeVisId2;
   }

   public String getValueTypeVisId3() {
      return this.mValueTypeVisId3;
   }

   public int getImpExpStatus() {
      return this.mImpExpStatus;
   }

   public Integer getImpStartYearOffset() {
      return this.mImpStartYearOffset;
   }

   public Integer getImpEndYearOffset() {
      return this.mImpEndYearOffset;
   }

   public Integer getExpStartYearOffset() {
      return this.mExpStartYearOffset;
   }

   public Integer getExpEndYearOffset() {
      return this.mExpEndYearOffset;
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

   public void setMappedDataTypeId(int newMappedDataTypeId) {
      if(this.mMappedDataTypeId != newMappedDataTypeId) {
         this.mModified = true;
         this.mMappedDataTypeId = newMappedDataTypeId;
         this.mPK = null;
      }
   }

   public void setMappedFinanceCubeId(int newMappedFinanceCubeId) {
      if(this.mMappedFinanceCubeId != newMappedFinanceCubeId) {
         this.mModified = true;
         this.mMappedFinanceCubeId = newMappedFinanceCubeId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setImpExpStatus(int newImpExpStatus) {
      if(this.mImpExpStatus != newImpExpStatus) {
         this.mModified = true;
         this.mImpExpStatus = newImpExpStatus;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setValueTypeVisId1(String newValueTypeVisId1) {
      if(this.mValueTypeVisId1 != null && newValueTypeVisId1 == null || this.mValueTypeVisId1 == null && newValueTypeVisId1 != null || this.mValueTypeVisId1 != null && newValueTypeVisId1 != null && !this.mValueTypeVisId1.equals(newValueTypeVisId1)) {
         this.mValueTypeVisId1 = newValueTypeVisId1;
         this.mModified = true;
      }

   }

   public void setValueTypeVisId2(String newValueTypeVisId2) {
      if(this.mValueTypeVisId2 != null && newValueTypeVisId2 == null || this.mValueTypeVisId2 == null && newValueTypeVisId2 != null || this.mValueTypeVisId2 != null && newValueTypeVisId2 != null && !this.mValueTypeVisId2.equals(newValueTypeVisId2)) {
         this.mValueTypeVisId2 = newValueTypeVisId2;
         this.mModified = true;
      }

   }

   public void setValueTypeVisId3(String newValueTypeVisId3) {
      if(this.mValueTypeVisId3 != null && newValueTypeVisId3 == null || this.mValueTypeVisId3 == null && newValueTypeVisId3 != null || this.mValueTypeVisId3 != null && newValueTypeVisId3 != null && !this.mValueTypeVisId3.equals(newValueTypeVisId3)) {
         this.mValueTypeVisId3 = newValueTypeVisId3;
         this.mModified = true;
      }

   }

   public void setImpStartYearOffset(Integer newImpStartYearOffset) {
      if(this.mImpStartYearOffset != null && newImpStartYearOffset == null || this.mImpStartYearOffset == null && newImpStartYearOffset != null || this.mImpStartYearOffset != null && newImpStartYearOffset != null && !this.mImpStartYearOffset.equals(newImpStartYearOffset)) {
         this.mImpStartYearOffset = newImpStartYearOffset;
         this.mModified = true;
      }

   }

   public void setImpEndYearOffset(Integer newImpEndYearOffset) {
      if(this.mImpEndYearOffset != null && newImpEndYearOffset == null || this.mImpEndYearOffset == null && newImpEndYearOffset != null || this.mImpEndYearOffset != null && newImpEndYearOffset != null && !this.mImpEndYearOffset.equals(newImpEndYearOffset)) {
         this.mImpEndYearOffset = newImpEndYearOffset;
         this.mModified = true;
      }

   }

   public void setExpStartYearOffset(Integer newExpStartYearOffset) {
      if(this.mExpStartYearOffset != null && newExpStartYearOffset == null || this.mExpStartYearOffset == null && newExpStartYearOffset != null || this.mExpStartYearOffset != null && newExpStartYearOffset != null && !this.mExpStartYearOffset.equals(newExpStartYearOffset)) {
         this.mExpStartYearOffset = newExpStartYearOffset;
         this.mModified = true;
      }

   }

   public void setExpEndYearOffset(Integer newExpEndYearOffset) {
      if(this.mExpEndYearOffset != null && newExpEndYearOffset == null || this.mExpEndYearOffset == null && newExpEndYearOffset != null || this.mExpEndYearOffset != null && newExpEndYearOffset != null && !this.mExpEndYearOffset.equals(newExpEndYearOffset)) {
         this.mExpEndYearOffset = newExpEndYearOffset;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MappedDataTypeEVO newDetails) {
      this.setMappedDataTypeId(newDetails.getMappedDataTypeId());
      this.setMappedFinanceCubeId(newDetails.getMappedFinanceCubeId());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setValueTypeVisId1(newDetails.getValueTypeVisId1());
      this.setValueTypeVisId2(newDetails.getValueTypeVisId2());
      this.setValueTypeVisId3(newDetails.getValueTypeVisId3());
      this.setImpExpStatus(newDetails.getImpExpStatus());
      this.setImpStartYearOffset(newDetails.getImpStartYearOffset());
      this.setImpEndYearOffset(newDetails.getImpEndYearOffset());
      this.setExpStartYearOffset(newDetails.getExpStartYearOffset());
      this.setExpEndYearOffset(newDetails.getExpEndYearOffset());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedDataTypeEVO deepClone() {
      MappedDataTypeEVO cloned = new MappedDataTypeEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mMappedDataTypeId = this.mMappedDataTypeId;
      cloned.mMappedFinanceCubeId = this.mMappedFinanceCubeId;
      cloned.mDataTypeId = this.mDataTypeId;
      cloned.mImpExpStatus = this.mImpExpStatus;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mValueTypeVisId1 != null) {
         cloned.mValueTypeVisId1 = this.mValueTypeVisId1;
      }

      if(this.mValueTypeVisId2 != null) {
         cloned.mValueTypeVisId2 = this.mValueTypeVisId2;
      }

      if(this.mValueTypeVisId3 != null) {
         cloned.mValueTypeVisId3 = this.mValueTypeVisId3;
      }

      if(this.mImpStartYearOffset != null) {
         cloned.mImpStartYearOffset = Integer.valueOf(this.mImpStartYearOffset.toString());
      }

      if(this.mImpEndYearOffset != null) {
         cloned.mImpEndYearOffset = Integer.valueOf(this.mImpEndYearOffset.toString());
      }

      if(this.mExpStartYearOffset != null) {
         cloned.mExpStartYearOffset = Integer.valueOf(this.mExpStartYearOffset.toString());
      }

      if(this.mExpEndYearOffset != null) {
         cloned.mExpEndYearOffset = Integer.valueOf(this.mExpEndYearOffset.toString());
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(MappedFinanceCubeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMappedDataTypeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setMappedDataTypeId(-this.mMappedDataTypeId);
         } else {
            parent.changeKey(this, -this.mMappedDataTypeId);
         }
      } else if(this.mMappedDataTypeId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedDataTypeId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(MappedFinanceCubeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMappedDataTypeId < 1) {
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

   public MappedDataTypeRef getEntityRef(MappedModelEVO evoMappedModel, MappedFinanceCubeEVO evoMappedFinanceCube, String entityText) {
      return new MappedDataTypeRefImpl(new MappedDataTypeCK(evoMappedModel.getPK(), evoMappedFinanceCube.getPK(), this.getPK()), entityText);
   }

   public MappedDataTypeCK getCK(MappedModelEVO evoMappedModel, MappedFinanceCubeEVO evoMappedFinanceCube) {
      return new MappedDataTypeCK(evoMappedModel.getPK(), evoMappedFinanceCube.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedDataTypeId=");
      sb.append(String.valueOf(this.mMappedDataTypeId));
      sb.append(' ');
      sb.append("MappedFinanceCubeId=");
      sb.append(String.valueOf(this.mMappedFinanceCubeId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("ValueTypeVisId1=");
      sb.append(String.valueOf(this.mValueTypeVisId1));
      sb.append(' ');
      sb.append("ValueTypeVisId2=");
      sb.append(String.valueOf(this.mValueTypeVisId2));
      sb.append(' ');
      sb.append("ValueTypeVisId3=");
      sb.append(String.valueOf(this.mValueTypeVisId3));
      sb.append(' ');
      sb.append("ImpExpStatus=");
      sb.append(String.valueOf(this.mImpExpStatus));
      sb.append(' ');
      sb.append("ImpStartYearOffset=");
      sb.append(String.valueOf(this.mImpStartYearOffset));
      sb.append(' ');
      sb.append("ImpEndYearOffset=");
      sb.append(String.valueOf(this.mImpEndYearOffset));
      sb.append(' ');
      sb.append("ExpStartYearOffset=");
      sb.append(String.valueOf(this.mExpStartYearOffset));
      sb.append(' ');
      sb.append("ExpEndYearOffset=");
      sb.append(String.valueOf(this.mExpEndYearOffset));
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

      sb.append("MappedDataType: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
