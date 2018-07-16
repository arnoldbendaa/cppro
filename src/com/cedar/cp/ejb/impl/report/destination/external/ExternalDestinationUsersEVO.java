// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.external;

import com.cedar.cp.api.report.destination.external.ExternalDestinationUsersRef;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersCK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersRefImpl;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ExternalDestinationUsersEVO implements Serializable {

   private transient ExternalDestinationUsersPK mPK;
   private int mExternalDestinationId;
   private int mExternalDestinationUsersId;
   private String mEmailAddress;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExternalDestinationUsersEVO() {}

   public ExternalDestinationUsersEVO(int newExternalDestinationId, int newExternalDestinationUsersId, String newEmailAddress) {
      this.mExternalDestinationId = newExternalDestinationId;
      this.mExternalDestinationUsersId = newExternalDestinationUsersId;
      this.mEmailAddress = newEmailAddress;
   }

   public ExternalDestinationUsersPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExternalDestinationUsersPK(this.mExternalDestinationId, this.mExternalDestinationUsersId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getExternalDestinationId() {
      return this.mExternalDestinationId;
   }

   public int getExternalDestinationUsersId() {
      return this.mExternalDestinationUsersId;
   }

   public String getEmailAddress() {
      return this.mEmailAddress;
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

   public void setExternalDestinationId(int newExternalDestinationId) {
      if(this.mExternalDestinationId != newExternalDestinationId) {
         this.mModified = true;
         this.mExternalDestinationId = newExternalDestinationId;
         this.mPK = null;
      }
   }

   public void setExternalDestinationUsersId(int newExternalDestinationUsersId) {
      if(this.mExternalDestinationUsersId != newExternalDestinationUsersId) {
         this.mModified = true;
         this.mExternalDestinationUsersId = newExternalDestinationUsersId;
         this.mPK = null;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setEmailAddress(String newEmailAddress) {
      if(this.mEmailAddress != null && newEmailAddress == null || this.mEmailAddress == null && newEmailAddress != null || this.mEmailAddress != null && newEmailAddress != null && !this.mEmailAddress.equals(newEmailAddress)) {
         this.mEmailAddress = newEmailAddress;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ExternalDestinationUsersEVO newDetails) {
      this.setExternalDestinationId(newDetails.getExternalDestinationId());
      this.setExternalDestinationUsersId(newDetails.getExternalDestinationUsersId());
      this.setEmailAddress(newDetails.getEmailAddress());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ExternalDestinationUsersEVO deepClone() {
      ExternalDestinationUsersEVO cloned = new ExternalDestinationUsersEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mExternalDestinationId = this.mExternalDestinationId;
      cloned.mExternalDestinationUsersId = this.mExternalDestinationUsersId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mEmailAddress != null) {
         cloned.mEmailAddress = this.mEmailAddress;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(ExternalDestinationEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mExternalDestinationUsersId > 0) {
         newKey = true;
         if(parent == null) {
            this.setExternalDestinationUsersId(-this.mExternalDestinationUsersId);
         } else {
            parent.changeKey(this, this.mExternalDestinationId, -this.mExternalDestinationUsersId);
         }
      } else if(this.mExternalDestinationUsersId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mExternalDestinationUsersId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(ExternalDestinationEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mExternalDestinationUsersId < 1) {
         parent.changeKey(this, this.mExternalDestinationId, startKey);
         nextKey = startKey + 1;
      }

      return nextKey;
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

   public ExternalDestinationUsersRef getEntityRef(ExternalDestinationEVO evoExternalDestination, String entityText) {
      return new ExternalDestinationUsersRefImpl(new ExternalDestinationUsersCK(evoExternalDestination.getPK(), this.getPK()), entityText);
   }

   public ExternalDestinationUsersCK getCK(ExternalDestinationEVO evoExternalDestination) {
      return new ExternalDestinationUsersCK(evoExternalDestination.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalDestinationId=");
      sb.append(String.valueOf(this.mExternalDestinationId));
      sb.append(' ');
      sb.append("ExternalDestinationUsersId=");
      sb.append(String.valueOf(this.mExternalDestinationUsersId));
      sb.append(' ');
      sb.append("EmailAddress=");
      sb.append(String.valueOf(this.mEmailAddress));
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

      sb.append("ExternalDestinationUsers: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
