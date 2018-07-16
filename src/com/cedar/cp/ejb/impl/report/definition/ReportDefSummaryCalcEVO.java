// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.api.report.definition.ReportDefSummaryCalcRef;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcCK;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcRefImpl;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ReportDefSummaryCalcEVO implements Serializable {

   private transient ReportDefSummaryCalcPK mPK;
   private int mReportDefinitionId;
   private int mModelId;
   private int mHierarchyDepth;
   private int mStructureId;
   private int mStructureElementId;
   private int mCcDeploymentId;
   private int mReportTemplateId;
   private String mColumnMap;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ReportDefSummaryCalcEVO() {}

   public ReportDefSummaryCalcEVO(int newReportDefinitionId, int newModelId, int newHierarchyDepth, int newStructureId, int newStructureElementId, int newCcDeploymentId, int newReportTemplateId, String newColumnMap) {
      this.mReportDefinitionId = newReportDefinitionId;
      this.mModelId = newModelId;
      this.mHierarchyDepth = newHierarchyDepth;
      this.mStructureId = newStructureId;
      this.mStructureElementId = newStructureElementId;
      this.mCcDeploymentId = newCcDeploymentId;
      this.mReportTemplateId = newReportTemplateId;
      this.mColumnMap = newColumnMap;
   }

   public ReportDefSummaryCalcPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportDefSummaryCalcPK(this.mReportDefinitionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportDefinitionId() {
      return this.mReportDefinitionId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getHierarchyDepth() {
      return this.mHierarchyDepth;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getCcDeploymentId() {
      return this.mCcDeploymentId;
   }

   public int getReportTemplateId() {
      return this.mReportTemplateId;
   }

   public String getColumnMap() {
      return this.mColumnMap;
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

   public void setReportDefinitionId(int newReportDefinitionId) {
      if(this.mReportDefinitionId != newReportDefinitionId) {
         this.mModified = true;
         this.mReportDefinitionId = newReportDefinitionId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setHierarchyDepth(int newHierarchyDepth) {
      if(this.mHierarchyDepth != newHierarchyDepth) {
         this.mModified = true;
         this.mHierarchyDepth = newHierarchyDepth;
      }
   }

   public void setStructureId(int newStructureId) {
      if(this.mStructureId != newStructureId) {
         this.mModified = true;
         this.mStructureId = newStructureId;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
      }
   }

   public void setCcDeploymentId(int newCcDeploymentId) {
      if(this.mCcDeploymentId != newCcDeploymentId) {
         this.mModified = true;
         this.mCcDeploymentId = newCcDeploymentId;
      }
   }

   public void setReportTemplateId(int newReportTemplateId) {
      if(this.mReportTemplateId != newReportTemplateId) {
         this.mModified = true;
         this.mReportTemplateId = newReportTemplateId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setColumnMap(String newColumnMap) {
      if(this.mColumnMap != null && newColumnMap == null || this.mColumnMap == null && newColumnMap != null || this.mColumnMap != null && newColumnMap != null && !this.mColumnMap.equals(newColumnMap)) {
         this.mColumnMap = newColumnMap;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportDefSummaryCalcEVO newDetails) {
      this.setReportDefinitionId(newDetails.getReportDefinitionId());
      this.setModelId(newDetails.getModelId());
      this.setHierarchyDepth(newDetails.getHierarchyDepth());
      this.setStructureId(newDetails.getStructureId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setCcDeploymentId(newDetails.getCcDeploymentId());
      this.setReportTemplateId(newDetails.getReportTemplateId());
      this.setColumnMap(newDetails.getColumnMap());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportDefSummaryCalcEVO deepClone() {
      ReportDefSummaryCalcEVO cloned = new ReportDefSummaryCalcEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mReportDefinitionId = this.mReportDefinitionId;
      cloned.mModelId = this.mModelId;
      cloned.mHierarchyDepth = this.mHierarchyDepth;
      cloned.mStructureId = this.mStructureId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mCcDeploymentId = this.mCcDeploymentId;
      cloned.mReportTemplateId = this.mReportTemplateId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mColumnMap != null) {
         cloned.mColumnMap = this.mColumnMap;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(ReportDefinitionEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ReportDefinitionEVO parent, int startKey) {
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

   public ReportDefSummaryCalcRef getEntityRef(ReportDefinitionEVO evoReportDefinition, String entityText) {
      return new ReportDefSummaryCalcRefImpl(new ReportDefSummaryCalcCK(evoReportDefinition.getPK(), this.getPK()), entityText);
   }

   public ReportDefSummaryCalcCK getCK(ReportDefinitionEVO evoReportDefinition) {
      return new ReportDefSummaryCalcCK(evoReportDefinition.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportDefinitionId=");
      sb.append(String.valueOf(this.mReportDefinitionId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("HierarchyDepth=");
      sb.append(String.valueOf(this.mHierarchyDepth));
      sb.append(' ');
      sb.append("StructureId=");
      sb.append(String.valueOf(this.mStructureId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("CcDeploymentId=");
      sb.append(String.valueOf(this.mCcDeploymentId));
      sb.append(' ');
      sb.append("ReportTemplateId=");
      sb.append(String.valueOf(this.mReportTemplateId));
      sb.append(' ');
      sb.append("ColumnMap=");
      sb.append(String.valueOf(this.mColumnMap));
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

      sb.append("ReportDefSummaryCalc: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
