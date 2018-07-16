// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.logonhistory;

import com.cedar.cp.api.logonhistory.LogonHistory;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import java.io.Serializable;
import java.sql.Timestamp;

public class LogonHistoryImpl implements LogonHistory, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mUserName;
   private Timestamp mEventDate;
   private int mEventType;
   private int mVersionNum;


   public LogonHistoryImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mUserName = "";
      this.mEventDate = null;
      this.mEventType = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (LogonHistoryPK)paramKey;
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

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setUserName(String paramUserName) {
      this.mUserName = paramUserName;
   }

   public void setEventDate(Timestamp paramEventDate) {
      this.mEventDate = paramEventDate;
   }

   public void setEventType(int paramEventType) {
      this.mEventType = paramEventType;
   }
}
