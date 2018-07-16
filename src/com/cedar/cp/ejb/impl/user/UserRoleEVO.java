// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.user.UserRoleRef;
import com.cedar.cp.dto.user.UserRoleCK;
import com.cedar.cp.dto.user.UserRolePK;
import com.cedar.cp.dto.user.UserRoleRefImpl;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class UserRoleEVO implements Serializable {

   private transient UserRolePK mPK;
   private int mUserId;
   private int mRoleId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public UserRoleEVO() {}

   public UserRoleEVO(int newUserId, int newRoleId) {
      this.mUserId = newUserId;
      this.mRoleId = newRoleId;
   }

   public UserRolePK getPK() {
      if(this.mPK == null) {
         this.mPK = new UserRolePK(this.mUserId, this.mRoleId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getRoleId() {
      return this.mRoleId;
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

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
         this.mPK = null;
      }
   }

   public void setRoleId(int newRoleId) {
      if(this.mRoleId != newRoleId) {
         this.mModified = true;
         this.mRoleId = newRoleId;
         this.mPK = null;
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

   public void setDetails(UserRoleEVO newDetails) {
      this.setUserId(newDetails.getUserId());
      this.setRoleId(newDetails.getRoleId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public UserRoleEVO deepClone() {
      UserRoleEVO cloned = new UserRoleEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mUserId = this.mUserId;
      cloned.mRoleId = this.mRoleId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(UserEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(UserEVO parent, int startKey) {
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

   public UserRoleRef getEntityRef(UserEVO evoUser, String entityText) {
      return new UserRoleRefImpl(new UserRoleCK(evoUser.getPK(), this.getPK()), entityText);
   }

   public UserRoleCK getCK(UserEVO evoUser) {
      return new UserRoleCK(evoUser.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("RoleId=");
      sb.append(String.valueOf(this.mRoleId));
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

      sb.append("UserRole: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
