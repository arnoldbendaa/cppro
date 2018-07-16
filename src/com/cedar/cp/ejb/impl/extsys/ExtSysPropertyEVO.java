// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:36
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysPropertyRef;
import com.cedar.cp.dto.extsys.ExtSysPropertyCK;
import com.cedar.cp.dto.extsys.ExtSysPropertyPK;
import com.cedar.cp.dto.extsys.ExtSysPropertyRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import java.io.Serializable;

public class ExtSysPropertyEVO implements Serializable {

   private transient ExtSysPropertyPK mPK;
   private int mExternalSystemId;
   private String mPropertyName;
   private String mPropertyValue;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysPropertyEVO() {}

   public ExtSysPropertyEVO(int newExternalSystemId, String newPropertyName, String newPropertyValue) {
      this.mExternalSystemId = newExternalSystemId;
      this.mPropertyName = newPropertyName;
      this.mPropertyValue = newPropertyValue;
   }

   public ExtSysPropertyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysPropertyPK(this.mExternalSystemId, this.mPropertyName);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }

   public String getPropertyName() {
      return this.mPropertyName;
   }

   public String getPropertyValue() {
      return this.mPropertyValue;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
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

   public void setDetails(ExtSysPropertyEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setPropertyName(newDetails.getPropertyName());
      this.setPropertyValue(newDetails.getPropertyValue());
   }

   public ExtSysPropertyEVO deepClone() {
      ExtSysPropertyEVO cloned = new ExtSysPropertyEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mExternalSystemId = this.mExternalSystemId;
      if(this.mPropertyName != null) {
         cloned.mPropertyName = this.mPropertyName;
      }

      if(this.mPropertyValue != null) {
         cloned.mPropertyValue = this.mPropertyValue;
      }

      return cloned;
   }

   public void prepareForInsert(ExternalSystemEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ExternalSystemEVO parent, int startKey) {
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

   public ExtSysPropertyRef getEntityRef(ExternalSystemEVO evoExternalSystem, String entityText) {
      return new ExtSysPropertyRefImpl(new ExtSysPropertyCK(evoExternalSystem.getPK(), this.getPK()), entityText);
   }

   public ExtSysPropertyCK getCK(ExternalSystemEVO evoExternalSystem) {
      return new ExtSysPropertyCK(evoExternalSystem.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
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

      sb.append("ExtSysProperty: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
