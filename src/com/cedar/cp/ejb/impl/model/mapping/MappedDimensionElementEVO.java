// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDimensionElementRef;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementCK;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementPK;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementRefImpl;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class MappedDimensionElementEVO implements Serializable {

   private transient MappedDimensionElementPK mPK;
   private int mMappedDimensionElementId;
   private int mMappedDimensionId;
   private int mMappingType;
   private String mElementVisId1;
   private String mElementVisId2;
   private String mElementVisId3;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int MAPPING_TYPE_SINGLE = 0;
   public static final int MAPPING_TYPE_PREFIX = 1;
   public static final int MAPPING_TYPE_RANGE = 2;
   public static final int MAPPING_TYPE_HIERARCHY = 3;


   public MappedDimensionElementEVO() {}

   public MappedDimensionElementEVO(int newMappedDimensionElementId, int newMappedDimensionId, int newMappingType, String newElementVisId1, String newElementVisId2, String newElementVisId3) {
      this.mMappedDimensionElementId = newMappedDimensionElementId;
      this.mMappedDimensionId = newMappedDimensionId;
      this.mMappingType = newMappingType;
      this.mElementVisId1 = newElementVisId1;
      this.mElementVisId2 = newElementVisId2;
      this.mElementVisId3 = newElementVisId3;
   }

   public MappedDimensionElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedDimensionElementPK(this.mMappedDimensionElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedDimensionElementId() {
      return this.mMappedDimensionElementId;
   }

   public int getMappedDimensionId() {
      return this.mMappedDimensionId;
   }

   public int getMappingType() {
      return this.mMappingType;
   }

   public String getElementVisId1() {
      return this.mElementVisId1;
   }

   public String getElementVisId2() {
      return this.mElementVisId2;
   }

   public String getElementVisId3() {
      return this.mElementVisId3;
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

   public void setMappedDimensionElementId(int newMappedDimensionElementId) {
      if(this.mMappedDimensionElementId != newMappedDimensionElementId) {
         this.mModified = true;
         this.mMappedDimensionElementId = newMappedDimensionElementId;
         this.mPK = null;
      }
   }

   public void setMappedDimensionId(int newMappedDimensionId) {
      if(this.mMappedDimensionId != newMappedDimensionId) {
         this.mModified = true;
         this.mMappedDimensionId = newMappedDimensionId;
      }
   }

   public void setMappingType(int newMappingType) {
      if(this.mMappingType != newMappingType) {
         this.mModified = true;
         this.mMappingType = newMappingType;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setElementVisId1(String newElementVisId1) {
      if(this.mElementVisId1 != null && newElementVisId1 == null || this.mElementVisId1 == null && newElementVisId1 != null || this.mElementVisId1 != null && newElementVisId1 != null && !this.mElementVisId1.equals(newElementVisId1)) {
         this.mElementVisId1 = newElementVisId1;
         this.mModified = true;
      }

   }

   public void setElementVisId2(String newElementVisId2) {
      if(this.mElementVisId2 != null && newElementVisId2 == null || this.mElementVisId2 == null && newElementVisId2 != null || this.mElementVisId2 != null && newElementVisId2 != null && !this.mElementVisId2.equals(newElementVisId2)) {
         this.mElementVisId2 = newElementVisId2;
         this.mModified = true;
      }

   }

   public void setElementVisId3(String newElementVisId3) {
      if(this.mElementVisId3 != null && newElementVisId3 == null || this.mElementVisId3 == null && newElementVisId3 != null || this.mElementVisId3 != null && newElementVisId3 != null && !this.mElementVisId3.equals(newElementVisId3)) {
         this.mElementVisId3 = newElementVisId3;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MappedDimensionElementEVO newDetails) {
      this.setMappedDimensionElementId(newDetails.getMappedDimensionElementId());
      this.setMappedDimensionId(newDetails.getMappedDimensionId());
      this.setMappingType(newDetails.getMappingType());
      this.setElementVisId1(newDetails.getElementVisId1());
      this.setElementVisId2(newDetails.getElementVisId2());
      this.setElementVisId3(newDetails.getElementVisId3());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedDimensionElementEVO deepClone() {
      MappedDimensionElementEVO cloned = new MappedDimensionElementEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mMappedDimensionElementId = this.mMappedDimensionElementId;
      cloned.mMappedDimensionId = this.mMappedDimensionId;
      cloned.mMappingType = this.mMappingType;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mElementVisId1 != null) {
         cloned.mElementVisId1 = this.mElementVisId1;
      }

      if(this.mElementVisId2 != null) {
         cloned.mElementVisId2 = this.mElementVisId2;
      }

      if(this.mElementVisId3 != null) {
         cloned.mElementVisId3 = this.mElementVisId3;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(MappedDimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMappedDimensionElementId > 0) {
         newKey = true;
         if(parent == null) {
            this.setMappedDimensionElementId(-this.mMappedDimensionElementId);
         } else {
            parent.changeKey(this, -this.mMappedDimensionElementId);
         }
      } else if(this.mMappedDimensionElementId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedDimensionElementId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(MappedDimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMappedDimensionElementId < 1) {
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

   public MappedDimensionElementRef getEntityRef(MappedModelEVO evoMappedModel, MappedDimensionEVO evoMappedDimension, String entityText) {
      return new MappedDimensionElementRefImpl(new MappedDimensionElementCK(evoMappedModel.getPK(), evoMappedDimension.getPK(), this.getPK()), entityText);
   }

   public MappedDimensionElementCK getCK(MappedModelEVO evoMappedModel, MappedDimensionEVO evoMappedDimension) {
      return new MappedDimensionElementCK(evoMappedModel.getPK(), evoMappedDimension.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedDimensionElementId=");
      sb.append(String.valueOf(this.mMappedDimensionElementId));
      sb.append(' ');
      sb.append("MappedDimensionId=");
      sb.append(String.valueOf(this.mMappedDimensionId));
      sb.append(' ');
      sb.append("MappingType=");
      sb.append(String.valueOf(this.mMappingType));
      sb.append(' ');
      sb.append("ElementVisId1=");
      sb.append(String.valueOf(this.mElementVisId1));
      sb.append(' ');
      sb.append("ElementVisId2=");
      sb.append(String.valueOf(this.mElementVisId2));
      sb.append(' ');
      sb.append("ElementVisId3=");
      sb.append(String.valueOf(this.mElementVisId3));
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

      sb.append("MappedDimensionElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
