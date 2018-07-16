// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cm;

import com.cedar.cp.api.cm.ChangeMgmt;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import java.io.Serializable;
import java.sql.Timestamp;

public class ChangeMgmtImpl implements ChangeMgmt, Serializable, Cloneable {

   private Object mPrimaryKey;
   private int mModelId;
   private Timestamp mCreatedTime;
   private int mTaskId;
   private String mSourceSystem;
   private String mXmlText;
   private int mVersionNum;
   private ModelRef mRelatedModelRef;


   public ChangeMgmtImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mModelId = 0;
      this.mCreatedTime = null;
      this.mTaskId = 0;
      this.mSourceSystem = "";
      this.mXmlText = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ChangeMgmtPK)paramKey;
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

   public ModelRef getRelatedModelRef() {
      return this.mRelatedModelRef;
   }

   public void setRelatedModelRef(ModelRef ref) {
      this.mRelatedModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setModelId(int paramModelId) {
      this.mModelId = paramModelId;
   }

   public void setCreatedTime(Timestamp paramCreatedTime) {
      this.mCreatedTime = paramCreatedTime;
   }

   public void setTaskId(int paramTaskId) {
      this.mTaskId = paramTaskId;
   }

   public void setSourceSystem(String paramSourceSystem) {
      this.mSourceSystem = paramSourceSystem;
   }

   public void setXmlText(String paramXmlText) {
      this.mXmlText = paramXmlText;
   }
}
