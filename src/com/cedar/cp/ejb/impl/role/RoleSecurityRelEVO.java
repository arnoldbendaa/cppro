// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.role.RoleSecurityRelRef;
import com.cedar.cp.dto.role.RoleSecurityRelCK;
import com.cedar.cp.dto.role.RoleSecurityRelPK;
import com.cedar.cp.dto.role.RoleSecurityRelRefImpl;
import com.cedar.cp.ejb.impl.role.RoleEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class RoleSecurityRelEVO implements Serializable {

   private transient RoleSecurityRelPK mPK;
   private int mRoleId;
   private int mRoleSecurityId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public RoleSecurityRelEVO() {}

   public RoleSecurityRelEVO(int newRoleId, int newRoleSecurityId, int newVersionNum) {
      this.mRoleId = newRoleId;
      this.mRoleSecurityId = newRoleSecurityId;
      this.mVersionNum = newVersionNum;
   }

   public RoleSecurityRelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new RoleSecurityRelPK(this.mRoleId, this.mRoleSecurityId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRoleId() {
      return this.mRoleId;
   }

   public int getRoleSecurityId() {
      return this.mRoleSecurityId;
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

   public void setRoleId(int newRoleId) {
      if(this.mRoleId != newRoleId) {
         this.mModified = true;
         this.mRoleId = newRoleId;
         this.mPK = null;
      }
   }

   public void setRoleSecurityId(int newRoleSecurityId) {
      if(this.mRoleSecurityId != newRoleSecurityId) {
         this.mModified = true;
         this.mRoleSecurityId = newRoleSecurityId;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(RoleSecurityRelEVO newDetails) {
      this.setRoleId(newDetails.getRoleId());
      this.setRoleSecurityId(newDetails.getRoleSecurityId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RoleSecurityRelEVO deepClone() {
      RoleSecurityRelEVO cloned = new RoleSecurityRelEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mRoleId = this.mRoleId;
      cloned.mRoleSecurityId = this.mRoleSecurityId;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(RoleEVO parent) {
      boolean newKey = this.insertPending();
      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(RoleEVO parent, int startKey) {
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

   public RoleSecurityRelRef getEntityRef(RoleEVO evoRole, String entityText) {
      return new RoleSecurityRelRefImpl(new RoleSecurityRelCK(evoRole.getPK(), this.getPK()), entityText);
   }

   public RoleSecurityRelCK getCK(RoleEVO evoRole) {
      return new RoleSecurityRelCK(evoRole.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RoleId=");
      sb.append(String.valueOf(this.mRoleId));
      sb.append(' ');
      sb.append("RoleSecurityId=");
      sb.append(String.valueOf(this.mRoleSecurityId));
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

      sb.append("RoleSecurityRel: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
