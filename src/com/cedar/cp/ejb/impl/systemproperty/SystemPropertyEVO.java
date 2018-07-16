// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.systemproperty;

import com.cedar.cp.api.systemproperty.SystemPropertyRef;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.dto.systemproperty.SystemPropertyRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class SystemPropertyEVO implements Serializable {

   private transient SystemPropertyPK mPK;
   private int mSystemPropertyId;
   private String mProperty;
   private String mValue;
   private String mDescription;
   private String mValidateExp;
   private String mValidateTxt;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;


   public SystemPropertyEVO() {}

   public SystemPropertyEVO(int newSystemPropertyId, String newProperty, String newValue, String newDescription, String newValidateExp, String newValidateTxt, int newVersionNum) {
      this.mSystemPropertyId = newSystemPropertyId;
      this.mProperty = newProperty;
      this.mValue = newValue;
      this.mDescription = newDescription;
      this.mValidateExp = newValidateExp;
      this.mValidateTxt = newValidateTxt;
      this.mVersionNum = newVersionNum;
   }

   public SystemPropertyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new SystemPropertyPK(this.mSystemPropertyId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getSystemPropertyId() {
      return this.mSystemPropertyId;
   }

   public String getProperty() {
      return this.mProperty;
   }

   public String getValue() {
      return this.mValue;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getValidateExp() {
      return this.mValidateExp;
   }

   public String getValidateTxt() {
      return this.mValidateTxt;
   }

   public int getVersionNum() {
      return this.mVersionNum;
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

   public void setSystemPropertyId(int newSystemPropertyId) {
      if(this.mSystemPropertyId != newSystemPropertyId) {
         this.mModified = true;
         this.mSystemPropertyId = newSystemPropertyId;
         this.mPK = null;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setProperty(String newProperty) {
      if(this.mProperty != null && newProperty == null || this.mProperty == null && newProperty != null || this.mProperty != null && newProperty != null && !this.mProperty.equals(newProperty)) {
         this.mProperty = newProperty;
         this.mModified = true;
      }

   }

   public void setValue(String newValue) {
      if(this.mValue != null && newValue == null || this.mValue == null && newValue != null || this.mValue != null && newValue != null && !this.mValue.equals(newValue)) {
         this.mValue = newValue;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setValidateExp(String newValidateExp) {
      if(this.mValidateExp != null && newValidateExp == null || this.mValidateExp == null && newValidateExp != null || this.mValidateExp != null && newValidateExp != null && !this.mValidateExp.equals(newValidateExp)) {
         this.mValidateExp = newValidateExp;
         this.mModified = true;
      }

   }

   public void setValidateTxt(String newValidateTxt) {
      if(this.mValidateTxt != null && newValidateTxt == null || this.mValidateTxt == null && newValidateTxt != null || this.mValidateTxt != null && newValidateTxt != null && !this.mValidateTxt.equals(newValidateTxt)) {
         this.mValidateTxt = newValidateTxt;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(SystemPropertyEVO newDetails) {
      this.setSystemPropertyId(newDetails.getSystemPropertyId());
      this.setProperty(newDetails.getProperty());
      this.setValue(newDetails.getValue());
      this.setDescription(newDetails.getDescription());
      this.setValidateExp(newDetails.getValidateExp());
      this.setValidateTxt(newDetails.getValidateTxt());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public SystemPropertyEVO deepClone() {
      SystemPropertyEVO cloned = new SystemPropertyEVO();
      cloned.mModified = this.mModified;
      cloned.mSystemPropertyId = this.mSystemPropertyId;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mProperty != null) {
         cloned.mProperty = this.mProperty;
      }

      if(this.mValue != null) {
         cloned.mValue = this.mValue;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mValidateExp != null) {
         cloned.mValidateExp = this.mValidateExp;
      }

      if(this.mValidateTxt != null) {
         cloned.mValidateTxt = this.mValidateTxt;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mSystemPropertyId > 0) {
         newKey = true;
         this.mSystemPropertyId = 0;
      } else if(this.mSystemPropertyId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mSystemPropertyId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mSystemPropertyId < 1) {
         this.mSystemPropertyId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public SystemPropertyRef getEntityRef() {
      return new SystemPropertyRefImpl(this.getPK(), this.mProperty);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SystemPropertyId=");
      sb.append(String.valueOf(this.mSystemPropertyId));
      sb.append(' ');
      sb.append("Property=");
      sb.append(String.valueOf(this.mProperty));
      sb.append(' ');
      sb.append("Value=");
      sb.append(String.valueOf(this.mValue));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ValidateExp=");
      sb.append(String.valueOf(this.mValidateExp));
      sb.append(' ');
      sb.append("ValidateTxt=");
      sb.append(String.valueOf(this.mValidateTxt));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
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

      sb.append("SystemProperty: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
