// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform.rebuild;

import com.cedar.cp.api.xmlform.rebuild.FormRebuildRef;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class FormRebuildEVO implements Serializable {

   private transient FormRebuildPK mPK;
   private int mFormRebuildId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private Timestamp mLastSubmit;
   private int mXmlformId;
   private int mBudgetCycleId;
   private int mStructureId0;
   private int mStructureId1;
   private int mStructureId2;
   private int mStructureId3;
   private int mStructureId4;
   private int mStructureId5;
   private int mStructureId6;
   private int mStructureId7;
   private int mStructureId8;
   private int mStructureElementId0;
   private int mStructureElementId1;
   private int mStructureElementId2;
   private int mStructureElementId3;
   private int mStructureElementId4;
   private int mStructureElementId5;
   private int mStructureElementId6;
   private int mStructureElementId7;
   private int mStructureElementId8;
   private String mDataType;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public FormRebuildEVO() {}

   public FormRebuildEVO(int newFormRebuildId, int newModelId, String newVisId, String newDescription, Timestamp newLastSubmit, int newXmlformId, int newBudgetCycleId, int newStructureId0, int newStructureId1, int newStructureId2, int newStructureId3, int newStructureId4, int newStructureId5, int newStructureId6, int newStructureId7, int newStructureId8, int newStructureElementId0, int newStructureElementId1, int newStructureElementId2, int newStructureElementId3, int newStructureElementId4, int newStructureElementId5, int newStructureElementId6, int newStructureElementId7, int newStructureElementId8, String newDataType) {
      this.mFormRebuildId = newFormRebuildId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mLastSubmit = newLastSubmit;
      this.mXmlformId = newXmlformId;
      this.mBudgetCycleId = newBudgetCycleId;
      this.mStructureId0 = newStructureId0;
      this.mStructureId1 = newStructureId1;
      this.mStructureId2 = newStructureId2;
      this.mStructureId3 = newStructureId3;
      this.mStructureId4 = newStructureId4;
      this.mStructureId5 = newStructureId5;
      this.mStructureId6 = newStructureId6;
      this.mStructureId7 = newStructureId7;
      this.mStructureId8 = newStructureId8;
      this.mStructureElementId0 = newStructureElementId0;
      this.mStructureElementId1 = newStructureElementId1;
      this.mStructureElementId2 = newStructureElementId2;
      this.mStructureElementId3 = newStructureElementId3;
      this.mStructureElementId4 = newStructureElementId4;
      this.mStructureElementId5 = newStructureElementId5;
      this.mStructureElementId6 = newStructureElementId6;
      this.mStructureElementId7 = newStructureElementId7;
      this.mStructureElementId8 = newStructureElementId8;
      this.mDataType = newDataType;
   }

   public FormRebuildPK getPK() {
      if(this.mPK == null) {
         this.mPK = new FormRebuildPK(this.mFormRebuildId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getFormRebuildId() {
      return this.mFormRebuildId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int[] getStructureIdArray() {
      return new int[]{this.getStructureId0(), this.getStructureId1(), this.getStructureId2(), this.getStructureId3(), this.getStructureId4(), this.getStructureId5(), this.getStructureId6(), this.getStructureId7(), this.getStructureId8()};
   }

   public void setStructureIdArray(int[] p) {
      this.setStructureId0(p[0]);
      this.setStructureId1(p[1]);
      this.setStructureId2(p[2]);
      this.setStructureId3(p[3]);
      this.setStructureId4(p[4]);
      this.setStructureId5(p[5]);
      this.setStructureId6(p[6]);
      this.setStructureId7(p[7]);
      this.setStructureId8(p[8]);
   }

   public int getStructureId0() {
      return this.mStructureId0;
   }

   public int getStructureId1() {
      return this.mStructureId1;
   }

   public int getStructureId2() {
      return this.mStructureId2;
   }

   public int getStructureId3() {
      return this.mStructureId3;
   }

   public int getStructureId4() {
      return this.mStructureId4;
   }

   public int getStructureId5() {
      return this.mStructureId5;
   }

   public int getStructureId6() {
      return this.mStructureId6;
   }

   public int getStructureId7() {
      return this.mStructureId7;
   }

   public int getStructureId8() {
      return this.mStructureId8;
   }

   public int[] getStructureElementIdArray() {
      return new int[]{this.getStructureElementId0(), this.getStructureElementId1(), this.getStructureElementId2(), this.getStructureElementId3(), this.getStructureElementId4(), this.getStructureElementId5(), this.getStructureElementId6(), this.getStructureElementId7(), this.getStructureElementId8()};
   }

   public void setStructureElementIdArray(int[] p) {
      this.setStructureElementId0(p[0]);
      this.setStructureElementId1(p[1]);
      this.setStructureElementId2(p[2]);
      this.setStructureElementId3(p[3]);
      this.setStructureElementId4(p[4]);
      this.setStructureElementId5(p[5]);
      this.setStructureElementId6(p[6]);
      this.setStructureElementId7(p[7]);
      this.setStructureElementId8(p[8]);
   }

   public int getStructureElementId0() {
      return this.mStructureElementId0;
   }

   public int getStructureElementId1() {
      return this.mStructureElementId1;
   }

   public int getStructureElementId2() {
      return this.mStructureElementId2;
   }

   public int getStructureElementId3() {
      return this.mStructureElementId3;
   }

   public int getStructureElementId4() {
      return this.mStructureElementId4;
   }

   public int getStructureElementId5() {
      return this.mStructureElementId5;
   }

   public int getStructureElementId6() {
      return this.mStructureElementId6;
   }

   public int getStructureElementId7() {
      return this.mStructureElementId7;
   }

   public int getStructureElementId8() {
      return this.mStructureElementId8;
   }

   public String getDataType() {
      return this.mDataType;
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

   public void setFormRebuildId(int newFormRebuildId) {
      if(this.mFormRebuildId != newFormRebuildId) {
         this.mModified = true;
         this.mFormRebuildId = newFormRebuildId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setXmlformId(int newXmlformId) {
      if(this.mXmlformId != newXmlformId) {
         this.mModified = true;
         this.mXmlformId = newXmlformId;
      }
   }

   public void setBudgetCycleId(int newBudgetCycleId) {
      if(this.mBudgetCycleId != newBudgetCycleId) {
         this.mModified = true;
         this.mBudgetCycleId = newBudgetCycleId;
      }
   }

   public void setStructureId0(int newStructureId0) {
      if(this.mStructureId0 != newStructureId0) {
         this.mModified = true;
         this.mStructureId0 = newStructureId0;
      }
   }

   public void setStructureId1(int newStructureId1) {
      if(this.mStructureId1 != newStructureId1) {
         this.mModified = true;
         this.mStructureId1 = newStructureId1;
      }
   }

   public void setStructureId2(int newStructureId2) {
      if(this.mStructureId2 != newStructureId2) {
         this.mModified = true;
         this.mStructureId2 = newStructureId2;
      }
   }

   public void setStructureId3(int newStructureId3) {
      if(this.mStructureId3 != newStructureId3) {
         this.mModified = true;
         this.mStructureId3 = newStructureId3;
      }
   }

   public void setStructureId4(int newStructureId4) {
      if(this.mStructureId4 != newStructureId4) {
         this.mModified = true;
         this.mStructureId4 = newStructureId4;
      }
   }

   public void setStructureId5(int newStructureId5) {
      if(this.mStructureId5 != newStructureId5) {
         this.mModified = true;
         this.mStructureId5 = newStructureId5;
      }
   }

   public void setStructureId6(int newStructureId6) {
      if(this.mStructureId6 != newStructureId6) {
         this.mModified = true;
         this.mStructureId6 = newStructureId6;
      }
   }

   public void setStructureId7(int newStructureId7) {
      if(this.mStructureId7 != newStructureId7) {
         this.mModified = true;
         this.mStructureId7 = newStructureId7;
      }
   }

   public void setStructureId8(int newStructureId8) {
      if(this.mStructureId8 != newStructureId8) {
         this.mModified = true;
         this.mStructureId8 = newStructureId8;
      }
   }

   public void setStructureElementId0(int newStructureElementId0) {
      if(this.mStructureElementId0 != newStructureElementId0) {
         this.mModified = true;
         this.mStructureElementId0 = newStructureElementId0;
      }
   }

   public void setStructureElementId1(int newStructureElementId1) {
      if(this.mStructureElementId1 != newStructureElementId1) {
         this.mModified = true;
         this.mStructureElementId1 = newStructureElementId1;
      }
   }

   public void setStructureElementId2(int newStructureElementId2) {
      if(this.mStructureElementId2 != newStructureElementId2) {
         this.mModified = true;
         this.mStructureElementId2 = newStructureElementId2;
      }
   }

   public void setStructureElementId3(int newStructureElementId3) {
      if(this.mStructureElementId3 != newStructureElementId3) {
         this.mModified = true;
         this.mStructureElementId3 = newStructureElementId3;
      }
   }

   public void setStructureElementId4(int newStructureElementId4) {
      if(this.mStructureElementId4 != newStructureElementId4) {
         this.mModified = true;
         this.mStructureElementId4 = newStructureElementId4;
      }
   }

   public void setStructureElementId5(int newStructureElementId5) {
      if(this.mStructureElementId5 != newStructureElementId5) {
         this.mModified = true;
         this.mStructureElementId5 = newStructureElementId5;
      }
   }

   public void setStructureElementId6(int newStructureElementId6) {
      if(this.mStructureElementId6 != newStructureElementId6) {
         this.mModified = true;
         this.mStructureElementId6 = newStructureElementId6;
      }
   }

   public void setStructureElementId7(int newStructureElementId7) {
      if(this.mStructureElementId7 != newStructureElementId7) {
         this.mModified = true;
         this.mStructureElementId7 = newStructureElementId7;
      }
   }

   public void setStructureElementId8(int newStructureElementId8) {
      if(this.mStructureElementId8 != newStructureElementId8) {
         this.mModified = true;
         this.mStructureElementId8 = newStructureElementId8;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setLastSubmit(Timestamp newLastSubmit) {
      if(this.mLastSubmit != null && newLastSubmit == null || this.mLastSubmit == null && newLastSubmit != null || this.mLastSubmit != null && newLastSubmit != null && !this.mLastSubmit.equals(newLastSubmit)) {
         this.mLastSubmit = newLastSubmit;
         this.mModified = true;
      }

   }

   public void setDataType(String newDataType) {
      if(this.mDataType != null && newDataType == null || this.mDataType == null && newDataType != null || this.mDataType != null && newDataType != null && !this.mDataType.equals(newDataType)) {
         this.mDataType = newDataType;
         this.mModified = true;
      }

   }

   public void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   public void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(FormRebuildEVO newDetails) {
      this.setFormRebuildId(newDetails.getFormRebuildId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setLastSubmit(newDetails.getLastSubmit());
      this.setXmlformId(newDetails.getXmlformId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setStructureId0(newDetails.getStructureId0());
      this.setStructureId1(newDetails.getStructureId1());
      this.setStructureId2(newDetails.getStructureId2());
      this.setStructureId3(newDetails.getStructureId3());
      this.setStructureId4(newDetails.getStructureId4());
      this.setStructureId5(newDetails.getStructureId5());
      this.setStructureId6(newDetails.getStructureId6());
      this.setStructureId7(newDetails.getStructureId7());
      this.setStructureId8(newDetails.getStructureId8());
      this.setStructureElementId0(newDetails.getStructureElementId0());
      this.setStructureElementId1(newDetails.getStructureElementId1());
      this.setStructureElementId2(newDetails.getStructureElementId2());
      this.setStructureElementId3(newDetails.getStructureElementId3());
      this.setStructureElementId4(newDetails.getStructureElementId4());
      this.setStructureElementId5(newDetails.getStructureElementId5());
      this.setStructureElementId6(newDetails.getStructureElementId6());
      this.setStructureElementId7(newDetails.getStructureElementId7());
      this.setStructureElementId8(newDetails.getStructureElementId8());
      this.setDataType(newDetails.getDataType());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public FormRebuildEVO deepClone() {
      FormRebuildEVO cloned = new FormRebuildEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mFormRebuildId = this.mFormRebuildId;
      cloned.mModelId = this.mModelId;
      cloned.mXmlformId = this.mXmlformId;
      cloned.mBudgetCycleId = this.mBudgetCycleId;
      cloned.mStructureId0 = this.mStructureId0;
      cloned.mStructureId1 = this.mStructureId1;
      cloned.mStructureId2 = this.mStructureId2;
      cloned.mStructureId3 = this.mStructureId3;
      cloned.mStructureId4 = this.mStructureId4;
      cloned.mStructureId5 = this.mStructureId5;
      cloned.mStructureId6 = this.mStructureId6;
      cloned.mStructureId7 = this.mStructureId7;
      cloned.mStructureId8 = this.mStructureId8;
      cloned.mStructureElementId0 = this.mStructureElementId0;
      cloned.mStructureElementId1 = this.mStructureElementId1;
      cloned.mStructureElementId2 = this.mStructureElementId2;
      cloned.mStructureElementId3 = this.mStructureElementId3;
      cloned.mStructureElementId4 = this.mStructureElementId4;
      cloned.mStructureElementId5 = this.mStructureElementId5;
      cloned.mStructureElementId6 = this.mStructureElementId6;
      cloned.mStructureElementId7 = this.mStructureElementId7;
      cloned.mStructureElementId8 = this.mStructureElementId8;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mLastSubmit != null) {
         cloned.mLastSubmit = Timestamp.valueOf(this.mLastSubmit.toString());
      }

      if(this.mDataType != null) {
         cloned.mDataType = this.mDataType;
      }

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
      if(this.mFormRebuildId > 0) {
         newKey = true;
         if(parent == null) {
            this.setFormRebuildId(-this.mFormRebuildId);
         } else {
            parent.changeKey(this, -this.mFormRebuildId);
         }
      } else if(this.mFormRebuildId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mFormRebuildId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mFormRebuildId < 1) {
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

   public void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public FormRebuildRef getEntityRef(ModelEVO evoModel) {
      return new FormRebuildRefImpl(new FormRebuildCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public FormRebuildCK getCK(ModelEVO evoModel) {
      return new FormRebuildCK(evoModel.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("FormRebuildId=");
      sb.append(String.valueOf(this.mFormRebuildId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("LastSubmit=");
      sb.append(String.valueOf(this.mLastSubmit));
      sb.append(' ');
      sb.append("XmlformId=");
      sb.append(String.valueOf(this.mXmlformId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("StructureId0=");
      sb.append(String.valueOf(this.mStructureId0));
      sb.append(' ');
      sb.append("StructureId1=");
      sb.append(String.valueOf(this.mStructureId1));
      sb.append(' ');
      sb.append("StructureId2=");
      sb.append(String.valueOf(this.mStructureId2));
      sb.append(' ');
      sb.append("StructureId3=");
      sb.append(String.valueOf(this.mStructureId3));
      sb.append(' ');
      sb.append("StructureId4=");
      sb.append(String.valueOf(this.mStructureId4));
      sb.append(' ');
      sb.append("StructureId5=");
      sb.append(String.valueOf(this.mStructureId5));
      sb.append(' ');
      sb.append("StructureId6=");
      sb.append(String.valueOf(this.mStructureId6));
      sb.append(' ');
      sb.append("StructureId7=");
      sb.append(String.valueOf(this.mStructureId7));
      sb.append(' ');
      sb.append("StructureId8=");
      sb.append(String.valueOf(this.mStructureId8));
      sb.append(' ');
      sb.append("StructureElementId0=");
      sb.append(String.valueOf(this.mStructureElementId0));
      sb.append(' ');
      sb.append("StructureElementId1=");
      sb.append(String.valueOf(this.mStructureElementId1));
      sb.append(' ');
      sb.append("StructureElementId2=");
      sb.append(String.valueOf(this.mStructureElementId2));
      sb.append(' ');
      sb.append("StructureElementId3=");
      sb.append(String.valueOf(this.mStructureElementId3));
      sb.append(' ');
      sb.append("StructureElementId4=");
      sb.append(String.valueOf(this.mStructureElementId4));
      sb.append(' ');
      sb.append("StructureElementId5=");
      sb.append(String.valueOf(this.mStructureElementId5));
      sb.append(' ');
      sb.append("StructureElementId6=");
      sb.append(String.valueOf(this.mStructureElementId6));
      sb.append(' ');
      sb.append("StructureElementId7=");
      sb.append(String.valueOf(this.mStructureElementId7));
      sb.append(' ');
      sb.append("StructureElementId8=");
      sb.append(String.valueOf(this.mStructureElementId8));
      sb.append(' ');
      sb.append("DataType=");
      sb.append(String.valueOf(this.mDataType));
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

      sb.append("FormRebuild: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
