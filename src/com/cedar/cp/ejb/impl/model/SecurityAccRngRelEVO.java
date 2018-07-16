// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.SecurityAccRngRelRef;
import com.cedar.cp.dto.model.SecurityAccRngRelCK;
import com.cedar.cp.dto.model.SecurityAccRngRelPK;
import com.cedar.cp.dto.model.SecurityAccRngRelRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class SecurityAccRngRelEVO implements Serializable {

   private transient SecurityAccRngRelPK mPK;
   private int mSecurityAccessDefId;
   private int mSecurityRangeId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public SecurityAccRngRelEVO() {}

   public SecurityAccRngRelEVO(int newSecurityAccessDefId, int newSecurityRangeId, int newVersionNum) {
      this.mSecurityAccessDefId = newSecurityAccessDefId;
      this.mSecurityRangeId = newSecurityRangeId;
      this.mVersionNum = newVersionNum;
   }

   public SecurityAccRngRelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new SecurityAccRngRelPK(this.mSecurityAccessDefId, this.mSecurityRangeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getSecurityAccessDefId() {
      return this.mSecurityAccessDefId;
   }

   public int getSecurityRangeId() {
      return this.mSecurityRangeId;
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

   public void setSecurityAccessDefId(int newSecurityAccessDefId) {
      if(this.mSecurityAccessDefId != newSecurityAccessDefId) {
         this.mModified = true;
         this.mSecurityAccessDefId = newSecurityAccessDefId;
         this.mPK = null;
      }
   }

   public void setSecurityRangeId(int newSecurityRangeId) {
      if(this.mSecurityRangeId != newSecurityRangeId) {
         this.mModified = true;
         this.mSecurityRangeId = newSecurityRangeId;
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

   public void setDetails(SecurityAccRngRelEVO newDetails) {
      this.setSecurityAccessDefId(newDetails.getSecurityAccessDefId());
      this.setSecurityRangeId(newDetails.getSecurityRangeId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public SecurityAccRngRelEVO deepClone() {
      SecurityAccRngRelEVO cloned = new SecurityAccRngRelEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mSecurityAccessDefId = this.mSecurityAccessDefId;
      cloned.mSecurityRangeId = this.mSecurityRangeId;
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

   public void prepareForInsert(SecurityAccessDefEVO parent) {
      boolean newKey = this.insertPending();
      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(SecurityAccessDefEVO parent, int startKey) {
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

   public SecurityAccRngRelRef getEntityRef(ModelEVO evoModel, SecurityAccessDefEVO evoSecurityAccessDef, String entityText) {
      return new SecurityAccRngRelRefImpl(new SecurityAccRngRelCK(evoModel.getPK(), evoSecurityAccessDef.getPK(), this.getPK()), entityText);
   }

   public SecurityAccRngRelCK getCK(ModelEVO evoModel, SecurityAccessDefEVO evoSecurityAccessDef) {
      return new SecurityAccRngRelCK(evoModel.getPK(), evoSecurityAccessDef.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SecurityAccessDefId=");
      sb.append(String.valueOf(this.mSecurityAccessDefId));
      sb.append(' ');
      sb.append("SecurityRangeId=");
      sb.append(String.valueOf(this.mSecurityRangeId));
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

      sb.append("SecurityAccRngRel: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
