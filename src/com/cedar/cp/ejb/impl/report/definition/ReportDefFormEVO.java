// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.api.report.definition.ReportDefFormRef;
import com.cedar.cp.dto.report.definition.ReportDefFormCK;
import com.cedar.cp.dto.report.definition.ReportDefFormPK;
import com.cedar.cp.dto.report.definition.ReportDefFormRefImpl;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ReportDefFormEVO implements Serializable {

   private transient ReportDefFormPK mPK;
   private int mReportDefinitionId;
   private int mModelId;
   private int mFormId;
   private int mDataTypeId;
   private int mStructureId0;
   private int mStructureElementId0;
   private int mStructureId1;
   private int mStructureElementId1;
   private int mStructureId2;
   private int mStructureElementId2;
   private int mStructureId3;
   private int mStructureElementId3;
   private int mStructureId4;
   private int mStructureElementId4;
   private int mStructureId5;
   private int mStructureElementId5;
   private int mStructureId6;
   private int mStructureElementId6;
   private int mStructureId7;
   private int mStructureElementId7;
   private int mStructureId8;
   private int mStructureElementId8;
   private int mStructureId9;
   private int mStructureElementId9;
   private int mReportDepth;
   private int mAutoExpandDepth;
   private int mReportTemplateId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ReportDefFormEVO() {}

   public ReportDefFormEVO(int newReportDefinitionId, int newModelId, int newFormId, int newDataTypeId, int newStructureId0, int newStructureElementId0, int newStructureId1, int newStructureElementId1, int newStructureId2, int newStructureElementId2, int newStructureId3, int newStructureElementId3, int newStructureId4, int newStructureElementId4, int newStructureId5, int newStructureElementId5, int newStructureId6, int newStructureElementId6, int newStructureId7, int newStructureElementId7, int newStructureId8, int newStructureElementId8, int newStructureId9, int newStructureElementId9, int newReportDepth, int newAutoExpandDepth, int newReportTemplateId) {
      this.mReportDefinitionId = newReportDefinitionId;
      this.mModelId = newModelId;
      this.mFormId = newFormId;
      this.mDataTypeId = newDataTypeId;
      this.mStructureId0 = newStructureId0;
      this.mStructureElementId0 = newStructureElementId0;
      this.mStructureId1 = newStructureId1;
      this.mStructureElementId1 = newStructureElementId1;
      this.mStructureId2 = newStructureId2;
      this.mStructureElementId2 = newStructureElementId2;
      this.mStructureId3 = newStructureId3;
      this.mStructureElementId3 = newStructureElementId3;
      this.mStructureId4 = newStructureId4;
      this.mStructureElementId4 = newStructureElementId4;
      this.mStructureId5 = newStructureId5;
      this.mStructureElementId5 = newStructureElementId5;
      this.mStructureId6 = newStructureId6;
      this.mStructureElementId6 = newStructureElementId6;
      this.mStructureId7 = newStructureId7;
      this.mStructureElementId7 = newStructureElementId7;
      this.mStructureId8 = newStructureId8;
      this.mStructureElementId8 = newStructureElementId8;
      this.mStructureId9 = newStructureId9;
      this.mStructureElementId9 = newStructureElementId9;
      this.mReportDepth = newReportDepth;
      this.mAutoExpandDepth = newAutoExpandDepth;
      this.mReportTemplateId = newReportTemplateId;
   }

   public ReportDefFormPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportDefFormPK(this.mReportDefinitionId);
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

   public int getFormId() {
      return this.mFormId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public int getStructureId0() {
      return this.mStructureId0;
   }

   public int getStructureElementId0() {
      return this.mStructureElementId0;
   }

   public int getStructureId1() {
      return this.mStructureId1;
   }

   public int getStructureElementId1() {
      return this.mStructureElementId1;
   }

   public int getStructureId2() {
      return this.mStructureId2;
   }

   public int getStructureElementId2() {
      return this.mStructureElementId2;
   }

   public int getStructureId3() {
      return this.mStructureId3;
   }

   public int getStructureElementId3() {
      return this.mStructureElementId3;
   }

   public int getStructureId4() {
      return this.mStructureId4;
   }

   public int getStructureElementId4() {
      return this.mStructureElementId4;
   }

   public int getStructureId5() {
      return this.mStructureId5;
   }

   public int getStructureElementId5() {
      return this.mStructureElementId5;
   }

   public int getStructureId6() {
      return this.mStructureId6;
   }

   public int getStructureElementId6() {
      return this.mStructureElementId6;
   }

   public int getStructureId7() {
      return this.mStructureId7;
   }

   public int getStructureElementId7() {
      return this.mStructureElementId7;
   }

   public int getStructureId8() {
      return this.mStructureId8;
   }

   public int getStructureElementId8() {
      return this.mStructureElementId8;
   }

   public int getStructureId9() {
      return this.mStructureId9;
   }

   public int getStructureElementId9() {
      return this.mStructureElementId9;
   }

   public int getReportDepth() {
      return this.mReportDepth;
   }

   public int getAutoExpandDepth() {
      return this.mAutoExpandDepth;
   }

   public int getReportTemplateId() {
      return this.mReportTemplateId;
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

   public void setFormId(int newFormId) {
      if(this.mFormId != newFormId) {
         this.mModified = true;
         this.mFormId = newFormId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setStructureId0(int newStructureId0) {
      if(this.mStructureId0 != newStructureId0) {
         this.mModified = true;
         this.mStructureId0 = newStructureId0;
      }
   }

   public void setStructureElementId0(int newStructureElementId0) {
      if(this.mStructureElementId0 != newStructureElementId0) {
         this.mModified = true;
         this.mStructureElementId0 = newStructureElementId0;
      }
   }

   public void setStructureId1(int newStructureId1) {
      if(this.mStructureId1 != newStructureId1) {
         this.mModified = true;
         this.mStructureId1 = newStructureId1;
      }
   }

   public void setStructureElementId1(int newStructureElementId1) {
      if(this.mStructureElementId1 != newStructureElementId1) {
         this.mModified = true;
         this.mStructureElementId1 = newStructureElementId1;
      }
   }

   public void setStructureId2(int newStructureId2) {
      if(this.mStructureId2 != newStructureId2) {
         this.mModified = true;
         this.mStructureId2 = newStructureId2;
      }
   }

   public void setStructureElementId2(int newStructureElementId2) {
      if(this.mStructureElementId2 != newStructureElementId2) {
         this.mModified = true;
         this.mStructureElementId2 = newStructureElementId2;
      }
   }

   public void setStructureId3(int newStructureId3) {
      if(this.mStructureId3 != newStructureId3) {
         this.mModified = true;
         this.mStructureId3 = newStructureId3;
      }
   }

   public void setStructureElementId3(int newStructureElementId3) {
      if(this.mStructureElementId3 != newStructureElementId3) {
         this.mModified = true;
         this.mStructureElementId3 = newStructureElementId3;
      }
   }

   public void setStructureId4(int newStructureId4) {
      if(this.mStructureId4 != newStructureId4) {
         this.mModified = true;
         this.mStructureId4 = newStructureId4;
      }
   }

   public void setStructureElementId4(int newStructureElementId4) {
      if(this.mStructureElementId4 != newStructureElementId4) {
         this.mModified = true;
         this.mStructureElementId4 = newStructureElementId4;
      }
   }

   public void setStructureId5(int newStructureId5) {
      if(this.mStructureId5 != newStructureId5) {
         this.mModified = true;
         this.mStructureId5 = newStructureId5;
      }
   }

   public void setStructureElementId5(int newStructureElementId5) {
      if(this.mStructureElementId5 != newStructureElementId5) {
         this.mModified = true;
         this.mStructureElementId5 = newStructureElementId5;
      }
   }

   public void setStructureId6(int newStructureId6) {
      if(this.mStructureId6 != newStructureId6) {
         this.mModified = true;
         this.mStructureId6 = newStructureId6;
      }
   }

   public void setStructureElementId6(int newStructureElementId6) {
      if(this.mStructureElementId6 != newStructureElementId6) {
         this.mModified = true;
         this.mStructureElementId6 = newStructureElementId6;
      }
   }

   public void setStructureId7(int newStructureId7) {
      if(this.mStructureId7 != newStructureId7) {
         this.mModified = true;
         this.mStructureId7 = newStructureId7;
      }
   }

   public void setStructureElementId7(int newStructureElementId7) {
      if(this.mStructureElementId7 != newStructureElementId7) {
         this.mModified = true;
         this.mStructureElementId7 = newStructureElementId7;
      }
   }

   public void setStructureId8(int newStructureId8) {
      if(this.mStructureId8 != newStructureId8) {
         this.mModified = true;
         this.mStructureId8 = newStructureId8;
      }
   }

   public void setStructureElementId8(int newStructureElementId8) {
      if(this.mStructureElementId8 != newStructureElementId8) {
         this.mModified = true;
         this.mStructureElementId8 = newStructureElementId8;
      }
   }

   public void setStructureId9(int newStructureId9) {
      if(this.mStructureId9 != newStructureId9) {
         this.mModified = true;
         this.mStructureId9 = newStructureId9;
      }
   }

   public void setStructureElementId9(int newStructureElementId9) {
      if(this.mStructureElementId9 != newStructureElementId9) {
         this.mModified = true;
         this.mStructureElementId9 = newStructureElementId9;
      }
   }

   public void setReportDepth(int newReportDepth) {
      if(this.mReportDepth != newReportDepth) {
         this.mModified = true;
         this.mReportDepth = newReportDepth;
      }
   }

   public void setAutoExpandDepth(int newAutoExpandDepth) {
      if(this.mAutoExpandDepth != newAutoExpandDepth) {
         this.mModified = true;
         this.mAutoExpandDepth = newAutoExpandDepth;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportDefFormEVO newDetails) {
      this.setReportDefinitionId(newDetails.getReportDefinitionId());
      this.setModelId(newDetails.getModelId());
      this.setFormId(newDetails.getFormId());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setStructureId0(newDetails.getStructureId0());
      this.setStructureElementId0(newDetails.getStructureElementId0());
      this.setStructureId1(newDetails.getStructureId1());
      this.setStructureElementId1(newDetails.getStructureElementId1());
      this.setStructureId2(newDetails.getStructureId2());
      this.setStructureElementId2(newDetails.getStructureElementId2());
      this.setStructureId3(newDetails.getStructureId3());
      this.setStructureElementId3(newDetails.getStructureElementId3());
      this.setStructureId4(newDetails.getStructureId4());
      this.setStructureElementId4(newDetails.getStructureElementId4());
      this.setStructureId5(newDetails.getStructureId5());
      this.setStructureElementId5(newDetails.getStructureElementId5());
      this.setStructureId6(newDetails.getStructureId6());
      this.setStructureElementId6(newDetails.getStructureElementId6());
      this.setStructureId7(newDetails.getStructureId7());
      this.setStructureElementId7(newDetails.getStructureElementId7());
      this.setStructureId8(newDetails.getStructureId8());
      this.setStructureElementId8(newDetails.getStructureElementId8());
      this.setStructureId9(newDetails.getStructureId9());
      this.setStructureElementId9(newDetails.getStructureElementId9());
      this.setReportDepth(newDetails.getReportDepth());
      this.setAutoExpandDepth(newDetails.getAutoExpandDepth());
      this.setReportTemplateId(newDetails.getReportTemplateId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportDefFormEVO deepClone() {
      ReportDefFormEVO cloned = new ReportDefFormEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mReportDefinitionId = this.mReportDefinitionId;
      cloned.mModelId = this.mModelId;
      cloned.mFormId = this.mFormId;
      cloned.mDataTypeId = this.mDataTypeId;
      cloned.mStructureId0 = this.mStructureId0;
      cloned.mStructureElementId0 = this.mStructureElementId0;
      cloned.mStructureId1 = this.mStructureId1;
      cloned.mStructureElementId1 = this.mStructureElementId1;
      cloned.mStructureId2 = this.mStructureId2;
      cloned.mStructureElementId2 = this.mStructureElementId2;
      cloned.mStructureId3 = this.mStructureId3;
      cloned.mStructureElementId3 = this.mStructureElementId3;
      cloned.mStructureId4 = this.mStructureId4;
      cloned.mStructureElementId4 = this.mStructureElementId4;
      cloned.mStructureId5 = this.mStructureId5;
      cloned.mStructureElementId5 = this.mStructureElementId5;
      cloned.mStructureId6 = this.mStructureId6;
      cloned.mStructureElementId6 = this.mStructureElementId6;
      cloned.mStructureId7 = this.mStructureId7;
      cloned.mStructureElementId7 = this.mStructureElementId7;
      cloned.mStructureId8 = this.mStructureId8;
      cloned.mStructureElementId8 = this.mStructureElementId8;
      cloned.mStructureId9 = this.mStructureId9;
      cloned.mStructureElementId9 = this.mStructureElementId9;
      cloned.mReportDepth = this.mReportDepth;
      cloned.mAutoExpandDepth = this.mAutoExpandDepth;
      cloned.mReportTemplateId = this.mReportTemplateId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
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

   public ReportDefFormRef getEntityRef(ReportDefinitionEVO evoReportDefinition, String entityText) {
      return new ReportDefFormRefImpl(new ReportDefFormCK(evoReportDefinition.getPK(), this.getPK()), entityText);
   }

   public ReportDefFormCK getCK(ReportDefinitionEVO evoReportDefinition) {
      return new ReportDefFormCK(evoReportDefinition.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportDefinitionId=");
      sb.append(String.valueOf(this.mReportDefinitionId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("FormId=");
      sb.append(String.valueOf(this.mFormId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("StructureId0=");
      sb.append(String.valueOf(this.mStructureId0));
      sb.append(' ');
      sb.append("StructureElementId0=");
      sb.append(String.valueOf(this.mStructureElementId0));
      sb.append(' ');
      sb.append("StructureId1=");
      sb.append(String.valueOf(this.mStructureId1));
      sb.append(' ');
      sb.append("StructureElementId1=");
      sb.append(String.valueOf(this.mStructureElementId1));
      sb.append(' ');
      sb.append("StructureId2=");
      sb.append(String.valueOf(this.mStructureId2));
      sb.append(' ');
      sb.append("StructureElementId2=");
      sb.append(String.valueOf(this.mStructureElementId2));
      sb.append(' ');
      sb.append("StructureId3=");
      sb.append(String.valueOf(this.mStructureId3));
      sb.append(' ');
      sb.append("StructureElementId3=");
      sb.append(String.valueOf(this.mStructureElementId3));
      sb.append(' ');
      sb.append("StructureId4=");
      sb.append(String.valueOf(this.mStructureId4));
      sb.append(' ');
      sb.append("StructureElementId4=");
      sb.append(String.valueOf(this.mStructureElementId4));
      sb.append(' ');
      sb.append("StructureId5=");
      sb.append(String.valueOf(this.mStructureId5));
      sb.append(' ');
      sb.append("StructureElementId5=");
      sb.append(String.valueOf(this.mStructureElementId5));
      sb.append(' ');
      sb.append("StructureId6=");
      sb.append(String.valueOf(this.mStructureId6));
      sb.append(' ');
      sb.append("StructureElementId6=");
      sb.append(String.valueOf(this.mStructureElementId6));
      sb.append(' ');
      sb.append("StructureId7=");
      sb.append(String.valueOf(this.mStructureId7));
      sb.append(' ');
      sb.append("StructureElementId7=");
      sb.append(String.valueOf(this.mStructureElementId7));
      sb.append(' ');
      sb.append("StructureId8=");
      sb.append(String.valueOf(this.mStructureId8));
      sb.append(' ');
      sb.append("StructureElementId8=");
      sb.append(String.valueOf(this.mStructureElementId8));
      sb.append(' ');
      sb.append("StructureId9=");
      sb.append(String.valueOf(this.mStructureId9));
      sb.append(' ');
      sb.append("StructureElementId9=");
      sb.append(String.valueOf(this.mStructureElementId9));
      sb.append(' ');
      sb.append("ReportDepth=");
      sb.append(String.valueOf(this.mReportDepth));
      sb.append(' ');
      sb.append("AutoExpandDepth=");
      sb.append(String.valueOf(this.mAutoExpandDepth));
      sb.append(' ');
      sb.append("ReportTemplateId=");
      sb.append(String.valueOf(this.mReportTemplateId));
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

      sb.append("ReportDefForm: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
