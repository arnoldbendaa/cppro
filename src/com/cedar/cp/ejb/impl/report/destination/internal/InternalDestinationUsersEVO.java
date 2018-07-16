// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.internal;

import com.cedar.cp.api.report.destination.internal.InternalDestinationUsersRef;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersCK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersRefImpl;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class InternalDestinationUsersEVO implements Serializable {

   private transient InternalDestinationUsersPK mPK;
   private int mInternalDestinationId;
   private int mUserId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public InternalDestinationUsersEVO() {}

   public InternalDestinationUsersEVO(int newInternalDestinationId, int newUserId) {
      this.mInternalDestinationId = newInternalDestinationId;
      this.mUserId = newUserId;
   }

   public InternalDestinationUsersPK getPK() {
      if(this.mPK == null) {
         this.mPK = new InternalDestinationUsersPK(this.mInternalDestinationId, this.mUserId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getInternalDestinationId() {
      return this.mInternalDestinationId;
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

   public void setInternalDestinationId(int newInternalDestinationId) {
      if(this.mInternalDestinationId != newInternalDestinationId) {
         this.mModified = true;
         this.mInternalDestinationId = newInternalDestinationId;
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

   public void setDetails(InternalDestinationUsersEVO newDetails) {
      this.setInternalDestinationId(newDetails.getInternalDestinationId());
      this.setUserId(newDetails.getUserId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public InternalDestinationUsersEVO deepClone() {
      InternalDestinationUsersEVO cloned = new InternalDestinationUsersEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mInternalDestinationId = this.mInternalDestinationId;
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

   public void prepareForInsert(InternalDestinationEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(InternalDestinationEVO parent, int startKey) {
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

   public InternalDestinationUsersRef getEntityRef(InternalDestinationEVO evoInternalDestination, String entityText) {
      return new InternalDestinationUsersRefImpl(new InternalDestinationUsersCK(evoInternalDestination.getPK(), this.getPK()), entityText);
   }

   public InternalDestinationUsersCK getCK(InternalDestinationEVO evoInternalDestination) {
      return new InternalDestinationUsersCK(evoInternalDestination.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("InternalDestinationId=");
      sb.append(String.valueOf(this.mInternalDestinationId));
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

      sb.append("InternalDestinationUsers: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
