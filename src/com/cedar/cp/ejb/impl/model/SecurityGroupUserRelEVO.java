// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.SecurityGroupUserRelRef;
import com.cedar.cp.dto.model.SecurityGroupUserRelCK;
import com.cedar.cp.dto.model.SecurityGroupUserRelPK;
import com.cedar.cp.dto.model.SecurityGroupUserRelRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.SecurityGroupEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class SecurityGroupUserRelEVO implements Serializable {

   private transient SecurityGroupUserRelPK mPK;
   private int mSecurityGroupId;
   private int mUserId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public SecurityGroupUserRelEVO() {}

   public SecurityGroupUserRelEVO(int newSecurityGroupId, int newUserId) {
      this.mSecurityGroupId = newSecurityGroupId;
      this.mUserId = newUserId;
   }

   public SecurityGroupUserRelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new SecurityGroupUserRelPK(this.mSecurityGroupId, this.mUserId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getSecurityGroupId() {
      return this.mSecurityGroupId;
   }

   public int getUserId() {
      return this.mUserId;
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

   public void setSecurityGroupId(int newSecurityGroupId) {
      if(this.mSecurityGroupId != newSecurityGroupId) {
         this.mModified = true;
         this.mSecurityGroupId = newSecurityGroupId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
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

   public void setDetails(SecurityGroupUserRelEVO newDetails) {
      this.setSecurityGroupId(newDetails.getSecurityGroupId());
      this.setUserId(newDetails.getUserId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public SecurityGroupUserRelEVO deepClone() {
      SecurityGroupUserRelEVO cloned = new SecurityGroupUserRelEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mSecurityGroupId = this.mSecurityGroupId;
      cloned.mUserId = this.mUserId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(SecurityGroupEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(SecurityGroupEVO parent, int startKey) {
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

   public SecurityGroupUserRelRef getEntityRef(ModelEVO evoModel, SecurityGroupEVO evoSecurityGroup, String entityText) {
      return new SecurityGroupUserRelRefImpl(new SecurityGroupUserRelCK(evoModel.getPK(), evoSecurityGroup.getPK(), this.getPK()), entityText);
   }

   public SecurityGroupUserRelCK getCK(ModelEVO evoModel, SecurityGroupEVO evoSecurityGroup) {
      return new SecurityGroupUserRelCK(evoModel.getPK(), evoSecurityGroup.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SecurityGroupId=");
      sb.append(String.valueOf(this.mSecurityGroupId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
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

      sb.append("SecurityGroupUserRel: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
