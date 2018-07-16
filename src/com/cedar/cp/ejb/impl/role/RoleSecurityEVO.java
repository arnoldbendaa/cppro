// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.role.RoleSecurityRef;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.dto.role.RoleSecurityRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class RoleSecurityEVO implements Serializable {

   private transient RoleSecurityPK mPK;
   private int mRoleSecurityId;
   private String mSecurityString;
   private String mDescription;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;


   public RoleSecurityEVO() {}

   public RoleSecurityEVO(int newRoleSecurityId, String newSecurityString, String newDescription) {
      this.mRoleSecurityId = newRoleSecurityId;
      this.mSecurityString = newSecurityString;
      this.mDescription = newDescription;
   }

   public RoleSecurityPK getPK() {
      if(this.mPK == null) {
         this.mPK = new RoleSecurityPK(this.mRoleSecurityId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRoleSecurityId() {
      return this.mRoleSecurityId;
   }

   public String getSecurityString() {
      return this.mSecurityString;
   }

   public String getDescription() {
      return this.mDescription;
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

   public void setRoleSecurityId(int newRoleSecurityId) {
      if(this.mRoleSecurityId != newRoleSecurityId) {
         this.mModified = true;
         this.mRoleSecurityId = newRoleSecurityId;
         this.mPK = null;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setSecurityString(String newSecurityString) {
      if(this.mSecurityString != null && newSecurityString == null || this.mSecurityString == null && newSecurityString != null || this.mSecurityString != null && newSecurityString != null && !this.mSecurityString.equals(newSecurityString)) {
         this.mSecurityString = newSecurityString;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(RoleSecurityEVO newDetails) {
      this.setRoleSecurityId(newDetails.getRoleSecurityId());
      this.setSecurityString(newDetails.getSecurityString());
      this.setDescription(newDetails.getDescription());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RoleSecurityEVO deepClone() {
      RoleSecurityEVO cloned = new RoleSecurityEVO();
      cloned.mModified = this.mModified;
      cloned.mRoleSecurityId = this.mRoleSecurityId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mSecurityString != null) {
         cloned.mSecurityString = this.mSecurityString;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
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
      if(this.mRoleSecurityId > 0) {
         newKey = true;
         this.mRoleSecurityId = 0;
      } else if(this.mRoleSecurityId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRoleSecurityId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mRoleSecurityId < 1) {
         this.mRoleSecurityId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public RoleSecurityRef getEntityRef() {
      return new RoleSecurityRefImpl(this.getPK(), this.mSecurityString);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RoleSecurityId=");
      sb.append(String.valueOf(this.mRoleSecurityId));
      sb.append(' ');
      sb.append("SecurityString=");
      sb.append(String.valueOf(this.mSecurityString));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
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

      sb.append("RoleSecurity: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
