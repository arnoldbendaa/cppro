// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.ModelPropertyRef;
import com.cedar.cp.dto.model.ModelPropertyCK;
import com.cedar.cp.dto.model.ModelPropertyPK;
import com.cedar.cp.dto.model.ModelPropertyRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;

public class ModelPropertyEVO implements Serializable {

   private transient ModelPropertyPK mPK;
   private int mModelId;
   private String mPropertyName;
   private String mPropertyValue;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ModelPropertyEVO() {}

   public ModelPropertyEVO(int newModelId, String newPropertyName, String newPropertyValue) {
      this.mModelId = newModelId;
      this.mPropertyName = newPropertyName;
      this.mPropertyValue = newPropertyValue;
   }

   public ModelPropertyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ModelPropertyPK(this.mModelId, this.mPropertyName);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getPropertyName() {
      return this.mPropertyName;
   }

   public String getPropertyValue() {
      return this.mPropertyValue;
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
         this.mPK = null;
      }
   }

   public void setPropertyName(String newPropertyName) {
      if(this.mPropertyName != null && newPropertyName == null || this.mPropertyName == null && newPropertyName != null || this.mPropertyName != null && newPropertyName != null && !this.mPropertyName.equals(newPropertyName)) {
         this.mPropertyName = newPropertyName;
         this.mModified = true;
      }

   }

   public void setPropertyValue(String newPropertyValue) {
      if(this.mPropertyValue != null && newPropertyValue == null || this.mPropertyValue == null && newPropertyValue != null || this.mPropertyValue != null && newPropertyValue != null && !this.mPropertyValue.equals(newPropertyValue)) {
         this.mPropertyValue = newPropertyValue;
         this.mModified = true;
      }

   }

   public void setDetails(ModelPropertyEVO newDetails) {
      this.setModelId(newDetails.getModelId());
      this.setPropertyName(newDetails.getPropertyName());
      this.setPropertyValue(newDetails.getPropertyValue());
   }

   public ModelPropertyEVO deepClone() {
      ModelPropertyEVO cloned = new ModelPropertyEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mModelId = this.mModelId;
      if(this.mPropertyName != null) {
         cloned.mPropertyName = this.mPropertyName;
      }

      if(this.mPropertyValue != null) {
         cloned.mPropertyValue = this.mPropertyValue;
      }

      return cloned;
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
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

   public ModelPropertyRef getEntityRef(ModelEVO evoModel, String entityText) {
      return new ModelPropertyRefImpl(new ModelPropertyCK(evoModel.getPK(), this.getPK()), entityText);
   }

   public ModelPropertyCK getCK(ModelEVO evoModel) {
      return new ModelPropertyCK(evoModel.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("PropertyName=");
      sb.append(String.valueOf(this.mPropertyName));
      sb.append(' ');
      sb.append("PropertyValue=");
      sb.append(String.valueOf(this.mPropertyValue));
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

      sb.append("ModelProperty: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
