// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type.param;

import com.cedar.cp.api.report.type.param.ReportTypeParamRef;
import com.cedar.cp.dto.report.type.param.ReportTypeParamCK;
import com.cedar.cp.dto.report.type.param.ReportTypeParamPK;
import com.cedar.cp.dto.report.type.param.ReportTypeParamRefImpl;
import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ReportTypeParamEVO implements Serializable {

   private transient ReportTypeParamPK mPK;
   private int mReportTypeParamId;
   private int mReportTypeId;
   private int mSeq;
   private int mControl;
   private String mDescription;
   private String mParamDisplay;
   private String mParamEntity;
   private String mDependentEntity;
   private String mValidationExp;
   private String mValidationTxt;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int TEXT = 0;
   public static final int COMBO_BOX = 1;
   public static final int CHECK_BOX = 2;
   public static final int TABLE_PICKER = 3;
   public static final int TREE_PICKER = 4;
   public static final int RA_TREE_PICKER = 4;


   public ReportTypeParamEVO() {}

   public ReportTypeParamEVO(int newReportTypeParamId, int newReportTypeId, int newSeq, int newControl, String newDescription, String newParamDisplay, String newParamEntity, String newDependentEntity, String newValidationExp, String newValidationTxt) {
      this.mReportTypeParamId = newReportTypeParamId;
      this.mReportTypeId = newReportTypeId;
      this.mSeq = newSeq;
      this.mControl = newControl;
      this.mDescription = newDescription;
      this.mParamDisplay = newParamDisplay;
      this.mParamEntity = newParamEntity;
      this.mDependentEntity = newDependentEntity;
      this.mValidationExp = newValidationExp;
      this.mValidationTxt = newValidationTxt;
   }

   public ReportTypeParamPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportTypeParamPK(this.mReportTypeParamId, this.mReportTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportTypeParamId() {
      return this.mReportTypeParamId;
   }

   public int getReportTypeId() {
      return this.mReportTypeId;
   }

   public int getSeq() {
      return this.mSeq;
   }

   public int getControl() {
      return this.mControl;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getParamDisplay() {
      return this.mParamDisplay;
   }

   public String getParamEntity() {
      return this.mParamEntity;
   }

   public String getDependentEntity() {
      return this.mDependentEntity;
   }

   public String getValidationExp() {
      return this.mValidationExp;
   }

   public String getValidationTxt() {
      return this.mValidationTxt;
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

   public void setReportTypeParamId(int newReportTypeParamId) {
      if(this.mReportTypeParamId != newReportTypeParamId) {
         this.mModified = true;
         this.mReportTypeParamId = newReportTypeParamId;
         this.mPK = null;
      }
   }

   public void setReportTypeId(int newReportTypeId) {
      if(this.mReportTypeId != newReportTypeId) {
         this.mModified = true;
         this.mReportTypeId = newReportTypeId;
         this.mPK = null;
      }
   }

   public void setSeq(int newSeq) {
      if(this.mSeq != newSeq) {
         this.mModified = true;
         this.mSeq = newSeq;
      }
   }

   public void setControl(int newControl) {
      if(this.mControl != newControl) {
         this.mModified = true;
         this.mControl = newControl;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setParamDisplay(String newParamDisplay) {
      if(this.mParamDisplay != null && newParamDisplay == null || this.mParamDisplay == null && newParamDisplay != null || this.mParamDisplay != null && newParamDisplay != null && !this.mParamDisplay.equals(newParamDisplay)) {
         this.mParamDisplay = newParamDisplay;
         this.mModified = true;
      }

   }

   public void setParamEntity(String newParamEntity) {
      if(this.mParamEntity != null && newParamEntity == null || this.mParamEntity == null && newParamEntity != null || this.mParamEntity != null && newParamEntity != null && !this.mParamEntity.equals(newParamEntity)) {
         this.mParamEntity = newParamEntity;
         this.mModified = true;
      }

   }

   public void setDependentEntity(String newDependentEntity) {
      if(this.mDependentEntity != null && newDependentEntity == null || this.mDependentEntity == null && newDependentEntity != null || this.mDependentEntity != null && newDependentEntity != null && !this.mDependentEntity.equals(newDependentEntity)) {
         this.mDependentEntity = newDependentEntity;
         this.mModified = true;
      }

   }

   public void setValidationExp(String newValidationExp) {
      if(this.mValidationExp != null && newValidationExp == null || this.mValidationExp == null && newValidationExp != null || this.mValidationExp != null && newValidationExp != null && !this.mValidationExp.equals(newValidationExp)) {
         this.mValidationExp = newValidationExp;
         this.mModified = true;
      }

   }

   public void setValidationTxt(String newValidationTxt) {
      if(this.mValidationTxt != null && newValidationTxt == null || this.mValidationTxt == null && newValidationTxt != null || this.mValidationTxt != null && newValidationTxt != null && !this.mValidationTxt.equals(newValidationTxt)) {
         this.mValidationTxt = newValidationTxt;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportTypeParamEVO newDetails) {
      this.setReportTypeParamId(newDetails.getReportTypeParamId());
      this.setReportTypeId(newDetails.getReportTypeId());
      this.setSeq(newDetails.getSeq());
      this.setControl(newDetails.getControl());
      this.setDescription(newDetails.getDescription());
      this.setParamDisplay(newDetails.getParamDisplay());
      this.setParamEntity(newDetails.getParamEntity());
      this.setDependentEntity(newDetails.getDependentEntity());
      this.setValidationExp(newDetails.getValidationExp());
      this.setValidationTxt(newDetails.getValidationTxt());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportTypeParamEVO deepClone() {
      ReportTypeParamEVO cloned = new ReportTypeParamEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mReportTypeParamId = this.mReportTypeParamId;
      cloned.mReportTypeId = this.mReportTypeId;
      cloned.mSeq = this.mSeq;
      cloned.mControl = this.mControl;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mParamDisplay != null) {
         cloned.mParamDisplay = this.mParamDisplay;
      }

      if(this.mParamEntity != null) {
         cloned.mParamEntity = this.mParamEntity;
      }

      if(this.mDependentEntity != null) {
         cloned.mDependentEntity = this.mDependentEntity;
      }

      if(this.mValidationExp != null) {
         cloned.mValidationExp = this.mValidationExp;
      }

      if(this.mValidationTxt != null) {
         cloned.mValidationTxt = this.mValidationTxt;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(ReportTypeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mReportTypeParamId > 0) {
         newKey = true;
         if(parent == null) {
            this.setReportTypeParamId(-this.mReportTypeParamId);
         } else {
            parent.changeKey(this, -this.mReportTypeParamId, this.mReportTypeId);
         }
      } else if(this.mReportTypeParamId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportTypeParamId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(ReportTypeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mReportTypeParamId < 1) {
         parent.changeKey(this, startKey, this.mReportTypeId);
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

   public ReportTypeParamRef getEntityRef(ReportTypeEVO evoReportType, String entityText) {
      return new ReportTypeParamRefImpl(new ReportTypeParamCK(evoReportType.getPK(), this.getPK()), entityText);
   }

   public ReportTypeParamCK getCK(ReportTypeEVO evoReportType) {
      return new ReportTypeParamCK(evoReportType.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportTypeParamId=");
      sb.append(String.valueOf(this.mReportTypeParamId));
      sb.append(' ');
      sb.append("ReportTypeId=");
      sb.append(String.valueOf(this.mReportTypeId));
      sb.append(' ');
      sb.append("Seq=");
      sb.append(String.valueOf(this.mSeq));
      sb.append(' ');
      sb.append("Control=");
      sb.append(String.valueOf(this.mControl));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ParamDisplay=");
      sb.append(String.valueOf(this.mParamDisplay));
      sb.append(' ');
      sb.append("ParamEntity=");
      sb.append(String.valueOf(this.mParamEntity));
      sb.append(' ');
      sb.append("DependentEntity=");
      sb.append(String.valueOf(this.mDependentEntity));
      sb.append(' ');
      sb.append("ValidationExp=");
      sb.append(String.valueOf(this.mValidationExp));
      sb.append(' ');
      sb.append("ValidationTxt=");
      sb.append(String.valueOf(this.mValidationTxt));
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

      sb.append("ReportTypeParam: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
