// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingDeploymentLineRef;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class WeightingDeploymentLineEVO implements Serializable {

   private transient WeightingDeploymentLinePK mPK;
   private int mDeploymentId;
   private int mLineIdx;
   private Integer mAccountStructureId;
   private Integer mAccountStructureElementId;
   private Boolean mAccountSelectionFlag;
   private Integer mBusinessStructureId;
   private Integer mBusinessStructureElementId;
   private Boolean mBusinessSelectionFlag;
   private Integer mDataTypeId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public WeightingDeploymentLineEVO() {}

   public WeightingDeploymentLineEVO(int newDeploymentId, int newLineIdx, Integer newAccountStructureId, Integer newAccountStructureElementId, Boolean newAccountSelectionFlag, Integer newBusinessStructureId, Integer newBusinessStructureElementId, Boolean newBusinessSelectionFlag, Integer newDataTypeId) {
      this.mDeploymentId = newDeploymentId;
      this.mLineIdx = newLineIdx;
      this.mAccountStructureId = newAccountStructureId;
      this.mAccountStructureElementId = newAccountStructureElementId;
      this.mAccountSelectionFlag = newAccountSelectionFlag;
      this.mBusinessStructureId = newBusinessStructureId;
      this.mBusinessStructureElementId = newBusinessStructureElementId;
      this.mBusinessSelectionFlag = newBusinessSelectionFlag;
      this.mDataTypeId = newDataTypeId;
   }

   public WeightingDeploymentLinePK getPK() {
      if(this.mPK == null) {
         this.mPK = new WeightingDeploymentLinePK(this.mDeploymentId, this.mLineIdx);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getDeploymentId() {
      return this.mDeploymentId;
   }

   public int getLineIdx() {
      return this.mLineIdx;
   }

   public Integer getAccountStructureId() {
      return this.mAccountStructureId;
   }

   public Integer getAccountStructureElementId() {
      return this.mAccountStructureElementId;
   }

   public Boolean getAccountSelectionFlag() {
      return this.mAccountSelectionFlag;
   }

   public Integer getBusinessStructureId() {
      return this.mBusinessStructureId;
   }

   public Integer getBusinessStructureElementId() {
      return this.mBusinessStructureElementId;
   }

   public Boolean getBusinessSelectionFlag() {
      return this.mBusinessSelectionFlag;
   }

   public Integer getDataTypeId() {
      return this.mDataTypeId;
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

   public void setDeploymentId(int newDeploymentId) {
      if(this.mDeploymentId != newDeploymentId) {
         this.mModified = true;
         this.mDeploymentId = newDeploymentId;
         this.mPK = null;
      }
   }

   public void setLineIdx(int newLineIdx) {
      if(this.mLineIdx != newLineIdx) {
         this.mModified = true;
         this.mLineIdx = newLineIdx;
         this.mPK = null;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setAccountStructureId(Integer newAccountStructureId) {
      if(this.mAccountStructureId != null && newAccountStructureId == null || this.mAccountStructureId == null && newAccountStructureId != null || this.mAccountStructureId != null && newAccountStructureId != null && !this.mAccountStructureId.equals(newAccountStructureId)) {
         this.mAccountStructureId = newAccountStructureId;
         this.mModified = true;
      }

   }

   public void setAccountStructureElementId(Integer newAccountStructureElementId) {
      if(this.mAccountStructureElementId != null && newAccountStructureElementId == null || this.mAccountStructureElementId == null && newAccountStructureElementId != null || this.mAccountStructureElementId != null && newAccountStructureElementId != null && !this.mAccountStructureElementId.equals(newAccountStructureElementId)) {
         this.mAccountStructureElementId = newAccountStructureElementId;
         this.mModified = true;
      }

   }

   public void setAccountSelectionFlag(Boolean newAccountSelectionFlag) {
      if(this.mAccountSelectionFlag != null && newAccountSelectionFlag == null || this.mAccountSelectionFlag == null && newAccountSelectionFlag != null || this.mAccountSelectionFlag != null && newAccountSelectionFlag != null && !this.mAccountSelectionFlag.equals(newAccountSelectionFlag)) {
         this.mAccountSelectionFlag = newAccountSelectionFlag;
         this.mModified = true;
      }

   }

   public void setBusinessStructureId(Integer newBusinessStructureId) {
      if(this.mBusinessStructureId != null && newBusinessStructureId == null || this.mBusinessStructureId == null && newBusinessStructureId != null || this.mBusinessStructureId != null && newBusinessStructureId != null && !this.mBusinessStructureId.equals(newBusinessStructureId)) {
         this.mBusinessStructureId = newBusinessStructureId;
         this.mModified = true;
      }

   }

   public void setBusinessStructureElementId(Integer newBusinessStructureElementId) {
      if(this.mBusinessStructureElementId != null && newBusinessStructureElementId == null || this.mBusinessStructureElementId == null && newBusinessStructureElementId != null || this.mBusinessStructureElementId != null && newBusinessStructureElementId != null && !this.mBusinessStructureElementId.equals(newBusinessStructureElementId)) {
         this.mBusinessStructureElementId = newBusinessStructureElementId;
         this.mModified = true;
      }

   }

   public void setBusinessSelectionFlag(Boolean newBusinessSelectionFlag) {
      if(this.mBusinessSelectionFlag != null && newBusinessSelectionFlag == null || this.mBusinessSelectionFlag == null && newBusinessSelectionFlag != null || this.mBusinessSelectionFlag != null && newBusinessSelectionFlag != null && !this.mBusinessSelectionFlag.equals(newBusinessSelectionFlag)) {
         this.mBusinessSelectionFlag = newBusinessSelectionFlag;
         this.mModified = true;
      }

   }

   public void setDataTypeId(Integer newDataTypeId) {
      if(this.mDataTypeId != null && newDataTypeId == null || this.mDataTypeId == null && newDataTypeId != null || this.mDataTypeId != null && newDataTypeId != null && !this.mDataTypeId.equals(newDataTypeId)) {
         this.mDataTypeId = newDataTypeId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(WeightingDeploymentLineEVO newDetails) {
      this.setDeploymentId(newDetails.getDeploymentId());
      this.setLineIdx(newDetails.getLineIdx());
      this.setAccountStructureId(newDetails.getAccountStructureId());
      this.setAccountStructureElementId(newDetails.getAccountStructureElementId());
      this.setAccountSelectionFlag(newDetails.getAccountSelectionFlag());
      this.setBusinessStructureId(newDetails.getBusinessStructureId());
      this.setBusinessStructureElementId(newDetails.getBusinessStructureElementId());
      this.setBusinessSelectionFlag(newDetails.getBusinessSelectionFlag());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public WeightingDeploymentLineEVO deepClone() {
      WeightingDeploymentLineEVO cloned = new WeightingDeploymentLineEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mDeploymentId = this.mDeploymentId;
      cloned.mLineIdx = this.mLineIdx;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mAccountStructureId != null) {
         cloned.mAccountStructureId = Integer.valueOf(this.mAccountStructureId.toString());
      }

      if(this.mAccountStructureElementId != null) {
         cloned.mAccountStructureElementId = Integer.valueOf(this.mAccountStructureElementId.toString());
      }

      if(this.mAccountSelectionFlag != null) {
         cloned.mAccountSelectionFlag = Boolean.valueOf(this.mAccountSelectionFlag.toString());
      }

      if(this.mBusinessStructureId != null) {
         cloned.mBusinessStructureId = Integer.valueOf(this.mBusinessStructureId.toString());
      }

      if(this.mBusinessStructureElementId != null) {
         cloned.mBusinessStructureElementId = Integer.valueOf(this.mBusinessStructureElementId.toString());
      }

      if(this.mBusinessSelectionFlag != null) {
         cloned.mBusinessSelectionFlag = Boolean.valueOf(this.mBusinessSelectionFlag.toString());
      }

      if(this.mDataTypeId != null) {
         cloned.mDataTypeId = Integer.valueOf(this.mDataTypeId.toString());
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(WeightingDeploymentEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mDeploymentId > 0) {
         newKey = true;
         if(parent == null) {
            this.setDeploymentId(-this.mDeploymentId);
         } else {
            parent.changeKey(this, this.mDeploymentId, -this.mLineIdx);
         }
      } else if(this.mDeploymentId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDeploymentId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(WeightingDeploymentEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mDeploymentId < 1) {
         parent.changeKey(this, this.mDeploymentId, startKey);
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

   public WeightingDeploymentLineRef getEntityRef(ModelEVO evoModel, WeightingProfileEVO evoWeightingProfile, WeightingDeploymentEVO evoWeightingDeployment, String entityText) {
      return new WeightingDeploymentLineRefImpl(new WeightingDeploymentLineCK(evoModel.getPK(), evoWeightingProfile.getPK(), evoWeightingDeployment.getPK(), this.getPK()), entityText);
   }

   public WeightingDeploymentLineCK getCK(ModelEVO evoModel, WeightingProfileEVO evoWeightingProfile, WeightingDeploymentEVO evoWeightingDeployment) {
      return new WeightingDeploymentLineCK(evoModel.getPK(), evoWeightingProfile.getPK(), evoWeightingDeployment.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DeploymentId=");
      sb.append(String.valueOf(this.mDeploymentId));
      sb.append(' ');
      sb.append("LineIdx=");
      sb.append(String.valueOf(this.mLineIdx));
      sb.append(' ');
      sb.append("AccountStructureId=");
      sb.append(String.valueOf(this.mAccountStructureId));
      sb.append(' ');
      sb.append("AccountStructureElementId=");
      sb.append(String.valueOf(this.mAccountStructureElementId));
      sb.append(' ');
      sb.append("AccountSelectionFlag=");
      sb.append(String.valueOf(this.mAccountSelectionFlag));
      sb.append(' ');
      sb.append("BusinessStructureId=");
      sb.append(String.valueOf(this.mBusinessStructureId));
      sb.append(' ');
      sb.append("BusinessStructureElementId=");
      sb.append(String.valueOf(this.mBusinessStructureElementId));
      sb.append(' ');
      sb.append("BusinessSelectionFlag=");
      sb.append(String.valueOf(this.mBusinessSelectionFlag));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
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

      sb.append("WeightingDeploymentLine: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
