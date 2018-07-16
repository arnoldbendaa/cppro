// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.passwordhistory;

import com.cedar.cp.api.passwordhistory.PasswordHistoryRef;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class PasswordHistoryEVO implements Serializable {

   private transient PasswordHistoryPK mPK;
   private int mPasswordHistoryId;
   private int mUserId;
   private String mPasswordBytes;
   private Timestamp mPasswordDate;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;


   public PasswordHistoryEVO() {}

   public PasswordHistoryEVO(int newPasswordHistoryId, int newUserId, String newPasswordBytes, Timestamp newPasswordDate, int newVersionNum) {
      this.mPasswordHistoryId = newPasswordHistoryId;
      this.mUserId = newUserId;
      this.mPasswordBytes = newPasswordBytes;
      this.mPasswordDate = newPasswordDate;
      this.mVersionNum = newVersionNum;
   }

   public PasswordHistoryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new PasswordHistoryPK(this.mPasswordHistoryId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getPasswordHistoryId() {
      return this.mPasswordHistoryId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getPasswordBytes() {
      return this.mPasswordBytes;
   }

   public Timestamp getPasswordDate() {
      return this.mPasswordDate;
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

   public void setPasswordHistoryId(int newPasswordHistoryId) {
      if(this.mPasswordHistoryId != newPasswordHistoryId) {
         this.mModified = true;
         this.mPasswordHistoryId = newPasswordHistoryId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
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

   public void setPasswordBytes(String newPasswordBytes) {
      if(this.mPasswordBytes != null && newPasswordBytes == null || this.mPasswordBytes == null && newPasswordBytes != null || this.mPasswordBytes != null && newPasswordBytes != null && !this.mPasswordBytes.equals(newPasswordBytes)) {
         this.mPasswordBytes = newPasswordBytes;
         this.mModified = true;
      }

   }

   public void setPasswordDate(Timestamp newPasswordDate) {
      if(this.mPasswordDate != null && newPasswordDate == null || this.mPasswordDate == null && newPasswordDate != null || this.mPasswordDate != null && newPasswordDate != null && !this.mPasswordDate.equals(newPasswordDate)) {
         this.mPasswordDate = newPasswordDate;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(PasswordHistoryEVO newDetails) {
      this.setPasswordHistoryId(newDetails.getPasswordHistoryId());
      this.setUserId(newDetails.getUserId());
      this.setPasswordBytes(newDetails.getPasswordBytes());
      this.setPasswordDate(newDetails.getPasswordDate());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public PasswordHistoryEVO deepClone() {
      PasswordHistoryEVO cloned = new PasswordHistoryEVO();
      cloned.mModified = this.mModified;
      cloned.mPasswordHistoryId = this.mPasswordHistoryId;
      cloned.mUserId = this.mUserId;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mPasswordBytes != null) {
         cloned.mPasswordBytes = this.mPasswordBytes;
      }

      if(this.mPasswordDate != null) {
         cloned.mPasswordDate = Timestamp.valueOf(this.mPasswordDate.toString());
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
      if(this.mPasswordHistoryId > 0) {
         newKey = true;
         this.mPasswordHistoryId = 0;
      } else if(this.mPasswordHistoryId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mPasswordHistoryId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mPasswordHistoryId < 1) {
         this.mPasswordHistoryId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public PasswordHistoryRef getEntityRef(String entityText) {
      return new PasswordHistoryRefImpl(this.getPK(), entityText);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("PasswordHistoryId=");
      sb.append(String.valueOf(this.mPasswordHistoryId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("PasswordBytes=");
      sb.append(String.valueOf(this.mPasswordBytes));
      sb.append(' ');
      sb.append("PasswordDate=");
      sb.append(String.valueOf(this.mPasswordDate));
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

      sb.append("PasswordHistory: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
