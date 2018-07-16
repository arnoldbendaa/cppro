// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.cm.ChangeMgmtRef;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.dto.cm.ChangeMgmtRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class ChangeMgmtEVO implements Serializable {

   private transient ChangeMgmtPK mPK;
   private int mChangeMgmtId;
   private int mModelId;
   private Timestamp mCreatedTime;
   private int mTaskId;
   private String mSourceSystem;
   private String mXmlText;
   private int mVersionNum;
   private boolean mModified;


   public ChangeMgmtEVO() {}

   public ChangeMgmtEVO(int newChangeMgmtId, int newModelId, Timestamp newCreatedTime, int newTaskId, String newSourceSystem, String newXmlText, int newVersionNum) {
      this.mChangeMgmtId = newChangeMgmtId;
      this.mModelId = newModelId;
      this.mCreatedTime = newCreatedTime;
      this.mTaskId = newTaskId;
      this.mSourceSystem = newSourceSystem;
      this.mXmlText = newXmlText;
      this.mVersionNum = newVersionNum;
   }

   public ChangeMgmtPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ChangeMgmtPK(this.mChangeMgmtId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getChangeMgmtId() {
      return this.mChangeMgmtId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public String getSourceSystem() {
      return this.mSourceSystem;
   }

   public String getXmlText() {
      return this.mXmlText;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setChangeMgmtId(int newChangeMgmtId) {
      if(this.mChangeMgmtId != newChangeMgmtId) {
         this.mModified = true;
         this.mChangeMgmtId = newChangeMgmtId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setTaskId(int newTaskId) {
      if(this.mTaskId != newTaskId) {
         this.mModified = true;
         this.mTaskId = newTaskId;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setCreatedTime(Timestamp newCreatedTime) {
      if(this.mCreatedTime != null && newCreatedTime == null || this.mCreatedTime == null && newCreatedTime != null || this.mCreatedTime != null && newCreatedTime != null && !this.mCreatedTime.equals(newCreatedTime)) {
         this.mCreatedTime = newCreatedTime;
         this.mModified = true;
      }

   }

   public void setSourceSystem(String newSourceSystem) {
      if(this.mSourceSystem != null && newSourceSystem == null || this.mSourceSystem == null && newSourceSystem != null || this.mSourceSystem != null && newSourceSystem != null && !this.mSourceSystem.equals(newSourceSystem)) {
         this.mSourceSystem = newSourceSystem;
         this.mModified = true;
      }

   }

   public void setXmlText(String newXmlText) {
      if(this.mXmlText != null && newXmlText == null || this.mXmlText == null && newXmlText != null || this.mXmlText != null && newXmlText != null && !this.mXmlText.equals(newXmlText)) {
         this.mXmlText = newXmlText;
         this.mModified = true;
      }

   }

   public void setDetails(ChangeMgmtEVO newDetails) {
      this.setChangeMgmtId(newDetails.getChangeMgmtId());
      this.setModelId(newDetails.getModelId());
      this.setCreatedTime(newDetails.getCreatedTime());
      this.setTaskId(newDetails.getTaskId());
      this.setSourceSystem(newDetails.getSourceSystem());
      this.setXmlText(newDetails.getXmlText());
      this.setVersionNum(newDetails.getVersionNum());
   }

   public ChangeMgmtEVO deepClone() {
      ChangeMgmtEVO cloned = new ChangeMgmtEVO();
      cloned.mModified = this.mModified;
      cloned.mChangeMgmtId = this.mChangeMgmtId;
      cloned.mModelId = this.mModelId;
      cloned.mTaskId = this.mTaskId;
      cloned.mVersionNum = this.mVersionNum;
      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      if(this.mSourceSystem != null) {
         cloned.mSourceSystem = this.mSourceSystem;
      }

      if(this.mXmlText != null) {
         cloned.mXmlText = this.mXmlText;
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mChangeMgmtId > 0) {
         newKey = true;
         this.mChangeMgmtId = 0;
      } else if(this.mChangeMgmtId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mChangeMgmtId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mChangeMgmtId < 1) {
         this.mChangeMgmtId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public ChangeMgmtRef getEntityRef(String entityText) {
      return new ChangeMgmtRefImpl(this.getPK(), entityText);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ChangeMgmtId=");
      sb.append(String.valueOf(this.mChangeMgmtId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      sb.append("TaskId=");
      sb.append(String.valueOf(this.mTaskId));
      sb.append(' ');
      sb.append("SourceSystem=");
      sb.append(String.valueOf(this.mSourceSystem));
      sb.append(' ');
      sb.append("XmlText=");
      sb.append(String.valueOf(this.mXmlText));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
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

      sb.append("ChangeMgmt: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
