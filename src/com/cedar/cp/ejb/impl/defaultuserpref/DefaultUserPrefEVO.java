// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.defaultuserpref;

import com.cedar.cp.api.defaultuserpref.DefaultUserPrefRef;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefRefImpl;
import java.io.Serializable;

public class DefaultUserPrefEVO implements Serializable {

   private transient DefaultUserPrefPK mPK;
   private String mName;
   private String mValue;
   private int mType;
   private String mHelpId;
   private int mVersionNum;
   private boolean mModified;


   public DefaultUserPrefEVO() {}

   public DefaultUserPrefEVO(String newName, String newValue, int newType, String newHelpId, int newVersionNum) {
      this.mName = newName;
      this.mValue = newValue;
      this.mType = newType;
      this.mHelpId = newHelpId;
      this.mVersionNum = newVersionNum;
   }

   public DefaultUserPrefPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DefaultUserPrefPK(this.mName);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public String getName() {
      return this.mName;
   }

   public String getValue() {
      return this.mValue;
   }

   public int getType() {
      return this.mType;
   }

   public String getHelpId() {
      return this.mHelpId;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setType(int newType) {
      if(this.mType != newType) {
         this.mModified = true;
         this.mType = newType;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setName(String newName) {
      if(this.mName != null && newName == null || this.mName == null && newName != null || this.mName != null && newName != null && !this.mName.equals(newName)) {
         this.mName = newName;
         this.mModified = true;
      }

   }

   public void setValue(String newValue) {
      if(this.mValue != null && newValue == null || this.mValue == null && newValue != null || this.mValue != null && newValue != null && !this.mValue.equals(newValue)) {
         this.mValue = newValue;
         this.mModified = true;
      }

   }

   public void setHelpId(String newHelpId) {
      if(this.mHelpId != null && newHelpId == null || this.mHelpId == null && newHelpId != null || this.mHelpId != null && newHelpId != null && !this.mHelpId.equals(newHelpId)) {
         this.mHelpId = newHelpId;
         this.mModified = true;
      }

   }

   public void setDetails(DefaultUserPrefEVO newDetails) {
      this.setName(newDetails.getName());
      this.setValue(newDetails.getValue());
      this.setType(newDetails.getType());
      this.setHelpId(newDetails.getHelpId());
      this.setVersionNum(newDetails.getVersionNum());
   }

   public DefaultUserPrefEVO deepClone() {
      DefaultUserPrefEVO cloned = new DefaultUserPrefEVO();
      cloned.mModified = this.mModified;
      cloned.mType = this.mType;
      cloned.mVersionNum = this.mVersionNum;
      if(this.mName != null) {
         cloned.mName = this.mName;
      }

      if(this.mValue != null) {
         cloned.mValue = this.mValue;
      }

      if(this.mHelpId != null) {
         cloned.mHelpId = this.mHelpId;
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(int startKey) {
      return startKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public DefaultUserPrefRef getEntityRef() {
      return new DefaultUserPrefRefImpl(this.getPK(), this.mName);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Name=");
      sb.append(String.valueOf(this.mName));
      sb.append(' ');
      sb.append("Value=");
      sb.append(String.valueOf(this.mValue));
      sb.append(' ');
      sb.append("Type=");
      sb.append(String.valueOf(this.mType));
      sb.append(' ');
      sb.append("HelpId=");
      sb.append(String.valueOf(this.mHelpId));
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

      sb.append("DefaultUserPref: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
