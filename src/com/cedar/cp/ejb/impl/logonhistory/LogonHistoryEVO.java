// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.logonhistory;

import com.cedar.cp.api.logonhistory.LogonHistoryRef;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.dto.logonhistory.LogonHistoryRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class LogonHistoryEVO implements Serializable {

   private transient LogonHistoryPK mPK;
   private int mLogonHistoryId;
   private String mUserName;
   private Timestamp mEventDate;
   private int mEventType;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;
   public static final int TIMED_OUT = -2;
   public static final int LOGOFF = -1;
   public static final int SUCCESSFUL = 1;
   public static final int FAILED = 2;


   public LogonHistoryEVO() {}

   public LogonHistoryEVO(int newLogonHistoryId, String newUserName, Timestamp newEventDate, int newEventType, int newVersionNum) {
      this.mLogonHistoryId = newLogonHistoryId;
      this.mUserName = newUserName;
      this.mEventDate = newEventDate;
      this.mEventType = newEventType;
      this.mVersionNum = newVersionNum;
   }

   public LogonHistoryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new LogonHistoryPK(this.mLogonHistoryId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getLogonHistoryId() {
      return this.mLogonHistoryId;
   }

   public String getUserName() {
      return this.mUserName;
   }

   public Timestamp getEventDate() {
      return this.mEventDate;
   }

   public int getEventType() {
      return this.mEventType;
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

   public void setLogonHistoryId(int newLogonHistoryId) {
      if(this.mLogonHistoryId != newLogonHistoryId) {
         this.mModified = true;
         this.mLogonHistoryId = newLogonHistoryId;
         this.mPK = null;
      }
   }

   public void setEventType(int newEventType) {
      if(this.mEventType != newEventType) {
         this.mModified = true;
         this.mEventType = newEventType;
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

   public void setUserName(String newUserName) {
      if(this.mUserName != null && newUserName == null || this.mUserName == null && newUserName != null || this.mUserName != null && newUserName != null && !this.mUserName.equals(newUserName)) {
         this.mUserName = newUserName;
         this.mModified = true;
      }

   }

   public void setEventDate(Timestamp newEventDate) {
      if(this.mEventDate != null && newEventDate == null || this.mEventDate == null && newEventDate != null || this.mEventDate != null && newEventDate != null && !this.mEventDate.equals(newEventDate)) {
         this.mEventDate = newEventDate;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(LogonHistoryEVO newDetails) {
      this.setLogonHistoryId(newDetails.getLogonHistoryId());
      this.setUserName(newDetails.getUserName());
      this.setEventDate(newDetails.getEventDate());
      this.setEventType(newDetails.getEventType());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public LogonHistoryEVO deepClone() {
      LogonHistoryEVO cloned = new LogonHistoryEVO();
      cloned.mModified = this.mModified;
      cloned.mLogonHistoryId = this.mLogonHistoryId;
      cloned.mEventType = this.mEventType;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUserName != null) {
         cloned.mUserName = this.mUserName;
      }

      if(this.mEventDate != null) {
         cloned.mEventDate = Timestamp.valueOf(this.mEventDate.toString());
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
      if(this.mLogonHistoryId > 0) {
         newKey = true;
         this.mLogonHistoryId = 0;
      } else if(this.mLogonHistoryId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mLogonHistoryId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mLogonHistoryId < 1) {
         this.mLogonHistoryId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public LogonHistoryRef getEntityRef(String entityText) {
      return new LogonHistoryRefImpl(this.getPK(), entityText);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("LogonHistoryId=");
      sb.append(String.valueOf(this.mLogonHistoryId));
      sb.append(' ');
      sb.append("UserName=");
      sb.append(String.valueOf(this.mUserName));
      sb.append(' ');
      sb.append("EventDate=");
      sb.append(String.valueOf(this.mEventDate));
      sb.append(' ');
      sb.append("EventType=");
      sb.append(String.valueOf(this.mEventType));
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

      sb.append("LogonHistory: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
